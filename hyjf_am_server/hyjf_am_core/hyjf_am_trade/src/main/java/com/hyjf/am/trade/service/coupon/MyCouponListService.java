package com.hyjf.am.trade.service.coupon;

import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;

import java.util.List;

/**
 * @author hesy
 * @version MyCouponListService, v0.1 2018/6/22 20:04
 */
public interface MyCouponListService {
    List<MyCouponListCustomizeVO> selectUserCouponList(String userId, String usedFlag, Integer limitStart, Integer limitEnd);

    Integer countUserCouponList(String userId, String usedFlag);

    BestCouponListVO selectBestCouponList(MyCouponListRequest request);

    Integer countAvaliableCoupon(MyCouponListRequest request);
}
