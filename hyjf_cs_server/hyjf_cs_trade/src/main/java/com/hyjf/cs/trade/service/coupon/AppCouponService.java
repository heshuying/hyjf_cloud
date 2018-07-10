package com.hyjf.cs.trade.service.coupon;

import com.hyjf.am.vo.trade.coupon.CouponUserForAppCustomizeVO;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;
import java.util.Map;

/**
 * @author walter.limeng
 * @version CouponService, v0.1 2018/7/9 14:08
 */
public interface AppCouponService extends BaseTradeService {
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

    /**
     * 散标投资根据标的编号，投资金额等查询优惠券
     * @param userId 用户ID
     * @param borrowNid 标的编号
     * @param money 投资金额
     * @param platform 投资平台
     * @return Map<String,Object>
     */
    Map<String,Object> getBorrowCoupon(Integer userId, String borrowNid, String money, String platform);
}
