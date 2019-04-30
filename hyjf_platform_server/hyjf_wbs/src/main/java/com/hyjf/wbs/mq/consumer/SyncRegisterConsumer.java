/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.mq.consumer;

/**
 * @author cui
 * @version SyncRegisterConsumer, v0.1 2019/4/17 15:20
 */

import java.util.UUID;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.user.service.SyncCustomerBaseService;
import com.hyjf.wbs.user.service.SyncCustomerService;

@Service
@RocketMQMessageListener(topic = MQConstant.WBS_REGISTER_TOPIC, selectorExpression = MQConstant.WBS_REGISTER_TAG, consumerGroup = MQConstant.WBS_REGISTER_GROUP)
public class SyncRegisterConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SyncCustomerBaseService syncCustomerBaseService;

	@Autowired
	private SyncCustomerService syncCustomerService;

	@Override
	public void onMessage(MessageExt messageExt) {

		String uuid = UUID.randomUUID().toString();
		logger.info("【{}】WBS客户同步消息:开始处理....", uuid);

		String msgBody = new String(messageExt.getBody());
		WbsRegisterMqVO wbsRegisterMqVO = JSONObject.parseObject(msgBody, WbsRegisterMqVO.class);

		logger.info("【{}】消息内容【{}】", uuid, wbsRegisterMqVO);

		CustomerSyncQO customerSyncQO=syncCustomerBaseService.build(wbsRegisterMqVO);

		syncCustomerService.sync(customerSyncQO);

		logger.info("【{}】WBS客户同步消息:处理结束....", uuid);
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		// 设置并发数
		defaultMQPushConsumer.setConsumeThreadMin(1);
		defaultMQPushConsumer.setConsumeThreadMax(1);
		defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
		defaultMQPushConsumer.setConsumeTimeout(30);
		logger.info("====SyncRegisterConsumer监听初始化完成, 启动完毕=====");
	}
}
