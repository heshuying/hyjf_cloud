package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhAccedeResponse;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.cs.trade.client.AmHjhPlanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AmHjhPlanClientImpl implements AmHjhPlanClient {


    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HjhPlanVO getHjhPlan(String planNid) {
        com.hyjf.am.response.user.HjhPlanResponse response = restTemplate.getForEntity("http://AM-TRADE//am-trade/hjhPlan/gethjhplan/"+planNid,
                com.hyjf.am.response.user.HjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }

    @Override
    public HjhAccedeVO getHjhAccede(String orderId) {
        HjhAccedeResponse response = restTemplate.getForEntity("http://AM-TRADE//am-trade/hjhPlan/gethjhaccede/"+orderId,HjhAccedeResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResult();
        }
        return null;
    }


}
