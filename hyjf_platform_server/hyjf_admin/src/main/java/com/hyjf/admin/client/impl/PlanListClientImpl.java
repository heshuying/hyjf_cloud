/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hyjf.am.response.Response;
import com.hyjf.admin.client.PlanListClient;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.response.admin.HjhPlanSumResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;

/**
 * @author libin
 * @version PlanListClientImpl.java, v0.1 2018年7月6日 上午9:12:10
 */
@Service
public class PlanListClientImpl implements PlanListClient{
    @Autowired
    private RestTemplate restTemplate;

	@Override
	public HjhPlanResponse getHjhPlanListByParam(PlanListRequest form) {
		HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getHjhPlanListByParam", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public HjhPlanSumVO getCalcSumByParam(PlanListRequest form) {
		HjhPlanSumResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getCalcSumByParam", form,HjhPlanSumResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResult();
        }
		return null;
	}
}
