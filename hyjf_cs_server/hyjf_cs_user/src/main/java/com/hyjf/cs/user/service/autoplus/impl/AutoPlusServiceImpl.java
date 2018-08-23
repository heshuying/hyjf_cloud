/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.autoplus.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.HjhUserAuthLogVO;
import com.hyjf.am.vo.user.HjhUserAuthVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.ClientConstants;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetDate;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhangqingqing
 * @version AutoPlusServiceImpl, v0.1 2018/6/11 15:39
 */
@Service
public class AutoPlusServiceImpl extends BaseUserServiceImpl implements AutoPlusService {
    private static final Logger logger = LoggerFactory.getLogger(AutoPlusServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    AmDataCollectClient amDataCollectClient;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 自动投资、债转授权
     *
     * @param user
     * @param client 0web 1wechat 2app
     * @param type   1表示投资 2表示债转
     * @param
     * @return
     */
    @Override
    public Map<String, Object> userCreditAuthInves(UserVO user, Integer client, String type, String channel, String lastSrvAuthCode, String smsCode) {
        // 判断是否授权过
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(user.getUserId());
        if (hjhUserAuth != null && hjhUserAuth.getAutoCreditStatus().intValue() == 1&&type.equals(ClientConstants.QUERY_TYPE_2)) {
            //用户已授权自动债转
            throw new ReturnMessageException(MsgEnum.ERR_AUTHORIZE_REPEAT);
        }else if (hjhUserAuth != null && hjhUserAuth.getAutoInvesStatus().intValue() == 1&&type.equals(ClientConstants.QUERY_TYPE_1)) {
            //用户已授权自动投资
            throw new ReturnMessageException(MsgEnum.ERR_AUTHORIZE_REPEAT);
        }
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(user, type, client, channel, lastSrvAuthCode, smsCode);
        // 插入日志
        this.insertUserAuthLog(user, bean, client, type);
        Map<String, Object> map = new HashMap<>();
        try {
            //调用pay接口
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            e.printStackTrace();
            CheckUtil.check(false, MsgEnum.ERR_BANK_CALL);
        }
        return map;
    }

    /**
     * app wechat授权自动债转、投资同步回调
     *
     * @param token
     * @param bean
     * @param userAutoType 1债转 0投资
     * @param
     * @return
     */
    @Override
    public Map<String, BaseMapBean> userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess) {
        Map<String, BaseMapBean> result = new HashMap<>();
        bean.convert();
        // 用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        if (isSuccess == null || !ClientConstants.ISSUCCESS.equals(isSuccess) || hjhUserAuth == null || hjhUserAuth.getAutoCreditStatus() != 1) {
            if (ClientConstants.INVES_AUTO_TYPE.equals(userAutoType)) {
                result = getErrorMap(ResultEnum.USER_ERROR_204, sign, userAutoType, hjhUserAuth);
            } else {
                result = getErrorMap(ResultEnum.USER_ERROR_205, sign, userAutoType, hjhUserAuth);
            }
        } else {
            result = getSuccessMap(sign, userAutoType, hjhUserAuth);
        }
        return result;
    }

    /**
     * 组装跳转错误页面MV
     *
     * @param param
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private Map<String, BaseMapBean> getErrorMap(ResultEnum param, String sign, String type, HjhUserAuthVO hjhUserAuth) {
        Map<String, BaseMapBean> result = new HashMap<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        baseMapBean.set("autoInvesStatus", hjhUserAuth == null ? "0" : hjhUserAuth.getAutoInvesStatus() == null ? "0" : hjhUserAuth.getAutoInvesStatus() + "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth == null ? "0" : hjhUserAuth.getAutoCreditStatus() == null ? "0" : hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(systemConfig.getFrontHost() + "/user/setting/authorization/result/failed");
        result.put("callBackForm", baseMapBean);
        return result;
    }

    /**
     * 组装跳转成功页面MV
     *
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private Map<String, BaseMapBean> getSuccessMap(String sign, String type, HjhUserAuthVO hjhUserAuth) {
        Map<String, BaseMapBean> result = new HashMap<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth == null ? "0" : hjhUserAuth.getAutoInvesStatus() == null ? "0" : hjhUserAuth.getAutoInvesStatus() + "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth == null ? "0" : hjhUserAuth.getAutoCreditStatus() == null ? "0" : hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.setCallBackAction(systemConfig.getFrontHost() + "/user/setting/authorization/result/success");
        result.put("callBackForm", baseMapBean);
        return result;
    }

    /**
     * 异步回调接口
     *
     * @param bean
     * @return
     */
    @Override
    public String userBgreturn(BankCallBean bean, String type) {
        BankCallResult result = new BankCallResult();
        logger.info("[用户授权异步回调开始]");
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        UserVO user = amUserClient.findUserById(userId);
        // 成功
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                BankCallBean retBean = getUserAuthQUery(userId, type);
                if (retBean != null && "1".equals(retBean.getState())) {
                    // 更新签约状态和日志表
                    BankRequest bankRequest = new BankRequest();
                    BeanUtils.copyProperties(bean, bankRequest);
                    amUserClient.updateUserAuthInves(bankRequest);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("bgReturn", e);
            }
        }
        logger.info("[用户授权完成后,回调结束]");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 组装发往江西银行参数
     *
     * @param users
     * @param type
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    private BankCallBean getCommonBankCallBean(UserVO users, String type, Integer client, String channel, String lastSrvAuthCode, String smsCode) {
        BankOpenAccountVO bankOpenAccountVO = this.getBankOpenAccount(users.getUserId());
        String remark = "";
        String txcode = "";
        BankCallBean bean = new BankCallBean(users.getUserId(), txcode, client);
        // 同步地址 跳转到前端页面
        String retUrl = super.getFrontHost(systemConfig, String.valueOf(client)) + "/user/automaticError" + "?channel="+type+"&logOrdId=" + bean.getLogOrderId();
        String successUrl = super.getFrontHost(systemConfig, String.valueOf(client)) + "/user/automaticSuccess?channel="+type;
        // 异步调用路
        String bgRetUrl = "";
        if (BankCallConstant.QUERY_TYPE_1.equals(type)) {
            remark = "投资人自动投标签约增强";
            bgRetUrl = systemConfig.getWebHost()+"/user/invesbgreturn";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1, 5), new SimpleDateFormat("yyyyMMdd")));
            bean.setTxAmount("1000000");
            bean.setTotAmount("1000000000");
        } else if (BankCallConstant.QUERY_TYPE_2.equals(type)) {
            remark = "投资人自动债权转让签约增强";
            bgRetUrl = systemConfig.getWebHost()+"/user/creditbgreturn";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
        }
        //1wechat 2app
        if (client == 1 || client == 2) {
            bean.setOrderId(bean.getLogOrderId());
        } else {
            String orderId = GetOrderIdUtils.getOrderId2(users.getUserId());
            bean.setOrderId(orderId);
            bean.setLogOrderId(orderId);
        }
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);
        bean.setAccountId(bankOpenAccountVO.getAccount());
        bean.setSmsCode(smsCode);
        bean.setLastSrvAuthCode(lastSrvAuthCode);
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setForgotPwdUrl(systemConfig.getForgetpassword());
        bean.setChannel(channel);
        bean.setLogRemark(remark);
        return bean;
    }


    /**
     * 检查用户信息
     *
     * @param users
     * @param lastSrvAuthCode
     * @param smsCode
     */
    @Override
    public void checkUserMessage(UserVO users, String lastSrvAuthCode, String smsCode) {
        if (users == null) {
            throw new ReturnMessageException(MsgEnum.ERR_USER_LOGIN_RETRY);
        }
        // 检查数据是否完整
        if (Validator.isNull(lastSrvAuthCode) || Validator.isNull(smsCode)) {
            throw new ReturnMessageException(MsgEnum.ERR_PARAM);
        }
        if (users.getBankOpenAccount() == 0) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 判断用户是否设置过交易密码
        CheckUtil.check(users.getIsSetPassword() == 1, MsgEnum.ERR_TRADE_PASSWORD_NOT_SET);
    }

    @Override
    public Map<String, Integer> userAutoStatus(Integer userId) {
        Map<String, Integer> resultMap = new HashMap<>();
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(userId);
        //债转
        resultMap.put("credit", hjhUserAuth.getAutoCreditStatus());
        //投标
        resultMap.put("inves", hjhUserAuth.getAutoInvesStatus());
        return resultMap;
    }

    @Override
    public Map<String, String> getStatus(Integer userId) {
        Map<String, String> result = new HashMap<>();
        // 检查用户授权状态
        HjhUserAuthVO userAuth = amUserClient.getHjhUserAuthByUserId(userId);
        //来自投标成功页面
        if (userAuth == null || userAuth.getAutoCreditStatus() == 0) {
            //未设置自动债转
            result.put("credit", "2");
        } else {
            //已设置自动债转
            result.put("credit", "0");
        }
        //来自债转成功页面
        if (userAuth == null || userAuth.getAutoInvesStatus() == 0) {
            //未设置自动投资
            result.put("inves", "1");
        } else {
            //已设置自动投资
            result.put("inves", "0");
        }

        return result;
    }

    @Override
    public String checkSmsParam(UserVO user, String userAutoType) {
        HjhUserAuthVO userAuth = amUserClient.getHjhUserAuthByUserId(user.getUserId());
        if (userAuth != null) {
            if ("0".equals(userAutoType) && userAuth.getAutoInvesStatus() == 1) {
                // 自动投标检查
                throw new CheckException(MsgEnum.ERR_AUTHORIZE_REPEAT);
            }
            if ("1".equals(userAutoType) && userAuth.getAutoCreditStatus() == 1) {
                // 自动债转检查
                throw new CheckException(MsgEnum.ERR_AUTHORIZE_REPEAT);
            }
        }
        CheckUtil.check(user != null, MsgEnum.ERR_USER_NOT_LOGIN);
        CheckUtil.check(user.getMobile() != null, MsgEnum.ERR_OBJECT_BLANK, "手机号");
        CheckUtil.check(StringUtils.isNotBlank(userAutoType), MsgEnum.ERR_PARAM_TYPE);
        String srvTxCode = "";
        if (ClientConstants.INVES_AUTO_TYPE.equals(userAutoType)) {
            srvTxCode = BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS;
        } else if (ClientConstants.CREDIT_AUTO_TYPE.equals(userAutoType)) {
            srvTxCode = BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUS;
        } else {
            throw new CheckException(MsgEnum.STATUS_CE000001);
        }
        return srvTxCode;
    }

    @Override
    public String checkApiSmsParam(AutoPlusRequestBean autoPlusRequestBean) {
        // 手机号
        String mobile = autoPlusRequestBean.getMobile();
        // 渠道
        String channel = autoPlusRequestBean.getChannel();
        // 机构编号
        String instCode = autoPlusRequestBean.getInstCode();
        // 短信类型  1为自动投标授权  2为自动债转授权
        String sendType = autoPlusRequestBean.getSendType();
        // 电子账户号
        String accountId = autoPlusRequestBean.getAccountId();
        String srvTxCode = "";
        // 验证请求参数
        // 机构编号
        CheckUtil.check(StringUtils.isNotBlank(instCode) && StringUtils.isNotBlank(sendType), MsgEnum.STATUS_CE000001);
        // 手机号
        CheckUtil.check(StringUtils.isNotBlank(mobile), MsgEnum.STATUS_ZC000001);
        // 渠道
        CheckUtil.check(StringUtils.isNotBlank(channel), MsgEnum.STATUS_ZC000006);
        // 验签
        if (!this.verifyRequestSign(autoPlusRequestBean, BaseDefine.METHOD_BORROW_AUTH_SEND_SMS)) {
            logger.info("----验签失败----");
            throw new CheckException(MsgEnum.STATUS_CE000002);
        }
        // 手机号合法性校验
        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.STATUS_ZC000003);
        // 根据手机号查询用户信息
        UserVO user = this.getUsersByMobile(mobile);
        logger.info("根据手机号查询用户信息失败,手机号:[" + mobile + "].");
        CheckUtil.check(null != user, MsgEnum.STATUS_CE000003);
        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(accountId);
        CheckUtil.check(null != bankOpenAccount, MsgEnum.STATUS_CE000004);
        CheckUtil.check(user.getBankOpenAccount().intValue() != 1, MsgEnum.STATUS_CE000006);

        Integer passwordFlag = user.getIsSetPassword();
        CheckUtil.check(passwordFlag == 1, MsgEnum.STATUS_TP000002);
        UserVO loginUser = this.getUsersById(bankOpenAccount.getUserId());
        //手机号是否正确
        CheckUtil.check(mobile.equals(loginUser.getMobile()), MsgEnum.STATUS_CE000003);
        // 查询是否已经授权过
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(user.getUserId());
        if ("1".equals(sendType)) {
            srvTxCode = BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS;
            if (hjhUserAuth != null && hjhUserAuth.getAutoInvesStatus() == 1) {
                logger.info("已经授权过");
                throw new CheckException(MsgEnum.STATUS_CE000009);
            }
        } else if ("2".equals(sendType)) {
            srvTxCode = BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS;
            if (hjhUserAuth != null && hjhUserAuth.getAutoCreditStatus() == 1) {
                logger.info("已经授权过");
                throw new CheckException(MsgEnum.STATUS_CE000009);
            }
        } else {
            throw new CheckException(MsgEnum.STATUS_CE000001);
        }
        return srvTxCode;
    }

    /**
     * 组装发往江西银行参数
     *
     * @param users(user,type,client,channel,lastSrvAuthCode,smsCode);
     * @param type
     * @param lastSrvAuthCode
     * @param smsCode
     * @param sign
     * @param token
     * @return
     */
    @Override
    public BankCallBean appGetCommonBankCallBean(UserVO users, int type, String lastSrvAuthCode, String smsCode, String sign, String token) {
        String remark = "";
        String txcode = "";
        BankCallBean bean = new BankCallBean();
        // 同步调用路径
        String retUrl = systemConfig.getAppHost() + "/user/setting/authorization/result/failed";
        String success = systemConfig.getAppHost() + "/user/setting/authorization/result/success";
        // 异步调用路
        String bgRetUrl = systemConfig.getAppHost() + "/hyjf-app/bank/user/autoplus";
        String forgetPassworedUrl = systemConfig.getForgetpassword() + "?sign=" + sign + "&token=" + token;
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(success);
        if (type == 1) {
            // 2.4.4.投资人自动投标签约增强
            bgRetUrl += "/userAuthInvesBgreturn";
            remark = "投资人自动投标签约增强";
            txcode = BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS;
            // 签约到期时间
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1, 5), new SimpleDateFormat("yyyyMMdd")));
            // 单笔投标金额的上限
            bean.setTxAmount("1000000");
            // 自动投标总金额上限（不算已还金额）
            bean.setTotAmount("1000000000");
        } else if (type == 2) {
            // 2.4.8.投资人自动债权转让签约增强
            bgRetUrl += "/userAuthCreditBgreturn";
            remark = "投资人自动债权转让签约增强";
            txcode = BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS;
        }
        String orderId = GetOrderIdUtils.getOrderId2(users.getUserId());
        // 取得用户在江西银行的客户号
        BankOpenAccountVO bankOpenAccount = getBankOpenAccount(users.getUserId());
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setTxCode(txcode);
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        bean.setAccountId(bankOpenAccount.getAccount());
        bean.setOrderId(orderId);
        //异步回调
        bean.setNotifyUrl(bgRetUrl);
        //忘记密码通知地址
        bean.setForgotPwdUrl(forgetPassworedUrl);
        // 前导业务授权码
        bean.setLastSrvAuthCode(lastSrvAuthCode);
        // 手机验证码
        bean.setSmsCode(smsCode);
        // 操作者ID
        bean.setLogUserId(String.valueOf(users.getUserId()));
        bean.setLogOrderId(orderId);
        bean.setLogRemark(remark);
        bean.setLogClient(0);
        return bean;
    }

    @Override
    public void appAuthInvesCheck(String srvAuthCode, String code, JSONObject checkResult, Integer userId) {
        if (checkResult != null) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"非法参数!");
        }
        if (userId == null || userId == 0) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"用户未登录!");
        }
        // 检查数据是否完整
        if (Validator.isNull(srvAuthCode) || Validator.isNull(code)) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"验证码或前导业务码不能为空!");
        }
        UserVO user = getUsersById(userId);
        if (user.getBankOpenAccount() == 0) {
            // 未开户
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"用户未开户!");
        }
        // 判断用户是否设置过交易密码
        if (user.getIsSetPassword() == 0) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"用户未设置交易密码!");
        }
        // 判断是否授权过
        HjhUserAuthVO hjhUserAuth = this.getHjhUserAuth(user.getUserId());
        if (hjhUserAuth != null && hjhUserAuth.getAutoInvesStatus() == 1) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL,"用户自动投资已授权,无需重复授权!");
        }
    }

    /**
     * 插入日志
     *
     * @param user
     * @param bean
     * @param client
     * @param authType
     */
    @Override
    public void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType) {
        Date nowTime = GetDate.getNowTime();
        HjhUserAuthLogVO hjhUserAuthLog = new HjhUserAuthLogVO();
        hjhUserAuthLog.setUserId(user.getUserId());
        hjhUserAuthLog.setUserName(user.getUsername());
        hjhUserAuthLog.setOrderId(bean.getLogOrderId());
        hjhUserAuthLog.setOrderStatus(2);
        if (authType != null && authType.equals(BankCallConstant.QUERY_TYPE_2)) {
            hjhUserAuthLog.setAuthType(4);
        } else {
            hjhUserAuthLog.setAuthType(Integer.valueOf(authType));
        }
        hjhUserAuthLog.setOperateEsb(client);
        hjhUserAuthLog.setCreateUser(user.getUserId());
        hjhUserAuthLog.setCreateTime(nowTime);
        hjhUserAuthLog.setUpdateTime(nowTime);
        hjhUserAuthLog.setUpdateUser(user.getUserId());
        hjhUserAuthLog.setDelFlg(0);
        amUserClient.insertUserAuthLog(hjhUserAuthLog);
    }

    public Map<String, String> getErrorMV(AutoPlusRequestBean payRequestBean, String status) {
        Map<String, String> result = new HashMap<>();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        result.put("callBackAction", payRequestBean.getRetUrl());
        result.put("chkValue", resultBean.getChkValue());
        result.put("status", resultBean.getStatus());
        result.put("acqRes", payRequestBean.getAcqRes());
        result.put("isSuccess", "false");
        return result;
    }

    @Override
    public Map<String, String> checkParam(AutoPlusRequestBean payRequestBean) {
        Map<String, String> resultMap = new HashMap<>();
        // 检查参数是否为空
        CheckUtil.check(!payRequestBean.checkParmIsNull(), MsgEnum.STATUS_CE000001);

        // 验签
        if (!this.verifyRequestSign(payRequestBean, BaseDefine.METHOD_BORROW_AUTH_INVES)) {
            CheckUtil.check(false, MsgEnum.STATUS_CE000002);
        }
        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(payRequestBean.getAccountId());
        CheckUtil.check(bankOpenAccount != null, MsgEnum.STATUS_CE000004);
        // 检查用户是否存在
        UserVO user = amUserClient.findUserById(bankOpenAccount.getUserId());
        CheckUtil.check(user != null, MsgEnum.STATUS_CE000006);
        CheckUtil.check(user.getBankOpenAccount().intValue() == 1, MsgEnum.STATUS_CE000007);
        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        CheckUtil.check(passwordFlag == 1, MsgEnum.STATUS_TP000002);
        // 根据订单号查询授权码
        String smsSeq = amDataCollectClient.selectBankSmsSeq(user.getUserId(), BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
        CheckUtil.check(StringUtils.isNotBlank(smsSeq), MsgEnum.STATUS_CE000008);
        logger.info("-------------------授权码为！" + smsSeq + "电子账户号" + payRequestBean.getAccountId() + "！--------------------status" + user.getIsSetPassword());
        // 查询是否已经授权过
        HjhUserAuthVO hjhUserAuth = amUserClient.getHjhUserAuthByUserId(user.getUserId());
        CheckUtil.check(hjhUserAuth == null || hjhUserAuth.getAutoInvesStatus() != 1, MsgEnum.ERR_AUTHORIZE_REPEAT);
        resultMap.put("isSuccess", "true");
        resultMap.put("smsSeq", smsSeq);
        return resultMap;
    }

    @Override
    public Map<String, Object> apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean) {
        BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(payRequestBean.getAccountId());
        UserVO user = amUserClient.findUserById(bankOpenAccount.getUserId());
        Integer userId = user.getUserId();
        // 同步调用路径
        String retUrl = systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesReturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesBgreturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(), userId, type, payRequestBean.getChannel(), smsSeq, payRequestBean.getSmsCode());
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 插入日志
        this.insertUserAuthLog(user, bean, 1, BankCallConstant.QUERY_TYPE_1);
        Map<String, Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            e.printStackTrace();
            CheckUtil.check(false, MsgEnum.ERR_BANK_CALL);
        }
        return map;
    }

    @Override
    public AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type) {
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        repwdResult.setCallBackAction(callback);
        repwdResult.setAcqRes(acqRes);
        bean.convert();
        //业务变更，银行不直接返回accountId，需要根据用户Id查询账号
        if (StringUtils.isNotBlank(bean.getLogUserId())) {
            BankOpenAccountVO bankOpenAccount = amUserClient.selectById(Integer.parseInt(bean.getLogUserId()));
            repwdResult.set("accountId", bankOpenAccount.getAccount());
        } else {
            repwdResult.set("accountId", bean.getAccountId());
        }
        //投资人签约状态查询
        BankCallBean retBean = getUserAuthQUery(Integer.parseInt(bean.getLogUserId()), type);
        bean = retBean;
        if (bean != null && BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) && "1".equals(bean.getState())) {
            try {
                // 成功
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.SUCCESS);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
                repwdResult.set("deadline", bean.getDeadline());

            } catch (Exception e) {
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
                repwdResult.set("chkValue", resultBean.getChkValue());
                repwdResult.set("status", resultBean.getStatus());
            }
        } else {
            // 失败

            BaseResultBean resultBean = new BaseResultBean();
            resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE999999);
            repwdResult.set("chkValue", resultBean.getChkValue());
            repwdResult.set("status", resultBean.getStatus());
        }
        return repwdResult;
    }

    /**
     * @param callback
     * @param acqRes
     * @Author: zhangqingqing
     * @Desc :授权异步回调
     * @Param: * @param bean
     * @Date: 11:03 2018/5/31
     * @Return: BankCallResult
     */
    @Override
    public BankCallResult userAuthInvesBgreturn(BankCallBean bean, String callback, String acqRes) {

        BankCallResult result = new BankCallResult();
        String message = "授权成功";
        String status = "";
        Map<String, String> params = new HashMap<String, String>();
        // 返回值修改 end
        bean.convert();
        int userId = Integer.parseInt(bean.getLogUserId());
        UserVO user = amUserClient.findUserById(userId);
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 更新签约状态和日志表
                BankRequest bankRequest = new BankRequest();
                BeanUtils.copyProperties(bean, bankRequest);
                amUserClient.updateUserAuthInves(bankRequest);
                status = ErrorCodeConstant.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                message = "授权失败";
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        } else {
            // 失败
            message = "授权失败,失败原因：" + getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        if (StringUtils.isNotBlank(bean.getLogUserId())) {
            BankOpenAccountVO bankOpenAccount = amUserClient.selectById(Integer.parseInt(bean.getLogUserId()));
            params.put("accountId", bankOpenAccount.getAccount() == null ? bean.getAccountId() : bankOpenAccount.getAccount());
        } else {
            params.put("accountId", bean.getAccountId());
        }
        params.put("status", status);
        params.put("statusDesc", message);

        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("deadline", bean.getBidDeadline());
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes", acqRes);
        CommonSoaUtils.noRetPostThree(callback, params);
        result.setMessage(message);
        result.setStatus(true);
        return result;
    }

    @Override
    public BankCallBean getUserAuthQUery(Integer userId, String type) {
        // 调用查询投资人签约状态查询
        BankOpenAccountVO bankOpenAccount = amUserClient.selectById(userId);
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);
        selectbean.setTxCode(BankCallConstant.TXCODE_CREDIT_AUTH_QUERY);
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(BankCallConstant.CHANNEL_PC);
        selectbean.setType(type);
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        //根据银行查询投资人签约状态
        if (BankCallConstant.QUERY_TYPE_1.equals(type)) {
            selectbean.setLogRemark("用户授权自动投资");
        } else if (BankCallConstant.QUERY_TYPE_2.equals(type)) {
            selectbean.setLogRemark("用户授权自动债转");
        }
        selectbean.setLogClient(0);
        // 调用接口
        BankCallBean retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    /**
     * 组装发往江西银行参数
     *
     * @param accountId
     * @param userid
     * @param type
     * @param channel
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    private BankCallBean getCommonBankCallBean(String accountId, Integer userid, String type, String channel, String lastSrvAuthCode, String smsCode) {
        String remark = "";
        String txcode = "";
        // 构造函数已经设置
        // 版本号  交易代码  机构代码  银行代码  交易日期  交易时间  交易流水号   交易渠道
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10, txcode, userid, channel);
        if (BankCallConstant.QUERY_TYPE_1.equals(type)) {
            remark = "投资人自动投标签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1, 5), new SimpleDateFormat("yyyyMMdd")));
            bean.setTxAmount("1000000");
            bean.setTotAmount("1000000000");
        } else if (BankCallConstant.QUERY_TYPE_2.equals(type)) {
            remark = "投资人自动债权转让签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
        }
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setOrderId(bean.getLogOrderId());
        bean.setAccountId(accountId);
        bean.setForgotPwdUrl(systemConfig.getForgetpassword());
        bean.setLastSrvAuthCode(lastSrvAuthCode);
        bean.setSmsCode(smsCode);
        bean.setLogRemark(remark);

        return bean;
    }

    @Override
    public BankCallBean getTermsAuthQuery(int userId, String channel) {
        BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(userId);
        // 调用查询投资人签约状态查询
        BankCallBean selectbean = new BankCallBean();
        // 接口版本号
        selectbean.setVersion(BankCallConstant.VERSION_10);
        selectbean.setTxCode(BankCallConstant.TXCODE_TERMS_AUTH_QUERY);
        // 机构代码
        selectbean.setInstCode(systemConfig.getBankInstcode());
        selectbean.setBankCode(systemConfig.getBankCode());
        selectbean.setTxDate(GetOrderIdUtils.getTxDate());
        selectbean.setTxTime(GetOrderIdUtils.getTxTime());
        selectbean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        selectbean.setChannel(channel);
        // 电子账号
        selectbean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        selectbean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 操作者ID
        selectbean.setLogUserId(String.valueOf(userId));
        selectbean.setLogOrderId(GetOrderIdUtils.getOrderId2(userId));
        selectbean.setLogClient(0);
        // 返回参数
        BankCallBean retBean = null;
        // 调用接口
        retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    @Override
    public BankCallBean weChatGetCommonBankCallBean(UserVO users, int type, String srvAuthCode, String code, String sign) {
        String remark = "";
        String txcode = "";

        BankCallBean bean = new BankCallBean();
        // 同步调用路径
        String retUrl = systemConfig.getWeChatHost() + "/user/setting/authorization/result/failed?sign="+sign+"&logOrdId="+bean.getLogOrderId();
        String success = systemConfig.getWeChatHost() + "/user/setting/authorization/result/success?sign="+sign;
        // 异步调用路
        String bgRetUrl = systemConfig.getWeChatHost() + "/user/autoplus";
        String forgetPassworedUrl = CustomConstants.FORGET_PASSWORD_URL + "?sign=" + sign;

        if (type == 1) {
            // 2.4.4.投资人自动投标签约增强
            bgRetUrl += "/userAuthInvesBgreturn";
            remark = "投资人自动投标签约增强";
            txcode = BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS;
            // 签约到期时间
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1, 5), new SimpleDateFormat("yyyyMMdd")));
            // 单笔投标金额的上限
            bean.setTxAmount("1000000");
            // 自动投标总金额上限（不算已还金额）
            bean.setTotAmount("1000000000");
        } else if (type == 2) {
            // 2.4.8.投资人自动债权转让签约增强
            bgRetUrl += "/userAuthCreditBgreturn";
            remark = "投资人自动债权转让签约增强";
            txcode = BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS;
        }
        String orderId = GetOrderIdUtils.getOrderId2(users.getUserId());
        // 取得用户在江西银行的客户号
        BankOpenAccountVO bankOpenAccount = this.getBankOpenAccount(users.getUserId());
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setTxCode(txcode);
        bean.setChannel(BankCallConstant.CHANNEL_WEI);
        bean.setAccountId(bankOpenAccount.getAccount());
        bean.setOrderId(orderId);
        bean.setForgotPwdUrl(forgetPassworedUrl);
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(success);
        bean.setNotifyUrl(bgRetUrl);
        bean.setLastSrvAuthCode(srvAuthCode);
        bean.setSmsCode(code);

        // 操作者ID
        bean.setLogUserId(String.valueOf(users.getUserId()));
        bean.setLogOrderId(orderId);
        bean.setLogRemark(remark);
        bean.setLogClient(0);
        return bean;
    }
}
