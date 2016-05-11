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
import com.ryd.business.service.*;
import com.ryd.business.service.thread.GenerateSimulationQuoteThread;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.business.service.util.Utils;
import com.ryd.cache.service.ICacheService;
import com.ryd.messagequeue.service.IMessageQueue;
import com.ryd.system.service.StDateScheduleService;
import com.ryd.system.service.StSystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.*;

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
    @Autowired
    private IMessageQueue iMessageQueue;

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
        // 验证报价参数
        // 验证报价状态（是否可以报价）
        //是否交易时间
        // 将报价保存到mysql数据库 失败后回滚
        // 将报价加入队列 失败后回滚

        // 将报价放入Kafka 失败后不回滚
        if (type==ApplicationConstants.ACCOUNT_TYPE_REAL) {
            if (StringUtils.isEmpty(quoteList)) return -1; // 参数不匹配
            // 交易时间内
            if (BusinessConstants.isCanQuote) {
                // 非交易时间
                return -2;
            }
            // 价格是否匹配涨跌幅
            for (StQuote quote: quoteList) {

                if(quote.getAmount()%100 == 1){
                    //数量必须为100的整数倍
                    return -3;
                }
                SearchStockDTO sdto = new SearchStockDTO();
                sdto.setStockId(quote.getStockId());
                StStock stock = stStockService.findStockListByStock(sdto);
                if (stock==null) {
                    // 股票价格信息不存在
                    throw new QuoteBusinessException("股票价格信息不存在");
                }
                quote.setStockCode(stock.getStockCode());
                if (!isStockQuotePriceInScope(stock, quote.getQuotePrice())) {
                    // 超出涨跌幅
                    return -4;
                }
                quote.setDateTime(System.currentTimeMillis());
            }
            // 账户金额是否够用
            for (StQuote quote: quoteList) {
                quote.setQuoteId(UUIDUtils.uuidTrimLine());
                quote.setCurrentAmount(quote.getAmount());
                quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
                quote.setUserType(ApplicationConstants.ACCOUNT_TYPE_REAL);
                //买股票
                if (quote.getQuoteType().shortValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY.shortValue()) {

                    //佣金比例
                    String commissionPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_COMMINSSION_PERCENT);
                    //最小佣金值
                    String minCommissionFee = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_COMMISIONFEE_MIN);

                    //买家购买股票消费资产
                    BigDecimal[] rsArr = Utils.calculate(quote.getQuotePrice(), quote.getAmount(), quote.getQuoteType(), commissionPercent, null, minCommissionFee, null);

                    quote.setFrozeMoney(ArithUtil.scale(rsArr[1]));
                    quote.setCommissionFee(ArithUtil.scale(rsArr[0]));
                    //买家减少资产
                    rs = stAccountService.updateStAccountMoneyReduce(quote.getAccountId(), quote.getFrozeMoney());

                } else if (quote.getQuoteType().shortValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL.shortValue()) { //卖股票

                    //委托卖股票，减少股票持仓数量
                    rs = stPositionService.updatePositionReduce(quote.getAccountId(), quote.getStockId(), quote.getAmount());
                }
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
                    rs = stPositionService.updatePositionRevokeAdd(quote.getAccountId(), quote.getStockId(), quote.getCurrentAmount());
                }
                if(rs){
                    //修改报价状态
                    quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_NOTDEAL);
                    updateQuote(quote);
                    if(!this.deleteQuoteFromQueue(quote)){
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
    public boolean updateQuote(StQuote quote) throws Exception{
        boolean rs = stQuoteDao.update(quote) > 0;
        if(!rs){
            throw new QuoteBusinessException("报价修改失败");
        }
        return rs;
    }

    @Override
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO) {
        StQuote stQuote = new StQuote();
        stQuote.setAccountId(searchQuoteDTO.getAccountId());
        stQuote.setStatus(searchQuoteDTO.getStatus());

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
        if (BusinessConstants.stTradeQueueMap.isEmpty()) {
            this.findStQuoteToCache(1000);
        }
        Collection<String> stockIdList = BusinessConstants.stTradeQueueMap.keySet();
        List<String> stockIdListNew = new ArrayList<String>(stockIdList);
        stockIdList=null;
        return stockIdListNew;
    }

    @Override
    public List<StQuote> findQuoteQueueByStock(SearchQuoteDTO searchQuoteDTO) {
        // TODO 缓存问题
        // 从缓存中获取
        ConcurrentSkipListMap<Long, StQuote> quoteList = null;

        if (BusinessConstants.stTradeQueueMap.isEmpty()) {
            this.findStQuoteToCache(1000);
        }
        String stockCode = stStockConfigService.getStockCodeByStockId(searchQuoteDTO.getStockId());
        searchQuoteDTO.setStockCode(stockCode);
        // 总的报价队列列表
        if (StringUtils.isEmpty(searchQuoteDTO.getStockCode())) {
            // TODO 返回总的买/卖队列
            quoteList = findQuoteQueueByType(searchQuoteDTO);
        } else {
            // 单只股票队列
            StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(searchQuoteDTO.getStockCode());
            if (tradeQueueDTO == null) return null;
            if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                quoteList = tradeQueueDTO.buyList;
            } else if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_SELL) {
                quoteList = tradeQueueDTO.sellList;
            }
            tradeQueueDTO = null;
        }
        Collection<StQuote> collectionList = quoteList.values();
        List<StQuote> list = new ArrayList<StQuote>();
        list.addAll(collectionList);
        return list;
    }

    // 返回总的买卖队列
    private ConcurrentSkipListMap<Long, StQuote> findQuoteQueueByType(SearchQuoteDTO searchQuoteDTO) {
        // TODO 待优化
        ConcurrentSkipListMap<Long, StQuote> quoteList = null;
        // 单只股票队列
        for (String key : BusinessConstants.stTradeQueueMap.keySet()) {
            if (quoteList == null) {
                quoteList = new ConcurrentSkipListMap<Long, StQuote>();
            }
            StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(key);
            if (searchQuoteDTO.getQuoteType()==ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                quoteList.putAll(tradeQueueDTO.buyList);
            } else if(searchQuoteDTO.getQuoteType()==ApplicationConstants.STOCK_QUOTETYPE_SELL) {
                quoteList.putAll(tradeQueueDTO.sellList);
            }
            tradeQueueDTO = null;
            if (quoteList.size()>20) {
                break;
            }
        }

        return quoteList;
    }

    @Override
    public StQuote findFirstQuoteByStock(SearchQuoteDTO searchQuoteDTO) {
        if (BusinessConstants.stTradeQueueMap.isEmpty()) {
            this.findStQuoteToCache(1000);
        }
        StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(searchQuoteDTO.getStockCode());
        if (tradeQueueDTO==null || tradeQueueDTO.buyList.size()==0 || tradeQueueDTO.sellList.size()==0) {
            return null;
        }
        // 获取队列中的第一条买/卖报价
        StQuote quote = tradeQueueDTO.getFristStQuoteFromQueue(searchQuoteDTO.getQuoteType());
        tradeQueueDTO = null;
        return quote;
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
        if (BusinessConstants.stTradeQueueMap.isEmpty()) {
            this.findStQuoteToCache(1000);
        }
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
            tradeQueueDTO = null;
            // TODO 放入缓存
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
        String stockCode = stStockConfigService.getStockCodeByStockId(quote.getStockId());
        if (StringUtils.isEmpty(stockCode)) {
            return false;
        }
        // 从队列中删除报价，同时修改报价状态
        StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(stockCode);
        if (tradeQueueDTO==null) {
            return false;
        }
        boolean isRemove = false;
        if (quote.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            isRemove = tradeQueueDTO.removeBuyStQuote(quote);
        } else {
            isRemove = tradeQueueDTO.removeSellStQuote(quote);
        }
        if (!isRemove) return false;
        BusinessConstants.stTradeQueueMap.put(stockCode, tradeQueueDTO);
        tradeQueueDTO = null;
        // TODO 放入缓存
        iCacheService.setObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, BusinessConstants.simulateQuoteMap);

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

    @Override
    public void executeSimulationQuote() {
        if (BusinessConstants.simulateQuoteMap.isEmpty()) {
            Object obj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, null);
            if (obj != null) {
                BusinessConstants.simulateQuoteMap = (ConcurrentHashMap<String, List<SimulationQuoteDTO>>) obj;
            }
        }
//        long simulationCount = 0;
        List<StQuote> newStQuoteList = new ArrayList<StQuote>();
        BlockingQueue<Runnable> quoteQueue = new LinkedBlockingQueue<Runnable>();
        // TODO 用于发消息的线程池
        ThreadPoolExecutor quoteService = new ThreadPoolExecutor(5, 50, 1, TimeUnit.MINUTES, quoteQueue);
        for (String stockCode : BusinessConstants.simulateQuoteMap.keySet()) {
            List<SimulationQuoteDTO> simulationQuoteDTOList = BusinessConstants.simulateQuoteMap.get(stockCode);
            if (!StringUtils.isEmpty(simulationQuoteDTOList)) {
                List<StQuote> stQuoteList = new ArrayList<StQuote>();
                for (SimulationQuoteDTO simulationQuoteDTO : simulationQuoteDTOList) {
                    StQuote quote = new StQuote();
                    quote.setQuoteId(UUIDUtils.uuidTrimLine());
                    quote.setAccountId("800891cdc704462ab0c2335460a91684");
                    String stockId = stStockConfigService.getStockIdByStockCode(stockCode);
                    quote.setStockId(stockId);
                    quote.setStockCode(stockCode);
                    quote.setUserType(ApplicationConstants.ACCOUNT_TYPE_VIRTUAL); // 马甲用户
                    quote.setQuoteType(simulationQuoteDTO.getQuoteType());
                    quote.setAmount(simulationQuoteDTO.getAmount());
                    quote.setQuotePrice(ArithUtil.scale(BigDecimal.valueOf(simulationQuoteDTO.getQuotePrice())));
                    quote.setQuoteId(UUIDUtils.uuidTrimLine());
                    quote.setDateTime(simulationQuoteDTO.getDateTime());
                    // 用于排序的字段
//                    long timeSort = Integer.parseInt(String.valueOf(simulationQuoteDTO.getDateTime()).substring(7));
//                    quote.setTimeSort(timeSort);

                    quote.setCurrentAmount(quote.getAmount());
                    quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
                    stQuoteList.add(quote);
                    // TODO 将模拟报价放入消息队列，异步入库
//                    iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(quote));
                    quoteService.execute(new GenerateSimulationQuoteThread(iMessageQueue, quote));
                }
                newStQuoteList.addAll(stQuoteList);
                stQuoteList.clear();
                try {
                    if (newStQuoteList!=null&&newStQuoteList.size()>=100) {
//                        simulationCount += newStQuoteList.size();
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
//                simulationCount += newStQuoteList.size();
                this.saveQuoteList(newStQuoteList, ApplicationConstants.ACCOUNT_TYPE_VIRTUAL);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("simulationCount:" + BusinessConstants.simulateQuoteMap.size());
        BusinessConstants.simulateQuoteMap.clear();
        quoteService.shutdown();

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

    public boolean findStQuoteToCache(int limit) {
        if (BusinessConstants.isInitQuoteSuccess) {
            return true;
        }
        SearchQuoteDTO searchQuoteDTO = new SearchQuoteDTO();
        searchQuoteDTO.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
        searchQuoteDTO.setOffset(0);
        searchQuoteDTO.setLimit(Integer.MAX_VALUE);
        List<StQuote> stQuoteList = this.findQuoteList(searchQuoteDTO);
        if (!StringUtils.isEmpty(stQuoteList)) {
            for (StQuote quote : stQuoteList) {
                String stockCode = stStockConfigService.getStockCodeByStockId(quote.getStockId());
                StTradeQueueDTO tradeQueueDTO = BusinessConstants.stTradeQueueMap.get(stockCode);
                if (tradeQueueDTO==null) {
                    tradeQueueDTO = new StTradeQueueDTO();
                }
                if (quote.getQuoteType().intValue()==ApplicationConstants.STOCK_QUOTETYPE_BUY.intValue()) {
                    tradeQueueDTO.addBuyStQuote(quote);
                } else if (quote.getQuoteType().intValue()==ApplicationConstants.STOCK_QUOTETYPE_SELL.intValue()) {
                    tradeQueueDTO.addSellStQuote(quote);
                }
                BusinessConstants.stTradeQueueMap.put(stockCode, tradeQueueDTO);
                tradeQueueDTO = null;
            }
        }

        return true;
    }

    /**
     * 是否在范围内报价
     * @param stStock
     * @param quotePrice
     * @return
     */
    private boolean isStockQuotePriceInScope(StStock stStock, BigDecimal quotePrice){

        //报价大于等于最小价格，小于等于最大价格，可以正常报价
        if((ArithUtil.compare(quotePrice, BigDecimal.valueOf(stStock.getMinPrice()))>=0) && (ArithUtil.compare(quotePrice, BigDecimal.valueOf(stStock.getMaxPrice()))<=0)){
            return true;
        }

        return false;
    }
}