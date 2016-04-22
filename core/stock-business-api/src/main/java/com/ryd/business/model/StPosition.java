package com.ryd.business.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>标题:持仓</p>
 * <p>描述:持仓</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StPosition  implements Serializable {

    private static final long serialVersionUID = 4511266955454600256L;

    private String positionId;
    //持仓帐户ID
    private String accountId;
    //股票ID
    private String stockId;
    //持有股票数量
    private Long amount;
    //可交易股票数量
    private Long marketAmount;
    //平均成本
    private BigDecimal avgPrice;
    //状态
    private Short status;

    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId == null ? null : positionId.trim();
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

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getMarketAmount() {
        return marketAmount;
    }

    public void setMarketAmount(Long marketAmount) {
        this.marketAmount = marketAmount;
    }

    public BigDecimal getAvgPrice() {
        return avgPrice;
    }

    public void setAvgPrice(BigDecimal avgPrice) {
        this.avgPrice = avgPrice;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }
}