package com.ryd.business.dto;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.model.StQuote;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 单只股票的报价信息
 * 用于存储单只股票的买的报价队列信息和卖的报价信息
 * 2016-3-31
 */
public class StTradeQueueDTO implements Serializable {
    private static final long serialVersionUID = 1531524722654988278L;

    //买卖家报价
    public ConcurrentSkipListMap<Long, StQuote> sellList = new ConcurrentSkipListMap<Long, StQuote>();
    public ConcurrentSkipListMap<Long, StQuote> buyList = new ConcurrentSkipListMap<Long, StQuote>();

    private Map.Entry<Long, StQuote> sellMap = null;

    public boolean addSellStQuote(StQuote stQuote) {
        if (stQuote.getTimeSort()==null||stQuote.getTimeSort()==0L) {
            stQuote.setTimeSort(Long.parseLong(String.valueOf(stQuote.getDateTime()).substring(7)));
        }
        Long quotePriceForSort = Long.parseLong("100000000") * (long)(stQuote.getQuotePrice().doubleValue()*100) + stQuote.getTimeSort();
        stQuote.setQuotePriceForSort(quotePriceForSort);
        return sellList.put(quotePriceForSort, stQuote) != null;
//        return false;
    }

    public boolean addBuyStQuote(StQuote stQuote) {
        if (stQuote.getTimeSort()==null||stQuote.getTimeSort()==0L) {
            stQuote.setTimeSort(Long.parseLong(String.valueOf(stQuote.getDateTime()).substring(7)));
        }
        Long quotePriceForSort = -1 * Long.parseLong("100000000") * (long)(stQuote.getQuotePrice().doubleValue()*100) + stQuote.getTimeSort();
        stQuote.setQuotePriceForSort(quotePriceForSort);
        return buyList.put(quotePriceForSort, stQuote) != null;
//        return false;
    }

    public boolean removeSellStQuote(StQuote stQuote) {
        if (stQuote.getTimeSort()==null||stQuote.getTimeSort()==0L) {
            stQuote.setTimeSort(Long.parseLong(String.valueOf(stQuote.getDateTime()).substring(7)));
        }
        Long quotePriceForSort = Long.parseLong("100000000") * (long)(stQuote.getQuotePrice().doubleValue()*100) + stQuote.getTimeSort();
        return sellList.remove(quotePriceForSort) != null;
//        return false;
    }

    public boolean removeBuyStQuote(StQuote stQuote) {
        if (stQuote.getTimeSort()==null||stQuote.getTimeSort()==0L) {
            stQuote.setTimeSort(Long.parseLong(String.valueOf(stQuote.getDateTime()).substring(7)));
        }
        Long quotePriceForSort = -1 * Long.parseLong("100000000") * (long)(stQuote.getQuotePrice().doubleValue()*100) + stQuote.getTimeSort();
        return buyList.remove(quotePriceForSort) != null;
//        return false;
    }

    /**
     * 返回队列中的第一条报价
     * 按队列的顺序
     * @param type
     * @return
     */
    public StQuote getFristStQuoteFromQueue(int type) {
        long key = Long.MIN_VALUE;
        if (type == ApplicationConstants.STOCK_QUOTETYPE_BUY) {
            sellMap = buyList.higherEntry(key);
        } else if (type == ApplicationConstants.STOCK_QUOTETYPE_SELL) {
            sellMap = sellList.higherEntry(key);
        }
        return sellMap==null?null:sellMap.getValue();
    }

    @Override
    public String toString() {
        return "StTradeQueueDTO{" +
                "sellList=" + sellList +
                ", buyList=" + buyList +
                ", sellMap=" + sellMap +
                '}';
    }
}