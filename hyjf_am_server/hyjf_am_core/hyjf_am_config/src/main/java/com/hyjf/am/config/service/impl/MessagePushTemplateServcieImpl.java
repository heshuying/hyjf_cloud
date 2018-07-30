/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTemplateMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplateExample;
import com.hyjf.am.config.service.MessagePushTemplateServcie;
import com.hyjf.am.resquest.config.MsgPushTemplateRequest;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.util.CustomConstants;

/**
 * @author fuqiang
 * @version MessagePushTemplateServcieImpl, v0.1 2018/5/8 10:42
 */
@Service
public class MessagePushTemplateServcieImpl implements MessagePushTemplateServcie {

	@Autowired
	private MessagePushTemplateMapper templateMapper;

	@Override
	public MessagePushTemplate findMessagePushTemplateByCode(String tplCode) {
		MessagePushTemplate messagePushTemplate = RedisUtils.getObj(RedisConstants.MESSAGE_PUSH_TEMPLATE,
				MessagePushTemplate.class);
		if (messagePushTemplate == null) {
			MessagePushTemplateExample example = new MessagePushTemplateExample();
			MessagePushTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTemplateCodeEqualTo(tplCode);
			List<MessagePushTemplate> messagePushTemplateList = templateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(messagePushTemplateList)) {
				messagePushTemplate = messagePushTemplateList.get(0);
				RedisUtils.setObjEx(RedisConstants.MESSAGE_PUSH_TEMPLATE, messagePushTemplate, 24 * 60 * 60);
				return messagePushTemplate;
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
	public void insertMsgPushTemplate(MsgPushTemplateRequest request) {
		MessagePushTemplate messagePushTemplate = new MessagePushTemplate();
		BeanUtils.copyProperties(request, messagePushTemplate);
		templateMapper.insert(messagePushTemplate);
	}
}
