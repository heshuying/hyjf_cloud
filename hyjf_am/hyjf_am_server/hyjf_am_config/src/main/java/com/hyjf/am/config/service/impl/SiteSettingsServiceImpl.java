/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SiteSettingsMapper;
import com.hyjf.am.config.dao.model.auto.SiteSettings;
import com.hyjf.am.config.dao.model.auto.SiteSettingsExample;
import com.hyjf.am.config.redis.RedisUtil;
import com.hyjf.am.config.service.SiteSettingsService;
import com.hyjf.common.constants.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version SiteSettingsServiceImpl, v0.1 2018/5/7 16:47
 */
@Service
public class SiteSettingsServiceImpl implements SiteSettingsService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private SiteSettingsMapper siteSettingsMapper;

    @Override
    public SiteSettings findOne() {
        SiteSettings siteSettings = (SiteSettings) redisUtil.get(RedisKey.SITE_SETTINGS);
        if (siteSettings == null) {
            SiteSettingsExample example = new SiteSettingsExample();
            List<SiteSettings> siteSettingsList = siteSettingsMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(siteSettingsList)) {
                siteSettings = siteSettingsList.get(0);
                redisUtil.set(RedisKey.SITE_SETTINGS, siteSettings);
                return siteSettingsList.get(0);
            }
        }

        return null;
    }
}
