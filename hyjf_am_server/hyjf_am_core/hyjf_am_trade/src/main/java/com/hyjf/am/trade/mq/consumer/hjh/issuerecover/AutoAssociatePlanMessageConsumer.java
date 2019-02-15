package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.issuerecover.AutoIssueMessageService;
import com.hyjf.am.trade.service.issuerecover.AutoIssueRecoverService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
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

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 10:56
 * @Description: AutoAssociatePlanMessageConsumer 关联计划
 */
@Service
@RocketMQMessageListener(topic = MQConstant.AUTO_ASSOCIATE_PLAN_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.AUTO_ASSOCIATE_PLAN_GROUP)
public class AutoAssociatePlanMessageConsumer
		implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AutoAssociatePlanMessageConsumer.class);
	/**
	 * 普通标的
	 */
	private final List<String> BORROW_NID_TAGS_LIST = Arrays.asList(MQConstant.AUTO_ASSOCIATE_PLAN_JOB_TAG,
			MQConstant.AUTO_ASSOCIATE_PLAN_AUTO_PRE_ISSUE_TAG, MQConstant.AUTO_ASSOCIATE_PLAN_BORROW_REPAIR_TAG,
			MQConstant.AUTO_ASSOCIATE_PLAN_ADMIN_INSERT_TAG, MQConstant.AUTO_ASSOCIATE_PLAN_ADMIN_ISSUE_TAG);
	/**
	 * 计划内债转标的
	 */
	private final List<String> CREDIT_NID_TAGS_LIST = Arrays.asList(MQConstant.AUTO_ASSOCIATE_PLAN_CLEAR_TAG,
			MQConstant.AUTO_ASSOCIATE_PLAN_CREDIT_REPAIR_TAG);
	@Autowired
	private AutoIssueMessageService autoIssueMessageService;
	@Autowired
	private AutoIssueRecoverService autoIssueRecoverService;
	@Autowired
	private CommonProducer commonProducer;

	@Override
	public void onMessage(MessageExt msg) {
		JSONObject params = JSONObject.parseObject(msg.getBody(), JSONObject.class);
		if (params == null) {
			logger.warn("自动关联计划 AutoAssociatePlanMessageConsumer 收到空消息,不处理，msgId is: {}", msg.getMsgId());
			return;
		}
		logger.info("自动关联计划 AutoAssociatePlanMessageConsumer 收到消息，content is: {}", params.toJSONString());

		String tags = msg.getTags();
		logger.info("consumer tags is: {}", tags);

		// 1. 原始标的
		if (BORROW_NID_TAGS_LIST.contains(tags)) {
			String borrowNid = params.getString("borrowNid");
			if (StringUtils.isBlank(borrowNid)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}
			this.doJoinPlanOfBorrow(borrowNid);
		}
		// 2. 债转标的情况
		else if (CREDIT_NID_TAGS_LIST.contains(tags)) {
			String creditNid = params.getString("creditNid");
			if (StringUtils.isBlank(creditNid)) {
				logger.warn("参数不全, msgId is: {}", msg.getMsgId());
				return;
			}
			this.doJoinPlanOfCredit(creditNid);
		}

		// 3. 错误消息
		else {
			logger.warn("不明消息，不予消费....");
			return;
		}
	}

	private void doJoinPlanOfBorrow(String borrowNid) {
		logger.info(borrowNid + " 原始标开始关联计划 ");
		Borrow borrow = autoIssueMessageService.getBorrowByNid(borrowNid);
		if (borrow == null) {
			logger.warn(borrowNid + " 该标的在表里不存在");
			return;
		}
		BorrowInfo borrowInfo = autoIssueMessageService.getBorrowInfoByNid(borrowNid);

		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_ISSUE + borrowNid;
		boolean result = RedisUtils.tranactionSet(redisKey, 300);
		if (!result) {
			logger.warn(borrowNid + " 正在关联计划(redis)");
			return;
		}

		// 业务校验
		// 发标的状态
		if (borrow.getStatus() != 2 || borrow.getVerifyStatus() != 4) {
			logger.warn(borrowNid + " 该标的不是已经发标的状态 ");
			return;
		}
		if (StringUtils.isNotBlank(borrow.getPlanNid())) {
			logger.warn(borrowNid + " 该标的已经绑定计划 " + borrow.getLabelId());
			return;
		}
		// 第三方资产
		HjhPlanAsset asset = autoIssueMessageService.selectPlanAssetByBorrowNidAndInstCode(borrow.getBorrowNid(), borrowInfo.getInstCode());
		if (asset != null) {
			if (StringUtils.isNotBlank(asset.getPlanNid()) || asset.getLabelId() == null
					|| asset.getLabelId().intValue() == 0) {
				logger.warn(asset.getBorrowNid() + " 该标的对应资产已经绑定计划或无标签绑定 " + borrow.getLabelId());
				return;
			}
			if (asset.getStatus().intValue() != 7) {
				logger.warn(asset.getBorrowNid() + " 该标的对应资产不是投资中状态 " + borrow.getLabelId());
				return;
			}
		}
		// 如果散标过来的标的，没有标签先打上
		if (asset == null && borrow.getLabelId() != null && borrow.getLabelId().intValue() == 0) {
			// 获取标签id
			HjhLabel label = autoIssueRecoverService.getLabelId(borrow, asset, borrowInfo);
			if (label == null || label.getId() == null) {
				logger.warn(borrowNid + " 该散标没有匹配到标签 ");
				return;
			}
			// 临时存着
			borrow.setLabelId(label.getId());
		}
		// 分配计划引擎
		String planNid = autoIssueMessageService.getPlanNid(borrow.getLabelId());
		if (planNid == null || borrow.getLabelId() == null || borrow.getLabelId().intValue() == 0) {
			logger.warn(borrowNid + " 该标的标签无计划关联 " + borrow.getLabelId());
			return;
		}
		// 关联计划
		boolean flag = autoIssueMessageService.updateIssueBorrow(borrowInfo, borrow, planNid, asset);
		if (!flag) {
			logger.error("关联计划失败！" + "[标的编号：" + borrowNid + "]");
		}

	}

	private void doJoinPlanOfCredit(String creditNid) {
		logger.info(creditNid + " 债转开始关联计划 ");

		// redis 防重复检查
		String redisKey = RedisConstants.BORROW_ISSUE + creditNid;
		boolean result = RedisUtils.tranactionSet(redisKey, 300);
		if (!result) {
			logger.warn(creditNid + " 正在关联计划(redis) ");
			return;
		}


		HjhDebtCredit credit = autoIssueMessageService.serchCreditByNid(creditNid);
		if (credit == null) {
			logger.warn(creditNid + " 该债转在表里不存在");
			return;
		}
		if (credit.getCreditStatus() != null && credit.getCreditStatus().intValue() != 0) {
			logger.warn(creditNid + " 该债转状态不为0 初始状态");
			return;
		}
		// 业务校验
		if (StringUtils.isNotBlank(credit.getPlanNidNew())) {
			logger.warn(creditNid + " 该债转已经绑定计划或无标签绑定 " + credit.getLabelId());
			return;
		}
		// 获取标签id
		HjhLabel label = autoIssueMessageService.getLabelId(credit);
		if (label == null || label.getId() == null) {
			logger.warn(creditNid + " 该债转没有匹配标签 ");
			// 汇计划三期邮件预警
			this.sendWarnMail(creditNid);
			return;
		}

		credit.setLabelId(label.getId());
		credit.setLabelName(label.getLabelName());
		// 分配计划引擎
		String planNid = autoIssueMessageService.getPlanNid(credit.getLabelId());
		if (planNid == null) {
			logger.warn(creditNid + " 该债转标签无计划关联 " + credit.getLabelId());
			return;
		}
		// 关联计划
		boolean flag = autoIssueMessageService.updateIssueCredit(credit, planNid);
		if (!flag) {
			logger.error("关联计划失败！" + "[债转标的编号：" + creditNid + "]");
		}
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====AutoAssociatePlanMessageConsumer start=====");
	}

	/**
	 * 汇计划三期邮件预警
	 * 
	 * @param creditNid
	 */
	private void sendWarnMail(String creditNid) {
		// 如果redis不存在这个KEY(一天有效期)，那么可以发邮件
		if (!RedisUtils.exists(RedisConstants.LABEL_MAIL_KEY + creditNid)) {
			StringBuffer emailmsg = new StringBuffer();
			emailmsg.append("债转编号：").append(creditNid).append("<br/>");
			emailmsg.append("当前时间：").append(GetDate.formatTime()).append("<br/>");
			emailmsg.append("错误信息：").append("该债转没有匹配标签！").append("<br/>");
			// 邮箱集合
			String emailList = autoIssueMessageService.getSystemEmailList();
			if (StringUtils.isBlank(emailList)) {
				logger.warn("系统邮件未配置...");
				return;
			}

			String[] toMail = emailList.split(",");
			MailMessage mailMessage = new MailMessage(null, null, "债转编号为：" + creditNid, emailmsg.toString(), null,
					toMail, null, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
			try {
				commonProducer.messageSend(
						new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), mailMessage));
				RedisUtils.set(RedisConstants.LABEL_MAIL_KEY + creditNid, creditNid, 24 * 60 * 60);
			} catch (MQException e2) {
				logger.error("发送邮件失败..", e2);
			}

		} else {
			logger.info("此邮件key值还未过期(一天)");
		}
	}
}
