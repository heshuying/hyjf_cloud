package com.hyjf.cs.market.client.impl;

import com.hyjf.am.response.Response;
import com.hyjf.am.response.datacollect.TzjDayReportResponse;
import com.hyjf.am.resquest.datacollect.TzjDayReportRequest;
import com.hyjf.am.vo.datacollect.OperationReportEntityVO;
import com.hyjf.am.vo.datacollect.TzjDayReportVO;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.common.annotation.Cilent;
import com.hyjf.cs.market.client.AmTradeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

	@Override
	public List<TenderCityCountVO> getTenderCityGroupBy(Date lastDay) {
		return null;
	}

	@Override
	public List<TenderSexCountVO> getTenderSexGroupBy(Date lastDay) {
		return null;
	}

	@Override
	public int getTenderAgeByRange(Date date, int firstAge, int endAge) {
		return 0;
	}

	@Override
	public OperationReportEntityVO getOperationReport(int i) {
		return null;
	}

	@Override
	public BigDecimal getAccountByMonth(Date firstDay, Date lastDay) {
		return null;
	}

	@Override
	public int getTradeCountByMonth(Date firstDay, Date lastDay) {
		return 0;
	}

	@Override
	public int getLoanNum(Date lastDay) {
		return 0;
	}

	@Override
	public double getInvestLastDate(Date lastDay) {
		return 0;
	}

	@Override
	public int getTenderCount(Date lastDay) {
		return 0;
	}

	@Override
	public float getFullBillAverageTime(Date lastDay) {
		return 0;
	}

	@Override
	public BigDecimal getRepayTotal(Date lastDay) {
		return null;
	}

	@Override
	public Integer countBorrowUser() {
		return null;
	}

	@Override
	public Integer countCurrentBorrowUser() {
		return null;
	}

	@Override
	public Integer countCurrentTenderUser() {
		return null;
	}

	@Override
	public BigDecimal sumBorrowUserMoney(Date lastDay) {
		return null;
	}

	@Override
	public BigDecimal sumBorrowUserMoneyTopTen() {
		return null;
	}

	@Override
	public BigDecimal sumBorrowUserMoneyTopOne() {
		return null;
	}
}
