package com.hyjf.am.trade.mq.producer;

import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.base.Producer;
import com.hyjf.am.trade.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 14:39
 * 风车理财回调通知消息生产者
 * @Description: WrbCallBackProducer
 */
@Component
public class WrbCallBackProducer extends Producer {
    private static final Logger logger = LoggerFactory.getLogger(WrbCallBackProducer.class);

    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.WRB_QUEUE_CALLBACK_NOTIFY_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
        return super.messageSend(messageContent);
    }
}
