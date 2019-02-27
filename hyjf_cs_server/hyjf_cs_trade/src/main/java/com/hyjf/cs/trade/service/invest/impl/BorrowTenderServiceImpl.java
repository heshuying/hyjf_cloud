/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.invest.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.MyCouponListRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.callcenter.CallCenterAccountDetailVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.EvaluationConfigVO;
import com.hyjf.am.vo.trade.TenderToCreditAssignCustomizeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.trade.coupon.BestCouponListVO;
import com.hyjf.am.vo.trade.coupon.CouponRealTenderVO;
import com.hyjf.am.vo.trade.coupon.CouponUserVO;
import com.hyjf.am.vo.trade.hjh.HjhPlanVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.*;
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
import com.hyjf.cs.trade.client.CsMessageClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
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
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description 出借接口
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
    private HjhTenderService hjhTenderService;
    @Autowired
    private BorrowCreditTenderService borrowTenderService;
    @Autowired
    private AuthService authService;
    @Autowired
    private CsMessageClient amMongoClient;
    @Autowired
    private CommonProducer commonProducer;
    /**
     * @param request
     * @Description 散标出借
     * @Author sunss
     * @Date 2018/6/24 14:35
     */
    @Override
    @HystrixCommand(commandKey = "散标出借(三端)-borrowTender",fallbackMethod = "fallBackTender",ignoreExceptions = CheckException.class,commandProperties = {
            //设置断路器生效
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),
            //一个统计窗口内熔断触发的最小个数3/10s
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "3"),
            @HystrixProperty(name = "fallback.isolation.semaphore.maxConcurrentRequests", value = "50"),
            @HystrixProperty(name = "execution.isolation.strategy", value = "SEMAPHORE"),
            //熔断5秒后去尝试请求
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "5000"),
            //失败率达到30百分比后熔断
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "30")})
    public WebResult<Map<String, Object>> borrowTender(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        logger.info("开始检查散标出借参数,userId:{}", userId);
        request.setUser(loginUser);
        request.setUserName(loginUser.getUsername());
        if(request.getAccount()==null||"".equals(request.getAccount())){
            request.setAccount("0");
        }
        // 设置redis 用户正在出借
        String key = RedisConstants.BORROW_TENDER_REPEAT + userId;
        boolean checkTender = RedisUtils.tranactionSet(key, RedisConstants.TENDER_OUT_TIME);
        if(!checkTender){
            // 用户正在出借
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
        //保存用户操作日志
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())|| CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle());

        String dayOrMonth ="";
        if(isMonth){//月标
            dayOrMonth = borrow.getBorrowPeriod() + "个月散标";
        }else{
            dayOrMonth = borrow.getBorrowPeriod() + "天散标";
        }
        UserVO userVO = borrowTenderService.getUserByUserId(userId);
        UserInfoVO userInfoVO = borrowTenderService.getUsersInfoByUserId(userId);
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE4);
        userOperationLogEntity.setIp(request.getIp());
        userOperationLogEntity.setPlatform(Integer.valueOf(request.getPlatform()));
        userOperationLogEntity.setRemark(dayOrMonth);
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(userVO.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfoVO.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        BankOpenAccountVO account = amUserClient.selectBankAccountById(userId);
        logger.info("散标出借校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        Map<String, Object> resultEval = borrowTenderCheck(request,borrow,borrowInfoVO,cuc,account);
        logger.info("所有参数都已检查通过!");
        // 如果没有本金出借且有优惠券出借
        BigDecimal decimalAccount = StringUtils.isNotEmpty(request.getAccount()) ? new BigDecimal(request.getAccount()) : BigDecimal.ZERO;
        WebResult<Map<String, Object>> result = new WebResult();
        if (decimalAccount.compareTo(BigDecimal.ZERO) != 1 && cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1)) {
            logger.info("没有本金出借且有优惠券出借:{}",decimalAccount);
            // 如果用了优惠券
            couponTender(request.getIp(),request.getAccount(),request.getPlatform()+"","",request.getUserName(),
                    request.getCouponGrantId()+"", userId, request.getBorrowNid());
            result.setStatus(MsgEnum.ERR_AMT_TENDER_SUCCESS.getCode());
            Map<String, Object> map = new HashedMap();
            map.put("sign",request.getSign());
            map.put("logOrdId","");
            map.put("couponGrantId",request.getCouponGrantId());
            map.put("account","0");
            map.put("borrowNid",request.getBorrowNid());
            map.put("isPrincipal","0");
            map.put("investLevel",borrow.getInvestLevel());
            map.putAll(resultEval);
            result.setData(map);
            return result;
        }else{
            logger.info("开始本金+优惠券出借");
            result = tender(request, borrow, account, cuc,resultEval);
        }
        // 开始真正的出借逻辑
        return result;
    }

    public WebResult<Map<String, Object>> fallBackTender(TenderRequest request){
        logger.info("==================已进入 散标出借(三端) fallBackTender 方法================");
        throw new CheckException(MsgEnum.STATUS_CE999999);
    }

    /**
     * 开始真正的出借  调用江西银行
     * @param request
     * @param borrow
     * @param account
     * @param cuc
     * @return
     */
    private WebResult<Map<String, Object>> tender(TenderRequest request, BorrowAndInfoVO borrow, BankOpenAccountVO account, CouponUserVO cuc, Map<String, Object> resultEval) {
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
        // 是否冻结金额  实时放款出借不冻结
        callBean.setFrzFlag(BankCallConstant.DEBT_FRZFLAG_UNFREEZE);
        // 订单号
        callBean.setLogOrderId(orderId);
        // 订单日期
        callBean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 客户IP
        callBean.setLogIp(request.getIp());
        // 出借用户
        callBean.setLogUserId(String.valueOf(userId));
        // 出借用户名
        callBean.setLogUserName(request.getUser().getUsername());
        callBean.setLogClient(Integer.parseInt(request.getPlatform()));

        //错误页
        String retUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/borrow/" + request.getBorrowNid() + "/result/failed?logOrdId="+callBean.getLogOrderId() + "&borrowNid=" + request.getBorrowNid();
        //成功页
        String successUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/borrow/" + request.getBorrowNid() + "/result/success?logOrdId=" +callBean.getLogOrderId() + "&borrowNid=" + request.getBorrowNid()
                +"&couponGrantId="+(request.getCouponGrantId()==null?0:request.getCouponGrantId())+"&isPrincipal=1&account="+callBean.getTxAmount();
        if(request.getToken() != null && !"".equals(request.getToken())){
            retUrl += "&token=1";
            successUrl += "&token=1";
        }
        if(request.getSign() != null && !"".equals(request.getSign())){
            retUrl += "&sign=" + request.getSign();
            successUrl += "&sign=" + request.getSign();
        }
        // 异步调用路
        String bgRetUrl = "http://CS-TRADE/hyjf-web/tender/borrow/bgReturn?platform="+request.getPlatform()+"&couponGrantId=" + (request.getCouponGrantId()==null?"0":request.getCouponGrantId());
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
            map.putAll(resultEval);
            map.put("investLevel",borrow.getInvestLevel());
            result.setData(map);
            return result;
        } catch (Exception e) {
            throw new CheckException(MsgEnum.STATUS_CE999999);
        }
    }

    /**
     * 检查出借金额
     * @param request
     * @param borrow
     * @param cuc
     * @param tenderAccount
     */
    private void checkTenderMoney(TenderRequest request, BorrowAndInfoVO borrow, CouponUserVO cuc, AccountVO tenderAccount) {
        String account = request.getAccount();
        // 判断用户出借金额是否为空
        if (!(StringUtils.isNotEmpty(account) || (StringUtils.isEmpty(account) && cuc != null && cuc.getCouponType() == 3))) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        // 出借金额是否数值
        if (!DigitalUtils.isNumber(account)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_FORMAT);
        }
        // 出借金额不能为0元
        if (("0".equals(account) && cuc == null)
                || ("0".equals(account) && cuc != null && cuc.getCouponType() == 2)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        // 出借金额是否为整数
        if (!DigitalUtils.isInteger(account)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INT);
        }
        int accountInt = Integer.parseInt(account);
        // 该优惠券只能单独使用
        if (accountInt != 0 && cuc != null && cuc.getCouponType() == 1 && cuc.getAddFlg()!=null&& cuc.getAddFlg() == 1) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_COUPON_USE_ALONE);
        }
        // 出借金额不能为负数
        if (accountInt < 0) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NEGATIVE);
        }
        // 将出借金额转化为BigDecimal
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
        // 出借金额不能大于项目总额
        if (accountBigDecimal.compareTo(borrow.getAccount()) > 0) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_GREATER_THAN_TOTAL);
        }
        // 可用金额不足
        if (tenderAccount.getBankBalance().compareTo(accountBigDecimal) < 0) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NOT_ENOUGH);
        }
        // redis剩余金额不足
        // 出借金额不能大于项目剩余
        if (accountBigDecimal.compareTo(new BigDecimal(balance)) == 1) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_BIG);
        }
        // add by cwyang 在只使用代金券和体验金,并且没有本金的情况下,不进行出借递增金额的判断,在出借金额等于最大可投金额时也不做递增金额的判断
        if (!(cuc != null && (cuc.getCouponType() == 3 || cuc.getCouponType() == 1) && accountInt == 0)) {
            // 出借递增金额须为" + borrowDetail.getIncreaseMoney() + " 元的整数倍
            if (borrow.getBorrowIncreaseMoney() != null && (accountInt - min) % borrow.getBorrowIncreaseMoney() != 0
                    && accountBigDecimal.compareTo(new BigDecimal(balance)) == -1 && accountInt < borrow.getTenderAccountMax()) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INCREMENTING,borrow.getBorrowIncreaseMoney());
            }
        }
    }

    /**
     *  检查用户状态  角色  授权状态等  是否允许出借
     *  登录>开户>设置交易密码>服务费授权（自动投标+自动债转）>授权有效期>授权金额>风险测评；
     * @param user
     * @param userInfo
     */
    private void checkUser(UserVO user, UserInfoVO userInfo) {
        // 用户未开户
        if (user.getBankOpenAccount() == 0) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码状态检查
        if (user.getIsSetPassword() == 0) {
            throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        // 判断用户是否禁用// 0启用，1禁用
        if (user.getStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_USER_INVALID);
        }
        if (null != userInfo) {
            // 合规校验角色
            String roleIsOpen = systemConfig.getRoleIsopen();
            if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
                if (userInfo.getRoleId().intValue() != 1) {
                    // 仅限出借人进行出借
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
                }
            }
        }
        // 缴费授权状态
        if (!authService.checkPaymentAuthStatus(user.getUserId())) {
            // 未进行服务费授权
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_PAYMENT_AUTH);
        }
        // 风险测评校验
        //this.checkEvaluation(user);

    }

    /**
     * 散标出借参数校验
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
        // 未设置该出借项目的项目类型
        if (projectType == null) {
            throw new CheckException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(projectType));
        // 未查询到该出借项目的设置信息
        if (borrowProjectType == null) {
            throw new CheckException(MsgEnum.ERR_TRADE_BORROR_USER_NOT_EXIST);
        }
        // 51老用户标// 1是51，0不是
        if (borrowProjectType.getInvestUserType().equals(0)) {
            Integer is51 = userInfo.getIs51();
            // 该项目只能51老用户出借
            if (is51 != null && is51 == 1) {
                throw new CheckException(MsgEnum.ERR_TRADE_51_OLD_USER);
            }
        }
        // 新手标
        logger.info("是否新手标：：："+borrowProjectType.getInvestUserType());
        logger.info("是否新手标：：："+borrowProjectType.getInvestUserType().equals(1));
        if (borrowProjectType.getInvestUserType().equals(1)) {
            boolean isNew = this.checkIsNewUserCanInvest(userId);
            // 该项目只能新手出借
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
        // 出借客户端 校验
        // 出借平台不能为空
        if(request.getPlatform()==null){
            throw new CheckException(MsgEnum.STATUS_ZC000018);
        }
        String platform = request.getPlatform();
        if (platform.equals("0") && borrow.getCanTransactionPc().equals("0")) {
            String tmpInfo = "";
            if (borrow.getCanTransactionAndroid().equals("1")) {
                tmpInfo += " Android端  ";
            }
            if (borrow.getCanTransactionIos().equals("1")) {
                tmpInfo += " Ios端  ";
            }
            if (borrow.getCanTransactionWei().equals("1")) {
                tmpInfo += " 微信端  ";
            }
            throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PLAT,tmpInfo);
        } else if (platform.equals("1") && borrow.getCanTransactionWei().equals("0")) {
            String tmpInfo = "";
            if (borrow.getCanTransactionAndroid().equals("1")) {
                tmpInfo += " Android端  ";
            }
            if (borrow.getCanTransactionIos().equals("1")) {
                tmpInfo += " Ios端  ";
            }
            if (borrow.getCanTransactionPc().equals("1")) {
                tmpInfo += " Pc端  ";
            }
            throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PLAT,tmpInfo);
        } else if (platform.equals("2") && borrow.getCanTransactionAndroid().equals("0")) {
            String tmpInfo = "";
            if (borrow.getCanTransactionPc().equals("1")) {
                tmpInfo += " PC端  ";
            }
            if (borrow.getCanTransactionIos().equals("1")) {
                tmpInfo += " Ios端  ";
            }
            if (borrow.getCanTransactionWei().equals("1")) {
                tmpInfo += " 微信端  ";
            }
            throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PLAT,tmpInfo);
        } else if (platform.equals("3") && borrow.getCanTransactionIos().equals("0")) {
            String tmpInfo = "";
            if (borrow.getCanTransactionPc().equals("1")) {
                tmpInfo += " PC端  ";
            }
            if (borrow.getCanTransactionAndroid().equals("1")) {
                tmpInfo += " Android端  ";
            }
            if (borrow.getCanTransactionWei().equals("1")) {
                tmpInfo += " 微信端  ";
            }
            throw new CheckException(MsgEnum.ERR_TENDER_ALLOWED_PLAT,tmpInfo);
        }
        // 借款人不可以自己出借项目
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
        UserInfoVO usersInfo = amUserClient.findUsersInfoById(borrow.getUserId());
        if (null != usersInfo) {
            String roleIsOpen = systemConfig.getRoleIsopen();
            if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
                if (userInfo.getRoleId().intValue() != 1) {
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
                }
            }
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
     * 散标出借异步处理
     *
     * @param bean
     * @param couponGrantId
     * @return
     */
    @Override
    public BankCallResult borrowTenderBgReturn(BankCallBean bean, String couponGrantId) {
        logger.info("开始调用散标出借异步方法,logOrdId:{},userId:{},优惠券:{},平台为:{} 返回码为：{}",bean.getLogOrderId(),bean.getLogUserId(),couponGrantId,bean.getLogClient(),bean.getRetCode());
        // 用户userId
		if (couponGrantId == null || "null".equals(couponGrantId) || couponGrantId.equals("")) {
			couponGrantId = "0";
		}
        int userId = StringUtils.isBlank(bean.getLogUserId()) ? 0 : Integer.parseInt(bean.getLogUserId());
        // 出借结果返回码
        String respCode = bean.getRetCode();
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
                logger.info("用户:" + userId + "**出借接口调用失败，余额不足，错误码: " + respCode);
                result.setMessage("出借失败，可用余额不足！请联系客服.");
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
            logger.error("用户:" + userId + "**回调时,borrowNid为空，错误码: " + respCode);
            result.setStatus(false);
            result.setMessage("回调时,borrowNid为空.");
            return result;
        }
        // 开始出借逻辑开始调用散标出借异步方法
        this.userBorrowTender(borrow, bean, couponGrantId);
        result.setStatus(true);
        return result;
    }

    /**
     * 获取出借结果 ---失败
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
     * 查询投标成功的结果
     *
     * @param userId
     * @param logOrdId
     * @param borrowNid
     * @param couponGrantId
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getBorrowTenderResultSuccess(Integer userId, String logOrdId, String borrowNid, Integer couponGrantId,String isPrincipal,String accountStr) {
        Map<String, Object> data = new HashedMap();
        DecimalFormat df = CustomConstants.DF_FOR_VIEW;
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(borrowNid);
        BorrowInfoVO borrowInfo = amTradeClient.getBorrowInfoByNid(borrowNid);
        // 查看tmp表
        data.put("borrowNid",borrow.getBorrowNid());
        data.put("investDesc","恭喜您出借成功");
        BigDecimal earnings = new BigDecimal("0");
        // 计算历史回报
        String interest = "0";
        String borrowStyle = borrow.getBorrowStyle();// 项目还款方式
        Integer borrowPeriod = borrow.getBorrowPeriod();// 周期
        BigDecimal borrowApr = borrow.getBorrowApr();// 項目预期年化收益率
        BigDecimal account = BigDecimal.ZERO;
        if(accountStr!=null&&!accountStr.equals("")){
            account = new BigDecimal(accountStr);
        }
        if(logOrdId!=null && account!=null && !"".equals(account) && "1".equals(isPrincipal)){
            switch (borrowStyle) {
                case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                    earnings = DuePrincipalAndInterestUtils.getMonthInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷360*天数；
                    earnings = DuePrincipalAndInterestUtils.getDayInterest(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：历史回报=出借金额*年化收益÷12*月数；
                    earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2,
                            BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：历史回报=出借金额*年化收益÷12*月数；
                    earnings = AverageCapitalPlusInterestUtils.getInterestCount(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                case CalculatesUtil.STYLE_PRINCIPAL:// 还款方式为”等额本金“
                    earnings = AverageCapitalUtils.getInterestCount(account, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                    interest = df.format(earnings);
                    break;
                default:
                    break;
            }
            // 产品加息预期收益
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
                BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(),account.toString() , borrowInfo.getBorrowExtraYield());
                earnings = incEarnings.add(earnings);
            }
            data.put("income",df.format(earnings));
            // 本金
            data.put("account",df.format(account));
        }
        // 查询优惠券信息
        CouponUserVO couponUser = amTradeClient.getCouponUser(couponGrantId, userId);
        CouponRealTenderVO couponRealTenderVO=new CouponRealTenderVO();
        logger.info("获取投标成功结果  logOrdId!=null && account!=null && !.equals(account):{} ",logOrdId!=null && account!=null && !"".equals(account));
        if(logOrdId!=null&&!"".equals(logOrdId)){
            couponRealTenderVO=amTradeClient.selectCouponRealTenderByOrderId(logOrdId);
        }

        if (couponUser != null&&couponRealTenderVO!=null) {
            data.put("couponType", couponUser.getCouponType());
            BigDecimal couponInterest = BigDecimal.ZERO;
            if (couponUser.getCouponType() == 1) {
                couponInterest = couponService.getInterestDj(couponUser.getCouponQuota(), couponUser.getCouponProfitTime().intValue(), borrowApr);
            } else {
                couponInterest = couponService.getInterest(borrowStyle, couponUser.getCouponType(), borrowApr, couponUser.getCouponQuota(),account.toString(), borrow.getBorrowPeriod());
            }
            logger.info("获取投标成功结果  earnings:{} ",earnings.toString());
            logger.info("获取投标成功结果  couponInterest:{} ",couponInterest.toString());
            if (couponUser != null && couponUser.getCouponType() == 3) {
                data.put("income", df.format(earnings.add(couponInterest).subtract(couponUser.getCouponQuota())));
                data.put("couponQuota", couponUser.getCouponQuota()+"元");
                data.put("appIncome", df.format(earnings.add(couponInterest)));
            } else if (couponUser != null && couponUser.getCouponType() == 1) {
                data.put("income", df.format(earnings.add(couponInterest)));
                data.put("couponQuota", couponUser.getCouponQuota()+"元");
            } else {
                data.put("income", df.format(earnings.add(couponInterest)));
                data.put("couponQuota", couponUser.getCouponQuota()+ "%");
            }
            data.put("couponInterest", df.format(couponInterest));

        } else {
            data.put("couponQuota", "");
            data.put("couponType", "");
            data.put("couponAll", "");
            data.put("couponInterest", "");
        }
        if(!data.containsKey("account")){
            data.put("account","0");
        }
        if(!data.containsKey("income")){
            data.put("income","0");
        }
        logger.info("返回给前端结果为：{} ",JSONObject.toJSONString(data));
        WebResult<Map<String, Object>> result = new WebResult();
        result.setData(data);
        return result;
    }

    /**
     * 查询appUtmReg是否首投
     * @param userId
     * @return
     */
    private boolean checkAppUtmInvestFlag(Integer userId) {
        // 新的判断是否为新用户方法
        try {
            int total = amTradeClient.countNewUserTotal(userId);
            logger.info("获取用户出借数量 userID {} 数量 {} ",userId,total);
            if (total == 1) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            throw e;
        }
    }

    /**
     * 风车理财出借同步回调通知
     * @param userId
     * @param logOrdId
     */
    private void notifyToWrb(Integer userId, String logOrdId) {
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("nid", logOrdId);
        params.put("returnType", "1");
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.WRB_QUEUE_CALLBACK_NOTIFY_TOPIC, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
           logger.error("风车理财出借回调异常...", e);
        }
    }


    /**
     * 获取出借信息
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
        BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(borrow.getProjectType()));
        String nowType = "1";
        if (borrowProjectType.getInvestUserType().equals(1)) {
            nowType = "3";
        }
        UserVO loginUser = null;
        if(tender.getUserId()!=null){
            loginUser = amUserClient.findUserById(Integer.valueOf(tender.getUserId()));
        }

        CouponUserVO couponUser = null;
        // 未登录，不计算优惠券
        if (loginUser != null) {
            // 获取用户最优优惠券
            if(tender.getCouponGrantId()!=null && tender.getCouponGrantId().intValue()>0){
                // 用户选择了优惠券
                couponUser = amTradeClient.getCouponUser(tender.getCouponGrantId(),tender.getUserId());
                logger.info("用户选择了优惠券  "+JSONObject.toJSONString(couponUser));
            }else if(tender.getCouponGrantId()==null || tender.getCouponGrantId().intValue()==0){
                // 获取用户最优优惠券
                BestCouponListVO couponConfig = new BestCouponListVO();
                MyCouponListRequest request = new MyCouponListRequest();
                request.setBorrowNid(tender.getBorrowNid());
                request.setUserId(String.valueOf(loginUser.getUserId()));
                request.setPlatform(CustomConstants.CLIENT_PC);
                request.setMoney(tender.getAccount()==null?"0":tender.getAccount());
                couponConfig = amTradeClient.selectBestCoupon(request);
                logger.info("最优优惠券   " + JSONObject.toJSONString(couponConfig));
                if (couponConfig != null) {
                    couponUser = amTradeClient.getCouponUser(Integer.parseInt(couponConfig.getUserCouponId()), tender.getUserId());
                    investInfo.setCouponConfig(couponConfig);
                }
            }
            if(couponUser!=null && (tender.getCouponGrantId()!=null && tender.getCouponGrantId().intValue()>0)){
                String config = "";
                // 加息券标识（0：禁用，1：可用）    1：体验金，2：加息券 3代金券
                int interestCoupon = borrowInfo.getBorrowInterestCoupon();
                // 体验金标识（0：禁用，1：可用）
                int moneyCoupon = borrowInfo.getBorrowTasteMoney();
                if (interestCoupon == 1) {
                    config += "2,";
                }
                if (moneyCoupon == 1) {
                    config += "1,";
                }
                Map<String, String> validateMap = couponService.validateCoupon(tender.getUserId(), tender.getAccount(), tender.getCouponGrantId(), tender.getPlatform(), borrow.getBorrowPeriod(), config,nowType);
                if (!MapUtils.isEmpty(validateMap)) {
                    couponUser = null;
                }
                logger.info("优惠券出借校验完毕  结果：{}" , validateMap);
                logger.info("用户优惠券信息为:{}" , JSONObject.toJSONString(couponUser));
            }
            if (couponUser != null) {
                BestCouponListVO couponConfig = new BestCouponListVO();
                couponConfig.setCouponType(couponUser.getCouponType());
                couponConfig.setUserCouponId(couponUser.getId()+"");
                couponConfig.setCouponQuota(couponUser.getCouponQuota());
                couponConfig.setCouponQuotaStr(couponConfig.getCouponQuotaStr());
                couponConfig.setTenderQuotaType(0);
                investInfo.setIsThereCoupon(1);
                investInfo.setCouponConfig(couponConfig);
            } else {
                investInfo.setIsThereCoupon(0);
            }
            // 可用优惠券张数
            MyCouponListRequest request = new MyCouponListRequest();
            request.setBorrowNid(tender.getBorrowNid());
            request.setUserId(String.valueOf(loginUser.getUserId()));
            request.setPlatform(tender.getPlatform());
            request.setMoney(tender.getAccount());
            Integer couponAvailableCount = amTradeClient.countAvaliableCoupon(request);
            investInfo.setCouponAvailableCount(couponAvailableCount);
            // 优惠券总张数
            /** 获取用户优惠券总张数开始  */
            Integer recordTotal = amTradeClient.getUserCouponCount(loginUser.getUserId(),"0");
            investInfo.setRecordTotal(recordTotal);
            /** 获取用户优惠券总张数结束 */
        } else {
            // 是否有优惠券
            investInfo.setIsThereCoupon(0);
            // 优惠券总张数
            investInfo.setRecordTotal(0);
            // 优惠券可用张数
            investInfo.setCouponAvailableCount(0);
        }

        // 设置产品加息 显示收益率
        if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
            investInfo.setBorrowExtraYield(df.format(borrowInfo.getBorrowExtraYield()));
        }

        // 如果出借金额不为空
        if ((!StringUtils.isBlank(money) && Long.parseLong(money) > 0) ||
                (couponUser != null && (couponUser.getCouponType() == 3 ||
                        couponUser.getCouponType() == 1))) {

            String borrowStyle = borrow.getBorrowStyle();
            // 收益率
            BigDecimal borrowApr = borrow.getBorrowApr();
            BigDecimal couponInterest = BigDecimal.ZERO;
            /** 叠加收益率开始*/
            if (couponUser != null) {
                if (couponUser.getCouponType() == 1) {
                    couponInterest =couponService.getInterestDj(couponUser.getCouponQuota(), couponUser.getCouponProfitTime().intValue(), borrowApr);
                } else {
                    couponInterest = couponService.getInterest(borrowStyle, couponUser.getCouponType(), borrowApr, couponUser.getCouponQuota(), money, borrow.getBorrowPeriod());
                }
                couponUser.setCouponInterest(df.format(couponInterest));
                // 加息券
                if (couponUser.getCouponType() == 2) {
                    borrowApr = borrowApr.add(couponUser.getCouponQuota());
                }
                if (couponUser.getCouponType() == 3) {
                    money = new BigDecimal(money).add(couponUser.getCouponQuota()).toString();
                }
            }
            /** 叠加收益率结束 */
            // 计算收益
            BigDecimal earnings = BorrowEarningsUtil.getBorrowEarnings(new BigDecimal(money),borrow.getBorrowPeriod(),borrow.getBorrowStyle(),borrow.getBorrowApr());
            investInfo.setEarnings(df.format(earnings));
            logger.info("本金收益  "+earnings.toString());
            if (couponUser != null && couponUser.getCouponType() == 3) {
                investInfo.setCapitalInterest(df.format(earnings.add(couponUser.getCouponQuota()).subtract(couponInterest)));
            } else if (couponUser != null && couponUser.getCouponType() == 1) {
                earnings = earnings.add(couponInterest);
                investInfo.setEarnings(df.format(earnings));
                investInfo.setCapitalInterest(df.format(earnings));
            } else {
                earnings = earnings.add(couponInterest);
                investInfo.setCapitalInterest(df.format(earnings.subtract(couponInterest)));
                investInfo.setEarnings(df.format(earnings));
            }
            investInfo.setCouponUser(couponUser);
            logger.info("本金+优惠券收益  "+investInfo.getEarnings());
            // 产品加息预期收益
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
                if (couponUser != null && couponUser.getCouponType() == 3){
                    money = new BigDecimal(money).subtract(couponUser.getCouponQuota()).toString();
                }
                BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(), money, borrowInfo.getBorrowExtraYield());
                logger.info("开始计算加息收益   incEarnings:{}  ",incEarnings );
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
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“：历史回报=出借金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).divide(new BigDecimal("1"), 2,
                        BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“：历史回报=出借金额*年化收益÷360*天数；
                earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“：历史回报=出借金额*年化收益÷12*月数；
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(money), borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2,
                        BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“：历史回报=出借金额*年化收益÷12*月数；
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
     * 获取APP端出借信息
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

        // 出借类型
        String investType = tender.getBorrowNid().substring(0, 3);
        String creditNid = tender.getBorrowNid().substring(3);
        String isConfirm = tender.getIsConfirm();//是否最后确认
        tender.setBorrowType(investType);
        //add by cwyang APP3.0.9 确认是否为最后一次确认，如果是最后一次确认则必须进行出借校验
        if(isConfirm != null && "1".equals(isConfirm)){
            AppInvestInfoResultVO resultVo = new AppInvestInfoResultVO();
            tender.setAccount(tender.getMoney());
            try{
                //InvestInfoApp 用于区分是否在 getAppTenderUrl 方法中判断用户测评
                getAppTenderUrl(tender,"InvestInfoApp");
            }catch (CheckException e){
                logger.error("报错了。。。",e);
                resultVo.setStatus("1");
                resultVo.setStatusDesc(e.getMessage());
                return resultVo;
            }
        }
        String money = tender.getMoney();
        {
            this.setProtocolsToResultVO(investInfo, investType);
        }

        // 转让的
        if ("HZR".equals(investType) && StringUtils.isNotEmpty(creditNid)) {
            investInfo = borrowTenderService.getInterestInfoApp(tender,creditNid,tender.getMoney());
            this.setProtocolsToResultVO(investInfo, investType);
            return investInfo;
        }
        // 计划的
        if ("HJH".equals(investType)) {
            investInfo = hjhTenderService.getInvestInfoApp(tender);
            this.setProtocolsToResultVO(investInfo, investType);
            return investInfo;
        }

        if ((!("HZR".equals(investType))) && (!("HJH".equals(investType)))) {
            // 查询项目信息
            // 优惠券总张数
            Integer recordTotal = 0;
            // 可用优惠券张数
            Integer couponAvailableCount;
            logger.info("entry 散标  borrowNid:[{}]",tender.getBorrowNid());
            RightBorrowVO borrow = amTradeClient.getRightBorrowByNid(tender.getBorrowNid());
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
            request.setMoney(tender.getMoney());
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
            investInfo.setUsedCouponDes("未使用");
            if( "1".equals(isConfirm)){
                investInfo.setRealAmount("¥" + CommonUtils.formatAmount(null, new BigDecimal(money)));
            }
            // 设置收益率
            if (couponConfig != null) {
                if (couponConfig.getCouponType() == 1) {
                    investInfo.setCouponDescribe("体验金: " + couponConfig.getCouponQuota() + "元");
                    investInfo.setCouponType("体验金");
                    investInfo.setCouponName("体验金");
                    investInfo.setUsedCouponDes("体验金: " + couponConfig.getCouponQuota() + "元");
                }
                if (couponConfig.getCouponType() == 2) {
                    investInfo.setCouponDescribe("加息券:  " + couponConfig.getCouponQuota() + "%");
                    investInfo.setCouponType("加息券");
                    investInfo.setCouponName("加息券");
                    investInfo.setUsedCouponDes("加息券:  " + couponConfig.getCouponQuota() + "%");
                }
                if (couponConfig.getCouponType() == 3) {
                    investInfo.setCouponDescribe("代金券: " + couponConfig.getCouponQuota() + "元");
                    investInfo.setCouponType("代金券");
                    investInfo.setCouponName("代金券");
                    investInfo.setUsedCouponDes("代金券: " + couponConfig.getCouponQuota() + "元");
                    //investInfo.setRealAmount("¥" + CommonUtils.formatAmount(null, new BigDecimal(money).add(couponConfig.getCouponQuota())));
                }
                investInfo.setCouponQuota(couponConfig.getCouponQuota().toString());
                investInfo.setCouponId(tender.getCouponGrantId()+"");
                investInfo.setIsUsedCoupon("1");
                investInfo.setIsThereCoupon("1");
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
            if (borrowInfo.getTenderAccountMax() != null && borrowAccountWait != null && (borrow.getProjectType() == 4 || borrow.getProjectType() == 11)) {
                BigDecimal TenderAccountMax = new BigDecimal(borrowInfo.getTenderAccountMax());
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
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
                investInfo.setBorrowExtraYield(df.format(borrowInfo.getBorrowExtraYield())+"%");
            }

            BigDecimal earnings = new BigDecimal("0");

            if (!StringUtils.isBlank(money) && Double.parseDouble(money) >= 0) {

                String borrowStyle = borrow.getBorrowStyle();
                // 收益率
                BigDecimal borrowApr = borrow.getBorrowApr();
                // 周期
                Integer borrowPeriod = borrow.getBorrowPeriod();
                // 计算本金出借历史回报
                switch (borrowStyle) {
                    // 还款方式为”按月计息，到期还本还息“: 历史回报=出借金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_END:
                        earnings = DuePrincipalAndInterestUtils.getMonthInterest(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”按天计息，到期还本还息“: 历史回报=出借金额*年化收益÷360*天数；
                    case CalculatesUtil.STYLE_ENDDAY:
                        earnings = DuePrincipalAndInterestUtils.getDayInterest(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”先息后本“: 历史回报=出借金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_ENDMONTH:
                        earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”等额本息“: 历史回报=出借金额*年化收益÷12*月数；
                    case CalculatesUtil.STYLE_MONTH:
                        earnings = AverageCapitalPlusInterestUtils.getInterestCount(new BigDecimal(money),
                                borrowApr.divide(new BigDecimal("100")), borrowPeriod)
                                .setScale(2, BigDecimal.ROUND_DOWN);
                        investInfo.setInterest(CommonUtils.formatAmount(null, earnings));
                        break;
                    // 还款方式为”等额本金“: 历史回报=出借金额*年化收益÷12*月数；
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
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
                investInfo.setDesc0("历史年回报率: " + borrow.getBorrowApr() + "% + "
                        + borrowInfo.getBorrowExtraYield() + "%");
            }else{
                investInfo.setDesc0("历史年回报率: "+borrow.getBorrowApr()+"%");
            }
            // 产品加息预期收益
            if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
                BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(), money, borrowInfo.getBorrowExtraYield());
                borrowInterest = incEarnings.add(borrowInterest);
            }


            investInfo.setDesc1("历史回报: " + CommonUtils.formatAmount(null, borrowInterest.add(couponInterest)) + "元");
            //investInfo.setDesc0("历史年回报率: " + borrow.getBorrowApr() + "%");
            investInfo.setConfirmRealAmount("出借金额: " + CommonUtils.formatAmount(money) + "元");
            investInfo.setRealAmount("出借金额: " + CommonUtils.formatAmount(money) + "元");
            investInfo.setBorrowInterest(CommonUtils.formatAmount(borrowInterest) + "元");
            // 安卓的历史回报使用这个字段
            investInfo.setProspectiveEarnings(CommonUtils.formatAmount(null,borrowInterest.add(couponInterest))+"元");
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
            // 出借协议
            if (money == null || "".equals(money) || (new BigDecimal(money).compareTo(BigDecimal.ZERO) == 0)) {
                investInfo.setRealAmount("¥" + CommonUtils.formatAmount(null, new BigDecimal("0")));
                investInfo.setButtonWord("确认");
            } else {
                investInfo.setRealAmount("¥" + CommonUtils.formatAmount(null, new BigDecimal(money)));
                investInfo.setButtonWord("确认出借" + CommonUtils.formatAmount(null, money) + "元");
            }

            // add by liuyang 神策数据统计 20180820 start
            // 获取标的还款方式
            BorrowStyleVO projectBorrowStyle = this.amTradeClient.getBorrowStyle(borrow.getBorrowStyle());
            if (projectBorrowStyle != null) {
                investInfo.setBorrowStyleName(StringUtils.isBlank(projectBorrowStyle.getName()) ? "" : projectBorrowStyle.getName());
            } else {
                investInfo.setBorrowStyleName("");
            }

            // 项目名称
            investInfo.setProjectName(borrowInfo.getProjectName());
            // 借款期限
            investInfo.setBorrowPeriod(borrow.getBorrowPeriod());
            if ("endday".equals(borrow.getBorrowStyle())) {
                investInfo.setDurationUnit("天");
            } else {
                investInfo.setDurationUnit("月");
            }
            // add by liuyang 神策数据统计 20180820 end
            return investInfo;
        }
        return null;
    }

    /**
     * 校验风险测评
     *
     * @param tender
     * @return
     */
    @Override
    public Map<String,Object> checkEvalApp(TenderRequest tender) {
        Map<String,Object> investInfo = new HashMap<>();
        // 出借类型
        String investType = tender.getBorrowNid().substring(0, 3);
        String creditNid = tender.getBorrowNid().substring(3);
        //初始化接口回传参数
        investInfo.put("revalJudge",false);
        investInfo.put("projectRevalJudge",false);
        investInfo.put("evalType","");
        investInfo.put("revaluationMoney","");
        investInfo.put("riskLevelDesc","");
        investInfo.put("projectRiskLevelDesc","");
        investInfo.put("revalPrincipalJudge",false);
        //测评判断逻辑开始
        UserVO loginUser = amUserClient.findUserById(tender.getUserId());
        //风险测评
        if (!(tender.getMoney() == null || "".equals(tender.getMoney()) || (new BigDecimal(tender.getMoney()).compareTo(BigDecimal.ZERO) == 0))) {
            //从user中获取客户类型，ht_user_evalation_result（用户测评总结表）
            UserEvalationResultVO userEvalationResultCustomize = amUserClient.selectUserEvalationResultByUserId(tender.getUserId());
            if (userEvalationResultCustomize != null) {
                EvaluationConfigVO evalConfig = new EvaluationConfigVO();
                //1.散标／债转出借者测评类型校验
                String debtEvaluationTypeCheck = "0";
                //2.散标／债转单笔投资金额校验
                String deptEvaluationMoneyCheck = "0";
                //3.散标／债转待收本金校验
                String deptCollectionEvaluationCheck = "0";
                //4.智投出借者测评类型校验
                String intellectualEveluationTypeCheck = "0";
                //5.智投单笔投资金额校验
                String intellectualEvaluationMoneyCheck = "0";
                //6.智投待收本金校验
                String intellectualCollectionEvaluationCheck = "0";
                //获取开关信息
                List<EvaluationConfigVO> evalConfigList = amTradeClient.selectEvaluationConfig(evalConfig);
                if (evalConfigList != null && evalConfigList.size() > 0) {
                    evalConfig = evalConfigList.get(0);
                    //1.散标／债转出借者测评类型校验
                    debtEvaluationTypeCheck = evalConfig.getDebtEvaluationTypeCheck() == null ? "0" : String.valueOf(evalConfig.getDebtEvaluationTypeCheck());
                    //2.散标／债转单笔投资金额校验
                    deptEvaluationMoneyCheck = evalConfig.getDeptEvaluationMoneyCheck() == null ? "0" : String.valueOf(evalConfig.getDeptEvaluationMoneyCheck());
                    //3.散标／债转待收本金校验
                    deptCollectionEvaluationCheck = evalConfig.getDeptCollectionEvaluationCheck() == null ? "0" : String.valueOf(evalConfig.getDeptCollectionEvaluationCheck());
                    //4.智投出借者测评类型校验
                    intellectualEveluationTypeCheck = evalConfig.getIntellectualEveluationTypeCheck() == null ? "0" : String.valueOf(evalConfig.getIntellectualEveluationTypeCheck());
                    //5.智投单笔投资金额校验
                    intellectualEvaluationMoneyCheck = evalConfig.getIntellectualEvaluationMoneyCheck() == null ? "0" : String.valueOf(evalConfig.getIntellectualEvaluationMoneyCheck());
                    //6.智投待收本金校验
                    intellectualCollectionEvaluationCheck = evalConfig.getIntellectualCollectionEvaluationCheck() == null ? "0" : String.valueOf(evalConfig.getIntellectualCollectionEvaluationCheck());
                    //7.投标时校验（二期）(预留二期开发)
                }
                //初始化金额返回值
                String revaluation_money, revaluation_money_principal;
                //根据类型从redis或数据库中获取测评类型和上限金额
                String eval_type = userEvalationResultCustomize.getEvalType();
                //初始化接口回传参数
                investInfo.put("evalType",eval_type);
                switch (eval_type) {
                    case "保守型":
                        //从redis获取金额（单笔）
                        revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE);
                        //如果reids不存在或者为零尝试获取数据库（单笔）
                        if ("0".equals(revaluation_money)) {
                            revaluation_money = evalConfig.getConservativeEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getConservativeEvaluationSingleMoney());
                        }
                        //从redis获取金额（代收本金）
                        revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_CONSERVATIVE_PRINCIPAL);
                        //如果reids不存在或者为零尝试获取数据库（代收本金）
                        if ("0".equals(revaluation_money_principal)) {
                            revaluation_money_principal = evalConfig.getConservativeEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getConservativeEvaluationPrincipalMoney());
                        }
                        break;
                    case "稳健型":
                        //从redis获取金额（单笔）
                        revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS);
                        //如果reids不存在或者为零尝试获取数据库（单笔）
                        if ("0".equals(revaluation_money)) {
                            revaluation_money = evalConfig.getSteadyEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getSteadyEvaluationSingleMoney());
                        }
                        //从redis获取金额（代收本金）
                        revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_ROBUSTNESS_PRINCIPAL);
                        //如果reids不存在或者为零尝试获取数据库（代收本金）
                        if ("0".equals(revaluation_money_principal)) {
                            revaluation_money_principal = evalConfig.getSteadyEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getSteadyEvaluationPrincipalMoney());
                        }
                        break;
                    case "成长型":
                        //从redis获取金额（单笔）
                        revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_GROWTH) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_GROWTH);
                        //如果reids不存在或者为零尝试获取数据库（单笔）
                        if ("0".equals(revaluation_money)) {
                            revaluation_money = evalConfig.getGrowupEvaluationSingleMoney() == null ? "0" : String.valueOf(evalConfig.getGrowupEvaluationSingleMoney());
                        }
                        //从redis获取金额（代收本金）
                        revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_GROWTH_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_GROWTH_PRINCIPAL);
                        //如果reids不存在或者为零尝试获取数据库（代收本金）
                        if ("0".equals(revaluation_money_principal)) {
                            revaluation_money_principal = evalConfig.getGrowupEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getGrowupEvaluationPrincipalMoney());
                        }
                        break;
                    case "进取型":
                        //从redis获取金额（单笔）
                        revaluation_money = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE);
                        //如果reids不存在或者为零尝试获取数据库（单笔）
                        if ("0".equals(revaluation_money)) {
                            revaluation_money = evalConfig.getEnterprisingEvaluationSinglMoney() == null ? "0" : String.valueOf(evalConfig.getEnterprisingEvaluationSinglMoney());
                        }
                        //从redis获取金额（代收本金）
                        revaluation_money_principal = RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL) == null ? "0" : RedisUtils.get(RedisConstants.REVALUATION_AGGRESSIVE_PRINCIPAL);
                        //如果reids不存在或者为零尝试获取数据库（代收本金）
                        if ("0".equals(revaluation_money_principal)) {
                            revaluation_money_principal = evalConfig.getEnterprisingEvaluationPrincipalMoney() == null ? "0" : String.valueOf(evalConfig.getEnterprisingEvaluationPrincipalMoney());
                        }
                        break;
                    default:
                        revaluation_money = null;
                        revaluation_money_principal = null;
                }
                String checkLeve = null;
                String borrowFlag = null;
                // 2. 转让投资信息
                if ("HZR".equals(investType) && StringUtils.isNotEmpty(creditNid)) {
                    // 查询债转信息
                    TenderToCreditAssignCustomizeVO creditAssign = this.amTradeClient.getInterestInfo(creditNid, tender.getMoney(),tender.getUserId());
                    // 原标项目编号
                    String bidNid = creditAssign.getBorrowNid();
                    BorrowAndInfoVO borrowInfoVO = this.amTradeClient.getBorrowByNid(bidNid);
                    if (borrowInfoVO != null) {
                        checkLeve = borrowInfoVO.getInvestLevel();
                        borrowFlag = "HZR";
                    }
                }else
                    // 3. 汇计划投资
                    if ("HJH".equals(investType)) {
                        String planNid = tender.getBorrowNid();
                        // 根据项目标号获取相应的计划信息
                        HjhPlanVO plan = amTradeClient.getPlanByNid(planNid);
                        if (plan != null) {
                            checkLeve = plan.getInvestLevel();
                            borrowFlag = "HJH";
                        }
                }else{
                    // 1. 散标投资信息
                    String bidNid = tender.getBorrowNid();
                    // 查询项目信息
                    BorrowAndInfoVO borrowInfoVO = this.amTradeClient.getBorrowByNid(bidNid);
                    if (borrowInfoVO != null) {
                        checkLeve = borrowInfoVO.getInvestLevel();
                        borrowFlag = "HSB";
                    }
                }
                //测评到期日
                Long lCreate = loginUser.getEvaluationExpiredTime().getTime();
                //当前日期
                Long lNow = System.currentTimeMillis();
                // 判断用户测评有效期
                if (loginUser.getIsEvaluationFlag() == 0) {
                    investInfo.put("riskTested",CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL);
                    investInfo.put("riskLevelDesc","根据监管要求，出借前必须进行风险测评。");
                } else {
                    if(loginUser.getIsEvaluationFlag()==1 && null != loginUser.getEvaluationExpiredTime()){
                        if (lCreate <= lNow) {
                            //已过期需要重新评测
                            //返回错误码
                            investInfo.put("riskTested",CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED);
                            investInfo.put("riskLevelDesc","根据监管要求，测评已过期，出借前必须进行风险测评。");
                        }
                    } else {
                        investInfo.put("riskTested",CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL);
                        investInfo.put("riskLevelDesc","根据监管要求，出借前必须进行风险测评。");
                    }
                }
                //风险类型校验
                if ((CustomConstants.EVALUATION_CHECK.equals(debtEvaluationTypeCheck) && (CustomConstants.TENDER_CHECK_LEVE_HZR.equals(borrowFlag) || CustomConstants.TENDER_CHECK_LEVE_HSB.equals(borrowFlag)))
                        || (CustomConstants.EVALUATION_CHECK.equals(intellectualEveluationTypeCheck) && CustomConstants.TENDER_CHECK_LEVE_HJH.equals(borrowFlag))) {
                    //计划类判断用户类型为稳健型以上才可以投资
                    if (!CommonUtils.checkStandardInvestment(eval_type,borrowFlag,checkLeve)) {
                        //返回类型和限额
                        investInfo.put("projectRevalJudge",true);
                        investInfo.put("projectRiskLevelDesc",CommonUtils.DESC_PROJECT_RISK_LEVEL_DESC.replace("{0}", userEvalationResultCustomize.getEvalType()).replace("{1}",checkLeve));
                        return investInfo;
                    }
                }
                if (revaluation_money == null) {
                    logger.info("=============从redis中获取测评类型和上限金额异常!(没有获取到对应类型的限额数据) eval_type=" + eval_type);
                } else {
                    if ((CustomConstants.EVALUATION_CHECK.equals(deptEvaluationMoneyCheck) && (CustomConstants.TENDER_CHECK_LEVE_HZR.equals(borrowFlag) || CustomConstants.TENDER_CHECK_LEVE_HSB.equals(borrowFlag)))
                            || (CustomConstants.EVALUATION_CHECK.equals(intellectualEvaluationMoneyCheck) && CustomConstants.TENDER_CHECK_LEVE_HJH.equals(borrowFlag))) {
                        investInfo.put("revaluationMoney",StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue()));
                        //金额对比判断（校验金额 大于 设置测评金额）
                        if (new BigDecimal(tender.getMoney()).compareTo(new BigDecimal(revaluation_money)) > 0) {
                            //返回类型和限额
                            investInfo.put("revalJudge",true);
                            investInfo.put("riskLevelDesc","您当前的风险测评类型为 #"+eval_type+"# \n根据监管要求,\n"+eval_type+"用户单笔最高出借限额 #"
                                    +StringUtil.getTenThousandOfANumber(Double.valueOf(revaluation_money).intValue())+"# 。");
                            return investInfo;
                        }
                    }
                }
                if (revaluation_money_principal == null) {
                    logger.info("=============从redis中获取测评类型和上限金额异常!(没有获取到对应类型的限额数据) eval_type=" + eval_type);
                } else {
                    //代收本金限额校验
                    if ((CustomConstants.EVALUATION_CHECK.equals(deptCollectionEvaluationCheck) && (CustomConstants.TENDER_CHECK_LEVE_HZR.equals(borrowFlag) || CustomConstants.TENDER_CHECK_LEVE_HSB.equals(borrowFlag)))
                            || (CustomConstants.EVALUATION_CHECK.equals(intellectualCollectionEvaluationCheck) && CustomConstants.TENDER_CHECK_LEVE_HJH.equals(borrowFlag))) {
                        //获取冻结金额和代收本金
                        CallCenterAccountDetailVO accountDetail = amTradeClient.queryAccountEvalDetail(tender.getUserId());
                        if (accountDetail != null) {
                            BigDecimal planFrost = accountDetail.getPlanFrost();// plan_frost 汇添金计划真实冻结金额
                            BigDecimal bankFrost = accountDetail.getBankFrost();// bank_frost 银行冻结金额
                            BigDecimal bankAwaitCapital = accountDetail.getBankAwaitCapital();// bank_await_capital 银行待收本金
                            BigDecimal account = BigDecimal.ZERO;
                            //加法运算
                            account = account.add(planFrost).add(bankFrost).add(bankAwaitCapital).add(new BigDecimal(tender.getMoney()));
                            //金额对比判断（校验金额 大于 设置测评金额）（代收本金）
                            if (account.compareTo(new BigDecimal(revaluation_money_principal)) > 0) {
                                //返回类型和限额
                                investInfo.put("revalPrincipalJudge",true);
                                investInfo.put("riskLevelDesc","如果您继续出借， ## \n当前累计出借本金将超过 \n您的风险等级 #"+eval_type+"# 对应的限额。");
                                return investInfo;
                            }
                        }
                    }
                }
            } else {
                logger.info("=============该用户测评总结数据为空! userId=" + tender.getUserId());
            }
        }
        return investInfo;
    }

    /**
     * 出借协议列表
     * @param investInfo
     * @param investType
     */
    private void setProtocolsToResultVO(AppInvestInfoResultVO investInfo, String investType){
        List<NewAgreementBean> list=new ArrayList<NewAgreementBean>();
        NewAgreementBean newAgreementBean=new NewAgreementBean("出借协议",  systemConfig.AppFrontHost+"/agreement/AgreementViewList?borrowType="+investType);
        list.add(newAgreementBean);
        investInfo.setProtocols(list);
        investInfo.setProtocolUrlDesc("协议列表");
    }

    /**
     * app端获取出借url
     *
     * @param tender
     * @return
     */
    @Override
    public String getAppTenderUrl(TenderRequest tender,String flag) {
        tender.setPlatform((tender.getPlatform() == null || "".equals(tender.getPlatform()))?"2":tender.getPlatform());
        String borrowType = tender.getBorrowType();
        String requestType = CommonConstant.APP_BANK_REQUEST_TYPE_TENDER;
        String baseUrl = super.getFrontHost(systemConfig,tender.getPlatform());
        String requestMapping = "/public/formsubmit?requestType=";
        String presetProps = tender.getPresetProps();
        String url = "";
        // 计划不需要跳转江西银行, 不能使用前端出借的统一页面，所以针对计划单独跳转前端处理页面
        if (CommonConstant.TENDER_TYPE_HJH.equalsIgnoreCase(borrowType)) {
            logger.info(presetProps);
            // 计划的
            Map<String, Object> result = hjhTenderService.checkPlan(tender);
            if ("getTenderUrl".equals(flag)) {
                if (result != null) {
                    String riskTested = (String) result.get("riskTested");
                    if (CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL.equals(riskTested)) {
                        //未测评新评测
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT);
                    } else if (CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED.equals(riskTested)) {
                        //已过期需要重新评测
                        throw new CheckException(MsgEnum.STATUS_EV000004);
                    } else if (CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL.equals(riskTested)) {
                        //计划类判断用户类型为稳健型以上才可以出借
                        throw new CheckException(MsgEnum.STATUS_EV000007);
                    } else if (CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS.equals(riskTested)) {
                        //金额对比判断（校验金额 大于 设置测评金额）
                        throw new CheckException(MsgEnum.STATUS_EV000005);
                    } else if (CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL.equals(riskTested)) {
                        //金额对比判断（校验金额 大于 设置测评金额 代收本金）
                        throw new CheckException(MsgEnum.STATUS_EV000008);
                    }
                }
            }
            requestType = "9";
            url = baseUrl + "/join/plan?requestType=" + requestType;
            url += "&couponGrantId=" + tender.getCouponGrantId() + "&borrowNid=" + tender.getBorrowNid() + "&platform=" + tender.getPlatform() + "&account=" + tender.getAccount();
            // add by liuyang 神策数据统计 20180824 start
            if (StringUtils.isNotBlank(presetProps)) {
                try {
                    presetProps = URLEncoder.encode(presetProps, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                url += ("&presetProps=") + (presetProps);
            }
            logger.info("出借URL:" + url.toString());
            //  add by liuyang 神策数据统计 20180824 end
            return url;
        }else if (CommonConstant.TENDER_TYPE_CREDIT.equalsIgnoreCase(borrowType)){
            // 债转的
            String creditNid = (StringUtils.isNotBlank(tender.getBorrowNid()) && tender.getBorrowNid().length() >3) ? tender.getBorrowNid().substring(3) : "";
            tender.setCreditNid(creditNid);
            tender.setAssignCapital(tender.getAccount());
            Map<String, Object> result = borrowTenderService.borrowCreditCheck(tender);
            if("getTenderUrl".equals(flag)){
                if(result!=null){
                    String riskTested = (String) result.get("riskTested");
                    if (CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL.equals(riskTested)) {
                        //未测评新评测
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT);
                    } else if(CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED.equals(riskTested)){
                        //已过期需要重新评测
                        throw new CheckException(MsgEnum.STATUS_EV000004);
                    }else if(CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL.equals(riskTested)){
                        //计划类判断用户类型为稳健型以上才可以出借
                        throw new CheckException(MsgEnum.STATUS_EV000007);
                    }else if(CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS.equals(riskTested)){
                        //金额对比判断（校验金额 大于 设置测评金额）
                        throw new CheckException(MsgEnum.STATUS_EV000005);
                    } else if (CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL.equals(riskTested)) {
                        //金额对比判断（校验金额 大于 设置测评金额 代收本金）
                        throw new CheckException(MsgEnum.STATUS_EV000008);
                    }
                }
            }
            requestType = "10";
            url = baseUrl + requestMapping + requestType;
            String couponGrantId = (tender.getCouponGrantId() != null ? tender.getCouponGrantId().toString() : "");
            url += "&couponGrantId="+couponGrantId+"&creditNid="+creditNid+"&platform="+tender.getPlatform()+"&assignCapital="+tender.getAccount();
            logger.info("url:[{}]",url);
            return url;
        }
        // 散标的
        Map<String, Object> result = appTenderCheck(tender);
        if("getTenderUrl".equals(flag)) {
            if (result!= null) {
                String riskTested = (String) result.get("riskTested");
                if (CustomConstants.BANK_TENDER_RETURN_ANSWER_FAIL.equals(riskTested)) {
                    //未测评新评测
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_RISK_ASSESSMENT);
                } else if(CustomConstants.BANK_TENDER_RETURN_ANSWER_EXPIRED.equals(riskTested)){
                    //已过期需要重新评测
                    throw new CheckException(MsgEnum.STATUS_EV000004);
                }else if(CustomConstants.BANK_TENDER_RETURN_CUSTOMER_STANDARD_FAIL.equals(riskTested)){
                    //计划类判断用户类型为稳健型以上才可以出借
                    throw new CheckException(MsgEnum.STATUS_EV000007);
                }else if(CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS.equals(riskTested)){
                    //金额对比判断（校验金额 大于 设置测评金额）
                    throw new CheckException(MsgEnum.STATUS_EV000005);
                } else if (CustomConstants.BANK_TENDER_RETURN_LIMIT_EXCESS_PRINCIPAL.equals(riskTested)) {
                    //金额对比判断（校验金额 大于 设置测评金额 代收本金）
                    throw new CheckException(MsgEnum.STATUS_EV000008);
                }
            }
        }
        url = baseUrl + requestMapping + requestType;
        //String url = super.getFrontHost(systemConfig,tender.getPlatform()) +"/hyjf-app/user/invest/tender?requestType="+CommonConstant.APP_BANK_REQUEST_TYPE_TENDER;
        url += "&couponGrantId="+tender.getCouponGrantId()+"&borrowNid="+tender.getBorrowNid()+"&platform="+tender.getPlatform()+"&account="+tender.getAccount();
        logger.info("url:[{}]",url);
        return url;
    }

    /**
     * 债转出借校验
     * @param tender
     */
    private void appCreditTenderCheck(TenderRequest tender) {

    }

    /**
     * 散标出借校验
     * @param request
     */
    private Map<String, Object> appTenderCheck(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(request.getUserId());
        Integer userId = loginUser.getUserId();
        logger.info("开始检查散标出借参数,userId:{}", userId);
        request.setUser(loginUser);
        request.setUserName(loginUser.getUsername());
        // 设置redis 用户正在出借
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
        logger.info("散标出借校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        Map<String, Object> result = borrowTenderCheck(request,borrow,borrowInfoVO,cuc,account);
        logger.info("所有参数都已检查通过!");
        return result;
    }


    /**
     * 微信端获取出借信息
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
            if (userInfo.getRoleId().intValue() != 1) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
            }
        }

        RightBorrowVO borrow = amTradeClient.getRightBorrowByNid(tender.getBorrowNid());
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

        // 产品加息
        if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
            vo.setBorrowApr(borrow.getBorrowApr() + "% + "
                    + borrowInfo.getBorrowExtraYield() + "%");
        }

        vo.setBorrowNid(tender.getBorrowNid());

        //设置是否有可用优惠券
        MyCouponListRequest request = new MyCouponListRequest();
        request.setBorrowNid(tender.getBorrowNid());
        request.setUserId(String.valueOf(tender.getUserId()));
        request.setPlatform(tender.getPlatform());
        request.setMoney(tender.getMoney());
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
            earnings = earnings.add(couponInterest);
            vo.setProspectiveEarnings(CommonUtils.formatAmount(null,earnings ) + "元");

        }

        // 产品加息预期收益
        if (Validator.isIncrease(borrow.getIncreaseInterestFlag(), borrowInfo.getBorrowExtraYield())) {
            BigDecimal incEarnings = increaseCalculate(borrow.getBorrowPeriod(), borrow.getBorrowStyle(), money, borrowInfo.getBorrowExtraYield());
            earnings = incEarnings.add(earnings);
            vo.setProspectiveEarnings(CommonUtils.formatAmount(null, earnings) + "元");
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
        // add by liuyang 神策数据统计 20180820 start
        // 获取标的还款方式
        BorrowStyleVO projectBorrowStyle = this.amTradeClient.getBorrowStyle(borrow.getBorrowStyle());
        if (projectBorrowStyle != null) {
            vo.setBorrowStyleName(StringUtils.isBlank(projectBorrowStyle.getName()) ? "" : projectBorrowStyle.getName());
        } else {
            vo.setBorrowStyleName("");
        }
        BorrowInfoVO borrowInfoVO = this.getBorrowInfoByNid(borrow.getBorrowNid());
        // 项目名称
        vo.setProjectName(borrowInfoVO.getProjectName());
        // 借款期限
        vo.setBorrowPeriod(borrow.getBorrowPeriod());
        if ("endday".equals(borrow.getBorrowStyle())) {
            vo.setDurationUnit("天");
        } else {
            vo.setDurationUnit("月");
        }
        // add by liuyang 神策数据统计 20180820 end
        return vo;
    }

    /**
     * web散标出借校验
     * @param request
     * @param borrow
     * @param borrowInfoVO
     * @param cuc
     * @param account
     * @return
     */
    @Override
    public Map<String, Object> borrowTenderCheck(TenderRequest request,BorrowAndInfoVO borrow, BorrowInfoVO borrowInfoVO ,CouponUserVO cuc ,BankOpenAccountVO account) {
        WebResult<Map<String, Object>> resultMap = new  WebResult<Map<String, Object>>();
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
        // 看标的是否关联计划 ，防止别有用心的guy从散标列表出借汇计划标的
        if(borrow.getIsShow() != null && borrow.getIsShow().intValue() ==1){
            if(StringUtils.isNotBlank(borrow.getPlanNid())){
                // 该标的绑定了计划
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_BIND_PLAN_ERROR);
            }
        }
        borrow.setTenderAccountMin(borrowInfoVO.getTenderAccountMin());
        borrow.setTenderAccountMax(borrowInfoVO.getTenderAccountMax());
        borrow.setCanTransactionAndroid(borrowInfoVO.getCanTransactionAndroid());
        borrow.setCanTransactionIos(borrowInfoVO.getCanTransactionIos());
        borrow.setCanTransactionPc(borrowInfoVO.getCanTransactionPc());
        borrow.setCanTransactionWei(borrowInfoVO.getCanTransactionWei());
        borrow.setBorrowIncreaseMoney(borrowInfoVO.getBorrowIncreaseMoney());
        logger.info("散标出借校验开始userId:{},planNid:{},ip:{},平台{},优惠券:{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform(), request.getCouponGrantId());
        UserVO user = amUserClient.findUserById(request.getUserId());
        request.setUser(user);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        // 检查用户状态  角色  授权状态等  是否允许出借
        checkUser(user, userInfo);
        //校验用户测评
        Map<String, Object> resultEval = hjhTenderService.checkEvaluationTypeMoney(request,borrow.getInvestLevel(),CustomConstants.TENDER_CHECK_LEVE_HSB);
        // 检查江西银行账户
        if(account ==null){
            account = amUserClient.selectBankAccountById(userId);
        }
        if (account == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(account.getAccount())) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 查询用户账户表-出借账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 出借检查参数
        this.checkParam(request, borrow, account, userInfo ,borrowInfoVO);
        // 查询选择的优惠券
        if (request.getCouponGrantId() != null && request.getCouponGrantId() > 0) {
            cuc = amTradeClient.getCouponUser(request.getCouponGrantId(), userId);
        }
        // 检查金额
        this.checkTenderMoney(request, borrow, cuc, tenderAccount );
        logger.info("所有参数都已检查通过!");
        return resultEval;
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
     * @param money  出借本金
     * @param borrow
     * @return
     */
    private BigDecimal calculateCouponTenderInterest(CouponUserVO couponConfig, String money, RightBorrowVO borrow) {
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

        // 出借金额
        BigDecimal accountDecimal = null;
        if (couponType == 1) {
            // 体验金 出借资金=体验金面值
            accountDecimal = couponQuota;
        } else if (couponType == 2) {
            // 加息券 出借资金=真实出借资金
            accountDecimal = new BigDecimal(money);
            borrowApr = couponQuota;
        } else if (couponType == 3) {
            // 代金券 出借资金=体验金面值
            accountDecimal = couponQuota;
        }
        switch (borrowStyle) {
            case CalculatesUtil.STYLE_END:// 还款方式为”按月计息，到期还本还息“: 历史回报=出借金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getMonthInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDDAY:// 还款方式为”按天计息，到期还本还息“: 历史回报=出借金额*年化收益÷360*天数；
                // 计算历史回报
                earnings = DuePrincipalAndInterestUtils.getDayInterest(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_ENDMONTH:// 还款方式为”先息后本“: 历史回报=出借金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = BeforeInterestAfterPrincipalUtils.getInterestCount(accountDecimal, borrowApr.divide(new BigDecimal("100")), borrowPeriod, borrowPeriod).setScale(2, BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_MONTH:// 还款方式为”等额本息“: 历史回报=出借金额*年化收益÷12*月数；
                // 计算历史回报
                earnings = AverageCapitalPlusInterestUtils.getInterestCount(accountDecimal,borrowApr.divide(new BigDecimal("100")), borrowPeriod).setScale(2,BigDecimal.ROUND_DOWN);
                break;
            case CalculatesUtil.STYLE_PRINCIPAL: //还款方式为“等额本金” 历史回报=出借金额*年化收益÷12*月数；
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
     * 出借异步逻辑
     *
     * @param borrow
     * @param bean
     * @param couponGrantId
     */
    private void userBorrowTender(BorrowAndInfoVO borrow, BankCallBean bean, String couponGrantId) {
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 借款金额
        String txAmount = bean.getTxAmount().replaceAll(",","");
        // 项目编号
        String borrowNid = borrow.getBorrowNid();
        boolean checkTender = RedisUtils.tranactionSet(RedisConstants.TENDER_ORDERID + bean.getLogOrderId(), 20);
        //同步/异步 优先执行完毕
        if (!checkTender) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_HANDING);
        }
        BorrowProjectTypeVO borrowProjectType = this.getProjectType(String.valueOf(borrow.getProjectType()));
        if (borrowProjectType.getInvestUserType().equals(1)) {
            boolean isNew = this.checkIsNewUserCanInvest(userId);
            // 该项目只能新手出借
            if (!isNew) {
                logger.error("该项目只能新手出借  {} ",JSONObject.toJSONString(bean));
                try {
                    boolean flag = bidCancel(userId, borrow.getBorrowNid(), bean.getOrderId(), txAmount);
                    //sendBidCancelMessage(userId);
                    if (!flag) {
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                    }
                } catch (Exception ee) {
                    logger.error("投标失败,请联系客服人员!userid:{} borrownid:{}  ordid:{}",userId, borrow.getBorrowNid(), bean.getOrderId());
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                }
                throw new CheckException(MsgEnum.ERR_TRADE_NEW_USER);
            }
        }
        BigDecimal amount = new BigDecimal(txAmount);
        // 出借金额大于0时候才执行
        if(amount.compareTo(BigDecimal.ZERO)==1){
            // 操作数据库表
            try{
                // redis扣减
                redisTender(userId, borrowNid, txAmount);
                logger.info("userId:{}   borrowNid:{}   redis减扣成功，开始进行投资",userId,borrowNid);
                this.borrowTender(borrow, bean);
            }catch (Exception e){
                // 回滚redis
                logger.error("标的出借异常  开始回滚redis  userId:{}   borrowNid:{}",userId, borrowNid);
                redisRecover(borrowNid,userId,txAmount);
                logger.info("标的出借异常  结束回滚redis ");
                // 出借失败,出借撤销
                try {
                    boolean flag = bidCancel(userId, borrow.getBorrowNid(), bean.getOrderId(), txAmount);
                    //sendBidCancelMessage(userId);
                    if (!flag) {
                        throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                    }
                } catch (Exception ee) {
                    logger.error("投标失败,请联系客服人员!userid:{} borrownid:{}  ordid:{}",userId, borrow.getBorrowNid(), bean.getOrderId());
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                }
                throw e;
            }
        }
        logger.info("用户:{},投标成功，金额：{}，优惠券开始调用ID：{}" ,userId, txAmount,couponGrantId);
        // 如果用了优惠券
        couponTender(bean.getLogIp(),bean.getTxAmount(),bean.getLogClient()+"",bean.getLogOrderId(),bean.getLogUserName(),
                couponGrantId, userId, borrowNid);
    }

    private void sendBidCancelMessage(Integer userId) throws MQException {
        // 替换参数
        Map<String, String> replaceMap = new HashMap<String, String>();
        UserInfoVO info = amUserClient.findUsersInfoById(userId);
        replaceMap.put("val_name", info.getTruename().substring(0, 1));
        replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
        replaceMap.put("val_date", GetDateUtils.formatDate(new Date()));
        SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_BIDCANCEL,
                CustomConstants.CHANNEL_TYPE_NORMAL);
        logger.info("smsMessage：{}" ,JSONObject.toJSON(smsMessage));
        AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_BIDCANCEL);
        logger.info("appMsMessage：{}" ,JSONObject.toJSON(appMsMessage));
        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                UUID.randomUUID().toString(), smsMessage));
        commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
                UUID.randomUUID().toString(), appMsMessage));
    }

    /**
     * 优惠券出借
     * @param ip
     * @param money
     * @param client
     * @param ordid
     * @param userName
     * @param couponGrantId
     * @param userId
     * @param borrowNid
     */
    private void couponTender(String ip,String money,String client,String ordid,String userName, String couponGrantId, Integer userId, String borrowNid) {
        if (StringUtils.isNotEmpty(couponGrantId) && !"0".equals(couponGrantId) && !"-1".equals(couponGrantId)) {
            // 开始使用优惠券
            Map<String, String> params = new HashMap<String, String>();
            params.put("mqMsgId", GetCode.getRandomCode(10));
            // 真实出借金额
            params.put("money", money);
            // 借款项目编号
            params.put("borrowNid", borrowNid);
            // 平台
            params.put("platform", client);
            // 优惠券id
            params.put("couponGrantId", couponGrantId);
            // ip
            params.put("ip", ip);
            // 真实出借订单号
            params.put("ordId", ordid);
            // 用户编号
            params.put("userId", userId+"");
            params.put("userName", userName);
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.HZT_COUPON_TENDER_TOPIC, UUID.randomUUID().toString(), params));
            } catch (MQException e) {
                logger.error("使用优惠券异常,userId:{},ordId:{},couponGrantId:{},borrowNid:{}",userId,ordid,couponGrantId,borrowNid);
                e.printStackTrace();
            }
        }
    }

    /**
     * 出借撤销
     * @param investUserId
     * @param productId
     * @param orgOrderId
     * @param txAmount
     * @return
     * @throws Exception
     */
    public boolean bidCancel(int investUserId, String productId, String orgOrderId, String txAmount) throws Exception {
        // 出借人的账户信息
        AccountVO outCust = this.getAccountByUserId(investUserId);
        if (outCust == null) {
            throw new Exception("出借人未开户。[出借人ID：" + investUserId + "]，" + "[出借订单号：" + orgOrderId + "]");
        }
        String tenderAccountId = outCust.getAccountId();
        logger.info("开始调用出借撤销接口  investUserId:{}  tenderAccountId:{}  productId:{}  orgOrderId:{}  txAmount:{} ",investUserId, tenderAccountId, productId, orgOrderId, txAmount);
        // 调用交易查询接口(出借撤销)
        BankCallBean queryTransStatBean = bankBidCancel(investUserId, tenderAccountId, productId, orgOrderId, txAmount);
        if (queryTransStatBean == null) {
            logger.error("调用投标申请撤销失败。" + ",[出借订单号：" + orgOrderId + "]");
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_HANDING);
        } else {
            String queryRespCode = queryTransStatBean.getRetCode();
            logger.info("出借失败交易接口查询接口返回码：" + queryRespCode+ ",[出借订单号：" + orgOrderId + "]");
            // 调用接口失败时(000以外)
            if (!BankCallConstant.RESPCODE_SUCCESS.equals(queryRespCode)) {
                String message = queryTransStatBean.getRetMsg();
                logger.error(this.getClass().getName(), "bidCancel", "调用交易查询接口(解冻)失败。" + message + ",[出借订单号：" + orgOrderId + "]", null);
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
     * 投标失败后,调用出借撤销接口
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
        JedisPool poolNew = RedisUtils.getPool();
        Jedis jedis = poolNew.getResource();
        BigDecimal accountBigDecimal = new BigDecimal(account);
        try{
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
        }catch(Exception e){
            logger.info("抛出异常:[{}]",e);
        }finally {
            //返还
            RedisUtils.returnResource(poolNew,jedis);
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
        bean.setLogUserName(user.getUsername());
        Integer attribute = null;
        if (userInfo != null) {
            // 获取出借用户的用户属性
            attribute = userInfo.getAttribute();
            if (attribute != null) {
                // 出借人用户属性
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

        // 单笔出借的融资服务费
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
        //出借授权码 出借结果授权码
        if (StringUtils.isNotBlank(bean.getAuthCode())) {
            tenderBg.setAuthCode(bean.getAuthCode());
        }
        // 开始调用原子层操作主表
        logger.info("开始操作原子层主表");
        boolean insertFlag = amTradeClient.borrowTender(tenderBg);
        logger.info("操作原子层主表结束 结果 {} ",insertFlag);
        if (insertFlag) {
            updateUtm(Integer.parseInt(bean.getLogUserId()), tenderBg.getAccountDecimal(), GetDate.getNowTime10(), borrow);
            // 网站累计出借追加
            // 出借、收益统计表
            JSONObject params = new JSONObject();
            params.put("tenderSum", accountDecimal);
            params.put("nowTime", GetDate.getDate(GetDate.getNowTime10()));
            // 出借修改mongodb运营数据
            params.put("type", 1);
            params.put("money", accountDecimal);
            try {
                // 网站累计投资追加
                // 投资修改mongodb运营数据
                commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), params));
                // 满标发短信在原子层
            } catch (MQException e) {
                e.printStackTrace();
            }

            // 查询出借来源
            List<BorrowTenderVO> list = amTradeClient.getBorrowTenderListByNid(bean.getOrderId());
            if (!CollectionUtils.isEmpty(list)) {
                BorrowTenderVO vo = list.get(0);
                if (CustomConstants.WRB_CHANNEL_CODE.equals(vo.getTenderFrom())) {
                    // 同步回调通知
                    logger.info("风车理财出借回调,订单Id :{}", bean.getOrderId());
                    this.notifyToWrb(user.getUserId(), bean.getOrderId());
                }
            }
            // 投标成功后,发送神策数据统计MQ
            // add by liuyang 神策数据统计 20180823 start
            try {
                // 投标成功后,参与纳觅返现
                sendReturnCashActivity(Integer.parseInt(bean.getLogUserId()),bean.getLogOrderId(),accountDecimal,borrow.getProjectType());
                // 投标成功后,发送神策数据统计MQ
                SensorsDataBean sensorsDataBean = new SensorsDataBean();
                sensorsDataBean.setUserId(Integer.parseInt(bean.getLogUserId()));
                sensorsDataBean.setOrderId(bean.getLogOrderId());
                sensorsDataBean.setEventCode("submit_tender");
                this.sendSensorsDataMQ(sensorsDataBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            // add by liuyang 神策数据统计 20180823 end
        }else{
            logger.error("出借失败  对象:{}",JSONObject.toJSONString(tenderBg));
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
            JedisPool poolNew = RedisUtils.getPool();
            Jedis jedis = poolNew.getResource();
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
                RedisUtils.returnResource(poolNew, jedis);
            }

        } else {
            // 您来晚了，下次再来抢吧！
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE);
        }
    }

    private void updateUtm(Integer userId, BigDecimal accountDecimal, Integer nowTime, BorrowAndInfoVO borrow) {
        //更新汇计划列表成功的前提下
        // 更新渠道统计用户累计出借
        // 出借人信息
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("userId", userId);
        // 认购本金
        params.put("accountDecimal", accountDecimal);
        // 出借时间
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
            investProjectPeriod = borrow.getBorrowPeriod() + "个月";
        }
        params.put("investProjectPeriod", investProjectPeriod);
        params.put("investFlag", checkAppUtmInvestFlag(userId));
        //压入消息队列
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                    MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
            logger.error("渠道统计用户累计出借推送消息队列失败！！！", e);
        }

        /*(6)更新  渠道统计用户累计出借  和  huiyingdai_utm_reg的首投信息 结束*/
    }

    /**
     * 投标成功后,发送神策数据统计MQ
     *
     * @param sensorsDataBean
     * @throws MQException
     */
    private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_HZT_INVEST_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
    }
    /**
     * 纳觅返现活动
     * @param userId
     * @param order
     */
    private void sendReturnCashActivity(Integer userId,String order,BigDecimal investMoney,int projectType) throws MQException {
        // 加入到消息队列
        JSONObject params = new JSONObject();
        params.put("userId", userId);
        params.put("orderId", order);
        params.put("investMoney", investMoney.toString());
        //来源,1=新手标，2=散标，3=汇计划
        Integer productType = 2;
        //4 新手标
        if(4 == projectType){
            productType = 1;
        }
        params.put("productType", productType);
        commonProducer.messageSend(new MessageContent(MQConstant.RETURN_CASH_ACTIVITY_SAVE_TOPIC, UUID.randomUUID().toString(), params));
    }
}
