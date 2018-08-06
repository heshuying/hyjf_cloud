/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.report.OperationReportJobService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tanyy
 * @version OperationReportController, v0.1 2018/7/23 14:10
 */
@Api(value = "定时任务统计运营报告报表")
@RestController
@RequestMapping("/cs-message/operation_report_job")
public class OperationReportJobController extends BaseController {

	@Autowired
	private OperationReportJobService operationReportJobService;

	@ApiOperation(value = "统计报表-根据当前时间要获取到上个月的数据", notes = "根据当前时间要获取到上个月的数据")
	@RequestMapping("/countOperationReport")
	public void countOperationReport() {
		logger.info("开始 从数据库获取运营报告的数据...");
//		boolean flag = RedisUtils.tranactionSet(RedisConstants.Statistics_Operation_Report);
//		if(!flag){
//			return;
//		}
		Calendar cal = Calendar.getInstance();

		try {
			// 插入性别，性别 ，区域的统计信息
			operationReportJobService.insertOperationGroupData(cal);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.info("重复插入，可忽略");
		} catch (com.mongodb.DuplicateKeyException e) {
			logger.info("重复插入，可忽略");
		} catch (Exception e) {
			logger.info("重复插入，可忽略");
		}

		// 插入投资类的信息
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
			cal = Calendar.getInstance();
			operationReportJobService.insertOperationData(cal);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			logger.info("重复插入，可忽略");
		} catch (com.mongodb.DuplicateKeyException e) {
			logger.info("重复插入，可忽略");
		} catch (Exception e) {
			logger.info("重复插入，可忽略");
		}


		// 插入前台界面的运营报告(月，季，半年，全年)
		try {
			String year = String.valueOf(GetDate.getYear());
			String month = GetDate.getMonth();

			//输出上个月的日期
			int lastMonth = getLastMonth();

			//每个月月初的1号，自动统计出上一个月的数据，统计顺序依次是：
			//1月，2月，第一季度，4月，5月，上半年，7月，8月，第三季度，10月，11月，年度报告
			if(lastMonth == 12){
				operationReportJobService.setYearReport(year,month);
			} else if(lastMonth == 6 ){
				operationReportJobService.setHalfYearReport(year,month);
			}else if(lastMonth == 3 || lastMonth == 9 ){
				operationReportJobService.setQuarterReport(year,month);
			}else{
				operationReportJobService.setMonthReport(year,month);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		RedisUtils.del(RedisConstants.Statistics_Operation_Report);

		logger.info("完成 插入统计数据到mongodb...");
	}

	/**
	 * 获得当前月份的上个月日期
	 * @return
	 */
	public static int getLastMonth(){
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		Calendar calendar = Calendar.getInstance();//日历对象
		calendar.setTime(new Date());//设置当前日期
		calendar.add(Calendar.MONTH, -1);//月份减一
		//输出上个月的日期
		int lastMonth = Integer.valueOf(sdf.format( calendar.getTime()));
		return lastMonth;
	}
}
