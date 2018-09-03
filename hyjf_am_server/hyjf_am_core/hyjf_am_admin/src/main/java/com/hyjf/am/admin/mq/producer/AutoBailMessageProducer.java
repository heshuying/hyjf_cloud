package com.hyjf.am.admin.mq.producer;
/**
 * @Auther: Walter
 * @Date: 2018/7/11 18:48
 * @Description:
 */

import com.hyjf.am.admin.mq.base.MessageContent;
import com.hyjf.am.admin.mq.base.Producer;
import com.hyjf.am.admin.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @author: walter.limeng （从原子层am-trade移动至此by liushouyi）
 * @Date: 2018/7/11
 * @Description: AutoBailMessageProducer
 */
@Component
public class AutoBailMessageProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.ROCKETMQ_BORROW_BAIL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
