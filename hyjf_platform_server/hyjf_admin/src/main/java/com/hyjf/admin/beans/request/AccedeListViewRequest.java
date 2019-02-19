/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

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
	
	@ApiModelProperty(value = "出借笔数查询")
	private String investCountsSrch;

	@ApiModelProperty(value = "是否具有组织架构查看权限")
	private String isOrganizationView;

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

	@ApiModelProperty(value = "预计开始退出时间开始（检索）")
	private String endDateStartSrch;

	@ApiModelProperty(value = "预计开始退出时间结束（检索）")
	private String endDateEndSrch;

	@ApiModelProperty(value = "实际退出时间开始（检索）")
	private String acctualPaymentTimeStartSrch;

	@ApiModelProperty(value = "实际退出时间结束（检索）")
	private String acctualPaymentTimeEndSrch;

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

	public String getIsOrganizationView() {
		return isOrganizationView;
	}

	public void setIsOrganizationView(String isOrganizationView) {
		this.isOrganizationView = isOrganizationView;
	}

	public String getMatchDatesSrch() {
		return matchDatesSrch;
	}

	public void setMatchDatesSrch(String matchDatesSrch) {
		this.matchDatesSrch = matchDatesSrch;
	}

	public String getInvestCountsSrch() {
		return investCountsSrch;
	}

	public void setInvestCountsSrch(String investCountsSrch) {
		this.investCountsSrch = investCountsSrch;
	}

	public String getEndDateStartSrch() {
		return endDateStartSrch;
	}

	public void setEndDateStartSrch(String endDateStartSrch) {
		this.endDateStartSrch = endDateStartSrch;
	}

	public String getEndDateEndSrch() {
		return endDateEndSrch;
	}

	public void setEndDateEndSrch(String endDateEndSrch) {
		this.endDateEndSrch = endDateEndSrch;
	}

	public String getAcctualPaymentTimeStartSrch() {
		return acctualPaymentTimeStartSrch;
	}

	public void setAcctualPaymentTimeStartSrch(String acctualPaymentTimeStartSrch) {
		this.acctualPaymentTimeStartSrch = acctualPaymentTimeStartSrch;
	}

	public String getAcctualPaymentTimeEndSrch() {
		return acctualPaymentTimeEndSrch;
	}

	public void setAcctualPaymentTimeEndSrch(String acctualPaymentTimeEndSrch) {
		this.acctualPaymentTimeEndSrch = acctualPaymentTimeEndSrch;
	}
}
