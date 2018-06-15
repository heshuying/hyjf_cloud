/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * 自动备案
 * @author fuqiang
 * @version AutoRecordProducer, v0.1 2018/6/14 9:55
 */
@Component
public class AutoRecordProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.BORROW_RECORD_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MassageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
