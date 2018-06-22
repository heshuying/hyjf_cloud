/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTemplateMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplateExample;
import com.hyjf.am.config.service.MessagePushTemplateServcie;
import com.hyjf.am.vo.config.MessagePushTemplateVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.util.CommonUtils;
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
		MessagePushTemplate messagePushTemplate = RedisUtils.getObj(RedisKey.MESSAGE_PUSH_TEMPLATE,
				MessagePushTemplate.class);
		if (messagePushTemplate == null) {
			MessagePushTemplateExample example = new MessagePushTemplateExample();
			MessagePushTemplateExample.Criteria criteria = example.createCriteria();
			criteria.andTemplateCodeEqualTo(tplCode);
			List<MessagePushTemplate> messagePushTemplateList = templateMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(messagePushTemplateList)) {
				messagePushTemplate = messagePushTemplateList.get(0);
				RedisUtils.setObjEx(RedisKey.MESSAGE_PUSH_TEMPLATE, messagePushTemplate, 24 * 60 * 60);
				return messagePushTemplate;
			}
		}
		return messagePushTemplate;
	}

	@Override
	public List<MessagePushTemplateVO> getAllTemplates() {
		MessagePushTemplateExample example = new MessagePushTemplateExample();
		MessagePushTemplateExample.Criteria cra = example.createCriteria();
		cra.andStatusEqualTo(CustomConstants.MSG_PUSH_STATUS_1);// 启用
		List<MessagePushTemplate> templateList = this.templateMapper.selectByExample(example);
		List<MessagePushTemplateVO> templateVOList = CommonUtils.convertBeanList(templateList,
				MessagePushTemplateVO.class);
		return templateVOList;
	}
}
