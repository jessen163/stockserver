package com.ryd.server.stocktrader.controller;

import com.ryd.basecommon.util.UUIDUtils;
import com.ryd.business.model.StAccount;
import com.ryd.business.service.StAccountService;
import com.ryd.business.service.StStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * <p>标题:</p>
 * <p>描述:</p>
 * 包名：PACKAGE_NAME
 * 创建人：songby
 * 创建时间：2016/4/26 19:05
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private StAccountService stAccountService;

    @RequestMapping(value = "/test")
    public ModelAndView mallMainViewWeb() throws Exception {
//        StAccount account = stAccountService.findStAccount("1234","123");

        StAccount a = new StAccount();
        a.setId(UUIDUtils.uuidTrimLine());
        a.setAccountName("A");
        a.setAccountNum("1");
        a.setAccountLevel((short)1);
        a.setAccountType((short)1);
        a.setPassword("123");
        a.setCreatetime(System.currentTimeMillis());
        stAccountService.addStAccount(a);

        System.out.println("hello world！"+a.getAccountName());
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        return mv;
    }
}
