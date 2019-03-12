/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.mq.consumer;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
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

import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.am.vo.admin.UtmVO;
import com.hyjf.am.vo.datacollect.PcChannelStatisticsVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;

/**
 * @author fuqiang
 * @version PcChannelStatisticsConsumer, v0.1 2018/7/2 10:11
 */
@Service
@RocketMQMessageListener(topic = MQConstant.PC_CHANNEL_STATISTICS_ADMIN_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.PC_CHANNEL_STATISTICS_ADMIN_GROUP)
public class PcChannelStatisticsAdminConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AmAdminClient amAdminClient;
    @Autowired
    private CommonProducer commonProducer;

    private static int  MAX_RECONSUME_TIME=3;
    @Override
    public void onMessage(MessageExt  message) {
        // 查询所有pc渠道
        List<UtmVO> voList = amAdminClient.selectUtmPlatList("pc");
        logger.info("查询所有pc渠道开始"+ JSONObject.toJSONString(voList));
        if (!CollectionUtils.isEmpty(voList)) {
            for (UtmVO vo : voList) {
                Integer sourceId = vo.getSourceId();
                // 访问数
                Integer accessNumber = amAdminClient.getAccessNumber(sourceId, "pc");
                if (accessNumber == null) {
                    accessNumber = 0;
                }
                // 注册数
                Integer registNumber = amAdminClient.getRegistNumber(sourceId, "pc");
                if (registNumber == null) {
                    registNumber = 0;
                }
                // 开户数
                Integer openaccountnumber = amAdminClient.getOpenAccountNumber(sourceId, "pc");
                if (openaccountnumber == null) {
                    openaccountnumber = 0;
                }
                // 投资人数
                Integer tenderNumber = amAdminClient.getTenderNumber(sourceId, "pc");
                if (tenderNumber == null) {
                    tenderNumber = 0;
                }
                // 累计充值
                BigDecimal cumulativeRecharge = amAdminClient.getCumulativeRecharge(sourceId, "pc");
                logger.info("pc渠道统计累计充值金额=="+cumulativeRecharge);
                if (cumulativeRecharge == null) {
                    cumulativeRecharge = BigDecimal.ZERO;
                }
                // 汇直投投资金额
                BigDecimal hztTenderPrice = amAdminClient.getHztTenderPrice(sourceId, "pc");
                if (hztTenderPrice == null) {
                    hztTenderPrice = BigDecimal.ZERO;
                }
                // 汇消费投资金额
                BigDecimal hxfTenderPrice = amAdminClient.getHxfTenderPrice(sourceId, "pc");
                if (hxfTenderPrice == null) {
                    hxfTenderPrice = BigDecimal.ZERO;
                }
                // 汇天利投资金额
                BigDecimal htlTenderPrice = amAdminClient.getHtlTenderPrice(sourceId, "pc");
                if (htlTenderPrice == null) {
                    htlTenderPrice = BigDecimal.ZERO;
                }
                // 汇添金投资金额
                BigDecimal htjTenderPrice = amAdminClient.getHtjTenderPrice(sourceId, "pc");
                if (htjTenderPrice == null) {
                    htjTenderPrice = BigDecimal.ZERO;
                }
                // 汇金理财投资金额
                BigDecimal rtbTenderPrice = amAdminClient.getRtbTenderPrice(sourceId, "pc");
                if (rtbTenderPrice == null) {
                    rtbTenderPrice = BigDecimal.ZERO;
                }
                // 汇转让投资金额//
                BigDecimal hzrTenderPrice = amAdminClient.getHzrTenderPrice(sourceId, "pc");
                if (hzrTenderPrice == null) {
                    hzrTenderPrice = BigDecimal.ZERO;
                }
                PcChannelStatisticsVO statisticsVO = new PcChannelStatisticsVO(sourceId, vo.getSourceName(),
                        accessNumber, registNumber, openaccountnumber, tenderNumber, cumulativeRecharge, hztTenderPrice,
                        hxfTenderPrice, htlTenderPrice, htjTenderPrice, rtbTenderPrice, hzrTenderPrice, new Date());
                statisticsVO.setCumulativeInvestment(hztTenderPrice.add(hxfTenderPrice).add(htlTenderPrice)
                        .add(htjTenderPrice).add(rtbTenderPrice).add(hzrTenderPrice));
                logger.info("发送的PcChannelStatisticsVO=="+JSONObject.toJSONString(statisticsVO));
                try {
                    //  对应INSERT
                    commonProducer.messageSend(new MessageContent(MQConstant.PC_CHANNEL_STATISTICS_TOPIC,
                            System.currentTimeMillis() + "", statisticsVO));
                } catch (MQException e) {
                    logger.error("PC渠道统计数据定时任务出错......", e);
                }
            }
        }
    }
    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);

        logger.info("====PcChannelStatisticsAdminConsumer consumer=====");
    }


}
