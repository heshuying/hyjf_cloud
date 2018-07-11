/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.admin.hjhplan;

import java.util.List;

import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;

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

}
