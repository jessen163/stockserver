package com.ryd.system.model;

import java.io.Serializable;

/**
 * <p>标题:参数配置</p>
 * <p>描述:参数配置</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StSystemParam implements Serializable {

    private static final long serialVersionUID = -6699578164927031988L;

    private String id;
    //参数名称
    private String keyName;
    //参数值
    private String keyValue;
    //参数类型 1.系统参数
    private Short keyType;
    //状态 1.正常 2.禁用
    private Short status;

    private String createdBy;

    private Long createdOn;

    private String updatedBy;

    private Long updatedOn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName == null ? null : keyName.trim();
    }

    public String getKeyValue() {
        return keyValue;
    }

    public void setKeyValue(String keyValue) {
        this.keyValue = keyValue == null ? null : keyValue.trim();
    }

    public Short getKeyType() {
        return keyType;
    }

    public void setKeyType(Short keyType) {
        this.keyType = keyType;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
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