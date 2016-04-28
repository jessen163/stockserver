package com.ryd.business.dto;

import java.io.Serializable;

/**
 * <p>标题:仓位查询实体</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.dto
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public class SearchPositionDTO implements Serializable {
    // 账户
    private String accountId;
    //查询股票
    private String stockId;
    //状态
    private Short status;
    // 等参数
    private int offset;

    private int limit;

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

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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
        return "SearchPositionDTO{" +
                "accountId='" + accountId + '\'' +
                ", stockId='" + stockId + '\'' +
                ", status=" + status +
                ", offset=" + offset +
                ", limit=" + limit +
                '}';
    }
}
