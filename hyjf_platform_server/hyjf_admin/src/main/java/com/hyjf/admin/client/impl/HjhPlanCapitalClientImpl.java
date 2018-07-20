package com.hyjf.admin.client.impl;

import com.hyjf.admin.client.HjhPlanCapitalClient;
import com.hyjf.am.response.admin.HjhPlanCapitalResponse;
import com.hyjf.am.resquest.admin.HjhPlanCapitalRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@Service
public class HjhPlanCapitalClientImpl implements HjhPlanCapitalClient {

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取汇计划--计划资金列表
     * @param hjhPlanCapitalRequest
     * @return
     */
    @Override
    public HjhPlanCapitalResponse getPlanCapitalList(HjhPlanCapitalRequest hjhPlanCapitalRequest) {
        HjhPlanCapitalResponse response = restTemplate.postForEntity("http://AM-DATA-COLLECT/am-statistics/search/getPlanCapitalList",
                hjhPlanCapitalRequest, HjhPlanCapitalResponse.class).getBody();
        if (response != null){
            return response;
        }
        return null;
    }
}
