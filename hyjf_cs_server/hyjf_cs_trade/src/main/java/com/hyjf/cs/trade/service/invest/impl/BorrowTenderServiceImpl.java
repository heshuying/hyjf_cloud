/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.invest.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BorrowTenderRequest;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
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
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.TenderInfoResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.bean.newagreement.NewAgreementBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.mq.producer.AppChannelStatisticsDetailProducer;
import com.hyjf.cs.trade.mq.producer.CalculateInvestInterestProducer;
import com.hyjf.cs.trade.mq.producer.CouponTenderProducer;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.consumer.CouponService;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.invest.BorrowCreditTenderService;
import com.hyjf.cs.trade.service.invest.BorrowTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
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

import javax.servlet.http.HttpServletRequest;
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
    private CouponService couponService;
    @Autowired
    private SystemConfig systemConfig;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AppChannelStatisticsDetailProducer appChannelStatisticsProducer;
    @Autowired
    private CalculateInvestInterestProducer calculateInvestInterestProducer;
    @Autowired
    private CouponTenderProducer couponTenderProducer;
    @Autowired
    private HjhTenderService hjhTenderService;
    @Autowired
    private BorrowCreditTenderService borrowTenderService;
    @Autowired
    private AuthService authService;
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
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
        BorrowInfoVO borrowInfoVO = amTradeClient.getBorrowInfoByNid(request.getBorrowNid());
        if (borrow == null) {
            throw new CheckException(MsgEnum.FIND_BORROW_ERROR);
        }
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        logger.info("散标投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        borrowTenderCheck(request,borrow,borrowInfoVO,cuc,account);
        logger.info("所有参数都已检查通过!");
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
    private WebResult<Map<String, Object>> tender(TenderRequest request, BorrowAndInfoVO borrow, BankOpenAccountVO account, CouponUserVO cuc) {
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
        String retUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/borrow/" + request.getBorrowNid() + "/result/failed?logOrdId="+callBean.getLogOrderId() + "&borrowNid=" + request.getBorrowNid();
        //成功页
        String successUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/borrow/" + request.getBorrowNid() + "/result/success?logOrdId=" +callBean.getLogOrderId() + "&borrowNid=" + request.getBorrowNid()
                +"&couponGrantId="+(request.getCouponGrantId()==null?0:request.getCouponGrantId());
        if(request.getToken() != null && !"".equals(request.getToken())){
            retUrl += "&token=1";
            successUrl += "&token=1";
        }
        if(request.getSign() != null && !"".equals(request.getSign())){
            retUrl += "&sign=" + request.getSign();
            successUrl += "&sign=" + request.getSign();
        }
        // 异步调用路
        String bgRetUrl = "";
        if(cuc != null){
            bgRetUrl = "http://CS-TRADE/hyjf-web/tender/borrow/bgReturn?platform="+request.getPlatform()+"&couponGrantId=" + cuc.getId();
        }else{
            bgRetUrl = "http://CS-TRADE/hyjf-web/tender/borrow/bgReturn?platform="+request.getPlatform()+"&couponGrantId=" + (request.getCouponGrantId()==null?"0":request.getCouponGrantId());
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
    private void checkTenderMoney(TenderRequest request, BorrowAndInfoVO borrow, CouponUserVO cuc, AccountVO tenderAccount) {
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
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_MONEY_LESS_NEED_BUY_ALL,balance);
            }
        } else {// 项目的剩余金额大于最低起投金额
            if (min != null && min != 0 && accountBigDecimal.compareTo(new BigDecimal(min)) == -1) {
                if (accountBigDecimal.compareTo(BigDecimal.ZERO) == 0) {
                    if (cuc != null && cuc.getCouponType() != 3 && cuc.getCouponType() != 1) {
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT,min);
                    }
                } else {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT,min);
                }
            } else {
                Integer max = borrow.getTenderAccountMax();
                if (max != null && max != 0 && accountBigDecimal.compareTo(new BigDecimal(max)) == 1) {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MAX_INVESTMENT,max);
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
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INCREMENTING,borrow.getBorrowIncreaseMoney());
            }
        }
    }

    /**
     *  检查用户状态  角色  授权状态等  是否允许投资
     * @param user
     * @param userInfo
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        if (null != userInfo) {
            // 合规校验角色
            String roleIsOpen = systemConfig.getRoleIsopen();
            if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
                if (userInfo.getRoleId() != 1) {
                    // 仅限出借人进行投资
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
                }
            }
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
        // 缴费授权状态
        if (!authService.checkPaymentAuthStatus(user.getUserId())) {
            // 未进行服务费授权
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_PAYMENT_AUTH);
        }
    }

    /**
     * 散标投资参数校验
     * @param request
     * @param borrow
     * @param account
     * @param userInfo
     */
    private void checkParam(TenderRequest request, BorrowAndInfoVO borrow, BankOpenAccountVO account, UserInfoVO userInfo ,BorrowInfoVO borrowInfoVO) {
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
        if (borrowProjectType.getInvestUserType().equals(0)) {
            Integer is51 = userInfo.getIs51();
            // 该项目只能51老用户投资
            if (is51 != null && is51 == 1) {
                throw new CheckException(MsgEnum.ERR_TRADE_51_OLD_USER);
            }
        }
        // 新手标
        logger.info("是否新手标：：："+borrowProjectType.getInvestUserType());
        logger.info("是否新手标：：："+borrowProjectType.getInvestUserType().equals(1));
        if (borrowProjectType.getInvestUserType().equals(1)) {
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
        if ("2".equals(request.getPlatform()) && "0".equals(borrowInfoVO.getCanTransactionAndroid())) {
            String tmpInfo = "";
            if ("1".equals(borrowInfoVO.getCanTransactionPc())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if ("1".equals(borrowInfoVO.getCanTransactionIos())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_IOS);
            }
            if ("1".equals(borrowInfoVO.getCanTransactionWei())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        } else if ("3".equals(request.getPlatform()) && "0".equals(borrowInfoVO.getCanTransactionIos())) {
            if ("1".equals(borrowInfoVO.getCanTransactionPc())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PC);
            }
            if ("1".equals(borrowInfoVO.getCanTransactionAndroid())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_ANDROID);
            }
            if ("1".equals(borrowInfoVO.getCanTransactionWei())) {
                throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_WEI);
            }
        }
        UserInfoVO usersInfo = amUserClient.findUsersInfoById(borrow.getUserId());
        if (null != usersInfo) {
            String roleIsOpen = systemConfig.getRoleIsopen();
            if(org.apache.commons.lang3.StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
                if (usersInfo.getRoleId() != 1) {// 非投资用户
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
                }
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
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowId);
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
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        // 查看tmp表
        BorrowTenderRequest borrowTenderRequest = new BorrowTenderRequest();
        borrowTenderRequest.setBorrowNid(borrowNid);
        borrowTenderRequest.setTenderNid(logOrdId);
        borrowTenderRequest.setTenderUserId(userId);
        data.put("borrowNid",borrow.getBorrowNid());
        data.put("investDesc","恭喜您，投资成功！");
        logger.info("获取投资成功结果参数 userId {}  logOrdId {} borrowNid {}",userId,logOrdId,borrowNid);
        BorrowTenderVO borrowTender = amTradeClient.selectBorrowTender(borrowTenderRequest);
        logger.info("获取投资成功结果为:"+borrowTender);

        if(borrowTender!=null){
            BigDecimal earnings = new BigDecimal("0");
            // 本金收益  历史回报
            //BigDecimal earnings = BorrowEarningsUtil.getBorrowEarnings(borrowTender.getAccount(),borrow.getBorrowPeriod(),borrow.getBorrowStyle(),borrow.getBorrowApr());
            // 计算历史回报
            String interest = null;
            String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
            Integer borrowPeriod = borrow.getBorrowPeriod();// 周期
            BigDecimal borrowApr = borrow.getBorrowApr();// 項目预期年化收益率
            String account = String.valueOf(borrowTender.getAccount());
            switch (borrowStyle) {
                case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：历史回报=投资金额*年化收益÷12*月数；
                    earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(account), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：历史回报=投资金额*年化收益÷360*天数；
                    earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(account), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：历史回报=投资金额*年化收益÷12*月数；
                    earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(account), borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2,
                            BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：历史回报=投资金额*年化收益÷12*月数；
                    earnings = AverageCapitalPlusInterestUtils.getInterestCount(new BigDecimal(account), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“
                    earnings = AverageCapitalUtils.getInterestCount(new BigDecimal(account), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                default:
                    break;
            }
            // 产品加息预期收益
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrow.getBorrowExtraYield())) {
                BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(),account , borrow.getBorrowExtraYield());
                BigDecimal oldEarnings = new BigDecimal(interest);
                earnings = incEarnings.add(oldEarnings);
            }
            data.put("income",df.format(earnings));
            // 本金
            data.put("account",df.format(borrowTender.getAccount()));

            // 查询优惠券信息
            CouponUserVO couponUser = amTradeClient.getCouponUser(couponGrantId, userId);
            if(couponUser!=null){
                // 查询优惠券的投资
                BorrowTenderCpnVO borrowTenderCpn = amTradeClient.getCouponTenderByTender(userId,borrowNid,borrowTender.getNid(),couponGrantId);
                // 优惠券收益
                if(borrowTenderCpn!=null){
                    data.put("couponQuota",borrowTenderCpn.getAccount());
                    data.put("couponType",couponUser.getCouponType());
                    data.put("couponInterest",couponService.getInterest(borrow.getBorrowStyle(),couponUser.getCouponType(),borrow.getBorrowApr(),couponUser.getCouponQuota(),borrowTender.getAccount().toString(),borrow.getBorrowPeriod()));
                }else{
                    data.put("couponQuota","");
                    data.put("couponType","");
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
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(tender.getBorrowNid());
        if (null == borrow) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(tender.getBorrowNid());
        if (null == borrowInfo) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        UserVO loginUser = null;
        if(tender.getUserId()!=null){
            loginUser = amUserClient.findUserById(Integer.valueOf(tender.getUserId()));
        }


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

        // 设置产品加息 显示收益率
        if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrow.getBorrowExtraYield())) {
            investInfo.setBorrowExtraYield(df.format(borrow.getBorrowExtraYield()));
        }

        // 如果投资金额不为空
        if ((!StringUtils.isBlank(money) && Long.parseLong(money) > 0) || (couponConfig != null && (couponConfig.getCouponType() == 3 || couponConfig.getCouponType() == 1))) {

            String borrowStyle = borrow.getBorrowStyle();
            // 收益率
            BigDecimal borrowApr = borrow.getBorrowApr();
            //TODO:开始时这里是有的
/*            if (borrow.getProjectType() == 13 && borrow.getBorrowExtraYield() != null && borrow.getBorrowExtraYield().compareTo(BigDecimal.ZERO) > 0) {
                borrowApr = borrowApr.add(borrow.getBorrowExtraYield());
            }*/
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
            // 计算收益
            BigDecimal earnings = BorrowEarningsUtil.getBorrowEarnings(new BigDecimal(money),borrow.getBorrowPeriod(),borrow.getBorrowStyle(),borrow.getBorrowApr());

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

            // 产品加息预期收益
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrow.getBorrowExtraYield())) {
                if (couponConfig != null && couponConfig.getCouponType() == 3){
                    money = new BigDecimal(money).subtract(couponConfig.getCouponQuota()).toString();
                }
                BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(), money, borrow.getBorrowExtraYield());
                //BigDecimal oldEarnings = new BigDecimal(investInfo.getEarnings());
                investInfo.setEarnings(df.format(incEarnings.add(earnings)));
            }
        }



        WebResult<TenderInfoResult> result = new WebResult();
        result.setData(investInfo);
        return result;
    }
    /**
     * 计算产品加息预期收益
     * @auth sunpeikai
     * @param
     * @return
     */
    private BigDecimal increaseCalculate(Integer borrowPeriod,String borrowStyle,String money,BigDecimal borrowApr) {
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
        return earnings;
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
        String creditNid = tender.getBorrowNid().substring(3);
        String isConfirm = tender.getIsConfirm();//是否最后确认


        //add by cwyang APP3.0.9 确认是否为最后一次确认，如果是最后一次确认则必须进行投资校验
        if(isConfirm != null && "1".equals(isConfirm)){
            AppInvestInfoResultVO resultVo = new AppInvestInfoResultVO();

            try{
                appTenderCheck(tender);
            }catch (Exception e){
                resultVo.setStatus("1");
                resultVo.setStatusDesc("投资校验失败！");
                return resultVo;
            }

        }

        logger.info("investType:[{}]",investType);
        String money = tender.getMoney();
        {
            this.setProtocolsToResultVO(investInfo, investType);
        }
        // 转让的
        if ("HZR".equals(investType) && StringUtils.isNotEmpty(creditNid)) {
            AppInvestInfoResultVO result = borrowTenderService.getInterestInfoApp(tender,creditNid,tender.getMoney());
            this.setProtocolsToResultVO(result, investType);

            return result;
        }
        // 计划的
        if ("HJH".equals(investType)) {
            AppInvestInfoResultVO result = hjhTenderService.getInvestInfoApp(tender);
            this.setProtocolsToResultVO(result, investType);

            return result;
        }

        if ((!("HZR".equals(investType))) && (!("HJH".equals(investType)))) {
            // 查询项目信息
            // 优惠券总张数
            Integer recordTotal = 0;
            // 可用优惠券张数
            Integer couponAvailableCount;
            logger.info("entry 散标  borrowNid:[{}]",tender.getBorrowNid());
            BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(tender.getBorrowNid());
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
            logger.info("recordTotal:{}  , couponAvailableCount:{}",recordTotal,couponAvailableCount);
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
                    investInfo.setRealAmount("¥" + CommonUtils.formatAmount(null, new BigDecimal(money).add(couponConfig.getCouponQuota())));
                }
                investInfo.setCouponQuota(couponConfig.getCouponQuota().toString());
                investInfo.setCouponId(couponConfig.getId() + "");
                investInfo.setIsUsedCoupon("1");
            } else {
                investInfo.setCouponDescribe("暂无可用");
                investInfo.setCouponName("");
                investInfo.setCouponQuota("");
                investInfo.setCouponId("-1");

                if (couponAvailableCount > 0) {
                    investInfo.setIsThereCoupon("1");
                    investInfo.setCouponDescribe("请选择");
                } else if (recordTotal > 0 && couponAvailableCount == 0) {
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

            // 设置产品加息 显示收益率
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrow.getBorrowExtraYield())) {
                investInfo.setBorrowExtraYield(df.format(borrow.getBorrowExtraYield())+"%");
            }

            BigDecimal earnings = new BigDecimal("0");

            if (!StringUtils.isBlank(money) && Double.parseDouble(money) >= 0) {
                if( "1".equals(isConfirm)){
                    investInfo.setRealAmount(money);
                }
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
            investInfo.setCapitalInterest("历史回报: " + CommonUtils.formatAmount(borrowInterest.add(couponInterest)) +"元");
            investInfo.setConfirmCouponDescribe("加息券:  无");
            investInfo.setRealAmount("");
            investInfo.setCouponType("");

            investInfo.setDesc("历史年回报率: " + borrow.getBorrowApr() + "%      历史回报: " + CommonUtils.formatAmount(borrowInterest.add(couponInterest)) + "元");
            /**
             * 产品加息
             */
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrow.getBorrowExtraYield())) {
                investInfo.setDesc0("历史年回报率: " + borrow.getBorrowApr() + "% + "
                        + borrow.getBorrowExtraYield() + "%");
            }else{
                investInfo.setDesc0("历史年回报率: "+borrow.getBorrowApr()+"%");
            }
            // 产品加息预期收益
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrow.getBorrowExtraYield())) {
                BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(), money, borrow.getBorrowExtraYield());
                borrowInterest = incEarnings.add(borrowInterest);
            }


            investInfo.setDesc1("历史回报: " + CommonUtils.formatAmount(null, borrowInterest.add(couponInterest)) + "元");
            //investInfo.setDesc0("历史年回报率: " + borrow.getBorrowApr() + "%");
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
            investInfo.setIncreaseMoney(String.valueOf(borrowInfo.getBorrowIncreaseMoney()));
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
            investInfo.setButtonWord("");
            investInfo.setStandardValues("");
            investInfo.setUsedCouponDes("未使用");
            // 投资协议
            if (money == null || "".equals(money) || (new BigDecimal(money).compareTo(BigDecimal.ZERO) == 0)) {
                investInfo.setRealAmount("0");
                investInfo.setButtonWord("确认");
            } else {
                investInfo.setRealAmount("¥" + money);
                investInfo.setButtonWord("确认投资" + CommonUtils.formatAmount(null, money) + "元");
            }

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
        NewAgreementBean newAgreementBean=new NewAgreementBean("投资协议",  systemConfig.AppFrontHost+"/agreement/AgreementViewList?borrowType="+investType);
        list.add(newAgreementBean);
        investInfo.setProtocols(list);
        investInfo.setProtocolUrlDesc("协议列表");
        investInfo.setUsedCouponDes("未使用");
        investInfo.setIsUsedCoupon("0");
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
        String borrowType = tender.getBorrowType();
        String requestType = CommonConstant.APP_BANK_REQUEST_TYPE_TENDER;
        String baseUrl = super.getFrontHost(systemConfig,tender.getPlatform());
        String requestMapping = "/public/formsubmit?requestType=";
        String url = "";
        // 计划不需要跳转江西银行, 不能使用前端投资的统一页面，所以针对计划单独跳转前端处理页面
        if (CommonConstant.TENDER_TYPE_HJH.equalsIgnoreCase(borrowType)){
            // 计划的
            hjhTenderService.checkPlan(tender);
            requestType = "9";
            url = baseUrl + "/join/plan?requestType=" + requestType;
            url += "&couponGrantId="+tender.getCouponGrantId()+"&borrowNid="+tender.getBorrowNid()+"&platform="+tender.getPlatform()+"&account="+tender.getAccount();
            return url;
        }else if (CommonConstant.TENDER_TYPE_CREDIT.equalsIgnoreCase(borrowType)){
            // 债转的
            String creditNid = (StringUtils.isNotBlank(tender.getBorrowNid()) && tender.getBorrowNid().length() >3) ? tender.getBorrowNid().substring(3) : "";
            tender.setCreditNid(creditNid);
            tender.setAssignCapital(tender.getAccount());
            borrowTenderService.borrowCreditCheck(tender);
            requestType = "10";
            url = baseUrl + requestMapping + requestType;
            String couponGrantId = (tender.getCouponGrantId() != null ? tender.getCouponGrantId().toString() : "");
            url += "&couponGrantId="+couponGrantId+"&creditNid="+creditNid+"&platform="+tender.getPlatform()+"&assignCapital="+tender.getAccount();
            logger.info("url:[{}]",url);
            return url;
        }
        // 散标的
        appTenderCheck(tender);
        url = baseUrl + requestMapping + requestType;
        //String url = super.getFrontHost(systemConfig,tender.getPlatform()) +"/hyjf-app/user/invest/tender?requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_TENDER;
        url += "&couponGrantId="+tender.getCouponGrantId()+"&borrowNid="+tender.getBorrowNid()+"&platform="+tender.getPlatform()+"&account="+tender.getAccount();
        logger.info("url:[{}]",url);
        return url;
    }

    /**
     * 债转投资校验
     * @param tender
     */
    private void appCreditTenderCheck(TenderRequest tender) {

    }

    /**
     * 散标投资校验
     * @param request
     */
    private void appTenderCheck(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        logger.info("开始检查散标投资参数,userId:{}", userId);
        request.setUser(loginUser);
        request.setUserName(loginUser.getUsername());
        // 设置redis 用户正在投资
        String key = RedisConstants.BORROW_TENDER_REPEAT + userId;
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
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
        BorrowInfoVO borrowInfoVO = amTradeClient.getBorrowInfoByNid(request.getBorrowNid());
        if (borrow == null) {
            throw new CheckException(MsgEnum.FIND_BORROW_ERROR);
        }
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        logger.info("散标投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        borrowTenderCheck(request,borrow,borrowInfoVO,cuc,account);
        logger.info("所有参数都已检查通过!");
    }


    /**
     * 微信端获取投资信息
     *
     * @param tender
     * @return
     */
    @Override
    public AppInvestInfoResultVO getInvestInfoWeChat(TenderRequest tender) {

        AppInvestInfoResultVO vo = new AppInvestInfoResultVO();
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        df.setRoundingMode(RoundingMode.FLOOR);
        BigDecimal couponInterest = BigDecimal.ZERO;
        BigDecimal borrowInterest = new BigDecimal(0);
        String money = tender.getMoney();
        // 优惠券总张数
        Integer recordTotal = 0;
        // 可用优惠券张数
        Integer couponAvailableCount;

        UserInfoVO userInfo = amUserClient.findUserInfoById(tender.getUserId());
        String roleIsOpen = systemConfig.getRoleIsopen();
        if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
            if (userInfo.getRoleId() != 1) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
            }
        }

        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(tender.getBorrowNid());
        if (null == borrow) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(tender.getBorrowNid());
        if (null == borrowInfo) {
            // 标的不存在
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_NOT_EXIST);
        }
        vo.setBorrowApr(borrow.getBorrowApr() + "%");
        vo.setBorrowNid(tender.getBorrowNid());

        //设置是否有可用优惠券
        MyCouponListRequest request = new MyCouponListRequest();
        request.setBorrowNid(tender.getBorrowNid());
        request.setUserId(String.valueOf(tender.getUserId()));
        request.setPlatform(tender.getPlatform());
        // 可用优惠券张数
        couponAvailableCount = amTradeClient.countAvaliableCoupon(request);
        // 优惠券总张数
        recordTotal = amTradeClient.getUserCouponCount(tender.getUserId(), "0");
        if (couponAvailableCount > 0) {
            vo.setIsThereCoupon("1");
            vo.setCouponDescribe("请选择");
        } else if (couponAvailableCount == 0 && recordTotal > 0) {
            vo.setIsThereCoupon("1");
            vo.setCouponDescribe("暂无可用");
        } else {
            vo.setIsThereCoupon("0");
            vo.setCouponDescribe("无可用");
        }
        BigDecimal borrowAccountWait = borrow.getBorrowAccountWait();
        // 去最小值 最大可投和 项目可投
        if (borrowInfo.getTenderAccountMax() != null && borrowAccountWait != null && (borrow.getProjectType() == 4 || borrow.getProjectType() == 11)) {
            BigDecimal TenderAccountMax = new BigDecimal(borrowInfo.getTenderAccountMax());
            if (TenderAccountMax.compareTo(borrowAccountWait) == -1) {
                vo.setBorrowAccountWait(CommonUtils.formatAmount(null, TenderAccountMax));
            } else {
                vo.setBorrowAccountWait(CommonUtils.formatAmount(null, borrowAccountWait));
            }
        } else {
            vo.setBorrowAccountWait(CommonUtils.formatAmount(null, borrowAccountWait));
        }
        String balanceWait = borrow.getBorrowAccountWait() + "";
        if (balanceWait == null || balanceWait.equals("")) {
            balanceWait = "0";
        }
        logger.info("剩余金额为:{}  最小起投{} ",balanceWait,borrowInfo.getTenderAccountMin());
        // 剩余可投小于起投，计算收益按照剩余可投计算
        if ((StringUtils.isBlank(money) || money.equals("0")) && new BigDecimal(balanceWait).compareTo(new BigDecimal(borrowInfo.getTenderAccountMin())) < 1) {
            money = new BigDecimal(balanceWait).intValue() + "";
        }

        //計算本金收益
        BigDecimal earnings = new BigDecimal("0");
        if (!StringUtils.isBlank(money) && Double.parseDouble(money) >= 0) {
            earnings = BorrowEarningsUtil.getBorrowEarnings(new BigDecimal(money),borrow.getBorrowPeriod(),borrow.getBorrowStyle(),borrow.getBorrowApr());
            vo.setInterest(CommonUtils.formatAmount(null, earnings));
            logger.info("散标本金预期收益:  {}", earnings);
        }

        if (tender.getCouponGrantId()==null||tender.getCouponGrantId()-0==0) {
            //未选择优惠券
            vo.setDesc("年化利率: " + borrow.getBorrowApr() + "%      预期收益: " + CommonUtils.formatAmount(null, earnings) + "元");
            vo.setProspectiveEarnings(CommonUtils.formatAmount(null, earnings) + "元");
        } else {
            //选择优惠券计算优惠券收益
            CouponUserVO couponConfig = amTradeClient.getCouponUser(tender.getCouponGrantId(), tender.getUserId());
            if (couponConfig != null && couponConfig.getId() > 0) {
                couponInterest = calculateCouponTenderInterest(couponConfig, money, borrow);
            }
            vo.setDesc("年化利率: " + borrow.getBorrowApr() + "%      预期收益: " + CommonUtils.formatAmount(null, earnings.add(couponInterest)) + "元");
            vo.setProspectiveEarnings(CommonUtils.formatAmount(null, earnings.add(couponInterest)) + "元");

        }

        vo.setInitMoney(borrowInfo.getTenderAccountMin() + "");
        vo.setIncreaseMoney(String.valueOf(borrowInfo.getBorrowIncreaseMoney()));
        vo.setInvestmentDescription(borrowInfo.getTenderAccountMin() + "元起投," + borrowInfo.getBorrowIncreaseMoney() + "元递增");
        // 可用余额的递增部分
        AccountVO account = amTradeClient.getAccount(tender.getUserId());
        BigDecimal balance = account.getBankBalance();
        vo.setUserBalance(CommonUtils.formatAmount(null,balance));
        BigDecimal tmpmoney = balance.subtract(new BigDecimal(borrowInfo.getTenderAccountMin())).divide(new BigDecimal(borrowInfo.getBorrowIncreaseMoney()), 0, BigDecimal.ROUND_DOWN)
                .multiply(new BigDecimal(borrowInfo.getBorrowIncreaseMoney())).add(new BigDecimal(borrowInfo.getTenderAccountMin()));
        if (balance.subtract(new BigDecimal(borrowInfo.getTenderAccountMin())).compareTo(new BigDecimal("0")) < 0) {
            // 可用余额<起投金额 时 investAllMoney 传 -1
            // 全投金额
            vo.setInvestAllMoney("-1");
        } else {
            String borrowAccountWaitStr = vo.getBorrowAccountWait().replace(",", "");
            if (new BigDecimal(borrowInfo.getTenderAccountMax()).compareTo(new BigDecimal(borrowAccountWaitStr)) < 0) {
                vo.setInvestAllMoney(borrowInfo.getTenderAccountMax() + "");
            } else if (tmpmoney.compareTo(new BigDecimal(borrowAccountWaitStr)) < 0) {
                // 全投金额
                vo.setInvestAllMoney(tmpmoney + "");
            } else {
                // 全投金额
                vo.setInvestAllMoney(vo.getBorrowAccountWait() + "");
            }
        }
        vo.setBorrowAccountWait(borrow.getBorrowAccountWait().intValue()+"");
        return vo;
    }

    /**
     * web散标投资校验
     * @param request
     * @param borrow
     * @param borrowInfoVO
     * @param cuc
     * @param account
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> borrowTenderCheck(TenderRequest request,BorrowAndInfoVO borrow, BorrowInfoVO borrowInfoVO ,CouponUserVO cuc ,BankOpenAccountVO account) {
        // 查询散标是否存在
        Integer userId = request.getUserId();
        if(borrow ==null){
            borrow = amTradeClient.selectBorrowByNid(request.getBorrowNid());
        }
        if(borrowInfoVO ==null){
            borrowInfoVO = amTradeClient.getBorrowInfoByNid(request.getBorrowNid());
        }
        if (borrow == null || borrowInfoVO == null) {
            throw new CheckException(MsgEnum.FIND_BORROW_ERROR);
        }
        borrow.setTenderAccountMin(borrowInfoVO.getTenderAccountMin());
        borrow.setTenderAccountMax(borrowInfoVO.getTenderAccountMax());
        borrow.setCanTransactionAndroid(borrowInfoVO.getCanTransactionAndroid());
        borrow.setCanTransactionIos(borrowInfoVO.getCanTransactionIos());
        borrow.setCanTransactionPc(borrowInfoVO.getCanTransactionPc());
        borrow.setCanTransactionWei(borrowInfoVO.getCanTransactionWei());
        borrow.setBorrowIncreaseMoney(borrowInfoVO.getBorrowIncreaseMoney());
        logger.info("散标投资校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        UserVO user = amUserClient.findUserById(request.getUserId());
        request.setUser(user);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        // 检查用户状态  角色  授权状态等  是否允许投资
        checkUser(user, userInfo);
        // 检查江西银行账户
        if(account ==null){
            account = amUserClient.selectBankAccountById(userId);
        }
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 查询用户账户表-投资账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 投资检查参数
        this.checkParam(request, borrow, account, userInfo ,borrowInfoVO);
        // 查询选择的优惠券
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = amTradeClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 检查金额
        this.checkTenderMoney(request, borrow, cuc, tenderAccount );
        logger.info("所有参数都已检查通过!");
        return new WebResult<Map<String, Object>>();
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
    private BigDecimal calculateCouponTenderInterest(CouponUserVO couponConfig, String money, BorrowAndInfoVO borrow) {
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
    private void userBorrowTender(BorrowAndInfoVO borrow, BankCallBean bean, String couponGrantId) {
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
        try{
            this.borrowTender(borrow, bean);
        }catch (Exception e){
            // 回滚redis
            logger.error("标的投资异常  开始回滚redis  userId:{}   borrowNid:{}",userId, borrowNid);
            redisRecover(borrowNid,userId,txAmount);
            logger.info("标的投资异常  结束回滚redis ");
            // 投资失败,投资撤销
            try {
                boolean flag = bidCancel(userId, borrow.getBorrowNid(), bean.getOrderId(), txAmount);
                if (!flag) {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                }
            } catch (Exception ee) {
                logger.error("投标失败,请联系客服人员!userid:{} borrownid:{}  ordid:{}",userId, borrow.getBorrowNid(), bean.getOrderId());
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
            }
            throw e;
        }
        logger.info("用户:{},投资成功，金额：{}，优惠券开始调用ID：{}" ,userId, txAmount,couponGrantId);
        // 如果用了优惠券
        if (StringUtils.isNotEmpty(couponGrantId) && !"0".equals(couponGrantId) && !"-1".equals(couponGrantId)) {
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
     * 投资撤销
     * @param investUserId
     * @param productId
     * @param orgOrderId
     * @param txAmount
     * @return
     * @throws Exception
     */
    public boolean bidCancel(int investUserId, String productId, String orgOrderId, String txAmount) throws Exception {
        // 投资人的账户信息
        AccountVO outCust = this.getAccountByUserId(investUserId);
        if (outCust == null) {
            throw new Exception("投资人未开户。[投资人ID：" + investUserId + "]，" + "[投资订单号：" + orgOrderId + "]");
        }
        String tenderAccountId = outCust.getAccountId();
        logger.info("开始调用投资撤销接口  investUserId:{}  tenderAccountId:{}  productId:{}  orgOrderId:{}  txAmount:{} ",investUserId, tenderAccountId, productId, orgOrderId, txAmount);
        // 调用交易查询接口(投资撤销)
        BankCallBean queryTransStatBean = bankBidCancel(investUserId, tenderAccountId, productId, orgOrderId, txAmount);
        if (queryTransStatBean == null) {
            logger.error("调用投标申请撤销失败。" + ",[投资订单号：" + orgOrderId + "]");
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_HANDING);
        } else {
            String queryRespCode = queryTransStatBean.getRetCode();
            logger.info("投资失败交易接口查询接口返回码：" + queryRespCode+ ",[投资订单号：" + orgOrderId + "]");
            // 调用接口失败时(000以外)
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(queryRespCode)) {
                String message = queryTransStatBean.getRetMsg();
                logger.error(this.getClass().getName(), "bidCancel", "调用交易查询接口(解冻)失败。" + message + ",[投资订单号：" + orgOrderId + "]", null);
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_HANDING);
            } else if (queryRespCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST1) || queryRespCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_EXIST2)) {
                logger.info("===============冻结记录不存在,不予处理========");
                amTradeClient.deleteBorrowTenderTmp(orgOrderId);
                return true;
            } else if (queryRespCode.equals(BankCallConstant.RETCODE_BIDAPPLY_NOT_RIGHT)) {
                logger.info("===============只能撤销投标状态为投标中的标的============");
                return false;
            } else {
                amTradeClient.deleteBorrowTenderTmp(orgOrderId);
                return true;
            }
        }
    }

    /**
     * 投标失败后,调用投资撤销接口
     * @param investUserId
     * @param investUserAccountId
     * @param productId
     * @param orgOrderId
     * @param txAmount
     * @return
     */
    private BankCallBean bankBidCancel(Integer investUserId, String investUserAccountId, String productId,
                                   String orgOrderId, String txAmount) {
        String methodName = "bidCancel";
        // 调用汇付接口(交易状态查询)
        BankCallBean bean = new BankCallBean();
        String orderId = GetOrderIdUtils.getOrderId2(investUserId);
        String bankCode = systemConfig.getBankBankcode();
        String instCode = systemConfig.getBankInstcode();
        AccountVO investUser = this.getAccountByUserId(investUserId);
        bean.setVersion(BankCallConstant.VERSION_10); // 版本号(必须)
        bean.setTxCode(BankCallMethodConstant.TXCODE_BID_CANCEL); // 交易代码
        bean.setInstCode(instCode);
        bean.setBankCode(bankCode);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime()); // 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6)); // 交易流水号
        bean.setChannel(BankCallConstant.CHANNEL_PC); // 交易渠道
        bean.setAccountId(investUserAccountId);// 电子账号
        bean.setOrderId(orderId); // 订单号(必须)
        bean.setTxAmount(CustomUtil.formatAmount(txAmount));// 交易金额
        bean.setProductId(productId);// 标的号
        bean.setOrgOrderId(orgOrderId);// 原标的订单号
        bean.setLogOrderId(orderId);// 订单号
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单日期
        bean.setLogUserId(String.valueOf(investUserId));// 用户Id
        bean.setLogUserName(investUser == null ? "" : investUser.getUserName()); // 用户名
        bean.setLogRemark("投标申请撤销"); // 备注
        // 调用银行接口
        BankCallBean chinapnrBean = BankCallUtils.callApiBg(bean);
        if (chinapnrBean == null) {
            logger.error(this.getClass().getName(), methodName, new Exception("调用交易状态查询接口失败![参数：" + bean.getAllParams() + "]"));
            return null;
        }
        return chinapnrBean;
    }

    /**
     * 回滚redis
     * @param borrowNid
     * @param userId
     * @param account
     * @return
     */
    private boolean redisRecover(String borrowNid, Integer userId, String account) {
        JedisPool pool = RedisUtils.getPool();
        Jedis jedis = pool.getResource();
        BigDecimal accountBigDecimal = new BigDecimal(account);
        while ("OK".equals(jedis.watch(RedisConstants.BORROW_NID+borrowNid))) {
            String balanceLast = RedisUtils.get(RedisConstants.BORROW_NID+borrowNid);
            if (StringUtils.isNotBlank(balanceLast)) {
                logger.info("PC用户:" + userId + "***redis剩余金额：" + balanceLast);
                BigDecimal recoverAccount = accountBigDecimal.add(new BigDecimal(balanceLast));
                Transaction transaction = jedis.multi();
                transaction.set(RedisConstants.BORROW_NID+borrowNid, recoverAccount.toString());
                List<Object> result = transaction.exec();
                if (result == null || result.isEmpty()) {
                    jedis.unwatch();
                } else {
                    String ret = (String) result.get(0);
                    if (ret != null && ret.equals("OK")) {
                        logger.info("用户:" + userId + "*******from redis恢复redis：" + account);
                        return true;
                    } else {
                        jedis.unwatch();
                    }
                }
            }
        }
        return false;
    }

    /**
     * 操作数据库表
     *
     * @param borrow
     * @param bean
     */
    private void borrowTender(BorrowAndInfoVO borrow, BankCallBean bean) {
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
        UserVO user = amUserClient.findUserById(Integer.parseInt(bean.getLogUserId()));
        UserInfoVO userInfo = amUserClient.findUsersInfoById(Integer.parseInt(bean.getLogUserId()));
        tenderBg.setTenderUserAttribute(userInfo.getAttribute());
        tenderBg.setClient(bean.getLogClient());
        tenderBg.setUserName(user.getUsername());
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
        // 开始调用原子层操作主表
        logger.info("开始操作原子层主表");
        boolean insertFlag = amTradeClient.borrowTender(tenderBg);
        logger.info("操作原子层主表结束 结果 {} ",insertFlag);
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
        }else{
            logger.error("投资失败  对象:{}",JSONObject.toJSONString(tenderBg));
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
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
                while ("OK".equals(jedis.watch(RedisConstants.BORROW_NID+borrowNid))) {
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
                                transaction.set(RedisConstants.BORROW_NID+borrowNid, lastAccount.toString());
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

    private void updateUtm(Integer userId, BigDecimal accountDecimal, Integer nowTime, BorrowAndInfoVO borrow) {
        //更新汇计划列表成功的前提下
        // 更新渠道统计用户累计投资
        // 投资人信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
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
        //压入消息队列
        try {
            appChannelStatisticsProducer.messageSend(new MessageContent(MQConstant.TENDER_CHANNEL_STATISTICS_DETAIL_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(params)));
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("渠道统计用户累计投资推送消息队列失败！！！");
        }

        /*(6)更新  渠道统计用户累计投资  和  huiyingdai_utm_reg的首投信息 结束*/
    }
}
