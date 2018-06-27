/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.cs.trade.client.BorrowTenderCpnClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author yaoy
 * @version BorrowTenderCpnClientImpl, v0.1 2018/6/25 15:45
 */
@Service
public class BorrowTenderCpnClientImpl implements BorrowTenderCpnClient {

    @Autowired
    RestTemplate restTemplate;

    /**
     * 根据优惠券查询投资信息
     * @param couponTenderNid
     * @return
     */
    @Override
    public BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid) {
        String url = "http://AM-TRADE/am-trade/batch/getCouponTenderInfo/"+couponTenderNid;
        BorrowTenderCpnResponse response = restTemplate.getForEntity(url,BorrowTenderCpnResponse.class).getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
