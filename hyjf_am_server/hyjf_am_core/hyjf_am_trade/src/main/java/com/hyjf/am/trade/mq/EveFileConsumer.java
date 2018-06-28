/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.AleveErrorLog;
import com.hyjf.am.trade.dao.model.auto.EveLog;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.am.trade.utils.TransUtil;
import com.hyjf.common.constants.MQConstant;
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

/**
 * @author wangjun
 * @version EveFileConsumer, v0.1 2018/6/25 17:52
 */
@Component
public class EveFileConsumer extends Consumer{
    private static final Logger logger = LoggerFactory.getLogger(EveFileConsumer.class);

    @Autowired
    AleveLogFileService aleveLogFileService;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.EVE_FILE_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.EVE_FILE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new EveFileConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====EveFile consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【导入流水明细(Ele)】接收到的消息为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【导入流水明细(eve)】接收到的消息：" + msgBody);

            JSONObject json = JSONObject.parseObject(msgBody);
            String savePath = json.getString("savePath");
            String beforeDate = DateUtils.getBeforeDateOfDay();//获取前一天时间返回时间类型 yyyyMMdd
            String filePatheve = json.getString("filePathEve");
            if(StringUtils.isBlank(savePath)){
                logger.error("【导入流水明细(eve)】接收到的savePath为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            if(StringUtils.isBlank(filePatheve)){
                logger.error("【导入流水明细(eve)】接收到的filePatheve为null");
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
            File dir = new File(savePath);
            File fin;
            try {
                logger.info(dir.getCanonicalPath() + File.separator +filePatheve+beforeDate);
                fin = new File(dir.getCanonicalPath() + File.separator +filePatheve+beforeDate);
                List<EveLog> eveLogs = new ArrayList<>();
                List<AleveErrorLog> aleveErrorLogs = new ArrayList<>();

                //读取文件
                TransUtil.readFileEve(fin,eveLogs,aleveErrorLogs);

                //插入数据
                if(!CollectionUtils.isEmpty(eveLogs)){
                    aleveLogFileService.insertEveLog(eveLogs);
                    logger.info("EveLog已插入 " + eveLogs.size() + " 条记录");
                }
                if(!CollectionUtils.isEmpty(aleveErrorLogs)){
                    aleveLogFileService.insertAleveErrorLog(aleveErrorLogs);//异常记录
                    logger.info("AleveErrorLog已插入 " + aleveErrorLogs.size() + " 条记录");
                }

            } catch (Exception e) {
                logger.error("eveLog插入失败",e);
            }
            logger.info("********************导入流水明细Eve结束*************************");
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }
}
