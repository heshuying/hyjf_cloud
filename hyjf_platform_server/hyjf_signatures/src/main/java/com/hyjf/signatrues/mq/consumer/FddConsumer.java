package com.hyjf.signatrues.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.fdd.FddDessenesitizationBean;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.signatrues.mq.handle.FddHandle;
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

/**
 * 法大大consumer
 * @author jijun
 * @version 20180627
 */

@Service
@RocketMQMessageListener(topic = MQConstant.FDD_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.FDD_GROUP)
public class FddConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

	private static final Logger logger = LoggerFactory.getLogger(FddConsumer.class);

	@Autowired
	private FddHandle fddHandle;
	private static int  MAX_RECONSUME_TIME=3;
	@Override
	public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		// MQ默认集群消费
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		//设置线程数量
		defaultMQPushConsumer.setConsumeThreadMin(1);

		defaultMQPushConsumer.setConsumeThreadMax(1);

		//start update by jijun 20180329
		//不设置最大重试次数,系统默认重复次数 1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h
		//defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);
		//end update by jijun 20180329
	}
	//MessageListenerConcurrently 并行消费 效率高 消费不追求时间顺序
	//MessageListenerOrderly 串行消费 效率不高    消费追求时间顺序
	@Override
	public void onMessage(MessageExt msg) {
		logger.info("----------------------法大大收到消息，开始处理...."+ msg.getKeys() +",重复消费次数："+msg.getReconsumeTimes());
		if (MQConstant.FDD_GENERATE_CONTRACT_TAG.equals(msg.getTags())) {
			// 生成合同
			logger.info("----------------------开始生成法大大合同------------------------");
			// 订单号
			String orderId = null;
			// 交易类型 1:散标 2：计划加入 3：直投债转 4：计划债转
			Integer transType = null;
			try {
				FddGenerateContractBean bean = JSONObject.parseObject(msg.getBody(),
						FddGenerateContractBean.class);
				if (bean==null) {
					logger.info("传入参数不得为空！");
					return;
				}
				orderId = bean.getOrdid();
				transType = bean.getTransType();
				if (Validator.isNull(orderId) && Validator.isNull(transType)) {
					logger.info("传入参数不得为空！");
					return;
				}
				if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType) {// 散标投资
					fddHandle.tenderGenerateContract(bean);
				} else if (FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType) {// 计划加入
					fddHandle.planJoinGenerateContract(bean);
				} else if (FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType) {// 债转投资
					fddHandle.creditGenerateContract(bean);
				} else if (FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType) {// 计划债转
					fddHandle.planCreditGenerateContract(bean);
				}else if(FddGenerateContractConstant.FDD_TRANSTYPE_APPLY_CRIDET == transType){//垫付债转投资
					fddHandle.creditGenerateContractApply(bean);
				}else if(FddGenerateContractConstant.FDD_TRANSTYPE_APPLY_PLAN_CRIDET == transType ){//计划垫付债转
					fddHandle.planCreditGenerateContractApply(bean);
				}

			} catch (Exception e) {
				logger.error("=============生成法大大合同任务异常，订单号：" + orderId + ",错误信息：", e);
				throw new RuntimeException("=============生成法大大合同任务异常，订单号：" + orderId + ",错误信息：", e);
			}
			logger.info("--------------------------------------生成法大大合同任务结束，订单号：" + orderId + "=============");

		} else if (MQConstant.FDD_AUTO_SIGN_TAG.equals(msg.getTags())) {
			// 自动签署
			//订单号
			String ordid = null;
			try {
				DzqzCallBean bean = JSONObject.parseObject(msg.getBody(),DzqzCallBean.class);
				if (Validator.isNull(bean)){
					logger.info("传入参数不得为空！");
					return;
				}
				ordid = bean.getContract_id();
				if (Validator.isNull(ordid)){
					logger.info("传入参数不得为空！");
					return;
				}

				logger.info("-----------------开始处理法大大自动签章异步处理，订单号：" + ordid);
				fddHandle.updateAutoSignData(bean);
			}catch (Exception e){
				logger.error("--------------------------------------法大大自动签署异步处理任务异常，订单号：" + ordid + ",错误信息：", e);
				throw new RuntimeException("--------------------------------------法大大自动签署异步处理任务异常，订单号：" + ordid + ",错误信息：", e);
			}
			logger.info("--------------------------------------法大大自动签署异步处理任务结束，订单号：" + ordid + "=============");

		} else if (MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG.equals(msg.getTags())) {
			// 下载脱敏
			FddDessenesitizationBean bean = JSONObject.parseObject(msg.getBody(),FddDessenesitizationBean.class);;
			String ordid = null;
			try {
				if (Validator.isNull(bean)){
					logger.info("法大大下载脱敏处理参数为空");
					return;
				}
				String agrementID = bean.getAgrementID();
				if(StringUtils.isBlank(agrementID)){
					logger.info("传入参数agrementID不得为空！");
					return;
				}
				ordid = bean.getOrdid();
				if (StringUtils.isBlank(ordid)){
					logger.info("传入参数ordid不得为空！");
					return;
				}
				String downloadUrl = bean.getDownloadUrl();
				if(StringUtils.isBlank(downloadUrl)){
					logger.info("传入参数downloadUrl不得为空！");
					return;
				}
				String ftpPath = bean.getFtpPath();
				if(StringUtils.isBlank(ftpPath)){
					logger.info("传入参数ftpPath不得为空！");
					return;
				}
				String savePath = bean.getSavePath();
				if(StringUtils.isBlank(savePath)){
					logger.info("传入参数savePath 不得为空！");
					return;
				}
				String transType = bean.getTransType();
				if(StringUtils.isBlank(transType)){
					logger.info("传入参数transType 不得为空！");
					return;
				}
				boolean tenderCompany = bean.isTenderCompany();
				boolean creditCompany = bean.isCreditCompany();
				logger.info("-----------------开始处理法大大下载脱敏，订单号：" + ordid);
				fddHandle.downPDFAndDesensitization(savePath,agrementID,transType,ftpPath,downloadUrl,tenderCompany,creditCompany);
			}catch (Exception e){
				logger.error("--------------------------------------法大大下载脱敏处理任务异常，订单号：" + ordid + ",错误信息：", e);
				throw new RuntimeException("--------------------------------------法大大下载脱敏处理任务异常，订单号：" + ordid + ",错误信息：", e);
			}
			logger.info("--------------------------------------法大大下载脱敏处理任务结束，订单号：" + ordid + "=============");
			return;
		}
		// 如果没有return success ，consumer会重新消费该消息，直到return success
		return;
	}
}
