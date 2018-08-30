/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.controller.content;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.admin.common.result.AdminResult;
import com.hyjf.admin.controller.BaseController;
import com.hyjf.admin.service.TeamService;
import com.hyjf.am.response.Response;
import com.hyjf.am.response.config.TeamResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author fuqiang
 * @version ContentTeamController, v0.1 2018/7/11 16:25
 */
@Api(tags = "公司管理-团队介绍")
@RestController
@RequestMapping("/hyjf-admin/content/contentteam")
public class TeamController extends BaseController {
	@Autowired
	private TeamService teamService;

	@ApiOperation(value = "公司管理-团队介绍条件列表查询", notes = "公司管理-团队介绍条件列表查询")
	@PostMapping("/searchaction")
	public AdminResult searchAction(@RequestBody TeamRequestBean requestBean) {
		TeamResponse response = teamService.searchAction(requestBean);
		if (response == null) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		if (!Response.isSuccess(response)) {
			return new AdminResult<>(FAIL, response.getMessage());
		}
		return new AdminResult(response);
	}

	@ApiOperation(value = "添加公司管理-团队介绍", notes = "添加公司管理-团队介绍")
	@PostMapping("/insert")
	public AdminResult add(@RequestBody TeamRequestBean requestBean) {
		int num = teamService.insertAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-团队介绍", notes = "修改公司管理-团队介绍")
	@PostMapping("/update")
	public AdminResult update(@RequestBody TeamRequestBean requestBean) {
		int num = teamService.updateAction(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "修改公司管理-资质荣誉状态", notes = "修改公司管理-资质荣誉状态")
	@PostMapping("/updatestatus")
	public AdminResult updatestatus(@RequestBody TeamRequestBean requestBean) {
		int num = teamService.updateStatus(requestBean);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}

	@ApiOperation(value = "删除公司管理-团队介绍", notes = "删除公司管理-团队介绍")
	@GetMapping("/delete/{id}")
	public AdminResult delete(@PathVariable Integer id) {
		int num = teamService.deleteById(id);
		if (num <= 0) {
			return new AdminResult<>(FAIL, FAIL_DESC);
		}
		return new AdminResult<>(SUCCESS, SUCCESS_DESC);
	}
}
