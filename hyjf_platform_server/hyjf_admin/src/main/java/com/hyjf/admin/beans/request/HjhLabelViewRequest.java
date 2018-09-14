/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version HjhLabelViewRequest.java, v0.1 2018年7月13日 上午9:37:44
 */
public class HjhLabelViewRequest extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private String ids;
	
	private Integer engineId;
	
    public int limit;
    
	private int paginatorPage = 1;
	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}
	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}
    
    @ApiModelProperty(value = "标签编号")
    private String labelId; 
    
    @ApiModelProperty(value = "标签名称查询")
	private String labelNameSrch;
    
    @ApiModelProperty(value = "标签状态查询")
	private Integer labelStateSrch;
    
    @ApiModelProperty(value = "操作开始时间查询")
	private String createTimeStartSrch;
    
    @ApiModelProperty(value = "操作结束时间查询")
	private String createTimeEndSrch;
	
    @ApiModelProperty(value = "机构编号查询")
	private String instCodeSrch;
    
    @ApiModelProperty(value = "机构产品类型查询")
	private Integer assetTypeSrch;
    
    @ApiModelProperty(value = "项目类型查询")
	private Integer projectTypeSrch;
    
    @ApiModelProperty(value = "还款方式查询")
	private String borrowStyleSrch;
	
    @ApiModelProperty(value = "使用状态查询")
	private Integer engineIdSrch;
    
    @ApiModelProperty(value = "标的最小期限")
    private String labelTermStart;
    
    @ApiModelProperty(value = "标的最大期限")
    private String labelTermEnd;
    
    @ApiModelProperty(value = "标的实际利率最小范围")
    private String labelAprStart;
    
    @ApiModelProperty(value = "标的实际利率最大范围")
    private String labelAprEnd;
    
    @ApiModelProperty(value = "标的实际最小金额")
    private String labelPaymentAccountStart;
    
    @ApiModelProperty(value = "标的实际最大金额")
    private String labelPaymentAccountEnd;
    
    @ApiModelProperty(value = "推送时间开始")
    private String pushTimeStartString;
    
    @ApiModelProperty(value = "推送时间结束")
    private String pushTimeEndString;
    
    @ApiModelProperty(value = "剩余最小天数")
    private String remainingDaysStart;
    
    @ApiModelProperty(value = "剩余最大天数")
    private String remainingDaysEnd;
    
    @ApiModelProperty(value = "日/月")
    private String labelTermType;
    
    @ApiModelProperty(value = "还款方式")
    private String borrowStyle;
    
    @ApiModelProperty(value = "还款方式名称")
    private String borrowStyleName;
    
    @ApiModelProperty(value = "标签名称输入")
    private String labelName;
    
    @ApiModelProperty(value = "资产来源Code输入")
    private String instCode;
    
    @ApiModelProperty(value = "资产来源名称输入")
    private String instName;
    
    @ApiModelProperty(value = "产品类型Code输入")
    private String assetType;
    
    @ApiModelProperty(value = "产品类型名称输入")
    private String assetTypeName;
    
    @ApiModelProperty(value = "项目类型Code输入")
    private String projectType;
    
    @ApiModelProperty(value = "项目类型名称输入")
    private String projectTypeName;
    
    @ApiModelProperty(value = "标的是否发生债转输入")
    private String isCredit;
    
    @ApiModelProperty(value = "债转次数不超过输入")
    private String creditSumMax;
    
    @ApiModelProperty(value = "标的是否逾期输入")
    private String isLate;
    
    @ApiModelProperty(value = "标签状态输入")
    private String labelState;
    
	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public Integer getEngineId() {
		return engineId;
	}

	public void setEngineId(Integer engineId) {
		this.engineId = engineId;
	}

	public String getPushTimeStartString() {
		return pushTimeStartString;
	}

	public void setPushTimeStartString(String pushTimeStartString) {
		this.pushTimeStartString = pushTimeStartString;
	}

	public String getPushTimeEndString() {
		return pushTimeEndString;
	}

	public void setPushTimeEndString(String pushTimeEndString) {
		this.pushTimeEndString = pushTimeEndString;
	}

	public String getLabelNameSrch() {
		return labelNameSrch;
	}

	public void setLabelNameSrch(String labelNameSrch) {
		this.labelNameSrch = labelNameSrch;
	}

	public Integer getLabelStateSrch() {
		return labelStateSrch;
	}

	public void setLabelStateSrch(Integer labelStateSrch) {
		this.labelStateSrch = labelStateSrch;
	}

	public String getCreateTimeStartSrch() {
		return createTimeStartSrch;
	}

	public void setCreateTimeStartSrch(String createTimeStartSrch) {
		this.createTimeStartSrch = createTimeStartSrch;
	}

	public String getCreateTimeEndSrch() {
		return createTimeEndSrch;
	}

	public void setCreateTimeEndSrch(String createTimeEndSrch) {
		this.createTimeEndSrch = createTimeEndSrch;
	}

	public String getInstCodeSrch() {
		return instCodeSrch;
	}

	public void setInstCodeSrch(String instCodeSrch) {
		this.instCodeSrch = instCodeSrch;
	}

	public Integer getAssetTypeSrch() {
		return assetTypeSrch;
	}

	public void setAssetTypeSrch(Integer assetTypeSrch) {
		this.assetTypeSrch = assetTypeSrch;
	}

	public Integer getProjectTypeSrch() {
		return projectTypeSrch;
	}

	public void setProjectTypeSrch(Integer projectTypeSrch) {
		this.projectTypeSrch = projectTypeSrch;
	}

	public String getBorrowStyleSrch() {
		return borrowStyleSrch;
	}

	public void setBorrowStyleSrch(String borrowStyleSrch) {
		this.borrowStyleSrch = borrowStyleSrch;
	}

	public Integer getEngineIdSrch() {
		return engineIdSrch;
	}

	public void setEngineIdSrch(Integer engineIdSrch) {
		this.engineIdSrch = engineIdSrch;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelTermStart() {
		return labelTermStart;
	}

	public void setLabelTermStart(String labelTermStart) {
		this.labelTermStart = labelTermStart;
	}

	public String getLabelTermEnd() {
		return labelTermEnd;
	}

	public void setLabelTermEnd(String labelTermEnd) {
		this.labelTermEnd = labelTermEnd;
	}

	public String getLabelAprStart() {
		return labelAprStart;
	}

	public void setLabelAprStart(String labelAprStart) {
		this.labelAprStart = labelAprStart;
	}

	public String getLabelAprEnd() {
		return labelAprEnd;
	}

	public void setLabelAprEnd(String labelAprEnd) {
		this.labelAprEnd = labelAprEnd;
	}

	public String getLabelPaymentAccountStart() {
		return labelPaymentAccountStart;
	}

	public void setLabelPaymentAccountStart(String labelPaymentAccountStart) {
		this.labelPaymentAccountStart = labelPaymentAccountStart;
	}

	public String getLabelPaymentAccountEnd() {
		return labelPaymentAccountEnd;
	}

	public void setLabelPaymentAccountEnd(String labelPaymentAccountEnd) {
		this.labelPaymentAccountEnd = labelPaymentAccountEnd;
	}

	public String getRemainingDaysStart() {
		return remainingDaysStart;
	}

	public void setRemainingDaysStart(String remainingDaysStart) {
		this.remainingDaysStart = remainingDaysStart;
	}

	public String getRemainingDaysEnd() {
		return remainingDaysEnd;
	}

	public void setRemainingDaysEnd(String remainingDaysEnd) {
		this.remainingDaysEnd = remainingDaysEnd;
	}

	public String getLabelTermType() {
		return labelTermType;
	}

	public void setLabelTermType(String labelTermType) {
		this.labelTermType = labelTermType;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getBorrowStyleName() {
		return borrowStyleName;
	}

	public void setBorrowStyleName(String borrowStyleName) {
		this.borrowStyleName = borrowStyleName;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getInstCode() {
		return instCode;
	}

	public void setInstCode(String instCode) {
		this.instCode = instCode;
	}

	public String getInstName() {
		return instName;
	}

	public void setInstName(String instName) {
		this.instName = instName;
	}

	public String getAssetType() {
		return assetType;
	}

	public void setAssetType(String assetType) {
		this.assetType = assetType;
	}

	public String getAssetTypeName() {
		return assetTypeName;
	}

	public void setAssetTypeName(String assetTypeName) {
		this.assetTypeName = assetTypeName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getIsCredit() {
		return isCredit;
	}

	public void setIsCredit(String isCredit) {
		this.isCredit = isCredit;
	}

	public String getCreditSumMax() {
		return creditSumMax;
	}

	public void setCreditSumMax(String creditSumMax) {
		this.creditSumMax = creditSumMax;
	}

	public String getIsLate() {
		return isLate;
	}

	public void setIsLate(String isLate) {
		this.isLate = isLate;
	}

	public String getLabelState() {
		return labelState;
	}

	public void setLabelState(String labelState) {
		this.labelState = labelState;
	}
	
}
