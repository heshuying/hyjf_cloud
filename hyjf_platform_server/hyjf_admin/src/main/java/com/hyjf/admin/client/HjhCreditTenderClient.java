/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import java.util.List;

import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;

/**
 * @author libin
 * @version HjhCreditTenderClient.java, v0.1 2018年7月11日 下午3:04:13
 */
public interface HjhCreditTenderClient {
	/**
	 * 获取详细列表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	HjhCreditTenderResponse getHjhCreditTenderListByParam(HjhCreditTenderRequest form);
	
	/**
	 * 获取详细列表未分页
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	List<HjhCreditTenderCustomizeVO> getHjhCreditTenderListByParamWithOutPage(HjhCreditTenderRequest form);
	
	/**
	 * 传参查询承接债转表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest form);

}
