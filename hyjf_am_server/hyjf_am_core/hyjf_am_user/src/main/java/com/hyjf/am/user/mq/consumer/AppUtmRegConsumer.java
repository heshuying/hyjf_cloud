package com.hyjf.am.user.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.dao.model.customize.AppUtmRegCustomize;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CommonUtils;
import com.hyjf.common.validator.Validator;
import org.apache.commons.lang.StringUtils;
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

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;

/**
 * @author xiasq
 * @version AppUtmRegConsumer, v0.1 2018/4/12 14:58
 */

@Service
@RocketMQMessageListener(topic = MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.APP_CHANNEL_STATISTICS_DETAIL_GROUP)
public class AppUtmRegConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AppUtmRegConsumer.class);
	@Autowired
	private AppUtmRegService appUtmRegService;

	@Autowired
	private UserService userService;

	@Override
	public void onMessage(MessageExt  message) {
		logger.info("AppUtmRegConsumer 收到消息，开始处理....msgs is :{}", new String(message.getBody()));

		MessageExt msg = message;

		try {

			// 开户更新
			if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG.equals(msg.getTags())) {
				Integer userId = JSONObject.parseObject(msg.getBody(), Integer.class);
				if (userId == null) {
					logger.error("参数错误，userId is null...");
					return ;
				}
				// 更新开户时间
				AppUtmReg entity = appUtmRegService.findByUserId(userId);
				if (entity != null) {
					entity.setOpenAccountTime(new Date());
					appUtmRegService.update(entity);
				}
			} else if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG.equals(msg.getTags())) {
				logger.info("app渠道统计保存消息....");
				AppUtmRegCustomize entity = JSONObject.parseObject(msg.getBody(),
						AppUtmRegCustomize.class);
				if (entity != null) {
	                logger.info("entity: {}", JSONObject.toJSONString(entity));
					this.insertRefferAppUtmReg(entity);
					// appUtmRegService.insert(entity);
				}
			} else if (MQConstant.APP_CHANNEL_STATISTICS_DETAIL_CREDIT_TAG.equals(msg.getTags())
					|| MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG.equals(msg.getTags())) {
				JSONObject entity = JSONObject.parseObject(msg.getBody(), JSONObject.class);
				logger.info("entity: {}", entity.toJSONString());
				if (Validator.isNotNull(entity)) {
					boolean investFlag = entity.getBooleanValue("investFlag");
					// 不是首投
					if (!investFlag) {
						Integer userId = entity.getInteger("userId");
						AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
						BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal")==null?BigDecimal.ZERO:entity.getBigDecimal("accountDecimal");
	                    BigDecimal invest = appUtmReg.getCumulativeInvest() == null? BigDecimal.ZERO: appUtmReg.getCumulativeInvest();
	                    appUtmReg.setCumulativeInvest(invest.add(accountDecimal));
	                    appUtmRegService.update(appUtmReg);
					} else {
					    // 首投
	                    Integer userId = entity.getInteger("userId");
	                    AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
						BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal")==null?BigDecimal.ZERO:entity.getBigDecimal("accountDecimal");
						String projectType = entity.getString("projectType");
						Integer investTime = entity.getInteger("investTime");
						String investProjectPeriod = entity.getString("investProjectPeriod");
	                    appUtmReg.setCumulativeInvest(accountDecimal);
	                    appUtmReg.setInvestAmount(accountDecimal);
	                    appUtmReg.setInvestProjectType(projectType);
	                    appUtmReg.setFirstInvestTime(investTime);
	                    appUtmReg.setInvestProjectPeriod(investProjectPeriod);
	                    appUtmRegService.update(appUtmReg);
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("消费处理异常",e);
		}
	
	
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.setMaxReconsumeTimes(3);
		logger.info("====AppUtmRegConsumer consumer=====");
	}

	/**
	 * 用户自主注册时，如果有推荐人，用户注册渠道=推荐人注册渠道
	 * 推荐人ID ： refferUserId
	 * 注册用户ID ： userId
	 * 前端回传固定渠道信息 ： utmId
	 * 前端回传客户端类型 ： platform
	 * */
	private void insertRefferAppUtmReg(AppUtmRegCustomize entity){
		Integer attribute = null;
		// 获取推荐人表
		User refferUser = userService.getRefferUsers(entity.getMobile(),entity.getReffer());
		if (refferUser != null) {
			UserInfo refferUserInfo = userService.findUsersInfo(refferUser.getUserId());
			if (refferUserInfo != null) {
				// 如果该用户的上级不为空
				if (Validator.isNotNull(refferUserInfo.getAttribute())) {
					if (Arrays.asList(2, 3).contains(refferUserInfo.getAttribute())) {
						// 有推荐人且推荐人为员工(Attribute=2或3)时才设置为有主单
						attribute = 1;
					}
				}
			}
		}
		Boolean refferUtmFlag = true;
		// 判断是否为线下线上员工（是员工走以下逻辑）
		if(attribute == 1) {
			// 推荐人不为空时
			if (refferUser != null) {
				String regSourctId;
				// 查询推荐人渠道（APP）
				AppUtmReg appUtmReg = appUtmRegService.findByUserId(refferUser.getUserId());
				// 查询推荐人渠道（PC）
				UtmReg reg = userService.findUtmRegByUserId(refferUser.getUserId());
				// 判断推荐人渠道是pc还是app
				if (appUtmReg != null) {
					// 类型转换
					regSourctId = String.valueOf(appUtmReg.getSourceId());
					// 是否为空
					if (StringUtils.isNotEmpty(regSourctId) && Validator.isNumber(regSourctId)) {
						// 插入推荐人渠道（覆盖MQ发送前拼装的推荐人数据）
						entity.setSourceId(appUtmReg.getSourceId());
						entity.setSourceName(appUtmReg.getSourceName());
						// 转换实体类
						AppUtmReg appUtmRegInsert = CommonUtils.convertBean(entity, AppUtmReg.class);
						appUtmRegService.insert(appUtmRegInsert);
						// 不再进行默认渠道插入
						refferUtmFlag = false;
						logger.error("用户自主注册时，如果有推荐人（APP），用户注册渠道=推荐人注册渠道，插入推荐人渠道（覆盖MQ发送前拼装的推荐人数据，推荐人推广） entity：" + appUtmRegInsert.toString());
					}
				} else if(reg != null){
					// 类型转换
					String regUtmid = String.valueOf(reg.getUtmId());
					// 是否为空
					if (StringUtils.isNotEmpty(regUtmid)) {
						// 插入推荐人渠道
						userService.insertUtmReg(entity.getUserId(), regUtmid);
						// 不再进行默认渠道插入
						refferUtmFlag = false;
						logger.info("插入推荐人渠道 如果有推荐人（PC），用户注册渠道=推荐人注册渠道 userId：" + entity.getUserId() +"  regUtmid： "+ regUtmid);
					}
				}else {
					logger.error("用户自主注册时，如果有推荐人，用户注册渠道=推荐人注册渠道，查询推荐人渠道appUtmReg 结果为null（走默认推广） refferUserId ：refferUser.getUserId()");
				}
			} else {
				logger.error("用户自主注册时，如果有推荐人，用户注册渠道=推荐人注册渠道，获取推荐人结果为null（走默认推广） Mobile ：" + entity.getMobile() + "Reffer" + entity.getReffer());
			}
		}else{
			logger.error("用户自主注册时，如果有推荐人，用户注册渠道=推荐人注册渠道  ： 推荐人非员工！ （走默认推广）");
		}
		if(refferUtmFlag){
			// 默认推广，插入
			AppUtmReg appUtmRegInsertDef = CommonUtils.convertBean(entity,AppUtmReg.class);
			appUtmRegService.insert(appUtmRegInsertDef);
			logger.error("用户自主注册时，如果有推荐人，用户注册渠道=推荐人注册渠道，插入推荐人渠道 app 默认渠道信息（默认推广）  entity：" + appUtmRegInsertDef.toString());
		}
	}
}
