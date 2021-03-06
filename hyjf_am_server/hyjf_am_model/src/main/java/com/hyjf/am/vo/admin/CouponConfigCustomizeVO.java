/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CouponConfigCustomizeVO, v0.1 2018/7/5 11:29
 */
public class CouponConfigCustomizeVO extends BaseVO implements Serializable {
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

    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
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

    public String getIsExpiration() {
        return isExpiration;
    }

    public void setIsExpiration(String isExpiration) {
        this.isExpiration = isExpiration;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }
}
