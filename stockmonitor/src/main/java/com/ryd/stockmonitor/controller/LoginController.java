package com.ryd.stockmonitor.controller;

import com.ryd.demo.server.bean.StAccount;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 显示监控界面
 * Created by Administrator on 2016/4/13.
 */
@Controller
@RequestMapping("/user")
public class LoginController {
    /**
     * 首页
     * @param model
     * @return
     */
    @RequestMapping("/index")
    String index(Model model) {
        return "index";
    }

    /**
     * 股票列表
     * @return
     */
    @RequestMapping("/stock_list")
    public String stockList() {
        return "stock_list";
    }

    /**
     * 股票详情-买、卖队列及交易记录
     * @param stockId
     * @return
     */
    @RequestMapping("/stock_detail/{stockId}")
    public String showStockDetail(@PathVariable String stockId) {
        return "stock_detail";
    }

    /**
     * 股票价格记录
     * @param stockId
     * @return
     */
    @RequestMapping("/stockprice_detail/{stockId}")
    public String showStockPriceDetail(@PathVariable String stockId) {
        return "stockprice_detail";
    }
}