package com.ryd.business.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 马甲订单
 * Created by chenji on 2016/4/28.
 */
public class SimulationQuoteDTO implements Serializable {
    private String stockId;
    //申报价格
    private double quotePrice;
    //申报股票数量
    private Long amount;
    //报价类型 1。买入 2。卖出
    private Short quoteType;
    //报价用户类型 1.真实用户 2.马甲用户
    private Short userType;
    //报价时间
    private Long dateTime;

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public double getQuotePrice() {
        return quotePrice;
    }

    public void setQuotePrice(double quotePrice) {
        this.quotePrice = quotePrice;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Short getQuoteType() {
        return quoteType;
    }

    public void setQuoteType(Short quoteType) {
        this.quoteType = quoteType;
    }

    public Short getUserType() {
        return userType;
    }

    public void setUserType(Short userType) {
        this.userType = userType;
    }

    public Long getDateTime() {
        return dateTime;
    }

    public void setDateTime(Long dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public String toString() {
        return "SimulationQuoteDTO{" +
                "stockId='" + stockId + '\'' +
                ", quotePrice=" + quotePrice +
                ", amount=" + amount +
                ", quoteType=" + quoteType +
                ", userType=" + userType +
                ", dateTime=" + dateTime +
                '}';
    }
}
