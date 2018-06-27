/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.config.service.impl;

import com.hyjf.am.config.dao.mapper.auto.MessagePushTagMapper;
import com.hyjf.am.config.dao.model.auto.MessagePushTag;
import com.hyjf.am.config.dao.model.auto.MessagePushTagExample;
import com.hyjf.am.config.service.MessagePushTagServcie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author fuqiang
 * @version MessagePushTagServcieImpl, v0.1 2018/6/26 11:48
 */
@Service
public class MessagePushTagServcieImpl implements MessagePushTagServcie {

    @Autowired
    private MessagePushTagMapper messagePushTagMapper;

    @Override
    public MessagePushTag findMsgTagByTagName(String tagName) {
        MessagePushTagExample example = new MessagePushTagExample();
        example.createCriteria().andTagNameEqualTo(tagName);
        List<MessagePushTag> messagePushTagList = messagePushTagMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(messagePushTagList)) {
            return messagePushTagList.get(0);
        }
        return null;
    }
}
