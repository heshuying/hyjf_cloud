/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.TeamService;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.vo.config.TeamVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public int insertAction(TeamRequestBean requestBean) {
		return amConfigClient.insertAction(requestBean);
	}

	@Override
	public int updateAction(TeamRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int updateStatus(TeamRequestBean requestBean) {
		Integer id = requestBean.getId();
		TeamVO record = amConfigClient.getTeamRecord(id);
		TeamRequestBean bean = new TeamRequestBean();
		BeanUtils.copyProperties(record, bean);
		bean.setStatus(requestBean.getStatus());
		return amConfigClient.updateAction(requestBean);
	}

	@Override
	public int deleteById(Integer id) {
		return amConfigClient.deleteTeamById(id);
	}
}
