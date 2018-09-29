/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.mq.producer;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.base.Producer;
import com.hyjf.cs.user.mq.base.ProducerFieldsWrapper;
import org.springframework.stereotype.Component;

/**
 * @author fuqiang
 * @version MailProducer, v0.1 2018/5/8 17:44
 */
@Component
public class MailProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.MAIL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
