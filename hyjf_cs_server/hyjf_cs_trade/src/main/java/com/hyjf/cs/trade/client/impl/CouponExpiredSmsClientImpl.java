/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BatchCouponTimeoutCommonResponse;
import com.hyjf.am.vo.trade.BatchCouponTimeoutCommonCustomizeVO;
import com.hyjf.cs.trade.client.CouponExpiredSmsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yaoy
 * @version CouponExpiredSmsClientImpl, v0.1 2018/6/22 14:18
 */
@Service
public class CouponExpiredSmsClientImpl implements CouponExpiredSmsClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<BatchCouponTimeoutCommonCustomizeVO> selectCouponQuota(int threeBeginDate, int threeEndDate) {
        String url = "http://AM-TRADE/am-trade/couponRepay/selectCouponQuota/" + threeBeginDate + "/" + threeEndDate;
        BatchCouponTimeoutCommonResponse response = restTemplate.getForEntity(url, BatchCouponTimeoutCommonResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }
}
