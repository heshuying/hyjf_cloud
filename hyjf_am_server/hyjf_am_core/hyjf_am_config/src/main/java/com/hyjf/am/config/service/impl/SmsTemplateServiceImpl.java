/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.SmsTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.config.dao.model.auto.SmsTemplateExample;
import com.hyjf.am.config.service.SmsTemplateService;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;

/**
 * @author fuqiang
 * @version SmsTemplateServiceImpl, v0.1 2018/5/8 10:24
 */
@Service
public class SmsTemplateServiceImpl implements SmsTemplateService {

	@Autowired
	private SmsTemplateMapper smsTemplateMapper;

	@Override
	public SmsTemplate findSmsTemplateByCode(String tplCode) {
		SmsTemplate smsTemplate = RedisUtils.getObj(RedisKey.SMS_TEMPLATE, SmsTemplate.class);
		if (smsTemplate == null) {
			SmsTemplateExample example = new SmsTemplateExample();
			SmsTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTplCodeEqualTo(tplCode);
			List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(smsTemplateList)) {
				smsTemplate = smsTemplateList.get(0);
				RedisUtils.setObjEx(RedisKey.SMS_TEMPLATE, smsTemplate, 24 * 60 * 60);
				return smsTemplate;
			}
		}
		return smsTemplate;
	}
}
