/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.am.message.processer;

/**
 * 信息常量定义类
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年6月20日
 * @see 下午2:21:36
 */
public class MessageDefine {

    /* 短信主队列key */
    public static final String SMSQUENEM = "smsQuenem";

    /* 短信副队列key */
    public static final String SMSQUENES = "smsQuenes";

    /* 邮件主队列key */
    public static final String MAILQUENEM = "mailQuenem";

    /* 邮件副队列key */
    public static final String MAILQUENES = "mailQuenes";

    /* app消息主队列key */
    public static final String APPMSQUENEM = "appMsQuenem";

    /* app消息副队列key */
    public static final String APPMSQUENES = "appMsQuenes";

    // 通知配置,根据模版给指定管理员手机号发送消息（满标，标到期等）
    public static final String SMSSENDFORMANAGER = "smsSendForManager";

    // 根据用户ID和模版号给某用户发短信
    public static final String SMSSENDFORUSER = "smsSendForUser";

    // 根据电话号码和模版号给某电话发短信
    public static final String SMSSENDFORMOBILE = "smsSendForMobile";

    // 根据电话号码和消息内容给某电话发短信
    public static final String SMSSENDFORUSERSNOTPL = "手动群发";

    // 给指定邮件地址发邮件
    public static final String MAILSENDFORMAILINGADDRESSMSG = "maillSendForMailingAddressMsg";

    // 根据电话号码和模板号给某电话推送app消息
    public static final String APPMSSENDFORMOBILE = "appMsSendForMobile";

    // 根据用户Id和模板号给某用户推送app消息
    public static final String APPMSSENDFORUSER = "appMsSendForUser";

    // 根据消息发送
    public static final String APPMSSENDFORMSG = "appMsSendForMsg";

    // 给指定用户发邮件
    public static final String MAILSENDFORUSER = "maillSendForUser";

    // 给指定邮件地址发邮件
    public static final String MAILSENDFORMAILINGADDRESS = "maillSendForMailingAddress";

    public static final String MAILSENDFORMAILINGTOSELF = "maillSendForMailingToSelf";

}
