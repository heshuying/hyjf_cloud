/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;

import com.hyjf.am.resquest.admin.PlanListRequest;
import com.hyjf.am.vo.trade.hjh.HjhPlanDetailVO;
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
	
	/**
	 * 计划详情列表
	 * @return
	 */
	List<HjhPlanDetailVO> selectHjhPlanDetailByPlanNid(PlanListRequest request);
	
	/**
	 * AJAX
	 * @return
	 */
	int isDebtPlanNameExist(String planName);
	
	/**
	 * AJAX
	 * @return
	 */
	int isDebtPlanNidExist(String planNid);
	
	/**
	 * 更新计划状态
	 * @return
	 */
	int updatePlanStatusByPlanNid(PlanListRequest request);
	
	/**
	 * 更新计划显示状态
	 * @return
	 */
	int updatePlanDisplayByPlanNid(PlanListRequest request);
	
	/**
	 * 通过主键校验存在
	 * @return
	 */
	boolean isExistsRecord(String planNid);
	
	/**
	 * 通过主键校验存在
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
	int updateRecord(PlanListRequest form) throws Exception;
	
	/**
	 * 插入操作
	 * 
	 * @param record
	 */
	int insertRecord(PlanListRequest form) throws Exception;
	
	/**
	 * 计划列表无分页
	 * @return
	 */
	List<HjhPlanVO> selectHjhPlanListWithoutPage(PlanListRequest request);
}
