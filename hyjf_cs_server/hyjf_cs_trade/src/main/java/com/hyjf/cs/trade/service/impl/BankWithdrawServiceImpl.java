package com.hyjf.cs.trade.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.BankWithdrawBeanRequest;
import com.hyjf.am.vo.config.FeeConfigVO;
import com.hyjf.am.vo.message.AppMsMessage;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.*;
import com.hyjf.am.vo.trade.account.AccountListVO;
import com.hyjf.am.vo.trade.account.AccountRechargeVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.am.vo.user.BankCardVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.common.bean.result.WebResult;
import com.hyjf.cs.trade.bean.BankCardBean;
import com.hyjf.cs.trade.bean.CheckResult;
import com.hyjf.cs.trade.bean.WebViewUser;
import com.hyjf.cs.trade.client.*;
import com.hyjf.cs.trade.config.SystemConfig;
import com.hyjf.cs.trade.constants.BankWithdrawError;
import com.hyjf.cs.trade.mq.AppMessageProducer;
import com.hyjf.cs.trade.mq.Producer;
import com.hyjf.cs.trade.mq.SmsProducer;
import com.hyjf.cs.trade.service.BankWithdrawService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallParamConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pangchengchao
 * @version BankWithdrawServiceImpl, v0.1 2018/6/14 14:32
 *//*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
@Service
public class BankWithdrawServiceImpl implements BankWithdrawService {
    private static final Logger logger = LoggerFactory.getLogger(WebBorrowServiceImpl.class);

    @Autowired
    AmUserClient amUserClient;

    @Autowired
    BankOpenClient bankOpenClient;

    @Autowired
    BindCardClient bindCardClient;

    @Autowired
    AccountListClient accountListClient;

    @Autowired
    WithdrawClient withdrawClient;

    @Autowired
    SystemConfig systemConfig;

    @Autowired
    AppMessageProducer appMessageProducer;

    @Autowired
    SmsProducer smsProducer;

    @Autowired
    private BankWithdrawClient bankWithdrawClient;//银行提现掉单

    @Value("${hyjf.bank.fee}")
    private String FEETMP;
    /**
     * url header获取
     */
    public static final Map<Integer,String> CLIENT_HEADER_MAP = new HashMap<Integer, String>(){{
        put(0,"web");
        put(1,"wechat");
        put(2,"app");
        put(3,"app");
    }};

    @Override
    public BankCallBean getUserBankWithdrawView(UserVO user, String transAmt, String cardNo, String payAllianceCode, String platform, String channel, String ip) {


        //检查用户信息
        UserVO users = this.checkUserWithdrawMessage(user, transAmt, cardNo, payAllianceCode);

        // 取得手续费 默认1
        String fee = this.getWithdrawFee(user.getUserId(), cardNo);
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(users, platform, channel, transAmt, cardNo, payAllianceCode, fee);
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


    @Override
    public Map<String, String> userBankWithdrawReturn(BankCallBean bean, String isSuccess, String wifee, String withdrawmoney) {
        Map<String, String> map=new HashMap<String, String>();
        bean.convert();
        String logOrderId = bean.getLogOrderId();
        AccountWithdrawVO accountwithdraw = this.withdrawClient.getAccountWithdrawByOrdId(logOrderId);
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
        AccountVO account = this.bindCardClient.getAccount(userId);
        // 根据用户ID查询用户银行卡信息
        BankCardVO bankCard = this.bankOpenClient.selectBankCardByUserId(userId);
        String ordId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId(); // 订单号
        // 银联行号
        String payAllianceCode = bean.getLogAcqResBean() == null ? "" : bean.getLogAcqResBean().getPayAllianceCode();
        int nowTime = GetDate.getNowTime10(); // 当前时间
        List<AccountWithdrawVO> listAccountWithdraw = this.withdrawClient.selectAccountWithdrawByOrdId(ordId);

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
                    int accountlistCnt = this.accountListClient.countAccountListByOrdId(ordId,"cash_success");
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
                                    boolean isBankCardUpdateFlag = this.bindCardClient.updateBankCardPayAllianceCode(updateBankCardVO) > 0 ? true : false;
                                    if (!isBankCardUpdateFlag) {
                                        throw new Exception("大额提现成功后,更新用户银行卡的银联行号失败~~~!" + bankCard.getId());
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                            // 提现金额
                            BigDecimal transAmt = bean.getBigDecimal(BankCallParamConstant.PARAM_TXAMOUNT);
                            String fee = this.getWithdrawFee(userId, bankCard.getCardNo());
                            // 提现手续费
                            BigDecimal feeAmt = new BigDecimal(fee);
                            // 总的交易金额
                            BigDecimal total = transAmt.add(feeAmt);
                            //构建提现保存信息
                            BankWithdrawBeanRequest bankWithdrawBeanRequest=createBankWithdrawBeanRequest(accountWithdraw,transAmt,fee,feeAmt,total,userId,ordId,nowTime,bean.getAccountId(),params.get("ip"));

                            boolean isAccountListFlag = withdrawClient.updatUserBankWithdrawHandler(bankWithdrawBeanRequest) > 0 ? true : false;
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
                                SmsMessage smsMessage = new SmsMessage(userId, replaceMap, null, null, MessageConstant.SMSSENDFORUSER, null, CustomConstants.PARAM_TPL_TIXIAN_SUCCESS,
                                        CustomConstants.CHANNEL_TYPE_NORMAL);
                                AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                                smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC,
                                        JSON.toJSONBytes(smsMessage)));
                                appMessageProducer.messageSend(new Producer.MassageContent(MQConstant.APP_MESSAGE_TOPIC,
                                        JSON.toJSONBytes(appMsMessage)));

                            } else {
                                // 替换参数
                                Map<String, String> replaceMap = new HashMap<String, String>();
                                replaceMap.put("val_amount", total.toString());
                                replaceMap.put("val_fee", feeAmt.toString());
                                UserInfoVO info = amUserClient.findUsersInfoById(userId);
                                replaceMap.put("val_name", info.getTruename().substring(0, 1));
                                replaceMap.put("val_sex", info.getSex() == 2 ? "女士" : "先生");
                                AppMsMessage appMsMessage = new AppMsMessage(userId, replaceMap, null, MessageConstant.APPMSSENDFORUSER, CustomConstants.JYTZ_TPL_TIXIAN_SUCCESS);
                                appMessageProducer.messageSend(new Producer.MassageContent(MQConstant.APP_MESSAGE_TOPIC,
                                        JSON.toJSONBytes(appMsMessage)));
                            }
                            // TODO 活动统计 需要的时候放开
                            /*CommonSoaUtils.listedTwoWithdraw(userId, total);*/
                            return jsonMessage("提现成功!", "0");
                        } catch (Exception e) {
                            // 回滚事务
                            e.printStackTrace();
                            return jsonMessage("提现失败,请联系客服!", "1");
                        }
                    }
                }
            } else {
                // 提现失败,更新订单状态
                List<AccountWithdrawVO> list = this.withdrawClient.selectAccountWithdrawByOrdId(ordId);
                if (list != null && list.size() > 0) {
                    AccountWithdrawVO accountwithdraw = list.get(0);
                    //mod by nxl 将状态更改为提现中
                    accountwithdraw.setStatus(WITHDRAW_STATUS_WAIT);
                    accountwithdraw.setUpdateTime(nowTime);
                    // 失败原因
                    String reason = this.getBankRetMsg(bean.getRetCode());
                    accountwithdraw.setReason(reason);
                    boolean isUpdateFlag = this.withdrawClient.updateAccountwithdrawLog(accountwithdraw) > 0 ? true : false;
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
    public WebResult<Object> toWithdraw(WebViewUser user) {
        Integer userId = user.getUserId();
        WebResult<Object> result = new WebResult<Object>();
        JSONObject ret = new JSONObject();
        DecimalFormat DF_FOR_VIEW = new DecimalFormat("#,##0.00");
        // 取得用户当前余额
        AccountVO account = this.bindCardClient.getAccount(userId);
        if (account == null) {
            result.setStatus(BankWithdrawError.ACCOUNT_ERROR.getCode());
            result.setStatusDesc(BankWithdrawError.ACCOUNT_ERROR.getMsg());
            return result;
        }
        // 查询页面上可以挂载的银行列表
        BankCardVO banks = bankOpenClient.selectBankCardByUserId(userId);
        if (banks == null) {
            // 用户未绑卡
            result.setStatus(BankWithdrawError.NOT_CARD_NO_ERROR.getCode());
            result.setStatusDesc(BankWithdrawError.NOT_CARD_NO_ERROR.getMsg());
            return result;
        }

        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        //用户角色
        String userRoId = userInfoVO.getRoleId() + "";
        //用户的可用金额
        BigDecimal bankBalance = account.getBankBalance();
        //查询用户投资记录
        int borrowTender = withdrawClient.getBorrowTender(userId);
        if (StringUtils.equals("1", userRoId)) {
            if (borrowTender <= 0) {
                //查询用户的24小时内充值记录
                List<AccountRechargeVO> todayRecharge = withdrawClient.getTodayRecharge(userId);
                if (todayRecharge != null && !todayRecharge.isEmpty()) {
                    // 计算用户当前可提现金额
                    for (AccountRechargeVO recharge : todayRecharge) {
                        bankBalance = bankBalance.subtract(recharge.getBalance());
                    }
                }
            }
        }

        ret.put("total", CustomConstants.DF_FOR_VIEW.format(account.getBankBalance()));// 可提现金额
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
            bankcards.add(bankCardBean);

            Integer bankId = banks.getBankId();
            BanksConfigVO banksConfig = bindCardClient.getBanksConfigByBankId(bankId + "");
            if (banksConfig != null && StringUtils.isNotEmpty(banksConfig.getBankName())) {
                bankCardBean.setBank(banksConfig.getBankName());
            }

            feeWithdraw = this.getWithdrawFee(userId, banks.getCardNo());
        }

        ret.put("userType", user.getUserType());// 是否为企业用户（
        ret.put("banks", bankcards);
        ret.put("isSetPassWord", user.getIsSetPassword());
        ret.put("payAllianceCode", payAllianceCode);
        ret.put("bankType", bankType);
        ret.put("feeWithdraw", DF_FOR_VIEW.format(new BigDecimal(feeWithdraw)));
        ret.put("roleId", user == null ? "" : user.getRoleId());
//		modelAndView.addObject("paymentAuthStatus", user.getPaymentAuthStatus());
        //update by jijun 2018/04/09 合规接口改造一期
        ret.put("paymentAuthStatus", "");
        ret.put("bankBalance", CustomConstants.DF_FOR_VIEW.format(bankBalance));
        result.setData(ret);
        return result;
    }

    @Override
    public void withdraw() {
        List<AccountWithdrawVO> withdrawList = this.bankWithdrawClient.selectBankWithdrawList();
        if (CollectionUtils.isNotEmpty(withdrawList)){
            for (AccountWithdrawVO accountWithdraw : withdrawList) {
                this.updateWithdraw(accountWithdraw);
            }
        }
    }

    /**
     * 更新处理中的提现记录
     * @param accountWithdraw
     */
    private void updateWithdraw(AccountWithdrawVO accountWithdraw) {
        try {
            //调用银行接口
            BankCallBeanVO bean = bankWithdrawClient.bankCallFundTransQuery(accountWithdraw);
            if (bean != null) {
                //调用后平台操作
                this.handlerAfterCash(bean, accountWithdraw);
            }else{
                throw new Exception("调用银行接口,银行返回NULL,充值订单号:"
                        + accountWithdraw.getNid()
                        + ",用户Id:" + accountWithdraw.getUserId());
            }
        } catch (Exception e) {
            logger.info("更新处理中的提现记录出錯...");
        }
    }

    /**
     * 用户提现回调方法
     * @param bean
     * @param accountWithdraw
     * @throws Exception
     */
    private void handlerAfterCash(BankCallBeanVO bean, AccountWithdrawVO accountWithdraw) throws Exception {

        // 当前时间
        int nowTime = GetDate.getNowTime10();
        // 用户ID
        int userId = accountWithdraw.getUserId();
        // 提现订单号
        String ordId = accountWithdraw.getNid();
        // 根据用户ID查询用户银行卡信息
        String bankId=bean.getBankCode();

        BankCardVO bankCard = this.bankWithdrawClient.selectBankCardByUserId(userId);

        // 1.该银行接口的 业务是否成功
        // 返回值=000成功 ,大额提现返回值为 CE999028
        // 并且Result = "00"
        // 冲正撤销标志为0
        // 返回retcode的错误码和result返回其他这两个都是有可能的，具体的是哪个和银行内部的操作有关，所以retcode和result这个你们都需要判断下
        // 其它:无该交易或者处理失败
        if ((BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) || "CE999028".equals(bean.getRetCode()))
                && "00".equals(bean.getResult())
                && !("1".equals(bean.getOrFlag()))) {

            //2.银行接口返回与本地记录匹配验证
            CheckResult rtCheck = this.checkCallRetAndHyjf(bean,accountWithdraw);
            if (!rtCheck.isResultBool()) {
                // 验证失败，异常信息抛出
                throw new Exception(rtCheck.getResultMsg());
            }

            //3.DB防并发处理
            rtCheck = this.checkConcurrencyDB(accountWithdraw, userId, ordId);;
            if (!rtCheck.isResultBool()) {
                // 记录被其他进程处理，日志信息输出
                logger.info(this.getClass().getName(), "handlerAfterCash",
                        rtCheck.getResultMsg());
                return;
            }


            //4.DB更新操作
            // 提现金额
            BigDecimal transAmt = new BigDecimal(bean.getTxAmount());

            String fee = this.getWithdrawFee(userId,bankCard == null ? "" : String.valueOf(bankCard.getBankId()),transAmt);
            // 提现手续费
            BigDecimal feeAmt = new BigDecimal(fee);
            // 总的交易金额
            BigDecimal total = transAmt.add(feeAmt);
            // 更新订单信息
            accountWithdraw.setFee((CustomUtil.formatAmount(feeAmt.toString()))); // 更新手续费
            accountWithdraw.setCredited(transAmt); // 更新到账金额
            accountWithdraw.setTotal(total); // 更新到总额
            accountWithdraw.setStatus(WITHDRAW_STATUS_SUCCESS);// 4:成功
            accountWithdraw.setUpdateTime(nowTime);
            accountWithdraw.setAccount(bean.getAccountId());
            accountWithdraw.setReason("");

            boolean isAccountwithdrawFlag=this.bankWithdrawClient.updateAccountwithdraw(accountWithdraw);
            if (!isAccountwithdrawFlag) {
                throw new Exception("提现后,更新用户提现记录表失败!" + "提现订单号:" + ordId + ",用户ID:" + userId);
            }
            AccountVO newAccount = new AccountVO();
            // 更新账户信息
            newAccount.setUserId(userId);// 用户Id
            newAccount.setBankTotal(total); // 累加到账户总资产
            newAccount.setBankBalance(total); // 累加可用余额
            newAccount.setBankBalanceCash(total);// 江西银行可用余额

            //更新银行提现
            boolean isAccountUpdateFlag = this.bankWithdrawClient.updateBankWithdraw(newAccount);
            if (!isAccountUpdateFlag) {
                throw new Exception("提现后,更新用户Account表失败!" + "提现订单号:" + ordId + ",用户ID:" + userId);
            }
            // 重新获取用户信息
            AccountVO account = this.bankWithdrawClient.getAccount(userId);
            // 写入收支明细
            AccountListVO accountList = new AccountListVO();
            accountList.setNid(ordId);
            accountList.setUserId(userId);
            accountList.setAmount(total);
            accountList.setType(2);
            accountList.setTrade("cash_success");
            accountList.setTradeCode("balance");
            accountList.setTotal(account.getTotal());
            accountList.setBalance(account.getBalance());
            accountList.setFrost(account.getFrost());
            accountList.setAwait(account.getAwait());
            accountList.setRepay(account.getRepay());
            accountList.setBankTotal(account.getBankTotal()); // 银行总资产
            accountList.setBankBalance(account.getBankBalance()); // 银行可用余额
            accountList.setBankFrost(account.getBankFrost());// 银行冻结金额
            accountList.setBankWaitCapital(account.getBankWaitCapital());// 银行待还本金
            accountList.setBankWaitInterest(account.getBankWaitInterest());// 银行待还利息
            accountList.setBankAwaitCapital(account.getBankAwaitCapital());// 银行待收本金
            accountList.setBankAwaitInterest(account.getBankAwaitInterest());// 银行待收利息
            accountList.setBankAwait(account.getBankAwait());// 银行待收总额
            accountList.setBankInterestSum(account.getBankInterestSum()); // 银行累计收益
            accountList.setBankInvestSum(account.getBankInvestSum());// 银行累计投资
            accountList.setBankWaitRepay(account.getBankWaitRepay());// 银行待还金额
            accountList.setPlanBalance(account.getPlanBalance());//汇计划账户可用余额
            accountList.setPlanFrost(account.getPlanFrost());
            accountList.setSeqNo(bean.getSeqNo());
            accountList.setTxDate(Integer.parseInt(bean.getTxDate()));
            accountList.setTxTime(Integer.parseInt(bean.getTxTime()));
            accountList.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
            accountList.setAccountId(bean.getAccountId());
            accountList.setRemark(accountWithdraw.getRemark());//TODO
            accountList.setCreateTime(nowTime);
            accountList.setBaseUpdate(nowTime);
            accountList.setOperator(String.valueOf(userId));
            accountList.setIp(accountWithdraw.getAddip());//TODO
            accountList.setIsUpdate(0);
            accountList.setBaseUpdate(0);
            accountList.setInterest(null);
            accountList.setIsBank(1);
            accountList.setWeb(0);
            accountList.setCheckStatus(0);// 对账状态0：未对账 1：已对账
            accountList.setTradeStatus(1);// 0失败1成功2失败

            boolean isAccountListFlag = this.bankWithdrawClient.addAccountList(accountList);
            if (!isAccountListFlag) {
                throw new Exception("提现成功后,插入交易明细表失败~!" + "提现订单号:" + ordId + ",用户ID:" + userId);
            }
        } else {
            // 提现失败,更新处理中订单状态为失败
            JSONObject paraMap = new JSONObject();
            paraMap.put("ordId",ordId);
            paraMap.put("accountWithdraw",accountWithdraw);
            paraMap.put("bankCallBeanVO",bean);
            this.bankWithdrawClient.selectAndUpdateAccountWithdraw(paraMap);
        }


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
        int accountlistCnt = this.bankWithdrawClient.getAccountlistCntByOrdId(ordId,"cash_success");
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
        BankCardVO bankCard = this.bankWithdrawClient.getBankInfo(userId, bankId);
        if (FEETMP == null) {
            FEETMP = "1";
        }
        if (bankCard != null) {
            String bankCode = bankCard.getBank();
            // 取得费率
            List<FeeConfigVO> listFeeConfig = this.bankWithdrawClient.getFeeConfig(bankCode);

            if (listFeeConfig != null && listFeeConfig.size() > 0) {
                FeeConfigVO feeConfig = listFeeConfig.get(0);
                BigDecimal takout = BigDecimal.ZERO;
                BigDecimal percent = BigDecimal.ZERO;
                if (Validator.isNotNull(feeConfig.getNormalTakeout()) && NumberUtils.isNumber(feeConfig.getNormalTakeout())) {
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
        accountWithdraw.setUpdateTime(nowTime);
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
            throw new ReturnMessageException(BankWithdrawError.USER_LOGIN_ERROR);
        }
        // 检查数据是否完整
        if (Validator.isNull(transAmt) || Validator.isNull(transAmt)) {
            throw new ReturnMessageException(BankWithdrawError.WITHDRAW_TRSANSAMT_ERROR);
        }
        if (Validator.isNull(cardNo) || Validator.isNull(cardNo)) {
            throw new ReturnMessageException(BankWithdrawError.PARAM_ERROR);
        }

        // 检查参数(交易金额是否大于0)
        BigDecimal account = new BigDecimal(transAmt);
        String feetmp = ClientConstants.BANK_FEE;
        if (feetmp == null) {
            feetmp = "1";
        }
        if (account.compareTo(new BigDecimal(feetmp)) <= 0) {
            throw new ReturnMessageException(BankWithdrawError.WITHDRAW_SERVICE_CHARGE_ERROR);
        }
        // 检查参数(银行卡ID是否数字)
        if (Validator.isNotNull(cardNo) && !NumberUtils.isNumber(cardNo)) {
            throw new ReturnMessageException(BankWithdrawError.WITHDRAW_CARD_NO_ERROR);
        }
        UserVO users= amUserClient.findUserById(user.getUserId());
        if (users.getBankOpenAccount()==0) {
            throw new ReturnMessageException(BankWithdrawError.NOT_REGIST_ERROR);
        }
        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            throw new ReturnMessageException(BankWithdrawError.NOT_SET_PWD_ERROR);
        }

        if ((account.compareTo(new BigDecimal(50001)) > 0) && StringUtils.isBlank(payAllianceCode)) {
            throw new ReturnMessageException(BankWithdrawError.WITHDRAW_PAYALLIANCECODE_ERROR);
        }

        BankCardVO bankCard = this.bindCardClient.queryUserCardValid(user.getUserId()+"", cardNo);
        if (bankCard == null || Validator.isNull(bankCard.getCardNo())) {
            throw new ReturnMessageException(BankWithdrawError.NOT_CARD_NO_ERROR);
        }

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
        int ret = 0;
        String ordId = bean.getLogOrderId() == null ? bean.get("OrdId") : bean.getLogOrderId(); // 订单号
        List<AccountWithdrawVO> listAccountWithdraw = this.withdrawClient.selectAccountWithdrawByOrdId(ordId);
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
        BankCardVO bankCard = this.bindCardClient.queryUserCardValid(user.getUserId()+"", cardNo);
        if (bankCard != null) {
            bank = bankCard.getBank();
        }
        AccountWithdrawVO record = new AccountWithdrawVO();
        record.setUserId(userId);
        record.setNid(bean.getLogOrderId()); // 订单号
        //record.setStatus(WITHDRAW_STATUS_WAIT); // 状态: 0:处理中
        // mod by nxl 将体现初始状态设置为初始值
        record.setStatus(WITHDRAW_STATUS_DEFAULT); // 状态: 0:初始值
        record.setAccount(cardNo);// 提现银行卡号
        record.setBank(bank); // 提现银行
        record.setBankId(bankCard.getId());
        record.setBranch(null);
        record.setProvince(0);
        record.setCity(0);
        record.setTotal(total);
        record.setCredited(money);
        record.setBankFlag(1);
        record.setFee(CustomUtil.formatAmount(fee.toString()));
        record.setAddtime(String.valueOf(nowTime));
        record.setAddip(params.get("ip"));
        record.setAccountId(bean.getAccountId());

        record.setBankSeqNo(bean.getTxDate() + bean.getTxTime() + bean.getSeqNo());
        record.setTxDate(Integer.parseInt(bean.getTxDate()));
        record.setTxTime(Integer.parseInt(bean.getTxTime()));
        record.setSeqNo(Integer.parseInt(bean.getSeqNo()));
        record.setRemark("网站提现");
        record.setClient(Integer.parseInt(client)); // 0pc
        record.setWithdrawType(0); // 提现类型 0主动提现  1代提现
        // 插入用户提现记录表
        withdrawClient.insertAccountWithdrawLog(record);
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
     * @return
     */
    private BankCallBean getCommonBankCallBean(UserVO user, String platform, String channel, String transAmt, String cardNo, String payAllianceCode, String fee) {
        BankCardVO bankCard = this.bindCardClient.queryUserCardValid(user.getUserId()+"", cardNo);
        UserInfoVO usersInfo = this.amUserClient.findUsersInfoById(user.getUserId());
        BankOpenAccountVO bankOpenAccountVO=bankOpenClient.selectById(user.getUserId());
        // 调用汇付接口(提现)
        String retUrl = systemConfig.getWebHost()+CLIENT_HEADER_MAP.get(platform)+"/borrow/userBankWithdrawReturn.do";
        String bgRetUrl = systemConfig.getWebHost()+CLIENT_HEADER_MAP.get(platform)+"/borrow/userBankWithdrawBgreturn.do";
        String successfulUrl = systemConfig.getWebHost()+CLIENT_HEADER_MAP.get(platform)+"/borrow/userBankWithdrawReturn.do?isSuccess=1&withdrawmoney=" + transAmt
                + "&wifee=" + fee;//
        // 路由代码
        String routeCode = "";
        // 调用汇付接口(4.2.2 用户绑卡接口)

        BankCallBean bean = new BankCallBean();
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(user.getUserId()));
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
        bean.setMobile(user.getMobile());// 手机号
        bean.setCardNo(bankCard.getCardNo());// 银行卡号
        bean.setTxAmount(CustomUtil.formatAmount(transAmt));
        bean.setTxFee(fee);
        // 成功跳转的url
        bean.setSuccessfulUrl(successfulUrl);
        // 扣除手续费
        if ((new BigDecimal(transAmt).compareTo(new BigDecimal(50000)) > 0) && StringUtils.isNotBlank(payAllianceCode)) {
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
            CorpOpenAccountRecordVO
                    record=amUserClient.selectCorpOpenAccountRecordByUserId(user.getUserId());
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);// 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
            bean.setRouteCode("2");
            bean.setCardBankCnaps(StringUtils.isEmpty(payAllianceCode) ? bankCard.getPayAllianceCode() : payAllianceCode);
        }

        bean.setForgotPwdUrl(systemConfig.getForgetPassword());
        bean.setRetUrl(retUrl);// 商户前台台应答地址(必须)
        bean.setNotifyUrl(bgRetUrl); // 商户后台应答地址(必须)
        System.out.println("提现前台回调函数：\n" + bean.getRetUrl());
        System.out.println("提现后台回调函数：\n" + bean.getNotifyUrl());

        return bean;
    }
    private String getWithdrawFee(Integer userId, String cardNo) {
        String feetmp = ClientConstants.BANK_FEE;
        if (feetmp == null) {
            feetmp = "1";
        }

        if(StringUtils.isEmpty(cardNo)){
            return feetmp;
        }

        BankCardVO bankCard = this.bindCardClient.queryUserCardValid(userId+"", cardNo);

        if (bankCard != null) {
            Integer bankId = bankCard.getBankId();
            // 取得费率
            BanksConfigVO banksConfig = bindCardClient.getBanksConfigByBankId(bankId+"");
            if (banksConfig != null) {
                if (Validator.isNotNull(banksConfig.getFeeWithdraw())) {
                    return banksConfig.getFeeWithdraw().toString();
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
}
