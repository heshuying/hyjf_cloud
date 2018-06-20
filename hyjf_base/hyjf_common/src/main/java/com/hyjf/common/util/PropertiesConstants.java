package com.hyjf.common.util;

public interface PropertiesConstants {

    // 邮件相关配置
    /** 邮件服务器地址 */
    String MAIL_SEND_HOST = "mail.send.host";
    /** 邮件服务器端口 */
    String MAIL_SEND_PORT = "mail.send.port";
    /** 邮件协议 */
    String MAIL_SEND_PROTOCOL = "mail.send.protocol";
    /** 邮件编码 */
    String MAIL_SEND_DEFAULTENCODING = "utf-8";
    /** 邮件用户名 */
    String MAIL_SEND_USERNAME = "mail.send.username";
    /** 邮件密码 */
    String MAIL_SEND_PASSWORD = "mail.send.password";
    /** 邮件发送人邮箱 */
    String MAIL_SEND_FROMMAIL = "mail.send.frommail";
    /** 邮件发送人姓名 */
    String MAIL_SEND_FROMMAILNAME = "mail.send.frommailname";
    /** 邮件模板物理目录 */
    String MAIL_TEMPLATE = "mail.template";
    /** 邮件发送权限 */
    String MAIL_SMTP_AUTH = "mail.smtp.auth";
    /** 邮件发送超时时间 */
    String MAIL_SMTP_TIMEOUT = "mail.smtp.timeout";
    /** 邮件发送SSL */
    String MAIL_SMTP_STARTTLS_ENABLE = "mail.smtp.starttls.enable";

    /** 邮件发送服务器 */
    String MAIL_HOST = "mail.host";
    String MAIL_SMTP_SOCKETFACTORY_CLASS = "mail.smtp.socketFactory.class";
    String MAIL_SMTP_SOCKETFACTORY_CLASS_VALUE = "javax.net.ssl.SSLSocketFactory";
    String MAIL_SMTP_SOCKETFACTORY_FALLBACK = "mail.smtp.socketFactory.fallback";
    String MAIL_SMTP_SOCKETFACTORY_FALLBACK_VALUE = "false";
    String MAIL_SMTP_PORT = "mail.smtp.port";
    String MAIL_SMTP_SOCKETFACTORY_PORT = "mail.smtp.socketFactory.port";
    String MAIL_SMTP_SOCKETFACTORY_PORT_VALUE = "465";

    // 主题工程相关配置
    /** 主题工程路径 */
    String HYJF_THEME_URL = "hyjf.theme.url";

    // 发放优惠券相关
    /** web-api工程路径 */
    String HYJF_API_WEB_URL = "hyjf.api.web.url";
    // 使用优惠券最长等待时间
    /** 优惠券接口最长等待时间工程路径 */
    String HYJF_API_WEB_TIME = "hyjf.api.web.time";
    
    // 支付工程相关配置
    /** 汇付支付工程路径 */
    String HYJF_PAY_URL = "hyjf.pay.url";
    /** 银行支付工程路径 */
    String HYJF_PAY_BANK_URL = "hyjf.pay.bank.url";
    /** 充值管理-手动充值密码   */
    String HYJF_HANDRECHARGE_PASSWORD ="hyjf.handrecharge.password";

    // 前端工程相关配置
    /** PHP工程路径 */
    String HYJF_WEB_NOTIFY_URL = "hyjf.web.tender.callbackurl";
    /** Web工程路径 https前缀 */
    String HYJF_WEB_URL = "hyjf.web.host";
    /** Web工程路径 https前缀 */
    String HYJF_WEB_URL_OLD = "hyjf.web.host.old";
    /** Web工程路径 http前缀 */
    String HTTP_HYJF_WEB_URL = "http.hyjf.web.host";
    /** Admin工程路径 */
    String HYJF_ADMIN_URL = "hyjf.admin.host";
    /** Admin工程登录路径 */
    String HYJF_ADMIN_LOGIN_URL = "hyjf.admin.login.url";
    /** APP工程路径 */
    String HYJF_APP_URL = "hyjf.app.host";

    // 生成PDF文件相关配置
    /** 接口URL */
    String HYJF_SEAL_URL = "hyjf.seal.url";
    /** 接口地址 */
    String HYJF_SEAL_ADDRESS = "hyjf.seal.address";
    /** 接口端口 */
    String HYJF_SEAL_PORT = "hyjf.seal.port";
    /** 接口操作人 */
    String HYJF_SEAL_OPERATE = "hyjf.seal.operate";

    /** 接口系统ID */
    String HYJF_SEAL_SYSID = "hyjf.seal.sysId";
    /** 接口用户ID */
    String HYJF_SEAL_USERID = "hyjf.seal.userid";
    /** 接口密码 */
    String HYJF_SEAL_PASSWORD = "hyjf.seal.password";
    /** PDF html地址 */
    String HYJF_MAKEPDF_URL = "hyjf.makepdf.url";
    /** 临时文件夹目录 */
    String HYJF_MAKEPDF_TEMPPATH = "hyjf.makepdf.temppath";
    /** 域名地址 */
    String HYJF_WEB_PDF_URL = "hyjf.web.pdf.host";
    
    /**
	 * 体验金收益过期时长
	 */
	String GAINS_TIMEOUT_DAYS = "gains.timeout.days";
	
	/**
	 * 购买vip金额
	 */
	String HYJF_VIP_MONEY = "hyjf.vip.money";
	
	/**
	 * 投资夺宝活动编号
	 */
	String TENDER_PRIZE_ACTIVITY_ID = "tender.prize.activity.id";
	
	/**
     * 投之家注册送68元代金券活动编号
     */
    String REGISTER_SEND_COUPON_ACTIVITY_ID = "hyjf.tzj.register.coupon.activity.id";
    
    /**
     * 全站注册送68元代金券活动编号
     */
    String REGISTER_ALL_SEND_COUPON_ACTIVITY_ID = "hyjf.register.give.coupon.activity.id";
    
    /**
     * 投资满1000送加息券活动id
     */
    String TENDER_SEND_COUPON_ACTIVITY_ID = "hyjf.investment.give.coupon.activity.id";
    /**
     * 10月份MGM活动-注册开户用户发放推荐星 活动编号
     */
    String MGM10_ACTIVITY_ID = "hyjf.activity.mgm10.id";
    /**
     * 10月份MGM活动-每次抽奖消耗的推荐星数量
     */
    String PRIZE_DRAW_RECOMMEND_COUNT = "prize.draw.recommend.count";
    
    
    /** crm 工程路径 */
    String HYJF_CRM_URL = "hyjf.crm.url";


    /** 第三方资产方推送用户汇盈平台登陆密码 */
    String HYJF_THIRD_PARTY_USER_PASSWORD = "hyjf.third.party.user.password";

    /** 汇盈金服用户密码加密RSA公钥*/
    String HYJF_PUBLICKEY_PASSWORD = "publickey.password";

    /** 汇盈金服用户密码解密RSA私钥*/
    String HYJF_PRIVATEKEY_PASSWORD = "privatekey.password";

    /** 风车理财回调服务器url */
    String WRB_CALLBACK_NOTIFY_URL = "wrb.callback.notify.url";
    String WRB_CALLBACK_LOGIN_URL = "wrb.callback.login.url";
    String WRB_CALLBACK_BIND_URL = "wrb.callback.bind.url";
}
