package com.ryd.server.stocktrader.quartz;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.business.service.StQuoteService;
import com.ryd.business.service.StStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 股票交易定时任务
 * 主要负责修改“更新股票价格任务”、“马甲盘任务”状态
 * Created by chenji on 2016/4/27.
 * TODO 待修改
 */
@Component
public class StockTraderTask {
    @Autowired
    private StStockService stStockService;

    @Autowired
    private StQuoteService stQuoteService;

    /**
     * 运行股票交易
     * 周一到周五 运行
     */
    @Scheduled(cron = "0 * * ? * MON-FRI")
    public void runStockTrader() {
        // TODO 每天早上8：30（提前一个小时）启动交易引擎
        // 每天下午4：00停止交易引擎
        // 每分钟停止交易业务，启动股票价格更新线程
        stStockService.updateRealTimeStockInfo();
        ApplicationConstants.isSubThreadWait = true;
        // 生成马甲订单
        stQuoteService.addSimulationQuote();
    }
}