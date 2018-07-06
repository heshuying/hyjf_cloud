/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hyjf.admin.client.PlanListClient;
import com.hyjf.admin.service.PlanListService;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
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

}
