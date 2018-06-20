/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.autoplus.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.hyjf.common.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.resquest.user.BankRequest;
import com.hyjf.am.vo.trade.BankReturnCodeConfigVO;
import com.hyjf.am.vo.user.*;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.*;
import com.hyjf.cs.user.client.BankOpenClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.AuthorizedError;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.autoplus.AutoPlusService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.cs.user.util.ResultEnum;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.bean.BankCallResult;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallStatusConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;

/**
 * @author zhangqingqing
 * @version AutoPlusServiceImpl, v0.1 2018/6/11 15:39
 */
@Service
public class AutoPlusServiceImpl extends BaseUserServiceImpl implements AutoPlusService  {
    private static final Logger logger = LoggerFactory.getLogger(AutoPlusServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private BankOpenClient bankOpenClient;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 自动投资、债转授权
     * @param user
     * @param client 0web 1wechat 2app
     * @param type 1表示投资 2表示债转
     * @param
     * @return
     */
    @Override
    public BankCallBean userCreditAuthInves(UserVO user , Integer client, String type, String channel, String lastSrvAuthCode, String smsCode) {
        // 判断是否授权过
        HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
        if(hjhUserAuth!=null&&hjhUserAuth.getAutoCreditStatus().intValue()==1){
            throw new ReturnMessageException(AuthorizedError.CANNOT_REPEAT_ERROR);
        }
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(user,type,client,channel,lastSrvAuthCode,smsCode);
        // 插入日志
        this.insertUserAuthLog(user, bean,client,type);
        return bean;
    }

    /**
     * app wechat授权自动债转、投资同步回调
     * @param token
     * @param bean
     * @param userAutoType 1债转 0投资
     * @param
     * @return
     */
    @Override
    public Map<String,BaseMapBean>  userAuthCreditReturn(String token, BankCallBean bean, String userAutoType, String sign, String isSuccess) {
        Map<String,BaseMapBean> result = new HashMap<>();
        bean.convert();
        // 用户ID
        Integer userId = Integer.parseInt(bean.getLogUserId());
        HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(userId);
        if (isSuccess == null || !ClientConstants.ISSUCCESS.equals(isSuccess)|| hjhUserAuth == null||hjhUserAuth.getAutoCreditStatus()!=1) {
            if (ClientConstants.INVES_AUTO_TYPE.equals(userAutoType)){
                result = getErrorMap(ResultEnum.USER_ERROR_204,sign,userAutoType, hjhUserAuth);
            }else {
                result = getErrorMap(ResultEnum.USER_ERROR_205,sign,userAutoType, hjhUserAuth);
            }
        }else {
            result = getSuccessMap(sign, userAutoType, hjhUserAuth);
        }
        return result;
    }

    /**
     * 组装跳转错误页面MV
     * @param param
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private Map<String,BaseMapBean> getErrorMap(ResultEnum param,String sign, String type, HjhUserAuthVO hjhUserAuth) {
        Map<String,BaseMapBean> result = new HashMap<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, param.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, param.getStatusDesc());
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.setCallBackAction(systemConfig.getWebHost()+"/user/setting/authorization/result/failed");
        result.put("callBackForm", baseMapBean);
        return result;
    }

    /**
     * 组装跳转成功页面MV
     * @param sign
     * @param type
     * @param hjhUserAuth
     * @return
     */
    private Map<String,BaseMapBean> getSuccessMap(String sign, String type, HjhUserAuthVO hjhUserAuth) {
        Map<String,BaseMapBean> result = new HashMap<>();
        BaseMapBean baseMapBean = new BaseMapBean();
        baseMapBean.set(CustomConstants.APP_STATUS, ResultEnum.SUCCESS.getStatus());
        baseMapBean.set(CustomConstants.APP_STATUS_DESC, ResultEnum.SUCCESS.getStatusDesc());
        baseMapBean.set(CustomConstants.APP_SIGN, sign);
        baseMapBean.set("autoInvesStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoInvesStatus()==null?"0":hjhUserAuth.getAutoInvesStatus()+ "");
        baseMapBean.set("autoCreditStatus", hjhUserAuth==null?"0":hjhUserAuth.getAutoCreditStatus()==null?"0":hjhUserAuth.getAutoCreditStatus() + "");
        baseMapBean.set("userAutoType", type);
        baseMapBean.setCallBackAction(systemConfig.getWebHost()+"/user/setting/authorization/result/success");
        result.put("callBackForm", baseMapBean);
        return result;
    }


    /**
     * web自动投资授权同步回调
     * @param token
     * @param bean
     * @param
     * @return
     */
    @Override
    public Map<String,String> userAuthReturn(String token, BankCallBean bean, String urlType, String isSuccess) {
        Map<String,String> resultMap = new HashMap<>();
        resultMap.put("status", "success");
        bean.convert();
        WebViewUser user = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS+token, WebViewUser.class);
        if (user == null) {
            throw new ReturnMessageException(AuthorizedError.USER_LOGIN_ERROR);
        }
        if (isSuccess == null || !ClientConstants.ISSUCCESS.equals(isSuccess)) {
            resultMap.put("status", "fail");
        }
        logger.info("自动投资授权同步回调调用查询接口查询状态结束  结果为:" + (bean == null ? "空" : bean.getRetCode()));
        HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
        if(hjhUserAuth.getAutoCreditStatus()==0) {
            resultMap.put("typeURL", urlType);
        }else {
            resultMap.put("typeURL", "0");
        }
        return resultMap;
    }

    /**
     * 异步回调接口
     * @param bean
     * @return
     */
    @Override
    public String userBgreturn(BankCallBean bean) {
        BankCallResult result = new BankCallResult();
        logger.info("[用户授权异步回调开始]");
        bean.convert();
        Integer userId = Integer.parseInt(bean.getLogUserId());
        // 查询用户开户状态
        UserVO user= amUserClient.findUserById(userId);
        // 成功
        if (user != null && BankCallConstant.RESPCODE_SUCCESS.equals(bean.get(BankCallConstant.PARAM_RETCODE))) {
            try {
                // 更新签约状态和日志表
                BankRequest bankRequest = new BankRequest();
                BeanUtils.copyProperties(bean,bankRequest);
                amUserClient.updateUserAuthInves(bankRequest);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("userAuthInvesBgreturn",e);
            }
        }
        logger.info("[用户授权完成后,回调结束]");
        result.setStatus(true);
        return JSONObject.toJSONString(result, true);
    }

    /**
     * 组装发往江西银行参数
     * @param users
     * @param type
     * @param lastSrvAuthCode
     * @param smsCode
     * @return
     */
    private BankCallBean getCommonBankCallBean(UserVO users,String type,Integer client, String channel,String lastSrvAuthCode,String smsCode) {
        String remark = "";
        String txcode = "";
        // 同步地址  是否跳转到前端页面
        String retUrl = systemConfig.getWebHost()+ ClientConstants.CLIENT_HEADER_MAP.get(client)+"/open/faild";
        String successUrl = systemConfig.getWebHost()+ClientConstants.CLIENT_HEADER_MAP.get(client)+"/open/success";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/web/user/bgreturn";
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,users.getUserId(),channel);
        if(BankCallConstant.QUERY_TYPE_1.equals(type)){
            remark = "投资人自动投标签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
            bean.setTxAmount("1000000");
            bean.setTotAmount("1000000000");
        } else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
            remark = "投资人自动债权转让签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
        }
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);
        //1wechat 2app
        if(client == 1 || client == 2){
            bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
            bean.setOrderId(bean.getLogOrderId());
            bean.setAccountId(StringUtil.toString(users.getBankOpenAccount()));
            bean.setForgotPwdUrl(systemConfig.getForgetpassword());
            bean.setLastSrvAuthCode(lastSrvAuthCode);
            bean.setSmsCode(smsCode);
            bean.setLogRemark(remark);
        }else {
            String orderId = GetOrderIdUtils.getOrderId2(users.getUserId());
            // 取得用户在江西银行的客户号
            BankOpenAccountVO bankOpenAccount = bankOpenClient.selectById(users.getUserId());
            bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
            bean.setInstCode(systemConfig.getBankInstcode());
            bean.setBankCode(systemConfig.getBankCode());
            bean.setTxDate(GetOrderIdUtils.getTxDate());
            bean.setTxTime(GetOrderIdUtils.getTxTime());
            bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
            bean.setChannel(channel);
            bean.setAccountId(bankOpenAccount.getAccount());
            bean.setOrderId(orderId);
            bean.setForgotPwdUrl(systemConfig.getForgetpassword());
            bean.setLastSrvAuthCode(lastSrvAuthCode);
            bean.setSmsCode(smsCode);
            // 操作者ID
            bean.setLogUserId(String.valueOf(users.getUserId()));
            bean.setLogOrderId(orderId);
            bean.setLogRemark(remark);
            bean.setLogClient(client);
        }
        return bean;
    }


    /**
     * 检查用户信息
     * @param users
     * @param lastSrvAuthCode
     * @param smsCode
     */
    @Override
    public void checkUserMessage(UserVO users, String lastSrvAuthCode, String smsCode){
        if (users == null) {
            throw new ReturnMessageException(AuthorizedError.USER_LOGIN_ERROR);
        }
        // 检查数据是否完整
        if (Validator.isNull(lastSrvAuthCode) || Validator.isNull(smsCode)) {
            throw new ReturnMessageException(AuthorizedError.PARAM_ERROR);
        }
        if (users.getBankOpenAccount()==0) {
            throw new ReturnMessageException(AuthorizedError.NOT_REGIST_ERROR);
        }
        // 判断用户是否设置过交易密码
        if (users.getIsSetPassword() == 0) {
            throw new ReturnMessageException(AuthorizedError.NOT_SET_PWD_ERROR);
        }
    }

    /**
     *插入日志
     * @param user
     * @param bean
     * @param client
     * @param authType
     */
    public void insertUserAuthLog(UserVO user, BankCallBean bean, Integer client, String authType) {
        Date nowTime=GetDate.getNowTime();
        HjhUserAuthLogVO hjhUserAuthLog=new HjhUserAuthLogVO();
        hjhUserAuthLog.setUserId(user.getUserId());
        hjhUserAuthLog.setUserName(user.getUsername());
        hjhUserAuthLog.setOrderId(bean.getLogOrderId());
        hjhUserAuthLog.setOrderStatus(2);
        if(authType!=null&&authType.equals(BankCallConstant.QUERY_TYPE_2)){
            hjhUserAuthLog.setAuthType(4);
        }else{
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



    @Override
    public Map<String,String> getErrorMV(AutoPlusRequestBean payRequestBean, String status) {
        Map<String,String> result = new HashMap<>();
        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        result.put("callBackAction",payRequestBean.getRetUrl());
        result.put("chkValue", resultBean.getChkValue());
        result.put("status", resultBean.getStatus());
        result.put("acqRes",payRequestBean.getAcqRes());
        result.put("isSuccess","false");
        return result;
    }

    @Override
    public Map<String,String> checkParam(AutoPlusRequestBean payRequestBean){
        Map<String,String> resultMap = new HashMap<>();
        // 检查参数是否为空
        if(payRequestBean.checkParmIsNull()){
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000001, "请求参数异常"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000001);
        }
        // 验签
        if (!this.verifyRequestSign(payRequestBean, BaseDefine.METHOD_BORROW_AUTH_INVES)) {
            payRequestBean.doNotify(payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000002, "验签失败"));
            logger.info("请求参数异常" + JSONObject.toJSONString(payRequestBean, true) + "]");
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000002);
        }
        // 根据电子账户号查询用户ID
        BankOpenAccountVO bankOpenAccount = this.bankOpenClient.selectByAccountId(payRequestBean.getAccountId());
        if(bankOpenAccount == null){
            logger.info("-------------------没有根据电子银行卡找到用户"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000004,"没有根据电子银行卡找到用户");
            payRequestBean.doNotify(params);
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000004);
        }
        // 检查用户是否存在
        UserVO user= amUserClient.findUserById(bankOpenAccount.getUserId());
        if (user == null) {
            logger.info("-------------------用户不存在汇盈金服账户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000007,"用户不存在汇盈金服账户！");
            payRequestBean.doNotify(params);
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000007);
        }
        if (user.getBankOpenAccount().intValue() != 1) {
            logger.info("-------------------用户未开户！"+payRequestBean.getAccountId()+"！--------------------");
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000006,"用户未开户！");
            payRequestBean.doNotify(params);
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000006);
        }
        // 检查是否设置交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag != 1) {
            logger.info("-------------------未设置交易密码！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_TP000002,"未设置交易密码！");
            payRequestBean.doNotify(params);
            return getErrorMV(payRequestBean,  ErrorCodeConstant.STATUS_TP000002);
        }
        // TODO: 2018/5/24 xiashuqing 根据订单号查询授权码
        //this.autoPlusService.selectBankSmsSeq(userId, BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
        String smsSeq = null;
        if (StringUtils.isBlank(smsSeq)) {
            logger.info("-------------------授权码为空！"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000008,"未查询到短信授权码！");
            payRequestBean.doNotify(params);
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000008);
        }
        logger.info("-------------------授权码为！"+smsSeq+"电子账户号"+payRequestBean.getAccountId()+"！--------------------status"+user.getIsSetPassword());
        // 查询是否已经授权过
        HjhUserAuthVO hjhUserAuth=amUserClient.getHjhUserAuthByUserId(user.getUserId());
        if(hjhUserAuth!=null&&hjhUserAuth.getAutoInvesStatus()==1){
            logger.info("-------------------已经授权过！"+payRequestBean.getAccountId());
            Map<String, String> params = payRequestBean.getErrorMap(ErrorCodeConstant.STATUS_CE000009,"已授权,请勿重复授权！");
            payRequestBean.doNotify(params);
            return getErrorMV(payRequestBean, ErrorCodeConstant.STATUS_CE000009);
        }else {
            resultMap.put("isSuccess","true");
            resultMap.put("smsSeq",smsSeq);
            return null;
        }
    }

    @Override
    public BankCallBean apiUserAuth(String type, String smsSeq, AutoPlusRequestBean payRequestBean) {
        BankOpenAccountVO bankOpenAccount = this.bankOpenClient.selectByAccountId(payRequestBean.getAccountId());
        UserVO user= amUserClient.findUserById(bankOpenAccount.getUserId());
        Integer userId = user.getUserId();
        // 同步调用路径
        String retUrl = systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesReturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getRetUrl().replace("#", "*-*-*");
        // 异步调用路
        String bgRetUrl =systemConfig.getWebHost()
                + "/server/autoPlus/userAuthInvesBgreturn?acqRes="
                + payRequestBean.getAcqRes() + "&callback=" + payRequestBean.getNotifyUrl().replace("#", "*-*-*");
        // 组装发往江西银行参数
        BankCallBean bean = getCommonBankCallBean(payRequestBean.getAccountId(),userId,type,payRequestBean.getChannel(),smsSeq,payRequestBean.getSmsCode());
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 插入日志
        this.insertUserAuthLog(user, bean,1, BankCallConstant.QUERY_TYPE_1);
        return bean;
    }

    @Override
    public AutoPlusRetBean userAuthCreditReturn(BankCallBean bean, String callback, String acqRes, String type) {
        AutoPlusRetBean repwdResult = new AutoPlusRetBean();
        repwdResult.setCallBackAction(callback);
        repwdResult.setAcqRes(acqRes);
        bean.convert();
        //业务变更，银行不直接返回accountId，需要根据用户Id查询账号
        if (StringUtils.isNotBlank(bean.getLogUserId())) {
            BankOpenAccountVO bankOpenAccount = bankOpenClient.selectById(Integer.parseInt(bean.getLogUserId()));
            repwdResult.set("accountId", bankOpenAccount.getAccount());
        }else{
            repwdResult.set("accountId", bean.getAccountId());
        }
        //投资人签约状态查询
        BankCallBean retBean = getUserAuthQUery(Integer.parseInt(bean.getLogUserId()),type);
        bean = retBean;
        if (bean!=null&& BankCallStatusConstant.RESPCODE_SUCCESS.equals(bean.getRetCode()) && "1".equals(bean.getState())) {
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
     * @Author: zhangqingqing
     * @Desc :授权异步回调
     * @Param: * @param bean
     * @param callback
     * @param acqRes
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
                BeanUtils.copyProperties(bean,bankRequest);
                amUserClient.updateUserAuthInves(bankRequest);
                status = ErrorCodeConstant.SUCCESS;
            } catch (Exception e) {
                e.printStackTrace();
                message = "授权失败";
                status = ErrorCodeConstant.STATUS_CE999999;
            }
        }else{
            // 失败
            message = "授权失败,失败原因：" + getBankRetMsg(bean.getRetCode());
            status = ErrorCodeConstant.STATUS_CE999999;
        }
        // 返回值
        if (StringUtils.isNotBlank(bean.getLogUserId())) {
            BankOpenAccountVO bankOpenAccount = bankOpenClient.selectById(Integer.parseInt(bean.getLogUserId()));
            params.put("accountId", bankOpenAccount.getAccount()==null?bean.getAccountId():bankOpenAccount.getAccount());
        }else{
            params.put("accountId", bean.getAccountId());
        }
        params.put("status", status);
        params.put("statusDesc",message);

        BaseResultBean resultBean = new BaseResultBean();
        resultBean.setStatusForResponse(status);
        params.put("deadline", bean.getBidDeadline());
        params.put("chkValue", resultBean.getChkValue());
        params.put("acqRes",acqRes);
        CommonSoaUtils.noRetPostThree(callback, params);
        result.setMessage(message);
        result.setStatus(true);
        return result;
    }

    public BankCallBean getUserAuthQUery(Integer userId,String type) {
        // 调用查询投资人签约状态查询
        BankOpenAccountVO bankOpenAccount = bankOpenClient.selectById(userId);
        BankCallBean selectbean = new BankCallBean();
        selectbean.setVersion(BankCallConstant.VERSION_10);
        selectbean.setTxCode(BankCallConstant.TXCODE_CREDIT_AUTH_QUERY);
        selectbean.setInstCode(systemConfig.getBankInstcode());
        selectbean.setBankCode(systemConfig.getBankCode());
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
        if(BankCallConstant.QUERY_TYPE_1.equals(type)){
            selectbean.setLogRemark("用户授权自动投资");
        }else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
            selectbean.setLogRemark("用户授权自动债转");
        }
        selectbean.setLogClient(0);
        // 调用接口
        BankCallBean retBean = BankCallUtils.callApiBg(selectbean);
        return retBean;
    }

    /**
     * 组装发往江西银行参数
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
        BankCallBean bean = new BankCallBean(BankCallConstant.VERSION_10,txcode,userid,channel);
        if(BankCallConstant.QUERY_TYPE_1.equals(type)){
            remark = "投资人自动投标签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_BID_AUTH_PLUS);
            bean.setDeadline(GetDate.date2Str(GetDate.countDate(1,5),new SimpleDateFormat("yyyyMMdd")));
            bean.setTxAmount("1000000");
            bean.setTotAmount("1000000000");
        } else if(BankCallConstant.QUERY_TYPE_2.equals(type)){
            remark = "投资人自动债权转让签约增强";
            bean.setTxCode(BankCallConstant.TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS);
        }
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE_PLUS);
        bean.setOrderId(bean.getLogOrderId());
        bean.setAccountId(accountId);
        // bean.setForgotPwdUrl(CustomConstants.FORGET_PASSWORD_URL);todo
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
        //selectbean.setInstCode(PropUtils.getSystem(BankCallConstant.BANK_INSTCODE)); todo
        // selectbean.setBankCode(PropUtils.getSystem(BankCallConstant.BANK_BANKCODE)); todo
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

    public String getBankRetMsg(String retCode) {
        //如果错误码不为空
        if (StringUtils.isNotBlank(retCode)) {
            BankReturnCodeConfigVO retCodes = this.amUserClient.getBankReturnCodeConfig(retCode);
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
}
