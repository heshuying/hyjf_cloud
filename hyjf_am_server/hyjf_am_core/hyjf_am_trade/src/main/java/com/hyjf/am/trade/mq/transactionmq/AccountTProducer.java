package com.hyjf.am.trade.mq.transactionmq;

import java.util.Date;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.controller.demo.ProducerTransactionMessageService;
import com.hyjf.am.trade.controller.demo.TransactionService;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
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
	@Autowired
	ProducerTransactionMessageService producerTransactionMessageService;

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
				logger.error("事务消息发送失败， 参数解析错误...");
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			try {
				// 执行本地事务
				transactionService.updateAmount(userId);
				// 保存本地消息表
				this.saveProducerTransactionMessage(msg);
			} catch (Exception e) {
				logger.error("本地事务执行失败....", e);
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			return LocalTransactionState.COMMIT_MESSAGE;
		});

		if (result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
			return true;
		}
		return false;
	}

	private void saveProducerTransactionMessage(Message msg) {
		ProducerTransactionMessage message = new ProducerTransactionMessage();
		message.setMessageStatus(MessageStatus.UNKKOWN.getCode());
		message.setRetryTimes(0);
		message.setTags(msg.getTags());
		message.setKeys(msg.getKeys());
		message.setTopic(msg.getTopic());
		message.setBody(new String(msg.getBody()));
		message.setCreateTime(new Date());
		message.setUpdateTime(new Date());
		producerTransactionMessageService.insert(message);
	}
}
