/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.am.vo.trade.coupon;

import java.math.BigDecimal;

/**
 * @Description 优惠券投资实体
 * @Author sss
 * @Date 2018/6/27 15:49
 */
public class CouponTenderUsedVO {
    private Integer couponGrantId;
    private Integer userId;
    private String account;
    private String ip;
    private Integer platform;
    private String borrowNid;
    private String mainTenderNid;
    private Integer period;
    private Integer tenderType;
    private BigDecimal expectApr;
    private  String borrowStyle;
    private BigDecimal couponInterest;
    private String userName;

    public Integer getCouponGrantId() {
        return couponGrantId;
    }

    public void setCouponGrantId(Integer couponGrantId) {
        this.couponGrantId = couponGrantId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPlatform() {
        return platform;
    }

    public void setPlatform(Integer platform) {
        this.platform = platform;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getMainTenderNid() {
        return mainTenderNid;
    }

    public void setMainTenderNid(String mainTenderNid) {
        this.mainTenderNid = mainTenderNid;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public BigDecimal getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(BigDecimal expectApr) {
        this.expectApr = expectApr;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public BigDecimal getCouponInterest() {
        return couponInterest;
    }

    public void setCouponInterest(BigDecimal couponInterest) {
        this.couponInterest = couponInterest;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
