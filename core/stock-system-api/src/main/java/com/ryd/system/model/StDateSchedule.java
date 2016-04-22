package com.ryd.system.model;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>标题:特殊时间配置-节假日</p>
 * <p>描述:特殊时间配置-节假日</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StDateSchedule implements Serializable {

    private static final long serialVersionUID = -8171316139284024723L;

    private String id;
    //日期
    private Date specialDate;
    //日期类型 1.节假日 2.特殊工作日
    private Short dateType;
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

    public Date getSpecialDate() {
        return specialDate;
    }

    public void setSpecialDate(Date specialDate) {
        this.specialDate = specialDate;
    }

    public Short getDateType() {
        return dateType;
    }

    public void setDateType(Short dateType) {
        this.dateType = dateType;
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