package com.ryd.server.automatedtrade.thread;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.dto.AutomatedTradingDTO;
import com.ryd.business.service.AutomatedTradingService;
import org.apache.log4j.Logger;

import java.util.concurrent.ScheduledExecutorService;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.automatedtrade.thread
 * 创建人：songby
 * 创建时间：2016/5/4 13:32
 */
public class AutoTradeThread implements Runnable {

    private static Logger logger = Logger.getLogger(AutoTradeThread.class);

    private AutomatedTradingService automatedTradingService;

    private  AutomatedTradingDTO automatedTradingDTO;

    public AutoTradeThread(AutomatedTradingService automatedTradingService, AutomatedTradingDTO automatedTradingDTO){
        this.automatedTradingService = automatedTradingService;
        this.automatedTradingDTO = automatedTradingDTO;
    }

    @Override
    public void run() {
        if(!ApplicationConstants.isAutoTradeMainThreadStop) {
            logger.info("自动化交易.............start...........");
            automatedTradingService.addAutomatedTradingByStock(automatedTradingDTO);
        }
    }
}
