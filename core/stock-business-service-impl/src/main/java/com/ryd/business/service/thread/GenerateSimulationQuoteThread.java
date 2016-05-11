package com.ryd.business.service.thread;

import com.ryd.basecommon.util.*;
import com.ryd.business.dto.SimulationQuoteDTO;
import com.ryd.business.model.StQuote;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.util.BusinessConstants;
import com.ryd.cache.service.ICacheService;
import com.ryd.messagequeue.service.IMessageQueue;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 发送模拟订单信息到Kafka
 * Created by chenji on 2016/4/28.
 */
public class GenerateSimulationQuoteThread implements Runnable {
    private IMessageQueue iMessageQueue;
    private StQuote stQuote;

    public GenerateSimulationQuoteThread(IMessageQueue iMessageQueue, StQuote stQuote) {
        this.iMessageQueue = iMessageQueue;
        this.stQuote = stQuote;
    }

    @Override
    public void run() {
        iMessageQueue.sendMessage(ApplicationConstants.PUSHMESSAGE_SIMULATIONQUOTE, FileUtils.objectToByte(stQuote));
    }
}
