/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.admin.client.ContentJobClient;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.vo.config.JobsVo;

/**
 * @author fuqiang
 * @version ContentJobClientImpl, v0.1 2018/7/12 14:27
 */
@Service
public class ContentJobClientImpl implements ContentJobClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public JobResponse searchAction(ContentJobRequestBean requestBean) {
		return null;
	}

	@Override
	public JobResponse insertAction(ContentJobRequestBean requestBean) {
		return null;
	}

	@Override
	public JobResponse updateAction(ContentJobRequestBean requestBean) {
		return null;
	}

	@Override
	public JobsVo getRecord(Integer id) {
		return null;
	}
}
