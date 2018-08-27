package com.hyjf.am.user.mq.producer;

import org.springframework.stereotype.Component;

import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.base.Producer;
import com.hyjf.am.user.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version AccountProducer, v0.1 2018/4/12 15:09
 */

@Component
public class AccountProducer extends Producer {

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(MQConstant.ACCOUNT_GROUP);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MessageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
