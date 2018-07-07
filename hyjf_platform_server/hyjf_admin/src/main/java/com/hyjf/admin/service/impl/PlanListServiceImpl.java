/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.PlanListClient;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;

/**
 * @author libin
 * @version PlanListServiceImpl.java, v0.1 2018年7月6日 上午9:10:05
 */
@Service
public class PlanListServiceImpl implements PlanListService{
	
    @Autowired
    private PlanListClient planListClient;

	@Override
	public HjhPlanResponse getHjhPlanListByParam(PlanListRequest form) {
		HjhPlanResponse response = planListClient.getHjhPlanListByParam(form);
		return response;
	}

	@Override
	public HjhPlanSumVO getCalcSumByParam(PlanListRequest form) {
		HjhPlanSumVO vo = planListClient.getCalcSumByParam(form);
		return vo;
	}

	@Override
	public List<HjhPlanDetailVO> getHjhPlanDetailByPlanNid(PlanListRequest form) {
		List<HjhPlanDetailVO> list = planListClient.getHjhPlanDetailByPlanNid(form);
		return list;
	}

	@Override
	public HjhPlanResponse getPlanNameAjaxCheck(PlanListRequest form) {
		HjhPlanResponse response = planListClient.getPlanNameAjaxCheck(form);
		return response;
	}

	@Override
	public HjhPlanResponse getPlanNidAjaxCheck(PlanListRequest form) {
		HjhPlanResponse response = planListClient.getPlanNidAjaxCheck(form);
		return response;
	}

	@Override
	public HjhPlanResponse updatePlanStatusByPlanNid(PlanListRequest form) {
		HjhPlanResponse response = planListClient.updatePlanStatusByPlanNid(form);
		return response;
	}

	@Override
	public HjhPlanResponse updatePlanDisplayByPlanNid(PlanListRequest form) {
		HjhPlanResponse response = planListClient.updatePlanDisplayByPlanNid(form);
		return response;
	}

	@Override
	public boolean isExistsRecord(String planNid) {
		boolean flg = planListClient.isExistsRecord(planNid);
		return flg;
	}

	@Override
	public int countByPlanName(String planName) {
		int count = planListClient.countByPlanName(planName);
		return count;
	}

	@Override
	public int isLockPeriodExist(String lockPeriod, String borrowStyle, String isMonth) {
		int count = planListClient.isLockPeriodExist(lockPeriod,borrowStyle,isMonth);
		return count;
	}

	@Override
	public int updateRecord(PlanListRequest form) {
		int count = planListClient.updateRecord(form);
		return count;
	}

	@Override
	public int insertRecord(PlanListRequest form) {
		int count = planListClient.insertRecord(form);
		return count;
	}
}
