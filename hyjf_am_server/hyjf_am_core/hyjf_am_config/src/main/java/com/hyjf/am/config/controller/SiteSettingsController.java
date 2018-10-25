/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.am.response.config.SiteSettingsResponse;
import com.hyjf.am.vo.config.SiteSettingsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liushouyi
 * @version SiteSettingsController, v0.1 2018/5/7 16:35
 */
@RestController
@RequestMapping("/am-config/site_settings")
public class SiteSettingsController extends BaseConfigController {

    @Autowired
    private SiteSettingService siteSettingsService;

    /**
     * 通过网站设置获取公司信息
     *
     * @return
     */
    @RequestMapping("/select_site_setting")
    public SiteSettingsResponse selectSiteSetting() {
        SiteSettingsResponse response = new SiteSettingsResponse();
        SiteSettingsVO siteSettingsVO = new SiteSettingsVO();
        SiteSetting siteSettings = siteSettingsService.selectSiteSetting();
        if (siteSettings != null) {
            BeanUtils.copyProperties(siteSettings, siteSettingsVO);
        }
        response.setResult(siteSettingsVO);
        return response;
    }
}
