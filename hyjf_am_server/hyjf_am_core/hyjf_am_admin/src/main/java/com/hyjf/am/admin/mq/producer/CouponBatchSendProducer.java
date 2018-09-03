/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.admin.mq.producer;

import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.admin.mq.base.Producer;
import com.hyjf.am.admin.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author yaoyong
 * @version CouponBatchSendProducer, v0.1 2018/9/3 15:07
 */
public class CouponBatchSendProducer extends Producer {

    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.BATCH_USER_COUPON_SEND_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
