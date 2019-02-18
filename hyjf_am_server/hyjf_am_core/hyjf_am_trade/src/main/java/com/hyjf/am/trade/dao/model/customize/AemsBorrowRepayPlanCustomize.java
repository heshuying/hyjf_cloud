/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * AEMS系统:还款计划查询接口
 *
 * @author liuyang
 * @version AemsBorrowRepayPlanCustomize, v0.1 2018/10/16 19:04
 */
public class AemsBorrowRepayPlanCustomize implements Serializable{
    // 逾期服务费
    private Integer lateDays;
    // 逾期服务费
    private String lateInterest;
    // 还款服务费
    private String repayFee;
    // 实还本金
    private String repayCapitalYes;
    // 实还利息
    private String repayInterestYes;
    // 借到金额
    private String borrowAccountYes;
    // 实还服务费
    private String manageFeeYes;
    // 提前还款减息
    private String reduceInterest;
    // 标的编号
    private String borrowNid;
    // 借款金额
    private String borrowAccount;
    // 年化利率
    private String borrowInterest;
    // 到账金额
    private  String yesAccount;
    // 到账时间
    private String yesAccountTime;
    // 应还本金
    private String repayCapital;
    // 应还利息
    private String repayInterest;
    // 应还总额
    private String borrowTotal;
    // 已还总额
    private String repayTotal;
    // 实际还款时间
    private String repayYseTime;
    // 期数
    private String repayPeriod;
    // 管理费
    private String manageFee;
    // 本期实际还款本息
    private String repayAccount;
    // 资产状态 0：未还款 1：已还款
    private String repayStatus;
    // 本期应还金额
    private String planRepayTotal;
    // 还款时间
    private String repayTime;
    // 总期数
    private String periods;
    // 资产编号
    private String productId;

    //第三方接口机构编号
    private String instCode;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowInterest() {
        return borrowInterest;
    }

    public void setBorrowInterest(String borrowInterest) {
        this.borrowInterest = borrowInterest;
    }

    public String getYesAccount() {
        return yesAccount;
    }

    public void setYesAccount(String yesAccount) {
        this.yesAccount = yesAccount;
    }

    public String getYesAccountTime() {
        return yesAccountTime;
    }

    public void setYesAccountTime(String yesAccountTime) {
        this.yesAccountTime = yesAccountTime;
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

    public String getBorrowTotal() {
        return borrowTotal;
    }

    public void setBorrowTotal(String borrowTotal) {
        this.borrowTotal = borrowTotal;
    }

    public String getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(String repayTotal) {
        this.repayTotal = repayTotal;
    }

    public String getRepayYseTime() {
        return repayYseTime;
    }

    public void setRepayYseTime(String repayYseTime) {
        this.repayYseTime = repayYseTime;
    }

    public String getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(String repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(String repayAccount) {
        this.repayAccount = repayAccount;
    }

    public String getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(String repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getPlanRepayTotal() {
        return planRepayTotal;
    }

    public void setPlanRepayTotal(String planRepayTotal) {
        this.planRepayTotal = planRepayTotal;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime;
    }

    public String getPeriods() {
        return periods;
    }

    public void setPeriods(String periods) {
        this.periods = periods;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public String getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(String lateInterest) {
        this.lateInterest = lateInterest;
    }

    public String getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(String repayFee) {
        this.repayFee = repayFee;
    }

    public String getRepayCapitalYes() {
        return repayCapitalYes;
    }

    public void setRepayCapitalYes(String repayCapitalYes) {
        this.repayCapitalYes = repayCapitalYes;
    }

    public String getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(String repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public String getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(String borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public String getManageFeeYes() {
        return manageFeeYes;
    }

    public void setManageFeeYes(String manageFeeYes) {
        this.manageFeeYes = manageFeeYes;
    }

    public String getReduceInterest() {
        return reduceInterest;
    }

    public void setReduceInterest(String reduceInterest) {
        this.reduceInterest = reduceInterest;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
}
