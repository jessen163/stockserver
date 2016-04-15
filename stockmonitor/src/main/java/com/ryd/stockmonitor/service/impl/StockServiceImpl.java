package com.ryd.stockmonitor.service.impl;

import com.ryd.demo.server.bean.StQuote;
import com.ryd.demo.server.bean.StStock;
import com.ryd.demo.server.bean.StTradeQueue;
import com.ryd.demo.server.bean.StTradeRecord;
import com.ryd.stockmonitor.service.StockService;

import java.util.List;

/**
 *
 * Created by Administrator on 2016/4/14.
 */
public class StockServiceImpl implements StockService {
    @Override
    public List<StStock> findStockList() {
        return null;
    }

    @Override
    public List<StQuote> findQuoteList() {
        return null;
    }

    @Override
    public List<StStock> findStockListByStockId(String stockId) {
        return null;
    }

    @Override
    public StTradeQueue findTradeQueueList(String stockId) {
        return null;
    }

    @Override
    public List<StTradeRecord> findTradeRecordList(String stockId) {
        return null;
    }
}
