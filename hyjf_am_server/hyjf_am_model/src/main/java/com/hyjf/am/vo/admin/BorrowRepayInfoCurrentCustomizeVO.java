package com.hyjf.am.vo.admin;

/**
 * @author hesy
 */
public class BorrowRepayInfoCurrentCustomizeVO {
    /** 借款编号*/
    private String borrowNid;
    /** 出借订单号*/
    private String tenderOrdid;
    /** 承接订单号*/
    private String assignOrdid;
    /** 还款订单号*/
    private String repayOrdid;
    /** 资产来源*/
    private String instName;
    /** 计划编号*/
    private String planNid;
    /** 借款人用户名*/
    private String borrowUserName;
    /** 出借人用户名*/
    private String recoverUserName;
    /** 期数*/
    private String period;
    /** 原始出借金额*/
    private String account;
    /** 持有金额*/
    private String amountHold;
    /** 应还本金*/
    private String recoverCapital;
    /** 应还利息*/
    private String recoverInterest;
    /** 应还本息*/
    private String recoverAccount;
    /** 应还管理费*/
    private String recoverFee;
    /** 还款状态 1已还 0未还*/
    private String recoverStatus;
    /** 还款来源*/
    private String repayMoneySource;
    /** 实还总额*/
    private String recoverAmountTotal;
    /** 应还时间*/
    private String recoverTime;
    /** 实际还款时间*/
    private String repayActionTime;
    /** 记录类别 1 未分期原始出借 2 分期原始出借 3 汇转让承接 4 计划底层债转标的承接*/
    private String recordType;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getTenderOrdid() {
        return tenderOrdid;
    }

    public void setTenderOrdid(String tenderOrdid) {
        this.tenderOrdid = tenderOrdid;
    }

    public String getAssignOrdid() {
        return assignOrdid;
    }

    public void setAssignOrdid(String assignOrdid) {
        this.assignOrdid = assignOrdid;
    }

    public String getRepayOrdid() {
        return repayOrdid;
    }

    public void setRepayOrdid(String repayOrdid) {
        this.repayOrdid = repayOrdid;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName;
    }

    public String getRecoverUserName() {
        return recoverUserName;
    }

    public void setRecoverUserName(String recoverUserName) {
        this.recoverUserName = recoverUserName;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAmountHold() {
        return amountHold;
    }

    public void setAmountHold(String amountHold) {
        this.amountHold = amountHold;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(String recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public String getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(String recoverFee) {
        this.recoverFee = recoverFee;
    }

    public String getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(String recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public String getRepayMoneySource() {
        return repayMoneySource;
    }

    public void setRepayMoneySource(String repayMoneySource) {
        this.repayMoneySource = repayMoneySource;
    }

    public String getRecoverAmountTotal() {
        return recoverAmountTotal;
    }

    public void setRecoverAmountTotal(String recoverAmountTotal) {
        this.recoverAmountTotal = recoverAmountTotal;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }
}
