package com.ryd.business.service.impl;

import com.ryd.basecommon.util.*;
import com.ryd.business.dao.StQuoteDao;
import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.exception.QuoteBusinessException;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StStock;
import com.ryd.business.service.StAccountService;
import com.ryd.business.service.StPositionService;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.service.StDateScheduleService;
import com.ryd.system.service.StSystemParamService;
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
        return this.saveQuoteList(Arrays.asList(quote));
    }

    @Override
    public Integer updateWithDrawQuote(StQuote quote) {
        return this.updateQuoteList(Arrays.asList(quote));
    }

    @Override
    public Integer saveQuoteList(List<StQuote> quoteList) throws Exception{
        Integer quoteFlag = 0;
        // 验证报价参数
        // 验证报价状态（是否可以报价）
//        1、是否节假日getIsFestival 2、是否交易时间
        // 将报价保存到mysql数据库 失败后回滚
        // 将报价加入队列 失败后回滚

        // 将报价放入Kafka 失败后不回滚
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
            if (!isStockQuotePriceInScope(BigDecimal.valueOf(stock.getBfclosePrice()), quote.getQuotePrice())) {
                // 超出涨跌幅
                return -3;
            }
        }
        // 账户金额是否够用
        for (StQuote quote: quoteList) {
            boolean rs=false;
            quote.setQuoteId(UUIDUtils.uuidTrimLine());
            // 用于排序的字段
            long timeSort = Integer.parseInt(String.valueOf(System.currentTimeMillis()).substring(7));
            quote.setTimeSort(timeSort);

            quote.setCurrentAmount(quote.getAmount());
            quote.setStatus(ApplicationConstants.STOCK_STQUOTE_STATUS_TRUSTEE);
            quote.setUserType(quote.getUserType() == null ? ApplicationConstants.ACCOUNT_TYPE_REAL : quote.getUserType());
            quote.setDateTime(System.currentTimeMillis());
            //买股票
            if (quote.getQuoteType().shortValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY.shortValue()) {

                BigDecimal money = ArithUtil.multiply(quote.getQuotePrice(),new BigDecimal(quote.getAmount().toString()));
                //佣金比例
                String commissionPercent = stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_COMMINSSION_PERCENT);
                //计算佣金
                BigDecimal commissionFee = ArithUtil.multiply(money, new BigDecimal(commissionPercent));

                quote.setFrozeMoney(ArithUtil.add(money, commissionFee));
                quote.setCommissionFee(commissionFee);
                //买家减少资产
                rs = stAccountService.updateStAccountMoneyReduce(quote.getAccountId(), quote.getFrozeMoney());
                if(!rs){
                    //用户金额不足
                    return -4;
                }
            }else if (quote.getQuoteType().shortValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL.shortValue()){ //卖股票

                //委托卖股票，减少股票持仓数量
                rs = stPositionService.updatePositionReduce(quote.getAccountId(), quote.getStockId(), quote.getAmount());
                if(!rs){
                    //用户持仓不足
                    return -5;
                }
            }
            if(rs) {
                // 添加报价到队列，同时保存到数据库 失败后回滚
                boolean ars = addQuoteToQueue(quote);

                if(ars) {
                    quoteFlag = 1;
                }else{
                    quoteFlag = -9;
                }
            }
        }

        return quoteFlag;
    }

    @Override
    public Integer updateQuoteList(List<StQuote> quoteList) {
        // TODO 撤销报价 交易时间以外不允许撤单，涨跌停股票允许报价、交易 在涨停或跌停以前报价成功的股票允许撮合
        if (StringUtils.isEmpty(quoteList)) {
            ConcurrentSkipListMap<Long, StQuote> quoteQueueList = null;
            for (StQuote quote : quoteList) {
                Object quoteobj = null;
                if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                    quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
                } else if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
                    quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), null);
                }

                quoteQueueList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;

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
                        quoteQueueList.remove(quote);
                    }
                }else{
                    return -1;
                }

                if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                    iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), quoteQueueList, 8 * 60 * 60);
                } else if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
                    iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), quoteQueueList, 8 * 60 * 60);
                }
            }
        }
        return null;
    }

    @Override
    public Integer updateQuote(StQuote quote) {
        return stQuoteDao.update(quote);
    }

    @Override
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO) {
        StQuote stQuote = new StQuote();
        stQuote.setAccountId(searchQuoteDTO.getAccountId());

        Long startTime = searchQuoteDTO.getQuoteStartDate().getTime();
        Long endTime = searchQuoteDTO.getQuoteEndDate().getTime();
        List<StQuote> quoteList = stQuoteDao.getTList(stQuote, startTime, endTime, searchQuoteDTO.getLimit(), searchQuoteDTO.getOffset());
        return quoteList;
    }

    @Override
    public List<String> findQuoteStockIdList() {
        Object stockIdListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, null);
        if (stockIdListObj == null) {
            return null;
        }
        List<String> stockIdList = (List<String>) stockIdListObj;
        return stockIdList;
    }

    @Override
    public List<StQuote> findQuoteQueueByStock(SearchQuoteDTO searchQuoteDTO) {
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
        // 从缓存中获取
        ConcurrentSkipListMap<Long, StQuote> quoteList = null;

        Object quoteobj = null;
        if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, searchQuoteDTO.getStockCode(), null);
        } else if (searchQuoteDTO.getQuoteType() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, searchQuoteDTO.getStockCode(), null);
        }
        if (quoteobj==null) return null;
        quoteList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
        Map.Entry<Long, StQuote> sellMap = quoteList.higherEntry(Long.MIN_VALUE);
        return sellMap==null?null:sellMap.getValue();
    }

    @Override
    public boolean addQuoteToQueue(StQuote quote) {
        // 入库
        stQuoteDao.add(quote);

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
        }

        Object stockIdListObj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, null);
        if (stockIdListObj != null) {
            List<String> stockIdList = (List<String>) stockIdListObj;
            if (!stockIdList.contains(quote.getStockId())) {
                stockIdList.add(quote.getStockId());

                // 存入缓存
                iCacheService.setObjectByKey(CacheConstant.CACHEKEY_QUEUE_STOCKID_LIST, quote.getQuoteId());
            }
        }

        return true;
    }

    @Override
    public boolean deleteQuoteFromQueue(StQuote quote) {
        // 从队列中删除报价，同时修改报价状态
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
        }
        return true;
    }

    @Override
    public void addSimulationQuote() {
        // 1、获取最新一次的股票价格-买一、买二、卖一、卖二
        // 2、获取模拟马甲用户
        // 3、多线程生成马甲订单
        ExecutorService stockService = Executors.newFixedThreadPool(10);
        final CountDownLatch cdOrder = new CountDownLatch(1);//指挥官的命令，设置为1，指挥官一下达命令，则cutDown,变为0，战士们执行任务

        List<SimulationQuoteDTO> simulationQuoteDTOList = null;
        if (iCacheService.getObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, null)!=null) {
            simulationQuoteDTOList=(List<SimulationQuoteDTO>)iCacheService.getObjectByKey(CacheConstant.CACHEKEY_SIMULATIONQUOTELIST, null);
        }
        if (!StringUtils.isEmpty(simulationQuoteDTOList)) {
            List<StQuote> stQuoteList = new ArrayList<StQuote>();
            for (SimulationQuoteDTO simulationQuoteDTO : simulationQuoteDTOList) {
                StQuote quote = new StQuote();
                quote.setAccountId("800891cdc704462ab0c2335460a91684");
                quote.setStockId(simulationQuoteDTO.getStockId());
                quote.setUserType(Short.parseShort("2")); // 马甲用户
                quote.setQuoteType(simulationQuoteDTO.getQuoteType());
                quote.setAmount(simulationQuoteDTO.getAmount());
                stQuoteList.add(quote);
            }
            try {
                this.saveQuoteList(stQuoteList);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
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