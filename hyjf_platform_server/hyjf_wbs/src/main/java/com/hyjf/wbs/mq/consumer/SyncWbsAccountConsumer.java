package com.hyjf.wbs.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.mq.MqConstants;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.service.customerinfo.AccountService;
import com.hyjf.wbs.user.dao.model.auto.UtmReg;
import com.hyjf.wbs.user.service.constomerinfo.UtmRegService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: wxd
 * @Date: 2019-04-10 16:17
 * @Description:用户信息变更实时推送wbs财富端
 */

@Service
@RocketMQMessageListener(topic = MQConstant.SYNC_ACCOUNT_TOPIC, selectorExpression = "*", consumerGroup = MqConstants.WBS_SYNC_ACCOUNT_GROUP)
public class SyncWbsAccountConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SyncWbsAccountConsumer.class);

    public static final String CONSUMER_NAME = "<<账户额度同步到WBS财富管理系统>>: ";
    @Autowired
    private AccountService accountService;
    @Autowired
    private UtmRegService utmRegService;

//    @Autowired
//    private UserDepartmentInfoService userDepartmentInfoService;

//    @Value("${hyjf.req.pri.key}")
//    private String hyjfReqPrimaryKeyPath;
//
//    @Value("${hyjf.req.password}")
//    private String hyjfReqPasswordPath;
//
//    @Value("${crm.updateCustomer.url}")
//    private String crmUpdateCustomerUrl;

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            MessageExt msg = messageExt;
            String tagName = msg.getTags();
            String jsonMsg = new String(msg.getBody());
            logger.info("====" + CONSUMER_NAME + "收到消息[{}],消费开始=====", jsonMsg);
            JSONObject jsonObj = JSON.parseObject(jsonMsg);
            if (jsonObj == null) {
                logger.error("=====" + CONSUMER_NAME + "消息实体转换异常=====");
                return;
            }
            String userId = jsonObj.getString("userId");
            if (StringUtils.isBlank(userId)) {
                logger.error("=====" + CONSUMER_NAME + "userId 为空=====");
                return;
            }

            //根据渠道注册表查询用户是否是属于财富端对应的渠道
            List<Integer> utmId = new ArrayList<Integer>();
            utmId.add(11200045);//纳觅渠道编号
//            utmId.add(11200045);//裕峰瑞渠道编号


            UtmReg utmReg = utmRegService.selectUtmInfo(Integer.valueOf(userId), utmId);
            //符合资产端客户信息，推送账户变更信息
            if (utmReg != null) {
                CustomerSyncQO customerSyncQO = new CustomerSyncQO();


                Account account = accountService.getAccount(Integer.valueOf(userId));
                if (account == null) {
                    logger.error("=====" + CONSUMER_NAME + " 没有查询到目标账户, userId = [{}]=====", userId);
                    return;
                }

                //TODO: 推送客户信息
            }
//
//            String reqData = buildData(account).toJSONString();
//            logger.info("=====" + CONSUMER_NAME + " 请求crm[url = {}]更新数据用户数据[reqData = {}]=====", crmUpdateCustomerUrl, reqData);
//            CloseableHttpResponse response = postJson(crmUpdateCustomerUrl, reqData);
//            if (null == response || response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                // 请求没有返回200 则稍后重新消费
//                return;// ConsumeConcurrentlyStatus.RECONSUME_LATER;
//            }
        } catch (Exception e1) {
            logger.error("=====" + CONSUMER_NAME + "同步账户额度到wbs异常=====");
            logger.error(e1.getMessage());
            return;
        }
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
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
        logger.info("====" + CONSUMER_NAME + "监听初始化完成, 启动完毕=====");
    }

//    /**
//     * 组装请求参数
//     *
//     * @author zhangyk
//     * @date 2018/8/3 14:32
//     */
//    private JSONObject buildData(Account account) {
//        JSONObject ret = new JSONObject();
//        Map<String, Object> map = new HashMap<>();
//        map.put("customerId", account.getUserId());
//        map.put("availableBalance", account.getBankBalance());
//        if (account.getBankAwait() == null) {
//            account.setBankAwait(new BigDecimal("0.00"));
//        }
//        if (account.getPlanAccountWait() == null) {
//            account.setPlanAccountWait(new BigDecimal("0.00"));
//        }
//        map.put("pendingAmount", account.getBankAwait().add(account.getPlanAccountWait()));
//
//        String sign = this.encryptByRSA(map, "10000001");
//        ret.put("instCode", "10000001");
//        ret.put("object", map);
//        ret.put("sign", sign);
//        return ret;
//    }
//
//
//    /**
//     * 请求数据加签
//     *
//     * @param mapText
//     */
//    private String encryptByRSA(Map<String, Object> mapText, String instCode) {
//        try {
//            String signText = getSignText(mapText);
//            logger.info("待加签数据【" + signText + "】");
//
//            RSAKeyUtil rsaKey = new RSAKeyUtil(new File(hyjfReqPrimaryKeyPath + instCode + ".p12"), hyjfReqPasswordPath);
//            RSAHelper signer = new RSAHelper(rsaKey.getPrivateKey());
//            String sign = signer.sign(signText);
//            return sign;
//        } catch (Exception e) {
//            logger.error("加签失败！" + e.getMessage(), e);
//        }
//        throw new IllegalArgumentException("加签失败！");
//
//    }
//
//    private String getSignText(Map<String, Object> generalSignInfo) throws Exception {
//        TreeMap<String, Object> treeMap = new TreeMap<>(generalSignInfo);
//
//        StringBuffer buff = new StringBuffer();
//        Iterator<Map.Entry<String, Object>> iter = treeMap.entrySet().iterator();
//        Map.Entry<String, Object> entry;
//        while (iter.hasNext()) {
//            entry = iter.next();
//            if (entry.getValue() == null) {
//                entry.setValue("");
//                buff.append("");
//            } else {
//                buff.append(String.valueOf(entry.getValue()));
//            }
//        }
//        String requestMerged = buff.toString();
//        return requestMerged.replaceAll("[\\t\\n\\r]", "");
//    }

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
                logger.info("======" + CONSUMER_NAME + " 投递返回结果 [{}]=====", content);
            } else {
                logger.info("=====" + CONSUMER_NAME + " 投递结果httpStatus异常=====");
            }
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