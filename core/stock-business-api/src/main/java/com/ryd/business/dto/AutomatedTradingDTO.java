package com.ryd.business.dto;

import java.io.Serializable;

/**
 *
 * Created by Administrator on 2016/5/3.
 */
public class AutomatedTradingDTO implements Serializable {
    private String accountId;

    private String stockId;

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
}
