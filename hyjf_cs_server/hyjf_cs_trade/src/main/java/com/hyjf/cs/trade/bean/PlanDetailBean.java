package com.hyjf.cs.trade.bean;

import java.io.Serializable;

public class PlanDetailBean implements Serializable {

    private String planNid;

    private String planName;

    private String planApr;

    private String planPeroid;

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

    public String getPlanPeroid() {
        return planPeroid;
    }

    public void setPlanPeroid(String planPeroid) {
        this.planPeroid = planPeroid;
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
}
