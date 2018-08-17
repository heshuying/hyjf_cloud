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
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentjob/searchaction", requestBean,
				JobResponse.class);
	}

	@Override
	public JobResponse insertAction(ContentJobRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentjob/insertaction", requestBean,
				JobResponse.class);
	}

	@Override
	public JobResponse updateAction(ContentJobRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentjob/insertaction", requestBean,
				JobResponse.class);
	}

	@Override
	public JobsVo getRecord(Integer id) {
		JobResponse response = restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentjob/getrecord/" + id,
				JobResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public JobResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentjob/delete/" + id,
				JobResponse.class);
	}
}
