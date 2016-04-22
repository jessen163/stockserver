package com.ryd.business.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>标题:结算记录</p>
 * <p>描述:结算记录</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StSettleRecord implements Serializable {

    private static final long serialVersionUID = -7265017214289937632L;

    private String settleRecordId;
    //结算帐户ID
    private String settleAccountId;
    //结算股票ID
    private String stockId;
    //报价
    private BigDecimal quotePrice;
    //股票数量
    private Long amount;
    //佣金
    private BigDecimal dealFee;
    //类型 1.买入 2.卖出
    private Short dealType;
    //结算时间
    private Long dateTime;

    public String getSettleRecordId() {
        return settleRecordId;
    }

    public void setSettleRecordId(String settleRecordId) {
        this.settleRecordId = settleRecordId == null ? null : settleRecordId.trim();
    }

    public String getSettleAccountId() {
        return settleAccountId;
    }

    public void setSettleAccountId(String settleAccountId) {
        this.settleAccountId = settleAccountId == null ? null : settleAccountId.trim();
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

    public BigDecimal getDealFee() {
        return dealFee;
    }

    public void setDealFee(BigDecimal dealFee) {
        this.dealFee = dealFee;
    }

    public Short getDealType() {
        return dealType;
    }

    public void setDealType(Short dealType) {
        this.dealType = dealType;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }
}