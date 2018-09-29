package com.hyjf.am.user.mq.producer;

import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.mq.base.Producer;
import com.hyjf.am.user.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version SmsProducer, v0.1 2018/4/12 15:09
 */

@Component
public class SmsProducer extends Producer {
	private static final Logger logger = LoggerFactory.getLogger(SmsProducer.class);

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
