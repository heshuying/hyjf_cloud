/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.mq.consumer.hgdatareport.nifa.statistical;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaBorrowerInfoVO;
import com.hyjf.am.vo.hgreportdata.nifa.NifaTenderInfoVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.am.vo.user.CertificateAuthorityVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaRepayInfoMessageService;
import com.hyjf.cs.trade.service.consumer.hgdatareport.nifa.NifaTenderInfoMessageService;
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
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author PC-LIUSHOUYI
 * @version NifaRepayInfoMessageConsumer, v0.1 2019/1/24 15:03
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.REPAY_SINGLE_SUCCESS_TAG, consumerGroup = MQConstant.NIFA_REPAY_GROUP)
public class NifaRepayInfoMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(NifaRepayInfoMessageConsumer.class);

    @Autowired
    NifaTenderInfoMessageService nifaTenderInfoMessageService;

    @Autowired
    NifaRepayInfoMessageService nifaRepayInfoMessageService;

    private String thisMessName = "互金标的相关还款信息上送";
    private String logHeader = "【" + CustomConstants.HG_DATAREPORT + CustomConstants.UNDERLINE + CustomConstants.HG_DATAREPORT_NIFA + " " + thisMessName + "】";

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

        String borrowNid = jsonObject.getString("borrowNid");
        Integer repayPeriod = jsonObject.getInteger("repayPeriod");
        String historyData = jsonObject.getString("historyData");
        if (StringUtils.isBlank(borrowNid) || null == repayPeriod) {
            logger.error(logHeader + "通知参数不全！！！");
            return;
        }

        // --> 消息处理
        try {
            boolean result;

            // 历史数据发送消息带上处理日期
            if (StringUtils.isBlank(historyData)) {
                SimpleDateFormat date_sdf = new SimpleDateFormat("yyyy-MM-dd");
                // 获取今天时间yyyy-mm-dd
                historyData = date_sdf.format(new Date());
            }

            // 增加防重校验（已报送过的不再处理和入库、未报送过的重新编辑写入一遍）
            NifaBorrowInfoVO nifaRepayInfoEntity = this.nifaTenderInfoMessageService.selectNifaBorrowInfoByBorrowNid(borrowNid, msgBody);
            if (null != nifaRepayInfoEntity && "1".equals(nifaRepayInfoEntity.getReportStatus())) {
                // 已经上报成功
                logger.info(logHeader + " 借款详情已经上报。" + msgBody);
                return;
            }
            // --> 拉数据
            // 获取标的的借款详情
            BorrowAndInfoVO borrow = this.nifaRepayInfoMessageService.getBorrowByNid(borrowNid);
            if (null == borrow) {
                throw new Exception(logHeader + "未获取到相关项目信息！！");
            }
            // 获取标的还款表数据
            BorrowRepayVO borrowRepay = this.nifaRepayInfoMessageService.selectBorrowRepay(borrowNid);
            if (null == borrowRepay) {
                throw new Exception(logHeader + "未获取到相关项目还款信息！！");
            }
            // 获取标的放款表数据
            List<BorrowRecoverVO> borrowRecoverList = this.nifaRepayInfoMessageService.selectBorrowRecoverListByBorrowNid(borrowNid);
            if (null == borrowRecoverList || borrowRecoverList.size() <= 0) {
                throw new Exception(logHeader + "未获取到相关项目放款信息！！");
            }
            List<BorrowTenderVO> borrowTenderList = this.nifaTenderInfoMessageService.selectBorrowTenderByBorrowNid(borrowNid);
            if (CollectionUtils.isEmpty(borrowTenderList)) {
                throw new Exception(logHeader + "未获取到该借款下所有投资人的相关信息！！borrowNid:" + borrowNid);
            }
            String bank = "";
            BankCardVO bankCard = this.nifaRepayInfoMessageService.selectBankCardByUserId(borrow.getUserId());
            if (null == bankCard || StringUtils.isBlank(bankCard.getBank())) {
                bank = "工商银行";
            }

            // 逾期次数
            String lateCounts = "0";
            // 借款人编号
            String borrower = "";
            // 借款人信息1：公司 2：个人 获取借款主体的相关信息
            if ("1".equals(borrow.getCompanyOrPersonal())) {
                // 获取借款公司信息
                BorrowUserVO borrowUsers = this.nifaRepayInfoMessageService.selectBorrowUsersByBorrowNid(borrowNid);
                if (null == borrowUsers) {
                    throw new Exception(logHeader + "未获取到借款公司信息！！");
                }
                // 当前借款公司的逾期次数
                lateCounts = borrowUsers.getOverdueTimes();
                String cardNo = "";
                if (StringUtils.isNotBlank(borrowUsers.getSocialCreditCode())) {
                    cardNo = borrowUsers.getSocialCreditCode();
                } else if (StringUtils.isNotBlank(borrowUsers.getCorporateCode())) {
                    cardNo = borrowUsers.getCorporateCode();
                }
                // 获取CA认证信息
                CertificateAuthorityVO certificateAuthority = this.nifaRepayInfoMessageService.selectCAInfoByUsername(borrowUsers.getUsername(), cardNo, 1);
                if (null != certificateAuthority && StringUtils.isNotBlank(certificateAuthority.getCustomerId())) {
                    borrower = certificateAuthority.getCustomerId();
                } else {
                    borrower = borrow.getId().toString();
                }
                // 判断借款公司信息是否上送
                NifaBorrowerInfoVO nifaBorrowerInfoEntity = new NifaBorrowerInfoVO();
                // 初始化数据类型
                nifaBorrowerInfoEntity.setMessage(msgBody);
                // 报送状态初始化
                nifaBorrowerInfoEntity.setReportStatus("0");
                // 数据做成时间
                nifaBorrowerInfoEntity.setCreateTime(new Date());
                nifaBorrowerInfoEntity.setUpdateTime(new Date());
                // 编辑借款公司信息
                result = this.nifaTenderInfoMessageService.selectDualNifaBorrowerUserInfo(borrowUsers, borrower, borrow.getBorrowLevel(), bank, nifaBorrowerInfoEntity);
                // 数据库保存借款公司信息
                if (result) {
                    this.nifaTenderInfoMessageService.insertNifaBorrowerUserInfo(nifaBorrowerInfoEntity);
                } else {
                    logger.error(logHeader + " 该出借公司信息处理错误。" + msgBody);
                }
            } else if ("2".equals(borrow.getCompanyOrPersonal())) {
                // 获取借款人信息
                BorrowManinfoVO borrowManinfo = this.nifaRepayInfoMessageService.selectBorrowMainfo(borrowNid);
                if (null == borrowManinfo) {
                    throw new Exception(logHeader + "未获取到借款人信息！！");
                }
                // 当前借款人的逾期次数
                lateCounts = borrowManinfo.getOverdueTimes();
                // 获取借款人CA认证信息
                CertificateAuthorityVO certificateAuthority = this.nifaRepayInfoMessageService.selectCAInfoByUsername(borrowManinfo.getName(), borrowManinfo.getCardNo(), 0);
                if (null != certificateAuthority && StringUtils.isNotBlank(certificateAuthority.getCustomerId())) {
                    borrower = certificateAuthority.getCustomerId();
                } else {
                    borrower = borrow.getId().toString();
                }
                // 判断借款人信息是否上送
                NifaBorrowerInfoVO nifaBorrowerInfoEntity = new NifaBorrowerInfoVO();
                // 初始化数据类型
                nifaBorrowerInfoEntity.setMessage(msgBody);
                // 报送状态初始化
                nifaBorrowerInfoEntity.setReportStatus("0");
                // 数据做成时间
                nifaBorrowerInfoEntity.setCreateTime(new Date());
                nifaBorrowerInfoEntity.setUpdateTime(new Date());
                // 编辑借款人信息
                result = this.nifaTenderInfoMessageService.selectDualNifaBorrowerManInfo(borrowManinfo, borrower, borrow.getBorrowLevel(), bank, nifaBorrowerInfoEntity);
                // 数据库保存借款人信息
                if (result) {
                    this.nifaTenderInfoMessageService.insertNifaBorrowerUserInfo(nifaBorrowerInfoEntity);
                } else {
                    logger.error(logHeader + " 该出借人信息处理错误。" + msgBody);
                }
            }
            // --> 开始处理投资人数据
            BigDecimal recoverFee = BigDecimal.ZERO;
            // 遍历放款记录
            for (BorrowTenderVO borrowTenderVO : borrowTenderList) {
                UserInfoVO userInfoVO = this.nifaTenderInfoMessageService.getUsersInfoByUserId(borrowTenderVO.getUserId());
                if (null == userInfoVO) {
                    throw new Exception(logHeader + "未获取到投资人相关信息！！borrowNid:" + borrowNid);
                }
                // 累加放款服务费
                recoverFee = recoverFee.add(borrowTenderVO.getLoanFee());
                NifaTenderInfoVO nifaTenderInfoEntity = new NifaTenderInfoVO();
                // 初始化数据类型
                nifaTenderInfoEntity.setMessage(msgBody);
                // 报送状态初始化
                nifaTenderInfoEntity.setReportStatus("0");
                // 数据做成时间
                nifaTenderInfoEntity.setCreateTime(new Date());
                nifaTenderInfoEntity.setUpdateTime(new Date());
                // 出借人详情需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
                result = this.nifaTenderInfoMessageService.selectDualNifaTenderInfo(borrow, borrowTenderVO, userInfoVO, nifaTenderInfoEntity);
                if (result) {
                    // 保存借款详情
                    this.nifaTenderInfoMessageService.insertNifaTenderInfo(nifaTenderInfoEntity);
                } else {
                    logger.error(logHeader + " 该出借人信息处理错误。" + msgBody + "，用户ID：" + userInfoVO.getUserId());
                }
            }
            // --> 开始处理还款数据
            if (null == nifaRepayInfoEntity) {
                nifaRepayInfoEntity = new NifaBorrowInfoVO();
            }
            // 初始化数据类型
            nifaRepayInfoEntity.setMessage(msgBody);
            // 报送状态初始化
            nifaRepayInfoEntity.setReportStatus("0");
            // 数据做成时间
            nifaRepayInfoEntity.setCreateTime(new Date());
            nifaRepayInfoEntity.setUpdateTime(new Date());
            // 借款详情没有上送过的重新编辑保存
            // 借款详情需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
            result = this.nifaRepayInfoMessageService.selectDualNifaRepayInfo(historyData, repayPeriod, borrow, borrowRepay, borrowRecoverList, recoverFee, lateCounts, nifaRepayInfoEntity);
            if (result) {
                // 保存借款详情
                this.nifaTenderInfoMessageService.insertNifaBorrowInfo(nifaRepayInfoEntity);
            } else {
                logger.error(logHeader + " 该出借人信息处理错误。" + msgBody);
            }
            logger.info(logHeader + " 处理成功。" + msgBody);
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
