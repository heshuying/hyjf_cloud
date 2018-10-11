package com.hyjf.am.trade.mq.producer.hjh.issuerecover;

import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.base.Producer;
import com.hyjf.am.trade.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 09:32
 * 关联计划消息
 * @Description: AutoIssueMessageProducer
 */
@Component
public class AutoIssueMessageProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.ROCKETMQ_BORROW_ISSUE_GROUP);
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
