/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import com.hyjf.admin.beans.request.ContentJobRequestBean;
import com.hyjf.admin.beans.request.ContentLandingPageRequestBean;
import com.hyjf.admin.client.ContentJobClient;
import com.hyjf.admin.client.ContentLandingPageClient;
import com.hyjf.am.response.admin.ContentEnvironmentResponse;
import com.hyjf.am.response.config.JobResponse;
import com.hyjf.am.response.config.LandingPageResponse;
import com.hyjf.am.response.user.ChangeLogResponse;
import com.hyjf.am.vo.config.JobsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author tanyy
 * @version ContentLandingPageClientImpl, v0.1 2018/7/16 14:27
 */
@Service
public class ContentLandingPageClientImpl implements ContentLandingPageClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public LandingPageResponse searchAction(ContentLandingPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlandingpage/searchaction",
				requestBean, LandingPageResponse.class);
	}

	@Override
	public LandingPageResponse insertAction(ContentLandingPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlandingpage/insert",
				requestBean, LandingPageResponse.class);
	}

	@Override
	public LandingPageResponse updateAction(ContentLandingPageRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/content/contentlandingpage/update",
				requestBean, LandingPageResponse.class);
	}


	@Override
	public LandingPageResponse deleteById(Integer id) {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/content/contentenvironment/delete/" + id,
				LandingPageResponse.class);
	}
}
