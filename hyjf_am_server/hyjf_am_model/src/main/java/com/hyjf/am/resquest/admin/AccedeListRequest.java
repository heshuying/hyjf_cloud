/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.hjh.AccedeListCustomizeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author libin
 * @version AccedeListRequest.java, v0.1 2018年7月7日 下午3:15:12
 */
public class AccedeListRequest extends BasePage implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 计划编号（检索）
	 */
	private String debtPlanNidSrch;
	/**
	 * 加入订单号（检索）
	 */
	private String accedeOrderIdSrch;
	/**
	 * 用户名（检索）
	 */
	private String userNameSrch;
	/**
	 * 锁定期（检索）
	 */
	private String debtLockPeriodSrch;
	/**
	 * 推荐人用户名（检索）
	 */
	private String refereeNameSrch;
	/**
	 * 订单状态下拉框（检索）
	 */
	private String orderStatus;
	/**
	 * 平台下拉框（检索）
	 */
	private String platformSrch;
	/**
	 * 检索开始时间（画面推送开始时间）
	 */
	private String searchStartDate;
	/**
	 * 检索结束时间（画面推送结束时间）
	 */
	private String searchEndDate;
	/**
	 * 检索开始时间（计息开始时间）
	 */
	private String countInterestTimeStartDate;
	/**
	 * 检索结束时间（计息结束时间）
	 */
	private String countInterestTimeEndDate;
	/**
	 * 列表数据
	 */
	private List<AccedeListCustomizeVO> recordList;
	
	/**
	 * 匹配期查询
	 */
	private String matchDatesSrch;
	
	/**
	 * 投资笔数查询
	 */
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
	
	private int userId;
	
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

	public List<AccedeListCustomizeVO> getRecordList() {
		return recordList;
	}

	public void setRecordList(List<AccedeListCustomizeVO> recordList) {
		this.recordList = recordList;
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
}
