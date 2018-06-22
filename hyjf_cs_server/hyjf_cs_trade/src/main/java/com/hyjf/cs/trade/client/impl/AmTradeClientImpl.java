package com.hyjf.cs.trade.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.resquest.trade.RtbIncreaseRepayRequest;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.cs.trade.client.AmTradeClient;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/6/19 15:44
 */
@Service
public class AmTradeClientImpl implements AmTradeClient {
	private static Logger logger = LoggerFactory.getLogger(AmTradeClientImpl.class);

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 根据借款编号,还款期数,还款方式取得融通宝待还款金额
	 * 
	 * @param borrowNid
	 * @param borrowStyle
	 * @param periodNow
	 * @return
	 */
	@Override
	public BigDecimal selectRtbRepayAmount(String borrowNid, String borrowStyle, Integer periodNow) {
		String url = "http://AM-TRADE/am-trade/batch/rtb/getRepayAmount/" + borrowNid + "/" + borrowStyle + "/"
				+ periodNow;
		BigDecimal rtbRepayAmount = restTemplate.getForEntity(url, BigDecimal.class).getBody();
		return rtbRepayAmount;
	}

	/**
	 * 融通宝还款加息
	 * 
	 * @param borrowApicronVO
	 */
	@Override
	public void rtbIncreaseReapy(BorrowApicronVO borrowApicronVO, String account, String companyAccount) {
		String url = "http://AM-TRADE/am-trade/batch/rtb/increaseInterestRepay";

		RtbIncreaseRepayRequest repayRequest = new RtbIncreaseRepayRequest();
		repayRequest.setBorrowApicronVO(borrowApicronVO);
		repayRequest.setAccount(account);
		repayRequest.setCompanyAccount(companyAccount);

		restTemplate.postForEntity(url, repayRequest, Object.class);
	}

	/**
	 * 统计加息券每日待收收益
	 * @param
	 * @return
	 */
	@Override
	public List<CouponRecoverCustomizeVO> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
		String url = "http://AM-TRADE/am-trade/batch/selectCouponInterestWaitToday/"+timeStart+"/"+timeEnd;
        CouponRecoverCustomizeResponse response = restTemplate.getForEntity(url,CouponRecoverCustomizeResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 统计加息券每日已收收益
	 * @param
	 * @return
	 */
	@Override
	public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
		String url = "http://AM-TRADE/am-trade/batch/selectCouponInterestReceivedToday/"+timeStart+"/"+timeEnd;
		BigDecimal interest = restTemplate.getForEntity(url,BigDecimal.class).getBody();
		if (interest != null) {
			return interest;
		}
		return null;
	}
}
