package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhAssetBorrowtype implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 资产类型
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 标的类型
     *
     * @mbggenerated
     */
    private Integer borrowCd;

    /**
     * 是否开启：0 未开启  1 已开启
     *
     * @mbggenerated
     */
    private Integer isOpen;

    /**
     * 自动录标: 0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoAdd;

    /**
     * 自动备案：0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoRecord;

    /**
     * 自动保证金：0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoBail;

    /**
     * 自动初审: 0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoAudit;

    /**
     * 自动复审: 0 否 1 是
     *
     * @mbggenerated
     */
    private Integer autoReview;

    /**
     * 自动发标时间间隔(分钟)
     *
     * @mbggenerated
     */
    private Integer autoSendMinutes;

    /**
     * 自动复审时间间隔(分钟)
     *
     * @mbggenerated
     */
    private Integer autoReviewMinutes;

    /**
     * 项目申请人
     *
     * @mbggenerated
     */
    private String applicant;

    /**
     * 垫付机构
     *
     * @mbggenerated
     */
    private String repayOrgName;

    /**
     * 说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 添加人
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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