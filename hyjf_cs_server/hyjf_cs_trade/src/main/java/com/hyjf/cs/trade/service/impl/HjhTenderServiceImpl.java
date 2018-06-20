/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.constants.TenderError;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.HjhTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @Description 加入计划
 * @Author sss
 * @Version v0.1
 * @Date 2018/6/19 9:50
 */
@Service
public class HjhTenderServiceImpl extends BaseTradeServiceImpl implements HjhTenderService {
    private static final Logger logger = LoggerFactory.getLogger(HjhTenderServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmBorrowClient amBorrowClient;

    @Autowired
    private CouponClient couponClient;

    @Autowired
    private RechargeClient rechargeClient;

    @Autowired
    private AmBankOpenClient amBankOpenClient;


    /**
     * @param request
     * @Description 检查加入计划的参数
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 9:47
     */
    @Override
    public void joinPlan(TenderRequest request) {
        WebViewUser loginUser = RedisUtils.getObj(request.getToken(), WebViewUser.class);
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = couponClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 查询计划
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            throw new ReturnMessageException(TenderError.NOT_FIND_PLAN_ERROR);
        }
        HjhPlanVO plan = amBorrowClient.getPlanByNid(request.getBorrowNid());
        if (plan.getPlanInvestStatus() == 2) {
            throw new ReturnMessageException(TenderError.PLAN_CLOSE_ERROR);
        }
        logger.info("加入计划投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        // 先检查redis  看用户是否重复投资
        boolean checkToken = checkRedisToken(request);
        if (!checkToken) {
            // 用户正在加入计划
            throw new ReturnMessageException(TenderError.TENDERING_ERROR);
        }
        // 设置redis 的值  防重校验
        String redisValue = GetDate.getCalendar().getTimeInMillis() + GetCode.getRandomCode(5);
        RedisUtils.set(RedisConstants.HJH_TENDER_REPEAT + userId, redisValue, 300);
        // 查询用户信息
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        UserVO user = amUserClient.findUserById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        checkUser(user, userInfo);
        // 检查江西银行账户
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new ReturnMessageException(TenderError.NOT_REGIST_ERROR);
        }
        // 查询用户账户表-投资账户
        AccountVO tenderAccount = rechargeClient.getAccount(userId);
        // 检查投资金额
        checkTenderMoney(request, plan, account, cuc, tenderAccount);
        logger.info("加入计划投资校验通过userId:{},ip:{},平台{},优惠券为:{}", userId, request.getIp(), request.getPlatform(), request.getCouponGrantId());

        // 开始投资------------------------------------------------------------------------------------------------------------------------------------------
        tender(request, plan, account, cuc, tenderAccount);
    }

    // 开始投资
    private void tender(TenderRequest request, HjhPlanVO plan, BankOpenAccountVO account, CouponUserVO cuc, AccountVO tenderAccount) {
        Integer userId = request.getUser().getUserId();
        BigDecimal decimalAccount = new BigDecimal(request.getAccount());
        // 体验金投资
        if (decimalAccount.compareTo(BigDecimal.ZERO) != 1 && cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1)) {
            logger.info("体验{},优惠金投资开始:userId{},平台券为:{}", userId, request.getPlatform(), request.getCouponGrantId());
            // TODO: 2018/6/20 体验金投资
            /*couponTender(modelAndView,result, planNid, account,ip,cuc, userId, couponOldTime,platform,couponGrantId);*/
            logger.info("体验金投资结束:userId{}" + userId);
            return;
        }
        // 生成冻结订单
        String frzzeOrderId = GetOrderIdUtils.getOrderId0(Integer.valueOf(userId));
        String frzzeOrderDate = GetOrderIdUtils.getOrderDate();
    }

    /**
     * @Description 检查投资金额
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 15:52
     */
    private void checkTenderMoney(TenderRequest request, HjhPlanVO plan, BankOpenAccountVO OpenAccount, CouponUserVO cuc, AccountVO tenderAccount) {
        String account = request.getAccount();
        // 投资金额必须是整数
        if (StringUtils.isEmpty(account)) {
            account = "0";
            request.setAccount(account);
        }
        if (!(StringUtils.isNotEmpty(account)
                || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3)
                || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 1
                && cuc.getAddFlg() == 1))) {
            throw new ReturnMessageException(TenderError.MONEY_NULL_ERROR);
        }
        // 投资金额小数点后超过两位
        if (account.contains(".")) {
            String accountSubstr = account.substring(account.indexOf(".") + 1);
            if (StringUtils.isNotEmpty(accountSubstr) && accountSubstr.length() > 2) {
                // 金额格式错误
                throw new ReturnMessageException(TenderError.MONEY_STYLE_ERROR);
            }
        }
        BigDecimal accountBigDecimal = new BigDecimal(account);
        int compareResult = accountBigDecimal.compareTo(BigDecimal.ZERO);
        if (compareResult < 0) {
            // 加入金额不能为负数
            throw new ReturnMessageException(TenderError.MONEY_STYLE_ERROR);
        }
        if ((compareResult == 0 && cuc == null)
                || (compareResult == 0 && cuc != null && cuc.getCouponType() == 2)) {
            // 投资金额不能为0
            throw new ReturnMessageException(TenderError.MONEY_ZERO_ERROR);
        }
        if (compareResult > 0 && cuc != null && cuc.getCouponType() == 1
                && cuc.getAddFlg() == 1) {
            // 投资金额不能为0
            throw new ReturnMessageException(TenderError.COUPON_USER_ONLY_ERROR);
        }
        String balance = RedisUtils.get(RedisConstants.HJH_PLAN + plan.getPlanNid());
        if (StringUtils.isEmpty(balance)) {
            // 您来晚了  下次再来
            throw new ReturnMessageException(TenderError.NO_JOIN_MONEY_ERROR);
        }
        // DB 该计划可投金额
        BigDecimal minInvest = plan.getMinInvestment();// 该计划的最小投资金额
        // 当剩余可投金额小于最低起投金额，不做最低起投金额的限制
        if (minInvest != null && minInvest.compareTo(BigDecimal.ZERO) != 0
                && new BigDecimal(balance).compareTo(minInvest) == -1) {
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
                // 剩余可加入金额为" + balance + "元
                throw new ReturnMessageException(TenderError.JOIN_MONEY_LESS_ERROR);
            }
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                // 剩余可加入只剩" + balance + "元，须全部购买"
                throw new ReturnMessageException(TenderError.JOIN_MONEY_ONLY_LESS_ERROR);
            }
        }
        if (accountBigDecimal.compareTo(plan.getMinInvestment()) == -1) {
            if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                if (cuc != null && cuc.getCouponType() != 3
                        && cuc.getCouponType() != 1) {
                    // plan.getMinInvestment() + "元起投"
                    throw new ReturnMessageException(TenderError.MIN_INVESTMENT_ERROR);
                }
            } else {
                // plan.getMinInvestment() + "元起投"
                throw new ReturnMessageException(TenderError.MIN_INVESTMENT_ERROR);
            }
        }
        BigDecimal max = plan.getMaxInvestment();
        if (max != null && max.compareTo(BigDecimal.ZERO) != 0
                && accountBigDecimal.compareTo(max) == 1) {
            // 项目最大加入额为" + max + "元
            throw new ReturnMessageException(TenderError.MAX_INVESTMENT_ERROR);
        }
        if (accountBigDecimal.compareTo(plan.getAvailableInvestAccount()) > 0) {
            // 加入金额不能大于开放额度
            throw new ReturnMessageException(TenderError.JOIN_MONEY_THAN_ACCOUNT_ERROR);
        }
        if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
            // 你没钱了
            throw new ReturnMessageException(TenderError.NO_MONEY_ERROR);
        }
        // 调用江西银行接口查询可用金额
        BigDecimal userBankBalance = this.getBankBalancePay(request.getUser().getUserId(),
                OpenAccount.getAccount());
        if (userBankBalance.compareTo(accountBigDecimal) < 0) {
            // 你又没钱了
            throw new ReturnMessageException(TenderError.NO_MONEY_ERROR);
        }
        // redis剩余金额不足判断逻辑
        if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
            // "项目太抢手了！剩余可加入金额只有" + balance + "元"
            throw new ReturnMessageException(TenderError.PLAN_MONEY_LESS_ERROR);
        }
        // 开放额度和阀值（1000）判断逻辑
        if (new BigDecimal(balance).compareTo(new BigDecimal(CustomConstants.TENDER_THRESHOLD)) == -1) {
            // 投资金额 != 开放额度
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                // 使用递增的逻辑
                if (plan.getInvestmentIncrement() != null
                        && BigDecimal.ZERO.compareTo((accountBigDecimal.subtract(minInvest)).remainder(plan.getInvestmentIncrement())) != 0) {
                    // 加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍
                    throw new ReturnMessageException(TenderError.MONEY_INTEGRAL_MULTIPLE_ERROR);
                }
            }
        } else {
            // (用户投资额度 - 起投额度)%增量 = 0
            if (plan.getInvestmentIncrement() != null
                    && BigDecimal.ZERO.compareTo(accountBigDecimal.subtract(minInvest).remainder(plan.getInvestmentIncrement())) != 0
                    && accountBigDecimal.compareTo(new BigDecimal(balance)) == -1) {
                // 加入递增金额须为" + plan.getInvestmentIncrement() + " 元的整数倍
                throw new ReturnMessageException(TenderError.MONEY_INTEGRAL_MULTIPLE_ERROR);
            }
        }
    }

    /**
     * @Description 检查用户状态  角色  授权状态 等  是否允许投资
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 11:37
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        if (userInfo.getRoleId() == 3) {// 担保机构用户
            throw new ReturnMessageException(TenderError.USER_ROLE_ERROR);
        }
        if (userInfo.getRoleId() == 2) {// 借款人不能投资
            throw new ReturnMessageException(TenderError.USER_ROLE_ERROR);
        }
        // 判断用户是否禁用
        if (user.getStatus() == 1) {// 0启用，1禁用
            throw new ReturnMessageException(TenderError.USER_DISABLED_ERROR);
        }
        // 检查用户授权状态
        HjhUserAuthVO userAuth = amUserClient.getHjhUserAuthVO(user.getUserId());
        // 为空则无授权
        if (userAuth == null) {
            throw new ReturnMessageException(TenderError.USER_AUTO_INVES_ERROR);
        } else {
            if (userAuth.getAutoInvesStatus() == 0) {
                throw new ReturnMessageException(TenderError.USER_AUTO_INVES_ERROR);
            }
            if (userAuth.getAutoCreditStatus() == 0) {
                throw new ReturnMessageException(TenderError.USER_AUTO_CREDIT_ERROR);
            }
        }
        // 服务费授权校验
        if (userAuth.getAutoPaymentStatus() == 0) {
            throw new ReturnMessageException(TenderError.USER_PAYMENT_AUTH_ERROR);
        }
        // 风险测评校验
        this.checkEvaluation(user);
    }

    /**
     * @Description 检查redis里面的缓存  用户是否能投资
     * @Author sunss
     * @Version v0.1
     * @Date 2018/6/19 10:38
     */
    private boolean checkRedisToken(TenderRequest request) {
        String token_ = "";
        if (RedisUtils.exists(RedisConstants.HJH_TENDER_REPEAT + request.getUser().getUserId())) {
            // 如果已经有了
            String redisTenderToken = RedisUtils.get(token_ + request.getUser().getUserId());
            if (!redisTenderToken.equals(request.getTenderToken())) {
                logger.info("用户已经加入计划userId:{},ip:{},ip{},平台{}", request.getUser().getUserId(), request.getIp(), request.getPlatform());
                return false;
            }
        }

        return true;
    }
}
