/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTemplateMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplate;
import com.hyjf.am.config.dao.model.auto.MessagePushTemplateExample;
import com.hyjf.am.config.service.MessagePushTemplateServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTemplateServcieImpl, v0.1 2018/5/8 10:42
 */
public class MessagePushTemplateServcieImpl implements MessagePushTemplateServcie {

    @Autowired
    private MessagePushTemplateMapper templateMapper;

    @Override
    public MessagePushTemplate findMessagePushTemplateByCode(String tplCode) {
        MessagePushTemplateExample example = new MessagePushTemplateExample();
        MessagePushTemplateExample.Criteria criteria = example.createCriteria();
        criteria.andTemplateCodeEqualTo(tplCode);
        List<MessagePushTemplate> messagePushTemplateList = templateMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(messagePushTemplateList)) {
            return messagePushTemplateList.get(0);
        }
        return null;
    }
}
