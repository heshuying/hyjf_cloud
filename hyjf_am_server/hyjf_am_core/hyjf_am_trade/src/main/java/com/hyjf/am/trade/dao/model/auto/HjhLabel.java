package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhLabel implements Serializable {
    private Integer id;

    private String labelName;

    private Integer labelTermStart;

    private Integer labelTermEnd;

    private String labelTermType;

    private BigDecimal labelAprStart;

    private BigDecimal labelAprEnd;

    private String borrowStyle;

    private String borrowStyleName;

    private BigDecimal labelPaymentAccountStart;

    private BigDecimal labelPaymentAccountEnd;

    private String instCode;

    private String instName;

    private Integer assetType;

    private String assetTypeName;

    private Integer projectType;

    private String projectTypeName;

    private Integer isCredit;

    private Integer isLate;

    private Integer creditSumMax;

    private Date pushTimeStart;

    private Date pushTimeEnd;

    private Integer remainingDaysStart;

    private Integer remainingDaysEnd;

    private Integer labelState;

    private Integer createUserId;

    private Date createTime;

    private Integer updateUserId;

    private Date updateTime;

    private Integer delFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getLabelTermStart() {
        return labelTermStart;
    }

    public void setLabelTermStart(Integer labelTermStart) {
        this.labelTermStart = labelTermStart;
    }

    public Integer getLabelTermEnd() {
        return labelTermEnd;
    }

    public void setLabelTermEnd(Integer labelTermEnd) {
        this.labelTermEnd = labelTermEnd;
    }

    public String getLabelTermType() {
        return labelTermType;
    }

    public void setLabelTermType(String labelTermType) {
        this.labelTermType = labelTermType == null ? null : labelTermType.trim();
    }

    public BigDecimal getLabelAprStart() {
        return labelAprStart;
    }

    public void setLabelAprStart(BigDecimal labelAprStart) {
        this.labelAprStart = labelAprStart;
    }

    public BigDecimal getLabelAprEnd() {
        return labelAprEnd;
    }

    public void setLabelAprEnd(BigDecimal labelAprEnd) {
        this.labelAprEnd = labelAprEnd;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName == null ? null : borrowStyleName.trim();
    }

    public BigDecimal getLabelPaymentAccountStart() {
        return labelPaymentAccountStart;
    }

    public void setLabelPaymentAccountStart(BigDecimal labelPaymentAccountStart) {
        this.labelPaymentAccountStart = labelPaymentAccountStart;
    }

    public BigDecimal getLabelPaymentAccountEnd() {
        return labelPaymentAccountEnd;
    }

    public void setLabelPaymentAccountEnd(BigDecimal labelPaymentAccountEnd) {
        this.labelPaymentAccountEnd = labelPaymentAccountEnd;
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

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName == null ? null : assetTypeName.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getProjectTypeName() {
        return projectTypeName;
    }

    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName == null ? null : projectTypeName.trim();
    }

    public Integer getIsCredit() {
        return isCredit;
    }

    public void setIsCredit(Integer isCredit) {
        this.isCredit = isCredit;
    }

    public Integer getIsLate() {
        return isLate;
    }

    public void setIsLate(Integer isLate) {
        this.isLate = isLate;
    }

    public Integer getCreditSumMax() {
        return creditSumMax;
    }

    public void setCreditSumMax(Integer creditSumMax) {
        this.creditSumMax = creditSumMax;
    }

    public Date getPushTimeStart() {
        return pushTimeStart;
    }

    public void setPushTimeStart(Date pushTimeStart) {
        this.pushTimeStart = pushTimeStart;
    }

    public Date getPushTimeEnd() {
        return pushTimeEnd;
    }

    public void setPushTimeEnd(Date pushTimeEnd) {
        this.pushTimeEnd = pushTimeEnd;
    }

    public Integer getRemainingDaysStart() {
        return remainingDaysStart;
    }

    public void setRemainingDaysStart(Integer remainingDaysStart) {
        this.remainingDaysStart = remainingDaysStart;
    }

    public Integer getRemainingDaysEnd() {
        return remainingDaysEnd;
    }

    public void setRemainingDaysEnd(Integer remainingDaysEnd) {
        this.remainingDaysEnd = remainingDaysEnd;
    }

    public Integer getLabelState() {
        return labelState;
    }

    public void setLabelState(Integer labelState) {
        this.labelState = labelState;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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