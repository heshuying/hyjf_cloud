/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl.newagreemnet;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.resquest.trade.CreditTenderRequest;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.TenderToCreditDetailCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.cs.trade.client.AmBorrowClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.CouponUserClient;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.newagreement.NewAgreementService;

/**
 * @author libin
 * @version NewAgreementServiceImpl.java, v0.1 2018年7月25日 下午2:23:06
 */
@Service
public class NewAgreementServiceImpl extends BaseTradeServiceImpl implements NewAgreementService{

    @Autowired
    private AmTradeClient amTradeClient;
    
    @Autowired
    private AmBorrowClient amBorrowClient;
    @Autowired
    CouponUserClient couponUserClient;	
	/**
	 * 获取债转承接信息
	 * @param nid
	 * @return
	 */
	@Override
	public HjhDebtCreditTenderVO getHjhDebtCreditTenderByPrimaryKey(Integer nid) {
		HjhDebtCreditTenderVO vo = amTradeClient.getHjhDebtCreditTenderByPrimaryKey(nid);
		return vo;
	}
	
	/**
	 * 查询协议表by assignNid(实际上是tenderNID)
	 * @param nid
	 * @return
	 */
	@Override
	public List<TenderAgreementVO> getTenderAgreementByTenderNid(String assignNid) {
		List<TenderAgreementVO> list = this.amTradeClient.selectTenderAgreementByNid(assignNid);
		return list;
	}

	/**
	 * 查询用户详情
	 * @param nid
	 * @return
	 */
	@Override
	public UserInfoVO getUsersInfoByUserId(Integer userId) {
		UserInfoVO userInfoVO = couponUserClient.getUserInfo(userId);
        if (userInfoVO != null) {
            return userInfoVO;
        }
		return null;
	}

	/**
	 * 根据borrowNid获取标的
	 * @param nid
	 * @return
	 */
	@Override
	public BorrowVO getBorrowByNid(String borrowNid) {
		// 借款详情
		BorrowVO borrow = this.amBorrowClient.getBorrowByNid(borrowNid);
        if (borrow != null) {
            return borrow;
        }
		return null;
	}

	@Override
	public HjhDebtCreditVO getHjhDebtCreditByCreditNid(String creditNid) {
		HjhDebtCreditVO credit = this.amTradeClient.selectHjhDebtCreditByCreditNid(creditNid);
        if (credit != null) {
            return credit;
        }
		return null;
	}

	@Override
	public List<CreditTenderVO> getCreditTenderList(CreditTenderRequest request) {
		List<CreditTenderVO> creditTenderList = this.amTradeClient.getCreditTenderList(request);
		return creditTenderList;
	}

	@Override
	public List<TenderToCreditDetailCustomizeVO> selectWebCreditTenderDetailForContract(Map<String, Object> params) {
		List<TenderToCreditDetailCustomizeVO> tenderToCreditDetailList = this.amTradeClient.selectWebCreditTenderDetailForContract(params);
		return tenderToCreditDetailList;
	}
}
