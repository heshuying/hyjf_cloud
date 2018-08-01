/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version AdminCouponUserRequestBean, v0.1 2018/8/1 9:26
 */
public class AdminCouponUserRequestBean extends BasePage implements Serializable {

    private CouponUserBeanRequest couponUserBeanRequest;

    private CouponConfigVO couponConfigVO;

    private Integer userId;

    private String loginUserId;

    public CouponUserBeanRequest getCouponUserBeanRequest() {
        return couponUserBeanRequest;
    }

    public void setCouponUserBeanRequest(CouponUserBeanRequest couponUserBeanRequest) {
        this.couponUserBeanRequest = couponUserBeanRequest;
    }

    public CouponConfigVO getCouponConfigVO() {
        return couponConfigVO;
    }

    public void setCouponConfigVO(CouponConfigVO couponConfigVO) {
        this.couponConfigVO = couponConfigVO;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(String loginUserId) {
        this.loginUserId = loginUserId;
    }
}
