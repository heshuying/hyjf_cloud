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
			if (request.getCurrPage() > 0 && request.getPageSize() > 0) {
				int limitStart = (request.getCurrPage() - 1) * (request.getPageSize());
				int limitEnd = request.getPageSize();
				example.setLimitStart(limitStart);
				example.setLimitEnd(limitEnd);
			}
			return smsTemplateMapper.selectByExample(example);
		} else {
			return findAll();
		}
	}

	@Override
	public int insertSmsTemplate(SmsTemplateRequest request) {
		if (request != null) {
			SmsTemplate smsTemplate = new SmsTemplate();
			BeanUtils.copyProperties(request, smsTemplate);
			return smsTemplateMapper.insert(smsTemplate);
		}
		return 0;
	}

	@Override
	public Integer updateSmsTemplateStatus(SmsTemplateRequest request) {
		if (StringUtils.isNotEmpty(request.getTplCode()) && request.getStatus() != null) {
			SmsTemplateExample example = new SmsTemplateExample();
			SmsTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTplCodeEqualTo(request.getTplCode());
			List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(smsTemplateList)) {
				SmsTemplate smsTemplate = smsTemplateList.get(0);
				smsTemplate.setStatus(request.getStatus());
				return smsTemplateMapper.updateByPrimaryKey(smsTemplate);
			}
		}
		return 0;
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
	public int updateSmsTemplate(SmsTemplateRequest request) {
		if (StringUtils.isNotEmpty(request.getTplCode()) && request.getStatus() != null) {
			SmsTemplateExample example = new SmsTemplateExample();
			SmsTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTplCodeEqualTo(request.getTplCode());
			List<SmsTemplate> smsTemplateList = smsTemplateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(smsTemplateList)) {
				SmsTemplate smsTemplate = smsTemplateList.get(0);
				BeanUtils.copyProperties(request, smsTemplate);
				return smsTemplateMapper.updateByPrimaryKey(smsTemplate);
			}
		}
		return 0;
	}

	@Override
	public int selectCount(SmsTemplateRequest request) {
		SmsTemplateExample example = new SmsTemplateExample();
		SmsTemplateExample.Criteria criteria = example.createCriteria();
		if (request != null) {
			if (request.getStatus() != null) {
				criteria.andStatusEqualTo(request.getStatus());
			}
			if (StringUtils.isNotBlank(request.getTplName())) {
				criteria.andTplNameEqualTo(request.getTplName());
			}

			List<SmsTemplate> list = smsTemplateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				return list.size();
			} else {
				return 0;
			}
		}
		return smsTemplateMapper.countByExample(example);
	}
}
