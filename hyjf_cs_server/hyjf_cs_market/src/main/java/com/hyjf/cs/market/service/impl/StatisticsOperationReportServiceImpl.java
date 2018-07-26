/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.service.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.am.vo.datacollect.OperationMongoGroupEntityVO;
import com.hyjf.am.vo.datacollect.OperationReportEntityVO;
import com.hyjf.am.vo.trade.TenderCityCountVO;
import com.hyjf.am.vo.trade.TenderSexCountVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.market.client.AmDataCollectClient;
import com.hyjf.cs.market.client.AmTradeClient;
import com.hyjf.cs.market.mq.base.MessageContent;
import com.hyjf.cs.market.mq.producer.StatisticsOperationReportProducer;
import com.hyjf.cs.market.service.BaseMarketServiceImpl;
import com.hyjf.cs.market.service.StatisticsOperationReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author fuqiang
 * @version StatisticsOperationReportServiceImpl, v0.1 2018/7/18 10:18
 */
@Service
public class StatisticsOperationReportServiceImpl extends BaseMarketServiceImpl implements StatisticsOperationReportService {
    Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private AmTradeClient amTradeClient;
	@Autowired
	private AmDataCollectClient amDataCollect;
	@Autowired
	private StatisticsOperationReportProducer statisticsProducer;

	@Override
	public void insertOperationGroupData(Calendar cal) {
		OperationMongoGroupEntityVO oegroup = new OperationMongoGroupEntityVO();
		// 插入统计日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		oegroup.setInsertDate(transferDateToInt(cal, sdf));

		// 要统计前一个月的数据，所以月份要减一
		cal.add(Calendar.MONTH, -1);
		sdf = new SimpleDateFormat("yyyyMM");
		oegroup.setStatisticsMonth(transferDateToInt(cal, sdf));

		// 投资人按照地域分布
		List<TenderCityCountVO> cityGroup = amTradeClient.getTenderCityGroupBy(getLastDay(cal));
		Map<Integer, String> cityMap = cityGrouptoMap(cityGroup);
		oegroup.setInvestorRegionMap(cityMap);
		// Gson gson=new Gson();
		// String json = gson.toJson(cityGroup);
		// List<com.hyjf.mongo.operationreport.entity.TenderCityCount>
		// list=gson.fromJson(json, new
		// TypeToken<List<com.hyjf.mongo.operationreport.entity.TenderCityCount>>(){}.getType()
		// );

		// 投资人按照性别分布
		List<TenderSexCountVO> sexGroup = amTradeClient.getTenderSexGroupBy(getLastDay(cal));
		Map<Integer, Integer> sexMap = sexGrouptoMap(sexGroup);
		oegroup.setInvestorSexMap(sexMap);

		// 投资人按照年龄分布
		Map<Integer, Integer> ageMap = new HashMap<Integer, Integer>();
		int age = amTradeClient.getTenderAgeByRange(getLastDay(cal), 0, OperationMongoGroupEntityVO.ageRange1);
		ageMap.put(OperationMongoGroupEntityVO.ageRange1, age);
		age = amTradeClient.getTenderAgeByRange(getLastDay(cal), OperationMongoGroupEntityVO.ageRange1,
				OperationMongoGroupEntityVO.ageRange2);
		ageMap.put(OperationMongoGroupEntityVO.ageRange2, age);
		age = amTradeClient.getTenderAgeByRange(getLastDay(cal), OperationMongoGroupEntityVO.ageRange2,
				OperationMongoGroupEntityVO.ageRange3);
		ageMap.put(OperationMongoGroupEntityVO.ageRange3, age);
		age = amTradeClient.getTenderAgeByRange(getLastDay(cal), OperationMongoGroupEntityVO.ageRange3,
				OperationMongoGroupEntityVO.ageRange4);
		ageMap.put(OperationMongoGroupEntityVO.ageRange4, age);

		oegroup.setInvestorAgeMap(ageMap);
		try {
			statisticsProducer.messageSend(new MessageContent(MQConstant.STATISTICS_OPERATION_REPORT_TOPIC,
					System.currentTimeMillis() + "", JSON.toJSONBytes(oegroup)));
		} catch (MQException e) {
			logger.error("每月统计运营数据失败......", e);
		}

	}

	@Override
	public void insertOperationData(Calendar cal) {
		// 插入统计日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		// 要统计前一个月的数据，所以月份要减一
		cal.add(Calendar.MONTH, -1);
		sdf = new SimpleDateFormat("yyyyMM");
		OperationReportEntityVO oe = amTradeClient.getOperationReport(transferDateToInt(cal, sdf));
		if (oe == null) {
			oe = new OperationReportEntityVO();
		}
		oe.setInsertDate(transferDateToInt(cal, sdf));

		oe.setStatisticsMonth(transferDateToInt(cal, sdf));
//		System.out.println(sdf.format(cal.getTime()));
		// 月交易金额
		oe.setAccountMonth(amTradeClient.getAccountByMonth(getFirstDay(cal), getLastDay(cal)));
//		System.out.println(sdf.format(cal.getTime()));
		// 月交易笔数
		oe.setTradeCountMonth(amTradeClient.getTradeCountByMonth(getFirstDay(cal), getLastDay(cal)));
//		System.out.println(sdf.format(cal.getTime()));
		// 累计交易笔数
//		oe.setTradeCount(operationReportCustomizeMapper.getTradeCount());
//		System.out.println(sdf.format(cal.getTime()));
		//累计交易金额
//		oe.setTotalCount(operationReportCustomizeMapper.getTotalCount());
		//累计收益
//		oe.setTotalInterest(operationReportCustomizeMapper.getIotalInterest());
		//借贷笔数
		oe.setLoanNum(amTradeClient.getLoanNum(getLastDay(cal)));
		//人均投资金额
		double bb=amTradeClient.getInvestLastDate(getLastDay(cal));
		int aa=amTradeClient.getTenderCount(getLastDay(cal));
//		System.out.println(bb);
//		System.out.println(aa);
//		System.out.println(bb/aa);
		oe.setPerInvest((int)Math.ceil(bb/aa));
		//平均满标时间
		oe.setFullBillTimeCurrentMonth(amTradeClient.getFullBillAverageTime(getLastDay(cal)));
//		System.out.println(sdf.format(cal.getTime()));
		//投资人总数
		oe.setTenderCount(amTradeClient.getTenderCount(getLastDay(cal)));
//		System.out.println(sdf.format(cal.getTime()));
		//代偿金额
		oe.setWillPayMoney(amTradeClient.getRepayTotal(getLastDay(cal)));

		BorrowUserStatisticVO borrowUserStatistic = this.selectBorrowUserStatistic();
		// 累计借款人
		oe.setBorrowuserCountTotal(borrowUserStatistic.getBorrowuserCountTotal());
		// 当前投资人
		oe.setBorrowuserCountCurrent(borrowUserStatistic.getBorrowuserCountCurrent());
		// 当前投资人
		oe.setTenderuserCountCurrent(borrowUserStatistic.getTenderuserCountCurrent());
		// 最大单一借款人待还金额占比
		oe.setBorrowuserMoneyTopone(borrowUserStatistic.getBorrowuserMoneyTopone().divide(borrowUserStatistic.getBorrowuserMoneyTotal(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP));
		// 前十大借款人待还金额占比
		oe.setBorrowuserMoneyTopten(borrowUserStatistic.getBorrowuserMoneyTopten().divide(borrowUserStatistic.getBorrowuserMoneyTotal(), 4, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_UP));

	}

	/**
	 * 时间转化
	 * 
	 * @param cl
	 * @param sdf
	 * @return
	 */
	public int transferDateToInt(Calendar cl, SimpleDateFormat sdf) {
		// SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		String c = sdf.format(cl.getTime());
		int date = Integer.parseInt(c);
		return date;
	}

	/**
	 * 通过输入的日期，获取这个日期所在月份的最后一天
	 *
	 * @param cal
	 * @return
	 */
	public static Date getLastDay(Calendar cal) {
		// 12小时显示方式
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hh:mm:ss");
		// 24小时显示方式
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
		// System.out.println(sdf.format(cal.getTime()));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		// System.out.println(sdf.format(cal.getTime()));
		// cal.set(Calendar.AM_PM, Calendar.PM);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		// System.out.println(sdf.format(cal.getTime()));
		return cal.getTime();
		// return GetDate.getYUEMO(cal.getTime());
	}

	private Map<Integer, String> cityGrouptoMap(List<TenderCityCountVO> list) {
		Map<Integer, String> map = new HashMap<Integer, String>();
		Iterator<TenderCityCountVO> iter = list.iterator();
		while (iter.hasNext()) {
			TenderCityCountVO tcity = iter.next();
			map.put(tcity.getCount(), tcity.getCount() + ":" + tcity.getName());
		}
		return map;
	}

	private Map<Integer, Integer> sexGrouptoMap(List<TenderSexCountVO> list) {
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Iterator<TenderSexCountVO> iter = list.iterator();
		while (iter.hasNext()) {
			TenderSexCountVO sex = iter.next();
			map.put(sex.getSex(), sex.getCount());
		}
		return map;
	}

	/**
	 * 通过输入的日期，获取这个日期所在月的第一天
	 *
	 * @param cal
	 * @return
	 */
	public static Date getFirstDay(Calendar cal) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
//		System.out.println(sdf.format(cal.getTime()));

		cal.set(Calendar.DAY_OF_MONTH,1);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

//		System.out.println(sdf.format(cal.getTime()));
		return cal.getTime();
//		return GetDate.getYUECHU(cal.getTime());
	}

	/**
	 * 获取借款用户数据
	 * @return
	 */
	public BorrowUserStatisticVO selectBorrowUserStatistic() {
		return amDataCollect.selectBorrowUserStatistic();
	}
}
