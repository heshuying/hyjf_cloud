/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.producer.hjh.calculatefairvalue;

import org.springframework.stereotype.Component;

import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.base.Producer;
import com.hyjf.am.trade.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * 汇计划计算加入订单公允价值MQ
 *
 * @author liuyang
 * @version HjhCalculateFairValueProducer, v0.1 2018/6/26 16:41
 */
@Component
public class HjhCalculateFairValueProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.HJH_CALCULATE_FAIR_VALUE_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}