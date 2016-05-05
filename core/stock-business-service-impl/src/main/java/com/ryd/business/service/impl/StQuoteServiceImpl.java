package com.ryd.business.service.impl;

import com.ryd.basecommon.util.*;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.dao.StQuoteDao;
import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.dto.StTradeQueueDTO;
import com.ryd.business.exception.QuoteBusinessException;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.*;
import com.ryd.business.service.thread.GenerateSimulationQuoteThread;
import com.ryd.business.service.thread.SyncStockThread;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.business.service.util.Utils;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.service.StDateScheduleService;
import com.ryd.system.service.StSystemParamService;
import org.apache.commons.lang.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>标题:报价业务实现类</p>
 * <p>描述:报价业务实现类</p>
 * 包名：com.ryd.business.service.impl
 * 创建人：chenji
 * 创建时间：2016/4/25 10:11
 */
@Service
public class StQuoteServiceImpl implements StQuoteService {
    @Autowired
    private StDateScheduleService stDateScheduleService;
    @Autowired
    private StQuoteDao stQuoteDao;
    @Autowired
    private StSystemParamService stSystemParamService;
    @Autowired
    private StAccountService stAccountService;
    @Autowired
    private StPositionService stPositionService;
    @Autowired
    private StStockService stStockService;
    @Autowired
    private StStockConfigService stStockConfigService;
    @Autowired
    private ICacheService iCacheService;

    @Override
    public StQuote findQuoteById(StQuote quote) {
        if (StringUtils.isEmpty(quote)&&StringUtils.isEmpty(quote.getQuoteId())) {
            return null;
        }
        return stQuoteDao.getTById(quote.getQuoteId());
    }

    @Override
    public Integer saveQuoteList(StQuote quote) throws Exception {
        return this.saveQuoteList(Arrays.asList(quote), ApplicationConstants.ACCOUNT_TYPE_REAL);
    }

    @Override
    public Integer saveQuoteList(List<StQuote> quoteList) throws Exception {
        return this.saveQuoteList(quoteList, ApplicationConstants.ACCOUNT_TYPE_REAL);
    }

    @Override
    public Integer updateWithDrawQuote(StQuote quote) throws Exception {
        return this.updateQuoteList(Arrays.asList(quote));
    }

    @Override
    public Integer saveQuoteList(List<StQuote> quoteList, int type) throws Exception{
        Integer quoteFlag = 0;
        boolean rs = false;
        StQuote stQuote = null;
        // 验证报价参数
        // 验证报价状态（是否可以报价）
//        1、是否节假日getIsFestival 2、是否交易时间
        // 将报价保存到mysql数据库 失败后回滚
        // 将报价加入队列 失败后回滚

        // 将报价放入Kafka 失败后不回滚
        if (type==ApplicationConstants.ACCOUNT_TYPE_REAL) {
            if (StringUtils.isEmpty(quoteList)) return -1; // 参数不匹配
            // 交易时间内
            if (!stDateScheduleService.getIsCanQuote()) {
                // 非交易时间
                return -2;
            }
            // 价格是否匹配涨跌幅
            for (StQuote quote: quoteList) {
                SearchStockDTO sdto = new SearchStockDTO();
                sdto.setStockId(quote.getStockId());
                StStock stock = stStockService.findStockListByStock(sdto);
                if (stock==null) {
                    // 股票价格信息不存在
                    throw new QuoteBusinessException("股票价格信息不存在");
                }
                quote.setStockCode(stock.getStockCode());
                if (!isStockQuotePriceInScope(BigDecimal.valueOf(stock.getBfclosePrice()), quote.getQuotePrice())) {
                    // 超出涨跌幅
                    return -3;
                }
                if (quote.getDateTime()==null||quote.getDateTime()==0L) {
                    quote.setDateTime(System.currentTimeMillis());
                }
            }
            // 账户金额是否够用
            for (StQuote quote: quoteList) {
                rs = false;
                quote.setQuoteId(UUIDUtils.uuidTrimLine());
                // 用于排序的字段
//                long timeSort = Integer.parseInt(String.valueOf(quote.getDateTime()).substring(7));
//                quote.setTimeSort(timeSort);

                quote.setCurrentAmount(quote.getAmount());
                quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
                quote.setUserType(quote.getUserType() == null ? ApplicationConstants.ACCOUNT_TYPE_REAL : quote.getUserType());
    //            quote.setDateTime(System.currentTimeMillis());
                //买股票
                if (quote.getQuoteType().shortValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY.shortValue()) {

                    BigDecimal money = ArithUtil.multiply(quote.getQuotePrice(), new BigDecimal(quote.getAmount().toString()));
                    //佣金比例
                    String commissionPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_COMMINSSION_PERCENT);
                    //计算佣金
                    BigDecimal commissionFee = ArithUtil.multiply(money, new BigDecimal(commissionPercent));

                    quote.setFrozeMoney(ArithUtil.add(money, commissionFee));
                    quote.setCommissionFee(commissionFee);
                    //买家减少资产
                    rs = stAccountService.updateStAccountMoneyReduce(quote.getAccountId(), quote.getFrozeMoney());
                    if (!rs) {
                        //用户金额不足
                        throw new QuoteBusinessException("用户资金不足");
                    }
                } else if (quote.getQuoteType().shortValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL.shortValue()) { //卖股票

                    //委托卖股票，减少股票持仓数量
                    rs = stPositionService.updatePositionReduce(quote.getAccountId(), quote.getStockId(), quote.getAmount());
                    if (!rs) {
                        //用户持仓不足
                        throw new QuoteBusinessException("用户持仓不足");
                    }
                }
                stQuote = quote;
            }
        }
        // 添加报价到队列，同时保存到数据库 失败后回滚
        boolean ars = false;

        if (type==ApplicationConstants.ACCOUNT_TYPE_REAL) {
            ars = addQuoteToQueue(quoteList);
        } else {
            ars = addSimulationQuoteToQueue(quoteList);
        }

        if(ars) {
            quoteFlag = 1;
        }else{
            throw new QuoteBusinessException("添加队列不成功");
        }

        return quoteFlag;
    }

    @Override
    public Integer updateQuoteList(List<StQuote> quoteList) throws Exception{
        if (StringUtils.isEmpty(quoteList)) {
            return -1;
        }
        for (StQuote quote : quoteList) {
            //状态为托管报价-撤单
            if(quote.getStatus().shortValue() == ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE.shortValue()){
                boolean rs = false;
                if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                    //撤回托管买股票费用,帐户增加资产
                    rs = stAccountService.updateStAccountMoneyAdd(quote.getAccountId(),quote.getFrozeMoney());
                } else if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
                    //撤回托管为卖的股票，持仓数量增加
                    rs = stPositionService.updatePositionAdd(quote.getAccountId(), quote.getStockId(), quote.getCurrentAmount());
                }
                if(rs){
                    //修改报价状态
                    quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_NOTDEAL);
                    updateQuote(quote);
                    if(!this.removeByQuote(quote)){
                        throw new QuoteBusinessException("撤单删除失败");
                    }
                }
            }else{
                return -3;
            }
        }

        return 1;
    }

    @Override
    public Integer updateQuote(StQuote quote) {
        return stQuoteDao.update(quote);
    }

    @Override
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO) {
        StQuote stQuote = new StQuote();
        stQuote.setAccountId(searchQuoteDTO.getAccountId());

        Long startTime = searchQuoteDTO.getQuoteStartDate()==null?null:searchQuoteDTO.getQuoteStartDate().getTime();
        Long endTime = searchQuoteDTO.getQuoteEndDate()==null?null:searchQuoteDTO.getQuoteEndDate().getTime();
        List<StQuote> quoteList = stQuoteDao.getTList(stQuote, startTime, endTime, searchQuoteDTO.getLimit(), searchQuoteDTO.getOffset());
        return quoteList;
    }

    @Override
    public List<String> findQuoteStockIdList() {
//        Object stockIdListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, null);
//        if (stockIdListObj == null) {
//            return null;
//        }
//        List<String> stockIdList = (List<String>) stockIdListObj;
        Collection<String> stockIdList = BusinessConstants.stTradeQueueMap.keySet();
        List<String> stockIdListNew = new ArrayList<String>(stockIdList);
        return stockIdListNew;
    }

    @Override
    public List<StQuote> findQuoteQueueByStock(SearchQuoteDTO searchQuoteDTO) {
        // TODO 缓存问题
        // 从缓存中获取
        ConcurrentSkipListMap<Long, StQuote> quoteList = null;

        Object quoteobj = null;
        if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, searchQuoteDTO.getStockCode(), null);
        } else if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, searchQuoteDTO.getStockCode(), null);
        }
        quoteList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
        Collection<StQuote> collectionList = quoteList.values();
        List<StQuote> list = new ArrayList<StQuote>();
        list.addAll(collectionList);
        return list;
    }

    @Override
    public StQuote findFirstQuoteByStock(SearchQuoteDTO searchQuoteDTO) {
        StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(searchQuoteDTO.getStockCode());
        if (tradeQueueDTO==null) {
            return null;
        }
        // 获取队列中的第一条买/卖报价
        StQuote quote = tradeQueueDTO.getFristStQuoteFromQueue(searchQuoteDTO.getQuoteType());
        return quote;
        // 从缓存中获取
        /*ConcurrentSkipListMap<Long, StQuote> quoteList = null;
        Object quoteobj = null;
        if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, searchQuoteDTO.getStockCode(), null);
        } else if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, searchQuoteDTO.getStockCode(), null);
        }
        if (quoteobj==null) return null;
        quoteList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
        Map.Entry<Long, StQuote> sellMap = quoteList.higherEntry(Long.MIN_VALUE);
        return sellMap==null?null:sellMap.getValue();*/
    }

    @Override
    public boolean addQuoteToQueue(List<StQuote> addQuoteList) {
        // 入库
        stQuoteDao.addBatch(addQuoteList);
        // 添加模拟报价
        this.addSimulationQuoteToQueue(addQuoteList);
        /*for (StQuote quote : addQuoteList) {
            Object quoteobj = null;
            ConcurrentSkipListMap<Long, StQuote> quoteList = null;
            if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
                if (quoteobj!=null) {
                    quoteList = (ConcurrentSkipListMap<Long, StQuote>)iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
                } else {
                    quoteList = new ConcurrentSkipListMap<Long, StQuote>();
                }
                Long quotePriceForSort = -1 * Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + quote.getTimeSort();
                quote.setQuotePriceForSort(quotePriceForSort);
                quoteList.put(quotePriceForSort, quote);


                // 存入缓存
                iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), quoteList, 8 * 60 * 60);
            } else {
                if (quoteobj!=null) {
                    quoteList = (ConcurrentSkipListMap<Long, StQuote>)iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
                } else {
                    quoteList = new ConcurrentSkipListMap<Long, StQuote>();
                }
                quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), null);
                Long quotePriceForSort = Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + quote.getTimeSort();
                quote.setQuotePriceForSort(quotePriceForSort);
                quoteList.put(quotePriceForSort, quote);
                // 存入缓存
                iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), quoteList, 8 * 60 * 60);
            }*/

            /*Object stockIdListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, null);
            if (stockIdListObj != null) {
                List<String> stockIdList = (List<String>) stockIdListObj;
                if (!stockIdList.contains(quote.getStockId())) {
                    stockIdList.add(quote.getStockId());

                    // 存入缓存
                    iCacheService.setObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, stockIdList);
                }
            }
        }*/

        return true;
    }

    @Override
    public boolean addSimulationQuoteToQueue(List<StQuote> addQuoteList) {
        // 入库
//        stQuoteDao.addBatch(addQuoteList);

        StTradeQueueDTO tradeQueueDTO = null;
        for (StQuote quote : addQuoteList) {
            tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(quote.getStockCode());
            if (tradeQueueDTO == null) {
                tradeQueueDTO = new StTradeQueueDTO();
            }
            if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY.intValue()) {
                tradeQueueDTO.addBuyStQuote(quote);
            } else {
                tradeQueueDTO.addSellStQuote(quote);
            }
            BusinessConstants.stTradeQueueMap.put(quote.getStockCode(), tradeQueueDTO);
        }

        /*ConcurrentSkipListMap<Long, StQuote> quoteList = new ConcurrentSkipListMap<Long, StQuote>();
        for (StQuote quote : addQuoteList) {
            if (quoteType==0) {
                quoteType = quote.getQuoteType();
                stockId = quote.getStockId();
            }
            Long quotePriceForSort = -1 * Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + quote.getTimeSort();
            quote.setQuotePriceForSort(quotePriceForSort);
            quoteList.put(quotePriceForSort, quote);
        }

        ConcurrentSkipListMap<Long, StQuote> quoteListTemp = null;
        Object quoteobj = null;
        if (quoteType == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, stockId, null);
            if (quoteobj!=null) {
                quoteListTemp = (ConcurrentSkipListMap<Long, StQuote>)quoteobj;
            } else {
                quoteListTemp = new ConcurrentSkipListMap<Long, StQuote>();
            }
            quoteListTemp.putAll(quoteList);

            // 存入缓存
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, stockId, quoteListTemp, 8 * 60 * 60);
        } else {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, stockId, null);
            if (quoteobj!=null) {
                quoteListTemp = (ConcurrentSkipListMap<Long, StQuote>)quoteobj;
            } else {
                quoteListTemp = new ConcurrentSkipListMap<Long, StQuote>();
            }
            quoteListTemp.putAll(quoteList);

            // 存入缓存
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, stockId, quoteListTemp, 8 * 60 * 60);
        }*/

        /*Object stockIdListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, null);
        if (stockIdListObj != null) {
            List<String> stockIdList = (List<String>) stockIdListObj;
            if (!stockIdList.contains(quote.getStockId())) {
                stockIdList.add(quote.getStockId());

                // 存入缓存
                iCacheService.setObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, stockIdList);
            }
        }*/
        return true;
    }

    @Override
    public boolean deleteQuoteFromQueue(StQuote quote) {
        // 从队列中删除报价，同时修改报价状态
        StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(quote.getStockId());
        if (tradeQueueDTO==null) {
            return false;
        }
        if (quote.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            tradeQueueDTO.removeBuyStQuote(quote);
        } else {
            tradeQueueDTO.removeSellStQuote(quote);
        }
        BusinessConstants.stTradeQueueMap.put(quote.getStockCode(), tradeQueueDTO);

        /*Object quoteobj = null;
        ConcurrentSkipListMap<Long, StQuote> quoteList = null;
        if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
            if (quoteobj!=null) {
                quoteList = (ConcurrentSkipListMap<Long, StQuote>)iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
            } else {
                quoteList = new ConcurrentSkipListMap<Long, StQuote>();
            }
            Long quotePriceForSort = -1 * Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + quote.getTimeSort();
//            quote.setQuotePriceForSort(quotePriceForSort);
            quoteList.remove(quotePriceForSort);
            // 存入缓存
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), quoteList, 8 * 60 * 60);
        } else {
            if (quoteobj!=null) {
                quoteList = (ConcurrentSkipListMap<Long, StQuote>)iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
            } else {
                quoteList = new ConcurrentSkipListMap<Long, StQuote>();
            }
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), null);
            Long quotePriceForSort = Long.parseLong("100000000") * (long)(quote.getQuotePrice().doubleValue()*100) + quote.getTimeSort();
            quote.setQuotePriceForSort(quotePriceForSort);
            quoteList.remove(quotePriceForSort);
            // 存入缓存
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), quoteList, 8 * 60 * 60);
        }*/
        return true;
    }

    /**
     * 从队列中删除报价
     * @param quote
     * @return
     */
    private boolean removeByQuote(StQuote quote) {
        return deleteQuoteFromQueue(quote);
        /*ConcurrentSkipListMap<Long, StQuote> quoteQueueList = null;
        Object quoteobj = null;
        if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
        } else if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), null);
        }
        StQuote stQuote = null;
        if (quoteobj!=null) {
            quoteQueueList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
            stQuote = quoteQueueList.remove(Utils.getQuoteKeyByQuote(quote));
        }

        if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), quoteQueueList, 8 * 60 * 60);
        } else if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
            iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), quoteQueueList, 8 * 60 * 60);
        }
        return stQuote==null?false:true;*/
    }

    @Override
    public void executeSimulationQuote() {
        // 1、获取最新一次的股票价格-买一、买二、卖一、卖二
        // 2、获取模拟马甲用户
        // 3、多线程生成马甲订单
        long simulationCount = 0;
        List<StQuote> newStQuoteList = new ArrayList<StQuote>();
        for (String stockCode : BusinessConstants.simulateQuoteMap.keySet()) {
            List<SimulationQuoteDTO> simulationQuoteDTOList = BusinessConstants.simulateQuoteMap.get(stockCode);
            if (!StringUtils.isEmpty(simulationQuoteDTOList)) {
                List<StQuote> stQuoteList = new ArrayList<StQuote>();
                for (SimulationQuoteDTO simulationQuoteDTO : simulationQuoteDTOList) {
                    StQuote quote = new StQuote();
                    quote.setAccountId("800891cdc704462ab0c2335460a91684");
                    String stockId = stStockConfigService.getStockIdByStockCode(stockCode);
                    quote.setStockId(stockId);
                    quote.setStockCode(stockCode);
                    quote.setUserType(ApplicationConstants.ACCOUNT_TYPE_VIRTUAL); // 马甲用户
                    quote.setQuoteType(simulationQuoteDTO.getQuoteType());
                    quote.setAmount(simulationQuoteDTO.getAmount());
                    quote.setQuotePrice(BigDecimal.valueOf(simulationQuoteDTO.getQuotePrice()));
                    quote.setQuoteId(UUIDUtils.uuidTrimLine());
                    quote.setDateTime(simulationQuoteDTO.getDateTime());
                    // 用于排序的字段
//                    long timeSort = Integer.parseInt(String.valueOf(simulationQuoteDTO.getDateTime()).substring(7));
//                    quote.setTimeSort(timeSort);

                    quote.setCurrentAmount(quote.getAmount());
                    quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
                    stQuoteList.add(quote);
                }
                newStQuoteList.addAll(stQuoteList);
                stQuoteList.clear();
                try {
                    if (newStQuoteList!=null&&newStQuoteList.size()>=100) {
                        simulationCount += newStQuoteList.size();
                        this.saveQuoteList(newStQuoteList, ApplicationConstants.ACCOUNT_TYPE_VIRTUAL);
                        newStQuoteList.clear();
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (newStQuoteList!=null&&newStQuoteList.size()>0) {
            try {
                simulationCount += newStQuoteList.size();
                this.saveQuoteList(newStQuoteList, ApplicationConstants.ACCOUNT_TYPE_VIRTUAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("simulationCount:"+simulationCount);


        /*ExecutorService stockService = Executors.newFixedThreadPool(10);
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务

        List<StStockConfig> list = stStockConfigService.findStockConfig(null, 1, Integer.MAX_VALUE);
        int count = list.size();
        cdOrder.countDown();
        final CountDownLatch cdAnswer = new CountDownLatch(count);
        StringBuffer stockCodeStringBuffer = new StringBuffer();;
        for (StStockConfig stock: list) {
                // 同步股票信息
                stockService.execute(new GenerateSimulationQuoteThread(cdOrder, cdAnswer, stock.getStockCode(), iCacheService, this));
        }

        try {
            // 等待任务执行完成
            cdAnswer.await();
            // 股票下载任务执行完成，通知交易引擎停止运行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }

    /**
     * 是否在范围内报价
     * @param closePrice 昨日收盘价
     * @param quotePrice 当前报价
     * @return
     */
    private boolean isStockQuotePriceInScope(BigDecimal closePrice, BigDecimal quotePrice){

        BigDecimal extent = ArithUtil.multiply(closePrice, new BigDecimal(stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_STOCK_UP_AND_DOWN_PERCENT)));

        BigDecimal maxPrice = ArithUtil.add(closePrice, extent);
        BigDecimal minPrice = ArithUtil.subtract(closePrice,extent);

        //报价大于等于最小价格，小于等于最大价格，可以正常报价
        if((ArithUtil.compare(quotePrice, minPrice)>=0) && (ArithUtil.compare(quotePrice, maxPrice)<=0)){
            return true;
        }

        return false;
    }
}