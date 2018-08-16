package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * 微信端端已回款列表
 */
public class WechatRepayMentListCustomize implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 3309762895449949778L;
    // 投资中项目id
    private String borrowNid;
    // 投资中项目名称
    private String borrowName;
    // 项目年华收益
    private String borrowApr;
    // 投资中待收本金
    private String account;
    // 投资中待收收益
    private String interest;
    // 投资合同url
    private String borrowUrl = "";
    // 项目进度
    private String borrowSchedule;
    // 优惠券投资时的优惠券类型，费优惠券投资则为空字符串
    private String couponType;
    private String label;
    // 投资类型 1直投 2优惠券 3加息
    private String investType;
    // RTB
    private String projectType;
    // 回款时间
    private String recoverTime;

    // 投资订单号
    private String orderId;
    // 项目期限
    private String period;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getBorrowUrl() {
        return borrowUrl;
    }

    public void setBorrowUrl(String borrowUrl) {
        this.borrowUrl = borrowUrl;
    }

    public String getBorrowSchedule() {
        return borrowSchedule;
    }

    public void setBorrowSchedule(String borrowSchedule) {
        this.borrowSchedule = borrowSchedule;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}