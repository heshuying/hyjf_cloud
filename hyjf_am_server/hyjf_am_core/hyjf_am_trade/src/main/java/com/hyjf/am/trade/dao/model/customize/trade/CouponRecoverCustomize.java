/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize.trade;

import java.io.Serializable;

/**
 * @author yaoy
 * @version CouponRecoverCustomize, v0.1 2018/6/21 15:45
 */
public class CouponRecoverCustomize implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    //项目编号
    private String borrowNid;
    //还款期数
    private String recoverPeriod;
    //已经还款时间
    private String recoverYestime;
    //应还利息
    private String recoverInterest;
    //应还本息
    private String recoverAccount;
    //应还本金
    private String recoverCapital;
    //估计还款时间
    private String recoverTime;
    //过期时间
    private String expTime;
    //转账订单编号
    private String transferId;
    //收益领取标识
    private String receivedFlg;
    //收益领取状态原始值
    private String receivedFlgOrigin;
    // 还款状态：0：未还款，1：已还款
    private String recoverStatus;
    // 转账时间
    private String transferTime;
    // 更新时间
    private int updateTime;

    // 优惠券投资编号
    private String tenderNid;
    // 优惠券类别
    private int couponType;
    // 操作ip
    private String addip;
    // 用户优惠券编号
    private String couponUserCode;


    /**
     * 检索条件 时间开始
     */
    private String timeStartSrch;

    /**
     * 检索条件 时间结束
     */
    private String timeEndSrch;


    /**
     * 检索条件 limitStart
     */
    private int limitStart = -1;

    /**
     * 检索条件 limitEnd
     */
    private int limitEnd = -1;

    /**
     * 检索条件 用户id
     */
    private String  userId;

    /**
     * 检索条件 项目编号
     */
    private String  bNid;
    /**
     * 检索条件 用户 优惠券id
     */
    private String  tenderId;

    /**
     * 检索条件 订单id
     */
    private String orderId;

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(String recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(String recoverYestime) {
        this.recoverYestime = recoverYestime;
    }

    public String getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(String recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public String getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(String recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getTransferId() {
        return transferId;
    }

    public void setTransferId(String transferId) {
        this.transferId = transferId;
    }

    public String getReceivedFlg() {
        return receivedFlg;
    }

    public void setReceivedFlg(String receivedFlg) {
        this.receivedFlg = receivedFlg;
    }

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getbNid() {
        return bNid;
    }

    public void setbNid(String bNid) {
        this.bNid = bNid;
    }

    public String getTenderId() {
        return tenderId;
    }

    public void setTenderId(String tenderId) {
        this.tenderId = tenderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }


    public String getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(String recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public String getTransferTime() {
        return transferTime;
    }

    public void setTransferTime(String transferTime) {
        this.transferTime = transferTime;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid;
    }

    public int getCouponType() {
        return couponType;
    }

    public void setCouponType(int couponType) {
        this.couponType = couponType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip;
    }

    public String getExpTime() {
        return expTime;
    }

    public void setExpTime(String expTime) {
        this.expTime = expTime;
    }

    public int getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(int updateTime) {
        this.updateTime = updateTime;
    }

    public String getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(String recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public String getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(String recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public String getReceivedFlgOrigin() {
        return receivedFlgOrigin;
    }

    public void setReceivedFlgOrigin(String receivedFlgOrigin) {
        this.receivedFlgOrigin = receivedFlgOrigin;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

}
