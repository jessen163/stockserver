package com.ryd.business.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题:资金流水查询实体</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.dto
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public class SearchMoneyJournalDTO implements Serializable {
    // 账户
    private String accountId;
    //查询股票
    private String stockId;
    //交易类型 1.买入 2.卖出
    private Short dealType;
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

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public Short getDealType() {
        return dealType;
    }

    public void setDealType(Short dealType) {
        this.dealType = dealType;
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

    @Override
    public String toString() {
        return "SearchMoneyJournalDTO{" +
                "accountId='" + accountId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", dealType=" + dealType +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
