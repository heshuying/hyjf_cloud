package com.hyjf.am.response.trade;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;

/**
 *
 * @author zhangyk
 * @date 2018/6/25 9:56
 */
public class MyBestCouponListResponse extends Response<BestCouponListVO> {

    private  Integer couponCount;  // 可用优惠券数目


    public Integer getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(Integer couponCount) {
        this.couponCount = couponCount;
    }
}
