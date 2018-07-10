package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

/**
 * 债转详情bean
 * @author zhangyk
 * @date 2018/7/10 17:12
 */
public class BorrowCreditInfoVO extends BaseVO {

    /*项目编号*/
    private String assignNid;

    /*债转编号*/
    private String creditNid;

    /*项目编号*/
    private String bidNid;

    /*出让人姓名*/
    private String creditUserName;

    /*认购人姓名*/
    private String userName;

    /*转让本金*/
    private String assignCapital;

    /*转让价格*/
    private String assignCapitalPrice;

    /*折让率*/
    private String creditDiscount;

    /*认购金额*/
    private String assignPrice;

    /*垫付利息*/
    private String assignInterestAdvance;

    /*债转服务费*/
    private String creditFee;

    /*支付金额*/
    private String assignPay;

    /*认购时间*/
    private String addTime;


    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(String assignCapital) {
        this.assignCapital = assignCapital;
    }

    public String getAssignCapitalPrice() {
        return assignCapitalPrice;
    }

    public void setAssignCapitalPrice(String assignCapitalPrice) {
        this.assignCapitalPrice = assignCapitalPrice;
    }

    public String getCreditDiscount() {
        return creditDiscount;
    }

    public void setCreditDiscount(String creditDiscount) {
        this.creditDiscount = creditDiscount;
    }

    public String getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(String assignPrice) {
        this.assignPrice = assignPrice;
    }

    public String getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(String assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public String getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(String creditFee) {
        this.creditFee = creditFee;
    }

    public String getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(String assignPay) {
        this.assignPay = assignPay;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
}
