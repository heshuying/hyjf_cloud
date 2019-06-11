package com.hyjf.cs.message.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.WrbTenderNotifyResponse;
import com.hyjf.am.response.datacollect.TotalInvestAndInterestResponse;
import com.hyjf.am.response.trade.*;
import com.hyjf.am.response.trade.account.AccountResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.vo.config.EventVO;
import com.hyjf.am.vo.datacollect.TotalInvestAndInterestVO;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.message.client.AmTradeClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.*;

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
	public List<OperationReportJobVO>  getTenderAgeByRangeList(Date date){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tenderagebyrangelist",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
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
		List<String> list = new ArrayList<>();
		//所以未开户用户 并且 累计出借金额 or 出借日期 不为空
		if(request.getOpen_account() == 0 && (StringUtils.isNotBlank(request.getAdd_money_count())
				|| StringUtils.isNotBlank(request.getAdd_time_begin())
				|| StringUtils.isNotBlank(request.getAdd_time_end()))){
			return list;
		}

		Response userResponse = restTemplate.postForObject("http://AM-USER/am-user/smsCode/queryUser", request, Response.class);
		Response tradeResponse = null;
		if((request.getOpen_account() != null && request.getOpen_account() != 0) && (StringUtils.isNotBlank(request.getAdd_money_count())
				|| StringUtils.isNotBlank(request.getAdd_time_begin())
				|| StringUtils.isNotBlank(request.getAdd_time_end()))){

			tradeResponse = restTemplate.postForObject("http://AM-TRADE/am-trade/smsCode/queryUser", request,
					Response.class);
		}

		if (userResponse != null) {
			List<String> userList = userResponse.getResultList();
            if (!CollectionUtils.isEmpty(userList)) {
				list.addAll(userList);
            }
		}
		if (tradeResponse != null ) {
			List<String> tradeList = tradeResponse.getResultList();
//			if (!CollectionUtils.isEmpty(tradeList) ) {
				list.retainAll(tradeList);
//			}
		}
		return list;
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

	@Override
	public List<WrbTenderNotifyCustomizeVO> getBorrowTenderByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getBorrowTenderByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<WrbTenderNotifyCustomizeVO> getProductListByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getProductListByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<WrbTenderNotifyCustomizeVO> getDebtPlanAccedeByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getDebtPlanAccedeByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<WrbTenderNotifyCustomizeVO> getCreditTenderByClient(AppChannelStatisticsRequest request) {
		WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getCreditTenderByClient",
				request,WrbTenderNotifyResponse.class);
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<WrbTenderNotifyCustomizeVO> getAccountRechargeByAddtime(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getAccountRechargeByAddtime",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
	}

	@Override
	public List<WrbTenderNotifyCustomizeVO> getBorrowTenderByAddtime(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getBorrowTenderByAddtime",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
	}

	@Override
	public List<WrbTenderNotifyCustomizeVO> getCreditTenderByAddtime(AppChannelStatisticsRequest request) {
        WrbTenderNotifyResponse response = restTemplate.postForObject("http://AM-TRADE/am-trade/borrowTender/getCreditTenderByAddtime",
                request,WrbTenderNotifyResponse.class);
        if (response != null) {
            return response.getResultList();
        }
        return null;
	}
}
