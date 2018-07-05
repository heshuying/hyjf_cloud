/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.trade.CouponUserListCustomizeResponse;
import com.hyjf.am.vo.trade.coupon.CouponUserListCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmCouponUserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * @author jun
 * @version AmCouponUserClientImpl, v0.1 2018/7/3 18:12
 */

@Service
public class AmCouponUserClientImpl implements AmCouponUserClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Integer countCouponValid(Integer userId) {
        String url = "http://AM-TRADE/am-trade/couponUser/countCouponValid/" + userId;
        return restTemplate.getForEntity(url, Integer.class).getBody();
    }

    @Override
    public List<CouponUserListCustomizeVO> selectCouponUserList(Map<String, Object> mapParameter) {
        String url = "http://AM-TRADE/am-trade/couponUser/selectCouponUserList";
        CouponUserListCustomizeResponse response = restTemplate.postForEntity(url,mapParameter,CouponUserListCustomizeResponse.class).getBody();
        if (Validator.isNotNull(response)){
            return response.getResultList();
        }
        return null;
    }

}
