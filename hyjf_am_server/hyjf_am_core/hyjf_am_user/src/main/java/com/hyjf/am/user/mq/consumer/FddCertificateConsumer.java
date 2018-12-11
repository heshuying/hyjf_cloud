package com.hyjf.am.user.mq.consumer;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

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

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.mq.base.CommonProducer;
import com.hyjf.am.user.mq.base.MessageContent;
import com.hyjf.am.user.service.front.ca.FddCertificateService;
import com.hyjf.am.vo.user.FddCertificateAuthorityVO;
import com.hyjf.common.constants.MQConstant;

/**
 * @author zhangqingqing
 * @version FddCertificateConsumer, v0.1 2018/6/19 16:46
 */
@Service
@RocketMQMessageListener(topic = MQConstant.FDD_CERTIFICATE_AUTHORITY_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.FDD_CERTIFICATE_AUTHORITY_GROUP)
public class FddCertificateConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(FddCertificateConsumer.class);

    @Autowired
    FddCertificateService fddCertificateService;

    @Autowired
    CommonProducer commonProducer;
    
    
	@Override
	public void onMessage(MessageExt  message) {
        logger.info("FddCertificateConsumer 收到消息，开始处理....msgs is :{}", new String(message.getBody()));
        MessageExt msg = message;

        // --> 消息转换
        String msgBody = new String(msg.getBody());
        logger.info("法大大CA认证接收消息：[" + msgBody + "].");
        FddCertificateAuthorityVO fddCertificateAuthorityVO = new FddCertificateAuthorityVO();
        try {
            fddCertificateAuthorityVO = JSONObject.parseObject(msgBody, FddCertificateAuthorityVO.class);
            if (fddCertificateAuthorityVO == null || fddCertificateAuthorityVO.getUserId() == null || fddCertificateAuthorityVO.getUserId() == 0) {
               logger.info("解析为空：" + msgBody);
                return ;
            }
        } catch (Exception e) {
            return ;
        }

        logger.info("法大大CA认证逻辑处理开始,用户ID:[" + fddCertificateAuthorityVO.getUserId() + "].");

        try {
            Integer userId = fddCertificateAuthorityVO.getUserId();
            // 根据用户ID获取用户信息
            User user = fddCertificateService.findUserByUserId(userId);
            if (user == null) {
                logger.info("根据用户ID获取用户信息失败,用户ID:[" + userId + "].");
                return ;
            }
            UserInfo userInfo = fddCertificateService.findUsersInfo(userId);
            if (userInfo == null) {
                logger.info("根据用户ID获取用户详情信息失败,用户ID:[" + userId + "].");
                return ;
            }
            // 调用法大大CA认证接口
           fddCertificateService.updateUserCAInfo(userId,user,userInfo);
            Map<String, String> map = Maps.newHashMap();
            map.put("userId", String.valueOf(userId).trim());
            if(!"mobileModify".equals(fddCertificateAuthorityVO.getCertFrom())){
                //crm投资推送
            	commonProducer.messageSend(new MessageContent(MQConstant.CRM_ROUTINGKEY_BANCKOPEN_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(map)));
                logger.info("开户发送MQ时间【{}】",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            }

        } catch (Exception e) {
            logger.error("消费出错", e);
            return;
        }
    
    
	}

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====FddCertificateConsumer consumer=====");
    }
}