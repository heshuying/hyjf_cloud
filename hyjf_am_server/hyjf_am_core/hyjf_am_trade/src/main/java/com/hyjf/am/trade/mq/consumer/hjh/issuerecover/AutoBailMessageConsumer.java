package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.issuerecover.AutoBailMessageService;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
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
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/11 18:52
 * @Description: AutoBailMessageConsumer 自动审核保证金
 */
@Service
@RocketMQMessageListener(topic = MQConstant.AUTO_VERIFY_BAIL_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AUTO_VERIFY_BAIL_GROUP)
public class AutoBailMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AutoBailMessageConsumer.class);

	@Resource
	private AutoBailMessageService autoBailMessageService;
	@Resource
	private CommonProducer commonProducer;

	@Override
	public void onMessage(MessageExt msg) {
		JSONObject params = JSONObject.parseObject(msg.getBody(), JSONObject.class);
		if (params == null) {
			logger.warn("自动审核保证金 AutoBailMessageConsumer 收到空消息,不处理，msgId is: {}", msg.getMsgId());
			return;
		}
		logger.info("自动审核保证金 AutoBailMessageConsumer 收到消息，content is: {}", params.toJSONString());

		String borrowNid = params.getString("borrowNid");
		if (StringUtils.isBlank(borrowNid)) {
			logger.warn("参数不全, msgId is: {}", msg.getMsgId());
			return;
		}

		// 1. admin标的备案成功
		if (MQConstant.AUTO_VERIFY_BAIL_ADMIN_TAG.equals(msg.getTags())) {
			logger.info("自动审核保证金来源: admin标的备案成功...");
			// do nothing, only remark
		}
		// 2. 汇计划发标修复定时任务
		else if (MQConstant.AUTO_VERIFY_BAIL_REPAIR_TAG.equals(msg.getTags())) {
			logger.info("自动审核保证金来源: 汇计划发标修复定时任务...");
			// do nothing, only remark
		}
		// 错误消息
		else {
			logger.warn("不明消息，不予消费....");
			return;
		}

		Borrow borrow = autoBailMessageService.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = autoBailMessageService.getBorrowInfoByNid(borrowNid);
		if (borrow == null || borrowInfo == null) {
			logger.info(borrowNid + " 该标的不存在！！");
			return;
		}

		// 自动审核保证金
		logger.info(borrowNid + " 开始自动审核保证金 " + borrowInfo.getInstCode());
		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_BAIL + borrowInfo.getInstCode() + borrowNid;
		boolean result = RedisUtils.tranactionSet(redisKey, 300);
		if (!result) {
			logger.info(borrowInfo.getInstCode() + " 正在自动审核保证金(redis) " + borrowNid);
			return;
		}
		// 业务校验
		if (borrow.getStatus() != null && borrow.getStatus().intValue() == 1 && borrow.getVerifyStatus() != null
				&& borrow.getVerifyStatus().intValue() == 0) {
			logger.info(borrow.getBorrowNid() + " 该标的状态为审核保证金状态、开始自动审核");
		} else {
			logger.warn(borrow.getBorrowNid() + " 该标的状态不是审核保证金状态");
			return;
		}
		// 判断该资产是否可以自动审核保证金
		HjhAssetBorrowtype hjhAssetBorrowType = autoBailMessageService.selectAssetBorrowType(borrowInfo.getInstCode(),
				borrowInfo.getAssetType());
		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoBail() == null
				|| hjhAssetBorrowType.getAutoBail() != 1) {
			logger.warn("该资产不能自动审核保证金,流程配置未启用");
			return;
		}

		boolean flag = autoBailMessageService.updateRecordBorrow(borrow);
		if (!flag) {
			logger.error("自动审核保证金失败！" + "[资产编号：" + borrowNid + "]");
			return;
		}

		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoAudit() == null
				|| hjhAssetBorrowType.getAutoAudit() != 1) {
			logger.warn("该资产不发初审队列,流程配置未启用");
			return;
		}
		try {
			commonProducer.messageSend(new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC,
					MQConstant.AUTO_BORROW_PREAUDIT_AUTO_BAIL_TAG, borrowNid, params));
		} catch (MQException e) {
			logger.error("发送【初审队列】MQ失败...");
		}
		logger.info(borrowNid + " 成功发送到初审队列, 结束自动审核保证金");
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====AutoBailMessageConsumer start=====");
	}
}
