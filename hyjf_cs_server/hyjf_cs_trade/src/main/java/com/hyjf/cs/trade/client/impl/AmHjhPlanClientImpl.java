package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.cs.trade.client.AmHjhPlanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmHjhPlanClientImpl implements AmHjhPlanClient {


    @Autowired
    private RestTemplate restTemplate;


    @Override
    public PlanDetailCustomizeVO getPlanDetailByPlanNid(String planId) {
        HjhPlanDetailResponse response = restTemplate.getForEntity("",HjhPlanDetailResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }
}
