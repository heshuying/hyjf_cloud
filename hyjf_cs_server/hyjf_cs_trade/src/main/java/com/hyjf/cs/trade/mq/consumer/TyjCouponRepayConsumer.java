/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.consumer.TyjCouponRepayService;

/**
 * @author yaoyong
 * @version TyjCouponRepayConsumer, v0.1 2018/8/13 11:30
 *          <p>
 *          体验金按收益期限还款 使用
 */
@Component
public class TyjCouponRepayConsumer extends Consumer {

	private static final Logger		logger	= LoggerFactory.getLogger(TyjCouponRepayConsumer.class);

	@Autowired
	private TyjCouponRepayService	tyjCouponRepayService;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.TYJ_COUPON_REPAY_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.TYJ_COUPON_REPAY_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new TyjCouponRepayConsumer.MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可
		defaultMQPushConsumer.start();
		logger.info("====TyjCouponRepayConsumer start=====");
	}

	public class MessageListener implements MessageListenerConcurrently {

		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list,
				ConsumeConcurrentlyContext consumeConcurrentlyContext) {
			MessageExt messageExt = list.get(0);
			logger.info("体验金按收益期限还款收到消息， 开始处理, messageExt is : {}", messageExt);
			String nids = new String(messageExt.getBody());
			try {
				List<String> recoverNidList = splitString(nids);

				if (CollectionUtils.isEmpty(recoverNidList)) {
					logger.info("待还款列表 : {}", JSONObject.toJSONString(recoverNidList));
					for (String recoverNid : recoverNidList) {
						tyjCouponRepayService.updateCouponOnlyRecover(recoverNid);
					}
				} else {
					logger.info("体验金按收益期限还款无数据....");
				}

			} catch (Exception e) {
				logger.error("体验金按收益期限还款失败。。。", e);
			}
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}

		private List<String> splitString(String nids) {
			List<String> list = null;
			if (!StringUtils.isEmpty(nids)) {
                list = Arrays.asList(nids.split(","));
            }
			return list;
		}
	}
}
