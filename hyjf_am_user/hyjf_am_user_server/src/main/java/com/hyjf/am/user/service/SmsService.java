package com.hyjf.am.user.service;

/**
 * @author xiasq
 * @version SmsService, v0.1 2018/4/12 17:32
 */
public interface SmsService {
    int save(String mobile, String verificationType, String verificationCode, String platform, Integer status);

    /**
     * 检查短信验证码
     */
    int checkMobileCode(String mobile, String verificationCode, String verificationType, String platform, Integer status, Integer updateStatus);

    /**
     * 发送的短信验证码保存到数据库
     * @param mobile
     * @param verificationCode
     * @param verificationType
     * @param status
     * @param platform
     * @return
     */
    int saveSmsCode(String mobile, String verificationCode, String verificationType, Integer status, String platform);
}
