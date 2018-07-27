/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.producer;

import org.springframework.stereotype.Component;

import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.base.Producer;
import com.hyjf.am.user.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * 用户原子服务通用生产者
 * @author dxj
 * @version AmUserProducer, v0.1 2018/6/28 14:23
 */
@Component
public class AmUserProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.AM_USER_GENERAL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
