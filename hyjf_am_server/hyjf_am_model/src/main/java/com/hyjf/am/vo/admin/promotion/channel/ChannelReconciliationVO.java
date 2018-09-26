/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin.promotion.channel;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author fq
 * @version ChannelReconciliationVO, v0.1 2018/9/21 10:20
 */
public class ChannelReconciliationVO extends BaseVO implements Serializable {
    /*
     * 用户名
     */
    private Integer userId;
    /*
     * 用户名
     */
    private String userName;

    /*
     * 渠道
     */
    private String utmName;

    /*
     * 计划订单号
     */
    private String accedeOrderId;

    /*
     * 计划编号
     */
    private String planNid;

    /*
     * 计划锁定期
     */
    private Integer lockPeriod;


    /*
     * 投资订单
     */
    private String orderCode;
    /*
     * 借款编号
     */
    private String borrowNid;
    /*
     * 标的期限
     */
    private String borrowPeriod;
    /*
     * 投资金额
     */
    private String investAmount;

    /*
     * 投资时间
     */
    private String investTime;

    /**
     * 注册时间
     */
    private String registTime;

    /**
     * 是否首投 0是 1否
     */
    private Integer firstFlag;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

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

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getInvestAmount() {
        return investAmount;
    }

    public void setInvestAmount(String investAmount) {
        this.investAmount = investAmount;
    }

    public String getInvestTime() {
        return investTime;
    }

    public void setInvestTime(String investTime) {
        this.investTime = investTime;
    }

    public String getRegistTime() {
        return registTime;
    }

    public void setRegistTime(String registTime) {
        this.registTime = registTime;
    }

    public Integer getFirstFlag() {
        return firstFlag;
    }

    public void setFirstFlag(Integer firstFlag) {
        this.firstFlag = firstFlag;
    }
}
