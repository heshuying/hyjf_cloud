/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.controller.front.statistics;

import com.hyjf.am.response.trade.OperationReportJobResponse;
import com.hyjf.am.resquest.trade.OperationReportJobRequest;
import com.hyjf.am.user.controller.BaseController;
import com.hyjf.am.user.service.extensioncenter.OperationReportJobCountService;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanyy
 * @version OperationReportJobController, v0.1 2018/7/23 14:10
 */
@Api(value = "定时任务统计运营报告报表统计接口")
@RestController
@RequestMapping("/am-user/batch/operation_report_job")
public class OperationReportJobController extends BaseController {

	@Resource
	private OperationReportJobCountService operationReportJobCountService;

	@RequestMapping("/countregistuser")
	public OperationReportJobResponse countRegistUser() {
		OperationReportJobResponse response = new OperationReportJobResponse();
		int count = operationReportJobCountService.countRegistUser();
		response.setCount(count);
		return response;
	}

	@RequestMapping("/sexcount")
	public OperationReportJobResponse sexCount(@RequestBody OperationReportJobRequest request) {
		OperationReportJobResponse response = new OperationReportJobResponse();
		List<OperationReportJobVO> list = operationReportJobCountService.getSexCount(request.getOperationReportJobVOList());
		response.setResultList(list);
		return response;
	}
    @RequestMapping("/agecount")
    public OperationReportJobResponse ageCount(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list = operationReportJobCountService.getAgeCount(request.getOperationReportJobVOList());
        response.setResultList(list);
        return response;
    }
    @RequestMapping("/usernames")
    public OperationReportJobResponse userNames(@RequestBody OperationReportJobRequest request) {
        OperationReportJobResponse response = new OperationReportJobResponse();
        List<OperationReportJobVO> list = operationReportJobCountService.getUserNames(request.getOperationReportJobVOList());
        response.setResultList(list);
        return response;
    }

	@RequestMapping("/userageandarea")
	public OperationReportJobResponse userAgeAndArea(@RequestBody OperationReportJobRequest request) {
		OperationReportJobResponse response = new OperationReportJobResponse();
		OperationReportJobVO info = operationReportJobCountService.getUserAgeAndArea(request.getUserId());
		response.setResult(info);
		return response;
	}
	@RequestMapping("/tenderagebyrange")
	public OperationReportJobResponse tenderAgeByRange(@RequestBody OperationReportJobRequest request) {
		OperationReportJobResponse response = new OperationReportJobResponse();
		int count = operationReportJobCountService.getTenderAgeByRange(request);
		response.setCount(count);
		return response;
	}
	@RequestMapping("/tendersexgroupby")
	public OperationReportJobResponse tenderSexGroupBy(@RequestBody OperationReportJobRequest request) {
		OperationReportJobResponse response = new OperationReportJobResponse();
		List<OperationReportJobVO> list = operationReportJobCountService.getTenderSexGroupBy(request);
		response.setResultList(list);
		return response;
	}
	@RequestMapping("/tendercitygroupbyuserids")
	public OperationReportJobResponse tenderCityGroupByUserIds(@RequestBody OperationReportJobRequest request) {
		OperationReportJobResponse response = new OperationReportJobResponse();
		List<OperationReportJobVO> list = operationReportJobCountService.getTenderCityGroupByUserIds(request.getOperationReportJobVOList());
		response.setResultList(list);
		return response;
	}
}
