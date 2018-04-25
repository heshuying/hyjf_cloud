package com.hyjf.common.constants;

/**
 * @author xiasq
 * @version CommonConstants, v0.1 2018/4/25 17:09
 */
public class CommonConstants {
    // PC
    public static final String CLIENT_PC = "0";

    // 微官网
    public static final String CLIENT_WECHAT = "1";

    // 安卓
    public static final String CLIENT_ANDROID = "2";

    // IOS
    public static final String CLIENT_IOS = "3";

    // 其他
    public static final String CLIENT_OTHER = "4";

    /** 短信验证码状态,新验证码 */
    public static final Integer CKCODE_NEW = 0;
    /** 短信验证码状态,失效 */
    public static final Integer CKCODE_FAILED = 7;
    /** 短信验证码状态,已验 */
    public static final Integer CKCODE_YIYAN = 8;
    /** 短信验证码状态,已用 */
    public static final Integer CKCODE_USED = 9;



    /** 短信模板名 */
    /** 注册 */
    public static final String PARAM_TPL_ZHUCE = "TPL_ZHUCE";
    /** 短信模板名-提现验证码 */
    public static final String PARAM_TPL_SMS_WITHDRAW = "TPL_SMS_WITHDRAW";

    /** 找回密码 */
    public static final String PARAM_TPL_ZHAOHUIMIMA = "TPL_ZHAOHUIMIMA";

    /** 更换手机号-验证原手机号 */
    public static final String PARAM_TPL_YZYSJH = "TPL_YZYSJH";

    /** 更换手机号-绑定新手机号 */
    public static final String PARAM_TPL_BDYSJH = "TPL_BDYSJH";
}
