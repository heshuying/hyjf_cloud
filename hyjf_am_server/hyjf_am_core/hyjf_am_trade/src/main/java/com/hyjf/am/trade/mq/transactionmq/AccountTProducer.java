package com.hyjf.am.trade.mq.transactionmq;

import com.alibaba.fastjson.JSON;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hyjf.am.trade.controller.transactiondemo.TransactionService;
import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version AccountTProducer, v0.1 2018/6/26 14:59
 */
@Component
public class AccountTProducer extends TransactionProducer {
	private Logger logger = LoggerFactory.getLogger(AccountTProducer.class);

	@Autowired
	TransactionService transactionService;

	@Override
	protected ProducerFieldsWrapper getFieldsWrapper() {
		ProducerFieldsWrapper wrapper = new ProducerFieldsWrapper();
		wrapper.setGroup("user_transaction_group_test");
		wrapper.setInstanceName(String.valueOf(System.currentTimeMillis()));
		return wrapper;
	}

	public boolean send(MassageContent messageContent) throws MQException {
		LocalTransactionState localTransactionState = super.messageSend(messageContent);
		if (localTransactionState == LocalTransactionState.COMMIT_MESSAGE) {
			return true;
		}
		return false;
	}

	@Override
	protected LocalTransactionState doExecuteLocalTransaction(Message message, Object obj) {
		Integer userId = JSON.parseObject(message.getBody(), Integer.class);
		if (userId == null) {
			logger.error("事务消息发送失败， 参数解析错误...");
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
		try {
			// 执行本地事务
			transactionService.updateAmount(userId);
		} catch (Exception e) {
			logger.error("本地事务执行失败....", e);
			return LocalTransactionState.ROLLBACK_MESSAGE;
		}
		return LocalTransactionState.COMMIT_MESSAGE;
	}
}
