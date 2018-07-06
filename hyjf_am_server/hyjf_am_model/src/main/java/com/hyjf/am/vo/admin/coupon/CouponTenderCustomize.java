package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;

import java.math.BigDecimal;

public class CouponTenderCustomize extends BaseVO {
    //投资订单号
    private String orderId;
    //用户名
    private String username;
    //0:无主单；1：有主单；2：线下员工；3：线上员工
    private String attribute;
    //用户优惠券编号
    private String couponUserCode;
    //优惠券编号
    private String couponCode;
    //优惠券类型描述
    private String couponTypeStr;
    //优惠券额度
    private String couponQuota;
    //项目编号
    private String borrowNid;
    //投资金额
    private BigDecimal account;
    //借款期限
    private String borrowPeriod;
    //年化利率
    private String borrowApr;
    /**
     * 还款状态（分期标的所有分期都已还款才统计为已还款）
     */
    private String receivedFlgAll;
    //投资平台
    private String operatingDeck;
    //使用时间
    private String orderDate;
    //用户名
    private String userId;
    //主键id
    private Integer id;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCouponTypeStr() {
        return couponTypeStr;
    }

    public void setCouponTypeStr(String couponTypeStr) {
        this.couponTypeStr = couponTypeStr;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getReceivedFlgAll() {
        return receivedFlgAll;
    }

    public void setReceivedFlgAll(String receivedFlgAll) {
        this.receivedFlgAll = receivedFlgAll;
    }

    public String getOperatingDeck() {
        return operatingDeck;
    }

    public void setOperatingDeck(String operatingDeck) {
        this.operatingDeck = operatingDeck;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
