package com.hyjf.am.user.service.front.user;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.service.BaseService;

import java.util.List;


public interface SmsService extends BaseService{
    int save(String mobile, String verificationType, String verificationCode, String platform, Integer status);

    /**
     * 发送的短信验证码保存到数据库
     * @param mobile
     * @param verificationCode
     * @param verificationType
     * @param status
     * @param platform
     * @return
     */
    int saveSmscode(String mobile, String verificationCode, String verificationType, Integer status, String platform);

    /**
     * 检查短信验证码
     */
    int updateCheckMobileCode(String mobile, String verificationCode, String verificationType, String platform,
                              Integer searchStatus, Integer updateStatus, boolean isUpdate);
}
