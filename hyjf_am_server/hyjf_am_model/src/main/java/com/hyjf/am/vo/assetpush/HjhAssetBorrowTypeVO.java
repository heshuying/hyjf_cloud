/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.assetpush;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version HjhAssetBorrowTypeVO, v0.1 2018/6/11 18:09
 */
public class HjhAssetBorrowTypeVO extends BaseVO implements Serializable {

    private Integer id;

    private String instCode;

    private Integer assetType;

    private Integer borrowCd;

    private Integer isOpen;

    private Integer autoAdd;

    private Integer autoRecord;

    private Integer autoBail;

    private Integer autoAudit;

    private Integer autoReview;

    private Integer autoSendMinutes;

    private Integer autoReviewMinutes;

    private String applicant;

    private String repayOrgName;

    private String remark;

    private Integer createUser;

    private Integer createTime;

    private Integer updateUser;

    private Integer updateTime;

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

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getBorrowCd() {
        return borrowCd;
    }

    public void setBorrowCd(Integer borrowCd) {
        this.borrowCd = borrowCd;
    }

    public Integer getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Integer isOpen) {
        this.isOpen = isOpen;
    }

    public Integer getAutoAdd() {
        return autoAdd;
    }

    public void setAutoAdd(Integer autoAdd) {
        this.autoAdd = autoAdd;
    }

    public Integer getAutoRecord() {
        return autoRecord;
    }

    public void setAutoRecord(Integer autoRecord) {
        this.autoRecord = autoRecord;
    }

    public Integer getAutoBail() {
        return autoBail;
    }

    public void setAutoBail(Integer autoBail) {
        this.autoBail = autoBail;
    }

    public Integer getAutoAudit() {
        return autoAudit;
    }

    public void setAutoAudit(Integer autoAudit) {
        this.autoAudit = autoAudit;
    }

    public Integer getAutoReview() {
        return autoReview;
    }

    public void setAutoReview(Integer autoReview) {
        this.autoReview = autoReview;
    }

    public Integer getAutoSendMinutes() {
        return autoSendMinutes;
    }

    public void setAutoSendMinutes(Integer autoSendMinutes) {
        this.autoSendMinutes = autoSendMinutes;
    }

    public Integer getAutoReviewMinutes() {
        return autoReviewMinutes;
    }

    public void setAutoReviewMinutes(Integer autoReviewMinutes) {
        this.autoReviewMinutes = autoReviewMinutes;
    }

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public String getRepayOrgName() {
        return repayOrgName;
    }

    public void setRepayOrgName(String repayOrgName) {
        this.repayOrgName = repayOrgName == null ? null : repayOrgName.trim();
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

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "HjhAssetBorrowTypeVO{" +
                "id=" + id +
                ", instCode='" + instCode + '\'' +
                ", assetType=" + assetType +
                ", borrowCd=" + borrowCd +
                ", isOpen=" + isOpen +
                ", autoAdd=" + autoAdd +
                ", autoRecord=" + autoRecord +
                ", autoBail=" + autoBail +
                ", autoAudit=" + autoAudit +
                ", autoReview=" + autoReview +
                ", autoSendMinutes=" + autoSendMinutes +
                ", autoReviewMinutes=" + autoReviewMinutes +
                ", applicant='" + applicant + '\'' +
                ", repayOrgName='" + repayOrgName + '\'' +
                ", remark='" + remark + '\'' +
                ", createUser=" + createUser +
                ", createTime=" + createTime +
                ", updateUser=" + updateUser +
                ", updateTime=" + updateTime +
                '}';
    }
}
