package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 交易引擎主线程-----用户匹配报价-更新股票信息、生成马甲订单
 * Created by chenji on 2016/4/26.
 */
public class TradingMainThread implements Runnable {
    private StStockService stStockService;
    private StQuoteService stQuoteService;
    private ExecutorService executorService;

    public TradingMainThread(ExecutorService tradingservice, StStockService stStockService, StQuoteService stQuoteService) {
        this.stStockService = stStockService;
        this.stQuoteService = stQuoteService;
        this.executorService = tradingservice;
    }

    @Override
    public void run() {
        while (!ApplicationConstants.isMainThreadStop) {
            System.out.println("TradingMainThread is Running!");
            List<String> stockIdList = stQuoteService.findQuoteStockIdList();
            for (String stockId: stockIdList) {
                // 分线程执行报价交易
                executorService.execute(new TradingSubThread(stockId, stQuoteService));


            }


        }
    }

    public static void main(String[] args) {
        ExecutorService tradingservice = Executors.newFixedThreadPool(10);

//        tradingservice.execute(new TradingMainThread());

//        for (int i =0;i < 100;i++) {
//            tradingservice.execute(new TradingSubThread());
//        }
    }
}
