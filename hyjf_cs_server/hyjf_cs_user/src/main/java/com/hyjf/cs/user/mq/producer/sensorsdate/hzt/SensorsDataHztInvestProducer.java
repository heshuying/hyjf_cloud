/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.mq.producer.sensorsdate.hzt;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.base.Producer;
import com.hyjf.cs.user.mq.base.ProducerFieldsWrapper;
import org.springframework.stereotype.Component;

/**
 * 神策数据统计:散标投资相关
 *
 * @author liuyang
 * @version SensorsDataHztInvestProducer, v0.1 2018/10/25 10:07
 */
@Component
public class SensorsDataHztInvestProducer extends Producer {

    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.SENSORSDATA_HZT_INVEST_GROUP);
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
