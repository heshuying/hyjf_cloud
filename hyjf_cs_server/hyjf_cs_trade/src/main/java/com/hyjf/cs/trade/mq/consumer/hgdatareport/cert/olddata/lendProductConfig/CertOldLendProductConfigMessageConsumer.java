/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.olddata.lendProductConfig;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.cert.CertClaimUpdateVO;
import com.hyjf.am.vo.trade.cert.CertClaimVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProductConfig.CertLendProductConfigService;
import org.apache.commons.collections.CollectionUtils;
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
import java.util.List;

/**
 * @Description 合规数据上报 CERT 产品配置信息历史数据上报
 * @Author nxl
 * @Date 2018/11/28 10:57
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.CERT_OLD_LENDPRODUCTCONFIG_TAG, consumerGroup = MQConstant.CERT_OLD_LENDPRODUCTCONFIG_GROUP)
public class CertOldLendProductConfigMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    Logger logger = LoggerFactory.getLogger(CertOldLendProductConfigMessageConsumer.class);

    private String thisMessName = "产品配置信息历史数据推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    private CertLendProductConfigService certLendProductConfigService;

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info(logHeader + "====start=====");
    }

    @Override
    public void onMessage(MessageExt msg) {
        logger.info(logHeader + " 开始。");
        // --> 消息内容校验
        if (msg == null || msg.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null！！！");
            return;
        }

        String msgBody = new String(msg.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(logHeader + "解析消息体失败！！！", e);
            return;
        }

        // 检查redis的值是否允许运行 允许返回true  不允许返回false
        boolean canRun = certLendProductConfigService.checkCanRun();
        if (!canRun) {
            logger.info(logHeader + "redis不允许上报！");
            return;
        }

        // --> 消息处理
        try {
            // --> 增加防重校验（根据不同平台不同上送方式校验不同）
            Integer intCountConfig = certLendProductConfigService.countCertBorrowByFlg();
            if (null != intCountConfig && intCountConfig > 0) {
                logger.error(logHeader + "暂无未上报的产品配置信息！！！");
                return;
            }
            int intCountClick = intCountConfig % 2000 == 0 ? intCountConfig / 2000 : (intCountConfig / 2000) + 1;
            logger.info(logHeader + "共：" + intCountClick + "次查询");
            for (int i = 0; i < intCountClick; i++) {
                int disCount = i + 1;
                logger.info(logHeader + "第 ：" + disCount + " 次查询未上报产品配置");

                //一次查询2000条数据
                List<CertClaimVO> certBorrowEntityList = certLendProductConfigService.getCertBorrowNoConfig();
                logger.info(logHeader + "查询的未上报的产品配置历史数据共: " + certBorrowEntityList.size() + "条,当前时间为:" + GetDate.getNowTime10());
                // --> 调用service组装数据
                JSONArray listRepay = certLendProductConfigService.getHistoryDate();
                int intCount = listRepay == null ? 0 : listRepay.size();
                logger.info(logHeader + "查询的产品配置历史数据共: " + intCount + "条" + ",当前时间为:" + GetDate.getNowTime10());
                if (null != listRepay && listRepay.size() > 0) {
                    List<CertReportEntityVO> entitys = CertCallUtil.groupByDate(listRepay, thisMessName, CertCallConstant.CERT_INF_TYPE_FINANCE_SCATTER_CONFIG);
                    // 遍历循环上报
                    for (CertReportEntityVO entity : entitys) {
                        try {
                            certLendProductConfigService.insertAndSendPost(entity);
                        } catch (Exception e) {
                            throw e;
                        }
                        // 批量修改状态  start
                        List<Integer> ids = new ArrayList<>();
                        for (CertClaimVO item : certBorrowEntityList) {
                            ids.add(item.getId());
                        }
                        if (ids.size() > 0) {
                            CertClaimUpdateVO update = new CertClaimUpdateVO();
                            update.setIds(ids);
                            CertClaimVO certBorrow = new CertClaimVO();
                            if (entity != null && CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(entity.getReportStatus())) {
                                // 成功
                                certBorrow.setIsConfig(1);
                            } else {
                                // 失败
                                certBorrow.setIsConfig(99);
                            }
                            update.setCertClaim(certBorrow);
                            // 批量修改
                            certLendProductConfigService.updateCertBorrowStatusBatch(update);
                        }
                        // 批量修改状态  end
                    }
                } else {
                    //查询的数据全部被完全承接的情况下，修改状态
                    // 批量修改状态  start
                    List<Integer> ids = new ArrayList<>();
                    for (CertClaimVO item : certBorrowEntityList) {
                        ids.add(item.getId());
                    }
                    if (ids.size() > 0) {
                        CertClaimUpdateVO update = new CertClaimUpdateVO();
                        update.setIds(ids);
                        CertClaimVO certBorrow = new CertClaimVO();
                        //查询出的数据被完全承接
                        certBorrow.setIsConfig(98);
                        update.setCertClaim(certBorrow);
                        // 批量修改
                        certLendProductConfigService.updateCertBorrowStatusBatch(update);
                    }
                }
                logger.info(logHeader + "第 ：" + disCount + " 次处理成功");

            }
        } catch (Exception e) {
            // 错误时，以下日志必须出力（预警捕捉点）
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
        } finally {
            logger.info(logHeader + " 结束。");
        }
    }
}
