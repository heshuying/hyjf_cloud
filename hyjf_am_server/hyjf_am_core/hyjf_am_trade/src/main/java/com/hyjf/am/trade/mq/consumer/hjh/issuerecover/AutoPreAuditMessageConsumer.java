package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Borrow;
import com.hyjf.am.trade.dao.model.auto.BorrowInfo;
import com.hyjf.am.trade.dao.model.auto.HjhAssetBorrowtype;
import com.hyjf.am.trade.dao.model.auto.HjhPlanAsset;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.issuerecover.AutoPreAuditMessageService;
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
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/11 19:24 自动初审
 * @Description: autoPreAuditMessageConsumer
 */
@Service
@RocketMQMessageListener(topic = MQConstant.AUTO_BORROW_PREAUDIT_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AUTO_BORROW_PREAUDIT_GROUP)
public class AutoPreAuditMessageConsumer
		implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AutoPreAuditMessageConsumer.class);

	/**
	 * 消费参数 borrow_nid
	 */
	private final List<String> BORROW_NID_TAGS_LIST = Arrays.asList(MQConstant.AUTO_BORROW_PREAUDIT_ADMIN_BAIL_TAG,
			MQConstant.AUTO_BORROW_PREAUDIT_ADMIN_RECORD_TAG, MQConstant.AUTO_BORROW_PREAUDIT_BORROW_REPAIR_TAG,
			MQConstant.AUTO_BORROW_PREAUDIT_AUTO_BAIL_TAG, MQConstant.AUTO_BORROW_PREAUDIT_BORROW_RECORD_TAG);
	/**
	 * 消费参数 inst_code + asset_id
	 */
	private final List<String> ASSET_TAGS_LIST = Arrays.asList(MQConstant.AUTO_BORROW_PREAUDIT_ADMIN_EXCEPTION_TAG,
			MQConstant.AUTO_BORROW_PREAUDIT_ASSET_REPAIR_TAG, MQConstant.AUTO_BORROW_PREAUDIT_ASSET_RECORD_TAG,
			MQConstant.AUTO_BORROW_PREAUDIT_ST_TAG);
	@Resource
	private AutoPreAuditMessageService autoPreAuditMessageService;
	@Resource
	private CommonProducer commonProducer;

	@Override
	public void onMessage(MessageExt msg) {
		JSONObject params = JSONObject.parseObject(msg.getBody(), JSONObject.class);
		if (params == null) {
			logger.warn("自动初审 AutoPreAuditMessageConsumer 收到空消息,不处理，msgId is: {}", msg.getMsgId());
			return;
		}
		logger.info("自动初审 AutoPreAuditMessageConsumer 收到消息，content is: {}", params.toJSONString());

		String tags = msg.getTags();
		logger.info("consumer tags is: {}", tags);

		// 1.标的
		if (BORROW_NID_TAGS_LIST.contains(tags)) {
			String borrowNid = params.getString("borrowNid");
			if (StringUtils.isBlank(borrowNid)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}
			this.doPreAuditBorrow(borrowNid);
		}
		// 2.资产
		else if (ASSET_TAGS_LIST.contains(tags)) {
			String assetId = params.getString("assetId");
			String instCode = params.getString("instCode");
			if (StringUtils.isBlank(assetId) || StringUtils.isBlank(instCode)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}
			this.doPreAuditBorrow(assetId, instCode);
		}

		// 3. 错误消息
		else {
			logger.warn("不明消息，不予消费....");
			return;
		}
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====AutoPreAuditMessageConsumer start=====");
	}

	private void doPreAuditBorrow(String borrowNid) {
		Borrow borrow = autoPreAuditMessageService.getBorrowByNid(borrowNid);
		BorrowInfo borrowInfo = autoPreAuditMessageService.getBorrowInfoByNid(borrowNid);
		// 自动初审
		logger.info(borrow.getBorrowNid() + " 开始自动初审 " + borrowInfo.getInstCode());
		if (borrow == null) {
			logger.warn(" 该资产在表里不存在！！");
			return;
		}
		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_PRE_AUDIT + borrowInfo.getInstCode() + borrow.getBorrowNid();
		boolean result = RedisUtils.tranactionSet(redisKey, 300);
		if (!result) {
			logger.warn(borrowInfo.getInstCode() + " 正在初审(redis) " + borrow.getBorrowNid());
			return;
		}
		// 业务校验
		if (borrow.getStatus() != null && borrow.getStatus().intValue() != 1 && borrow.getVerifyStatus() != null
				&& borrow.getVerifyStatus().intValue() != 1) {
			logger.warn(borrow.getBorrowNid() + " 该资产状态不是初审(已审核保证金)状态");
			return;
		}
		// 判断该资产是否可以自动初审，是否关联计划
		HjhAssetBorrowtype hjhAssetBorrowType = autoPreAuditMessageService
				.selectAssetBorrowType(borrowInfo.getInstCode(), borrowInfo.getAssetType());
		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoAudit() == null
				|| hjhAssetBorrowType.getAutoAudit() != 1) {
			logger.warn(borrow.getBorrowNid() + " 标的不能自动初审,原因自动初审未配置");
			return;
		}

		boolean flag = autoPreAuditMessageService.updateRecordBorrow(borrow, borrowInfo);
		if (!flag) {
			logger.error("自动初审失败！" + "[项目编号：" + borrow.getBorrowNid() + "]");
			return;
		}

		// 自动初审成功推送消息到合规上报数据
		// 6.流程自动初审成功后触发
		JSONObject params = new JSONObject();
		params.put("borrowNid", borrow.getBorrowNid());
		params.put("userId", borrow.getUserId());
        //应急中心二期，散标发标时，报送数据 start
        params.put("planNid", borrow.getBorrowNid());
        params.put("isPlan","0");
        //应急中心二期，散标发标时，报送数据 end
		commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTING_TAG, UUID.randomUUID().toString(), params),
				MQConstant.HG_REPORT_DELAY_LEVEL);

		if (borrow.getIsEngineUsed() != null && borrow.getIsEngineUsed() == 1) {
			// 成功后到关联计划队列
			this.sendAutoJoinPlanMessage(borrow.getBorrowNid());
		} else {
			logger.info("散标修改redis：借款的borrowNid,account借款总额");
			RedisUtils.set(RedisConstants.BORROW_NID + borrow.getBorrowNid(), borrow.getAccount().toString());
		}
		logger.info(borrow.getBorrowNid() + " 结束自动初审");
	}

	private void doPreAuditBorrow(String assetId, String instCode) {
		// 资产自动初审
		logger.info(assetId + " 开始自动初审 " + instCode);
		HjhPlanAsset hjhPlanAsset = autoPreAuditMessageService.selectPlanAsset(assetId, instCode);
		if (hjhPlanAsset == null) {
			logger.warn(assetId + " 该资产在表里不存在！！");
			return;
		}
		// redis 防重复检查
		String redisKeys = RedisConstants.BORROW_PRE_AUDIT + hjhPlanAsset.getInstCode() + hjhPlanAsset.getAssetId();
		boolean results = RedisUtils.tranactionSet(redisKeys, 300);
		if (!results) {
			logger.warn(hjhPlanAsset.getInstCode() + " 正在初审(redis) " + hjhPlanAsset.getAssetId());
			return;
		}
		// 业务校验
		if (hjhPlanAsset.getStatus() != null && hjhPlanAsset.getStatus().intValue() != 5
				&& hjhPlanAsset.getVerifyStatus() != null && hjhPlanAsset.getVerifyStatus().intValue() == 1) {
			logger.warn(assetId + " 该资产状态不是初审状态");
			return;
		}
		// 判断该资产是否可以自动初审，是否关联计划t
		HjhAssetBorrowtype hjhAssetBorrowType = autoPreAuditMessageService.selectAssetBorrowType(instCode,
				hjhPlanAsset.getAssetType());
		if (hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoAudit() == null
				|| hjhAssetBorrowType.getAutoAudit() != 1) {
			logger.warn("该资产不能自动初审,流程配置未启用");
			return;
		}

		// 初审修改表
		Borrow borrow = autoPreAuditMessageService.getBorrowByNid(hjhPlanAsset.getBorrowNid());
		if(borrow == null){
			logger.warn("未找到标的信息， assetId is: {}", hjhPlanAsset.getAssetId());
			return;
		}
		boolean flags = autoPreAuditMessageService.updateRecordBorrow(hjhPlanAsset, borrow);
		if (!flags) {
			logger.error("自动初审失败！" + "[资产编号：" + hjhPlanAsset.getAssetId() + "]");
			return;
		}

		// 自动初审成功推送消息到合规上报数据
		// 6.流程自动初审成功后触发
		JSONObject params = new JSONObject();
		params.put("borrowNid", borrow.getBorrowNid());
		params.put("userId", borrow.getUserId());
        //应急中心二期，散标发标时，报送数据 start
        params.put("planNid", borrow.getBorrowNid());
        params.put("isPlan","0");
        //应急中心二期，散标发标时，报送数据 end
		commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.ISSUE_INVESTING_TAG, UUID.randomUUID().toString(), params),
				MQConstant.HG_REPORT_DELAY_LEVEL);

		// 未匹配计划发送关联计划队列
		if (StringUtils.isBlank(borrow.getPlanNid())) {
			this.sendAutoJoinPlanMessage(hjhPlanAsset.getBorrowNid());
		}
		logger.info(hjhPlanAsset.getAssetId() + " 结束自动初审");
	}

	/**
	 * 发送关联计划消息
	 * 
	 * @param borrowNid
	 */
	private void sendAutoJoinPlanMessage(String borrowNid) {
		try {
			JSONObject params = new JSONObject();
			params.put("borrowNid", borrowNid);
			// 防止队列触发太快，导致无法获得本事务变泵的数据，延时级别为2
			commonProducer.messageSendDelay(new MessageContent(MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC,
					MQConstant.AUTO_ASSOCIATE_PLAN_AUTO_PRE_ISSUE_TAG, borrowNid, params), 2);
		} catch (MQException e) {
			logger.error("发送【关联计划队列】MQ失败...");
		}
	}
}
