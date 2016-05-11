package com.ryd.server.stocktrader.swing.thread;

import com.ryd.basecommon.protocol.protobuf.DiyNettyMessage;
import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.ArithUtil;
import com.ryd.business.dto.AutomatedTradingDTO;
import com.ryd.business.dto.SearchStockDTO;
import com.ryd.business.model.StAccount;
import com.ryd.business.model.StQuote;
import com.ryd.business.model.StStock;
import com.ryd.business.model.StStockConfig;
import com.ryd.business.service.AutomatedTradingService;
import com.ryd.server.stocktrader.swing.common.ClientConstants;
import com.ryd.server.stocktrader.swing.frame.QuotePriceJDialog;
import com.ryd.server.stocktrader.swing.service.impl.MessageServiceImpl;
import com.ryd.server.stocktrader.utils.TestParamBuilderUtil;
import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：com.ryd.server.automatedtrade.thread
 * 创建人：songby
 * 创建时间：2016/5/4 13:32
 */
public class AutoTradeThread implements Runnable {

    private StStockConfig stockConfig;
    private BlockingQueue<Runnable> stockQueue;
    private ThreadPoolExecutor stockService;

    public AutoTradeThread(ThreadPoolExecutor stockService, StStockConfig stockConfig, BlockingQueue<Runnable> stockQueue){
        this.stockConfig = stockConfig;
        this.stockQueue = stockQueue;
        this.stockService = stockService;
    }

    @Override
    public void run() {
        if(!ApplicationConstants.isAutoTradeMainThreadStop) {
            addAutomatedTradingByStock(stockConfig);
            if(stockQueue.size()==0){
                ClientConstants.isCanAutoQuote = true;
            }
        }else{
            System.out.println("----------自动化交易停止---------");
            ClientConstants.isCanAutoQuote = false;
            stockService.shutdownNow();
        }
    }

    private  boolean addAutomatedTradingByStock(StStockConfig stockConfig) {
        ClientConstants.treadint++;
        StStock stStock = ClientConstants.stStockMap.get(stockConfig.getStockCode());
        if(stStock!=null && ArithUtil.compare(stStock.getCurrentPrice(), 0) > 0) {
            double minBuyPrice = ArithUtil.subtract(stStock.getCurrentPrice(), 0.01);
            double maxSellPrice = ArithUtil.add(stStock.getCurrentPrice(), 0.01);
            try {
                DiyNettyMessage.NettyMessage.Builder builderb = TestParamBuilderUtil.getQuote(stockConfig.getId(), ClientConstants.stAccount.getId(),
                        minBuyPrice, ApplicationConstants.STOCK_QUOTETYPE_BUY, 100);
                MessageServiceImpl.sendMessage(builderb.build());

//            DiyNettyMessage.NettyMessage.Builder builders = TestParamBuilderUtil.getQuote(stockConfig.getId(), ClientConstants.stAccount.getId(),
//                    maxSellPrice, ApplicationConstants.STOCK_QUOTETYPE_SELL, 100);
//            MessageServiceImpl.sendMessage(builders.build());
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
        return false;
    }
}
