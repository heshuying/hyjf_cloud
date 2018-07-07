/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.hyjf.am.response.Response;
import com.hyjf.admin.client.PlanListClient;
import com.hyjf.am.response.admin.HjhPlanDetailResponse;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.response.admin.HjhPlanSumResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
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

	@Override
	public List<HjhPlanDetailVO> getHjhPlanDetailByPlanNid(PlanListRequest form) {
		HjhPlanDetailResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getHjhPlanDetailByPlanNid", form,HjhPlanDetailResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response.getResultList();
        }
		return null;
	}

	@Override
	public HjhPlanResponse getPlanNameAjaxCheck(PlanListRequest form) {
		HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getPlanNameAjaxCheck", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public HjhPlanResponse getPlanNidAjaxCheck(PlanListRequest form) {
		HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/getPlanNidAjaxCheck", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public HjhPlanResponse updatePlanStatusByPlanNid(PlanListRequest form) {
		HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/updatePlanStatusByPlanNid", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
		return null;
	}

	@Override
	public HjhPlanResponse updatePlanDisplayByPlanNid(PlanListRequest form) {
		HjhPlanResponse response = restTemplate
                .postForEntity("http://AM-TRADE/am-trade/planList/updatePlanDisplayByPlanNid", form, HjhPlanResponse.class).getBody();
        if (response != null && Response.SUCCESS.equals(response.getRtn())) {
            return response;
        }
        return null;
	}

	@Override
	public boolean isExistsRecord(String planNid) {
		String url = "http://AM-TRADE/am-trade/planList/isExistsRecord/" + planNid;
		boolean Flag = restTemplate.getForEntity(url,Boolean.class).getBody();
		return Flag;
	}

	@Override
	public int countByPlanName(String planName) {
		String url = "http://AM-TRADE/am-trade/planList/countByPlanName/" + planName;
		Integer count = restTemplate.getForEntity(url,Integer.class).getBody();
		return count;
	}

	@Override
	public int isLockPeriodExist(String lockPeriod, String borrowStyle, String isMonth) {
		String url = "http://AM-TRADE/am-trade/planList/isLockPeriodExist/" + lockPeriod + "/" + borrowStyle + "/" + isMonth;
		Integer count = restTemplate.getForEntity(url,Integer.class).getBody();
		return count;
	}

	@Override
	public int updateRecord(PlanListRequest form) {
		String url = "http://AM-TRADE/am-trade/planList/updateRecord";
		Integer Flag = restTemplate.postForEntity(url,form,Integer.class).getBody();
		return Flag;
	}

	@Override
	public int insertRecord(PlanListRequest form) {
		String url = "http://AM-TRADE/am-trade/planList/insertRecord";
		Integer Flag = restTemplate.postForEntity(url,form,Integer.class).getBody();
		return Flag;
	}
}
