/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.status;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.status.CertBorrowStatusService;
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

import java.util.Map;

/**
 * @Description 合规数据上报 CERT 散标状态上报（满标）
 * @Author nxl
 * @Date 2018/11/28 10:57
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.CERT_BORROW_STATUS_TAG, consumerGroup = MQConstant.CERT_BORROW_STATUS_GROUP_INVESTED)
public class CertBorrowStatusMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    Logger logger = LoggerFactory.getLogger(CertBorrowStatusMessageConsumer.class);

    private String thisMessName = "散标状态信息推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader + "====start=====");
    }

    @Autowired
    private CertBorrowStatusService certBorrowStatusService;

    @Override
    public void onMessage(MessageExt msg) {
        logger.info(logHeader + " 开始。");
        // --> 消息内容校验
        if (msg == null || msg.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null！！！");
            return;
        }

        String msgBody = new String(msg.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(logHeader + "解析消息体失败！！！", e);
            return;
        }

        String borrowNid = jsonObject.getString("borrowNid");
        String tradeDate = jsonObject.getString("tradeDate");

        if (StringUtils.isBlank(borrowNid)) {
            logger.error(logHeader + "通知参数不全！！！");
            return;
        }

        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        boolean canRun = certBorrowStatusService.checkCanRun();
        if (!canRun) {
            logger.info(logHeader + "redis不允许上报！");
            return;
        }

        // --> 消息处理
        try {
            // --> 调用service组装数据
            JSONArray listRepay = new JSONArray();
            Map<String, Object> mapParam = certBorrowStatusService.selectBorrowByBorrowNid(borrowNid, null, false);
            listRepay.add(mapParam);
            logger.info("数据：" + listRepay);
            // 上送数据
            CertReportEntityVO entity = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_STATUS, borrowNid, listRepay);
            try {
                // 掉单用
                if (tradeDate != null && !"".equals(tradeDate)) {
                    entity.setTradeDate(tradeDate);
                }
                certBorrowStatusService.insertAndSendPost(entity);
            } catch (Exception e) {
                throw e;
            }
            //判断标的状态 = 9 的,再报送一次 添加判断=9
            // 上送数据
            String productStatus = mapParam.get("productStatus").toString();
            if (productStatus.equals("9")) {
                // --> 调用service组装数据
                JSONArray listRepayAfter = new JSONArray();
                Map<String, Object> mapParamAfter = certBorrowStatusService.selectBorrowByBorrowNid(borrowNid, productStatus, false);
                listRepayAfter.add(mapParamAfter);
                logger.info("数据：" + listRepayAfter);
                // 上送数据
                CertReportEntityVO entityAfter = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_STATUS, borrowNid, listRepayAfter);
                try {
                    // 掉单用
                    if (tradeDate != null && !"".equals(tradeDate)) {
                        entityAfter.setTradeDate(tradeDate);
                    }
                    certBorrowStatusService.insertAndSendPost(entityAfter);
                } catch (Exception e) {
                    throw e;
                }
            }
            logger.info(logHeader + " 处理成功。" + msgBody);
        } catch (Exception e) {
            // 错误时，以下日志必须出力（预警捕捉点）
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
        } finally {
            logger.info(logHeader + " 结束。");
        }
    }
}
