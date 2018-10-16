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
import com.hyjf.cs.message.mq.base.Consumer;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author xiasq
 * @version AccountWebListConsumer, v0.1 2018/6/19 16:46
 */
@Component
public class AccountWebListConsumer extends Consumer {
	private static final Logger logger = LoggerFactory.getLogger(AccountWebListConsumer.class);

	@Autowired
	private AccountWebListDao accountWebListDao;
	@Autowired
	private AmUserClient amUserClient;
	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.ACCOUNT_WEB_LIST_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.ACCOUNT_WEB_LIST_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new AccountWebListConsumer.MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====AccountWebListConsumer consumer=====");
	}

	public class MessageListener implements MessageListenerConcurrently {
		@Override
		public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
			logger.info("AccountWebListConsumer 收到消息，开始处理....msgs is :{}", msgs);
			for (MessageExt msg : msgs) {
				AccountWebListVO accountWebListVO = JSONObject.parseObject(msg.getBody(), AccountWebListVO.class);
				AccountWebList accountWebList = null;
				if (accountWebListVO != null) {
					accountWebList = new AccountWebList();
					BeanUtils.copyProperties(accountWebListVO, accountWebList);
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
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		}
	}
}