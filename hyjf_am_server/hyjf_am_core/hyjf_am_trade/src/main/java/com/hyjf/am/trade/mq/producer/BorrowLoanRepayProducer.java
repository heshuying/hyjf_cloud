package com.hyjf.am.trade.mq.producer;

import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.base.Producer;
import com.hyjf.am.trade.mq.base.ProducerFieldsWrapper;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import org.springframework.stereotype.Component;

/**
 * @author dxj
 * @version BorrowLoanRepayProducer, v0.1 2018/4/12 15:09
 */

@Component
public class BorrowLoanRepayProducer extends Producer {

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(MQConstant.BORROW_GROUP);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MessageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}

	@Override
	public boolean messageSendDelay(MessageContent messageContent, int delayLevel) throws MQException {
		return super.messageSendDelay(messageContent, delayLevel);
	}
}
