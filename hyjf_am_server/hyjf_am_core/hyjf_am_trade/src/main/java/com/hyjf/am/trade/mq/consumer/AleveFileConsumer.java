/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.AleveErrorLog;
import com.hyjf.am.trade.dao.model.auto.AleveLog;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.DownloadFileProducer;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.am.trade.utils.TransUtil;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.calculate.DateUtils;
import org.apache.commons.lang3.StringUtils;
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
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author wangjun
 * @version AleveFileConsumer, v0.1 2018/6/25 17:52
 */
@Component
public class AleveFileConsumer extends Consumer{
    private static final Logger logger = LoggerFactory.getLogger(AleveFileConsumer.class);

    @Autowired
    AleveLogFileService aleveLogFileService;

    @Autowired
    private DownloadFileProducer downloadFileProducer;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.ALEVE_FILE_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ALEVE_FILE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AleveFileConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====AleveFile consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【导入流水明细(Aleve)】接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【导入流水明细(Aleve)】接收到的消息：" + msgBody);

            String beforeDate = DateUtils.getBeforeDateOfDay();//获取前一天时间返回时间类型 yyyyMMdd
            JSONObject json = JSONObject.parseObject(msgBody);
            String savePath = json.getString("savePath");
            if(StringUtils.isBlank(savePath)){
                logger.error("【导入流水明细(Aleve)】接收到的savePath为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            String filePathAleve = json.getString("filePathAleve");
            if(StringUtils.isBlank(filePathAleve)){
                logger.error("【导入流水明细(Aleve)】接收到的filePathAleve为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            File dir = new File(savePath);
            File fin;
            try {
                logger.info(dir.getCanonicalPath() + File.separator +filePathAleve+beforeDate);
                fin = new File(dir.getCanonicalPath() + File.separator +filePathAleve+beforeDate);

                List<AleveLog> aleveLogs = new ArrayList<>();
                List<AleveErrorLog> aleveErrorLogs = new ArrayList<>();

                //读取文件
                TransUtil.readFileAleve(fin,aleveLogs,aleveErrorLogs);

                //插入数据
                //数据量太大 事务占用时间太长导致主从不同步 改成单条插入提交
                if(!CollectionUtils.isEmpty(aleveLogs)){
                    //成功插入条数
//                    int successCount = aleveLogs.size();
//                    for (AleveLog aleveLog : aleveLogs) {
//                        try {
//                            //插入aleveLog表
//                            aleveLogFileService.insertAleveLog(aleveLog);
//                        }
//                        catch (Exception e){
//                            logger.error("AleveLog插入失败，电子账号：{}，交易金额：{}，流水号：{}", aleveLog.getCardnbr(), aleveLog.getAmount(), aleveLog.getTranno());
//                            successCount -= 1;
//                            continue;
//                        }
//                    }
//                    logger.info("AleveLog已插入 " + successCount + " 条记录");
                    try {
                        aleveLogFileService.insertAleveLogByList(aleveLogs);
                        logger.info("AleveLog已插入 " + aleveLogs.size() + " 条记录");
                    } catch (Exception e){
                        logger.error("AleveLog插入失败", e);
                    }
                }
                if(!CollectionUtils.isEmpty(aleveErrorLogs)){
                    int successCount = aleveErrorLogs.size();
                    for(AleveErrorLog aleveErrorLog : aleveErrorLogs){
                        try {
                            //插入aleveErrorLog表
                            aleveLogFileService.insertAleveErrorLog(aleveErrorLog);
                        }
                        catch (Exception e){
                            logger.error("AleveErrorLog插入失败");
                            successCount -= 1;
                            continue;
                        }
                    }
                    logger.info("AleveErrorLog已插入 " + successCount + " 条记录");
                }
            } catch (Exception e) {
                logger.error("AleveLog插入失败", e);
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            JSONObject params = new JSONObject();
            params.put("status", "1");
            try {
                downloadFileProducer.messageSend(new MessageContent(MQConstant.AUTO_CORRECTION_TOPIC, UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
            } catch (MQException e) {
                logger.error("发送【自动冲正】MQ失败...");
            }
            logger.info("********************导入流水明细Aleve结束*************************");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
