package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.olddata.investdetail;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertAccountListCustomizeVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.investdetail.CertInvestDetailService;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.investdetail.CertOldInvestDetailService;
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
 * @Description 
 * @Author pangchengchao
 * @Version v0.1
 * @Date
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.CERT_OLD_INVEST_DETAIL_OTHER_TAG, consumerGroup = MQConstant.CERT_OLD_INVEST_DETAIL_OTHER_GROUP)
public class CertOldInvestDetailOtherMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    Logger logger = LoggerFactory.getLogger(CertOldInvestDetailOtherMessageConsumer.class);

    private String thisMessName = "投资明细中间空白历史记录信息";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    private CertOldInvestDetailService certOldInvestDetailService;
    @Autowired
    private CertInvestDetailService CertInvestDetailService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====CertOldInvestDetailOtherMessageConsumer start=====");
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

        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        String tradeDate = jsonObject.getString("tradeDate");

        Integer page=1;
        Integer size=1000;
        Integer id= new Integer(RedisUtils.get("CERT_OLD_INVEST_DETAIL_MAX_ID"));
        Integer maxOldId= new Integer(RedisUtils.get("CERT_OLD_INVEST_DETAIL_OTHER_MAX_ID"));
        String minId = id+"";
        String maxId = id+1000+"";
        try {
            while (!"1".equals(RedisUtils.get("CREDIT_TENDER_OTHER_RUN"))){
                if(new Integer(maxId)>maxOldId){
                    maxId=maxOldId+"";
                }
                // --> 调用service组装数据
                JSONArray data =CertInvestDetailService.createDate(minId,maxId);
                if(data==null){
                    logger.info(logHeader + "生成完成！");
                    RedisUtils.set("CREDIT_TENDER_OTHER_RUN","1");
                    return;
                }
                // 上送数据
                CertReportEntityVO entity = new CertReportEntityVO(thisMessName, CertCallConstant.CERT_INF_TYPE_INVEST_DETAIL, thisMessName, data);
                try {
                    // 掉单用
                    if(tradeDate!=null&&!"".equals(tradeDate)){
                        entity.setTradeDate(tradeDate);
                    }
                    certOldInvestDetailService.insertOldMessage(entity);
                } catch (Exception e) {
                    throw e;
                }
                logger.info(logHeader + " 处理成功。page=" + page+",size="+size+",data.size()"+data.size());
                if(new Integer(maxId)==maxOldId){
                    logger.info(logHeader + "生成完成！");
                    RedisUtils.set("CREDIT_TENDER_OTHER_RUN","1");
                    return;
                }
                minId = maxId;
                maxId = new Integer(maxId)+1000+"";
            }
        } catch (Exception e) {
            logger.error("报错了",e);
            // 错误时，以下日志必须出力（预警捕捉点）
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
        } finally {
            logger.info(logHeader + " 结束。");
        }
    }
}
