/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderSumVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;

import java.util.List;

/**
 * @author libin
 * @version AdminHjhCreditTenderService.java, v0.1 2018年7月11日 下午3:16:44
 */
public interface AdminHjhCreditTenderService {
	/**
	 * COUNT
	 * 
	 * @param params
	 * @return
	 */
	Integer countHjhCreditTenderTotal(HjhCreditTenderRequest request);
	
	/**
	 * 获取详细列表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	List<HjhCreditTenderCustomizeVO> selectHjhCreditTenderList(HjhCreditTenderRequest request,int limitStart, int limitEnd);
	
	/**
	 * 获取详细列表未分页
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest request);
	
	/**
	 * @Author: libin
	 * @Desc :汇计划承接记录详情
	 */
	HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest request);
	
	
	/**
	* @Author: libin
	* @Desc :根据参数获取 HjhCreditTenderSumVO
	*/
	HjhCreditTenderSumVO getHjhCreditTenderCalcSumByParam(HjhCreditTenderRequest request);

}
