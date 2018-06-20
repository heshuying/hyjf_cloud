/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.client.impl;

import com.hyjf.am.response.user.HjhInstConfigResponse;
import com.hyjf.am.vo.user.HjhInstConfigVO;
import com.hyjf.cs.user.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author zhangqingqing
 * @version AmTradeClientImpl, v0.1 2018/6/20 12:45
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhInstConfigVO selectInstConfigByInstCode(String instCode) {
        HjhInstConfigResponse response = restTemplate
                .getForEntity("http://AM-TRADE/am-trade/trade/selectInstConfigByInstCode/"+instCode, HjhInstConfigResponse.class)
                .getBody();
        if (response != null) {
            return response.getResult();
        }
        return null;
    }
}
