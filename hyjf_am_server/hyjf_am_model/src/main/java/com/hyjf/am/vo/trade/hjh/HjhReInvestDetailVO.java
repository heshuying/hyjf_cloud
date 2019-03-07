package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class HjhReInvestDetailVO extends BaseVO implements Serializable {

    private String accedeOrderId;

    private String planNid;

    private String userName;

    private String inviteUserName;

    private String userAttribute;

    private String borrowNid;

    private String expectApr;

    private String borrowPeriod;

    //借款期限（带单位）
    private String borrowPeriodView;

    private String isMonth;

    private String accedeAccount;

    private String borrowStyle;

    private String investType;

    private String countInterestTime;

    private String addTime;

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(String isMonth) {
        this.isMonth = isMonth;
    }

    public String getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getCountInterestTime() {
        return countInterestTime;
    }

    public void setCountInterestTime(String countInterestTime) {
        this.countInterestTime = countInterestTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getBorrowPeriodView() {
        return borrowPeriodView;
    }

    public void setBorrowPeriodView(String borrowPeriodView) {
        this.borrowPeriodView = borrowPeriodView;
    }
}
