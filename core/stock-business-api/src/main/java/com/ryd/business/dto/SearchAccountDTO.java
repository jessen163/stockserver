package com.ryd.business.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题:帐户查询实体</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.dto
 * 创建人：songby
 * 创建时间：2016/4/29 9:40
 */
public class SearchAccountDTO implements Serializable {
    // 账户
    private String accountId;
    //真实姓名
    private String realName;
    //用户名
    private String accountName;
    //帐号
    private String accountNum;
    //帐户级别 1.普通 2.VIP
    private Short accountLevel;
    //帐户类型 1.真实账户 2.马甲帐户
    private Short accountType;
    //状态 1.正常 2.禁用
    private Short status;
    // 开始时间
    private Date startDate;
    // 结束时间
    private Date endDate;
    // 等参数
    private int offset;

    private int limit;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum;
    }

    public Short getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Short accountLevel) {
        this.accountLevel = accountLevel;
    }

    public Short getAccountType() {
        return accountType;
    }

    public void setAccountType(Short accountType) {
        this.accountType = accountType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }
}
