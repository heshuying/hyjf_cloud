/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * 自动初审
 * @author fuqiang
 * @version AutoPreAuditProducer, v0.1 2018/6/14 14:29
 */
@Component
public class AutoPreAuditProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.BORROW_PREAUDIT_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MassageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
