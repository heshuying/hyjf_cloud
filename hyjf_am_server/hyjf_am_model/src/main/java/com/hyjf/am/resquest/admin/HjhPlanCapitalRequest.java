package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * 汇计划--计划资金
 * @Author : huanghui
 */
public class HjhPlanCapitalRequest extends BasePage implements Serializable {

    //借款编号（检索）
    private String borrowNidSrch;
    //投资方式（检索）
    private String investTypeSrch;
    //还款方式（检索）
    private String borrowStyleSrch;

    //计划订单号
    private String accedeOrderId;
    //用户名
    private String userName;
    //推荐人
    private String inviteUserName;
    //用户属性
    private String userAttribute;
    //借款编号
    private String borrowNid;
    //年化利率
    private String expectApr;
    //借款期限(锁定期)
    private String borrowPeriod;
    private String isMonth;
    //投资金额
    private String accedeAccount;
    //还款方式
    private String borrowStyle;
    //投资方式
    private String investType;
    //计息时间
    private String countInterestTime;
    //投资时间
    private String addTime;

    private String planNid;




    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getInvestTypeSrch() {
        return investTypeSrch;
    }

    public void setInvestTypeSrch(String investTypeSrch) {
        this.investTypeSrch = investTypeSrch;
    }

    public String getBorrowStyleSrch() {
        return borrowStyleSrch;
    }

    public void setBorrowStyleSrch(String borrowStyleSrch) {
        this.borrowStyleSrch = borrowStyleSrch;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId;
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

}
