/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 *           Created at: 2015年11月11日 下午2:17:31
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.am.trade.bean.repay;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class ProjectRepayDetailBean implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -3054505521964711662L;

    // 用户id
    public String userId;

    // 用户名
    public String userName;

    // 实际还款总额
    public String repayTotal;

    // 应还本息和
    public String repayAccount;

    // 应还本金
    public String repayCapital;

    // 应还利息
    public String repayInterest;

    // 项目管理费
    public String manageFee;

    // 提前还款标识
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

    // 还款时间
    public String repayTime;

    // 还款状态
    public String status;

    // 原始提前还款利息
    public String chargeOriginalInterest;

    // 提前还款罚息
    public String chargePenaltyInterest;

    /**
     * 构造方法
     */
    public ProjectRepayDetailBean() {
        super();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(String repayAccount) {
        this.repayAccount = repayAccount;
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

	public String getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(String chargeDays) {
        this.chargeDays = chargeDays;
    }

    public String getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(String chargeInterest) {
        this.chargeInterest = chargeInterest;
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

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

	public String getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(String repayCapital) {
        this.repayCapital = repayCapital;
    }

    public String getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(String advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public String getAdvanceDays() {
        return advanceDays;
    }

    public void setAdvanceDays(String advanceDays) {
        this.advanceDays = advanceDays;
    }

    public String getAdvanceInterest() {
        return advanceInterest;
    }

    public void setAdvanceInterest(String advanceInterest) {
        this.advanceInterest = advanceInterest;
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
}
