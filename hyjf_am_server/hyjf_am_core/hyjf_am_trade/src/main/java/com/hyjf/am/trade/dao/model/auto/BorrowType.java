package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;

public class BorrowType implements Serializable {
    private Integer id;

    private String nid;

    private Integer status;

    private String name;

    private String title;

    private String description;

    private Integer accountMultiple;

    private Integer passwordStatus;

    private String amountType;

    private Integer amountFirst;

    private Integer amountEnd;

    private BigDecimal frostScaleVip;

    private Integer adminStatus;

    private BigDecimal aprFirst;

    private BigDecimal aprEnd;

    private Integer checkFirst;

    private Integer checkEnd;

    private String tenderAccountMin;

    private String tenderAccountMax;

    private Integer periodFirst;

    private Integer periodEnd;

    private Integer validateFirst;

    private Integer validateEnd;

    private Integer awardStatus;

    private BigDecimal awardScaleFirst;

    private BigDecimal awardScaleEnd;

    private BigDecimal awardAccountFirst;

    private BigDecimal awardAccountEnd;

    private Integer awardFalseStatus;

    private Integer verifyAutoStatus;

    private String verifyAutoRemark;

    private String styles;

    private BigDecimal frostScale;

    private Integer lateDays;

    private BigDecimal vipLateScale;

    private BigDecimal allLateScale;

    private Integer partStatus;

    private Integer systemBorrowFullStatus;

    private Integer systemBorrowRepayStatus;

    private Integer systemWebRepayStatus;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getAccountMultiple() {
        return accountMultiple;
    }

    public void setAccountMultiple(Integer accountMultiple) {
        this.accountMultiple = accountMultiple;
    }

    public Integer getPasswordStatus() {
        return passwordStatus;
    }

    public void setPasswordStatus(Integer passwordStatus) {
        this.passwordStatus = passwordStatus;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType == null ? null : amountType.trim();
    }

    public Integer getAmountFirst() {
        return amountFirst;
    }

    public void setAmountFirst(Integer amountFirst) {
        this.amountFirst = amountFirst;
    }

    public Integer getAmountEnd() {
        return amountEnd;
    }

    public void setAmountEnd(Integer amountEnd) {
        this.amountEnd = amountEnd;
    }

    public BigDecimal getFrostScaleVip() {
        return frostScaleVip;
    }

    public void setFrostScaleVip(BigDecimal frostScaleVip) {
        this.frostScaleVip = frostScaleVip;
    }

    public Integer getAdminStatus() {
        return adminStatus;
    }

    public void setAdminStatus(Integer adminStatus) {
        this.adminStatus = adminStatus;
    }

    public BigDecimal getAprFirst() {
        return aprFirst;
    }

    public void setAprFirst(BigDecimal aprFirst) {
        this.aprFirst = aprFirst;
    }

    public BigDecimal getAprEnd() {
        return aprEnd;
    }

    public void setAprEnd(BigDecimal aprEnd) {
        this.aprEnd = aprEnd;
    }

    public Integer getCheckFirst() {
        return checkFirst;
    }

    public void setCheckFirst(Integer checkFirst) {
        this.checkFirst = checkFirst;
    }

    public Integer getCheckEnd() {
        return checkEnd;
    }

    public void setCheckEnd(Integer checkEnd) {
        this.checkEnd = checkEnd;
    }

    public String getTenderAccountMin() {
        return tenderAccountMin;
    }

    public void setTenderAccountMin(String tenderAccountMin) {
        this.tenderAccountMin = tenderAccountMin == null ? null : tenderAccountMin.trim();
    }

    public String getTenderAccountMax() {
        return tenderAccountMax;
    }

    public void setTenderAccountMax(String tenderAccountMax) {
        this.tenderAccountMax = tenderAccountMax == null ? null : tenderAccountMax.trim();
    }

    public Integer getPeriodFirst() {
        return periodFirst;
    }

    public void setPeriodFirst(Integer periodFirst) {
        this.periodFirst = periodFirst;
    }

    public Integer getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(Integer periodEnd) {
        this.periodEnd = periodEnd;
    }

    public Integer getValidateFirst() {
        return validateFirst;
    }

    public void setValidateFirst(Integer validateFirst) {
        this.validateFirst = validateFirst;
    }

    public Integer getValidateEnd() {
        return validateEnd;
    }

    public void setValidateEnd(Integer validateEnd) {
        this.validateEnd = validateEnd;
    }

    public Integer getAwardStatus() {
        return awardStatus;
    }

    public void setAwardStatus(Integer awardStatus) {
        this.awardStatus = awardStatus;
    }

    public BigDecimal getAwardScaleFirst() {
        return awardScaleFirst;
    }

    public void setAwardScaleFirst(BigDecimal awardScaleFirst) {
        this.awardScaleFirst = awardScaleFirst;
    }

    public BigDecimal getAwardScaleEnd() {
        return awardScaleEnd;
    }

    public void setAwardScaleEnd(BigDecimal awardScaleEnd) {
        this.awardScaleEnd = awardScaleEnd;
    }

    public BigDecimal getAwardAccountFirst() {
        return awardAccountFirst;
    }

    public void setAwardAccountFirst(BigDecimal awardAccountFirst) {
        this.awardAccountFirst = awardAccountFirst;
    }

    public BigDecimal getAwardAccountEnd() {
        return awardAccountEnd;
    }

    public void setAwardAccountEnd(BigDecimal awardAccountEnd) {
        this.awardAccountEnd = awardAccountEnd;
    }

    public Integer getAwardFalseStatus() {
        return awardFalseStatus;
    }

    public void setAwardFalseStatus(Integer awardFalseStatus) {
        this.awardFalseStatus = awardFalseStatus;
    }

    public Integer getVerifyAutoStatus() {
        return verifyAutoStatus;
    }

    public void setVerifyAutoStatus(Integer verifyAutoStatus) {
        this.verifyAutoStatus = verifyAutoStatus;
    }

    public String getVerifyAutoRemark() {
        return verifyAutoRemark;
    }

    public void setVerifyAutoRemark(String verifyAutoRemark) {
        this.verifyAutoRemark = verifyAutoRemark == null ? null : verifyAutoRemark.trim();
    }

    public String getStyles() {
        return styles;
    }

    public void setStyles(String styles) {
        this.styles = styles == null ? null : styles.trim();
    }

    public BigDecimal getFrostScale() {
        return frostScale;
    }

    public void setFrostScale(BigDecimal frostScale) {
        this.frostScale = frostScale;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getVipLateScale() {
        return vipLateScale;
    }

    public void setVipLateScale(BigDecimal vipLateScale) {
        this.vipLateScale = vipLateScale;
    }

    public BigDecimal getAllLateScale() {
        return allLateScale;
    }

    public void setAllLateScale(BigDecimal allLateScale) {
        this.allLateScale = allLateScale;
    }

    public Integer getPartStatus() {
        return partStatus;
    }

    public void setPartStatus(Integer partStatus) {
        this.partStatus = partStatus;
    }

    public Integer getSystemBorrowFullStatus() {
        return systemBorrowFullStatus;
    }

    public void setSystemBorrowFullStatus(Integer systemBorrowFullStatus) {
        this.systemBorrowFullStatus = systemBorrowFullStatus;
    }

    public Integer getSystemBorrowRepayStatus() {
        return systemBorrowRepayStatus;
    }

    public void setSystemBorrowRepayStatus(Integer systemBorrowRepayStatus) {
        this.systemBorrowRepayStatus = systemBorrowRepayStatus;
    }

    public Integer getSystemWebRepayStatus() {
        return systemWebRepayStatus;
    }

    public void setSystemWebRepayStatus(Integer systemWebRepayStatus) {
        this.systemWebRepayStatus = systemWebRepayStatus;
    }
}