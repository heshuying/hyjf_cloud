package com.hyjf.am.config.mq.producer;

import com.hyjf.am.config.mq.base.MessageContent;
import com.hyjf.am.config.mq.base.Producer;
import com.hyjf.am.config.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * @author dxj
 * @date 2018/07/06
 */
@Component
public class TestProducer extends Producer {
	private static final Logger logger = LoggerFactory.getLogger(TestProducer.class);

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(MQConstant.TEST_GROUP);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MessageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
