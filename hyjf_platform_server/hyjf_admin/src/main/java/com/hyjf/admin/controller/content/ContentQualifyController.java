/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.ContentQualifyRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentQualifyService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.admin.ContentQualifyResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuqiang
 * @version ContentQualifyController, v0.1 2018/7/11 9:23
 */
@Api(tags = "公司管理-资质荣誉")
@RestController
@RequestMapping("/hyjf-admin/content/contentqualify")
public class ContentQualifyController extends BaseController {
	@Autowired
	private ContentQualifyService contentQualifyService;

	@ApiOperation(value = "公司管理-资质荣誉条件列表查询", notes = "公司管理-资质荣誉条件列表查询")
	@PostMapping("/search_action")
	public AdminResult searchAction(@RequestBody ContentQualifyRequestBean requestBean) {
		ContentQualifyResponse response = contentQualifyService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "添加公司管理-资质荣誉", notes = "添加公司管理-资质荣誉")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody ContentQualifyRequestBean requestBean) {
		int num = contentQualifyService.insertAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-资质荣誉", notes = "修改公司管理-资质荣誉")
	@PostMapping("/update")
	public AdminResult update(@RequestBody ContentQualifyRequestBean requestBean) {
		int num = contentQualifyService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-资质荣誉状态", notes = "修改公司管理-资质荣誉状态")
	@PostMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentQualifyRequestBean requestBean) {
		int num = contentQualifyService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除公司管理-资质荣誉状态", notes = "删除公司管理-资质荣誉状态")
	@GetMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		int num = contentQualifyService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}
}
