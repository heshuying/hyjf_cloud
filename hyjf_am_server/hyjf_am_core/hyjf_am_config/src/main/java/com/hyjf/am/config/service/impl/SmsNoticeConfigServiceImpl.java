/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.SmsNoticeConfigMapper;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfig;
import com.hyjf.am.config.dao.model.auto.SmsNoticeConfigExample;
import com.hyjf.am.config.service.SmsNoticeConfigService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;

/**
 * @author fuqiang
 * @version SmsNoticeConfigServiceImpl, v0.1 2018/5/8 10:08
 */
@Service
public class SmsNoticeConfigServiceImpl implements SmsNoticeConfigService {
	@Autowired
	private SmsNoticeConfigMapper smsNoticeConfigMapper;

	@Override
	public SmsNoticeConfig findSmsNoticeByCode(String tplCode) {
		SmsNoticeConfig smsNoticeConfig = RedisUtils.getObj(RedisKey.SMS_NOTICE_CONFIG, SmsNoticeConfig.class);
		if (smsNoticeConfig == null) {
			SmsNoticeConfigExample example = new SmsNoticeConfigExample();
			SmsNoticeConfigExample.Criteria criteria = example.createCriteria();
			criteria.andNameEqualTo(tplCode);
			List<SmsNoticeConfig> smsNoticeConfigList = smsNoticeConfigMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(smsNoticeConfigList)) {
				smsNoticeConfig = smsNoticeConfigList.get(0);
				RedisUtils.setObjEx(RedisKey.SMS_NOTICE_CONFIG, smsNoticeConfig, 24 * 60 * 60);
				return smsNoticeConfig;
			}
		}
		return smsNoticeConfig;
	}
}
