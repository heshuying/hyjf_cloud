/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.mq.consumer;

/**
 * @author cui
 * @version SyncRegisterConsumer, v0.1 2019/4/17 15:20
 */

import java.text.SimpleDateFormat;
import java.util.UUID;

import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.service.AccountService;
import com.hyjf.wbs.user.dao.model.auto.BankCard;
import com.hyjf.wbs.user.dao.model.auto.User;
import com.hyjf.wbs.user.service.UserService;
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
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.wbs.WbsRegisterMqVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.client.AmTradeClient;
import com.hyjf.wbs.client.AmUserClient;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.user.service.SyncCustomerService;

@Service
@RocketMQMessageListener(topic = MQConstant.WBS_REGISTER_TOPIC, selectorExpression = MQConstant.WBS_REGISTER_TAG, consumerGroup = MQConstant.WBS_REGISTER_GROUP)
public class SyncRegisterConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SyncCustomerService syncCustomerService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private UserService userService;

	private final int ACCOUNT_OPENED = 1;

	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onMessage(MessageExt messageExt) {

		String uuid=UUID.randomUUID().toString();
		logger.info("【{}】WBS客户同步消息:开始处理....",uuid);

		String msgBody = new String(messageExt.getBody());
		WbsRegisterMqVO wbsRegisterMqVO = JSONObject.parseObject(msgBody, WbsRegisterMqVO.class);

		logger.info("【{}】消息内容【{}】",uuid,wbsRegisterMqVO);

		Preconditions.checkNotNull(wbsRegisterMqVO, "消息内容为空！");
		String userId = wbsRegisterMqVO.getAssetCustomerId();
		String thirdpartyId = wbsRegisterMqVO.getCustomerId();
		String utmId = wbsRegisterMqVO.getUtmId();

		CustomerSyncQO customerSyncQO = new CustomerSyncQO();
		customerSyncQO.setAssetCustomerId(userId);
		customerSyncQO.setEntId(Integer.parseInt(utmId));
		customerSyncQO.setCustomerId(thirdpartyId);

		if (Strings.isNullOrEmpty(userId)) {
			logger.info("【{}】WBS客户回调MQ收到消息格式不正确【{}】",uuid, msgBody);
		} else {
			Integer userIdd = Integer.parseInt(userId);
			User userVO = userService.findUserById(userIdd);
			if (userVO != null) {
				if (userVO.getOpenAccount() != null && userVO.getOpenAccount().intValue() == ACCOUNT_OPENED) {
					// 余额信息
					Account accountVO = accountService.getAccount(userIdd);
					// 开户行信息
					BankCard bankCardVO = userService.selectBankCardByUserId(userIdd);

					buildData(userVO, accountVO, bankCardVO, customerSyncQO);

				} else {

					buildData(userVO, customerSyncQO);
				}
			}

			logger.info("【{}】请求参数内容【{}】",uuid,customerSyncQO);
			syncCustomerService.sync(customerSyncQO);

		}
		logger.info("【{}】WBS客户同步消息:处理结束....",uuid);
	}

	private void buildData(User userVO, CustomerSyncQO customerSyncQO) {

		customerSyncQO.setUserName(userVO.getUsername());

		customerSyncQO.setPlatformRegistrationTime(sdf.format(userVO.getRegTime()));

	}
	private void buildData(User userVO, Account accountVO, BankCard bankCardVO, CustomerSyncQO customerSyncQO) {

		buildData(userVO, customerSyncQO);

		// 开户行信息
		customerSyncQO.setBankOfDeposit(bankCardVO.getBank());

		customerSyncQO.setPlatformAccountOpeningTime(sdf.format(bankCardVO.getCreateTime()));

		customerSyncQO.setBankReservedPhoneNumber(bankCardVO.getMobile());

		customerSyncQO.setBankCardNumber(bankCardVO.getCardNo());

		// 余额待收信息
		customerSyncQO
				.setPrecipitatedCapital(accountVO.getBalance() == null ? 0 : accountVO.getBalance().doubleValue());

		customerSyncQO
				.setFundsToBeCollected(accountVO.getBankAwait() == null ? 0 : accountVO.getBankAwait().doubleValue());

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
