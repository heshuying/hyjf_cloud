package com.hyjf.cs.user.service;

/**
 * @author xiasq
 * @version CouponService, v0.1 2018/4/12 11:36
 */
public interface CouponService {
    /**
     * 检查活动是否有效
     * @param activityId
     * @return
     */
    boolean checkActivityIfAvailable(String activityId);
}
