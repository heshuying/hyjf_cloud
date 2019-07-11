/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.HjhCreditTenderResponse;
import com.hyjf.am.resquest.admin.HjhCreditTenderRequest;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderCustomizeVO;
import com.hyjf.am.vo.trade.hjh.HjhCreditTenderSumVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;

import java.util.List;

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
	
	/**
	 * 传参查询承接债转表
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	HjhDebtCreditTenderVO selectHjhCreditTenderRecord(HjhCreditTenderRequest form);
	
	/**
	 * PDF下载加脱敏
	 * @param tenderAgreement
	 * @param borrowNid
	 * @param transType
	 * @param instCode
	 */
	void updateSaveSignInfo(TenderAgreementVO tenderAgreement,String borrowNid, Integer transType, String instCode);
	
	/**
	 * 传参查询承接债转表列总计
	 * 
	 * @param DebtCreditCustomize
	 * @return
	 */
	HjhCreditTenderSumVO getCalcSumByParam(HjhCreditTenderRequest form);

	/**
	 * list 分页
	 * @param request
	 * @param result
	 * @return
	 */
	List<HjhCreditTenderCustomizeVO> paging(HjhCreditTenderRequest request, List<HjhCreditTenderCustomizeVO> result);

    Response doCreditEnd(String orderId);
}
