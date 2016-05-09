package com.ryd.business.model;

import com.ryd.basecommon.util.DateUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>标题:帐户</p>
 * <p>描述:帐户</p>
 * 包名：com.ryd.basecommon.common
 * 创建人：songby
 * 创建时间：2016/4/21 10:29
 */
public class StAccount implements Serializable {

    private static final long serialVersionUID = 8302508422475928154L;

    private String id;
    //真实姓名
    private String realName;
    //用户名
    private String accountName;
    //帐号
    private String accountNum;
    //密码
    private String password;
    //总资产
    private BigDecimal totalAssets;
    //可用资产
    private BigDecimal useMoney;
    //帐户级别 1.普通 2.VIP
    private Short accountLevel;
    //帐户类型 1.真实账户 2.马甲帐户
    private Short accountType;
    //手机号
    private String mobile;
    //性别 0.女 1.男
    private Short sex;
    //备注
    private String remark;
    //创建时间
    private Long createtime;

    private String createTimeFormat;
    //修改时间
    private Long updatetime;
    //状态 1.正常 2.禁用
    private Short status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    public String getAccountNum() {
        return accountNum;
    }

    public void setAccountNum(String accountNum) {
        this.accountNum = accountNum == null ? null : accountNum.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getUseMoney() {
        return useMoney;
    }

    public void setUseMoney(BigDecimal useMoney) {
        this.useMoney = useMoney;
    }

    public Short getAccountLevel() {
        return accountLevel;
    }

    public void setAccountLevel(Short accountLevel) {
        this.accountLevel = accountLevel;
    }

    public Short getAccountType() {
        return accountType;
    }

    public void setAccountType(Short accountType) {
        this.accountType = accountType;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public Short getSex() {
        return sex;
    }

    public void setSex(Short sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Long getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Long createtime) {
        this.createtime = createtime;
        this.createTimeFormat = DateUtils.formatLongToStr(createtime, DateUtils.TIME_FORMAT);
    }

    public Long getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Long updatetime) {
        this.updatetime = updatetime;
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getCreateTimeFormat() {
        return createTimeFormat;
    }
}