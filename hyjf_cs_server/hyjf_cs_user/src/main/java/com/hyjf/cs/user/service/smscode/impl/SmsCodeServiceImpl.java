package com.hyjf.cs.user.service.smscode.impl;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.config.SystemConfig;
import com.hyjf.cs.user.mq.base.CommonProducer;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.service.impl.BaseUserServiceImpl;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author xiasq
 * @version SmsCodeServiceImpl, v0.1 2018/4/25 9:02
 */
@Service
public class SmsCodeServiceImpl extends BaseUserServiceImpl implements SmsCodeService {
    private static final Logger logger = LoggerFactory.getLogger(SmsCodeServiceImpl.class);

    @Autowired
    private AmUserClient amUserClient;
    @Autowired
    private CommonProducer commonProducer;
    @Autowired
    private AmConfigClient amConfigClient;
    @Autowired
    SystemConfig systemConfig;

    /**
     * 参数检查
     *
     * @param validCodeType
     * @param mobile
     * @param ip
     */
    @Override
    public void sendSmsCodeCheckParam(String validCodeType, String mobile, Integer userId, String ip) {

        List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
                CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_DUANXINDENGLU);
        //无效的验证码类型
        CheckUtil.check(Validator.isNotNull(validCodeType) && codeTypes.contains(validCodeType), MsgEnum.ERR_OBJECT_INVALID, "验证码类型");
        CheckUtil.check(Validator.isNotNull(mobile) && Validator.isMobile(mobile), MsgEnum.ERR_FMT_MOBILE);
        if (validCodeType.equals(CommonConstant.PARAM_TPL_ZHUCE)) {
            // 注册时要判断不能重复
            CheckUtil.check(!existUser(mobile), MsgEnum.ERR_USER_NOT_EXISTS);
        }
        if(CommonConstant.PARAM_TPL_DUANXINDENGLU.equals(validCodeType)){
            CheckUtil.check(existUser(mobile), MsgEnum.ERR_USER_INFO_GET);
        }
        if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH) || validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
			if (userId != null) {
				WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisConstants.USERID_KEY + userId,
						WebViewUserVO.class);
				CheckUtil.check(webViewUserVO != null, MsgEnum.ERR_USER_NOT_EXISTS);
				// 验证原手机号校验
				if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH)) {
					CheckUtil.check(StringUtils.isNotBlank(webViewUserVO.getMobile()), MsgEnum.ERR_USER_NOT_EXISTS);
					CheckUtil.check(webViewUserVO.getMobile().equals(mobile), MsgEnum.ERR_MOBILE_NEED_SAME);
				}
				// 绑定新手机号校验
				if (validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
					CheckUtil.check(!webViewUserVO.getMobile().equals(mobile), MsgEnum.ERR_MOBILE_NEED_DIFFERENT);
					CheckUtil.check(!existUser(mobile), MsgEnum.ERR_MOBILE_EXISTS);
				}
			}
        }

        // 判断发送间隔时间
        String intervalTime = RedisUtils.get(mobile + ":" + validCodeType + ":IntervalTime");

        logger.info("mobile is: {}, validCodeType is: {}, IntervalTime is: {}", mobile, validCodeType, intervalTime);
        CheckUtil.check(StringUtils.isBlank(intervalTime), MsgEnum.ERR_SMSCODE_SEND_TOO_FAST);
		String ipCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT + ip);
        if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
            ipCount = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ip, "0", 24 * 60 * 60);
        }
        logger.info(mobile + "------ip---" + ip + "----------MaxIpCount-----------" + ipCount);
        SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
        CheckUtil.check(smsConfig != null, MsgEnum.ERR_OBJECT_GET, "短信配置");//获取短信配置失败
        if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
            CheckUtil.check(Integer.valueOf(ipCount).equals(smsConfig.getMaxIpCount()), MsgEnum.ERR_SMSCODE_SEND_TOO_MANNY);
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "IP访问次数超限");
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
            } catch (Exception e) {
                CheckUtil.check(false, MsgEnum.ERR_IP_VISIT_TOO_MANNY);
            }
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT + ip, (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
        }
        // 判断最大发送数max_phone_count
        String count = RedisUtils.get( RedisConstants.CACHE_MAX_PHONE_COUNT+mobile);
        if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
            count = "0";
            RedisUtils.set( RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, "0",24 * 60 * 60);
        }
        logger.info(mobile + "----------MaxPhoneCount-----------" + count);
        if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
            CheckUtil.check(Integer.valueOf(count)<(smsConfig.getMaxPhoneCount()), MsgEnum.ERR_SMSCODE_SEND_TOO_MANNY);
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "手机验证码发送次数超限");
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
            } catch (Exception e) {
                CheckUtil.check(false, MsgEnum.ERR_SMSCODE_SEND_TOO_MANNY);
            }
        }
        RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);

        // 发送checkCode最大时间间隔，默认60秒
        RedisUtils.set(mobile + ":" + validCodeType + ":IntervalTime", mobile,
                smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
    }

    /**
     * 1. 验证码发送前校验 2. 生成验证码 3. 保存验证码 4. 发送短信
     *
     * @param validCodeType
     * @param mobile
     * @param ip
     * @throws Exception
     */
    @Override
    public void sendSmsCode(String validCodeType, String mobile,String platform, String ip) throws Exception {
        // 生成验证码
        String checkCode = GetCode.getRandomSMSCode(6);

        if(systemConfig.isHyjfEnvTest()){
            // 测试环境验证码111111
            checkCode = "111111";
        }
        logger.info("手机号: {}, 短信验证码: {}", mobile, checkCode);
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", checkCode);

        // 保存短信验证码
        amUserClient.saveSmsCode(mobile, checkCode, validCodeType, CommonConstant.CKCODE_NEW, platform);

        SmsMessage smsMessage = new SmsMessage(null, param, mobile, null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
                validCodeType, CustomConstants.CHANNEL_TYPE_NORMAL);

        // 发送
        commonProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
        // 累加IP次数
        String currentMaxIpCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT+ip);
        if (StringUtils.isBlank(currentMaxIpCount)) {
            currentMaxIpCount = "0";
        }
        // 累加手机次数
        String currentMaxPhoneCount = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile);
        if (StringUtils.isBlank(currentMaxPhoneCount)) {
            currentMaxPhoneCount = "0";
        }
        RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ip, (Integer.valueOf(currentMaxIpCount) + 1) + "", 24 * 60 * 60);
        RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, (Integer.valueOf(currentMaxPhoneCount) + 1) + "", 24 * 60 * 60);
    }

    /**
     * 参数检查
     *
     * @param validCodeType
     * @param mobile
     * @param ip
     */
    @Override
    public JSONObject appSendSmsCodeCheckParam(String validCodeType, String mobile, Integer userId, String ip) {
        JSONObject ret = new JSONObject();
        List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
                CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_BDYSJH, CommonConstant.PARAM_TPL_DUANXINDENGLU);
        //无效的验证码类型
        if(Validator.isNull(validCodeType) || !codeTypes.contains(validCodeType)){
            ret.put("status", "1");
            ret.put("statusDesc", "验证码类型错误");
            return ret;
        }
        if(Validator.isNull(mobile) || !Validator.isMobile(mobile)){
            ret.put("status", "1");
            ret.put("statusDesc", "请输入您的真实手机号码");
            return ret;
        }
        if (validCodeType.equals(CommonConstant.PARAM_TPL_ZHUCE)) {
            // 注册时要判断不能重复
            if(existUser(mobile)){
                ret.put("status", "1");
                ret.put("statusDesc", "该手机号已经注册");
                return ret;
            }
        }
        if (validCodeType.equals(CommonConstant.PARAM_TPL_DUANXINDENGLU)) {
            //
            if(!existUser(mobile)){
                ret.put("status", "1");
                ret.put("statusDesc", "该手机号未注册");
                return ret;
            }
        }

        if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH) || validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
            if (userId != null) {
                WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisConstants.USERID_KEY + userId, WebViewUserVO.class);
                if (webViewUserVO == null){
                    ret.put("status", "1");
                    ret.put("statusDesc", "用户不存在");
                    return ret;
                }
                // 验证原手机号校验
                if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH)) {
                    if (StringUtils.isBlank(webViewUserVO.getMobile())){
                        ret.put("status", "1");
                        ret.put("statusDesc", "不存在用户");
                        return ret;
                    }
                    if (!webViewUserVO.getMobile().equals(mobile)){
                        ret.put("status", "1");
                        ret.put("statusDesc", "获取验证码手机号与注册手机号不一致");
                        return ret;
                    }
                }
                // 绑定新手机号校验
                if (validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
                    if (webViewUserVO.getMobile().equals(mobile)){
                        ret.put("status", "1");
                        ret.put("statusDesc", "获取验证码手机号与注册手机号不一致");
                        return ret;
                    }
                    if (existUser(mobile)){
                        ret.put("status", "1");
                        ret.put("statusDesc", "该手机号已经注册");
                        return ret;
                    }
                }
            }
        }

        // 判断发送间隔时间
        String intervalTime = RedisUtils.get(mobile + ":" + validCodeType + ":IntervalTime");
        logger.info(mobile + ":" + validCodeType + "----------IntervalTime-----------" + intervalTime);
        if (StringUtils.isNotBlank(intervalTime)){
            ret.put("status", "1");
            ret.put("statusDesc", "请求验证码操作过快");
            return ret;
        }
        String ipCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT+ip);
        if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
            ipCount = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ip, "0", 24 * 60 * 60);
        }
        logger.info(mobile + "------ip---" + ip + "----------MaxIpCount-----------" + ipCount);
        SmsConfigVO smsConfig = amConfigClient.findSmsConfig();
        if (smsConfig == null){
            ret.put("status", "1");
            ret.put("statusDesc", "获取短信配置失败");
            return ret;
        }
        if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
            if (!Integer.valueOf(ipCount).equals(smsConfig.getMaxIpCount())){
                ret.put("status", "1");
                ret.put("statusDesc", "该设备短信请求次数超限，请明日再试");
                return ret;
            }
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "IP访问次数超限");
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
/*                String[] toMailArray = new String[1];
                SiteSettingsVO siteSettingsVO = amConfigClient.selectSiteSetting();
                if(siteSettingsVO == null){
                    logger.error("邮件配置无效");
                    throw new Exception("邮件配置无效");
                }
                toMailArray[0] = siteSettingsVO.getSmtpReply();
                MailMessage mailMessage = new MailMessage(null, replaceStrs, "IP访问次数超限" + ip, null, null, toMailArray,
                        CustomConstants.EMAILPARAM_TPL_DUANXINCHAOXIAN, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
                // 发送邮件
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), mailMessage));*/
            } catch (Exception e) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "IP访问次数超限");
                    return ret;
            }
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT + ip, (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
        }
        // 判断最大发送数max_phone_count
        String count = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile);
        if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
            count = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, "0",24 * 60 * 60);
        }
        logger.info(mobile + "----------MaxPhoneCount-----------" + count);
        if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
            if (!Integer.valueOf(count).equals(smsConfig.getMaxPhoneCount())){
                ret.put("status", "1");
                ret.put("statusDesc", "该设备短信请求次数超限，请明日再试");
                return ret;
            }
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "手机验证码发送次数超限");
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
            } catch (Exception e) {
                    ret.put("status", "1");
                    ret.put("statusDesc", "该设备短信请求次数超限，请明日再试");
                    return ret;
            }
            RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);
        }

        // 发送checkCode最大时间间隔，默认60秒
        RedisUtils.set(mobile + ":" + validCodeType + ":IntervalTime", mobile,
        smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
        return ret;
    }

    @Override
    public boolean existUser(String mobile) {
        UserVO userVO = amUserClient.findUserByMobile(mobile);
        logger.info("手机号为："+mobile+"；user信息："+(userVO==null));
        return userVO == null ? false : true;
    }


    @Override
    public void checkParam(String verificationType, String code, String mobile) {
        CheckUtil.check(StringUtils.isNotBlank(verificationType), MsgEnum.STATUS_CE000001);
        List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
                CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_BDYSJH);
        //无效的验证码类型
        CheckUtil.check(Validator.isNotNull(verificationType) && codeTypes.contains(verificationType), MsgEnum.ERR_OBJECT_INVALID, "验证码类型");
        CheckUtil.check(StringUtils.isNotBlank(mobile),MsgEnum.STATUS_CE000001);
        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.ERR_FMT_MOBILE);
        CheckUtil.check(StringUtils.isNotBlank(code), MsgEnum.ERR_SMSCODE_BLANK);
    }

    @Override
    public JSONObject wechatCheckParam(String verificationType, String mobile, String ip, JSONObject ret, SmsConfigVO smsConfig) {
        // 解密
        if (Validator.isNull(verificationType)) {
            ret.put("status", "99");
            ret.put("statusDesc", "验证码类型不能为空");
            return ret;
        }
        if (Validator.isNull(mobile)) {
            ret.put("status", "99");
            ret.put("statusDesc", "手机号不能为空");
            return ret;
        }
        if (!Validator.isMobile(mobile)) {
            ret.put("status", "99");
            ret.put("statusDesc", "请输入您的真实手机号码");
            return ret;
        }

        if (!(verificationType.equals(CommonConstant.PARAM_TPL_ZHUCE) || verificationType.equals(CommonConstant.PARAM_TPL_ZHAOHUIMIMA) || verificationType.equals(CommonConstant.PARAM_TPL_BDYSJH) || verificationType.equals(CommonConstant.PARAM_TPL_YZYSJH))) {
            ret.put("status", "99");
            ret.put("statusDesc", "无效的验证码类型");
            return ret;
        }
        {
            if (verificationType.equals(CommonConstant.PARAM_TPL_ZHUCE)) {
                if (existUser(mobile)) {
                    ret.put("status", "99");
                    ret.put("statusDesc", "该手机号已经注册");
                    return ret;
                }
            } else if (verificationType.equals(CommonConstant.PARAM_TPL_ZHAOHUIMIMA) || verificationType.equals(CommonConstant.PARAM_TPL_YZYSJH) || verificationType.equals(CommonConstant.PARAM_TPL_DUANXINDENGLU)) {
                if (!existUser(mobile)) {
                    ret.put("status", "99");
                    ret.put("statusDesc", "该手机号尚未注册");
                    return ret;
                }

            } else if (verificationType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
                if (existUser(mobile)) {
                    ret.put("status", "99");
                    ret.put("statusDesc", "手机号已存在，请重新填写");
                    return ret;
                }
            }
        }
        String ipCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT+ip);
        if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
            ipCount = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ip, "0", 24 * 60 * 60);
        }
        logger.info(mobile + "------ip---" + ip + "----------MaxIpCount-----------" + ipCount);
        if (smsConfig == null){
            ret.put("status", "1");
            ret.put("statusDesc", "获取短信配置失败");
            return ret;
        }
        if (Integer.valueOf(ipCount) >= smsConfig.getMaxIpCount()) {
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "IP访问次数超限" + ip);
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
/*                String[] toMailArray = new String[1];
                SiteSettingsVO siteSettingsVO = amConfigClient.selectSiteSetting();
                if(siteSettingsVO == null){
                    throw new Exception("邮件配置无效");
                }
                toMailArray[0] = siteSettingsVO.getSmtpReply();
                MailMessage mailMessage = new MailMessage(null, replaceStrs, "IP访问次数超限" + ip, null, null, toMailArray,
                        CustomConstants.EMAILPARAM_TPL_DUANXINCHAOXIAN, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
                // 发送邮件
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), mailMessage));*/
            } catch (Exception e) {
                ret.put("status", "1");
                ret.put("statusDesc", "IP访问次数超限");
                return ret;
            }
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT + ip, (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
            if (!Integer.valueOf(ipCount).equals(smsConfig.getMaxIpCount())){
                ret.put("status", "1");
                ret.put("statusDesc", "该设备短信请求次数超限，请明日再试");
                return ret;
            }
        }
        // 判断最大发送数max_phone_count
        String count = RedisUtils.get(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile);
        if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
            count = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, "0",24 * 60 * 60);
        }
        logger.info(mobile + "----------MaxPhoneCount-----------" + count);
        if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "手机验证码发送次数超限");
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    commonProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), smsMessage));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
/*                String[] toMailArray = new String[1];
                SiteSettingsVO siteSettingsVO = amConfigClient.selectSiteSetting();
                if(siteSettingsVO == null){
                    throw new Exception("邮件配置无效");
                }
                toMailArray[0] = siteSettingsVO.getSmtpReply();
                MailMessage mailMessage = new MailMessage(null, replaceStrs, "手机验证码发送次数超限" + mobile, null, null, toMailArray,
                        CustomConstants.EMAILPARAM_TPL_DUANXINCHAOXIAN, MessageConstant.MAIL_SEND_FOR_MAILING_ADDRESS);
                // 发送邮件
                commonProducer.messageSend(new MessageContent(MQConstant.MAIL_TOPIC, UUID.randomUUID().toString(), mailMessage));*/
            } catch (Exception e) {
                ret.put("status", "1");
                ret.put("statusDesc", "该设备短信请求次数超限，请明日再试");
                return ret;
            }
            RedisUtils.set(RedisConstants.CACHE_MAX_PHONE_COUNT+mobile, (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);
            if (!Integer.valueOf(ipCount).equals(smsConfig.getMaxIpCount())){
                ret.put("status", "1");
                ret.put("statusDesc", "手机发送次数超限");
                return ret;
            }
        }

        // 判断发送间隔时间
        String intervalTime = RedisUtils.get("IntervalTime:" + verificationType + ":"+mobile);
        if (StringUtils.isNotBlank(intervalTime)) {
            ret.put("status", "1");
            ret.put("statusDesc", "验证码发送过于频繁");
            return ret;
        }
        // 发送checkCode最大时间间隔，默认60秒
        RedisUtils.set( "IntervalTime:" + verificationType + ":"+mobile, mobile,
                smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
        return ret;
    }
}
