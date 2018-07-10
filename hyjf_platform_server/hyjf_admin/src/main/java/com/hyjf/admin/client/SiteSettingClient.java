/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.client;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.am.response.config.SiteSettingsResponse;

/**
 * @author fuqiang
 * @version SiteSettingClient, v0.1 2018/7/10 11:31
 */
public interface SiteSettingClient {
	/**
	 * 获取网站配置
	 *
	 * @return
	 */
	SiteSettingsResponse selectSiteSetting();

	/**
	 * 修改网站配置
	 *
	 * @param requestBean
	 * @return
	 */
	SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean);
}
