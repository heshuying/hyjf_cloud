package com.hyjf.am.admin.mq.producer;

import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.admin.mq.base.Producer;
import com.hyjf.am.admin.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * 同步账户额度生产者
 * @author zhangyk
 * @date 2018/8/3 14:36
 */
@Component
public class SyncAccountProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.SYNC_ACCOUNT_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSendDelay(MessageContent messageContent,int delayLevel) throws MQException {
        return super.messageSendDelay(messageContent,delayLevel);
    }
}
