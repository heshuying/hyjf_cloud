package com.hyjf.am.trade.dao.model.customize;

import java.math.BigDecimal;

public class HjhReInvestDebtCustomize {

    //计划订单号（检索）
    private String assignPlanOrderIdSrch;
    //承接计划编号（检索）
    private String assignPlanNidSrch;
    //承接订单号（检索）
    private String assignOrderIdSrch;
    //承接人（检索）
    private String userNameSrch;
    //出让人（检索）
    private String creditUserNameSrch;
    //债转编号（检索）
    private String creditNidSrch;
    //原项目编号（检索）
    private String borrowNidSrch;
    //承接方式（检索）
    private String assignTypeSrch;
    //还款方式（检索）
    private String borrowStyleSrch;

    //计划订单号
    private String assignPlanOrderId;
    //承接计划编号
    private String assignPlanNid;
    //承接订单号
    private String assignOrderId;
    //承接人
    private String userName;
    //出让人
    private String creditUserName;
    //债转编号
    private String creditNid;
    //原项目编号
    private String borrowNid;
    //还款方式
    private String borrowStyle;
    //承接本金
    private String assignCapital;
    //垫付利息
    private String assignInterestAdvance;
    //实际支付金额(购买价格)
    private String assignPay;
    //承接方式
    private String assignType;
    //项目总期数
    private String borrowPeriod;
    //承接时所在期数
    private String assignPeriod;
    //承接时间
    private String assignTime;
    //前画面传入
    private String planNid;
    private String date;

    private BigDecimal sumCreditCapital;
    private BigDecimal sumLiquidationFairValue;
    private BigDecimal sumAssignCapital;
    private BigDecimal sumAssignAdvanceInterest;

    public String getAssignPlanOrderIdSrch() {
        return assignPlanOrderIdSrch;
    }

    public void setAssignPlanOrderIdSrch(String assignPlanOrderIdSrch) {
        this.assignPlanOrderIdSrch = assignPlanOrderIdSrch;
    }

    public String getAssignPlanNidSrch() {
        return assignPlanNidSrch;
    }

    public void setAssignPlanNidSrch(String assignPlanNidSrch) {
        this.assignPlanNidSrch = assignPlanNidSrch;
    }

    public String getAssignOrderIdSrch() {
        return assignOrderIdSrch;
    }

    public void setAssignOrderIdSrch(String assignOrderIdSrch) {
        this.assignOrderIdSrch = assignOrderIdSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getCreditUserNameSrch() {
        return creditUserNameSrch;
    }

    public void setCreditUserNameSrch(String creditUserNameSrch) {
        this.creditUserNameSrch = creditUserNameSrch;
    }

    public String getCreditNidSrch() {
        return creditNidSrch;
    }

    public void setCreditNidSrch(String creditNidSrch) {
        this.creditNidSrch = creditNidSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getAssignTypeSrch() {
        return assignTypeSrch;
    }

    public void setAssignTypeSrch(String assignTypeSrch) {
        this.assignTypeSrch = assignTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getAssignPlanOrderId() {
        return assignPlanOrderId;
    }

    public void setAssignPlanOrderId(String assignPlanOrderId) {
        this.assignPlanOrderId = assignPlanOrderId;
    }

    public String getAssignPlanNid() {
        return assignPlanNid;
    }

    public void setAssignPlanNid(String assignPlanNid) {
        this.assignPlanNid = assignPlanNid;
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }

    public void setAssignOrderId(String assignOrderId) {
        this.assignOrderId = assignOrderId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getAssignType() {
        return assignType;
    }

    public void setAssignType(String assignType) {
        this.assignType = assignType;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getAssignPeriod() {
        return assignPeriod;
    }

    public void setAssignPeriod(String assignPeriod) {
        this.assignPeriod = assignPeriod;
    }

    public String getAssignTime() {
        return assignTime;
    }

    public void setAssignTime(String assignTime) {
        this.assignTime = assignTime;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public BigDecimal getSumCreditCapital() {
        return sumCreditCapital;
    }

    public void setSumCreditCapital(BigDecimal sumCreditCapital) {
        this.sumCreditCapital = sumCreditCapital;
    }

    public BigDecimal getSumLiquidationFairValue() {
        return sumLiquidationFairValue;
    }

    public void setSumLiquidationFairValue(BigDecimal sumLiquidationFairValue) {
        this.sumLiquidationFairValue = sumLiquidationFairValue;
    }

    public BigDecimal getSumAssignCapital() {
        return sumAssignCapital;
    }

    public void setSumAssignCapital(BigDecimal sumAssignCapital) {
        this.sumAssignCapital = sumAssignCapital;
    }

    public BigDecimal getSumAssignAdvanceInterest() {
        return sumAssignAdvanceInterest;
    }

    public void setSumAssignAdvanceInterest(BigDecimal sumAssignAdvanceInterest) {
        this.sumAssignAdvanceInterest = sumAssignAdvanceInterest;
    }
}
