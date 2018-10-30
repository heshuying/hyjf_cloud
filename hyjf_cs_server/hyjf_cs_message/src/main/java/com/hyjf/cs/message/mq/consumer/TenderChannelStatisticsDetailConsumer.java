package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.user.UtmRegVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.message.bean.ic.AppChannelStatisticsDetail;
import com.hyjf.cs.message.client.AmUserClient;
import com.hyjf.cs.message.mongo.ic.AppChannelStatisticsDetailDao;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

/**
 * @Description 投资更新首投信息等
 * @Author sunss
 * @Date 2018/9/5 9:45
 */
@Component
public class TenderChannelStatisticsDetailConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(TenderChannelStatisticsDetailConsumer.class);

    @Autowired
    private AppChannelStatisticsDetailDao dao;
    @Autowired
    private AmUserClient amUserClient;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.TENDER_CHANNEL_STATISTICS_DETAIL_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.TENDER_CHANNEL_STATISTICS_DETAIL_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====TenderChannelStatisticsDetailConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("TenderChannelStatisticsDetailConsumer 收到消息，开始处理....msgs is :{}", msgs);
            for (MessageExt msg : msgs) {
                JSONObject entity = JSONObject.parseObject(msg.getBody(),
                        JSONObject.class);
                logger.info("entity：{}",JSONObject.toJSONString(entity));
                //查询mongo里面是否已经有数据了
                Integer userId = entity.getInteger("userId");
                AppChannelStatisticsDetail appChannelStatisticsDetails = dao.findByUserId(userId);
                logger.info("AppChannelStatisticsDetail:{}",JSONObject.toJSONString(appChannelStatisticsDetails));
                if (appChannelStatisticsDetails != null) {
                    if (Validator.isNotNull(entity)) {
                        Integer investFlag = amUserClient.countNewUserTotal(userId);
                        if (investFlag >1) {
                            // 累计投资
                            Query query = new Query();
                            Criteria criteria = Criteria.where("userId").is(userId);
                            query.addCriteria(criteria);
                            Update update = new Update();
                            BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal");
                            update.inc("cumulativeInvest", accountDecimal);
                            dao.update(query, update);
                        } else if (investFlag == 1) {
                            logger.info("开始更新首次投资信息....userId:"+userId);
                            // 首次投资
                            try{
                                Query query = new Query();
                                Criteria criteria = Criteria.where("userId").is(userId);
                                query.addCriteria(criteria);

                                Update update = new Update();

                                BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal");
                                String projectType = entity.getString("projectType");
                                Integer investTime = entity.getInteger("investTime");
                                String investProjectPeriod = entity.getString("investProjectPeriod");

                                update.inc("cumulativeInvest", accountDecimal).set("investAmount", accountDecimal)
                                        .set("investProjectType", projectType).set("firstInvestTime", investTime)
                                        .set("investProjectPeriod", investProjectPeriod);

                                dao.update(query, update);
                            }catch (Exception e){
                                logger.error("报错了。。。",e);
                            }
                        }
                    }
                } else {
                    // 更新huiyingdai_utm_reg的首投信息
                    UtmRegVO utmReg = amUserClient.findUtmRegByUserId(userId);
                    if (utmReg != null) {
                        boolean isTender = checkIsNewUserCanInvest(userId);
                        // 更新渠道统计用户累计投资
                        if (isTender) {
                            // 更新huiyingdai_utm_reg的首投信息
                            try {
                                HashMap<String, Object> params = JSONObject.parseObject(msg.getBody(), HashMap.class);
                                params.put("investFlag", isTender);
                                amUserClient.updateFirstUtmReg(params);
                            } catch (Exception e) {
                                e.printStackTrace();
                                logger.error("更新huiyingdai_utm_reg的首投信息失败");
                            }
                        }
                    }
                }

            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    /**
     * 检查用户是否是新手 true 是  false 不是
     *
     * @param userId
     * @return
     */
    private boolean checkIsNewUserCanInvest(Integer userId) {
        // 新的判断是否为新用户方法
        int total = amUserClient.countNewUserTotal(userId);
        if (total == 0) {
            return true;
        } else {
            return false;
        }
    }
}
