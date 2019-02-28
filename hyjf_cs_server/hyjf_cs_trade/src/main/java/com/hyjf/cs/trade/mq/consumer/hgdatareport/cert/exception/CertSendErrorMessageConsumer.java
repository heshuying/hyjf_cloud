package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.exception;

import com.hyjf.am.vo.hgreportdata.cert.CertErrLogVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.exception.CertSendExceptionService;
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
 * @Description 合规数据上报 CERT 上报失败异常处理
 * @Author sunss
 * @Date 2019/1/21 10:23
 */
@Service
@RocketMQMessageListener(topic = MQConstant.CERT_EXCEPTION_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.CERT_EXCEPTION_GROUP)
public class CertSendErrorMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(CertSendErrorMessageConsumer.class);

    private String thisMessName = "上报失败异常处理";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";
    // 是否正在运行
    private static boolean isRun = false;

    @Autowired
    private CertSendExceptionService certSendExceptionService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader+"====start=====");
    }

    @Override
    public void onMessage(MessageExt message) {
        logger.info(logHeader+" 收到消息，开始处理....");

        // --> 消息内容校验
        if (message == null || message.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null！！！");
            return;
        }
        if(isRun){
            logger.error(logHeader + "正在运行！");
            return;
        }
        String msgBody = new String(message.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);
        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        boolean canRun = certSendExceptionService.checkCanRun();
        if(!canRun){
            logger.info(logHeader + "redis不允许上报！");
            return;
        }
        isRun = true;

        // --> 消息处理
        try {
            // --> 调用service组装数据
            // 查询待处理的异常
            List<CertErrLogVO> errLogs = certSendExceptionService.getCertErrLogs();
            if(errLogs!=null && errLogs.size()>0){
                for (CertErrLogVO item: errLogs) {
                    certSendExceptionService.insertData(item);
                }
            }else {
                logger.info(logHeader+" 无需要处理的数据~~");
            }
        } catch (Exception e) {
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
            isRun = false;
            return;
        }finally {
            logger.info(logHeader + " 结束。");
            isRun = false;
        }
    }
}
