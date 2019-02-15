package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.transferstatus;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transferproject.CertTransferProjectService;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.transferstatus.CertTransferStatusService;
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
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.CERT_TRANSFER_STATUS_TAG, consumerGroup = MQConstant.CERT_TRANSFER_STATUS_GROUP)
public class CertTransferStatusMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    Logger logger = LoggerFactory.getLogger(CertTransferStatusMessageConsumer.class);

    private String thisMessName = "转让状态信息";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";


    @Autowired
    private CertTransferStatusService certTransferStatusService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CertTransferProjectMessageConsumer start=====");
    }

    @Override
    public void onMessage(MessageExt paramBean) {
        logger.info(logHeader + " 开始。");
        // --> 消息内容校验
        String msgBody = new String(paramBean.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(logHeader + "解析消息体失败！！！", e);
            return;
        }

        String creditNid = jsonObject.getString("creditNid");
        String flag = jsonObject.getString("flag");
        //1,2,3
        String status = jsonObject.getString("status");
        String borrowNid = jsonObject.getString("borrowNid");
        String tradeDate = jsonObject.getString("tradeDate");

        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        boolean canRun = certTransferStatusService.checkCanRun();
        if(!canRun){
            logger.info(logHeader + "redis不允许上报！");
            return;
        }

        if (StringUtils.isBlank(flag)) {
            logger.error(logHeader + "通知参数不全！！！flag");
            return;
        }
        if (StringUtils.isBlank(status)) {
            logger.error(logHeader + "通知参数不全！！！certStatus");
            return;
        }
        if("5".equals(status)){
            if (StringUtils.isBlank(borrowNid)) {
                logger.error(logHeader + "通知参数不全！！！borroNid");
                return;
            }
        }else{
            if (StringUtils.isBlank(creditNid)) {
                logger.error(logHeader + "通知参数不全！！！creditNid");
                return;
            }
        }
        if("1".equals(status)){
            logger.error(logHeader + "通知参数不做处理！！！");
            return;
        }
        // --> 消息处理
        try {
            // --> 增加防重校验（根据不同平台不同上送方式校验不同）

            Map<String, Object> map=certTransferStatusService.getMap(creditNid,flag,status,borrowNid);
            if(map==null){
                return;
            }
            // --> 调用service组装数据
            JSONArray data =certTransferStatusService.createDate(map,flag);
            if(data==null){
                return;
            }
            // 上送数据
            CertReportEntityVO entity = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_TRANSFER_STATUS, msgBody, data);
            try {
                // 掉单用
                if(tradeDate!=null&&!"".equals(tradeDate)){
                    entity.setTradeDate(tradeDate);
                }
                entity = certTransferStatusService.insertAndSendPost(entity);
            } catch (Exception e) {
                throw e;
            }

            if("0".equals(map.get("transferStatus"))){
                map=certTransferStatusService.getMap(creditNid,flag,"1",borrowNid);
                // --> 调用service组装数据
                data =certTransferStatusService.createDate(map,flag);
                // 上送数据
                entity = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_TRANSFER_STATUS, msgBody, data);
                // 掉单用
                if(tradeDate!=null&&!"".equals(tradeDate)){
                    entity.setTradeDate(tradeDate);
                }
                entity = certTransferStatusService.insertAndSendPost(entity);
            } else if("2".equals(map.get("transferStatus"))){
                map.put("transferStatus","4");
                map.put("amount","0.00");
                map.put("interest","0.00");
                map.put("floatMoney", "0.00");
                data =certTransferStatusService.createDate(map,flag);
                // 上送数据
                entity = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_TRANSFER_STATUS, msgBody, data);
                // 掉单用
                if(tradeDate!=null&&!"".equals(tradeDate)){
                    entity.setTradeDate(tradeDate);
                }
                entity = certTransferStatusService.insertAndSendPost(entity);
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
