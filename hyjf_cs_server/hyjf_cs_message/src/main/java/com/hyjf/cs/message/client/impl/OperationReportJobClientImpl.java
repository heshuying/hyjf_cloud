package com.hyjf.cs.message.client.impl;

import com.hyjf.am.response.trade.OperationReportJobResponse;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.vo.config.IdCardCustomize;
import com.hyjf.am.vo.datacollect.OperationReportInfoVO;
import com.hyjf.am.vo.datacollect.OperationReportVO;
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
	public  List<OperationReportJobVO> getTenderCityGroupByUserIds(List<OperationReportJobVO> cityUserIds){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(cityUserIds);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/tendercitygroupbyuserids",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getTenderCityGroupBy(List<OperationReportJobVO> bms){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setOperationReportJobVOList(bms);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-CONFIG/am-config/content/idcard/tendercitygroupby",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getTenderCityGroupByList(Date date){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tendercitygroup",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public  List<OperationReportJobVO> getTenderSexGroupBy(Date date,List<OperationReportJobVO> ageRangeUserIds) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		request.setOperationReportJobVOList(ageRangeUserIds);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/tendersexgroupby",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public  List<OperationReportJobVO> getTenderSexGroupByList(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tendersexgroupbylist",request, OperationReportJobResponse.class).getBody();
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
		int count = restTemplate.postForEntity("http://AM-USER/am-user/batch/operation_report_job/tenderagebyrange",request, int.class).getBody();
		return count;
	}
	@Override
	public List<OperationReportJobVO>  getTenderAgeByRangeList(Date date,int firstAge,int endAge){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		request.setFirstAge(firstAge);
		request.setEndAge(endAge);
		OperationReportJobResponse response = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tenderagebyrangelist",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public BigDecimal getAccountByMonth(Date beginDate, Date endDate) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setBeginDate(beginDate);
		request.setEndDate(endDate);
		BigDecimal count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/accountbymonth",request, BigDecimal.class).getBody();
		return count;
	}
	@Override
	public  int getTradeCountByMonth(Date beginDate,Date endDate) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setBeginDate(beginDate);
		request.setEndDate(endDate);
		int count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tradecountbymonth",request, int.class).getBody();
		return count;
	}
	@Override
	public  int getLoanNum(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		int count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/loannum",request, int.class).getBody();
		return count;
	}
	@Override
	public  int getTenderCount(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		int count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tendercount",request, int.class).getBody();
		return count;
	}

	@Override
	public  double getInvestLastDate(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		double count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/investlastdate",request, double.class).getBody();
		return count;
	}
	@Override
	public float getFullBillAverageTime(Date date){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		float count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/fullbillaveragetime",request, float.class).getBody();
		return count;
	}
	@Override
	public BigDecimal getRepayTotal(Date date) {
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setDate(date);
		BigDecimal count = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/repaytotal",request, BigDecimal.class).getBody();
		return count;
	}
	@Override
	public List<OperationReportJobVO> getPerformanceSum(){
		OperationReportJobResponse response =  restTemplate.getForEntity("http://AM-TRADE/am-trade/report/operationreportjob/performanceSum", OperationReportJobResponse.class).getBody();
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
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/monthdealmoney",request, OperationReportJobResponse.class).getBody();
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
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/revenueandyield",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getRechargeMoneyAndSum(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/rechargemoneyandsum",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getCompleteCount(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/completecount",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getBorrowPeriod(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/borrowperiod",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getSexDistribute( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/sexdistribute",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getAgeDistribute( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/agedistribute",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getMoneyDistribute( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/moneydistribute",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}

	@Override
	public List<OperationReportJobVO> getTenMostMoney( int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/tenmostmoney",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getOneInvestMost(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/oneinvestmost",request, OperationReportJobResponse.class).getBody();
		if (response != null) {
			return response.getResultList();
		}
		return null;
	}
	@Override
	public List<OperationReportJobVO> getOneInterestsMost(int intervalMonth){
		OperationReportJobRequest request = new OperationReportJobRequest();
		request.setIntervalMonth(intervalMonth);
		OperationReportJobResponse response  = restTemplate.postForEntity("http://AM-TRADE/am-trade/report/operationreportjob/oneinterestsmost",request, OperationReportJobResponse.class).getBody();
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
	@Override
	public IdCardCustomize getIdCardCustomize(IdCardCustomize idCardCustomize){
		return  restTemplate.postForEntity("http://AM-CONFIG/am-config/content/idcard/idcarddetail",idCardCustomize, IdCardCustomize.class).getBody();
	}


}
