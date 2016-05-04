package com.ryd.server.automatedtrade.controller;

import com.ryd.business.dto.AutomatedTradingDTO;
import com.ryd.business.service.AutomatedTradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 自动化交易
 * Created by Administrator on 2016/5/3.
 */
@Controller
public class TradeController {
    @Autowired
    private AutomatedTradingService automatedTradingService;

    public TradeController() {
        System.out.println("TradeController");
    }

    /**
     * 交易主界面
     * @return
     */
    @RequestMapping("/index1")
    public String tradeIndex() {
        return "index";
    }

    /**
     * 提交报价
     * @param accountId
     * @param stockId
     * @return
     */
    public String saveTrading(String accountId, String stockId) {
        AutomatedTradingDTO automatedTradingDTO = new AutomatedTradingDTO();
        automatedTradingDTO.setAccountId(accountId);
        automatedTradingService.addAutomatedTradingByStock(automatedTradingDTO);
        return null;
    }
}
