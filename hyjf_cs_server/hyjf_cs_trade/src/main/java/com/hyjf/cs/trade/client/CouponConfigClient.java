/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;

/**
 * @author yaoy
 * @version CouponConfigClient, v0.1 2018/6/19 18:28
 */
public interface CouponConfigClient {
   CouponConfigVO selectCouponConfig(String couponCode);


   BestCouponListVO selectBestCoupon(MyCouponListRequest request);


   Integer countAvaliableCoupon(MyCouponListRequest request);

   /**
    * 查询汇计划最优优惠券
    * @param request
    * @return
    */
   BestCouponListVO selectHJHBestCoupon(MyCouponListRequest request);

   /**
    *
    * @param couponCode
    * @return
    */
   Integer checkCouponSendExcess(String couponCode);
   /**
    * 查询HJH可用优惠券数量
    * @param request
    * @return
    */
   Integer countHJHAvaliableCoupon(MyCouponListRequest request);
}
