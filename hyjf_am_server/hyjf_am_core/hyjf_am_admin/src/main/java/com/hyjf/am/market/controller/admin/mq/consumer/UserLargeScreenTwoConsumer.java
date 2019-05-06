package com.hyjf.am.market.controller.admin.mq.consumer;

import com.hyjf.am.admin.mq.consumer.SellDailyConsumer;
import com.hyjf.am.market.dao.model.auto.ScreenTwoParam;
import com.hyjf.am.market.service.UserLargeScreenTwoCustomizeService;
import com.hyjf.am.trade.service.UserLargeScreenTwoCustomizeTService;
import com.hyjf.am.user.dao.model.auto.CustomerTaskConfig;
import com.hyjf.common.constants.MQConstant;
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
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RocketMQMessageListener(topic = MQConstant.SCREEN_DATA_TWO_TOPIC, selectorExpression = "*", consumerGroup = MQConstant.SCREEN_DATA_TWO_GROUP)
public class UserLargeScreenTwoConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {
    private static final Logger logger = LoggerFactory.getLogger(SellDailyConsumer.class);
    private static int MAX_RECONSUME_TIME = 3;

    @Autowired
    private UserLargeScreenTwoCustomizeService userLargeScreenTwoCustomizeService;
    @Autowired
    private UserLargeScreenTwoCustomizeTService userLargeScreenTwoCustomizeTService;

    @Override
    public void onMessage(MessageExt messageExt) {
        if (MQConstant.SCREEN_DATA_TWO_SELECT_TAG.equals(messageExt.getTags())) {
            logger.info("用户画像-运营部投屏二数据batch获取 ==========>>> [Start]");
            // 增资、提现率处理
            // 查询有效坐席
            List<CustomerTaskConfig> customerList = userLargeScreenTwoCustomizeService.getCustomer();
            if (!CollectionUtils.isEmpty(customerList)){
                // 查询坐席下的增资、提现率
                List<ScreenTwoParam> result = userLargeScreenTwoCustomizeService.getCapitalIncreaseAndCashWithdrawalRateByCustomer(customerList);
                if (!CollectionUtils.isEmpty(result)){
                    // 运营部对象
                    ScreenTwoParam operationParam = new ScreenTwoParam();
                    // 运营部总增资
                    BigDecimal operationalCapitalIncrease = new BigDecimal("0");
                    for (ScreenTwoParam param : result) {
                        operationalCapitalIncrease = operationalCapitalIncrease.add(param.getCapitalIncrease());
                    }
                    // 查询运营部当前总站岗资金
                    BigDecimal operNowBalance = userLargeScreenTwoCustomizeService.getOperNowBalance();
                    // 给运营部对象赋值
                    operationParam.setFlag(3);
                    operationParam.setCapitalIncrease(operationalCapitalIncrease);
                    operationParam.setQueryTime(new Date());
                    operationParam.setNowBalance(operNowBalance);
                    result.add(operationParam);
                    // 添加数据之前先清空表历史数据,防止表数据增长太快
                    userLargeScreenTwoCustomizeTService.deleteAllParam();
                    // 添加集合数据到 用户画像-屏幕二数据表
                    userLargeScreenTwoCustomizeTService.insertResult(result);
                }
            }
            logger.info("用户画像-运营部投屏二数据batch获取 ==========>>> [End]");

            logger.info("用户画像-运营部投屏 每日用户划转执行 ==========>>> [Start]");
            // 运营部用户划转线下，同步删除用户历史数据
            // 查询所有运营部用户的userId
            List<String> operUserIdList = userLargeScreenTwoCustomizeService.getOperationUserId();
            if(!CollectionUtils.isEmpty(operUserIdList)){
                // 每个集合数据量
                int num = 1000;
                // 查询运营部用户资金明细表下的所有userId
                List<String> uolUserIdList = userLargeScreenTwoCustomizeService.getUserOperateListUserId();
                if(!CollectionUtils.isEmpty(uolUserIdList)){
                    // 资金明细表-非运营部用户统计
                    List<String> delUolUserId = new ArrayList<>();
                    for (String param : uolUserIdList) {
                        if (!operUserIdList.contains(param)){
                            delUolUserId.add(param);
                        }
                    }
                    // 资金明细表-数据批量删除
                    if(!CollectionUtils.isEmpty(delUolUserId)){
                        // 计算分多少个集合
                        int listNum = delUolUserId.size()%num == 0 ? delUolUserId.size()/num : delUolUserId.size()/num+1;
                        // 储存userId的大list切分成多个小list,防止sql过长
                        List<List<String>> delUolUserIdLists = averageAssign(delUolUserId, listNum);
                        for (List<String> param : delUolUserIdLists) {
                            userLargeScreenTwoCustomizeTService.deleteUserOperate(param);
                        }
                    }
                }

                // 查询坐席每日待回款金额表下的所有userId
                List<String> rpUserIdList = userLargeScreenTwoCustomizeService.getRepaymentPlan();
                if(!CollectionUtils.isEmpty(rpUserIdList)){
                    // 坐席每日待回款金额表-非运营部用户统计
                    List<String> delRpUserId = new ArrayList<>();
                    for (String param : rpUserIdList) {
                        if (!operUserIdList.contains(param)){
                            delRpUserId.add(param);
                        }
                    }
                    // 坐席每日待回款金额表-数据批量删除
                    if(!CollectionUtils.isEmpty(delRpUserId)){
                        // 计算分多少个集合
                        int listNum = delRpUserId.size()%num == 0 ? delRpUserId.size()/num : delRpUserId.size()/num+1;
                        // 储存userId的大list切分成多个小list,防止sql过长
                        List<List<String>> delRpUserIdLists = averageAssign(delRpUserId, listNum);
                        for (List<String> param : delRpUserIdLists) {
                            userLargeScreenTwoCustomizeTService.deleteRepaymentPlan(param);
                        }
                    }
                }
            }
            logger.info("用户画像-运营部投屏 每日用户划转执行 ==========>>> [End]");
        }
    }

    /**
     * 将一个list均分成n个list,主要通过偏移量来实现的
     * @param source
     * @return
     */
    public static <T> List<List<T>> averageAssign(List<T> source,int n){
        List<List<T>> result = new ArrayList<List<T>>();
        //(先计算出余数)
        int remaider = source.size()%n;
        //然后是商
        int number = source.size()/n;
        //偏移量
        int offset = 0;
        for(int i=0; i<n; i++){
            List<T> value = null;
            if(remaider > 0){
                value = source.subList(i*number+offset, (i+1)*number+offset+1);
                remaider--;
                offset++;
            }else{
                value=source.subList(i*number+offset, (i+1)*number+offset);
            }
            result.add(value);
        }
        return result;
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        //设置最大重试次数
        defaultMQPushConsumer.setMaxReconsumeTimes(MAX_RECONSUME_TIME);

        logger.info("====用户画像-运营部投屏二数据batch获取、每日用户划转执行 消费端开始执行=====");
    }
}
