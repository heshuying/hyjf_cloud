/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @author zhangqingqing
 * @version CrmBankOpenMessageProducer, v0.1 2018/6/28 14:23
 */
@Component
public class CrmBankOpenMessageProducer extends Producer {
    @Override
    protected Producer.ProducerFieldsWrapper getFieldsWrapper() {
        Producer.ProducerFieldsWrapper wrapper = new Producer.ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.CRM_ROUTINGKEY_BANCKOPEN_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(Producer.MassageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
