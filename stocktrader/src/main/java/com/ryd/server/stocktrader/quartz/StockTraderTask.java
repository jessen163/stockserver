package com.ryd.server.stocktrader.quartz;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StSettleRecordService;
import com.ryd.business.service.StStockService;
import com.ryd.business.service.StTradeRecordService;
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

    /**
     * 运行股票交易
     * 周一到周五 运行
     * TODO 每天早上8：30（提前一个小时）启动交易引擎
     */
    @Scheduled(cron = "0 * 8-15 ? * MON-FRI")
    public void runStockTraderEngine() {
        if (ApplicationConstants.isMainThreadStop||ApplicationConstants.isSubThreadStop) {
            ApplicationConstants.isMainThreadStop = false;
            ApplicationConstants.isSubThreadStop = false;
            stTradeRecordService.updateStockTrading();
        }
    }

    /**
     * 更新股票信息、生成马甲报价
     * 9点-15点
     */
    @Scheduled(cron = "0 * 9-15 ? * MON-FRI")
    public void runUpdateStockData() {
        // 每天下午4：00停止交易引擎
        // 每分钟停止交易业务，启动股票价格更新线程
        stStockService.updateRealTimeStockInfo();
        ApplicationConstants.isSubThreadWait = true;
        // 生成马甲订单
        stQuoteService.addSimulationQuote();
        // 4、完成后模拟单后启动报价
        ApplicationConstants.isSubThreadWait = false;
    }

    /**
     * 停止交易引擎
     * TODO 每天15：30（推迟半小时）停止交易引擎 22点为测试时间
     */
    @Scheduled(cron = "0 0 22 ? * MON-FRI")
    public void stopStockTrader() {
        ApplicationConstants.isMainThreadStop = true;
        ApplicationConstants.isSubThreadStop = true;
    }

    /**
     * 运行股票结算
     * 周一到周五 运行
     * TODO 每天16：00（推迟一小时）启动结算 23:30为测试时间
     */
    @Scheduled(cron = "0 30 23 ? * MON-FRI")
    public void runUpdateStockSettling() {
        System.out.println("结算开始..........start............");
        stSettleRecordService.updateStockSettling();
        System.out.println("结算开始..........end............");
    }
}