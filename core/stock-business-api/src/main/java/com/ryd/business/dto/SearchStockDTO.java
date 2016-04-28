package com.ryd.business.dto;

import java.io.Serializable;

/**
 * <p>标题:股票查询实体</p>
 * <p>描述:</p>
 * 包名：com.ryd.business.dto
 * 创建人：chenji
 * 创建时间：2016/4/24 10:11
 */
public class SearchStockDTO implements Serializable {

    private String stockId;
    // 板块
    private int boardType;
    // 账户
    // 开始时间
    // 结束时间
    // 等参数

    public int getBoardType() {
        return boardType;
    }

    public void setBoardType(int boardType) {
        this.boardType = boardType;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }
}
