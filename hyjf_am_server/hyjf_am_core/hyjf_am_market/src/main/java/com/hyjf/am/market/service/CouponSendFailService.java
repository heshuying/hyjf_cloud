package com.hyjf.am.market.service;

/**
 * @author xiasq
 * @version CouponSendFailService, v0.1 2019/5/20 11:37
 */
public interface CouponSendFailService {
    /**
     * 保存
     * @param userId
     * @param activityId
     * @param couponCode
     * @param sendFlg
     * @param remark
     * @return
     */
    int save(Integer userId, Integer activityId, String couponCode, Integer sendFlg, String remark);
}
