/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.bifa.hjhplaninfo;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BifaHjhPlanInfoEntityVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaHjhPlanInfoService;
import org.apache.commons.lang3.StringUtils;
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

/**
 * @author jun
 * @version BifaHjhPlanInfoConsumer, v0.1 2019/1/31 15:01
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.HJHPLAN_ADD_TAG, consumerGroup = MQConstant.BIFA_HJH_PLANINFO_GROUP)
public class BifaHjhPlanInfoConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(BifaHjhPlanInfoConsumer.class);
    private String thisMessName = "新增智投信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";


    @Autowired
    private BifaHjhPlanInfoService bifaHjhPlanInfoService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader + " 开始。");
    }

    @Override
    public void onMessage(MessageExt msg) {
        // --> 消息检查
        if (msg == null || msg.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null");
            return;
        }
        //-->新增智投上报智投信息
        if (MQConstant.HJHPLAN_ADD_TAG.equals(msg.getTags())){
            //-->日志输出接收到的消息
            String msgBody = new String(msg.getBody());
            logger.info(logHeader + "接收到的消息：" + msgBody);

            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.parseObject(msgBody);
            } catch (Exception e) {
                logger.error(logHeader + "解析消息体失败！！！", e);
                return;
            }
            //-->获取新增的智投编号
            String planNid = jsonObject.getString("planNid");
            if (StringUtils.isEmpty(planNid)) {
                logger.error(logHeader + "智投编号不能为空！！！");
                return;
            }
            //-->去mongo中查一下看看该智投是否已上报过
            BifaHjhPlanInfoEntityVO bifaHjhPlanInfoEntity = bifaHjhPlanInfoService.getBifaHjhPlanInfoFromMongoDB(planNid);
            if (bifaHjhPlanInfoEntity==null){
                HjhPlanVO hjhplan = this.bifaHjhPlanInfoService.getHjhPlan(planNid);
                if (null == hjhplan) {
                    logger.error(logHeader + "未获取到新增的智投信息！！"+"planNid:"+planNid);
                    return;
                }
                // --> 数据变换
                bifaHjhPlanInfoEntity = new BifaHjhPlanInfoEntityVO();
                boolean result = bifaHjhPlanInfoService.convertBifaHjhPlanInfo(hjhplan,bifaHjhPlanInfoEntity);
                if (!result){
                    logger.error(logHeader + "新增的智投数据变换失败！！"+JSONObject.toJSONString(bifaHjhPlanInfoEntity));
                    return;
                }
                // --> 上报数据（实时上报）
                //上报数据失败时 将数据存放到mongoDB
                String methodName = "productRegistration";
                BifaHjhPlanInfoEntityVO reportResult = bifaHjhPlanInfoService.reportData(methodName,bifaHjhPlanInfoEntity);
                if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())){
                    logger.info(logHeader + "上报新增的智投数据成功。" + JSONObject.toJSONString(reportResult));
                } else if ("9".equals(reportResult.getReportStatus())) {
                    logger.error(logHeader + "上报新增的智投数据失败！！"+JSONObject.toJSONString(reportResult));
                }
                // --> 保存上报数据
                bifaHjhPlanInfoService.insertHjhPlanInfoReportData(reportResult);
                logger.info(logHeader + "新增的智投数据保存本地成功！！"+JSONObject.toJSONString(reportResult));
            }

        }

    }

}
