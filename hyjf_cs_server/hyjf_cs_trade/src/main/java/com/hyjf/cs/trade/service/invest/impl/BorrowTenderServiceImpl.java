/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.invest.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.datacollect.AppChannelStatisticsDetailVO;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponConfigVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MsgCode;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.AppResult;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmMongoClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.AppChannelStatisticsDetailProducer;
import com.hyjf.cs.trade.mq.producer.CalculateInvestInterestProducer;
import com.hyjf.cs.trade.mq.producer.CouponTenderProducer;
import com.hyjf.cs.trade.mq.producer.UtmRegProducer;
import com.hyjf.cs.trade.service.consumer.CouponService;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.invest.BorrowTenderService;
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
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

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
    private AmTradeClient amTradeClient;
    @Autowired
    private AmMongoClient amMongoClient;
    @Autowired
    private CouponService couponService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AppChannelStatisticsDetailProducer appChannelStatisticsProducer;
    @Autowired
    private UtmRegProducer utmRegProducer;
    @Autowired
    private CalculateInvestInterestProducer calculateInvestInterestProducer;
    @Autowired
    private CouponTenderProducer couponTenderProducer;
    @Autowired
    private HjhTenderService hjhTenderService;

    /**
     * @param request
     * @Description 散标投资
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    @Override
    public WebResult<Map<String, Object>> borrowTender(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        logger.info("开始检查散标投资参数,userId:{}", userId);
        request.setUser(loginUser);
        request.setUserName(loginUser.getUsername());
        // 设置redis 用户正在投资
        String key = RedisConstants.BORROW_TENDER_REPEAT + userId;
        boolean checkTender = RedisUtils.tranactionSet(key, RedisConstants.TENDER_OUT_TIME);
        if(!checkTender){
            // 用户正在投资
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_IN_PROGRESS);
        }
        if (StringUtils.isEmpty(request.getBorrowNid())) {
            // 项目编号不能为空
            throw new CheckException(MsgEnum.STATUS_CE000013);
        }
        // 查询选择的优惠券
        CouponUserVO cuc = null;
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = amTradeClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 查询散标是否存在
        BorrowVO borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
        BorrowInfoVO borrowInfoVO = amTradeClient.getBorrowInfoByNid(request.getBorrowNid());
        borrow.setTenderAccountMin(borrowInfoVO.getTenderAccountMin());
        borrow.setTenderAccountMax(borrowInfoVO.getTenderAccountMax());
        borrow.setCanTransactionAndroid(borrowInfoVO.getCanTransactionAndroid());
        borrow.setCanTransactionIos(borrowInfoVO.getCanTransactionIos());
        borrow.setCanTransactionPc(borrowInfoVO.getCanTransactionPc());
        borrow.setCanTransactionWei(borrowInfoVO.getCanTransactionWei());
        borrow.setBorrowIncreaseMoney(borrowInfoVO.getBorrowIncreaseMoney());
        if (borrow == null) {
            throw new CheckException(MsgEnum.FIND_BORROW_ERROR);
        }
        logger.info("散标投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        UserVO user = amUserClient.findUserById(request.getUser().getUserId());
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        checkUser(user, userInfo);
        // 检查江西银行账户
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 查询用户账户表-投资账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 投资检查参数
        this.checkParam(request, borrow, account, userInfo);
        // 检查金额
        this.checkTenderMoney(request, borrow, cuc, tenderAccount);
        // 开始真正的投资逻辑
        return tender(request, borrow, account, cuc);
    }

    /**
     * 开始真正的投资  调用江西银行
     * @param request
     * @param borrow
     * @param account
     * @param cuc
     * @return
     */
    private WebResult<Map<String, Object>> tender(TenderRequest request, BorrowVO borrow, BankOpenAccountVO account, CouponUserVO cuc) {
        // 生成订单id
        Integer userId = request.getUser().getUserId();
        String orderId = GetOrderIdUtils.getOrderId2(Integer.valueOf(userId));
        request.setOrderId(orderId);
        BankCallBean callBean = new BankCallBean(userId, BankCallConstant.TXCODE_BID_APPLY, Integer.parseInt(request.getPlatform()), BankCallConstant.BANK_URL_MOBILE_BIDAPPLY);
        // 交易流水号
        callBean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        // 电子账号
        callBean.setAccountId(account.getAccount());
        // 订单号
        callBean.setOrderId(orderId);
        // 交易金额
        callBean.setTxAmount(CustomUtil.formatAmount(request.getAccount()));
        // 标的号
        callBean.setProductId(borrow.getBorrowNid());
        // 是否冻结金额  实时放款投资不冻结
        callBean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_UNFREEZE);
        // 订单号
        callBean.setLogOrderId(orderId);
        // 订单日期
        callBean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 客户IP
        callBean.setLogIp(request.getIp());
        // 投资用户
        callBean.setLogUserId(String.valueOf(userId));
        // 投资用户名
        callBean.setLogUserName(request.getUser().getUsername());
        callBean.setLogClient(Integer.parseInt(request.getPlatform()));
        //错误页
        String retUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/borrow/" + request.getBorrowNid() + "/result/fail?logOrdId="+callBean.getLogOrderId();
        //成功页
        String successUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/borrow/" + request.getBorrowNid() + "/result/success?logOrdId="+callBean.getLogOrderId()+"&couponGrantId="+request.getCouponGrantId();

        // 异步调用路
        String bgRetUrl = "";
        if(cuc != null){
            bgRetUrl = systemConfig.getWebHost() + "tender/borrow/bgReturn?couponGrantId=" + cuc.getId();
        }else{
            bgRetUrl = systemConfig.getWebHost() + "tender/borrow/bgReturn?couponGrantId=" + request.getCouponGrantId();
        }
        //忘记密码url
        String forgetPassWoredUrl = CustomConstants.FORGET_PASSWORD_URL;
        callBean.setRetUrl(retUrl);
        callBean.setSuccessfulUrl(successUrl);
        callBean.setNotifyUrl(bgRetUrl);
        callBean.setForgotPwdUrl(forgetPassWoredUrl);
        // 插入记录 tmp表
        boolean insertResult = amTradeClient.updateBeforeChinaPnR(request);
        logger.info("插入记录表结果：insertResult：{} ",insertResult);
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
     */
    private void checkTenderMoney(TenderRequest request, BorrowVO borrow, CouponUserVO cuc, AccountVO tenderAccount) {
        String account = request.getAccount();
        // 判断用户投资金额是否为空
        if (!(StringUtils.isNotEmpty(account) || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3))) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        // 投资金额是否数值
        if (!DigitalUtils.isNumber(account)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_FORMAT);
        }
        // 投资金额不能为0元
        if (("0".equals(account) && cuc == null)
                || ("0".equals(account) && cuc != null && cuc.getCouponType() == 2)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        // 投资金额是否为整数
        if (!DigitalUtils.isInteger(account)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INT);
        }
        int accountInt = Integer.parseInt(account);
        // 该优惠券只能单独使用
        if (accountInt != 0 && cuc != null && cuc.getCouponType() == 1 && cuc.getAddFlg() == 1) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_COUPON_USE_ALONE);
        }
        // 投资金额不能为负数
        if (accountInt < 0) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NEGATIVE);
        }
        // 将投资金额转化为BigDecimal
        BigDecimal accountBigDecimal = new BigDecimal(account);
        String balance = RedisUtils.get(RedisConstants.BORROW_NID+borrow.getBorrowNid());
        logger.info("标的号{}  项目剩余redis:{}  取值为{}",borrow.getBorrowNid(),balance,RedisConstants.BORROW_NID+borrow.getBorrowNid());
        // 您来晚了，下次再来抢吧
        if (StringUtils.isEmpty(balance)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
        }
        // 起投金额
        Integer min = borrow.getTenderAccountMin();
        // 当剩余可投金额小于最低起投金额，不做最低起投金额的限制
        if (min != null && min != 0 && new BigDecimal(balance).compareTo(new BigDecimal(min)) == -1) {
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_BIG);
            }
            // 剩余可投只剩
            if (accountBigDecimal.compareTo(new BigDecimal(balance)) != 0) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_MONEY_LESS_NEED_BUY_ALL);
            }
        } else {// 项目的剩余金额大于最低起投金额
            if (accountBigDecimal.compareTo(new BigDecimal(min)) == -1) {
                if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                    if (cuc != null && cuc.getCouponType() != 3 && cuc.getCouponType() != 1) {
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT);
                    }
                } else {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT);
                }
            } else {
                Integer max = borrow.getTenderAccountMax();
                if (max != null && max != 0 && accountBigDecimal.compareTo(new BigDecimal(max)) == 1) {
                    // "项目最大投资额为" + max + "元", "1"
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MAX_INVESTMENT);
                }
            }
        }
        // 投资金额不能大于项目总额
        if (accountBigDecimal.compareTo(borrow.getAccount()) > 0) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_GREATER_THAN_TOTAL);
        }
        // 可用金额不足
        if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NOT_ENOUGH);
        }
        // redis剩余金额不足
        // 投资金额不能大于项目剩余
        if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_BIG);
        }
        // add by cwyang 在只使用代金券和体验金,并且没有本金的情况下,不进行投资递增金额的判断,在投资金额等于最大可投金额时也不做递增金额的判断
        if (!(cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1) && accountInt == 0)) {
            // 投资递增金额须为" + borrowDetail.getIncreaseMoney() + " 元的整数倍
            if (borrow.getBorrowIncreaseMoney() != null && (accountInt - min) % borrow.getBorrowIncreaseMoney() != 0
                    && accountBigDecimal.compareTo(new BigDecimal(balance)) == -1 && accountInt < borrow.getTenderAccountMax()) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INCREMENTING);
            }
        }
    }

    /**
     *  检查用户状态  角色  授权状态等  是否允许投资
     * @param user
     * @param userInfo
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        // 担保机构用户
        if (userInfo.getRoleId() == 3) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
        }
        // 借款人不能投资
        if (userInfo.getRoleId() == 2) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
        }
        // 判断用户是否禁用// 0启用，1禁用
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
    }

    /**
     * 散标投资参数校验
     * @param request
     * @param borrow
     * @param account
     * @param userInfo
     */
    private void checkParam(TenderRequest request, BorrowVO borrow, BankOpenAccountVO account, UserInfoVO userInfo) {
        Integer userId = request.getUser().getUserId();
        // 借款人不存在
        if (borrow.getUserId() == null) {
            throw new CheckException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        // 0，51老用户；1，新用户；2，全部用户
        Integer projectType = borrow.getProjectType();
        // 未设置该投资项目的项目类型
        if (projectType == null) {
            throw new CheckException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(projectType));
        // 未查询到该投资项目的设置信息
        if (borrowProjectType == null) {
            throw new CheckException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        // 51老用户标// 1是51，0不是
        if ("0".equals(borrowProjectType.getInvestUserType())) {
            Integer is51 = userInfo.getIs51();
            // 该项目只能51老用户投资
            if (is51 != null && is51 == 1) {
                throw new CheckException(MsgEnum.ERR_TRADE_51_OLD_USER);
            }
        }
        // 新手标
        if ("1".equals(borrowProjectType.getInvestUserType())) {
            boolean isNew = this.checkIsNewUserCanInvest(userId);
            // 该项目只能新手投资
            if (!isNew) {
                throw new CheckException(MsgEnum.ERR_TRADE_NEW_USER);
            }
        }
        // 用户未在平台开户
        if (account == null) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 判断借款人开户信息是否存在
        if (account.getAccount() == null) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 投资客户端 校验
        // 投资平台不能为空
        if(request.getPlatform()==null){
            throw new CheckException(MsgEnum.STATUS_ZC000018);
        }
        if ("2".equals(request.getPlatform()) && "0".equals(borrow.getCanTransactionAndroid())) {
            String tmpInfo = "";
            if ("1".equals(borrow.getCanTransactionPc())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if ("1".equals(borrow.getCanTransactionIos())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_IOS);
            }
            if ("1".equals(borrow.getCanTransactionWei())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        } else if ("3".equals(request.getPlatform()) && "0".equals(borrow.getCanTransactionIos())) {
            if ("1".equals(borrow.getCanTransactionPc())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if ("1".equals(borrow.getCanTransactionAndroid())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_ANDROID);
            }
            if ("1".equals(borrow.getCanTransactionWei())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        }
        // 借款人不可以自己投资项目
        if (userId.equals(String.valueOf(borrow.getUserId()))) {
            throw new CheckException(MsgEnum.ERR_TENDER_YOURSELF);
        }
        // 判断借款是否流标 // 流标
        if (borrow.getStatus() == 6) {
            throw new CheckException(MsgEnum.ERR_TENDER_BIDDERS);
        }
        // 已满标
        if (borrow.getBorrowFullStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_TENDER_FULL_STANDARD);
        }
    }

    /**
     * 获取项目类型
     * @param projectType
     * @return
     */
    private BorrowProjectTypeVO getProjectType(String projectType) {
        BorrowProjectTypeVO borrowProjectType = null;
        List<BorrowProjectTypeVO> projectTypes = amTradeClient.selectBorrowProjectByBorrowCd(projectType);
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
        logger.info("开始调用散标投资异步方法,logOrdId:{},userId:{},优惠券:{},平台为:{} 返回码为：{}",bean.getLogOrderId(),bean.getLogUserId(),couponGrantId,bean.getLogClient(),bean.getRetCode());
        // 用户Userid
        if(couponGrantId==null||couponGrantId.equals("null") ||couponGrantId.equals("")){
            couponGrantId = "0";
        }
        int userId = StringUtils.isBlank(bean.getLogUserId()) ? 0 : Integer.parseInt(bean.getLogUserId());
        // 投资结果返回码
        String respCode = bean.getRetCode();
        Integer platForm = bean.getLogClient();
        BankCallResult result = new BankCallResult();
        // true 的话PAY工程就不调用了  false的话PAY还会调用
        result.setStatus(true);
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
            amTradeClient.updateTenderResult(bean.getLogUserId(),bean.getLogOrderId(),respCode,retMsg,bean.getProductId());
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
        BorrowVO borrow = amTradeClient.selectBorrowByNid(borrowId);
        if (borrow == null) {
            logger.info("用户:" + userId + "**回调时,borrowNid为空，错误码: " + respCode);
            result.setStatus(false);
            result.setMessage("回调时,borrowNid为空.");
            return result;
        }
        // 开始投资逻辑开始调用散标投资异步方法
        this.userBorrowTender(borrow, bean, couponGrantId);
        result.setStatus(true);
        return result;
    }

    /**
     * 获取投资结果 ---失败
     *
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getBorrowTenderResult(Integer userId, String logOrdId, String borrowNid) {
        WebResult<Map<String, Object>> result = new WebResult<Map<String, Object>>();
        String retMsg = amTradeClient.getBorrowTenderResult(userId,logOrdId,borrowNid);
        Map<String, Object> map = new HashedMap();
        map.put("error",retMsg);
        result.setData(map);
        return result;
    }

    /**
     * 查询投资成功的结果
     *
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @param couponGrantId
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getBorrowTenderResultSuccess(Integer userId, String logOrdId, String borrowNid, Integer couponGrantId) {
        Map<String, Object> data = new HashedMap();
        BorrowVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        // 查看tmp表
        BorrowTenderRequest borrowTenderRequest = new BorrowTenderRequest();
        borrowTenderRequest.setBorrowNid(borrowNid);
        borrowTenderRequest.setTenderNid(logOrdId);
        borrowTenderRequest.setTenderUserId(userId);
        data.put("borrowNid",borrow.getBorrowNid());
        data.put("investDesc","恭喜您，投资成功！");
        BorrowTenderVO borrowTender = amTradeClient.selectBorrowTender(borrowTenderRequest);
        logger.info("获取投资成功结果为:"+borrowTender);
        if(borrowTender!=null){
            // 本金收益  历史回报
            data.put("income",borrowTender.getRecoverAccountWait());
            // 本金
            data.put("account",borrowTender.getAccount());

            // 查询优惠券信息
            CouponUserVO couponUser = amTradeClient.getCouponUser(couponGrantId, userId);
            if(couponUser!=null){
                // 查询优惠券的投资
                BorrowTenderCpnVO borrowTenderCpn = amTradeClient.getCouponTenderByTender(userId,borrowNid,borrowTender.getNid(),couponGrantId);
                // 优惠券收益
                if(borrowTenderCpn!=null){
                    data.put("couponQuota",borrowTenderCpn.getAccount());
                    data.put("couponType",couponUser.getCouponType());
                    data.put("couponAll",borrowTenderCpn.getRecoverAccountAll());
                    data.put("couponInterest",borrowTenderCpn.getRecoverAccountInterestWait());
                }else{
                    data.put("couponQuota","");
                    data.put("couponType","");
                    data.put("couponAll","");
                    data.put("couponInterest","");
                }
            }else{
                data.put("couponQuota","");
                data.put("couponType","");
                data.put("couponAll","");
                data.put("couponInterest","");
            }
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
        BorrowVO borrow = amTradeClient.selectBorrowByNid(tender.getBorrowNid());
        if (null == borrow) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(tender.getBorrowNid());
        if (null == borrowInfo) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        UserVO loginUser = amUserClient.findUserById(Integer.valueOf(tender.getUserId()));

        BestCouponListVO couponConfig = new BestCouponListVO();
        // 未登录，不计算优惠券
        if (loginUser != null) {
            // 获取用户最优优惠券
            MyCouponListRequest request = new MyCouponListRequest();
            request.setBorrowNid(tender.getBorrowNid());
            request.setUserId(String.valueOf(loginUser.getUserId()));
            request.setPlatform(tender.getPlatform());
            couponConfig = amTradeClient.selectBestCoupon(request);
            if (couponConfig != null) {
                investInfo.setIsThereCoupon(1);
            } else {
                investInfo.setIsThereCoupon(0);
            }
            // 可用优惠券张数
            Integer couponAvailableCount = amTradeClient.countAvaliableCoupon(request);
            investInfo.setCouponAvailableCount(couponAvailableCount);
            // 优惠券总张数
            /** 获取用户优惠券总张数开始  */
            Integer recordTotal = amTradeClient.getUserCouponCount(loginUser.getUserId(),"0");
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
     * 获取APP端投资信息
     *
     * @param tender
     * @return
     */
    @Override
    public AppInvestInfoResultVO getInvestInfoApp(TenderRequest tender) {

        AppInvestInfoResultVO investInfo = new AppInvestInfoResultVO();
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal couponInterest = BigDecimal.ZERO;
        BigDecimal borrowInterest = new BigDecimal(0);

        // 投资类型
        String investType = tender.getBorrowNid().substring(0, 3);

        // 转让的
        if ("HZR".equals(investType) && StringUtils.isNotEmpty(tender.getCreditNid())) {
            return null;
        }
        // 计划的
        if ("HJH".equals(investType)) {
            return hjhTenderService.getInvestInfoApp(tender);
        }

        if ((!("HZR".equals(investType))) && (!("HJH".equals(investType)))) {
            // 查询项目信息
            String money = tender.getAccount();
            // 优惠券总张数
            Integer recordTotal = 0;
            // 可用优惠券张数
            Integer couponAvailableCount;

            BorrowVO borrow = amTradeClient.selectBorrowByNid(tender.getBorrowNid());
            if (null == borrow) {
                // 标的不存在
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
            }
            BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(tender.getBorrowNid());
            if (null == borrowInfo) {
                // 标的不存在
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
            }
            WebViewUserVO loginUser = RedisUtils.getObj(RedisConstants.USERID_KEY + tender.getUserId(), WebViewUserVO.class);
            // 可用优惠券张数
            MyCouponListRequest request = new MyCouponListRequest();
            request.setBorrowNid(tender.getBorrowNid());
            request.setUserId(String.valueOf(loginUser.getUserId()));
            request.setPlatform(tender.getPlatform());
            couponAvailableCount = amTradeClient.countAvaliableCoupon(request);
            investInfo.setCouponAvailableCount(couponAvailableCount + "");
            // 优惠券总张数
            recordTotal = amTradeClient.getUserCouponCount(loginUser.getUserId(), "0");
            investInfo.setBorrowApr(borrow.getBorrowApr() + "%");
            investInfo.setPaymentOfInterest("");
            // 是否使用优惠券
            investInfo.setIsUsedCoupon("0");
            // 检查优惠券可不可用
            CouponUserVO couponConfig = null;
            Integer userId = loginUser.getUserId();
            if (tender.getCouponGrantId() != null && tender.getCouponGrantId() > 0) {
                couponConfig = amTradeClient.getCouponUser(tender.getCouponGrantId(), userId);
            }

            // 设置收益率
            if (couponConfig != null) {
                if (couponConfig.getCouponType() == 1) {
                    investInfo.setCouponDescribe("体验金: " + couponConfig.getCouponQuota() + "元");
                    investInfo.setCouponType("体验金");
                }
                if (couponConfig.getCouponType() == 2) {
                    investInfo.setCouponDescribe("加息券:  " + couponConfig.getCouponQuota() + "%");
                    investInfo.setCouponType("加息券");
                }
                if (couponConfig.getCouponType() == 3) {
                    investInfo.setCouponDescribe("代金券: " + couponConfig.getCouponQuota() + "元");
                    investInfo.setCouponType("代金券");
                }
                investInfo.setCouponQuota(couponConfig.getCouponQuota().toString());
                investInfo.setCouponId(couponConfig.getId() + "");
                investInfo.setIsUsedCoupon("1");
            } else {
                investInfo.setCouponDescribe("暂无可用");
                investInfo.setCouponName("");
                investInfo.setCouponQuota("");
                investInfo.setCouponId("-1");

                if (recordTotal > 0) {
                    investInfo.setIsThereCoupon("1");
                    investInfo.setCouponDescribe("请选择");
                } else if (recordTotal == 0) {
                    investInfo.setIsThereCoupon("1");
                    investInfo.setCouponDescribe("暂无可用");
                } else {
                    investInfo.setIsThereCoupon("0");
                    investInfo.setCouponDescribe("无可用");
                }

                BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(borrow.getProjectType()));
                if ("HZT".equals(borrowProjectType.getBorrowProjectType())) {
                    if ("ZXH".equals(borrowProjectType.getBorrowClass()) || "NEW".equals(borrowProjectType.getBorrowClass())) {
                        investInfo.setConfirmCouponDescribe("不支持使用优惠券");
                        investInfo.setCapitalInterest("");
                    } else {
                        investInfo.setConfirmCouponDescribe("未使用优惠券");
                        investInfo.setCapitalInterest("");
                    }
                } else {
                    investInfo.setConfirmCouponDescribe("不支持使用优惠券");
                    investInfo.setCapitalInterest("");
                }
            }
            investInfo.setBorrowNid(borrow.getBorrowNid());

            BigDecimal borrowAccountWait = borrow.getBorrowAccountWait();
            // 去最小值 最大可投和 项目可投
            if (borrow.getTenderAccountMax() != null && borrowAccountWait != null && (borrow.getProjectType() == 4 || borrow.getProjectType() == 11)) {
                BigDecimal TenderAccountMax = new BigDecimal(borrow.getTenderAccountMax());
                if (TenderAccountMax.compareTo(borrowAccountWait) == -1) {
                    investInfo.setBorrowAccountWait(CommonUtils.formatAmount(null, TenderAccountMax));
                } else {
                    investInfo.setBorrowAccountWait(CommonUtils.formatAmount(null, borrowAccountWait));
                }
            } else {
                investInfo.setBorrowAccountWait(CommonUtils.formatAmount(null, borrowAccountWait));
            }

            String balanceWait = borrow.getBorrowAccountWait() + "";
            if (balanceWait == null || balanceWait.equals("")) {
                balanceWait = "0";
            }
            if (money == null) {
                money = "0";
            }
            // 剩余可投小于起投，计算收益按照剩余可投计算
            if ((StringUtils.isBlank(money) || money.equals("0")) && new BigDecimal(balanceWait).compareTo(new BigDecimal(borrowInfo.getTenderAccountMin())) < 0) {
                money = new BigDecimal(balanceWait).intValue() + "";
            }

            BigDecimal earnings = new BigDecimal("0");

            if (!StringUtils.isBlank(money) && Double.parseDouble(money) >= 0) {
                String borrowStyle = borrow.getBorrowStyle();
                // 收益率
                BigDecimal borrowApr = borrow.getBorrowApr();
                // 周期
                Integer borrowPeriod = borrow.getBorrowPeriod();
                // 计算本金投资历史回报
                switch (borrowStyle) {
                    // 还款方式为”按月计息，到期还本还息“: 历史回报=投资金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_END:
                        earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”按天计息，到期还本还息“: 历史回报=投资金额*年化收益÷360*天数；
                    case CalculatesUtil.STYLE_ENDDAY:
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”先息后本“: 历史回报=投资金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_ENDMONTH:
                        earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”等额本息“: 历史回报=投资金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_MONTH:
                        earnings = AverageCapitalPlusInterestUtils.getInterestCount(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”等额本金“: 历史回报=投资金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_PRINCIPAL:
                        earnings = AverageCapitalUtils.getInterestCount(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    default:
                        investInfo.setInterest("");
                        break;
                }
                logger.info("散标本金历史回报:  {}", earnings);
                borrowInterest = earnings;
                //计算优惠券历史回报
                if (couponConfig != null && couponConfig.getId() > 0) {
                    couponInterest = calculateCouponTenderInterest(couponConfig, money, borrow);
                }

            }
            investInfo.setCapitalInterest("历史回报: 0元");
            investInfo.setConfirmCouponDescribe("加息券:  无");
            investInfo.setRealAmount("");
            investInfo.setCouponType("");

            investInfo.setDesc("历史年回报率: " + borrow.getBorrowApr() + "%      历史回报: " + CommonUtils.formatAmount(borrowInterest.add(couponInterest)) + "元");
            investInfo.setDesc0("历史年回报率: " + borrow.getBorrowApr() + "%");
            investInfo.setConfirmRealAmount("投资金额: " + CommonUtils.formatAmount(money) + "元");
            investInfo.setRealAmount("投资金额: " + CommonUtils.formatAmount(money) + "元");
            investInfo.setBorrowInterest(CommonUtils.formatAmount(borrowInterest) + "元");
            // 安卓的历史回报使用这个字段
            investInfo.setProspectiveEarnings(CommonUtils.formatAmount(borrowInterest.add(couponInterest)));
            investInfo.setStatus(CustomConstants.APP_STATUS_SUCCESS);
            investInfo.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);

            AccountVO account = amTradeClient.getAccount(userId);
            BigDecimal balance = account.getBankBalance();
            investInfo.setBalance(CommonUtils.formatAmount(null, balance));
            investInfo.setInitMoney(borrowInfo.getTenderAccountMin() + "");
            investInfo.setIncreaseMoney(String.valueOf(borrow.getBorrowIncreaseMoney()));
            investInfo.setInvestmentDescription(borrowInfo.getTenderAccountMin() + "元起投," + borrowInfo.getBorrowIncreaseMoney() + "元递增");
            // 可用余额的递增部分
            BigDecimal tmpmoney = balance.subtract(new BigDecimal(borrowInfo.getTenderAccountMin())).divide(new BigDecimal(borrowInfo.getBorrowIncreaseMoney()), 0, BigDecimal.ROUND_DOWN)
                    .multiply(new BigDecimal(borrowInfo.getBorrowIncreaseMoney())).add(new BigDecimal(borrowInfo.getTenderAccountMin()));
            if (balance.subtract(new BigDecimal(borrowInfo.getTenderAccountMin())).compareTo(new BigDecimal("0")) < 0) {
                // 可用余额<起投金额 时 investAllMoney 传 -1
                // 全投金额
                investInfo.setInvestAllMoney("-1");
            } else {
                String borrowAccountWaitStr = investInfo.getBorrowAccountWait().replace(",", "");
                logger.info("borrow.getTenderAccountMax()=[{}],borrowAccountWaitStr=[{}]", borrowInfo.getTenderAccountMax(), borrowAccountWaitStr);
                if (new BigDecimal(borrowInfo.getTenderAccountMax()).compareTo(new BigDecimal(borrowAccountWaitStr)) < 0) {
                    investInfo.setInvestAllMoney(borrowInfo.getTenderAccountMax() + "");
                } else if (tmpmoney.compareTo(new BigDecimal(borrowAccountWaitStr)) < 0) {
                    // 全投金额
                    investInfo.setInvestAllMoney(tmpmoney + "");
                } else {
                    // 全投金额
                    investInfo.setInvestAllMoney(investInfo.getBorrowAccountWait() + "");
                }

                //计算全投金额 modify by cwyang 2017-8-17
                String result = getMinAmount(borrowInfo.getTenderAccountMax(), tmpmoney, borrowAccountWaitStr);
                if (result != null) {
                    investInfo.setInvestAllMoney(result);
                }

            }
            investInfo.setBorrowAccountWait(CommonUtils.formatAmount(borrow.getBorrowAccountWait()) + "");
            investInfo.setAnnotation("");

            // 设置无用的东西 不给app返回null

            investInfo.setEndTime("");
            investInfo.setDesc1("");
            investInfo.setButtonWord("");
            investInfo.setStandardValues("");

            // 投资协议
            this.setProtocolsToResultVO(investInfo, investType);

            // 前端要求改成bean，不要封装
/*        AppResult<AppInvestInfoResultVO> result = new AppResult();
        result.setData(investInfo);*/
            return investInfo;
        }
        return null;
    }
    /**
     * 投资协议列表
     * @param investInfo
     * @param investType
     */
    private void setProtocolsToResultVO(AppInvestInfoResultVO investInfo, String investType){
        List<NewAgreementBean> list=new ArrayList<NewAgreementBean>();
        NewAgreementBean newAgreementBean=new NewAgreementBean("投资协议",  systemConfig.webHost+"agreement/AgreementViewList?borrowType="+investType);
        list.add(newAgreementBean);
        investInfo.setProtocols(list);
        investInfo.setProtocolUrlDesc("协议列表");
    }

    /**
     * app端获取投资url
     *
     * @param tender
     * @return
     */
    @Override
    public String getAppTenderUrl(TenderRequest tender) {
        tender.setPlatform((tender.getPlatform() == null || "".equals(tender.getPlatform()))?"2":tender.getPlatform());
        String url = super.getFrontHost(systemConfig,tender.getPlatform()) +"/public/formsubmit?requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_TENDER;
        //String url = super.getFrontHost(systemConfig,tender.getPlatform()) +"/hyjf-app/user/invest/tender?requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_TENDER;
        url += "&couponGrantId="+tender.getCouponGrantId()+"&borrowNid="+tender.getBorrowNid()+"&platform="+tender.getPlatform()+"&account="+tender.getAccount();
        logger.info("url:[{}]",url);
        //ModelAndView mv = new ModelAndView("redirect:"+url);
        return url;
    }

    /**
     * 计算全投金额
     * @param tenderAccountMax
     * @param tmpmoney
     * @param borrowAccountWait
     * @return
     */
    private String getMinAmount(Integer tenderAccountMax, BigDecimal tmpmoney, String borrowAccountWait) {
        if (tenderAccountMax == null || tmpmoney == null || StringUtils.isBlank(borrowAccountWait)) {
            return null;
        }else{
            ArrayList<BigDecimal> amountList =  new ArrayList<BigDecimal>();
            amountList.add(new BigDecimal(tenderAccountMax));
            amountList.add(tmpmoney);
            amountList.add(new BigDecimal(borrowAccountWait));
            for (int i = 0; i < amountList.size(); i++) {
                for (int j = i+1; j < amountList.size(); j++) {
                    if (amountList.get(i).compareTo(amountList.get(j)) > 0) {
                        BigDecimal temp = null;
                        temp = amountList.get(i);
                        amountList.set(i, amountList.get(j));
                        amountList.set(j, temp);
                    }
                }
            }
            return amountList.get(0).toString();
        }
    }

    /**
     * 计算优惠券历史回报
     * @param couponConfig
     * @param couponConfig
     * @param couponConfig
     * @param money  投资本金
     * @param borrow
     * @return
     */
    private BigDecimal calculateCouponTenderInterest(CouponUserVO couponConfig, String money, BorrowVO borrow) {
        //计算优惠券历史回报
        BigDecimal couponInterest = BigDecimal.ZERO;
        BigDecimal borrowApr = borrow.getBorrowApr();
        String borrowStyle = borrow.getBorrowStyle();

        if (couponConfig != null) {
            Integer couponType = couponConfig.getCouponType();
            BigDecimal couponQuota = couponConfig.getCouponQuota();
            Integer couponProfitTime = couponConfig.getCouponProfitTime();

            if (couponType == 1) {
                couponInterest = getInterestDj(couponQuota, couponProfitTime, borrowApr);
            } else {
                couponInterest = getInterest(borrowStyle, couponType, borrowApr, couponQuota, money, borrow.getBorrowPeriod());
            }
        }
        logger.info("优惠券的收益: {} ", couponInterest);
        return couponInterest;
    }

    /**
     * 体验金收益计算
     * @param couponQuota
     * @param couponProfitTime
     * @param borrowApr
     * @return
     */
    private BigDecimal getInterestDj(BigDecimal couponQuota, Integer couponProfitTime, BigDecimal borrowApr) {
        BigDecimal earnings = new BigDecimal("0");
        earnings = couponQuota.multiply(borrowApr.divide(new BigDecimal(100), 6, BigDecimal.ROUND_HALF_UP)).divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP)
                .multiply(new BigDecimal(couponProfitTime)).setScale(2, BigDecimal.ROUND_DOWN);
        return earnings;
    }

    /**
     * 优惠券收益计算
     * @param borrowStyle
     * @param couponType
     * @param borrowApr
     * @param couponQuota
     * @param money
     * @param borrowPeriod
     * @return
     */

    private BigDecimal getInterest(String borrowStyle, Integer couponType, BigDecimal borrowApr, BigDecimal couponQuota, String money, Integer borrowPeriod) {
        BigDecimal earnings = new BigDecimal("0");

        // 投资金额
        BigDecimal accountDecimal = null;
        if (couponType == 1) {
            // 体验金 投资资金=体验金面值
            accountDecimal = couponQuota;
        } else if (couponType == 2) {
            // 加息券 投资资金=真实投资资金
            accountDecimal = new BigDecimal(money);
            borrowApr = couponQuota;
        } else if (couponType == 3) {
            // 代金券 投资资金=体验金面值
            accountDecimal = couponQuota;
        }
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“: 历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“: 历史回报=投资金额*年化收益÷360*天数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getDayInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“: 历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“: 历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = AverageCapitalPlusInterestUtils.getInterestCount(accountDecimal,borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2,BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_PRINCIPAL: //还款方式为“等额本金” 历史回报=投资金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = AverageCapitalUtils.getInterestCount(accountDecimal,borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            default:
                break;
        }
        if (couponType == 3) {
            earnings = earnings.add(couponQuota);
        }
        return earnings;
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
        boolean checkTender = RedisUtils.tranactionSet(RedisConstants.TENDER_ORDERID + bean.getLogOrderId(), 20);
        //同步/异步 优先执行完毕
        if (!checkTender) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_HANDING);
        }
        // redis扣减
        redisTender(userId, borrowNid, txAmount);
        // 操作数据库表
        this.borrowTender(borrow, bean);
        logger.info("用户:{},投资成功，金额：{}，优惠券开始调用ID：{}" ,userId, txAmount,couponGrantId);
        // 如果用了优惠券
        if (StringUtils.isNotEmpty(couponGrantId)) {
            // 开始使用优惠券
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            // 真实投资金额
            params.put("money", bean.getTxAmount());
            // 借款项目编号
            params.put("borrowNid", borrowNid);
            // 平台
            params.put("platform", bean.getLogClient()+"");
            // 优惠券id
            params.put("couponGrantId", couponGrantId);
            // ip
            params.put("ip", bean.getLogIp());
            // 真实投资订单号
            params.put("ordId", bean.getLogOrderId());
            // 用户编号
            params.put("userId", userId+"");
            try {
                couponTenderProducer.messageSend(new MessageContent(MQConstant.HZT_COUPON_TENDER_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
            } catch (MQException e) {
                logger.error("使用优惠券异常,userId:{},ordId:{},couponGrantId:{},borrowNid:{}",userId,bean.getLogOrderId(),couponGrantId,borrowNid);
                e.printStackTrace();
            }
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
        // 服务费率
        BigDecimal serviceFeeRate = Validator.isNull(borrow.getServiceFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getServiceFeeRate());
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

        boolean insertFlag = amTradeClient.borrowTender(tenderBg);
        if (insertFlag) {
            updateUtm(Integer.parseInt(bean.getLogUserId()), tenderBg.getAccountDecimal(), GetDate.getNowTime10(), borrow);
            // 网站累计投资追加
            // 投资、收益统计表
            JSONObject params = new JSONObject();
            params.put("tenderSum", accountDecimal);
            params.put("nowTime", GetDate.getDate(GetDate.getNowTime10()));
            // 投资修改mongodb运营数据
            params.put("type", 1);
            params.put("money", accountDecimal);
            try {
                // 网站累计投资追加
                // 投资修改mongodb运营数据
                calculateInvestInterestProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                // 满标发短信在原子层
            } catch (MQException e) {
                e.printStackTrace();
            }
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
        // 冻结前验证
        BigDecimal accountDecimal = new BigDecimal(txAmount);
        String accountRedisWait = RedisUtils.get(RedisConstants.BORROW_NID+borrowNid);
        if (StringUtils.isNotBlank(accountRedisWait)) {
            // 操作redis
            JedisPool pool = RedisUtils.getPool();
            Jedis jedis = pool.getResource();
            MsgCode redisMsgCode = null;
            try {
                while ("OK".equals(jedis.watch(borrowNid))) {
                    accountRedisWait = RedisUtils.get(RedisConstants.BORROW_NID+borrowNid);
                    if (StringUtils.isNotBlank(accountRedisWait)) {
                        logger.info("用户:" + userId + "***冻结前可投金额：" + accountRedisWait);
                        if (new BigDecimal(accountRedisWait).compareTo(BigDecimal.ZERO) == 0) {
                            // 您来晚了，下次再来抢吧！
                            redisMsgCode = MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE;
                            throw new CheckException(redisMsgCode);
                        } else {
                            if (new BigDecimal(accountRedisWait).compareTo(accountDecimal) < 0) {
                                // 可投剩余金额为" + accountRedisWait + "元！"
                                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_LESS);
                            } else {
                                Transaction transaction = jedis.multi();
                                BigDecimal lastAccount = new BigDecimal(accountRedisWait).subtract(accountDecimal);
                                transaction.set(borrowNid, lastAccount.toString());
                                List<Object> result = transaction.exec();
                                if (result == null || result.isEmpty()) {
                                    jedis.unwatch();
                                } else {
                                    String ret = (String) result.get(0);
                                    if (ret != null && "OK".equals(ret)) {
                                        logger.info("redis操作成功  用户:{},***冻结前减redis：{}" ,userId, accountDecimal);
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
                        throw new CheckException(redisMsgCode);
                    }
                }
            } catch (Exception e) {
                if (redisMsgCode != null) {
                    throw new CheckException(redisMsgCode);
                } else {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                }
            } finally {
                RedisUtils.returnResource(pool, jedis);
            }

        } else {
            // 您来晚了，下次再来抢吧！
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
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
                Map<String, Object> params = new HashMap<String, Object>();
                // 认购本金
                params.put("accountDecimal", accountDecimal);
                // 投资时间
                params.put("investTime", nowTime);
                // 项目类型
                if (borrow.getProjectType() == 13) {
                    params.put("projectType", "汇金理财");
                } else {
                    params.put("projectType", "汇直投");
                }
                // 首次投标项目期限
                String investProjectPeriod = "";
                String borrowStyle = borrow.getBorrowStyle();
                if ("endday".equals(borrowStyle)) {
                    investProjectPeriod = borrow.getBorrowPeriod() + "天";
                } else {
                    investProjectPeriod = borrow.getBorrowPeriod() + "月";
                }
                params.put("investProjectPeriod", investProjectPeriod);
                //根据investFlag标志位来决定更新哪种投资
                params.put("investFlag", checkIsNewUserCanInvest(userId));
                //压入消息队列
                try {
                    appChannelStatisticsProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                            MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                } catch (MQException e) {
                    e.printStackTrace();
                    logger.error("渠道统计用户累计投资推送消息队列失败！！！");
                }
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
                    // 首次投标项目期限// 还款方式
                    String borrowStyle = borrow.getBorrowStyle();
                    if ("endday".equals(borrowStyle)) {
                        investProjectPeriod = borrow.getBorrowPeriod() + "天";
                    } else {
                        investProjectPeriod = borrow.getBorrowPeriod() + "月";
                    }
                    // 首次投标项目期限
                    params.put("investProjectPeriod", investProjectPeriod);
                    // 更新渠道统计用户累计投资
                    try {
                        if(this.checkIsNewUserCanInvest(userId)){
                            utmRegProducer.messageSend(new MessageContent(MQConstant.STATISTICS_UTM_REG_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
                        }
                    } catch (MQException e) {
                        e.printStackTrace();
                        logger.error("更新huiyingdai_utm_reg的首投信息失败");
                    }
                }
            }
        }
        /*(6)更新  渠道统计用户累计投资  和  huiyingdai_utm_reg的首投信息 结束*/
    }
}
