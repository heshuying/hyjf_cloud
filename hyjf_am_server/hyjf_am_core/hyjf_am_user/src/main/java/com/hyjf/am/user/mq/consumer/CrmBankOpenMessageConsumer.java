/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.mq.consumer;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import com.hyjf.am.user.dao.model.auto.*;
import com.hyjf.am.user.service.front.ca.FddCertificateService;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.RSAHelper;
import com.hyjf.common.util.RSAKeyUtil;

/**
 * @author zhangqingqing
 * @version CrmBankOpenMessageConsumer, v0.1 2018/6/28 14:27
 */
@Service
@RocketMQMessageListener(topic = MQConstant.CRM_ROUTINGKEY_BANCKOPEN_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.CRM_ROUTINGKEY_BANCKOPEN_GROUP)
public class CrmBankOpenMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CrmBankOpenMessageConsumer.class);

    @Value("${crm.insertCustomerAction.url}")
    private String crmInsertUrl;

    @Value("${hyjf.req.pri.key}")
    private String reqPriKey;

    @Value("${hyjf.req.password}")
    private String reqPassword;

    @Autowired
    FddCertificateService fddCertificateService;
    
    @Override
	public void onMessage(MessageExt  message) {
        logger.info("CrmBankOpenMessageConsumer 收到消息，开始处理....");
        MessageExt msg = message;
        // --> 消息转换
        logger.info("【crm开户同步】接收到的消息：" + msg);
        String msgBody = new String(msg.getBody());
        Map<String,Integer> msgMap = JSONObject.parseObject(msgBody, Map.class);
        logger.info("【crm开户同步】接收到的用户ID：" + msgMap.get("userId"));
        CloseableHttpResponse result = null;
        logger.info(msgMap.get("userId")+"");
        try {
            Integer userId = Integer.parseInt(String.valueOf(msgMap.get("userId")).trim());
            logger.info("crmInsertUrl================:"+crmInsertUrl+"；buildData(userId) is null："+(buildData(userId)==null));
           // result = restTemplate.postForEntity(crmInsertUrl, buildData(userId).toJSONString(),CloseableHttpResponse.class).getBody();
            result = postJson(crmInsertUrl, buildData(userId).toJSONString());
        } catch (Exception e) {
            logger.error("【crm开户同步】异常，重新投递", e);
            return ;
        }
		if (result == null || result.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
			logger.error("【crm开户同步】网络异常，重新投递, response status code is : {}",
					result == null ? null : result.getStatusLine().getStatusCode());
            return ;
        } else {
            logger.info("【crm开户同步】投递成功");
        }
    
    }

	@Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CrmBankOpenMessageConsumer consumer=====");
    }
	
    private JSONObject buildData(Integer userId) {
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
            map.put("recommendCardId", referrerInfo!=null?referrerInfo.getIdcard():"");
            map.put("recommendName", referrerInfo!=null?referrerInfo.getTruename():"");
            map.put("recommondUsername", referrerUser!=null?referrerUser.getUsername():"");
            map.put("recommendMobile", referrerUser!=null?referrerUser.getMobile():"");
            map.put("recommondId", referrerUser!=null?referrerUser.getUserId():"");
        }
        map.put("customerCardId", userInfo!=null?userInfo.getIdcard():"");
        map.put("availableBalance", 0);
        map.put("customerId", String.valueOf(userId));
        map.put("customerMobile", user!=null?user.getMobile():"");
        map.put("customerName", userInfo!=null?userInfo.getTruename():"");
        map.put("customerUsername", user!=null?user.getUsername():"");
        map.put("openingTimeStr", account!=null?GetDate.dateToDateFormatStr(account.getCreateTime(), "yyyy-MM-dd HH:mm:ss"):"");
        map.put("platformInstcode", 10000001);
        map.put("registerTimeStr", user!=null?GetDate.dateToDateFormatStr(user.getRegTime(), "yyyy-MM-dd HH:mm:ss"):"");

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

    private CloseableHttpResponse postJson(String url, String jsonStr) {

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
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    logger.error(e.getMessage());
                }
            }
        }
        return response;
    }

}
