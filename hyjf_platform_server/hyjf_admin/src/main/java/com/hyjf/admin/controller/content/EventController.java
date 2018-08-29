/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.EventRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.EventService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.EventResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公司管理-公司记事
 * 
 * @author fuqiang
 * @version EventController, v0.1 2018/7/11 17:20
 */
@Api(tags = "公司管理-公司记事")
@RestController
@RequestMapping("/hyjf-admin/content/contentevent")
public class EventController extends BaseController {
	@Autowired
	private EventService eventService;

	@ApiOperation(value = "公司管理-公司记事条件列表查询", notes = "公司管理-公司记事条件列表查询")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody EventRequestBean requestBean) {
		EventResponse response = eventService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "添加公司管理-公司记事", notes = "添加公司管理-公司记事")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody EventRequestBean requestBean) {
		int num = eventService.insertAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-公司记事", notes = "修改公司管理-公司记事")
	@PostMapping("/update")
	public AdminResult update(@RequestBody EventRequestBean requestBean) {
		int num = eventService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-公司记事", notes = "修改公司管理-公司记事")
	@PostMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody EventRequestBean requestBean) {
		int num = eventService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除公司管理-公司记事", notes = "删除公司管理-公司记事")
	@GetMapping("/delete/{id}")
	public AdminResult updatestatus(@PathVariable Integer id) {
		int num = eventService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}
}
