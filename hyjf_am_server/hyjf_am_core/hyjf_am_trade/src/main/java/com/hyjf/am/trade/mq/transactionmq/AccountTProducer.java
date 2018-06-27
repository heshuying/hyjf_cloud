package com.hyjf.am.trade.mq.transactionmq;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.controller.demo.TransactionService;
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

	@Override
	public boolean messageSend(MassageContent messageContent) throws MQException {
		TransactionSendResult result = super.messageSend(messageContent, (msg, arg) -> {

			Integer userId = JSON.parseObject(msg.getBody(), Integer.class);
			if (userId == null) {
				logger.error("失败， 参数解析错误...");
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			// 执行本地事务
			try {
				transactionService.updateAmount(userId);
			} catch (Exception e) {
				logger.error("失败....", e);
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			logger.info("事务消息提交成功...");
			return LocalTransactionState.COMMIT_MESSAGE;
		});

		if (result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE)
			return true;
		return false;
	}

}
