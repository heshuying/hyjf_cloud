/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTemplateMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplateExample;
import com.hyjf.am.config.redis.RedisUtil;
import com.hyjf.am.config.service.MessagePushTemplateServcie;
import com.hyjf.common.constants.RedisKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author fuqiang
 * @version MessagePushTemplateServcieImpl, v0.1 2018/5/8 10:42
 */
@Service
public class MessagePushTemplateServcieImpl implements MessagePushTemplateServcie {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private MessagePushTemplateMapper templateMapper;

    @Override
    public MessagePushTemplate findMessagePushTemplateByCode(String tplCode) {
        MessagePushTemplate messagePushTemplate = (MessagePushTemplate) redisUtil.get(RedisKey.MESSAGE_PUSH_TEMPLATE);
        if (messagePushTemplate == null) {
            MessagePushTemplateExample example = new MessagePushTemplateExample();
            MessagePushTemplateExample.Criteria criteria = example.createCriteria();
            criteria.andTemplateCodeEqualTo(tplCode);
            List<MessagePushTemplate> messagePushTemplateList = templateMapper.selectByExample(example);
            if (!CollectionUtils.isEmpty(messagePushTemplateList)) {
                messagePushTemplate = messagePushTemplateList.get(0);
                redisUtil.setEx(RedisKey.MESSAGE_PUSH_TEMPLATE, messagePushTemplate, 1, TimeUnit.DAYS);
                return messagePushTemplate;
            }
        }
        return messagePushTemplate;
    }
}
