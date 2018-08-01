/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.common.result.ListResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.TeamService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.vo.config.TeamVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version ContentTeamController, v0.1 2018/7/11 16:25
 */
@Api(value = "公司管理-团队介绍", tags = "公司管理-团队介绍")
@RestController
@RequestMapping("/hyjf-admin/content/contentteam")
public class TeamController extends BaseController {
	@Autowired
	private TeamService teamService;

	@ApiOperation(value = "公司管理-团队介绍条件列表查询", notes = "公司管理-团队介绍条件列表查询")
	@RequestMapping("/searchaction")
	public AdminResult<ListResult<TeamVO>> searchAction(@RequestBody TeamRequestBean requestBean) {
		TeamResponse response = teamService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>(ListResult.build(response.getResultList(), response.getCount()));
	}

	@ApiOperation(value = "添加公司管理-团队介绍", notes = "添加公司管理-团队介绍")
	@RequestMapping("/insert")
	public AdminResult add(@RequestBody TeamRequestBean requestBean) {
		TeamResponse response = teamService.insertAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改公司管理-团队介绍", notes = "修改公司管理-团队介绍")
	@RequestMapping("/update")
	public AdminResult update(@RequestBody TeamRequestBean requestBean) {
		TeamResponse response = teamService.updateAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "修改公司管理-资质荣誉状态", notes = "修改公司管理-资质荣誉状态")
	@RequestMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody TeamRequestBean requestBean) {
		TeamResponse response = teamService.updateStatus(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}

	@ApiOperation(value = "删除公司管理-团队介绍", notes = "删除公司管理-团队介绍")
	@RequestMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		TeamResponse response = teamService.deleteById(id);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult<>();
	}
}
