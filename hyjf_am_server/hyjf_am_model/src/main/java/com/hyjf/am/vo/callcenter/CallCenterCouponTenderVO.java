/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.callcenter;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author wangjun
 * @version CallCenterCouponTenderVO, v0.1 2018/6/19 15:06
 */
public class CallCenterCouponTenderVO extends BaseVO implements Serializable {
    //主键id
    private Integer id;
    //投资订单号
    private String orderId;
    //项目编号
    private String borrowNid;
    private String attribute;
    //优惠券编号
    private String couponUserCode;
    //优惠券编号
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
    //使用时间
    private String orderDate;
    //使用时间
    private String addTime;
    //优惠券适用平台
    private String couponSystem;
    //优惠券适用产品类型
    private String projectType;
    //投资项目期限条件
    private String projectExpirationType;
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
}
