package com.hyjf.cs.user.mq;

import com.hyjf.common.constants.MQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version CouponProducer, v0.1 2018/4/12 11:11
 */
@Component
public class CouponProducer extends Producer {

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(MQConstant.GRANT_COUPON_GROUP);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MassageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
