/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.common.cache;

/**
 * redis专用常量
 * @author fp
 * @version RedisConstants, v0.1 2018/3/27 15:43
 */
public class RedisConstants {

    // ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ key通用常量 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ //
    /**
     * 投资防重校验过期时间
     */
    public static final Integer TENDER_OUT_TIME = 300;

    // 汇计划分割线
    public static final String HJH_SLASH = "_";

    // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ key通用常量 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ //


    /**
     * 记录密码错误次数Redis前缀web端(password_err_count_app:userName/mobile)
     */
    public static final String PASSWORD_ERR_COUNT_APP = "password_err_count_app:";

    /**
     * 记录密码错误次数Redis前缀web端(password_err_count_app:userName/mobile)
     */
    public static final String PASSWORD_ERR_COUNT_WEB = "password_err_count_web:";

    /**
     * 用户一秒内的登录次数(ip)
     */
    public static final String LOGIN_ONE_COUNT_WEB = "login_one_count_web:";

    /**
     * 用户一秒内的登录次数(ip)
     */
    public static final String LOGIN_ONE_COUNT_APP = "login_one_count_app:";

    /**
     * 恶意攻击登录ip黑名单
     */
    public static final String LOGIN_BLACK_LIST_WEB = "login_black_list_web:";

    /**
     * 恶意攻击登录ip黑名单
     */
    public static final String LOGIN_BLACK_LIST_APP = "login_black_list_app:";


    /**
     * 风险保证金前缀
     */
//	public static final String CAPITAL_TOPLIMIT_ = "capital_toplimit:";

    /**
     * 汇计划发标redis key
     */
    public static final String GEN_HJH_BORROW_NID = "gen_hjh_borrow_nid:";

    /**
     * 加入计划防重校验
     */
    public static final String HJH_TENDER_REPEAT = "hjh_tender_repeat:";

    /**
     * 汇计划可投余额前缀 (hjhbal:$planNid)
     */
    public static final String HJH_PLAN = "hjhbal:";

    /**
     * 汇计划进入锁定期处理中队列
     */
    public static final String HJH_LOCK_REPEAT = "hjh_lockisrepeat:";

    /**
     * 散标投资异步防重校验
     */
    public static final String TENDER_ORDERID = "tender_orderid:";

    /**
     * 投资优惠券使用rediskey(RedisConstants.COUPON_TENDER_KEY+couponGrantId, 300)  by sunss
     */
    public static final String COUPON_TENDER_KEY = "coupon_tender:";


    /** 直投类放款任务名称 */
	public static final String ZHITOU_LOAN_TASK = "zhitouLoan:";
	/**计划类实时放款 add by cwyang 2017-10-23*/
	public static final String PLAN_REALTIME_LOAN_TASK = "planRealtimeLoan:";

	/**直投类实时放款 add by cwyang 2017-10-23*/
	public static final String ZHITOU_REALTIME_LOAN_TASK = "zhitouRealtimeLoan:";
	/** 计划类放款任务名称 */
	public static final String PLAN_LOAN_TASK = "planLoan:";
	/** 放款请求任务名称 */
	public static final String LOAN_REQUEST_TASK = "loanRequest:";

	/** 直投类还款任务名称 */
	public static final String ZHITOU_REPAY_TASK = "zhitouRepay:";
	/** 计划类还款任务名称 */
	public static final String PLAN_REPAY_TASK = "planRepay:";
	/** 还款请求任务名称 */
	public static final String REPAY_REQUEST_TASK = "repayRequest:";

    // 汇计划队列前缀 (queue:credit:$creditNid, queue:invest:$borrowNid, queue:invest:$borrowNid_tmp)
    public static final String HJH_PLAN_LIST = "queue:";

    // 汇计划自动债转中的标志 redis key (debtswaping:$borrowNid)
    public static final String HJH_DEBT_SWAPING = "debtswaping:";

    // 汇计划标的队列标识 债转（债转标的） (queue:credit:$creditNid)
    public static final String HJH_BORROW_CREDIT = "credit:";
    // 汇计划标的队列标识 投资（原始标的） (queue:invest:$borrowNid)
    public static final String HJH_BORROW_INVEST = "invest:";

    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 start
    // _tmp (queue:invest:$borrowNid_tmp)
    public static final String HJH_SLASH_TMP = "_tmp";
    // add 汇计划三期 汇计划自动投资(分散投资) liubin 20180515 end

    /**
     * 散标投资防重校验
     */
    public static final String BORROW_TENDER_REPEAT = "borrow_tender_repeat:";

    /**
     * 短信模板缓存
     */
    public static final String SMS_TEMPLATE_TPLCODE = "sms_template_tplCode:";

    /**
     * 网关路由配置
     */
    public static final String ZUUL_ROUTER_CONFIG_KEY = "zuul_roter_config_key:";

    /**
     * PARAM表配置
     */
    public static final String CACHE_PARAM_NAME = "hyjf_param_name:";

    /**
     * 短信单手机控制(max_phone_count:mobile)
     */
    public static final String CACHE_MAX_PHONE_COUNT = "max_phone_count:";

    /**
     * 单IP最大登录控制(max_ip_count:ip)
     */
    public static final String CACHE_MAX_IP_COUNT = "max_ip_count:";

    /**
     * 自动关联计划redis防重复
     */
    public static final String AUTO_ISSUE_REPEAT = "borrow_issue:";

    /**
     * 单用户防止重复提交redis 前缀
     */
    public static final String PRE_REQUEST_LIMIT = "req_limit:";

    /**
     * borrowNid
     */
    public static final String BORROW_NID = "BORROW_NID:";
    /**
     * wechat sign(sign:sign)
     */
    public static final String SIGN = "sign:";
    /**
     * admin单点登陆用户
     */
    public static final String ADMIN_REQUEST = "admin:";
    
    /**
     * 协议模板---协议前台展示名称的别名
     */
    public static final String PROTOCOL_TEMPLATE_ALIAS = "protocol_template_alias:";
    
    /**
     * 协议模板---协议文件存储Redis前缀
     */
    public static final String PROTOCOL_TEMPLATE_URL = "protocol_template_url:";
    

    /**
     * 用户token令牌前缀(token:token)
     */
    public static final String USER_TOEKN_KEY = "token:";

    /**
     * 用户userId令牌前缀(user_id:userId)
     */
    public static final String USERID_KEY = "user_id:";

    /**
     * 放款批次号key
     */
    public static final String BATCH_NO = "batch_no:";
    /**
     *
     */
    public static final String DATA_BATCH_NO = "data_batch_no:";
    /**
     * 短信配置key
     */
    public static final String SMS_CONFIG = "sms_config:";

    /**
     * 记录密码错误次数Redis前缀(password_err_count:userId)
     */
    public static final String PASSWORD_ERR_COUNT = "password_err_count:";

    /**
     * 邮件配置key
     */
    public static final String SITE_SETTINGS = "site_settings:";

    /**
     * 消息推送模版key
     */
    public static final String MESSAGE_PUSH_TEMPLATE = "message_push_template:";

    /**
     * 短信通知配置key
     */
    public static final String SMS_NOTICE_CONFIG = "sms_notice_config:";

    /**
     * 短信模版key
     */
    public static final String SMS_TEMPLATE = "sms_template:";

    /**
     * 邮件模版key
     */
    public static final String SMS_MAIL_TEMPLATE = "sms_mail_template:";

    /**
     * 运营报告定时任务key
     */
    public static final String Statistics_Operation_Report = "statistics_operation_report_task";

    /** CONTROLLOR @value值 */
    public static final String CONTROLLOR_CLASS_NAME = "user_regist_define:";

    /**
     * 充值防重校验
     */
    public static final String RECHARGE_ORDERID = "recharge_orderid:";

    /**
     * 定时发标key
     */
    public static final String ONTIME = "ontime:";
    /*分隔符*/
    public static final String UNDERLINE = "_";

    /*分隔符*/
    public static final String COLON = ":";

    /** 定时发标时间key名 */
    public static final String REDIS_KEY_ONTIME = "ontime";

    /** 定时发标状态修改锁key名 */
    public static final String REDIS_KEY_ONTIME_LOCK = "ontime_lock";

    /** 定时发标状态修改状态key名 */
    public static final String REDIS_KEY_ONTIME_STATUS = "ontime_status";

    /**
     * 用户画像评分
     */
    public static final String USERPORTRAIT_SCORE = "user_portrait_score";

    /**
	 * 汇计划提成
	 * 之前是大寫PUSH_MONEY
	 * add by libin
	 */
	public static final String PUSH_MONEY_ = "push_money:";

	/**
	 * 四端平台名称rediskey(这不是redis前缀, 这是key)
	 * @author zhangyk
	 * @date 2018/8/13 9:24
	 */
	public static final String CLIENT = "CLIENT";

    /** 某计划连续开放额度不同次数 */
    public static final String CONT_WARN_OF_HJH_ACCOUNT = "cont_warn_of_hjh_account:";

    /**
     * 群发短信--剩余短信条数
     */
    public static final String REMAIN_NUMBER = "remain_number:";

    /**
     * 群发短信--短信余额
     */
    public static final String REMAIN_MONEY = "remain_money:";

    /**
     * 上海大屏幕运营数据
     */
    public static final String SH_OPERATIONAL_DATA = "sh_operational_data:";

    /**
     * 上海大屏幕运营数据 : 统计投资人
     */
    public static final String STATISTICAL_INVESTOR = "statistical_investor:";

    /**
     * 上海大屏幕运营数据 : 统计投资
     */
    public static final String STATISTICAL_INVESTMENT = "statistical_investment:";

    /**
     * 上海大屏幕运营数据 : 统计充值
     */
    public static final String STATISTICAL_RECHARGE = "statistical_recharge:";

    /**
     * 上海大屏幕运营数据 : 注册人统计
     */
    public static final String REGISTRANT_STATISTICS = "registrant_statistics:";

    /**
     * 上海大屏幕运营数据 : 公司动态列表
     */
    public static final String ARTICLE_LIST = "article_list:";

    /**
     * 协议模板二期---动态展示协议前台展示名称
     */
    public static final String PROTOCOL_PARAMS = "protocol_params:";

    /**
     * 同步余额rediskey(syn_balance:userId)
     */
    public static final String SYNBALANCE = "syn_balance:";


    /**
     * 受托支付申请防并发key(trustee_pay:logOrderId)
     */
    public static final String CONCURRENCE_TRUSTEEPAY = "trustee_pay:";

    /**
     * 担保机构批量还款防并发key
     */
    public static final String CONCURRENCE_BATCH_ORGREPAY_USERID = "batch_org_repay_userid:";

    /**
     * 线下充值类型
     */
    public static final String UNDER_LINE_RECHARGE_TYPE = "under_line_recharge_type:";

    /**
     * 还款申请防并发key
     */
    public static final String CONCURRENCE_REPAY_REQUEST = "repay_borrow_nid:";

    /**
     * 记录密码错误次数Redis前缀admin端
     */
    public static final String PASSWORD_ERR_COUNT_ADMIN = "password_err_count_admin_";

    /**
     * 记录密码错误次数Redis前缀.web.app三端共享
     */
    public static final String PASSWORD_ERR_COUNT_ALL = "password_err_count_all_";

    /**
     * 日推标累计额度前缀
     */
    public static final String DAY_MARK_ACCUMULATE = "bail_day_mark_accumulate:";

    /**
     * 日发标已用
     */
    public static final String DAY_USED = "bail_day_used:";

    /**
     * 日推标上限额度前缀
     */
    public static final String DAY_MARK_LINE = "bail_day_mark_line:";

    /**
     * 月发标已用
     */
    public static final String MONTH_USED = "bail_month_used:";

    /**
     * 月推标上限额度前缀
     */
    public static final String MONTH_MARK_LINE = "bail_month_mark_line:";

    /**
     * 登录失败配置项
     */
    public static final String LOCKED_CONFIG = "lock_config";


    /**
     * 测评到期时间和测评结果投资金额上限用
     */
    public static final String REVALUATION = "evaluation:";

    /**
     * 测评到期时间
     */
    public static final String REVALUATION_EXPIRED_DAY = REVALUATION  + "evaluation_expired_day";

    /**
     * 保守型
     */
    public static final String REVALUATION_CONSERVATIVE = REVALUATION  + "conservative";

    /**
     * 稳健型
     */
    public static final String REVALUATION_ROBUSTNESS = REVALUATION  + "robustness";

    /**
     * 成长型
     */
    public static final String REVALUATION_GROWTH = REVALUATION  + "growth";

    /**
     * 进取型
     */
    public static final String REVALUATION_AGGRESSIVE =  REVALUATION  + "aggressive";


    /**
     * 上传合同模板暂存地址
     */
    public static final String TEMPLATE_UPLOAD_URL = "template_upload_url:";

    /**
     * 缴费授权
     */
    public final static String KEY_PAYMENT_AUTH = "AUTHCONFIG:paymentAuth";
    /**
     * 还款授权
     */
    public final static String KEY_REPAYMENT_AUTH = "AUTHCONFIG:repaymentAuth";
    /**
     * 自动投标
     */
    public final static String KEY_AUTO_TENDER_AUTH = "AUTHCONFIG:autoTenderAuth";
    /**
     * 自动债转
     */
    public final static String KEY_AUTO_CREDIT_AUTH = "AUTHCONFIG:autoCreditAuth";
    /**
     * 网贷之家token校验(+userName)
     */
    public final static String KEY_WDZJ_KEY="token_wdzj_";

    /**
     * 神策开户redis+用户ID
     */
    public final static String SENSORS_DATA_OPEN_ACCOUNT ="SENSORS_DATA_OPEN_ACCOUNT:";
}
