/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.admin.client.SiteSettingClient;
import com.hyjf.admin.service.SiteSettingService;
import com.hyjf.am.response.config.SiteSettingsResponse;

/**
 * @author fuqiang
 * @version SiteSettingServiceImpl, v0.1 2018/7/10 11:23
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {
	@Autowired
	private SiteSettingClient siteSettingClient;

	@Override
	public SiteSettingsResponse selectSiteSetting() {
		return siteSettingClient.selectSiteSetting();
	}

	@Override
	public SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean) {
		return siteSettingClient.updateAction(requestBean);
	}
}
