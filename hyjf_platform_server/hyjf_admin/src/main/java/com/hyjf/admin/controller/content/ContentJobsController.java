/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentJobService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.JobResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuqiang
 * @version ContentJobsController, v0.1 2018/7/12 11:35
 */
@Api(tags = "公司管理-招贤纳士")
@RestController
@RequestMapping("/hyjf-admin/content/contentjob")
public class ContentJobsController extends BaseController {
	@Autowired
	private ContentJobService contentPartnerService;

	@ApiOperation(value = "公司管理-招贤纳士列表查询", notes = "公司管理-招贤纳士列表查询")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody ContentJobRequestBean requestBean) {
		JobResponse response = contentPartnerService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "添加公司管理-招贤纳士", notes = "添加公司管理-招贤纳士")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody ContentJobRequestBean requestBean) {
		int num = contentPartnerService.insertAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-招贤纳士", notes = "修改公司管理-招贤纳士")
	@PostMapping("/update")
	public AdminResult update(@RequestBody ContentJobRequestBean requestBean) {
		int num = contentPartnerService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-招贤纳士", notes = "修改公司管理-招贤纳士")
	@PostMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentJobRequestBean requestBean) {
		int num = contentPartnerService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除公司管理-招贤纳士", notes = "删除公司管理-招贤纳士")
	@GetMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		int num = contentPartnerService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}
}
