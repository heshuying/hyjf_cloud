package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.market.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Set;

/**
 * @author xiasq
 * @version AmTradeClientImpl, v0.1 2018/7/6 11:21
 */
@Cilent
public class AmTradeClientImpl implements AmTradeClient {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * 查询投之家每日充值人数、投资人数 、投资金额、首投金额、首投人数、复投人数
	 * 
	 * @param tzjUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportVO queryTradeDataOnToday(Set<Integer> tzjUserIds, Date startTime, Date endTime) {
		TzjDayReportRequest request = new TzjDayReportRequest();
		request.setTzjUserIds(tzjUserIds);
		request.setStartTime(startTime);
		request.setEndTime(endTime);

		TzjDayReportResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/tzj/queryTradeDataOnToday",
				request, TzjDayReportResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}

	/**
	 * 新充人数 新投人数
	 * 
	 * @param registerUserIds
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@Override
	public TzjDayReportVO queryTradeNewDataOnToday(Set<Integer> registerUserIds, Date startTime, Date endTime) {
		TzjDayReportRequest request = new TzjDayReportRequest();
		request.setTzjUserIds(registerUserIds);
		request.setStartTime(startTime);
		request.setEndTime(endTime);

		TzjDayReportResponse response = restTemplate.postForEntity(
				"http://AM-TRADE/am-trade/tzj/queryTradeNewDataOnToday", request, TzjDayReportResponse.class).getBody();
		if (response != null && Response.SUCCESS.equals(response.getRtn())) {
			return response.getResult();
		}
		return null;
	}
}
