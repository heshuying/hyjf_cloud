package com.hyjf.wbs.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.mq.MqConstants;
import com.hyjf.wbs.qvo.ProductInfoQO;
import com.hyjf.wbs.trade.service.SynProductInfoService;
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

import java.io.IOException;

/**
 * @Auther: wxd
 * @Date: 2019-04-16 14:05
 * @Description:标的信息实时推送wbs财富端
 */

@Service
@RocketMQMessageListener(topic = MQConstant.WBS_BORROW_INFO_TOPIC, selectorExpression = MQConstant.WBS_BORROW_INFO_TAG, consumerGroup = MqConstants.WBS_SYNC_Product_Info_GROUP)
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
    @Autowired
    private SynProductInfoService synProductInfoService;

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
            ProductInfoQO productInfoQO = new ProductInfoQO();


            productInfoQO.setProductNo(productNo);
            productInfoQO.setProductStatus(Integer.valueOf(productStatus));

            String productName = "";
            String linkUrl = "";
            String H5linkUrl = "";
            if (productType.equals("0")) {
                productName = "散标";
                linkUrl = PC_SANBIAO_URL + productNo;
                H5linkUrl = H5_SANBIAO_URL + productNo;
            } else if (productType.equals("1")) {
                productName = "智投";
                linkUrl = PC_ZHITOU_URL + productNo;
                H5linkUrl = H5_ZHITOU_URL + productNo;
            }
            productInfoQO.setProductName(productName);
            productInfoQO.setLinkUrl(linkUrl);
            productInfoQO.setH5linkUrl(H5linkUrl);

            synProductInfoService.sync(productInfoQO);


//
//            String reqData = buildData(account).toJSONString();
//            logger.info("=====" + CONSUMER_NAME + " 请求crm[url = {}]更新数据用户数据[reqData = {}]=====", crmUpdateCustomerUrl, reqData);
//            CloseableHttpResponse response = postJson(crmUpdateCustomerUrl, reqData);
//            if (null == response || response.getStatusLine().getStatusCode() != HttpStatus.SC_OK) {
//                // 请求没有返回200 则稍后重新消费
//                return;// ConsumeConcurrentlyStatus.RECONSUME_LATER;
//            }
        } catch (Exception e1) {
            logger.error("=====" + CONSUMER_NAME + "异常=====");
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
}