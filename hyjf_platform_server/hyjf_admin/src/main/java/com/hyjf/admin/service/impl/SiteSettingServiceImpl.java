/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service.impl;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.admin.client.AmConfigClient;
import com.hyjf.admin.service.SiteSettingService;
import com.hyjf.am.response.config.SiteSettingsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author fuqiang
 * @version SiteSettingServiceImpl, v0.1 2018/7/10 11:23
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {
	@Autowired
	private AmConfigClient amConfigClient;

	@Override
	public SiteSettingsResponse selectSiteSetting() {
		return amConfigClient.selectSiteSetting();
	}

	@Override
	public SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean) {
		return amConfigClient.updateAction(requestBean);
	}
}
