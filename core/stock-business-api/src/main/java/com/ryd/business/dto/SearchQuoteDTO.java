package com.ryd.business.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题:报价查询实体</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.dto
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public class SearchQuoteDTO implements Serializable {
    // 股票ID
    private String stockCode;
    // 账户ID
    private String accountId;
    // 报价类型： 1、买 2、卖
    private int quoteType;
    // 报价时间
    private Date quoteStartDate;
    // 报价时间
    private Date quoteEndDate;
    // 托管状态
    private Short status;

    private int offset;

    private int limit;

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public int getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(int quoteType) {
        this.quoteType = quoteType;
    }

    public Date getQuoteStartDate() {
        return quoteStartDate;
    }

    public void setQuoteStartDate(Date quoteStartDate) {
        this.quoteStartDate = quoteStartDate;
    }

    public Date getQuoteEndDate() {
        return quoteEndDate;
    }

    public void setQuoteEndDate(Date quoteEndDate) {
        this.quoteEndDate = quoteEndDate;
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
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
        return "SearchQuoteDTO{" +
                "stockCode='" + stockCode + '\'' +
                ", accountId='" + accountId + '\'' +
                ", quoteType=" + quoteType +
                ", quoteStartDate=" + quoteStartDate +
                ", quoteEndDate=" + quoteEndDate +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
