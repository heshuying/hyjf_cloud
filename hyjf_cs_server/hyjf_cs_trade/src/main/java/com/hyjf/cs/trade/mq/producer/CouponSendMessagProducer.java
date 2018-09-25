package com.hyjf.cs.trade.mq.producer;

import org.springframework.stereotype.Component;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.base.Producer;
import com.hyjf.cs.trade.mq.base.ProducerFieldsWrapper;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 09:21
 * @Description: CouponSendMessagProducer
 */
@Component
public class CouponSendMessagProducer extends Producer {

    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.GRANT_COUPON_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
