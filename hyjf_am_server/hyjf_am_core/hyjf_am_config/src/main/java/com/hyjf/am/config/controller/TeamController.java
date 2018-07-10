/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyjf.am.config.dao.model.auto.Team;
import com.hyjf.am.config.service.TeamService;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.vo.config.TeamVO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * @author fuqiang
 * @version TeamController, v0.1 2018/7/9 16:42
 */
@Api(value = "创始人信息")
@RestController
@RequestMapping("/am-config/team")
public class TeamController extends BaseConfigController {

    @Autowired
    private TeamService teamService;

	@ApiOperation(value = "创始人信息", notes = "获取创始人信息")
	@RequestMapping("/getfounder")
	public TeamResponse getFounder() {
	    logger.info("获取创始人信息开始......");
		TeamResponse response = new TeamResponse();
		Team team = teamService.getFounder();
		if (team != null) {
			TeamVO teamVO = new TeamVO();
			BeanUtils.copyProperties(team, teamVO);
			response.setResult(teamVO);
		}
		return response;
	}
}
