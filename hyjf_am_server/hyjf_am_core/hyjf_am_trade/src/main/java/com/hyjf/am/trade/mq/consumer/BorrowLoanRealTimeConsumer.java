/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.consumer.RealTimeBorrowLoanService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import org.apache.commons.lang.StringUtils;
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

import java.util.UUID;

/**
 * 自投类标的放款消费处理
 *
 * @author dxj
 * @version BorrowLoanRealTimeConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
@Service
@RocketMQMessageListener(topic = MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.BORROW_REALTIMELOAN_ZT_REQUEST_GROUP)
public class BorrowLoanRealTimeConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(BorrowLoanRealTimeConsumer.class);

    @Autowired
    private RealTimeBorrowLoanService realTimeBorrowLoanService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("直投放款请求 收到消息，开始处理....");
        BorrowApicron borrowApicron;
        try{
            try {
                MessageExt msgD = messageExt;
                borrowApicron = JSONObject.parseObject(msgD.getBody(), BorrowApicron.class);
                if (borrowApicron == null || borrowApicron.getId() == null || borrowApicron.getBorrowNid() == null
                        || StringUtils.isNotEmpty(borrowApicron.getPlanNid())) {
                    logger.info(" 直投放款异常消息：" + msgD.getMsgId());
                    return;
                }
            } catch (Exception e1) {
                logger.error(e1.getMessage());
                return;
            }
            String borrowNid = borrowApicron.getBorrowNid();// 借款编号
            // int borrowUserId = borrowApicron.getUserId();// 借款人userId
            Integer failTimes = borrowApicron.getFailCounts();
            // 生成任务key 校验并发请求
            String redisKey = RedisConstants.ZHITOU_LOAN_TASK + ":" + borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow();

            try {
                logger.info("标的编号：" + borrowNid + "，开始实时放款！");
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if (!result) {
                    logger.error("直投类放款请求中....");
                    return;
                }
                borrowApicron = realTimeBorrowLoanService.selApiCronByPrimaryKey(borrowApicron.getId());
                // 如果放款状态为请求中
                if (borrowApicron.getStatus().equals(CustomConstants.BANK_BATCH_STATUS_SENDING)) {
                    // 发送放款
                    BankCallBean requestLoanBean = realTimeBorrowLoanService.requestLoans(borrowApicron);
                    if (requestLoanBean == null) {
                        borrowApicron.setFailTimes(borrowApicron.getFailTimes() + 1);
                        // 放款失败处理
                        boolean batchDetailFlag = realTimeBorrowLoanService.loanBatchUpdateDetails(borrowApicron, requestLoanBean);
                        if (!batchDetailFlag) {
                            throw new Exception("放款成功后，变更放款数据失败。" + "[借款编号：" + borrowNid + "]");
                        }
                        // 不需要以下代码了
    //						boolean apicronResultFlag = realTimeBorrowLoanService.updateBorrowApicron(borrowApicron,CustomConstants.BANK_BATCH_STATUS_FAIL);
    //						if (apicronResultFlag) {
    //							throw new Exception(
    //									"更新状态为（放款请求失败）失败。[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
    //						} else {
    //							throw new Exception("放款失败,[用户ID：" + borrowUserId + "]," + "[借款编号：" + borrowNid + "]");
    //						}
                    } else {// 放款成功
                        // 进行后续操作
                        boolean batchDetailFlag = realTimeBorrowLoanService.loanBatchUpdateDetails(borrowApicron, requestLoanBean);
                        if (!batchDetailFlag) {
                            throw new Exception("放款成功后，变更放款数据失败。" + "[借款编号：" + borrowNid + "]");
                        }
                        // 放款成功,更新mongo运营数据
                        logger.info("放款成功更新运营数据...");
                        sendMQ(borrowApicron);

                        // add 合规数据上报 埋点 liubin 20181122 start
                        // 推送数据到MQ 放款成功
                        JSONObject params = new JSONObject();
                        params.put("borrowNid",borrowNid);
                        commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.LOAN_SUCCESS_TAG, UUID.randomUUID().toString(), params),
                                MQConstant.HG_REPORT_DELAY_LEVEL);
                        // add 合规数据上报 埋点 liubin 20181122 end
                    }
                    //
                } else if (borrowApicron.getStatus().equals(CustomConstants.BANK_BATCH_STATUS_SENDED)) {
                    // 发送放款
                    BankCallBean requestLoanBean = new BankCallBean();
                    boolean batchDetailFlag = realTimeBorrowLoanService.loanBatchUpdateDetails(borrowApicron, requestLoanBean);
                    if (!batchDetailFlag) {
                        throw new Exception("放款成功后，变更放款数据失败。" + "[借款编号：" + borrowNid + "]");
                    }
                    // 放款成功,更新mongo运营数据
                    logger.info("再次更新放款 运营数据...");
                    sendMQ(borrowApicron);
                } else {
                    logger.error("标的编号：" + borrowNid + "，不是放款状态 " + borrowApicron.getStatus());
                }

            } catch (Exception e) {
                logger.error("散标放款系统异常", e);
                StringBuffer sbError = new StringBuffer();// 错误信息
                sbError.append(e.getMessage()).append("<br/>");
                String online = "生产环境";// 取得是否线上
                String toMail[] = systemConfig.getLoadRepayMailAddrs();
                if (systemConfig.isEnvTest()) {
                    online = "测试环境";
                }
                // 发送错误邮件
                StringBuffer msg = new StringBuffer();
                msg.append("借款标号：").append(borrowNid).append("<br/>");
                msg.append("放款时间：").append(GetDate.formatTime()).append("<br/>");
                msg.append("执行次数：").append("第" + failTimes + "次").append("<br/>");
                msg.append("错误信息：").append(e.getMessage()).append("<br/>");
                msg.append("详细错误信息：<br/>").append(sbError.toString());

                try {
                    if (toMail == null) {
                        throw new Exception("错误收件人没有配置。" + "[借款编号：" + borrowNid + "]");
                    }
                    MailMessage mailmessage = new MailMessage(null, null,
                            "[" + online + "] " + borrowNid + " 第" + failTimes + "次放款失败", msg.toString(), null, toMail,
                            null, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                    commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, borrowNid, mailmessage));
                } catch (Exception e2) {
                    logger.error("发送邮件失败..", e2);
                }
            }
            logger.info("----------------------放款任务结束，项目编号：" + borrowNid + "=============");
            RedisUtils.del(redisKey);
            logger.info("----------------------------直投放款结束--------------------------------");
        } catch (Exception e) {
            logger.error("【直投放款】消费异常!", e);
            return;
        }
        return;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        // 设置并发数
        defaultMQPushConsumer.setConsumeThreadMin(1);
        defaultMQPushConsumer.setConsumeThreadMax(1);
        defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
        defaultMQPushConsumer.setConsumeTimeout(30);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====散标放款业务消费启动=====");
    }

    /**
     * 推送放款成功消息
     *
     * @param borrowApicron
     */
    private void sendMQ(BorrowApicron borrowApicron) {
        JSONObject params = new JSONObject();
        // 散标
        params.put("type", 1);
        params.put("money", borrowApicron.getBorrowAccount());
        //运营数据队列
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
            logger.error("发送运营数据更新MQ失败,放款标的:" + borrowApicron.getBorrowNid());
        }
        // 发送mq到生成互金合同要素信息
        try {
            JSONObject param = new JSONObject();
            param.put("borrowNid", borrowApicron.getBorrowNid());
            commonProducer.messageSendDelay(new MessageContent(MQConstant.CONTRACT_ESSENCE_TOPIC, UUID.randomUUID().toString(), param), 2);
        } catch (Exception e) {
            logger.error("发送mq到生成互金合同要素信息失败,放款标的:" + borrowApicron.getBorrowNid());
        }
    }
}
