/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin.content;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.Team;
import com.hyjf.am.config.service.TeamService;
import com.hyjf.am.response.IntegerResponse;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.resquest.admin.TeamRequest;
import com.hyjf.am.vo.config.TeamVO;
import com.hyjf.common.util.CommonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author fuqiang
 * @version TeamController, v0.1 2018/7/9 16:42
 */
@RestController
@RequestMapping("/am-config/team")
public class TeamController extends BaseConfigController {

    @Autowired
    private TeamService teamService;

	/**
	 * 获取创始人信息
	 *
	 * @return
	 */
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

	/**
	 * 根据条件查询公司管理-团队介绍
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/searchaction")
	public TeamResponse searchAction(@RequestBody TeamRequest request) {
		logger.info("查询内容管理-办公环境开始......");
		TeamResponse response = new TeamResponse();
		List<Team> list = teamService.searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			List<TeamVO> voList = CommonUtils.convertBeanList(list, TeamVO.class);
			response.setResultList(voList);
		}
		// 查询符合条件的条数
		int count = teamService.selectCount(request);
		response.setCount(count);
		return response;
	}

	/**
	 * 添加公司管理-团队介绍
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertaction")
	public IntegerResponse insertAction(@RequestBody TeamRequest request) {
		int num = teamService.insertAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 修改公司管理-团队介绍
	 *
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateaction")
	public IntegerResponse updateAction(@RequestBody TeamRequest request) {
		int num = teamService.updateAction(request);
		return new IntegerResponse(num);
	}

	/**
	 * 根据id查询公司管理-团队介绍
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/getrecord/{id}")
	public TeamResponse getRecord(@PathVariable Integer id) {
		TeamResponse response = new TeamResponse();
		Team team = teamService.getRecord(id);
		if (team != null) {
			TeamVO vo = new TeamVO();
			BeanUtils.copyProperties(team, vo);
			response.setResult(vo);
		}
		return response;
	}

	/**
	 * 根据id查询公司管理-团队介绍
	 *
	 * @param id
	 * @return
	 */
	@RequestMapping("/delete/{id}")
	public IntegerResponse delete(@PathVariable Integer id) {
		int num = teamService.deleteById(id);
        return new IntegerResponse(num);
	}
}
