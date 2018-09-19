package com.hyjf.am.trade.mq.consumer.hjh.issuerecover;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.Consumer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.MailProducer;
import com.hyjf.am.trade.service.task.issuerecover.AutoBailMessageService;
import com.hyjf.am.trade.service.task.issuerecover.AutoIssueMessageService;
import com.hyjf.am.vo.message.MailMessage;
import com.hyjf.am.vo.trade.hjh.issuerecover.AutoIssuerecoverVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.GetDate;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

import static com.hyjf.am.trade.service.task.issuerecover.impl.AutoIssueRecoverServiceImpl.LABEL_MAIL_KEY;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/12 10:56
 * @Description: AutoIssueMessageConsumer
 * 关联计划
 */
@Component
public class AutoIssueMessageConsumer extends Consumer {
    private static final Logger logger = LoggerFactory.getLogger(AutoIssueMessageConsumer.class);
    @Resource
    private AutoBailMessageService autoBailMessageService;
    @Resource
    private AutoIssueMessageService autoIssueMessageService;
    @Autowired
    private MailProducer mailProducer;
    @Value("${hyjf.env.test}")
    private Boolean env_test;

    @Value("${hyjf.alerm.email.test}")
    private static String emailList1;

    @Value("${hyjf.alerm.email}")
    private static String emaillist2;

    @Override
    public void init(DefaultMQPushConsumer defaultMQPushConsumer) throws MQClientException {
        defaultMQPushConsumer.setInstanceName(String.valueOf(System.currentTimeMillis()));
        defaultMQPushConsumer.setConsumerGroup(MQConstant.ROCKETMQ_BORROW_PREAUDIT_GROUP);
        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(MQConstant.ROCKETMQ_BORROW_ISSUE_TOPIC, "*");
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        defaultMQPushConsumer.registerMessageListener(new AutoIssueMessageConsumer.MessageListener());
        // Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
        defaultMQPushConsumer.start();
    }

    /**
     *
     */
    public class MessageListener implements MessageListenerConcurrently {
        @Override
        public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

            logger.info("AutoSendMessageConsumer 收到消息，开始处理....");
            MessageExt msg = msgs.get(0);
            String borrowNid = null;
            try {
                AutoIssuerecoverVO autoIssuerecoverVO = JSONObject.parseObject(msg.getBody(), AutoIssuerecoverVO.class);
                // 计划加入号
                borrowNid = autoIssuerecoverVO.getBorrowNid();
                // 计划加入号为空
                if (autoIssuerecoverVO == null || (autoIssuerecoverVO.getBorrowNid() == null && autoIssuerecoverVO.getCreditNid() == null)) {
                    logger.error("标的编号为空");
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }

                // --> 消息处理

                Borrow borrow = autoBailMessageService.getBorrowByBorrowNidrowNid(borrowNid);
                BorrowInfo borrowInfo = autoBailMessageService.getById(borrow.getId());

                // 原始标的情况
                if(StringUtils.isNotBlank(autoIssuerecoverVO.getBorrowNid())){
                    logger.info(autoIssuerecoverVO.getBorrowNid()+" 原始标开始关联计划 ");

                    // redis 放重复检查
                    String redisKey = "borrowissue:" + autoIssuerecoverVO.getBorrowNid();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if(!result){
                        logger.info(autoIssuerecoverVO.getBorrowNid()+" 正在关联计划(redis)");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }


                    if(borrow == null){
                        logger.info(autoIssuerecoverVO.getBorrowNid()+" 该标的在表里不存在");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 业务校验
                    // 发标的状态
                    if(borrow.getStatus() !=2 || borrow.getVerifyStatus() !=4){
                        logger.info(autoIssuerecoverVO.getBorrowNid()+" 该标的不是已经发标的状态 ");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    if(StringUtils.isNotBlank(borrow.getPlanNid())){
                        logger.info(autoIssuerecoverVO.getBorrowNid()+" 该标的已经绑定计划 "+borrow.getLabelId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 第三方资产
                    HjhPlanAsset asset = autoIssueMessageService.selectPlanAssetByBorrowNid(borrow.getBorrowNid(),borrowInfo.getInstCode());
                    if(asset != null){
                        if(StringUtils.isNotBlank(asset.getPlanNid()) || asset.getLabelId() == null || asset.getLabelId().intValue() == 0){
                            logger.info(asset.getBorrowNid()+" 该标的对应资产已经绑定计划或无标签绑定 "+borrow.getLabelId());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        if(asset.getStatus().intValue()!= 7){
                            logger.info(asset.getBorrowNid()+" 该标的对应资产不是投资中状态 "+borrow.getLabelId());
                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                    }

                    // 如果散标过来的标的，没有标签先打上
                    if (asset == null && borrow.getLabelId() != null && borrow.getLabelId().intValue() == 0) {
                        // 获取标签id
                        HjhLabel label = autoIssueMessageService.getLabelId(borrowInfo,borrow,null);
                        if(label == null || label.getId() == null){
                            logger.info(autoIssuerecoverVO.getBorrowNid()+" 该散标没有匹配到标签 ");

                            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                        }
                        // 临时存着
                        borrow.setLabelId(label.getId());
                    }
                    // 分配计划引擎
                    String planNid = autoIssueMessageService.getPlanNid(borrow.getLabelId());
                    if(planNid == null || borrow.getLabelId() == null || borrow.getLabelId().intValue()==0){
                        logger.info(autoIssuerecoverVO.getBorrowNid()+" 该标的标签无计划关联 "+borrow.getLabelId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 关联计划
                    boolean flag = autoIssueMessageService.updateIssueBorrow(borrowInfo,borrow,planNid,asset);
                    if (!flag) {
                        logger.error("关联计划失败！" + "[标的编号：" + autoIssuerecoverVO.getBorrowNid() + "]");
                    }

                    // 债转标的情况
                }else if (StringUtils.isNotBlank(autoIssuerecoverVO.getCreditNid())) {
                    logger.info(autoIssuerecoverVO.getCreditNid()+" 债转开始关联计划 ");

                    // redis 放重复检查
                    String redisKey = "borrowissue:" + autoIssuerecoverVO.getCreditNid();
                    boolean result = RedisUtils.tranactionSet(redisKey, 300);
                    if(!result){
                        logger.info(autoIssuerecoverVO.getCreditNid()+" 正在关联计划(redis) ");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }


                    HjhDebtCredit credit = autoIssueMessageService.getCreditByNid(autoIssuerecoverVO.getCreditNid());
                    if(credit == null){
                        logger.info(autoIssuerecoverVO.getCreditNid()+" 该债转在表里不存在");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    if(credit.getCreditStatus() != null && credit.getCreditStatus().intValue() != 0){
                        logger.info(autoIssuerecoverVO.getCreditNid()+" 该债转状态不为0 初始状态");
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 业务校验
                    if(StringUtils.isNotBlank(credit.getPlanNidNew())){
                        logger.info(autoIssuerecoverVO.getCreditNid()+" 该债转已经绑定计划或无标签绑定 "+credit.getLabelId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 获取标签id
                    HjhLabel label = autoIssueMessageService.getLabelId(credit);
                    if(label == null || label.getId() == null){
                        logger.info(autoIssuerecoverVO.getCreditNid()+" 该债转没有匹配标签 ");

                        /**汇计划三期邮件预警 BY LIBIN start*/
                        // 如果redis不存在这个KEY(一天有效期)，那么可以发邮件
                        if(!RedisUtils.exists(LABEL_MAIL_KEY + autoIssuerecoverVO.getCreditNid())){
                            StringBuffer emailmsg = new StringBuffer();
                            emailmsg.append("债转编号：").append(autoIssuerecoverVO.getCreditNid()).append("<br/>");
                            emailmsg.append("当前时间：").append(GetDate.formatTime()).append("<br/>");
                            emailmsg.append("错误信息：").append("该债转没有匹配标签！").append("<br/>");
                            // 邮箱集合
                            String emailList= "";
                            if (env_test){
                                emailList = emailList1;
                            }else{
                                emailList = emaillist2;
                            }
                            String [] toMail = emailList.split(",");
                            MailMessage mailMessage = new MailMessage(null, null, "债转编号为：" + autoIssuerecoverVO.getCreditNid(), emailmsg.toString(), null, toMail, null,
                                    MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
                            try {
                                mailProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC,UUID.randomUUID().toString(), JSON.toJSONBytes(mailMessage)));
                                RedisUtils.set(LABEL_MAIL_KEY + autoIssuerecoverVO.getCreditNid(), autoIssuerecoverVO.getCreditNid(), 24 * 60 * 60);
                            } catch (MQException e2) {
                                logger.error("发送邮件失败..", e2);
                            }

                        } else {
                            logger.info("此邮件key值还未过期(一天)");
                        }
                        /**汇计划三期邮件预警 BY LIBIN end*/

                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }
                    credit.setLabelId(label.getId());
                    credit.setLabelName(label.getLabelName());

                    // 分配计划引擎
                    String planNid = autoIssueMessageService.getPlanNid(credit.getLabelId());
                    if(planNid == null){
                        logger.info(autoIssuerecoverVO.getCreditNid()+" 该债转标签无计划关联 "+credit.getLabelId());
                        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                    }

                    // 关联计划
                    boolean flag = autoIssueMessageService.updateIssueCredit(credit,planNid);
                    if (!flag) {
                        logger.error("关联计划失败！" + "[债转标的编号：" + autoIssuerecoverVO.getCreditNid() + "]");
                    }


                }


            } catch (Exception e) {
                logger.error("自动关联计划异常！",e);
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            } finally {
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        }
    }
}
