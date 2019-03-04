/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.AleveErrorLog;
import com.hyjf.am.trade.dao.model.auto.EveLog;
import com.hyjf.am.trade.service.task.AleveLogFileService;
import com.hyjf.am.trade.utils.TransUtil;
import com.hyjf.common.constants.MQConstant;
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

/**
 * @author wangjun
 * @version EveFileConsumer, v0.1 2018/6/25 17:52
 */
@Service
@RocketMQMessageListener(topic = MQConstant.EVE_FILE_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.EVE_FILE_GROUP)
public class EveFileConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(EveFileConsumer.class);

    @Autowired
    private AleveLogFileService aleveLogFileService;

    @Override
    public void onMessage(MessageExt messageExt) {
        // --> 消息检查
        MessageExt msg = messageExt;
        if(msg == null || msg.getBody() == null){
            logger.error("【导入流水明细(Ele)】接收到的消息为null");
            return;
        }
        try{
            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【导入流水明细(eve)】接收到的消息：" + msgBody);

            JSONObject json = JSONObject.parseObject(msgBody);
            String savePath = json.getString("savePath");
//            String beforeDate = DateUtils.getBeforeDateOfDay();//获取前一天时间返回时间类型 yyyyMMdd
            String beforeDate = json.getString("dualDate");
            String filePatheve = json.getString("filePathEve");
            if(StringUtils.isBlank(savePath)){
                logger.error("【导入流水明细(eve)】接收到的savePath为null");
                return;
            }
            if(StringUtils.isBlank(beforeDate)){
                logger.error("【导入流水明细(eve)】接收到的dualDate为null");
                return;
            }
            if(StringUtils.isBlank(filePatheve)){
                logger.error("【导入流水明细(eve)】接收到的filePatheve为null");
                return;
            }

            Integer countsEve = this.aleveLogFileService.countEveByExample(beforeDate);
            if(countsEve > 0){
                logger.error("【导入流水明细(eve)】eve数据库已存在数据、请核对后再做处理！");
                return;
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
                    try {
                        aleveLogFileService.insertEveLogByList(eveLogs);
                        logger.info("EveLog已插入 " + eveLogs.size() + " 条记录");
                    } catch (Exception e){
                        logger.error("EveLog插入失败", e);
                    }
                }
                if(!CollectionUtils.isEmpty(aleveErrorLogs)){
                    try {
                        aleveLogFileService.insertAleveErrorLogByList(aleveErrorLogs);
                        logger.info("AleveErrorLog已插入 " + aleveErrorLogs.size() + " 条记录");
                    } catch (Exception e){
                        logger.error("AleveErrorLog插入失败", e);
                    }
                }

            } catch (Exception e) {
                logger.error("EveLog插入失败", e);
                return; //ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }
            logger.info("********************导入流水明细Eve结束*************************");
        } catch (Exception e) {
            logger.error("【导入流水明细(eve)】消费异常!", e);
            return;
        }
        return;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====EveFile consumer=====");
    }
}
