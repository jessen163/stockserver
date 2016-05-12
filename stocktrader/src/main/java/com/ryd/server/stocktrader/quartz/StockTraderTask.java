package com.ryd.server.stocktrader.quartz;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.service.*;
import com.ryd.business.service.util.BusinessConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 股票交易定时任务
 * 主要负责“启动交易引擎”、“更新股票实时价格”、“生成马甲盘”、“停止交易引擎”、“交易结算”
 *
 * Created by chenji on 2016/4/27.
 * TODO 待优化
 */
@Component
public class StockTraderTask {
    @Autowired
    private StStockService stStockService;
    @Autowired
    private StQuoteService stQuoteService;
    @Autowired
    private StTradeRecordService stTradeRecordService;
    @Autowired
    private StSettleRecordService stSettleRecordService;
    @Autowired
    private StPositionService stPositionService;

    /**
     * 运行股票交易
     * 周一到周五 运行
     * TODO 每天早上8：30（提前一个小时）启动交易引擎
     */
    @Scheduled(cron = "0 * 8-22 ? * *")
    public void runStockTraderEngine() {
        if (ApplicationConstants.isMainThreadStop||ApplicationConstants.isSubThreadStop) {
            BusinessConstants.isCanQuote = true;
            ApplicationConstants.isMainThreadStop = false;
            ApplicationConstants.isSubThreadStop = false;
            stTradeRecordService.updateStockTrading();
        }
    }

    /**
     * 更新股票信息、生成马甲报价
     * 9点-15点
     * TODO 每天早上9点-15点更新（提前一个小时）启动交易引擎 下方为测试配置
     *
     */
    @Scheduled(cron = "0 * 9-22 ? * *")
    public void runUpdateStockData() {
        if (ApplicationConstants.isMainThreadStop||ApplicationConstants.isSubThreadStop) {
            runStockTraderEngine();
        }
        long start = System.currentTimeMillis();
        // 每天下午4：00停止交易引擎
        // 每分钟停止交易业务，启动股票价格更新线程
        stStockService.executeRealTimeStockInfo();
        System.out.println((System.currentTimeMillis() - start) / 1000 + "秒");
        long start2 = System.currentTimeMillis();
        ApplicationConstants.isSubThreadWait = true;
//        // 生成马甲订单
        stQuoteService.executeSimulationQuote();
        System.out.println((System.currentTimeMillis() - start) / 1000+"秒-----------end");
        // 4、完成后模拟单后启动报价
        ApplicationConstants.isSubThreadWait = false;
        System.out.println((System.currentTimeMillis() - start2) / 1000+"秒-----------simulate");
    }

    /**
     * 停止交易引擎
     * TODO 每天15：30（推迟半小时）停止交易引擎 22点为测试时间
     */
    @Scheduled(cron = "0 0 22 ? * *")
    public void stopStockTrader() {
        BusinessConstants.isCanQuote = false;
        ApplicationConstants.isMainThreadStop = true;
        ApplicationConstants.isSubThreadStop = true;
    }

    /**
     * 运行股票结算
     * 周一到周五 运行
     * TODO 每天16：00（推迟一小时）启动结算 23:30为测试时间
     */
    @Scheduled(cron = "0 49 15 ? * *")
    public void runUpdateStockSettling() {
        System.out.println("结算开始..........start............");
        try {
            stSettleRecordService.updateStockSettling();
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("结算开始..........end............");
    }
}