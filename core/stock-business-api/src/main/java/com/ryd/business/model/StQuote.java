package com.ryd.business.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>标题:报价</p>
 * <p>描述:报价</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StQuote  implements Serializable {

    private static final long serialVersionUID = 6982895777434115945L;

    private String quoteId;
    //报价帐户ID
    private String accountId;
    //股票ID
    private String stockId;
    //申报价格
    private BigDecimal quotePrice;
    //申报股票数量
    private Long amount;
    //当前股票数量
    private Long currentAmount;
    //报价类型 1。买入 2。卖出
    private String quoteType;
    //买入报价时-冻结资金
    private BigDecimal frozeMoney;
    //佣金
    private BigDecimal commissionFee;
    //状态 1.托管  2.交易中 3.已成交 4.撤单  5. 结算未成交
    private String status;
    //报价用户类型 1.真实用户 2.马甲用户
    private String userType;
    //报价时间
    private Long dateTime;

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

    public Long getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(Long currentAmount) {
        this.currentAmount = currentAmount;
    }

    public String getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(String quoteType) {
        this.quoteType = quoteType == null ? null : quoteType.trim();
    }

    public BigDecimal getFrozeMoney() {
        return frozeMoney;
    }

    public void setFrozeMoney(BigDecimal frozeMoney) {
        this.frozeMoney = frozeMoney;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }
}