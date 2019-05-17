package com.hyjf.wbs.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.mq.MqConstants;
import com.hyjf.wbs.qvo.ProductInfoQO;
import com.hyjf.wbs.trade.dao.model.auto.HjhPlan;
import com.hyjf.wbs.trade.dao.model.customize.BorrowCustomize;
import com.hyjf.wbs.trade.service.BorrowInfoService;
import com.hyjf.wbs.trade.service.HjhPlanInfoService;
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
    @Autowired
    private BorrowInfoService borrowInfoService;
    @Autowired
    private HjhPlanInfoService hjhPlanInfoService;

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
            // 产品状态 2,5
            String productStatus = jsonObj.getString("productStatus");
            // 产品类型 0 散标类, 1 计划类
            String productType = jsonObj.getString("productType");
            //产品发布状态
            Integer publishStatus = 1;
            String productName = "";
            String linkUrl = "";
            String H5linkUrl = "";
            Integer productTpyeInt = 2;
            ProductInfoQO productInfoQO = new ProductInfoQO();
            if (productType.equals("0")) {
                productName = "散标";
                productTpyeInt = 2;
                linkUrl = PC_SANBIAO_URL + productNo;
                H5linkUrl = H5_SANBIAO_URL + productNo;
                if (productStatus.equals("3") ||productStatus.equals("5") || productStatus.equals("6")) {
                    publishStatus = 3;
                }
                //查询标的信息
                BorrowCustomize borrowCustomize = borrowInfoService.selectByNid(productNo);
                productInfoQO.setProductName("散标");
                if (borrowCustomize != null) {
                    /**
                     * 散标判断是否加入计划，如果加入计划，则不推送
                     */
                    if(borrowCustomize.getPlanNid()==null||borrowCustomize.getPlanNid().isEmpty()) {
                        productInfoQO.setDeadlineNum(borrowCustomize.getDeadlineNum());
                        productInfoQO.setDeadlineUnit(borrowCustomize.getDeadlineUnit());
                        productInfoQO.setInvestAmount(Double.valueOf(borrowCustomize.getInvestAmount()));
                        productInfoQO.setReferenceIncome(String.valueOf(borrowCustomize.getReferenceIncome()));
                    }else{
                        logger.info("====" + CONSUMER_NAME + "散标已加入计划，不符合推送范围，不消费，标的编号[{}]=====", productNo);
                        return;
                    }
                }

            } else if (productType.equals("1")) {
                productName = "智投";
                productTpyeInt = 3;
                linkUrl = PC_ZHITOU_URL + productNo;
                H5linkUrl = H5_ZHITOU_URL + productNo;
                //智投项目 还款中的状态5改为对应的募集结束3
                if (productStatus.equals("8")) {
                    publishStatus = 3;
                }


                //查询标的信息
                HjhPlan hjhPlan = hjhPlanInfoService.selectHjhPlanInfo(productNo);
                if (hjhPlan != null) {
                    Integer isMonth = 1;
                    if (hjhPlan.getIsMonth() == 0) {
                        isMonth = 1;
                    } else if (hjhPlan.getIsMonth() == 1) {
                        isMonth = 2;
                    }
                    productInfoQO.setDeadlineNum(hjhPlan.getLockPeriod());
                    productInfoQO.setDeadlineUnit(isMonth);
                    productInfoQO.setInvestAmount(hjhPlan.getMinInvestment().doubleValue());
                    productInfoQO.setReferenceIncome(String.valueOf(hjhPlan.getExpectApr()));
                    productInfoQO.setProductName(hjhPlan.getPlanName());
                }
            } else {
                logger.info("====" + CONSUMER_NAME + "产品类型传值不符合要求，消息内容[{}]=====", jsonObj);
                return;
            }


            productInfoQO.setProductType(productTpyeInt);
            productInfoQO.setPublishStatus(publishStatus);
            productInfoQO.setH5linkUrl(H5linkUrl);
            productInfoQO.setLinkUrl(linkUrl);

            //TODO:方便测试使用，将产品编号用作产品名称，方便测试传数据区分 上线前删除此行
            productInfoQO.setProductName(productNo);
            productInfoQO.setProductNo(productNo);
            productInfoQO.setProductStatus(Integer.valueOf(productStatus));


            synProductInfoService.sync(productInfoQO);

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