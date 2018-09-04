package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowFinmanNewCharge implements Serializable {
    private String manChargeCd;

    private String manChargeType;

    private Integer manChargeTime;

    private String manChargeTimeType;

    private String projectType;

    private String instCode;

    private Integer assetType;

    private String autoBorrowApr;

    private Integer chargeMode;

    private String chargeRate;

    private String manChargeRate;

    private String returnRate;

    private String lateInterest;

    private BigDecimal serviceFeeTotal;

    private Integer lateFreeDays;

    private Integer autoRepay;

    private Integer repayerType;

    private Integer status;

    private String remark;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public String getManChargeCd() {
        return manChargeCd;
    }

    public void setManChargeCd(String manChargeCd) {
        this.manChargeCd = manChargeCd == null ? null : manChargeCd.trim();
    }

    public String getManChargeType() {
        return manChargeType;
    }

    public void setManChargeType(String manChargeType) {
        this.manChargeType = manChargeType == null ? null : manChargeType.trim();
    }

    public Integer getManChargeTime() {
        return manChargeTime;
    }

    public void setManChargeTime(Integer manChargeTime) {
        this.manChargeTime = manChargeTime;
    }

    public String getManChargeTimeType() {
        return manChargeTimeType;
    }

    public void setManChargeTimeType(String manChargeTimeType) {
        this.manChargeTimeType = manChargeTimeType == null ? null : manChargeTimeType.trim();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType == null ? null : projectType.trim();
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getAutoBorrowApr() {
        return autoBorrowApr;
    }

    public void setAutoBorrowApr(String autoBorrowApr) {
        this.autoBorrowApr = autoBorrowApr == null ? null : autoBorrowApr.trim();
    }

    public Integer getChargeMode() {
        return chargeMode;
    }

    public void setChargeMode(Integer chargeMode) {
        this.chargeMode = chargeMode;
    }

    public String getChargeRate() {
        return chargeRate;
    }

    public void setChargeRate(String chargeRate) {
        this.chargeRate = chargeRate == null ? null : chargeRate.trim();
    }

    public String getManChargeRate() {
        return manChargeRate;
    }

    public void setManChargeRate(String manChargeRate) {
        this.manChargeRate = manChargeRate == null ? null : manChargeRate.trim();
    }

    public String getReturnRate() {
        return returnRate;
    }

    public void setReturnRate(String returnRate) {
        this.returnRate = returnRate == null ? null : returnRate.trim();
    }

    public String getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(String lateInterest) {
        this.lateInterest = lateInterest == null ? null : lateInterest.trim();
    }

    public BigDecimal getServiceFeeTotal() {
        return serviceFeeTotal;
    }

    public void setServiceFeeTotal(BigDecimal serviceFeeTotal) {
        this.serviceFeeTotal = serviceFeeTotal;
    }

    public Integer getLateFreeDays() {
        return lateFreeDays;
    }

    public void setLateFreeDays(Integer lateFreeDays) {
        this.lateFreeDays = lateFreeDays;
    }

    public Integer getAutoRepay() {
        return autoRepay;
    }

    public void setAutoRepay(Integer autoRepay) {
        this.autoRepay = autoRepay;
    }

    public Integer getRepayerType() {
        return repayerType;
    }

    public void setRepayerType(Integer repayerType) {
        this.repayerType = repayerType;
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