/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTemplateMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplateExample;
import com.hyjf.am.config.service.MessagePushTemplateService;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateServiceImpl, v0.1 2018/5/8 10:42
 */
@Service
public class MessagePushTemplateServiceImpl implements MessagePushTemplateService {

	@Autowired
	private MessagePushTemplateMapper templateMapper;

	@Override
	public MessagePushTemplate findMessagePushTemplateByCode(String tplCode) {
		MessagePushTemplate messagePushTemplate = RedisUtils.getObj(RedisConstants.MESSAGE_PUSH_TEMPLATE,
				MessagePushTemplate.class);
		if (messagePushTemplate == null||!StringUtils.equals(messagePushTemplate.getTemplateCode(),tplCode)) {
			MessagePushTemplateExample example = new MessagePushTemplateExample();
			MessagePushTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTemplateCodeEqualTo(tplCode);
			criteria.andStatusEqualTo(1);
			List<MessagePushTemplate> messagePushTemplateList = templateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(messagePushTemplateList)) {
				messagePushTemplate = messagePushTemplateList.get(0);
				RedisUtils.setObjEx(RedisConstants.MESSAGE_PUSH_TEMPLATE, messagePushTemplate, 24 * 60 * 60);
				return messagePushTemplate;
			}else{
				return null;
			}
		}
		return messagePushTemplate;
	}

	@Override
	public List<MessagePushTemplate> getAllTemplates() {
		MessagePushTemplateExample example = new MessagePushTemplateExample();
		MessagePushTemplateExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(CustomConstants.MSG_PUSH_STATUS_1);// 启用
		List<MessagePushTemplate> templateList = this.templateMapper.selectByExample(example);
		return templateList;
	}

	@Override
	public List<MessagePushTemplate> findMsgPushTemplate(MsgPushTemplateRequest request) {
		if (request != null) {
			MessagePushTemplateExample example = new MessagePushTemplateExample();
			MessagePushTemplateExample.Criteria cra = example.createCriteria();
			if (StringUtils.isNotBlank(request.getTagCode())) {
				cra.andTagCodeEqualTo(request.getTagCode());
			}
			if (StringUtils.isNotBlank(request.getTemplateTitle())) {
				cra.andTemplateTitleEqualTo(request.getTemplateTitle());
			}
			if (StringUtils.isNotBlank(request.getTemplateCode())) {
				cra.andTemplateCodeEqualTo(request.getTemplateCode());
			}
			if (request.getStatus() != null) {
				cra.andStatusEqualTo(request.getStatus());
			}
			return templateMapper.selectByExample(example);
		} else {
			return getAllTemplates();
		}
	}

	@Override
	public int insertMsgPushTemplate(MsgPushTemplateRequest request) {
		MessagePushTemplate messagePushTemplate = new MessagePushTemplate();
		BeanUtils.copyProperties(request, messagePushTemplate);
		return templateMapper.insert(messagePushTemplate);
	}

	@Override
	public Integer countRecord(MsgPushTemplateRequest request) {
		MessagePushTemplateExample example = new MessagePushTemplateExample();
		MessagePushTemplateExample.Criteria criteria = example.createCriteria();
		if (request.getTagId() != null) {
			criteria.andTagIdEqualTo(request.getTagId());
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(request.getTemplateTitle())) {
			criteria.andTemplateTitleLike("%"+request.getTemplateTitle()+ "%");
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(request.getTemplateCode())) {
			criteria.andTemplateCodeLike("%"+request.getTemplateCode()+ "%");
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		return this.templateMapper.countByExample(example);
	}

	@Override
	public List<MessagePushTemplate> searchList(MsgPushTemplateRequest request, int limitStart, int limitEnd) {
		MessagePushTemplateExample example = new MessagePushTemplateExample();
		MessagePushTemplateExample.Criteria criteria = example.createCriteria();
		// 条件查询
		if (limitStart != -1) {
			example.setLimitStart(limitStart);
			example.setLimitEnd(limitEnd);
		}
		if (request.getTagId() != null) {
			criteria.andTagIdEqualTo(request.getTagId());
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(request.getTemplateTitle())) {
			criteria.andTemplateTitleLike("%"+request.getTemplateTitle()+ "%");
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(request.getTemplateCode())) {
			criteria.andTemplateCodeLike("%"+request.getTemplateCode()+ "%");
		}
		if (request.getStatus() != null) {
			criteria.andStatusEqualTo(request.getStatus());
		}
		if (request.getTemplateAction() != null) {
			criteria.andTemplateActionEqualTo(request.getTemplateAction());
		}
		example.setOrderByClause("create_time DESC");
		return this.templateMapper.selectByExample(example);
	}

	@Override
	public MessagePushTemplate findMsgPushTemplateById(Integer id) {
		MessagePushTemplate page = templateMapper.selectByPrimaryKey(id);
		return page;
	}

    @Override
    public Integer insertMessagePushTemplate(MessagePushTemplate messagePushTemplate) {
        return templateMapper.insertSelective(messagePushTemplate);
    }

	@Override
	public Integer updateAction(MessagePushTemplate messagePushTemplate) {
		return templateMapper.updateByPrimaryKeySelective(messagePushTemplate);
	}

	@Override
	public Integer deleteAction(List<Integer> ids) {
		Integer result = 0;
		for (Integer id : ids) {
			result = templateMapper.deleteByPrimaryKey(id);
			result++;
		}
		return result;
	}

	@Override
	public Integer countByTemplate(Integer id, String templateCode) {
		MessagePushTemplateExample example = new MessagePushTemplateExample();
		MessagePushTemplateExample.Criteria cra = example.createCriteria();
		if (Validator.isNotNull(id)) {
			cra.andIdNotEqualTo(id);
		}
		if (org.apache.commons.lang.StringUtils.isNotEmpty(templateCode)) {
			cra.andTemplateCodeEqualTo(templateCode);
		}
		int cnt = templateMapper.countByExample(example);
		return cnt;
	}
}
