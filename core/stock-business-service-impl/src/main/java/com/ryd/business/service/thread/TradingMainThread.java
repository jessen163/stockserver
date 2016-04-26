package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 交易引擎主线程-----用户匹配报价-更新股票信息、生成马甲订单
 * Created by chenji on 2016/4/26.
 */
public class TradingMainThread implements Runnable {
    public TradingMainThread() {
    }

    @Override
    public void run() {
        while (!ApplicationConstants.isMainThreadStop) {
            System.out.println("TradingMainThread is Running!");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ApplicationConstants.isSubThreadWait=true;
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ApplicationConstants.isSubThreadWait=false;
        }
    }

    public static void main(String[] args) {
        ExecutorService tradingservice = Executors.newFixedThreadPool(10);

        tradingservice.execute(new TradingMainThread());

        for (int i =0;i < 100;i++) {
            tradingservice.execute(new TradingSubThread());
        }
    }
}
