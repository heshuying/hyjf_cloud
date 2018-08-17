/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version AssetListRequest.java, v0.1 2018年7月12日 下午2:56:39
 */
public class AssetListViewRequest extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "资产编号查询")
	private String assetIdSrch;
	
	@ApiModelProperty(value = "资产来源查询")
	private String instCodeSrch;
	
	@ApiModelProperty(value = "产品类型查询")
	private String assetTypeSrch;
	
	@ApiModelProperty(value = "标的编号查询")
	private String borrowNidSrch;
	
	@ApiModelProperty(value = "计划编号查询")
	private String planNidSrch;
	
	@ApiModelProperty(value = "用户名查询")
	private String userNameSrch;
	
	@ApiModelProperty(value = "标签名称查询")
	private String labelNameSrch;
	
	@ApiModelProperty(value = "开户状态查询")
	private String bankOpenAccountSrch;
	
	@ApiModelProperty(value = "审核状态查询")
	private String verifyStatusSrch;
	
	@ApiModelProperty(value = "项目状态查询")
	private String statusSrch;
	
	@ApiModelProperty(value = "推送时间(开始)查询")
	private String recieveTimeStartSrch;
	
	@ApiModelProperty(value = "推送时间(结束)查询")
	private String recieveTimeEndSrch;
	
	@ApiModelProperty(value = "限制条数(选传)查询")
	private int limit;

	@ApiModelProperty(value = "用户类型(选传)查询")
	private int userType;

	public int getUserType() {
		return userType;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public String getAssetIdSrch() {
		return assetIdSrch;
	}

	public void setAssetIdSrch(String assetIdSrch) {
		this.assetIdSrch = assetIdSrch;
	}

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

	public String getBorrowNidSrch() {
		return borrowNidSrch;
	}

	public void setBorrowNidSrch(String borrowNidSrch) {
		this.borrowNidSrch = borrowNidSrch;
	}

	public String getPlanNidSrch() {
		return planNidSrch;
	}

	public void setPlanNidSrch(String planNidSrch) {
		this.planNidSrch = planNidSrch;
	}

	public String getUserNameSrch() {
		return userNameSrch;
	}

	public void setUserNameSrch(String userNameSrch) {
		this.userNameSrch = userNameSrch;
	}

	public String getLabelNameSrch() {
		return labelNameSrch;
	}

	public void setLabelNameSrch(String labelNameSrch) {
		this.labelNameSrch = labelNameSrch;
	}

	public String getBankOpenAccountSrch() {
		return bankOpenAccountSrch;
	}

	public void setBankOpenAccountSrch(String bankOpenAccountSrch) {
		this.bankOpenAccountSrch = bankOpenAccountSrch;
	}

	public String getVerifyStatusSrch() {
		return verifyStatusSrch;
	}

	public void setVerifyStatusSrch(String verifyStatusSrch) {
		this.verifyStatusSrch = verifyStatusSrch;
	}

	public String getStatusSrch() {
		return statusSrch;
	}

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	public String getRecieveTimeStartSrch() {
		return recieveTimeStartSrch;
	}

	public void setRecieveTimeStartSrch(String recieveTimeStartSrch) {
		this.recieveTimeStartSrch = recieveTimeStartSrch;
	}

	public String getRecieveTimeEndSrch() {
		return recieveTimeEndSrch;
	}

	public void setRecieveTimeEndSrch(String recieveTimeEndSrch) {
		this.recieveTimeEndSrch = recieveTimeEndSrch;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}
}
