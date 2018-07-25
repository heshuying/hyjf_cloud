/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller;

import com.hyjf.am.response.message.OperationReportResponse;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.am.vo.datacollect.OperationReportVO;
import com.hyjf.am.vo.datacollect.QuarterOperationReportVO;
import com.hyjf.common.paginator.Paginator;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.service.report.OperationReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version OperationReportController, v0.1 2018/7/23 14:10
 */
@Api(value = "运营报告配置")
@RestController
@RequestMapping("/cs-message/operation_report")
public class OperationReportController extends BaseController {


	@Autowired
	private OperationReportService operationReportService;

	@ApiOperation(value = "运营报告配置", notes = "运营报告配置列表")
	@RequestMapping("/list")
	public OperationReportResponse list(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		Map<String, Object> map = new HashMap<String, Object>();
		Integer count = 0;
		map.put("typeSearch", request.getTypeSearch());
		if(StringUtils.isNotEmpty(request.getStartCreate())){
			map.put("timeStar",request.getStartCreate()+" 00:00:00");
		}
		if(StringUtils.isNotEmpty(request.getEndCreate())){
			map.put("timeEnd",request.getEndCreate()+" 23:59:59");
		}
		//根据类型切换查询逻辑
		if (StringUtils.isNotEmpty(request.getTypeSearch())) {
			String type = request.getTypeSearch();
			//1到12月份为month类型
			int typeInt = Integer.valueOf(type).intValue();
			if (typeInt <= 12) {
				map.put("monthType", typeInt);
				count = operationReportService.countRecordByMonth(map);
				response.setCount(count);
				if (count != null && count > 0) {
					if(request.getCurrPage()>0){
						Paginator paginator = new Paginator(request.getCurrPage(), count);
						map.put("limitStart", paginator.getOffset());
						map.put("limitEnd", paginator.getLimit());
					}
					List<OperationReportVO> recordList = this.operationReportService
							.getRecordListByMonth(map);
					response.setResultList(recordList);
				}
			} else if (typeInt == 13 || typeInt == 14) { //季度
				if (typeInt == 13) {
					map.put("quarterType", 1);
				} else if (typeInt == 14) {
					map.put("quarterType", 3);
				}
				count = operationReportService.countRecordByQuarter(map);
				response.setCount(count);
				if (count != null && count > 0) {
					if(request.getCurrPage()>0){
						Paginator paginator = new Paginator(request.getCurrPage(), count);
						map.put("limitStart", paginator.getOffset());
						map.put("limitEnd", paginator.getLimit());
					}
					List<OperationReportVO> recordList = this.operationReportService
							.getRecordListByQuarter(map);
					response.setResultList(recordList);
				}
			} else if (typeInt == 15) { //半年
				count = operationReportService.countRecordByHalfYear(map);
				response.setCount(count);
				if (count != null && count > 0) {
					if(request.getCurrPage()>0){
						Paginator paginator = new Paginator(request.getCurrPage(), count);
						map.put("limitStart", paginator.getOffset());
						map.put("limitEnd", paginator.getLimit());
					}
					List<OperationReportVO> recordList = this.operationReportService
							.getRecordListByHalfYear(map);
					response.setResultList(recordList);
				}
			} else if (typeInt == 16) {  //全年
				count = operationReportService.countRecordByYear(map);
				response.setCount(count);
				if (count != null && count > 0) {
					if(request.getCurrPage()>0){
						Paginator paginator = new Paginator(request.getCurrPage(), count);
						map.put("limitStart", paginator.getOffset());
						map.put("limitEnd", paginator.getLimit());
					}
					List<OperationReportVO> recordList = this.operationReportService
							.getRecordListByYear(map);
					response.setResultList(recordList);
				}
			}

		} else {
			//查询所有数据
			count = operationReportService.countRecord(map);
			response.setCount(count);
			if (count != null && count > 0) {
				if(request.getCurrPage()>0){
					Paginator paginator = new Paginator(request.getCurrPage(), count);
					map.put("limitStart", paginator.getOffset());
					map.put("limitEnd", paginator.getLimit());
				}
				List<OperationReportVO> recordList = this.operationReportService
						.getRecordList(map);
			response.setResultList(recordList);
			}
		}
		return response;
	}
	@ApiOperation(value = "获取已发布运营报告列表", notes = "获取已发布运营报告列表-app和web端使用")
	@RequestMapping("/listbyrelease")
	public OperationReportResponse listByRelease(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = operationReportService.getRecordListByReleaseJson(request);
		return response;
	}

	@ApiOperation(value = "获取已发布运营报告列表", notes = "获取已发布运营报告列表-app和web端使用")
	@RequestMapping("/reportinfo/{id}")
	public OperationReportResponse reportInfo(@PathVariable String id) {
		OperationReportResponse response = operationReportService.reportInfo(id);
		return response;
	}

	/**
	 *
	 * @Description  根据id查询运营报告
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/selectdetail/{id}")
	public OperationReportResponse selectDetail(@PathVariable String id) {
		OperationReportResponse response = operationReportService.selectOperationreportCommon(id);
		return response;
	}
	/**
	 *
	 * @Description  月度运营报告新增修改
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/insertmonthaction")
	public OperationReportResponse insertMonthAction(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			if (StringUtils.isBlank(operationReport.getId())) {
				//月度新增
				response = operationReportService.insertMonthlyOperationReport(request);
			} else {
				//修改月度报告
				response = operationReportService.updateMonthOperationReport(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  逻辑删除
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/delete/{id}")
	public OperationReportResponse delete(@PathVariable String id) {
		OperationReportResponse response = new OperationReportResponse();
		if(StringUtils.isNotEmpty(id)){
			OperationReportVO record = new OperationReportVO();
			record.setIsDelete(1);
			record.setId(id);
			int result = operationReportService.updateByPrimaryKeySelective(record);
			if(result==0){
				response.setRtn("0");
				response.setMessage("成功");
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  发布
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/publish")
	public OperationReportResponse publish(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO record = new OperationReportVO();
		int nowTime = GetDate.getMyTimeInMillis();
		if(request.getOperationReport()==null||request.getOperationReport().getId()==null){
			response.setRtn("1");
			response.setMessage("失败");
			return response;
		}
		record.setId(request.getOperationReport().getId());
		if (request.getOperationReport().getIsRelease() == 1) {
			record.setIsRelease(0);//发布切换为未发布
		} else {
			record.setIsRelease(1);//未发布切换为发布
		}
		record.setUpdateTime(nowTime);
		record.setReleaseTime(nowTime);
		Integer result = operationReportService.updateByPrimaryKeySelective(record);
		if (result == 1) {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}
	/**
	 *
	 * @Description  季度运营报告新增
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/insertquarteraction")
	public OperationReportResponse insertQuarterAction(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		QuarterOperationReportVO quarterOperationReport = request.getQuarterOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			Integer operationReportType = operationReport.getOperationReportType();
			if (StringUtils.isBlank(id)) {
				//第一季度5，第三季度6对应的季度运营报告类型1,3
				if (operationReportType == 5 || operationReportType == 6) {
					//设置运营报告中的运营报告类型，1月度，2季度，3半年，4年
					operationReport.setOperationReportType(2);
					request.setOperationReport(operationReport);
					//新增季度报告,
					if (operationReportType == 5) {
						quarterOperationReport.setQuarterType(1);
						request.setQuarterOperationReport(quarterOperationReport);
					}
					if (operationReportType == 6) {
						quarterOperationReport.setQuarterType(3);
						request.setQuarterOperationReport(quarterOperationReport);
					}
				}
				response = operationReportService.insertQuarterOperationReport(request);
			} else {
				//修改季度报告
				response = operationReportService.updateQuarterOperationReport(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  上半年度运营报告新增修改
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/inserthalfyearaction")
	public OperationReportResponse insertHalfYearAction(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		QuarterOperationReportVO quarterOperationReport = request.getQuarterOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			if (StringUtils.isBlank(id)) {
				operationReport.setOperationReportType(3);
				//新增上半年报告,
				operationReportService.insertHalfYearOperationReport(request);
			} else {
				//修改上半年度报告
				operationReportService.updateHalfYearOperationReport(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  年度运营报告新增
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/insertyearaction")
	public OperationReportResponse insertYearAction(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		QuarterOperationReportVO quarterOperationReport = request.getQuarterOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			if (StringUtils.isBlank(id)) {
				//新增年度报告
				response = operationReportService.insertYearOperationReport(request);
			} else {
				//修改年度报告
				response = operationReportService.updateYearOperationReport(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  月度新增修改页面预览
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/monthpreview")
	public OperationReportResponse monthPreview(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			if (StringUtils.isBlank(id)) {
				//月度新增预览
				response = operationReportService.insertMonthlyOperationReportPreview(request);
			} else {
				//修改月度报告预览
				response = operationReportService.updateMonthOperationReportPreview(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}
	/**
	 *
	 * @Description  年度新增修改页面预览
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/yearpreview")
	public OperationReportResponse yearPreview(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			if (StringUtils.isBlank(id)) {
				//新增年度报告
				response = operationReportService.insertYearlyOperationReportPreview(request);
			} else {
				//修改年度报告
				response = operationReportService.updateYearOperationReportPreview(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  季度新增修改页面预览
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/quarterpreview")
	public OperationReportResponse quarterPreview(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			if (StringUtils.isBlank(id)) {
				//季度新增页面预览
				Integer type= operationReport.getOperationReportType();
				response = operationReportService.insertQuarterOperationReportPreview(request,type);
			} else {
				//季度报告修改页面预览
				response = operationReportService.updateQuarterOperationReportPreview(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

	/**
	 *
	 * @Description  半年度新增修改页面预览
	 * @Date 14:08 2018/7/23
	 * @Param
	 * @return
	 */
	@PostMapping("/halfpreview")
	public OperationReportResponse Halfpreview(@RequestBody OperationReportRequest request) {
		OperationReportResponse response = new OperationReportResponse();
		OperationReportVO operationReport = request.getOperationReport();
		if(operationReport!=null){
			String year = operationReport.getYear();
			if(StringUtils.isBlank(year)){
				operationReport.setYear(String.valueOf(GetDate.getYear()));
			}
			String id = operationReport.getId();
			if (StringUtils.isBlank(id)) {
				//季度新增页面预览
				response = operationReportService.insertHalfYearOperationReportPreview(request);
			} else {
				//季度报告修改页面预览
				response = operationReportService.updateHalfYearOperationReportPreview(request);
			}
		}else {
			response.setRtn("1");
			response.setMessage("失败");
		}
		return response;
	}

}
