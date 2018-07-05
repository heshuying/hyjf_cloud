package com.hyjf.cs.trade.client;

import com.hyjf.am.vo.trade.coupon.CouponTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;

/**
 * @Description 优惠券相关
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/19 16:05
 */
public interface CouponClient {

	/**
	 * @Description 根据优惠券ID和用户ID查询优惠券
	 * @Author sunss
	 * @Version v0.1
	 * @Date 2018/6/19 16:20
	 */
	CouponUserVO getCouponUser(Integer couponGrantId, Integer userId);

	/**
	 * 优惠券投资
	 * @param couponTender
	 * @return
	 */
    boolean updateCouponTender(CouponTenderVO couponTender);
}
