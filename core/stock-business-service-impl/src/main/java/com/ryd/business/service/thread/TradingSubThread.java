package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;

import java.util.concurrent.TimeUnit;

/**
 * 交易子线程---用于匹配单只股票的买卖报价
 * Created by chenji on 2016/4/26.
 */
public class TradingSubThread implements Runnable {
    @Override
    public void run() {
        while (!ApplicationConstants.isSubThreadStop) {
            try {
                // 等待
                while (ApplicationConstants.isSubThreadWait) {
                    TimeUnit.MILLISECONDS.sleep(200);
                    System.out.println("TradingSubThread is Waiting!");
                }
                System.out.println("TradingSubThread is Running!");
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
