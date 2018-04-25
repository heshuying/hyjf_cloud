package com.hyjf.am.user.mq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version AccountProducer, v0.1 2018/4/12 15:09
 */

@Component
public class AccountProducer extends Producer {
	private static final Logger logger = LoggerFactory.getLogger(AccountProducer.class);

	@Value("${rocketMQ.group.accuontGroup}")
	private String accuontGroup;

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(accuontGroup);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MassageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
