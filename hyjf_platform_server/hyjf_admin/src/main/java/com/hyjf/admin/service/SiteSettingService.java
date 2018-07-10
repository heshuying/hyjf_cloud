/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.service;

import com.hyjf.admin.beans.request.SiteSettingRequestBean;
import com.hyjf.am.response.config.SiteSettingsResponse;

/**
 * @author fuqiang
 * @version SiteSettingService, v0.1 2018/7/10 11:22
 */
public interface SiteSettingService {
	/**
	 * 获取网站设置
	 *
	 * @return
	 */
	SiteSettingsResponse selectSiteSetting();

	/**
	 * 修改网站设置
	 *
	 * @param requestBean
	 * @return
	 */
	SiteSettingsResponse updateAction(SiteSettingRequestBean requestBean);
}
