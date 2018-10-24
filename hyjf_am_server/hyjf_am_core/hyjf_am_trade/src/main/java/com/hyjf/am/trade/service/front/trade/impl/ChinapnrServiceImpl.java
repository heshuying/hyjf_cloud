/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.front.trade.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.trade.ChinaPnrWithdrawRequest;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.mq.producer.AppMessageProducer;
import com.hyjf.am.trade.mq.producer.SmsProducer;
import com.hyjf.am.trade.service.front.trade.ChinapnrService;
import com.hyjf.am.trade.service.impl.BaseServiceImpl;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.ChinapnrBeanVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.http.URLCodec;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhangqingqing
 * @version ChinapnrServiceImpl, v0.1 2018/9/8 10:12
 */
@Service
public class ChinapnrServiceImpl extends BaseServiceImpl implements ChinapnrService {

    @Autowired
    private SmsProducer smsProducer;

    @Autowired
    AppMessageProducer appMessageProducer;

    @Override
    public ChinapnrExclusiveLogWithBLOBs selectChinapnrExclusiveLog(long id) {
        return chinapnrExclusiveLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateChinapnrExclusiveLog(ChinapnrExclusiveLogWithBLOBs record) {
        ChinapnrExclusiveLogExample example = new ChinapnrExclusiveLogExample();
        example.createCriteria().andIdEqualTo(record.getId()).andUpdatetimeEqualTo(record.getUpdatetime()).andUpdateuserNotEqualTo("callback2");
        record.setUpdatetime(String.valueOf(GetDate.getMyTimeInMillis()));
        record.setUpdateuser("callback2");
        return chinapnrExclusiveLogMapper.updateByExampleSelective(record, example);
    }

    @Override
    public List<ChinapnrLog> getChinapnrLog(String ordId) {
        List<String> respCode = new ArrayList<String>();
        respCode.add(ChinaPnrConstant.RESPCODE_SUCCESS);
        respCode.add(ChinaPnrConstant.RESPCODE_CHECK);
        ChinapnrLogExample example = new ChinapnrLogExample();
        example.createCriteria().andOrdidEqualTo(ordId).andMsgTypeEqualTo("Cash").andRespCodeIn(respCode);
        example.setOrderByClause(" resp_code ");
        List<ChinapnrLog> list = chinapnrLogMapper.selectByExampleWithBLOBs(example);
        return list;
    }

    /**
     * 更新提现表
     *
     * @param ordId
     * @return
     */
    @Override
    public int updateAccountWithdrawByOrdId(String ordId, String reason) {
        AccountWithdraw record = new AccountWithdraw();
        record.setReason(reason);
        AccountWithdrawExample accountWithdrawExample = new AccountWithdrawExample();
        accountWithdrawExample.createCriteria().andNidEqualTo(ordId);
        int ret = this.accountWithdrawMapper.updateByExampleSelective(record, accountWithdrawExample);
        return ret;
    }

    /**
     * 更新检证状态
     *
     * @return
     */
    @Override
    public int updateChinapnrExclusiveLogStatus(long uuid, String status) {
        ChinapnrExclusiveLogWithBLOBs record = new ChinapnrExclusiveLogWithBLOBs();
        record.setId(uuid);
        record.setStatus(status);
        return chinapnrExclusiveLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public boolean updateAfterCash(ChinaPnrWithdrawRequest chinaPnrWithdrawRequest) {
        ChinapnrBeanVO bean = chinaPnrWithdrawRequest.getChinapnrBean();
        // 用户ID
        int userId = chinaPnrWithdrawRequest.getUserId();
        // 当前时间
        Date nowTime = GetDate.getNowTime2();
        // 订单号
        String ordId = bean.getOrdId() == null ? bean.get(ChinaPnrConstant.PARAM_ORDID) : bean.getOrdId();
        AccountWithdrawExample accountWithdrawExample = new AccountWithdrawExample();
        AccountWithdrawExample.Criteria crt = accountWithdrawExample.createCriteria();
        crt.andNidEqualTo(ordId);
        List<AccountWithdraw> listAccountWithdraw = accountWithdrawMapper.selectByExample(accountWithdrawExample);
        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            // 提现信息
            AccountWithdraw accountWithdraw = listAccountWithdraw.get(0);
            // 提现状态
            int withdrawStatus = accountWithdraw.getStatus();
            // 返回值=000成功
            if (ChinaPnrConstant.RESPCODE_SUCCESS.equals(bean.getRespCode())) {
                logger.info("返回值=000成功");
                // 如果信息未被处理
                if (withdrawStatus == CustomConstants.WITHDRAW_STATUS_DEFAULT) {
                    // 开启事务
                    TransactionStatus txStatus = null;
                    try {
                        txStatus = this.transactionManager.getTransaction(transactionDefinition);
                        // 支出金额
                        BigDecimal transAmt = stringToBigDecimal(bean.getTransAmt());
                        // 提现金额
                        BigDecimal realTansAmt = stringToBigDecimal(bean.getRealTransAmt());
                        // 提现手续费
                        BigDecimal feeAmt = transAmt.subtract(realTansAmt);
                        // 更新手续费
                        accountWithdraw.setFee(feeAmt.toString());
                        // 更新到账金额
                        accountWithdraw.setCredited(realTansAmt);
                        // 更新到总额
                        accountWithdraw.setTotal(transAmt);
                        // 4:成功
                        accountWithdraw.setStatus(CustomConstants.WITHDRAW_STATUS_SUCCESS);
                        accountWithdraw.setUpdateTime(nowTime);
                        accountWithdraw.setAccount(bean.getOpenAcctId());
                        accountWithdraw.setBank(bean.getOpenBankId());
                        accountWithdraw.setReason("");
                        crt.andStatusEqualTo(withdrawStatus);
                        // 更新订单信息
                        boolean withdrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                        if (!withdrawFlag) {
                            throw new RuntimeException("提现更新withdraw失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 更新账户信息
                        Account account = new Account();
                        account.setUserId(userId);
                        // 总资产total减少
                        account.setTotal(transAmt);
                        // 可用余额balance减少
                        account.setBalance(transAmt);
                        // 总收入total增加
                        //account.setExpend(realTansAmt);
                        // 版本控制(防止事物重复提交)
                        boolean accountFlag = this.adminAccountCustomizeMapper.updateUserWithdrawSuccess(account) > 0 ? true : false;
                        // 更新用户账户信息
                        if (!accountFlag) {
                            throw new RuntimeException("提现更新account失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        account = this.getAccount(userId);
                        // 写入收支明细
                        AccountList accountList = new AccountList();
                        accountList.setBankAwait(account.getBankAwait());
                        accountList.setBankAwaitCapital(account.getBankAwaitCapital());
                        accountList.setBankAwaitInterest(account.getBankAwaitInterest());
                        accountList.setBankBalance(account.getBankBalance());
                        accountList.setBankFrost(account.getBankFrost());
                        accountList.setBankInterestSum(account.getBankInterestSum());
                        accountList.setBankTotal(account.getBankTotal());
                        accountList.setBankWaitCapital(account.getBankWaitCapital());
                        accountList.setBankWaitInterest(account.getBankWaitInterest());
                        accountList.setBankWaitRepay(account.getBankWaitRepay());
                        //汇计划账户可用余额
                        accountList.setPlanBalance(account.getPlanBalance());
                        accountList.setPlanFrost(account.getPlanFrost());
                        accountList.setCheckStatus(1);
                        accountList.setTradeStatus(1);
                        accountList.setIsBank(0);
                        accountList.setNid(ordId);
                        accountList.setUserId(userId);
                        accountList.setAmount(transAmt);
                        accountList.setType(2);
                        accountList.setTrade("cash_success");
                        accountList.setTradeCode("balance");
                        accountList.setTotal(account.getTotal());
                        accountList.setBalance(account.getBalance());
                        accountList.setFrost(account.getFrost());
                        accountList.setAwait(account.getAwait());
                        accountList.setRepay(account.getRepay());
                        accountList.setRemark("网站提现");
                        accountList.setCreateTime(nowTime);
                        accountList.setOperator(String.valueOf(userId));
                        accountList.setIp(chinaPnrWithdrawRequest.getIp());
                        accountList.setWeb(0);
                        boolean accountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                        if (!accountListFlag) {
                            throw new RuntimeException("提现插入交易明细accountList失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 提交事务
                        this.transactionManager.commit(txStatus);
                        UserVO users = chinaPnrWithdrawRequest.getUser();
                        // 可以发送提现短信时
                        Map<String, String> replaceMap = new HashMap<String, String>();
                        replaceMap.put("val_amount", transAmt.toString());
                        replaceMap.put("val_fee", feeAmt.toString());
                        UserInfoVO info = chinaPnrWithdrawRequest.getUserInfo();
                        replaceMap.put("val_name", info.getTruename().substring(0, 1));
                        replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
                        if (users != null && users.getWithdrawSms() != null && users.getWithdrawSms() == 0) {
                            // 替换参数
                            SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_TIXIAN_SUCCESS,
                                    CustomConstants.CHANNEL_TYPE_NORMAL);
                            AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                            // 发送
                            smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
                            appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(appMsMessage)));
                        } else {
                            // 替换参数
                            AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                            appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(appMsMessage)));
                        }
                        return true;
                    } catch (Exception e) {
                        // 回滚事务
                        transactionManager.rollback(txStatus);
                        e.printStackTrace();
                    }
                }
                // 返回正常(000), 且当前表的状态为1:处理中
                else if (withdrawStatus == CustomConstants.WITHDRAW_STATUS_WAIT) {
                    // 开启事务
                    TransactionStatus txStatus = null;
                    try {
                        txStatus = this.transactionManager.getTransaction(transactionDefinition);
                        // 支出金额
                        BigDecimal transAmt = stringToBigDecimal(bean.getTransAmt());
                        // 更新状态为成功
                        accountWithdraw.setStatus(CustomConstants.WITHDRAW_STATUS_SUCCESS);
                        accountWithdraw.setUpdateTime(nowTime);
                        accountWithdraw.setAccount(bean.getOpenAcctId());
                        accountWithdraw.setBank(bean.getOpenBankId());
                        accountWithdraw.setReason("");
                        crt.andStatusEqualTo(withdrawStatus);
                        boolean accountWithDrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                        if (!accountWithDrawFlag) {
                            throw new RuntimeException("提现更新accountwithdraw失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 更新账户信息
                        Account account = this.getAccount(userId);
                        // 写入收支明细
                        AccountList accountList = new AccountList();
                        accountList.setBankAwait(account.getBankAwait());
                        accountList.setBankAwaitCapital(account.getBankAwaitCapital());
                        accountList.setBankAwaitInterest(account.getBankAwaitInterest());
                        accountList.setBankBalance(account.getBankBalance());
                        accountList.setBankFrost(account.getBankFrost());
                        accountList.setBankInterestSum(account.getBankInterestSum());
                        accountList.setBankTotal(account.getBankTotal());
                        accountList.setBankWaitCapital(account.getBankWaitCapital());
                        accountList.setBankWaitInterest(account.getBankWaitInterest());
                        accountList.setBankWaitRepay(account.getBankWaitRepay());
                        //汇计划账户可用余额
                        accountList.setPlanBalance(account.getPlanBalance());
                        accountList.setPlanFrost(account.getPlanFrost());
                        accountList.setCheckStatus(1);
                        accountList.setTradeStatus(1);
                        accountList.setIsBank(0);
                        accountList.setNid(ordId);
                        accountList.setUserId(userId);
                        accountList.setAmount(transAmt);
                        accountList.setType(2);
                        accountList.setTrade("cash_success");
                        accountList.setTradeCode("balance");
                        accountList.setTotal(account.getTotal());
                        accountList.setBalance(account.getBalance());
                        accountList.setFrost(account.getFrost());
                        accountList.setAwait(account.getAwait());
                        accountList.setRepay(account.getRepay());
                        accountList.setRemark("网站提现");
                        accountList.setCreateTime(nowTime);
                        accountList.setOperator(String.valueOf(chinaPnrWithdrawRequest.getUserId()));
                        accountList.setIp(chinaPnrWithdrawRequest.getIp());
                        accountList.setWeb(0);
                        boolean accountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                        if (!accountListFlag) {
                            throw new RuntimeException("提现插入交易明细accountList失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 提交事务
                        this.transactionManager.commit(txStatus);
                        return true;
                    } catch (Exception e) {
                        // 回滚事务
                        transactionManager.rollback(txStatus);
                        e.printStackTrace();
                    }
                }
            }
            // 如果返回的是处理中/999审核中
            else if (ChinaPnrConstant.RESPCODE_CHECK.equals(bean.getRespCode())) {
                // 如果是首次返回
                if (withdrawStatus == CustomConstants.WITHDRAW_STATUS_DEFAULT) {
                    // 开启事务
                    TransactionStatus txStatus = null;
                    try {
                        txStatus = this.transactionManager.getTransaction(transactionDefinition);
                        // 支出金额
                        BigDecimal transAmt = stringToBigDecimal(bean.getTransAmt());
                        // 提现金额
                        BigDecimal realTansAmt = stringToBigDecimal(bean.getRealTransAmt());
                        // 提现手续费
                        BigDecimal feeAmt = transAmt.subtract(realTansAmt);
                        // 更新订单信息
                        // 更新手续费
                        accountWithdraw.setFee(feeAmt.toString());
                        // 更新到账金额
                        accountWithdraw.setCredited(realTansAmt);
                        // 更新到总额
                        accountWithdraw.setTotal(transAmt);
                        // 1:审核中
                        accountWithdraw.setStatus(CustomConstants.WITHDRAW_STATUS_WAIT);
                        accountWithdraw.setUpdateTime(nowTime);
                        accountWithdraw.setAccount(bean.getOpenAcctId());
                        accountWithdraw.setBank(bean.getOpenBankId());
                        accountWithdraw.setReason("");
                        crt.andStatusEqualTo(withdrawStatus);
                        boolean accountWithdrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                        if (!accountWithdrawFlag) {
                            throw new RuntimeException("提现更新accountwithdraw失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 更新账户信息
                        Account account = new Account();
                        account.setUserId(userId);
                        // 总资产total减少
                        account.setTotal(transAmt);
                        // 可用余额balance减少
                        account.setBalance(transAmt);
                        // 版本控制(防止事物重复提交)
                        boolean accountFlag = this.adminAccountCustomizeMapper.updateUserWithdrawSuccess(account) > 0 ? true : false;
                        // 更新用户账户信息
                        if (!accountFlag) {
                            throw new RuntimeException("提现更新account失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 提交事务
                        this.transactionManager.commit(txStatus);
                        UserVO users = chinaPnrWithdrawRequest.getUser();
                        // 可以发送提现短信时
                        Map<String, String> replaceMap = new HashMap<String, String>();
                        replaceMap.put("val_amount", transAmt.toString());
                        replaceMap.put("val_fee", feeAmt.toString());
                        UserInfoVO info = chinaPnrWithdrawRequest.getUserInfo();
                        replaceMap.put("val_name", info.getTruename().substring(0, 1));
                        replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
                        if (users != null && users.getWithdrawSms() != null && users.getWithdrawSms() == 0) {
                            // 替换参数
                            SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_TIXIAN_SUCCESS,
                                    CustomConstants.CHANNEL_TYPE_NORMAL);
                            AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                            // 发送
                            smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
                            appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(appMsMessage)));
                        } else {
                            // 替换参数
                            AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                            appMessageProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(appMsMessage)));
                        }
                        return true;
                    } catch (Exception e) {
                        // 回滚事务
                        transactionManager.rollback(txStatus);
                        e.printStackTrace();
                    }
                }
                // 已经是处理中
                else {
                    return true;
                }
            }
            // 提现失败
            else {
                System.out.println("提现失败异步回调，订单号：" + ordId);
                // 开启事务
                TransactionStatus txStatus = null;
                try {
                    txStatus = this.transactionManager.getTransaction(transactionDefinition);
                    // 支出金额
                    BigDecimal transAmt = stringToBigDecimal(bean.getTransAmt());
                    // 提现金额
                    BigDecimal realTansAmt = stringToBigDecimal(bean.getRealTransAmt());
                    // 提现手续费
                    BigDecimal feeAmt = transAmt.subtract(realTansAmt);
                    // 如果信息已被处理(400)
                    if (withdrawStatus == CustomConstants.WITHDRAW_STATUS_SUCCESS) {
                        // 更新为失败状态
                        // 失败
                        accountWithdraw.setStatus(CustomConstants.WITHDRAW_STATUS_FAIL);
                        accountWithdraw.setUpdateTime(nowTime);
                        accountWithdraw.setReason("");
                        crt.andStatusEqualTo(withdrawStatus);
                        boolean accountWithDrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                        if (!accountWithDrawFlag) {
                            throw new RuntimeException("提现更新accountwithdraw失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        Account account = new Account();
                        account.setUserId(userId);
                        // 总资产total增加
                        account.setTotal(transAmt);
                        // 可用余额balance增加
                        account.setBalance(transAmt);
                        // 版本控制
                        boolean accountFlag = this.adminAccountCustomizeMapper.updateUserWithdrawFail(account) > 0 ? true : false;
                        // 更新用户账户信息
                        if (!accountFlag) {
                            throw new RuntimeException("提现更新account失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        account = this.getAccount(userId);
                        // 写入收支明细
                        AccountList accountList = new AccountList();
                        accountList.setBankAwait(account.getBankAwait());
                        accountList.setBankAwaitCapital(account.getBankAwaitCapital());
                        accountList.setBankAwaitInterest(account.getBankAwaitInterest());
                        accountList.setBankBalance(account.getBankBalance());
                        accountList.setBankFrost(account.getBankFrost());
                        accountList.setBankInterestSum(account.getBankInterestSum());
                        accountList.setBankTotal(account.getBankTotal());
                        accountList.setBankWaitCapital(account.getBankWaitCapital());
                        accountList.setBankWaitInterest(account.getBankWaitInterest());
                        accountList.setBankWaitRepay(account.getBankWaitRepay());
                        //汇计划账户可用余额
                        accountList.setPlanBalance(account.getPlanBalance());
                        accountList.setPlanFrost(account.getPlanFrost());
                        accountList.setCheckStatus(1);
                        accountList.setTradeStatus(1);
                        accountList.setIsBank(0);
                        accountList.setNid(ordId);
                        accountList.setUserId(userId);
                        accountList.setAmount(transAmt);
                        accountList.setType(1);
                        accountList.setTrade("cash_false");
                        accountList.setTradeCode("balance");
                        accountList.setTotal(account.getTotal());
                        accountList.setBalance(account.getBalance());
                        accountList.setFrost(account.getFrost());
                        accountList.setAwait(account.getAwait());
                        accountList.setRepay(account.getRepay());
                        accountList.setRemark("网站提现");
                        accountList.setCreateTime(nowTime);
                        accountList.setOperator(String.valueOf(chinaPnrWithdrawRequest.getUserId()));
                        accountList.setIp(chinaPnrWithdrawRequest.getIp());
                        accountList.setWeb(0);
                        boolean accountListFlag = this.accountListMapper.insertSelective(accountList) > 0 ? true : false;
                        if (!accountListFlag) {
                            throw new RuntimeException("提现插入交易明细accountList失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 提交事务
                        this.transactionManager.commit(txStatus);
                    } else if (withdrawStatus == CustomConstants.WITHDRAW_STATUS_WAIT) {
                        // 更新为失败状态
                        accountWithdraw.setStatus(CustomConstants.WITHDRAW_STATUS_FAIL);
                        accountWithdraw.setUpdateTime(nowTime);
                        accountWithdraw.setReason("");
                        crt.andStatusEqualTo(withdrawStatus);
                        boolean accountWithDrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                        if (!accountWithDrawFlag) {
                            throw new RuntimeException("提现更新accountwithdraw失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        Account account = new Account();
                        account.setUserId(userId);
                        // 总资产total增加
                        account.setTotal(transAmt);
                        // 可用余额balance增加
                        account.setBalance(transAmt);
                        // 版本控制
                        boolean accountFlag = this.adminAccountCustomizeMapper.updateUserWithdrawFail(account) > 0 ? true : false;
                        // 更新用户账户信息
                        if (!accountFlag) {
                            throw new RuntimeException("提现更新account失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 提交事务
                        this.transactionManager.commit(txStatus);
                    } else {
                        // 失敗原因
                        String reason = bean.getRespDesc();
                        accountWithdraw.setStatus(CustomConstants.WITHDRAW_STATUS_FAIL);// 失败
                        accountWithdraw.setFee(feeAmt.toString()); // 更新手续费
                        accountWithdraw.setCredited(realTansAmt); // 更新到账金额
                        accountWithdraw.setTotal(transAmt); // 更新到总额
                        accountWithdraw.setAccount(bean.getOpenAcctId()); // 提现银行卡号
                        accountWithdraw.setBank(bean.getOpenBankId()); // 提现银行
                        accountWithdraw.setUpdateTime(nowTime);
                        crt.andStatusEqualTo(withdrawStatus);
                        if (StringUtils.isNotEmpty(reason)) {
                            if (reason.contains("%")) {
                                reason = URLCodec.decodeURL(reason);
                            }
                        }
                        accountWithdraw.setReason(reason);
                        boolean accountWithdrawFlag = this.accountWithdrawMapper.updateByExampleSelective(accountWithdraw, accountWithdrawExample) > 0 ? true : false;
                        if (!accountWithdrawFlag) {
                            throw new RuntimeException("提现更新accountwithdraw失败,用户userId：" + userId + ",提现金额：" + transAmt);
                        }
                        // 提交事务
                        this.transactionManager.commit(txStatus);
                    }
                } catch (Exception e) {
                    // 回滚事务
                    transactionManager.rollback(txStatus);
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public BigDecimal stringToBigDecimal(String value) {
        if (Validator.isNotNull(value) && NumberUtils.isNumber(value)) {
            return new BigDecimal(value);
        }else {
            return BigDecimal.ZERO;
        }
    }
}
