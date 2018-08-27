/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author fuqiang
 * @version BorrowFinmanNewChargeVO, v0.1 2018/6/12 18:08
 */
public class BorrowFinmanNewChargeVO extends BaseVO implements Serializable {
    private String manChargeCd;

    private String manChargeType;

    private Integer manChargeTime;

    private String manChargeTimeType;

    private String projectType;
    /** 项目名称 */
    private String projectName;

    /** 资产来源 */
    private String instName;

    /** 产品类型名称 */
    private String assetTypeName;

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

    private Integer createTime;

    private Integer updateTime;

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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }
}
