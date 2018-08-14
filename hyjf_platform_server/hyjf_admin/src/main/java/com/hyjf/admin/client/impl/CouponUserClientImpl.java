/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.CouponUserClient;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.*;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author yaoyong
 * @version CouponUserClientImpl, v0.1 2018/7/23 16:43
 */
@Service
public class CouponUserClientImpl implements CouponUserClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取优惠券用户列表
     *
     * @param couponUserBeanRequest
     * @return
     */
    @Override
    public CouponUserCustomizeResponse searchList(CouponUserBeanRequest couponUserBeanRequest) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/getCouponUserList";
        CouponUserCustomizeResponse response = restTemplate.postForEntity(url, couponUserBeanRequest, CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据id删除一条优惠券
     *
     * @param id
     * @return
     */
    @Override
    public CouponUserCustomizeResponse deleteById(int id, String remark, String userId) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/deleteCouponUser/" + id + remark + userId;
        CouponUserCustomizeResponse response = restTemplate.getForEntity(url, CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }


    /**
     * 获取优惠券配置列表
     *
     * @param request
     * @return
     */
    @Override
    public List<CouponConfigCustomizeVO> getCouponConfig(CouponConfigRequest request) {
        String url = "http://AM-TRADE/am-trade/couponConfig/adminCouponConfig";
        CouponConfigCustomizeResponse response = restTemplate.postForEntity(url, request, CouponConfigCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 获取有效活动列表
     *
     * @param request
     * @return
     */
    @Override
    public List<ActivityListCustomizeVO> getActivityList(ActivityListCustomizeVO request) {
        String url = "http://AM-MARKET/am-market/activity/selectRecordListValid";
        ActivityListCustomizeResponse response = restTemplate.postForEntity(url, request, ActivityListCustomizeResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    /**
     * 根据用户名获取用户
     *
     * @param userName
     * @return
     */
    @Override
    public UserResponse getUserByUserName(String userName) {
        String url = "http://AM-USER/am-user/user/findByCondition/" + userName;
        UserResponse userResponse = restTemplate.getForEntity(url, UserResponse.class).getBody();
        if (userResponse != null) {
            return userResponse;
        }
        return null;
    }

    /**
     * 根据用户id获取用户详情信息
     *
     * @param userId
     * @return
     */
    @Override
    public UserInfoResponse getUserInfoByUserId(Integer userId) {
        String url = "http://AM-USER/am-user/userInfo/findById/" + userId;
        UserInfoResponse userInfoResponse = restTemplate.getForEntity(url, UserInfoResponse.class).getBody();
        if (userInfoResponse != null) {
            return userInfoResponse;
        }
        return null;
    }

    /**
     * 获取注册时的用户渠道
     *
     * @param userId
     * @return
     */
    @Override
    public UtmResponse getChannelNameByUserId(Integer userId) {
        String url = "http://AM-USER/am-user/channel/getchannelnamebyuserd/" + userId;
        UtmResponse utmResponse = restTemplate.getForEntity(url, UtmResponse.class).getBody();
        if (utmResponse != null) {
            return utmResponse;
        }
        return null;
    }

    /**
     * 根据优惠券编码查询优惠券
     *
     * @param couponCode
     * @return
     */
    @Override
    public CouponConfigResponse selectCouponConfig(String couponCode) {
        String url = "http://AM-TRADE/am-trade/couponConfig/selectCouponConfig/" + couponCode;
        CouponConfigResponse configResponse = restTemplate.getForEntity(url, CouponConfigResponse.class).getBody();
        if (configResponse != null) {
            return configResponse;
        }
        return null;
    }

    /**
     * 发放一条优惠券
     *
     * @param couponUserRequest
     * @return
     */
    @Override
    public CouponUserResponse insertCouponUser(CouponUserRequest couponUserRequest) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/insertCouponUser";
        CouponUserResponse response = restTemplate.postForEntity(url, couponUserRequest, CouponUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 根据优惠券编码查询用户优惠券
     * @param couponCode
     * @return
     */
    @Override
    public CouponUserResponse getCouponUserByCouponCode(String couponCode) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/getCouponUsrByCouponCode/" + couponCode;
        CouponUserResponse response = restTemplate.getForEntity(url,CouponUserResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     *根据条件查询优惠券使用详情
     * @param paramMap
     * @return
     */
    @Override
    public CouponTenderResponse getCouponTenderDetailCustomize(Map<String,Object> paramMap) {
        String url = "http://AM-TRADE/am-trade/coupon/tender/hztcoupontenderdetail";
        CouponTenderResponse response = restTemplate.postForEntity(url,paramMap,CouponTenderResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 查询回款列表
     * @param paramMap
     * @return
     */
    @Override
    public CouponTenderResponse getCouponRecoverCustomize(Map<String, Object> paramMap) {
        CouponTenderResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/coupon/tender/hztcouponrecover", paramMap, CouponTenderResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
    }

    /**
     * 根据用户优惠券id查询用户优惠券详情
     * @param couponUserId
     * @return
     */
    @Override
    public CouponUserCustomizeResponse selectCouponUserById(Integer couponUserId) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/selectCouponUserById/"+couponUserId;
        CouponUserCustomizeResponse response = restTemplate.getForEntity(url,CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     * 用户优惠券审批
     * @param adminCouponUserRequestBean
     * @return
     */
    @Override
    public CouponUserCustomizeResponse auditRecord(AdminCouponUserRequestBean adminCouponUserRequestBean) {
        String url = "http://AM-TRADE/am-trade/adminCouponUser/auditRecord";
        CouponUserCustomizeResponse response = restTemplate.postForEntity(url,adminCouponUserRequestBean,CouponUserCustomizeResponse.class).getBody();
        if (response != null) {
            return response;
        }
        return null;
    }

    /**
     *批量上传发券
     * @param params
     * @return
     */
    @Override
    public JSONObject getBatchCoupons(Map<String, String> params) {
        String url = "http://AM-TRADE/am-trade/checkCoupon/getBatchCoupons";
        return restTemplate.postForEntity(url,params,JSONObject.class).getBody();
    }


}
