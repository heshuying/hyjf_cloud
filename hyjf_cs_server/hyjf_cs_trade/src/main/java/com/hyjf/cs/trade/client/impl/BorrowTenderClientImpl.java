package com.hyjf.cs.trade.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.trade.BorrowTenderCpnResponse;
import com.hyjf.am.response.trade.BorrowTenderResponse;
import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.response.trade.FddTempletResponse;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.FddTempletVO;
import com.hyjf.am.vo.trade.TenderAgreementVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.BorrowTenderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BorrowTenderClientImpl implements BorrowTenderClient {



    @Autowired
	private RestTemplate restTemplate;

    @Override
    public Integer countUserInvest(Integer userId, String borrowNid) {
        BorrowTenderResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/borrowTender/countUserInvest/" +userId + "/" + borrowNid,BorrowTenderResponse.class).getBody();
        if (Response.isSuccess(response)){
            return response.getTenderCount();
        }
        return null;
    }

	@Override
	public BorrowTenderVO selectBorrowTender(BorrowTenderRequest btRequest) {
		String url = "http://AM-TRADE/am-trade/borrowTender/selectBorrowTender";
		BorrowTenderResponse response = restTemplate.postForEntity(url,btRequest,BorrowTenderResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<FddTempletVO> getFddTempletList(Integer protocolType) {
		String url = "http://AM-TRADE/am-trade/borrowTender/getFddTempletList/"+protocolType;
		FddTempletResponse response = restTemplate.getForEntity(url,FddTempletResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResultList();
		}
		return null;
	}
	
	@Override
	public int saveTenderAgreement(TenderAgreementVO info) {
		String url = "http://AM-TRADE/am-trade/borrowTender/saveTenderAgreement";
		return restTemplate.postForEntity(url,info,Integer.class).getBody();
	}

	@Override
	public int updateTenderAgreement(TenderAgreementVO tenderAgreement) {
		String url = "http://AM-TRADE/am-trade/borrowTender/updateTenderAgreement";
		return restTemplate.postForEntity(url,tenderAgreement,Integer.class).getBody();
	}

	@Override
	public List<BorrowTenderVO> getBorrowTenderListByNid(String nid) {
    	String url = "http://AM-TRADE/am-trade/borrowTender/getBorrowTenderListByNid/"+nid;
    	BorrowTenderResponse response = restTemplate.getForEntity(url,BorrowTenderResponse.class).getBody();
    	if (Validator.isNotNull(response)){
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 根据投资订单号查询已承接金额
	 *
	 * @param tenderNid
	 * @return
	 */
	@Override
	public BigDecimal getAssignCapital(String tenderNid) {
		String url = "http://AM-TRADE/am-trade/borrowTender/get_assign_capital_by_tender_nid/"+tenderNid;
		BigDecimal response = restTemplate.getForEntity(url,BigDecimal.class).getBody();
		return response;
	}

	/**
	 * 保存债转日志
	 *
	 * @param creditTenderLog
	 * @return
	 */
	@Override
	public Integer saveCreditTenderAssignLog(CreditTenderLogVO creditTenderLog) {
		String url = "http://AM-TRADE/am-trade/borrowTender/save_credit_tender_assign_log";
		return restTemplate.postForEntity(url,creditTenderLog,Integer.class).getBody();
	}

	/**
	 * 查看是否已经插入网站收支明细
	 *
	 * @param logOrderId
	 * @param tenderType 交易类型
	 * @return
	 */
	@Override
	public Integer countAccountWebListByOrdId(String logOrderId, String tenderType) {
		String url = "http://AM-TRADE/am-trade/borrowTender/countAccountWebListByOrdId/"+tenderType+"/"+tenderType;
		return restTemplate.getForEntity(url,Integer.class).getBody();
	}

    @Override
    public BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid) {
		String url = "http://AM-TRADE/am-trade/borrowTender/getcoupontenderinfo/"+couponTenderNid;
		BorrowTenderCpnResponse response = restTemplate.getForEntity(url,BorrowTenderCpnResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResult();
		}
        return null;
    }

	@Override
	public CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow) {
		String url = "http://AM-TRADE/am-trade/borrowTender/getcurrentcouponrecover/"+couponTenderNid+"/"+periodNow;
		CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url,CouponRecoverCustomizeResponse.class).getBody();
		if(Validator.isNotNull(response)) {
			return response.getResult();
		}
		return null;
	}

}
