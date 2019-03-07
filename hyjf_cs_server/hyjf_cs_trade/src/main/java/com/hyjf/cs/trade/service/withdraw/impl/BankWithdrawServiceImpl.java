package com.hyjf.cs.trade.service.withdraw.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.result.CheckResult;
import com.hyjf.am.resquest.trade.AfterCashParamRequest;
import com.hyjf.am.resquest.trade.ApiUserWithdrawRequest;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.resquest.trade.SensorsDataBean;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.*;
import com.hyjf.cs.trade.bean.assetpush.UserWithdrawRequestBean;
import com.hyjf.cs.trade.client.AmConfigClient;
import com.hyjf.cs.trade.client.AmTradeClient;
import com.hyjf.cs.trade.client.AmUserClient;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.mq.base.CommonProducer;
import com.hyjf.cs.trade.mq.base.MessageContent;
import com.hyjf.cs.trade.service.auth.AuthService;
import com.hyjf.cs.trade.service.impl.BaseTradeServiceImpl;
import com.hyjf.cs.trade.service.withdraw.BankWithdrawService;
import com.hyjf.cs.trade.util.ErrorCodeConstant;
import com.hyjf.cs.trade.util.SignUtil;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.*;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author pangchengchao
 * @version BankWithdrawServiceImpl, v0.1 2018/6/14 14:32
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
@Service
public class BankWithdrawServiceImpl extends BaseTradeServiceImpl implements BankWithdrawService {
    private static final Logger logger = LoggerFactory.getLogger(BankWithdrawServiceImpl.class);

    @Autowired
    AmUserClient amUserClient;
    @Autowired
    AmConfigClient amConfigClient;
    
    @Autowired
    AmTradeClient amTradeClient;

    @Autowired
    private AuthService authService;


    @Autowired
    SystemConfig systemConfig;

    @Autowired
    CommonProducer commonProducer;

    @Value("${hyjf.bank.fee}")
    private String FEETMP;


    @Override
    @HystrixCommand(commandKey = "提现(三端)-getUserBankWithdrawView",fallbackMethod = "fallBackWithdraw",ignoreExceptions = CheckException.class,commandProperties = {
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
    public BankCallBean getUserBankWithdrawView(UserVO user, String transAmt, String cardNo, String payAllianceCode, String platform, String channel, String ip, String retUrl, String bgRetUrl, String successfulUrl, String forgotPwdUrl) {


        //检查用户信息
        UserVO users = this.checkUserWithdrawMessage(user, transAmt, cardNo, payAllianceCode);

        // 取得手续费 默认1
        String fee = this.getWithdrawFee(user.getUserId(), cardNo);
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(users, platform, channel, transAmt, cardNo, payAllianceCode, fee,retUrl,bgRetUrl,successfulUrl,forgotPwdUrl);
        logger.debug("提现返回bean"+bean);
        // 插值用参数
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", String.valueOf(user.getUserId()));
        params.put("userName", user.getUsername());
        params.put("ip", ip);
        params.put("cardNo", cardNo);
        params.put("fee", CustomUtil.formatAmount(fee));
        params.put("client", "0");
        // 插入日志
        this.updateWithdrawBeforeCash(users,bean,platform,params);
        return bean;
    }

    public BankCallBean fallBackWithdraw(UserVO user, String transAmt, String cardNo, String payAllianceCode, String platform, String channel, String ip, String retUrl, String bgRetUrl, String successfulUrl, String forgotPwdUrl,Throwable e){
        logger.error("警告 ！！ fallBackWithdraw -->"+e.getMessage());
        return null;
    }

    @Override
    public Map<String, String> userBankWithdrawReturn(BankCallBean bean, String isSuccess, String wifee, String withdrawmoney) {
        Map<String, String> map=new HashMap<String, String>();
        bean.convert();
        String logOrderId = bean.getLogOrderId();
        AccountWithdrawVO accountwithdraw = this.amTradeClient.getAccountWithdrawByOrdId(logOrderId);
        // 调用成功了
        if (isSuccess != null && "1".equals(isSuccess)) {
            if (accountwithdraw != null) {
                //modelAndView = new ModelAndView(BankWithdrawDefine.WITHDRAW_SUCCESS);
                map.put("amt", accountwithdraw.getTotal().toString());
                map.put("arrivalAmount", accountwithdraw.getCredited().toString());
                map.put("fee", accountwithdraw.getFee().toString());
                map.put("info", "恭喜您，提现成功");
                return map;
            }else{
                //modelAndView = new ModelAndView(BankWithdrawDefine.WITHDRAW_SUCCESS);
                map.put("amt", withdrawmoney);
                map.put("fee", wifee);
                map.put("info", "恭喜您，提现成功");
                return map;
            }
        }
        if (accountwithdraw != null) {
            //modelAndView = new ModelAndView(BankWithdrawDefine.WITHDRAW_SUCCESS);
            map.put("amt", accountwithdraw.getTotal().toString());
            map.put("arrivalAmount", accountwithdraw.getCredited().toString());
            map.put("fee", accountwithdraw.getFee().toString());
            map.put("info", "恭喜您，提现成功");
        } else {
            //modelAndView = new ModelAndView(BankWithdrawDefine.WITHDRAW_INFO);
            map.put("info", "银行处理中，请稍后查询交易明细");
        }

        return null;
    }

    @Override
    public JSONObject handlerAfterCash(BankCallBean bean, Map<String, String> params) {
        // 用户ID
        int userId = Integer.parseInt(params.get("userId"));
        // 查询账户信息
        //AccountVO account = this.bindCardClient.getAccount(userId);
        // 根据用户ID查询用户银行卡信息
        BankCardVO bankCard = this.amUserClient.selectBankCardByUserId(userId);
        String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
        // 银联行号
        String payAllianceCode = bean.getLogAcqResBean() == null ? "" : bean.getLogAcqResBean().getPayAllianceCode();
        int nowTime = GetDate.getNowTime10(); // 当前时间
        Date nowDate = new Date();
        List<AccountWithdrawVO> listAccountWithdraw = this.amTradeClient.selectAccountWithdrawByOrdId(ordId);

        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            // 提现信息
            AccountWithdrawVO accountWithdraw = listAccountWithdraw.get(0);
            // 返回值=000成功 ,大额提现返回值为 CE999028
            if (BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) || "CE999028".equals(bean.getRetCode())) {
                // 如果信息未被处理
                if (accountWithdraw.getStatus() == WITHDRAW_STATUS_SUCCESS) {
                    // 如果是已经提现成功了
                    return jsonMessage("提现成功", "0");
                } else {
                    // 查询是否已经处理过
                    int accountlistCnt = this.amTradeClient.countAccountListByOrdId(ordId,"cash_success");
                    // 未被处理
                    if (accountlistCnt == 0) {
                        try {
                            // 提现成功后,更新银行联行号
                            // 大额提现返回成功 并且银联行号不为空的情况,将正确的银联行号更新到bankCard表中
                            System.out.println("银联行号:" + payAllianceCode);
                            if ("CE999028".equals(bean.getRetCode()) && StringUtils.isNotEmpty(payAllianceCode)) {
                                BankCardVO updateBankCardVO=new BankCardVO();
                                updateBankCardVO.setId(bankCard.getId());
                                updateBankCardVO.setPayAllianceCode(payAllianceCode);
                                try {
                                    boolean isBankCardUpdateFlag = this.amUserClient.updateBankCardPayAllianceCode(updateBankCardVO) > 0 ? true : false;
                                    if (!isBankCardUpdateFlag) {
                                        throw new Exception("大额提现成功后,更新用户银行卡的银联行号失败~~~!" + bankCard.getId());
                                    }
                                } catch (Exception e) {
                                    logger.error(e.getMessage());
                                }
                            }
                            // 提现金额
                            BigDecimal transAmt = bean.getBigDecimal(BankCallParamConstant.PARAM_TXAMOUNT);
                            String fee = this.getWithdrawFee(userId, bankCard.getCardNo());
                            // 提现手续费
                            BigDecimal feeAmt = new BigDecimal(fee);
                            // 总的交易金额
                            BigDecimal total = transAmt.add(feeAmt);
                            logger.info("总的交易金额:"+total+";提现手续费:"+feeAmt+";transAmt:"+transAmt);
                            //构建提现保存信息
                            BankWithdrawBeanRequest bankWithdrawBeanRequest=createBankWithdrawBeanRequest(accountWithdraw,transAmt,fee,feeAmt,total,userId,ordId,nowTime,bean.getAccountId(),params.get("ip"));

                            boolean isAccountListFlag = amTradeClient.updatUserBankWithdrawHandler(bankWithdrawBeanRequest) > 0 ? true : false;
                            if (!isAccountListFlag) {
                                throw new Exception("提现成功后,插入交易明细表失败~!");
                            }
                            // 更新用户账户信息
                            UserVO users = amUserClient.findUserById(userId);
                            // 可以发送提现短信时
                            if (users != null && users.getWithdrawSms() != null && users.getWithdrawSms() == 0) {
                                // 替换参数
                                Map<String, String> replaceMap = new HashMap<String, String>();
                                replaceMap.put("val_amount", total.toString());
                                replaceMap.put("val_fee", feeAmt.toString());
                                UserInfoVO info = amUserClient.findUsersInfoById(userId);
                                replaceMap.put("val_name", info.getTruename().substring(0, 1));
                                replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
                                SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMS_SEND_FOR_USER, null, CustomConstants.PARAM_TPL_TIXIAN_SUCCESS,
                                        CustomConstants.CHANNEL_TYPE_NORMAL);
                                AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                                commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC,
                                        UUID.randomUUID().toString(), smsMessage));
                                commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
                                        UUID.randomUUID().toString(), appMsMessage));

                            } else {
                                // 替换参数
                                Map<String, String> replaceMap = new HashMap<String, String>();
                                replaceMap.put("val_amount", total.toString());
                                replaceMap.put("val_fee", feeAmt.toString());
                                UserInfoVO info = amUserClient.findUsersInfoById(userId);
                                replaceMap.put("val_name", info.getTruename().substring(0, 1));
                                replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
                                AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APP_MS_SEND_FOR_USER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                                commonProducer.messageSend(new MessageContent(MQConstant.APP_MESSAGE_TOPIC,
                                        UUID.randomUUID().toString(), appMsMessage));
                            }
                            // TODO 活动统计 需要的时候放开
                            /*CommonSoaUtils.listedTwoWithdraw(userId, total);*/

                            try{
                                // 提现成功后,发送神策数据统计MQ
                                SensorsDataBean sensorsDataBean = new SensorsDataBean();
                                sensorsDataBean.setOrderId(bean.getLogOrderId());
                                sensorsDataBean.setEventCode("withdraw_result");
                                sensorsDataBean.setUserId(Integer.parseInt(bean.getLogUserId()));
                                this.sendSensorsDataMQ(sensorsDataBean);
                            }catch (Exception e){
                                logger.error(e.getMessage());
                            }

                            return jsonMessage("提现成功!", "0");
                        } catch (Exception e) {
                            // 回滚事务
                            logger.error(e.getMessage());
                            return jsonMessage("提现失败,请联系客服!", "1");
                        }
                    }
                }
            } else {
                // 提现失败,更新订单状态
                List<AccountWithdrawVO> list = this.amTradeClient.selectAccountWithdrawByOrdId(ordId);
                if (list != null && list.size() > 0) {
                    AccountWithdrawVO accountwithdraw = list.get(0);
                    //mod by nxl 将状态更改为提现中
                    accountwithdraw.setStatus(WITHDRAW_STATUS_WAIT);
                    accountwithdraw.setUpdateTime(nowDate);
                    // 失败原因
                    String reason = this.getBankRetMsg(bean.getRetCode());
                    accountwithdraw.setReason(reason);
                    boolean isUpdateFlag = this.amTradeClient.updateAccountwithdrawLog(accountwithdraw);
                    if (!isUpdateFlag) {
                        throw new RuntimeException("提现失败后,更新提现记录表失败");
                    }
                }
                return jsonMessage(bean.getRetMsg() == null ? "" : bean.getRetMsg(), "1");
            }
        }
        return null;
    }


    @Override
    public UserVO getUserByUserId(Integer userId) {

        return amUserClient.findUserById(userId);
    }
    /**
     * @Description 获取提现信息
     * @Author pangchengchao
     * @Version v0.1
     * @Date
     */
    @Override
    public WebResult<Object> toWithdraw(WebViewUserVO user) {

        WebResult<Object> result = new WebResult<Object>();
        JSONObject ret = new JSONObject();
        if(user==null){
            result.setStatus(MsgEnum.ERR_USER_LOGIN_RETRY.getCode());
            result.setStatusDesc(MsgEnum.ERR_USER_LOGIN_RETRY.getMsg());
            return result;
        }
        Integer userId = user.getUserId();
        DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");
        // 取得用户当前余额
        AccountVO account = this.amUserClient.getAccount(userId);
        if (account == null) {
            result.setStatus(MsgEnum.ERR_USER_UNUSUAL.getCode());
            result.setStatusDesc(MsgEnum.ERR_USER_UNUSUAL.getMsg());
            return result;
        }
        // 判断用户是否开户
        if (!user.isBankOpenAccount()) {
            result.setStatus(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN.getCode());
            result.setStatusDesc(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN.getMsg());
            return result;
        }
        // 查询页面上可以挂载的银行列表
        BankCardVO banks = amUserClient.selectBankCardByUserId(userId);
        if (banks == null) {
            // 用户未绑卡
            result.setStatus(MsgEnum.ERR_CARD_NOT_BIND.getCode());
            result.setStatusDesc(MsgEnum.ERR_CARD_NOT_BIND.getMsg());
            return result;
        }

        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        //用户角色
        String userRoId = userInfoVO.getRoleId() + "";
        //用户的可用金额
        BigDecimal bankBalance = account.getBankBalance();
        //查询用户出借记录
        int borrowTender = amTradeClient.getBorrowTender(userId);
        if (StringUtils.equals("1", userRoId)) {
            if (borrowTender <= 0) {
                //查询用户的24小时内充值记录
                List<AccountRechargeVO> todayRecharge = amTradeClient.getTodayRecharge(userId);
                if (todayRecharge != null && !todayRecharge.isEmpty()) {
                    // 计算用户当前可提现金额
                    for (AccountRechargeVO recharge : todayRecharge) {
                        bankBalance = bankBalance.subtract(recharge.getBalance());
                    }
                }
            }
        }

        ret.put("total", CustomConstants.DF_FOR_VIEW.format(account.getBankBalance()));// 可提现金额
        ret.put("unformattedTotal", account.getBankBalance());// 可提现金额
        List<BankCardBean> bankcards = new ArrayList<BankCardBean>();
        // 银行联号
        String payAllianceCode = "";
        // 银行类型
        String bankType = "";
        String feeWithdraw = "1.00";
        if (banks != null) {
            BankCardBean bankCardBean = new BankCardBean();
            bankCardBean.setId(banks.getId());
            bankCardBean.setBankId(banks.getBankId());// 银行Id
            bankType = String.valueOf(banks.getBankId());
            payAllianceCode = banks.getPayAllianceCode() == null ? "" : banks.getPayAllianceCode();
            bankCardBean.setPayAllianceCode(banks.getPayAllianceCode() == null ? "" : banks.getPayAllianceCode());
            bankCardBean.setBank(banks.getBank());
            String cardNo = banks.getCardNo();
            String cardNoInfo = BankCardUtil.getCardNo(cardNo);
            bankCardBean.setCardNoInfo(cardNoInfo);
            bankCardBean.setCardNo(cardNo);
            bankCardBean.setIsDefault("2");// 卡类型

            Integer bankId = banks.getBankId();
            JxBankConfigVO jxBankConfigVO = amConfigClient.getBankNameByBankId(bankId + "");
            if (jxBankConfigVO != null && StringUtils.isNotEmpty(jxBankConfigVO.getBankName())) {
                bankCardBean.setBank(jxBankConfigVO.getBankName()==null?"":jxBankConfigVO.getBankName());
                bankCardBean.setBankCode(jxBankConfigVO.getBankCode()==null?"":jxBankConfigVO.getBankCode());
                bankCardBean.setLogo(jxBankConfigVO.getBankLogo()==null?"":jxBankConfigVO.getBankLogo());
            }
            bankcards.add(bankCardBean);

            feeWithdraw = this.getWithdrawFee(userId, banks.getCardNo());
        }

        ret.put("userType", user.getUserType());// 是否为企业用户（
        ret.put("banks", bankcards);
        ret.put("isSetPassWord", user.getIsSetPassword());
        ret.put("payAllianceCode", payAllianceCode);
        ret.put("bankType", bankType);
        ret.put("feeWithdraw", DF_FOR_VIEW.format(new BigDecimal(feeWithdraw)));
        ret.put("roleId", userRoId);
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthVO(userId);
        // 是否开启服务费授权 0未开启  1已开启
        ret.put("paymentAuthStatus", hjhUserAuth==null?"":hjhUserAuth.getAutoPaymentStatus());
        // 是否开启服务费授权 0未开启  1已开启
        ret.put("paymentAuthOn", authService.getAuthConfigFromCache(RedisConstants.KEY_PAYMENT_AUTH).getEnabledStatus());
        ret.put("bankBalance", CustomConstants.DF_FOR_VIEW.format(bankBalance));
        result.setData(ret);
        return result;
    }

    /**
     * 定时任务提现
     */
    @Override
    public void batchWithdraw() {
        List<AccountWithdrawVO> withdrawList = this.amTradeClient.selectBankWithdrawList();
        if (CollectionUtils.isNotEmpty(withdrawList)){
            logger.info("获取的提现掉单记录数："+withdrawList.size());
            for (AccountWithdrawVO accountWithdraw : withdrawList) {
                updateWithdraw(accountWithdraw);
            }
        }
    }


    @Override
    public UserVO findUserByMobile(String mobile) {
        return amUserClient.findUserByMobile(mobile);
    }

    @Override
    public BankCardVO getBankInfo(Integer userId, String cardNo) {
        return amUserClient.getBankCardByCardNo(userId,cardNo);
    }

    /**
     * 更新银行提现掉单
     * add by jijun 20180621
     * @param accountwithdraw
     */
    private void updateWithdraw(AccountWithdrawVO accountwithdraw) {

        try {
            //调用银行接口
            BankCallBean bean = this.bankCallFundTransQuery(accountwithdraw);
            logger.info("提现掉单调用银行接口返回："+JSONObject.toJSONString(bean));
            if (bean != null) {
                int userId = accountwithdraw.getUserId();
                BankCardVO bankCard = this.amUserClient.selectBankCardByUserId(userId);
                //bean.getTxAmount() 为""
                if (StringUtils.isEmpty(bean.getTxAmount())){
                    throw new RuntimeException("调用银行接口,银行返回交易金额为空,充值订单号:"
                            + accountwithdraw.getNid()
                            + ",用户Id:" + accountwithdraw.getUserId());
                }

                BigDecimal transAmt = new BigDecimal(bean.getTxAmount());
                String withdrawFee = this.getWithdrawFee(userId,bankCard == null ? "" : String.valueOf(bankCard.getBankId()), transAmt);
                //调用后平台操作
                AfterCashParamRequest request = new  AfterCashParamRequest();
                request.setBankCallBeanVO(CommonUtils.convertBean(bean,BankCallBeanVO.class));
                request.setAccountWithdrawVO(accountwithdraw);
                request.setBankCardVO(bankCard);
                request.setWithdrawFee(withdrawFee);
                boolean result=this.amTradeClient.handlerAfterCash(request);
                if (result){
                    logger.info("银行提现掉单修复成功!,账户:"+accountwithdraw.getAccountId());

                }else{
                    logger.error("银行提现掉单修复失败!,账户:"+accountwithdraw.getAccountId());
                }
            }else {
                throw new RuntimeException("调用银行接口,银行返回NULL,充值订单号:"
                        + accountwithdraw.getNid()
                        + ",用户Id:" + accountwithdraw.getUserId());
            }

        } catch (RuntimeException e) {
            logger.error(this.getClass().getName(), "bankCallFundTransQuery", e);
        }


    }

    /**
     * 调用银行接口
     * @param accountwithdraw
     * @return
     */
    private BankCallBean bankCallFundTransQuery(AccountWithdrawVO accountwithdraw) {
        // 银行接口用BEAN
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,
                BankCallConstant.TXCODE_FUND_TRANS_QUERY,
                accountwithdraw.getUserId());
        //设置特有参数
        bean.setAccountId(accountwithdraw.getAccountId());// 借款人电子账号
        bean.setOrgTxDate(String.valueOf(accountwithdraw.getTxDate()));//原交易日期
        //时间补满6位
        bean.setOrgTxTime(String.format("%06d", accountwithdraw.getTxTime()));//原交易时间
        bean.setOrgSeqNo(String.valueOf(accountwithdraw.getSeqNo()));//原交易流水号
        bean.setLogRemark("单笔资金类业务交易查询（提现Batch）");
        try {
            BankCallBean result = BankCallUtils.callApiBg(bean);
            if (result != null) {
                if (StringUtils.isBlank(result.getRetMsg())) {
                    //根据响应代码取得响应描述
                    result.setRetMsg(this.getBankRetMsg(result.getRetCode()));
                }
            }
            return result;
        } catch (Exception e) {
            logger.error(this.getClass().getName(), "bankCallFundTransQuery", e);
        }
        return null;
    }


    /**
     *
     * @param accountWithdraw
     * @param userId
     * @param ordId
     * @return 已被更新false，未更新true
     */
    private CheckResult checkConcurrencyDB(AccountWithdrawVO accountWithdraw, int userId, String ordId) {
        CheckResult result = new CheckResult();

        Boolean resultBool = true;
        String resultMsg = null;
        String msg = "此笔充提现状态已发生变化,提现订单号:" + ordId + ",用户Id:" + userId;

        //匹配验证
        // 如果信息已被处理
        if (accountWithdraw.getStatus() == WITHDRAW_STATUS_SUCCESS) {
            resultBool = false;
            resultMsg = msg;
        }
        // 查询是否已经处理过
        int accountlistCnt = this.amTradeClient.getAccountlistCntByOrdId(ordId,"cash_success");
        // 如果信息已被处理
        if (accountlistCnt != 0) {
            resultBool = false;
            resultMsg = msg;
        }

        //匹配结果
        result.setResultBool(resultBool);
        result.setResultMsg(resultMsg);
        return result;
    }


    private String getWithdrawFee(Integer userId, String bankId, BigDecimal amount) {
        BankCardVO bankCard = this.amUserClient.getBankCardByCardNo(userId, bankId);
        if (StringUtils.isBlank(FEETMP)) {
            FEETMP = "1";
        }
        if (bankCard != null) {
            String bankCode = bankCard.getBank();
            // 取得费率
            List<FeeConfigVO> listFeeConfig = this.amConfigClient.getFeeConfig(bankCode);

            if (listFeeConfig != null && listFeeConfig.size() > 0) {
                FeeConfigVO feeConfig = listFeeConfig.get(0);
                BigDecimal takout = BigDecimal.ZERO;
                BigDecimal percent = BigDecimal.ZERO;
                if (Validator.isNotNull(feeConfig.getNormalTakeout()) && NumberUtils.isCreatable(feeConfig.getNormalTakeout())) {
                    takout = new BigDecimal(feeConfig.getNormalTakeout());
                }
                return takout.add(percent).toString();
            } else {
                return FEETMP;
            }
        } else {
            return FEETMP;
        }
    }

    /**
     * 银行接口返回与平台记录匹配验证
     * @param bean
     * @param accountWithdraw
     * @return
     */
    private CheckResult checkCallRetAndHyjf(BankCallBeanVO bean, AccountWithdrawVO accountWithdraw) {
        CheckResult result = new CheckResult();

        Boolean resultBool = true;
        String resultMsg = null;

        //匹配验证
        //提现金额
        BigDecimal txAmount = new BigDecimal(bean.getTxAmount());
        if (!txAmount.equals(accountWithdraw.getCredited())) {
            resultBool = false;
            resultMsg = "本地记录的提现金额与银行返回的交易金额不一致:本地记录的提现金额:" + accountWithdraw.getTotal() + ",银行返回的充值金额:" + txAmount;
        }

        //匹配结果
        result.setResultBool(resultBool);
        result.setResultMsg(resultMsg);
        return result;
    }



    private BankWithdrawBeanRequest createBankWithdrawBeanRequest(AccountWithdrawVO accountWithdraw, BigDecimal transAmt, String fee, BigDecimal feeAmt, BigDecimal total, int userId, String ordId, int nowTime, String accountId, String ip) {
        BankWithdrawBeanRequest bankWithdrawBeanRequest = new BankWithdrawBeanRequest();
        Date nowDate = new Date();
        bankWithdrawBeanRequest.setUserId(userId);
        bankWithdrawBeanRequest.setTransAmt(transAmt);
        bankWithdrawBeanRequest.setFee(fee);
        bankWithdrawBeanRequest.setFeeAmt(feeAmt);
        bankWithdrawBeanRequest.setTotal(total);
        bankWithdrawBeanRequest.setOrdId(ordId);
        bankWithdrawBeanRequest.setNowTime(nowTime);
        bankWithdrawBeanRequest.setAccountId(accountId);
        bankWithdrawBeanRequest.setIp(ip);

        // 构建订单信息
        accountWithdraw.setFee((CustomUtil.formatAmount(feeAmt.toString()))); // 更新手续费
        accountWithdraw.setCredited(transAmt); // 更新到账金额
        accountWithdraw.setTotal(total); // 更新到总额
        accountWithdraw.setStatus(WITHDRAW_STATUS_SUCCESS);// 4:成功
        accountWithdraw.setUpdateTime(nowDate);
        accountWithdraw.setAccount(accountId);
        accountWithdraw.setReason("");
        accountWithdraw.setNid(ordId);
        bankWithdrawBeanRequest.setAccountWithdrawVO(accountWithdraw);
        AccountVO newAccount = new AccountVO();
        // 构建账户信息
        newAccount.setUserId(userId);// 用户Id
        newAccount.setBankTotal(total); // 累加到账户总资产
        newAccount.setBankBalance(total); // 累加可用余额
        newAccount.setBankBalanceCash(total);// 江西银行可用余额
        bankWithdrawBeanRequest.setAccountVO(newAccount);
        return  bankWithdrawBeanRequest;
    }

    public JSONObject jsonMessage(String errorDesc, String error) {
        JSONObject jo = null;
        if (Validator.isNotNull(errorDesc)) {
            jo = new JSONObject();
            jo.put("error", error);
            jo.put("errorDesc", errorDesc);
        }
        return jo;
    }

    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO retCodes =amUserClient.getBankReturnCodeConfig(retCode);
            if (retCodes != null) {
                String retMsg = retCodes.getErrorMsg();
                if (StringUtils.isNotBlank(retMsg)) {
                    return retMsg;
                } else {
                    return "请联系客服！";
                }
            } else {
                return "请联系客服！";
            }
        } else {
            return "操作失败！";
        }
    }
    /**
     * 检查用户信息
     * @param user
     * @param transAmt
     * @param cardNo
     * @param payAllianceCode
     */
    public UserVO checkUserWithdrawMessage(UserVO user, String transAmt, String cardNo, String payAllianceCode){
        if (user == null) {
            throw new CheckException(MsgEnum.ERR_USER_LOGIN_RETRY);
        }
        // 检查数据是否完整
        if (Validator.isNull(transAmt)) {
            throw new CheckException(MsgEnum.ERR_AMT_WITHDRAW_AMOUNT);
        }
        if (Validator.isNull(cardNo)) {
            throw new CheckException(MsgEnum.ERR_PARAM);
        }

        // 检查参数(交易金额是否大于0)
        BigDecimal account = new BigDecimal(transAmt);
        String feetmp = ClientConstants.BANK_FEE;
        if (feetmp == null) {
            feetmp = "1";
        }
        if (account.compareTo(new BigDecimal(feetmp)) <= 0) {
            throw new CheckException(MsgEnum.ERR_AMT_WITHDRAW_AMOUNT_GREATER_THAN_ONE);
        }
        // 检查参数(可能使用特殊开头(如04)的银行卡,所以不再使用是否是数字进行判断)
       /* if (Validator.isNotNull(cardNo) && !StringUtils.isNumeric(cardNo)) {
            throw new CheckException(MsgEnum.ERR_AMT_WITHDRAW_CARD);
        }*/
        UserVO users= amUserClient.findUserById(user.getUserId());
        if (users.getBankOpenAccount()==0) {
            throw new CheckException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            throw new CheckException(MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
        }
        if (users.getStatus() == 1) {
            throw new CheckException(MsgEnum.ERR_USER_INVALID);
        }
        if ((account.compareTo(new BigDecimal(50000)) > 0) && StringUtils.isBlank(payAllianceCode)) {
            throw new CheckException(MsgEnum.ERR_AMT_WITHDRAW_BANK_ALLIANCE_CODE_REQUIRED);
        }

        BankCardVO bankCard = this.amUserClient.queryUserCardValid(user.getUserId()+"", cardNo);
        if (bankCard == null || Validator.isNull(bankCard.getCardNo())) {
            throw new CheckException(MsgEnum.ERR_CARD_NOT_BIND);
        }
//
        // 取得用户当前余额
        AccountVO accountVO = amUserClient.getAccount(user.getUserId());
        // 提现金额大于可用余额
        if (null==accountVO) {
            throw new CheckException(MsgEnum.ERR_USER_UNUSUAL);
        }
        // 提现金额大于可用余额
        if(account.compareTo(accountVO.getBankBalance()) > 0){
            throw new CheckException(MsgEnum.ERR_AMT_WITHDRAW_BANK_MORETHEN_BANLANCE);
        }
        // 服务费授权状态
        if(!authService.checkPaymentAuthStatus(user.getUserId())){
            throw new CheckException(MsgEnum.ERR_AUTH_USER_PAYMENT);
        }
        //服务费收取
        return users;
    }
    // add by nxl 添加体现状态
    // 体现状态：初始值
    private static  final int WITHDRAW_STATUS_DEFAULT = 0;
    // 提现状态:提现中
    private static final int WITHDRAW_STATUS_WAIT = 1;
    // 提现状态:失败
    private static final int WITHDRAW_STATUS_FAIL = 3;
    // 提现状态:成功
    private static final int WITHDRAW_STATUS_SUCCESS = 2;
    /**
     *插入日志
     * @param user
     * @param bean
     * @param client
     */
    public void updateWithdrawBeforeCash(UserVO user, BankCallBean bean, String client,Map<String, String> params) {
        String ordId = bean.getLogOrderId() == null ? bean.get("OrdId") : bean.getLogOrderId(); // 订单号
        List<AccountWithdrawVO> listAccountWithdraw = this.amTradeClient.selectAccountWithdrawByOrdId(ordId);
        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            return;
        }
        int nowTime = GetDate.getNowTime10(); // 当前时间
        BigDecimal money = new BigDecimal(bean.getTxAmount()); // 提现金额
        BigDecimal fee = BigDecimal.ZERO; // 取得费率
        if (Validator.isNotNull(params.get("fee"))) {
            fee = new BigDecimal(params.get("fee")); // 取得费率
        }
        BigDecimal total = money.add(fee); // 实际出账金额
        Integer userId = GetterUtil.getInteger(params.get("userId")); // 用户ID
        String cardNo = params.get("cardNo"); // 银行卡号
        String bank = null;
        // 取得银行信息
        BankCardVO bankCard = this.amUserClient.queryUserCardValid(user.getUserId()+"", cardNo);
        if (bankCard != null) {
            bank = bankCard.getBank();
        } else {
        	 return;
        }
        AccountWithdrawVO record = new AccountWithdrawVO();
        record.setUserId(userId);
        record.setNid(bean.getLogOrderId()); // 订单号
        //record.setStatus(WITHDRAW_STATUS_WAIT); // 状态: 0:处理中
        // mod by nxl 将体现初始状态设置为初始值
        record.setStatus(WITHDRAW_STATUS_DEFAULT); // 状态: 0:初始值
        record.setAccount(cardNo);// 提现银行卡号
        record.setBank(bank); // 提现银行
        if(bankCard.getId() != null){
        	record.setBankId(bankCard.getId());
        }
//        record.setBranch(null);
//        record.setProvince(0);
//        record.setCity(0);
        record.setTotal(total);
        record.setCredited(money);
        record.setBankFlag(1);
        record.setFee(CustomUtil.formatAmount(fee.toString()));
        //record.setAddtime(String.valueOf(nowTime));
        record.setAddIp(params.get("ip"));
        record.setAccountId(bean.getAccountId());

        record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
        record.setTxDate(Integer.parseInt(bean.getTxDate()));
        record.setTxTime(Integer.parseInt(bean.getTxTime()));
        record.setSeqNo(Integer.parseInt(bean.getSeqNo()));
        record.setRemark("网站提现");
        record.setClient(Integer.parseInt(client)); // 0pc
        record.setWithdrawType(0); // 提现类型 0主动提现  1代提现
        // 插入用户提现记录表
        amTradeClient.insertAccountWithdrawLog(record);
    }


    /**
     * 组装发往江西银行参数
     * @param user
     * @param platform
     * @param channel
     * @param transAmt
     * @param cardNo
     * @param payAllianceCode
     * @param fee
     * @param retUrl
     * @param bgRetUrl
     * @param successfulUrl
     * @param forgotPwdUrl
     * @return
     */
    private BankCallBean getCommonBankCallBean(UserVO user, String platform, String channel, String transAmt, String cardNo, String payAllianceCode, String fee, String retUrl, String bgRetUrl, String successfulUrl, String forgotPwdUrl) {
        String orderId=GetOrderIdUtils.getOrderId2(user.getUserId());
        BankCardVO bankCard = this.amUserClient.queryUserCardValid(user.getUserId()+"", cardNo);
        UserInfoVO usersInfo = this.amUserClient.findUsersInfoById(user.getUserId());
        BankOpenAccountVO bankOpenAccountVO=amUserClient.selectBankAccountById(user.getUserId());
        // 调用汇付接口(提现)
        retUrl = retUrl+"&logOrdId="+orderId;
        if(platform.equals(CommonConstant.CLIENT_PC)){
            successfulUrl = successfulUrl+"&withdrawmoney=" + transAmt+ "&wifee=" + fee;
        }else {
            successfulUrl = successfulUrl+"&amount=" + transAmt+ "&charge=" + fee;
        }
        // 路由代码
        String routeCode = "";
        // 调用汇付接口(4.2.2 用户绑卡接口)

        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(orderId);
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogRemark("用户提现");
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_WITHDRAW);
        bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
        bean.setTxCode(BankCallMethodConstant.TXCODE_WITHDRAW);
        bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
        bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
        bean.setChannel(channel);// 交易渠道
        bean.setAccountId(bankOpenAccountVO.getAccount());// 存管平台分配的账号
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
        bean.setIdNo(usersInfo.getIdcard());// 证件号
        bean.setName(usersInfo.getTruename());// 姓名
        bean.setMobile(user.getMobile());// 手机号s
        bean.setCardNo(bankCard.getCardNo());// 银行卡号
        bean.setTxAmount(CommonUtils.formatNumber(new BigDecimal(transAmt).subtract(new BigDecimal(fee)).toString()));
        bean.setTxFee(fee);
        // 成功跳转的url
        bean.setSuccessfulUrl(successfulUrl);
        // 提现金额大于五万,走人行通道,路由代码传2
        if ((new BigDecimal(transAmt).compareTo(new BigDecimal(50001)) > 0) && StringUtils.isNotBlank(payAllianceCode)) {
            routeCode = "2";// 路由代码
            bean.setCardBankCnaps(payAllianceCode);// 绑定银行联行号
        }
        if ("2".equals(routeCode)) {
            bean.setRouteCode(routeCode);
        }
        // 企业用户提现
        if (user.getUserType() == 1) { // 企业用户 传组织机构代码
            CorpOpenAccountRecordVO
                    record=amUserClient.selectCorpOpenAccountRecordByUserId(user.getUserId());
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
            bean.setRouteCode("2");
            bean.setCardBankCnaps(StringUtils.isEmpty(payAllianceCode) ? bankCard.getPayAllianceCode() : payAllianceCode);
        }

        bean.setForgotPwdUrl(forgotPwdUrl);
        bean.setRetUrl(retUrl);// 商户前台台应答地址(必须)
        bean.setNotifyUrl(bgRetUrl); // 商户后台应答地址(必须)
        logger.info("提现前台回调函数：\n" + bean.getRetUrl());
        logger.info("提现后台回调函数：\n" + bean.getNotifyUrl());

        return bean;
    }


    @Override
    public String getWithdrawFee(Integer userId, String cardNo) {
        String feetmp = ClientConstants.BANK_FEE;
        if (feetmp == null) {
            feetmp = "1";
        }

        if(StringUtils.isEmpty(cardNo)){
            return feetmp;
        }

        BankCardVO bankCard = this.amUserClient.queryUserCardValid(userId+"", cardNo);

        if (bankCard != null) {
            Integer bankId = bankCard.getBankId();
            // 取得费率
            JxBankConfigVO jxBankConfigVO = amConfigClient.getBankNameByBankId(bankId+"");
            if (jxBankConfigVO != null) {
                if (Validator.isNotNull(jxBankConfigVO.getFeeWithdraw())) {
                    return jxBankConfigVO.getFeeWithdraw().toString();
                }else{
                    return feetmp;
                }
            } else {
                return feetmp;
            }
        } else {
            return feetmp;
        }
    }

    @Override
    public UserInfoVO getUserInfoByUserId(Integer userId) {
        return amUserClient.findUsersInfoById(userId);
    }

    @Override
    public int updateBeforeCash(BankCallBean bean, Map<String, String> params) {
        int ret = 0;
        String ordId = bean.getLogOrderId() == null ? bean.get("OrdId") : bean.getLogOrderId(); // 订单号
        List<AccountWithdrawVO> listAccountWithdraw = this.amTradeClient.selectAccountWithdrawByOrdId(ordId);
        if (listAccountWithdraw != null && listAccountWithdraw.size() > 0) {
            return ret;
        }
        int nowTime = GetDate.getNowTime10(); // 当前时间
        BigDecimal money = new BigDecimal(bean.getTxAmount()); // 提现金额
        BigDecimal fee = BigDecimal.ZERO; // 取得费率
        if (Validator.isNotNull(params.get("fee"))) {
            fee = new BigDecimal(params.get("fee")); // 取得费率
        }
        BigDecimal total = money.add(fee); // 实际出账金额
        Integer userId = GetterUtil.getInteger(params.get("userId")); // 用户ID
        String cardNo = params.get("cardNo"); // 银行卡号
        String bank = null;
        // 取得银行信息
        BankCardVO bankCard = getBankInfo(userId, cardNo);
        if (bankCard != null) {
            bank = bankCard.getBank();
        }else{
        	return ret;
        }
        AccountWithdrawVO record = new AccountWithdrawVO();
        record.setUserId(userId);
        record.setNid(bean.getLogOrderId()); // 订单号
        //record.setStatus(WITHDRAW_STATUS_WAIT); // 状态: 0:处理中
        // mod by nxl 将体现初始状态设置为初始值
        record.setStatus(WITHDRAW_STATUS_DEFAULT); // 状态: 0:初始值
        record.setAccount(cardNo);// 提现银行卡号
        record.setBank(bank); // 提现银行
        if(bankCard.getId() != null){
        	record.setBankId(bankCard.getId());
        }
//        record.setBranch(null);
//        record.setProvince(0);
//        record.setCity(0);
        record.setTotal(total);
        record.setCredited(money);
        record.setBankFlag(1);
        record.setFee(CustomUtil.formatAmount(fee.toString()));
        record.setAddIp(params.get("ip"));
        record.setAccountId(bean.getAccountId());
        record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
        record.setTxDate(Integer.parseInt(bean.getTxDate()));
        record.setTxTime(Integer.parseInt(bean.getTxTime()));
        record.setSeqNo(Integer.parseInt(bean.getSeqNo()));
        record.setRemark("网站提现");
        record.setClient(GetterUtil.getInteger(params.get("client"))); // 0pc
        record.setWithdrawType(0); // 提现类型 0主动提现  1代提现
        // 插入用户提现记录表
        ret +=  amTradeClient.insertAccountWithdrawLog(record);
        return ret;
    }

    @Override
    public AccountWithdrawVO getAccountWithdrawByOrdId(String logOrderId) {
        return amTradeClient.getAccountWithdrawByOrdId(logOrderId);
    }

    @Override
    public void getWithdrawResult(int userId, String logOrderId, Map<String, String> result) {
        // 根据用户ID查询用户银行卡信息
        BankCardVO bankCard = amUserClient.selectBankCardByUserId(userId);
        List<AccountWithdrawVO> listAccountWithdraw = this.amTradeClient.selectAccountWithdrawByOrdId(logOrderId);
        if(!listAccountWithdraw.isEmpty()){
            // 提现信息
            AccountWithdrawVO accountWithdraw = listAccountWithdraw.get(0);
            result.put("cardNo", bankCard.getCardNo());
            result.put("total", accountWithdraw.getTotal()==null?"0":accountWithdraw.getTotal().toString());
            result.put("balance", accountWithdraw.getCredited()==null?"0":accountWithdraw.getCredited().toString());
            result.put("fee", accountWithdraw.getFee());
            result.put("orderId", logOrderId);
            if("2".equals(accountWithdraw.getStatus())){
                result.put("status", "0");
            }
        }
    }

    @Override
    public WebResult<Object> seachUserBankWithdrawErrorMessgae(String logOrdId) {
        WebResult<Object> result = new WebResult<Object>();
        AccountWithdrawVO vo = amTradeClient.getAccountWithdrawByOrdId(logOrdId);
        result.setStatus(WebResult.SUCCESS);
        Map<String,String> map = new HashedMap();
        if(vo!=null&&vo.getReason().length()>0){
            map.put("error",vo.getReason());
        }else{
            map.put("error","系统异常，请稍后再试！");
        }
        result.setData(map);
        return result;
    }

    @Override
    public JxBankConfigVO getBanksConfigByBankId(Integer bankId) {
        return amConfigClient.getBankNameByBankId(bankId+"");
    }

    @Override
    public int getTenderRecord(Integer userId) {
        return  amTradeClient.getBorrowTender(userId);
    }

    @Override
    public List<AccountRechargeVO> getRechargeMoney(Integer userId) {
        return amTradeClient.getTodayRecharge(userId);
    }

    @Override
    public int getBorrowTender(Integer userId) {
        return amTradeClient.getBorrowTender(userId);
    }

    @Override
    public List<AccountRechargeVO> getTodayRecharge(Integer userId) {
        return amTradeClient.getTodayRecharge(userId);
    }
    /**
     * 用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    @HystrixCommand(commandKey = "提现(api)-withdraw",fallbackMethod = "fallBackApiWithdraw",ignoreExceptions = CheckException.class,commandProperties = {
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
    public  Map<String,Object> withdraw(UserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView();
        Map<String,Object> map = new HashMap<>();
        try {
            // 用户电子账户号
            String accountId = userWithdrawRequestBean.getAccountId();
            // 提现金额
            String account = userWithdrawRequestBean.getAccount();
            // 提现银行卡
            String cardNo = userWithdrawRequestBean.getCardNo();
            // 渠道
            String channel = userWithdrawRequestBean.getChannel();
            // 银联行号
            String payAllianceCode = userWithdrawRequestBean.getPayAllianceCode();
            // 同步回调URL
            String retUrl = userWithdrawRequestBean.getRetUrl();
            // 异步回调URL
            String bgRetUrl = userWithdrawRequestBean.getBgRetUrl();
            // 机构编码
            String instCode = userWithdrawRequestBean.getInstCode();
            // 忘记密码URL
            String forgotPwdUrl = userWithdrawRequestBean.getForgotPwdUrl();
            // 银行卡号
            if (Validator.isNull(cardNo)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    // 发送第三方异步回调
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"银行卡号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"银行卡号不能为空");
            }
            // 银行电子账户号
            if (Validator.isNull(accountId)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    // 请求第三方异步回调
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"电子账户号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"电子账户号不能为空");
            }
            // 渠道
            if (Validator.isNull(channel)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"渠道不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"渠道不能为空");
            }
            // 充值金额
            if (Validator.isNull(account)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能为空");
            }
/*            // 同步URL
            if (Validator.isNull(retUrl)) {
                // 异步回调URL不为空
                if (Validator.isNotNull(bgRetUrl)) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"同步回调URL不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "请求参数异常");
                return modelAndView;
            }*/
            // 异步回调URL
            if (Validator.isNull(bgRetUrl)) {
                logger.info("异步回调URL为空");
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"请求参数异常");
            }
            // 机构编号
            if (Validator.isNull(instCode)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"机构编号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"机构编号不能为空");
            }
            // 忘记密码Url
            if (Validator.isNull(forgotPwdUrl)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"忘记密码URL不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"忘记密码URL不能为空");
            }
            // 验签  先去掉验签
            if (!this.verifyRequestSign(userWithdrawRequestBean,BaseDefine.METHOD_SERVER_WITHDRAW)) {
                logger.info("-------------------验签失败！--------------------");
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000002,"验签失败!");
            }
            // 充值金额校验
            if (!account.matches("-?[0-9]+.*[0-9]*")) {
                logger.info("提现金额格式错误,充值金额:[" + account + "]");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额格式错误");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额格式错误");
            }

            // 根据机构编号检索机构信息
            HjhInstConfigVO instConfig = amTradeClient.selectInstConfigByInstCode(instCode);
            // 机构编号
            if (instConfig == null) {
                logger.info("获取机构信息为空,机构编号:[" + instCode + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_ZC000004,"机构编号错误");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_ZC000004,"机构编号错误");
            }
            // 大额提现判断银行联行号
            if ((new BigDecimal(account).compareTo(new BigDecimal(50001)) > 0) && StringUtils.isBlank(payAllianceCode)) {
                logger.info("大额提现时,银行联行号不能为空");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"大额提现时,银行联行号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"大额提现时,银行联行号不能为空");
            }
            // 根据电子账户号查询用户ID
            BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(accountId);
            if (bankOpenAccount == null) {
                logger.info("查询用户开户信息失败,用户电子账户号:[" + accountId + "]");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
            }
            // 用户ID
            Integer userId = bankOpenAccount.getUserId();
            // 根据用户ID查询用户信息
            UserVO user = amUserClient.findUserById(userId);
            if (user == null) {
                logger.info("根据用户ID查询用户信息失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
            }

            // 检查是否设置交易密码
            Integer passwordFlag = user.getIsSetPassword();
            if (passwordFlag != 1) {// 未设置交易密码
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_TP000002,"未设置交易密码");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_TP000002,"未设置交易密码");
            }

            // 服务费授权状态和开关
            if (!checkPaymentAuthStatus(bankOpenAccount.getUserId())) {
                logger.info("用户未进行缴费授权,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000011,"用户未进行缴费授权");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000011,"用户未进行缴费授权");
            }
            // 根据用户ID查询用户详情
            UserInfoVO userInfo = amUserClient.findUserInfoById(userId);
            if (userInfo == null) {
                logger.info("根据用户ID查询用户详情失败,用户电子账户号:[" + accountId + ",用户ID:[" + userId + "]");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
            }
            // 身份证号
            String idNo = userInfo.getIdcard();
            // 姓名
            String trueName = userInfo.getTruename();
            // 用户手机号
            String mobile = user.getMobile();
            // 提现用户名
            String userName = user.getUsername();
            // 根据用户ID查询用户平台银行卡信息
            BankCardVO bankCard = amUserClient.getBankCardByUserId(userId);
            if (bankCard == null) {
                logger.info("根据用户ID查询用户银行卡信息失败,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
            }
            // 用户汇盈平台的银行卡卡号
            String localCardNo = bankCard.getCardNo() == null ? "" : bankCard.getCardNo();
            // 如果两边银行卡号不一致
            if (!cardNo.equals(localCardNo)) {
                logger.info("用户银行卡信息不一致,用户电子账户号:[" + accountId + "],请求银行卡号:[" + cardNo + "],平台保存的银行卡号:[" + localCardNo + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
            }

            // 根据用户ID查询用户账户信息
            AccountVO hyAccount = amTradeClient.getAccount(userId);
            // 取得账户为空
            if (hyAccount == null) {
                logger.info("根据用户ID查询用户账户信息失败,用户ID:[" + userId + "],电子账户号:[" + accountId + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000009,"查询用户账户信息失败");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000009,"查询用户账户信息失败");
            }
            // 提现金额大于汇盈账户余额
            if (new BigDecimal(account).compareTo(hyAccount.getBankBalance()) > 0) {
                logger.info("提现金额大于汇盈账户可用余额,用户ID:[" + userId + "],电子账户号:[" + accountId + "],提现金额:[" + account + "],账户可用余额:[" + hyAccount.getBankBalance() + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000010,"用户账户余额不足");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000010,"用户账户余额不足");
            }
            // 取得手续费 默认1
            // 11-23  改为从数据库中读取配置的手续费
            String fee = instConfig.getCommissionFee().toString();
            //String fee = this.userWithdrawService.getWithdrawFee(userId, cardNo, new BigDecimal(account));
            // 实际取现金额
            // 去掉一块钱手续费
            if (!(new BigDecimal(account).compareTo(new BigDecimal(fee)) > 0)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能小于手续费");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能小于手续费");
            }

            account = new BigDecimal(account).subtract(new BigDecimal(Validator.isNull(fee) ? "0" : fee)).toString();
            // 调用江西银行提现接口
            // 调用汇付接口(提现)

            String bankRetUrl =  systemConfig.getServerHost()+"/hyjf-api/server/user/withdraw/return?callback=" + retUrl.replace("#", "*-*-*");
            // 支付工程路径
            String bankBgRetUrl = "http://CS-TRADE/hyjf-api/server/user/withdraw/callback?callback=" + bgRetUrl.replace("#", "*-*-*");

            // 路由代码
            String routeCode = "";
            // 调用汇付接口(4.2.2 用户绑卡接口)
            BankCallBean bean = new BankCallBean();
            bean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
            bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());// 订单时间
            bean.setLogUserId(String.valueOf(userId));
            bean.setLogRemark("第三方服务接口:用户提现");
            bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_WITHDRAW);
            bean.setVersion(BankCallConstant.VERSION_10);// 接口版本号
            bean.setTxCode(BankCallMethodConstant.TXCODE_WITHDRAW);
            bean.setTxDate(GetOrderIdUtils.getTxDate());// 交易日期
            bean.setTxTime(GetOrderIdUtils.getTxTime());// 交易时间
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));// 交易流水号6位
            bean.setChannel(channel);// 交易渠道
            bean.setAccountId(accountId);// 存管平台分配的账号
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);// 证件类型01身份证
            bean.setIdNo(idNo);// 证件号
            bean.setName(trueName);// 姓名
            bean.setMobile(mobile);// 手机号
            bean.setCardNo(bankCard.getCardNo());// 银行卡号
            bean.setTxAmount(CustomUtil.formatAmount(account));
            bean.setTxFee(fee);

            // 扣除手续费
            if ((new BigDecimal(account).compareTo(new BigDecimal(50000)) > 0) && StringUtils.isNotBlank(payAllianceCode)) {
                routeCode = "2";// 路由代码
                bean.setCardBankCnaps(payAllianceCode);// 绑定银行联行号
            }
            if (routeCode.equals("2")) {
                bean.setRouteCode(routeCode);
                LogAcqResBean logAcq = new LogAcqResBean();
                logAcq.setPayAllianceCode(payAllianceCode);
                bean.setLogAcqResBean(logAcq);
            }
            // 企业用户提现
            if (user.getUserType() == 1) { // 企业用户 传组织机构代码
                CorpOpenAccountRecordVO record = amUserClient.getCorpOpenAccountRecord(userId);
                bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型
                bean.setIdNo(record.getBusiCode());
                bean.setName(record.getBusiName());
                bean.setRouteCode("2");
                bean.setCardBankCnaps(StringUtils.isEmpty(payAllianceCode) ? bankCard.getPayAllianceCode() : payAllianceCode);
            }
            bean.setForgotPwdUrl(systemConfig.getFrontHost()+systemConfig.getForgetpassword());
            bean.setForgotPwdUrl(userWithdrawRequestBean.getForgotPwdUrl());
            bean.setRetUrl(bankRetUrl+"&logOrderId="+bean.getLogOrderId());// 商户前台台应答地址(必须)
            bean.setNotifyUrl(bankBgRetUrl); // 商户后台应答地址(必须)
            logger.info("提现同步回调URL:[" + bean.getRetUrl() + "],异步回调URL:[" + bean.getNotifyUrl() + "].");
            // 插值用参数
            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", String.valueOf(userId));
            params.put("userName", userName);
            params.put("ip", CustomUtil.getIpAddr(request));
            params.put("cardNo", cardNo);
            params.put("fee", CustomUtil.formatAmount(fee));
            // 提现平台
            params.put("client", userWithdrawRequestBean.getPlatform()==null?"0":userWithdrawRequestBean.getPlatform());
            // 用户提现前处理
            BankCardVO bankCardVO = amUserClient.queryUserCardValid(String.valueOf(userId),cardNo);
            int cnt = this.updateBeforeCash(bean, params,bankCardVO);
            if (cnt > 0) {
                // 跳转到调用江西银行
                modelAndView = BankCallUtils.callApi(bean);
            } else {
                logger.info("提现前,插入提现记录失败");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> retParams = asyncParam(userWithdrawRequestBean, ErrorCodeConstant.STATUS_CE000001,"提现异常");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), retParams);
                }
                return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
            }
        } catch (Exception e) {
            logger.info("提现发生异常,异常信息:[" + e.getMessage() + "].");
            // 异步回调URL
            if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                Map<String, String> retParams = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
                CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), retParams);
            }
            return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
        }
        map.put("modelAndView",modelAndView);
        return map;
    }

    public  Map<String,Object> fallBackApiWithdraw(UserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request) {
        logger.info("==================已进入 提现(api) fallBackApiWithdraw 方法================");
        return syncParamForMap(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"请求参数异常");
    }

    /**
     * 用户提现更新数据表
     * @auth sunpeikai
     * @param
     * @return
     */
    private int updateBeforeCash(BankCallBean bean, Map<String,String> params, BankCardVO bankCardVO){
        ApiUserWithdrawRequest request = new ApiUserWithdrawRequest();
        request.setBankCallBeanVO(CommonUtils.convertBean(bean,BankCallBeanVO.class));
        request.setParams(params);
        request.setBankCardVO(bankCardVO);
        return amTradeClient.updateBeforeCash(request);
    }

    /**
     * 同步返回参数组装
     * @auth sunpeikai
     * @param
     * @return
     */
    private ModelAndView syncParam(UserWithdrawResultBean userWithdrawResultBean,UserWithdrawRequestBean userWithdrawRequestBean,String status,String statusDesc){
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        ModelAndView modelAndView = new ModelAndView("api/api_error_send.html");
        userWithdrawResultBean.setCallBackAction(userWithdrawRequestBean.getRetUrl());
        userWithdrawResultBean.set("accountId", userWithdrawRequestBean.getAccountId());
        userWithdrawResultBean.set("statusDesc", statusDesc);
        userWithdrawResultBean.set("status", resultBean.getStatus());
        userWithdrawResultBean.set("chkValue", resultBean.getChkValue());
        modelAndView.addObject("callBackForm", userWithdrawResultBean);
        return modelAndView;
    }

    /**
     * 同步返回参数组装
     * @auth sunpeikai
     * @param
     * @return
     */
    private Map<String,Object> syncParamForMap(UserWithdrawRequestBean userWithdrawRequestBean,String status,String statusDesc){
        Map<String,Object> result = new HashMap<>();
        result.put("callBackAction",userWithdrawRequestBean.getRetUrl());
        result.put("accountId", userWithdrawRequestBean.getAccountId());
        result.put("statusDesc", statusDesc);
        result.put("status",status);
        return result;
    }


    /**
     * 异步返回参数组装
     * @auth sunpeikai
     * @param
     * @return
     */
    private Map<String, String> asyncParam(UserWithdrawRequestBean userWithdrawRequestBean,String status,String statusDesc){
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        Map<String, String> retParams = new HashMap<String, String>();
        retParams.put("accountId", userWithdrawRequestBean.getAccountId());
        retParams.put("statusDesc", statusDesc);
        retParams.put("status", resultBean.getStatus());
        retParams.put("chkValue", resultBean.getChkValue());
        return retParams;
    }

    /**
     *
     * 检查服务费授权状态
     * @author sunss
     * @param userId
     * @return
     */
    public boolean checkPaymentAuthStatus(Integer userId) {
        // 如果用户ID没有 直接成功吧 不会出现这种
        if (userId == null) {
            return true;
        }
        // 检查开关是否打开 没打开 不用校验
/*        HjhUserAuthConfig hjhUserAuthConfig=RedisUtils.getObj(key,HjhUserAuthConfig.class);
        if (CommonUtils.getAuthConfigFromCache(CommonUtils.KEY_PAYMENT_AUTH).getEnabledStatus() - 1 != 0) {
            return true;
        }*/
        HjhUserAuthVO auth = amUserClient.getHjhUserAuthByUserId(userId);
        if (auth == null || auth.getAutoPaymentStatus() - 1 != 0) {
            return false;
        }
        return true;
    }

    /**
     * api用户提现后,同步处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public  Map<String,Object> cashReturn(HttpServletRequest request, BankCallBean bean) {
        logger.info("用户提现后,同步处理");
        Map<String,Object> result =new HashMap<>();
        bean.convert();
        String logOrderId = request.getParameter("logOrderId") == null ? "" : request.getParameter("logOrderId") ;
        // 提现订单号
        logger.info("提现订单号:[" + logOrderId + "].");
        String url = request.getParameter("callback").replace("*-*-*", "#");
        AccountWithdrawVO accountWithdrawVO = amTradeClient.getAccountWithdrawByOrderId(logOrderId);
        // 提现成功
        if (accountWithdrawVO != null) {
            logger.info("提现成功,提现订单号:[" + logOrderId + "]");
            result.put("amt",String.valueOf(accountWithdrawVO.getTotal()));// 交易金额
            result.put("arrivalAmount",String.valueOf(accountWithdrawVO.getCredited()));// 到账金额
            result.put("fee",accountWithdrawVO.getFee());// 提现手续费
            result.put("statusDesc", "提现成功");
            result.put("status", ErrorCodeConstant.SUCCESS);
            result.put("amt", String.valueOf(accountWithdrawVO.getTotal()));// 交易金额
            result.put("arrivalAmount", String.valueOf(accountWithdrawVO.getCredited()));// 到账金额
            result.put("fee", accountWithdrawVO.getFee());// 提现手续费
            result.put("orderId", accountWithdrawVO.getNid());// 提现订单号
        } else {
            logger.info("银行处理中,请稍后查询交易明细");
            result.put("statusDesc", "银行处理中,请稍后查询交易明细");
            result.put("status", ErrorCodeConstant.STATUS_CE000005);
        }
        logger.info("url:=="+url);
        result.put("callBackAction",url);
        return result;
    }

    /**
     * 用户提现异步回调处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public BankCallResult withdrawBgReturn(HttpServletRequest request, BankCallBean bean) {
        UserWithdrawResultBean resultBean = new UserWithdrawResultBean();
        String logOrderId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId();
        bean.convert();
        BankCallResult result = new BankCallResult();
        try {
            String status ="";
            String message = "";
            Integer userId = Integer.parseInt(bean.getLogUserId()); // 用户ID
            resultBean.setCallBackAction(request.getParameter("callback"));
            // 插值用参数
            Map<String, String> params = new HashMap<String, String>();
            params.put("userId", String.valueOf(userId));
            params.put("ip", CustomUtil.getIpAddr(request));
            // 执行提现后处理
            ApiUserWithdrawRequest userWithdrawRequest = new ApiUserWithdrawRequest();
            BankCardVO bankCardVO = amUserClient.getBankCardByUserId(userId);
            userWithdrawRequest.setBankCallBeanVO(CommonUtils.convertBean(bean,BankCallBeanVO.class));
            userWithdrawRequest.setParams(params);
            String resultStr = amTradeClient.handlerAfterCash(userWithdrawRequest);
            JSONObject jsonObject = JSONObject.parseObject(resultStr);
            boolean flag = jsonObject.getBoolean("flag");
            // 提现成功后,更新银行联行号
            // 大额提现返回成功 并且银联行号不为空的情况,将正确的银联行号更新到bankCard表中
            // 原本在原子层逻辑中插入，但是涉及到跨库
            if(flag){
                // 银联行号
                String payAllianceCode = bean.getLogAcqResBean() == null ? "" : bean.getLogAcqResBean().getPayAllianceCode();
                logger.info("银联行号:" + payAllianceCode);
                if ("CE999028".equals(bean.getRetCode()) && StringUtils.isNotEmpty(payAllianceCode)) {
                    bankCardVO.setPayAllianceCode(payAllianceCode);
                    boolean isBankCardUpdateFlag = amUserClient.updateBankCard(bankCardVO) > 0;
                    if (!isBankCardUpdateFlag) {
                        throw new Exception("大额提现成功后,更新用户银行卡的银联行号失败~~~!" + bankCardVO.getId());
                    }
                }
            }

            AccountWithdrawVO accountWithdrawVO = amTradeClient.getAccountWithdrawByOrderId(logOrderId);
            // 提现成功
            if (accountWithdrawVO != null) {
                logger.info("提现成功,提现订单号:[" + logOrderId + "]");
                status = ErrorCodeConstant.SUCCESS;
                resultBean.setAmt(String.valueOf(accountWithdrawVO.getTotal()));// 交易金额
                resultBean.setArrivalAmount(String.valueOf(accountWithdrawVO.getCredited()));// 到账金额
                resultBean.setFee(accountWithdrawVO.getFee());// 提现手续费
                message="提现成功";
            } else {
                logger.info("银行处理中,请稍后查询交易明细");
//                resultBean.setStatus("2");
//                resultBean.setStatusDesc("银行处理中,请稍后查询交易明细");
                message = "银行处理中,请稍后查询交易明细";
                status = ErrorCodeConstant.STATUS_CE000005;
            }
            logger.info("用户提现异步回调end");
            params.put("accountId",bean.getAccountId());
            params.put("orderId",logOrderId);
            params.put("status", status);
            params.put("statusDesc",message);
            BaseResultBean baseResultBean = new BaseResultBean();
            baseResultBean.setStatusForResponse(status);
            params.put("chkValue", baseResultBean.getChkValue());
            CommonSoaUtils.noRetPostThree(request.getParameter("callback").replace("*-*-*","#"), params);
        } catch (Exception e) {
            logger.info("提现失败,用户提现发生异常,错误信息:[" + e.getMessage() + "]");
//            resultBean.setStatus(BaseResultBean.STATUS_FAIL);
//            resultBean.setStatusDesc("提现失败");
        }
        result.setStatus(true);
        return result;
    }

    /**
     * 获取用户提现记录
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public BaseResultBean getUserWithdrawRecord(UserWithdrawRequestBean userWithdrawRequestBean) {
        logger.info("userWithdrawRequestBean:"+JSONObject.toJSONString(userWithdrawRequestBean));
        UserWithdrawRecordResultBean result=new UserWithdrawRecordResultBean();
        if (Validator.isNull(userWithdrawRequestBean.getAccountId())||
                Validator.isNull(userWithdrawRequestBean.getLimitEnd())||
                Validator.isNull(userWithdrawRequestBean.getLimitStart())||
                Validator.isNull(userWithdrawRequestBean.getInstCode())) {

            logger.info("-------------------请求参数非法--------------------");
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.setStatusDesc("请求参数非法");
            return result;
        }

        //验签
        if(!SignUtil.verifyRequestSign(userWithdrawRequestBean, "/getUserWithdrawRecord")){
            logger.info("-------------------验签失败！--------------------");
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.setStatusDesc("验签失败");
            return result;
        }

        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectBankOpenAccountByAccountId(userWithdrawRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+userWithdrawRequestBean.getAccountId()+"！--------------------");
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.setStatusDesc("没有根据电子银行卡找到用户");
            return result;
        }
        UserVO user = amUserClient.findUserById(bankOpenAccount.getUserId());//用户ID
        if(user == null){
            logger.info("---用户不存在汇盈金服账户---");
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.setStatusDesc("用户不存在汇盈金服账户");
            return result;
        }
        if (user.getBankOpenAccount()==0) {
            logger.info("-------------------没有根据电子银行卡找到用户"+userWithdrawRequestBean.getAccountId()+"！--------------------");
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.setStatusDesc("没有根据电子银行卡找到用户");
            return result;
        }

        if (userWithdrawRequestBean.getInstCode()==null||!userWithdrawRequestBean.getInstCode().equals(user.getInstCode())) {
            logger.info("-------------------该电子账户号非本平台注册不能查询"+userWithdrawRequestBean.getAccountId()+"！--------------------");
            result.setStatus(BaseResultBean.STATUS_FAIL);
            result.setStatusDesc("该电子账户号非本平台注册不能查询");
            return result;
        }

        // 查询用户提现记录列表 做格式化等操作
        List<UserWithdrawRecordCustomizeVO> recordList =
                this.getThirdPartyUserWithdrawRecord(user.getUserId(),userWithdrawRequestBean.getStatus(),userWithdrawRequestBean.getLimitStart(),userWithdrawRequestBean.getLimitEnd());
        logger.info("用户提现记录列表【{}】", JSON.toJSONString(recordList));
        result.setRecordList(recordList);
        result.setStatus(BaseResultBean.STATUS_SUCCESS);
        result.setStatusDesc(BaseResultBean.STATUS_DESC_SUCCESS);
        // 返回查询结果
        return result;
    }

    /**
     * 查询用户提现记录列表
     * @auth sunpeikai
     * @param
     * @return
     */
    private List<UserWithdrawRecordCustomizeVO> getThirdPartyUserWithdrawRecord(Integer userId,String status,Integer limitStart,Integer limitEnd){
        UserVO userVO = amUserClient.findUserById(userId);
        UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
        List<UserWithdrawRecordCustomizeVO> resultList = new ArrayList<>();
        ApiUserWithdrawRequest request = new ApiUserWithdrawRequest();
        request.setUserId(userId);
        request.setStatus(Integer.valueOf(status));
        request.setLimitStart(limitStart);
        request.setLimitEnd(limitEnd);
        List<AccountWithdrawVO> accountWithdrawVOList = amTradeClient.searchAccountWithdrawByUserIdPaginate(request);
        Map<String,String> map = CacheUtil.getParamNameMap("WITHDRAW_STATUS");
        for(AccountWithdrawVO accountWithdrawVO:accountWithdrawVOList){
            UserWithdrawRecordCustomizeVO result = new UserWithdrawRecordCustomizeVO();
            result.setUsername(userVO.getUsername());
            result.setTruename(userInfoVO.getTruename());
            result.setTotal(accountWithdrawVO.getTotal().toString());
            result.setCredited(accountWithdrawVO.getCredited().toString());
            result.setFee(accountWithdrawVO.getFee());
            BankCardVO bankCardVO = amUserClient.getBankCardById(accountWithdrawVO.getBankId());
            String cardNo = bankCardVO.getCardNo();
            result.setCardNo((cardNo==null || "".equals(cardNo))?"银行卡已删除":cardNo);
            result.setBank(accountWithdrawVO.getBank());
            result.setStatus(map.get(String.valueOf(accountWithdrawVO.getStatus())));
            result.setWithdrawTime(String.valueOf(accountWithdrawVO.getCreateTime()));
            result.setWithdrawTime(GetDate.dateToString(accountWithdrawVO.getCreateTime()));
            resultList.add(result);
        }
        return resultList;
    }

    /**
     * 提现成功后,发送神策数据统计MQ
     *
     * @param sensorsDataBean
     */
    private void sendSensorsDataMQ(SensorsDataBean sensorsDataBean) throws MQException {
        this.commonProducer.messageSendDelay(new MessageContent(MQConstant.SENSORSDATA_WITHDRAW_TOPIC, UUID.randomUUID().toString(), sensorsDataBean), 2);
    }

}
