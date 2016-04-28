package com.ryd.business.dto;

import java.io.Serializable;

/**
 * 股票价格列表
 * Created by chenji on 2016/4/28.
 */
public class StockPriceDTO implements Serializable {
    // 交易价格
    private double tradePrice;
    // 交易时间 格式 HH:mm:ss
    private String tradeTime;

    public double getTradePrice() {
        return tradePrice;
    }

    public void setTradePrice(double tradePrice) {
        this.tradePrice = tradePrice;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    @Override
    public String toString() {
        return "StockPriceDTO{" +
                "tradePrice=" + tradePrice +
                ", tradeTime='" + tradeTime + '\'' +
                '}';
    }
}
