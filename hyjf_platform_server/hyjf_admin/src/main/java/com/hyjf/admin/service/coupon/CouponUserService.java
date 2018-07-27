/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.coupon;

import com.hyjf.am.response.admin.AdminCouponUserCustomizeResponse;
import com.hyjf.am.response.admin.CouponConfigCustomizeResponse;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.vo.user.UserVO;

/**
 * @author yaoyong
 * @version CouponUserService, v0.1 2018/7/23 16:09
 */
public interface CouponUserService {
    CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest);

    CouponUserCustomizeResponse deleteById(int id, String remark, String userId);

    AdminCouponUserCustomizeResponse getRecordList(CouponConfigRequest request);

    UserResponse getUser(String userName);

    UserInfoResponse getUserInfo(Integer userId);

    UtmResponse getChannelName(Integer userId);

    CouponConfigResponse getCouponConfig(String couponCode);

    CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest);
}
