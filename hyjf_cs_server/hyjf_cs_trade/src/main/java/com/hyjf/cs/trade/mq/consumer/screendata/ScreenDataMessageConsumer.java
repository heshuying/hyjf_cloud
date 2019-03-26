package com.hyjf.cs.trade.mq.consumer.screendata;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.response.trade.ScreenDataResponse;
import com.hyjf.am.resquest.trade.ScreenDataBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 大屏数据统计
 * @author lisheng
 * @version ScreenDataMessageConsumer, v0.1 2019/3/15 14:37
 */
@Service
@RocketMQMessageListener(topic = MQConstant.SCREEN_DATA_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SCREEN_DATA_GROUP)
public class ScreenDataMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(ScreenDataMessageConsumer.class);
    private static int MAX_RECONSUME_TIME = 3;
    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmTradeClient amTradeClient;
    @Autowired
    private CommonProducer commonProducer;
    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("ScreenDataMessageConsumer 收到消息，开始处理....msgId is :{} ,msgbody:{}", messageExt.getMsgId(),messageExt.getBody());
        ScreenDataBean data = JSONObject.parseObject(new String(messageExt.getBody()), ScreenDataBean.class);
        logger.info("ScreenDataMessageConsumer 收到参数:"+data.toString());
        Integer userId = data.getUserId();
        String orderId = data.getOrderId();
        BigDecimal investMoney = data.getMoney();
        Integer productType = data.getProductType();
        //查询用户是否归属运营部
        ScreenDataResponse userGroup = amUserClient.findUserGroup(data);
        if (userGroup != null) {
            Integer group = userGroup.getGroup();
            String currentOwner = userGroup.getCurrentOwner();
            data.setCurrentOwner(currentOwner);
            data.setCustomerGroup(group);
            if (group == -1) {
                logger.info("当前用户不属于运营部，userId：{}", userId);
            } else {
                BigDecimal userFreeMoney = amTradeClient.findUserFreeMoney(userId);
                if (userFreeMoney != null) {
                    data.setBalance(userFreeMoney);
                }
                // 投资需要查询年化金额
                if (data.getOperating() == 1) {
                    //消息推送开始
                    pushMessage(data, currentOwner);
                    //消息推送结束
                    BigDecimal yearMoney = amTradeClient.findYearMoney(userId, orderId, productType, investMoney);
                    data.setYearMoney(yearMoney);
                }
                //回款处理
                if (data.getOperating() == 3) {
                    amTradeClient.dealRepayMoney(data);
                }
                amTradeClient.insertScreenData(data);
            }
        }
    }

    /**
     * 推送接口
     * @param data
     * @param currentOwner
     */
    private void pushMessage(ScreenDataBean data, String currentOwner) {
        if(data.getMoney().compareTo(new BigDecimal(100))!=-1){
            JSONObject jsonObject = new JSONObject();
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            jsonObject.put("currentOwner",currentOwner);
            jsonObject.put("userName",data.getUserName());
            jsonObject.put("time",formatter.format(new Date()));
            jsonObject.put("money",data.getMoney());
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.USER_SCREEN_PICTURE3_TOPIC, UUID.randomUUID().toString(), jsonObject));
            } catch (MQException e) {
                logger.error("推送数据接口出现异常...", e);
            }
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
        logger.info("====大屏数据统计 消费端开始执行=====");
    }
}
