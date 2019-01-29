/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.nifa.statistical;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.nifa.NifaCreditInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaCreditTransferVO;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditTenderVO;
import com.hyjf.am.vo.trade.hjh.HjhDebtCreditVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaCreditInfoMessageService;
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

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaCreditInfoMessageConsumer, v0.1 2019/1/24 16:35
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.UNDERTAKE_ALL_SUCCESS_TAG, consumerGroup = "NIFA_CREDIT_INFO_GROUP")
public class NifaCreditInfoMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(NifaTenderInfoMessageConsumer.class);

    private String thisMessName = "互金标的相关债转信息上送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_NIFA + " " + thisMessName + "】";

    @Autowired
    NifaCreditInfoMessageService nifaCreditInfoMessageService;

    @Override
    public void onMessage(MessageExt message) {
        logger.info(logHeader + " 开始。");
        // --> 消息内容校验
        if (message == null || message.getBody() == null) {
            logger.error(logHeader + "接收到的消息为null！！！");
            return;
        }

        // --> 消息转换
        String msgBody = new String(message.getBody());
        logger.info(logHeader + "接收到的消息：" + msgBody);

        JSONObject jsonObject;
        try {
            jsonObject = JSONObject.parseObject(msgBody);
        } catch (Exception e) {
            logger.error(logHeader + "解析消息体失败！！！", e);
            return;
        }

        String creditNid = jsonObject.getString("creditNid");
        Integer creditType = jsonObject.getInteger("flag");
        String historyData = jsonObject.getString("historyData");
        if (StringUtils.isBlank(creditNid) || null == creditType) {
            logger.error(logHeader + "通知参数不全！！！");
            return;
        }
        if (!CustomConstants.BORROW_CREDIT_STATUS.equals(creditType) && !CustomConstants.HJH_CREDIT_STATUS.equals(creditType)) {
            logger.info(logHeader + "通知参数不在处理范围内！！！");
            return;
        }

        // --> 消息处理
        try {
            // 防重处理
            NifaCreditInfoVO nifaCreditInfoEntity = this.nifaCreditInfoMessageService.selectNifaCreditInfoByCreditNid(creditNid);
            if (null != nifaCreditInfoEntity) {
                // 已经上报成功
                throw new Exception(logHeader + "债转数据已经做成！！" + msgBody);
            }

            // 历史数据发送消息带上处理日期
            if (StringUtils.isBlank(historyData)) {
                SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 获取今天时间yyyy-mm-dd
                historyData = date_sdf.format(new Date());
            }
            // --> 统计用变量初始化
            // 待收本息累加
            BigDecimal assignRepayAccount = BigDecimal.ZERO;
            // 转让价格累加
            BigDecimal assignPay = BigDecimal.ZERO;
            // 转让手续费
            BigDecimal creditFee = BigDecimal.ZERO;
            // 总承接本金
            BigDecimal tenderCapital = BigDecimal.ZERO;
            // 总承接利息
            BigDecimal tenderInterest = BigDecimal.ZERO;

            // 处理结果标识
            boolean result;
            // --> 散标债转数据处理
            if (CustomConstants.BORROW_CREDIT_STATUS.equals(creditType)) {
                // --> 拉取数据
                // 获取散标债转信息表
                BorrowCreditVO borrowCredit = this.nifaCreditInfoMessageService.selectBorrowCreditByCreditNid(creditNid);
                if (null == borrowCredit) {
                    throw new Exception(logHeader + "未获取到散标债转信息！！" + msgBody);
                }
                // 获取散标债转承接人的承接信息
                List<CreditTenderVO> creditTenderList = this.nifaCreditInfoMessageService.selectCreditTenderByCreditNid(creditNid);
                if (null == creditTenderList || creditTenderList.size() <= 0) {
                    throw new Exception(logHeader + "未获取到散标债转承接人的承接信息！！" + msgBody);
                }

                // --> 债转信息数据处理
                // 增加防重校验（已报送过的不再处理和入库、未报送过的重新编辑写入一遍）
                // 获取原始标的信息
                BorrowAndInfoVO borrow = this.nifaCreditInfoMessageService.getBorrowByNid(borrowCredit.getBidNid());
                if (null == borrow) {
                    throw new Exception(logHeader + "未获取到散标债转的原始标的信息！！" + msgBody);
                }

                // --> 出让人数据处理
                for (CreditTenderVO creditTender : creditTenderList) {
                    // --> 统计数据累加
                    // 待收本息累加->在repay表中取值
                    assignRepayAccount = assignRepayAccount.add(creditTender.getAssignAccount());
                    // 转让价格累加
                    assignPay = assignPay.add(creditTender.getAssignPay());
                    // 转让手续费
                    creditFee = creditFee.add(creditTender.getCreditFee());
                    // 承接总本金
                    tenderCapital = tenderCapital.add(creditTender.getAssignCapital());
                    // 承接总利息
                    tenderInterest = tenderInterest.add(creditTender.getAssignInterest());

                    // --> 出借人数据处理
                    // 拉取承接人详情
                    UserInfoVO usersInfo = this.nifaCreditInfoMessageService.getUsersInfoByUserId(creditTender.getUserId());
                    if (null == usersInfo) {
                        throw new Exception(logHeader + "未获取到散标债转出借人的详情！！" + msgBody);
                    }
                    NifaCreditTransferVO nifaCreditTransferEntity = new NifaCreditTransferVO();
                    // 初始化数据类型
                    // 散标债转信息处理
                    nifaCreditTransferEntity.setMessage(msgBody);
                    // 报送状态初始化
                    nifaCreditTransferEntity.setReportStatus("0");
                    // 数据做成时间
                    nifaCreditTransferEntity.setCreateTime(new Date());
                    nifaCreditTransferEntity.setUpdateTime(new Date());
                    // 散标债转信息没有上送过的重新编辑保存
                    // 散标债转信息需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
                    result = this.nifaCreditInfoMessageService.selectDualNifaCreditTransfer(creditTender.getAssignPay(), creditNid, usersInfo, nifaCreditTransferEntity);
                    if (!result) {
                        throw new Exception(logHeader + "该散标承接人信息处理错误！！" + msgBody + "，UserId：" + usersInfo.getUserId());
                    }
                    // 保存散标债转信息
                    result = this.nifaCreditInfoMessageService.insertNifaCreditTransfer(nifaCreditTransferEntity);
                    if (!result) {
                        throw new Exception(logHeader + "保存散标债转承接人信息失败！！" + msgBody + "，UserId：" + usersInfo.getUserId());
                    }
                }
                // --> 开始处理债转数据
                if (null == nifaCreditInfoEntity) {
                    nifaCreditInfoEntity = new NifaCreditInfoVO();
                }
                // 初始化数据类型
                // 散标债转信息处理
                nifaCreditInfoEntity.setMessage(msgBody);
                // 报送状态初始化
                nifaCreditInfoEntity.setReportStatus("0");
                // 数据做成时间
                nifaCreditInfoEntity.setCreateTime(new Date());
                nifaCreditInfoEntity.setUpdateTime(new Date());
                // 散标债转信息没有上送过的重新编辑保存
                // 散标债转信息需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
                result = this.nifaCreditInfoMessageService.selectDualNifaCreditInfo(assignRepayAccount, assignPay, creditFee, creditTenderList.size(), historyData, borrowCredit, borrow, nifaCreditInfoEntity);
                if (!result) {
                    throw new Exception(logHeader + "该散标债转信息处理错误！！" + msgBody);
                }
                // 保存散标债转信息
                result = this.nifaCreditInfoMessageService.insertNifaCreditInfo(nifaCreditInfoEntity);
                if (!result) {
                    throw new Exception(logHeader + "保存散标债转信息失败！！" + msgBody);
                }
            }

            // --> 计划债转数据处理
            if (CustomConstants.HJH_CREDIT_STATUS.equals(creditType)) {
                // 承接人承接记录 hyjf_hjh_debt_credit_tender
                List<HjhDebtCreditTenderVO> hjhDebtCreditTenderList = this.nifaCreditInfoMessageService.selectHjhDebtCreditTenderByCreditNid(creditNid + "");
                if (null == hjhDebtCreditTenderList || hjhDebtCreditTenderList.size() <= 0) {
                    throw new Exception(logHeader + "未获取到计划债转承接人的承接信息！！" + msgBody);
                }
                // 汇计划债转表 hyjf_hjh_debt_credit
                HjhDebtCreditVO hjhDebtCredit = this.nifaCreditInfoMessageService.selectHjhDebtCreditByCreditNid(creditNid + "");
                if (null == hjhDebtCredit) {
                    throw new Exception(logHeader + "未获取到汇计划债转表信息！！" + msgBody);
                }
                // 获取原始标的信息
                BorrowAndInfoVO borrow = this.nifaCreditInfoMessageService.getBorrowByNid(hjhDebtCredit.getBorrowNid());
                if (null == borrow) {
                    throw new Exception(logHeader + "未获取到计划债转的原始标的信息！！" + msgBody);
                }

                // --> 出让人数据处理
                for (HjhDebtCreditTenderVO hjhDebtCreditTender : hjhDebtCreditTenderList) {
                    // 累计代收本息
                    assignRepayAccount = assignRepayAccount.add(hjhDebtCreditTender.getAssignAccount());
                    // 累计债转服务费
                    creditFee = creditFee.add(hjhDebtCreditTender.getAssignServiceFee());
                    // 实际支付金额累计
                    assignPay = assignPay.add(hjhDebtCreditTender.getAssignPay());
                    // 承接总本金
                    tenderCapital = tenderCapital.add(hjhDebtCreditTender.getAssignCapital());
                    // 承接总利息
                    tenderInterest = tenderInterest.add(hjhDebtCreditTender.getAssignInterest());

                    UserInfoVO usersInfo = this.nifaCreditInfoMessageService.getUsersInfoByUserId(hjhDebtCreditTender.getUserId());
                    if (null == usersInfo) {
                        throw new Exception(logHeader + "未获取到计划债转承接人详细信息！！" + msgBody);
                    }
                    NifaCreditTransferVO nifaCreditTransferEntity = new NifaCreditTransferVO();
                    // 初始化数据类型
                    // 计划债转信息处理
                    nifaCreditTransferEntity.setMessage(msgBody);
                    // 报送状态初始化
                    nifaCreditTransferEntity.setReportStatus("0");
                    // 数据做成时间
                    nifaCreditTransferEntity.setCreateTime(new Date());
                    nifaCreditTransferEntity.setUpdateTime(new Date());
                    // 计划债转信息没有上送过的重新编辑保存
                    // 计划债转信息需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
                    result = this.nifaCreditInfoMessageService.selectDualNifaCreditTransfer(hjhDebtCreditTender.getAssignPay(), creditNid, usersInfo, nifaCreditTransferEntity);
                    if (!result) {
                        throw new Exception(logHeader + "该计划债转承接人信息处理错误！！" + msgBody + "，UserId：" + usersInfo.getUserId());
                    }
                    // 保存散标债转信息
                    result = this.nifaCreditInfoMessageService.insertNifaCreditTransfer(nifaCreditTransferEntity);
                    if (!result) {
                        throw new Exception(logHeader + "该计划债转承接人信息保存错误！！" + msgBody + "，UserId：" + usersInfo.getUserId());
                    }
                }
                // --> 开始处理债转信息
                if (null == nifaCreditInfoEntity) {
                    nifaCreditInfoEntity = new NifaCreditInfoVO();
                }
                // 初始化数据类型
                // 散标债转信息处理
                nifaCreditInfoEntity.setMessage(msgBody);
                // 报送状态初始化
                nifaCreditInfoEntity.setReportStatus("0");
                // 数据做成时间
                nifaCreditInfoEntity.setCreateTime(new Date());
                nifaCreditInfoEntity.setUpdateTime(new Date());
                // 散标债转信息没有上送过的重新编辑保存
                // 散标债转信息需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
                result = this.nifaCreditInfoMessageService.selectDualNifaHjhFirstCreditInfo(assignRepayAccount, creditFee, assignPay, tenderCapital, tenderInterest, hjhDebtCreditTenderList.size(), borrow.getBorrowNid(), historyData, hjhDebtCredit, borrow, nifaCreditInfoEntity);
                if (!result) {
                    throw new Exception(logHeader + "该计划债转信息处理错误！！" + msgBody);
                }
                // 保存计划债转信息
                result = this.nifaCreditInfoMessageService.insertNifaCreditInfo(nifaCreditInfoEntity);
                if (!result) {
                    throw new Exception(logHeader + "该计划债转信息保存错误！！" + msgBody);
                }
            }

        } catch (Exception e) {
            // 错误时，以下日志必须出力（预警捕捉点）
            logger.error(logHeader + " 处理失败！！" + msgBody, e);
        } finally {
            logger.info(logHeader + " 结束。");
        }
    }

    @Override
    public void prepareStart(DefaultMQPushConsumer defaultMQPushConsumer) {
        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);
        logger.info("====NifaRepayInfoMessage consumer=====");
    }
}
