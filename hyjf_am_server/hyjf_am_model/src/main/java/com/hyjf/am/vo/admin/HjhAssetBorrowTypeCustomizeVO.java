package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author by xiehuili on 2018/8/2.
 */
public class HjhAssetBorrowTypeCustomizeVO extends BasePage implements Serializable{

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -6164613081621602935L;

    /** 项目名称 */
    private String projectName;

    /** 资产来源 */
    private String instName;

    /** 产品类型名称 */
    private String assetTypeName;

    /** 状态 */
    private String status;

    /** 资产来源 检索条件 */
    private String instCodeSrch;

    /** 产品类型 检索条件 */
    private String assetTypeSrch;

    /** 项目类型 */
    private String borrowCdSrch;

    /** 状态 检索条件 */
    private String statusSrch;

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

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

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

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
        this.instCode = instCode;
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
        this.applicant = applicant;
    }

    public String getRepayOrgName() {
        return repayOrgName;
    }

    public void setRepayOrgName(String repayOrgName) {
        this.repayOrgName = repayOrgName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    /**
     * projectName
     * @return the projectName
     */

    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * instName
     * @return the instName
     */

    public String getInstName() {
        return instName;
    }

    /**
     * @param instName the instName to set
     */

    public void setInstName(String instName) {
        this.instName = instName;
    }

    /**
     * assetTypeName
     * @return the assetTypeName
     */

    public String getAssetTypeName() {
        return assetTypeName;
    }

    /**
     * @param assetTypeName the assetTypeName to set
     */

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName;
    }

    /**
     * status
     * @return the status
     */

    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * instCodeSrch
     * @return the instCodeSrch
     */

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    /**
     * @param instCodeSrch the instCodeSrch to set
     */

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    /**
     * assetTypeSrch
     * @return the assetTypeSrch
     */

    public String getAssetTypeSrch() {
        return assetTypeSrch;
    }

    /**
     * @param assetTypeSrch the assetTypeSrch to set
     */

    public void setAssetTypeSrch(String assetTypeSrch) {
        this.assetTypeSrch = assetTypeSrch;
    }

    /**
     * borrowCdSrch
     * @return the borrowCdSrch
     */

    public String getBorrowCdSrch() {
        return borrowCdSrch;
    }

    /**
     * @param borrowCdSrch the borrowCdSrch to set
     */

    public void setBorrowCdSrch(String borrowCdSrch) {
        this.borrowCdSrch = borrowCdSrch;
    }

    /**
     * statusSrch
     * @return the statusSrch
     */

    public String getStatusSrch() {
        return statusSrch;
    }

    /**
     * @param statusSrch the statusSrch to set
     */

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    /**
     * limitStart
     * @return the limitStart
     */

    public int getLimitStart() {
        return limitStart;
    }

    /**
     * @param limitStart the limitStart to set
     */

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * limitEnd
     * @return the limitEnd
     */

    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd the limitEnd to set
     */

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
