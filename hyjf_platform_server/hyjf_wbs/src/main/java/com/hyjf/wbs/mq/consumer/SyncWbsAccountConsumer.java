package com.hyjf.wbs.mq.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.wbs.configs.WbsConfig;
import com.hyjf.wbs.mq.MqConstants;
import com.hyjf.wbs.qvo.CustomerSyncQO;
import com.hyjf.wbs.trade.dao.model.auto.Account;
import com.hyjf.wbs.trade.service.AccountService;
import com.hyjf.wbs.user.dao.model.auto.UtmReg;
import com.hyjf.wbs.user.dao.model.customize.BankOpenAccountRecordCustomize;
import com.hyjf.wbs.user.service.BankOpenRecordService;
import com.hyjf.wbs.user.service.SyncCustomerService;
import com.hyjf.wbs.user.service.UtmRegService;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wxd
 * @Date: 2019-04-10 16:17
 * @Description:用户信息变更实时推送wbs财富端
 */

@Service
@RocketMQMessageListener(topic = MQConstant.SYNC_ACCOUNT_TOPIC, selectorExpression = "*", consumerGroup = MqConstants.WBS_SYNC_ACCOUNT_GROUP)
public class SyncWbsAccountConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(SyncWbsAccountConsumer.class);

    public static final String CONSUMER_NAME = "<<客户信息同步到WBS财富管理系统>>: ";
    @Autowired
    private WbsConfig wbsConfig;
    @Autowired
    private AccountService accountService;
    @Autowired
    private UtmRegService utmRegService;
    @Autowired
    private BankOpenRecordService bankOpenRecordService;
    @Autowired
    private SyncCustomerService syncCustomerService;

    @Override
    public void onMessage(MessageExt messageExt) {
        try {
            MessageExt msg = messageExt;
            String tagName = msg.getTags();
            String jsonMsg = new String(msg.getBody());
            logger.info("====" + CONSUMER_NAME + "收到消息[{}],消费开始=====", jsonMsg);
            JSONObject jsonObj = JSON.parseObject(jsonMsg);
            if (jsonObj == null) {
                logger.error("=====" + CONSUMER_NAME + "消息实体转换异常=====");
                return;
            }
            String userId = jsonObj.getString("userId");
            if (StringUtils.isBlank(userId)) {
                logger.error("=====" + CONSUMER_NAME + "userId 为空=====");
                return;
            }

            //根据渠道注册表查询用户是否是属于财富端对应的渠道
            List<Integer> utmId = new ArrayList<Integer>();
            utmId.add(wbsConfig.getUtmNami());//纳觅渠道编号
            utmId.add(wbsConfig.getUtmYufengrui());//纳觅渠道编号
            utmId.add(wbsConfig.getUtmDatang());//纳觅渠道编号
            utmId.add(wbsConfig.getUtmQianle());//纳觅渠道编号


            UtmReg utmReg = utmRegService.selectUtmInfo(Integer.valueOf(userId), utmId);
            //符合资产端客户信息，推送账户变更信息
            if (utmReg != null) {
                CustomerSyncQO customerSyncQO = new CustomerSyncQO();


                Account account = accountService.getAccount(Integer.valueOf(userId));
                if (account == null) {
                    logger.error("=====" + CONSUMER_NAME + " 没有查询到目标账户, userId = [{}]=====", userId);
                    return;
                }
                Map<String, Object> mapParam = new HashMap<String, Object>();
                mapParam.put("userId", Integer.valueOf(userId));
                BankOpenAccountRecordCustomize bankOpenAccountRecordCustomize = bankOpenRecordService.selectBankAccountList(mapParam);
                if (bankOpenAccountRecordCustomize == null) {
                    logger.error("=====" + CONSUMER_NAME + "userId==" + userId + "未查到开户记录");
                } else {
                    customerSyncQO.setPlatformAccountOpeningTime(bankOpenAccountRecordCustomize.getOpenTime());
                }
                customerSyncQO.setAssetCustomerId(userId);
                if(!getEntId(utmReg.getUtmId()).isEmpty()){
                    customerSyncQO.setEntId(Integer.valueOf(getEntId(utmReg.getUtmId())));
                }else{
                    logger.error("=====" + CONSUMER_NAME + " 查询不到财富端id, utmim = [{}]=====", utmReg.getUtmId());
                    return;
                }
                customerSyncQO.setUserName(account.getUserName());
                customerSyncQO.setPrecipitatedCapital(account.getBalance().doubleValue());
                customerSyncQO.setFundsToBeCollected(account.getAwait().doubleValue());
                syncCustomerService.sync(customerSyncQO);

            }

        } catch (Exception e1) {
            logger.error("=====" + CONSUMER_NAME + "异常=====");
            logger.error(e1.getMessage());
            return;
        }
        // 如果没有return success ，consumer会重新消费该消息，直到return success
        return;
    }

    public String getEntId(Integer utmId) {
        String thirdIds = wbsConfig.getThridPropertyIds();
        String[] thirdIdsArr = thirdIds.split(",");
        if (utmId.equals(wbsConfig.getUtmNami()) || utmId.equals(wbsConfig.getUtmYufengrui())) {
            return thirdIdsArr[0];
        } else if (utmId.equals(wbsConfig.getUtmDatang())) {
            return thirdIdsArr[1];
        } else if (utmId.equals(wbsConfig.getUtmQianle())) {
            return thirdIdsArr[2];
        } else {
            return null;
        }


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
        logger.info("====" + CONSUMER_NAME + "监听初始化完成, 启动完毕=====");
    }

}