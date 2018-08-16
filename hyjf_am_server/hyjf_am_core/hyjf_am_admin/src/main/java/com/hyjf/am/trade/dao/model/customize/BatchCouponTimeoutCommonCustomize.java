/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @author yaoy
 * @version BatchCouponTimeoutCommonCustomize, v0.1 2018/6/22 14:10
 */
public class BatchCouponTimeoutCommonCustomize implements Serializable {
    private static final long serialVersionUID = 6302534038180678827L;

    /**
     * 用户编号
     */
    private Integer userId;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 代金券面值总计
     */
    private Integer couponQuota;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(Integer couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
