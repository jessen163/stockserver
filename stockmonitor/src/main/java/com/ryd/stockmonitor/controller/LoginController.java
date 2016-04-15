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
    @RequestMapping("/index")
    String index(Model model) {
        return "index";
    }

    @RequestMapping("/stock_list")
    public String stockList() {
        return "stock_list";
    }

    @RequestMapping("/stock_detail/{stockId}")
    public String showStockDetail(@PathVariable String stockId) {
        return "stock_detail";
    }

    @RequestMapping("/echoIndex")
    public String echoIndex() {
        return "echo";
    }
    @RequestMapping("/reverseIndex")
    public String reverseIndex() {
        return "reverse";
    }
}