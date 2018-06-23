package com.hyjf.cs.trade.client.impl;

import java.math.BigDecimal;
import java.util.List;

import com.hyjf.am.response.trade.CouponRecoverCustomizeResponse;
import com.hyjf.am.response.trade.MyCouponListResponse;
import com.hyjf.am.response.trade.MyInviteListResponse;
import com.hyjf.am.response.trade.MyRewardListResponse;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.MyInviteListRequest;
import com.hyjf.am.resquest.trade.RtbIncreaseRepayRequest;
import com.hyjf.am.vo.trade.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.MyInviteListCustomizeVO;
import com.hyjf.am.vo.trade.MyRewardRecordCustomizeVO;
import com.hyjf.am.vo.trade.borrow.BorrowApicronVO;
import com.hyjf.am.vo.trade.coupon.MyCouponListCustomizeVO;
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

	public static final String urlBase = "http://AM-TRADE/am-trade/";

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
		String url = urlBase + "batch/rtb/getRepayAmount/" + borrowNid + "/" + borrowStyle + "/"
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
		String url = urlBase + "batch/rtb/increaseInterestRepay";

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
		String url = urlBase + "batch/selectCouponInterestWaitToday/"+timeStart+"/"+timeEnd;
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
		String url = urlBase + "batch/selectCouponInterestReceivedToday/"+timeStart+"/"+timeEnd;
		BigDecimal interest = restTemplate.getForEntity(url,BigDecimal.class).getBody();
		if (interest != null) {
			return interest;
		}
		return null;
	}

	/**
	 * 我的优惠券列表
	 * @auther: hesy
	 * @date: 2018/6/23
	 */
	@Override
	public List<MyCouponListCustomizeVO> selectMyCouponList(MyCouponListRequest requestBean){
		String url = urlBase + "coupon/myCouponList";
		MyCouponListResponse response = restTemplate.postForEntity(url,requestBean,MyCouponListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 我的邀请列表
	 * @param requestBean
	 * @return
	 */
	@Override
	public List<MyInviteListCustomizeVO> selectMyInviteList(MyInviteListRequest requestBean){
		String url = urlBase + "invite/myInviteList";
		MyInviteListResponse response = restTemplate.postForEntity(url,requestBean,MyInviteListResponse.class).getBody();
			if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	/**
	 * 我的邀请列表
	 * @param requestBean
	 * @return
	 */
	@Override
	public List<MyRewardRecordCustomizeVO> selectMyRewardList(MyInviteListRequest requestBean){
		String url = urlBase + "invite/myRewardList";
		MyRewardListResponse response = restTemplate.postForEntity(url,requestBean,MyRewardListResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
}
