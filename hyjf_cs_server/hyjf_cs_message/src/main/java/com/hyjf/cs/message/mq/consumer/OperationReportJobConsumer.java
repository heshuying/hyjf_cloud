/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.OperationReportJobBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.message.service.report.OperationReportJobNewService;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author tyy
 * @version OperationReportJobConsumer, v0.1 2018/7/2 10:11
 */
@Service
@RocketMQMessageListener(topic = MQConstant.OPERATIONREPORT_JOB_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.OPERATIONREPORT_JOB_GROUP)
public class OperationReportJobConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    Logger logger = LoggerFactory.getLogger(getClass());

    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private OperationReportJobNewService operationReportJobNewService;

    @Override
    public void onMessage(MessageExt message) {
        logger.info("OperationReportJobConsumer 收到消息，开始处理....msgs is :{}", new String(message.getBody()));
        MessageExt msg = message;
        OperationReportJobBean bean = JSONObject.parseObject(msg.getBody(), OperationReportJobBean.class);
        //查询在admin做成功处理逻辑
        if ("success".equals(bean.getStatus())) {
            try {
                // 插入性别，性别 ，区域的统计信息
                operationReportJobNewService.insertOperationGroupData(bean);
            } catch (org.springframework.dao.DuplicateKeyException e) {
                logger.info("重复插入，可忽略");
            } catch (com.mongodb.DuplicateKeyException e) {
                logger.info("重复插入，可忽略");
            } catch (Exception e) {
                logger.info("重复插入，可忽略");
            }

            // 插入出借类的信息
            try {
                operationReportJobNewService.insertOperationData(bean);
            } catch (org.springframework.dao.DuplicateKeyException e) {
                logger.info("重复插入，可忽略");
            } catch (com.mongodb.DuplicateKeyException e) {
                logger.info("重复插入，可忽略");
            } catch (Exception e) {
                logger.info("重复插入，可忽略");
            }

            // 插入前台界面的运营报告(月，季，半年，全年)
            try {
                String year = bean.getYear();
                String month = bean.getMonth();
                //输出上个月的日期
                int lastMonth = bean.getLastMonth();
                logger.info("生成报告year=" + year + "生成报告month=" + month);
                //每个月月初的1号，自动统计出上一个月的数据，统计顺序依次是：
                //1月，2月，第一季度，4月，5月，上半年，7月，8月，第三季度，10月，11月，年度报告
                if (lastMonth == 12) {
                    operationReportJobNewService.setYearReport(year, month, bean);
                } else if (lastMonth == 6) {
                    operationReportJobNewService.setHalfYearReport(year, month, bean);
                } else if (lastMonth == 3 || lastMonth == 9) {
                    operationReportJobNewService.setQuarterReport(year, month, bean);
                } else {
                    operationReportJobNewService.setMonthReport(year, month, bean);
                }

            } catch (Exception e) {
                logger.error(e.getMessage());
            }

            RedisUtils.del(RedisConstants.Statistics_Operation_Report);
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
        logger.info("====OperationReportJobConsumer consumer=====");
    }


    /**
     * 获得当前月份的上个月日期
     *
     * @return
     */
    public static int getLastMonth(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        //输出上个月的日期
        int lastMonth = Integer.valueOf(sdf.format(calendar.getTime()));
        return lastMonth;
    }
}
