package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.StringUtils;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StTradeRecordService;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 交易引擎主线程-----用户匹配报价-更新股票信息、生成马甲订单
 * Created by chenji on 2016/4/26.
 */
public class TradingMainThread implements Runnable {
    private StQuoteService stQuoteService;
    private ExecutorService executorService;
    private StTradeRecordService stTradeRecordService;
    public TradingMainThread(ExecutorService tradingservice, StQuoteService stQuoteService, StTradeRecordService stTradeRecordService) {
        this.stQuoteService = stQuoteService;
        this.executorService = tradingservice;
        this.stTradeRecordService = stTradeRecordService;
    }

    @Override
    public void run() {
        while (!ApplicationConstants.isMainThreadStop) {
//            System.out.println("TradingMainThread is Running!");
            try {
                List<String> stockCodeList = stQuoteService.findQuoteStockIdList();
                if (!StringUtils.isEmpty(stockCodeList)) {
                    for (String stockCode: stockCodeList) {
                        // 分线程执行报价交易
                        executorService.execute(new TradingSubThread(stockCode, stQuoteService, stTradeRecordService));
                    }
                }
                // 等待
                while (ApplicationConstants.isSubThreadWait) {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    System.out.println("MainThread is Waiting!");
                }
                TimeUnit.SECONDS.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        executorService.shutdownNow();
    }
}
