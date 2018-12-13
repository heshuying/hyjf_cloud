package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.bean.ic.AccountWebList;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.ic.AccountWebListDao;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.apache.rocketmq.spring.core.RocketMQPushConsumerLifecycleListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiasq
 * @version AccountWebListConsumer, v0.1 2018/6/19 16:46
 */
@Service
@RocketMQMessageListener(topic = MQConstant.ACCOUNT_WEB_LIST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.ACCOUNT_WEB_LIST_GROUP)
public class AccountWebListConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
	private static final Logger logger = LoggerFactory.getLogger(AccountWebListConsumer.class);

	@Autowired
	private AccountWebListDao accountWebListDao;
	@Autowired
	private AmUserClient amUserClient;

	@Override
	public void onMessage(MessageExt  message) {
			logger.info("AccountWebListConsumer 收到消息，开始处理....msgId is :{}", message.getMsgId());
			AccountWebListVO accountWebListVO = JSONObject.parseObject(message.getBody(), AccountWebListVO.class);
			AccountWebList accountWebList = new AccountWebList();
			if (accountWebListVO != null) {
				BeanUtils.copyProperties(accountWebListVO, accountWebList);
			}else {
				logger.info("AccountWebListConsumer 收到消息，为空 accountWebListVO：{}", accountWebListVO);
				return ;
			}
			UserInfoVO usersInfo = amUserClient.findUsersInfoById(accountWebListVO.getUserId());
			// 承接人
			UserInfoCustomizeVO userInfoCustomize = amUserClient.queryUserInfoCustomizeByUserId(accountWebListVO.getUserId());
			if(null==accountWebList.getUsername()){
				UserVO user = amUserClient.findUserById(accountWebListVO.getUserId());
				accountWebList.setUsername(user.getUsername());
			}
			if (usersInfo != null) {
				Integer attribute = usersInfo.getAttribute();
				if (attribute != null) {
					if (userInfoCustomize != null ) {
						// 查找用户信息
						accountWebList.setRegionName(userInfoCustomize.getRegionName());
						accountWebList.setBranchName(userInfoCustomize.getBranchName());
						accountWebList.setDepartmentName(userInfoCustomize.getDepartmentName());
					}
				}
				accountWebList.setTruename(usersInfo.getTruename());
			}
			accountWebListDao.save(accountWebList);

	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		logger.info("====AccountWebListConsumer consumer=====");
	}



}