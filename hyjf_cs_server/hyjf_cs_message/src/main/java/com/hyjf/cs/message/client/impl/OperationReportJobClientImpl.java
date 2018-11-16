package com.hyjf.cs.message.client.impl;

import com.hyjf.am.response.trade.OperationReportJobResponse;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.vo.config.IdCardCustomize;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.cs.message.client.OperationReportJobClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportJobClientImpl, v0.1 2018/4/19 12:44
 */
@Service
public class OperationReportJobClientImpl implements OperationReportJobClient {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<OperationReportJobVO> getTenderCityGroupByList(Date date){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tendercitygroupbylist",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public  List<OperationReportJobVO> getTenderSexGroupByList(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tendersexgroupbylist",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public  int getTenderAgeByRange(Date date,int firstAge,int endAge,List<OperationReportJobVO> ageRangeUserIds) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		request.setFirstAge(firstAge);
		request.setEndAge(endAge);
		request.setOperationReportJobVOList(ageRangeUserIds);
		OperationReportJobResponse response = restTemplate.postForObject("http://AM-USER/am-user/batch/operation_report_job/tenderagebyrange",
				request, OperationReportJobResponse.class);
		if(response != null ){
			return response.getCount();
		}
		return 0;
	}


	@Override
	public BigDecimal getAccountByMonth(Date beginDate, Date endDate) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setBeginDate(beginDate);
		request.setEndDate(endDate);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/accountbymonth",request, OperationReportJobResponse.class).getBody();
		return response.getTotalAccount();
	}
	@Override
	public  int getTradeCountByMonth(Date beginDate,Date endDate) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setBeginDate(beginDate);
		request.setEndDate(endDate);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tradecountbymonth",request, OperationReportJobResponse.class).getBody();
		return response.getCount();
	}
	@Override
	public  int getLoanNum(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/loannum",request, OperationReportJobResponse.class).getBody();
		return response.getCount();
	}
	@Override
	public  int getTenderCount(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tendercount",request, OperationReportJobResponse.class).getBody();
		return response.getCount();
	}

	@Override
	public  double getInvestLastDate(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/investlastdate",request, OperationReportJobResponse.class).getBody();
		return response.getAccount();
	}
	@Override
	public float getFullBillAverageTime(Date date){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/fullbillaveragetime",request, OperationReportJobResponse.class).getBody();
		return response.getFullBillAverage();
	}
	@Override
	public BigDecimal getRepayTotal(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/repaytotal",request, OperationReportJobResponse.class).getBody();
		return response.getTotalAccount();
	}
	@Override
	public List<OperationReportJobVO> getPerformanceSum(){
		OperationReportJobResponse response =  restTemplate.getForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/performancesum", OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public int countRegistUser(){
		OperationReportJobResponse response =  restTemplate.getForEntity("http://AM-USER/am-user/batch/operation_report_job/countregistuser", OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getCount();
		}
		return 0;
	}
	@Override
	public List<OperationReportJobVO> getSexCount(List<OperationReportJobVO> list){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(list);
		OperationReportJobResponse response =  restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/sexcount",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getAgeCount(List<OperationReportJobVO> list){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(list);
		OperationReportJobResponse response =  restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/agecount",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getUserNames( List<OperationReportJobVO> list){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(list);
		OperationReportJobResponse response =  restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/usernames",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getMonthDealMoney(int startMonth,int endMonth) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setStartMonth(startMonth);
		request.setEndMonth(endMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/monthdealmoney",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getRevenueAndYield(int intervalMonth,int startMonth,int endMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		request.setStartMonth(startMonth);
		request.setEndMonth(endMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/revenueandyield",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getRechargeMoneyAndSum(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/rechargemoneyandsum",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getCompleteCount(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/completecount",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getBorrowPeriod(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/borrowperiod",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<OperationReportJobVO> getAgeDistribute( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/agedistribute",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getMoneyDistribute( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/moneydistribute",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<OperationReportJobVO> getTenMostMoney( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/tenmostmoney",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getOneInvestMost(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/oneinvestmost",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getOneInterestsMost(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-ADMIN/am-trade/report/operationreportjob/oneinterestsmost",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public OperationReportJobVO getUserAgeAndArea(Integer userId){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setUserId(userId);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/userageandarea",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResult();
		}
		return null;
	}


}
