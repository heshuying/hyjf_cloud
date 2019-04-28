/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.cert.olddata.lendProductConfig;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.hyjf.am.vo.hgreportdata.cert.CertOldLendProductConfigVO;
import com.hyjf.am.vo.hgreportdata.cert.CertReportEntityVO;
import com.hyjf.am.vo.trade.cert.CertBorrowUpdateVO;
import com.hyjf.am.vo.trade.cert.CertBorrowVO;
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

            List<CertBorrowVO> certBorrowEntityList = certLendProductConfigService.getCertBorrowNoConfig();
            if(CollectionUtils.isEmpty(certBorrowEntityList)){
                logger.error(logHeader + "暂无未上报的产品配置信息！！！");
                return;
            }
            logger.info(logHeader + "查询的未上报的产品配置历史数据共: " + certBorrowEntityList.size() + "条");
            // --> 调用service组装数据
            JSONArray listRepay = certLendProductConfigService.getHistoryDate();
            int intCount = listRepay == null ? 0 : listRepay.size();
            logger.info(logHeader + "查询的产品配置历史数据共: " + intCount + "条" + ",当前时间为:" + GetDate.getNowTime10());
            if (null == listRepay || listRepay.size() <= 0) {
                logger.error(logHeader + "组装参数为空！！！");
                return;
            }

            //转换为list
            List<JSONArray> jsonArrayList = new ArrayList<JSONArray>();
            List<CertOldLendProductConfigVO> certOldRepayPlanBeans = JSONArray.parseArray(listRepay.toJSONString(), CertOldLendProductConfigVO.class);
            if (null != certOldRepayPlanBeans) {
                //拆分数据,防止数据长多过长
                List<List<CertOldLendProductConfigVO>> parts = Lists.partition(certOldRepayPlanBeans, 3000);
                for (List<CertOldLendProductConfigVO> child : parts) {
                    JSONArray jsonArray = JSONArray.parseArray(JSON.toJSONString(child));
                    jsonArrayList.add(jsonArray);
                }
            }
            logger.info(logHeader + "产品配置历史数据拆分完毕,共" + jsonArrayList.size() + "条" + ",当前时间为:" + GetDate.getNowTime10());
            if (null != jsonArrayList && jsonArrayList.size() > 0) {
                for (int i = 0; i < jsonArrayList.size(); i++) {
                    JSONArray repay = jsonArrayList.get(i);
                    List<CertReportEntityVO> entitys = CertCallUtil.groupByDate(repay, thisMessName, CertCallConstant.CERT_INF_TYPE_REPAY_PLAN);
                    // 遍历循环上报
                    for (CertReportEntityVO entity : entitys) {
                        try {
                            certLendProductConfigService.insertAndSendPost(entity);
                        } catch (Exception e) {
                            throw e;
                        }
                        // 批量修改状态  start
                        List<Integer> ids = new ArrayList<>();
                        for (CertBorrowVO item : certBorrowEntityList) {
                            ids.add(item.getId());
                        }
                        if (ids.size() > 0) {
                            CertBorrowUpdateVO update = new CertBorrowUpdateVO();
                            update.setIds(ids);
                            CertBorrowVO certBorrow = new CertBorrowVO();
                            if (entity != null && CertCallConstant.CERT_RETURN_STATUS_SUCCESS.equals(entity.getReportStatus())) {
                                // 成功
                                certBorrow.setIsConfig(1);
                            } else {
                                // 失败
                                certBorrow.setIsConfig(99);
                            }
                            update.setCertBorrow(certBorrow);
                            // 批量修改
                            certLendProductConfigService.updateCertBorrowStatusBatch(update);
                        }
                        // 批量修改状态  end
                    }
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
