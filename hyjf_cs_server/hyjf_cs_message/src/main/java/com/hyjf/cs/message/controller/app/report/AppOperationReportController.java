/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.controller.app.report;

import com.alibaba.fastjson.JSONObject;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author tanyy
 * @version OperationReportController, v0.1 2018/8/3 14:10
 */
@Api(value = "运营报告配置",description ="app运营报告配置接口")
@RestController
@RequestMapping("/hyjf-app/report")
public class AppOperationReportController extends BaseController {

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
	@RequestMapping("/reportInfo/{id}")
	public OperationReportResponse reportInfo(@PathVariable String id) {
		OperationReportResponse response = operationReportService.reportInfo(id);
		return response;
	}

}
