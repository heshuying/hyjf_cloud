package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.SmsConfigMapper;
import com.hyjf.am.config.dao.model.auto.SmsConfig;
import com.hyjf.am.config.dao.model.auto.SmsConfigExample;
import com.hyjf.am.config.redis.RedisUtil;
import com.hyjf.am.config.service.SmsConfigService;
import com.hyjf.common.constants.RedisKey;

/**
 * @author xiasq
 * @version SmsConfigServiceImpl, v0.1 2018/4/24 18:18
 */
@Service
public class SmsConfigServiceImpl implements SmsConfigService {
	@Autowired
	private SmsConfigMapper smsConfigMapper;

	@Autowired
	private RedisUtil redisUtil;

	@Override
	public SmsConfig findOne() {
		SmsConfig smsConfig = (SmsConfig) redisUtil.get(RedisKey.SMS_CONFIG);
		if (smsConfig == null) {
			List<SmsConfig> list = smsConfigMapper.selectByExample(new SmsConfigExample());
			if (!CollectionUtils.isEmpty(list)){
				smsConfig = list.get(0);
				redisUtil.set(RedisKey.SMS_CONFIG, smsConfig);
				return smsConfig;
			}
		}
		return smsConfig;
	}
}
