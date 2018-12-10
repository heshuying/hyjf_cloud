package com.hyjf.signatrues.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.fdd.FddDessenesitizationBean;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.common.constants.FddGenerateContractConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.fadada.bean.DzqzCallBean;
import com.hyjf.signatrues.mq.base.Consumer;
import com.hyjf.signatrues.mq.handle.FddHandle;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.*;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 法大大consumer
 * @author jijun
 * @version 20180627
 */

@Component
public class FddConsumer extends Consumer {

	private static final Logger logger = LoggerFactory.getLogger(FddConsumer.class);

	@Autowired
	private FddHandle fddHandle;

	@Override
	public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
		defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
		defaultMQPushConsumer.setConsumerGroup(MQConstant.FDD_GROUP);
		// 订阅指定MyTopic下tags等于MyTag
		defaultMQPushConsumer.subscribe(MQConstant.FDD_TOPIC, "*");
		// 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
		// 如果非第一次启动，那么按照上次消费的位置继续消费
		defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 设置为集群消费(区别于广播消费)
		// MQ默认集群消费
		defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
		defaultMQPushConsumer.registerMessageListener(new MessageListener());
		// Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
		defaultMQPushConsumer.start();
		logger.info("====法大大 consumer=====");
	}
	//MessageListenerConcurrently 并行消费 效率高 消费不追求时间顺序
	//MessageListenerOrderly 串行消费 效率不高    消费追求时间顺序
	public class MessageListener implements MessageListenerOrderly {
		@Override
		public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
			logger.info("法大大 收到消息，开始处理....msgs is :{}", msgs);

			for (MessageExt msg : msgs) {
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
							return ConsumeOrderlyStatus.SUCCESS;
						}
						orderId = bean.getOrdid();
						transType = bean.getTransType();
						if (Validator.isNull(orderId) && Validator.isNull(transType)) {
							logger.info("传入参数不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						if (FddGenerateContractConstant.PROTOCOL_TYPE_TENDER == transType) {// 散标出借
							fddHandle.tenderGenerateContract(bean);
						} else if (FddGenerateContractConstant.PROTOCOL_TYPE_PLAN == transType) {// 计划加入
							fddHandle.planJoinGenerateContract(bean);
						} else if (FddGenerateContractConstant.PROTOCOL_TYPE_CREDIT == transType) {// 债转出借
							fddHandle.creditGenerateContract(bean);
						} else if (FddGenerateContractConstant.FDD_TRANSTYPE_PLAN_CRIDET == transType) {// 计划债转
							fddHandle.planCreditGenerateContract(bean);
						}else if(FddGenerateContractConstant.FDD_TRANSTYPE_APPLY_CRIDET == transType){//垫付债转出借
							fddHandle.creditGenerateContractApply(bean);
						}else if(FddGenerateContractConstant.FDD_TRANSTYPE_APPLY_PLAN_CRIDET == transType ){//计划垫付债转
							fddHandle.planCreditGenerateContractApply(bean);
						}

					} catch (Exception e) {
						logger.info("=============生成法大大合同任务异常，订单号：" + orderId + ",错误信息：" + e.getMessage()	+ "=============");
						return ConsumeOrderlyStatus.SUCCESS;
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
							return ConsumeOrderlyStatus.SUCCESS;
						}
						ordid = bean.getContract_id();
						if (Validator.isNull(ordid)){
							logger.info("传入参数不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}

						logger.info("-----------------开始处理法大大自动签章异步处理，订单号：" + ordid);
						fddHandle.updateAutoSignData(bean);
					}catch (Exception e1){
						logger.info("--------------------------------------法大大自动签署异步处理任务异常，订单号：" + ordid + ",错误信息："+ e1.getMessage()+"=============");
						return ConsumeOrderlyStatus.SUCCESS;
					}
					logger.info("--------------------------------------法大大自动签署异步处理任务结束，订单号：" + ordid + "=============");

				} else if (MQConstant.FDD_DOWNPDF_AND_DESSENSITIZATION_TAG.equals(msg.getTags())) {
					// 下载脱敏
					FddDessenesitizationBean bean = JSONObject.parseObject(msg.getBody(),FddDessenesitizationBean.class);;
					String ordid = null;
					try {
						if (Validator.isNull(bean)){
							logger.info("法大大下载脱敏处理参数为空");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						String agrementID = bean.getAgrementID();
						if(StringUtils.isBlank(agrementID)){
							logger.info("传入参数agrementID不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						ordid = bean.getOrdid();
						if (StringUtils.isBlank(ordid)){
							logger.info("传入参数ordid不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						String downloadUrl = bean.getDownloadUrl();
						if(StringUtils.isBlank(downloadUrl)){
							logger.info("传入参数downloadUrl不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						String ftpPath = bean.getFtpPath();
						if(StringUtils.isBlank(ftpPath)){
							logger.info("传入参数ftpPath不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						String savePath = bean.getSavePath();
						if(StringUtils.isBlank(savePath)){
							logger.info("传入参数savePath 不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						String transType = bean.getTransType();
						if(StringUtils.isBlank(transType)){
							logger.info("传入参数transType 不得为空！");
							return ConsumeOrderlyStatus.SUCCESS;
						}
						boolean tenderCompany = bean.isTenderCompany();
						boolean creditCompany = bean.isCreditCompany();
						logger.info("-----------------开始处理法大大下载脱敏，订单号：" + ordid);
						fddHandle.downPDFAndDesensitization(savePath,agrementID,transType,ftpPath,downloadUrl,tenderCompany,creditCompany);
					}catch (Exception e1){
						logger.info("--------------------------------------法大大下载脱敏处理任务异常，订单号：" + ordid + ",错误信息："+ e1.getMessage()+"=============");
						e1.printStackTrace();
						return ConsumeOrderlyStatus.SUCCESS;
					}
					logger.info("--------------------------------------法大大下载脱敏处理任务结束，订单号：" + ordid + "=============");
					return ConsumeOrderlyStatus.SUCCESS;
				}
			}
			// 如果没有return success ，consumer会重新消费该消息，直到return success
			return ConsumeOrderlyStatus.SUCCESS;
		}
	}
}
