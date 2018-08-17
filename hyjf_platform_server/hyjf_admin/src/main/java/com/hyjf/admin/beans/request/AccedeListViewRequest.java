/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import java.io.Serializable;

import com.hyjf.admin.beans.BaseRequest;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author libin
 * @version AccedeListViewRequest.java, v0.1 2018年7月21日 上午10:03:51
 */
public class AccedeListViewRequest extends BaseRequest implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "计划编号检索")
	private String debtPlanNidSrch;

	@ApiModelProperty(value = "加入订单号检索")
	private String accedeOrderIdSrch;

	@ApiModelProperty(value = "用户名检索")
	private String userNameSrch;

	@ApiModelProperty(value = "锁定期检索")
	private String debtLockPeriodSrch;

	@ApiModelProperty(value = "推荐人用户名检索")
	private String refereeNameSrch;

	@ApiModelProperty(value = "订单状态下拉框检索")
	private String orderStatus;

	@ApiModelProperty(value = "平台下拉框检索")
	private String platformSrch;

	@ApiModelProperty(value = "检索开始时间（画面推送开始时间）")
	private String searchStartDate;

	@ApiModelProperty(value = "检索结束时间（画面推送结束时间）")
	private String searchEndDate;

	@ApiModelProperty(value = "检索开始时间（计息开始时间）")
	private String countInterestTimeStartDate;

	@ApiModelProperty(value = "检索结束时间（计息结束时间）")
	private String countInterestTimeEndDate;
	
	@ApiModelProperty(value = "匹配期查询")
	private String matchDatesSrch;
	
	@ApiModelProperty(value = "投资笔数查询")
	private String investCountsSrch;

	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;
	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;

	public int limit;
	
	@ApiModelProperty(value = "用户id")
	private int userId;
	
	@ApiModelProperty(value = "邮箱")
	private String email;

	public String getDebtPlanNidSrch() {
		return debtPlanNidSrch;
	}

	public void setDebtPlanNidSrch(String debtPlanNidSrch) {
		this.debtPlanNidSrch = debtPlanNidSrch;
	}

	public String getAccedeOrderIdSrch() {
		return accedeOrderIdSrch;
	}

	public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
		this.accedeOrderIdSrch = accedeOrderIdSrch;
	}

	public String getUserNameSrch() {
		return userNameSrch;
	}

	public void setUserNameSrch(String userNameSrch) {
		this.userNameSrch = userNameSrch;
	}

	public String getDebtLockPeriodSrch() {
		return debtLockPeriodSrch;
	}

	public void setDebtLockPeriodSrch(String debtLockPeriodSrch) {
		this.debtLockPeriodSrch = debtLockPeriodSrch;
	}

	public String getRefereeNameSrch() {
		return refereeNameSrch;
	}

	public void setRefereeNameSrch(String refereeNameSrch) {
		this.refereeNameSrch = refereeNameSrch;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPlatformSrch() {
		return platformSrch;
	}

	public void setPlatformSrch(String platformSrch) {
		this.platformSrch = platformSrch;
	}

	public String getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public String getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	public String getCountInterestTimeStartDate() {
		return countInterestTimeStartDate;
	}

	public void setCountInterestTimeStartDate(String countInterestTimeStartDate) {
		this.countInterestTimeStartDate = countInterestTimeStartDate;
	}

	public String getCountInterestTimeEndDate() {
		return countInterestTimeEndDate;
	}

	public void setCountInterestTimeEndDate(String countInterestTimeEndDate) {
		this.countInterestTimeEndDate = countInterestTimeEndDate;
	}

	public int getPaginatorPage() {
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
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

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
