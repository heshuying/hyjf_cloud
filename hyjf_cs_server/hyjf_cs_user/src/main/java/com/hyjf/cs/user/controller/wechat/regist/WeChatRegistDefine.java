/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.user.controller.wechat.regist;

import com.hyjf.cs.user.bean.BaseDefine;

/**
 * @author: sunpeikai
 * @version: WeChatRegistDefine, v0.1 2018/7/31 13:57
 */
public class WeChatRegistDefine {
    public static final String REQUEST_HOME = "/hyjf-wechat";
    /** REQUEST_MAPPING */
    public static final String REQUEST_MAPPING = "/userRegist";
    /** 验证验证码 */
    public static final String VALIDATE_VERIFICATIONCODE_ACTION = "/validateVerificationCodeAction";
    /** 验证验证码 */
    public static final String VALIDATE_VERIFICATIONCODE_REQUEST = REQUEST_HOME + REQUEST_MAPPING + VALIDATE_VERIFICATIONCODE_ACTION;

    /** 短信验证码状态,新验证码 */
    public static final Integer CKCODE_NEW = 0;
    /** 短信验证码状态,失效 */
    public static final Integer CKCODE_FAILED = 7;
    /** 短信验证码状态,已验 */
    public static final Integer CKCODE_YIYAN = 8;
    /** 短信验证码状态,已用 */
    public static final Integer CKCODE_USED = 9;
}
