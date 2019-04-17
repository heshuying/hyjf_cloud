/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.wbs.mq.consumer;

/**
 * @author cui
 * @version SyncRegisterConsumer, v0.1 2019/4/17 15:20
 */

import java.text.SimpleDateFormat;

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
@RocketMQMessageListener(topic = MQConstant.WBS_REGISTER_TOPIC, selectorExpression = MQConstant.WBS_BORROW_INFO_TAG, consumerGroup = MQConstant.WBS_REGISTER_GROUP)
public class SyncRegisterConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private SyncCustomerService syncCustomerService;

	@Autowired
	private AmUserClient amUserClient;

	@Autowired
	private AmTradeClient amTradeClient;

	private final int ACCOUNT_OPENED = 1;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public void onMessage(MessageExt messageExt) {

		logger.info("WBS消息:用户注册,收到消息，开始处理....");

		String msgBody = new String(messageExt.getBody());
		WbsRegisterMqVO wbsRegisterMqVO = JSONObject.parseObject(msgBody, WbsRegisterMqVO.class);

		Preconditions.checkNotNull(wbsRegisterMqVO, "消息内容为空！");
		String userId = wbsRegisterMqVO.getAssetCustomerId();
		String thirdpartyId = wbsRegisterMqVO.getThirdpartyId();
		String utmId = wbsRegisterMqVO.getUtmId();

        CustomerSyncQO customerSyncQO=new CustomerSyncQO();
        customerSyncQO.setAssetCustomerId(userId);
        customerSyncQO.setEntId(Integer.parseInt(utmId));
        customerSyncQO.setCustomerId(thirdpartyId);

		if (Strings.isNullOrEmpty(userId) || Strings.isNullOrEmpty(thirdpartyId) || Strings.isNullOrEmpty(utmId)) {
			logger.info("WBS用户注册MQ收到消息格式不正确【{}】", msgBody);
		} else {
			Integer userIdd = Integer.parseInt(userId);
			UserVO userVO = amUserClient.findUserById(userIdd);
			if (userVO != null) {
				if (userVO.getOpenAccount() != null && userVO.getOpenAccount().intValue() == ACCOUNT_OPENED) {
					AccountVO accountVO = amTradeClient.getAccount(userIdd);

					buildData(userVO,accountVO,customerSyncQO);

				}else{

				    buildData(userVO,customerSyncQO);
                }
			}

			syncCustomerService.sync(customerSyncQO);

		}
	}

    private void buildData(UserVO userVO, CustomerSyncQO customerSyncQO) {

	    customerSyncQO.setUserName(userVO.getUsername());

	    customerSyncQO.setPlatformRegistrationTime(sdf.format(userVO.getRegTime()));

    }

    private void buildData(UserVO userVO, AccountVO accountVO, CustomerSyncQO customerSyncQO) {

	    buildData(userVO,customerSyncQO);

	    customerSyncQO.setPlatformAccountOpeningTime(sdf.format(accountVO.getCreateTime()));

	    //TODO 银行信息
//	    customerSyncQO.setBankOfDeposit(accountVO.getBan);

    }

    @Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====SyncRegisterConsumer start=====");
	}
}
