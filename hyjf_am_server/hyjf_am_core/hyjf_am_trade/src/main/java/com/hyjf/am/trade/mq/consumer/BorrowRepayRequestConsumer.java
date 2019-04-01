/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.consumer.BatchBorrowRepayPlanService;
import com.hyjf.am.trade.service.front.consumer.BatchBorrowRepayZTService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
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
 * 所有标的的还款请求处理消费处理
 *
 * @author dxj
 * @version BorrowRepayRequestConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
@Service
@RocketMQMessageListener(topic = MQConstant.BORROW_REPAY_REQUEST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.BORROW_REPAY_REQUEST_GROUP)
public class BorrowRepayRequestConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(BorrowRepayRequestConsumer.class);


    @Autowired
    private BatchBorrowRepayPlanService batchBorrowRepayPlanService;

    @Autowired
    private BatchBorrowRepayZTService batchBorrowRepayZTService;

    @Autowired
    private SystemConfig systemConfig;

    @Autowired
    private CommonProducer commonProducer;

    @Override
    public void onMessage(MessageExt messageExt) {
        logger.info("【还款请求】收到消息，开始处理....");
        BorrowApicron borrowApicron = null;
        try{
            try {
                MessageExt msg = messageExt;
                borrowApicron = JSONObject.parseObject(msg.getBody(), BorrowApicron.class);
                if (borrowApicron == null || borrowApicron.getId() == null || borrowApicron.getBorrowNid() == null) {
                    logger.error("【还款请求】收到消息，信息不全！");
                    return;
                }
            } catch (Exception e) {
                logger.error("【还款请求】收到消息，解析异常！", e);
                return;
            }
            // 查询表确认是否真正的成功
            borrowApicron = batchBorrowRepayPlanService.selApiCronByPrimaryKey(borrowApicron.getId());
            Integer repayUserId = borrowApicron.getUserId();// 还款用户userId
            String borrowNid = borrowApicron.getBorrowNid();// 项目编号
            Integer periodNow = borrowApicron.getPeriodNow();// 当前期数
            // 生成任务key 校验并发请求
            String redisKey = RedisConstants.REPAY_REQUEST_TASK + borrowApicron.getPeriodNow();
            //验证请求参数
            if (Validator.isNull(repayUserId) || Validator.isNull(borrowNid)
                    || Validator.isNull(periodNow)) {
                logger.error("【还款请求】收到消息，信息不全！");
                return;
            }
            // 确认是否真的还款请求
    	    if(borrowApicron.getStatus().intValue() == 1 || borrowApicron.getStatus().intValue() == 7 ) {
    	        ;
    	    }else {
                logger.error("【还款请求】借款编号：{}，已经请求过还款，状态有误！", borrowNid);
                return;
    	    }
            boolean result = this.outRepeatQueue(redisKey, borrowApicron);//modify by cwyang 数据库事务后变更,不设时间,待所有流程结束后再去除防重标示
            if (result) {
                logger.error("【还款请求】借款编号：{}，还款请求中...", borrowNid);
                // 还款请求重复,需要判断是否为银行请求成功,平台处理失败的情况,分情况处理
                boolean flag = checkLoanRequestException(borrowApicron, redisKey);
                if (flag) {//存在请求异常情况,进行后续处理
                    boolean updateResult = updateLoanRequestException(borrowApicron);
                    if (updateResult) {//异常情况修复完成
                        // 去除防重标示
                        delRedisKey(borrowApicron);
                    } else {
                        logger.error("【还款请求】借款编号：{}，还款请求异常修复失败,请人为处理！", borrowNid);
                    }
                }
                return;
            }
            boolean delFlag = false;
            try {
                String planNid = borrowApicron.getPlanNid();
                Map map = null;
                // 全部发送还款请求
                if (StringUtils.isNotBlank(planNid)) {//计划还款请求
                    logger.info("【还款请求】智投编号：{}，借款编号：{}，开始请求还款。当前状态：{}", planNid, borrowNid, borrowApicron.getStatus());
                    map = batchBorrowRepayPlanService.requestRepay(borrowApicron);
                } else {//直投还款请求
                    logger.info("【还款请求】借款编号：{}，开始请求还款。当前状态：{}", borrowNid, borrowApicron.getStatus());
                    map = batchBorrowRepayZTService.requestRepay(borrowApicron);
                }
                boolean requestLoanFlag = (boolean) map.get("result");
                delFlag = (boolean) map.get("delFlag");
                if (!requestLoanFlag) {
                    try {
                        // 更新任务API状态
                        batchBorrowRepayZTService.updateBorrowApicron(borrowApicron, CustomConstants.BANK_BATCH_STATUS_SEND_FAIL);
                    } catch (Exception e) {
                        delFlag = true;
                        throw new Exception("批次还款任务表(ht_borrow_apicron)更新还款状态(请求失败)失败，[借款编号:" + borrowNid + "]");
                    }
                }
                if (!delFlag) {
                    delRedisKey(borrowApicron);
                }
            } catch (Exception e) {
                logger.error("【还款请求】还款请求时系统异常！", e);
                StringBuffer sbError = new StringBuffer();// 错误信息
                sbError.append(e.getMessage()).append("<br/>");
                String online = "生产环境";// 取得是否线上
                String toMail[] = systemConfig.getLoadRepayMailAddrs();
                if (systemConfig.isEnvTest()) {
                    online = "测试环境";
                }
                // 发送错误邮件
                StringBuffer msg = new StringBuffer();
                msg.append("借款标号：").append(borrowApicron.getBorrowNid()).append("<br/>");
                msg.append("还款时间：").append(GetDate.formatTime()).append("<br/>");
                msg.append("错误信息：").append(e.getMessage()).append("<br/>");
                msg.append("详细错误信息：<br/>").append(sbError.toString());
                try {
                    if (toMail == null) {
                        throw new Exception("未配置收件人！[借款编号：" + borrowNid + "]");
                    }
                    MailMessage mailMessage = new MailMessage(null, null, "[" + online + "] " + borrowApicron.getBorrowNid(), msg.toString(), null, toMail, null,
                            MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                    commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, borrowApicron.getBorrowNid(), mailMessage));
                } catch (Exception e2) {
                    logger.error("【还款请求】发送邮件失败..", e2);
                }
                if (!delFlag) {
                    delRedisKey(borrowApicron);
                }
            }
            logger.info("【还款请求】借款编号：{}，还款请求结束！", borrowNid);
            // 如果没有return success ，consumer会重新消费该消息，直到return success
        } catch (Exception e) {
            logger.error("【还款请求】消费异常!", e);
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
        defaultMQPushConsumer.setConsumeTimeout(15);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====计划直投还款请求消费启动=====");
    }

    /**
     * 删除队列防重key
     *
     * @param borrowApicron
     */
    private void delRedisKey(BorrowApicron borrowApicron) {
        String key = RedisConstants.REPAY_REQUEST_TASK + borrowApicron.getPeriodNow();
        String value = borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow();
        Long srem = RedisUtils.srem(key, value);
        logger.info("【还款请求】借款编号：{}，还款请求正常完成，删除队列防重标示：{}", borrowApicron.getBorrowNid(), srem);
    }

    /**
     * 处理银行成功,平台处理失败的情况
     *
     * @param borrowApicron
     * @return
     */
    private boolean updateLoanRequestException(BorrowApicron borrowApicron) {
        //  处理还款请求异常的问题
        String borrowNid = borrowApicron.getBorrowNid();
        logger.info("【还款请求】借款编号：{}，开始处理还款请求异常。", borrowNid);
        try {
            // 因请求成功需要加日志，此处直接改为校验成功 update by wgx 2019/04/01
            boolean apicronResultFlag = batchBorrowRepayZTService.updateBorrowApicron(borrowApicron, CustomConstants.BANK_BATCH_STATUS_VERIFY_SUCCESS);
            if (apicronResultFlag) {
                logger.info("【还款请求】借款编号：{}，处理还款请求异常成功,还款请求结果为校验成功。", borrowNid);
                return true;
            } else {
                throw new Exception("更新还款任务状态(还款请求成功)失败！[借款编号：" + borrowNid + "]");
            }
        } catch (Exception e) {
            logger.error("【还款请求】处理还款请求异常时发生系统异常！", e);
        }
        return false;
    }

    /**
     * 校验是否存在银行请求成功,平台处理失败的情况
     *
     * @param borrowApicron
     */
    private boolean checkLoanRequestException(BorrowApicron borrowApicron, String redisKey) {
        //  校验是否存在还款请求异常的问题
        //判断批次号是否为空
        String batchNo = borrowApicron.getBatchNo();
        String borrowNid = borrowApicron.getBorrowNid();
        String bankSeqNo = borrowApicron.getBankSeqNo();// 还款序列号
        logger.info("【还款请求】借款编号：{}，开始校验是否存在请求异常的情况。批次号:{}", borrowNid, batchNo);
        try {
            if (StringUtils.isNotBlank(batchNo)) {
                BankCallBean batchResult = batchBorrowRepayZTService.batchQuery(borrowApicron);
                if (Validator.isNull(batchResult)) {
                    throw new Exception("还款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
                }
                logger.info("【还款请求】借款编号：{}，校验请求异常时批次查询成功！", borrowNid);
                // 批次还款返回码
                String retCode = StringUtils.isNotBlank(batchResult.getRetCode()) ? batchResult.getRetCode() : "";
                if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
                    String retMsg = batchResult.getRetMsg();
                    // 批次号不存在则删除标示,重新发起申请
                    if (BankCallConstant.RESPCODE_BATCHNO_NOTEXIST.equals(retCode)) {
                        delRedisKey(borrowApicron);
                    }
                    throw new Exception("还款状态查询失败！[银行返回信息：" + retMsg + "]，[返回码：" + retCode + "]，[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
                }
                // 批次还款状态
                String batchState = batchResult.getBatchState();
                if (StringUtils.isBlank(batchState)) {
                    throw new Exception("还款状态查询失败！[银行唯一订单号：" + bankSeqNo + "]，[借款编号：" + borrowNid + "]");
                }
                logger.info("【还款请求】借款编号：{}，存在还款请求异常情况,已经发起过还款请求,需要进行异常处理！批次查询状态：{}", borrowNid, batchState);
                return true;
            } else {
                delRedisKey(borrowApicron);
            }
        } catch (Exception e) {
            logger.error("【还款请求】借款编号：{}，还款请求异常处理校验异常！", borrowNid, e);
        }
        return false;
    }

    /**
     * 校验一个队列中的重复任务
     *
     * @param key
     * @param apicron
     */
    private boolean outRepeatQueue(String key, BorrowApicron apicron) {
        String value = apicron.getBorrowNid() + "_" + apicron.getPeriodNow();
        // 通过判断redis中是否存在该项目编号 从而判断该项目编号是否已经在消息队列中，防止重复放款
        if (RedisUtils.sismember(key, value)) {
            return true;
        }
        // 将新的待放款的项目编号放入redis
        RedisUtils.sadd(key, value);
        return false;
    }
}
