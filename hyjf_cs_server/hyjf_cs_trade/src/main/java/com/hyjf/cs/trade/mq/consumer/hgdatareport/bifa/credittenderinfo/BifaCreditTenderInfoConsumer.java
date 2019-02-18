/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.bifa.credittenderinfo;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.bifa.BifaCreditTenderInfoEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaCommonConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaCreditTenderInfoService;
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
 * @author jun
 * @version BifaCreditTenderInfoHandle, v0.1 2019/1/17 9:33
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, consumerGroup = MQConstant.BIFA_CREDITTENDERINFO_GROUP)
public class BifaCreditTenderInfoConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    Logger logger = LoggerFactory.getLogger(BifaCreditTenderInfoConsumer.class);

    private String thisMessName = "转让信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    private BifaCreditTenderInfoService bifaCreditTenderInfoService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader + " 开始。");
    }

    @Override
    public void onMessage(MessageExt msg) {
        // --> 消息检查
        if (msg == null || msg.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null");
            return;
        }

        if (MQConstant.UNDERTAKE_ALL_SUCCESS_TAG.equals(msg.getTags())) {
            //-->日志输出接收到的消息
            String msgBody = new String(msg.getBody());
            logger.info(logHeader + "接收到的消息：" + msgBody);

            JSONObject jsonObject;
            try {
                jsonObject = JSONObject.parseObject(msgBody);
            } catch (Exception e) {
                logger.error(logHeader + "解析消息体失败！！！", e);
                return;
            }
            String creditNid = jsonObject.getString("creditNid");
            Integer flag = jsonObject.getInteger("flag");
            if (StringUtils.isBlank(creditNid) || flag == null) {
                logger.error(logHeader + "通知参数不全！！！");
                return;
            }

            //-->消息处理
            if (CustomConstants.BORROW_CREDIT_STATUS.equals(flag)) {
                //1散标完全承接 2三天到期有承接 3还款停止有承接 4后台停止后有承接
                // 获取散标债转信息
                BorrowCreditVO borrowCredit = this.bifaCreditTenderInfoService.selectBorrowCreditInfo(creditNid);
                if (null == borrowCredit) {
                    logger.error(logHeader + "未获取到债转信息！！" + "creditNid:" + creditNid);
                    return;
                }
                //散标转让
                //债转本金=已认购本金(1.完全承接)
                //债转本金≠已认购本金并且已认购本金!=0(2.后台手动停止后有承接3.散标转让到期有承接)
                //先校验是否符合上报条件
                BifaCreditTenderInfoEntityVO bifaCreditInfoEntity = this.bifaCreditTenderInfoService.getBifaBorrowCreditInfoFromMongDB(BifaCommonConstants.HZR + borrowCredit.getCreditNid());
                if (null != bifaCreditInfoEntity) {
                    // 已经上报成功
                    logger.info(logHeader + " 已经上报。" + msgBody);
                    return;
                }
                if (null == bifaCreditInfoEntity) {
                    //出让人信息
                    UserInfoVO creditUserInfo = this.bifaCreditTenderInfoService.getUsersInfoByUserId(borrowCredit.getCreditUserId());
                    if (null == creditUserInfo) {
                        logger.error(logHeader + "未获取到出让人信息！！" + "userId:" + borrowCredit.getCreditUserId());
                        return;
                    }

                    BorrowAndInfoVO borrow = this.bifaCreditTenderInfoService.getBorrowByBorrowNid(borrowCredit.getBidNid());
                    if (null == borrow) {
                        logger.error(logHeader + "未获取到该债转的原始标的信息！！borrowNid：" + borrowCredit.getBidNid());
                        return;
                    }
                    bifaCreditInfoEntity = new BifaCreditTenderInfoEntityVO();
                    // --> 数据变换
                    boolean result = this.bifaCreditTenderInfoService.convertBifaBorrowCreditInfo(borrowCredit, borrow, creditUserInfo, bifaCreditInfoEntity);
                    if (!result) {
                        logger.error(logHeader + "数据变换失败！！" + JSONObject.toJSONString(bifaCreditInfoEntity));
                        return;
                    }
                    // --> 上报数据（实时上报）
                    //上报数据失败时 将数据存放到mongoDB
                    String methodName = "productRegistration";
                    BifaCreditTenderInfoEntityVO reportResult = this.bifaCreditTenderInfoService.reportData(methodName,bifaCreditInfoEntity);
                    if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())){
                        logger.info(logHeader + "上报数据成功！！"+JSONObject.toJSONString(reportResult));
                    } else if ("9".equals(reportResult.getReportStatus())) {
                        logger.error(logHeader + "上报数据失败！！"+JSONObject.toJSONString(reportResult));
                    }
                    // --> 保存转让类型到本地mongo
                    reportResult.setFlag(flag);
                    // --> 保存上报数据
                    this.bifaCreditTenderInfoService.insertCreditTenderInfoReportData(reportResult);
                    logger.info(logHeader + "上报数据保存本地！！"+JSONObject.toJSONString(reportResult));
                }
            }else if (CustomConstants.HJH_CREDIT_STATUS.equals(flag)) {
                //智投转让
                //1完全承接 2晚上结束债权 3还款
                HjhDebtCreditVO hjhDebtCredit = this.bifaCreditTenderInfoService.selectHjhDebtCreditInfo(creditNid);
                if (null == hjhDebtCredit) {
                    logger.error(logHeader + "未获取到智投转让信息！！" + "creditNid:" + creditNid);
                    return;
                }

                BifaCreditTenderInfoEntityVO bifaCreditInfoEntity = this.bifaCreditTenderInfoService.getBifaBorrowCreditInfoFromMongDB(BifaCommonConstants.HZR + hjhDebtCredit.getCreditNid());
                if (null != bifaCreditInfoEntity) {
                    // 已经上报成功
                    logger.info(logHeader + " 已经上报。" + msgBody);
                    return;
                }
                if (null == bifaCreditInfoEntity) {
                    //出让人信息
                    UserInfoVO creditUserInfo = this.bifaCreditTenderInfoService.getUsersInfoByUserId(hjhDebtCredit.getUserId());
                    if (null == creditUserInfo) {
                        logger.error(logHeader + "未获取到出让人信息！！" + "userId:" + hjhDebtCredit.getUserId());
                        return;
                    }

                    BorrowAndInfoVO borrow = this.bifaCreditTenderInfoService.getBorrowByBorrowNid(hjhDebtCredit.getBorrowNid());
                    if (null == borrow) {
                        logger.error(logHeader + "未获取到该债转的原始标的信息！！borrowNid：" + hjhDebtCredit.getBorrowNid());
                        return;
                    }

                    bifaCreditInfoEntity = new BifaCreditTenderInfoEntityVO();
                    // --> 数据变换
                    boolean result = this.bifaCreditTenderInfoService.convertBifaHjhCreditInfo(hjhDebtCredit, borrow, creditUserInfo, bifaCreditInfoEntity);
                    if (!result) {
                        logger.error(logHeader + "数据变换失败！！" + JSONObject.toJSONString(bifaCreditInfoEntity));
                        return;
                    }
                    // --> 上报数据（实时上报）
                    //上报数据失败时 将数据存放到mongoDB
                    String methodName = "productRegistration";
                    BifaCreditTenderInfoEntityVO reportResult = this.bifaCreditTenderInfoService.reportData(methodName, bifaCreditInfoEntity);
                    if ("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())) {
                        logger.info(logHeader + "上报数据成功！！" + JSONObject.toJSONString(reportResult));
                    } else if ("9".equals(reportResult.getReportStatus())) {
                        logger.error(logHeader + "上报数据失败！！" + JSONObject.toJSONString(reportResult));
                    }

                    // --> 保存转让类型到本地mongo
                    reportResult.setFlag(flag);
                    // --> 保存上报数据
                    this.bifaCreditTenderInfoService.insertCreditTenderInfoReportData(reportResult);
                    logger.info(logHeader + "上报数据保存本地！！" + JSONObject.toJSONString(reportResult));

                }

            }

        }

    }

}
