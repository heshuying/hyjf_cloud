package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BorrowFinserCharge implements Serializable {
    private String chargeCd;

    private String chargeType;

    private Integer chargeTime;

    private String chargeTimeType;

    private String chargeRate;

    private Integer status;

    private String remark;

    private Integer projectType;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getChargeCd() {
        return chargeCd;
    }

    public void setChargeCd(String chargeCd) {
        this.chargeCd = chargeCd == null ? null : chargeCd.trim();
    }

    public String getChargeType() {
        return chargeType;
    }

    public void setChargeType(String chargeType) {
        this.chargeType = chargeType == null ? null : chargeType.trim();
    }

    public Integer getChargeTime() {
        return chargeTime;
    }

    public void setChargeTime(Integer chargeTime) {
        this.chargeTime = chargeTime;
    }

    public String getChargeTimeType() {
        return chargeTimeType;
    }

    public void setChargeTimeType(String chargeTimeType) {
        this.chargeTimeType = chargeTimeType == null ? null : chargeTimeType.trim();
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate == null ? null : chargeRate.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}