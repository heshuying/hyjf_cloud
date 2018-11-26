/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.hjh.HjhAccedeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.mq.base.Consumer;
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

import java.util.HashMap;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhQuitConsumer, v0.1 2018/6/27 18:26
 */
@Component
public class HjhQuitConsumer extends Consumer  {

    private static final Logger logger = LoggerFactory.getLogger(HjhQuitConsumer.class);

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {

        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.HJH_QUIT_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.HJH_QUIT_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new HjhQuitConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====计划锁定/退出 消费端开始运行=====");
    }


    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【汇计划计划进入锁定期/退出计划】接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【汇计划计划进入锁定期/退出计划】接收到的消息：" + msgBody);

            HjhAccedeVO mqHjhAccede;
            try {
                mqHjhAccede = JSONObject.parseObject(msgBody, HjhAccedeVO.class);
                if(mqHjhAccede == null || mqHjhAccede.getAccedeOrderId() == null || mqHjhAccede.getOrderStatus() == null ||mqHjhAccede.getCreditCompleteFlag() == null){
                    logger.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            } catch (Exception e1) {
                logger.error("计划锁定/退出参数解释失败：" + msgBody, e1);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            try {
                String accedeOrderId = mqHjhAccede.getAccedeOrderId();
                Integer orderStatus = mqHjhAccede.getOrderStatus();
                Integer creditCompleteFlag = mqHjhAccede.getCreditCompleteFlag();

                // 生成任务key 校验并发请求
                String redisKey = RedisConstants.PLAN_REPAY_TASK + accedeOrderId;
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if (!result) {
                    RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT, accedeOrderId);
                    logger.error("计划订单号：" + accedeOrderId + " 锁定/退出中....");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                //根据状态判断是计划锁定还是退出
                if (orderStatus == 2) {
                    //锁定计划
                    logger.debug("--------------计划订单号：" + accedeOrderId + "，开始进入锁定期！------");
                    updateForLock(accedeOrderId);
                } else if (orderStatus == 5 && creditCompleteFlag == 1) {
                    //退出计划 计划退出中并且清算标示完成
                    logger.debug("--------------计划订单号：" + accedeOrderId + "，开始退出计划！------");
                    amTradeClient.updateForQuit(accedeOrderId);
                }

                RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT, accedeOrderId);
                RedisUtils.del(redisKey);
                logger.debug("----------------------------计划退出结束--------------------------------");

                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } catch (Exception e){
                logger.error("计划锁定/退出失败：" + mqHjhAccede.getAccedeOrderId(), e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
        }

        private void updateForLock(String accedeOrderId) {
            List<HjhAccedeVO> accedeVOS = amTradeClient.selectHjhAccedeListByOrderId(accedeOrderId);
            if(accedeVOS != null){
                HjhAccedeVO hjhAccedeVO = accedeVOS.get(0);
                UserInfoVO inverestUserInfo = amUserClient.selectUserInfoByUserId(hjhAccedeVO.getInviteUserId());

                HashMap map = new HashMap();
                map.put("projectType",2);
                map.put("userType","线上员工");
                Integer pushMoneyOnline = amTradeClient.getCommisionConfig(map);
//                map.put("userType","51老用户");
//                Integer pushMoney51 = amTradeClient.getCommisionConfig(map);
                Integer commissionUserID = getCommissionUser(hjhAccedeVO, pushMoneyOnline, null, inverestUserInfo);
                UserInfoVO commissioUserInfoVO = null;
                if(commissionUserID != null && commissionUserID > 0 ){
                    commissioUserInfoVO = amUserClient.selectUserInfoByUserId(commissionUserID);
                }
                BankOpenAccountVO bankOpenAccountVO = amUserClient.selectBankAccountById(commissionUserID);
                List<UserInfoCustomizeVO> userInfoCustomizeVOS = amUserClient.queryDepartmentInfoByUserId(commissionUserID);
                amTradeClient.updateForLock(accedeOrderId,inverestUserInfo,commissioUserInfoVO,bankOpenAccountVO,userInfoCustomizeVOS);
            }
        }

        /**
         *
         * 计算提成用户id
         * @author hsy
         * @return
         */
        private Integer getCommissionUser(HjhAccedeVO record, Integer pushMoneyOnline,
                                          Integer pushMoney51, UserInfoVO userInfoInvite) {
            if(pushMoneyOnline == 1 && record.getUserAttribute() !=null && record.getUserAttribute() == 3){
                return record.getUserId();
            }else if(pushMoneyOnline == 1 && record.getInviteUserAttribute() != null && record.getInviteUserAttribute() == 3){
                return record.getInviteUserId();
            }/*else if(pushMoney51 == 1 && userInfoInvite != null && userInfoInvite.getIs51() != null && userInfoInvite.getIs51() == 1 && userInfoInvite.getAttribute() <2){
                return record.getInviteUserId();
            }*/
            return null;
        }
    }
}
