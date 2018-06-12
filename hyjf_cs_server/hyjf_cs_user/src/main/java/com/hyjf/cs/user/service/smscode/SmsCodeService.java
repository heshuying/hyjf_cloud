package com.hyjf.cs.user.service.smscode;

import com.hyjf.common.exception.MQException;

/**
 * @author xiasq
 * @version SmsCodeService, v0.1 2018/4/25 9:02
 */
public interface SmsCodeService {
    /**
     * 发送短信验证码
     * @param validCodeType
     * @param mobile
     * @param token
     * @param ip
     * @throws MQException
     */
    void sendSmsCode(String validCodeType, String mobile, String token, String ip) throws MQException;

    boolean existUser(String mobile);
}
