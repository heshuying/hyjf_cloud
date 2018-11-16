/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.message.OperationReportJobBean;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.message.bean.ic.PcChannelStatistics;
import com.hyjf.cs.message.mq.base.Consumer;
import com.hyjf.cs.message.service.report.OperationReportJobNewService;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tyy
 * @version OperationReportJobConsumer, v0.1 2018/7/2 10:11
 */
@Component
public class OperationReportJobConsumer extends Consumer {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private OperationReportJobNewService operationReportJobNewService;
    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.OPERATIONREPORT_JOB_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.OPERATIONREPORT_JOB_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new OperationReportJobConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====OperationReportJobConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            logger.info("OperationReportJobConsumer 收到消息，开始处理....msgs is :{}", msgs);
            for (MessageExt msg : msgs) {
                OperationReportJobBean bean = JSONObject.parseObject(msg.getBody(), OperationReportJobBean.class);
                //查询在admin做成功处理逻辑
                if("success".equals(bean.getStatus())){
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

                    // 插入投资类的信息
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
                        Calendar cal =  bean.getCalendar();
                        String year = String.valueOf(cal.get(Calendar.YEAR));
                        String month = "";
                        int monthnow = cal.get(Calendar.MONTH) + 1;
                        if (monthnow < 10) {
                            month =  "0" + String.valueOf(monthnow);
                        } else {
                            month =  String.valueOf(monthnow);
                        }
                        //输出上个月的日期
                        int lastMonth = getLastMonth(cal);

                        //每个月月初的1号，自动统计出上一个月的数据，统计顺序依次是：
                        //1月，2月，第一季度，4月，5月，上半年，7月，8月，第三季度，10月，11月，年度报告
                        if(lastMonth == 12){
                            operationReportJobNewService.setYearReport(year,month,bean);
                        } else if(lastMonth == 6 ){
                            operationReportJobNewService.setHalfYearReport(year,month,bean);
                        }else if(lastMonth == 3 || lastMonth == 9 ){
                            operationReportJobNewService.setQuarterReport(year,month,bean);
                        }else{
                            operationReportJobNewService.setMonthReport(year,month,bean);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    RedisUtils.del(RedisConstants.Statistics_Operation_Report);





                }
            }


            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    /**
     * 获得当前月份的上个月日期
     * @return
     */
    public static int getLastMonth(Calendar calendar){
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        calendar.setTime(new Date());//设置当前日期
        calendar.add(Calendar.MONTH, -1);//月份减一
        //输出上个月的日期
        int lastMonth = Integer.valueOf(sdf.format( calendar.getTime()));
        return lastMonth;
    }
}
