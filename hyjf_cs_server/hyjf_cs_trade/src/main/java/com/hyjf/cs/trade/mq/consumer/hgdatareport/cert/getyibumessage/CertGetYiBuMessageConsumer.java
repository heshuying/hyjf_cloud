/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.getyibumessage;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertLogVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.getyibumessage.CertGetYiBuMessageService;
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

import java.util.List;

/**
 * @Description 合规数据上报 CERT 查询批次数据入库消息 （延时队列）
 * @Author nxl
 * @Date 2018/12/25 17:57
 */
@Service
@RocketMQMessageListener(topic = MQConstant.CERT_GETYIBU_MESSAGE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.CERT_GETYIBU_MESSAGE_GROUP)
public class CertGetYiBuMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    Logger logger = LoggerFactory.getLogger(CertGetYiBuMessageConsumer.class);

    private String thisMessName = "查询批次数据入库消息";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";
    private static boolean isRun = false;
    @Autowired
    private CertGetYiBuMessageService certGetYiBuMessageService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader + "====start=====");
    }

    @Override
    public void onMessage(MessageExt msg) {
        logger.info(logHeader + " 开始。");
        // --> 消息内容校验
        if (msg == null || msg.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null！！！");
            return;
        }
        if (isRun) {
            logger.error(logHeader + "正在运行中！！！");
            return;
        }
        isRun = true;
        String msgBody = new String(msg.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(logHeader + "解析消息体失败！！！", e);
            isRun = false;
            return;
        }

        /*String batchNum = jsonObject.getString("batchNum");*/
        String mqMsgId = jsonObject.getString("mqMsgId");
        if (StringUtils.isBlank(mqMsgId)) {
            logger.error(logHeader + "通知参数不全！！！");
            isRun = false;
            return;
        }

        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        boolean canRun = certGetYiBuMessageService.checkCanRun();
        if (!canRun) {
            logger.info(logHeader + "redis不允许上报！");
            isRun = false;
            return;
        }

        // --> 消息处理
        try {
            // --> 调用service组装数据
            List<CertLogVO> certLogList = certGetYiBuMessageService.getCertLog();
            logger.info(logHeader + "查询批次数据入库数量为"+certLogList.size());
            if (null != certLogList && certLogList.size() > 0) {
               /* for (CertLogVO certLog : certLogList) {
                    int start = certLog.getLogOrdId().indexOf("_");
                    String bachNum = certLog.getLogOrdId().substring(start + 1);
                    String infType = certLog.getInfType().toString();
                    CertReportEntityVO certReportEntity = certGetYiBuMessageService.updateYiBuMessage(bachNum, certLog.getId(), infType);
                    logger.info(logHeader + "返回结果为:" + JSONObject.toJSON(certReportEntity));
                }*/
                String certLogOrderId ="84_CERT20181123001_201902160_11779";
                int start = certLogOrderId.indexOf("_");
                String bachNum = certLogOrderId.substring(start + 1);
                String infType =certLogOrderId.substring(0, start);
                CertReportEntityVO certReportEntity = certGetYiBuMessageService.updateYiBuMessage(bachNum, 120, infType);
                logger.info(logHeader + "返回结果为:" + JSONObject.toJSON(certReportEntity));
                logger.info(logHeader + " 处理成功。" + msgBody);
            }else{
                logger.info(logHeader + " 未查询到批次数据入库数据。");
            }
        } catch (Exception e) {
            // 错误时，以下日志必须出力（预警捕捉点）
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
        } finally {
            isRun = false;
            logger.info(logHeader + " 结束。");
        }
    }
}
