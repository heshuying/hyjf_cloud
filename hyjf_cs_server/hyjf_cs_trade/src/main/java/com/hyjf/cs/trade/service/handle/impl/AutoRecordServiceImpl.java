/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.trade.service.handle.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowRegistRequest;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.STZHWhiteListVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.trade.hjh.HjhAssetBorrowTypeVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanAssetVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.ApiAssetClient;
import com.hyjf.cs.trade.client.AutoRecordClient;
import com.hyjf.cs.trade.client.AutoSendClient;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.AutoPreAuditProducer;
import com.hyjf.cs.trade.mq.producer.SmsProducer;
import com.hyjf.cs.trade.service.handle.AutoRecordService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;

/**
 * @author fuqiang
 * @version AutoRecordServiceImpl, v0.1 2018/6/14 10:17
 */
@Service
public class AutoRecordServiceImpl extends BaseTradeServiceImpl implements AutoRecordService {

    private static final Logger _log = LoggerFactory.getLogger(AutoRecordServiceImpl.class);

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    private AutoRecordClient autoRecordClient;

    @Autowired
    private ApiAssetClient apiAssetClient;

    @Autowired
    private SmsProducer smsProducer;

    @Autowired
    private AutoPreAuditProducer autoPreAuditProducer;

    @Override
    public boolean updateRecordBorrow(HjhPlanAssetVO hjhPlanAssetVO, HjhAssetBorrowTypeVO hjhAssetBorrowTypeVO) {
        // 备案，需要更新资产表
        JSONObject jsonObject = debtRegist(hjhPlanAssetVO.getBorrowNid());

        //更新资产表为初审中
        if ("0".equals(jsonObject.get("success"))) {

            HjhPlanAssetVO hjhPlanAssetnew = new HjhPlanAssetVO();
            hjhPlanAssetnew.setId(hjhPlanAssetVO.getId());

            // 受托支付，更新为待授权
            if (hjhPlanAssetVO.getEntrustedFlg() != null && hjhPlanAssetVO.getEntrustedFlg().intValue() == 1) {
                hjhPlanAssetnew.setStatus(4);//4 待授权
            } else {
                hjhPlanAssetnew.setStatus(5);//初审中
            }
            //获取当前时间
            int nowTime = GetDate.getNowTime10();
            hjhPlanAssetnew.setUpdateTime(nowTime);
            hjhPlanAssetnew.setUpdateUserId(1);
            boolean borrowFlag = this.autoSendClient.updateHjhPlanAssetnew(hjhPlanAssetnew) > 0 ? true : false;
            if (borrowFlag) {
                return true;
            }

            // 重复失败的情况
        } else if ("2".equals(jsonObject.get("success"))) {
            _log.info("备案失败 " + hjhPlanAssetVO.getBorrowNid() + " 原因：" + jsonObject.get("msg"));
        } else {
            _log.info("备案失败 " + hjhPlanAssetVO.getBorrowNid() + " 原因：" + jsonObject.get("msg"));
            // 备案失败发送短信//TODO: 没有配合模板
            // 您好，有一笔标的备案失败，请及时关注！
            Map<String, String> replaceStrs = new HashMap<String, String>();
            replaceStrs.put("val_title", hjhPlanAssetVO.getBorrowNid());
            SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null, MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_NOTICE_BORROW_RECORD_FAIL, CustomConstants.CHANNEL_TYPE_NORMAL);
            try {
                smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(smsMessage)));
            } catch (MQException e) {
                _log.error("短信发送失败...", e);
            }
        }


        return false;
    }

    @Override
    public void sendToMQ(HjhPlanAssetVO hjhPlanAssetVO, String borrowPreauditGroup) {
        // 加入到消息队列
        JSONObject params = new JSONObject();
        params.put("mqMsgId", GetCode.getRandomCode(10));
        params.put("assetId", hjhPlanAssetVO.getAssetId());
        params.put("instCode", hjhPlanAssetVO.getInstCode());
        try {
            autoPreAuditProducer.messageSend(new MessageContent(MQConstant.ROCKETMQ_BORROW_PREAUDIT_TOPIC,UUID.randomUUID().toString(), JSONObject.toJSONBytes(params)));
        } catch (MQException e) {
            e.printStackTrace();
            _log.error("自动初审发送消息失败...", e);
        }
    }

    /**
     * 备案
     *
     * @param borrowNid
     * @return
     */
    public JSONObject debtRegist(String borrowNid) {
        // 返回结果
        JSONObject result = new JSONObject();
        // 获取相应的标的详情
        /*BorrowExample borrowExample = new BorrowExample();
        BorrowExample.Criteria borrowCra = borrowExample.createCriteria();
        borrowCra.andBorrowNidEqualTo(borrowNid);
        List<BorrowWithBLOBsVO> borrowList = this.borrowMapper.selectByExampleWithBLOBs(borrowExample);// 获取相应的标的信息*/
        BorrowVO borrowVO = autoRecordClient.selectBorrowByNid(borrowNid);
        if (borrowVO != null) {
            // 检查是否备案失败，如果是，跳过
            if (borrowVO.getStatus() == 0 && borrowVO.getRegistStatus() == 4) {
                _log.info("标的：" + borrowNid + " 自动备案失败过");
                result.put("success", "2");
                result.put("msg", "自动备案失败过！");
                return result;
            }


            String borrowStyle = borrowVO.getBorrowStyle();// 項目还款方式
            // 是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            int userId = borrowVO.getUserId();// 借款人userId
            UserVO user = apiAssetClient.selectUsersById(userId);// 借款人用户
            if (Validator.isNotNull(user)) {
                BankOpenAccountVO bankOpenAccount = apiAssetClient.selectBankAccountById(userId);
                if (Validator.isNotNull(bankOpenAccount)) {
                    // 更新相应的标的状态为备案中
                    BorrowRegistRequest req = new BorrowRegistRequest(borrowVO, 0, 1);
                    boolean debtRegistingFlag = autoRecordClient.updateBorrowRegist(req);
                    if (debtRegistingFlag) {
                        // 获取共同参数
                        String channel = BankCallConstant.CHANNEL_PC;
                        String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());
                        String orderDate = GetOrderIdUtils.getOrderDate();
                        String txDate = GetOrderIdUtils.getTxDate();
                        String txTime = GetOrderIdUtils.getTxTime();
                        String seqNo = GetOrderIdUtils.getSeqNo(6);
                        // 调用开户接口
                        BankCallBean debtRegistBean = new BankCallBean();
                        debtRegistBean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
                        debtRegistBean.setTxCode(BankCallConstant.TXCODE_DEBT_REGISTER);// 消息类型(用户开户)
                        debtRegistBean.setTxDate(txDate);
                        debtRegistBean.setTxTime(txTime);
                        debtRegistBean.setSeqNo(seqNo);
                        debtRegistBean.setChannel(channel);
                        debtRegistBean.setAccountId(bankOpenAccount.getAccount());// 借款人电子账号
                        debtRegistBean.setProductId(borrowNid);// 标的表id
                        debtRegistBean.setProductDesc(borrowVO.getName());// 标的名称
                        debtRegistBean.setRaiseDate(borrowVO.getBankRaiseStartDate());// 募集日,标的保存时间
                        debtRegistBean.setRaiseEndDate(borrowVO.getBankRaiseEndDate());// 募集结束日期
                        if (isMonth) {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_UNCERTAINDATE);// 付息方式没有不确定日期
                        } else {
                            debtRegistBean.setIntType(BankCallConstant.DEBT_INTTYPE_EXPIREDATE);
                        }
                        debtRegistBean.setDuration(String.valueOf(borrowVO.getBankBorrowDays()));// (借款期限,天数）
                        debtRegistBean.setTxAmount(String.valueOf(borrowVO.getAccount()));// 交易金额
                        debtRegistBean.setRate(String.valueOf(borrowVO.getBorrowApr()));// 年华利率

                        // 受托支付
						/*receiptAccountId	收款人电子帐户	A	19	C	当entrustFlag不为空时必填
						entrustFlag	受托支付标志	A	1	C	为空时单一借款人模式
						0：非受托支付业务类别
						1：受托支付业务类别*/
                        if (borrowVO.getEntrustedFlg() != null && borrowVO.getEntrustedFlg().intValue() == 1) {
                            STZHWhiteListVO stzhWhiteListVO = autoRecordClient.selectSTZHWhiteList(borrowVO.getEntrustedUserId(), borrowVO.getInstCode());

                            if (stzhWhiteListVO != null) {
                                debtRegistBean.setEntrustFlag(borrowVO.getEntrustedFlg().toString());
                                debtRegistBean.setReceiptAccountId(stzhWhiteListVO.getStAccountid());
                            }

                        }

                        if (Validator.isNotNull(borrowVO.getRepayOrgUserId())) {
                            BankOpenAccountVO account = this.apiAssetClient.selectBankAccountById(borrowVO.getRepayOrgUserId());
                            if (Validator.isNotNull(account)) {
                                debtRegistBean.setBailAccountId(account.getAccount());
                            }
                        }
                        debtRegistBean.setLogOrderId(orderId);
                        debtRegistBean.setLogOrderDate(orderDate);
                        debtRegistBean.setLogUserId(String.valueOf(user.getUserId()));
                        debtRegistBean.setLogRemark("借款人标的登记");
                        debtRegistBean.setLogClient(0);
                        try {
                            BankCallBean registResult = BankCallUtils.callApiBg(debtRegistBean);
                            String retCode = StringUtils.isNotBlank(registResult.getRetCode()) ? registResult.getRetCode() : "";
                            if (BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {

                                // 如果是受托支付 备案成功时更改状态
                                int status = 1;
                                if (borrowVO.getEntrustedFlg() != null && borrowVO.getEntrustedFlg().intValue() == 1) {
                                    status = 7;
                                }

                                boolean debtRegistedFlag = autoRecordClient.updateBorrowRegist(new BorrowRegistRequest(borrowVO, status, 2));
                                if (debtRegistedFlag) {
                                    result.put("success", "0");
                                    result.put("msg", "备案成功！");
                                } else {
                                    result.put("success", "1");
                                    result.put("msg", "备案成功后，更新相应的状态失败,请联系客服！");
                                }
                            } else {
                                autoRecordClient.updateBorrowRegist(new BorrowRegistRequest(borrowVO, 0, 4));
                                String message = registResult.getRetMsg();
                                result.put("success", "1");
                                result.put("msg", StringUtils.isNotBlank(message) ? message : "银行备案接口调用失败！");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            autoRecordClient.updateBorrowRegist(new BorrowRegistRequest(borrowVO, 0, 4));
                            result.put("success", "1");
                            result.put("msg", "银行备案接口调用失败！");
                        }
                    } else {
                        result.put("success", "1");
                        result.put("msg", "更新相应的标的信息失败,请稍后再试！");
                    }
                }
            } else {
                result.put("success", "1");
                result.put("msg", "借款人信息错误！");
            }
        } else {
            result.put("success", "1");
            result.put("msg", "项目编号为空！");
            _log.info("标的：" + borrowNid + " 项目编号为空");
        }
        return result;
    }

}
