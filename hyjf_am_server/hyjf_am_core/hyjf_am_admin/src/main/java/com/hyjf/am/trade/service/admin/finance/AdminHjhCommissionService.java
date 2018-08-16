/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.finance;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.admin.CommissionComboRequest;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.am.vo.trade.hjh.HjhCommissionCustomizeVO;

/**
 * @author libin
 * @version AdminHjhCommissionService.java, v0.1 2018年8月7日 下午4:44:24
 */
public interface AdminHjhCommissionService {
	
    /**
     * 按照筛选条件查询数据条数
     * @param request 筛选条件
     * @return
     */
	Integer countTotal(HjhCommissionRequest request);
	
    /**
     * 根据筛选条件查询list
     * @param request 筛选条件
     * @return
     */
	List<HjhCommissionCustomizeVO> selectHjhCommissionList(HjhCommissionRequest request,int limitStart,int limitEnd);
	
    /**
     * 查询金额总计 
     * @param id
     * @return
     */
	Map<String , Object> queryPushMoneyTotle(HjhCommissionRequest request,int limitStart,int limitEnd);
	
    /**
     * 查询汇计划提成是否已经发放
     * @param id
     * @return
     */
	TenderCommissionVO queryTenderCommissionByPrimaryKey(int ids);
	
	/**
	 * @Author: libin
	 * @Desc :获取部门列表    
	 */
	List<OADepartmentCustomizeVO> getCrmDepartmentList(HjhCommissionRequest request);
	
    /**
     * 根据用户id查询其在crm中的员工属性
     * @param id
     * @return
     */
	Integer queryCrmCuttype(Integer userId);
	
    /**
     * 发提成
     * @param id
     * @return
     */
	Integer updateTenderCommissionRecord(CommissionComboRequest request);
}
