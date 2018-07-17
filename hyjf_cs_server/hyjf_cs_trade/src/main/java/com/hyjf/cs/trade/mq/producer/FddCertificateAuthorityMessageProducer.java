/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.producer;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.base.Producer;
import com.hyjf.cs.trade.mq.base.ProducerFieldsWrapper;
import org.springframework.stereotype.Component;

/**
 * @author: sunpeikai
 * @version: FddCertificateAuthorityMessageProducer, v0.1 2018/7/17 10:27
 */
@Component
public class FddCertificateAuthorityMessageProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.CRM_ROUTINGKEY_BANCKOPEN_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
