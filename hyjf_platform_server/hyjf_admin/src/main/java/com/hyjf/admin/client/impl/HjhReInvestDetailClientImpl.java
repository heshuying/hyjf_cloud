package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HjhReInvestDetailClient;
import com.hyjf.am.response.admin.HjhReInvestDetailResponse;
import com.hyjf.am.resquest.admin.HjhReInvestDetailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class HjhReInvestDetailClientImpl implements HjhReInvestDetailClient {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhReInvestDetailResponse getHjhReInvestDetailList(HjhReInvestDetailRequest request) {
        HjhReInvestDetailResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/hjhPlanCapital/hjhPlanCapitalReinvestInfo", request, HjhReInvestDetailResponse.class).getBody();

        if (response != null){
            return response;
        }
        return null;
    }
}
