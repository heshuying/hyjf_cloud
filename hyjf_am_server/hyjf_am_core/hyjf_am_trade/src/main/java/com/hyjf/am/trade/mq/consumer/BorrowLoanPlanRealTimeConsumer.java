/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.config.SystemConfig;
import com.hyjf.am.trade.dao.model.auto.BorrowApicron;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.front.consumer.RealTimeBorrowLoanPlanService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
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
 * 计划类标的放款消费处理
 * @author dxj
 * @version BorrowLoanPlanRealTimeConsumer.java, v0.1 2018年6月20日 下午6:09:19
 */
@Service
@RocketMQMessageListener(topic = MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.BORROW_REALTIMELOAN_PLAN_REQUEST_GROUP)
public class BorrowLoanPlanRealTimeConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private static final Logger logger = LoggerFactory.getLogger(BorrowLoanPlanRealTimeConsumer.class);

    @Autowired
    private RealTimeBorrowLoanPlanService realTimeBorrowLoanPlanService;

    @Autowired
    private SystemConfig systemConfig;

	@Autowired
	private CommonProducer commonProducer;

	@Override
	public void onMessage(MessageExt messageExt) {
		logger.info("计划放款请求 收到消息，开始处理....");
		BorrowApicron borrowApicron;
		try{
            MessageExt msgD = messageExt;
            borrowApicron = JSONObject.parseObject(msgD.getBody(), BorrowApicron.class);
            if(borrowApicron == null || borrowApicron.getId() == null || borrowApicron.getBorrowNid() == null
                    || StringUtils.isEmpty(borrowApicron.getPlanNid()) ){
                logger.info(" 智投放款异常消息：" + msgD.getMsgId());
                return;
            }
            // 借款编号
            String borrowNid = borrowApicron.getBorrowNid();
            Integer failTimes = borrowApicron.getFailTimes();
            // 生成任务key 校验并发请求
            String redisKey = RedisConstants.ZHITOU_LOAN_TASK + ":" + borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow();

            try {
                logger.info("标的编号："+borrowNid+"，开始实时放款！");
                boolean result = RedisUtils.tranactionSet(redisKey, 300);
                if(!result){
                    logger.error("智投类放款请求中....");
                    return;
                }

                borrowApicron = realTimeBorrowLoanPlanService.selApiCronByPrimaryKey(borrowApicron.getId());
                //放款状态
                int loanStatus = borrowApicron.getStatus();
                // 如果放款状态为请求中
                if (loanStatus == CustomConstants.BANK_BATCH_STATUS_SENDING ) {
                    //调用银行实时放款接口进行放款
                    BankCallBean requestLoanBean = realTimeBorrowLoanPlanService.requestLoans(borrowApicron);
                    if (requestLoanBean == null) {
                        //调用银行实时放款接口失败只打印日志，在requestLoans方法中已经更新borrow_apicron表的状态了
                        logger.error("智投标的：" + borrowNid + "调用银行实时放款接口失败！");
                    }else{
                        //放款成功 更新业务数据 此处不开事务
                        realTimeBorrowLoanPlanService.updWhenPlanLoanSuccessed(borrowApicron);
                    }
                } else if(loanStatus == CustomConstants.BANK_BATCH_STATUS_SENDED) {
                    //自动修复出现异常的数据
                    realTimeBorrowLoanPlanService.updWhenPlanLoanSuccessed(borrowApicron);
                } else {
                    logger.error("智投标的编号：" + borrowNid + "，不是放款状态 "+ borrowApicron.getStatus());
                }

                // 重新获取borrowApicron数据，根据状态判断是否发送互金等埋点MQ
                borrowApicron = realTimeBorrowLoanPlanService.selApiCronByPrimaryKey(borrowApicron.getId());
                //放款成功，并且所有数据(出借人，借款人)都更新成功
                if(borrowApicron.getStatus().equals(CustomConstants.BANK_BATCH_STATUS_SUCCESS)){
                    logger.info("智投标的:" + borrowNid + "放款成功，发送相关MQ");
                    sendMQ(borrowApicron);
                }

            } catch (Exception e) {
                logger.error("智投放款系统异常", e);
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
                    if(toMail == null) {
                        throw new Exception("错误收件人没有配置。" + "[借款编号：" + borrowNid + "]");
                    }
                    MailMessage mailmessage = new MailMessage(null, null, "[" + online + "] " + borrowNid + " 第" + failTimes + "次放款失败", msg.toString(), null, toMail, null,
                            MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS_MSG);
                    commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, borrowNid, mailmessage));
                } catch (Exception e2) {
                    logger.error("发送邮件失败..", e2);
                }
                // 消息队列指令不消费
                logger.error("放款请求系统异常....");
                return;
            }
            logger.info("----------------智投放款任务结束，项目编号：" + borrowNid + "=============");
            RedisUtils.del(redisKey);
            logger.info("---------------------智投放款结束--------------------------------");
        } catch (Exception e) {
            logger.error("【智投放款】消费异常!", e);
            return;
        }
		return;
	}

	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		// 设置并发数
		defaultMQPushConsumer.setConsumeThreadMin(1);
		defaultMQPushConsumer.setConsumeThreadMax(1);
		defaultMQPushConsumer.setConsumeMessageBatchMaxSize(1);
		defaultMQPushConsumer.setConsumeTimeout(30);
		logger.info("====计划放款业务消费启动=====");
	}

	/**
	 * 推送放款成功消息
	 * @param borrowApicron
	 */
	private void sendMQ(BorrowApicron borrowApicron) {
        JSONObject param = new JSONObject();
//		try {
//			param.put("borrowNid", borrowApicron.getBorrowNid());
//			commonProducer.messageSendDelay(new MessageContent(MQConstant.CONTRACT_ESSENCE_TOPIC,UUID.randomUUID().toString(),param),2);
//		} catch (Exception e) {
//			logger.error("发送mq到生成互金合同要素信息失败,放款标的:" + borrowApicron.getBorrowNid());
//		}

		try {
            // add 合规数据上报 埋点 liubin 20181122 start
            // 推送数据到MQ 放款成功
            param = new JSONObject();
            param.put("borrowNid",borrowApicron.getBorrowNid());
            commonProducer.messageSendDelay2(new MessageContent(MQConstant.HYJF_TOPIC, MQConstant.LOAN_SUCCESS_TAG, UUID.randomUUID().toString(), param),
                    MQConstant.HG_REPORT_DELAY_LEVEL);
            // add 合规数据上报 埋点 liubin 20181122 end
        } catch (Exception e){
            logger.error("发送合规数据上报MQ失败,放款标的:" + borrowApicron.getBorrowNid());
        }
	}
}
