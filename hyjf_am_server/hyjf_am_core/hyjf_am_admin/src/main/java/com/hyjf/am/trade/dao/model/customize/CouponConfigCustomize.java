/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CouponConfigCustomize, v0.1 2018/7/5 14:17
 */
public class CouponConfigCustomize implements Serializable {
    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;
    //主键id
    private Integer id;
    //优惠券名称
    private String couponName;
    //优惠券编号
    private String couponCode;
    //优惠券面值
    private String couponQuota;
    //优惠券类型
    private String couponType;
    //发行数量
    private String couponQuantity;
    //已发行数量
    private String issueNumber;
    //有效期类别
    private String expirationType;
    //有效期截止日
    private String expirationDate;
    //有效期时长
    private String expirationLength;
    //是否已过期 0 过期  1 未过期
    private String isExpiration;
    //发行时间
    private String addTime;
    //发行时间
    private int status;

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
     * limitStart
     *
     * @return the limitStart
     */

    public int getLimitStart() {
        return limitStart;
    }

    /**
     * @param limitStart
     *            the limitStart to set
     */

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * limitEnd
     *
     * @return the limitEnd
     */

    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd
     *            the limitEnd to set
     */

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getCouponQuantity() {
        return couponQuantity;
    }

    public void setCouponQuantity(String couponQuantity) {
        this.couponQuantity = couponQuantity;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getExpirationType() {
        return expirationType;
    }

    public void setExpirationType(String expirationType) {
        this.expirationType = expirationType;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getExpirationLength() {
        return expirationLength;
    }

    public void setExpirationLength(String expirationLength) {
        this.expirationLength = expirationLength;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
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

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIsExpiration() {
        return isExpiration;
    }

    public void setIsExpiration(String isExpiration) {
        this.isExpiration = isExpiration;
    }
}
