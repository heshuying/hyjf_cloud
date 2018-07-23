package com.hyjf.common.constants;

public class UserConstant {
	/** 邮箱状态1未验证  */
	public static final int EMAIL_ACTIVE_STATUS_1 = 1;
	/** 邮箱状态2已验证 */
	public static final int EMAIL_ACTIVE_STATUS_2 = 2;
	/** 邮箱状态3过期(或已发送新的邮件) */
	public static final int EMAIL_ACTIVE_STATUS_3 = 3;

	// 验证码类型
	/** 注册 */
	public static final String PARAM_TPL_ZHUCE = "TPL_ZHUCE";
	/** 找回密码 */
	public static final String PARAM_TPL_ZHAOHUIMIMA = "TPL_ZHAOHUIMIMA";
	/** 更换手机号-验证原手机号 */
	public static final String PARAM_TPL_YZYSJH = "TPL_YZYSJH";
	/** 更换手机号-绑定新手机号 */
	public static final String PARAM_TPL_BDYSJH = "TPL_BDYSJH";
	/** 提现放欺诈-验证码校验 */
	public static final String PARAM_TPL_SMS_WITHDRAW = "TPL_SMS_WITHDRAW";

	/** 短信验证码状态,新验证码 */
	public static final Integer CKCODE_NEW = 0;
	/** 短信验证码状态,失效 */
	public static final Integer CKCODE_FAILED = 7;
	/** 短信验证码状态,已验 */
	public static final Integer CKCODE_YIYAN = 8;
	/** 短信验证码状态,已用 */
	public static final Integer CKCODE_USED = 9;
}
