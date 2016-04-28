package com.ryd.business.service.thread;

import java.util.concurrent.CountDownLatch;

/**
 * 生成模拟订单线程
 * Created by chenji on 2016/4/28.
 */
public class GenerateSimulationQuoteThread implements Runnable {
    CountDownLatch cdOrder;
    CountDownLatch cdAddQuote;

    public GenerateSimulationQuoteThread(CountDownLatch cdOrder, CountDownLatch cdAddQuote) {
        this.cdOrder = cdOrder;
        this.cdAddQuote = cdAddQuote;
    }

    @Override
    public void run() {
        //
    }
}
