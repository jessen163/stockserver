package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.business.dto.SearchQuoteDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StTradeRecordService;

import java.util.concurrent.TimeUnit;

/**
 * 交易子线程---用于匹配单只股票的买卖报价
 * Created by chenji on 2016/4/26.
 */
public class TradingSubThread implements Runnable {
    private String stockId;
    private StQuoteService stQuoteService;
    private StTradeRecordService stTradeRecordService;
    public TradingSubThread(String stockId, StQuoteService stQuoteService, StTradeRecordService stTradeRecordService) {
        this.stockId = stockId;
        this.stQuoteService = stQuoteService;
        this.stTradeRecordService = stTradeRecordService;
    }

    @Override
    public void run() {
        while (!ApplicationConstants.isSubThreadStop) {
            try {
                // 等待
                while (ApplicationConstants.isSubThreadWait) {
                    TimeUnit.MILLISECONDS.sleep(500);
                    System.out.println("TradingSubThread is Waiting!");
                }
//                System.out.println("TradingSubThread is Running!");
                SearchQuoteDTO searchQuoteDTO = new SearchQuoteDTO();
                searchQuoteDTO.setStockCode(stockId);
                searchQuoteDTO.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_BUY);
                StQuote buyQuote = stQuoteService.findFirstQuoteByStock(searchQuoteDTO);
                searchQuoteDTO.setQuoteType(ApplicationConstants.STOCK_QUOTETYPE_SELL);
                StQuote sellQuote = stQuoteService.findFirstQuoteByStock(searchQuoteDTO);
                if (buyQuote==null||sellQuote==null) break;
                //买家和卖家同为马甲不能交易
                if (buyQuote.getUserType()==ApplicationConstants.ACCOUNT_TYPE_VIRTUAL && sellQuote.getUserType()==ApplicationConstants.ACCOUNT_TYPE_VIRTUAL)break;

                // 如果撮合成功, 执行交易, 同时更新买入、卖出队列
                if (buyQuote.getQuotePrice().doubleValue() >= sellQuote.getQuotePrice().doubleValue() && !buyQuote.getAccountId().equals(sellQuote.getAccountId())) {
                    // 调用交易接口
                    stTradeRecordService.updateTradeSettling(buyQuote, sellQuote);
                }
//              TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
