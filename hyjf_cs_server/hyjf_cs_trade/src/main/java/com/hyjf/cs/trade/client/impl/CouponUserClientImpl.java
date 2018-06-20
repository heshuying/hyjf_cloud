/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CouponUserResponse;
import com.hyjf.am.vo.trade.CouponUserVo;
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
    public List<CouponUserVo> selectCouponUser(int nowBeginDate, int nowEndDate) {
        CouponUserResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/couponUser/selectCouponUser/" + nowBeginDate + "/" + nowEndDate, CouponUserResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
