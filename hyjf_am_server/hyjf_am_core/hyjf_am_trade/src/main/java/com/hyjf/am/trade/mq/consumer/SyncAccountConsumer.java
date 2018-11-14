package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.Account;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.service.front.account.AccountService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 同步账户额度到crm消费端
 *
 * @author zhangyk
 * @date 2018/8/3 11:21
 */
@Component
public class SyncAccountConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(SyncAccountConsumer.class);

    public static final String CONSUMER_NAME = "<<账户额度同步到CRM>>: ";

    @Autowired
    private AccountService accountService;

    @Value("${hyjf.req.pri.key}")
    private String hyjfReqPrimaryKeyPath;

    @Value("${hyjf.req.password}")
    private String hyjfReqPasswordPath;

    @Value("${crm.updateCustomer.url}")
    private String crmUpdateCustomerUrl;


    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {

        // defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.SYNC_ACCOUNT_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.SYNC_ACCOUNT_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        // 设置并发数
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);

        defaultMQPushConsumer.registerMessageListener(new SyncAccountConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====" + CONSUMER_NAME + "监听初始化完成, 启动完毕=====");

    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {


            try {

                MessageExt msg = msgs.get(0);
                String tagName = msg.getTags();
                String jsonMsg = new String(msg.getBody());
                logger.info("====" + CONSUMER_NAME + "收到消息[{}],消费开始=====", jsonMsg);
                JSONObject jsonObj = JSON.parseObject(jsonMsg);
                if (jsonObj == null) {
                    logger.error("=====" + CONSUMER_NAME + "消息实体转换异常=====");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                String userId = jsonObj.getString("userId");
                if (StringUtils.isBlank(userId)) {
                    logger.error("=====" + CONSUMER_NAME + "userId 为空=====");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
                Account account = accountService.getAccount(Integer.valueOf(userId));
                if (account == null) {
                    logger.error("=====" + CONSUMER_NAME + " 没有查询到目标账户, userId = [{}]=====", userId);
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                String reqData = buildData(account).toJSONString();
                logger.info("=====" + CONSUMER_NAME + " 请求crm[url = {}]更新数据用户数据[reqData = {}]=====", crmUpdateCustomerUrl, reqData);
                CloseableHttpResponse response = postJson(crmUpdateCustomerUrl, reqData);
                if (null == response || response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
                    // 请求没有返回200 则稍后重新消费
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            } catch (Exception e1) {
                logger.error("====="+CONSUMER_NAME+"同步账户额度到crm异常=====");
                logger.error(e1.getMessage());
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;

        }
    }

    /**
     * 组装请求参数
     * @author zhangyk
     * @date 2018/8/3 14:32
     */
    private JSONObject buildData(Account account) {
        JSONObject ret = new JSONObject();
        Map<String, Object> map = new HashMap<>();
        map.put("customerId", account.getUserId());
        map.put("availableBalance", account.getBankBalance());
        if (account.getBankAwait() == null){
            account.setBankAwait(new BigDecimal("0.00"));
        }
        if (account.getPlanAccountWait() == null){
            account.setPlanAccountWait(new BigDecimal("0.00"));
        }
        map.put("pendingAmount", account.getBankAwait().add(account.getPlanAccountWait()));

        String sign = this.encryptByRSA(map, "10000001");
        ret.put("instCode", "10000001");
        ret.put("object", map);
        ret.put("sign", sign);
        return ret;
    }


    /**
     * 请求数据加签
     *
     * @param mapText
     */
    private String encryptByRSA(Map<String, Object> mapText, String instCode) {
        try {
            String signText = getSignText(mapText);
            logger.info("待加签数据【" + signText + "】");

            RSAKeyUtil rsaKey = new RSAKeyUtil(new File(hyjfReqPrimaryKeyPath + instCode + ".p12"), hyjfReqPasswordPath);
            RSAHelper signer = new RSAHelper(rsaKey.getPrivateKey());
            String sign = signer.sign(signText);
            return sign;
        } catch (Exception e) {
            logger.error("加签失败！" + e.getMessage(), e);
        }
        throw new IllegalArgumentException("加签失败！");

    }

    private String getSignText(Map<String, Object> generalSignInfo) throws Exception {
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

    /**
     * 处理post请求.
     *
     * @param url 参数
     * @return json
     */
    public CloseableHttpResponse postJson(String url, String jsonStr) {

        // 实例化httpClient
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 实例化post方法
        HttpPost httpPost = new HttpPost(url);

        // 结果
        CloseableHttpResponse response = null;
        try {
            // 提交的参数
            StringEntity uefEntity = new StringEntity(jsonStr, "utf-8");
            uefEntity.setContentEncoding("UTF-8");
            uefEntity.setContentType("application/json");
            // 将参数给post方法
            httpPost.setEntity(uefEntity);
            // 执行post方法
            response = httpclient.execute(httpPost);

            // 执行post方法
            response = httpclient.execute(httpPost);
            if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                logger.info("======"+ CONSUMER_NAME+" 投递返回结果 [{}]=====",content);
            }else{
                logger.info("=====" + CONSUMER_NAME +" 投递结果httpStatus异常=====");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
}
