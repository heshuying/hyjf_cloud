/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.mq.base.Consumer;
import com.hyjf.cs.trade.service.borrow.AutoRecordService;
import org.apache.commons.lang3.StringUtils;
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

import java.util.List;
import java.util.Random;

/**
 * 自动备案
 * @author fuqiang
 * @version AutoRecordConsumer, v0.1 2018/6/14 9:57
 */
@Component
public class AutoRecordConsumer extends Consumer {

    private static final Logger logger = LoggerFactory.getLogger(AutoRecordConsumer.class);

    @Autowired
    private AutoRecordService autoRecordService;

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.ROCKETMQ_BORROW_RECORD_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ROCKETMQ_BORROW_RECORD_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoRecordConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
    }

    public class MessageListener implements MessageListenerConcurrently {

        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {

            // --> 消息检查
            MessageExt msg = list.get(0);
            if(msg == null || msg.getBody() == null){
                logger.error("【自动备案任务】接收到的消息为null");
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            }

            // --> 消息转换
            String msgBody = new String(msg.getBody());
            logger.info("【自动备案请求】接收到的消息：" + msgBody);

            HjhPlanAssetVO mqHjhPlanAsset;
            try {
                mqHjhPlanAsset = JSONObject.parseObject(msgBody, HjhPlanAssetVO.class);
                if(mqHjhPlanAsset == null || (mqHjhPlanAsset.getAssetId() == null && mqHjhPlanAsset.getBorrowNid() == null)){
                    logger.info("解析为空：" + msgBody);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

                // --> 消息处理

                // 资产编号不为空的是三方推送的资产
                if (StringUtils.isNotBlank(mqHjhPlanAsset.getAssetId())) {
                    String assetId = mqHjhPlanAsset.getAssetId();
                    String instCode = mqHjhPlanAsset.getInstCode();

                    // 资产自动备案
                    logger.info(assetId + " 开始自动备案 " + instCode);
                    HjhPlanAssetVO hjhPlanAssetVO = autoSendClient.selectPlanAsset(assetId, instCode);
                    if (hjhPlanAssetVO == null) {
                        logger.info(" 该资产在表里不存在！！");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // redis 放重复检查
                    String redisKey = "borrowrecord:" + hjhPlanAssetVO.getInstCode() + hjhPlanAssetVO.getAssetId();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if (!result) {
                        logger.info(hjhPlanAssetVO.getInstCode() + " 正在备案(redis) " + hjhPlanAssetVO.getAssetId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 业务校验
                    if (hjhPlanAssetVO.getStatus() != null && hjhPlanAssetVO.getStatus().intValue() != 3 &&
                            hjhPlanAssetVO.getVerifyStatus() != null && hjhPlanAssetVO.getVerifyStatus().intValue() == 1) {
                        logger.info(assetId + " 该资产状态不是备案状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    /** * 网站合规改造-自动备案添加还款授权校验 start 根据产品需求：只处理 资产来源为非汇盈金服的标的 校验 */
                    /**因业务需求暂时注掉各种授权校验代码 */

/*			logger.info("开始校验借款人的还款授权：" + hjhPlanAssetVO.getUserId());
			// 替换参数(短信用)
			Map<String, String> replaceMap = new HashMap<String, String>();
			if(StringUtils.isNotEmpty(hjhPlanAssetVO.getBorrowNid())){
				//通过资产中的标的查询此标的是否有担保机构
				Borrow borrow = autoRecordService.getBorrowByBorrowNid(hjhPlanAssetVO.getBorrowNid());
				if(borrow != null){
					// (1.1)担保机构id可以为空,不为空时校验，为空不校验授权
					if(borrow.getRepayOrgUserId() != null){
						HjhUserAuth hjhUserAuth = autoRecordService.getHjhUserAuthByUserID(borrow.getRepayOrgUserId());
						if(hjhUserAuth == null){
							logger.info("该资产无担保机构或者未做任何授权" + mqHjhPlanAsset.getAssetId() );
						} else {
							// 还款授权状态 DB 默认为 0
							if (hjhUserAuth.getAutoRepayStatus() == null || hjhUserAuth.getAutoRepayStatus().toString().equals("0")) {
								logger.info("该资产的担保机构未做还款授权" + mqHjhPlanAsset.getAssetId() );
							}
							// 缴费授权状态 DB 默认为 0
							if (hjhUserAuth.getAutoPaymentStatus() == null || hjhUserAuth.getAutoPaymentStatus().toString().equals("0")) {
								logger.info("该资产的担保机构未做缴费授权" + mqHjhPlanAsset.getAssetId() );
							}
						}
					}
					// (1.2)受托人id可以为空,不为空时校验，为空不校验授权
					if(borrow.getEntrustedUserId() != null){
						HjhUserAuth hjhUserAuth1 = autoRecordService.getHjhUserAuthByUserID(borrow.getEntrustedUserId());
						if(hjhUserAuth1 == null){
							logger.info("该资产无收款人或者该收款人未做任何授权" + mqHjhPlanAsset.getAssetId() );
						} else {
							// 缴费授权状态 DB 默认为 0
							if (hjhUserAuth1.getAutoPaymentStatus() == null || hjhUserAuth1.getAutoPaymentStatus().toString().equals("0")) {
								logger.info("该资产的收款人未做缴费授权" + mqHjhPlanAsset.getAssetId() );
							}
						}
					}
					// (1.3)借款人id必须非空
					if(borrow.getUserId() != null){
						HjhUserAuth hjhUserAuth2 = autoRecordService.getHjhUserAuthByUserID(borrow.getUserId());
						if(hjhUserAuth2 == null){
							logger.info("该资产无借款人或者借款未做任何授权" + mqHjhPlanAsset.getAssetId() );
						} else {
							// 缴费授权状态 DB 默认为 0
							if (hjhUserAuth2.getAutoPaymentStatus() == null || hjhUserAuth2.getAutoPaymentStatus().toString().equals("0")) {
								logger.info("该资产的借款人未做缴费授权" + mqHjhPlanAsset.getAssetId() );
							}
							// 是否可用担保机构还款(0:否,1:是) DB默认为0
							if(borrow.getIsRepayOrgFlag() != null && borrow.getIsRepayOrgFlag() == 1){
								如果是担保机构还款，还款授权可以不做
							} else {
								// 还款授权状态 DB 默认为 0
								if (hjhUserAuth2.getAutoRepayStatus() == null || hjhUserAuth2.getAutoRepayStatus().toString().equals("0")) {
									logger.info("该资产的借款人无担保机构垫付并且未做还款授权" + mqHjhPlanAsset.getAssetId() );
								}
							}
						}
					} else {
						logger.info("该资产无借款人" + mqHjhPlanAsset.getAssetId() );
					}
				} else {
					logger.info("该资产在borrow表中不存在：" + hjhPlanAssetVO.getAssetId());
				}
			} else {
				logger.info("此资产无标的编号：" + hjhPlanAssetVO.getAssetId());
			}
			logger.info("结束校验授权：" + hjhPlanAssetVO.getAssetId());*/


                    /** * 网站合规改造-自动备案添加还款授权校验 end */


                    //判断该资产是否可以自动备案，是否关联计划
                    HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO = apiAssetClient.selectAssetBorrowType(hjhPlanAssetVO.getInstCode(), hjhPlanAssetVO.getAssetType());
                    if (hjhAssetBorrowTypeVO == null || hjhAssetBorrowTypeVO.getAutoRecord() == null || hjhAssetBorrowTypeVO.getAutoRecord() != 1) {
                        logger.info(hjhPlanAssetVO.getAssetId() + " 该资产不能自动备案,原因自动备案未配置");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    boolean flag = autoRecordService.updateRecordBorrow(hjhPlanAssetVO, hjhAssetBorrowTypeVO);
                    if (!flag) {
                        logger.error("自动备案失败！" + "[资产编号：" + hjhPlanAssetVO.getAssetId() + "]");
                    } else {
                        // 成功后到初审队列
                        if (hjhPlanAssetVO.getEntrustedFlg() != null && hjhPlanAssetVO.getEntrustedFlg().intValue() == 1) {
                            logger.info(hjhPlanAssetVO.getAssetId() + "  未推送，等待授权");
                        } else {
                            autoRecordService.sendToMQ(hjhPlanAssetVO, MQConstant.ROCKETMQ_BORROW_PREAUDIT_GROUP);
                            logger.info(hjhPlanAssetVO.getAssetId() + " 成功发送到初审队列");
                        }

                        // 备案成功后随机睡0.2到0.5秒
                        try {
                            Random random = new Random();
                            int rand = (random.nextInt(4) + 2) * 100;
                            Thread.sleep(rand);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    logger.info(hjhPlanAssetVO.getAssetId() + " 结束自动备案");
                } else if (StringUtils.isNotBlank(mqHjhPlanAsset.getBorrowNid())) {
                    // 资产编号为空、借款编号不为空的是手动录标
                    logger.info(mqHjhPlanAsset.getBorrowNid()+" 开始自动备案 "+ mqHjhPlanAsset.getInstCode());
                    // redis 放重复检查
                    String redisKey = "borrowrecord:" + mqHjhPlanAsset.getInstCode()+mqHjhPlanAsset.getBorrowNid();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if(!result){
                        logger.info(mqHjhPlanAsset.getInstCode()+" 正在备案(redis) "+mqHjhPlanAsset.getBorrowNid());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    // 获取当前标的详情
                    BorrowVO borrow = autoRecordService.getBorrowByNid(mqHjhPlanAsset.getBorrowNid());

                    // 标的状态位判断
                    if (null == borrow.getStatus() || borrow.getStatus() != 0 ||
                            null == borrow.getRegistStatus() || borrow.getRegistStatus() != 0) {
                        logger.info("标的："+borrow.getBorrowNid()+" 不是备案状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    //判断该资产是否可以自动备案，是否关联计划
                    HjhAssetBorrowTypeVO hjhAssetBorrowType = apiAssetClient.selectAssetBorrowType(borrow.getInstCode(), borrow.getAssetType());
                    if(hjhAssetBorrowType == null || hjhAssetBorrowType.getAutoRecord() == null || hjhAssetBorrowType.getAutoRecord() != 1){
                        logger.info(borrow.getBorrowNid()+" 标的不能自动备案,原因自动备案未配置");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    boolean flag = autoRecordService.updateRecordBorrow(borrow);
                    if (!flag) {
                        logger.error("自动备案失败！" + "[资产/标的借款编号：" + borrow.getBorrowNid() + "]");
                    }else{
                        // 成功后到初审队列
                        if(borrow.getEntrustedFlg() != null && borrow.getEntrustedFlg().intValue() ==1){
                            logger.info(borrow.getBorrowNid()+"  未推送，等待授权");
                        }else{
                            // 审核保证金的标的发送MQ到消息队列
                            if (null != hjhAssetBorrowType && null != hjhAssetBorrowType.getAutoBail() && hjhAssetBorrowType.getAutoBail() == 1) {
                                // 加入到消息队列
                                autoRecordService.sendToMQ(borrow, MQConstant.ROCKETMQ_BORROW_PREAUDIT_GROUP);
                                logger.info(borrow.getBorrowNid()+" 成功发送到审核保证金队列");
                            }
                        }

                        // 备案成功后随机睡0.2到0.5秒
                        try {
                            Random random = new Random();
                            int rand = (random.nextInt(4)+2)*100;
                            Thread.sleep(rand);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                    logger.info(borrow.getBorrowNid()+" 结束自动备案");
                }
            } catch (Exception e1) {
                e1.printStackTrace();
                return ConsumeConcurrentlyStatus.RECONSUME_LATER;
            } finally {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
