package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * 还款信息详情bean
 * @author zhangyk
 * @date 2018/7/12 11:55
 */
public class BorrowCreditRepayInfoVO extends BaseVO implements Serializable {

    /*借款编号*/
    private String borrowNid;

    /*还款方式*/
    private String borrowStyle;

    /*借款标题*/
    private String borrowName;

    /*还款期数*/
    private String recoverPeriod;

    /*待收本金*/
    private String assignCapital;

    /*待收利息*/
    private String assignInterest;

    /*待收本息*/
    private String assignAccount;

    /*还款服务费*/
    private String manageFee;

    /*还款状态*/
    private String status;

    /*下次应还时间*/
    private  String assignRepayNextTime;


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

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignInterest() {
        return assignInterest;
    }

    public void setAssignInterest(String assignInterest) {
        this.assignInterest = assignInterest;
    }

    public String getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(String assignAccount) {
        this.assignAccount = assignAccount;
    }

    public String getManageFee() {
        return manageFee;
    }

    public void setManageFee(String manageFee) {
        this.manageFee = manageFee;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignRepayNextTime() {
        return assignRepayNextTime;
    }

    public void setAssignRepayNextTime(String assignRepayNextTime) {
        this.assignRepayNextTime = assignRepayNextTime;
    }
}
