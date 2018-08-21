package com.hyjf.am.trade.mq.transactionmq;

import java.io.Serializable;
import java.util.concurrent.*;

import javax.annotation.PostConstruct;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.cache.RedisUtils;
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

		// 设置事务决断处理类
		TransactionListener transactionListener = new TransactionListenerImpl();
		producer.setTransactionListener(transactionListener);

		ExecutorService executorService = new ThreadPoolExecutor(2, 5, 100, TimeUnit.SECONDS,
				new ArrayBlockingQueue<Runnable>(2000), new ThreadFactory() {
					@Override
					public Thread newThread(Runnable r) {
						Thread thread = new Thread(r);
						thread.setName("client-transaction-msg-check-thread");
						return thread;
					}
				});
		producer.setExecutorService(executorService);

		producer.start();
	}

	protected abstract ProducerFieldsWrapper getFieldsWrapper();

	/**
	 *
	 * @param message
	 *            消息体
	 * @return
	 * @throws MQClientException
	 */
	protected LocalTransactionState send(Message message) throws MQClientException {
		logger.info("mq address--->{}, 开始发送事务消息, message is :{}", namesrvAddr, message);
		TransactionSendResult transactionSendResult = producer.sendMessageInTransaction(message, null);
		logger.info("事务消息发送结果: {}", JSONObject.toJSONString(transactionSendResult));
		return transactionSendResult != null ? transactionSendResult.getLocalTransactionState() : null;
	}

	/**
	 *
	 * @param messageContent
	 * @return
	 * @throws MQException
	 */
	public LocalTransactionState messageSend(MassageContent messageContent) throws MQException {
		try {
			Message message = new Message(messageContent.topic, messageContent.tag, messageContent.keys,
					messageContent.body);
			return send(message);
		} catch (MQClientException e) {
			throw new MQException("mq send error", e);
		}
	}

	public TransactionMQProducer getTransactionMQProducer() throws MQClientException {
		return producer;
	}

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

	/**
	 * 执行本地事务
	 *
	 * @param message
	 * @param obj
	 * @return
	 */
	protected abstract LocalTransactionState doExecuteLocalTransaction(Message message, Object obj);

	class TransactionListenerImpl implements TransactionListener {
		private Logger logger = LoggerFactory.getLogger(getClass());

		/**
		 * 执行本地事务
		 * 
		 * @param message
		 * @param obj
		 * @return
		 */
		@Override
		public LocalTransactionState executeLocalTransaction(Message message, Object obj) {
			logger.info("execute local transaction start...");
			LocalTransactionState localTransactionState = doExecuteLocalTransaction(message, obj);
			// 本地事务执行结果存入redis，便于回查使用
			String redisKey = getLocalTransactionResultKey(message.getTopic(), message.getTags(), message.getKeys());
			RedisUtils.setObj(redisKey, localTransactionState);
			logger.info("execute local transaction end, result is: {}", localTransactionState);
			return localTransactionState == null ? LocalTransactionState.UNKNOW : localTransactionState;
		}

		/**
		 * 检查本地事务执行结果
		 * 
		 * @param messageExt
		 * @return
		 */
		@Override
		public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
			logger.info("mq server check local transaction start...");
			String redisKey = getLocalTransactionResultKey(messageExt.getTopic(), messageExt.getTags(),
					messageExt.getKeys());
			LocalTransactionState localTransactionState = RedisUtils.getObj(redisKey, LocalTransactionState.class);
			logger.info("mq server check local transaction end, result is: {}", localTransactionState);
			return localTransactionState == null ? LocalTransactionState.UNKNOW : localTransactionState;
		}

		private String getLocalTransactionResultKey(String topic, String tags, String key) {
			return topic + ":" + tags + ":" + key;
		}
	}
}
