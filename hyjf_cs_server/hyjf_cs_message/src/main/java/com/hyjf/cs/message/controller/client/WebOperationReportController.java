/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.OperationReportColumnEntity;
import com.hyjf.cs.message.service.report.OperationReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanyy
 * @version OperationReportController, v0.1 2018/8/3 14:10
 */
@Api(tags = "web端-运营报告配置接口")
@RestController
@RequestMapping("/hyjf-web/report")
public class WebOperationReportController extends BaseController {

	@Autowired
	private OperationReportService operationReportService;

	@ApiOperation(value = "获取已发布运营报告列表", notes = "获取已发布运营报告列表")
	@PostMapping("/reportList")
	public WebResult<Object> listByRelease(@RequestParam(value = "releaseFlag",required = false) Integer releaseFlag,
										   @RequestParam(value = "paginatorPage",required = false,defaultValue = "0") Integer paginatorPage) {
		WebResult result = new WebResult();
		OperationReportRequest request = new OperationReportRequest();
		request.setIsRelease(releaseFlag);
		request.setCurrPage(paginatorPage);
		JSONObject response = operationReportService.getRecordListByReleaseJson(request);
		if(response.get("success")=="success"){
			result.setData(response.get("recordList"));
		}
		return result;

	}
	@ApiOperation(value = "运营报告明细", notes = "运营报告明细")
	@GetMapping("/reportInfo/{id}")
	public WebResult<Object> reportInfo(@PathVariable String id) {
		WebResult result = new WebResult();
		JSONObject response = operationReportService.reportInfo(id);
		if(response.get("success")!="success"){
			result.setStatus("1");
			result.setStatusDesc("失败");
		}
		return result;
	}

	@ApiOperation(value = "进入页面预览初始化", notes = "进入页面预览初始化")
	@GetMapping("/initMonthReport/{id}")
	public WebResult<Object> initMonthReport(@PathVariable String id) {
		WebResult result = new WebResult();
		OperationReportColumnEntity report = operationReportService.selectByPrimaryKey(id);
		if(report==null){
			result.setStatus("1");
			result.setStatusDesc("失败");
		}else {
			result.setData(report);
		}
		return result;
	}
}
