package com.ryd.business.dto;

import java.io.Serializable;

/**
 * 股票成交量
 * Created by chenji on 2016/5/9.
 */
public class StockTradeAmountDTO implements Serializable {
    // 成交量
    private long tradeAmount;
    // 成交时间
    private String tradeTime;

    public long getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(long tradeAmount) {
        this.tradeAmount = tradeAmount;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }
}
