package com.hyjf.am.resquest.trade;

import java.util.List;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 18:12
 * @Description: CouponUserSearchRequest
 */
public class CouponUserSearchRequest {
    private List<String> couponCodeList;

    private Integer activityId;

    private Integer userId;

    public List<String> getCouponCodeList() {
        return couponCodeList;
    }

    public void setCouponCodeList(List<String> couponCodeList) {
        this.couponCodeList = couponCodeList;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
