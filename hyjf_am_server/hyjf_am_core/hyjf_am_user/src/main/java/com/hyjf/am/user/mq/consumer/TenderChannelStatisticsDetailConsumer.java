package com.hyjf.am.user.mq.consumer;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.user.dao.model.auto.AppUtmReg;
import com.hyjf.am.user.dao.model.auto.UtmReg;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.front.user.AppUtmRegService;
import com.hyjf.am.user.service.front.user.UserService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;

/**
 * @Description 出借更新首投信息等
 * @Author sunss
 * @Date 2018/9/5 9:45
 */
//@Component
public class TenderChannelStatisticsDetailConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(TenderChannelStatisticsDetailConsumer.class);

    @Autowired
    private AppUtmRegService appUtmRegService;
    @Autowired
    private UserService userService;

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
                //查询mysql里面是否已经有数据了
                Integer userId = entity.getInteger("userId");
                AppUtmReg appUtmReg = appUtmRegService.findByUserId(userId);
                logger.info("AppUtmReg:{}",JSONObject.toJSONString(appUtmReg));
                if (appUtmReg != null) {
                    if (Validator.isNotNull(entity)) {
                        Integer investFlag = userService.selectTenderCount(userId);
                        if (investFlag >1) {
                            // 累计出借
                            BigDecimal accountDecimal = entity.getBigDecimal("accountDecimal")==null?BigDecimal.ZERO:entity.getBigDecimal("accountDecimal");
                            BigDecimal cumulativeInvest = appUtmReg.getCumulativeInvest()==null?BigDecimal.ZERO:appUtmReg.getCumulativeInvest();
                            appUtmReg.setCumulativeInvest(cumulativeInvest.add(accountDecimal));
                            appUtmRegService.update(appUtmReg);
                        } else if (investFlag == 1) {
                            logger.info("开始更新首次出借信息....userId:"+userId);
                            // 首次出借
                            try{
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
                            }catch (Exception e){
                                logger.error("报错了。。。",e);
                            }
                        }
                    }
                } else {
                    // 更新huiyingdai_utm_reg的首投信息
                    UtmReg utmReg = userService.findUtmRegByUserId(userId);
                    if (utmReg != null) {
                        boolean isTender = checkIsNewUserCanInvest(userId);
                        // 更新渠道统计用户累计出借
                        if (isTender) {
                            // 更新huiyingdai_utm_reg的首投信息
                            try {
                                HashMap<String, Object> params = JSONObject.parseObject(msg.getBody(), HashMap.class);
                                params.put("investFlag", isTender);
                                userService.updateFirstUtmReg(params);
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
        int total = userService.selectTenderCount(userId);
        if (total == 0) {
            return true;
        } else {
            return false;
        }
    }
}
