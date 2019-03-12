/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.service.password.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.bean.app.BaseResultBeanFrontEnd;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.trade.CorpOpenAccountRecordVO;
import com.hyjf.am.vo.user.BankOpenAccountVO;
import com.hyjf.am.vo.user.UserInfoVO;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.common.cache.RedisConstants;
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
import com.hyjf.cs.user.bean.BaseResultBean;
import com.hyjf.cs.user.bean.ThirdPartyTransPasswordRequestBean;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmDataCollectClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.constants.ErrorCodeConstant;
import com.hyjf.cs.user.constants.ResultEnum;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.password.PassWordService;
import com.hyjf.cs.user.vo.SendSmsVO;
import com.hyjf.pay.lib.bank.bean.BankCallBean;
import com.hyjf.pay.lib.bank.util.BankCallConstant;
import com.hyjf.pay.lib.bank.util.BankCallUtils;
import com.hyjf.soa.apiweb.CommonSoaUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.StringUtil;
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
public class PassWordServiceImpl extends BaseUserServiceImpl implements PassWordService {


    private static Logger logger = LoggerFactory.getLogger(PassWordServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;

    @Autowired
    private AmConfigClient amConfigClient;

    @Autowired
    private AmDataCollectClient amDataCollectClient;

    @Autowired
    private CommonProducer commonProducer;

    @Autowired
    private SystemConfig systemConfig;

    /**
     * 设置密码失败页面
     */
    public static final String PASSWORD_ERROR_PATH = "/bank/user/personalsetting/error";

    /**
     * 修改用户登录密码
     *
     * @param
     * @param newPW
     * @return
     */
    @Override
    public boolean updatePassWd(UserVO user, String newPW) {
        logger.info("UserController.updatePassWd run.. newPW is :{}", newPW);
        user.setPassword(MD5Utils.MD5(MD5Utils.MD5(newPW) + user.getSalt()));
        boolean success = amUserClient.updateUserById(user) > 0;
        CheckUtil.check(success, MsgEnum.ERR_PASSWORD_MODIFY);
        return success;
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
        CheckUtil.check(accountFlag == 1, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        /*int count = amUserClient.countUserCardValid(String.valueOf(user.getUserId()));
        CheckUtil.check(count > 0, MsgEnum.ERR_CARD_NOT_BIND);*/
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        CheckUtil.check(passwordFlag != 1, MsgEnum.STATUS_TP000001);
        int userId = user.getUserId();
        // 取得用户详细信息
        UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        CheckUtil.check(bankAccount != null, MsgEnum.ERR_BANK_ACCOUNT_NOT_OPEN);
        // 调用设置密码接口
        String txcode = "";
        BankCallBean bean = new BankCallBean(userId, txcode, ClientConstants.WEB_CLIENT);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        bean.setSeqNo(GetOrderIdUtils.getSeqNo(6));
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        if (user.getUserType() == 1) {
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = getCorpOpenAccountRecord(userId);
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        } else {
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
            bean.setName(userInfoVO.getTruename());
        }
        // 电子账号
        bean.setAccountId(bankAccount.getAccount());
        bean.setMobile(user.getMobile());
        logger.info("交易密码回调参数:" + bean.getLogOrderId());
        //channel=0：设置交易密码/1：重置交易密码
        String retUrl = super.getFrontHost(systemConfig, String.valueOf(ClientConstants.WEB_CLIENT)) + "/user/setPasswordResult" + "?channel=0&logOrdId=" + bean.getLogOrderId();
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-web/user/password/passwordBgreturn";
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        bean.setLogRemark("设置交易密码");
        Map<String, Object> map = new HashMap<>();
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
        UserInfoVO userInfoVO = amUserClient.findUserInfoById(userId);
        BankOpenAccountVO bankAccount = amUserClient.selectById(userId);
        // 调用设置密码接口
        String txcode = "";
        BankCallBean bean = new BankCallBean(userId, txcode, ClientConstants.WEB_CLIENT);
        bean.setTxDate(GetOrderIdUtils.getTxDate());
        bean.setTxTime(GetOrderIdUtils.getTxTime());
        // 消息类型(密码重置)
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        if (user.getUserType() == 1) {
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = getCorpOpenAccountRecord(userId);
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        } else {
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(userInfoVO.getIdcard());
            bean.setName(userInfoVO.getTruename());
        }
        // 电子账号
        bean.setAccountId(bankAccount.getAccount());
        bean.setMobile(user.getMobile());
        //channel=0：设置交易密码/1：重置交易密码
        String retUrl = super.getFrontHost(systemConfig, String.valueOf(ClientConstants.WEB_CLIENT)) + "/user/setPasswordResult" + "?channel=1&logOrdId=" + bean.getLogOrderId();
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-web/user/password/resetPasswordBgreturn";
        bean.setRetUrl(retUrl);
        bean.setNotifyUrl(bgRetUrl);
        // 操作者ID
        bean.setLogUserId(String.valueOf(userId));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        Map<String, Object> map = new HashMap<>();
        try {
            map = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new ReturnMessageException(MsgEnum.ERR_BANK_CALL);
        }
        return map;
    }

    @Override
    public void checkParam(UserVO userVO, String oldPW, String newPW, String pwSure) {
        // 验证用的password
        oldPW = MD5Utils.MD5(MD5Utils.MD5(oldPW) + userVO.getSalt());
        CheckUtil.check(oldPW.equals(userVO.getPassword()), MsgEnum.ERR_PASSWORD_OLD_INCORRECT);
        CheckUtil.check(!oldPW.equals(newPW), MsgEnum.ERR_PASSWORD_TWO_SAME_PASSWORD);
        checkPassword(newPW);
    }

    @Override
    public JSONObject weChatCheckParam(UserVO userVO, String newPassword, String oldPassword) {
        JSONObject ret = new JSONObject();
        checkPassword(newPassword);
        // 检查参数正确性
        if (Validator.isNull(userVO) || Validator.isNull(newPassword) || Validator.isNull(oldPassword)) {
            ret.put("status", "997");
            ret.put("statusDesc", "请求参数非法");
            return ret;
        }

        try {
            // 验证旧密码
            oldPassword = MD5Utils.MD5(MD5Utils.MD5(oldPassword) + userVO.getSalt());
            if (StringUtils.isBlank(oldPassword) || !oldPassword.equals(userVO.getPassword())) {
                ret.put("status", "99");
                ret.put("statusDesc", "旧密码不正确");
                return ret;
            }
        } catch (Exception e) {
            logger.error("校验用户密码失败...");
            ret.put("status", "99");
            ret.put("statusDesc", e.getMessage());
        }
        return ret;
    }

    @Override
    public void checkPassword(String password) {
        CheckUtil.check(password.length() >= 8 || password.length() <= 16, MsgEnum.ERR_PASSWORD_LENGTH);
        boolean hasNumber = false;
        for (int i = 0; i < password.length(); i++) {
            if (Validator.isNumber(password.substring(i, i + 1))) {
                hasNumber = true;
                break;
            }
        }
//        CheckUtil.check(hasNumber,MsgEnum.ERR_PASSWORD_NO_NUMBER);
        String regEx = "^(?![0-9]+$)(?![a-zA-Z]+$)(?![\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]+$)[0-9A-Za-z\\`\\~\\!\\@\\#\\$\\%\\^\\&\\*\\(\\)\\_\\+\\-\\=\\{\\}\\|\\[\\]\\\\\\;\\'\\:\\\"\\,\\.\\/\\<\\>\\?]{8,16}$";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        CheckUtil.check(m.matches(), MsgEnum.ERR_FMT_PASSWORD);
    }

    @Override
    public int updatePassword(UserVO userVO, String newPW) {
        String codeSalt = userVO.getSalt();
        newPW = MD5Utils.MD5(MD5Utils.MD5(newPW) + codeSalt);
        userVO.setPassword(newPW);
        int cnt = amUserClient.updateUserById(userVO);
        return cnt;
    }

    @Override
    public boolean existPhone(String mobile) {
        int size = amUserClient.countByMobile(mobile);
        if (size > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean validPassword(Integer userId, String password) {
        String codeSalt = "";
        String passwordDb = "";
        UserVO user = this.getUsersById(userId);
        codeSalt = user.getSalt();
        passwordDb = user.getPassword();
        // 验证用的password
        password = MD5Utils.MD5(MD5Utils.MD5(password) + codeSalt);
        // 密码正确时
        if (password.equals(passwordDb)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void appCheckParam(String key, UserVO userVO, String version, String netStatus, String platform, String sign, String token, String randomString, String order, String newPassword, String oldPassword) {
        // 检查参数正确性
        CheckUtil.check(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(netStatus) && StringUtils.isNotBlank(platform) && StringUtils.isNotBlank(randomString) && StringUtils.isNotBlank(order), MsgEnum.STATUS_CE000001);

        // 解密
        oldPassword = DES.decodeValue(key, oldPassword);
        CheckUtil.check(StringUtils.isNotBlank(oldPassword), MsgEnum.ERR_OBJECT_REQUIRED, "旧密码");
        oldPassword = MD5Utils.MD5(MD5Utils.MD5(oldPassword) + userVO.getSalt());
        // 密码正确时
        CheckUtil.check(StringUtils.isNotBlank(oldPassword) && oldPassword.equals(userVO.getPassword()), MsgEnum.ERR_PASSWORD_OLD_INCORRECT);
        // 解密
        newPassword = DES.decodeValue(key, newPassword);
        CheckUtil.check(StringUtils.isNotBlank(newPassword), MsgEnum.ERR_PASSWORD_NEW_REQUIRED);
        checkPassword(newPassword);
    }

    /**
     * 微信修改密码发送短信验证码
     *
     * @param sendSmsVO
     */
    @Override
    public JSONObject sendCode(SendSmsVO sendSmsVO) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "000");
        jsonObject.put("statusDesc", "短信发送成功");
        try {
            //获取系统参数
            SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
            //发送前校验
            this.validateData(sendSmsVO, smsConfig);
            //发送短信并将发送数据存到数据库和Redis
            this.sendSms(sendSmsVO, smsConfig);
        } catch (CheckException e) {
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", e.getMessage());
        } catch (Exception e) {
            logger.error("发送短信异常", e);
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", "失败");
        }
        return jsonObject;
    }

    @Override
    public void backCheck(SendSmsVO sendSmsVo) {
        if (StringUtils.isBlank(sendSmsVo.getMobile())) {
            throw new CheckException("99", "手机号不能为空");
        }
        if (StringUtils.isBlank(sendSmsVo.getSmscode())) {
            throw new CheckException("99", "验证码不能为空");
        }
        // 检查参数正确性
        int cnt = amUserClient.checkMobileCode(sendSmsVo.getMobile(), sendSmsVo.getSmscode(), CustomConstants.PARAM_TPL_ZHAOHUIMIMA, CustomConstants.CLIENT_WECHAT, CommonConstant.CKCODE_YIYAN, CommonConstant.CKCODE_USED, true);
        if (cnt <= 0) {
            throw new CheckException("99", "验证码不正确");
        }
    }

    @Override
    public BankCallBean apiSetPassword(ThirdPartyTransPasswordRequestBean transPasswordRequestBean, String bankDetailUrl, String txCode, UserVO user, BankOpenAccountVO bankOpenAccount) {
        //拼装参数
        UserInfoVO usersInfo = getUserInfo(user.getUserId());
        // 同步调用路径
        String retUrl = systemConfig.getServerHost() + "/hyjf-api/server/user/transpassword";
        String successUrl = systemConfig.getServerHost() + "/hyjf-api/server/user/transpassword";
        // 异步调用路
        String bgRetUrl = "http://CS-USER/hyjf-api/server/user/transpassword";
        // 调用设置密码接口
        if (txCode.equals(BankCallConstant.TXCODE_PASSWORD_SET_PAGE)) {
            retUrl += "/passwordReturn?acqRes=" + transPasswordRequestBean.getAcqRes() + "&logUserId=" + user.getUserId() + "&callback=" + transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
            successUrl += "/passwordReturn?acqRes=" + transPasswordRequestBean.getAcqRes() + "&isSuccess=1&callback=" + transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
            bgRetUrl += "/passwordBgreturn?acqRes=" + transPasswordRequestBean.getAcqRes() + "&callback=" + transPasswordRequestBean.getBgRetUrl().replace("#", "*-*-*");
        } else {
            retUrl += "/resetPasswordReturn?acqRes=" + transPasswordRequestBean.getAcqRes() + "&logUserId=" + user.getUserId() + "&callback=" + transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
            successUrl += "/resetPasswordReturn?acqRes=" + transPasswordRequestBean.getAcqRes() + "&isSuccess=1&callback=" + transPasswordRequestBean.getRetUrl().replace("#", "*-*-*");
            bgRetUrl += "/resetPasswordBgreturn?acqRes=" + transPasswordRequestBean.getAcqRes() + "&callback=" + transPasswordRequestBean.getBgRetUrl().replace("#", "*-*-*");
        }
        logger.info(retUrl + "..." + bgRetUrl);
        BankCallBean bean = new BankCallBean();
        // 接口版本号
        bean.setVersion(BankCallConstant.VERSION_10);
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        // 机构代码
        bean.setInstCode(systemConfig.getBankInstcode());
        bean.setBankCode(systemConfig.getBankCode());
        bean.setChannel(transPasswordRequestBean.getChannel());
        bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setIdNo(usersInfo.getIdcard());
        bean.setName(usersInfo.getTruename());
        bean.setMobile(user.getMobile());
        // 页面同步返回 URL
        bean.setRetUrl(retUrl);
        bean.setSuccessfulUrl(successUrl);
        // 页面异步返回URL(必须)
        bean.setNotifyUrl(bgRetUrl);
        // 操作者ID
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(bankDetailUrl);
        bean.setLogOrderId(GetOrderIdUtils.getOrderId2(user.getUserId()));
        return bean;
    }

    /**
     * 发送短信
     *
     * @param
     */
    private void sendSms(SendSmsVO sendSmsVO, SmsConfigVO smsConfig) throws MQException {
        // 生成验证码
        String checkCode = GetCode.getRandomSMSCode(6);
        if (systemConfig.isHyjfEnvTest()) {
            // 测试环境验证码111111
            checkCode = "111111";
        }
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", checkCode);

        // 发送短信验证码
        SmsMessage smsMessage = new SmsMessage(null, param, sendSmsVO.getMobile(), null, MessageConstant.SMS_SEND_FOR_MOBILE, null, CustomConstants.PARAM_TPL_ZHAOHUIMIMA, CustomConstants.CHANNEL_TYPE_NORMAL);
        // 发送
        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
        // 累加手机次数
        String currentMaxPhoneCount = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT + sendSmsVO.getMobile());
        if (StringUtils.isBlank(currentMaxPhoneCount)) {
            currentMaxPhoneCount = "0";
        }
        RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT + sendSmsVO.getMobile(), (Integer.valueOf(currentMaxPhoneCount) + 1) + "", RedisUtils.getRemainMiao());
        // 保存短信验证码(由于验证没有用到，将其注释掉)
        amUserClient.saveSmsCode(sendSmsVO.getMobile(), checkCode, CustomConstants.PARAM_TPL_ZHAOHUIMIMA, CommonConstant.CKCODE_NEW, CustomConstants.CLIENT_WECHAT);
        // 发送checkCode最大时间间隔，默认60秒
        RedisUtils.set(sendSmsVO.getMobile() + ":" + CustomConstants.PARAM_TPL_ZHAOHUIMIMA + ":IntervalTime", sendSmsVO.getMobile(), smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
    }

    /**
     * 参数校验
     *
     * @param sendSmsVo
     */
    private void validateData(SendSmsVO sendSmsVo, SmsConfigVO smsConfig) {
        //手机号为空
        if (Validator.isNull(sendSmsVo.getMobile())) {
            throw new CheckException("2001", "请填写手机号");
        }
        //手机格式
        if (!Validator.isMobile(sendSmsVo.getMobile())) {
            throw new CheckException("2002", "手机号格式不对");
        }
        //该手机号用户是否存在
        if (!existUser(sendSmsVo.getMobile())) {
            throw new CheckException("2003", "您的手机号尚未注册");
        }
        // 判断发送间隔时间
        String key = sendSmsVo.getMobile() + ":" + CustomConstants.PARAM_TPL_ZHAOHUIMIMA + ":IntervalTime";
        String intervalTime = RedisUtils.get(key);
        if (StringUtils.isNotBlank(intervalTime)) {
            throw new CheckException("2005", "操作过于频繁,请稍后重试");
        }
        //判断该设备号的发送次数（暂时不做）
        // 判断最大发送数max_phone_count（当日该手机号发送的次数）
        String count = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT + sendSmsVo.getMobile());
        if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
            count = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT + sendSmsVo.getMobile(), "0");
        }
        if (Integer.valueOf(count) > smsConfig.getMaxPhoneCount()) {
            throw new CheckException("2006", "该手机号短信请求次数超限，请明日再试");
        }
    }

    @Override
    public UserVO checkStatus(String token, String sign) {
        if (token == null) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL, "用户未登录！");
        }
        //判断用户是否登录
        Integer userId = SecretUtil.getUserId(sign);
        if (userId == null || userId <= 0) {
            throw new CheckException(BaseResultBeanFrontEnd.FAIL, "用户未登录！");
        }
        //判断用户是否开户
        UserVO user = this.getUsersById(userId);
        if (user.getBankOpenAccount().intValue() != 1) {
            //未开户
            throw new CheckException(BaseResultBeanFrontEnd.FAIL, "用户未开户！");
        }
        return user;
    }

    @Override
    public JSONObject validateVerificationCoden(SendSmsVO sendSmsVo, boolean isUpdate) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("status", "000");
        jsonObject.put("statusDesc", "验证成功");
        try {
            if (StringUtils.isBlank(sendSmsVo.getMobile())) {
                throw new CheckException("2001", "手机号不能为空");
            }
            if (StringUtils.isBlank(sendSmsVo.getSmscode())) {
                throw new CheckException("2002", "验证码不能为空");
            }
            //验证
            amUserClient.checkMobileCode(sendSmsVo.getMobile(), sendSmsVo.getSmscode(), CustomConstants.PARAM_TPL_ZHAOHUIMIMA, sendSmsVo.getPlatform(), CommonConstant.CKCODE_NEW, CommonConstant.CKCODE_YIYAN, isUpdate);
        } catch (CheckException e) {
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", e.getMessage());
        } catch (Exception e) {
            logger.error("发送短信异常", e);
            jsonObject.put("status", "99");
            jsonObject.put("statusDesc", "短信验证失败");
        }
        return jsonObject;
    }

    @Override
    public Map<String, Object> setAppPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount) {
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if (user.getUserType() == 1) {
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = this.getCorpOpenAccountRecord(user.getUserId());
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        } else {
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setMobile(user.getMobile());
        // 操作者ID
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            logger.error("调用银行接口失败", e);
            throw new CheckException(BaseResultBeanFrontEnd.FAIL, "调用银行接口失败！");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> resetAppPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount) {
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if (user.getUserType() == 1) {
            CorpOpenAccountRecordVO record = this.getCorpOpenAccountRecord(user.getUserId());
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        } else {
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setMobile(user.getMobile());
        // 操作者ID
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            logger.error("调用银行接口失败!", e);
            throw new CheckException(BaseResultBeanFrontEnd.FAIL, "调用银行接口失败！");
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> setWeChatPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount) {
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setChannel(BankCallConstant.CHANNEL_WEI);
        if (user.getUserType() == 1) {
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = this.getCorpOpenAccountRecord(user.getUserId());
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        } else {
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        //电子账号
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setMobile(user.getMobile());
        // 操作者ID
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        // 跳转到汇付天下画面
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new CheckException(ResultEnum.USER_ERROR_208.getStatus(), ResultEnum.USER_ERROR_208.getStatusDesc());
        }
        return resultMap;
    }

    @Override
    public UserVO weChatCheck(Integer userId) {
        //判断用户是否登录
        if (StringUtil.isBlank(userId.toString())) {
            throw new CheckException(ResultEnum.ERROR_004.getStatus(), ResultEnum.ERROR_004.getStatusDesc());
        }
        //判断用户是否开户
        UserVO user = this.getUsersById(userId);
        if (user.getBankOpenAccount().intValue() != 1) {
            //未开户
            throw new CheckException(ResultEnum.USER_ERROR_200.getStatus(), ResultEnum.USER_ERROR_200.getStatusDesc());
        }
        return user;
    }

    @Override
    public Map<String, Object> resetWeChatPassword(BankCallBean bean, UserVO user, UserInfoVO usersInfo, BankOpenAccountVO bankOpenAccount) {
        // 消息类型
        bean.setTxCode(BankCallConstant.TXCODE_PASSWORD_RESET_PAGE);
        bean.setChannel(BankCallConstant.CHANNEL_APP);
        if (user.getUserType() == 1) {
            //企业用户 传组织机构代码
            CorpOpenAccountRecordVO record = this.getCorpOpenAccountRecord(user.getUserId());
            // 证件类型 20：其他证件（组织机构代码）25：社会信用号
            bean.setIdType(record.getCardType() != null ? String.valueOf(record.getCardType()) : BankCallConstant.ID_TYPE_COMCODE);
            bean.setIdNo(record.getBusiCode());
            bean.setName(record.getBusiName());
        } else {
            bean.setIdType(BankCallConstant.ID_TYPE_IDCARD);
            bean.setIdNo(usersInfo.getIdcard());
            bean.setName(usersInfo.getTruename());
        }
        //电子账号
        bean.setAccountId(String.valueOf(bankOpenAccount.getAccount()));
        bean.setMobile(user.getMobile());
        // 操作者ID
        bean.setLogUserId(String.valueOf(user.getUserId()));
        bean.setLogBankDetailUrl(BankCallConstant.BANK_URL_PASSWORDRESETPAGE);
        bean.setLogOrderDate(GetOrderIdUtils.getOrderDate());
        // 跳转到汇付天下画面
        Map<String, Object> resultMap = new HashMap<>();
        try {
            resultMap = BankCallUtils.callApiMap(bean);
        } catch (Exception e) {
            throw new CheckException(ResultEnum.USER_ERROR_208.getStatus(), ResultEnum.USER_ERROR_208.getStatusDesc());
        }
        return resultMap;
    }

    @Override
    public Map<String, Object> apiCheack(ThirdPartyTransPasswordRequestBean transPasswordRequestBean, String type, String verifyValue) {
        Map<String, Object> result = new HashMap<>();
        String account = transPasswordRequestBean.getAccountId();
        String retUrl = transPasswordRequestBean.getRetUrl();
        String bgRetUrl = transPasswordRequestBean.getBgRetUrl();
        String channel = transPasswordRequestBean.getChannel();
        String instCode = transPasswordRequestBean.getInstCode();
        String acqRes = transPasswordRequestBean.getAcqRes();
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(retUrl) || StringUtils.isEmpty(bgRetUrl) || StringUtils.isEmpty(channel) || StringUtils.isEmpty(instCode)) {
            if (StringUtils.isNotEmpty(bgRetUrl)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("statusDesc", "请求参数非法");
                params.put("acqRes", acqRes);
                BaseResultBean resultBean = new BaseResultBean();
                resultBean.setStatusForResponse(ErrorCodeConstant.STATUS_CE000001);
                params.put("status", resultBean.getStatus());
                params.put("chkValue", resultBean.getChkValue());
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            if (Validator.isNotNull(retUrl)) {
                // 设置交易密码
                result.put("statusDesc", "请求参数非法");
                result.put("status", ErrorCodeConstant.STATUS_CE000001);
                result.put("acqRes", acqRes);
                return result;
            }
            result.put("statusDesc", "请求参数非法");
            result.put("status", ErrorCodeConstant.STATUS_CE000001);
            result.put("acqRes", acqRes);
            return result;
        }
        //验签  暂时去掉验签
        if (!this.verifyRequestSign(transPasswordRequestBean, verifyValue)) {
            result.put("accountId", account);
            result.put("acqRes", acqRes);
            // 设置交易密码
            result.put("statusDesc", "验签失败！");
            result.put("status", ErrorCodeConstant.STATUS_CE000002);
            if (Validator.isNotNull(bgRetUrl)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", ErrorCodeConstant.STATUS_CE000002);
                params.put("statusDesc", "验签失败！");
                params.put("acqRes", acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            return result;
        }
        //根据账号找出用户ID
        BankOpenAccountVO bankOpenAccount = amUserClient.selectByAccountId(account);
        if (bankOpenAccount == null) {
            result.put("accountId", account);
            // 设置交易密码
            result.put("statusDesc", "没有根据电子银行卡找到用户 ");
            result.put("status", ErrorCodeConstant.STATUS_CE000004);
            result.put("acqRes", acqRes);
            if (Validator.isNotNull(bgRetUrl)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", ErrorCodeConstant.STATUS_CE000004);
                params.put("statusDesc", "没有根据电子银行卡找到用户 ");
                params.put("acqRes", acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            return result;
        }
        UserVO user = getUsersById(bankOpenAccount.getUserId());
        if (user == null) {
            result.put("accountId", account);
            // 设置交易密码
            result.put("statusDesc", "用户不存在汇盈金服账户！");
            result.put("status", ErrorCodeConstant.STATUS_CE000006);
            result.put("acqRes", acqRes);
            if (Validator.isNotNull(bgRetUrl)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", ErrorCodeConstant.STATUS_CE000006);
                params.put("statusDesc", "用户不存在汇盈金服账户！");
                params.put("acqRes", acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            return result;
        }
        //未开户
        if (user.getBankOpenAccount().intValue() != 1) {
            logger.info("-------------------用户未开户！--------------------");
            result.put("accountId", account);
            // 设置交易密码
            result.put("statusDesc", "用户未开户！");
            result.put("status", ErrorCodeConstant.STATUS_CE000007);
            result.put("acqRes", acqRes);
            if (Validator.isNotNull(bgRetUrl)) {
                Map<String, String> params = new HashMap<String, String>();
                params.put("accountId", account);
                params.put("status", ErrorCodeConstant.STATUS_CE000007);
                params.put("statusDesc", "用户未开户！");
                params.put("acqRes", acqRes);
                CommonSoaUtils.noRetPostThree(bgRetUrl, params);
            }
            return result;
        }
        // 判断用户是否设置过交易密码
        Integer passwordFlag = user.getIsSetPassword();
        if (type.equals(BankCallConstant.TXCODE_PASSWORD_SET_PAGE)) {
            if (passwordFlag == 1) {
                result.put("accountId", account);
                // 设置交易密码
                result.put("statusDesc", "已设置交易密码");
                result.put("status", ErrorCodeConstant.STATUS_TP000001);
                result.put("acqRes", acqRes);
                if (Validator.isNotNull(bgRetUrl)) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("accountId", account);
                    params.put("status", ErrorCodeConstant.STATUS_TP000001);
                    params.put("statusDesc", "已设置交易密码");
                    params.put("acqRes", acqRes);
                    CommonSoaUtils.noRetPostThree(bgRetUrl, params);
                }
                return result;
            }
        } else {
            if (passwordFlag == 0) {
                //未设置交易密码
                result.put("accountId", transPasswordRequestBean.getAccountId());
                // 设置交易密码
                result.put("statusDesc", "未设置过交易密码，请先设置交易密码");
                result.put("status", ErrorCodeConstant.STATUS_TP000002);
                result.put("acqRes", transPasswordRequestBean.getAcqRes());
                if (Validator.isNotNull(transPasswordRequestBean.getBgRetUrl())) {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("accountId", transPasswordRequestBean.getAccountId());
                    params.put("status", ErrorCodeConstant.STATUS_TP000002);
                    params.put("statusDesc", "未设置过交易密码，请先设置交易密码");
                    params.put("acqRes", transPasswordRequestBean.getAcqRes());
                    CommonSoaUtils.noRetPostThree(transPasswordRequestBean.getBgRetUrl(), params);
                }
                return result;
            }
        }
        result.put("user", user);
        result.put("bankOpenAccount", bankOpenAccount);
        result.put("flag", "1");
        return result;
    }
}
