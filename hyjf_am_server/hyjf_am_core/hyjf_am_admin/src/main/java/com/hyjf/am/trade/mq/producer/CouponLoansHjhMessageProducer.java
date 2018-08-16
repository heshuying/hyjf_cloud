package com.hyjf.am.trade.mq.producer;

import org.springframework.stereotype.Component;

import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.base.Producer;
import com.hyjf.am.trade.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 16:40
 * @Description: CouponLoansHjhMessageProducer
 */
@Component
public class CouponLoansHjhMessageProducer  extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.HJH_COUPON_LOAN_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }

}
