/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.SmsTemplateMapper;
import com.hyjf.am.config.dao.model.auto.SmsTemplate;
import com.hyjf.am.config.dao.model.auto.SmsTemplateExample;
import com.hyjf.am.config.service.SmsTemplateService;
import com.hyjf.am.resquest.config.SmsTemplateRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

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
		SmsTemplate smsTemplate = RedisUtils.getObj(RedisConstants.SMS_TEMPLATE, SmsTemplate.class);
		if (smsTemplate == null) {
			SmsTemplateExample example = new SmsTemplateExample();
			SmsTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTplCodeEqualTo(tplCode);
			List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(smsTemplateList)) {
				smsTemplate = smsTemplateList.get(0);
				RedisUtils.setObjEx(RedisConstants.SMS_TEMPLATE, smsTemplate, 24 * 60 * 60);
				return smsTemplate;
			}
		}
		return smsTemplate;
	}

	@Override
	public List<SmsTemplate> findAll() {
		SmsTemplateExample example = new SmsTemplateExample();
		SmsTemplateExample.Criteria criteria = example.createCriteria();
		return smsTemplateMapper.selectByExample(example);
	}

	@Override
	public List<SmsTemplate> findSmsTemplate(SmsTemplateRequest request) {
		SmsTemplateExample example = new SmsTemplateExample();
		SmsTemplateExample.Criteria criteria = example.createCriteria();
		if (request != null) {
			if (request.getStatus() != null) {
				criteria.andStatusEqualTo(request.getStatus());
			}
			if (StringUtils.isNotBlank(request.getTplName())) {
				criteria.andTplNameEqualTo(request.getTplName());
			}
			return smsTemplateMapper.selectByExample(example);
		} else {
			return findAll();
		}
	}

	@Override
	public void insertSmsTemplate(SmsTemplateRequest request) {
		if (request != null) {
			SmsTemplate smsTemplate = new SmsTemplate();
			BeanUtils.copyProperties(request, smsTemplate);
			smsTemplateMapper.insert(smsTemplate);
		}
	}

	@Override
	public void openSmsTemplate(SmsTemplateRequest request) {
		SmsTemplate smsTemplate = new SmsTemplate();
		BeanUtils.copyProperties(request, smsTemplate);
		// 开启
		smsTemplate.setStatus(1);
		smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);
	}

	@Override
	public void closeSmsTemplate(SmsTemplateRequest request) {
		SmsTemplate smsTemplate = new SmsTemplate();
		BeanUtils.copyProperties(request, smsTemplate);
		// 关闭
		smsTemplate.setStatus(0);
		smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);
	}

	@Override
	public void updateSmsTemplate(SmsTemplateRequest request) {
		SmsTemplate smsTemplate = new SmsTemplate();
		BeanUtils.copyProperties(request, smsTemplate);
		smsTemplateMapper.updateByPrimaryKeySelective(smsTemplate);
	}
}
