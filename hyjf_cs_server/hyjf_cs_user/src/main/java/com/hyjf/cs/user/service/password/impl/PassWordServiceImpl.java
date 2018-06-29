/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password.impl;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.resquest.user.SmsCodeRequest;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.bank.LogAcqResBean;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.CheckException;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.exception.ReturnMessageException;
import com.hyjf.common.util.*;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.bean.BaseDefine;
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.ThirdPartyTransPasswordRequestBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.Producer;
import com.hyjf.cs.user.mq.SmsProducer;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.util.ErrorCodeConstant;
import com.hyjf.cs.user.util.RSAJSPUtil;
import com.hyjf.cs.user.vo.PasswordRequest;
import com.hyjf.cs.user.vo.SendSmsVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wangc
 * @version PassWordServiceImpl, v0.1 2018/6/14 14:10
 */
@Service
public class  PassWordServiceImpl  extends BaseUserServiceImpl implements PassWordService {


    private static Logger logger = LoggerFactory.getLogger(PassWordServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private SmsProducer smsProducer;

    @Autowired
    SystemConfig systemConfig;

    /**
     * 修改用户登录密码
     * @param
     * @param newPW
     * @return
     */
    @Override
    public void updatePassWd(UserVO user, String newPW) {
        logger.info("UserController.updatePassWd run.. newPW is :{}",newPW);
        user.setPassword(MD5Utils.MD5(MD5Utils.MD5(newPW) + user.getSalt()));
        boolean success =  amUserClient.updateUserById(user) > 0;
        CheckUtil.check(success,MsgEnum.ERR_PASSWORD_MODIFY);
    }

    @Override
    public void updateUserIsSetPassword(Integer userId) {
        UserVO iuser = new UserVO();
        iuser.setUserId(userId);
        iuser.setIsSetPassword(1);
        amUserClient.updateUserById(iuser);
    }

    @Override
    public Map<String, Object> setPassword(UserVO user) {
        // 判断用户是否开户
        int accountFlag = user.getBankOpenAccount();
        // 未开户
        if (accountFlag!=1) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        }
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        CheckUtil.check(passwordFlag != 1, MsgEnum.STATUS_TP000001);
        int userId = user.getUserId();
        // 取得用户详细信息
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        // 调用设置密码接口
        String txcode="";
        BankCallBean bean = new BankCallBean(userId,txcode, ClientConstants.WEB_CLIENT);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_SET);
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        if(user.getUserType() == 1){
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= getCorpOpenAccountRecord(userId);
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
            bean.setName(userInfoVO.getTruename());
        }
        // 电子账号
        bean.setAccountId(bankAccount.getAccount());
        bean.setMobile(user.getMobile());
        String retUrl = systemConfig.getFrontHost() + "/password/openError"+"?logOrdId="+bean.getLogOrderId();
        String successUrl = systemConfig.getFrontHost() +"/password/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.getWebHost() + "/app/user/password/passwordBgreturn";
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDSET);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        bean.setLogRemark("设置交易密码");
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        return map;
    }

    @Override
    public Map<String, Object> resetPassword(UserVO user) {
        int userId = user.getUserId();
        UserInfoVO userInfoVO = amUserClient.findUsersInfoById(userId);
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        // 调用设置密码接口
        BankCallBean bean = new BankCallBean();
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        // 消息类型(密码重置)
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET);
        if(user.getUserType() == 1){
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record= getCorpOpenAccountRecord(userId);
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        }else{
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
            bean.setName(userInfoVO.getTruename());
        }
        // 电子账号
        bean.setAccountId(bankAccount.getAccount());
        bean.setMobile(user.getMobile());
        String retUrl = systemConfig.getFrontHost() + "/password/openError"+"?logOrdId="+bean.getLogOrderId();
        String successUrl = systemConfig.getFrontHost() +"/password/openSuccess";
        // 异步调用路
        String bgRetUrl = systemConfig.webHost + "/web/user/password/resetPasswordBgreturn";
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 商户私有域，存放开户平台,用户userId
        LogAcqResBean acqRes = new LogAcqResBean();
        acqRes.setUserId(userId);
        bean.setLogAcqResBean(acqRes);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_MOBILE);
        bean.setLogOrderId(GetOrderIdUtils.getUsrId(userId));
        Map<String,Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        return map;
    }

    @Override
    public void checkParam(UserVO userVO, String oldPW, String newPW, String pwSure) {
        CheckUtil.check(StringUtils.isNotBlank(oldPW),MsgEnum.ERR_OBJECT_REQUIRED,"原始登录密码");
        CheckUtil.check(StringUtils.isNotBlank(newPW)&&StringUtils.isNotBlank(pwSure),MsgEnum.ERR_OBJECT_REQUIRED,"新密码");
        oldPW = RSAJSPUtil.rsaToPassword(oldPW);
        newPW = RSAJSPUtil.rsaToPassword(newPW);
        pwSure = RSAJSPUtil.rsaToPassword(pwSure);
        CheckUtil.check(newPW.equals(pwSure),MsgEnum.ERR_PASSWORD_TWO_DIFFERENT_PASSWORD);
        // 验证用的password
        oldPW = MD5Utils.MD5(MD5Utils.MD5(oldPW) + userVO.getSalt());
        CheckUtil.check(oldPW.equals(userVO.getPassword()),MsgEnum.ERR_PASSWORD_OLD_INCORRECT);
        CheckUtil.check(!oldPW.equals(newPW),MsgEnum.ERR_PASSWORD_TWO_SAME_PASSWORD);
        checkPassword(newPW);
    }

    @Override
    public void weChatCheckParam(UserVO userVO,String newPassword, String oldPassword) {
        oldPassword = MD5Utils.MD5(MD5Utils.MD5(oldPassword) + userVO.getSalt());
        CheckUtil.check( StringUtils.isNotBlank(oldPassword) && oldPassword.equals(userVO.getPassword()),MsgEnum.ERR_PASSWORD_OLD_INCORRECT);
        checkPassword(newPassword);
    }

    @Override
    public void appCheckParam(String key, UserVO userVO,PasswordRequest passwordRequest) {
         String version = passwordRequest.getVersion();
         String netStatus = passwordRequest.getNetStatus();
         String platform = passwordRequest.getPlatform();
         String randomString = passwordRequest.getRandomString();
         String order = passwordRequest.getOrder();
         String newPassword = passwordRequest.getNewPassword();
         String oldPassword = passwordRequest.getOldPassword();
        // 检查参数正确性
        CheckUtil.check(StringUtils.isNotBlank(version)&&StringUtils.isNotBlank(netStatus)&&StringUtils.isNotBlank(platform)&&StringUtils.isNotBlank(randomString)&&StringUtils.isNotBlank(order),MsgEnum.STATUS_CE000001);
        try {
            // 解密
            oldPassword = DES.decodeValue(key, oldPassword);
            CheckUtil.check(StringUtils.isNotBlank(oldPassword),MsgEnum.ERR_OBJECT_REQUIRED,"旧密码");
            oldPassword = MD5Utils.MD5(MD5Utils.MD5(oldPassword)+userVO.getSalt());
            // 密码正确时
            CheckUtil.check(StringUtils.isNotBlank(oldPassword) && oldPassword.equals(userVO.getPassword()),MsgEnum.ERR_PASSWORD_OLD_INCORRECT);
        } catch (Exception e) {
            throw new CheckException(MsgEnum.STATUS_CE000001);
        }
        // 解密
        newPassword = DES.decodeValue(key, newPassword);
        CheckUtil.check(StringUtils.isNotBlank(newPassword),MsgEnum.ERR_PASSWORD_NEW_REQUIRED);
        checkPassword(newPassword);
    }

    public void checkPassword(String password){
        CheckUtil.check(password.length()>=6 || password.length() <= 16,MsgEnum.ERR_PASSWORD_LENGTH);
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
        CheckUtil.check(hasNumber,MsgEnum.ERR_PASSWORD_NO_NUMBER);
        String regEx = "^[a-zA-Z0-9]+$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        CheckUtil.check(m.matches(),MsgEnum.ERR_FMT_PASSWORD);
    }

    /**
     * 微信修改密码发送短信验证码
     * @param sendSmsVO
     */
    @Override
    public void sendCode(SendSmsVO sendSmsVO) {
        try {
            //获取系统参数
            SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
            //发送前校验
            this.validateData(sendSmsVO,smsConfig);
            //发送短信并将发送数据存到数据库和Redis
            this.sendSms(sendSmsVO,smsConfig);
        } catch (Exception e){
            throw new CheckException(MsgEnum.ERR_SYSTEM_UNUSUAL);
        }
    }

    @Override
    public void backCheck(SmsCodeRequest request, String newPassword) {
        // 检查参数正确性
        CheckUtil.check(StringUtils.isNotBlank(newPassword) ||StringUtils.isNotBlank(request.getVerificationCode()),MsgEnum.STATUS_CE000001);
        checkPassword(newPassword);
        CheckUtil.check(StringUtils.isNotBlank(request.getMobile()),MsgEnum.ERR_OBJECT_REQUIRED,"手机号");
        CheckUtil.check(StringUtils.isNotBlank(request.getVerificationCode()),MsgEnum.ERR_OBJECT_REQUIRED,"验证码");
        int cnt = amUserClient.checkMobileCode( request.getMobile(),request.getVerificationCode(),request.getVerificationType(),request.getPlatform(), CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED);
        CheckUtil.check(cnt > 0,MsgEnum.STATUS_ZC000015);
    }

    @Override
    public Map<String,Object> apiSetPassword(ThirdPartyTransPasswordRequestBean param,String bankDetailUrl,String txCode) {
        Map<String,Object> result = new HashMap<>();
        String account = param.getAccountId();
        String retUrl = param.getRetUrl();
        String bgRetUrl = param.getBgRetUrl();
        String channel = param.getChannel();
        String instCode = param.getInstCode();
        String acqRes = param.getAcqRes();
        BaseResultBean resultBean = new BaseResultBean();
        if (StringUtils.isEmpty(account)||StringUtils.isEmpty(retUrl)||StringUtils.isEmpty(bgRetUrl)||StringUtils.isEmpty(channel)||StringUtils.isEmpty(instCode)) {
            if(StringUtils.isNotEmpty(bgRetUrl)){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("statusDesc","请求参数非法");
                params.put("acqRes",acqRes);
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                params.put("status", resultBean.getStatus());
                params.put("chkValue", resultBean.getChkValue());
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            CheckUtil.check(StringUtils.isEmpty(retUrl),MsgEnum.STATUS_CE000001);
        }
        //验签  暂时去掉验签
        if(!this.verifyRequestSign(param, BaseDefine.METHOD_SERVER_SET_PASSWORD)){
            if(Validator.isNotNull(bgRetUrl)){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","验签失败！");
                params.put("acqRes",acqRes);
                params.put("chkValue", resultBean.getChkValue());
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            throw new CheckException(MsgEnum.STATUS_CE000002);
        }
        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(account);
        if(bankOpenAccount == null){
            if(Validator.isNotNull(bgRetUrl)){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","没有根据电子银行卡找到用户 ");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            throw new CheckException(MsgEnum.STATUS_CE000004);
        }
        UserVO user = getUsersById(bankOpenAccount.getUserId());
        if (user == null) {
            if(Validator.isNotNull(bgRetUrl)){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","用户不存在汇盈金服账户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            throw new CheckException(MsgEnum.STATUS_CE000006);
        }
        if (user.getBankOpenAccount().intValue() != 1) {
            if(Validator.isNotNull(bgRetUrl)){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","用户未开户！");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            throw new CheckException(MsgEnum.STATUS_CE000007);
        }
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (passwordFlag == 1) {
            if(Validator.isNotNull(bgRetUrl)){
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", resultBean.getStatus());
                params.put("statusDesc","已设置交易密码");
                params.put("chkValue", resultBean.getChkValue());
                params.put("acqRes",acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            throw new CheckException(MsgEnum.STATUS_TP000001);
        }
        //拼装参数
        UserInfoVO usersInfo = getUserInfo(user.getUserId());
        // 同步调用路径
         retUrl = systemConfig.getFrontHost() + "/password/openError"+"?acqRes="+acqRes+"&callback="+retUrl.replace("#", "*-*-*");
         String successUrl = systemConfig.getFrontHost() +"/password/openSuccess";
        // 异步调用路
         bgRetUrl = systemConfig.webHost + "/api/user/password/passwordReturn.do?acqRes="+acqRes+"&callback="+bgRetUrl.replace("#", "*-*-*");
        // 调用设置密码接口
        System.out.println(retUrl+"..."+bgRetUrl);
        BankCallBean bean = new BankCallBean();
        bean.setTxCode(txCode);
        bean.setChannel(channel);
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());
        bean.setRetUrl(retUrl);// 页面同步返回 URL
        bean.setSuccessfulUrl(successUrl);
        bean.setNotifyUrl(bgRetUrl);// 页面异步返回URL(必须)
        // 操作者ID
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(bankDetailUrl);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(user.getUserId()));
        //掉银行接口
        try {
            result = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 发送短信
     * @param
     */
    private void sendSms(SendSmsVO sendSmsVO,SmsConfigVO smsConfig) throws MQException {
        // 生成验证码
        String checkCode = GetCode.getRandomSMSCode(6);
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", checkCode);

        // 发送短信验证码
        SmsMessage smsMessage = new SmsMessage(null, param, sendSmsVO.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_ZHAOHUIMIMA, CustomConstants.CHANNEL_TYPE_NORMAL);
        // 发送
        smsProducer.messageSend(new Producer.MassageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(),JSON.toJSONBytes(smsMessage)));
            // 累加手机次数
            String currentMaxPhoneCount = RedisUtils.get(sendSmsVO.getMobile() + ":MaxPhoneCount");
            if (StringUtils.isBlank(currentMaxPhoneCount)) {
                currentMaxPhoneCount = "0";
            }
            RedisUtils.set(sendSmsVO.getMobile() + ":MaxPhoneCount", (Integer.valueOf(currentMaxPhoneCount) + 1) + "", RedisUtils.getRemainMiao());
            // 保存短信验证码(由于验证没有用到，将其注释掉)
            amUserClient.saveSmsCode(sendSmsVO.getMobile(), checkCode, CustomConstants.PARAM_TPL_ZHAOHUIMIMA, CommonConstant.CKCODE_NEW, CustomConstants.CLIENT_WECHAT);
            // 发送checkCode最大时间间隔，默认60秒
         RedisUtils.set(sendSmsVO.getMobile() + ":" + CustomConstants.PARAM_TPL_ZHAOHUIMIMA + ":IntervalTime", sendSmsVO.getMobile(), smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
    }

    /**
     * 参数校验
     * @param sendSmsVo
     */
    private void validateData(SendSmsVO sendSmsVo,SmsConfigVO smsConfig) {
        CheckUtil.check(StringUtils.isNotBlank(sendSmsVo.getMobile()),MsgEnum.ERR_OBJECT_REQUIRED,"手机号");
        CheckUtil.check(Validator.isMobile(sendSmsVo.getMobile()),MsgEnum.STATUS_CE000010);
        //查询该手机号是在数据库中已存在
        CheckUtil.check(existUser(sendSmsVo.getMobile()),MsgEnum.ERR_USER_NOT_EXISTS);
        // 判断发送间隔时间
        String key = sendSmsVo.getMobile() + ":" + CustomConstants.PARAM_TPL_ZHAOHUIMIMA + ":IntervalTime";
        String intervalTime = RedisUtils.get(key);
        CheckUtil.check(StringUtils.isBlank(intervalTime),MsgEnum.ERR_SMSCODE_SEND_TOO_FAST);
        //判断该设备号的发送次数（暂时不做）

        // 判断最大发送数max_phone_count（当日该手机号发送的次数）
        String count = RedisUtils.get(sendSmsVo.getMobile() + ":MaxPhoneCount");
        if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
            count = "0";
            RedisUtils.set(sendSmsVo.getMobile() + ":MaxPhoneCount", "0");
        }
        CheckUtil.check(Integer.valueOf(count) <= smsConfig.getMaxPhoneCount(),MsgEnum.ERR_SMSCODE_SEND_TOO_MANNY);
    }
}
