package com.hyjf.cs.user.service.smscode.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.hyjf.am.vo.config.SmsConfigVO;
import com.hyjf.am.vo.message.SmsMessage;
import com.hyjf.am.vo.user.UserVO;
import com.hyjf.am.vo.user.WebViewUserVO;
import com.hyjf.common.cache.RedisConstants;
import com.hyjf.common.cache.RedisUtils;
import com.hyjf.common.constants.CommonConstant;
import com.hyjf.common.constants.MQConstant;
import com.hyjf.common.constants.MessageConstant;
import com.hyjf.common.constants.RedisKey;
import com.hyjf.common.enums.MsgEnum;
import com.hyjf.common.exception.MQException;
import com.hyjf.common.util.CustomConstants;
import com.hyjf.common.util.DES;
import com.hyjf.common.util.GetCode;
import com.hyjf.common.validator.CheckUtil;
import com.hyjf.common.validator.Validator;
import com.hyjf.cs.user.client.AmConfigClient;
import com.hyjf.cs.user.client.AmUserClient;
import com.hyjf.cs.user.mq.base.MessageContent;
import com.hyjf.cs.user.mq.producer.SmsProducer;
import com.hyjf.cs.user.service.BaseUserServiceImpl;
import com.hyjf.cs.user.service.smscode.SmsCodeService;
import com.hyjf.cs.user.vo.SmsRequest;

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
    private SmsProducer smsProducer;
    @Autowired
    private AmConfigClient amConfigClient;


    /**
     * 1. 验证码发送前校验 2. 生成验证码 3. 保存验证码 4. 发送短信
     *
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     * @throws MQException
     */
    @Override
    public void sendSmsCode(String validCodeType, String mobile,String platform, String token, String ip) throws MQException {

        this.sendSmsCodeCheckParam(validCodeType, mobile, token, ip);

        // 生成验证码
        String checkCode = GetCode.getRandomSMSCode(6);
        logger.info("手机号: {}, 短信验证码: {}", mobile, checkCode);
        Map<String, String> param = new HashMap<String, String>();
        param.put("val_code", checkCode);

        // 保存短信验证码
        amUserClient.saveSmsCode(mobile, checkCode, validCodeType, CommonConstant.CKCODE_NEW, platform);

        SmsMessage smsMessage = new SmsMessage(null, param, mobile, null, MessageConstant.SMS_SEND_FOR_MOBILE, null,
                validCodeType, CustomConstants.CHANNEL_TYPE_NORMAL);

        // 发送
        smsProducer.messageSend(new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
    }

    @Override
    public void appCheckParam(SmsRequest request, String verificationType, String verificationCode, String mobile, String key) {
        // 版本号
        String version = request.getVersion();
        // 网络状态
        String netStatus = request.getNetStatus();
        // 平台
        String platform = request.getPlatform();
        // 随机字符串
        String randomString = request.getRandomString();
        // Order
        String order = request.getOrder();
        CheckUtil.check(StringUtils.isNotBlank(version) && StringUtils.isNotBlank(netStatus) && StringUtils.isNotBlank(platform) && StringUtils.isNotBlank(randomString) && StringUtils.isNotBlank(order), MsgEnum.STATUS_CE000001);
        CheckUtil.check(StringUtils.isNotBlank(verificationType), MsgEnum.ERR_OBJECT_REQUIRED, "验证码类型");
        CheckUtil.check(StringUtils.isNotBlank(verificationCode), MsgEnum.ERR_OBJECT_REQUIRED, "验证码");
        List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
                CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_BDYSJH);
        //无效的验证码类型
        CheckUtil.check(Validator.isNotNull(verificationType) && codeTypes.contains(verificationType), MsgEnum.ERR_OBJECT_INVALID, "验证码类型");
        CheckUtil.check(null != key, MsgEnum.STATUS_CE000001);

        // 业务逻辑
        // 解密
        mobile = DES.decodeValue(key, mobile);
        CheckUtil.check(StringUtils.isNotBlank(mobile), MsgEnum.ERR_OBJECT_REQUIRED,"手机号");
        CheckUtil.check(Validator.isMobile(mobile), MsgEnum.ERR_FMT_MOBILE);
    }

    /**
     * 参数检查
     *
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     */
    private void sendSmsCodeCheckParam(String validCodeType, String mobile, String token, String ip) {

        List<String> codeTypes = Arrays.asList(CommonConstant.PARAM_TPL_ZHUCE, CommonConstant.PARAM_TPL_ZHAOHUIMIMA,
                CommonConstant.PARAM_TPL_YZYSJH, CommonConstant.PARAM_TPL_BDYSJH);
        //无效的验证码类型
        CheckUtil.check(Validator.isNotNull(validCodeType) && codeTypes.contains(validCodeType), MsgEnum.ERR_OBJECT_INVALID, "验证码类型");
        CheckUtil.check(Validator.isNotNull(mobile) && Validator.isMobile(mobile), MsgEnum.ERR_FMT_MOBILE);
        if (validCodeType.equals(CommonConstant.PARAM_TPL_ZHUCE)) {
            // 注册时要判断不能重复
            CheckUtil.check(!existUser(mobile), MsgEnum.ERR_MOBILE_EXISTS);
        }
        if (validCodeType.equals(CommonConstant.PARAM_TPL_YZYSJH) || validCodeType.equals(CommonConstant.PARAM_TPL_BDYSJH)) {
            if (StringUtils.isNotEmpty(token)) {
                WebViewUserVO webViewUserVO = RedisUtils.getObj(RedisKey.USER_TOKEN_REDIS + token, WebViewUserVO.class);
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
        logger.info(mobile + ":" + validCodeType + "----------IntervalTime-----------" + intervalTime);
        CheckUtil.check(StringUtils.isBlank(intervalTime), MsgEnum.ERR_SMSCODE_SEND_TOO_FAST);
        String ipCount = RedisUtils.get(RedisConstants.CACHE_MAX_IP_COUNT+ip);
        if (StringUtils.isBlank(ipCount) || !Validator.isNumber(ipCount)) {
            ipCount = "0";
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT+ip, "0");
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
                    smsProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
            } catch (Exception e) {
                CheckUtil.check(false, MsgEnum.ERR_IP_VISIT_TOO_MANNY);
            }
            RedisUtils.set(RedisConstants.CACHE_MAX_IP_COUNT + ip, (Integer.valueOf(ipCount) + 1) + "", 24 * 60 * 60);
        }
        // 判断最大发送数max_phone_count
        String count = RedisUtils.get(mobile + RedisConstants.CACHE_MAX_PHONE_COUNT);
        if (StringUtils.isBlank(count) || !Validator.isNumber(count)) {
            count = "0";
            RedisUtils.set(mobile + RedisConstants.CACHE_MAX_PHONE_COUNT, "0");
        }
        logger.info(mobile + "----------MaxPhoneCount-----------" + count);
        if (Integer.valueOf(count) >= smsConfig.getMaxPhoneCount()) {
            CheckUtil.check(Integer.valueOf(count).equals(smsConfig.getMaxPhoneCount()), MsgEnum.ERR_SMSCODE_SEND_TOO_MANNY);
            try {
                // 发送短信通知
                Map<String, String> replaceStrs = new HashMap<String, String>();
                replaceStrs.put("var_phonenu", mobile);
                replaceStrs.put("val_reason", "手机验证码发送次数超限");
                SmsMessage smsMessage = new SmsMessage(null, replaceStrs, null, null,
                        MessageConstant.SMS_SEND_FOR_MANAGER, null, CustomConstants.PARAM_TPL_DUANXINCHAOXIAN,
                        CustomConstants.CHANNEL_TYPE_NORMAL);
                try {
                    smsProducer.messageSend(
                            new MessageContent(MQConstant.SMS_CODE_TOPIC, UUID.randomUUID().toString(), JSON.toJSONBytes(smsMessage)));
                } catch (MQException e) {
                    logger.error("短信发送失败...", e);
                }
            } catch (Exception e) {
                CheckUtil.check(false, MsgEnum.ERR_SMSCODE_SEND_TOO_MANNY);
            }
            RedisUtils.set(mobile + RedisConstants.CACHE_MAX_PHONE_COUNT, (Integer.valueOf(count) + 1) + "", 24 * 60 * 60);
        }

        // 发送checkCode最大时间间隔，默认60秒
        RedisUtils.set(mobile + ":" + validCodeType + ":IntervalTime", mobile,
                smsConfig.getMaxIntervalTime() == null ? 60 : smsConfig.getMaxIntervalTime());
    }

    @Override
    public boolean existUser(String mobile) {
        UserVO userVO = amUserClient.findUserByMobile(mobile);
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
}
