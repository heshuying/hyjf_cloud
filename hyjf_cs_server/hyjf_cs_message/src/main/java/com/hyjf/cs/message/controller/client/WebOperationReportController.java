/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.client;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.message.OperationReportRequest;
import com.hyjf.cs.common.controller.BaseController;
import com.hyjf.cs.message.bean.mc.OperationReportColumnEntity;
import com.hyjf.cs.message.service.report.OperationReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@GetMapping("/reportList")
	public JSONObject listByRelease(HttpServletRequest httpServletRequest) {
		OperationReportRequest request = new OperationReportRequest();
		String param1 =httpServletRequest.getParameter("isRelease");
		String param2 =httpServletRequest.getParameter("paginatorPage");
		request.setIsRelease(param1==null?null:Integer.valueOf(param1));
		request.setCurrPage(param2==null?0:Integer.valueOf(param2));
		JSONObject response = operationReportService.getRecordListByReleaseJson(request);
		return response;

	}
	@ApiOperation(value = "运营报告明细", notes = "运营报告明细")
	@GetMapping("/reportInfo/{id}")
	public JSONObject reportInfo(@PathVariable String id) {
		JSONObject response = operationReportService.reportInfo(id);
		return response;
	}

	@ApiOperation(value = "进入页面预览初始化", notes = "进入页面预览初始化")
	@GetMapping("/initMonthReport/{id}")
	public JSONObject initMonthReport(@PathVariable String id) {
		JSONObject jsonObject = new JSONObject();
		OperationReportColumnEntity report = operationReportService.selectByPrimaryKey(id);
		if(report!=null){
			jsonObject.put("success", "success");
			jsonObject.put("reportType",report.getOperationReportType());
		}else {
			jsonObject.put("success", "success");
			jsonObject.put("countIsZero", "暂无任何数据");
		}
		return jsonObject;
	}
}
