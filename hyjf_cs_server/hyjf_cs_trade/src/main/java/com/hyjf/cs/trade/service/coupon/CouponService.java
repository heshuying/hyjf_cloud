package com.hyjf.cs.trade.service.coupon;

import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;

/**
 * @author walter.limeng
 * @version CouponService, v0.1 2018/7/9 14:08
 */
public interface CouponService extends BaseTradeService {
    /**
     * 获取我的优惠券分页数据
     * @param userId 用户ID
     * @param page 页数
     * @param pageSize 大小
     * @param couponStatus 优惠券状态
     * @return List
     */
    List<CouponUserForAppCustomizeVO> getMyCoupon(Integer userId, Integer page, Integer pageSize, String couponStatus);

    /**
     *  获取我的优惠券总数
     * @param userId 用户ID
     * @param couponStatus 优惠券状态
     * @return Integer
     */
    Integer countMyCoupon(Integer userId,String couponStatus);
}
