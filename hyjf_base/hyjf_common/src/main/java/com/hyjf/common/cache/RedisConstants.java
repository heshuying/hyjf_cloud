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
     * 出借防重校验过期时间
     */
    public static final Integer TENDER_OUT_TIME = 300;

    // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 start
    // _tmp
    public static final String HJH_SLASH_TMP = "_tmp";
    // add 汇计划三期 汇计划自动出借(分散出借) liubin 20180515 end

    // ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ key通用常量 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ //
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
     * 风险保证金前缀 （capital_toplimit:InstCode）
     */
//	public static final String CAPITAL_TOPLIMIT_ = "capital_toplimit:";

    /**
     * 汇计划发标redis key(gen_hjh_borrow_nid:$borrowPreNid)
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
     * 散标出借异步防重校验（tender_orderid:LogOrderId）
     */
    public static final String TENDER_ORDERID = "tender_orderid:";

    /**
     * 出借优惠券使用rediskey(RedisConstants.COUPON_TENDER_KEY+couponGrantId, 300)  by sunss
     */
    public static final String COUPON_TENDER_KEY = "coupon_tender:";


    /** 直投类放款任务名称 （zhitouLoan: + borrowApicron.getBorrowNid() + "_" + borrowApicron.getPeriodNow()）*/
	public static final String ZHITOU_LOAN_TASK = "zhitouLoan:";
	/**计划类实时放款 add by cwyang 2017-10-23*/
	public static final String PLAN_REALTIME_LOAN_TASK = "planRealtimeLoan:";

	/**直投类实时放款 add by cwyang 2017-10-23*/
	public static final String ZHITOU_REALTIME_LOAN_TASK = "zhitouRealtimeLoan:";
	/** 计划类放款任务名称 */
	public static final String PLAN_LOAN_TASK = "planLoan:";
	/** 放款请求任务名称 */
	public static final String LOAN_REQUEST_TASK = "loanRequest:";

	/** 直投类还款任务名称 （zhitouRepay:BorrowNid + "_" + PeriodNow）*/
	public static final String ZHITOU_REPAY_TASK = "zhitouRepay:";
	/** 计划类还款任务名称 （planRepay:BorrowNid + "_" + PeriodNow）*/
	public static final String PLAN_REPAY_TASK = "planRepay:";
	/** 还款请求任务名称 （repayRequest:PeriodNow）*/
	public static final String REPAY_REQUEST_TASK = "repayRequest:";

    // 汇计划队列前缀 (queue:credit:$creditNid, queue:invest:$borrowNid, queue:invest:$borrowNid_tmp)
    public static final String HJH_PLAN_LIST = "queue:";

    // 汇计划自动债转中的标志 redis key (debtswaping:$borrowNid)
    public static final String HJH_DEBT_SWAPING = "DebtSwaping:";

    // 汇计划标的队列标识 债转（债转标的） (queue:credit:$creditNid)
    public static final String HJH_BORROW_CREDIT = "credit:";
    // 汇计划标的队列标识 出借（原始标的） (queue:invest:$borrowNid)
    public static final String HJH_BORROW_INVEST = "invest:";

    /**
     * 散标出借防重校验(borrow_tender_repeat:$userId)
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
     * PARAM表配置(paramName.getNameClass())
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
     * 单用户防止重复提交redis 前缀+$userid:$uri
     */
    public static final String PRE_REQUEST_LIMIT = "req_limit:";

    /**
     * borrowNid(BORROW_NID:$borrowNid)
     */
    public static final String BORROW_NID = "BORROW_NID:";
    /**
     * wechat sign(sign:sign)
     */
    public static final String SIGN = "sign:";
    /**
     * admin单点登陆用户(admin:$username)
     */
    public static final String ADMIN_REQUEST = "admin:";

    /**
     * 协议模板---协议前台展示名称的别名+$aliasName
     */
    public static final String PROTOCOL_TEMPLATE_ALIAS = "protocol_template_alias:";

    /**
     * 协议模板---协议文件存储Redis前缀+$protocolId
     */
    public static final String PROTOCOL_TEMPLATE_URL = "protocol_template_url:";


    /**
     * 用户token令牌前缀（token:token）
     */
    public static final String USER_TOEKN_KEY = "token:";

    /**
     * 用户userId令牌前缀（user_id:userId）
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
     * 运营报告定时任务key（statistics_operation_report_task:$boolean）
     */
    public static final String Statistics_Operation_Report = "statistics_operation_report_task";

    /** CONTROLLOR @value值 */
    public static final String CONTROLLOR_CLASS_NAME = "user_regist_define:";

    /**
     * 充值防重校验+$$orderId
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

    /** 某计划连续开放额度不同次数 (cont_warn_of_hjh_account:$planNid)*/
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
     * 担保机构批量还款防并发key(batch_org_repay_userid:userId)
     */
    public static final String CONCURRENCE_BATCH_ORGREPAY_USERID = "batch_org_repay_userid:";

    /**
     * 线下充值类型（under_line_recharge_type:codeListString）
     */
    public static final String UNDER_LINE_RECHARGE_TYPE = "under_line_recharge_type:";

    /**
     * 还款申请防并发key(repay_borrow_nid:$borrowNid)
     */
    public static final String CONCURRENCE_REPAY_REQUEST = "repay_borrow_nid:";

    /**
     * 记录密码错误次数Redis前缀admin端（password_err_count_admin:$username)
     */
    public static final String PASSWORD_ERR_COUNT_ADMIN = "password_err_count_admin:";

    /**
     * 记录密码错误次数Redis前缀.web.app三端共享+$userId
     */
    public static final String PASSWORD_ERR_COUNT_ALL = "password_err_count_all:";

    /**
     * 日推标累计额度前缀(bail_day_mark_accumulate:$instCode)
     */
    public static final String DAY_MARK_ACCUMULATE = "bail_day_mark_accumulate:";

    /**
     * 日发标已用(bail_day_used:$InstCode_yyyyMMdd)
     */
    public static final String DAY_USED = "bail_day_used:";

    /**
     * 日推标上限额度前缀(bail_day_mark_line:$instCode)
     */
//    public static final String DAY_MARK_LINE = "bail_day_mark_line:";

    /**
     * 月发标已用+$instCode
     */
    public static final String MONTH_USED = "bail_month_used:";

    /**
     * 月推标上限额度前缀+$instCode
     */
//    public static final String MONTH_MARK_LINE = "bail_month_mark_line:";

    /**
     * 登录失败配置项
     */
    public static final String LOCKED_CONFIG = "lock_config";


    /**
     * 测评到期时间和测评结果出借金额上限用redis组
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
     * 保守型（代收本金）
     */
    public static final String REVALUATION_CONSERVATIVE_PRINCIPAL = REVALUATION  + "principalconservative";

    /**
     * 稳健型（代收本金）
     */
    public static final String REVALUATION_ROBUSTNESS_PRINCIPAL = REVALUATION  + "principalrobustness";

    /**
     * 成长型（代收本金）
     */
    public static final String REVALUATION_GROWTH_PRINCIPAL = REVALUATION  + "principalgrowth";

    /**
     * 进取型（代收本金）
     */
    public static final String REVALUATION_AGGRESSIVE_PRINCIPAL =  REVALUATION  + "principalaggressive";

    /**
     * 上传合同模板暂存地址(template_upload_url:templetId)
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

    /**
     * 汇计划计算提成并发控制
     */
    public final static String HJHCOMMISION_COMPUTE = "hjhcommision_compute:";

    /**
     * 绑卡回调并发控制
     */
    public final static String CONCURRENCE_BIND_CARD = "bindCard:";

    /**
     * 散标优惠券放款 couponloans + borrowNid
     */
    public final static String COUPON_LOANS ="couponloans:";

    /**
     * 自动录标 borrowsend + instcode + assetId
     */
    public final static String BORROW_SEND ="borrowsend:";

    /**
     * 自动初审 borrowpreaudit + instcode + assetId/borrowNid
     */
    public final static String BORROW_PRE_AUDIT ="borrowpreaudit:";

    /**
     * 关联计划 borrowissue + creditNid/borrowNid
     */
    public final static String BORROW_ISSUE ="borrowissue:";
    /**
     * 北互金历史上报定时任务执行开关(0:关闭；1：开启)
     */
    public static final String BIFA_HISTORY_OPEN_FLAG = "bifa_history_open_flag";
    /**
     * 北互金历史上报定时任务开始时间
     */
    public static final String BIFA_HISTORY_START_YYYYMMDD = "bifa_history_start_yyyymmdd";
    /**
     * 北互金历史上报定时任务结束时间
     */
    public static final String BIFA_HISTORY_END_YYYYMMDD = "bifa_history_end_yyyymmdd";

    /**
     * 邮件发送key
     */
    public static String LABEL_MAIL_KEY = "labelmailkey:";

    /**
     * 自动备案 borrowrecord + instcode + assetId/borrowNid
     */
    public static String BORROW_RECORD = "borrowrecord:";

    /**
     * 自动审核保证金 borrowbail + instcode + borrowNid
     */
    public static String BORROW_BAIL = "borrowbail:";


    /**
     * synBalance + userId
     */
    public static String SYN_VALANCE = "synBalance:";

    /**
     * 优惠券还款 couponrepay + borrowNid + periodNow
     */
    public static String COUPON_REPAY = "couponrepay:";

    /**
     * 优惠券发放 couponsend + userid + couponCode + sendFlg +  activityId
     */
    public static String COUPON_SEND = "couponsend:";

    /**
     * 汇计划优惠券还款 couponrepayhjh + orderId
     */
    public static String COUPON_REPAY_HJH = "couponrepayhjh:";

    /**
     * 汇计划优惠券放款 couponloanshjh + orderId
     */
    public static String COUPON_LOANS_HJH = "couponloanshjh:";

    /**
     * rocketmq 事务消息结果
     */
    public static final String MQ_TRANSACTION_PREFIX = "mq_tran:";
    /**
     * admin单点登陆唯一Id
     */
    public static final String ADMIN_UNIQUE_ID = "admin@:";

    /**
     * 纳觅返现活动
     */
    public static final String RETURN_CASH_ACTIVITY = "RETURN_CASH_ACTIVITY:";

    /**合规数据上报 CERT 批次号自增 序列号*/
    public static final String CERT_BATCH_NUMBER_SEQ_ID = "CERT_BATCH_NUMBER_SEQ_ID";
    /**合规数据上报 CERT 是否初始化标的  没有 就是没初始化*/
    public static final String CERT_BATCH_IS_INIT_BORROW= "CERT_BATCH_IS_INIT_BORROW";

    /**合规数据上报 CERT 是否开始实时上报数据  0不上报  1上报*/
    public static final String CERT_CAN_RUN= "CERT_CAN_RUN";

    /**合规数据上报 CERT 已上报交易流水最大Id*/
    public static final String CERT_TRANSACT_OTHER_MAXID= "certTransactOtherMaxId";
    /**合规数据上报 CERT 已上报投资明细最大Id*/
    public static final String CERT_INVEST_DETAIL_MAXID= "certInvestDetailMaxId";

    /**
     * 推送表的消息唯一标识
     */
    public static final String MSG_PUSH_CODE = "MSG_PUSH_CODE:";

    /**
     * 校验债转用的计算金额是否有异常数据(0:关闭；1：开启)
     */
    public static final String CHECK_HJH_CREDIT_CALC_FLAG = "check_hjh_credit_calc_flag";

    /**
     * 后台处理Aleve和eve文件增加redis锁控制、防止重复发送mq消息
     */
    public static final String DUAL_HISTORY_ALEVE = "DUAL_HISTORY_ALEVE:";


    /**
     * app端短信验证码登录   自动登录的key
     */
    public static final String APP_SMS_LOGIN_KEY = "APP_SMS_LOGIN_KEY:";





    /**
     * 投资异步执行三次问题  加一个锁校验一下
     * 投资成功后  12小时以内不处理
     */
    public static final String BORROW_TENDER_ORDER_CHECK = "BORROW_TENDER_ORDER_CHECK:";


    /**
     * 清算,计算公允价值与自动投资并发处理
     *
     */
    public static final String  HJH_TENDER_LOCK ="HJH_TENDER_LOCK:";

    public static final String POINTS_SHOP_SWITCH = "points_shop_switch";
}
