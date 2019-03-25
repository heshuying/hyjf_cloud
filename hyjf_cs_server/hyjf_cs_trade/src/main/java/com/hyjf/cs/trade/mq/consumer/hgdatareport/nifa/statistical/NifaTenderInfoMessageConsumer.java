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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author LIUSHOUYI
 * @version NifaTenderInfoMessageConsumer, v0.1 2018/6/25 17:52
 */
@Service
@RocketMQMessageListener(topic = MQConstant.HYJF_TOPIC, selectorExpression = MQConstant.LOAN_SUCCESS_TAG, consumerGroup = MQConstant.NIFA_LOAN_GROUP)
public class NifaTenderInfoMessageConsumer implements RocketMQListener<MessageExt>, RocketMQPushConsumerLifecycleListener {

    private static final Logger logger = LoggerFactory.getLogger(NifaTenderInfoMessageConsumer.class);

    /**
     * 获取18位社会信用代码
     */
    @Value("${hyjf.com.social.credit.code}")
    private String COM_SOCIAL_CREDIT_CODE;

    @Autowired
    NifaTenderInfoMessageService nifaTenderInfoMessageService;

    private String thisMessName = "互金标的相关信息上送";
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
        String historyData = jsonObject.getString("historyData");
        if (StringUtils.isBlank(borrowNid)) {
            logger.error(logHeader + "通知参数不全！！！");
            return;
        }

        // --> 消息处理

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
            NifaBorrowInfoVO nifaBorrowInfoVO = this.nifaTenderInfoMessageService.selectNifaBorrowInfoByBorrowNid(borrowNid, msgBody);
            // 放款数据已存在mongo库不再生成数据、确认相关信息手动清理完成后再推送处理消息
            if (null != nifaBorrowInfoVO ) {
                // 已经上报成功
                logger.info(logHeader + " 借款详情已经上报。" + msgBody);
                return;
            }

            // --> 拉数据
            // 获取标的的借款详情
            BorrowAndInfoVO borrow = this.nifaTenderInfoMessageService.getBorrowByNid(borrowNid);
            if (null == borrow) {
                throw new Exception(logHeader + "未获取到相关项目信息！！borrowNid:" + borrowNid);
            }
            // 获取标的还款表数据
            BorrowRepayVO borrowRepay = this.nifaTenderInfoMessageService.selectBorrowRepay(borrowNid);
            if (null == borrowRepay) {
                throw new Exception(logHeader + "未获取到相关项目还款信息！！borrowNid:" + borrowNid);
            }
            // 获取标的放款表数据
            List<BorrowRecoverVO> borrowRecoverList = this.nifaTenderInfoMessageService.selectBorrowRecoverListByBorrowNid(borrowNid);
            if (CollectionUtils.isEmpty(borrowRecoverList)) {
                throw new Exception(logHeader + "未获取到相关项目放款信息！！borrowNid:" + borrowNid);
            }
            List<BorrowTenderVO> borrowTenderList = this.nifaTenderInfoMessageService.selectBorrowTenderByBorrowNid(borrowNid);
            if (CollectionUtils.isEmpty(borrowTenderList)) {
                throw new Exception(logHeader + "未获取到该借款下所有投资人的相关信息！！borrowNid:" + borrowNid);
            }
            // 取江西银行绑定的银行卡信息
            BankCardVO bankCard = this.nifaTenderInfoMessageService.selectBankCardByUserId(borrow.getUserId());
            if (null == bankCard || StringUtils.isBlank(bankCard.getBank())) {
                bankCard = new BankCardVO();
                bankCard.setBank("工商银行");
            }

            // 查询到符合条件的数据上报状态变成1、数据重新做成
//            this.nifaTenderInfoMessageService.updateNifaBorrowerInfo(borrowNid, msgBody);
//            this.nifaTenderInfoMessageService.updateNifaTenderInfo(borrowNid, msgBody);
            // 逾期次数
            String lateCounts = "0";
            // 借款人编号
            String borrower = "";
            // 借款人信息1：公司 2：个人 获取借款主体的相关信息
            if ("1".equals(borrow.getCompanyOrPersonal())) {
                // 获取借款公司信息
                BorrowUserVO borrowUsers = this.nifaTenderInfoMessageService.selectBorrowUsersByBorrowNid(borrowNid);
                if (null == borrowUsers) {
                    throw new Exception(logHeader + "未获取到借款公司信息！！borrowNid:" + borrowNid);
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
                CertificateAuthorityVO certificateAuthority = this.nifaTenderInfoMessageService.selectCAInfoByUsername(borrowUsers.getUsername(), cardNo, 1);
                if (null != certificateAuthority && StringUtils.isNotBlank(certificateAuthority.getCustomerId())) {
                    borrower = certificateAuthority.getCustomerId();
                } else {
                    borrower = borrow.getId().toString();
                }
                NifaBorrowerInfoVO nifaBorrowerInfoVO = new NifaBorrowerInfoVO();
                // 初始化数据类型
                nifaBorrowerInfoVO.setMessage(msgBody);
                // 报送状态初始化
                nifaBorrowerInfoVO.setReportStatus("0");
                // 数据做成时间
                nifaBorrowerInfoVO.setCreateTime(new Date());
                nifaBorrowerInfoVO.setUpdateTime(new Date());
                // 编辑借款公司信息
                result = this.nifaTenderInfoMessageService.selectDualNifaBorrowerUserInfo(borrowUsers, borrower, borrow.getBorrowLevel(), bankCard.getBank(), nifaBorrowerInfoVO);
                // 数据库保存借款公司信息
                if (!result) {
                    throw new Exception(logHeader + "该出借公司信息处理错误！！msgBody:" + msgBody);
                }
                result = this.nifaTenderInfoMessageService.insertNifaBorrowerUserInfo(nifaBorrowerInfoVO);
                if (!result) {
                    throw new Exception(logHeader + "该出借公司信息插入失败！！msgBody:" + msgBody);
                }
            } else if ("2".equals(borrow.getCompanyOrPersonal())) {
                // 获取借款人信息
                BorrowManinfoVO borrowManinfo = this.nifaTenderInfoMessageService.selectBorrowMainfo(borrowNid);
                if (null == borrowManinfo) {
                    throw new Exception(logHeader + "未获取到借款人信息！！borrowNid:" + borrowNid);
                }
                // 当前借款人的逾期次数
                lateCounts = borrowManinfo.getOverdueTimes();
                // 获取借款人CA认证信息
                CertificateAuthorityVO certificateAuthority = this.nifaTenderInfoMessageService.selectCAInfoByUsername(borrowManinfo.getName(), borrowManinfo.getCardNo(), 0);
                if (null != certificateAuthority && StringUtils.isNotBlank(certificateAuthority.getCustomerId())) {
                    borrower = certificateAuthority.getCustomerId();
                } else {
                    borrower = borrow.getId().toString();
                }
                NifaBorrowerInfoVO nifaBorrowerInfoVO = new NifaBorrowerInfoVO();
                // 初始化数据类型
                nifaBorrowerInfoVO.setMessage(msgBody);
                // 报送状态初始化
                nifaBorrowerInfoVO.setReportStatus("0");
                // 数据做成时间
                nifaBorrowerInfoVO.setCreateTime(new Date());
                nifaBorrowerInfoVO.setUpdateTime(new Date());
                // 编辑借款人信息
                result = this.nifaTenderInfoMessageService.selectDualNifaBorrowerManInfo(borrowManinfo, borrower, borrow.getBorrowLevel(), bankCard.getBank(), nifaBorrowerInfoVO);
                // 数据库保存借款人信息
                if (!result) {
                    throw new Exception(logHeader + "该借款人信息处理错误！！" + msgBody);
                }
                result = this.nifaTenderInfoMessageService.insertNifaBorrowerUserInfo(nifaBorrowerInfoVO);
                if (!result) {
                    throw new Exception(logHeader + "该出借人信息插入失败！！" + msgBody);
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
                // 一个人投资两笔的情况会出现两条相同的数据
                NifaTenderInfoVO nifaTenderInfoVO = new NifaTenderInfoVO();
                // 初始化数据类型
                nifaTenderInfoVO.setMessage(msgBody);
                // 报送状态初始化
                nifaTenderInfoVO.setReportStatus("0");
                // 数据做成时间
                nifaTenderInfoVO.setCreateTime(new Date());
                nifaTenderInfoVO.setUpdateTime(new Date());
                // 出借人详情需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
                result = this.nifaTenderInfoMessageService.selectDualNifaTenderInfo(borrow, borrowTenderVO, userInfoVO, nifaTenderInfoVO);
                if (!result) {
                    throw new Exception(logHeader + " 该出借人信息处理错误。" + msgBody + "，用户ID：" + borrowTenderVO.getUserId());
                }
                // 保存借款详情
                result = this.nifaTenderInfoMessageService.insertNifaTenderInfo(nifaTenderInfoVO);
                if (!result) {
                    throw new Exception(logHeader + " 该出借人信息处理错误。" + msgBody + "，用户ID：" + borrowTenderVO.getUserId());
                }
            }
            // --> 开始处理借款信息
            if (null == nifaBorrowInfoVO) {
                nifaBorrowInfoVO = new NifaBorrowInfoVO();
            }
            // 初始化数据类型
            // 借款详情处理
            nifaBorrowInfoVO.setMessage(msgBody);
            // 报送状态初始化
            nifaBorrowInfoVO.setReportStatus("0");
            // 数据做成时间
            nifaBorrowInfoVO.setCreateTime(new Date());
            nifaBorrowInfoVO.setUpdateTime(new Date());
            // 借款详情没有上送过的重新编辑保存
            // 借款详情需要获取到借款人的逾期次数、该次数从借款主体的用户表取得
            result = this.nifaTenderInfoMessageService.selectDualNifaBorrowInfo(historyData, borrow, borrowRepay, borrowRecoverList, recoverFee, lateCounts, nifaBorrowInfoVO);
            if (!result) {
                throw new Exception(logHeader + "该借款详情信息处理错误！！" + msgBody);
            }
            // 保存借款详情
            result = this.nifaTenderInfoMessageService.insertNifaBorrowInfo(nifaBorrowInfoVO);
            if (!result) {
                throw new Exception(logHeader + "该借款详情信息插入失败！！" + msgBody);
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
        logger.info("====NifaTenderInfoMessage consumer=====");
    }
}
