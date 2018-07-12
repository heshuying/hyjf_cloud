/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.EventService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.EventResponse;
import com.hyjf.am.vo.config.EventVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 公司管理-公司记事
 * 
 * @author fuqiang
 * @version EventController, v0.1 2018/7/11 17:20
 */
@Api(value = "公司管理-公司记事")
@RestController
@RequestMapping("/hyjf-admin/content/contentevent")
public class EventController extends BaseController {
	@Autowired
	private EventService eventService;

	@ApiOperation(value = "公司管理-公司记事", notes = "公司管理-公司记事条件列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<EventVO>> searchAction(@RequestBody EventRequestBean requestBean) {
		EventResponse response = eventService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "公司管理-公司记事", notes = "添加公司管理-公司记事")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody EventRequestBean requestBean) {
		EventResponse response = eventService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-公司记事", notes = "修改公司管理-公司记事")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody EventRequestBean requestBean) {
		EventResponse response = eventService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-公司记事", notes = "修改公司管理-公司记事")
	@RequestMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody EventRequestBean requestBean) {
		EventResponse response = eventService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}