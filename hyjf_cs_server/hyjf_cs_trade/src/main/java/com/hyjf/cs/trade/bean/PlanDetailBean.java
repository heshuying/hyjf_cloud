package com.hyjf.cs.trade.bean;

import java.io.Serializable;

public class PlanDetailBean implements Serializable {

    private String planNid;

    private String planName;

    private String planApr;

    private String planPeriod;

    private String planAccount;

    private String planStatus;

    private String timer;

    private String buyBeginTime;

    private String planAccountWait;

    private String debtMinInvestment;

    private String debtInvestmentIncrement;

    private String debtMaxInvestment;

    private String buyBeginTimeFormat;

    private String fullExpireTimeFormat;

    private String liquidateFactTimeFormat;
    //还款方式(hjh)
    private String borrowStyle;
    //还款方式名称(hjh)
    private String borrowStyleName;
    // 默认0 天标，1 月标
    private String isMonth;

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getPlanApr() {
        return planApr;
    }

    public void setPlanApr(String planApr) {
        this.planApr = planApr;
    }

    public String getPlanPeriod() {
        return planPeriod;
    }

    public void setPlanPeriod(String planPeriod) {
        this.planPeriod = planPeriod;
    }

    public String getPlanAccount() {
        return planAccount;
    }

    public void setPlanAccount(String planAccount) {
        this.planAccount = planAccount;
    }

    public String getPlanStatus() {
        return planStatus;
    }

    public void setPlanStatus(String planStatus) {
        this.planStatus = planStatus;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public String getBuyBeginTime() {
        return buyBeginTime;
    }

    public void setBuyBeginTime(String buyBeginTime) {
        this.buyBeginTime = buyBeginTime;
    }

    public String getPlanAccountWait() {
        return planAccountWait;
    }

    public void setPlanAccountWait(String planAccountWait) {
        this.planAccountWait = planAccountWait;
    }

    public String getDebtMinInvestment() {
        return debtMinInvestment;
    }

    public void setDebtMinInvestment(String debtMinInvestment) {
        this.debtMinInvestment = debtMinInvestment;
    }

    public String getDebtInvestmentIncrement() {
        return debtInvestmentIncrement;
    }

    public void setDebtInvestmentIncrement(String debtInvestmentIncrement) {
        this.debtInvestmentIncrement = debtInvestmentIncrement;
    }

    public String getDebtMaxInvestment() {
        return debtMaxInvestment;
    }

    public void setDebtMaxInvestment(String debtMaxInvestment) {
        this.debtMaxInvestment = debtMaxInvestment;
    }

    public String getBuyBeginTimeFormat() {
        return buyBeginTimeFormat;
    }

    public void setBuyBeginTimeFormat(String buyBeginTimeFormat) {
        this.buyBeginTimeFormat = buyBeginTimeFormat;
    }

    public String getFullExpireTimeFormat() {
        return fullExpireTimeFormat;
    }

    public void setFullExpireTimeFormat(String fullExpireTimeFormat) {
        this.fullExpireTimeFormat = fullExpireTimeFormat;
    }

    public String getLiquidateFactTimeFormat() {
        return liquidateFactTimeFormat;
    }

    public void setLiquidateFactTimeFormat(String liquidateFactTimeFormat) {
        this.liquidateFactTimeFormat = liquidateFactTimeFormat;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getBorrowStyleName() {
        return borrowStyleName;
    }

    public void setBorrowStyleName(String borrowStyleName) {
        this.borrowStyleName = borrowStyleName;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }
}
