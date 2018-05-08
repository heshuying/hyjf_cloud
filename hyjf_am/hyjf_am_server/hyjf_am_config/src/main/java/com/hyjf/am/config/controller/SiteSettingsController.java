/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller;

import com.hyjf.am.config.dao.model.auto.SiteSettings;
import com.hyjf.am.config.service.SiteSettingsService;
import com.hyjf.am.response.config.SiteSettingsResponse;
import com.hyjf.am.vo.config.SiteSettingsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuqiang
 * @version SiteSettingsController, v0.1 2018/5/7 16:35
 */
@RestController
@RequestMapping("/am-config/siteSettings")
public class SiteSettingsController {

    @Autowired
    private SiteSettingsService siteSettingsService;

    Logger logger = LoggerFactory.getLogger(SiteSettingsController.class);

    public SiteSettingsResponse findOne() {
        logger.info("查询邮件配置开始...");
        SiteSettingsResponse response = new SiteSettingsResponse();
        SiteSettingsVO siteSettingsVO = null;
        SiteSettings siteSettings = siteSettingsService.findOne();
        if (siteSettings != null) {
            siteSettingsVO = new SiteSettingsVO();
            BeanUtils.copyProperties(siteSettings, siteSettingsVO);
        }
        logger.info("siteSettingsVO is {}", siteSettingsVO);
        response.setResult(siteSettingsVO);
        return response;
    }
}
