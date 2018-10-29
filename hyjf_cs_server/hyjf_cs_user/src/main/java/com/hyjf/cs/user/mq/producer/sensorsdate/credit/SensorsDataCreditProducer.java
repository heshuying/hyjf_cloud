/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.mq.producer.sensorsdate.credit;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.base.Producer;
import com.hyjf.cs.user.mq.base.ProducerFieldsWrapper;
import org.springframework.stereotype.Component;

/**
 * 神策数据统计:债转相关相关
 *
 * @author liuyang
 * @version SensorsDataCreditProducer, v0.1 2018/10/23 9:22
 */
@Component
public class SensorsDataCreditProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.SENSORSDATA_CREDIT_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }

    @Override
    public boolean messageSendDelay(MessageContent messageContent,int delayLevel) throws MQException {
        return super.messageSendDelay(messageContent,delayLevel);
    }
}
