package com.hyjf.common.constants;

/**
 * @author xiasq
 * @version MQConstant, v0.1 2018/5/4 13:45
 */
public interface MQConstant {

	/**
	 * 不特别指定的时候使用的默认tag
	 */
	String HYJF_DEFAULT_TAG = "HYJF_DEFAULT_TAG";
	/**
	 * 不特别指定的时候使用的默认key,标识一条消息唯一
	 */
	String HYJF_DEFAULT_KEY = String.valueOf(System.currentTimeMillis());

	/**
	 * 发送短信的 group topic
	 */
	String SMS_CODE_GROUP = "SMS_CODE_GROUP";
	String SMS_CODE_TOPIC = "SMS_CODE_TOPIC";

	/**
	 * 发送邮件的 group topic
	 */
	String MAIL_GROUP = "MAIL_GROUP";
	String MAIL_TOPIC = "MAIL_TOPIC";

	/**
	 * 发送app push group topic
	 */
	String APP_MESSAGE_GROUP = "APP_MESSAGE_GROUP";
	String APP_MESSAGE_TOPIC = "APP_MESSAGE_TOPIC";

	/**
	 * 注册保存资产表account
	 */
	String ACCOUNT_GROUP = "ACCOUNT_GROUP";
	String ACCOUNT_TOPIC = "ACCOUNT_TOPIC";

	/**
	 * 优惠券发放
	 */
	String GRANT_COUPON_GROUP = "COUPON_GROUP";
	// 注册送188红包
	String REGISTER_COUPON_TOPIC = "REGISTER_COUPON_TOPIC";
	String REGISTER_COUPON_TAG = "REGISTER_COUPON_TAG";
	// 投之家送2张加息券
	String TZJ_REGISTER_INTEREST_TAG = "TZJ_REGISTER_INTEREST_TAG";


	/**
	 * app渠道统计数据
	 */
	String APP_CHANNEL_STATISTICS_DETAIL_GROUP = "APP_CHANNEL_STATISTICS_DETAIL_GROUP";
	String APP_CHANNEL_STATISTICS_DETAIL_TOPIC = "APP_CHANNEL_STATISTICS_DETAIL_TOPIC";
	String APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG = "APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG";
}