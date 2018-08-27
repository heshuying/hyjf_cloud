package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.config.ParamNameVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * @author by xiehuili on 2018/7/30.
 */
@ApiModel(value="流程配置",description="流程配置")
public class AdminBorrowFlowRequest extends BasePage implements Serializable{

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = 32154339572253967L;

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

    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;
    /** 列表list */
//    private List<HjhAssetBorrowTypeCustomize> recordList;
    @ApiModelProperty(value = "资产来源 检索条件")
    private String instCodeSrch;
    @ApiModelProperty(value = "产品类型 检索条件")
    private String assetTypeSrch;
    @ApiModelProperty(value = "项目类型 ")
    private String borrowCdSrch;
    @ApiModelProperty(value = "状态 检索条件 ")
    private String statusSrch;
    @ApiModelProperty(value = "期限 检索条件 ")
    private String manChargeTimeSear;
    private List<ParamNameVO> paramNameVOS;
    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getAssetTypeSrch() {
        return assetTypeSrch;
    }

    public void setAssetTypeSrch(String assetTypeSrch) {
        this.assetTypeSrch = assetTypeSrch;
    }

    public String getBorrowCdSrch() {
        return borrowCdSrch;
    }

    public void setBorrowCdSrch(String borrowCdSrch) {
        this.borrowCdSrch = borrowCdSrch;
    }

    public String getStatusSrch() {
        return statusSrch;
    }

    public void setStatusSrch(String statusSrch) {
        this.statusSrch = statusSrch;
    }

    public String getManChargeTimeSear() {
        return manChargeTimeSear;
    }

    public void setManChargeTimeSear(String manChargeTimeSear) {
        this.manChargeTimeSear = manChargeTimeSear;
    }

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

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public List<ParamNameVO> getParamNameVOS() {
        return paramNameVOS;
    }

    public void setParamNameVOS(List<ParamNameVO> paramNameVOS) {
        this.paramNameVOS = paramNameVOS;
    }
}
