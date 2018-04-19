package com.hyjf.cs.user.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version SmsProducer, v0.1 2018/4/12 15:09
 */

@Component
public class SmsProducer extends Producer {
    private static final Logger logger = LoggerFactory.getLogger(SmsProducer.class);

    @Value("${rocketMQ.group.smsCodeGroup}")
    private String smsCodeGroup;

    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(smsCodeGroup);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MassageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
