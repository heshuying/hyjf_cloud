/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.ROaDepartment;
import com.hyjf.am.trade.dao.model.auto.ROaDepartmentExample;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.front.crm.CrmDepartmentService;
import com.hyjf.am.vo.user.CrmDepartmentVO;
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
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @Description crm ht_r_oa_department 表同步
 * @Author sunss
 * @Date 2018/7/26 10:25
 */
@Component
public class CrmOaDepartmentConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(CrmOaDepartmentConsumer.class);
    private static final String OPERATION_ADD = "TAG_OA_DEPARTMENT_OPERATION_ADD";
    private static final String OPERATION_UPDATE = "TAG_OA_DEPARTMENT_OPERATION_UPDATE";
    private static final String OPERATION_DELETE = "TAG_OA_DEPARTMENT_OPERATION_DELETE";

    @Autowired
    CrmDepartmentService crmDepartmentService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {

        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.TRADE_CRM_OA_DEPARTMENT_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.CRM_OA_DEPARTMENT_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CrmOaDepartmentConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====CrmOaDepartmentConsumer consumer=====");
    }


    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            MessageExt msg = list.get(0);
            if (msg.getTopic().equals(MQConstant.CRM_OA_DEPARTMENT_TOPIC)) {
                String msgBody = new String(msg.getBody());
                logger.info("【操作资金crm oa user对象】接收到的消息：" + msgBody);

                if (msg.getTags() != null && msg.getTags().equals(OPERATION_ADD)) {
                    ROaDepartment department = JSONObject.parseObject(msgBody, ROaDepartment.class);
                    crmDepartmentService.insert(department);
                    logger.info("----------------------添加部门消息处理结束------------------------");
                } else if (msg.getTags() != null && msg.getTags().equals(OPERATION_UPDATE)) {
                    JSONObject date = JSONObject.parseObject(msgBody);
                    if (date != null) {
                        if (date.containsKey("type") && date.get("type").equals("complex")) {
                            // 根据DepartmentExample 修改
                            JSONObject allParam = JSONObject.parseObject(date.getString("object"));
                            ROaDepartmentExample example = JSONObject.parseObject(allParam.getString("example"), ROaDepartmentExample.class);
                            ROaDepartment department = JSONObject.parseObject(allParam.getString("record"), ROaDepartment.class);
                            crmDepartmentService.updateByExample(department, example);
                        } else if (date.containsKey("type") && date.get("type").equals("simple")) {
                            // 根据ID修改
                            ROaDepartment department = JSONObject.parseObject(date.getString("object"), ROaDepartment.class);
                            crmDepartmentService.update(department);
                        }
                    }
                    logger.info("----------------------更新部门消息处理结束！------------------------");
                } else if (msg.getTags() != null && msg.getTags().equals(OPERATION_DELETE)) {
                    // 调岗
                    Integer id = JSONObject.parseObject(msgBody, Integer.class);
                    crmDepartmentService.delete(id);
                    logger.info("----------------------删除部门消息处理结束------------------------");

                }
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

}
