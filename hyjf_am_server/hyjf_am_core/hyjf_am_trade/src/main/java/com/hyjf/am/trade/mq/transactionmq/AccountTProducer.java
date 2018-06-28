package com.hyjf.am.trade.mq.transactionmq;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.controller.demo.ProducerTransactionMessageService;
import com.hyjf.am.trade.dao.mapper.auto.ProducerTransactionMessageMapper;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.trade.controller.demo.TransactionService;
import com.hyjf.common.exception.MQException;

import java.util.Date;

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
			// 执行本地事务
			try {
				transactionService.updateAmount(userId);
			} catch (Exception e) {
				logger.error("事务消息发送失败....", e);
				return LocalTransactionState.ROLLBACK_MESSAGE;
			}
			logger.info("事务消息提交确认, 开始保存本地消息表...");
			this.saveProducerTransactionMessage(msg);
			return LocalTransactionState.COMMIT_MESSAGE;
		});

		// 事务消息推送成功，修改状态
		try {
			this.updateProducerTransactionMessage(messageContent, result);
		} catch (Exception e) {
			logger.error("更新推送表结果不影响业务执行....");
		}

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
		producerTransactionMessageService.save(message);
	}

	private void updateProducerTransactionMessage(MassageContent messageContent, TransactionSendResult result) {
		ProducerTransactionMessage message = producerTransactionMessageService.findByCondition(messageContent.topic,
				messageContent.keys, messageContent.tag);
		if (message == null) {
			logger.error("事务消息未提交成功...messageContent is :{}", JSONObject.toJSONString(messageContent));
			return;
		}

		if (result.getLocalTransactionState() == LocalTransactionState.COMMIT_MESSAGE) {
			message.setMessageStatus(MessageStatus.COMMIT.getCode());
		} else if (result.getLocalTransactionState() == LocalTransactionState.ROLLBACK_MESSAGE) {
			message.setMessageStatus(MessageStatus.ROLLBACK.getCode());
		} else if (result.getLocalTransactionState() == LocalTransactionState.UNKNOW) {
			message.setMessageStatus(MessageStatus.UNKKOWN.getCode());
		}
		message.setUpdateTime(new Date());
		producerTransactionMessageService.save(message);
	}

}
