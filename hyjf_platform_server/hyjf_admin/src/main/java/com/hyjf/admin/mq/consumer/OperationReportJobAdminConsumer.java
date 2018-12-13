/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.client.AmAdminClient;
import com.hyjf.admin.mq.base.CommonProducer;
import com.hyjf.admin.mq.base.Consumer;
import com.hyjf.admin.mq.base.MessageContent;
import com.hyjf.am.vo.message.OperationReportJobBean;
import com.hyjf.am.vo.trade.OperationReportJobVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.exception.MQException;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author tyy
 * @version OperationReportJobAdminConsumer, v0.1 2018/7/2 10:11
 */
@Component
public class OperationReportJobAdminConsumer extends Consumer {
    Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private AmAdminClient amAdminClient;
    @Autowired
    private CommonProducer commonProducer;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.OPERATIONREPORT_JOB_ADMIN_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.OPERATIONREPORT_JOB_ADMIN_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new OperationReportJobAdminConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
        logger.info("====OperationReportJobAdminConsumer consumer=====");
    }

    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
            for (MessageExt msg : msgs) {
                OperationReportJobBean bean = JSONObject.parseObject(msg.getBody(), OperationReportJobBean.class);
                logger.info("====OperationReportJobAdminConsumer consumeMessage=====" + JSONObject.toJSONString(bean));
                int lastMonth = bean.getLastMonth();
                Calendar cal = bean.getCalendar();
                List<OperationReportJobVO> cityGroup = amAdminClient.getTenderCityGroupByList(getLastDay(cal));
                bean.setCityGroup(cityGroup);
                List<OperationReportJobVO> sexGroup = amAdminClient.getTenderSexGroupByList(getLastDay(cal));
                bean.setSexGroup(sexGroup);
                List<OperationReportJobVO> ageRangeUserIds = amAdminClient.getTenderAgeByRangeList(getLastDay(cal));
                bean.setAgeRangeUserIds(ageRangeUserIds);
                // 月交易金额
                bean.setAccountMonth(amAdminClient.getAccountByMonth(getFirstDay(cal), getLastDay(cal)));
                // 月交易笔数
                bean.setTradeCountMonth(amAdminClient.getTradeCountByMonth(getFirstDay(cal), getLastDay(cal)));
                //借贷笔数
                bean.setLoanNum(amAdminClient.getLoanNum(getLastDay(cal)));
                //人均投资金额
                double bb = amAdminClient.getInvestLastDate(getLastDay(cal));
                int aa = amAdminClient.getTenderCount(getLastDay(cal));
                bean.setPerInvest((int) Math.ceil(bb / aa));
                //平均满标时间
                bean.setFullBillTimeCurrentMonth(amAdminClient.getFullBillAverageTime(getLastDay(cal)));
                //投资人总数
                bean.setTenderCount(aa);
                //代偿金额
                bean.setWillPayMoney(amAdminClient.getRepayTotal(getLastDay(cal)));
                //业绩总览
                bean.setListPerformanceSum(amAdminClient.getPerformanceSum());
                if (lastMonth == 12) {
                    // 全年度12个月成交金额
                    bean.setListMonthDealMoney(amAdminClient.getMonthDealMoney(0, 12));
                    //全年赚取收益,去年全年赚取收益,全年平均年率
                    bean.setListOperationReportInfoCustomize(amAdminClient.getRevenueAndYield(12, 12, 24));
                    //充值金额、充值笔数
                    bean.setListRechargeMoneyAndSum(amAdminClient.getRechargeMoneyAndSum(12));
                    //渠道分析
                    bean.setListCompleteCount(amAdminClient.getCompleteCount(12));
                    //性别分布
                    bean.setListSexDistribute(amAdminClient.getSexDistribute(12));
                    //年龄分布
                    bean.setListAgeDistribute(amAdminClient.getAgeDistribute(12));
                    //金额分布
                    bean.setListMoneyDistribute(amAdminClient.getMoneyDistribute(12));
                    //计算 十大投资人投资金额
                    bean.setListTenMostMoney(amAdminClient.getTenMostMoney(12));
                    //大赢家，收益最高
                    bean.setListOneInterestsMost(amAdminClient.getOneInterestsMost(12));
                    //超活跃，投资笔数最多
                    bean.setListtOneInvestMost(amAdminClient.getOneInvestMost(12));
                    bean.setListBorrowPeriod(amAdminClient.getBorrowPeriod(12));
                } else if (lastMonth == 6) {
                    bean.setListMonthDealMoney(amAdminClient.getMonthDealMoney(0, 6));
                    bean.setListOperationReportInfoCustomize(amAdminClient.getRevenueAndYield(6, 12, 18));
                    //充值金额、充值笔数
                    bean.setListRechargeMoneyAndSum(amAdminClient.getRechargeMoneyAndSum(6));
                    //渠道分析
                    bean.setListCompleteCount(amAdminClient.getCompleteCount(6));
                    //性别分布
                    bean.setListSexDistribute(amAdminClient.getSexDistribute(6));
                    //年龄分布
                    bean.setListAgeDistribute(amAdminClient.getAgeDistribute(6));
                    //金额分布
                    bean.setListMoneyDistribute(amAdminClient.getMoneyDistribute(6));
                    //计算 十大投资人投资金额
                    bean.setListTenMostMoney(amAdminClient.getTenMostMoney(6));
                    //大赢家，收益最高
                    bean.setListOneInterestsMost(amAdminClient.getOneInterestsMost(6));
                    //超活跃，投资笔数最多
                    bean.setListtOneInvestMost(amAdminClient.getOneInvestMost(6));

                    bean.setListBorrowPeriod(amAdminClient.getBorrowPeriod(6));

                } else if (lastMonth == 3 || lastMonth == 9) {
                    bean.setListMonthDealMoney(amAdminClient.getMonthDealMoney(0, 3));
                    bean.setListLastMonthDealMoney(amAdminClient.getMonthDealMoney(12, 15));
                    bean.setListOperationReportInfoCustomize(amAdminClient.getRevenueAndYield(3, 12, 15));
                    //充值金额、充值笔数
                    bean.setListRechargeMoneyAndSum(amAdminClient.getRechargeMoneyAndSum(3));
                    //渠道分析
                    bean.setListCompleteCount(amAdminClient.getCompleteCount(3));
                    //性别分布
                    bean.setListSexDistribute(amAdminClient.getSexDistribute(3));
                    //年龄分布
                    bean.setListAgeDistribute(amAdminClient.getAgeDistribute(3));
                    //金额分布
                    bean.setListMoneyDistribute(amAdminClient.getMoneyDistribute(3));
                    //计算 十大投资人投资金额
                    bean.setListTenMostMoney(amAdminClient.getTenMostMoney(3));
                    //大赢家，收益最高
                    bean.setListOneInterestsMost(amAdminClient.getOneInterestsMost(3));
                    //超活跃，投资笔数最多
                    bean.setListtOneInvestMost(amAdminClient.getOneInvestMost(3));
                } else {
                    bean.setListMonthDealMoney(amAdminClient.getMonthDealMoney(0, 1));
                    bean.setListLastMonthDealMoney(amAdminClient.getMonthDealMoney(12, 13));
                    bean.setListOperationReportInfoCustomize(amAdminClient.getRevenueAndYield(1, 12, 13));
                    //充值金额、充值笔数
                    bean.setListRechargeMoneyAndSum(amAdminClient.getRechargeMoneyAndSum(1));
                    //渠道分析
                    bean.setListCompleteCount(amAdminClient.getCompleteCount(1));
                    //性别分布
                    bean.setListSexDistribute(amAdminClient.getSexDistribute(1));
                    //年龄分布
                    bean.setListAgeDistribute(amAdminClient.getAgeDistribute(1));
                    //金额分布
                    bean.setListMoneyDistribute(amAdminClient.getMoneyDistribute(1));
                    //计算 十大投资人投资金额
                    bean.setListTenMostMoney(amAdminClient.getTenMostMoney(1));
                    //大赢家，收益最高
                    bean.setListOneInterestsMost(amAdminClient.getOneInterestsMost(1));
                    //超活跃，投资笔数最多
                    bean.setListtOneInvestMost(amAdminClient.getOneInvestMost(1));
                }


                try {
                    //成功
                    bean.setStatus("success");
                    commonProducer.messageSend(new MessageContent(MQConstant.OPERATIONREPORT_JOB_TOPIC,
                            System.currentTimeMillis() + "", bean));
                } catch (MQException e) {
                    logger.error("运营报告的mq发送失败......", e);
                }
            }
            // 如果没有return success ，consumer会重新消费该消息，直到return success
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
    }

    /**
     * 通过输入的日期，获取这个日期所在月份的最后一天
     *
     * @param cal
     * @return
     */
    public static Date getLastDay(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }


    /**
     * 通过输入的日期，获取这个日期所在月的第一天
     *
     * @param cal
     * @return
     */
    public static Date getFirstDay(Calendar cal) {
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }
}
