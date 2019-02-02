package com.hyjf.cs.message.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.message.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @author lisheng
 * @version AmTradeClientImpl, v0.1 2018/7/30 14:42
 */
@Cilent
public class AmTradeClientImpl implements AmTradeClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AccountVO getAccountByUserId(Integer userId) {
		AccountResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/account/getAccountByUserId/" + userId, AccountResponse.class)
				.getBody();
		if (Response.isSuccess(response)) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 获取出借金额和预期金额
	 * 
	 * @param begin
	 * @param end
	 * @param userId
	 * @return
	 */
	@Override
	public BorrowTenderResponse getBorrowTender(int userId, int begin, int end) {
		BorrowTenderResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/getBorrowTender/" + userId + "/" + begin + "/" + end,
						BorrowTenderResponse.class)
				.getBody();
		return response;
	}

	@Override
	public CreditTenderResponse getCreditTender(int userId, int begin, int end) {
		CreditTenderResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/getCreditTender/" + userId + "/" + begin + "/" + end,
						CreditTenderResponse.class)
				.getBody();
		return response;
	}

	@Override
	public HjhAccedeResponse getAccede(int userId, int begin, int end) {
		HjhAccedeResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/getAccede/" + userId + "/" + begin + "/" + end,
						HjhAccedeResponse.class)
				.getBody();
		return response;
	}

	@Override
	public BorrowTenderCpnResponse getBorrowTenderCPN(int userId, int begin, int end) {
		BorrowTenderCpnResponse response = restTemplate.getForEntity(
				"http://AM-TRADE/am-trade/wxWeekly/getBorrowTenderCPN/" + userId + "/" + begin + "/" + end,
				BorrowTenderCpnResponse.class).getBody();
		return response;
	}

	@Override
	public BorrowRecoverResponse getBorrowRecover(int userId, int begin, int end) {
		BorrowRecoverResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/getBorrowRecover/" + userId + "/" + begin + "/" + end,
						BorrowRecoverResponse.class)
				.getBody();
		return response;
	}

	@Override
	public CreditRepayResponse getCreditRepay(int userId, int begin, int end) {
		CreditRepayResponse response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/getCreditRepay/" + userId + "/" + begin + "/" + end,
						CreditRepayResponse.class)
				.getBody();
		return response;
	}

	@Override
	public CreditRepayResponse getCreditRepayToCredit(int userId, int begin, int end) {
		CreditRepayResponse response = restTemplate.getForEntity(
				"http://AM-TRADE/am-trade/wxWeekly/getCreditRepayToCredit/" + userId + "/" + begin + "/" + end,
				CreditRepayResponse.class).getBody();
		return response;
	}

	@Override
	public boolean getCoupon(int userId) {
		boolean response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/getCoupon/" + userId, boolean.class).getBody();
		return response;
	}

	@Override
	public EventVO getEventsAll(int begin, int end) {
		EventVO response = restTemplate
				.getForEntity("http://AM-TRADE/am-trade/wxWeekly/EventsAll/" + begin + "/" + end, EventVO.class)
				.getBody();
		return response;
	}

	@Override
	public TotalInvestAndInterestVO getTotalInvestAndInterest() {
		TotalInvestAndInterestResponse response = restTemplate.getForObject(
				"http://AM-TRADE/am-trade/totalinvestandinterest/gettotalinvestandinterest",
				TotalInvestAndInterestResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public List<String> queryUser(SmsCodeUserRequest request) {
		Response tradeResponse = restTemplate.postForObject("http://AM-TRADE/am-trade/smsCode/queryUser", request,
				Response.class);
		Response userResponse = restTemplate.postForObject("http://AM-USER/am-user/smsCode/queryUser", request, Response.class);
		if (tradeResponse != null && userResponse != null) {
			List<String> tradeList = tradeResponse.getResultList();
			List<String> userList = userResponse.getResultList();
            if (!CollectionUtils.isEmpty(tradeList) && !CollectionUtils.isEmpty(userList)) {
                tradeList.retainAll(userList);
            }
			return tradeList;
		}
		return null;
	}

	@Override
	public HjhPlanVO getHjhPlan(String planNid) {
		com.hyjf.am.response.user.HjhPlanResponse response = restTemplate.getForEntity("http://AM-TRADE/am-trade/hjhPlan/gethjhplan/"+planNid,
				com.hyjf.am.response.user.HjhPlanResponse.class).getBody();
		if (Response.isSuccess(response)){
			return response.getResult();
		}
		return null;
	}
}
