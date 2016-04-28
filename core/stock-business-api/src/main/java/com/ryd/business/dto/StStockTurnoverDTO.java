package com.ryd.business.dto;

import java.io.Serializable;

/**
 * 股票成交量实体
 * Created by chenji on 2016/4/28.
 */
public class StStockTurnoverDTO implements Serializable {
    // 交易量
    private long tradeAmount;
    // 交易时间 格式 HH:mm:ss
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

    @Override
    public String toString() {
        return "StStockTurnoverDTO{" +
                "tradeAmount=" + tradeAmount +
                ", tradeTime='" + tradeTime + '\'' +
                '}';
    }
}
