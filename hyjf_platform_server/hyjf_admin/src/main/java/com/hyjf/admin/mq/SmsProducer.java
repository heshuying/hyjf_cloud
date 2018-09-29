package com.hyjf.admin.mq;

import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @author jijun
 * @version SmsProducer, v0.1 2018/07/11 12:09
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
	public boolean messageSend(MessageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
