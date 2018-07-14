/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.ContentJobService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.vo.config.JobsVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version ContentJobsController, v0.1 2018/7/12 11:35
 */
@Api(value = "公司管理-招贤纳士")
@RestController
@RequestMapping("/hyjf-admin/content/contentjob")
public class ContentJobsController extends BaseController {
	@Autowired
	private ContentJobService contentPartnerService;

	@ApiOperation(value = "公司管理-招贤纳士", notes = "公司管理-招贤纳士列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<JobsVo>> searchAction(@RequestBody ContentJobRequestBean requestBean) {
		JobResponse response = contentPartnerService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "公司管理-招贤纳士", notes = "添加公司管理-招贤纳士")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody ContentJobRequestBean requestBean) {
		JobResponse response = contentPartnerService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-招贤纳士", notes = "修改公司管理-招贤纳士")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody ContentJobRequestBean requestBean) {
		JobResponse response = contentPartnerService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-招贤纳士", notes = "修改公司管理-招贤纳士")
	@RequestMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody ContentJobRequestBean requestBean) {
		JobResponse response = contentPartnerService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "公司管理-招贤纳士", notes = "删除公司管理-招贤纳士")
	@RequestMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		JobResponse response = contentPartnerService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
