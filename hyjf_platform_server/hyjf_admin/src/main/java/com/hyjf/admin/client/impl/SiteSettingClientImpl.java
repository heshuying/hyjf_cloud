/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.admin.client.SiteSettingClient;
import com.hyjf.am.response.config.SiteSettingsResponse;

/**
 * @author fuqiang
 * @version SiteSettingClientImpl, v0.1 2018/7/10 11:34
 */
@Service
public class SiteSettingClientImpl implements SiteSettingClient {
	@Autowired
	private RestTemplate restTemplate;

	@Override
	public SiteSettingsResponse selectSiteSetting() {
		return restTemplate.getForObject("http://AM-CONFIG/am-config/siteSettings/findOne", SiteSettingsResponse.class);
	}

	@Override
	public SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean) {
		return restTemplate.postForObject("http://AM-CONFIG/am-config/siteSettings/update", requestBean,
				SiteSettingsResponse.class);
	}
}
