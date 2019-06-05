package com.hyjf.am.admin.mq.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.market.service.SellDailyService;
import com.hyjf.am.vo.market.SellDailyVO;
import com.hyjf.common.util.GetDate;

/**
 * @author xiasq
 * @version SellDailyDataHandler, v0.1 2019-04-08 14:26
 */
@Component
public class SellDailyDataHandler {
	private Logger logger = LoggerFactory.getLogger(SellDailyDataHandler.class);
	private static final String YYZX_DIVISION_NAME = "运营中心";
	private static final String QIANLE_DIVISION_NAME = "渠道";
	/**
	 * 查询所有分部
	 */
	private static final Integer QUERY_ALL_DIVISION_TYPE = 1;
	/**
	 * 上海运营中心-网络运营部 id:327
	 */
	private static final Integer QUERY_OC_THREE_DIVISION_TYPE = 2;
	/**
	 * 查询APP推广
	 */
	private static final Integer QUERY_APP_TYPE = 3;

	/**
	 * 千乐数据渠道编号
	 */
	@Value("${qianle.sourceid}")
	private String sourceId;

	@Autowired
	private SellDailyService sellDailyService;

	public SellDailyDataDto doHandler(int column, Date startTime, Date endTime) {
		logger.info("SellDailyDataHandler.doHandler start, sourceId is: {}", sourceId);
		// 按照一级分部，二级分部分组查询统计数据
		List<SellDailyVO> list = null;
		// 运营部
		SellDailyVO operationSellDaily = null;
		// app推广单独查询
		//List<SellDailyVO> appSellDailyList = null;
		// 千乐数据
		SellDailyVO qlSellDaily = null;
		// 债转数
		List<SellDailyVO> creditSellDailyList = null;

		// 辅助参数
		List<SellDailyVO> operSellDailyList = null;
		switch (column) {
		case 1:
			list = sellDailyService.countTotalInvestOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalInvestOnMonth(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);
			//appSellDailyList = sellDailyService.countTotalInvestOnMonth(startTime, endTime, QUERY_APP_TYPE);
			qlSellDaily = sellDailyService.countTotalInvestOnMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 2:
			list = sellDailyService.countTotalRepayOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalRepayOnMonth(startTime, endTime, QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countTotalRepayOnMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;

            creditSellDailyList = sellDailyService.countTotalCredit(startTime, endTime);
			break;
		case 3:
			list = sellDailyService.countTotalInvestOnPreviousMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalInvestOnPreviousMonth(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);
			//appSellDailyList = sellDailyService.countTotalInvestOnPreviousMonth(startTime, endTime, QUERY_APP_TYPE);

			qlSellDaily = sellDailyService.countTotalInvestOnPreviousMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 5:
			list = sellDailyService.countTotalWithdrawOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalWithdrawOnMonth(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countTotalWithdrawOnMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 7:
			list = sellDailyService.countTotalRechargeOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalRechargeOnMonth(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countTotalRechargeOnMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 8:
			list = sellDailyService.countTotalAnnualInvestOnMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalAnnualInvestOnMonth(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);
			//appSellDailyList = sellDailyService.countTotalAnnualInvestOnMonth(startTime, endTime, QUERY_APP_TYPE);

			qlSellDaily = sellDailyService.countTotalAnnualInvestOnMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 9:
			list = sellDailyService.countTotalAnnualInvestOnPreviousMonth(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalAnnualInvestOnPreviousMonth(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);
			//appSellDailyList = sellDailyService.countTotalAnnualInvestOnPreviousMonth(startTime, endTime,
					//QUERY_APP_TYPE);

			qlSellDaily = sellDailyService.countTotalAnnualInvestOnPreviousMonthQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 11:
			list = sellDailyService.countTotalTenderYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalTenderYesterday(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);
			//appSellDailyList = sellDailyService.countTotalTenderYesterday(startTime, endTime, QUERY_APP_TYPE);

			qlSellDaily = sellDailyService.countTotalTenderYesterdayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 12:
			list = sellDailyService.countTotalRepayYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalRepayYesterday(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countTotalRepayYesterdayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 13:
			list = sellDailyService.countTotalAnnualInvestYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalAnnualInvestYesterday(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);
			//appSellDailyList = sellDailyService.countTotalAnnualInvestYesterday(startTime, endTime, QUERY_APP_TYPE);

			qlSellDaily = sellDailyService.countTotalAnnualInvestYesterdayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 14:
			list = sellDailyService.countTotalWithdrawYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalWithdrawYesterday(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countTotalWithdrawYesterdayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 15:
			list = sellDailyService.countTotalRechargeYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countTotalRechargeYesterday(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countTotalRechargeYesterdayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 17:
			list = sellDailyService.countNoneRepayToday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countNoneRepayToday(startTime, endTime, QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countNoneRepayTodayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;

			creditSellDailyList = sellDailyService.countTotalCredit(startTime, endTime);
			break;
		case 18:
			list = sellDailyService.countRegisterTotalYesterday(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countRegisterTotalYesterday(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countRegisterTotalYesterdayQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 19:
			list = sellDailyService.countRechargeGt3000UserNum(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countRechargeGt3000UserNum(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countRechargeGt3000UserNumQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 20:
			list = sellDailyService.countInvestGt3000UserNum(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countInvestGt3000UserNum(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countInvestGt3000UserNumQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		case 21:
			list = sellDailyService.countInvestGt3000MonthUserNum(startTime, endTime, QUERY_ALL_DIVISION_TYPE);
			operSellDailyList = sellDailyService.countInvestGt3000MonthUserNum(startTime, endTime,
					QUERY_OC_THREE_DIVISION_TYPE);
			operationSellDaily = CollectionUtils.isEmpty(operSellDailyList) ? new SellDailyVO(null, null)
					: mergeSellDaily(operSellDailyList);

			qlSellDaily = sellDailyService.countInvestGt3000MonthUserNumQl(startTime, endTime, sourceId);
			qlSellDaily = qlSellDaily == null ? new SellDailyVO(null, null) : qlSellDaily;
			break;
		}

		if (logger.isDebugEnabled()) {
			if (!CollectionUtils.isEmpty(list)) {
				logger.debug("column is: {}, list: {}", column, JSONObject.toJSONString(list));
			}
		}

		SellDailyDataDto dto = new SellDailyDataDto();
		dto.setList(list);
		//dto.setAppSellDailyList(appSellDailyList);
		dto.setCreditSellDaily(creditSellDailyList);

		if (operationSellDaily != null) {
			dto.setOperationSellDaily(setValue(operationSellDaily, YYZX_DIVISION_NAME, "网络运营部", 2, 0));
		}

		if (qlSellDaily != null) {
			logger.info("千乐数据qlSellDaily: {}", qlSellDaily.print());
			dto.setQlSellDaily(setValue(qlSellDaily, QIANLE_DIVISION_NAME, "千乐", 2, 0));
		}
		return dto;
	}

	private SellDailyVO setValue(SellDailyVO ocSellDaily, String primaryDivision, String twoDivision, int drawOrder, int storeNum) {
		ocSellDaily.setDateStr(GetDate.getFormatDateStr());
		ocSellDaily.setPrimaryDivision(primaryDivision);
		ocSellDaily.setTwoDivision(twoDivision);
		ocSellDaily.setDrawOrder(drawOrder);
		ocSellDaily.setStoreNum(storeNum);
		return ocSellDaily;
	}

	/**
	 * 多行合并 - 合并上海运营中心和青岛运营中心的数据
	 *
	 * @param sellDailyList
	 * @return
	 */
	private SellDailyVO mergeSellDaily(List<SellDailyVO> sellDailyList) {
		SellDailyVO sellDailyVO = new SellDailyVO(null, null);
		for (SellDailyVO vo : sellDailyList) {
			if (vo.getInvestTotalMonth() != null) {
				sellDailyVO.setInvestTotalMonth(sellDailyVO.getInvestTotalMonth().add(vo.getInvestTotalMonth()));
			}
			if (vo.getRepaymentTotalMonth() != null) {
				sellDailyVO
						.setRepaymentTotalMonth(sellDailyVO.getRepaymentTotalMonth().add(vo.getRepaymentTotalMonth()));
			}
			if (vo.getInvestTotalPreviousMonth() != null) {
				sellDailyVO.setInvestTotalPreviousMonth(
						sellDailyVO.getInvestTotalPreviousMonth().add(vo.getInvestTotalPreviousMonth()));
			}
			if (vo.getWithdrawTotalMonth() != null) {
				sellDailyVO.setWithdrawTotalMonth(sellDailyVO.getWithdrawTotalMonth().add(vo.getWithdrawTotalMonth()));
			}
			if (vo.getRechargeTotalMonth() != null) {
				sellDailyVO.setRechargeTotalMonth(sellDailyVO.getRechargeTotalMonth().add(vo.getRechargeTotalMonth()));
			}

			if (vo.getInvestAnnualTotalMonth() != null) {
				sellDailyVO.setInvestAnnualTotalMonth(
						sellDailyVO.getInvestAnnualTotalMonth().add(vo.getInvestAnnualTotalMonth()));
			}

			sellDailyVO.setInvestRatioGrowth("");
			sellDailyVO.setWithdrawRate("");
			if (vo.getInvestAnnualTotalPreviousMonth() != null) {
				sellDailyVO.setInvestAnnualTotalPreviousMonth(
						sellDailyVO.getInvestAnnualTotalPreviousMonth().add(vo.getInvestAnnualTotalPreviousMonth()));
			}
			if (vo.getInvestTotalYesterday() != null) {
				sellDailyVO.setInvestTotalYesterday(
						sellDailyVO.getInvestTotalYesterday().add(vo.getInvestTotalYesterday()));
			}
			if (vo.getRepaymentTotalYesterday() != null) {
				sellDailyVO.setRepaymentTotalYesterday(
						sellDailyVO.getRepaymentTotalYesterday().add(vo.getRepaymentTotalYesterday()));
			}
			if (vo.getInvestAnnualTotalYesterday() != null) {
				sellDailyVO.setInvestAnnualTotalYesterday(
						sellDailyVO.getInvestAnnualTotalYesterday().add(vo.getInvestAnnualTotalYesterday()));
			}
			sellDailyVO.setInvestAnnularRatioGrowth("");

			if (vo.getWithdrawTotalYesterday() != null) {
				sellDailyVO.setWithdrawTotalYesterday(
						sellDailyVO.getWithdrawTotalYesterday().add(vo.getWithdrawTotalYesterday()));
			}
			if (vo.getRechargeTotalYesterday() != null) {
				sellDailyVO.setRechargeTotalYesterday(
						sellDailyVO.getRechargeTotalYesterday().add(vo.getRechargeTotalYesterday()));
			}
			if (vo.getNonRepaymentToday() != null) {
				sellDailyVO.setNonRepaymentToday(sellDailyVO.getNonRepaymentToday().add(vo.getNonRepaymentToday()));
			}

			sellDailyVO.setNetCapitalInflowYesterday(BigDecimal.ZERO);
			sellDailyVO.setRegisterTotalYesterday(
					sellDailyVO.getRegisterTotalYesterday() + vo.getRegisterTotalYesterday());
			sellDailyVO
					.setRechargeGt3000UserNum(sellDailyVO.getRechargeGt3000UserNum() + vo.getRechargeGt3000UserNum());
			sellDailyVO.setInvestGt3000UserNum(sellDailyVO.getInvestGt3000UserNum() + vo.getInvestGt3000UserNum());
			sellDailyVO.setInvestGt3000MonthUserNum(
					sellDailyVO.getInvestGt3000MonthUserNum() + vo.getInvestGt3000MonthUserNum());
		}
		return sellDailyVO;
	}

}
