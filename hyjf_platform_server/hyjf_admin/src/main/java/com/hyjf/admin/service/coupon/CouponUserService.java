/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.coupon;

import com.hyjf.am.response.admin.AdminCouponUserCustomizeResponse;
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
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.admin.coupon.CouponTenderDetailVo;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    CouponUserResponse getCouponUserByCouponCode(String couponCode);

    CouponTenderDetailVo getCouponTenderDetailCustomize(Map<String, Object> paramMap);

    List<CouponRecoverVO> getCouponRecoverCustomize(Map<String, Object> paramMap);

    CouponUserVO selectCouponUserById(Integer couponUserId);

    CouponUserCustomizeResponse auditRecord(AdminCouponUserRequestBean adminCouponUserRequestBean);

    String uploadFile(HttpServletRequest request, HttpServletResponse response, String loginUserId) throws IOException;
}
