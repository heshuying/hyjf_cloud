package com.hyjf.cs.trade.service.consumer.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.admin.CouponRepayRequest;
import com.hyjf.am.vo.admin.BankMerchantAccountVO;
import com.hyjf.am.vo.admin.TransferExceptionLogVO;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.BankMerchantAccountListVO;
import com.hyjf.am.vo.trade.borrow.BorrowAndInfoVO;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.trade.coupon.CouponTenderCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.consumer.CouponRepayService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/17 16:45
 * @Description: CouponRepayServiceImpl
 */
@Service
public class CouponRepayServiceImpl implements CouponRepayService {
    private static final Logger logger = LoggerFactory.getLogger(CouponRepayServiceImpl.class);
    @Autowired
    private CommonProducer commonProducer;

    @Resource
    private AmUserClient amUserClient;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmTradeClient borrowClient;

    /**
     * 用户ID
     */
    private static final String USERID = "userId";
    /**
     * 还款金额(优惠券用)
     */
    private static final String VAL_AMOUNT = "val_amount";
    /**
     * 优惠券类型
     */
    private static final String VAL_COUPON_TYPE = "val_coupon_type";
    /**
     * 优惠券还款类别 1：直投类
     */
    private static final Integer RECOVER_TYPE_HZT = 1;
    /**
     * 优惠券还款类别 2：汇添金
     */
    private static final Integer RECOVER_TYPE_HTJ = 2;

    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderListHjh(String orderId) {
        return borrowClient.getCouponTenderListHjh(orderId);
    }

    @Override
    public void updateCouponRecoverHjh(String planNid, CouponTenderCustomizeVO ct) throws Exception {
        String methodName = "updateCouponRecoverHtj";
        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
        Map<String, String> msg = new HashMap<String, String>();
        retMsgList.add(msg);
        /** 基本变量 */
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        /** 标的基本数据 */
        // 出借人用户ID
        Integer tenderUserId = null;
        // 出借人用户ID
        tenderUserId = Integer.valueOf(ct.getUserId());
        String couponTenderNid = ct.getOrderId();
        // 取得优惠券出借信息
        BorrowTenderCpnVO borrowTenderCpn = this.getCouponTenderInfo(couponTenderNid);
        // 出借人在银行存管的账户信息
        BankOpenAccountVO bankOpenAccountInfo = this.getBankOpenAccount(tenderUserId);
        if (bankOpenAccountInfo == null) {
            throw new Exception("出借人未开户。[用户ID：" + tenderUserId + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
        }
        // 当前还款
        CouponRecoverCustomizeVO currentRecover = this.getCurrentCouponRecover(couponTenderNid, 1);
        if (currentRecover == null) {
            logger.info("优惠券还款数据不存在。[计划编号：" + planNid + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
            return;
        }
        // 应还利息
        String recoverInterestStr = StringUtils.isEmpty(currentRecover.getRecoverInterest()) ? "0.00" : currentRecover.getRecoverInterest();
        // 应还本息
        String recoverAccountStr = StringUtils.isEmpty(currentRecover.getRecoverAccount()) ? "0.00" : currentRecover.getRecoverAccount();
        // 应还本金
        String recoverCapitalStr = StringUtils.isEmpty(currentRecover.getRecoverCapital()) ? "0.00" : currentRecover.getRecoverCapital();
        BigDecimal recoverInterest = new BigDecimal(recoverInterestStr);
        BigDecimal recoverAccount = new BigDecimal(recoverAccountStr);
        BigDecimal recoverCapital = new BigDecimal(recoverCapitalStr);
        CouponRecoverVO cr = new CouponRecoverVO();
        cr.setId(currentRecover.getId());
        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        BankCallBean resultBean = null;
        BankCallBean bean = new BankCallBean();
        if (new BigDecimal(recoverAccountStr).compareTo(BigDecimal.ZERO) != 0) {
            String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();

            bean.setVersion(BankCallConstant.VERSION_10);// 版本号
            bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
            bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
            bean.setSeqNo(seqNo);// 交易流水号
            bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
            bean.setAccountId(merrpAccount);// 电子账号
            bean.setTxAmount(CustomUtil.formatAmount(recoverAccountStr));
            bean.setForAccountId(bankOpenAccountInfo.getAccount());
            bean.setDesLineFlag("1");
            bean.setDesLine(orderId);
            bean.setLogOrderId(orderId);// 订单号
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(String.valueOf(tenderUserId));
            bean.setLogClient(0);// 平台

            int transferStatus = Integer.MIN_VALUE;
            try {
                resultBean = BankCallUtils.callApiBg(bean);
                if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                    // 转账失败
                    logger.info("转账失败..." + "[优惠券出借编号：" + couponTenderNid + "]");
                    cr.setReceivedFlg(4);
                    this.borrowClient.updateByPrimaryKeySelective(cr);
                    String errorMsg = StringUtils.EMPTY;
                    int type = 1;
                    if (currentRecover.getCouponType() == 1) {
                        errorMsg = "体验金收益自动领取失败";
                        type = 0;
                    } else if (currentRecover.getCouponType() == 2) {
                        errorMsg = "加息券收益自动领取失败";
                    } else if (currentRecover.getCouponType() == 3) {
                        errorMsg = "代金券收益自动领取失败";
                        type = 2;
                    }


                    if (resultBean != null && Validator.isNotNull(resultBean.getRetMsg())) {
                        errorMsg = resultBean.getRetMsg();
                    }

                    if (resultBean != null && (BankCallConstant.RETCODE_BIDAPPLY_YUE_FAIL.equals(resultBean.getRetCode()) || BankCallConstant.RETCODE_YUE_FAIL.equals(resultBean.getRetCode()))) {
                        errorMsg = "商户子账户余额不足，请先充值或向该子账户转账";
                        sendSmsFail(currentRecover.getCouponType());
                    }

                    transferStatus = 1;
                    // 插入转账异常
                    this.insertTransferExceptionLog(bean, resultBean, tenderUserId, recoverAccount, bankOpenAccountInfo.getAccount(), cr.getId(), transferStatus,
                            errorMsg, type);
                    logger.error(methodName, new Exception("转账失败！失败数据已插入转账异常表。errormsg:" + errorMsg));
                    return;

                }

            } catch (Exception e) {
                logger.error(methodName, "系统发生异常，更新异常转账表失败,事物回滚", e);
                throw new RuntimeException("系统发生异常，更新异常转账表失败,事物回滚!" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
        }

        // 判断该收支明细是否存在时,跳出本次循环
        if (countAccountListByNidCoupon(orderId) == 0) {
            // 更新账户信息(出借人)
            AccountVO account = new AccountVO();
            account.setUserId(tenderUserId);

            account.setBankBalance(recoverAccount); // 账户余额
            account.setBankInterestSum(recoverAccount);

            // 计划已还总利息
            account.setPlanRepayInterest(recoverAccount);
            // 计划待收收益
            account.setPlanInterestWait(recoverInterest);
            // 计划待收总额
            account.setPlanAccountWait(recoverAccount);
            // 更新用户计划账户
            int accountCnt = this.borrowClient.updateOfRepayCouponHjh(account);
            if (accountCnt == 0) {
                throw new RuntimeException("出借人资金记录(huiyingdai_account)更新失败！" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
            TimeUnit.SECONDS.sleep(1);//秒
            // 取得账户信息(出借人)
            account = this.borrowClient.getAccountByUserId(tenderUserId);
            if (account == null) {
                throw new RuntimeException("出借人账户信息不存在。[出借人ID：" + borrowTenderCpn.getUserId() + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
            // 写入收支明细
            AccountListVO accountList = dealAccountList(orderId, tenderUserId, account, bankOpenAccountInfo, seqNo, bean, recoverAccount, currentRecover, ct);
            int accountListCnt = insertAccountList(accountList);
            if (accountListCnt == 0) {
                throw new RuntimeException("收支明细(huiyingdai_account_list)写入失败！" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
        }
        // 更新出借表
        // 已收总额
        borrowTenderCpn.setRecoverAccountYes(borrowTenderCpn.getRecoverAccountYes().add(recoverAccount));
        // 已收本金
        borrowTenderCpn.setRecoverAccountCapitalYes(borrowTenderCpn.getRecoverAccountCapitalYes().add(recoverCapital));
        // 已收利息
        borrowTenderCpn.setRecoverAccountInterestYes(borrowTenderCpn.getRecoverAccountInterestYes().add(recoverInterest));
        // 待收总额
        borrowTenderCpn.setRecoverAccountWait(borrowTenderCpn.getRecoverAccountWait().subtract(recoverAccount));
        // 待收本金
        borrowTenderCpn.setRecoverAccountCapitalWait(borrowTenderCpn.getRecoverAccountCapitalWait().subtract(recoverCapital));
        // 待收利息
        borrowTenderCpn.setRecoverAccountInterestWait(borrowTenderCpn.getRecoverAccountInterestWait().subtract(recoverInterest));
        int borrowTenderCnt = borrowClient.updateBorrowTenderCpn(borrowTenderCpn);
        if (borrowTenderCnt == 0) {
            throw new RuntimeException("出借表(hyjf_borrow_tender_cpn)更新失败！" + "[优惠券出借编号：" + couponTenderNid + "]");
        }
        // 更新优惠券出借还款表
        // 转账订单编号
        cr.setTransferId(orderId);
        // 已还款
        cr.setRecoverStatus(1);
        // 收益领取状态(已领取)
        cr.setReceivedFlg(5);
        // 转账时间
        cr.setTransferTime(nowTime);
        // 已经还款时间
        cr.setRecoverYestime(nowTime);
        // 已还利息
        cr.setRecoverInterestYes(recoverInterest);
        // 已还本息
        cr.setRecoverAccountYes(recoverAccount);
        if (currentRecover.getCouponType() == 3) {
            // 代金券
            // 已还本金
            cr.setRecoverCapitalYes(recoverCapital);
        } else {
            // 体验金和加息券
            // 已还本金
            cr.setRecoverCapitalYes(BigDecimal.ZERO);
        }
        // 更新时间
        cr.setUpdateTime(new Date());
        // 更新用户
        cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_REPAY));
        // 通知用户
        cr.setNoticeFlg(1);
        this.borrowClient.updateByPrimaryKeySelective(cr);
        // 插入网站收支明细记录
        AccountWebListVO accountWebList = new AccountWebListVO();
        // 未分期
        accountWebList.setOrdid(borrowTenderCpn.getNid());// 订单号
        accountWebList.setUserId(tenderUserId); // 出借者
        accountWebList.setAmount(Double.valueOf(recoverAccount.toString())); // 优惠券出借受益
        accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出

        String tradeType = StringUtils.EMPTY;
        if (currentRecover.getCouponType() == 1) {
            // 体验金
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_TYJ);
            // 体验金收益
            tradeType = CustomConstants.TRADE_COUPON_SY;
        } else if (currentRecover.getCouponType() == 2) {
            // 加息券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_JXQ);
            // 加息券回款
            tradeType = CustomConstants.TRADE_COUPON_HK;
        } else if (currentRecover.getCouponType() == 3) {
            // 代金券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_DJQ);
            // 代金券回款
            tradeType = CustomConstants.TRADE_COUPON_DJ;
        }
        accountWebList.setTradeType(tradeType); // 加息券回款
        String remark = "计划编号：" + planNid + "<br />优惠券:" + ct.getCouponUserCode();
        accountWebList.setRemark(remark); // 出借编号
        accountWebList.setCreateTime(nowTime);
        int accountWebListCnt = insertAccountWebList(accountWebList);
        if (accountWebListCnt == 0) {
            throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[出借订单号：" + borrowTenderCpn.getNid() + "]");
        }

        // 添加红包账户明细
        BankMerchantAccountVO nowBankMerchantAccount = this.getBankMerchantAccount(resultBean.getAccountId());
        nowBankMerchantAccount.setAvailableBalance(nowBankMerchantAccount.getAvailableBalance().subtract(recoverAccount));
        nowBankMerchantAccount.setAccountBalance(nowBankMerchantAccount.getAccountBalance().subtract(recoverAccount));
        nowBankMerchantAccount.setUpdateTime(new Date());

        // 更新红包账户信息
        int updateCount = this.updateBankMerchantAccount(nowBankMerchantAccount);
        if (updateCount > 0) {
            UserInfoCustomizeVO userInfoCustomize = this.queryUserInfoByUserId(tenderUserId);

            // 添加红包明细
            BankMerchantAccountListVO bankMerchantAccountList = new BankMerchantAccountListVO();
            bankMerchantAccountList.setOrderId(orderId);
            bankMerchantAccountList.setBorrowNid(planNid);
            bankMerchantAccountList.setUserId(tenderUserId);
            bankMerchantAccountList.setAccountId(bankOpenAccountInfo.getAccount());
            bankMerchantAccountList.setAmount(recoverAccount);
            bankMerchantAccountList.setBankAccountCode(resultBean.getAccountId());
            bankMerchantAccountList.setBankAccountBalance(nowBankMerchantAccount.getAccountBalance());
            bankMerchantAccountList.setBankAccountFrost(nowBankMerchantAccount.getFrost());
            bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
            bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
            bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
            bankMerchantAccountList.setTxDate(Integer.parseInt(resultBean.getTxDate()));
            bankMerchantAccountList.setTxTime(Integer.parseInt(resultBean.getTxTime()));
            bankMerchantAccountList.setSeqNo(resultBean.getSeqNo());
            bankMerchantAccountList.setCreateTime(new Date());
            bankMerchantAccountList.setUpdateTime(new Date());
            bankMerchantAccountList.setCreateUserId(tenderUserId);
            bankMerchantAccountList.setUpdateUserId(tenderUserId);
            bankMerchantAccountList.setRegionName(userInfoCustomize.getRegionName());
            bankMerchantAccountList.setBranchName(userInfoCustomize.getBranchName());
            bankMerchantAccountList.setDepartmentName(userInfoCustomize.getDepartmentName());
            bankMerchantAccountList.setCreateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setUpdateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setRemark("汇计划优惠券还款");

            this.borrowClient.insertBankMerchantAccountList(bankMerchantAccountList);
        }


        logger.info(CouponRepayServiceImpl.class.toString(), methodName,
                "-----------还款结束---" + planNid + "---------" + currentRecover.getTransferId() + "---------------");
        msg.put(USERID, ct.getUserId());
        msg.put(VAL_AMOUNT, StringUtils.isEmpty(recoverAccountStr) ? "0.00" : recoverAccountStr);
        msg.put(VAL_COUPON_TYPE,
                currentRecover.getCouponType() == 1 ? "体验金" : currentRecover.getCouponType() == 2 ? "加息券"
                        : currentRecover.getCouponType() == 3 ? "代金券" : "");
        // 发送短信
        this.sendSmsCoupon(retMsgList);
        // 发送push消息
        this.sendPushMsgCoupon(retMsgList);
    }

    @Override
    public List<CouponTenderCustomizeVO> getCouponTenderList(String borrowNid) {
        return borrowClient.getCouponTenderList(borrowNid);
    }

    @Override
    public void updateCouponRecoverMoney(String borrowNid, Integer periodNow, CouponTenderCustomizeVO ct) throws Exception {
        String methodName = "updateCouponRecoverMoney";
        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
        Map<String, String> msg = new HashMap<String, String>();

        /** 基本变量 */
        // 当前时间
        int nowTime = GetDate.getNowTime10();
        /** 标的基本数据 */
        // 取得借款详情
        BorrowAndInfoVO borrow = borrowClient.selectBorrowByNid(borrowNid);
        // 还款期数
        Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
        // 剩余还款期数
        Integer periodNext = borrowPeriod - periodNow;
        // 还款方式
        String borrowStyle = borrow.getBorrowStyle();
        // 出借人用户ID
        Integer tenderUserId = null;
        // 出借人用户ID
        tenderUserId = Integer.valueOf(ct.getUserId());
        String couponTenderNid = ct.getOrderId();
        // 取得优惠券出借信息
        BorrowTenderCpnVO borrowTenderCpn = this.getCouponTenderInfo(couponTenderNid);
        // 出借人在银行存管的账户信息
        BankOpenAccountVO bankOpenAccountInfo = this.getBankOpenAccount(tenderUserId);
        if (bankOpenAccountInfo == null) {
            throw new Exception("出借人未开户。[用户ID：" + tenderUserId + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
        }

        // 是否月标(true:月标, false:天标)
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
        // 当前还款
        CouponRecoverCustomizeVO currentRecover = null;
        // [principal: 等额本金, month:等额本息, month:等额本息, endmonth:先息后本]
        if (isMonth) {
            // 取得分期还款
            currentRecover = this.getCurrentCouponRecover(couponTenderNid, periodNow);

        } else {// [endday: 按天计息, end:按月计息]
            currentRecover = this.getCurrentCouponRecover(couponTenderNid, 1);

        }
        if (currentRecover == null) {
            logger.info("优惠券还款数据不存在。[借款编号：" + borrowNid + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
            //throw new Exception("优惠券还款数据不存在。[借款编号：" + borrowNid + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
            return;
        }
        // 应还利息
        String recoverInterestStr = StringUtils.isEmpty(currentRecover.getRecoverInterest()) ? "0.00" : currentRecover.getRecoverInterest();
        // 应还本息
        String recoverAccountStr = StringUtils.isEmpty(currentRecover.getRecoverAccount()) ? "0.00" : currentRecover.getRecoverAccount();
        // 应还本金
        String recoverCapitalStr = StringUtils.isEmpty(currentRecover.getRecoverCapital()) ? "0.00" : currentRecover.getRecoverCapital();
        BigDecimal recoverInterest = new BigDecimal(recoverInterestStr);
        BigDecimal recoverAccount = new BigDecimal(recoverAccountStr);
        BigDecimal recoverCapital = new BigDecimal(recoverCapitalStr);
        CouponRecoverVO cr = new CouponRecoverVO();
        cr.setId(currentRecover.getId());
        String orderId = GetOrderIdUtils.getOrderId2(tenderUserId);
        String seqNo = GetOrderIdUtils.getSeqNo(6);
        BankCallBean resultBean = null;
        BankCallBean bean = new BankCallBean();
        if (new BigDecimal(recoverAccountStr).compareTo(BigDecimal.ZERO) != 0) {

            String merrpAccount = systemConfig.getBANK_MERRP_ACCOUNT();

            bean.setVersion(BankCallConstant.VERSION_10);// 版本号
            bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
            bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
            bean.setSeqNo(seqNo);// 交易流水号
            bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
            bean.setAccountId(merrpAccount);// 电子账号
            bean.setTxAmount(CustomUtil.formatAmount(recoverAccountStr));
            bean.setForAccountId(bankOpenAccountInfo.getAccount());
            bean.setDesLineFlag("1");
            bean.setDesLine(orderId);
            bean.setLogOrderId(orderId);// 订单号
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间(必须)格式为yyyyMMdd，例如：20130307
            bean.setLogUserId(String.valueOf(tenderUserId));
            bean.setLogClient(0);// 平台

            int transferStatus = Integer.MIN_VALUE;
            try {
                resultBean = BankCallUtils.callApiBg(bean);
                if (resultBean == null || !BankCallConstant.RESPCODE_SUCCESS.equals(resultBean.getRetCode())) {
                    logger.info("转账失败..." + "[优惠券出借编号：" + couponTenderNid + "]");
                    cr.setReceivedFlg(4);
                    this.borrowClient.updateByPrimaryKeySelective(cr);
                    String errorMsg = StringUtils.EMPTY;
                    int type = 1;
                    if (currentRecover.getCouponType() == 1) {
                        errorMsg = "体验金收益自动领取失败";
                        type = 0;
                    } else if (currentRecover.getCouponType() == 2) {
                        errorMsg = "加息券收益自动领取失败";
                    } else if (currentRecover.getCouponType() == 3) {
                        errorMsg = "代金券收益自动领取失败";
                        type = 2;
                    }

                    if (resultBean != null && Validator.isNotNull(resultBean.getRetMsg())) {
                        errorMsg = resultBean.getRetMsg();

                    }

                    if (resultBean != null && (BankCallConstant.RETCODE_BIDAPPLY_YUE_FAIL.equals(resultBean.getRetCode()) || BankCallConstant.RETCODE_YUE_FAIL.equals(resultBean.getRetCode()))) {
                        errorMsg = "商户子账户余额不足，请先充值或向该子账户转账";
                        sendSmsFail(currentRecover.getCouponType());
                    }

                    transferStatus = 1;
                    // 插入转账异常
                    this.insertTransferExceptionLog(bean, resultBean, tenderUserId, recoverAccount, bankOpenAccountInfo.getAccount(), cr.getId(), transferStatus,
                            errorMsg, type);
                    logger.error(methodName, new Exception("转账失败！失败数据已插入转账异常表。errormsg:" + errorMsg));
                    return;
                }
                // 转账失败

            } catch (Exception e) {
                logger.error(methodName, "系统发生异常，更新异常转账表失败,事物回滚", e);
                throw new RuntimeException("系统发生异常，更新异常转账表失败,事物回滚!" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
        }

        // 判断该收支明细是否存在时,跳出本次循环
        if (countAccountListByNidCoupon(orderId) == 0) {
            // 更新账户信息(出借人)
            AccountVO account = new AccountVO();
            account.setUserId(tenderUserId);

            account.setBankBalance(recoverAccount); // 账户余额
            account.setBankTotal(BigDecimal.ZERO);// 出借人资金总额 +利息
            account.setBankFrost(BigDecimal.ZERO);// 出借人冻结金额+出借金额(等额本金时出借金额可能会大于计算出的本金之和)
            account.setBankAwait(recoverAccount);// 出借人待收金额+利息+
            // 本金
            account.setBankAwaitCapital(BigDecimal.ZERO);// 出借人待收本金
            account.setBankAwaitInterest(recoverAccount);// 出借人待收利息
            account.setBankInterestSum(recoverAccount);
            account.setBankInvestSum(BigDecimal.ZERO);// 出借人累计出借
            account.setBankFrostCash(BigDecimal.ZERO);// 江西银行冻结金额
            logger.info("优惠券还款更新账户表");
            int accountCnt = this.borrowClient.updateOfRepayTender(account);
            if (accountCnt == 0) {
                throw new RuntimeException("出借人资金记录(huiyingdai_account)更新失败！" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
            // 取得账户信息(出借人)
            account = borrowClient.getAccountByUserId(tenderUserId);
            if (account == null) {
                throw new RuntimeException("出借人账户信息不存在。[出借人ID：" + borrowTenderCpn.getUserId() + "]，" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
            // 写入收支明细
            logger.info("优惠券还款写入收支明细");
            AccountListVO accountList = new AccountListVO();
            // 转账订单编号
            accountList.setNid(orderId);
            // 出借人
            accountList.setUserId(tenderUserId);
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
            accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(account.getPlanFrost());
            accountList.setAccountId(bankOpenAccountInfo.getAccount());
            accountList.setTxDate(Integer.parseInt(GetOrderIdUtils.getTxDate()));
            accountList.setTxTime(Integer.parseInt(GetOrderIdUtils.getTxTime()));
            accountList.setSeqNo(seqNo);
            accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
            accountList.setCheckStatus(0);
            accountList.setTradeStatus(1);
            accountList.setIsBank(1);

            // 出借收入
            accountList.setAmount(recoverAccount);
            // 1收入
            accountList.setType(1);
            String trade = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                trade = "experience_profit";
            } else if (currentRecover.getCouponType() == 2) {
                trade = "increase_interest_profit";
            } else if (currentRecover.getCouponType() == 3) {
                trade = "cash_coupon_profit";
            }
            // 还款成功
            accountList.setTrade(trade);
            // 余额操作
            accountList.setTradeCode("balance");
            // 出借人资金总额
            accountList.setTotal(account.getTotal());
            // 出借人可用金额
            accountList.setBalance(account.getBalance());
            // 出借人冻结金额
            accountList.setFrost(account.getFrost());
            // 出借人待收金额
            accountList.setAwait(account.getAwait());
            // 创建时间
            accountList.setCreateTime(new Date());
            // 更新时间
//			accountList.setBaseUpdate(nowTime);
            // 操作者
            accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY);
            String remark = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                remark = "体验金：" + ct.getCouponUserCode();
            } else if (currentRecover.getCouponType() == 2) {
                remark = "加息券：" + ct.getCouponUserCode();
            } else if (currentRecover.getCouponType() == 3) {
                remark = "代金券：" + ct.getCouponUserCode();
            }
            accountList.setRemark(remark);
            accountList.setIp(borrow.getAddip()); // 操作IP
            accountList.setIsUpdate(0);
            accountList.setBaseUpdate(0);
            // accountList.setInterest(recoverInterest); // 利息
            accountList.setWeb(0); // PC
            int accountListCnt = insertAccountList(accountList);
            if (accountListCnt == 0) {
                throw new RuntimeException("收支明细(huiyingdai_account_list)写入失败！" + "[优惠券出借编号：" + couponTenderNid + "]");
            }
        }
        // 更新出借表
        // 已收总额
        borrowTenderCpn.setRecoverAccountYes(borrowTenderCpn.getRecoverAccountYes().add(recoverAccount));
        // 已收本金
        borrowTenderCpn.setRecoverAccountCapitalYes(borrowTenderCpn.getRecoverAccountCapitalYes().add(recoverCapital));
        // 已收利息
        borrowTenderCpn.setRecoverAccountInterestYes(borrowTenderCpn.getRecoverAccountInterestYes().add(recoverInterest));
        // 待收总额
        borrowTenderCpn.setRecoverAccountWait(borrowTenderCpn.getRecoverAccountWait().subtract(recoverAccount));
        // 待收本金
        borrowTenderCpn.setRecoverAccountCapitalWait(borrowTenderCpn.getRecoverAccountCapitalWait().subtract(recoverCapital));
        // 待收利息
        borrowTenderCpn.setRecoverAccountInterestWait(borrowTenderCpn.getRecoverAccountInterestWait().subtract(recoverInterest));
        int borrowTenderCnt = borrowClient.updateBorrowTenderCpn(borrowTenderCpn);
        if (borrowTenderCnt == 0) {
            throw new RuntimeException("出借表(hyjf_borrow_tender_cpn)更新失败！" + "[优惠券出借编号：" + couponTenderNid + "]");
        }
        // 更新优惠券出借还款表
        // 转账订单编号
        cr.setTransferId(orderId);
        // 已还款
        cr.setRecoverStatus(1);
        // 收益领取状态(已领取)
        cr.setReceivedFlg(5);
        // 转账时间
        cr.setTransferTime(nowTime);
        // 已经还款时间
        cr.setRecoverYestime(nowTime);
        // 已还利息
        cr.setRecoverInterestYes(recoverInterest);
        // 已还本息
        cr.setRecoverAccountYes(recoverAccount);
        if (currentRecover.getCouponType() == 3) {
            // 代金券
            // 已还本金
            cr.setRecoverCapitalYes(recoverCapital);
        } else {
            // 体验金和加息券
            // 已还本金
            cr.setRecoverCapitalYes(BigDecimal.ZERO);
        }
        // 更新时间
        cr.setUpdateTime(new Date());
        // 更新用户
        cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_REPAY));
        // 通知用户
        cr.setNoticeFlg(1);
        this.borrowClient.updateByPrimaryKeySelective(cr);
        // 插入网站收支明细记录
        AccountWebListVO accountWebList = new AccountWebListVO();
        if (!isMonth) {
            // 未分期
            accountWebList.setOrdid(borrowTenderCpn.getNid());// 订单号
        } else {
            // 分期
            accountWebList.setOrdid(borrowTenderCpn.getNid() + "_" + periodNow);// 订单号
            if (periodNext > 0) {
                // 更新还款期
                this.crRecoverPeriod(couponTenderNid, periodNow + 1);
            }
        }
        accountWebList.setBorrowNid(borrowNid); // 出借编号
        accountWebList.setUserId(tenderUserId); // 出借者
        accountWebList.setAmount(Double.valueOf(recoverAccount.toString())); // 优惠券出借受益
        accountWebList.setType(CustomConstants.TYPE_OUT); // 类型1收入,2支出

        String tradeType = StringUtils.EMPTY;
        if (currentRecover.getCouponType() == 1) {
            // 体验金
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_TYJ);
            // 体验金收益
            tradeType = CustomConstants.TRADE_COUPON_SY;
        } else if (currentRecover.getCouponType() == 2) {
            // 加息券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_JXQ);
            // 加息券回款
            tradeType = CustomConstants.TRADE_COUPON_HK;
        } else if (currentRecover.getCouponType() == 3) {
            // 代金券
            accountWebList.setTrade(CustomConstants.TRADE_COUPON_DJQ);
            // 代金券回款
            tradeType = CustomConstants.TRADE_COUPON_DJ;
        }
        accountWebList.setTradeType(tradeType); // 加息券回款
        String remark = "项目编号：" + borrowNid + "<br />优惠券:" + ct.getCouponUserCode();
        accountWebList.setRemark(remark); // 出借编号
        accountWebList.setCreateTime(nowTime);
        int accountWebListCnt = insertAccountWebList(accountWebList);
        if (accountWebListCnt == 0) {
            throw new RuntimeException("网站收支记录(huiyingdai_account_web_list)更新失败！" + "[出借订单号：" + borrowTenderCpn.getNid() + "]");
        }

        // 添加红包账户明细
        BankMerchantAccountVO nowBankMerchantAccount = this.getBankMerchantAccount(resultBean.getAccountId());
        nowBankMerchantAccount.setAvailableBalance(nowBankMerchantAccount.getAvailableBalance().subtract(recoverAccount));
        nowBankMerchantAccount.setAccountBalance(nowBankMerchantAccount.getAccountBalance().subtract(recoverAccount));
        nowBankMerchantAccount.setUpdateTime(new Date());

        // 更新红包账户信息
        int updateCount = this.updateBankMerchantAccount(nowBankMerchantAccount);
        if (updateCount > 0) {
            UserInfoCustomizeVO userInfoCustomize = this.queryUserInfoByUserId(tenderUserId);

            // 添加红包明细
            BankMerchantAccountListVO bankMerchantAccountList = new BankMerchantAccountListVO();
            bankMerchantAccountList.setOrderId(orderId);
            bankMerchantAccountList.setBorrowNid(borrowNid);
            bankMerchantAccountList.setUserId(tenderUserId);
            bankMerchantAccountList.setAccountId(bankOpenAccountInfo.getAccount());
            bankMerchantAccountList.setAmount(recoverAccount);
            bankMerchantAccountList.setBankAccountCode(resultBean.getAccountId());
            bankMerchantAccountList.setBankAccountBalance(nowBankMerchantAccount.getAccountBalance());
            bankMerchantAccountList.setBankAccountFrost(nowBankMerchantAccount.getFrost());
            bankMerchantAccountList.setTransType(CustomConstants.BANK_MER_TRANS_TYPE_AUTOMATIC);
            bankMerchantAccountList.setType(CustomConstants.BANK_MER_TYPE_EXPENDITURE);
            bankMerchantAccountList.setStatus(CustomConstants.BANK_MER_TRANS_STATUS_SUCCESS);
            bankMerchantAccountList.setTxDate(Integer.parseInt(resultBean.getTxDate()));
            bankMerchantAccountList.setTxTime(Integer.parseInt(resultBean.getTxTime()));
            bankMerchantAccountList.setSeqNo(resultBean.getSeqNo());
            bankMerchantAccountList.setCreateTime(new Date());
            bankMerchantAccountList.setUpdateTime(new Date());
            bankMerchantAccountList.setCreateUserId(tenderUserId);
            bankMerchantAccountList.setUpdateUserId(tenderUserId);
            bankMerchantAccountList.setRegionName(userInfoCustomize.getRegionName());
            bankMerchantAccountList.setBranchName(userInfoCustomize.getBranchName());
            bankMerchantAccountList.setDepartmentName(userInfoCustomize.getDepartmentName());
            bankMerchantAccountList.setCreateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setUpdateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setRemark("直投类优惠券还款");

            this.borrowClient.insertBankMerchantAccountList(bankMerchantAccountList);
        }


        logger.info(CouponRepayServiceImpl.class.toString(), methodName,
                "-----------还款结束---" + borrowNid + "---------" + currentRecover.getTransferId() + "---------------");
        msg.put(USERID, ct.getUserId());
        msg.put(VAL_AMOUNT, StringUtils.isEmpty(recoverAccountStr) ? "0.00" : recoverAccountStr);
        msg.put(VAL_COUPON_TYPE,
                currentRecover.getCouponType() == 1 ? "体验金" : currentRecover.getCouponType() == 2 ? "加息券"
                        : currentRecover.getCouponType() == 3 ? "代金券" : "");
        retMsgList.add(msg);
        // 发送短信
        this.sendSmsCoupon(retMsgList);
        // 发送push消息
        this.sendPushMsgCoupon(retMsgList);
    }


    /**
     * 更新还款期
     *
     * @param tenderNid 出借订单编号
     * @param period    期数
     */
    private void crRecoverPeriod(String tenderNid, int period) {

        Map<String, Object> paramMapCurrent = new HashMap<String, Object>();
        paramMapCurrent.put("currentRecoverFlg", 1);
        paramMapCurrent.put("tenderNid", tenderNid);
        paramMapCurrent.put("period", period);
        this.borrowClient.crRecoverPeriod(tenderNid, period);

    }

    /**
     * @return
     * @Author walter.limeng
     * @Description 根据用户ID查询用户信息
     * @Date 14:24 2018/7/18
     * @Param userId
     */
    public UserInfoCustomizeVO queryUserInfoByUserId(Integer userId) {
        return amUserClient.queryUserInfoCustomizeByUserId(userId);
    }

    /**
     * 更新红包账户
     *
     * @param account
     * @return
     */
    public int updateBankMerchantAccount(BankMerchantAccountVO account) {
        return borrowClient.updateBankMerchatAccount(account);
    }

    /**
     * 加载红包账户
     *
     * @param accountCode
     * @return
     */
    public BankMerchantAccountVO getBankMerchantAccount(String accountCode) {
        BankMerchantAccountVO bankMerchantAccounts = borrowClient.getBankMerchantAccount(accountCode);
        return bankMerchantAccounts;
    }

    /**
     * 插入网站收支记录
     *
     * @param accountWebList
     * @return
     */
    private int insertAccountWebList(AccountWebListVO accountWebList) {
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC,
                    UUID.randomUUID().toString(), accountWebList));
            return 1;
        } catch (MQException e) {
            logger.error("更新网站收支明细失败！");
        }
        return 0;
    }

    /**
     * 写入收支明细
     *
     * @param accountList
     * @return
     */
    private int insertAccountList(AccountListVO accountList) {
        // 写入收支明细
        return this.borrowClient.insertAccountListSelective(accountList);
    }

    /**
     * 判断该收支明细是否存在(加息券)
     *
     * @param nid
     * @return
     */
    private int countAccountListByNidCoupon(String nid) {
        return this.borrowClient.countByNidAndTrade(nid, "increase_interest_profit");
    }

    /**
     * 作成转账异常记录
     *
     * @param fromBean
     * @param bankCallBean
     * @param userId
     */
    private void insertTransferExceptionLog(BankCallBean fromBean, BankCallBean bankCallBean, int userId, BigDecimal transAmt, String accountId,
                                            int recoverId, int transferStatus, String errorMsg, int type) throws Exception {
        List<TransferExceptionLogVO> listLog = this.borrowClient.selectByRecoverId(recoverId);
        if (listLog != null && listLog.size() > 0) {
            // 异常转账记录已经存在，不再执行插入操作
            logger.info("异常转账记录已经存在，不再执行插入操作");
            return;
        }
        int nowTime = GetDate.getNowTime10();
        TransferExceptionLogVO transferExceptionLog = new TransferExceptionLogVO();
        transferExceptionLog.setUuid(CreateUUID.createUUID());
        // 订单编号
        transferExceptionLog.setSeqNo(fromBean.getSeqNo());
        // 发送内容
        transferExceptionLog.setContent(JSONObject.toJSONString(fromBean, true));
        // 返回内容
        transferExceptionLog.setResult(bankCallBean == null ? null : JSONObject.toJSONString(bankCallBean, true));
        // 加息券
        transferExceptionLog.setType(type);
        // 状态 失败
        transferExceptionLog.setStatus(transferStatus);
        // 返回码
        transferExceptionLog.setRespcode(bankCallBean == null ? null : bankCallBean.getRetCode());
        // 交易金额
        transferExceptionLog.setTransAmt(transAmt);
        // 出借人客户号
        transferExceptionLog.setAccountId(accountId);
        // 出借人编号
        transferExceptionLog.setUserId(userId);
        // 还款表（coupon_recover）id
        transferExceptionLog.setRecoverId(recoverId);
        // 转账订单号
        transferExceptionLog.setOrderId(fromBean.getLogOrderId());
        // 备注
        transferExceptionLog.setRemark(errorMsg);
        transferExceptionLog.setAddTime(new Date());
        transferExceptionLog.setAddUser(CustomConstants.OPERATOR_AUTO_REPAY);
        transferExceptionLog.setUpdateTime(new Date());
        transferExceptionLog.setUpdateUser(CustomConstants.OPERATOR_AUTO_REPAY);
        transferExceptionLog.setDelFlag(0);
        // 转账失败记录
        this.borrowClient.insertTransferExLog(transferExceptionLog);
    }

    /**
     * 发送push消息(优惠券还款成功)
     *
     * @param msgList
     */
    public void sendPushMsgCoupon(List<Map<String, String>> msgList) {
        if (msgList != null && msgList.size() > 0) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(USERID)) && NumberUtils.isNumber(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))) {
                    UserVO users = amUserClient.findUserById(Integer.valueOf(msg.get(USERID)));
                    if (users == null) {
                        return;
                    }
                    AppMsMessage appMsMessage = new AppMsMessage(users.getUserId(), msg, null, MessageConstant.APP_MS_SEND_FOR_USER,
                            CustomConstants.JYTZ_COUPON_PROFIT);
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, String.valueOf(users.getUserId()),
                                appMsMessage));
                    } catch (MQException e) {
                        logger.error("发送app消息失败..", e);
                    }
                }
            }
        }
    }

    /**
     * 优惠券还款 余额不足 短信通知
     */
    private void sendSmsFail(int couponType) {
        Map<String, String> replaceStrs = new HashMap<String, String>();
        try {
            if (2 == couponType) {
                replaceStrs.put("couponType", "加息券");
            } else if (3 == couponType) {
                replaceStrs.put("couponType", "代金券");
            } else if (1 == couponType) {
                replaceStrs.put("couponType", "体验金");
            }

            SmsMessage smsMessage =
                    new SmsMessage(null, replaceStrs, null, null,
                            MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_COUPON_JIA_YUE, CustomConstants.CHANNEL_TYPE_NORMAL);
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, null, smsMessage));
            } catch (MQException e2) {
                logger.error("发送邮件失败..", e2);
            }
        } catch (Exception e) {
            logger.error(this.getClass().toString(), "sendSmsFail", e.getMessage());
        }
    }

    /**
     * 发送短信(优惠券还款成功)
     *
     * @param msgList
     */
    public void sendSmsCoupon(List<Map<String, String>> msgList) {
        if (msgList != null && msgList.size() > 0) {
            logger.info("优惠券还款，短信发送："+msgList);
            for (Map<String, String> msg : msgList) {
                logger.info("优惠券还款，短信发送："+msg);
                if (Validator.isNotNull(msg.get(USERID)) && NumberUtils.isNumber(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))) {
                    UserVO users = amUserClient.findUserById(Integer.valueOf(msg.get(USERID)));
                    if (users == null || Validator.isNull(users.getMobile()) || (users.getRecieveSms() != null && users.getRecieveSms() == 1)) {
                        return;
                    }
                    SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(USERID)), msg, null, null, MessageConstant.SMS_SEND_FOR_USER, null,
                            CustomConstants.PARAM_TPL_COUPON_PROFIT, CustomConstants.CHANNEL_TYPE_NORMAL);
                    try {
                        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, msg.get(USERID), smsMessage));
                        logger.info("优惠券还款，短信发送MQ成功！");
                    } catch (MQException e2) {
                        logger.error("发送短信失败..", e2);
                    }
                }
            }
        }
    }

    /**
     * @return AccountListVO
     * @Author walter.limeng
     * @Description 处理accountList
     * @Date 18:28 2018/7/17
     * @Param
     */
    public AccountListVO dealAccountList(String orderId, Integer tenderUserId, AccountVO account, BankOpenAccountVO bankOpenAccountInfo,
                                         String seqNo, BankCallBean bean, BigDecimal recoverAccount, CouponRecoverCustomizeVO currentRecover,
                                         CouponTenderCustomizeVO ct) {
        AccountListVO accountList = new AccountListVO();
        // 转账订单编号
        accountList.setNid(orderId);
        // 出借人
        accountList.setUserId(tenderUserId);
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
        accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
        accountList.setPlanFrost(account.getPlanFrost());
        accountList.setAccountId(bankOpenAccountInfo.getAccount());
        accountList.setTxDate(Integer.parseInt(GetOrderIdUtils.getTxDate()));
        accountList.setTxTime(Integer.parseInt(GetOrderIdUtils.getTxTime()));
        accountList.setSeqNo(seqNo);
        accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
        accountList.setCheckStatus(0);
        accountList.setTradeStatus(1);
        accountList.setIsBank(1);
        // 出借收入
        accountList.setAmount(recoverAccount);
        // 1收入
        accountList.setType(1);
        String trade = StringUtils.EMPTY;
        if (currentRecover.getCouponType() == 1) {
            trade = "experience_profit";
        } else if (currentRecover.getCouponType() == 2) {
            trade = "increase_interest_profit";
        } else if (currentRecover.getCouponType() == 3) {
            trade = "cash_coupon_profit";
        }
        // 还款成功
        accountList.setTrade(trade);
        // 余额操作
        accountList.setTradeCode("balance");
        // 出借人资金总额
        accountList.setTotal(account.getTotal());
        // 出借人可用金额
        accountList.setBalance(account.getBalance());
        // 出借人冻结金额
        accountList.setFrost(account.getFrost());
        // 出借人待收金额
        accountList.setAwait(account.getAwait());
        // 创建时间
        accountList.setCreateTime(new Date());
        // 更新时间
        accountList.setBaseUpdate(GetDate.getNowTime10());
        // 操作者
        accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY);
        String remark = StringUtils.EMPTY;
        if (currentRecover.getCouponType() == 1) {
            remark = "体验金：" + ct.getCouponUserCode();
        } else if (currentRecover.getCouponType() == 2) {
            remark = "加息券：" + ct.getCouponUserCode();
        } else if (currentRecover.getCouponType() == 3) {
            remark = "代金券：" + ct.getCouponUserCode();
        }
        accountList.setRemark(remark);
        //accountList.setIp(borrow.getAddip()); // 操作IP
        accountList.setIsUpdate(0);
        accountList.setBaseUpdate(0);
        // accountList.setInterest(recoverInterest); // 利息
        accountList.setWeb(0); // PC
        return accountList;
    }

    /**
     * 取得优惠券出借信息
     *
     * @param couponTenderNid
     * @return
     */
    private BorrowTenderCpnVO getCouponTenderInfo(String couponTenderNid) {
        return borrowClient.getCouponTenderInfo(couponTenderNid);
    }

    /**
     * @return
     * @Author walter.limeng
     * @Description 根据用户ID获取用户开户信息
     * @Date 17:29 2018/7/17
     * @Param userId
     */
    public BankOpenAccountVO getBankOpenAccount(Integer userId) {
        return amUserClient.selectBankAccountById(userId);
    }

    /**
     * 根据订单编号取得该订单的还款列表
     *
     * @param couponTenderNid
     * @param periodNow
     * @return
     */
    private CouponRecoverCustomizeVO getCurrentCouponRecover(String couponTenderNid, int periodNow) {
        return this.borrowClient.getCurrentCouponRecover(couponTenderNid, periodNow);

    }

    /**
     * 优惠券单独出借放款
     *
     * @return
     */
    @Override
    public List<String> selectNidForCouponOnly() {
        return borrowClient.selectNidForCouponOnly(new CouponRepayRequest());
    }

    /**
     * 体验金按收益期限还款
     *
     * @param nids
     */
    @Override
    public void couponOnlyRepay(String nids) {
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.TYJ_COUPON_REPAY_TOPIC, UUID.randomUUID().toString(), nids));
        } catch (MQException e) {
            e.printStackTrace();
            logger.info("体验金按收益期限还款消息队列 失败");
        }
    }
}
