package com.ryd.business.service.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.FileUtils;
import com.ryd.business.model.StStock;
import com.ryd.messagequeue.service.IMessageQueue;

import java.util.List;

/**
 * 发送股票信息到Kafka
 * Created by chenji on 2016/5/6.
 */
public class SyncStockMessageThread implements Runnable {
    private IMessageQueue iMessageQueue;
    private List<StStock> stockList;

    public SyncStockMessageThread(IMessageQueue iMessageQueue, List<StStock> stockList) {
        this.iMessageQueue = iMessageQueue;
        this.stockList = stockList;
    }

    @Override
    public void run() {
//        iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_STOCKPRICELIST, FileUtils.objectToByte(stockList));
    }
}
