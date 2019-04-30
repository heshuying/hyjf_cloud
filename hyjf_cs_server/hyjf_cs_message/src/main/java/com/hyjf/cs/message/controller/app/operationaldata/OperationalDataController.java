package com.hyjf.cs.message.controller.app.operationaldata;


import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.report.OperationGroupReport;
import com.hyjf.cs.message.bean.ic.report.OperationReport;
import com.hyjf.cs.message.bean.ic.SubEntity;
import com.hyjf.cs.message.service.report.PlatDataStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author tanyy
 * @version OperationalDataController, v0.1 2018/8/6 17:39
 */
@Api(tags ="app端-运营数据")
@RestController
@RequestMapping("/hyjf-app/find/operationalData")
public class OperationalDataController {

	private Logger logger = LoggerFactory.getLogger(OperationalDataController.class);

	@Autowired
	private PlatDataStatisticsService platDataStatisticsService;

	/**
	 * 获取平台实时数据
	 *
	 * @return
	 */
	@ApiOperation(value = "app运营数据第一页面接口数据获取", notes = "app运营数据第一页面接口数据获取")
	@GetMapping ("/getPlatformRealTimeData")
	@ResponseBody
	public JSONObject getPlatformRealTimeData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {

			OperationReport oe = platDataStatisticsService.findOneOperationReportEntity();
			if(oe==null){
				result.put("status", "999");
				result.put("statusDesc", "暂无任何数据");
				return result;
			}
			JSONObject info = new JSONObject();
			//累计交易笔数(实时)
			info.put("CumulativeTransactionNum", platDataStatisticsService.selectTotalTradeSum());
			//累计交易总额(实时)
			info.put("CumulativeTransactionTotal", platDataStatisticsService.selectTotalInvest());
			//累计为用户赚取收益(实时)
			info.put("CumulativeUserIncome", platDataStatisticsService.selectTotalInterest());

			//加上统计月份
			int staticMonth=oe.getStatisticsMonth();
			info.put("CutOffDate", transferIntToDate(staticMonth));

			// 获取12个月的数据

			List<OperationReport> list = platDataStatisticsService.findOperationReportEntityList();

			List<String> xlist = new ArrayList<String>();
			List<String> yMoneytlist = new ArrayList<String>();
			List<String> ycountlist = new ArrayList<String>();
			for (int i = list.size() - 1; i >= 0; i--) {
				xlist.add(oe.format(String.valueOf(list.get(i).getStatisticsMonth())));
				yMoneytlist.add(trim(list.get(i).getAccountMonth().intValue(),100000000));
				ycountlist.add(trim(list.get(i).getTradeCountMonth(),10000));
			}

			JSONObject detail = new JSONObject();
			detail.put("XAxis", xlist.toString());
			detail.put("YAxis", yMoneytlist);
			info.put("MonthlyTransactionTotlaList", detail);

			detail = new JSONObject();
			detail.put("XAxis", xlist.toString());
			detail.put("YAxis", ycountlist);
			info.put("MonthlyTransactionNumList", detail);

			//安全运营天数
			Integer totalDays = GetDate.countDate(GetDate.stringToDate("2003-04-13 00:00:00"), new Date());
			info.put("survivalYears",totalDays/365);
			info.put("survivalDays",totalDays%365);

			result.put("info", info);

		} catch (Exception e) {
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}

	/**
	 * 获取借款&&出借数据
	 *
	 * @return
	 */
	@ApiOperation(value = "app运营数据第二页面和第三页面数据统计", notes = "app运营数据第二页面和第三页面数据统计")
	@GetMapping("/getLoanInvestData")
	@ResponseBody
	public JSONObject getLoanInvestData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			OperationReport oe = platDataStatisticsService.findOneOperationReportEntity();
			JSONObject detail = new JSONObject();
			if(oe != null){
				detail.put("CumulativeTransactionTotal", oe.getWillPayMoney());
				detail.put("LoanNum", oe.getLoanNum());

				detail.put("investorTotal", oe.getTenderCount());
				detail.put("perInvestTotal", oe.getPerInvest());
				float time = oe.getFullBillTimeCurrentMonth();

				detail.put("fullScaleHour", oe.getHour(time));
				detail.put("fullScaleMinute", oe.getMinutes(time));
				detail.put("fullScaleSecond", oe.getSeconds(time));
				detail.put("TotalBorrower", oe.getBorrowUserCountTotal());
				detail.put("NowBorrower", oe.getBorrowUserCountCurrent());
				detail.put("CurrentInvestor", oe.getTenderUserCountCurrent());
				detail.put("MaxBorrowerRate", oe.getBorrowUserMoneyTopOne());
				detail.put("Top10BorrowerRate", oe.getBorrowUserMoneyTopTen());
			}

			detail.put("relationshipTotal", 0);
			detail.put("relationshipNum", 0);
			detail.put("overdueTotal", 0);
			detail.put("overdueNum", 0);
			detail.put("overdue90Total", 0);
			detail.put("overdue90Num", 0);

			result.put("info", detail);

		} catch (Exception e) {
			logger.error("发生异常", e);
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}

	/**
	 * 获取出借人地域分布数据
	 *
	 * @return
	 */
	@ApiOperation(value = "app运营数据第四页面数据统计", notes = "app运营数据第四页面数据统计")
	@GetMapping("/getInvestorRegionData")
	@ResponseBody
	public JSONObject getInvestorRegionData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			OperationGroupReport oe = platDataStatisticsService.findOneOperationMongoGroupEntity();
			if(oe==null){
				result.put("status", "999");
				result.put("statusDesc", "暂无任何数据");
				return result;
			}
			// 获取出借人区域信息
			Map<Integer, String> cityMap = oe.getInvestorRegionMap();
			List<SubEntity> list = oe.orgnizeData(cityMap);
			List<SubEntity> sublist=oe.formatList(list);

			ObjectMapper mapper = new ObjectMapper();

			result.put("InvestorRegionList", mapper.writeValueAsString(sublist));
		} catch (Exception e) {
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}

	/**
	 * 获取出借人性别&&年龄数据
	 *
	 * @return
	 */

	@ApiOperation(value = "app运营数据第五页面数据统计", notes = "app运营数据第五页面数据统计")
	@GetMapping("/getInvestorSexAgeData")
	@ResponseBody
	public JSONObject getSexAgeData() {
		JSONObject result = new JSONObject();
		result.put("status", "000");
		result.put("statusDesc", "成功");
		try {
			JSONObject info = new JSONObject();
			OperationGroupReport oe = platDataStatisticsService.findOneOperationMongoGroupEntity();
			if(oe==null){
				result.put("status", "999");
				result.put("statusDesc", "暂无任何数据");
				return result;
			}
			// 出借人性别的分布
			Map<Integer, Integer> sexMap = oe.getInvestorSexMap();
			int maleCount = sexMap.get(OperationGroupReport.MALE);
			int femaleCount = sexMap.get(OperationGroupReport.FEMALE);
			float malePer = (float) maleCount * 100 / (maleCount + femaleCount);
			float femalePer = (float) femaleCount * 100 / (maleCount + femaleCount);
			info.put("InvestorRegionMenRate", oe.formatDate(malePer) + "%");
			info.put("InvestorRegionWoMenRate", oe.formatDate(femalePer) + "%");

			// 出借人年龄分布
			Map<Integer, Integer> ageMap = oe.getInvestorAgeMap();
			int r1 = ageMap.get(OperationGroupReport.ageRange1);
			int r2 = ageMap.get(OperationGroupReport.ageRange2);
			int r3 = ageMap.get(OperationGroupReport.ageRange3);
			int r4 = ageMap.get(OperationGroupReport.ageRange4);
			int total = r1 + r2 + r3 + r4;

			List<String> list = new ArrayList<String>();
			list.add(oe.getAge(OperationGroupReport.ageRange1Desc, OperationGroupReport.ageRange1Color,
					oe.formatDate((float) r1 * 100 / total)));
			list.add(oe.getAge(OperationGroupReport.ageRange2Desc, OperationGroupReport.ageRange2Color,
					oe.formatDate((float) r2 * 100 / total)));
			list.add(oe.getAge(OperationGroupReport.ageRange3Desc, OperationGroupReport.ageRange3Color,
					oe.formatDate((float) r3 * 100 / total)));
			list.add(oe.getAge(OperationGroupReport.ageRange4Desc, OperationGroupReport.ageRange4Color,
					oe.formatDate((float) r4 * 100 / total)));

			info.put("InvestorAgeList", list);

			result.put("info", info);
		} catch (Exception e) {
			result.put("status", "999");
			result.put("statusDesc", "失败");
		}

		return result;
	}


	/**
	 * 转化数字格式的日期
	 * @param date
	 * @return
	 */
	public  String transferIntToDate(int date ) {
		Calendar cl=Calendar.getInstance();
		String str=String.valueOf(date);
		int year=Integer.valueOf(str.substring(0,4));
		int month=Integer.valueOf(str.substring(4,6))-1;

		cl.set(Calendar.YEAR,year);
		cl.set(Calendar.MONTH,month);
		int lastDay = cl.getActualMaximum(Calendar.DAY_OF_MONTH);
		cl.set(Calendar.DAY_OF_MONTH, lastDay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		return sdf.format(cl.getTime());
	}

	public String trim(float input,int fenzi){
		return  BigDecimal.valueOf((float)input/fenzi).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
	}
}
