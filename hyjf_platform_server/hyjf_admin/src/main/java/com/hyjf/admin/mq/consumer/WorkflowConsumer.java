/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.mq.consumer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.controller.websocket.WebSocketServer;
import com.hyjf.admin.service.workflow.WorkFlowConfigService;
import com.hyjf.common.constants.MQConstant;
import io.swagger.models.auth.In;
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

import java.io.IOException;

/**
 * 业务流程配置异常处理
 */
@Service
@RocketMQMessageListener(topic = MQConstant.WORKFLOW_MESSAGE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.WORKFLOW_GROUP)
public class WorkflowConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private Logger logger = LoggerFactory.getLogger(WorkflowConsumer.class);

    @Autowired
    private WorkFlowConfigService workFlowConfigService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
    }

    @Override
    public void onMessage(MessageExt message) {
        if(message == null || message.getBody() == null){
            logger.error("【业务流程配置异常处理】接收到的消息为null");
            return;
        }

        String msgBody = new String(message.getBody());
        logger.info("【业务流程配置异常处理】接收到的消息：" + msgBody);
        JSONObject requestJson;
        try {
            requestJson = JSONObject.parseObject(msgBody);
            JSONArray jsonArray = requestJson.getJSONArray("adminUserId");
            Integer[] adminUserId  = jsonArray.toArray(new Integer[0]);
            workFlowConfigService.disableAdminUser(adminUserId);
        }catch (Exception e){
            logger.error("【业务流程配置异常处理】出现异常"+e.getLocalizedMessage());
        }

    }
}
