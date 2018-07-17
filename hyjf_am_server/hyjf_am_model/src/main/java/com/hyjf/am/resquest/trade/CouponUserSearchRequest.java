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

    private String userId;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
