package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;

public class BorrowFinhxfmanCharge implements Serializable {
    private String manChargeCd;

    private String manChargeType;

    private String manChargePer;

    private String manChargePerEnd;

    private Integer chargeTime;

    private String chargeTimeType;

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

    public String getManChargePer() {
        return manChargePer;
    }

    public void setManChargePer(String manChargePer) {
        this.manChargePer = manChargePer == null ? null : manChargePer.trim();
    }

    public String getManChargePerEnd() {
        return manChargePerEnd;
    }

    public void setManChargePerEnd(String manChargePerEnd) {
        this.manChargePerEnd = manChargePerEnd == null ? null : manChargePerEnd.trim();
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
}