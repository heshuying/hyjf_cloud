/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentEnvironmentService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.vo.config.ContentEnvironmentVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 内容管理-办公环境
 * 
 * @author fuqiang
 * @version ContentEnvironmentController, v0.1 2018/7/11 11:13
 */
@Api(value = "内容管理-办公环境")
@RestController
@RequestMapping("/hyjf-admin/content/contentenvironment")
public class ContentEnvironmentController extends BaseController {
	@Autowired
	private ContentEnvironmentService contentEnvironmentService;

	@ApiOperation(value = "内容管理-办公环境", notes = "内容管理-办公环境列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<ContentEnvironmentVO>> searchAction(
			@RequestBody ContentEnvironmentRequestBean requestBean) {
		ContentEnvironmentResponse response = contentEnvironmentService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "内容管理-办公环境", notes = "内容管理-办公环境")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody ContentEnvironmentRequestBean requestBean) {
		ContentEnvironmentResponse response = contentEnvironmentService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "内容管理-办公环境", notes = "修改内容管理-办公环境")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody ContentEnvironmentRequestBean requestBean) {
		ContentEnvironmentResponse response = contentEnvironmentService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "内容管理-办公环境", notes = "修改内容管理-办公环境状态")
	@RequestMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentEnvironmentRequestBean requestBean) {
		ContentEnvironmentResponse response = contentEnvironmentService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
