/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.trade.service.task.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.CouponRecoverCustomizeRequest;
import com.hyjf.am.trade.dao.mapper.auto.*;
import com.hyjf.am.trade.dao.mapper.customize.AdminAccountCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchCouponTimeoutCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.BatchTyjRepayCustomizeMapper;
import com.hyjf.am.trade.dao.mapper.customize.CouponRecoverCustomizeMapper;
import com.hyjf.am.trade.dao.model.auto.*;
import com.hyjf.am.trade.dao.model.customize.BatchCouponTimeoutCommonCustomize;
import com.hyjf.am.trade.dao.model.customize.CouponRecoverCustomize;
import com.hyjf.am.trade.mq.base.CommonProducer;
import com.hyjf.am.trade.mq.base.MessageContent;
import com.hyjf.am.trade.service.task.CouponRepayService;
import com.hyjf.am.vo.admin.coupon.CouponRecoverVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.borrow.BorrowTenderCpnVO;
import com.hyjf.am.vo.trade.coupon.CouponRecoverCustomizeVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoCustomizeVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author yaoy
 * @version CouponRepayServiceImpl, v0.1 2018/6/21 18:14
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CouponRepayServiceImpl implements CouponRepayService {

    public static final Logger logger = LoggerFactory.getLogger(CouponRepayServiceImpl.class);

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

    @Autowired
    private CouponRecoverCustomizeMapper couponRecoverCustomizeMapper;
    @Autowired
    private BatchCouponTimeoutCustomizeMapper batchCouponTimeoutCustomizeMapper;
    @Autowired
    private BatchTyjRepayCustomizeMapper batchTyjRepayCustomizeMapper;
    @Autowired
    private CouponRecoverMapper couponRecoverMapper;
    @Autowired
    private TransferExceptionLogMapper transferExceptionLogMapper;
    @Autowired
    private AccountListMapper accountListMapper;
    @Autowired
    private AdminAccountCustomizeMapper adminAccountCustomizeMapper;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private BorrowTenderCpnMapper borrowTenderCpnMapper;
    @Autowired
    private BankMerchantAccountMapper bankMerchantAccountMapper;
    @Autowired
    private BankMerchantAccountListMapper bankMerchantAccountListMapper;
    @Autowired
    private CommonProducer commonProducer;

    @Value("${hyjf.bank.merrp.account}")
    private String BANK_MERRP_ACCOUNT;

    @Override
    public List<CouponRecoverCustomize> selectCouponInterestWaitToday(long timeStart, long timeEnd) {
        Map<String, Object> map = new HashMap<>();
        map.put("timeStart", timeStart);
        map.put("timeEnd", timeEnd);
        List<CouponRecoverCustomize> couponRecoverCustomizes = couponRecoverCustomizeMapper.selectCouponInterestWaitToday(map);
        return couponRecoverCustomizes;
    }

    @Override
    public BigDecimal selectCouponInterestReceivedToday(long timeStart, long timeEnd) {
        Map<String, Object> map = new HashMap<>();
        map.put("timeStart", timeStart);
        map.put("timeEnd", timeEnd);
        BigDecimal interestReceived = couponRecoverCustomizeMapper.selectCouponInterestReceivedToday(map);
        return interestReceived;
    }

    @Override
    public List<BatchCouponTimeoutCommonCustomize> selectCouponQuota(int threeBeginDate, int threeEndDate) {
        Map<String, Object> map = new HashMap<>();
        map.put("threeBeginDate", threeBeginDate);
        map.put("threeEndDate", threeEndDate);
        List<BatchCouponTimeoutCommonCustomize> batchCouponTimeoutCommonCustomizes = batchCouponTimeoutCustomizeMapper.selectCouponQuota(map);
        return batchCouponTimeoutCommonCustomizes;
    }

    @Override
    public List<String> selectNidForCouponOnly(Map<String, Object> paramMap) {
        return batchTyjRepayCustomizeMapper.selectNidForTyj(paramMap);
    }

    @Override
    public CouponRecoverCustomize selectCurrentCouponRecover(Map<String, Object> map) {
        return couponRecoverCustomizeMapper.selectCurrentCouponRecover(map);
    }

    @Override
    public void updateCouponRecover(CouponRecover couponRecover) {
        couponRecoverMapper.updateByPrimaryKeySelective(couponRecover);
    }

    @Override
    public Integer updateCouponOnlyRecover(CouponRecoverCustomizeRequest request) throws MQException {
        List<Map<String, String>> retMsgList = new ArrayList<Map<String, String>>();
        Map<String, String> msg = new HashMap<String, String>();
        retMsgList.add(msg);
        BankOpenAccountVO bankOpenAccountVO = request.getBankOpenAccountVO();
        CouponRecoverVO recoverVO = request.getCouponRecoverVO();
        CouponRecoverCustomizeVO currentRecover = request.getCurrentRecoverVO();
        BorrowTenderCpnVO borrowTenderCpnVO = request.getBorrowTenderCpn();
        UserVO user = request.getUser();
        String orderId = request.getOrderId();
        BigDecimal recoverAccount = request.getRecoverAccount();
        BigDecimal recoverInterest = request.getRecoverInterest();
        BigDecimal recoverCapital = request.getRecoverCapital();
        String seqNo = request.getSeqNo();
        Integer tenderUserId = request.getTenderUserId();
        String nid = request.getRecoverNid();
        UserInfoCustomizeVO userInfoCustomize = request.getUserInfoCustomize();
        BankCallBean resultBean = null;
        BankCallBean bean = new BankCallBean();

        Date nowTime = GetDate.getDate();

        CouponRecover cr = new CouponRecover();
        BeanUtils.copyProperties(recoverVO, cr);

        if (recoverAccount.compareTo(BigDecimal.ZERO) != 0) {
            String merrpAccount = BANK_MERRP_ACCOUNT;

            bean.setVersion(BankCallConstant.VERSION_10);// 版本号
            bean.setTxCode(BankCallMethodConstant.TXCODE_VOUCHER_PAY);// 交易代码
            bean.setTxDate(GetOrderIdUtils.getTxDate()); // 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
            bean.setSeqNo(seqNo);// 交易流水号
            bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
            bean.setAccountId(merrpAccount);// 电子账号
            bean.setTxAmount(CustomUtil.formatAmount(recoverAccount.toString()));
            bean.setForAccountId(bankOpenAccountVO.getAccount());
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
                    cr.setReceivedFlg(4);
                    this.couponRecoverMapper.updateByPrimaryKeySelective(cr);
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
                    this.insertTransferExceptionLog(bean, resultBean, tenderUserId, recoverAccount, bankOpenAccountVO.getAccount(), cr.getId(), transferStatus,
                            errorMsg, type);
                    logger.error(String.valueOf(new Exception("转账失败！失败数据已插入转账异常表。errormsg:" + errorMsg)));
                    return 0;

                }

            } catch (Exception e) {
                logger.error("系统发生异常，更新异常转账表失败,事物回滚", e);
                throw new RuntimeException("系统发生异常，更新异常转账表失败,事物回滚!" + "[优惠券出借编号：" + nid + "]");
            }
        }
        // 判断该收支明细是否存在时,跳出本次循环
        if (countAccountListByNidCoupon(orderId) == 0) {
            // 更新账户信息(出借人)
            Account account = new Account();

            int accountCnt;
            // 如果是计划类出借
            if (borrowTenderCpnVO.getTenderType() == 3) {
                // 更新账户信息(出借人)
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
                accountCnt = this.adminAccountCustomizeMapper.updateOfRepayCouponHjh(account);
            } else {
                // 直投类
                account.setUserId(tenderUserId);
                account.setBankTotal(BigDecimal.ZERO);// 出借人资金总额 +利息
                account.setBankFrost(BigDecimal.ZERO);// 出借人冻结金额+出借金额(等额本金时出借金额可能会大于计算出的本金之和)
                account.setBankBalance(recoverAccount); // 账户余额
                account.setBankAwait(recoverAccount);// 出借人待收金额+利息+ 本金
                account.setBankAwaitCapital(BigDecimal.ZERO);// 出借人待收本金
                account.setBankAwaitInterest(recoverAccount);// 出借人待收利息
                account.setBankInterestSum(recoverAccount);
                account.setBankInvestSum(BigDecimal.ZERO);// 出借人累计出借
                account.setBankFrostCash(BigDecimal.ZERO);// 江西银行冻结金额

                accountCnt = this.adminAccountCustomizeMapper.updateOfRepayTender(account);
            }
            if (accountCnt == 0) {
                throw new RuntimeException("出借人资金记录(ht_account)更新失败！" + "[优惠券出借编号：" + nid + "]");
            }
            // 取得账户信息(出借人)
            account = this.getAccountByUserId(tenderUserId);
            if (account == null) {
                throw new RuntimeException("出借人账户信息不存在。[出借人ID：" + borrowTenderCpnVO.getUserId() + "]，" + "[优惠券出借编号：" + nid + "]");
            }
            AccountList accountList = new AccountList();
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
            accountList.setAccountId(bankOpenAccountVO.getAccount());
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
            String remark = "";
            String trade = StringUtils.EMPTY;
            if (currentRecover.getCouponType() == 1) {
                trade = "experience_profit";
                remark = "体验金：" + currentRecover.getCouponUserCode();
            } else if (currentRecover.getCouponType() == 2) {
                trade = "increase_interest_profit";
                remark = "加息券：" + currentRecover.getCouponUserCode();
            } else if (currentRecover.getCouponType() == 3) {
                trade = "cash_coupon_profit";
                remark = "代金券：" + currentRecover.getCouponUserCode();
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
            accountList.setCreateTime(nowTime);
            // 更新时间
//            accountList.setBaseUpdate(nowTime);
            // 操作者
            accountList.setOperator(CustomConstants.OPERATOR_AUTO_REPAY);

            accountList.setRemark(remark);
//            accountList.setIsUpdate(0);
//            accountList.setBaseUpdate(0);
            // accountList.setInterest(recoverInterest); // 利息
            accountList.setWeb(0); // PC
            int accountListCnt = insertAccountList(accountList);
            if (accountListCnt == 0) {
                throw new RuntimeException("收支明细(ht_account_list)写入失败！" + "[优惠券出借编号：" + nid + "]");
            }
        }
        // 更新出借表
        // 已收总额
        borrowTenderCpnVO.setRecoverAccountYes(borrowTenderCpnVO.getRecoverAccountYes().add(recoverAccount));
        // 已收本金
        borrowTenderCpnVO.setRecoverAccountCapitalYes(borrowTenderCpnVO.getRecoverAccountCapitalYes().add(recoverCapital));
        // 已收利息
        borrowTenderCpnVO.setRecoverAccountInterestYes(borrowTenderCpnVO.getRecoverAccountInterestYes().add(recoverInterest));
        // 待收总额
        borrowTenderCpnVO.setRecoverAccountWait(borrowTenderCpnVO.getRecoverAccountWait().subtract(recoverAccount));
        // 待收本金
        borrowTenderCpnVO.setRecoverAccountCapitalWait(borrowTenderCpnVO.getRecoverAccountCapitalWait().subtract(recoverCapital));
        // 待收利息
        borrowTenderCpnVO.setRecoverAccountInterestWait(borrowTenderCpnVO.getRecoverAccountInterestWait().subtract(recoverInterest));
        BorrowTenderCpn borrowTenderCpn = new BorrowTenderCpn();
        BeanUtils.copyProperties(borrowTenderCpnVO, borrowTenderCpn);
        int borrowTenderCnt = borrowTenderCpnMapper.updateByPrimaryKeySelective(borrowTenderCpn);
        if (borrowTenderCnt == 0) {
            throw new RuntimeException("出借表(ht_borrow_tender_cpn)更新失败！" + "[优惠券出借编号：" + nid + "]");
        }
        // 更新优惠券出借还款表
        // 转账订单编号
        cr.setTransferId(orderId);
        // 已还款
        cr.setRecoverStatus(1);
        // 收益领取状态(已领取)
        cr.setReceivedFlg(5);
        // 转账时间
        cr.setTransferTime(GetDate.getNowTime10());
        // 已经还款时间
        cr.setRecoverYestime(GetDate.getNowTime10());
        // 已还利息
        cr.setRecoverInterestYes(recoverInterest);
        // 已还本息
        cr.setRecoverAccountYes(recoverAccount);
        // 本金
        cr.setRecoverCapitalYes(BigDecimal.ZERO);
        // 更新时间
        cr.setUpdateTime(nowTime);
        // 更新用户
        cr.setUpdateUserId(Integer.parseInt(CustomConstants.OPERATOR_AUTO_REPAY));
        // 通知用户
        cr.setNoticeFlg(1);
        this.couponRecoverMapper.updateByPrimaryKeySelective(cr);

        // 添加红包账户明细
        BankMerchantAccount nowBankMerchantAccount = this.getBankMerchantAccount(resultBean.getAccountId());
        nowBankMerchantAccount.setAvailableBalance(nowBankMerchantAccount.getAvailableBalance().subtract(recoverAccount));
        nowBankMerchantAccount.setAccountBalance(nowBankMerchantAccount.getAccountBalance().subtract(recoverAccount));
        nowBankMerchantAccount.setUpdateTime(nowTime);

        // 更新红包账户信息
        int updateCount = this.updateBankMerchantAccount(nowBankMerchantAccount);
        if (updateCount > 0) {
            // 添加红包明细
            BankMerchantAccountList bankMerchantAccountList = new BankMerchantAccountList();
            bankMerchantAccountList.setOrderId(orderId);
            bankMerchantAccountList.setUserId(tenderUserId);
            bankMerchantAccountList.setAccountId(bankOpenAccountVO.getAccount());
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
            bankMerchantAccountList.setRegionName(userInfoCustomize.getRegionName());
            bankMerchantAccountList.setBranchName(userInfoCustomize.getBranchName());
            bankMerchantAccountList.setDepartmentName(userInfoCustomize.getDepartmentName());
            bankMerchantAccountList.setCreateUserId(tenderUserId);
            bankMerchantAccountList.setUpdateUserId(tenderUserId);
            bankMerchantAccountList.setCreateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setUpdateUserName(userInfoCustomize.getUserName());
            bankMerchantAccountList.setRemark("优惠券单独出借还款");

            this.bankMerchantAccountListMapper.insertSelective(bankMerchantAccountList);
        }


        logger.info(CouponRepayServiceImpl.class.toString(),
                "-----------还款结束---" + borrowTenderCpn.getBorrowNid() + "---------" + currentRecover.getTransferId() + "---------------");
        msg.put(USERID, tenderUserId.toString());
        msg.put(VAL_AMOUNT, StringUtils.isEmpty(recoverAccount.toString()) ? "0.00" : recoverAccount.toString());
        msg.put(VAL_COUPON_TYPE, "体验金");
        // 发送短信
        this.sendSmsCoupon(retMsgList, user);
        // 发送push消息
        this.sendPushMsgCoupon(retMsgList, user);
        return 1;
    }

    @Override
    public BorrowTenderCpn getCouponTenderInfo(String couponTenderNid) {
        BorrowTenderCpnExample example = new BorrowTenderCpnExample();
        example.createCriteria().andNidEqualTo(couponTenderNid);
        List<BorrowTenderCpn> btList = this.borrowTenderCpnMapper.selectByExample(example);
        if (btList != null && btList.size() > 0) {
            return btList.get(0);
        }
        return null;
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
            commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));

        } catch (Exception e) {
            logger.debug(this.getClass().toString(), "sendSmsFail", e.getMessage());
        }
    }

    /**
     * 作成转账异常记录
     *
     * @param fromBean
     * @param bankCallBean
     * @param userId
     * @param transAmt
     * @param recoverId
     */
    private void insertTransferExceptionLog(BankCallBean fromBean, BankCallBean bankCallBean, int userId, BigDecimal
            transAmt, String accountId, int recoverId, int transferStatus, String errorMsg, int type) throws Exception {
        TransferExceptionLogExample example = new TransferExceptionLogExample();
        example.createCriteria().andRecoverIdEqualTo(recoverId);
        List<TransferExceptionLog> listLog = this.transferExceptionLogMapper.selectByExample(example);
        if (listLog != null && listLog.size() > 0) {
            // 异常转账记录已经存在，不再执行插入操作
            logger.info("异常转账记录已经存在，不再执行插入操作");
            return;
        }
        Date nowTime = GetDate.getDate();
        TransferExceptionLogWithBLOBs transferExceptionLog = new TransferExceptionLogWithBLOBs();
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
        transferExceptionLog.setCreateTime(nowTime);
        transferExceptionLog.setCreateUser(CustomConstants.OPERATOR_AUTO_REPAY);
        transferExceptionLog.setUpdateTime(nowTime);
        transferExceptionLog.setUpdateUser(CustomConstants.OPERATOR_AUTO_REPAY);
        transferExceptionLog.setDelFlag(0);
        // 转账失败记录
        this.transferExceptionLogMapper.insertSelective(transferExceptionLog);
    }

    /**
     * 判断该收支明细是否存在(加息券)
     *
     * @param nid
     * @return
     */
    private int countAccountListByNidCoupon(String nid) {
        AccountListExample accountListExample = new AccountListExample();
        accountListExample.createCriteria().andNidEqualTo(nid).andTradeEqualTo("increase_interest_profit");
        return this.accountListMapper.countByExample(accountListExample);
    }

    /**
     * 取出账户信息
     *
     * @param userId
     * @return
     */
    public Account getAccountByUserId(Integer userId) {
        AccountExample accountExample = new AccountExample();
        accountExample.createCriteria().andUserIdEqualTo(userId);
        List<Account> listAccount = this.accountMapper.selectByExample(accountExample);
        if (listAccount != null && listAccount.size() > 0) {
            return listAccount.get(0);
        }
        return null;
    }


    /**
     * 写入收支明细
     *
     * @param accountList
     * @return
     */
    private int insertAccountList(AccountList accountList) {
        // 写入收支明细
        return this.accountListMapper.insertSelective(accountList);
    }

    /**
     * 加载红包账户
     *
     * @param accountCode
     * @return
     */
    public BankMerchantAccount getBankMerchantAccount(String accountCode) {
        BankMerchantAccountExample bankMerchantAccountExample = new BankMerchantAccountExample();
        bankMerchantAccountExample.createCriteria().andAccountCodeEqualTo(accountCode);
        List<BankMerchantAccount> bankMerchantAccounts = bankMerchantAccountMapper.selectByExample(bankMerchantAccountExample);
        if (bankMerchantAccounts != null && bankMerchantAccounts.size() != 0) {
            return bankMerchantAccounts.get(0);
        } else {
            return null;
        }
    }

    /**
     * 更新红包账户
     *
     * @param account
     * @return
     */
    public int updateBankMerchantAccount(BankMerchantAccount account) {
        return bankMerchantAccountMapper.updateByPrimaryKeySelective(account);
    }

    /**
     * 发送短信(优惠券还款成功)
     */
    public void sendSmsCoupon(List<Map<String, String>> msgList, UserVO user) throws MQException {
        if (msgList != null && msgList.size() > 0) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(USERID)) && NumberUtils.isNumber(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))) {
                    if (user == null || Validator.isNull(user.getMobile()) || (user.getRecieveSms() != null && user.getRecieveSms() == 1)) {
                        return;
                    }
                    SmsMessage smsMessage = new SmsMessage(Integer.valueOf(msg.get(USERID)), msg, null, null, "smsSendForUser", null,
                            CustomConstants.PARAM_TPL_COUPON_PROFIT, CustomConstants.CHANNEL_TYPE_NORMAL);
                    commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                }
            }
        }
    }

    /**
     * 发送push消息(优惠券还款成功)
     */
    public void sendPushMsgCoupon(List<Map<String, String>> msgList, UserVO user) throws MQException {
        if (msgList != null && msgList.size() > 0) {
            for (Map<String, String> msg : msgList) {
                if (Validator.isNotNull(msg.get(USERID)) && NumberUtils.isNumber(msg.get(USERID)) && Validator.isNotNull(msg.get(VAL_AMOUNT))) {
                    if (user == null) {
                        return;
                    }
                    AppMsMessage appMsMessage = new AppMsMessage(user.getUserId(), msg, null, "appMsSendForUser",
                            CustomConstants.JYTZ_COUPON_PROFIT);
                    commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), appMsMessage));
                }
            }
        }
    }
}