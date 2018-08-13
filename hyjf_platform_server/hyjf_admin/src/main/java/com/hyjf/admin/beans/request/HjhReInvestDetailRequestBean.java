package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;

/**
 * @Author : huanghui
 */
public class HjhReInvestDetailRequestBean extends BasePage {


    //标的planNid
    private String planNid;
    //时间
    private String date;
    //计划投资订单号（检索）
    private String accedeOrderIdSrch;
    //用户名（检索）
    private String userNameSrch;
    //借款编号（检索）
    private String borrowNidSrch;
    //借款期限(锁定期)（检索）
    private String lockPeriodSrch;
    //投资方式（检索）
    private String investTypeSrch;
    //还款方式（检索）
    private String borrowStyleSrch;

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

    public String getAccedeOrderIdSrch() {
        return accedeOrderIdSrch;
    }

    public void setAccedeOrderIdSrch(String accedeOrderIdSrch) {
        this.accedeOrderIdSrch = accedeOrderIdSrch;
    }

    public String getUserNameSrch() {
        return userNameSrch;
    }

    public void setUserNameSrch(String userNameSrch) {
        this.userNameSrch = userNameSrch;
    }

    public String getBorrowNidSrch() {
        return borrowNidSrch;
    }

    public void setBorrowNidSrch(String borrowNidSrch) {
        this.borrowNidSrch = borrowNidSrch;
    }

    public String getLockPeriodSrch() {
        return lockPeriodSrch;
    }

    public void setLockPeriodSrch(String lockPeriodSrch) {
        this.lockPeriodSrch = lockPeriodSrch;
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
}
