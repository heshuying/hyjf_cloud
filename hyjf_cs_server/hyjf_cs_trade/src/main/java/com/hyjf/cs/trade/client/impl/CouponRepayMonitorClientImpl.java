/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.CouponRepayMonitorResponse;
import com.hyjf.am.vo.trade.CouponRepayMonitorVO;
import com.hyjf.cs.trade.client.CouponRepayMonitorClient;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author yaoy
 * @version CouponRepayMonitorClientImpl, v0.1 2018/6/21 17:07
 */
@Service
public class CouponRepayMonitorClientImpl implements CouponRepayMonitorClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<CouponRepayMonitorVO> selectCouponRepayMonitor(String nowDay) {
        String url = "http://AM-TRADE/am-trade/couponRepayMonitor/selectCouponRepayMonitor/"+nowDay;
        CouponRepayMonitorResponse response = restTemplate.getForEntity(url,CouponRepayMonitorResponse.class).getBody();
        if (response != null) {
            return response.getResultList();
        }
        return null;
    }

    @Override
    public int insertCouponRepayMonitor(CouponRepayMonitorVO monitor) {
        String url = "http://AM-TRADE/am-trade/couponRepayMonitor/insertCouponRepayMonitor";
        CouponRepayMonitorResponse response = restTemplate.postForEntity(url,monitor, CouponRepayMonitorResponse.class).getBody();
        if (response != null) {
            return response.getInsertFlag();
        }
        return 0;
    }

    @Override
    public int updateCouponRepayMonitor(CouponRepayMonitorVO monitor) {
        String url = "http://AM-TRADE/am-trade/couponRepayMonitor/updateCouponRepayMonitor";
        CouponRepayMonitorResponse response = restTemplate.postForEntity(url,monitor,CouponRepayMonitorResponse.class).getBody();
        if (response != null) {
            return response.getUpdateFlag();
        }
        return 0;
    }
}
