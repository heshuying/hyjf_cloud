/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.alibaba.fastjson.JSONArray;
import com.hyjf.am.response.admin.HjhCommissionResponse;
import com.hyjf.am.resquest.admin.HjhCommissionRequest;
import com.hyjf.am.vo.admin.TenderCommissionVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;

/**
 * @author libin
 * @version HjhCommissionService.java, v0.1 2018年8月7日 下午2:44:47
 */
public interface HjhCommissionService {
	/**
	 * 汇计划提成列表查询
	 *
	 * @param instCodeSrch
	 * @return List<HjhAssetTypeVO>
	 */
	HjhCommissionResponse selectHjhCommissionList(HjhCommissionRequest form);
	
    /**
     * 查询金额总计 
     * @param id
     * @return
     */
	HjhCommissionResponse selecthjhCommissionTotal(HjhCommissionRequest form);
	
    /**
     * 汇计划提成列表-校验发提成状态是不是已经发放
     *
     * @param request
     * @param form
     * @return
     */
	TenderCommissionVO queryTenderCommissionByPrimaryKey(int ids);
	
	/**
	 * 获取部门列表
	 * 此方法后期可以做成基类的方法
	 * @return
	 */
	JSONArray getCrmDepartmentList(String[] list);
	
	/**
	 * 根据userId获取提成方式
	 * 此方法后期可以做成基类的方法
	 * @return
	 */
	Integer queryCrmCuttype(Integer userId);

	/**
	 * 发提成
	 * @return
	 */
	Integer updateTenderCommissionRecord(TenderCommissionVO commission, BankCallBean resultBean, ChinapnrBean chinapnrBean);
}
