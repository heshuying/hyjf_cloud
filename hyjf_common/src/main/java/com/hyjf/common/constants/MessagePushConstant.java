package com.hyjf.common.constants;

/**
 * @author xiasq
 * @version MessagePushConstant, v0.1 2018/4/24 18:43
 */
public interface MessagePushConstant {

	// 通知配置,根据模版给指定管理员手机号发送消息（满标，标到期等）
	String SMSSENDFORMANAGER = "smsSendForManager";

	// 根据用户ID和模版号给某用户发短信
	String SMSSENDFORUSER = "smsSendForUser";

	// 根据电话号码和模版号给某电话发短信
	String SMSSENDFORMOBILE = "smsSendForMobile";

	// 根据电话号码和消息内容给某电话发短信
	String SMSSENDFORUSERSNOTPL = "手动群发";

	// 给指定邮件地址发邮件
	String MAILSENDFORMAILINGADDRESSMSG = "maillSendForMailingAddressMsg";

	// 根据电话号码和模板号给某电话推送app消息
	String APPMSSENDFORMOBILE = "appMsSendForMobile";

	// 根据用户Id和模板号给某用户推送app消息
	String APPMSSENDFORUSER = "appMsSendForUser";

	// 根据消息发送
	String APPMSSENDFORMSG = "appMsSendForMsg";

	// 给指定用户发邮件
	String MAILSENDFORUSER = "maillSendForUser";

	// 给指定邮件地址发邮件
	String MAILSENDFORMAILINGADDRESS = "maillSendForMailingAddress";

	String MAILSENDFORMAILINGTOSELF = "maillSendForMailingToSelf";

}
