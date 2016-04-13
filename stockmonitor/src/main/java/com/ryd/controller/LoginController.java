package com.ryd.controller;

import com.ryd.bean.StAccount;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Administrator on 2016/4/13.
 */
@Controller
@RequestMapping(value="/user")
public class LoginController {
    @RequestMapping("/{accountNumber}")
    @ResponseBody
    public StAccount view(@PathVariable("accountNumber") String accountNumber) {
        StAccount account = new StAccount();
        account.setAccountNumber(accountNumber);

        return account;
    }

    @RequestMapping("/index")
    public String index(Model map) {
        // ����һ�����ԣ�������ģ���ж�ȡ
        map.addAttribute("host", "http://blog.didispace.com");
        // returnģ���ļ������ƣ���Ӧsrc/main/resources/templates/index.html
        return "index";
    }

    @RequestMapping(value="/demo2")
    public String demo2(Model map) {
//        // ����һ�����ԣ�������ģ���ж�ȡ
//        map.addAttribute("host", "http://blog.didispace.com");
        // returnģ���ļ������ƣ���Ӧsrc/main/resources/templates/index.html
        return "demo";
    }
}
