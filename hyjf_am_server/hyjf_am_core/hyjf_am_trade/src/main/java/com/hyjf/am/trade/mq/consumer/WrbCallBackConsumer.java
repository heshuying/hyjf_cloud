package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.front.wrb.WrbCallBackService;
import com.hyjf.am.trade.utils.WrbCoopDESUtil;
import com.hyjf.am.trade.utils.WrbParseParamUtil;
import com.hyjf.am.vo.trade.wrb.WrbTenderNotifyCustomizeVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
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

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/20 14:00
 * @Description: WrbCallBackConsumer
 */
@Component
public class WrbCallBackConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(WrbCallBackConsumer.class);

    @Autowired
    private WrbCallBackService wrbCallBackService;
    @Autowired
    private SystemConfig systemConfig;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.WRB_QUEUE_CALLBACK_NOTIFY_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.WRB_QUEUE_CALLBACK_NOTIFY_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new WrbCallBackConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====WrbCallBackConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("风车理财回调通知接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【风车理财回调通知】接收到的消息：" + msgBody);

            JSONObject requestJson = JSONObject.parseObject(msgBody);
            // 2.验证请求参数
            Integer userId = requestJson.getInteger("userId");
            String nid = requestJson.getString("nid");
            // 回调类型， 1-投资回调 2-回款回调
            Integer returnType = requestJson.getInteger("returnType");
            // 回款时间
            String backTime = requestJson.getString("backTime");
            // 回款金额
            String backMoney = requestJson.getString("backMoney");
            if (Validator.isNull(userId) || Validator.isNull(nid) || Validator.isNull(returnType)) {
                logger.error("风车理财回调通知参数不全.....");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            if (returnType.intValue() == 2) {
                if (Validator.isNull(backTime) || Validator.isNull(backMoney)) {
                    logger.error("风车理财回款通知参数不全.....");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            }

            // 3.业务逻辑
            try {
                this.doHandle(userId, nid, returnType, backTime, backMoney);
            } catch (Exception e) {
                logger.error("风车理财回调失败...", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            logger.info("风车理财回调通知处理完成...");

            logger.info("********************风车理财回调通知结束*************************");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }


        private void doHandle(Integer userId, String nid, Integer returnType, String backTime, String backMoney){
            // 调用风车理财回调接口
            Map<String, String> allParams = new HashMap<>();
            try{
                //allParams.put("from", CustomConstants.WRB_CHANNEL_CODE);
                allParams.put("pf_user_id", WrbCoopDESUtil.desEncrypt(WrbCoopDESUtil.PF_KEY, String.valueOf(userId)));

                WrbTenderNotifyCustomizeVO customize = wrbCallBackService.searchBorrowTenderByNid(nid, userId);
                if (customize == null) {
                    logger.error("请求参数错误，找不到投资数据....");
                    throw new RuntimeException();
                }
                // returnType ：1-投资回调 ， 2-回款回调
                if (returnType.intValue() == 1) {
                    allParams.put("invest_time", customize.getAddtime());
                    allParams.put("invest_sno", nid);
                    allParams.put("invest_money", customize.getAccount().setScale(4, BigDecimal.ROUND_HALF_UP).toString());
                    allParams.put("invest_limit", customize.getInvestPeriod());
                    allParams.put("invest_rate", customize.getBorrowApr());
                    allParams.put("back_way", customize.getBorrowStyleName());
                    allParams.put("invest_title", customize.getBorrowName());
                } else if (returnType.intValue() == 2) {
                    allParams.put("back_time", backTime);
                    allParams.put("bid_id", customize.getBorrowNid());
                    allParams.put("invest_sno", nid);
                    allParams.put("back_money", backMoney);
                    allParams.put("invest_title", customize.getBorrowName());
                } else {
                    logger.error("错误的回调类型....");
                    throw new RuntimeException();
                }
                allParams.put("param","test");
            }catch (Exception e){
                logger.error("风车理财处理异常！",e);
            }

            logger.info("param is :{}", JSONObject.toJSONString(allParams));

            String result = WrbParseParamUtil.wrbCallback(systemConfig.getWRB_CALLBACK_NOTIFY_URL(), allParams);
            logger.info("风车理财回调结果：{}", result);
        }

    }
}
