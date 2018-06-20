/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CouponConfigResponse;
import com.hyjf.am.vo.trade.CouponConfigVo;
import com.hyjf.cs.trade.client.CouponConfigClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yaoy
 * @version CouponConfigClientImpl, v0.1 2018/6/19 18:32
 */
@Service
public class CouponConfigClientImpl implements CouponConfigClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public CouponConfigVo selectCouponConfig(String couponCode) {
        CouponConfigResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponConfig/selectCouponConfig/" + couponCode, CouponConfigResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
