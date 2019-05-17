package com.hyjf.cs.trade.bean.repay;

import com.hyjf.am.response.Response;

import java.io.Serializable;
import java.util.List;

public class ProjectBean extends Response<ProjectBean> implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -2142812516666739413L;

    // 是否是汇添金项目
    public String tType;

    // 还款人id
    public String userId;
    
    //角色
    public String roleId;

    // 项目编号
    public String borrowNid;

    // 项目名称
    public String borrowName;

    // 项目还款方式
    public String borrowStyle;

    // 项目还款总额 加管理费
    public String repayTotal;
    
    // 项目还款本息和
    public String repayAccount;
    
    // 项目还款本金
    public String repayCapital;
    
    // 当前的利息
    public String repayInterest;
    
    //应还利息
    public String shouldInterest;
    
    // 当前管理费
    public String manageFee;

    // 当前还款期数
    public String repayPeriod;

    // 当前还款状态(是否完成 0未还款1还款中)
    public String repayStatus;

    // 当前还款方式（0正常还款1提前还款2延期还款3逾期还款）
    public String advanceStatus;

    // 提前天数
    public String chargeDays;

    public String advanceDays;

    // 提前还款利息
    public String chargeInterest;

    public String advanceInterest;

    // 延期天数
    public String delayDays;

    // 延期利息
    public String delayInterest;

    // 逾期天数
    public String lateDays;

    // 逾期罚息
    public String lateInterest;
    
    public String username;
    //本期应还笔数
	public String repayNum;

	public boolean isAllRepay;
    /** 用户还款详情 */
    private List<ProjectRepayBean> userRepayList;

	// 原始提前还款利息
	private String chargeOriginalInterest;

	// 提前还款罚息
	private String chargePenaltyInterest;

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

	public boolean isAllRepay() { return isAllRepay; }

	public void setAllRepay(boolean allRepay) { isAllRepay = allRepay; }

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
}
