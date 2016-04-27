package com.ryd.business.service.impl;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.dao.StQuoteDao;
import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.service.StQuoteService;
import com.ryd.cache.service.ICacheService;
import com.ryd.system.service.StDateScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

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
    private ICacheService iCacheService;

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
        if (stDateScheduleService.getIsCanQuote()) {
            // 非交易时间
            return -2;
        }
        // 价格是否匹配涨跌幅
        for (StQuote quote: quoteList) {
//            if (不匹配) {
//                // 超出涨跌幅
//                return -3;
//            }
        }
        // 账户金额是否够用
        for (StQuote quote: quoteList) {
//            if (账户是否足够) {
                  // 账户余额不足
//                return -4;
//            }
            // 添加报价到队列，同时保存到数据库 失败后回滚
            this.addQuoteToQueue(quote);
        }

        return quoteList.size();
    }

    @Override
    public Integer updateQuoteList(List<StQuote> quoteList) {
        // TODO 撤销报价 交易时间以外不允许撤单，涨跌停股票允许报价、交易 在涨停或跌停以前报价成功的股票允许撮合
        if (StringUtils.isEmpty(quoteList)) {
            ConcurrentSkipListMap<Long, StQuote> quoteQueueList = null;
            for (StQuote quote : quoteList) {
                //

                Object quoteobj = null;
                if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
                    quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
                } else if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_SELL){
                    quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), null);
                }
                quoteQueueList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
                quoteQueueList.remove(quote);
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
    public List<StQuote> findQuoteList(SearchQuoteDTO searchQuoteDTO) {
        // TODO 待修改
        StQuote stQuote = new StQuote();
        stQuote.setAccountId(searchQuoteDTO.getAccountId());
        List<StQuote> quoteList = stQuoteDao.getTList(stQuote, 0, Integer.MAX_VALUE);
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
        quoteList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
//        return quoteList.higherEntry();
        return null;
    }

    @Override
    public boolean addQuoteToQueue(StQuote quote) {
        // 入库
        stQuoteDao.add(quote);

        Object quoteobj = null;
        ConcurrentSkipListMap<Long, StQuote> quoteList = null;
        if (quote.getQuoteType().intValue() == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), null);
            // TODO 冻结资金
        } else {
            quoteobj = iCacheService.getObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_SELLQUEUE, quote.getStockId(), null);
            // TODO 冻结股票
        }
        if (quoteobj == null) {
            return false;
        }

        quoteList = (ConcurrentSkipListMap<Long, StQuote>) quoteobj;
        // 入队列 TODO 待修改
        quoteList.put(quote.getDateTime() + quote.getQuotePrice().longValue(), quote);
        // 存入缓存
        iCacheService.setObjectByKey(CacheConstant.CACHEKEY_STOCK_QUOTE_BUYQUEUE, quote.getStockId(), quoteList, 8 * 60 * 60);

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
    public boolean deleteQuoteFromQueue(StQuote stQuote) {
        // 从队列中删除报价，同时修改报价状态 TODO 待实现
        return false;
    }
}