/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.client.AmConfigClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.admin.client.TeamClient;
import com.hyjf.admin.service.TeamService;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.vo.config.TeamVO;

/**
 * @author fuqiang
 * @version TeamServiceImpl, v0.1 2018/7/11 16:38
 */
@Service
public class TeamServiceImpl implements TeamService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public TeamResponse searchAction(TeamRequestBean requestBean) {
		return amConfigClient.searchAction(requestBean);
	}

	@Override
	public TeamResponse insertAction(TeamRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public TeamResponse updateAction(TeamRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public TeamResponse updateStatus(TeamRequestBean requestBean) {
		Integer id = requestBean.getId();
		TeamVO record = amConfigClient.getTeamRecord(id);
		if (record.getStatus() == 1) {
			record.setStatus(0);
		} else if (record.getStatus() == 0) {
			record.setStatus(1);
		}
		BeanUtils.copyProperties(record, requestBean);
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public TeamResponse deleteById(Integer id) {
		return amConfigClient.deleteTeamById(id);
	}
}
