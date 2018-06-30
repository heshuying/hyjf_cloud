/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.HjhAccedeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditClientImpl, v0.1 2018/6/26 11:59
 */
@Service
public class HjhAccedeClientImpl implements HjhAccedeClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhAccedeVO getHjhAccedeByAccedeOrderId(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/hjhAccede/getHjhAccedeByAccedeOrderId/"+accedeOrderId;
        HjhAccedeResponse response = restTemplate.getForEntity(url,HjhAccedeResponse.class).getBody();
        if (Validator.isNotNull(response)){
             return response.getResult();
        }
        return null;
    }
}
