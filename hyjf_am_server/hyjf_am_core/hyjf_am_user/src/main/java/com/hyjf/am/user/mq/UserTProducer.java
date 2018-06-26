package com.hyjf.am.user.mq;

import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @author xiasq
 * @version UsereProducer, v0.1 2018/6/26 14:59
 */
@Component
public class UserTProducer extends TransactionProducer {

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup("transaction_group");
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MassageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}

}
