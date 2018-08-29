/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentEnvironmentService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 内容管理-办公环境
 * 
 * @author fuqiang
 * @version ContentEnvironmentController, v0.1 2018/7/11 11:13
 */
@Api(tags = "内容管理-办公环境")
@RestController
@RequestMapping("/hyjf-admin/content/contentenvironment")
public class ContentEnvironmentController extends BaseController {
	@Autowired
	private ContentEnvironmentService contentEnvironmentService;

	@ApiOperation(value = "内容管理-办公环境列表查询", notes = "内容管理-办公环境列表查询")
	@PostMapping("/searchaction")
	public AdminResult searchAction(
			@RequestBody ContentEnvironmentRequestBean requestBean) {
		ContentEnvironmentResponse response = contentEnvironmentService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "内容管理-办公环境", notes = "内容管理-办公环境")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody ContentEnvironmentRequestBean requestBean) {
		int num = contentEnvironmentService.insertAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改内容管理-办公环境", notes = "修改内容管理-办公环境")
	@PostMapping("/update")
	public AdminResult update(@RequestBody ContentEnvironmentRequestBean requestBean) {
		int num = contentEnvironmentService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改内容管理-办公环境状态", notes = "修改内容管理-办公环境状态")
	@PostMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentEnvironmentRequestBean requestBean) {
		int num = contentEnvironmentService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除内容管理-办公环境", notes = "删除内容管理-办公环境")
	@GetMapping("/delete/{id}")
	public AdminResult updatestatus(@PathVariable Integer id) {
		int num = contentEnvironmentService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}
}
