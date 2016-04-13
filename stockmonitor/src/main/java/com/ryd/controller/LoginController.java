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
        // 加入一个属性，用来在模板中读取
        map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "index";
    }

    @RequestMapping(value="/demo2")
    public String demo2(Model map) {
//        // 加入一个属性，用来在模板中读取
//        map.addAttribute("host", "http://blog.didispace.com");
        // return模板文件的名称，对应src/main/resources/templates/index.html
        return "demo";
    }
}
