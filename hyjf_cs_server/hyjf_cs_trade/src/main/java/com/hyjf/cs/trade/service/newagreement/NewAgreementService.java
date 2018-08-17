/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.newagreement;

import java.util.List;
import java.util.Map;

import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.app.AppNewAgreementVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.ProtocolTemplateVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.UserHjhInvistDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;

import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

/**
 * APP端协议Service
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
	 * @param assignNid
	 * @return
	 */
	List<TenderAgreementVO> getTenderAgreementByTenderNid(String assignNid);
	
	/**
	 * 查询用户详情
	 * @param userId
	 * @return
	 */
	UserInfoVO getUsersInfoByUserId(Integer userId);
	
	/**
	 * 根据borrowNid获取标的
	 * @param borrowNid
	 * @return
	 */
	BorrowVO getBorrowByNid(String borrowNid);
	
	/**
	 * 根据creditNid获取债转信息
	 * @param creditNid
	 * @return
	 */
	HjhDebtCreditVO getHjhDebtCreditByCreditNid(String creditNid);
	
	/**
	 * 根据参数查询债转列表
	 * @param request
	 * @return
	 */
	List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request);
	
	/**
	 * 根据参数查询 TenderToCreditDetailCustomizeVO
	 * @param params
	 * @return
	 */
	List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String,Object> params);
	
	/**
	 * 根据参数查询 BorrowTenderVO
	 * @param tenderNid
	 * @return
	 */
	List<BorrowTenderVO> getBorrowTenderListByNid(String tenderNid);
	
	/**
	* 会计划投资详情
	* @param params
	* @return
	*/
	UserHjhInvistDetailCustomizeVO selectUserHjhInvistDetail(Map<String, Object> params);
	
	/**
	 * 获取债转承接信息by AssignOrderId
	 * @param assignOrderId
	 * @return
	 */
	HjhDebtCreditTenderVO getHjhDebtCreditTenderByAssignOrderId(String assignOrderId);
	
	/**
	 * 获取债转承接信息by AssignNid
	 * @param assignNid
	 * @return
	 */
	CreditTenderVO getCreditTenderByAssignNid(String assignNid);
	
	/**
	 * 获取协议模板 by  DisplayName
	 * @param displayName
	 * @return
	 */
	List<ProtocolTemplateVO> getProtocolTemplateVOByDisplayName(String displayName);

	/**
	 * 获得协议模板图片
	 * @param aliasName
	 * @return
	 */
	AppNewAgreementVO setProtocolImg(String aliasName);
}
