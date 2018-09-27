/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.withdraw.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.trade.ApiUserWithdrawRequest;
import com.hyjf.am.vo.bank.BankCallBeanVO;
import com.hyjf.am.vo.trade.BankConfigVO;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.trade.JxBankConfigVO;
import com.hyjf.am.vo.trade.account.AccountVO;
import com.hyjf.am.vo.trade.account.AccountWithdrawVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.CacheUtil;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmTradeClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.withdraw.UserWithdrawService;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallMethodConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.pay.lib.chinapnr.ChinapnrBean;
import com.hyjf.pay.lib.chinapnr.util.ChinaPnrConstant;
import com.hyjf.pay.lib.chinapnr.util.ChinapnrUtil;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: sunpeikai
 * @version: UserWithdrawServiceImpl, v0.1 2018/7/23 15:18
 */
@Service
public class UserWithdrawServiceImpl extends BaseUserServiceImpl implements UserWithdrawService {
    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmTradeClient amTradeClient;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 根据userId获取accountBank
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public List<AccountBankVO> getBankHfCardByUserId(Integer userId) {
        return amUserClient.getAccountBankByUserId(userId);
    }
    /**
     * 根据银行名查询银行配置
     * @auth sunpeikai
     * @param bank 银行code，例如：招商银行,CMB
     * @return
     */
    @Override
    public BankConfigVO getBankInfo(String bank) {
        return amConfigClient.getBankConfigByCode(bank);
    }

    /**
     * 根据id查询银行配置
     * @auth sunpeikai
     * @param bankId 主键id
     * @return
     */
    @Override
    public JxBankConfigVO getJxBankConfigByBankId(Integer bankId) {
        return amConfigClient.getJxBankConfigById(bankId);
    }
    /**
     * 根据userId获取BankCard
     * @auth sunpeikai
     * @param userId 用户id
     * @return
     */
    @Override
    public List<BankCardVO> getBankCardByUserId(Integer userId) {
        return amUserClient.getBankOpenAccountById(userId);
    }

    /**
     * 用户提现
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public ModelAndView withdraw(UserWithdrawRequestBean userWithdrawRequestBean, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("/callback/callback_post");
        UserWithdrawResultBean userWithdrawResultBean = new UserWithdrawResultBean();
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"银行卡号不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "银行卡号不能为空");
                return modelAndView;
            }
            // 银行电子账户号
            if (Validator.isNull(accountId)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    // 请求第三方异步回调
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"电子账户号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"电子账户号不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "电子账户号不能为空");
                return modelAndView;
            }
            // 渠道
            if (Validator.isNull(channel)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"渠道不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"渠道不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "渠道不能为空");
                return modelAndView;
            }
            // 充值金额
            if (Validator.isNull(account)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现金额不能为空");
                return modelAndView;
            }

            // 同步URL
            if (Validator.isNull(retUrl)) {
                // 异步回调URL不为空
                if (Validator.isNotNull(bgRetUrl)) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"同步回调URL不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "请求参数异常");
                return modelAndView;
            }
            // 异步回调URL
            if (Validator.isNull(bgRetUrl)) {
                // 同步地址不为空
                if (Validator.isNotNull(retUrl)) {
                    logger.info("异步回调URL为空");
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"请求参数异常");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "请求参数异常");
                return modelAndView;
            }
            // 机构编号
            if (Validator.isNull(instCode)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"机构编号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"机构编号不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "机构编号不能为空");
                return modelAndView;
            }
            // 忘记密码Url
            if (Validator.isNull(forgotPwdUrl)) {
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"忘记密码URL不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"忘记密码URL不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "忘记密码URL不能为空");
                return modelAndView;
            }
            // 验签  先去掉验签
            if (!this.verifyRequestSign(userWithdrawRequestBean, BaseDefine.METHOD_SERVER_WITHDRAW)) {
                logger.info("-------------------验签失败！--------------------");
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000002);
                userWithdrawResultBean.setCallBackAction(userWithdrawRequestBean.getRetUrl());
                userWithdrawResultBean.set("accountId", userWithdrawRequestBean.getAccountId());
                userWithdrawResultBean.set("acqRes", userWithdrawRequestBean.getAcqRes());
                modelAndView = new ModelAndView("/callback/callback_transpassword");
                // 设置交易密码
                modelAndView.addObject("statusDesc", "验签失败！");
                userWithdrawResultBean.set("status", resultBean.getStatus());
                userWithdrawResultBean.set("chkValue", resultBean.getChkValue());
                modelAndView.addObject("callBackForm", userWithdrawResultBean);
                //  返回第三方异步回调
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"验签失败");
                    params.put("acqRes", userWithdrawRequestBean.getAcqRes());
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                return modelAndView;
            }

            // 充值金额校验
            if (!account.matches("-?[0-9]+.*[0-9]*")) {
                logger.info("提现金额格式错误,充值金额:[" + account + "]");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额格式错误");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额格式错误");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现金额格式错误");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_ZC000004,"机构编号错误");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "机构编号错误");
                return modelAndView;
            }
            // 大额提现判断银行联行号
            if ((new BigDecimal(account).compareTo(new BigDecimal(50001)) > 0) && StringUtils.isBlank(payAllianceCode)) {
                logger.info("大额提现时,银行联行号不能为空");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"大额提现时,银行联行号不能为空");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"大额提现时,银行联行号不能为空");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "大额提现时,银行联行号不能为空");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
            }

            // 检查是否设置交易密码
            Integer passwordFlag = user.getIsSetPassword();
            if (passwordFlag != 1) {// 未设置交易密码
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_TP000002,"未设置交易密码");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_TP000002,"未设置交易密码");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
            }

            // 服务费授权状态和开关
            if (!checkPaymentAuthStatus(bankOpenAccount.getUserId())) {
                logger.info("用户未进行缴费授权,用户电子账户号:[" + accountId + "],用户ID:[" + userId + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000011,"用户未进行缴费授权");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000011,"用户未进行缴费授权");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现失败");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现失败");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000009,"查询用户账户信息失败");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "查询用户账户信息失败");
                return modelAndView;
            }
            // 提现金额大于汇盈账户余额
            if (new BigDecimal(account).compareTo(hyAccount.getBankBalance()) > 0) {
                logger.info("提现金额大于汇盈账户可用余额,用户ID:[" + userId + "],电子账户号:[" + accountId + "],提现金额:[" + account + "],账户可用余额:[" + hyAccount.getBankBalance() + "].");
                // 异步回调URL
                if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                    Map<String, String> params = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000010,"用户账户余额不足");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), params);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_NC000010,"用户账户余额不足");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "用户账户余额不足");
                return modelAndView;
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
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现金额不能小于手续费");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现金额不能小于手续费");
                return modelAndView;

            }

            account = new BigDecimal(account).subtract(new BigDecimal(Validator.isNull(fee) ? "0" : fee)).toString();
            // 调用江西银行提现接口
            // 调用汇付接口(提现)

            // todo  url的域名先干掉，应该加什么？？
            String bankRetUrl =  "/server/user/withdraw/return.do?callback=" + retUrl.replace("#", "*-*-*");
           // String bankRetUrl = systemConfig.httpWebHost + "/server/user/withdraw/return.do?callback=" + retUrl.replace("#", "*-*-*");
            // 支付工程路径
           // String bankBgRetUrl = systemConfig.httpWebHost + "/server/user/withdraw/callback.do?callback=" + bgRetUrl.replace("#", "*-*-*");// 支付工程路径
            String bankBgRetUrl = "/server/user/withdraw/callback.do?callback=" + bgRetUrl.replace("#", "*-*-*");// 支付工程路径

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
            // TODO忘记密码URL
            //bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);
            bean.setForgotPwdUrl(userWithdrawRequestBean.getForgotPwdUrl());
            bean.setRetUrl(bankRetUrl);// 商户前台台应答地址(必须)
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
                    Map<String, String> retParams = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
                    CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), retParams);
                }
                // 同步回调URL不为空
                if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                    // 同步回调
                    return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
                }
                modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
                modelAndView.addObject("message", "提现异常");
                return modelAndView;
            }
        } catch (Exception e) {
            logger.info("提现发生异常,异常信息:[" + e.getMessage() + "].");
            // 异步回调URL
            if (Validator.isNotNull(userWithdrawRequestBean.getBgRetUrl())) {
                Map<String, String> retParams = asyncParam(userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
                CommonSoaUtils.noRetPostThree(userWithdrawRequestBean.getBgRetUrl(), retParams);
            }
            // 同步回调URL不为空
            if (Validator.isNotNull(userWithdrawRequestBean.getRetUrl())) {
                // 同步回调
                return syncParam(userWithdrawResultBean,userWithdrawRequestBean,ErrorCodeConstant.STATUS_CE000001,"提现异常");
            }
            modelAndView = new ModelAndView("/withdraw/withdraw_cash_fail");
            modelAndView.addObject("message", "提现异常");
            return modelAndView;
        }
        return modelAndView;
    }

    /**
     * api用户提现后,同步处理
     * @auth sunpeikai
     * @param
     * @return
     */
    @Override
    public ModelAndView cashReturn(HttpServletRequest request, BankCallBean bean) {
        logger.info("用户提现后,同步处理");
        ModelAndView modelAndView = new ModelAndView("/callback/callback_post");
        bean.convert();
        String logOrderId = bean.getLogOrderId() == null ? "" : bean.getLogOrderId();
        // 提现订单号
        logger.info("提现订单号:[" + logOrderId + "].");
        UserWithdrawResultBean resultBean = new UserWithdrawResultBean();
        resultBean.setCallBackAction(request.getParameter("callback").replace("*-*-*", "#"));
        AccountWithdrawVO accountWithdrawVO = amTradeClient.getAccountWithdrawByOrderId(logOrderId);
        // 提现成功
        if (accountWithdrawVO != null) {
            logger.info("提现成功,提现订单号:[" + logOrderId + "]");
            resultBean.setAmt(String.valueOf(accountWithdrawVO.getTotal()));// 交易金额
            resultBean.setArrivalAmount(String.valueOf(accountWithdrawVO.getCredited()));// 到账金额
            resultBean.setFee(accountWithdrawVO.getFee());// 提现手续费
            modelAndView.addObject("statusDesc", "提现成功");
            BaseResultBean baseResultBean = new BaseResultBean();
            baseResultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
            resultBean.set("chkValue", baseResultBean.getChkValue());
            resultBean.set("status", baseResultBean.getStatus());
            resultBean.set("amt", String.valueOf(accountWithdrawVO.getTotal()));// 交易金额
            resultBean.set("arrivalAmount", String.valueOf(accountWithdrawVO.getCredited()));// 到账金额
            resultBean.set("fee", accountWithdrawVO.getFee());// 提现手续费
            resultBean.set("orderId", accountWithdrawVO.getNid());// 提现订单号
        } else {
            logger.info("银行处理中,请稍后查询交易明细");
            modelAndView.addObject("statusDesc", "银行处理中,请稍后查询交易明细");
            BaseResultBean baseResultBean = new BaseResultBean();
            baseResultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000005);
            resultBean.set("chkValue", baseResultBean.getChkValue());
            resultBean.set("status", baseResultBean.getStatus());
//            resultBean.setStatus("2");
//            resultBean.setStatusDesc("银行处理中,请稍后查询交易明细");
        }
        modelAndView.addObject("callBackForm", resultBean);
        return modelAndView;
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
        if(!this.verifyRequestSign(userWithdrawRequestBean, BaseDefine.METHOD_SERVER_GET_USER_WITHDRAW_RECORD)){
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
            //TODO:这里有问题，数据库表的字段已经更换。同时由于nxl代码中有关于老字段的应用，所以这里需要确认
            result.setWithdrawTime(accountWithdrawVO.getAddtime());
            //result.setWithdrawTime(GetDate.dateToString(accountWithdrawVO.getCreateTime()));
            resultList.add(result);
        }
        return resultList;
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

    @Override
    public JSONObject getCashInfo(Integer userId, JSONObject ret,String version, String bankCode, String getcash) {
        // 金额显示格式
        DecimalFormat moneyFormat = null;
        // 判断选择哪种金融样式
        if (version.contains(CustomConstants.APP_VERSION_NUM)) {
            moneyFormat = CustomConstants.DF_FOR_VIEW_V1;
        } else {
            moneyFormat = CustomConstants.DF_FOR_VIEW;
        }
        // 取得用户当前余额
        AccountVO account = amTradeClient.getAccount(userId);
        if (account == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "你的账户信息存在异常，请联系客服人员处理。");
            return ret;
        } else {
            // 可提现金额
            ret.put("total", moneyFormat.format(account.getBalance()));
        }
        // 取得用户在汇付天下的账户信息
        AccountChinapnrVO accountChinapnr = amUserClient.getAccountChinapnr(userId);
        // 用户未开户时,返回错误信息
        if (accountChinapnr == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "用户未开户!");
            return ret;
        }
        // 取得银行卡信息
        // begin 调汇付接口查询银行卡信息 4.4.11 因为绑卡的时候汇付未能传递给我们是否默认卡
        ChinapnrBean bean = new ChinapnrBean();
        bean.setVersion(ChinaPnrConstant.VERSION_10);
        // 消息类型(必须)
        bean.setCmdId(ChinaPnrConstant.CMDID_QUERY_CARD_INFO);
        // 商户客户号
        bean.setMerCustId(systemConfig.getChinapnrMercustid());
        // 用户客户号(必须)
        bean.setUsrCustId(String.valueOf(accountChinapnr.getChinapnrUsrcustid()));
        // 调用汇付接口
        ChinapnrBean chinaPnrBean = ChinapnrUtil.callApiBg(bean);
        // end 调汇付接口查询银行卡信息 4.4.11 因为绑卡的时候汇付未能传递给我们是否默认卡
        if (chinaPnrBean == null) {
            ret.put("status", "1");
            ret.put("statusDesc", "调用汇付接口(查询银行卡信息 4.4.11)发生错误!");
            return ret;
        }
        String UsrCardInfolist = chinaPnrBean.getUsrCardInfolist();
        JSONArray array = JSONObject.parseArray(UsrCardInfolist);
        BankConfigVO bankSetUp = new BankConfigVO();
        if (array.size() > 0) {
            ret.put("bankCnt", array.size() + "");
            List<BankCardBean> bankcards = new ArrayList<BankCardBean>();
            for (int j = 0; j < array.size(); j++) {
                JSONObject obj = array.getJSONObject(j);
                if (!"R".equals(obj.getString("RealFlag"))) {
                    // 只有实名卡才入库
                    continue;
                }
                BankConfigVO bankConfig = amConfigClient.getBankConfigByCode(obj.getString("BankId"));
                BankCardBean bankCardBean = new BankCardBean();
                // 普通卡
                bankCardBean.setIsDefault("0");
                if ("Y".equals(obj.getString("IsDefault"))) {
                    // 默认卡
                    bankCardBean.setIsDefault("1");
                }
                if ("Y".equals(obj.getString("ExpressFlag"))) {
                    // 快捷卡
                    bankCardBean.setIsDefault("2");
                }
                // 银行代号
                bankCardBean.setBankCode(obj.getString("BankId"));
                // 银行名称
                bankCardBean.setBank(bankConfig.getName());
                // 应前台要求，logo路径给绝对路径
                bankCardBean.setLogo(systemConfig.getAppServerHost() + bankConfig.getAppLogo());
                bankCardBean.setCardNo(obj.getString("CardId"));
                bankcards.add(bankCardBean);
                // 判断是否已经传银行卡code，如果已传则获取该银行的信息
                if (bankCode != null && bankCode.equals(obj.getString("BankId"))) {
                    bankSetUp = bankConfig;
                } else {
                    // 如果没有传银行卡code，则判断是默认银行卡或快捷卡记录银行设置
                    if ("Y".equals(obj.getString("IsDefault")) || "Y".equals(obj.getString("ExpressFlag"))) {
                        bankSetUp = bankConfig;
                    }
                }
            }
            ret.put("banks", bankcards);
            ret.put("logo", bankcards.get(0).getLogo());
            ret.put("cardNo", bankcards.get(0).getCardNo());
            ret.put("bank", bankcards.get(0).getBank());
        } else {
            ret.put("bankCnt", "0");
        }

        // 银行卡支持的提现方式 开始
        int cashchlCnt = 0;
        JSONArray cashchls = new JSONArray();
        // 判断是否有一般提现
        if (1 == bankSetUp.getNormalWithdraw()) {
            JSONObject jo = new JSONObject();
            jo.put("cashchlNm", "GENERAL");
            jo.put("cashchlRemark", "一般提现");
            // 默认提现方式,0一般提现,1快速提现,2即时提现,默认0
            if (bankSetUp.getWithdrawDefaulttype() == 0) {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "1");
            } else {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "0");
            }
            cashchlCnt++;
            cashchls.add(jo);
        }
        // 判断是否有快速提现
        if (1 == bankSetUp.getQuickWithdraw()) {
            JSONObject jo = new JSONObject();
            jo.put("cashchlNm", "FAST");
            jo.put("cashchlRemark", "快速提现");
            // 默认提现方式,0一般提现,1快速提现,2即时提现,默认0
            if (bankSetUp.getWithdrawDefaulttype() == 1) {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "1");
            } else {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "0");
            }
            cashchlCnt++;
            cashchls.add(jo);
        }
        // 判断是否有即时提现
        if (1 == bankSetUp.getImmediatelyWithdraw()) {
            JSONObject jo = new JSONObject();
            jo.put("cashchlNm", "IMMEDIATE");
            jo.put("cashchlRemark", "即时提现");
            // 默认提现方式,0一般提现,1快速提现,2即时提现,默认0
            if (bankSetUp.getWithdrawDefaulttype() == 2) {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "1");
            } else {
                // 是否默认提现方式1是，0否
                jo.put("isDefaultCashchl", "0");
            }
            cashchlCnt++;
            cashchls.add(jo);
        }
        // 提现方式总数
        ret.put("cashchlCnt", cashchlCnt + "");
        ret.put("cashchls", cashchls);
        // 银行卡支持的提现方式 结束
        // 如果提现金额是0
        if ("0".equals(getcash) || "".equals(getcash)) {
            ret.put("accountDesc", "手续费: 0 元；实际到账: 0 元");
            ret.put("fee", "0.00 元");
            ret.put("balance", "0.00 元");
            ret.put("buttonWord", "提现");
        } else {

            String balance = "";
            if ((new BigDecimal(getcash).subtract(BigDecimal.ONE)).compareTo(BigDecimal.ZERO) < 0) {
                balance = "0";
            } else {
                balance = moneyFormat.format(new BigDecimal(getcash).subtract(BigDecimal.ONE));
            }
            ret.put("accountDesc", "手续费: 1 元；实际到账: " + balance + " 元");
            ret.put("fee", "1.00 元");
            ret.put("balance", balance+" 元");
            ret.put("buttonWord", "确认提现"+moneyFormat.format(new BigDecimal("".equals(getcash)?"0":getcash))+"元");
        }

        ret.put("status", "0");
        ret.put("statusDesc", "成功");
        ret.put("request", "/hyjf-app/user/withdraw" + ClientConstants.GET_WITHDRAW_INFO_MAPPING);
        return ret;
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
        ModelAndView modelAndView = new ModelAndView("/callback/callback_transpassword");
        userWithdrawResultBean.setCallBackAction(userWithdrawRequestBean.getRetUrl());
        userWithdrawResultBean.set("accountId", userWithdrawRequestBean.getAccountId());
        userWithdrawResultBean.set("statusDesc", statusDesc);
        userWithdrawResultBean.set("status", resultBean.getStatus());
        userWithdrawResultBean.set("chkValue", resultBean.getChkValue());
        modelAndView.addObject("callBackForm", userWithdrawResultBean);
        return modelAndView;
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
}
