/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.response.trade.HjhDebtCreditResponse;
import com.hyjf.am.response.trade.HjhDebtCreditTenderResponse;
import com.hyjf.am.resquest.trade.HjhDebtCreditRequest;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.HjhAccedeClient;
import com.hyjf.cs.trade.client.HjhDebtCreditClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhDebtCreditClientImpl, v0.1 2018/6/26 11:59
 */
@Service
public class HjhAccedeClientImpl implements HjhAccedeClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<HjhAccedeVO> getHjhAccedeListByAccedeOrderId(String accedeOrderId) {
        String url = "http://AM-TRADE/am-trade/hjhAccede/getHjhAccedeListByAccedeOrderId/"+accedeOrderId;
        HjhAccedeResponse response = restTemplate.getForEntity(url,HjhAccedeResponse.class).getBody();
        if (Validator.isNotNull(response)){
             return response.getResultList();
        }
        return null;
    }
}
