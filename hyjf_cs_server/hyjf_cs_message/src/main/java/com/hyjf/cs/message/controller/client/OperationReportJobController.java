/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.StringResponse;
import com.hyjf.am.response.message.BorrowUserStatisticResponse;
import com.hyjf.am.response.message.OperationReportEntityResponse;
import com.hyjf.am.vo.datacollect.BorrowUserStatisticVO;
import com.hyjf.am.vo.datacollect.OperationReportEntityVO;
import com.hyjf.am.vo.message.OperationReportJobBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.ic.BorrowUserStatistic;
import com.hyjf.cs.message.bean.ic.OperationReport;
import com.hyjf.cs.message.mq.base.MessageContent;
import com.hyjf.cs.message.mq.producer.OperationReportJobAdminProducer;
import com.hyjf.cs.message.service.report.OperationReportJobNewService;
import com.hyjf.cs.message.service.report.PlatDataStatisticsService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tanyy
 * @version OperationReportController, v0.1 2018/7/23 14:10
 */
@ApiIgnore
@RestController
@RequestMapping("/cs-message/operation_report_job")
public class OperationReportJobController extends BaseController {

	@Autowired
	private OperationReportJobNewService operationReportJobNewService;

	@Autowired
	private PlatDataStatisticsService platDataStatisticsService;
	@Autowired
	OperationReportJobAdminProducer operationReportJobAdminProducer;

	@ApiOperation(value = "统计报表-根据当前时间要获取到上个月的数据", notes = "根据当前时间要获取到上个月的数据")
	@RequestMapping("/countOperationReport")
	public StringResponse countOperationReport() {
		logger.info("开始发送运营报告的mq...");
		StringResponse response = new StringResponse();
		OperationReportJobBean bean = new OperationReportJobBean();
		Calendar cal = Calendar.getInstance();
		bean.setCalendar(cal);
		int lastMonth = getLastMonth(cal);
		bean.setLastMonth(lastMonth);
		String year = String.valueOf(GetDate.getYear());
		String month = GetDate.getMonth();
		bean.setYear(year);
		bean.setMonth(month);
		logger.info("生成报告year="+year+"生成报告month="+month);
		try {
			operationReportJobAdminProducer.messageSend(new MessageContent(MQConstant.OPERATIONREPORT_JOB_ADMIN_TOPIC,
					System.currentTimeMillis() + "", JSONObject.toJSONBytes(bean)));
		} catch (MQException e) {
			logger.error("运营报告的mq发送失败......", e);
			return new StringResponse("error");
		}

	/*	Calendar cal = Calendar.getInstance();

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

		logger.info("完成 插入统计数据到mongodb...");*/
		return response;
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

	@RequestMapping("/getBorrowUserStatistic")
	public BorrowUserStatisticResponse getBorrowUserStatistic() {
		BorrowUserStatisticResponse response = new BorrowUserStatisticResponse();
		BorrowUserStatisticVO vo = new BorrowUserStatisticVO();
		BorrowUserStatistic borrowUserStatistic = operationReportJobNewService.selectBorrowUserStatistic();
		if(borrowUserStatistic != null){

			BeanUtils.copyProperties(borrowUserStatistic,vo);
		}
		response.setResult(vo);
		return response;
	}

	@RequestMapping("/findOneOperationMongDaoByMonth/{month}")
	public OperationReportEntityResponse getBorrowUserStatistic(@PathVariable Integer month) {
		OperationReportEntityResponse response = new OperationReportEntityResponse();
		OperationReportEntityVO vo = new OperationReportEntityVO();
		OperationReport operationReport = platDataStatisticsService.findOneOperationMongDaoByMonth(month);
		if(operationReport != null){

			BeanUtils.copyProperties(operationReport,vo);
		}
		response.setResult(vo);
		return response;
	}
	/**
	 * 获得当前月份的上个月日期
	 * @return
	 */
	public static int getLastMonth(Calendar calendar){
		SimpleDateFormat sdf = new SimpleDateFormat("MM");
		calendar.setTime(new Date());//设置当前日期
		calendar.add(Calendar.MONTH, -1);//月份减一
		//输出上个月的日期
		int lastMonth = Integer.valueOf(sdf.format( calendar.getTime()));
		return lastMonth;
	}
}
