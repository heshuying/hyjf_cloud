package com.hyjf.cs.user.service.smscode;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.service.BaseUserService;

/**
 * @author xiasq
 * @version SmsCodeService, v0.1 2018/4/25 9:02
 */
public interface SmsCodeService extends BaseUserService {
    /**
     * 发送短信验证码
     * @param validCodeType
     * @param mobile
     * @param ip
     * @throws MQException
     */
    void sendSmsCode(String validCodeType, String mobile,String platform, String ip) throws MQException;


    /**
     * 参数检查
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     */
    void sendSmsCodeCheckParam(String validCodeType, String mobile, String token, String ip);

    /**
     * app参数检查
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     * @return
     */
    JSONObject appSendSmsCodeCheckParam(String validCodeType, String mobile, String token, String ip);

    /**
     * web 参数检查
     * @param verificationType
     * @param code
     * @param mobile
     */
    void checkParam(String verificationType, String code, String mobile);

    /**
     * 微信发送验证码参数校验
     * @param verificationType
     * @param mobile
     * @param ipAddr
     */
    JSONObject wechatCheckParam(String verificationType, String mobile, String ipAddr,JSONObject ret);
}
