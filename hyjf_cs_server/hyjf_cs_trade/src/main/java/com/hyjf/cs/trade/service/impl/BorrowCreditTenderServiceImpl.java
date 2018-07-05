/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.BorrowCreditVO;
import com.hyjf.am.vo.trade.CreditTenderLogVO;
import com.hyjf.am.vo.trade.TenderToCreditAssignCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowRecoverVO;
import com.hyjf.am.vo.trade.borrow.BorrowRepayPlanVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.calculate.BeforeInterestAfterPrincipalUtils;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowCreditTenderService;
import com.hyjf.cs.trade.service.CouponService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description 投资接口
 * @Author sunss
 * @Date 2018/6/24 14:30
 */
@Service
public class BorrowCreditTenderServiceImpl extends BaseTradeServiceImpl implements BorrowCreditTenderService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowCreditTenderServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBorrowClient amBorrowClient;

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private RechargeClient rechargeClient;

    @Autowired
    private AmMongoClient amMongoClient;

    @Autowired
    private CouponService couponService;

    @Autowired
    private BorrowClient borrowClient;

    @Autowired
    private AutoSendClient autoSendClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private BorrowTenderClient borrowTenderClient;

    @Autowired
    private CreditClient creditClient;

    @Autowired
    private BorrowRecoverClient borrowRecoverClient;

    @Autowired
    private AmBorrowRepayPlanClient amBorrowRepayPlanClient;


    private static String regex = "^[-+]?(([0-9]+)(([0-9]+))?|(([0-9]+))?)$";
    private static DecimalFormat DF_COM_VIEW = new DecimalFormat("######0.00");
    private static String oldOrNewDate = "2016-12-27 20:00:00";

    /**
     * 债转投资
     *
     * @param request
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> borrowCreditTender(TenderRequest request)  {
        WebViewUserVO loginUser = RedisUtils.getObj(request.getToken(), WebViewUserVO.class);
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        // 检查请求参数是否正确
        this.checkRequest(request);
        // 获取债转数据
        BorrowCreditVO borrowCredit = creditClient.getBorrowCreditByCreditNid(request.getCreditNid());
        if(borrowCredit==null){
            // 获取债转数据错误
            throw new CheckException(MsgEnum.ERROR_CREDIT_NOT_EXIST);
        }
        UserVO user = amUserClient.findUserById(userId);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        this.checkUser(user,userInfo,bankOpenAccount,borrowCredit);
        // 查询用户账户表-投资账户
        AccountVO tenderAccount = rechargeClient.getAccount(userId);
        // 前端Web页面投资可债转输入投资金额后收益提示 用户未登录 (包含查询条件)
        TenderToCreditAssignCustomizeVO creditAssign = this.creditClient.getInterestInfo(request.getCreditNid(), request.getAssignCapital(),userId);
        // 检查金额
        this.checkTenderMoney(request, tenderAccount,creditAssign);
        // 获取插入债转日志的数据
        CreditTenderLogVO creditTenderLog = this.getCreditTenderLog(request,user,borrowCredit);
        // 获取调用银行的参数
        BankCallBean bean = this.getCreditBankCallBean(request,user,bankOpenAccount,creditAssign,creditTenderLog);
        Map<String,Object> callData = null;
        try{
            callData = BankCallUtils.callApiMap(bean);
        }catch (Exception e){
            // 调用银行接口失败
            throw new CheckException(MsgEnum.ERR_BANK_CALL);
        }
        WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
        result.setData(callData);
        // 保存债转的日志
        saveCreditTenderAssignLog(bean,creditTenderLog);
        return result;
    }

    /**
     * 插入债转日志表
     * @param bean
     * @param creditTenderLog
     */
    private void saveCreditTenderAssignLog(BankCallBean bean, CreditTenderLogVO creditTenderLog) {
        creditTenderLog.setTxDate(Integer.parseInt(bean.getTxDate()));
        creditTenderLog.setTxTime(Integer.parseInt(bean.getTxTime()));
        creditTenderLog.setSeqNo(Integer.parseInt(bean.getSeqNo()));
        creditTenderLog.setClient(bean.getLogClient());
        // 银行请求订单号
        creditTenderLog.setLogOrderId(bean.getLogOrderId());
        // 检查是否能债转  ？？？原来的逻辑不用了1726行CreditServiceImpl
        // 插入债转日志表
        Integer saveCount = borrowTenderClient.saveCreditTenderAssignLog(creditTenderLog);
    }

    /**
     * 获取插入债转日志的数据
     * @param request
     * @param user
     * @param borrowCredit
     * @return
     */
    private CreditTenderLogVO getCreditTenderLog(TenderRequest request, UserVO user, BorrowCreditVO borrowCredit) {
        String assignCapital = request.getAssignCapital();
        CreditTenderLogVO creditTenderLog = new CreditTenderLogVO();
        BorrowRecoverVO borrowRecover = borrowRecoverClient.getBorrowRecoverByTenderNidBidNid(borrowCredit.getTenderNid(), borrowCredit.getBidNid());
        if (borrowRecover == null) {
            // 未查询到用户的放款记录
            throw new CheckException(MsgEnum.ERROR_CREDIT_NO_MONEY);
        }
        // 如果放款时间小于 20170703 重新计算已承接金额
        if (borrowRecover.getAddTime() < 1499011200 && borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
            // 计算已承接的债权
            BigDecimal assignedCapital = borrowTenderClient.getAssignCapital(borrowRecover.getNid());
            creditTenderLog.setTenderMoney(borrowRecover.getRecoverCapital().subtract(assignedCapital));
        } else {
            creditTenderLog.setTenderMoney(borrowRecover.getRecoverCapital());
        }
        // 获取借款数据
        BorrowVO borrow = borrowClient.selectBorrowByNid(borrowCredit.getBidNid());
        if (borrow == null) {
            // 标的信息不存在  当前认购人数太多,提交的认购债权本金已经失效,或者可以稍后再试
            throw new CheckException(MsgEnum.ERROR_CREDIT_NO_BORROW);
        }
        // 计算折后价格
        BigDecimal assignPrice = new BigDecimal(assignCapital).setScale(2, BigDecimal.ROUND_DOWN)
                .subtract(new BigDecimal(assignCapital).multiply(borrowCredit.getCreditDiscount().divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN));
        BigDecimal yearRate = borrowCredit.getBidApr().divide(new BigDecimal("100"));
        // 获取标的借款人
        String borrowStyle = borrow.getBorrowStyle();
        // 债转本息
        BigDecimal creditAccount = null;
        // 债转期全部利息
        BigDecimal creditInterest = null;
        // 垫付利息 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
        BigDecimal assignInterestAdvance = null;
        // 债转利息
        BigDecimal assignPayInterest = null;
        // 实付金额 承接本金*（1-折价率）+应垫付利息
        BigDecimal assignPay = null;
        // 剩余待承接本金
        BigDecimal sellerCapitalWait = borrowCredit.getCreditCapital().subtract(borrowCredit.getCreditCapitalAssigned());
        // 待承接的待收收益
        BigDecimal sellerInterestWait = borrowCredit.getCreditInterest().subtract(borrowCredit.getCreditInterestAssigned());
        // 待垫付的垫付利息
        BigDecimal sellerInterestAdvanceWait = borrowCredit.getCreditInterestAdvance().subtract(borrowCredit.getCreditInterestAdvanceAssigned());
        // 到期还本还息和按天计息，到期还本还息
        if (borrowStyle.equals(CalculatesUtil.STYLE_END) || borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
            // 剩余天数
            int lastDays = borrowCredit.getCreditTerm();
            // 按天
            if (borrowStyle.equals(CalculatesUtil.STYLE_ENDDAY)) {
                creditAccount = DuePrincipalAndInterestUtils.getDayPrincipalInterestAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestWait, yearRate,
                        borrow.getBorrowPeriod());
                // 垫付利息
                // 垫息总额=债权本金*年化收益÷360*融资期限-债权本金*年化收益÷360*剩余期限
                assignInterestAdvance = DuePrincipalAndInterestUtils.getDayAssignInterestAdvanceAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestAdvanceWait,
                        yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays));
                // 债转期全部利息
                creditInterest = DuePrincipalAndInterestUtils.getDayInterestAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestWait, yearRate,
                        borrow.getBorrowPeriod());
                // 债转利息
                assignPayInterest = creditInterest;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = assignPrice.add(assignInterestAdvance);
            } else {// 按月
                // 债转本息
                creditAccount = DuePrincipalAndInterestUtils.getMonthPrincipalInterestAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestWait, yearRate,
                        borrow.getBorrowPeriod());
                // 债转期全部利息
                creditInterest = DuePrincipalAndInterestUtils.getMonthInterestAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait, sellerInterestWait, yearRate,
                        borrow.getBorrowPeriod());
                // 垫付利息
                // 垫息总额=债权本金*年化收益÷12*融资期限-债权本金*年化收益÷360*剩余天数
                assignInterestAdvance = DuePrincipalAndInterestUtils.getMonthAssignInterestAdvanceAfterCredit(new BigDecimal(assignCapital), sellerCapitalWait,
                        sellerInterestAdvanceWait, yearRate, borrow.getBorrowPeriod(), new BigDecimal(lastDays));
                // 债转利息
                // assignPayInterest =
                // creditInterest.subtract(assignInterestAdvance);
                assignPayInterest = creditInterest;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = assignPrice.add(assignInterestAdvance);
            }
        }
        // 先息后本
        if (borrowStyle.equals(CalculatesUtil.STYLE_ENDMONTH)) {
            int lastDays = 0;
            String bidNid = borrow.getBorrowNid();
            List<BorrowRepayPlanVO> borrowRepayPlans = amBorrowRepayPlanClient.selectBorrowRepayPlan(bidNid, borrow.getBorrowPeriod());

            if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
                try {
                    String nowDate = GetDate.getDateTimeMyTimeInMillis(borrowCredit.getAddTime());
                    String recoverDate = GetDate.getDateTimeMyTimeInMillis(borrowRepayPlans.get(0).getRepayTime());
                    lastDays = GetDate.daysBetween(nowDate, recoverDate);
                } catch (Exception e) {
                    throw new CheckException(MsgEnum.ERROR_CREDIT_DATA_ERROR);
                }
            }
            // 已还多少期
            int repayPeriod = borrowCredit.getRecoverPeriod();
            if (new BigDecimal(assignCapital).compareTo(sellerCapitalWait) == 0) {
                // 最后一笔承接
                creditAccount = sellerCapitalWait.add(sellerInterestWait);
                // 承接人剩余利息
                assignPayInterest = sellerInterestWait;
                // 剩余垫付利息
                assignInterestAdvance = sellerInterestAdvanceWait;
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = assignPrice.add(assignInterestAdvance);
            } else {
                // 承接人每月应还利息
                BigDecimal interestAssign = BeforeInterestAfterPrincipalUtils.getPerTermInterest(new BigDecimal(assignCapital), borrowCredit.getBidApr().divide(new BigDecimal(100)),
                        borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
                // 应还总额
                creditAccount = new BigDecimal(assignCapital).add(interestAssign.multiply(new BigDecimal(borrow.getBorrowPeriod() - repayPeriod)));
                // 债转期全部利息
                creditInterest = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(assignCapital), yearRate, borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
                // 承接人剩余利息
                assignPayInterest = interestAssign.multiply(new BigDecimal(borrow.getBorrowPeriod() - repayPeriod));
                // 出让人每月应还利息
                BigDecimal interest = BeforeInterestAfterPrincipalUtils.getPerTermInterest(borrowCredit.getCreditCapital(), borrowCredit.getBidApr().divide(new BigDecimal(100)),
                        borrow.getBorrowPeriod(), borrow.getBorrowPeriod());
                // 垫息总额=投资人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
                assignInterestAdvance = BeforeInterestAfterPrincipalUtils.getAssignInterestAdvance(new BigDecimal(assignCapital), borrowCredit.getCreditCapital(), yearRate, interest,
                        new BigDecimal(lastDays + ""));
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = assignPrice.add(assignInterestAdvance);
            }
        }
        // 保存credit_tender_log表
        creditTenderLog.setUserId(user.getUserId());
        creditTenderLog.setCreditUserId(borrowCredit.getCreditUserId());
        creditTenderLog.setStatus(0);
        // 因为标的号必须六位之内 所以用id  去掉 setBorrowId
        creditTenderLog.setBidNid(borrowCredit.getBidNid());
        creditTenderLog.setCreditNid(String.valueOf(borrowCredit.getCreditNid()));
        creditTenderLog.setCreditTenderNid(borrowCredit.getTenderNid());
        creditTenderLog.setAssignCapital(new BigDecimal(assignCapital).setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignAccount(creditAccount.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignInterest(assignPayInterest.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignInterestAdvance(assignInterestAdvance.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignPrice(assignPrice.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignPay(assignPay.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignRepayAccount(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignRepayCapital(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignRepayInterest(BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_DOWN));
        creditTenderLog.setAssignRepayEndTime(borrowCredit.getCreditRepayEndTime());
        creditTenderLog.setAssignRepayLastTime(borrowCredit.getCreditRepayLastTime());
        creditTenderLog.setAssignRepayNextTime(borrowCredit.getCreditRepayNextTime());
        creditTenderLog.setAssignRepayYesTime(0);
        // 还剩余多少期
        creditTenderLog.setAssignRepayPeriod(borrowCredit.getCreditPeriod());
        creditTenderLog.setAssignCreateDate(Integer.parseInt(GetDate.yyyyMMdd.format(new Date())));
        Long ifOldDate = null;
        try {
            ifOldDate = GetDate.datetimeFormat.parse(oldOrNewDate).getTime() / 1000;
        } catch (Exception e) {
            System.err.println("债转算是否是新旧标区分时间错误，债转标号:" + borrowCredit.getCreditNid());
        }
        if (ifOldDate != null && ifOldDate <= borrowCredit.getAddTime()) {
            creditTenderLog.setCreditFee(assignPay.multiply(new BigDecimal(0.01)));
        } else {
            creditTenderLog.setCreditFee(assignPay.multiply(new BigDecimal(0.005)));
        }
        creditTenderLog.setAddIp(request.getIp());
        return creditTenderLog;
    }

    /**
     * 获取调用银行的参数
     * @param request
     * @param user
     * @param bankOpenAccount
     * @param creditAssign
     * @param creditTenderLog
     * @return
     */
    private BankCallBean getCreditBankCallBean(TenderRequest request, UserVO user, BankOpenAccountVO bankOpenAccount, TenderToCreditAssignCustomizeVO creditAssign, CreditTenderLogVO creditTenderLog) {
        BankCallBean bean =  new BankCallBean(user.getUserId(),BankCallConstant.TXCODE_CREDITINVEST,Integer.parseInt(request.getPlatform()),BankCallConstant.BANK_URL_MOBILE_CREDITINVEST);
        BankOpenAccountVO accountChinapnrCrediter = amUserClient.selectBankAccountById(creditTenderLog.getCreditUserId());
        bean.setAccountId(bankOpenAccount.getAccount());
        // 实付金额 承接本金*（1-折价率）+应垫付利息
        bean.setTxAmount(DF_COM_VIEW.format(creditAssign.getAssignPay()));
        bean.setTxFee(creditTenderLog.getCreditFee() != null ? DF_COM_VIEW.format(creditTenderLog.getCreditFee()) : "0.01");
        bean.setTsfAmount(DF_COM_VIEW.format(creditTenderLog.getAssignCapital()));
        // 对手电子账号:卖出方账号
        bean.setForAccountId(accountChinapnrCrediter.getAccount());
        bean.setOrgOrderId(creditTenderLog.getCreditTenderNid());
        bean.setOrgTxAmount(DF_COM_VIEW.format(creditTenderLog.getTenderMoney()));
        bean.setProductId(creditTenderLog.getBidNid());
        // 忘记密码的跳转URL
        bean.setForgotPwdUrl(systemConfig.getForgetpassword());
        // TODO: 2018/7/4  前端提供地址
        String retUrl = systemConfig.getFrontHost() + "/user/openError"+"?logOrdId="+bean.getLogOrderId();
        String successUrl = systemConfig.getFrontHost() +"/user/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/tender/credit/bgReturn";
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setLogRemark("债转投资");
        return bean;
    }

    /**
     * 检查金额相关
     * @param request
     * @param tenderAccount
     * @param creditAssign
     */
    private void checkTenderMoney(TenderRequest request, AccountVO tenderAccount, TenderToCreditAssignCustomizeVO creditAssign) {
        // 验证用户余额是否可以债转
        String assignPay = creditAssign.getAssignTotal();
        if (tenderAccount.getBankBalance() != null && tenderAccount.getBankBalance().compareTo(BigDecimal.ONE) >= 0) {
            if (tenderAccount.getBankBalance().compareTo(new BigDecimal(assignPay)) < 0) {
                // 余额不足
                throw new CheckException(MsgEnum.ERROR_CREDIT_NO_MONEY);
            }
        } else {
            // 余额不足
            throw new CheckException(MsgEnum.ERROR_CREDIT_NO_MONEY);
        }
    }

    /**
     * 检查用户状态
     * @param user
     * @param userInfo
     * @param bankOpenAccount
     * @param borrowCredit
     */
    private void checkUser(UserVO user, UserInfoVO userInfo, BankOpenAccountVO bankOpenAccount, BorrowCreditVO borrowCredit) {
        // 担保机构用户
        if (userInfo.getRoleId() == 3) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
        }
        // 借款人不能投资
        if (userInfo.getRoleId() == 2) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
        }
        // 判断用户是否禁用  0启用，1禁用
        if (user.getStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_USER_INVALID);
        }
        // 用户未开户
        if (user.getBankOpenAccount() == 0) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码状态检查
        if (user.getIsSetPassword() == 0) {
            throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        // 风险测评校验
        this.checkEvaluation(user);
        if (bankOpenAccount == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(bankOpenAccount.getAccount())) {
            // 未开户
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        if(borrowCredit.getCreditUserId().intValue()==user.getUserId().intValue()){
            // 不可以承接自己出让的债权
            throw new CheckException(MsgEnum.ERROR_CREDIT_CANT_BBY_YOURSELF);
        }
    }

    /**
     * 检查请求参数是否为空
     * @param request
     */
    private void checkRequest(TenderRequest request) {
        if (StringUtils.isEmpty(request.getCreditNid()) || StringUtils.isEmpty(request.getAssignCapital())) {
            // 债转编号和承接本金不能为空
            throw new CheckException(MsgEnum.ERROR_CREDIT_NID_CAPITAL_NULL);
        }
        if (!request.getAssignCapital().matches(regex) || !Validator.isNumber(request.getCreditNid())) {
            // 债转编号和承接本金必须是数字格式
            throw new CheckException(MsgEnum.ERROR_CREDIT_NID_CAPITAL_NUMBER);
        }
    }
}
