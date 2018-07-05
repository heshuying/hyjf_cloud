/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import java.util.List;
import com.hyjf.am.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.client.AllocationEngineClient;
import com.hyjf.am.response.admin.HjhRegionResponse;
import com.hyjf.am.resquest.admin.AllocationEngineRuquest;
import com.hyjf.am.vo.admin.HjhRegionVO;

/**
 * @author libin
 * @version AllocationEngineClientImpl.java, v0.1 2018年7月3日 下午12:57:32
 */
@Service
public class AllocationEngineClientImpl implements AllocationEngineClient{
	@Autowired
	private RestTemplate restTemplate;
	
    /**
     * 查询计划专区列表
     * @param request
     * @return
     */
	@Override
	public List<HjhRegionVO> getHjhRegionList(AllocationEngineRuquest form) {
		HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/selectHjhRegionList", form, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }			
		return null;
	}

	@Override
	public String getPlanNameByPlanNid(AllocationEngineRuquest form) {
		HjhRegionResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/allocation/selectPlanNameByPlanNid", form, HjhRegionResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getPlanName();
        }	
		return null;
	}

	@Override
	public int insertRecord(HjhRegionVO request) {
		String url = "http://AM-TRADE/am-trade/allocation/insertRecord";
		Integer Flag = restTemplate.postForEntity(url,request,Integer.class).getBody();
		return Flag;
	}
}
