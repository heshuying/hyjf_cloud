package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * admin: 还款信息bean
 * @author zhangyk
 * @date 2018/7/11 15:10
 */
public class BorrowCreditRepayVO  extends BaseVO implements Serializable {

    /*承接人*/
    private String userName;

    /*债转编号*/
    private String creditNid;

    /*出让人*/
    private String creditUserName;

    /*项目编号*/
    private String bidNid;

    /*订单号*/
    private String assignNid;

    /*应收本金*/
    private String assignCapital;

    /*应收利息*/
    private String assignInterest;

    /*应收本息*/
    private String assignAccount;

    /*已收本息*/
    private String assignRepayAccount;

    /*还款服务费*/
    private String creditFee;

    /*债权承接时间*/
    private String addTime;

    /*下次还款时间*/
    private String assignRepayNextTime;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
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

    public String getAssignRepayAccount() {
        return assignRepayAccount;
    }

    public void setAssignRepayAccount(String assignRepayAccount) {
        this.assignRepayAccount = assignRepayAccount;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getAssignRepayNextTime() {
        return assignRepayNextTime;
    }

    public void setAssignRepayNextTime(String assignRepayNextTime) {
        this.assignRepayNextTime = assignRepayNextTime;
    }
}
