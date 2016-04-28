package com.ryd.business.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>标题:资金流水</p>
 * <p>描述:资金流水</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StMoneyJournal implements Serializable {

    private static final long serialVersionUID = 3260202949142399174L;

    private String recordId;

    //对应报价ID
    private String quoteId;
    //对应帐户ID
    private String accountId;
    //对应股票ID
    private String stockId;
    //报价
    private BigDecimal quotePrice;
    //股票数量
    private Long amount;
    //交易类型 1.买入 2.卖出
    private Short dealType;
    //交易金额
    private BigDecimal dealMoney;
    //交易佣金
    private BigDecimal dealFee;
    //印花税
    private BigDecimal dealTax;
    //交易时间
    private Long dateTime;

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId == null ? null : recordId.trim();
    }

    public String getQuoteId() {
        return quoteId;
    }

    public void setQuoteId(String quoteId) {
        this.quoteId = quoteId == null ? null : quoteId.trim();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId == null ? null : accountId.trim();
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId == null ? null : stockId.trim();
    }

    public BigDecimal getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(BigDecimal quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Short getDealType() {
        return dealType;
    }

    public void setDealType(Short dealType) {
        this.dealType = dealType;
    }

    public BigDecimal getDealMoney() {
        return dealMoney;
    }

    public void setDealMoney(BigDecimal dealMoney) {
        this.dealMoney = dealMoney;
    }

    public BigDecimal getDealFee() {
        return dealFee;
    }

    public void setDealFee(BigDecimal dealFee) {
        this.dealFee = dealFee;
    }

    public BigDecimal getDealTax() {
        return dealTax;
    }

    public void setDealTax(BigDecimal dealTax) {
        this.dealTax = dealTax;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }
}