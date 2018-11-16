/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.service.admin.message;

import com.hyjf.am.resquest.admin.SmsCodeUserRequest;
import com.hyjf.am.user.dao.model.customize.SmsCodeCustomize;

import java.util.List;

/**
 * @author fq
 * @version SmsCodeService, v0.1 2018/8/20 20:30
 */
public interface SmsCodeService {
    /**
     * 查询符合条件的用户
     * @param request
     * @return
     */
    List<SmsCodeCustomize> queryUser(SmsCodeUserRequest request);



    /**
     * 校验千乐验证码
     * @param phone
     * @param code
     * @return
     */
    int checkQianleMobileCode(String phone, String code);


    int save(String mobile, String verificationType, String verificationCode, String platform, Integer status);

}
