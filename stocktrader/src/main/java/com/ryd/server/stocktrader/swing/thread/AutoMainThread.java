package com.ryd.server.stocktrader.swing.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.model.StStockConfig;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import org.apache.log4j.Logger;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.stocktrader.swing.thread
 * 创建人：songby
 * 创建时间：2016/5/11 16:51
 */
public class AutoMainThread implements Runnable {

    private ThreadPoolExecutor stockService;

    public AutoMainThread() {
    }

    @Override
    public void run() {
        ClientConstants.treadint = 0;
        if (ClientConstants.isCanAutoQuote) {
            System.out.println("------自动化交易开始------------------");
            ClientConstants.isCanAutoQuote = false;
            ApplicationConstants.isAutoTradeMainThreadStop = false;
            BlockingQueue<Runnable> stockQueue = new LinkedBlockingQueue<Runnable>();
            stockService = new ThreadPoolExecutor(5, 10, 2, TimeUnit.MINUTES, stockQueue);
            for (StStockConfig stockConfig : ClientConstants.stStockConfigList) {
                // 自动化交易
                stockService.execute(new AutoTradeThread(stockService, stockConfig, stockQueue));
            }
        }
    }
}