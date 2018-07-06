/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;

/**
 * @author libin
 * @version AdminHjhPlanService.java, v0.1 2018年7月6日 上午10:07:22
 */
public interface AdminHjhPlanService {
	/**
	 * @method: countHjhPlanListTotal
	 * @description: 计划数量查询
	 * @return: int
	 * @mender: LIBIN
	 * @date: 2017年8月11日
	 */
	Integer countHjhPlanListTotal(PlanListRequest request);
	
	/**
	 * 计划列表
	 * @return
	 */
	List<HjhPlanVO> selectHjhPlanList(PlanListRequest request, int limitStart, int limitEnd);
	
	/**
	 * 计划列表sum
	 * @return
	 */
	HjhPlanSumVO getCalcSumByParam(PlanListRequest request);
}
