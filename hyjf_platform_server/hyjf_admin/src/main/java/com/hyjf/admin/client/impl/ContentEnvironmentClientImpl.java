/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.ContentEnvironmentRequestBean;
import com.hyjf.admin.client.ContentEnvironmentClient;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.vo.config.ContentEnvironmentVO;

/**
 * @author fuqiang
 * @version ContentEnvironmentClientImpl, v0.1 2018/7/11 14:39
 */
@Service
public class ContentEnvironmentClientImpl implements ContentEnvironmentClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ContentEnvironmentResponse searchAction(ContentEnvironmentRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentenvironment/searchaction",
				requestBean, ContentEnvironmentResponse.class);
	}

	@Override
	public ContentEnvironmentResponse insertAction(ContentEnvironmentRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentenvironment/insertaction",
				requestBean, ContentEnvironmentResponse.class);
	}

	@Override
	public ContentEnvironmentResponse updateAction(ContentEnvironmentRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentenvironment/updateaction",
				requestBean, ContentEnvironmentResponse.class);
	}

	@Override
	public ContentEnvironmentVO getRecord(Integer id) {
		ContentEnvironmentResponse response = restTemplate.getForObject(
				"http://AM-CONFIG/am-config/content/contentenvironment/getrecord/" + id,
				ContentEnvironmentResponse.class);
		if (response != null) {
			return response.getResult();
		}
		return null;
	}

	@Override
	public ContentEnvironmentResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentenvironment/delete/" + id,
				ContentEnvironmentResponse.class);
	}
}
