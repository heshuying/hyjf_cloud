/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.CouponUserVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.BorrowProjectTypeVO;
import com.hyjf.am.vo.trade.borrow.BorrowVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.CustomUtil;
import com.hyjf.common.util.DigitalUtils;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.cs.trade.service.CouponService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Description 投资接口
 * @Author sunss
 * @Date 2018/6/24 14:30
 */
@Service
public class BorrowTenderServiceImpl extends BaseTradeServiceImpl implements BorrowTenderService {
    private static final Logger logger = LoggerFactory.getLogger(BorrowTenderServiceImpl.class);

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


    /**
     * @param request
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    @Override
    public WebResult<Map<String, Object>> borrowTender(TenderRequest request) {
        WebViewUserVO loginUser = RedisUtils.getObj(request.getToken(), WebViewUserVO.class);
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            // 项目编号不能为空
            throw new ReturnMessageException(MsgEnum.STATUS_CE000013);
        }
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = couponClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 查询散标是否存在
        BorrowVO borrow = borrowClient.selectBorrowByNid(request.getBorrowNid());
        if (borrow == null) {
            throw new ReturnMessageException(MsgEnum.FIND_BORROW_ERROR);
        }
        logger.info("散标投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        UserVO user = amUserClient.findUserById(request.getUser().getUserId());
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        checkUser(user, userInfo);
        // 检查江西银行账户
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 查询用户账户表-投资账户
        AccountVO tenderAccount = rechargeClient.getAccount(userId);
        // 投资检查参数
        this.checkParam(request, borrow, account, userInfo,user);
        // 检查金额
        this.checkTenderMoney(request, borrow, cuc, tenderAccount,userInfo,user);
        // 开始真正的投资逻辑
        return tender(request, borrow, account, cuc, tenderAccount,userInfo,user);
    }

    /**
     * 开始真正的投资
     * @param request
     * @param borrow
     * @param account
     * @param cuc
     * @param tenderAccount
     * @param userInfo
     * @param user
     * @return
     */
    private WebResult<Map<String,Object>> tender(TenderRequest request, BorrowVO borrow, BankOpenAccountVO account, CouponUserVO cuc, AccountVO tenderAccount, UserInfoVO userInfo, UserVO user) {
        // 生成订单id
        Integer userId = request.getUser().getUserId();
        String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));
        request.setOrderId(orderId);
        BankCallBean callBean =  new BankCallBean(userId,BankCallConstant.TXCODE_BID_APPLY,Integer.parseInt(request.getPlatform()),BankCallConstant.BANK_URL_MOBILE_BIDAPPLY);
        callBean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号
        callBean.setChannel(BankCallConstant.CHANNEL_PC);// 交易渠道
        callBean.setAccountId(account.getAccount());// 电子账号
        callBean.setOrderId(orderId);// 订单号
        callBean.setTxAmount(CustomUtil.formatAmount(request.getAccount()));// 交易金额
        callBean.setProductId(borrow.getBorrowNid());// 标的号
        callBean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_UNFREEZE);// 是否冻结金额  实时放款投资不冻结
        callBean.setLogOrderId(orderId);// 订单号
        callBean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单日期
        callBean.setLogIp(request.getIp());// 客户IP
        callBean.setLogUserId(String.valueOf(userId));// 投资用户
        callBean.setLogUserName(request.getUser().getUsername());// 投资用户名
        callBean.setLogClient(Integer.parseInt(request.getPlatform()));

        String retUrl = systemConfig.getFrontHost() + "/user/openError"+"?logOrdId="+orderId;
        String successUrl = systemConfig.getFrontHost() +"/user/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/secure/open/bgReturn?" ;
        //忘记密码url
        String forgetPassworedUrl = CustomConstants.FORGET_PASSWORD_URL;
        callBean.setRetUrl(retUrl);
        callBean.setSuccessfulUrl(successUrl);
        callBean.setNotifyUrl(bgRetUrl);
        callBean.setForgotPwdUrl(forgetPassworedUrl);
        // 插入记录 tmp表
        amBorrowClient.updateBeforeChinaPnR(request);
        try{
            Map<String,Object> map = BankCallUtils.callApiMap(callBean);
            WebResult<Map<String,Object>> result = new WebResult<Map<String,Object>>();
            result.setData(map);
            return result;
        }catch (Exception e){
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
    }

    /**
     * 检查投资金额
     * @param request
     * @param borrow
     * @param cuc
     * @param tenderAccount
     * @param userInfo
     * @param user
     */
    private void checkTenderMoney(TenderRequest request, BorrowVO borrow, CouponUserVO cuc, AccountVO tenderAccount, UserInfoVO userInfo, UserVO user) {
        String account = request.getAccount();
        // 判断用户投资金额是否为空
        if (!(StringUtils.isNotEmpty(account) || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3))) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        // 投资金额是否数值
        if (!DigitalUtils.isNumber(account)) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_FORMAT);
        }
        // 投资金额不能为0元
        if (("0".equals(account) && cuc == null)
                        || ("0".equals(account) && cuc != null && cuc.getCouponType() == 2)) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        // 投资金额是否为整数
        if (!DigitalUtils.isInteger(account)) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_INT);
        }
        int accountInt = Integer.parseInt(account);
        // 该优惠券只能单独使用
        if (accountInt != 0 && cuc != null && cuc.getCouponType() == 1 && cuc.getAddFlg() == 1) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_COUPON_USE_ALONE);
        }
        // 投资金额不能为负数
        if (accountInt < 0) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_NEGATIVE);
        }
        // 将投资金额转化为BigDecimal
        BigDecimal accountBigDecimal = new BigDecimal(account);
        String balance = RedisUtils.get(borrow.getBorrowNid());
        // 您来晚了，下次再来抢吧
        if (StringUtils.isEmpty(balance)) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
        }
        // 起投金额
        Integer min = borrow.getTenderAccountMin();
        // 当剩余可投金额小于最低起投金额，不做最低起投金额的限制
        if (min != null && min != 0 && new BigDecimal(balance).compareTo(new BigDecimal(min)) == -1) {
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
                throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_BIG);
            }
            // 剩余可投只剩
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_BORROW_MONEY_LESS_NEED_BUY_ALL);
            }
        } else {// 项目的剩余金额大于最低起投金额
            if (accountBigDecimal.compareTo(new BigDecimal(min)) == -1) {
                if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                    if (cuc != null && cuc.getCouponType() != 3 && cuc.getCouponType() != 1) {
                        throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT);
                    }
                } else {
                    throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT);
                }
            } else {
                Integer max = borrow.getTenderAccountMax();
                if (max != null && max != 0 && accountBigDecimal.compareTo(new BigDecimal(max)) == 1) {
                    // "项目最大投资额为" + max + "元", "1"
                    throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MAX_INVESTMENT);
                }
            }
        }
        // 投资金额不能大于项目总额
        if (accountBigDecimal.compareTo(borrow.getAccount()) > 0) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_GREATER_THAN_TOTAL);
        }
        // 可用金额不足
        if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_NOT_ENOUGH);
        }
        // redis剩余金额不足
        // 投资金额不能大于项目剩余
        if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_BIG);
        }
        // add by cwyang 在只使用代金券和体验金,并且没有本金的情况下,不进行投资递增金额的判断,在投资金额等于最大可投金额时也不做递增金额的判断
        if (!(cuc != null && (cuc.getCouponType() == 3||cuc.getCouponType() == 1) && accountInt == 0)) {
            // 投资递增金额须为" + borrowDetail.getIncreaseMoney() + " 元的整数倍
            if (borrow.getBorrowIncreaseMoney()!= null && (accountInt - min) % borrow.getBorrowIncreaseMoney() != 0
                    && accountBigDecimal.compareTo(new BigDecimal(balance)) == -1 && accountInt < borrow.getTenderAccountMax()) {
                throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_INCREMENTING);
            }
        }
    }

    /**
     *  检查用户状态  角色  授权状态等  是否允许投资
     * @param user
     * @param userInfo
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        if (userInfo.getRoleId() == 3) {// 担保机构用户
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
        }
        if (userInfo.getRoleId() == 2) {// 借款人不能投资
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
        }
        // 判断用户是否禁用
        if (user.getStatus() == 1) {// 0启用，1禁用
            throw new ReturnMessageException(MsgEnum.ERR_USER_INVALID);
        }
        // 用户未开户
        if (user.getBankOpenAccount() == 0) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码状态检查
        if (user.getIsSetPassword() == 0) {
            throw new ReturnMessageException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        // 风险测评校验
        this.checkEvaluation(user);
    }

    /**
     * 散标投资参数校验
     * @param request
     * @param borrow
     * @param account
     * @param userInfo
     * @param user
     */
    private void checkParam(TenderRequest request, BorrowVO borrow, BankOpenAccountVO account,UserInfoVO userInfo, UserVO user) {
        Integer userId = request.getUser().getUserId();
        // 借款人不存在
        if (borrow.getUserId() == null) {
            throw new ReturnMessageException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        Integer projectType = borrow.getProjectType();// 0，51老用户；1，新用户；2，全部用户
        // 未设置该投资项目的项目类型
        if (projectType == null) {
            throw new ReturnMessageException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(projectType));
        // 未查询到该投资项目的设置信息
        if (borrowProjectType == null) {
            throw new ReturnMessageException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        // 51老用户标
        if (borrowProjectType.getInvestUserType().equals("0")) {
            Integer is51 = userInfo.getIs51();// 1是51，0不是
            // 该项目只能51老用户投资
            if (is51 != null && is51 == 1) {
                throw new ReturnMessageException(MsgEnum.ERR_TRADE_51_OLD_USER);
            }
        }
        // 新手标
        if (borrowProjectType.getInvestUserType().equals("1")) {
            boolean isNew = this.checkIsNewUserCanInvest(userId);
            // 该项目只能新手投资
            if (!isNew) {
                throw new ReturnMessageException(MsgEnum.ERR_TRADE_NEW_USER);
            }
        }
        // 用户未在平台开户
        if (account == null) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 判断借款人开户信息是否存在
        if (account.getAccount() == null) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 投资客户端 校验
        // 投资平台不能为空
        if(request.getPlatform()==null){
            throw new ReturnMessageException(MsgEnum.STATUS_ZC000018);
        }
        if (request.getPlatform().equals("2") && borrow.getCanTransactionAndroid().equals("0")) {
            String tmpInfo = "";
            if (borrow.getCanTransactionPc().equals("1")) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if (borrow.getCanTransactionIos().equals("1")) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_IOS);
            }
            if (borrow.getCanTransactionWei().equals("1")) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        } else if (request.getPlatform().equals("3") && borrow.getCanTransactionIos().equals("0")) {
            if (borrow.getCanTransactionPc().equals("1")) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if (borrow.getCanTransactionAndroid().equals("1")) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_ANDROID);
            }
            if (borrow.getCanTransactionWei().equals("1")) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        }
        // 借款人不可以自己投资项目
        if (userId.equals(String.valueOf(borrow.getUserId()))) {
            throw new ReturnMessageException(MsgEnum.ERR_TENDER_YOURSELF);
        }
        // 判断借款是否流标
        if (borrow.getStatus() == 6) { // 流标
            throw new ReturnMessageException(MsgEnum.ERR_TENDER_BIDDERS);
        }
        // 已满标
        if (borrow.getBorrowFullStatus() == 1) {
            throw new ReturnMessageException(MsgEnum.ERR_TENDER_FULL_STANDARD);
        }
    }

    /**
     * 获取项目类型
     * @param projectType
     * @return
     */
    private BorrowProjectTypeVO getProjectType(String projectType) {
        BorrowProjectTypeVO borrowProjectType = null;
        List<BorrowProjectTypeVO> projectTypes = autoSendClient.selectBorrowProjectByBorrowCd(projectType);
        if (projectTypes != null && projectTypes.size() == 1) {
            borrowProjectType = projectTypes.get(0);
        }
        return borrowProjectType;
    }

    /**
     * 散标投资异步处理
     *
     * @param bean
     * @return
     */
    @Override
    public BankCallResult borrowTenderBgReturn(BankCallBean bean) {
        int userId = StringUtils.isBlank(bean.getLogUserId()) ? 0 : Integer.parseInt(bean.getLogUserId());// 用户Userid
        String respCode = bean.getRetCode();// 投资结果返回码
        Integer platForm = bean.getLogClient();
        BankCallResult result = new BankCallResult();
        result.setStatus(true); // true 的话PAY工程就不调用了  false的话PAY还会调用
        if (StringUtils.isBlank(respCode)) {
            result.setStatus(false);
            result.setMessage("银行返回码为空");
            return result;
        }
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(respCode)) {
            // 返回码提示余额不足，不结冻
            if (BankCallConstant.RETCODE_BIDAPPLY_YUE_FAIL.equals(respCode)) {
                logger.info("PC用户:" + userId + "**投资接口调用失败，余额不足，错误码: " + respCode);
                result.setMessage("投资失败，可用余额不足！请联系客服.");
                return result;
            } else {
                result.setMessage(bean.getRetMsg());
                return result;
            }
        }

        return null;
    }
}
