/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;

/**
 * @author libin
 * @version PlanListClient.java, v0.1 2018年7月6日 上午9:11:33
 */
public interface PlanListClient {
	
	/**
    * 获取计划列表
    * @return
    */
	HjhPlanResponse getHjhPlanListByParam(PlanListRequest form);
	
	/**
    * 获取计划列表 sum
    * @return
    */
	HjhPlanSumVO getCalcSumByParam(PlanListRequest form);
}
