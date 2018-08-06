/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;

import java.io.Serializable;
import java.util.List;

/**
 * @author yaoyong
 * @version AdminCouponUserCustomizeVO, v0.1 2018/7/24 15:23
 */
public class AdminCouponUserCustomizeVO extends BaseVO implements Serializable {
    private List<CouponConfigCustomizeVO> couponConfigCustomizeVOS;

    private List<ActivityListCustomizeVO> activityListCustomizeVOS;

    public List<CouponConfigCustomizeVO> getCouponConfigCustomizeVOS() {
        return couponConfigCustomizeVOS;
    }

    public void setCouponConfigCustomizeVOS(List<CouponConfigCustomizeVO> couponConfigCustomizeVOS) {
        this.couponConfigCustomizeVOS = couponConfigCustomizeVOS;
    }

    public List<ActivityListCustomizeVO> getActivityListCustomizeVOS() {
        return activityListCustomizeVOS;
    }

    public void setActivityListCustomizeVOS(List<ActivityListCustomizeVO> activityListCustomizeVOS) {
        this.activityListCustomizeVOS = activityListCustomizeVOS;
    }
}
