/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.newagreement;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementResultBean;
import com.hyjf.cs.trade.service.BaseTradeService;

import java.util.List;
import java.util.Map;

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
	 * @return
	 */
	List<TenderAgreementVO> getTenderAgreementByTenderNid(String accedeOrderId);
	
	/**
	 * 查询用户详情
	 * @param userId
	 * @return
	 */
	@Override
	UserInfoVO getUsersInfoByUserId(Integer userId);
	
	/**
	 * 根据borrowNid获取标的
	 * @param borrowNid
	 * @return
	 */
    @Override
    BorrowAndInfoVO getBorrowByNid(String borrowNid);
	
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
	* 会计划出借详情
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
	 * 协议名称 动态获得
	 *
	 * @return
	 */
	List<ProtocolTemplateVO> getdisplayNameDynamic();

	/**
	 * 获得协议模板图片
	 * @param aliasName
	 * @return
	 */
	NewAgreementResultBean setProtocolImg(String aliasName);

	/**
	 * 获得协议模板pdf
	 * @param aliasName
	 * @return
	 */
	NewAgreementResultBean getAgreementPdf(String aliasName);

	/**
	 * 协议名称-动态获得
	 * @return
	 */
	JSONObject getdisplayNameDynamicMethod();
	/**
	 * 获取所有在帮助中心显示的模板列表
	 * add by nxl 20190313
	 * PC 1.1.2
	 * @return
	 */
	List<ProtocolTemplateVO> selectAllShowProtocolTemplate();
}
