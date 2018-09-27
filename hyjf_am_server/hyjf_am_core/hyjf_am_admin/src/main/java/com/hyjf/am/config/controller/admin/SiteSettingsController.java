/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.controller.admin;

import com.hyjf.am.config.controller.BaseConfigController;
import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.am.response.AdminResponse;
import com.hyjf.am.response.config.SiteSettingsResponse;
import com.hyjf.am.resquest.admin.SitesettingRequest;
import com.hyjf.am.vo.config.SiteSettingsVO;
import com.hyjf.common.util.GetDate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author fuqiang
 * @version SiteSettingsController, v0.1 2018/5/7 16:35
 */
@RestController
@RequestMapping("/am-config/siteSettings")
public class SiteSettingsController extends BaseConfigController{
    
    @Autowired
    private SiteSettingService siteSettingsService;

    /**
     * 查询邮件配置
     *
     * @return
     */
    @RequestMapping("/findOne")
    public SiteSettingsResponse findOne() {
        logger.info("查询邮件配置开始...");
        SiteSettingsResponse response = new SiteSettingsResponse();
        SiteSettingsVO siteSettingsVO = null;
        SiteSetting siteSettings = siteSettingsService.findOne();
        if (siteSettings != null) {
            siteSettingsVO = new SiteSettingsVO();
            BeanUtils.copyProperties(siteSettings, siteSettingsVO);
        }
        logger.info("siteSettingsVO is {}", siteSettingsVO);
        response.setResult(siteSettingsVO);
        return response;
    }

    /**
     * 修改件配置
     *
     * @return
     */
    @RequestMapping("/update")
    public SiteSettingsResponse update(@RequestBody SitesettingRequest request) {
        logger.info("修改邮件配置开始...");
        SiteSettingsResponse response = new SiteSettingsResponse();
        request.setUpdateTime(GetDate.getDate());
        siteSettingsService.update(request);
        response.setRtn(AdminResponse.SUCCESS);
        return response;
    }

    /**
     * 通过网站设置获取公司信息
     *
     * @return
     */
    @RequestMapping("/select_site_setting")
    public SiteSettingsResponse selectSiteSetting(){
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
