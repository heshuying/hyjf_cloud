/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.producer;

import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.base.Producer;
import com.hyjf.am.trade.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.stereotype.Component;

/**
 *  交易原子服务通用生产者
 * @author dxj
 * @version AmTradeProducer, v0.1 2018/6/28 14:23
 */
@Component
public class AmTradeProducer extends Producer {
    @Override
    protected ProducerFieldsWrapper getFieldsWrapper() {
        ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
        wrapper.setGroup(MQConstant.AM_TRADE_GENERAL_GROUP);
        wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
        return wrapper;
    }

    @Override
    public boolean messageSend(MessageContent messageContent) throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			
			// 为了设置延时消息
//			message.setDelayTimeLevel(2);
			return send(message);
		} catch (MQClientException | RemotingException | MQBrokerException e ) {
			throw new MQException("mq send error", e);
		} catch (InterruptedException e){
			Thread.currentThread().interrupt();
			throw new MQException("mq InterruptedException send error", e);
		}
	}
}
