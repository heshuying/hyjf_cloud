/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.common.constants;

/**
 * @author xiasq
 * @version MessageConstant, v0.1 2018/5/4 13:45
 */
public class MessageConstant {

	/**
	 * 通知配置,根据模版给指定管理员手机号发送消息（满标，标到期等）
	 */
	public static final String SMS_SEND_FOR_MANAGER = "sms_send_for_manager";
	/**
	 * 根据用户ID和模版号给某用户发短信
	 */
	public static final String SMS_SEND_FOR_USER = "sms_send_for_user";

	/** 根据电话号码和模版号给某电话发短信 */
	public static final String SMS_SEND_FOR_MOBILE = "sms_send_for_mobile";

	/** 根据电话号码和消息内容给某电话发短信 */
	public static final String SMS_SEND_FOR_USERS_NO_TPL = "手动群发";

	/** 根据电话号码和消息内容给某电话发短信 */
	public static final String SMS_SEND_FOR_BIRTHDAY = "生日祝福";

	/** 根据电话号码和模板号给某电话推送app消息 */
	public static final String APP_MS_SEND_FOR_MOBILE = "app_ms_send_for_mobile";

	/** 根据用户Id和模板号给某用户推送app消息 */
	public static final String APP_MS_SEND_FOR_USER = "app_ms_send_for_user";

	/** 根据消息发送 */
	public static final String APP_MS_SEND_FOR_MSG = "app_ms_send_for_msg";

	/** 推送失败补偿 */
	public static final String APP_MS_SEND_REPEAT = "app_ms_send_repeat";

	/** 给指定用户发邮件 */
	public static final String MAIL_SEND_FOR_USER = "mail_send_for_user";

	/** 给指定邮件地址发邮件 */
	public static final String MAIL_SEND_FOR_MAILING_ADDRESS = "mail_send_for_mailing_address";

	/** 批量给邮件地址发送邮件信息 */
	public static final String MAIL_SEND_FOR_MAILING_ADDRESS_MSG = "mail_send_for_mailing_address_msg";

	/** 发送销售日报邮件 */
	public static final String MAIL_SEND_FRO_SELL_DAILY = "mail_send_for_sell_daily";

}
