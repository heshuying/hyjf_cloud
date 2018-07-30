/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl.coupon;

import com.hyjf.admin.client.CouponUserClient;
import com.hyjf.admin.service.coupon.CouponUserService;
import com.hyjf.am.response.admin.AdminCouponUserCustomizeResponse;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yaoyong
 * @version CouponUserServiceImpl, v0.1 2018/7/23 16:10
 */
@Service
public class CouponUserServiceImpl implements CouponUserService {

    @Autowired
    private CouponUserClient couponUserClient;


    /**
     * 获取优惠券用户列表
     *
     * @param couponUserBeanRequest
     * @return
     */
    @Override
    public CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest) {
        return couponUserClient.searchList(couponUserBeanRequest);
    }

    /**
     * 根据id删除一条优惠券
     *
     * @param id
     * @return
     */
    @Override
    public CouponUserCustomizeResponse deleteById(int id, String remark, String userId) {
        return couponUserClient.deleteById(id, remark, userId);
    }

    /**
     * 获取优惠券配置
     * @param request
     * @return
     */
    @Override
    public AdminCouponUserCustomizeResponse getRecordList(CouponConfigRequest request) {
        AdminCouponUserCustomizeResponse response = new AdminCouponUserCustomizeResponse();
        List<CouponConfigCustomizeVO> adminCouponUserCustomizeVOS = couponUserClient.getCouponConfig(request);
        List<ActivityListCustomizeVO> activityListCustomizeVOS = couponUserClient.getActivityList(new ActivityListRequest());
        response.getResult().setCouponConfigCustomizeVOS(adminCouponUserCustomizeVOS);
        response.getResult().setActivityListCustomizeVOS(activityListCustomizeVOS);
        return response;
    }

    /**
     * 根据用户名获取用户
     * @param userName
     * @return
     */
    @Override
    public UserResponse getUser(String userName) {
        return couponUserClient.getUserByUserName(userName);
    }

    /**
     * 根据用户id获取用户详情信息
     * @param userId
     * @return
     */
    @Override
    public UserInfoResponse getUserInfo(Integer userId) {
        return couponUserClient.getUserInfoByUserId(userId);
    }

    /**
     * 获取注册时的用户渠道
     * @param userId
     * @return
     */
    @Override
    public UtmResponse getChannelName(Integer userId) {
        return couponUserClient.getChannelNameByUserId(userId);
    }

    /**
     * 根据优惠券编码查询优惠券
     * @param couponCode
     * @return
     */
    @Override
    public CouponConfigResponse getCouponConfig(String couponCode) {
        return couponUserClient.selectCouponConfig(couponCode);
    }

    /**
     * 发放一条优惠券
     * @param couponUserRequest
     * @return
     */
    @Override
    public CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest) {
        return couponUserClient.insertCouponUser(couponUserRequest);
    }


}
