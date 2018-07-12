/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.TeamRequestBean;
import com.hyjf.admin.client.TeamClient;
import com.hyjf.am.response.config.TeamResponse;
import com.hyjf.am.vo.config.TeamVO;

/**
 * @author fuqiang
 * @version TeamClientImpl, v0.1 2018/7/11 16:42
 */
@Service
public class TeamClientImpl implements TeamClient {
	@Override
	public TeamResponse searchAction(TeamRequestBean requestBean) {
		return null;
	}

	@Override
	public TeamResponse insertAction(TeamRequestBean requestBean) {
		return null;
	}

	@Override
	public TeamResponse updateAction(TeamRequestBean requestBean) {
		return null;
	}

	@Override
	public TeamVO getRecord(Integer id) {
		return null;
	}
}
