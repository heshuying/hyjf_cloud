/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.impl;

import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.statistics.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.service.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.BorrowTenderService;
import com.hyjf.cs.trade.service.CouponService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;
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

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private BorrowTenderClient borrowTenderClient;

    @Autowired
    private BorrowTenderCpnClient borrowTenderCpnClient;

    @Autowired
    private CouponConfigClient couponConfigClient;
    @Autowired
    private CouponUserClient couponUserClient;

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
        this.checkParam(request, borrow, account, userInfo, user);
        // 检查金额
        this.checkTenderMoney(request, borrow, cuc, tenderAccount, userInfo, user);
        // 开始真正的投资逻辑
        return tender(request, borrow, account, cuc, tenderAccount, userInfo, user);
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
    private WebResult<Map<String, Object>> tender(TenderRequest request, BorrowVO borrow, BankOpenAccountVO account, CouponUserVO cuc, AccountVO tenderAccount, UserInfoVO userInfo, UserVO user) {
        // 生成订单id
        Integer userId = request.getUser().getUserId();
        String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));
        request.setOrderId(orderId);
        BankCallBean callBean = new BankCallBean(userId, BankCallConstant.TXCODE_BID_APPLY, Integer.parseInt(request.getPlatform()), BankCallConstant.BANK_URL_MOBILE_BIDAPPLY);
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

        String retUrl = systemConfig.getFrontHost() + "/user/openError" + "?logOrdId=" + orderId;
        String successUrl = systemConfig.getFrontHost() + "/user/openSuccess?logOrdId="+orderId;
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/secure/open/bgReturn?couponGrantId=" + cuc.getId();
        //忘记密码url
        String forgetPassWoredUrl = CustomConstants.FORGET_PASSWORD_URL;
        callBean.setRetUrl(retUrl);
        callBean.setSuccessfulUrl(successUrl);
        callBean.setNotifyUrl(bgRetUrl);
        callBean.setForgotPwdUrl(forgetPassWoredUrl);
        // 插入记录 tmp表
        amBorrowClient.updateBeforeChinaPnR(request);
        try {
            Map<String, Object> map = BankCallUtils.callApiMap(callBean);
            WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
            result.setData(map);
            return result;
        } catch (Exception e) {
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
        if (!(cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1) && accountInt == 0)) {
            // 投资递增金额须为" + borrowDetail.getIncreaseMoney() + " 元的整数倍
            if (borrow.getBorrowIncreaseMoney() != null && (accountInt - min) % borrow.getBorrowIncreaseMoney() != 0
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
    private void checkParam(TenderRequest request, BorrowVO borrow, BankOpenAccountVO account, UserInfoVO userInfo, UserVO user) {
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
        if ("0".equals(borrowProjectType.getInvestUserType())) {
            Integer is51 = userInfo.getIs51();// 1是51，0不是
            // 该项目只能51老用户投资
            if (is51 != null && is51 == 1) {
                throw new ReturnMessageException(MsgEnum.ERR_TRADE_51_OLD_USER);
            }
        }
        // 新手标
        if ("1".equals(borrowProjectType.getInvestUserType())) {
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
        if ("2".equals(request.getPlatform()) && "0".equals(borrow.getCanTransactionAndroid())) {
            String tmpInfo = "";
            if ("1".equals(borrow.getCanTransactionPc())) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if ("1".equals(borrow.getCanTransactionIos())) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_IOS);
            }
            if ("1".equals(borrow.getCanTransactionWei())) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        } else if ("3".equals(request.getPlatform()) && "0".equals(borrow.getCanTransactionIos())) {
            if ("1".equals(borrow.getCanTransactionPc())) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if ("1".equals(borrow.getCanTransactionAndroid())) {
                throw new ReturnMessageException(MsgEnum.ERR_TENDER_ALLOWED_ANDROID);
            }
            if ("1".equals(borrow.getCanTransactionWei())) {
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
     * @param couponGrantId
     * @return
     */
    @Override
    public BankCallResult borrowTenderBgReturn(BankCallBean bean, String couponGrantId) {
        logger.info("开始调用投资异步方法");
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
            // 更新失败原因
            String retMsg = bean.getRetMsg();
            BankReturnCodeConfigVO retMsgVo = amConfigClient.getBankReturnCodeConfig(respCode);
            if (retMsgVo != null) {
                retMsg = retMsgVo.getErrorMsg();
            }
            amBorrowClient.updateTenderResult(bean.getLogUserId(),bean.getLogOrderId(),respCode,retMsg,bean.getProductId());
            // 返回码提示余额不足，不结冻
            if (BankCallConstant.RETCODE_BIDAPPLY_YUE_FAIL.equals(respCode)) {
                logger.info("用户:" + userId + "**投资接口调用失败，余额不足，错误码: " + respCode);
                result.setMessage("投资失败，可用余额不足！请联系客服.");
                return result;
            } else {
                result.setMessage(bean.getRetMsg());
                return result;
            }
        }

        bean.convert();
        // 借款Id
        String borrowId = bean.getProductId();

        BorrowVO borrow = borrowClient.selectBorrowByNid(borrowId);
        if (borrow == null) {
            logger.info("PC用户:" + userId + "**回调时,borrowNid为空，错误码: " + respCode);
            result.setMessage("回调时,borrowNid为空.");
            return result;
        }

        // 开始投资逻辑
        this.userBorrowTender(borrow, bean, couponGrantId);
        return result;
    }

    /**
     * 获取投资结果 ---失败
     *
     * @param userVO
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getBorrowTenderResult(WebViewUserVO userVO, String logOrdId, String borrowNid) {
        WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
        String retMsg = amBorrowClient.getBorrowTenderResult(userVO.getUserId(),logOrdId,borrowNid);
        Map<String, Object> map = new HashedMap();
        map.put("error",retMsg);
        result.setData(map);
        return result;
    }

    /**
     * 查询投资成功的结果
     *
     * @param userVO
     * @param logOrdId
     * @param borrowNid
     * @param couponGrantId
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getBorrowTenderResultSuccess(WebViewUserVO userVO, String logOrdId, String borrowNid, Integer couponGrantId) {
        Map<String, Object> data = new HashedMap();
        BorrowVO borrow = amBorrowClient.getBorrowByNid(borrowNid);
        // 查看tmp表
        BorrowTenderRequest borrowTenderRequest = new BorrowTenderRequest();
        borrowTenderRequest.setBorrowNid(borrowNid);
        borrowTenderRequest.setTenderNid(logOrdId);
        borrowTenderRequest.setTenderUserId(userVO.getUserId());
        data.put("borrowNid",borrow.getBorrowNid());
        BorrowTenderVO borrowTender = borrowTenderClient.selectBorrowTender(borrowTenderRequest);
        if(borrowTender!=null){
            // 本金收益  历史回报
            data.put("income",borrowTender.getRecoverAccountWait());
            // 本金
            data.put("account",borrowTender.getAccount());

            // 查询优惠券信息
            CouponUserVO couponUser = couponClient.getCouponUser(couponGrantId, userVO.getUserId());
            // 查询优惠券的投资
            BorrowTenderCpnVO borrowTenderCpn = borrowTenderCpnClient.getCouponTenderByTender(userVO.getUserId(),borrowNid,borrowTender.getNid(),couponGrantId);
            // 优惠券收益
            data.put("couponQuota",borrowTenderCpn.getAccount());
            data.put("couponType",couponUser.getCouponType());
            data.put("couponAll",borrowTenderCpn.getRecoverAccountAll());
            data.put("couponInterest",borrowTenderCpn.getRecoverAccountInterestWait());
        }
        WebResult<Map<String, Object>> result = new WebResult();
        result.setData(data);
        return result;
    }

    /**
     * 获取投资信息
     *
     * @param tender
     * @return
     */
    @Override
    public WebResult<TenderInfoResult> getInvestInfo(TenderRequest tender) {
        TenderInfoResult investInfo = new TenderInfoResult();
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        // 查询项目信息
        String money = tender.getAccount();
        BorrowVO borrow = borrowClient.selectBorrowByNid(tender.getBorrowNid());
        if (null == borrow) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        WebViewUserVO loginUser = RedisUtils.getObj(tender.getToken(), WebViewUserVO.class);
        BestCouponListVO couponConfig = new BestCouponListVO();
        // 未登录，不计算优惠券
        if (loginUser != null) {
            // 获取用户最优优惠券
            MyCouponListRequest request = new MyCouponListRequest();
            request.setBorrowNid(tender.getBorrowNid());
            request.setUserId(String.valueOf(loginUser.getUserId()));
            request.setPlatform(tender.getPlatform());
            couponConfig = couponConfigClient.selectBestCoupon(request);
            if (couponConfig != null) {
                investInfo.setIsThereCoupon(1);
            } else {
                investInfo.setIsThereCoupon(0);
            }
            // 可用优惠券张数
            Integer couponAvailableCount = couponConfigClient.countAvaliableCoupon(request);
            investInfo.setCouponAvailableCount(couponAvailableCount);
            // 优惠券总张数
            /** 获取用户优惠券总张数开始  */
            Integer recordTotal = couponUserClient.getUserCouponCount(loginUser.getUserId(),"0");
            investInfo.setRecordTotal(recordTotal);
            /** 获取用户优惠券总张数结束 */
        } else {
            couponConfig = null;
            // 是否有优惠券
            investInfo.setIsThereCoupon(0);
            // 优惠券总张数
            investInfo.setRecordTotal(0);
            // 优惠券可用张数
            investInfo.setCouponAvailableCount(0);
        }

        // 如果投资金额不为空
        if ((!StringUtils.isBlank(money) && Long.parseLong(money) > 0) || (couponConfig != null && (couponConfig.getCouponType() == 3 || couponConfig.getCouponType() == 1))) {

            String borrowStyle = borrow.getBorrowStyle();
            // 收益率
            BigDecimal borrowApr = borrow.getBorrowApr();
            if (borrow.getProjectType() == 13 && borrow.getBorrowExtraYield() != null && borrow.getBorrowExtraYield().compareTo(BigDecimal.ZERO) > 0) {
                borrowApr = borrowApr.add(borrow.getBorrowExtraYield());
            }
            BigDecimal couponInterest = BigDecimal.ZERO;
            /** 叠加收益率开始*/
            if (couponConfig != null) {
                if (couponConfig.getCouponType() == 1) {
                    couponInterest =couponService.getInterestDj(couponConfig.getCouponQuota(), couponConfig.getCouponProfitTime().intValue(), borrowApr);
                } else {
                    couponInterest = couponService.getInterest(borrowStyle, couponConfig.getCouponType(), borrowApr, couponConfig.getCouponQuota(), money, borrow.getBorrowPeriod());
                }

                couponConfig.setCouponInterest(df.format(couponInterest));
                if (couponConfig.getCouponType() == 2) {
                    borrowApr = borrowApr.add(couponConfig.getCouponQuota());
                }
                if (couponConfig.getCouponType() == 3) {
                    money = new BigDecimal(money).add(couponConfig.getCouponQuota()).toString();
                }
            }
            /** 叠加收益率结束 */
            // 周期
            Integer borrowPeriod = borrow.getBorrowPeriod();
            BigDecimal earnings = new BigDecimal("0");
            switch (borrowStyle) {
                case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：历史回报=投资金额*年化收益÷12*月数；
                    // 计算历史回报
                    earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2,
                            BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：历史回报=投资金额*年化收益÷360*天数；
                    earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：历史回报=投资金额*年化收益÷12*月数；
                    earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2,
                            BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：历史回报=投资金额*年化收益÷12*月数；
                    earnings = AverageCapitalPlusInterestUtils.getInterestCount(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“
                    earnings = AverageCapitalUtils.getInterestCount(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    break;
                default:
                    break;
            }
            investInfo.setEarnings(df.format(earnings));
            if (couponConfig != null && couponConfig.getCouponType() == 3) {
                investInfo.setCapitalInterest(df.format(earnings.add(couponConfig.getCouponQuota()).subtract(couponInterest)));
            } else if (couponConfig != null && couponConfig.getCouponType() == 1) {
                investInfo.setEarnings(df.format(earnings.add(couponInterest)));
                investInfo.setCapitalInterest(df.format(earnings));
            } else {
                investInfo.setCapitalInterest(df.format(earnings.subtract(couponInterest)));
            }
            investInfo.setCouponConfig(couponConfig);
        }
        WebResult<TenderInfoResult> result = new WebResult();
        result.setData(investInfo);
        return result;
    }

    /**
     * 投资异步逻辑
     *
     * @param borrow
     * @param bean
     * @param couponGrantId
     */
    private void userBorrowTender(BorrowVO borrow, BankCallBean bean, String couponGrantId) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 借款金额
        String txAmount = bean.getTxAmount();
        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        // 检查redis是否已经执行了
        boolean checkTender = RedisUtils.tranactionSet(RedisConstants.TENDER_ORDERID + bean.getLogOrderId(), 20);
        //同步/异步 优先执行完毕
        if (!checkTender) {
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_HANDING);
        }
        // redis扣减
        redisTender(userId, borrowNid, txAmount);
        // 操作数据库表
        this.borrowTender(borrow, bean);

        logger.info("用户:" + userId + "***投资成功: " + txAmount);
        logger.info("异步调用优惠劵ID:" + couponGrantId);
        // 如果用了优惠券
        if (StringUtils.isNotEmpty(couponGrantId)) {
            couponService.borrowTenderCouponUse(couponGrantId, borrow, bean);
        }

    }

    /**
     * 操作数据库表
     *
     * @param borrow
     * @param bean
     */
    private void borrowTender(BorrowVO borrow, BankCallBean bean) {
        // 1.删除临时表

        // 2.插入冻结表
        BigDecimal accountDecimal = new BigDecimal(bean.getTxAmount());

        TenderBgVO tenderBg = new TenderBgVO();
        tenderBg.setAccountDecimal(accountDecimal);
        tenderBg.setAccountId(bean.getAccountId());
        tenderBg.setBorrowNid(borrow.getBorrowNid());
        tenderBg.setOrderId(bean.getOrderId());
        tenderBg.setRetCode(bean.getRetCode());
        tenderBg.setUserId(Integer.parseInt(bean.getLogUserId()));
        tenderBg.setIp(bean.getLogIp());
        tenderBg.setTxDate(bean.getTxDate());
        tenderBg.setTxTime(bean.getTxTime());
        tenderBg.setSeqNo(bean.getSeqNo());
        UserInfoVO userInfo = amUserClient.findUsersInfoById(Integer.parseInt(bean.getLogUserId()));
        tenderBg.setTenderUserAttribute(userInfo.getAttribute());
        tenderBg.setClient(bean.getLogClient());
        Integer attribute = null;
        if (userInfo != null) {
            // 获取投资用户的用户属性
            attribute = userInfo.getAttribute();
            if (attribute != null) {
                // 投资人用户属性
                tenderBg.setTenderUserAttribute(attribute);
                // 如果是线上员工或线下员工，推荐人的userId和username不插
                if (attribute == 2 || attribute == 3) {
                    UserInfoCrmVO userInfoCustomize = amUserClient.queryUserCrmInfoByUserId(Integer.parseInt(bean.getLogUserId()));
                    if (userInfoCustomize != null) {
                        tenderBg.setInviteRegionId(userInfoCustomize.getRegionId());
                        tenderBg.setInviteRegionName(userInfoCustomize.getRegionName());
                        tenderBg.setInviteBranchId(userInfoCustomize.getBranchId());
                        tenderBg.setInviteBranchName(userInfoCustomize.getBranchName());
                        tenderBg.setInviteDepartmentId(userInfoCustomize.getDepartmentId());
                        tenderBg.setInviteDepartmentName(userInfoCustomize.getDepartmentName());
                    }
                } else if (attribute == 1) {
                    UserVO spreadsUsers = amUserClient.getSpreadsUsersByUserId(Integer.parseInt(bean.getLogUserId()));
                    if (spreadsUsers != null) {
                        int refUserId = spreadsUsers.getUserId();
                        // 查找用户推荐人
                        tenderBg.setInviteUserId(spreadsUsers.getUserId());
                        tenderBg.setInviteUserName(spreadsUsers.getUsername());
                        // 推荐人信息
                        UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                        // 推荐人用户属性
                        if (refUsers != null) {
                            tenderBg.setInviteUserAttribute(refUsers.getAttribute());
                        }
                        // 查找用户推荐人部门
                        UserInfoCrmVO userInfoCustomize = amUserClient.queryUserCrmInfoByUserId(refUserId);
                        if (userInfoCustomize != null) {
                            tenderBg.setInviteRegionId(userInfoCustomize.getRegionId());
                            tenderBg.setInviteRegionName(userInfoCustomize.getRegionName());
                            tenderBg.setInviteBranchId(userInfoCustomize.getBranchId());
                            tenderBg.setInviteBranchName(userInfoCustomize.getBranchName());
                            tenderBg.setInviteDepartmentId(userInfoCustomize.getDepartmentId());
                            tenderBg.setInviteDepartmentName(userInfoCustomize.getDepartmentName());
                        }
                    }
                } else if (attribute == 0) {
                    UserVO spreadsUsers = amUserClient.getSpreadsUsersByUserId(Integer.parseInt(bean.getLogUserId()));
                    if (spreadsUsers != null) {
                        int refUserId = spreadsUsers.getUserId();
                        // 查找推荐人
                        tenderBg.setInviteUserId(spreadsUsers.getUserId());
                        tenderBg.setInviteUserName(spreadsUsers.getUsername());
                        // 推荐人信息
                        UserInfoVO refUsers = amUserClient.findUsersInfoById(refUserId);
                        // 推荐人用户属性
                        if (refUsers != null) {
                            tenderBg.setInviteUserAttribute(refUsers.getAttribute());
                        }
                    }
                }
            }
        }

        // 单笔投资的融资服务费
        String borrowStyle = borrow.getBorrowStyle();
        BigDecimal perService = new BigDecimal(0);
        BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate()); // 服务费率
        if (StringUtils.isNotEmpty(borrow.getBorrowStyle())) {
            BigDecimal serviceScale = serviceFeeRate;
            // 到期还本还息end/先息后本endmonth/等额本息month/等额本金principal
            if (CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle) || CustomConstants.BORROW_STYLE_END.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle)) {
                perService = FinancingServiceChargeUtils.getMonthsPrincipalServiceCharge(accountDecimal, serviceScale);
            }
            // 按天计息到期还本还息
            else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                perService = FinancingServiceChargeUtils.getDaysPrincipalServiceCharge(accountDecimal, serviceScale, borrow.getBorrowPeriod());
            }
        }
        tenderBg.setPerService(perService);
        //投资授权码 投资结果授权码
        if (StringUtils.isNotBlank(bean.getAuthCode())) {
            tenderBg.setAuthCode(bean.getAuthCode());
        }

        boolean insertFlag = amBorrowClient.borrowTender(tenderBg);
        if (insertFlag) {
            updateUtm(Integer.parseInt(bean.getLogUserId()), tenderBg.getAccountDecimal(), GetDate.getNowTime10(), borrow);
        }
    }

    /**
     * 操作redis扣钱
     *
     * @param userId
     * @param borrowNid
     * @param txAmount
     */
    private void redisTender(Integer userId, String borrowNid, String txAmount) {
        BigDecimal accountDecimal = new BigDecimal(txAmount);// 冻结前验证
        String accountRedisWait = RedisUtils.get(borrowNid);
        if (StringUtils.isNotBlank(accountRedisWait)) {
            // 操作redis
            JedisPool pool = RedisUtils.getPool();
            Jedis jedis = pool.getResource();
            MsgCode redisMsgCode = null;
            try {
                while ("OK".equals(jedis.watch(borrowNid))) {
                    accountRedisWait = RedisUtils.get(borrowNid);
                    if (StringUtils.isNotBlank(accountRedisWait)) {
                        logger.info("用户:" + userId + "***冻结前可投金额：" + accountRedisWait);
                        if (new BigDecimal(accountRedisWait).compareTo(BigDecimal.ZERO) == 0) {
                            // 您来晚了，下次再来抢吧！
                            redisMsgCode = MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE;
                            throw new ReturnMessageException(redisMsgCode);
                        } else {
                            if (new BigDecimal(accountRedisWait).compareTo(accountDecimal) < 0) {
                                // 可投剩余金额为" + accountRedisWait + "元！"
                                throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_MONEY_LESS);
                            } else {
                                Transaction transaction = jedis.multi();
                                BigDecimal lastAccount = new BigDecimal(accountRedisWait).subtract(accountDecimal);
                                transaction.set(borrowNid, lastAccount.toString());
                                List<Object> result = transaction.exec();
                                if (result == null || result.isEmpty()) {
                                    jedis.unwatch();
                                } else {
                                    String ret = (String) result.get(0);
                                    if (ret != null && ret.equals("OK")) {
                                        logger.info("redis操作成功  用户:" + userId + "***冻结前减redis：" + accountDecimal);
                                        break;
                                    } else {
                                        jedis.unwatch();
                                    }
                                }
                            }
                        }
                    } else {
                        // 您来晚了，下次再来抢吧！
                        redisMsgCode = MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE;
                        throw new ReturnMessageException(redisMsgCode);
                    }
                }
            } catch (Exception e) {
                if (redisMsgCode != null) {
                    throw new ReturnMessageException(redisMsgCode);
                } else {
                    throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                }
            } finally {
                RedisUtils.returnResource(pool, jedis);
            }

        } else {
            // 您来晚了，下次再来抢吧！
            throw new ReturnMessageException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
        }
    }

    private void updateUtm(Integer userId, BigDecimal accountDecimal, Integer nowTime, BorrowVO borrow) {
        //更新汇计划列表成功的前提下
        // 更新渠道统计用户累计投资
        // 投资人信息
        UserVO users = amUserClient.findUserById(userId);
        if (users != null) {
            // 更新渠道统计用户累计投资 从mongo里面查询
            AppChannelStatisticsDetailVO appChannelStatisticsDetails = amMongoClient.getAppChannelStatisticsDetailByUserId(users.getUserId());
            if (appChannelStatisticsDetails != null) {
                // TODO: 2018/6/22  发送消息到mq
                // appChannelStatisticsDetails != null && appChannelStatisticsDetails.size() == 1) {
              /*  AppChannelStatisticsDetail channelDetail = appChannelStatisticsDetails.get(0);
                Map<String, Object> params = new HashMap<String, Object>();
                params.put("id", channelDetail.getId());
                // 认购本金
                params.put("accountDecimal", accountDecimal);
                // 投资时间
                params.put("investTime", nowTime);
                // 项目类型
                params.put("projectType", "汇计划");
                // 首次投标项目期限
                String investProjectPeriod = debtPlan.getLockPeriod() + "天";
                params.put("investProjectPeriod", investProjectPeriod);
                // 更新渠道统计用户累计投资
                if (users.getInvestflag() == 1) {
                    this.appChannelStatisticsDetailCustomizeMapper.updateAppChannelStatisticsDetail(params);
                } else if (users.getInvestflag() == 0) {
                    // 更新首投投资
                    this.appChannelStatisticsDetailCustomizeMapper.updateFirstAppChannelStatisticsDetail(params);
                }
                logger.info("用户:"+ userId+ "***********************************预更新渠道统计表AppChannelStatisticsDetail，订单号："+ planAccede.getAccedeOrderId());
           */
            } else {
                // 更新huiyingdai_utm_reg的首投信息
                UtmRegVO utmReg = amUserClient.findUtmRegByUserId(userId);
                if (utmReg != null) {
                    Map<String, Object> params = new HashMap<String, Object>();
                    params.put("id", utmReg.getId());
                    params.put("accountDecimal", accountDecimal);
                    // 投资时间
                    params.put("investTime", nowTime);
                    // 项目类型
                    params.put("projectType", "汇计划");
                    String investProjectPeriod = "";
                    // 首次投标项目期限
                    String borrowStyle = borrow.getBorrowStyle();// 还款方式
                    if ("endday".equals(borrowStyle)) {
                        investProjectPeriod = borrow.getBorrowPeriod() + "天";
                    } else {
                        investProjectPeriod = borrow.getBorrowPeriod() + "月";
                    }
                    // 首次投标项目期限
                    params.put("investProjectPeriod", investProjectPeriod);
                    // 更新渠道统计用户累计投资
                    if (users.getInvestflag() == 0) {
                        // 更新huiyingdai_utm_reg的首投信息
                        boolean updateUtmFlag = amUserClient.updateFirstUtmReg(params);
                    }
                }
            }
        }
        /*(6)更新  渠道统计用户累计投资  和  huiyingdai_utm_reg的首投信息 结束*/
    }
}
