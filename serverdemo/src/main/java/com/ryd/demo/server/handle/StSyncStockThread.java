package com.ryd.demo.server.handle;

import com.ryd.demo.server.bean.StStock;
import com.ryd.demo.server.common.DataConstant;
import com.ryd.demo.server.service.StockAnalysisServiceI;
import com.ryd.demo.server.service.StockGetInfoFromApiI;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 *
 * 同步股票实时价格信息
 * Created by Administrator on 2016/4/9.
 */
public class StSyncStockThread implements Runnable {

    private static Logger logger = Logger.getLogger(StSyncStockThread.class);

    private StockGetInfoFromApiI stockGetInfoFromApiI;

    private final StStock stock;

    public StSyncStockThread(StockGetInfoFromApiI stockGetInfoFromApiI, StStock stStock) {
        this.stockGetInfoFromApiI = stockGetInfoFromApiI;
        this.stock = stStock;
    }

    @Override
    public void run() {
//            logger.info("更新股票实时信息.............start...........");
            LinkedList<StStock> stStockList= DataConstant.stockPriceList.get(stock.getStockId());
            if (stStockList==null) {
                stStockList = new LinkedList<StStock>();
            }
            StStock newStock= stockGetInfoFromApiI.getStStockInfo(stock.getStockType(), stock.getStockCode());
            if (stStockList.size()==5) {
                stStockList.removeFirst();
            }
            stStockList.add(newStock);
            DataConstant.stockPriceList.put(newStock.getStockId(),stStockList);
//            logger.info(stStockList);
//            logger.info("更新股票实时信息.............end...........");
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
    }
}
