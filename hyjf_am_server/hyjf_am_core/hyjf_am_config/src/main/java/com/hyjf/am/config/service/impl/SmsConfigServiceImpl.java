package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.SmsConfigMapper;
import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.dao.model.auto.SmsConfigExample;
import com.hyjf.am.config.service.SmsConfigService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.cache.RedisConstants;

/**
 * @author xiasq
 * @version SmsConfigServiceImpl, v0.1 2018/4/24 18:18
 */
@Service
public class SmsConfigServiceImpl implements SmsConfigService {
	@Autowired
	private SmsConfigMapper smsConfigMapper;

	@Override
	public SmsConfig findOne() {
		SmsConfig smsConfig = RedisUtils.getObj(RedisConstants.SMS_CONFIG, SmsConfig.class);
		if (smsConfig == null) {
			List<SmsConfig> list = smsConfigMapper.selectByExample(new SmsConfigExample());
			if (!CollectionUtils.isEmpty(list)) {
				smsConfig = list.get(0);
				RedisUtils.setObjEx(RedisConstants.SMS_CONFIG, smsConfig, 24 * 60 * 60);
				return smsConfig;
			}
		}
		return smsConfig;
	}
}
