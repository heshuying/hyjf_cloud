/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;
import com.hyjf.am.vo.user.UserVO;

import java.util.List;

/**
 * @author yaoyong
 * @version CouponUserClient, v0.1 2018/7/23 16:43
 */
public interface CouponUserClient {
    CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest);

    CouponUserCustomizeResponse deleteById(int id, String remark, String userId);

    List<CouponConfigCustomizeVO> getCouponConfig(CouponConfigRequest request);

    List<ActivityListCustomizeVO> getActivityList(ActivityListRequest request);

    UserResponse getUserByUserName(String userName);

    UserInfoResponse getUserInfoByUserId(Integer userId);

    UtmResponse getChannelNameByUserId(Integer userId);

    CouponConfigResponse selectCouponConfig(String couponCode);

    CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest);
}
