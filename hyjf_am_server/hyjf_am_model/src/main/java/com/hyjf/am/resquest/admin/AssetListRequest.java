package com.hyjf.am.resquest.admin;

import java.io.Serializable;

/**
 * @author libin
 * @version AssetListRequest, v0.1 2018/6/27 
 */
public class AssetListRequest implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 资产编号
	private String assetIdSrch;
	// 资产来源
	private String instCodeSrch;
	// 产品类型
	private String assetTypeSrch;
	// 项目编号
	private String borrowNidSrch;
	// 计划编号
	private String planNidSrch;
	// 用户名
	private String userNameSrch;
	//标的标签
	private String labelNameSrch;
	// 开户状态
	private String bankOpenAccountSrch;
	// 审核状态
	private String verifyStatusSrch;
	// 项目状态
	private String statusSrch;
	// 推送时间
	private String recieveTimeStartSrch;
	// 推送时间
	private String recieveTimeEndSrch;

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
