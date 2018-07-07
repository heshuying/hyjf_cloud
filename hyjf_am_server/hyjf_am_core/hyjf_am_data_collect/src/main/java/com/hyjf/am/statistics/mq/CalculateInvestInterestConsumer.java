/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.statistics.mq;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.statistics.bean.TotalInvestAndInterestEntity;
import com.hyjf.am.statistics.mongo.CalculateInvestInterestDao;
import com.hyjf.am.statistics.mongo.TotalInvestAndInterestMongoDao;
import com.hyjf.common.constants.MQConstant;
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
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 网站累计投资追加    修改mongodb运营数据
 * @Author sunss
 * @Date 2018/7/7 15:16
 */
@Component
public class CalculateInvestInterestConsumer extends Consumer {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private CalculateInvestInterestDao calculateInvestInterestDao;
    @Autowired
    private TotalInvestAndInterestMongoDao totalInvestAndInterestMongoDao;


    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CalculateInvestInterestConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====AppChannelStatisticsConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("CalculateInvestInterestConsumer 收到消息，开始处理....msgs is :{}", msgs);

            for (MessageExt msg : msgs) {
                JSONObject data = JSONObject.parseObject(msg.getBody(), JSONObject.class);
                BigDecimal tenderSum = (BigDecimal) data.get("tenderSum");
                Integer nowTime = (Integer) data.get("nowTime");
                Query query = new Query();
                Update update = new Update();
                update.inc("tenderSum", tenderSum).set("updateTime", nowTime);
                calculateInvestInterestDao.update(query, update);

                if(data.containsKey("money")){
                    BigDecimal money = (BigDecimal) data.get("money");
                    // 已收利息
                    BigDecimal recoverInterestAmount = (BigDecimal) data.get("recoverInterestAmount");
                    Integer type = (Integer) data.get("type");
                    if ("1".equals(type)) { // 投资增加交易总额
                        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
                        // 第一次插入
                        if (entity == null) {
                            entity = new TotalInvestAndInterestEntity();
                        }
                        entity.setTotalInvestAmount(entity.getTotalInvestAmount().add(money == null?BigDecimal.ZERO:money));
                        entity.setTotalInvestNum(entity.getTotalInvestNum() + 1);
                        logger.info("运营数据type=1, entity is :{}", entity);
                        // save没有插入，有则更新
                        totalInvestAndInterestMongoDao.save(entity);
                    } else if ("2".equals(type)) {  // 还款添加收益
                        // 累计收益(实时)
                        //BigDecimal totalInterestAmount = operationDataService.countTotalInterestAmount();
                        BigDecimal totalInterestAmount = recoverInterestAmount == null ? BigDecimal.ZERO : recoverInterestAmount;
                        logger.info("已收收益： {}", totalInterestAmount.toString());

                        // TODO: 2018/7/7 这里需要查询   没用到  先放
                        List<Map<String, Object>> list =null;//operationDataService.searchPlanStatisticData();
                        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
                        // 第一次插入
                        if (entity == null) {
                            entity = new TotalInvestAndInterestEntity();
                        }
                        entity.setTotalInterestAmount(entity.getTotalInterestAmount().add(totalInterestAmount));
                        if (!CollectionUtils.isEmpty(list)) {
                            Map<String, Object> map = list.get(0);
                            BigDecimal interestTotal = (BigDecimal) map.get("interest_total");
                            entity.setHjhTotalInterestAmount(interestTotal);
                        }
                        logger.info("运营数据type=2, entity is :{}", entity);
                        // save没有插入，有则更新
                        totalInvestAndInterestMongoDao.save(entity);
                    } else if ("3".equals(type)) {  // 计划
                        // 查询计划数据
                        TotalInvestAndInterestEntity entity = totalInvestAndInterestMongoDao.findOne(new Query());
                        if (entity == null) {
                            entity = new TotalInvestAndInterestEntity();
                        }
                        entity.setTotalInvestAmount(entity.getTotalInvestAmount().add(money == null ? BigDecimal.ZERO : money));
                        entity.setTotalInvestNum(entity.getTotalInvestNum() + 1);
                        entity.setHjhTotalInvestAmount(entity.getHjhTotalInvestAmount().add(money == null ? BigDecimal.ZERO : money.divide(new BigDecimal(10000))));
                        entity.setHjhTotalInvestNum(entity.getHjhTotalInvestNum() + 1);
                        logger.info("运营数据type=3, entity is :{}", entity);
                        // save没有插入，有则更新
                        totalInvestAndInterestMongoDao.save(entity);
                    }
                }
            }

            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
