package com.ryd.server.automatedtrade.controller;

import com.ryd.basecommon.util.DateUtils;
import com.ryd.business.dto.SearchAccountDTO;
import com.ryd.business.model.StAccount;
import com.ryd.business.service.StAccountService;
import com.ryd.server.automatedtrade.common.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>标题:帐户Controller</p>
 * <p>描述:帐户Controller</p>
 * 包名：com.ryd.server.automatedtrade.controller
 * 创建人：songby
 * 创建时间：2016/5/9 10:00
 */
@Controller
@RequestMapping("/account")
public class StAccountController {

    @Autowired
    private StAccountService stAccountService;

    /**
     * 帐户列表
     * @return
     */
    @RequestMapping("/accountList")
    public ModelAndView accountList() {

//        SearchAccountDTO searchAccountDTO = new SearchAccountDTO();
//        searchAccountDTO.setLimit(Constants.PAGE_LIMIT);
//        searchAccountDTO.setOffset(0);
//        List<StAccount> accountList = stAccountService.findStAccountList(searchAccountDTO);
//        int pageSize = 1;
//        int totalCount = stAccountService.getCount(searchAccountDTO);
//        if (totalCount > 0) {
//            double a = (double) totalCount / Constants.PAGE_LIMIT;
//            pageSize = (int) Math.ceil(a);
//        }
//
        ModelAndView mv = new ModelAndView();
//        mv.addObject("list", accountList);
//        mv.addObject("pageSize",pageSize == 0 ? 1 : pageSize);
//        mv.addObject("totalCount", totalCount);
//        mv.addObject("reanName", "");
//        mv.addObject("accountName", "");
//        mv.addObject("accountNum", "");
//        mv.addObject("accountLevel", "");
//        mv.addObject("accountType", "");
//        mv.addObject("status", "");
//        mv.addObject("startTime", "");
//        mv.addObject("endTime", "");
        mv.setViewName("account/account_list");

        return mv;
    }

    @RequestMapping(value = "ajax/searchAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> searchAccount(@RequestParam("realName") String realName,
                                               @RequestParam("accountName") String accountName,
                                               @RequestParam("accountNum") String accountNum,
                                               @RequestParam("accountLevel") Short accountLevel,
                                               @RequestParam("accountType") Short accountType,
                                               @RequestParam("status") Short status,
                                               @RequestParam("startTime") String startTime,
                                               @RequestParam("endTime") String endTime,
                                               @RequestParam("pageIndex") int pageIndex) {

        Map<String, Object> result = new HashMap<String, Object>();
        Long start = 0L;
        Long end = 0L;

        if (StringUtils.isNotBlank(startTime)) {
            Date sTime = DateUtils.formatStrToDate(startTime, "yyyy-MM-dd HH:mm:ss");
            start = sTime.getTime();
        }
        if (StringUtils.isNotBlank(endTime)) {
            Date dTime = DateUtils.formatStrToDate(endTime, "yyyy-MM-dd HH:mm:ss");
            end=dTime.getTime();
        }

        SearchAccountDTO searchAccountDTO = new SearchAccountDTO();
        searchAccountDTO.setRealName(realName);
        searchAccountDTO.setAccountName(accountName);
        searchAccountDTO.setAccountNum(accountNum);
        searchAccountDTO.setAccountLevel(accountLevel);
        searchAccountDTO.setAccountType(accountType);
        searchAccountDTO.setStatus(status);
        searchAccountDTO.setLimit(Constants.PAGE_LIMIT);
        int offset = (pageIndex - 1) * Constants.PAGE_LIMIT;
        searchAccountDTO.setOffset(offset);
        List<StAccount> accountList = stAccountService.findStAccountList(searchAccountDTO);
        int pageSize = 1;
        int totalCount = stAccountService.getCount(searchAccountDTO);
        if (totalCount > 0) {
            double a = (double) totalCount / Constants.PAGE_LIMIT;
            pageSize = (int) Math.ceil(a);
        }
        result.put("list", accountList);
        result.put("pageSize", pageSize == 0 ? 1 : pageSize);
        result.put("totalCount", totalCount);
        result.put("reanName", realName);
        result.put("accountName", accountName);
        result.put("accountNum", accountNum);
        result.put("accountLevel", accountLevel);
        result.put("accountType", accountType);
        result.put("status", status);
        result.put("startTime", startTime);
        result.put("endTime", endTime);
        return result;
    }

    /**
     * 进入修改界面
     * @param accountId
     * @return
     */
    @RequestMapping("/uptStAccountModel")
    public ModelAndView uptStAccountModel(@RequestParam("accountId") String accountId) {
        ModelAndView mv = new ModelAndView();
        StAccount stAccount = stAccountService.getStAccountById(accountId);
        mv.setViewName("account/add_upt_account_info");
        mv.addObject("account",stAccount);
        return mv;
    }

    @RequestMapping(value="/updateStAccount")
    public ModelAndView updateStAccount(@RequestParam("accountId") String accountId,
                                        @RequestParam("realName") String realName,
                                        @RequestParam("accountName") String accountName,
                                        @RequestParam("accountNum") String accountNum,
                                        @RequestParam("password") String password,
                                        @RequestParam("accountLevel") Short accountLevel,
                                        @RequestParam("accountType") Short accountType,
                                        @RequestParam("status") Short status,
                                        @RequestParam("totalAssets") double totalAssets,
                                        @RequestParam("useMoney") double useMoney,
                                        @RequestParam("sex") Short sex,
                                        @RequestParam("remark") String remark,
                                        @RequestParam("isUptType") Short isUptType
    ) {
        StAccount stAccount = new StAccount();
        stAccount.setId(accountId);
        stAccount.setRealName(realName);
        stAccount.setAccountName(accountName);
        stAccount.setAccountNum(accountNum);
        stAccount.setPassword(password);
        stAccount.setAccountLevel(accountLevel);
        stAccount.setAccountType(accountType);
        stAccount.setStatus(status);
        stAccount.setTotalAssets(BigDecimal.valueOf(totalAssets));
        stAccount.setUseMoney(BigDecimal.valueOf(useMoney));
        stAccount.setSex(sex);
        stAccount.setRemark(remark);
        if(isUptType == 1){
           stAccount.setPassword(password);
        }
        stAccountService.updateStAccount(stAccount);
        return new ModelAndView(new RedirectView("accountList.htm"));
    }
}
