/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.transactionmq;

import java.io.File;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.am.user.dao.model.auto.BankOpenAccount;
import com.hyjf.am.user.dao.model.auto.BankOpenAccountExample;
import com.hyjf.am.user.dao.model.auto.SpreadsUser;
import com.hyjf.am.user.dao.model.auto.User;
import com.hyjf.am.user.dao.model.auto.UserInfo;
import com.hyjf.am.user.mq.base.Consumer;
import com.hyjf.am.user.service.batch.FddCertificateService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;

/**
 * @author zhangqingqing
 * @version CrmBankOpenMessageConsumer, v0.1 2018/6/28 14:27
 */
@Component
public class CrmBankOpenMessageConsumer extends Consumer{
    private static final Logger logger = LoggerFactory.getLogger(CrmBankOpenMessageConsumer.class);

    @Autowired
    private RestTemplate restTemplate;

    @Value("${crm.insertCustomerAction.url}")
    private String crmInsertUrl;

    @Value("${hyjf.req.pri.key}")
    private String reqPriKey;

    @Value("${hyjf.req.password}")
    private String reqPassword;

    @Autowired
    FddCertificateService fddCertificateService;
    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.CRM_ROUTINGKEY_BANCKOPEN_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.CRM_ROUTINGKEY_BANCKOPEN_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new CrmBankOpenMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====CrmBankOpenMessageConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("CrmBankOpenMessageConsumer 收到消息，开始处理....");
            for (MessageExt msg : msgs) {
                // --> 消息转换
                logger.info("【crm开户同步】接收到的消息：" + msg);
                String msgBody = new String(msg.getBody());
                Map<String,Integer> msgMap = JSONObject.parseObject(msgBody, Map.class);
                ResponseEntity<CloseableHttpResponse> result = null;
                try {
                   result = restTemplate.postForEntity(crmInsertUrl,buildData(msgMap.get("userId")),CloseableHttpResponse.class);
                } catch (Exception e) {
                    logger.info("【crm开户同步】异常，重新投递");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                if (!result.getStatusCode().equals(HttpStatus.SC_OK) ) {
                    logger.info("【crm开户同步】网络异常，重新投递");
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                } else {
                    logger.info("【crm开户同步】投递成功");
                }
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
    private Object buildData(Integer userId) {
        JSONObject ret = new JSONObject();
        Map<String, Object> map = Maps.newHashMap();

        UserInfo userInfo = fddCertificateService.findUsersInfo(userId);
        SpreadsUser spreadsUsers = fddCertificateService.selectSpreadsUsersByUserId(userId);
        User user = fddCertificateService.findUserByUserId(userId);
        BankOpenAccountExample accountExample = new BankOpenAccountExample();
        BankOpenAccountExample.Criteria crt = accountExample.createCriteria();
        crt.andUserIdEqualTo(userId);
        BankOpenAccount account = fddCertificateService.selectByExample(accountExample);
        if(spreadsUsers != null){
            UserInfo referrerInfo = fddCertificateService.findUsersInfo(spreadsUsers.getSpreadsUserId());
            User referrerUser = fddCertificateService.findUserByUserId(spreadsUsers.getSpreadsUserId());
            map.put("recommendCardId", referrerInfo.getIdcard());
            map.put("recommendName", referrerInfo.getTruename());
            map.put("recommondUsername", referrerUser.getUsername());
            map.put("recommendMobile", referrerUser.getMobile());
            map.put("recommondId", referrerUser.getUserId());
        }
        map.put("customerCardId", userInfo.getIdcard());
        map.put("availableBalance", 0);
        map.put("customerId", String.valueOf(userId));
        map.put("customerMobile", user.getMobile());
        map.put("customerName", userInfo.getTruename());
        map.put("customerUsername", user.getUsername());
        map.put("openingTimeStr", GetDate.dateToDateFormatStr(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        map.put("platformInstcode", 10000001);
        map.put("registerTimeStr", GetDate.timestamptoStrYYYYMMDDHHMMSS(String.valueOf(user.getRegTime())));

        String sign = encryptByRSA(map, "10000001");
        ret.put("instCode", "10000001");
        ret.put("timeStamp", String.valueOf(GetDate.getDate().getTime()));
        ret.put("object", map);
        ret.put("sign", sign);
        return ret;
    }

    /**
     * 请求数据加签
     *
     * @param mapText
     */
    public  String encryptByRSA(Map<String, Object> mapText, String instCode) {
        try {
            String signText = getSignText(mapText);
            logger.info("待加签数据【" + signText + "】");
            RSAKeyUtil rsaKey = new RSAKeyUtil(new File(reqPriKey + instCode + ".p12"), reqPassword);
            RSAHelper signer = new RSAHelper(rsaKey.getPrivateKey());
            String sign = signer.sign(signText);
            return sign;
        } catch (Exception e) {
            logger.error("加签失败！" + e.getMessage(), e);
        }
        throw new IllegalArgumentException("加签失败！");

    }

    private  String getSignText(Map<String, Object> generalSignInfo) throws Exception {
        TreeMap<String, Object> treeMap = new TreeMap<>(generalSignInfo);

        StringBuffer buff = new StringBuffer();
        Iterator<Map.Entry<String, Object>> iter = treeMap.entrySet().iterator();
        Map.Entry<String, Object> entry;
        while (iter.hasNext()) {
            entry = iter.next();
            if (entry.getValue() == null) {
                entry.setValue("");
                buff.append("");
            } else {
                buff.append(String.valueOf(entry.getValue()));
            }
        }
        String requestMerged = buff.toString();
        return requestMerged.replaceAll("[\\t\\n\\r]", "");
    }

}
