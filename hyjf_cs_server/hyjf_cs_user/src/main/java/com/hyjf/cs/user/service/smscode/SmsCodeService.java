package com.hyjf.cs.user.service.smscode;

import com.hyjf.common.exception.MQException;
import com.hyjf.cs.user.service.BaseUserService;
import com.hyjf.cs.user.vo.SmsRequest;

/**
 * @author xiasq
 * @version SmsCodeService, v0.1 2018/4/25 9:02
 */
public interface SmsCodeService extends BaseUserService {
    /**
     * 发送短信验证码
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     * @throws MQException
     */
    void sendSmsCode(String validCodeType, String mobile,String platform, String token, String ip) throws MQException;

    /**
     *
     * @param request
     * @param verificationType
     * @param verificationCode
     * @param mobile
     * @param key
     * @return
     */
    void appCheckParam(SmsRequest request, String verificationType, String verificationCode, String mobile,String key);


    void checkParam(String verificationType, String code, String mobile);
}
