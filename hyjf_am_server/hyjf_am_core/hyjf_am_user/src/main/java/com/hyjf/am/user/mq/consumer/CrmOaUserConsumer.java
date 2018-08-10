/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.ROaUsers;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.front.crm.CrmUserService;
import com.hyjf.am.vo.user.CrmUsersVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CommonUtils;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Description crm  OA User表同步
 * @Author sunss
 * @Date 2018/7/26 10:25
 */
public class CrmOaUserConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(CrmOaUserConsumer.class);
    private static final String OPERATION_ADD = "add";
    private static final String OPERATION_UPDATE = "update";
    private static final String OPERATION_DELETE = "delete";

    @Autowired
    CrmUserService crmUserService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {

        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.USER_CRM_OA_USER_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.CRM_OA_USER_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CrmOaUserConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====CrmOaUserConsumer consumer=====");
    }


    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【操作资金crm oa user对象】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【操作资金crm oa user对象】接收到的消息：" + msgBody);

            CrmUsersVO oaUsersVO = null;
            try {
                oaUsersVO = JSONObject.parseObject(msgBody, CrmUsersVO.class);
                if(oaUsersVO == null){
                    logger.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            try {
                // 操作数据库
                doOperation(oaUsersVO);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e){
                e.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }
    }

    /**
     * 操作数据库
     * @param oaUsersVO
     */
    private void doOperation(CrmUsersVO oaUsersVO) throws Exception {
        String operation = oaUsersVO.getOperation();
        if(StringUtils.isEmpty(operation)){
            throw new Exception("传入参数错误，operation为空");
        }
        ROaUsers user = CommonUtils.convertBean(oaUsersVO,ROaUsers.class);
        if(OPERATION_UPDATE.equals(operation)){
            crmUserService.update(user);
        } else if(OPERATION_ADD.equals(operation)){
            crmUserService.insert(user);
        }else if(OPERATION_DELETE.equals(operation)){
            crmUserService.delete(user);
        }else{
            throw new Exception("传入参数错误，operation错误");
        }

    }
}
