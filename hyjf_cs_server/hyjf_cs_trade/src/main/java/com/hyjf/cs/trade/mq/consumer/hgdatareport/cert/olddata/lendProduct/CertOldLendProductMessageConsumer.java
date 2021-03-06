/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.olddata.lendProduct;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.cert.CertProductUpdateVO;
import com.hyjf.am.vo.trade.cert.CertProductVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallConstant;
import com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.common.CertCallUtil;
import com.hyjf.cs.trade.service.consumer.hgdatareport.cert.lendProduct.CertLendProductService;
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
 * @Description 合规数据上报 CERT 产品信息历史数据上报
 * @Author nxl
 * @Date 2018/11/28 10:57
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.CERT_OLD_LENDPRODUCT_TAG, consumerGroup = MQConstant.CERT_OLD_LENDPRODUCT_GROUP)
public class CertOldLendProductMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    Logger logger = LoggerFactory.getLogger(CertOldLendProductMessageConsumer.class);

    private String thisMessName = "产品信息历史数据推送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_CERT + " " + thisMessName + "】";

    @Autowired
    private CertLendProductService certLendProductService;

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
        boolean canRun = certLendProductService.checkCanRun();
        if (!canRun) {
            logger.info(logHeader + "redis不允许上报！");
            return;
        }

        // --> 消息处理
        try {
            // --> 调用service组装数据
            // 产品信息历史数据上报，查询所有产品信息，组装上报数据
            List<CertProductVO> certProductVOList = certLendProductService.selectCertProductList();
            if (!CollectionUtils.isNotEmpty(certProductVOList)) {
                logger.error(logHeader + "暂无上报的产品信息！！！");
                return;
            }
            logger.info(logHeader + "查询的未上报的产品信息历史数据共: " + certProductVOList.size() + "条,当前时间为:" + GetDate.getNowTime10());
            // --> 调用service组装数据
            JSONArray jsonArrayProduct = certLendProductService.getHistoryDateProduct();
            logger.info("数据：" + jsonArrayProduct.toString());
            int intCount = jsonArrayProduct == null ? 0 : jsonArrayProduct.size();
            logger.info(logHeader + "查询的产品信息历史数据共: " + intCount + "条" + ",当前时间为:" + GetDate.getNowTime10());
            if (null != jsonArrayProduct && jsonArrayProduct.size() > 0) {
                List<CertReportEntityVO> entitys = CertCallUtil.groupByDate(jsonArrayProduct, thisMessName, CertCallConstant.CERT_INF_TYPE_FINANCE);
                // 遍历循环上报
                for (CertReportEntityVO entity : entitys) {
                    try {
                        certLendProductService.insertAndSendPost(entity);
                    } catch (Exception e) {
                        throw e;
                    }
                    // 批量修改状态  start
                    List<Integer> ids = new ArrayList<>();
                    for (CertProductVO item : certProductVOList) {
                        ids.add(item.getId());
                    }
                    if (ids.size() > 0) {
                        CertProductUpdateVO update = new CertProductUpdateVO();
                        update.setIds(ids);
                        CertProductVO certProduct = new CertProductVO();
                        if (entity != null && CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(entity.getReportStatus())) {
                            // 成功
                            certProduct.setIsProduct(1);
                        } else {
                            // 失败
                            certProduct.setIsProduct(99);
                        }
                        update.setCertProduct(certProduct);
                        // 批量修改
                        certLendProductService.updateCertProductBatch(update);
                    }
                    // 批量修改状态  end
                }
            }
            logger.info(logHeader + " 处理成功。" + msgBody);
        } catch (Exception e) {
            // 错误时，以下日志必须出力（预警捕捉点）
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
        } finally {
            logger.info(logHeader + " 结束。");
        }
    }
}
