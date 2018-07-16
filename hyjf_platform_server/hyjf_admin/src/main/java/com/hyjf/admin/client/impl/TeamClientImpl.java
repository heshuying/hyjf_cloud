/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public TeamResponse searchAction(TeamRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/team/searchaction", requestBean,
				TeamResponse.class);
	}

	@Override
	public TeamResponse insertAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/team/insertaction", requestBean,
                TeamResponse.class);
	}

	@Override
	public TeamResponse updateAction(TeamRequestBean requestBean) {
        return restTemplate.postForObject("http://AM-CONFIG/am-config/team/updateaction", requestBean,
                TeamResponse.class);
	}

	@Override
	public TeamVO getRecord(Integer id) {
		TeamResponse response = restTemplate.getForObject("http//AM-CONFIG/am-config/team/getrecord/" + id,
				TeamResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public TeamResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/team/delete/" + id, TeamResponse.class);
	}
}
