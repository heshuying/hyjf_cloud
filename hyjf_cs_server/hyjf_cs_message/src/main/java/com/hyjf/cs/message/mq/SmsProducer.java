package com.hyjf.cs.message.mq;

import org.springframework.stereotype.Component;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version SmsProducer, v0.1 2018/4/12 15:09
 */

@Component
public class SmsProducer extends Producer {

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(MQConstant.SMS_CODE_GROUP);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MassageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
