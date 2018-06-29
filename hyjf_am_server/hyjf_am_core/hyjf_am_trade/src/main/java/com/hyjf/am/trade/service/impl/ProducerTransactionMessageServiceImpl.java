package com.hyjf.am.trade.service.impl;

import com.hyjf.am.trade.dao.mapper.auto.ProducerTransactionMessageMapper;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessage;
import com.hyjf.am.trade.dao.model.auto.ProducerTransactionMessageExample;
import com.hyjf.am.trade.mq.transactionmq.AccountTProducer;
import com.hyjf.am.trade.mq.transactionmq.MessageStatus;
import com.hyjf.am.trade.service.ProducerTransactionMessageService;
import com.hyjf.common.util.GetDate;
import org.apache.rocketmq.client.QueryResult;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author xiasq
 * @version ProducerTransactionMessageServiceImpl, v0.1 2018/6/28 15:18
 */
@Service
public class ProducerTransactionMessageServiceImpl implements ProducerTransactionMessageService {
	private Logger logger = LoggerFactory.getLogger(ProducerTransactionMessageServiceImpl.class);

	@Autowired
	ProducerTransactionMessageMapper producerTransactionMessageMapper;
	@Autowired
	private AccountTProducer accountTProducer;

	/**
	 * 保存
	 * 
	 * @param message
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void insert(ProducerTransactionMessage message) {
		producerTransactionMessageMapper.insertSelective(message);
	}

	/**
	 * 更新
	 * @param message
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(ProducerTransactionMessage message){
		producerTransactionMessageMapper.updateByPrimaryKey(message);
	}

	/**
	 * 根据特定条件查询
	 * 
	 * @param topic
	 * @param keys
	 * @param tag
	 * @return
	 */
	@Override
	public ProducerTransactionMessage findByCondition(String topic, String keys, String tag) {
		ProducerTransactionMessageExample example = new ProducerTransactionMessageExample();
		ProducerTransactionMessageExample.Criteria criteria = example.createCriteria();
		criteria.andTopicEqualTo(topic).andTagsEqualTo(tag).andKeysEqualTo(keys);

		List<ProducerTransactionMessage> list = producerTransactionMessageMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		if (list.size() > 1) {
			throw new RuntimeException("消息不唯一...");
		}

		return list.get(0);
	}

	/**
	 * 事务回查
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void callBackQuery() throws MQClientException, InterruptedException, RemotingException, MQBrokerException {
		ProducerTransactionMessageExample example = new ProducerTransactionMessageExample();
		ProducerTransactionMessageExample.Criteria criteria = example.createCriteria();
		// 回查1天内的未确认的事务
		criteria.andMessageStatusEqualTo(MessageStatus.UNKKOWN.getCode());
		Date endDate = new Date();
		Date startDate = GetDate.getDayStartOfSomeDay(endDate);
		criteria.andCreateTimeBetween(startDate, endDate);
		List<ProducerTransactionMessage> list = producerTransactionMessageMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			for (ProducerTransactionMessage message : list) {
				// 回查3次以上，重新发送确认消息，否则继续回查， 避免生产端刚发送消息就出发回查，这时候无需重发确认
				if (message.getRetryTimes() >= 3) {
					// todo 重发的消息需要修改sysflag是8，现在是发普通消息 怎么做？
					Message repeatMsg = new Message();
					repeatMsg.setBody(message.getBody().getBytes());
					repeatMsg.setKeys(message.getKeys());
					repeatMsg.setTags(message.getTags());
					repeatMsg.setTopic(message.getTopic());
					logger.info("重发消息....");
					accountTProducer.getTransactionMQProducer().send(repeatMsg);

					message.setMessageStatus(MessageStatus.COMMIT.getCode());
					message.setRetryTimes(message.getRetryTimes() + 1);
					this.update(message);
				} else {
					this.callBackQueryFromMQ(message, startDate, endDate);
				}
			}
		}
	}

	/**
	 * 根据key查询mq的消息
	 * 
	 * @param keys
	 */
	private void callBackQueryFromMQ(ProducerTransactionMessage message, Date startDate, Date endDate)
			throws MQClientException, InterruptedException {

		QueryResult queryResult = accountTProducer.getTransactionMQProducer().queryMessage(message.getTopic(),
				message.getKeys(), 64, startDate.getTime(), endDate.getTime());
		List<MessageExt> msgList = queryResult.getMessageList();
		if (CollectionUtils.isEmpty(msgList)) {
			throw new RuntimeException("根据key值未查询到消息...");
		}
		for (MessageExt msg : msgList) {
			// sysFlag 4:prepared消息 8：确认消息
			if (msg.getSysFlag() == 8) {
				// 事务提交成功
				message.setMessageStatus(MessageStatus.COMMIT.getCode());
				message.setRetryTimes(message.getRetryTimes() + 1);
				message.setMsgId(msg.getMsgId());
				this.update(message);
				return;
			}
		}
		message.setRetryTimes(message.getRetryTimes() + 1);
		this.update(message);
	}
}
