package com.hyjf.am.trade.mq.transactionmq;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version TransactionProducer, v0.1 2018/4/12 11:04
 */
public abstract class TransactionProducer {
	private static final Logger logger = LoggerFactory.getLogger(TransactionProducer.class);

	private TransactionMQProducer producer;

	@Value("${rocketMQ.namesrvAddr}")
	private String namesrvAddr;

	@PostConstruct
	protected void init() throws MQClientException {
		ProducerFieldsWrapper wrapper = this.getFieldsWrapper();
		Assert.notNull(wrapper, "ProducerFieldsWrapper must not be null");

		producer = new TransactionMQProducer(wrapper.getGroup());
		producer.setNamesrvAddr(namesrvAddr);
		producer.setVipChannelEnabled(false);
		producer.setInstanceName(wrapper.getInstanceName());
		producer.setCheckThreadPoolMinSize(2);
		producer.setCheckThreadPoolMaxSize(2);
		producer.setCheckRequestHoldMax(2000);
		// 设置事务决断处理类
		producer.setTransactionCheckListener(msg -> {
			// 现在的版本取消了回查,这块不再执行，如果需要，自定义回查的代码，目前暂时不用
			logger.info("message callback, do nothing...");
			logger.info("server checking TrMsg %s%n", msg);
			return LocalTransactionState.UNKNOW;
		});
		producer.start();
	}

	protected abstract ProducerFieldsWrapper getFieldsWrapper();

	/**
	 * 
	 * @param message
	 *            消息体
	 * @param localTransactionExecuter
	 *            本地事务执行
	 * @return
	 * @throws MQClientException
	 */
	protected TransactionSendResult send(Message message, LocalTransactionExecuter localTransactionExecuter)
			throws MQClientException {
		logger.info("mq address--->{}, 开始发送事务消息, message is :{}", namesrvAddr, message);
		TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(message,
				localTransactionExecuter, null);

		logger.info("事务消息发送结果: {}", JSONObject.toJSONString(transactionSendResult));
		return transactionSendResult;
	}

	/**
	 *
	 * @param messageContent
	 * @param localTransactionExecuter
	 *            本地事务执行
	 * @return
	 * @throws MQException
	 */
	protected TransactionSendResult messageSend(MassageContent messageContent, LocalTransactionExecuter localTransactionExecuter)
			throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			return send(message, localTransactionExecuter);
		} catch (MQClientException e) {
			throw new MQException("mq send error", e);
		}
	}

	protected TransactionMQProducer getTransactionMQProducer() throws MQClientException {
		return producer;
	}

	public abstract boolean messageSend(MassageContent messageContent) throws MQException;

	public static class MassageContent implements Serializable {
		private static final long serialVersionUID = -6846413929342308237L;
		public final String topic;
		public final String tag;
		public final String keys;
		public final byte[] body;

		public MassageContent(String topic, String tag, String keys, byte[] body) {
			this.topic = topic;
			this.tag = tag;
			this.keys = keys;
			this.body = body;
		}

		public MassageContent(String topic, String keys, byte[] body) {
			this(topic, MQConstant.HYJF_DEFAULT_TAG, keys, body);
		}

	}

	protected static class ProducerFieldsWrapper {
		private String group;
		private String instanceName;

		public String getGroup() {
			return group;
		}

		public void setGroup(String group) {
			this.group = group;
		}

		public String getInstanceName() {
			return instanceName;
		}

		public void setInstanceName(String instanceName) {
			this.instanceName = instanceName;
		}
	}
}
