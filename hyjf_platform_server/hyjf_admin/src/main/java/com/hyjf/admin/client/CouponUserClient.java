/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.CouponTenderResponse;
import com.hyjf.am.response.admin.CouponUserCustomizeResponse;
import com.hyjf.am.response.admin.UtmResponse;
import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.admin.AdminCouponUserRequestBean;
import com.hyjf.am.resquest.admin.CouponConfigRequest;
import com.hyjf.am.resquest.admin.CouponUserBeanRequest;
import com.hyjf.am.resquest.admin.CouponUserRequest;
import com.hyjf.am.resquest.market.ActivityListRequest;
import com.hyjf.am.vo.admin.ActivityListCustomizeVO;
import com.hyjf.am.vo.admin.CouponConfigCustomizeVO;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version CouponUserClient, v0.1 2018/7/23 16:43
 */
public interface CouponUserClient {
    CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest);

    CouponUserCustomizeResponse deleteById(int id, String remark, String userId);

    List<CouponConfigCustomizeVO> getCouponConfig(CouponConfigRequest request);

    List<ActivityListCustomizeVO> getActivityList(ActivityListCustomizeVO request);

    UserResponse getUserByUserName(String userName);

    UserInfoResponse getUserInfoByUserId(Integer userId);

    UtmResponse getChannelNameByUserId(Integer userId);

    CouponConfigResponse selectCouponConfig(String couponCode);

    CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest);

    CouponUserResponse getCouponUserByCouponCode(String couponCode);

    CouponTenderResponse getCouponTenderDetailCustomize(Map<String,Object> paramMap);

    CouponTenderResponse getCouponRecoverCustomize(Map<String, Object> paramMap);

    CouponUserCustomizeResponse selectCouponUserById(Integer couponUserId);

    CouponUserCustomizeResponse auditRecord(AdminCouponUserRequestBean adminCouponUserRequestBean);

    JSONObject getBatchCoupons(Map<String, String> params);
}
