/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client.impl;

import org.springframework.stereotype.Service;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.admin.client.SiteSettingClient;
import com.hyjf.am.response.config.SiteSettingsResponse;

/**
 * @author fuqiang
 * @version SiteSettingClientImpl, v0.1 2018/7/10 11:34
 */
@Service
public class SiteSettingClientImpl implements SiteSettingClient {
	@Override
	public SiteSettingsResponse selectSiteSetting() {
		return null;
	}

	@Override
	public SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean) {
		return null;
	}
}
