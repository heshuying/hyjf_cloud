package com.hyjf.wbs.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.dto.ProductInfoQO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * @Auther: wxd
 * @Date: 2019-04-16 14:05
 * @Description:标的信息实时推送wbs财富端
 */

@Service
@RocketMQMessageListener(topic = MQConstant.WBS_BORROW_INFO_TOPIC, selectorExpression = MQConstant.WBS_BORROW_INFO_TAG, consumerGroup = "WBS_SYNC_Product_Info_GROUP")
public class SyncProductInfoConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SyncWbsAccountConsumer.class);

    public static final String CONSUMER_NAME = "<<产品信息及状态同步到WBS财富管理系统>>: ";

    @Value("${hyjf.web.pc.sanbiao.host}")
    private String PC_SANBIAO_URL;

    @Value("${hyjf.web.pc.zhitou.host}")
    private String PC_ZHITOU_URL;

    @Value("${hyjf.web.h5.sanbiao.host}")
    private String H5_SANBIAO_URL;

    @Value("${hyjf.web.h5.zhitou.host}")
    private String H5_ZHITOU_URL;


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


            // 产品编号
            String productNo = jsonObj.getString("productNo");
            // 产品状态
            String productStatus = jsonObj.getString("productStatus");
            // 产品类型 0 散标类, 1 计划类
            String productType = jsonObj.getString("productType");
            ProductInfoQO productInfoDto = new ProductInfoQO();



            productInfoDto.setProductNo(productNo);
            productInfoDto.setProductStatus(Integer.valueOf(productStatus));

            String productName = "";
            String linkUrl = "";
            String H5linkUrl = "";
            if (productType.equals("0")) {
                productName="散标";
                linkUrl = PC_SANBIAO_URL + productNo;
                H5linkUrl = H5_SANBIAO_URL + productNo;
            } else if (productType.equals("1")) {
                productName="智投";
                linkUrl = PC_ZHITOU_URL + productNo;
                H5linkUrl = H5_ZHITOU_URL + productNo;
            }
            productInfoDto.setProductName(productName);
            productInfoDto.setLinkUrl(linkUrl);
            productInfoDto.setH5linkUrl(H5linkUrl);


            //TODO: 推送客户信息

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