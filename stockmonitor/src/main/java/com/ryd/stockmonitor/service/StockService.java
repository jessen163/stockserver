package com.ryd.stockmonitor.service;

import com.ryd.demo.server.bean.StQuote;
import com.ryd.demo.server.bean.StStock;
import com.ryd.demo.server.bean.StTradeQueue;
import com.ryd.demo.server.bean.StTradeRecord;

import java.util.List;

/**
 * Created by Administrator on 2016/4/14.
 */
public interface StockService {
    /**
     * 股票信息
     * @return
     */
    public List<StStock> findStockList();

    /**
     * 最新的报价信息
     * @return
     */
    public List<StQuote> findQuoteList();

    /**
     * 查询单只股票最近五次价格信息
     * @param stockId
     * @return
     */
    public List<StStock> findStockListByStockId(String stockId);

    /**
     * 查询单只股票最近报价信息-分买、卖队列
     * @param stockId
     * @return
     */
    public StTradeQueue findTradeQueueList(String stockId);

    /**
     * 查询单只股票最近十次交易信息
     * @param stockId
     * @return
     */
    public List<StTradeRecord> findTradeRecordList(String stockId);
}
