package com.ryd.business.model;

import com.ryd.basecommon.util.ApplicationConstants;

import java.io.Serializable;

/**
 * <p>标题:股票配置表</p>
 * <p>描述:股票配置表</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StStockConfig implements Serializable {
    private String id;
    //股票名称
    private String stockName;
    //股票代码
    private String stockCode;
    //股票类型 1.上海 2。深圳
    private Short stockType;
    //股票状态 1.正常 2.禁用
    private Short status;
    //板块类型 1.中小 2.创业
    private Short boardType;
    //备注
    private String remark;

    private String createdBy;

    private Long createdOn;

    private String updatedBy;

    private Long updatedOn;

    //股票类型 1.上海 2。深圳
    private String stockTypeName;

    public String getStockTypeName() {
        return this.getStockType() == 1? "sh":"sz";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName == null ? null : stockName.trim();
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode == null ? null : stockCode.trim();
    }

    public Short getStockType() {
        return stockType;
    }

    public void setStockType(Short stockType) {
        this.stockType = stockType;
    }

    public Short getBoardType() {
        return boardType;
    }

    public void setBoardType(Short boardType) {
        this.boardType = boardType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy == null ? null : createdBy.trim();
    }

    public Long getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy == null ? null : updatedBy.trim();
    }

    public Long getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Long updatedOn) {
        this.updatedOn = updatedOn;
    }
}