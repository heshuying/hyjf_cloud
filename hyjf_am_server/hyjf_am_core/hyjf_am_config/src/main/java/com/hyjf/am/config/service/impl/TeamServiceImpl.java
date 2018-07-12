/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.am.config.dao.mapper.auto.TeamMapper;
import com.hyjf.am.config.dao.model.auto.Team;
import com.hyjf.am.config.dao.model.auto.TeamExample;
import com.hyjf.am.config.service.TeamService;
import com.hyjf.am.resquest.admin.TeamRequest;
import com.hyjf.common.util.GetDate;

/**
 * @author fuqiang
 * @version TeamServiceImpl, v0.1 2018/7/9 16:46
 */
@Service
public class TeamServiceImpl implements TeamService {

	@Autowired
	private TeamMapper teamMapper;

	@Override
	public Team getFounder() {
		TeamExample example = new TeamExample();
		TeamExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(1);// 开启状态
		example.setOrderByClause("`order` asc");
		List<Team> teamList = teamMapper.selectByExample(example);
		return teamList.size() == 0 ? new Team() : teamList.get(0);
	}

	@Override
	public List<Team> searchAction(TeamRequest request) {
		TeamExample example = new TeamExample();
		TeamExample.Criteria criteria = example.createCriteria();
		if (StringUtils.isNotBlank(request.getPosition())) {
			criteria.andPositionEqualTo(request.getPosition());
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getStartTime() != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(GetDate.str2Date(request.getStartTime(), GetDate.date_sdf));
		}
		if (request.getEndTime() != null) {
			criteria.andCreateTimeLessThanOrEqualTo(GetDate.str2Date(request.getEndTime(), GetDate.date_sdf));
		}
		return null;
	}

	@Override
	public void insertAction(TeamRequest request) {
		Team team = new Team();
		BeanUtils.copyProperties(request, team);
		teamMapper.insert(team);
	}

	@Override
	public void updateAction(TeamRequest request) {
		Team team = new Team();
		BeanUtils.copyProperties(request, team);
		teamMapper.updateByPrimaryKey(team);
	}

	@Override
	public Team getRecord(Integer id) {
		return teamMapper.selectByPrimaryKey(id);
	}
}
