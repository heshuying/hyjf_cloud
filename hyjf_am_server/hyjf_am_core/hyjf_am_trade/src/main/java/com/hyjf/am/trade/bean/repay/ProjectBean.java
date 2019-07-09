package com.hyjf.am.trade.bean.repay;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.List;

public class ProjectBean extends BaseVO implements Serializable {


	/**
	 * 此处为属性说明
	 */
	private static final long serialVersionUID = -2142812516666739413L;

	// 是否是汇添金项目
	private String tType;

	// 还款人id
	private String userId;

	//角色
	private String roleId;

	// 项目编号
	private String borrowNid;

	// 项目名称
	private String borrowName;

	// 项目还款方式
	private String borrowStyle;

	// 项目还款总额 加管理费
	private String repayTotal;

	// 项目还款本息和
	private String repayAccount;

	// 项目还款本金
	private String repayCapital;

	// 当前的利息
	private String repayInterest;

	//应还利息
	private String shouldInterest;

	// 当前管理费
	private String manageFee;

	// 当期的还款期数
	private String repayPeriod;

	// 当前还款状态(是否完成 0未还款1还款中)
	private String repayStatus;

	// 当前还款方式（0正常还款1提前还款2延期还款3逾期还款）
	private String advanceStatus;

	// 当前还款方式（1逾期还款0其他）
	private String lateStatus;

	// 提前天数
	private String chargeDays;

	private String advanceDays;

	// 提前还款利息
	private String chargeInterest;

	private String advanceInterest;

	// 延期天数
	private String delayDays;

	// 延期利息
	private String delayInterest;

	// 逾期天数
	private String lateDays;

	// 逾期罚息
	private String lateInterest;

	private String username;

	//本期应还笔数
	private String repayNum;

	// 看正在还款的是否 全部结清（1为全部结清的还款中，以外则全否）
	private String allRepay;

	// 看正在还款的是否  只能全部结清（1为 是，2为 全部逾期(只能按期还)，0为正常）
	private String onlyAllRepay;

	private boolean isAllRepay;

	/**
	 * 逾期期数列表
	 */
	private List<Integer> lateArray;

	/**
	 * 用户选择的逾期期数，为空或0，默认未选择单期或多期还逾期的方式
	 */
	private Integer latePeriod;

	/**
	 * 当前还款期数
	 * 全部还款：当前还款期数：第1至5期，全部结清
	 * 其他的：当前还款期数：第1至5期
	 */
	private String allPeriodStr;

	// 原始提前还款利息
	private String chargeOriginalInterest;

	// 提前还款罚息
	private String chargePenaltyInterest;

	// 标的期数
	private String borrowPeriod;

	public boolean isAllRepay() {
		return isAllRepay;
	}

	public void setAllRepay(boolean allRepay) {
		isAllRepay = allRepay;
	}

	/** 用户还款详情 */
	private List<ProjectRepayBean> userRepayList;

	public ProjectBean() {
		super();
	}

	public String getShouldInterest() {
		return shouldInterest;
	}

	public void setShouldInterest(String shouldInterest) {
		this.shouldInterest = shouldInterest;
	}

	public String gettType() {
		return tType;
	}

	public void settType(String tType) {
		this.tType = tType;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	public String getRepayAccount() {
		return repayAccount;
	}

	public void setRepayAccount(String repayAccount) {
		this.repayAccount = repayAccount;
	}

	public String getRepayNum() {
		return repayNum;
	}

	public void setRepayNum(String repayNum) {
		this.repayNum = repayNum;
	}

	public String getRepayCapital() {
		return repayCapital;
	}

	public void setRepayCapital(String repayCapital) {
		this.repayCapital = repayCapital;
	}

	public String getRepayInterest() {
		return repayInterest;
	}

	public void setRepayInterest(String repayInterest) {
		this.repayInterest = repayInterest;
	}

	public String getManageFee() {
		return manageFee;
	}

	public void setManageFee(String manageFee) {
		this.manageFee = manageFee;
	}

	public String getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(String repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public String getRepayStatus() {
		return repayStatus;
	}

	public void setRepayStatus(String repayStatus) {
		this.repayStatus = repayStatus;
	}

	public String getAdvanceStatus() {
		return advanceStatus;
	}

	public void setAdvanceStatus(String advanceStatus) {
		this.advanceStatus = advanceStatus;
	}

	public String getLateStatus() {
		return lateStatus;
	}

	public void setLateStatus(String lateStatus) {
		this.lateStatus = lateStatus;
	}

	public String getChargeDays() {
		return chargeDays;
	}

	public void setChargeDays(String chargeDays) {
		this.chargeDays = chargeDays;
	}

	public String getAdvanceDays() {
		return advanceDays;
	}

	public void setAdvanceDays(String advanceDays) {
		this.advanceDays = advanceDays;
	}

	public String getChargeInterest() {
		return chargeInterest;
	}

	public void setChargeInterest(String chargeInterest) {
		this.chargeInterest = chargeInterest;
	}

	public String getAdvanceInterest() {
		return advanceInterest;
	}

	public void setAdvanceInterest(String advanceInterest) {
		this.advanceInterest = advanceInterest;
	}

	public String getDelayDays() {
		return delayDays;
	}

	public void setDelayDays(String delayDays) {
		this.delayDays = delayDays;
	}

	public String getDelayInterest() {
		return delayInterest;
	}

	public void setDelayInterest(String delayInterest) {
		this.delayInterest = delayInterest;
	}

	public String getLateDays() {
		return lateDays;
	}

	public void setLateDays(String lateDays) {
		this.lateDays = lateDays;
	}

	public String getLateInterest() {
		return lateInterest;
	}

	public void setLateInterest(String lateInterest) {
		this.lateInterest = lateInterest;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<ProjectRepayBean> getUserRepayList() {
		return userRepayList;
	}

	public void setUserRepayList(List<ProjectRepayBean> userRepayList) {
		this.userRepayList = userRepayList;
	}

	public String getAllRepay() {
		return allRepay;
	}

	public void setAllRepay(String allRepay) {
		this.allRepay = allRepay;
	}

	public String getOnlyAllRepay() {
		return onlyAllRepay;
	}

	public void setOnlyAllRepay(String onlyAllRepay) {
		this.onlyAllRepay = onlyAllRepay;
	}

	public List<Integer> getLateArray() {
		return lateArray;
	}

	public void setLateArray(List<Integer> lateArray) {
		this.lateArray = lateArray;
	}

	public Integer getLatePeriod() {
		return latePeriod;
	}

	public void setLatePeriod(Integer latePeriod) {
		this.latePeriod = latePeriod;
	}

	public String getAllPeriodStr() {
		return allPeriodStr;
	}

	public void setAllPeriodStr(String allPeriodStr) {
		this.allPeriodStr = allPeriodStr;
	}

	public String getChargeOriginalInterest() {
		return chargeOriginalInterest;
	}

	public void setChargeOriginalInterest(String chargeOriginalInterest) {
		this.chargeOriginalInterest = chargeOriginalInterest;
	}

	public String getChargePenaltyInterest() {
		return chargePenaltyInterest;
	}

	public void setChargePenaltyInterest(String chargePenaltyInterest) {
		this.chargePenaltyInterest = chargePenaltyInterest;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}
}
