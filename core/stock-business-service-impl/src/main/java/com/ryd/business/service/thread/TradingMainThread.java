package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.StTradeRecordService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 交易引擎主线程-----用户匹配报价-更新股票信息、生成马甲订单
 * Created by chenji on 2016/4/26.
 */
public class TradingMainThread implements Runnable {
    private StStockService stStockService;
    private StQuoteService stQuoteService;
    private ExecutorService executorService;
    private StTradeRecordService stTradeRecordService;
    public TradingMainThread(ExecutorService tradingservice, StStockService stStockService, StQuoteService stQuoteService, StTradeRecordService stTradeRecordService) {
        this.stStockService = stStockService;
        this.stQuoteService = stQuoteService;
        this.executorService = tradingservice;
        this.stTradeRecordService = stTradeRecordService;
    }

    @Override
    public void run() {
        while (!ApplicationConstants.isMainThreadStop) {
//            System.out.println("TradingMainThread is Running!");
            try {
                List<String> stockIdList = stQuoteService.findQuoteStockIdList();
                if (!StringUtils.isEmpty(stockIdList)) {
                    for (String stockId: stockIdList) {
                        // 分线程执行报价交易
                        executorService.execute(new TradingSubThread(stockId, stQuoteService, stTradeRecordService));
                    }
                }
                // 等待
                while (ApplicationConstants.isSubThreadWait) {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("TradingSubThread is Waiting!");
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
