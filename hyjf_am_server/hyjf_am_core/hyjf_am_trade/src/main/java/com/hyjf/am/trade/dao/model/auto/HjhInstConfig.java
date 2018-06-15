package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhInstConfig implements Serializable {
    private Integer id;

    private String instCode;

    private String instName;

    private Integer instType;

    private BigDecimal capitalToplimit;

    private BigDecimal commissionFee;

    private String remark;

    private Integer createUser;

    private Date createTime;

    private Integer updateUser;

    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName == null ? null : instName.trim();
    }

    public Integer getInstType() {
        return instType;
    }

    public void setInstType(Integer instType) {
        this.instType = instType;
    }

    public BigDecimal getCapitalToplimit() {
        return capitalToplimit;
    }

    public void setCapitalToplimit(BigDecimal capitalToplimit) {
        this.capitalToplimit = capitalToplimit;
    }

    public BigDecimal getCommissionFee() {
        return commissionFee;
    }

    public void setCommissionFee(BigDecimal commissionFee) {
        this.commissionFee = commissionFee;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
}