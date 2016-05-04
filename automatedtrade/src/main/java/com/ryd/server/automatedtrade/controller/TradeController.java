package com.ryd.server.automatedtrade.controller;

import com.ryd.basecommon.util.ApplicationConstants;
import com.ryd.basecommon.util.CacheConstant;
import com.ryd.business.dto.AutomatedTradingDTO;
import com.ryd.business.service.AutomatedTradingService;
import com.ryd.server.automatedtrade.thread.AutoTradeThread;
import com.ryd.system.service.StSystemParamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 自动化交易
 * Created by Administrator on 2016/5/3.
 */
@Controller
@RequestMapping("/autotrade")
public class TradeController {
    @Autowired
    private AutomatedTradingService automatedTradingService;
    @Autowired
    private StSystemParamService stSystemParamService;

    public TradeController() {

    }

    /**
     * 交易主界面
     * @return
     */
    @RequestMapping("/index")
    public ModelAndView tradeIndex() {

        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");

        return mv;
    }

    /**
     * 提交报价
     * @param accountNum
     * @param stockCode
     * @return
     */
    @RequestMapping(value = "/ajax/saveTrading", method = RequestMethod.GET)
    @ResponseBody
    public String saveTrading(
            @RequestParam("accountNum") String accountNum,
            @RequestParam("stockCode") String stockCode
    ) {
        long interval = Long.parseLong(stSystemParamService.getParamByKey(CacheConstant.CACHEKEY_SYSTEM_CONFIG_AUTOTRADE_INTERVAL));
        if(ApplicationConstants.isAutoTradeMainThreadStop) {
            ApplicationConstants.isAutoTradeMainThreadStop = false;
            AutomatedTradingDTO automatedTradingDTO = new AutomatedTradingDTO();
            automatedTradingDTO.setAccountId(accountNum);
            automatedTradingDTO.setStockId(stockCode);

            ScheduledExecutorService autoService = Executors.newSingleThreadScheduledExecutor();
            autoService.scheduleAtFixedRate(new AutoTradeThread(autoService, automatedTradingService, automatedTradingDTO), 0, interval, TimeUnit.SECONDS);

            return "开始成功";
        }else{
            return "自动化交易已经开始";
        }
    }

    /**
     * 结束报价
     * @return
     */
    @RequestMapping(value = "/ajax/stopTrading", method = RequestMethod.GET)
    @ResponseBody
    public String stopTrading() {
        ApplicationConstants.isAutoTradeMainThreadStop = true;
        return "结束成功";
    }
}
