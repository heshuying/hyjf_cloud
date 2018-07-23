package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HjhReInvestDebtClient;
import com.hyjf.am.response.admin.HjhReInvestDebtResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDebtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HjhReInvestDebtClientImpl implements HjhReInvestDebtClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhReInvestDebtResponse hjhReInvestDebtList(HjhReInvestDebtRequest request) {
        HjhReInvestDebtResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhReInvestDebt/hjhReInvestDebtList", request, HjhReInvestDebtResponse.class).getBody();

        if (response != null){
            return  response;
        }
        return null;
    }
}
