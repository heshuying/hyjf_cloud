/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.service.consumer.TyjCouponRepayService;
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
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author yaoyong
 * @version TyjCouponRepayConsumer, v0.1 2018/8/13 11:30
 *          <p>
 *          体验金按收益期限还款 使用
 */
@Service
@RocketMQMessageListener(topic = MQConstant.TYJ_COUPON_REPAY_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.TYJ_COUPON_REPAY_GROUP)
public class TyjCouponRepayConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private static final Logger		logger	= LoggerFactory.getLogger(TyjCouponRepayConsumer.class);

	@Autowired
	private TyjCouponRepayService	tyjCouponRepayService;

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====TyjCouponRepayConsumer start=====");
	}

	@Override
	public void onMessage(MessageExt messageExt) {
		String nids = new String(messageExt.getBody());
		logger.info("体验金按收益期限还款收到消息， 开始处理, nids is : {}", nids);
		try {
			List<String> recoverNidList = splitString(nids);

			if (!CollectionUtils.isEmpty(recoverNidList)) {
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
		return;
	}

	private List<String> splitString(String nids) {
		List<String> list = null;
		if (!StringUtils.isEmpty(nids)) {
			list = Arrays.asList(nids.split(","));
		}
		return list;
	}
}
