/**
 * Description:江西银行账户实体类
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: 刘彬
 * @version: 1.0
 *           Created at: 2017年07月07日 下午2:33:39
 *           Modification History:
 *           Modified by :
 */
package com.hyjf.callcenter.beans;

import java.io.Serializable;

public class CouponTenderBean implements Serializable  {
	//用户名
    private String userName;
    //手机号
    private String mobile;
    //投资订单号
    private String orderId;
    //项目编号
    private String borrowNid;
    //用户属性
    private String attribute;
    //用户优惠券编号
    private String couponUserCode;
    //优惠券类别编号
    private String couponCode;
    //优惠券类型
    private String couponType;
    //优惠券额度
    private String couponQuota;
    //投资金额
    private String account;
    //投资平台
    private String operatingDeck;
    //年化利率
    private String borrowApr;
    //借款期限
    private String borrowPeriod;
    //状态
    private String receivedFlg;
    //优惠券使用时间
    private String orderDate;
    //优惠券获得时间
    private String addTime;
    //优惠券适用平台
    private String couponSystem;
    //优惠券适用产品类型
    private String projectType;
    //投资项目期限条件
    private String projectExpirationType;
    
    //投资类型
    private String tenderType="直投";
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getBorrowNid() {
        return borrowNid;
    }
    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }
    public String getAttribute() {
        return attribute;
    }
    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }
    public String getCouponUserCode() {
        return couponUserCode;
    }
    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }
    public String getCouponCode() {
        return couponCode;
    }
    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }
    public String getCouponType() {
        return couponType;
    }
    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }
    public String getCouponQuota() {
        return couponQuota;
    }
    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account = account;
    }
    public String getOperatingDeck() {
        return operatingDeck;
    }
    public void setOperatingDeck(String operatingDeck) {
        this.operatingDeck = operatingDeck;
    }
    public String getBorrowApr() {
        return borrowApr;
    }
    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }
    public String getBorrowPeriod() {
        return borrowPeriod;
    }
    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }
    public String getReceivedFlg() {
        return receivedFlg;
    }
    public void setReceivedFlg(String receivedFlg) {
        this.receivedFlg = receivedFlg;
    }
    public String getOrderDate() {
        return orderDate;
    }
    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }
    public String getCouponSystem() {
        return couponSystem;
    }
    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }
    public String getProjectType() {
        return projectType;
    }
    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
    public String getProjectExpirationType() {
        return projectExpirationType;
    }
    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }
    public String getTenderType() {
        return tenderType;
    }
    public void setTenderType(String tenderType) {
        this.tenderType = tenderType;
    }
    
}
