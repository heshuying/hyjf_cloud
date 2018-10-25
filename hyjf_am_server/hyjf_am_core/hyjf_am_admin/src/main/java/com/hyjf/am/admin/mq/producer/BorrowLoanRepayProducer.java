/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.admin.mq.producer;

import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.admin.mq.base.Producer;
import com.hyjf.am.admin.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @author wangjun
 * @version BorrowLoanRepayProducer, v0.1 2018/10/15 11:24
 */
@Component
public class BorrowLoanRepayProducer extends Producer {

    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.BORROW_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }

    @Override
    public boolean messageSendDelay(MessageContent messageContent, int delayLevel) throws MQException {
        return super.messageSendDelay(messageContent, delayLevel);
    }
}