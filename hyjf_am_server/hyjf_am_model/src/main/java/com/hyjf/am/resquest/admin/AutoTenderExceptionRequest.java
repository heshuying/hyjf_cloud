package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import java.io.Serializable;

public class AutoTenderExceptionRequest extends BasePage implements Serializable {

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
	 * 推荐人用户名（检索）
	 */
	private String refereeNameSrch;
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

	private Integer limitFlg = 1;

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

	public String getRefereeNameSrch() {
		return refereeNameSrch;
	}

	public void setRefereeNameSrch(String refereeNameSrch) {
		this.refereeNameSrch = refereeNameSrch;
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

	public Integer getLimitFlg() {
		return limitFlg;
	}

	public void setLimitFlg(Integer limitFlg) {
		this.limitFlg = limitFlg;
	}
}
