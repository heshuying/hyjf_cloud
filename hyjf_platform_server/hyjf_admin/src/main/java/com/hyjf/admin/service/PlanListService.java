/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.admin.HjhPlanResponse;
import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanSumVO;

/**
 * @author libin
 * @version PlanListService.java, v0.1 2018年7月6日 上午9:09:16
 */
public interface PlanListService {
	/**
     * 获取计划列表
     * @return
     */
	HjhPlanResponse getHjhPlanListByParam(PlanListRequest form);

	/**
     * 获取计划列表sum 
     * @return
     */
	HjhPlanSumVO getCalcSumByParam(PlanListRequest form);  
	
	/**
     * 获取计划详情列表
     * @return
     */
	List<HjhPlanDetailVO> getHjhPlanDetailByPlanNid(PlanListRequest form);
	
	/**
     * AJAX
     * @return
     */
	HjhPlanResponse getPlanNameAjaxCheck(PlanListRequest form);
	
	/**
     * AJAX
     * @return
     */
	HjhPlanResponse getPlanNidAjaxCheck(PlanListRequest form);
	
	/**
     * 修改计划状态
     * @return
     */
	HjhPlanResponse updatePlanStatusByPlanNid(PlanListRequest form);
	
	/**
     * 修改计划显示状态
     * @return
     */
	HjhPlanResponse updatePlanDisplayByPlanNid(PlanListRequest form);
	/**
	 * 根据主键判断数据是否存在
	 * 
	 * @Title isExistsRecord
	 * @param planNid
	 * @return
	 */
	boolean isExistsRecord(String planNid);
	
	/**
	 * 根据计划名称查询数量
	 * 
	 * @Title isExistsRecord
	 * @param planNid
	 * @return
	 */
	int countByPlanName(String planName);
	
	/**
	 * 根据计划还款方式，锁定期，锁定期类型获取计划数量(月)
	 * 
	 * @Title isDebtPlanTypeNameExist
	 * @param debtPlanTypeName
	 * @return
	 */
	int isLockPeriodExist(String lockPeriod,String borrowStyle,String isMonth);
	
	/**
	 * 更新操作
	 * 
	 * @Title updateRecord
	 * @param planListBean
	 * @throws Exception
	 */
	int updateRecord(PlanListRequest form) ;
	
	/**
	 * 插入操作
	 * 
	 * @param record
	 */
	int insertRecord(PlanListRequest form);
}
