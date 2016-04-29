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

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
