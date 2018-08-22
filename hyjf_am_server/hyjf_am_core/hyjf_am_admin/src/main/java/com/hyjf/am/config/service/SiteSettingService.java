/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service;

import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.resquest.admin.SitesettingRequest;

/**
 * @author fuqiang
 * @version SiteSettingsService, v0.1 2018/5/7 16:47
 */
public interface SiteSettingService {
    /**
     * 查询邮件配置
     *
     * @return
     */
    SiteSetting findOne();


    void updateTest1() throws Exception;

    /**
     * 邮件配置
     *
     * @param request
     * @return
     */
    void update(SitesettingRequest request);
}
