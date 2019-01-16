package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.issuerecover.AutoPreAuditMessageService;
import com.hyjf.am.trade.service.task.issuerecover.AutoRecordService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 自动备案
 * 
 * @author walter.limeng
 * @version AutoRecordMessageConsumer, v0.1 2018/7/11 16:30
 */
@Service
@RocketMQMessageListener(topic = MQConstant.AUTO_BORROW_RECORD_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AUTO_BORROW_RECORD_GROUP)
public class AutoRecordMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AutoRecordMessageConsumer.class);

	@Resource
	private AutoRecordService autoRecordService;
	@Resource
	private CommonProducer commonProducer;
	@Autowired
	private AutoPreAuditMessageService autoPreAuditMessageService;

	@Override
	public void onMessage(MessageExt msg) {
		JSONObject params = JSONObject.parseObject(msg.getBody(), JSONObject.class);
		if (params == null) {
			logger.warn("自动备案 AutoRecordMessageConsumer 收到空消息,不处理，msgId is: {}", msg.getMsgId());
			return;
		}
		logger.info("自动备案 AutoRecordMessageConsumer 收到消息，content is: {}", params.toJSONString());

		// 1. 自动录入标的
		if (MQConstant.AUTO_BORROW_RECORD_ISSUE_TAG.equals(msg.getTags())) {
			logger.info("自动备案来源: 自动录入标的...");
			String assetId = params.getString("assetId");
			String instCode = params.getString("instCode");

			if (StringUtils.isBlank(assetId) || StringUtils.isBlank(instCode)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}
			this.doRecordBorrowByAuto(assetId, instCode);
		}

		// 2. admin手工录入标的
		else if (MQConstant.AUTO_BORROW_RECORD_ADMIN_TAG.equals(msg.getTags())) {
			logger.info("自动备案来源: admin手工录入标的...");
			String borrowNid = params.getString("borrowNid");
			String instCode = params.getString("instCode");

			if (StringUtils.isBlank(borrowNid)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}

			this.doRecordBorrowByHand(borrowNid, instCode);
		}

		// 3. 汇计划发标修复定时任务录标
		else if (MQConstant.AUTO_BORROW_RECORD_REPAIR_TAG.equals(msg.getTags())) {
			logger.info("自动备案来源: 汇计划发标修复定时任务录标...");
			String assetId = params.getString("assetId");
			String instCode = params.getString("instCode");

			if (StringUtils.isBlank(assetId) || StringUtils.isBlank(instCode)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}
			this.doRecordBorrowByAuto(assetId, instCode);
		}

	}

	private void doRecordBorrowByAuto(String assetId, String instCode) {
		HjhPlanAsset hjhPlanAsset = autoPreAuditMessageService.selectPlanAsset(assetId, instCode);
		if (hjhPlanAsset == null) {
			logger.warn(" 该资产在表里不存在！！");
			return;
		}
		// 资产自动备案
		logger.info(hjhPlanAsset.getAssetId() + " 开始自动备案 " + hjhPlanAsset.getInstCode());

		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_RECORD + hjhPlanAsset.getInstCode() + hjhPlanAsset.getAssetId();
		boolean result = RedisUtils.tranactionSet(redisKey, 300);
		if (!result) {
			logger.info(hjhPlanAsset.getInstCode() + " 正在备案(redis) " + hjhPlanAsset.getAssetId());
			return;
		}
		// 业务校验
		if (hjhPlanAsset.getStatus() != null && hjhPlanAsset.getStatus().intValue() != 3
				&& hjhPlanAsset.getVerifyStatus() != null && hjhPlanAsset.getVerifyStatus().intValue() == 1) {
			logger.warn(hjhPlanAsset.getAssetId() + " 该资产状态不是备案状态");
			return;
		}
		// 判断该资产是否可以自动备案，是否关联计划
		BorrowInfo borrowInfo = new BorrowInfo();
		borrowInfo.setInstCode(hjhPlanAsset.getInstCode());
		borrowInfo.setAssetType(hjhPlanAsset.getAssetType());
		HjhAssetBorrowtype hjhAssetBorrowType = autoRecordService.selectAssetBorrowType(borrowInfo);
		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoRecord() == null
				|| hjhAssetBorrowType.getAutoRecord() != 1) {
			logger.warn(hjhPlanAsset.getAssetId() + " 该资产不能自动备案,原因自动备案未配置");
			return;
		}

		boolean flag = autoRecordService.updateRecordBorrow(hjhPlanAsset, hjhAssetBorrowType);
		if (!flag) {
			logger.error("自动备案失败！" + "[资产编号：" + hjhPlanAsset.getAssetId() + "]");
			return;
		}

		// 成功后到初审队列
		if (hjhPlanAsset.getEntrustedFlg() != null && hjhPlanAsset.getEntrustedFlg().intValue() == 1) {
			logger.warn(hjhPlanAsset.getAssetId() + "  未推送，等待授权");
			return;
		}

		try {
			// modify by yangchangwei 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2
			JSONObject params = new JSONObject();
			params.put("assetId", assetId);
			params.put("instCode", instCode);
			commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC, assetId, params),
					2);
		} catch (MQException e) {
			logger.error("发送【自动初审】MQ失败...");
		}
		logger.info(hjhPlanAsset.getAssetId() + " 成功发送到初审队列, 自动备案处理结束");
	}

	private void doRecordBorrowByHand(String borrowNid, String instCode) {
		// admin手动录入标的
		logger.info(borrowNid + " 开始自动备案 " + instCode);
		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_RECORD + instCode + borrowNid;
		boolean result = RedisUtils.tranactionSet(redisKey, 300);
		if (!result) {
			logger.info(instCode + " 正在备案(redis) " + borrowNid);
			return;
		}
		// 获取当前标的详情
		Borrow borrow = autoRecordService.getBorrowByBorrowNid(borrowNid);
		BorrowInfo borrowInfo = autoRecordService.getBorrowInfoById(borrow.getBorrowNid());
		// 标的状态位判断
		if (null == borrow.getStatus() || borrow.getStatus() != 0 || null == borrow.getRegistStatus()
				|| borrow.getRegistStatus() != 0) {
			logger.warn("标的：" + borrow.getBorrowNid() + " 不是备案状态");
			return;
		}
		// 判断该资产是否可以自动备案，是否关联计划
		HjhAssetBorrowtype hjhAssetBorrowType = autoRecordService.selectAssetBorrowType(borrowInfo);
		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoRecord() == null
				|| hjhAssetBorrowType.getAutoRecord() != 1) {
			logger.warn(borrow.getBorrowNid() + " 标的不能自动备案,原因自动备案未配置");
			return;
		}

		boolean flag = autoRecordService.updateRecordBorrow(borrow, borrowInfo);
		if (!flag) {
			logger.error("自动备案失败！" + "[资产/标的借款编号：" + borrow.getBorrowNid() + "]");
			return;
		}

		if (borrowInfo.getEntrustedFlg() != null && borrowInfo.getEntrustedFlg().intValue() == 1) {
			logger.info(borrow.getBorrowNid() + "  未推送，等待授权");
			return;
		}

		// 发送到初审队列
		if (null != hjhAssetBorrowType && null != hjhAssetBorrowType.getAutoAudit()
				&& hjhAssetBorrowType.getAutoAudit() == 1) {
			try {
				JSONObject requestParams = new JSONObject();
				requestParams.put("borrowNid", borrow.getBorrowNid());
				// 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2 延时5秒
				commonProducer.messageSendDelay(
						new MessageContent(MQConstant.AUTO_BORROW_PREAUDIT_TOPIC, borrow.getBorrowNid(), requestParams),
						2);
			} catch (MQException e) {
				logger.error("发送【审核保证金队列】MQ失败...");
			}
			logger.info(borrow.getBorrowNid() + " 成功发送到审核保证金队列, 结束自动备案");
		}
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====AutoRecordMessageConsumer start=====");
	}
}
