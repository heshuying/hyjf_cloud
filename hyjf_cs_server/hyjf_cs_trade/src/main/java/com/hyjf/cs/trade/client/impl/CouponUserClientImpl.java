/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.response.user.UserInfoResponse;
import com.hyjf.am.response.user.UserResponse;
import com.hyjf.am.resquest.trade.CouponUserSearchRequest;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.cs.trade.client.CouponUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yaoy
 * @version CouponUserClientImpl, v0.1 2018/6/19 18:31
 */
@Service
public class CouponUserClientImpl implements CouponUserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CouponUserVO> selectCouponUser(int nowBeginDate, int nowEndDate) {
        CouponUserResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponUser/selectCouponUser/" + nowBeginDate + "/" + nowEndDate, CouponUserResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public Integer getUserCouponCount(Integer userId, String usedFlag) {
        CouponUserResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponUser/user_coupon_count/" + userId + "/" + usedFlag, CouponUserResponse.class).getBody();
        if (Response.isSuccess(response)) {
            return response.getCount();
        }
        return null;
    }

    @Override
    public UserVO getUser(String userName) {
        UserResponse response = restTemplate.getForEntity("http://AM-USER/am-user/user/findByCondition/"+userName,UserResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public UserInfoVO getUserInfo(Integer userId) {
        UserInfoResponse response = restTemplate.getForEntity("http://AM-USER/am-user/userInfo/findById/"+userId,UserInfoResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }

    @Override
    public String selectChannelNameByUserId(Integer userId) {
        String chanelName = restTemplate.getForEntity("http://AM-USER/am-user/channel/selectChannelNameById/"+userId,String.class).getBody();
        return chanelName;
    }

    @Override
    public Integer insertCouponUser(CouponUserVO couponUser) {
        String url = "http://AM-TRADE/am-trade/couponUser/insertCouponUser";
        CouponUserResponse response = restTemplate.postForEntity(url,couponUser,CouponUserResponse.class).getBody();
        return response.getCount();
    }

    @Override
    public boolean getSendRepeat(CouponUserSearchRequest couponUserRequest) {
        String url = "http://AM-TRADE/am-trade/couponUser/getsendrepeat";
        CouponUserResponse response = restTemplate.postForEntity(url,couponUserRequest,CouponUserResponse.class).getBody();
        return response.getIsSend();
    }


}
