package com.hyjf.cs.trade.mq.producer;

import org.springframework.stereotype.Component;

import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.base.Producer;
import com.hyjf.cs.trade.mq.base.ProducerFieldsWrapper;

/**
 * @Description 更新huiyingdai_utm_reg的首投信息
 * @Author sunss
 * @Date 2018/7/7 14:38
 */
@Component
public class UtmRegProducer extends Producer {

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup(MQConstant.STATISTICS_UTM_REG_GROUP);
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	@Override
	public boolean messageSend(MessageContent messageContent) throws MQException {
		return super.messageSend(messageContent);
	}
}
