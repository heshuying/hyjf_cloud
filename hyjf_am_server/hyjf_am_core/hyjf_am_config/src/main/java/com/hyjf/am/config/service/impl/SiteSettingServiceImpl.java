/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.SiteSettingMapper;
import com.hyjf.am.config.dao.model.auto.SiteSetting;
import com.hyjf.am.config.dao.model.auto.SiteSettingExample;
import com.hyjf.am.config.service.SiteSettingService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;

/**
 * @author fuqiang
 * @version SiteSettingServiceImpl, v0.1 2018/5/7 16:47
 */
@Service
public class SiteSettingServiceImpl implements SiteSettingService {
	@Autowired
	private SiteSettingMapper SiteSettingMapper;

	@Override
	public SiteSetting findOne() {
		SiteSetting SiteSetting = null;
		if (SiteSetting == null) {
			SiteSettingExample example = new SiteSettingExample();
			List<SiteSetting> SiteSettingList = SiteSettingMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(SiteSettingList)) {
				SiteSetting = SiteSettingList.get(0);
				RedisUtils.setObjEx(RedisKey.SITE_SETTINGS, SiteSetting, 24 * 60 * 60);
				return SiteSetting;
			}
		}
		return SiteSetting;
	}
}
