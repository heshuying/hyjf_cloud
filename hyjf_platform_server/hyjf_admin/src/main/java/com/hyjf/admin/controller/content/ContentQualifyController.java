/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.ContentQualifyRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentQualifyService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentQualifyResponse;
import com.hyjf.am.vo.config.ContentQualifyVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version ContentQualifyController, v0.1 2018/7/11 9:23
 */
@Api(value = "公司管理-资质荣誉")
@RestController
@RequestMapping("/hyjf-admin/content/contentqualify")
public class ContentQualifyController extends BaseController {
	@Autowired
	private ContentQualifyService contentQualifyService;

	@ApiOperation(value = "公司管理-资质荣誉", notes = "公司管理-资质荣誉条件列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<ContentQualifyVO>> searchAction(@RequestBody ContentQualifyRequestBean requestBean) {
		ContentQualifyResponse response = contentQualifyService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "公司管理-资质荣誉", notes = "添加公司管理-资质荣誉")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody ContentQualifyRequestBean requestBean) {
		ContentQualifyResponse response = contentQualifyService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-资质荣誉", notes = "修改公司管理-资质荣誉")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody ContentQualifyRequestBean requestBean) {
		ContentQualifyResponse response = contentQualifyService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-资质荣誉", notes = "修改公司管理-资质荣誉状态")
	@RequestMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentQualifyRequestBean requestBean) {
		ContentQualifyResponse response = contentQualifyService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
