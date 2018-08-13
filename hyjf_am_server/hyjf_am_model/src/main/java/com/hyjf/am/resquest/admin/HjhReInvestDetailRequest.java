package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * 产品中心 --> 汇计划 --> 资金计划 -> 复投详情
 * @Author : huanghui
 */
public class HjhReInvestDetailRequest implements Serializable {

    //时间
    private String date;
    //计划投资订单号（检索）
    private String accedeOrderIdSrch;
    //用户名（检索）
    private String userNameSrch;
    //借款期限(锁定期)（检索）
    private String lockPeriodSrch;
    //投资方式（检索）
    private String investTypeSrch;
    //还款方式（检索）
    private String borrowStyleSrch;

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
