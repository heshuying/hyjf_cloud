/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.TeamMapper;
import com.hyjf.am.config.dao.model.auto.Team;
import com.hyjf.am.config.dao.model.auto.TeamExample;
import com.hyjf.am.config.service.TeamService;
import com.hyjf.am.resquest.admin.TeamRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
		if (StringUtils.isNotBlank(request.getName())) {
			criteria.andNameEqualTo(request.getName());
		}
		if (StringUtils.isNotBlank(request.getPosition())) {
			criteria.andPositionEqualTo(request.getPosition());
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getStartTime() != null && request.getEndTime() != null) {
			criteria.andCreateTimeGreaterThanOrEqualTo(request.getStartTime());
			criteria.andCreateTimeLessThanOrEqualTo(request.getEndTime());
		}
		if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
			int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
			int limitEnd = request.getPageSize();
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		return teamMapper.selectByExample(example);
	}

	@Override
	public int insertAction(TeamRequest request) {
		Team team = new Team();
		BeanUtils.copyProperties(request, team);
		team.setImgappurl(team.getImgurl());
		return teamMapper.insertSelective(team);
	}

	@Override
	public int updateAction(TeamRequest request) {
		Team team = new Team();
		BeanUtils.copyProperties(request, team);
		return teamMapper.updateByPrimaryKeySelective(team);
	}

	@Override
	public Team getRecord(Integer id) {
		return teamMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteById(Integer id) {
		return teamMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int selectCount(TeamRequest request) {
		request.setCurrPage(0);
		List<Team> list = searchAction(request);
		if (!CollectionUtils.isEmpty(list)) {
			return list.size();
		}
		return 0;
	}
}
