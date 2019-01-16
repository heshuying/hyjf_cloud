package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import org.apache.commons.lang3.StringUtils;
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
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * 自动录标
 * 
 * @author walter.limeng
 * @version AutoIssueBorrowMessageConsumer, v0.1 2018/7/11 10:30
 */
@Service
@RocketMQMessageListener(topic = MQConstant.AUTO_ISSUE_RECOVER_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AUTO_ISSUE_RECOVER_GROUP)
public class AutoIssueBorrowMessageConsumer
		implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AutoIssueBorrowMessageConsumer.class);
	@Autowired
	private AutoIssueRecoverService autoIssueRecoverService;
	@Autowired
	private CommonProducer commonProducer;
	@Autowired
	private AutoPreAuditMessageService autoPreAuditMessageService;

	@Override
	public void onMessage(MessageExt msg) {
		JSONObject params = JSONObject.parseObject(msg.getBody(), JSONObject.class);
		if (params == null) {
			logger.warn("自动录标 AutoIssueBorrowMessageConsumer收到空消息,不处理，msgId is: {}", msg.getMsgId());
			return;
		}
		logger.info("自动录标 AutoIssueBorrowMessageConsumer 收到消息，content is: {}", params.toJSONString());

		String assetId = params.getString("assetId");
		String instCode = params.getString("instCode");

		if (StringUtils.isBlank(assetId) || StringUtils.isBlank(instCode)) {
			logger.warn("参数不全, msgId is: {}", msg.getMsgId());
			return;
		}

		HjhPlanAsset hjhPlanAsset = autoPreAuditMessageService.selectPlanAsset(assetId, instCode);
		if (hjhPlanAsset == null) {
			logger.warn(" 该资产在表里不存在！！");
			return;
		}

		// 1. 资产推送录标
		if (MQConstant.AUTO_ISSUE_RECOVER_PUSH_TAG.equals(msg.getTags())) {
			logger.info("自动录标来源: 资产推送录标...");
			// do nothing, only remark
		}
		// 2. 汇计划发标修复定时任务录标
		else if (MQConstant.AUTO_ISSUE_RECOVER_REPAIR_TAG.equals(msg.getTags())) {
			logger.info("自动录标来源: 汇计划发标修复定时任务录标...");
			// do nothing, only remark
		}

		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_SEND + hjhPlanAsset.getInstCode() + hjhPlanAsset.getAssetId();
		boolean result = RedisUtils.tranactionSet(redisKey, 60 * 5);
		if (!result) {
			logger.info(hjhPlanAsset.getInstCode() + " 正在录标 (redis)" + hjhPlanAsset.getAssetId());
			return;
		}
		// 业务校验
		if (hjhPlanAsset.getStatus() != null && hjhPlanAsset.getStatus().intValue() != 0
				&& hjhPlanAsset.getVerifyStatus() != null && hjhPlanAsset.getVerifyStatus().intValue() == 1) {
			logger.warn(hjhPlanAsset.getAssetId() + " 该资产状态不是录标状态");
			return;
		}
		// 判断该资产是否可以自动录标，是否关联计划
		HjhAssetBorrowtype hjhAssetBorrowType = autoIssueRecoverService.selectAssetBorrowType(hjhPlanAsset);
		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoAdd() != 1) {
			logger.warn(" 该资产不能自动录标,流程配置未启用");
			return;
		}

		boolean flag = autoIssueRecoverService.insertSendBorrow(hjhPlanAsset, hjhAssetBorrowType);
		if (!flag) {
			logger.warn("自动录标失败！" + "[资产编号：" + hjhPlanAsset.getAssetId() + "]");
			return;
		}

		// 成功后到备案队列
		try {
			// modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2
			commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_BORROW_RECORD_TOPIC,
					MQConstant.AUTO_BORROW_RECORD_ISSUE_TAG, hjhPlanAsset.getAssetId(), params), 2);
		} catch (MQException e) {
			logger.error("发送【自动录标】MQ失败...");
		}
		logger.info(hjhPlanAsset.getAssetId() + " 结束自动录标");
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====AutoIssueBorrowMessageConsumer start=====");
	}
}
