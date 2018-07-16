package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.HjhPlanResponse;
import com.hyjf.am.response.trade.HjhPlanDetailResponse;
import com.hyjf.am.resquest.trade.HjhPlanRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanCustomizeVO;
import com.hyjf.am.vo.trade.hjh.PlanDetailCustomizeVO;
import com.hyjf.cs.trade.client.AmHjhPlanClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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


    /**
     * 获取app首页的汇计划列表
     * @author zhangyk
     * @date 2018/7/9 11:19
     */
    @Override
    public List<HjhPlanCustomizeVO> getAppHomePlanList(HjhPlanRequest request) {
        HjhPlanResponse response = restTemplate.postForEntity("http://AM-TRADE//am-trade/hjhPlan/selectAppHjhPlanList",request,HjhPlanResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getResultList();
        }
        return null;
    }


}
