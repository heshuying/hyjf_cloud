package com.hyjf.cs.trade.service.coupon;

import com.hyjf.am.vo.coupon.UserCouponBean;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 15:05
 * @Description: CouponSendMessageService
 */
public interface CouponSendMessageService {
    /**
     * @Author walter.limeng
     * @Description  自动发放用户优惠券
     * @Date 15:28 2018/7/16
     * @Param userCouponBean
     * @return 
     */
    void insertUserCoupon(UserCouponBean userCouponBean) throws Exception;
}
