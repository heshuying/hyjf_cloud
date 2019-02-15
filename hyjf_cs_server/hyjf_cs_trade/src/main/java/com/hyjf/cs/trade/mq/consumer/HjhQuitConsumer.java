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
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version HjhQuitConsumer, v0.1 2018/6/27 18:26
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HJH_LOCK_QUIT_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.HJH_LOCK_QUIT_GROUP)
public class HjhQuitConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(HjhQuitConsumer.class);

    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    AmUserClient amUserClient;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====计划锁定/退出 消费端开始运行=====");
    }

    @Override
    public void onMessage(MessageExt msg) {
        HjhAccedeVO mqHjhAccede;
        //转换队列消息
        try {
            // --> 消息检查
            if(msg == null || msg.getBody() == null){
                logger.error("【汇计划计划进入锁定期/退出计划】接收到的消息为null");
                return;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【汇计划计划进入锁定期/退出计划】接收到的消息：" + msgBody);

            mqHjhAccede = JSONObject.parseObject(msgBody, HjhAccedeVO.class);
            if(mqHjhAccede == null || mqHjhAccede.getAccedeOrderId() == null || mqHjhAccede.getOrderStatus() == null ||mqHjhAccede.getCreditCompleteFlag() == null){
                logger.info("解析为空：" + msgBody);
                return;
            }
        } catch (Exception e1) {
            logger.error("计划锁定/退出参数解释失败：", e1);
            return;
        }
        //业务处理
        String accedeOrderId = mqHjhAccede.getAccedeOrderId();
        Integer orderStatus = mqHjhAccede.getOrderStatus();
        Integer creditCompleteFlag = mqHjhAccede.getCreditCompleteFlag();
        try {
            // 生成任务key 校验并发请求
            String redisKey = RedisConstants.PLAN_REPAY_TASK + accedeOrderId;
            boolean result = RedisUtils.tranactionSet(redisKey, 300);
            if (!result) {
                logger.error("计划订单号：" + accedeOrderId + " 锁定/退出中....");
                return;
            }

            //根据状态判断是计划锁定还是退出
            if (orderStatus == 2) {
                //锁定计划
                logger.info("--------------计划订单号：" + accedeOrderId + "，开始进入锁定期！------");
                updateForLock(accedeOrderId);
            } else if (orderStatus == 5 && creditCompleteFlag == 1) {
                //退出计划 计划退出中并且清算标示完成
                logger.info("--------------计划订单号：" + accedeOrderId + "，开始退出计划！------");
                amTradeClient.updateForQuit(accedeOrderId);
            }

            RedisUtils.del(redisKey);
            logger.info("--------------计划订单号：" + accedeOrderId + "，锁定/退出结束------");

            return;
        } catch (Exception e){
            logger.error("计划锁定/退出失败：" + mqHjhAccede.getAccedeOrderId(), e);
            return;
        } finally {
            RedisUtils.srem(RedisConstants.HJH_LOCK_REPEAT, accedeOrderId);
        }
    }

    private void updateForLock(String accedeOrderId) {
        List<HjhAccedeVO> accedeVOS = amTradeClient.selectHjhAccedeListByOrderId(accedeOrderId);
        if(!CollectionUtils.isEmpty(accedeVOS)){
            HjhAccedeVO hjhAccedeVO = accedeVOS.get(0);
            UserInfoVO inverestUserInfo = amUserClient.selectUserInfoByUserId(hjhAccedeVO.getInviteUserId());

            HashMap map = new HashMap();
            map.put("projectType",2);
            map.put("userType","线上员工");
            Integer pushMoneyOnline = amTradeClient.getCommisionConfig(map);
//                map.put("userType","51老用户");
//                Integer pushMoney51 = amTradeClient.getCommisionConfig(map);
            //获取提成用户ID
            Integer commissionUserID = getCommissionUser(hjhAccedeVO, pushMoneyOnline, null, inverestUserInfo);
            UserInfoVO commissioUserInfoVO = null;
            BankOpenAccountVO bankOpenAccountVO = null;
            List<UserInfoCustomizeVO> userInfoCustomizeVOS = null;
            //提成用户不为空时，获取提成用户相关信息用于更新提成信息
            if(commissionUserID != null && commissionUserID > 0 ){
                commissioUserInfoVO = amUserClient.selectUserInfoByUserId(commissionUserID);
                bankOpenAccountVO = amUserClient.selectBankAccountById(commissionUserID);
                userInfoCustomizeVOS = amUserClient.queryDepartmentInfoByUserId(commissionUserID);
            }
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
