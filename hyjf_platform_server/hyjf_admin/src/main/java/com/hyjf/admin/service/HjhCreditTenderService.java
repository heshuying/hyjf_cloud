/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import java.util.List;

import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;

/**
 * @author libin
 * @version HjhCreditTenderService.java, v0.1 2018年7月11日 下午2:23:39
 */
public interface HjhCreditTenderService {
	
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
}
