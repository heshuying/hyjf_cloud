/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.bifa.borrowstatus;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.trade.bifa.BifaBorrowStatusEntityVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.bifa.BifaBorrowStatusService;
import org.apache.commons.collections.CollectionUtils;
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

import java.util.List;

/**
 * @author jun
 * @version BifaBorrowStatusConsumer, v0.1 2019/1/14 17:01
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.LOAN_SUCCESS_TAG + "||" +MQConstant.REPAY_ALL_SUCCESS_TAG, consumerGroup = MQConstant.BIFA_BORROW_STATUS_GROUP)
public class BifaBorrowStatusConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(BifaBorrowStatusConsumer.class);
    private String thisMessName = "产品状态更新信息上报";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_BIFA + " " + thisMessName + "】";

    @Autowired
    private BifaBorrowStatusService bifaBorrowStatusService;

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
        //-->标的放款和最后一期还款完成上报产品状态更新
        if (MQConstant.LOAN_SUCCESS_TAG.equals(msg.getTags()) || MQConstant.REPAY_ALL_SUCCESS_TAG.equals(msg.getTags())) {
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

            String borrowNid = jsonObject.getString("borrowNid");
            if (StringUtils.isEmpty(borrowNid)) {
                logger.error(logHeader + "通知参数不全！！！");
                return;
            }

            BorrowAndInfoVO borrowAndInfoVO = bifaBorrowStatusService.getBorrowAndInfo(borrowNid);
            if (borrowAndInfoVO==null){
                logger.error(logHeader + "获取标的信息失败！！！");
                return;
            }

            //放款标的4，最后一笔还款完成标的5符合上报条件
            if (borrowAndInfoVO.getStatus()!=4 && borrowAndInfoVO.getStatus()!=5){
                logger.error(logHeader + "不符合上报条件！！！"+JSONObject.toJSONString(borrowAndInfoVO));
                return;
            }

            //-->判断散标还是智投下的标的
            if (StringUtils.isEmpty(borrowAndInfoVO.getPlanNid())){
                //-->散标，进行散标补报校验
                bifaBorrowStatusService.checkBorrowInfoIsReported(borrowAndInfoVO);
            }else{
                //-->智投，进行智投补报校验
                bifaBorrowStatusService.checkHjhPlanInfoIsReported(borrowAndInfoVO.getPlanNid());
            }

            //-->查看产品状态更新是否已上报
            BifaBorrowStatusEntityVO bifaBorrowStatusEntity = this.bifaBorrowStatusService.getBifaBorrowStatusFromMongoDB(borrowAndInfoVO);
            if (null != bifaBorrowStatusEntity) {
                // 已经上报成功
                logger.info(logHeader + " 已经上报。" + JSONObject.toJSONString(bifaBorrowStatusEntity));
                return;
            }
            if (null == bifaBorrowStatusEntity) {
                // --> 拉数据
                //标的投资信息
                List<BorrowTenderVO> borrowTenders = bifaBorrowStatusService.selectBorrowTenders(borrowNid);
                if (CollectionUtils.isEmpty(borrowTenders)){
                    logger.error(logHeader + "未获取到标的投资信息！！"+"borrowNid:"+borrowNid);
                    return;
                }
                // --> 数据变换
                bifaBorrowStatusEntity= new BifaBorrowStatusEntityVO();
                boolean result = bifaBorrowStatusService.convertBorrowStatus(borrowAndInfoVO,borrowTenders,bifaBorrowStatusEntity);
                if (!result){
                    logger.error(logHeader + "数据变换失败！！"+JSONObject.toJSONString(bifaBorrowStatusEntity));
                    return;
                }
                // --> 上报数据（实时上报）
                //上报数据失败时 将数据存放到mongoDB
                String methodName = "productStatusUpdate";
                BifaBorrowStatusEntityVO reportResult = this.bifaBorrowStatusService.reportData(methodName,bifaBorrowStatusEntity);
                if("1".equals(reportResult.getReportStatus()) || "7".equals(reportResult.getReportStatus())){
                    logger.info(logHeader + "上报数据成功！！"+JSONObject.toJSONString(reportResult));
                } else if ("9".equals(reportResult.getReportStatus())) {
                    logger.error(logHeader + "上报数据失败！！"+JSONObject.toJSONString(reportResult));
                }

                // --> 保存上报数据
                this.bifaBorrowStatusService.insertBorrowStatusReportData(reportResult);
                logger.info(logHeader + "上报数据保存本地！！"+JSONObject.toJSONString(reportResult));
            }

        }

    }

}
