/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.AleveErrorLog;
import com.hyjf.am.trade.dao.model.auto.AleveLog;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.am.trade.utils.TransUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.calculate.DateUtils;
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
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wangjun
 * @version AleveFileConsumer, v0.1 2018/6/25 17:52
 */
@Service
@RocketMQMessageListener(topic = MQConstant.ALEVE_FILE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.ALEVE_FILE_GROUP)
public class AleveFileConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(AleveFileConsumer.class);

    @Autowired
    private AleveLogFileService aleveLogFileService;

    @Autowired
    private CommonProducer commonProducer;

    @Override
    public void onMessage(MessageExt messageExt) {
        // --> 消息检查
        MessageExt msg = messageExt;
        if (msg == null || msg.getBody() == null) {
            logger.error("【导入流水明细(Aleve)】接收到的消息为null");
            return;
        }
        try {
            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【导入流水明细(Aleve)】接收到的消息：" + msgBody);

//            String beforeDate = DateUtils.getBeforeDateOfDay();//获取前一天时间返回时间类型 yyyyMMdd
            JSONObject json = JSONObject.parseObject(msgBody);
            String isBOA = json.getString("isBOA");
            String savePath = json.getString("savePath");
            if (StringUtils.isBlank(savePath)) {
                logger.error("【导入流水明细(Aleve)】接收到的savePath为null");
                return;
            }
            String filePathAleve = json.getString("filePathAleve");
            if (StringUtils.isBlank(filePathAleve)) {
                logger.error("【导入流水明细(Aleve)】接收到的filePathAleve为null");
                return;
            }
            String beforeDate = json.getString("dualDate");
            if (StringUtils.isBlank(beforeDate)) {
                logger.error("【导入流水明细(Aleve)】接收到的dualDate为null");
                return;
            }

            Integer countsAleve = this.aleveLogFileService.countAleveByExample(beforeDate);
            if(countsAleve > 0){
                logger.error("【导入流水明细(Aleve)】eve数据库已存在数据、请核对后再做处理！");
                return;
            }

            File dir = new File(savePath);
            File fin;
            try {
                logger.info(dir.getCanonicalPath() + File.separator + filePathAleve + beforeDate);
                fin = new File(dir.getCanonicalPath() + File.separator + filePathAleve + beforeDate);

                List<AleveLog> aleveLogs = new ArrayList<>();
                List<AleveErrorLog> aleveErrorLogs = new ArrayList<>();

                //读取文件
                TransUtil.readFileAleve(fin, aleveLogs, aleveErrorLogs, beforeDate);

                //插入数据
                if (!CollectionUtils.isEmpty(aleveLogs)) {
                    try {
                        aleveLogFileService.insertAleveLogByList(aleveLogs);
                        logger.info("AleveLog已插入 " + aleveLogs.size() + " 条记录");
                    } catch (Exception e) {
                        logger.error("AleveLog插入失败", e);
                    }
                }
                if (!CollectionUtils.isEmpty(aleveErrorLogs)) {
                    try {
                        aleveLogFileService.insertAleveErrorLogByList(aleveErrorLogs);
                        logger.info("AleveErrorLog已插入 " + aleveErrorLogs.size() + " 条记录");
                    } catch (Exception e) {
                        logger.error("AleveErrorLog插入失败", e);
                    }
                }
            } catch (Exception e) {
                logger.error("AleveLog插入失败", e);
                return;// ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            if (StringUtils.isNotBlank(isBOA) && "1".equals(isBOA)) {
                JSONObject params = new JSONObject();
                params.put("status", "1");
                try {
                    commonProducer.messageSend(new MessageContent(MQConstant.AUTO_CORRECTION_TOPIC, UUID.randomUUID().toString(), params));
                } catch (MQException e) {
                    logger.error("发送【自动冲正】MQ失败...");
                }
            }
            logger.info("********************导入流水明细Aleve结束*************************");
            return;
        } catch (Exception e) {
            logger.error("【导入流水明细(Aleve)】消费异常!", e);
            return;
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====AleveFile consumer=====");
    }
}
