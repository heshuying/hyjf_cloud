package com.hyjf.am.trade.service.admin.statistics.impl;

import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.trade.dao.mapper.customize.trade.OperationReportInfoJobCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.trade.OperationReportJobCustomizeMapper;
import com.hyjf.am.trade.service.admin.statistics.OperationReportJobService;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 *
 * @author tanyy
 * @date 2018/07/26
 */
@Service
public class OperationReportJobServiceImpl implements OperationReportJobService {
	@Resource
    OperationReportJobCustomizeMapper operationReportCustomizeMapper;
	@Resource
	OperationReportInfoJobCustomizeMapper operationReportInfoJobCustomizeMapper;
	@Override
	public  List<OperationReportJobVO> getTenderCityGroupByList(OperationReportJobRequest request){

		return operationReportInfoJobCustomizeMapper.getTenderCityGroupByList(request.getDate());
	}
	@Override
	public  List<OperationReportJobVO> getTenderSexGroupByList(OperationReportJobRequest request){

		return operationReportInfoJobCustomizeMapper.getTenderSexGroupByList(request.getDate());
	}
	@Override
	public  int getTenderAgeByRange(Date date, int firstAge, int endAge){

		return operationReportCustomizeMapper.getTenderAgeByRange(date,firstAge,endAge);
	}
	@Override
	public BigDecimal getAccountByMonth(Date beginDate, Date endDate){
		return operationReportCustomizeMapper.getAccountByMonth(beginDate,endDate);

	}

	@Override
	public int getTradeCountByMonth(Date beginDate,Date endDate){

		return operationReportCustomizeMapper.getTradeCountByMonth(beginDate,endDate);
	}


	@Override
	public int getLoanNum(Date date){
		return operationReportCustomizeMapper.getLoanNum(date);

	}
	@Override
	public double getInvestLastDate(Date date){
		return operationReportCustomizeMapper.getInvestLastDate(date);
	}


	@Override
	public int getTenderCount(Date date){
		return operationReportCustomizeMapper.getTenderCount(date);

	}

	@Override
	public int getTradeCount(){
		return operationReportCustomizeMapper.getTradeCount();

	}
	@Override
	public float getFullBillAverageTime(Date date){
		return operationReportCustomizeMapper.getFullBillAverageTime(date);

	}
	@Override
	public BigDecimal getRepayTotal(Date date){
		return operationReportCustomizeMapper.getRepayTotal(date);

	}

	@Override
	public List<OperationReportJobVO> getPerformanceSum(){
		return operationReportInfoJobCustomizeMapper.getPerformanceSum();

	}

	@Override
	public List<OperationReportJobVO> getMonthDealMoney(int startMonth,int endMonth){
		return operationReportInfoJobCustomizeMapper.getMonthDealMoney(startMonth,endMonth);

	}
	@Override
	public List<OperationReportJobVO> getRevenueAndYield(int intervalMonth,int startMonth,int endMonth){
		return operationReportInfoJobCustomizeMapper.getRevenueAndYield(intervalMonth,startMonth,endMonth);

	}
	@Override
	public List<OperationReportJobVO> getRechargeMoneyAndSum(int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getRechargeMoneyAndSum(intervalMonth);
	}
	@Override
	public List<OperationReportJobVO> getCompleteCount(int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getCompleteCount(intervalMonth);

	}
	@Override
	public List<OperationReportJobVO> getBorrowPeriod(int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getBorrowPeriod(intervalMonth);

	}
	@Override
	public List<OperationReportJobVO> getSexDistribute( int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getSexDistribute(intervalMonth);

	}
	@Override
	public List<OperationReportJobVO> getAgeDistribute( int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getAgeDistribute(intervalMonth);
	}
	@Override
	public List<OperationReportJobVO> getMoneyDistribute( int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getMoneyDistribute(intervalMonth);

	}
	@Override
	public List<OperationReportJobVO> getTenMostMoney( int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getTenMostMoney(intervalMonth);

	}
	@Override
	public List<OperationReportJobVO> getOneInvestMost(int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getOneInvestMost(intervalMonth);

	}
	@Override
	public List<OperationReportJobVO> getOneInterestsMost(int intervalMonth){
		return operationReportInfoJobCustomizeMapper.getOneInterestsMost(intervalMonth);

	}
	@Override
	public OperationReportJobVO getUserAgeAndArea(Integer userId){
		return operationReportInfoJobCustomizeMapper.getUserAgeAndArea(userId);

	}
	@Override
	public List<OperationReportJobVO>  getTenderAgeByRangeList(Date date, int firstAge, int endAge){
		return operationReportInfoJobCustomizeMapper.getTenderAgeByRangeList(date,firstAge,endAge);
	}
}
