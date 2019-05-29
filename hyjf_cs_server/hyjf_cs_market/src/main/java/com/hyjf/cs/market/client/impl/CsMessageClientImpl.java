/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.BigDecimalResponse;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.app.AppUtmRegResponse;
import com.hyjf.am.response.message.AppAccesStatisticsResponse;
import com.hyjf.am.response.message.BorrowUserStatisticResponse;
import com.hyjf.am.response.trade.CalculateInvestInterestResponse;
import com.hyjf.am.resquest.admin.AppChannelStatisticsRequest;
import com.hyjf.am.vo.datacollect.AppAccesStatisticsVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.cs.market.client.CsMessageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

/**
 * @author fuqiang
 * @version AmDataCollectImpl, v0.1 2018/7/18 13:56
 */
@Service
public class CsMessageClientImpl implements CsMessageClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public BorrowUserStatisticVO selectBorrowUserStatistic() {
		BorrowUserStatisticResponse response = restTemplate.getForObject(
				"http://CS-MESSAGE/cs-message/operationReportJob/getBorrowUserStatistic",
				BorrowUserStatisticResponse.class);
		return response.getResult();
	}

	@Override
	public String getTotalInvestmentAmount() {
		CalculateInvestInterestResponse response = restTemplate.getForObject(
				"http://CS-MESSAGE/cs-message/search/gettotalinvestmentamount", CalculateInvestInterestResponse.class);
		if (response != null) {
			BigDecimal interestSum = response.getInterestSum();
			if (interestSum != null) {
				return String.valueOf(interestSum.divide(new BigDecimal("100000000")));
			}
		}
		return null;
	}

	/**
	 * 累计出借总额
	 * 
	 * @return
	 */
	@Override
	public BigDecimal selectTenderSum() {
		BigDecimalResponse tenderSum = restTemplate.getForObject(
				"http://CS-MESSAGE/cs-message/totalInvestAndInterest/selectTenderSum", BigDecimalResponse.class);
		return tenderSum.getResultDec();
	}

	/**
	 * 累计收益
	 * 
	 * @return
	 */
	@Override
	public BigDecimal selectInterestSum() {
		BigDecimalResponse interestSum = restTemplate.getForObject(
				"http://CS-MESSAGE/cs-message/totalInvestAndInterest/selectInterestSum", BigDecimalResponse.class);
		return interestSum.getResultDec();
	}

	/**
	 * 累计出借笔数
	 * 
	 * @return
	 */
	@Override
	public int selectTotalTenderSum() {
		IntegerResponse totalTenderSum = restTemplate.getForObject(
				"http://CS-MESSAGE/cs-message/totalInvestAndInterest/selectTotalTenderSum", IntegerResponse.class);
		return totalTenderSum.getResultInt();
	}

	/**
	 * 根据开始时间、结束时间和来源查询数据
	 * 
	 * @return
	 */
	@Override
	public List<AppAccesStatisticsVO> getAppAccesStatisticsVO(AppChannelStatisticsRequest request) {
		AppAccesStatisticsResponse response = restTemplate.postForObject(
				"http://CS-MESSAGE/cs-message/app_channel_statistics/getAccessNumber", request,
				AppAccesStatisticsResponse.class);

		return response.getResultList();
	}

	/**
	 * 根据开始时间、结束时间和来源查询数据
	 * 
	 * @return
	 */
	@Override
	public List<AppUtmRegVO> getAppChannelStatisticsDetailVO(AppChannelStatisticsRequest request) {
		AppUtmRegResponse response = restTemplate.postForObject("http://AM-USER/am-user/app_utm_reg/getRegistNumber",
				request, AppUtmRegResponse.class);

		return response.getResultList();
	}

    /**
     * app发现页运营报告信息
     * @param isRelease
     * @return
     */
	@Override
	public List getReportList(Integer isRelease) {
		JSONObject response = restTemplate.getForObject("http://CS-MESSAGE/hyjf-app/report/reportList?isRelease=" + isRelease, JSONObject.class);
		if (response != null && "success".equals(response.get("success"))) {
			List reportList = (List) response.get("recordList");
			return (List) reportList.stream().limit(2).collect(toList());
		}
		return new ArrayList();
	}

}
