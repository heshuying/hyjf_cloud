/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.cs.trade.service.invest.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.fdd.FddGenerateContractBean;
import com.hyjf.am.response.config.DebtConfigResponse;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.admin.UserOperationLogEntityVO;
import com.hyjf.am.vo.config.DebtConfigVO;
import com.hyjf.am.vo.datacollect.AccountWebListVO;
import com.hyjf.am.vo.datacollect.AppUtmRegVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.borrow.*;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.UserOperationLogConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.util.calculate.AccountManagementFeeUtils;
import com.hyjf.common.util.calculate.BeforeInterestAfterPrincipalUtils;
import com.hyjf.common.util.calculate.CalculatesUtil;
import com.hyjf.common.util.calculate.DuePrincipalAndInterestUtils;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.app.AppInvestInfoResultVO;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.hjh.HjhTenderService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.invest.BorrowCreditTenderService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @Description 出借接口
 * @Author sunss
 * @Date 2018/6/24 14:30
 */
@Service
public class BorrowCreditTenderServiceImpl extends BaseTradeServiceImpl implements BorrowCreditTenderService {
    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private AmTradeClient amTradeClient;
    @Autowired
    SystemConfig systemConfig;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    private AuthService authService;
    @Autowired
    private HjhTenderService hjhTenderService;

    private static String regex = "^[-+]?(([0-9]+)(([0-9]+))?|(([0-9]+))?)$";
    private static DecimalFormat DF_COM_VIEW = new DecimalFormat("######0.00");
    private static String oldOrNewDate = "2016-12-27 20:00:00";
    private static DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");

    /**
     * 债转出借
     *
     * @param request
     * @return
     */
    @Override
    @HystrixCommand(commandKey = "债转出借(app/web)-borrowCreditTender",fallbackMethod = "fallBackCredit",ignoreExceptions = CheckException.class,commandProperties = {
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
    public WebResult<Map<String, Object>> borrowCreditTender(TenderRequest request)  {
        UserVO loginUser = amUserClient.findUserById(Integer.valueOf(request.getUserId()));

        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        // 检查请求参数是否正确
        this.checkRequest(request);
        // 获取债转数据
        BorrowCreditVO borrowCredit = amTradeClient.getBorrowCreditByCreditNid(request.getCreditNid());
        if(borrowCredit==null){
            // 获取债转数据错误
            throw new CheckException(MsgEnum.ERROR_CREDIT_NOT_EXIST);
        }
        logger.info("债转出借校验开始   userId:{},credNid:{},ip:{},平台{}", userId, request.getCreditNid(), request.getIp(), request.getPlatform());
        String hborrowNid = borrowCredit.getBidNid();
        BorrowAndInfoVO borrow = amTradeClient.getBorrowByNid(hborrowNid);
        UserVO user = amUserClient.findUserById(userId);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(userId);
        // 检查用户状态  角色  授权状态等  是否允许出借
        this.checkUser(user,userInfo,bankOpenAccount,borrowCredit);
        //保存用户操作日志
        boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrow.getBorrowStyle()) || CustomConstants.BORROW_STYLE_MONTH.equals(borrow.getBorrowStyle())
                || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrow.getBorrowStyle())|| CustomConstants.BORROW_STYLE_END.equals(borrow.getBorrowStyle());

        String dayOrMonth ="";
        if(isMonth){//月标
            dayOrMonth = borrow.getBorrowPeriod() + "个月债转";
        }else{
            dayOrMonth = borrow.getBorrowPeriod() + "天债转";
        }
        UserOperationLogEntityVO userOperationLogEntity = new UserOperationLogEntityVO();
        userOperationLogEntity.setOperationType(UserOperationLogConstant.USER_OPERATION_LOG_TYPE4);
        userOperationLogEntity.setIp(request.getIp());
        userOperationLogEntity.setPlatform(Integer.valueOf(request.getPlatform()));
        userOperationLogEntity.setRemark(dayOrMonth);
        userOperationLogEntity.setOperationTime(new Date());
        userOperationLogEntity.setUserName(user.getUsername());
        userOperationLogEntity.setUserRole(String.valueOf(userInfo.getRoleId()));
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.USER_OPERATION_LOG_TOPIC, UUID.randomUUID().toString(), userOperationLogEntity));
        } catch (MQException e) {
            logger.error("保存用户日志失败", e);
        }
        // 查询用户账户表-出借账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 前端Web页面出借可债转输入出借金额后收益提示 用户未登录 (包含查询条件)
        TenderToCreditAssignCustomizeVO creditAssign = this.amTradeClient.getInterestInfo(request.getCreditNid(), request.getAssignCapital(),userId);
        logger.info("creditAssign {}", JSONObject.toJSONString(creditAssign));
        // 检查金额
        this.checkTenderMoney(request, tenderAccount,creditAssign);
        logger.info("债转出借校验通过始   userId:{},credNid:{},ip:{},平台{}", userId, request.getCreditNid(), request.getIp(), request.getPlatform());
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
        logger.info("债转出借跳转到银行完成   userId:{},credNid:{},ip:{},平台{}", userId, request.getBorrowNid(), request.getIp(), request.getPlatform());
        return result;
    }

    /**
     *  债转出借(app/web) fallBackCredit 方法
     * @param request
     * @return
     */
    public WebResult<Map<String, Object>> fallBackCredit(TenderRequest request){
        logger.info("==================已进入 债转出借(app/web) fallBackCredit 方法================");
        throw new CheckException(MsgEnum.STATUS_CE999999);
    }

    /**
     * 债转出借异步
     *
     * @param bean
     * @return
     */
    @Override
    public BankCallResult borrowCreditTenderBgReturn(BankCallBean bean) {
        BankCallResult resultBean = new BankCallResult();
        // 用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        String logOrderId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId();
        String retCode = StringUtils.isNotBlank(bean.getRetCode()) ? bean.getRetCode() : "";
        // 更新相应的债转承接log表 修改错误信息  等
        if (!BankCallConstant.RESPCODE_SUCCESS.equals(retCode)) {
            logger.info("债转承接失败,userId:{},订单号:{},平台:{},返回码{}",bean.getLogUserId(),bean.getLogOrderId(),bean.getLogClient(),bean.getRetCode());
            // 债转失败了  更新错误信息
            String retMsg = bean.getRetMsg();
            BankReturnCodeConfigVO retMsgVo = amConfigClient.getBankReturnCodeConfig(retCode);
            if (retMsgVo != null) {
                retMsg = retMsgVo.getErrorMsg();
            }
            amTradeClient.updateCreditTenderResult(bean.getLogOrderId(),bean.getLogUserId(),retCode,retMsg);
            resultBean.setStatus(true);
            return resultBean;
        }
        logger.info("开始进行出借异步逻辑 返回承接成功,开始查询承接状态.userId:{},订单号:{},平台:{},返回码{}",bean.getLogUserId(),bean.getLogOrderId(),bean.getLogClient(),bean.getRetCode());
        // 调用相应的查询接口查询此笔承接的相应的承接状态
        BankCallBean tenderQueryBean = this.creditInvestQuery(logOrderId, userId ,bean.getAccountId());
        logger.info("查询债转状态完成.userId:{},订单号:{},平台:{},返回码{}",bean.getLogUserId(),bean.getLogOrderId(),bean.getLogClient(),tenderQueryBean==null?"空":tenderQueryBean.getRetCode());
        if (Validator.isNotNull(tenderQueryBean)) {
            // bean实体转化
            tenderQueryBean.convert();
            // 获取债转查询返回码
            String queryRetCode = StringUtils.isNotBlank(tenderQueryBean.getRetCode()) ? tenderQueryBean.getRetCode() : "";
            if (BankCallConstant.RESPCODE_SUCCESS.equals(queryRetCode)) {
                // 承接成功
                // 查询相应的债转承接记录
                List<CreditTenderLogVO> creditTenderLog = this.amTradeClient.getCreditTenderLogs(logOrderId,userId);
                logger.info("查询相应的债转承接记录.{}",creditTenderLog);
                // 如果已经查询到相应的债转承接log表
                if (Validator.isNotNull(creditTenderLog)) {
                    // 此次查询的授权码
                    String authCode = tenderQueryBean.getAuthCode();
                    logger.info("此次查询的授权码.{}",authCode);
                    if (StringUtils.isNotBlank(authCode)) {
                        // 更新债转交易成功后的相关信息
                        logger.info("开始更新债转信息-----");
                        boolean tenderFlag = this.updateTenderCreditInfo(logOrderId, userId, authCode,creditTenderLog.get(0));
                        logger.info("更新债转信息完毕-----{}",tenderFlag);
                        if(!tenderFlag){
                            // 更新债转数据异常
                            throw new CheckException(MsgEnum.ERROR_CREDIT_UPDATE_ERROR);
                        }
                    }else{
                        // 未查询到债转授权码
                        throw new CheckException(MsgEnum.ERROR_CREDIT_AUTH_CODE_ERROR);
                    }
                }else{
                    // 未查询到债转承接记录
                    throw new CheckException(MsgEnum.ERROR_CREDIT_FIND_LOG_ERROR);
                }
            }else{
                // 修改失败原因
                String retMsg = tenderQueryBean.getRetMsg();
                BankReturnCodeConfigVO retMsgVo = amConfigClient.getBankReturnCodeConfig(retCode);
                if (retMsgVo != null) {
                    retMsg = retMsgVo.getErrorMsg();
                }
                amTradeClient.updateCreditTenderResult(bean.getLogOrderId(),bean.getLogUserId(),retCode,retMsg);
                // 债转承接状态异常
                throw new CheckException(MsgEnum.ERROR_CREDIT_QUERY_ERROR);
            }
        }else{
            // 查询债转状态异常
            throw new CheckException(MsgEnum.ERROR_CREDIT_QUERY_ERROR);
        }
        logger.info("出借异步处理完毕,userId:{},订单号:{},平台:{}",bean.getLogUserId(),bean.getLogOrderId(),bean.getLogClient());
        resultBean.setStatus(true);
        return resultBean;
    }

    /**
     * 债转出借获取出借失败结果
     *
     * @param userId
     * @param logOrdId
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getFaileResult(Integer userId, String logOrdId) {
        String errorMsg = amTradeClient.getFailResult(logOrdId,userId);
        Map<String, Object> data = new HashedMap();
        data.put("errorMsg",errorMsg==null?"请联系客服":errorMsg);
        WebResult<Map<String, Object>> result = new WebResult();
        result.setData(data);
        return result;
    }

    /**
     * 获取债转成功的信息
     *
     * @param userId
     * @param logOrdId
     * @return
     */
    @Override
    public WebResult<Map<String, Object>> getSuccessResult(Integer userId, String logOrdId) {
        CreditTenderVO bean = amTradeClient.getCreditTenderByUserIdOrdId(logOrdId,userId);
        Map<String, Object> data = new HashedMap();
        if(bean!=null){
            // 出借金额
            data.put("assignCapital",bean.getAssignCapital());
            // 历史回报
            data.put("assignInterest",bean.getAssignInterest());
            WebResult<Map<String, Object>> result = new WebResult();
            result.setData(data);
            return result;
        }else{
            throw  new CheckException(MsgEnum.ERR_AMT_TENDER_FIND_CREDIT_SUCCESS_MESS_ERROR);
        }

    }

    /**
     * 前端Web页面出借可债转输入出借金额后获取收益
     *
     * @param userId
     * @param creditNid
     * @param assignCapital
     * @return
     */
    @Override
    public JSONObject getInterestInfo(int userId, String creditNid, String assignCapital) {
        TenderToCreditAssignCustomizeVO creditAssign = this.amTradeClient.getInterestInfo(creditNid, assignCapital,userId);
        JSONObject ret = new JSONObject();
        if (Validator.isNotNull(creditAssign)) {
            creditAssign.setMoney(DF_FOR_VIEW.format(new BigDecimal(assignCapital).setScale(2, BigDecimal.ROUND_DOWN)));
            ret.put("creditAssign", creditAssign);
            ret.put(CustomConstants.RESULT_FLAG, CustomConstants.RESULT_SUCCESS);
        } else {
            ret.put(CustomConstants.RESULT_FLAG, CustomConstants.RESULT_FAIL);
            ret.put(CustomConstants.MSG, "系统异常,请稍后再试!");
        }
        return ret;
    }

    /**
     * App页面出借可债转输入出借金额后获取收益
     *
     * @param tender
     * @param creditNid
     * @param assignCapital
     * @return
     */
    @Override
    public AppInvestInfoResultVO getInterestInfoApp(TenderRequest tender, String creditNid, String assignCapital) {
        tender.setAssignCapital(assignCapital);
        String money = assignCapital;
        String investType = tender.getBorrowNid().substring(0, 3);
        AppInvestInfoResultVO result = new AppInvestInfoResultVO();
        // 查询债转信息
        TenderToCreditAssignCustomizeVO creditAssign = this.amTradeClient.getInterestInfo(creditNid, assignCapital,tender.getUserId());
        if (money == null || "".equals(money) || (new BigDecimal(money).compareTo(BigDecimal.ZERO) == 0)) {
            money = "0";
            result.setRealAmount("¥0.00");
            result.setButtonWord("确认");
        } else {
            result.setRealAmount("¥" + CommonUtils.formatAmount(null, money));
            result.setButtonWord("确认出借"+CommonUtils.formatAmount(null, money)+"元");
        }
        result.setBorrowNid(creditNid);
        result.setBorrowType(investType);
        result.setProspectiveEarnings("");
        result.setInterest("");
        result.setStandardValues("0");

        // add by liuyang  神策数据统计 20180820 start
        // 原标项目编号
        String bidNid = creditAssign.getBorrowNid();
        // 根据债转编号查询转让记录
        BorrowCreditVO borrowCreditVO = this.amTradeClient.getBorrowCreditByCreditNid(creditNid);
        if(borrowCreditVO == null){
            logger.error("根据债转编号查询转让记录失败,转让编号:["+creditNid + "].");
            return result;
        }
        // 根据原标标号取借款信息
        RightBorrowVO borrow = this.amTradeClient.getRightBorrowByNid(bidNid);
        if(borrow==null){
            logger.error("根据标的号获取标的信息失败,标的编号:["+bidNid+"].");
            return result;
        }
        BorrowInfoVO borrowInfoVO = this.amTradeClient.getBorrowInfoByNid(bidNid);
        if (borrowInfoVO == null){
            logger.error("根据标的号查询标的详情信息失败,标的编号:["+bidNid+"].");
            return result;
        }
        if (borrow != null) {
            BorrowStyleVO projectBorrowStyle = this.amTradeClient.getBorrowStyle(borrow.getBorrowStyle());
            if (projectBorrowStyle != null) {
                result.setBorrowStyleName(StringUtils.isBlank(projectBorrowStyle.getName()) ? "" : projectBorrowStyle.getName());
            } else {
                result.setBorrowStyleName("");
            }
        }

        // 项目名称
        result.setProjectName(borrowInfoVO.getProjectName());
        // 借款期限
        result.setBorrowPeriod(borrow.getBorrowPeriod());
        if ("endday".equals(borrow.getBorrowStyle())) {
            result.setDurationUnit("天");
        } else {
            result.setDurationUnit("月");
        }
        // 债转期限
        result.setCreditPeriod(borrowCreditVO.getCreditTerm());
        // 期限单位
        result.setCreditDurationUnit("天");
        // add by liuyang  神策数据统计 20180820 end
        if(creditAssign!=null){
            AccountVO account = this.getAccountByUserId(tender.getUserId());
            BigDecimal balance = account.getBankBalance();
            result.setBalance(CommonUtils.formatAmount(null, balance));
            result.setStatus(CustomConstants.APP_STATUS_SUCCESS);
            result.setStatusDesc(CustomConstants.APP_STATUS_DESC_SUCCESS);
            // 待承接垫付利息
            BigDecimal interestAdvanceWait = new BigDecimal(creditAssign.getAssignInterestAdvance().replaceAll(",",""));
            // 待承接金额
            BigDecimal capitalWait = new BigDecimal(creditAssign.getCreditCapital().replaceAll(",",""));
            result.setBorrowAccountWait(CommonUtils.formatAmount(null, creditAssign.getAssignCapital()));
            result.setCouponDescribe("");
            result.setCouponId("");
            result.setCouponQuota("");
            result.setEndTime("");
            result.setInitMoney("");
            result.setIsThereCoupon("0");
            result.setCouponName("");
            result.setCouponType("");
            result.setCouponAvailableCount("");
            result.setAssignPay("");
            result.setBorrowApr(creditAssign.getCreditDiscount()+"%");
            if (StringUtils.isNotEmpty(money) && !"0".equals(money)) {
                // 实际支付金额
                result.setRealAmount("¥" + creditAssign.getAssignPay());
                //result.setRealAmount("实际支付金额:" + creditAssign.getAssignPay());
                // 历史回报
                result.setProspectiveEarnings(creditAssign.getAssignInterest()+"元");
                //BigDecimal assignInterest = new BigDecimal(bean.getAssignInterest()).add(new BigDecimal(money));
                //result.setProspectiveEarnings(assignInterest+"元");
                //备注
                result.setDesc("折让率: "+creditAssign.getCreditDiscount()+"%      历史回报: " + creditAssign.getAssignInterest() +"元");
                //折让率
                result.setDesc0("折让率: "+creditAssign.getCreditDiscount()+"%");
                //历史回报
                result.setDesc1("历史回报: "+creditAssign.getAssignInterest()+"元");
                // 实际支付金额
                result.setAssignPay(creditAssign.getAssignPay());
                // 认购本金
                result.setAssignCapital(DF_FOR_VIEW.format(new BigDecimal(creditAssign.getAssignCapital())) + "元");
                // 垫付利息
                result.setAssignInterestAdvance(creditAssign.getAssignInterestAdvance() + "元");
                // 垫付利息u
                result.setPaymentOfInterest(creditAssign.getAssignInterestAdvance() + "元");
                // 实际支付计算式
                result.setAssignPayText(creditAssign.getAssignPayText());
                // 折价率
                result.setCreditDiscount(creditAssign.getCreditDiscount() + "%");
                //按钮上的文字
                result.setButtonWord("实际支付"+creditAssign.getAssignPay()+"元");
            } else {
                result.setCreditDiscount("");
                // 认购本金
                result.setAssignCapital("0.00" + "元");
                // 垫付利息
                result.setAssignInterestAdvance("0.00" + "元");
                // 垫付利息
                result.setPaymentOfInterest("0.00" + "元");
                //备注
                result.setDesc("折让率: "+creditAssign.getCreditDiscount()+"%      历史回报: 0.00元");
                //折让率
                result.setDesc0("折让率: "+creditAssign.getCreditDiscount()+"%");
                //历史回报
                result.setDesc1("历史回报: "+"0.00元");
                // 实际支付计算式
                result.setAssignPayText("");
                // 实际支付金额
                result.setAssignPay("0.00");
                // 历史回报
                result.setProspectiveEarnings("0.00元");
            }
            result.setInvestmentDescription("承接金额应大于1元");
            result.setAnnotation("注: 实际支付金额=认购本金（1-折让率）+垫付利息");
            // 折比率
            BigDecimal creditDiscount = new BigDecimal(1).subtract(new BigDecimal(creditAssign.getCreditDiscount()).divide(new BigDecimal(100)));
            BigDecimal sum = capitalWait.multiply(creditDiscount).add(interestAdvanceWait);
            BigDecimal max = new BigDecimal(0);
            //modify by cwyang 被除数不得为0
            if (sum.compareTo(new BigDecimal(0)) > 0) {
                max = capitalWait.multiply(balance).divide(sum, 8, RoundingMode.DOWN);
            }
            if (max.compareTo(capitalWait) > 0) {
                // 全投金额
                result.setInvestAllMoney((String.valueOf(capitalWait.intValue())));
            } else {
                // 全投金额
                result.setInvestAllMoney(String.valueOf(max.intValue()));
            }
        }else{
            result.setStatusDesc(CustomConstants.APP_STATUS_DESC_FAIL);
            // 全投金额
            result.setInvestAllMoney("0");
        }
        return result;
    }

    /**
     * 债转承接校验
     *
     * @param request
     * @return
     */
    @Override
    public Map<String, Object> borrowCreditCheck(TenderRequest request) {
        UserVO loginUser = amUserClient.findUserById(Integer.valueOf(request.getUserId()));
        Integer userId = loginUser.getUserId();
        request.setUser(loginUser);
        // 检查请求参数是否正确
        this.checkRequest(request);
        // 获取债转数据
        BorrowCreditVO borrowCredit = amTradeClient.getBorrowCreditByCreditNid(request.getCreditNid());
        if(borrowCredit==null){
            // 获取债转数据错误
            throw new CheckException(MsgEnum.ERROR_CREDIT_NOT_EXIST);
        }
        logger.info("债转出借校验开始   userId:{},credNid:{},ip:{},平台{}", userId, request.getCreditNid(), request.getIp(), request.getPlatform());
        UserVO user = amUserClient.findUserById(userId);
        UserInfoVO userInfo = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankOpenAccount = amUserClient.selectBankAccountById(userId);
        // 检查用户状态  角色  授权状态等  是否允许出借
        this.checkUser(user,userInfo,bankOpenAccount,borrowCredit);
        // 查询用户账户表-出借账户
        AccountVO tenderAccount = amTradeClient.getAccount(userId);
        // 前端Web页面出借可债转输入出借金额后收益提示 用户未登录 (包含查询条件)
        TenderToCreditAssignCustomizeVO creditAssign = this.amTradeClient.getInterestInfo(request.getCreditNid(), request.getAssignCapital(),userId);
        logger.info("creditAssign {}", JSONObject.toJSONString(creditAssign));
        // 检查金额
        this.checkTenderMoney(request, tenderAccount,creditAssign);
        //校验用户测评
        //给测评接口金额赋值
        request.setAccount(request.getAssignCapital());
        // 原标项目编号
        String bidNid = borrowCredit.getBidNid();
        // 根据原标标号取借款信息
        BorrowAndInfoVO borrowAndInfoVO = amTradeClient.getBorrowByNid(bidNid);
        Map<String, Object> resultEval = hjhTenderService.checkEvaluationTypeMoney(request,borrowAndInfoVO.getInvestLevel(),CustomConstants.TENDER_CHECK_LEVE_HZR);
        logger.info("债转出借校验通过始   userId:{},credNid:{},ip:{},平台{}", userId, request.getCreditNid(), request.getIp(), request.getPlatform());
        return resultEval;
    }

    /**
     * 债转成功后操作
     * @param logOrderId
     * @param userId
     * @param authCode
     * @param creditTenderLog
     * @return
     */
    private boolean updateTenderCreditInfo(String logOrderId, Integer userId, String authCode, CreditTenderLogVO creditTenderLog) {
        int nowTime = GetDate.getNowTime10();
        if (creditTenderLog != null) {
            CreditTenderBgVO creditTenderBg = new CreditTenderBgVO();
            // 1.删除log表
            // 债权结束标志位
            Integer debtEndFlag = 0;
            // 出让人userId
            int sellerUserId = creditTenderLog.getCreditUserId();
            // 原始出借订单号
            String tenderOrderId = creditTenderLog.getCreditTenderNid();
            // 项目编号
            String borrowNid = creditTenderLog.getBidNid();
            // 债转编号
            String creditNid = creditTenderLog.getCreditNid();
            // 取得债权出让人的用户电子账户号
            BankOpenAccountVO sellerBankAccount = this.getBankOpenAccount(sellerUserId);
            // 出让人账户信息
            AccountVO sellerAccount = this.amTradeClient.getAccount(sellerUserId);
            // 承接人账户信息
            AccountVO assignAccount = this.amTradeClient.getAccount(userId);
            // 项目详情
            BorrowAndInfoVO borrow = this.amTradeClient.selectBorrowByNid(borrowNid);
            // 还款方式
            String borrowStyle = borrow.getBorrowStyle();
            // 项目总期数
            Integer borrowPeriod = Validator.isNull(borrow.getBorrowPeriod()) ? 1 : borrow.getBorrowPeriod();
            // 管理费率
            BigDecimal feeRate = Validator.isNull(borrow.getManageFeeRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getManageFeeRate());
            // 差异费率
            BigDecimal differentialRate = Validator.isNull(borrow.getDifferentialRate()) ? BigDecimal.ZERO : new BigDecimal(borrow.getDifferentialRate());
            // 初审时间
            int borrowVerifyTime = Validator.isNull(borrow.getVerifyTime()) ? 0 : borrow.getVerifyTime();
            // 是否月标(true:月标, false:天标)
            boolean isMonth = CustomConstants.BORROW_STYLE_PRINCIPAL.equals(borrowStyle) || CustomConstants.BORROW_STYLE_MONTH.equals(borrowStyle)
                    || CustomConstants.BORROW_STYLE_ENDMONTH.equals(borrowStyle);
            // 管理费
            BigDecimal perManageSum = BigDecimal.ZERO;
            // 承接人
            UserInfoCustomizeVO userInfoCustomize = this.amUserClient.queryUserInfoCustomizeByUserId(userId);
            // 承接人的推荐人
            SpreadsUserVO spreadsUsers = this.amUserClient.querySpreadsUsersByUserId(userId);
            // 承接人的推荐人
            UserInfoCustomizeVO userInfoCustomizeRefCj = null;
            // 出让人
            UserInfoCustomizeVO userInfoCustomizeSeller = this.amUserClient.queryUserInfoCustomizeByUserId(sellerUserId);
            // 出让人
            SpreadsUserVO spreadsUsersSeller = this.amUserClient.querySpreadsUsersByUserId(sellerUserId);
            // 出让人的推荐人
            UserInfoCustomizeVO userInfoCustomizeRefCr;
            // 获取BorrowCredit信息
            // 获取汇转让标的列表
            List<BorrowCreditVO> borrowCreditList = this.amTradeClient.getBorrowCreditList(creditNid, sellerUserId, tenderOrderId);
            // 2更新borrow_credit
            if (borrowCreditList != null && borrowCreditList.size() == 1) {
                BorrowCreditVO borrowCredit = borrowCreditList.get(0);
                // 获取BorrowRecover信息
                BorrowRecoverVO borrowRecover = this.amTradeClient.getBorrowRecoverByTenderNidBidNid(borrowCredit.getTenderNid(), borrowCredit.getBidNid());
                // 总收入,本金+垫付利息
                borrowCredit.setCreditIncome(borrowCredit.getCreditIncome().add(creditTenderLog.getAssignPay()));
                // 已认购本金
                borrowCredit.setCreditCapitalAssigned(borrowCredit.getCreditCapitalAssigned().add(creditTenderLog.getAssignCapital()));
                // 已垫付利息
                borrowCredit.setCreditInterestAdvanceAssigned(borrowCredit.getCreditInterestAdvanceAssigned().add(creditTenderLog.getAssignInterestAdvance()));
                // 已承接利息
                borrowCredit.setCreditInterestAssigned(borrowCredit.getCreditInterestAssigned().add(creditTenderLog.getAssignInterest()));
                // 服务费
                borrowCredit.setCreditFee(borrowCredit.getCreditFee().add(creditTenderLog.getCreditFee()));
                // 认购时间
                borrowCredit.setAssignTime(GetDate.getNowTime10());
                // 出借次数
                borrowCredit.setAssignNum(borrowCredit.getAssignNum() + 1);

                // 3更新相应的borrowCredit

                // 1.插入credit_tender
                CreditTenderVO creditTender = new CreditTenderVO();
                // 认购日期
                creditTender.setAssignCreateDate(creditTenderLog.getAssignCreateDate());
                // 支付金额
                creditTender.setAssignPay(creditTenderLog.getAssignPay());
                // 服务费
                creditTender.setCreditFee(creditTenderLog.getCreditFee());
                // 添加时间
                creditTender.setCreateTime(new Date());
                // 出借本金
                creditTender.setAssignCapital(creditTenderLog.getAssignCapital());
                // 用户名称
                creditTender.setUserId(userId);
                // 用户名
                creditTender.setUserName(creditTenderLog.getUserName());
                // 出让人id
                creditTender.setCreditUserId(sellerUserId);
                creditTender.setCreditUserName(creditTenderLog.getCreditUserName());
                creditTender.setBorrowUserId(creditTenderLog.getBorrowUserId());
                creditTender.setBorrowUserName(creditTenderLog.getBorrowUserName());
                // 状态
                creditTender.setStatus(0);
                // 原标标号
                creditTender.setBidNid(creditTenderLog.getBidNid());
                // 债转标号
                creditTender.setCreditNid(creditTenderLog.getCreditNid());
                // 债转投标单号
                creditTender.setCreditTenderNid(creditTenderLog.getCreditTenderNid());
                // 认购单号
                creditTender.setAssignNid(creditTenderLog.getAssignNid());
                // 回收总额
                creditTender.setAssignAccount(creditTenderLog.getAssignAccount());
                // 债转利息
                creditTender.setAssignInterest(creditTenderLog.getAssignInterest());
                // 垫付利息
                creditTender.setAssignInterestAdvance(creditTenderLog.getAssignInterestAdvance());
                // 购买价格
                creditTender.setAssignPrice(creditTenderLog.getAssignPrice());
                // 已还总额
                creditTender.setAssignRepayAccount(creditTenderLog.getAssignRepayAccount());
                // 已还本金
                creditTender.setAssignRepayCapital(creditTenderLog.getAssignRepayCapital());
                // 已还利息
                creditTender.setAssignRepayInterest(creditTenderLog.getAssignRepayInterest());
                // 最后还款日
                creditTender.setAssignRepayEndTime(creditTenderLog.getAssignRepayEndTime());
                // 上次还款时间
                creditTender.setAssignRepayLastTime(creditTenderLog.getAssignRepayLastTime());
                // 下次还款时间
                creditTender.setAssignRepayNextTime(creditTenderLog.getAssignRepayNextTime());
                // 最终实际还款时间
                creditTender.setAssignRepayYesTime(creditTenderLog.getAssignRepayYesTime());
                creditTender.setAssignRepayPeriod(creditTenderLog.getAssignRepayPeriod());
                // 还款期数
                creditTender.setAddIp(creditTenderLog.getAddIp());
                // 客户端
                creditTender.setClient(creditTenderLog.getClient());
                // 银行存管新增授权码
                creditTender.setAuthCode(authCode);
                // 已还款期数
                creditTender.setRecoverPeriod(borrowCredit.getRecoverPeriod());

                //添加承接人承接时推荐人信息
                if (spreadsUsers != null) {
                    int refUserId = spreadsUsers.getSpreadsUserId();
                    userInfoCustomizeRefCj = this.amUserClient.queryUserInfoCustomizeByUserId(refUserId);
                    if (Validator.isNotNull(userInfoCustomizeRefCj)) {
                        creditTender.setInviteUserName(userInfoCustomizeRefCj.getUserName());
                        creditTender.setInviteUserAttribute(userInfoCustomizeRefCj.getAttribute());
                        creditTender.setInviteUserRegionname(userInfoCustomizeRefCj.getRegionName());
                        creditTender.setInviteUserBranchname(userInfoCustomizeRefCj.getBranchName());
                        creditTender.setInviteUserDepartmentname(userInfoCustomizeRefCj.getDepartmentName());
                    }
                } else if (userInfoCustomize.getAttribute() == 2 || userInfoCustomize.getAttribute() == 3) {
                    creditTender.setInviteUserName(userInfoCustomize.getUserName());
                    creditTender.setInviteUserAttribute(userInfoCustomize.getAttribute());
                    creditTender.setInviteUserRegionname(userInfoCustomize.getRegionName());
                    creditTender.setInviteUserBranchname(userInfoCustomize.getBranchName());
                    creditTender.setInviteUserDepartmentname(userInfoCustomize.getDepartmentName());
                }

                //添加出让人承接时推荐人信息
                if (spreadsUsersSeller != null) {
                    int refUserId = spreadsUsersSeller.getSpreadsUserId();
                    userInfoCustomizeRefCr = this.amUserClient.queryUserInfoCustomizeByUserId(refUserId);
                    if (Validator.isNotNull(userInfoCustomizeRefCr)) {
                        creditTender.setInviteUserCreditName(userInfoCustomizeRefCr.getUserName());
                        creditTender.setInviteUserCreditAttribute(userInfoCustomizeRefCr.getAttribute());
                        creditTender.setInviteUserCreditRegionname(userInfoCustomizeRefCr.getRegionName());
                        creditTender.setInviteUserCreditBranchname(userInfoCustomizeRefCr.getBranchName());
                        creditTender.setInviteUserCreditDepartmentname(userInfoCustomizeRefCr.getDepartmentName());
                    }

                } else if (userInfoCustomizeSeller.getAttribute() == 2 || userInfoCustomizeSeller.getAttribute() == 3) {
                    creditTender.setInviteUserCreditName(userInfoCustomizeSeller.getUserName());
                    creditTender.setInviteUserCreditAttribute(userInfoCustomizeSeller.getAttribute());
                    creditTender.setInviteUserCreditRegionname(userInfoCustomizeSeller.getRegionName());
                    creditTender.setInviteUserCreditBranchname(userInfoCustomizeSeller.getBranchName());
                    creditTender.setInviteUserCreditDepartmentname(userInfoCustomizeSeller.getDepartmentName());
                }
                // creditTender插入数据库
                // 2.处理承接人account表和account_list表
                // 承接人账户信息
                AccountVO assignAccountNew = new AccountVO();
                assignAccountNew.setUserId(userId);
                assignAccountNew.setBankBalance(creditTender.getAssignPay());
                assignAccountNew.setBankTotal(creditTender.getAssignCapital().add(creditTender.getAssignInterest()).subtract(creditTender.getAssignPay()));
                // 银行待收+承接金额
                assignAccountNew.setBankAwait(creditTender.getAssignAccount());
                // 银行待收本金+承接本金
                assignAccountNew.setBankAwaitCapital(creditTender.getAssignCapital());
                // 银行待收利息+承接利息
                assignAccountNew.setBankAwaitInterest(creditTender.getAssignInterest());
                // 累计出借+承接本金
                assignAccountNew.setBankInvestSum(creditTender.getAssignCapital());
                // 更新账户信息
                // 重新获取承接人用户账户信息  改为原子层

                // 插入交易明细
                // 3.处理出让人account表和account_list表
                AccountVO sellerAccountNew = new AccountVO();
                sellerAccountNew.setUserId(sellerUserId);
                // 银行可用余额
                sellerAccountNew.setBankBalance(creditTender.getAssignPay().subtract(creditTender.getCreditFee()));
                // 银行总资产
                sellerAccountNew.setBankTotal(creditTender.getAssignPay().subtract(creditTender.getCreditFee()).subtract(creditTender.getAssignAccount()));
                // 出让人待收金额
                sellerAccountNew.setBankAwait(creditTender.getAssignAccount());
                // 出让人待收本金
                sellerAccountNew.setBankAwaitCapital(creditTender.getAssignCapital());
                // 出让人待收利息
                sellerAccountNew.setBankAwaitInterest(creditTender.getAssignInterest());
                // 出让人累计收益
                sellerAccountNew.setBankInterestSum((creditTender.getAssignInterestAdvance()==null?BigDecimal.ZERO:creditTender.getAssignInterestAdvance()));
                sellerAccountNew.setBankBalanceCash(creditTender.getAssignPay().subtract(creditTender.getCreditFee()));
                // 更新账户信息
                // 重新获取用户账户信息 改为原子层

                // 插入交易明细
                // 6.更新Borrow_recover
                if (borrowRecover != null) {
                    // 不分期
                    if (!isMonth) {
                        // 管理费
                        BigDecimal perManage = BigDecimal.ZERO;
                        // 如果是完全承接
                        if (borrowCredit.getCreditStatus() == 2) {
                            perManage = borrowRecover.getRecoverFee().subtract(borrowRecover.getCreditManageFee());
                        } else {
                            // 按月计息，到期还本还息end
                            if (CustomConstants.BORROW_STYLE_END.equals(borrowStyle)) {
                                perManage = AccountManagementFeeUtils.getDueAccountManagementFeeByMonth(creditTender.getAssignCapital(), feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                            }
                            // 按天计息到期还本还息
                            else if (CustomConstants.BORROW_STYLE_ENDDAY.equals(borrowStyle)) {
                                perManage = AccountManagementFeeUtils.getDueAccountManagementFeeByDay(creditTender.getAssignCapital(), feeRate, borrowPeriod, differentialRate, borrowVerifyTime);
                            }
                        }
                        perManageSum = perManage;
                        CreditRepayVO creditRepay = new CreditRepayVO();
                        // 用户名称
                        creditRepay.setUserId(userId);
                        // 出让人id
                        creditRepay.setCreditUserId(creditTender.getCreditUserId());
                        // 状态
                        creditRepay.setStatus(0);
                        // 原标标号
                        creditRepay.setBidNid(creditTender.getBidNid());
                        // 债转标号
                        creditRepay.setCreditNid(creditTender.getCreditNid());
                        // 债转投标单号
                        creditRepay.setCreditTenderNid(creditTender.getCreditTenderNid());
                        // 认购单号
                        creditRepay.setAssignNid(creditTender.getAssignNid());
                        // 应还本金
                        creditRepay.setAssignCapital(creditTender.getAssignCapital());
                        // 应还总额
                        creditRepay.setAssignAccount(creditTender.getAssignAccount());
                        // 应还利息
                        creditRepay.setAssignInterest(creditTender.getAssignInterest());
                        // 垫付利息
                        creditRepay.setAssignInterestAdvance(creditTender.getAssignInterestAdvance());
                        // 购买价格
                        creditRepay.setAssignPrice(creditTender.getAssignPrice());
                        // 支付金额
                        creditRepay.setAssignPay(creditTender.getAssignPay());
                        // 已还总额
                        creditRepay.setAssignRepayAccount(BigDecimal.ZERO);
                        // 已还本金
                        creditRepay.setAssignRepayCapital(BigDecimal.ZERO);
                        // 已还利息
                        creditRepay.setAssignRepayInterest(BigDecimal.ZERO);
                        // 最后还款日
                        creditRepay.setAssignRepayEndTime(creditTender.getAssignRepayEndTime());
                        // 上次还款时间
                        creditRepay.setAssignRepayLastTime(creditTender.getAssignRepayLastTime());
                        // 下次还款时间
                        creditRepay.setAssignRepayNextTime(creditTender.getAssignRepayNextTime());
                        // 最终实际还款时间
                        creditRepay.setAssignRepayYesTime(0);
                        // 还款期数
                        creditRepay.setAssignRepayPeriod(1);
                        // 认购日期
                        creditRepay.setAssignCreateDate(creditTender.getAssignCreateDate());
                        // ip
                        creditRepay.setAddIp(creditTender.getAddIp());
                        // 客户端
                        creditRepay.setClient(creditTenderLog.getClient());
                        // 原标还款期数
                        creditRepay.setRecoverPeriod(1);
                        creditRepay.setAdvanceStatus(0);
                        creditRepay.setChargeDays(0);
                        creditRepay.setChargeInterest(BigDecimal.ZERO);
                        creditRepay.setDelayDays(0);
                        creditRepay.setDelayInterest(BigDecimal.ZERO);
                        creditRepay.setLateDays(0);
                        creditRepay.setLateInterest(BigDecimal.ZERO);
                        // 唯一nid
                        creditRepay.setUniqueNid(creditTender.getAssignNid() + "_1");
                        // 管理费
                        creditRepay.setManageFee(perManage);
                        // 授权码
                        creditRepay.setAuthCode(authCode);
                        creditRepay.setUserName(creditTenderLog.getUserName());
                        creditRepay.setCreditUserName(creditTender.getCreditUserName());
                        creditTenderBg.setCreditRepayVO(creditRepay);
                        // 插入
                    } else {
                        // 管理费
                        if (creditTender.getAssignRepayPeriod() > 0) {
                            // 先息后本
                            if (CalculatesUtil.STYLE_ENDMONTH.equals(borrowStyle)) {
                                for (int i = 1; i <= creditTender.getAssignRepayPeriod(); i++) {
                                    BigDecimal perManage = BigDecimal.ZERO;
                                    perManageSum = perManageSum.add(perManage);
                                }
                            }
                        }
                    }
                    borrowRecover.setCreditAmount(borrowRecover.getCreditAmount().add(creditTender.getAssignCapital()));
                    borrowRecover.setCreditInterestAmount(borrowRecover.getCreditInterestAmount().add(creditTender.getAssignInterestAdvance()));
                    // 已收债转管理费
                    borrowRecover.setCreditManageFee(borrowRecover.getCreditManageFee().add(perManageSum));
                    logger.info("borrowCredit.getCreditCapitalAssigned():{}    borrowCredit.getCreditCapital():{}",borrowCredit.getCreditCapitalAssigned(),borrowCredit.getCreditCapital());
                    if (borrowCredit.getCreditCapitalAssigned().compareTo(borrowCredit.getCreditCapital()) == 0) {
                        debtEndFlag = 1;
                        borrowCredit.setCreditStatus(2);
                    }
                    // 债权是否结束状态
                    borrowRecover.setDebtStatus(debtEndFlag);
                    borrowRecover.setCreditStatus(borrowCredit.getCreditStatus());
                    creditTenderBg.setAssignAccountNew(assignAccountNew);
                    creditTenderBg.setBorrowCredit(borrowCredit);
                    creditTenderBg.setCreditTender(creditTender);
                    creditTenderBg.setSellerAccountNew(sellerAccountNew);
                    creditTenderBg.setCreditTenderLog(creditTenderLog);
                    creditTenderBg.setBorrowRecover(borrowRecover);
                    creditTenderBg.setSellerBankAccount(sellerBankAccount);
                    // 保存债转主数据
                    Integer result = this.amTradeClient.saveCreditBgData(creditTenderBg);
                    if(result==0){
                        logger.error("抱歉，出借失败，请重试  {}",JSONObject.toJSONString(creditTenderBg));
                        throw  new CheckException(MsgEnum.ERR_AMT_TENDER_INVESTMENT);
                    }
                    // 发送法大大协议
                    logger.info("==========承接转让发送法大大协议:bidNid="+creditTender.getBidNid()+",assignNid="+creditTender.getAssignNid()+
                            ",creditNid="+creditTender.getCreditNid()+",creditTenderNid="+creditTender.getCreditTenderNid());

                    this.sendPdfMQ(userId, creditTender.getBidNid(),creditTender.getAssignNid(), creditTender.getCreditNid(), creditTender.getCreditTenderNid());
                    // 发送承接完成短信
                    if (borrowCredit.getCreditCapitalAssigned().compareTo(borrowCredit.getCreditCapital()) == 0) {
                        try {
                            logger.info("发送承接完成短信 mq ");
                            this.sendCreditSuccessMessage(creditTender,borrowCredit);
                        } catch (MQException e) {
                            logger.error("e   :",e);
                            e.printStackTrace();
                        }
                    }
                    //----------------------------------准备开始操作运营数据等  用mq----------------------------------
                    logger.info("开始更新运营数据等 updateUtm ");
                    updateUtm(userId, creditTenderLog.getAssignCapital(), GetDate.getNowTime10(), borrowCredit.getCreditTerm() + "天");
                    logger.info("开始更新app渠道统计数据 ht_app_utm_reg ");
                    updateAppChannel(userId, borrowCredit, creditTenderLog);
                    // 网站累计出借追加
                    // 出借、收益统计表
                    JSONObject params = new JSONObject();
                    params.put("tenderSum", creditTenderLog.getAssignCapital());
                    params.put("nowTime", GetDate.getDate(GetDate.getNowTime10()));
                    // 出借修改mongodb运营数据
                    params.put("type", 1);
                    params.put("money", creditTenderLog.getAssignCapital());
                    try {
                        // 网站累计投资追加
                        // 投资修改mongodb运营数据
                        logger.info("网站累计投资追加 mq ");
                        commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC, UUID.randomUUID().toString(), params));
                        // 满标发短信在原子层
                    } catch (MQException e) {
                        e.printStackTrace();
                    }
                    // 4.添加网站收支明细  // 发送mq更新添加网站收支明细
                    // 服务费大于0时,插入网站收支明细
                    if (creditTender.getCreditFee().compareTo(BigDecimal.ZERO) > 0) {
                        BigDecimal money = creditTender.getCreditFee().setScale(2,BigDecimal.ROUND_DOWN);
                        // 插入网站收支明细记录
                        logger.info("网站收支明细记录 mq ");
                        AccountWebListVO accountWebList = new AccountWebListVO();
                        accountWebList.setOrdid(logOrderId);
                        accountWebList.setBorrowNid(creditTender.getBidNid());
                        accountWebList.setAmount(Double.valueOf(money.toString()));
                        accountWebList.setType(1);
                        accountWebList.setTrade("CREDITFEE");
                        accountWebList.setTradeType("债转服务费");
                        accountWebList.setUserId(creditTender.getUserId());
                        accountWebList.setUsrcustid(creditTenderLog.getAccountId());
                        accountWebList.setRemark(creditTender.getCreditNid());
                        accountWebList.setNote(null);
                        accountWebList.setCreateTime(nowTime);
                        accountWebList.setOperator(null);
                        accountWebList.setFlag(1);
                        try {
                            commonProducer.messageSend(new MessageContent(MQConstant.ACCOUNT_WEB_LIST_TOPIC, UUID.randomUUID().toString(), accountWebList));
                        } catch (MQException e) {
                            logger.error("更新网站收支明细失败！logOrdId:{},userId:{}",logOrderId,userId);
                        }
                    }

                    // 承接成功后, 发送神策数据统计MQ
                    // 神策数据统计 add by liuyang 20180726 start
                    try {
                        SensorsDataBean sensorsDataBean = new SensorsDataBean();
                        sensorsDataBean.setUserId(userId);
                        sensorsDataBean.setEventCode("receive_credit_assign");
                        sensorsDataBean.setOrderId(creditTender.getAssignNid());
                        // 发送神策数据统计MQ
                        logger.info("承接成功后发送神策数据统计MQ,承接订单号:[" + creditTender.getAssignNid() + "].");
                        this.sendSensorsDataMQ(sensorsDataBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    // 神策数据统计 add by liuyang 20180726 end
                    return true;
                }
            }else{
                throw new CheckException(MsgEnum.ERROR_CREDIT_NOT_EXIST);
            }
        }
        return true;
    }

    /**
     * 更新app渠道统计表
     * @param userId
     * @param borrowCredit
     * @param creditTenderLog
     */
    private void updateAppChannel(Integer userId, BorrowCreditVO borrowCredit, CreditTenderLogVO creditTenderLog) {
        AppUtmRegVO appChannelStatisticsDetails = amUserClient.getAppChannelStatisticsDetailByUserId(userId);
        if (appChannelStatisticsDetails != null) {
            logger.info("更新app渠道统计表, userId is: {}", userId);
            Map<String, Object> params = new HashMap<String, Object>();
            // 认购本金
            params.put("accountDecimal", creditTenderLog.getAssignCapital());
            // 出借时间
            params.put("investTime", GetDate.getNowTime10());
            // 项目类型
            params.put("projectType", "汇转让");
            // 首次投标项目期限
            params.put("investProjectPeriod", borrowCredit.getCreditTerm() + "天");
            //根据investFlag标志位来决定更新哪种出借
            params.put("investFlag", checkIsNewUserCanInvest2(userId));
            // 用户id
            params.put("userId", userId);
            //压入消息队列
            try {
                commonProducer.messageSend(new MessageContent(MQConstant.APP_CHANNEL_STATISTICS_DETAIL_TOPIC,
                        MQConstant.APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG, UUID.randomUUID().toString(), params));
            } catch (MQException e) {
                e.printStackTrace();
                logger.error("渠道统计用户累计出借推送消息队列失败！！！");
            }
        }
    }

    /**
     * 发送法大大协议
     * @param tenderUserId
     * @param borrowNid
     * @param assignOrderId
     * @param creditNid
     * @param creditTenderNid
     */
    private void sendPdfMQ(Integer tenderUserId, String borrowNid, String assignOrderId, String creditNid, String creditTenderNid) {
        FddGenerateContractBean bean = new FddGenerateContractBean();
        bean.setOrdid(assignOrderId);
        bean.setAssignOrderId(assignOrderId);
        bean.setCreditNid(creditNid);
        bean.setCreditTenderNid(creditTenderNid);
        bean.setTenderUserId(tenderUserId);
        bean.setBorrowNid(borrowNid);
        bean.setTransType(3);
        bean.setTenderType(1);
        try{
            logger.info("债转承接发送法大大协议----ing");
            commonProducer.messageSendDelay(new MessageContent(MQConstant.FDD_TOPIC,MQConstant.FDD_GENERATE_CONTRACT_TAG,UUID.randomUUID().toString(),bean),2);
        }catch (Exception e){
            logger.error("债转承接发送法大大协议失败  {}",JSONObject.toJSONString(bean));
        }
    }

    private void updateUtm(Integer userId, BigDecimal accountDecimal, Integer nowTime, String investProjectPeriod) {
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
        params.put("projectType", "汇转让");
        // 首次投标项目期限
        params.put("investProjectPeriod", investProjectPeriod);
        //压入消息队列
        try {
            commonProducer.messageSend(new MessageContent(MQConstant.STATISTICS_UTM_REG_TOPIC, UUID.randomUUID().toString(), params));
        } catch (MQException e) {
            e.printStackTrace();
            logger.error("渠道统计用户累计出借推送消息队列失败！！！");
        }

        /*(6)更新  渠道统计用户累计出借  和  huiyingdai_utm_reg的首投信息 结束*/
    }

    /**
     * 推送app消息 以及满标发短信
     * @param creditTender
     * @param borrowCredit
     * @throws MQException
     */
    private void sendCreditSuccessMessage(CreditTenderVO creditTender,BorrowCreditVO borrowCredit) throws MQException {
        //获取承接人用户信息
        UserVO webUser = this.amUserClient.findUserById(creditTender.getUserId());
        UserInfoVO usersInfo = this.amUserClient.findUserInfoById(creditTender.getUserId());

        if (webUser != null) {
            //给承接人推送消息
            Map<String, String> appParam = getCommonMessage(usersInfo);
            appParam.put("val_title", creditTender.getCreditNid() + "");
            appParam.put("val_balance", creditTender.getAssignPay() + "");
            appParam.put("val_profit", creditTender.getAssignInterest() + "");
            appParam.put("val_amount", creditTender.getAssignAccount() + "");

            AppMsMessage message = new AppMsMessage(null, appParam, webUser.getMobile(), MessageConstant.APP_MS_SEND_FOR_MOBILE, CustomConstants.JYTZ_TPL_CJZQ);
            commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), message));

            if (borrowCredit.getCreditCapitalAssigned().compareTo(borrowCredit.getCreditCapital()) == 0) {
                //转让人用户信息
                UserVO creditUser = this.amUserClient.findUserById(borrowCredit.getCreditUserId());
                UserInfoVO creditUserInfo = this.amUserClient.findUserInfoById(borrowCredit.getCreditUserId());
                if(creditUser != null){
                    // 向出让人推送债转完全承接消息
                    Map<String, String> param = getCommonMessage(creditUserInfo);

                    param.put("val_amount", borrowCredit.getCreditCapital() + "");
                    param.put("val_profit", borrowCredit.getCreditInterestAdvanceAssigned() + "");
                    // 发送短信验证码
                    SmsMessage smsMessage = new SmsMessage(null, param, creditUser.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_ZZQBZRCG,
                            CustomConstants.CHANNEL_TYPE_NORMAL);
                    commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));

                    AppMsMessage appMsMessage = new AppMsMessage(null, param, creditUser.getMobile(), MessageConstant.APP_MS_SEND_FOR_MOBILE, CustomConstants.JYTZ_TPL_ZHUANRANGJIESHU);
                    commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC, UUID.randomUUID().toString(), appMsMessage));
                }
            }
        }
    }

    /**
     * 获取推送共同消息
     * @param userInfoVO
     * @return
     */
    private Map<String, String> getCommonMessage(UserInfoVO userInfoVO){
        Map<String, String> param = new HashMap<String, String>();
        if (userInfoVO.getTruename() != null && userInfoVO.getTruename().length() > 1) {
            param.put("val_name", userInfoVO.getTruename().substring(0, 1));
        } else {
            param.put("val_name", userInfoVO.getTruename());
        }
        if (userInfoVO.getSex() == 1) {
            param.put("val_sex", "先生");
        } else if (userInfoVO.getSex() == 2) {
            param.put("val_sex", "女士");
        } else {
            param.put("val_sex", "");
        }
        return param;
    }

    /**
     * 调用相应的查询接口查询此笔承接的相应的承接状态
     * @param logOrderId
     * @param userId
     * @param accountId
     * @return
     */
    private BankCallBean creditInvestQuery(String logOrderId, Integer userId,String accountId) {
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,BankCallConstant.TXCODE_CREDIT_INVEST_QUERY,userId);
        // 存管平台分配的账号
        bean.setAccountId(accountId);
        // 原购买债权订单号
        bean.setOrgOrderId(logOrderId);
        return BankCallUtils.callApiBg(bean);
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
        creditTenderLog.setAssignNid(bean.getLogOrderId());
        // 银行请求订单号
        creditTenderLog.setLogOrderId(bean.getLogOrderId());
        // 检查是否能债转  ？？？原来的逻辑不用了1726行CreditServiceImpl
        // 插入债转日志表
        Integer saveCount = amTradeClient.saveCreditTenderAssignLog(creditTenderLog);
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
        BorrowRecoverVO borrowRecover = this.amTradeClient.getBorrowRecoverByTenderNidBidNid(borrowCredit.getTenderNid(), borrowCredit.getBidNid());
        if (borrowRecover == null) {
            // 未查询到用户的放款记录
            throw new CheckException(MsgEnum.ERROR_CREDIT_NO_MONEY);
        }
        // 如果放款时间小于 20170703 重新计算已承接金额
        if (GetDate.getTime10(borrowRecover.getCreateTime()) < 1499011200 && borrowRecover.getCreditAmount().compareTo(BigDecimal.ZERO) > 0) {
            // 计算已承接的债权
            BigDecimal assignedCapital = amTradeClient.getAssignCapital(borrowRecover.getNid());
            creditTenderLog.setTenderMoney(borrowRecover.getRecoverCapital().subtract(assignedCapital));
        } else {
            creditTenderLog.setTenderMoney(borrowRecover.getRecoverCapital());
        }
        // 获取借款数据
        BorrowAndInfoVO borrow = amTradeClient.selectBorrowByNid(borrowCredit.getBidNid());
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
        BigDecimal creditAccount = BigDecimal.ZERO;
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
                logger.info("1垫付利息::"+assignInterestAdvance);
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
                logger.info("2垫付利息::"+assignInterestAdvance);
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
            List<BorrowRepayPlanVO> borrowRepayPlans = amTradeClient.getBorrowRepayPlansByPeriod(bidNid, borrowCredit.getRecoverPeriod()+1);

            if (borrowRepayPlans != null && borrowRepayPlans.size() > 0) {
                try {
                    String nowDate = GetDate.getDateTimeMyTimeInMillis(borrowCredit.getCreateTime());
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
                logger.info("3垫付利息::"+assignInterestAdvance);
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
                // 垫息总额=出借人认购本金/出让人转让本金*出让人本期利息）-（债权本金*年化收益÷360*本期剩余天数
                logger.info("assignCapital:{}   getCreditCapital:{}  yearRate:{}  interest:{}  lastDays:{} ",assignCapital, borrowCredit.getCreditCapital(),yearRate,interest,lastDays);

                logger.info("assignInterestAdvance:{} ",assignInterestAdvance);
                assignInterestAdvance = BeforeInterestAfterPrincipalUtils.getAssignInterestAdvance(new BigDecimal(assignCapital), borrowCredit.getCreditCapital(), yearRate, interest,
                        new BigDecimal(lastDays + ""));
                logger.info("---assignInterestAdvance:{} ",assignInterestAdvance);
                // 实付金额 承接本金*（1-折价率）+应垫付利息
                assignPay = assignPrice.add(assignInterestAdvance);
            }
        }
        logger.info("4assignInterestAdvance:{} ",assignInterestAdvance);
        // 保存credit_tender_log表
        creditTenderLog.setUserId(user.getUserId());
        creditTenderLog.setCreditUserId(borrowCredit.getCreditUserId());
        creditTenderLog.setStatus(0);
        creditTenderLog.setBorrowUserName(borrow.getBorrowUserName());
        creditTenderLog.setBorrowUserId(borrow.getUserId());
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
            logger.error("债转算是否是新旧标区分时间错误，债转标号:" + borrowCredit.getCreditNid());
        }
        DebtConfigResponse response = amConfigClient.getDebtConfig();
        DebtConfigVO config = response.getResult();
        if (ifOldDate != null && ifOldDate <= GetDate.getTime10(borrowCredit.getCreateTime())) {
            creditTenderLog.setCreditFee(assignPay.multiply(new BigDecimal(0.01)));
            if(config!=null) {
                creditTenderLog.setCreditFee(assignPay.multiply(config.getAttornRate().divide(new BigDecimal(100))).setScale(2, BigDecimal.ROUND_DOWN));
            }else {
                creditTenderLog.setCreditFee(assignPay.multiply(new BigDecimal(0.01)));
            }
        } else {
            creditTenderLog.setCreditFee(assignPay.multiply(new BigDecimal(0.005)));
        }
        creditTenderLog.setUserName(user.getUsername());
        creditTenderLog.setCreditUserName(borrowCredit.getCreditUserName());
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
        String orderId = GetOrderIdUtils.getOrderId2(user.getUserId());
        bean.setOrderId(orderId);
        bean.setLogOrderId(orderId);
        logger.info("creditTenderLog.getCreditUserId():"+creditTenderLog.getCreditUserId());
        BankOpenAccountVO accountChinapnrCrediter = amUserClient.selectBankAccountById(creditTenderLog.getCreditUserId());
        logger.info("accountChinapnrCrediter:{}",JSONObject.toJSONString(accountChinapnrCrediter));
        bean.setAccountId(bankOpenAccount.getAccount());
        // 实付金额 承接本金*（1-折价率）+应垫付利息
        bean.setTxAmount(creditAssign.getAssignPay().replaceAll(",",""));
        bean.setTxFee(creditTenderLog.getCreditFee() != null ? DF_COM_VIEW.format(creditTenderLog.getCreditFee()) : "0.01");
        bean.setTsfAmount(DF_COM_VIEW.format(creditTenderLog.getAssignCapital()));
        // 对手电子账号:卖出方账号
        bean.setForAccountId(accountChinapnrCrediter.getAccount());
        bean.setOrgOrderId(creditTenderLog.getCreditTenderNid());
        bean.setOrgTxAmount(DF_COM_VIEW.format(creditTenderLog.getTenderMoney()));
        bean.setProductId(creditTenderLog.getBidNid());


        String retUrl = "";
        String successUrl = "";
        if((ClientConstants.APP_CLIENT_IOS+"").equals(request.getPlatform())|| (ClientConstants.APP_CLIENT+"").equals(request.getPlatform())){
            // 忘记密码的跳转URL
            bean.setForgotPwdUrl(systemConfig.getAppFrontHost()+systemConfig.getAppForgetpassword());
            //错误页
            retUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/transfer/"+request.getCreditNid()+"/result/failed?logOrdId="+bean.getLogOrderId()+"&sign="+request.getSign()+"&token="+request.getSign();
            //成功页
            successUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/transfer/"+request.getCreditNid()+"/result/success?logOrdId="+bean.getLogOrderId()+"&sign="+request.getSign()+"&token="+request.getSign();
        }else{
            // 忘记密码的跳转URL
            bean.setForgotPwdUrl(systemConfig.getFrontHost()+systemConfig.getForgetpassword());
            //错误页
            retUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/transfer/transferInvestError?logOrdId="+bean.getLogOrderId();
            //成功页
            successUrl = super.getFrontHost(systemConfig,request.getPlatform()) + "/transfer/transferInvestSuccess?logOrdId="+bean.getLogOrderId();
        }

        // 异步调用路
        String bgRetUrl = "http://CS-TRADE/hyjf-web/tender/credit/bgReturn?platform="+request.getPlatform();
        bean.setRetUrl("1231231"+retUrl);
        bean.setNotifyUrl("1234123"+bgRetUrl);
        bean.setSuccessfulUrl("12312312"+successUrl);
        bean.setLogRemark("债转出借");
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
        long accountInt = 0L;
        try {
            // 出借金额必须是整数
            accountInt = Long.parseLong(request.getAssignCapital());
        } catch (Exception e) {
            logger.error("格式化错误 ",e);
            // 出借金额不能为整数
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_INT);
        }
        if (accountInt == 0) {
            // 出借金额不能为0元
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_ZERO);
        }
        if (accountInt < 0) {
            // 出借金额不能为负数
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_NEGATIVE);
        }
        // 将出借金额转化为BigDecimal
        BigDecimal accountBigDecimal = new BigDecimal(request.getAssignCapital().replaceAll(",",""));
        BigDecimal creditCapital = new BigDecimal(creditAssign.getCreditCapital().replaceAll(",",""));
        // 剩余可投金额
        Integer min = 1;
        if (min != null && min != 0 && accountBigDecimal.compareTo(new BigDecimal(min)) == -1) {
            if (accountBigDecimal.compareTo(creditCapital) == 1) {
                // 剩余可投只剩{0}元，须全部购买
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_LESS,creditCapital);
            }
            if (accountBigDecimal.compareTo(accountBigDecimal) != 0) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_BORROW_MONEY_LESS_NEED_BUY_ALL,creditCapital);
            }
        } else {
            // 项目的剩余金额大于最低起投金额
            if (accountBigDecimal.compareTo(new BigDecimal(min)) == -1) {
                // {0}元起投
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MIN_INVESTMENT,"1");
            } else {
                if (accountBigDecimal.compareTo(accountBigDecimal) == 1) {
                    // 项目最大出借金额为{0}元
                    throw new CheckException(MsgEnum.ERR_AMT_TENDER_MAX_INVESTMENT,creditCapital);
                }
            }
        }
        BigDecimal assCreditCapital = new BigDecimal(creditAssign.getAssignCapital().replaceAll(",",""));
        if (assCreditCapital.equals(BigDecimal.ZERO)) {
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_YOU_ARE_LATE,creditCapital);
        } else {
            if (accountBigDecimal.compareTo(assCreditCapital) > 0) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_MONEY_BIG,creditCapital);
            }
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

        // 用户未开户
        if (user.getBankOpenAccount() == 0) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 交易密码状态检查
        if (user.getIsSetPassword() == 0) {
            throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        if (bankOpenAccount == null || user.getBankOpenAccount() == 0 || StringUtils.isEmpty(bankOpenAccount.getAccount())) {
            // 未开户
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 判断用户是否禁用  0启用，1禁用
        if (user.getStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_USER_INVALID);
        }
        String roleIsOpen = systemConfig.getRoleIsopen();
        if(StringUtils.isNotBlank(roleIsOpen) && roleIsOpen.equals("true")){
            if (userInfo.getRoleId().intValue() != 1) {
                throw new CheckException(MsgEnum.ERR_AMT_TENDER_ONLY_LENDERS);
            }
        }
        if (!authService.checkPaymentAuthStatus(user.getUserId())) {
            // 未进行服务费授权
            throw new CheckException(MsgEnum.ERR_AMT_TENDER_NEED_PAYMENT_AUTH);

        }
        // 风险测评校验
        //this.checkEvaluation(user);
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


    /**
     * 承接成功后,发送神策数据统计
     *
     * @param sensorsDataBean
     */
    private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_CREDIT_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
    }
}
