/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.newagreement;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * @author libin
 * @version NewAgreementService.java, v0.1 2018年7月25日 下午2:21:43
 */
public interface NewAgreementService extends BaseTradeService{
	
	/**
	 * 获取债转承接信息
	 * @param nid
	 * @return
	 */
	HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid);
	
	/**
	 * 查询协议表by assignNid(实际上是tenderNID)
	 * @param nid
	 * @return
	 */
	List<TenderAgreementVO> getTenderAgreementByTenderNid(String assignNid);
	
	/**
	 * 查询用户详情
	 * @param nid
	 * @return
	 */
	UserInfoVO getUsersInfoByUserId(Integer userId);
	
	/**
	 * 根据borrowNid获取标的
	 * @param nid
	 * @return
	 */
	BorrowVO getBorrowByNid(String borrowNid);
	
	/**
	 * 根据creditNid获取债转信息
	 * @param nid
	 * @return
	 */
	HjhDebtCreditVO getHjhDebtCreditByCreditNid(String creditNid);
	
	/**
	 * 根据参数查询债转列表
	 * @param nid
	 * @return
	 */
	List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request);
	
	/**
	 * 根据参数查询 TenderToCreditDetailCustomizeVO
	 * @param nid
	 * @return
	 */
	List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String,Object> params);
}
