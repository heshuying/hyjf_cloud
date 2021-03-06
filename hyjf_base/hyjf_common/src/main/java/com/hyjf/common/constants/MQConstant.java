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

	String TEST_GROUP = "TEST_GROUP";
	String TEST_TOPIC = "TEST_TOPIC";

	/** ------------------------------ 消息中心 start ------------------------------ */
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
	 * 发送运营报告admin group topic
	 */
	String OPERATIONREPORT_JOB_ADMIN_GROUP = "OPERATIONREPORT_JOB_ADMIN_GROUP";
	String OPERATIONREPORT_JOB_ADMIN_TOPIC = "OPERATIONREPORT_JOB_ADMIN_TOPIC";

	/**
	 * 发送运营报告group topic
	 */
	String OPERATIONREPORT_JOB_GROUP = "OPERATIONREPORT_JOB_GROUP";
	String OPERATIONREPORT_JOB_TOPIC = "OPERATIONREPORT_JOB_TOPIC";
	/**
	 * 发送app push group topic
	 */
	String APP_MESSAGE_GROUP = "APP_MESSAGE_GROUP";
	String APP_MESSAGE_TOPIC = "APP_MESSAGE_TOPIC";

	/**
	 * 发送短信统计 push group topic
	 */
	String SMS_COUNT_MESSAGE_GROUP = "SMS_COUNT_MESSAGE_GROUP";
	String SMS_COUNT_MESSAGE_TOPIC = "SMS_COUNT_MESSAGE_TOPIC";
	/** ------------------------------ 消息中心 end ------------------------------ */




	/** ------------------------------ 优惠券相关 start ------------------------------ */

	/**
	 * 优惠券发放
	 */
	String GRANT_COUPON_GROUP = "COUPON_GROUP";
	String GRANT_COUPON_TOPIC = "COUPON_TOPIC";

	/**
	 * 优惠券发放失败转存
	 */
	String GRANT_COUPON_FAIL_GROUP = "COUPON_FAIL_GROUP";
	String GRANT_COUPON_FAIL_TOPIC = "COUPON_FAIL_TOPIC";

	/**
	 * 汇直投-优惠券使用
	 */
	String HZT_COUPON_TENDER_GROUP = "HZT_COUPON_TENDER_GROUP";
	String HZT_COUPON_TENDER_TOPIC = "HZT_COUPON_TENDER_TOPIC";

	/**
	 * 汇计划-优惠券使用
	 */
	String HJH_COUPON_TENDER_GROUP = "HJH_COUPON_TENDER_GROUP";
	String HJH_COUPON_TENDER_TOPIC = "HJH_COUPON_TENDER_TOPIC";

	/**
	 * 汇直投-优惠券放款
	 */
	String HZT_COUPON_LOAN_GROUP = "HZT_COUPON_LOAN_GROUP";
	String HZT_COUPON_LOAN_TOPIC = "HZT_COUPON_LOAN_TOPIC";

	/**
	 * 汇计划-优惠券放款
	 */
	String HJH_COUPON_LOAN_GROUP = "HJH_COUPON_LOAN_GROUP";
	String HJH_COUPON_LOAN_TOPIC = "HJH_COUPON_LOAN_TOPIC";

	/**
	 * 汇直投-优惠券还款
	 */
	String HZT_COUPON_REPAY_GROUP = "HZT_COUPON_REPAY_GROUP";
	String HZT_COUPON_REPAY_TOPIC = "HZT_COUPON_REPAY_TOPIC";

	/**
	 * 汇计划-优惠券还款
	 */
	String HJH_COUPON_REPAY_GROUP = "HJH_COUPON_REPAY_GROUP";
	String HJH_COUPON_REPAY_TOPIC = "HJH_COUPON_REPAY_TOPIC";

	/**
	 * 体验金按收益期限还款
	 */
	String TYJ_COUPON_REPAY_GROUP = "TYJ_COUPON_REPAY_GROUP";
	String TYJ_COUPON_REPAY_TOPIC = "TYJ_COUPON_REPAY_TOPIC";

	/** ------------------------------- 优惠券相关 end ------------------------- end */




	/** ------------------------------ 数据统计 start ------------------------------ */

	/** 每月统计运营数据 */
	String STATISTICS_OPERATION_REPORT_GROUP = "STATISTICS_OPERATION_REPORT_GROUP";
	String STATISTICS_OPERATION_REPORT_TOPIC = "STATISTICS_OPERATION_REPORT_TOPIC";

	/** 借款用户运营数据 */
	String BORROW_USER_STATISTICS_GROUP = "BORROW_USER_STATISTICS_GROUP";
	String BORROW_USER_STATISTICS_TOPIC = "BORROW_USER_STATISTICS_TOPIC";

	/**
	 * 网站累计数据统计 修改mongodb运营数据
	 */
	String STATISTICS_CALCULATE_INVEST_INTEREST_GROUP = "STATISTICS_CALCULATE_INVEST_INTEREST_GROUP";
	String STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC = "STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC";
	/** 总出借金额累加tag */
	String STATISTICS_CALCULATE_INVEST_SUM_TAG = "STATISTICS_CALCULATE_INVEST_SUM_TAG";
	/** 总收益累加tag */
	String STATISTICS_CALCULATE_INTEREST_SUM_TAG = "STATISTICS_CALCULATE_INTEREST_SUM_TAG";
	/** 更新统计平台数据tag */
	String STATISTICS_CALCULATE_INTEREST_UPDATE_TAG = "STATISTICS_CALCULATE_INTEREST_UPDATE_TAG";



	/**
	 * 会员操作日志
	 */
	String USER_OPERATION_LOG_GROUP = "USER_OPERATION_LOG_GROUP";
	String USER_OPERATION_LOG_TOPIC = "USER_OPERATION_LOG_TOPIC";

	/**
	 * 用户大屏三消息
	 */
	String USER_SCREEN_PICTURE3_GROUP = "USER_SCREEN_PICTURE3_GROUP";
	String USER_SCREEN_PICTURE3_TOPIC = "USER_SCREEN_PICTURE3_TOPIC";

	/**
	 * 新PC渠道统计
	 */
	String PC_CHANNEL_STATISTICS_GROUP = "PC_CHANNEL_STATISTICS_GROUP";
	String PC_CHANNEL_STATISTICS_TOPIC = "PC_CHANNEL_STATISTICS_TOPIC";

	/**
	 * 新PC渠道统计admin
	 */
	String PC_CHANNEL_STATISTICS_ADMIN_GROUP = "PC_CHANNEL_STATISTICS_ADMIN_GROUP";
	String PC_CHANNEL_STATISTICS_ADMIN_TOPIC = "PC_CHANNEL_STATISTICS_ADMIN_TOPIC";

	/**
	 * app渠道统计
	 */
	String APP_CHANNEL_STATISTICS_GROUP = "APP_CHANNEL_STATISTICS_GROUP";
	String APP_CHANNEL_STATISTICS_TOPIC = "APP_CHANNEL_STATISTICS_TOPIC";

	/**
	 * 网站收支数据统计
	 */
	String ACCOUNT_WEB_LIST_GROUP = "ACCOUNT_WEB_LIST_GROUP";
	String ACCOUNT_WEB_LIST_TOPIC = "ACCOUNT_WEB_LIST_TOPIC";

	/**
	 * app渠道统计数据
	 */
	String APP_CHANNEL_STATISTICS_DETAIL_GROUP = "APP_CHANNEL_STATISTICS_DETAIL_GROUP";
	String APP_CHANNEL_STATISTICS_DETAIL_TOPIC = "APP_CHANNEL_STATISTICS_DETAIL_TOPIC";
	String APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG = "APP_CHANNEL_STATISTICS_DETAIL_UPDATE_TAG";
	String APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG = "APP_CHANNEL_STATISTICS_DETAIL_SAVE_TAG";
	String APP_CHANNEL_STATISTICS_DETAIL_CREDIT_TAG = "APP_CHANNEL_STATISTICS_DETAIL_CREDIT_TAG";
	String APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG = "APP_CHANNEL_STATISTICS_DETAIL_INVEST_TAG";

	/**
	 * app渠道登录统计
	 */
	String APP_ACCESS_STATISTICS_GROUP = "APP_ACCESS_STATISTICS_GROUP";
	String APP_ACCESS_STATISTICS_TOPIC = "APP_ACCESS_STATISTICS_TOPIC";
	/**
	 * 投之家日报表
	 */
	@Deprecated
	String STATISTICS_TZJ_GROUP = "STATISTICS_TZJ_GROUP";
	@Deprecated
	String STATISTICS_TZJ_TOPIC = "STATISTICS_TZJ_TOPIC";

	/**
	 * 投之家渠道报表
	 */
	@Deprecated
	String STATISTICS_TZJ_UTM_GROUP = "STATISTICS_TZJ_UTM_GROUP";
	@Deprecated
	String STATISTICS_TZJ_UTM_TOPIC = "STATISTICS_TZJ_UTM_TOPIC";

	/** ------------------------------ 数据统计 end ------------------------------ */




	/** ------------------------------ 汇计划 start ------------------------------ */

	/**
	 * 汇计划进入锁定期/退出
	 */
	String HJH_LOCK_QUIT_GROUP = "HJH_LOCK_QUIT_GROUP";
	String HJH_LOCK_QUIT_TOPIC = "HJH_LOCK_QUIT_TOPIC";

	/**
	 * 汇计划自动计算公允价值
	 */
	String HJH_CALCULATE_FAIR_VALUE_GROUP = "HJH_CALCULATE_FAIR_VALUE_GROUP";
	String HJH_CALCULATE_FAIR_VALUE_TOPIC = "HJH_CALCULATE_FAIR_VALUE_TOPIC";

	/** ------------------------------ 汇计划 end ------------------------------ */




	/** ------------------------------ 标的自动化 start ------------------------------ */

	/**
	 * 自动录标
	 *
	 * 来源:
	 * 	1. 资产推送成功;
	 * 	2. 汇计划发标修复定时任务;
	 */
	String AUTO_ISSUE_RECOVER_GROUP = "AUTO_ISSUE_RECOVER_GROUP";
	String AUTO_ISSUE_RECOVER_TOPIC = "AUTO_ISSUE_RECOVER_TOPIC";
	/** TAG */
	String AUTO_ISSUE_RECOVER_PUSH_TAG = "AUTO_ISSUE_RECOVER_PUSH_TAG";
	String AUTO_ISSUE_RECOVER_REPAIR_TAG = "AUTO_ISSUE_RECOVER_REPAIR_TAG";

	/**
	 * 自动备案
	 *
	 * 来源：
	 * 	1.admin录入标的成功判断是自动备案;
	 * 	2.自动录标成功;
	 * 	3.汇计划发标修复定时任务;
	 */
	String AUTO_BORROW_RECORD_GROUP = "AUTO_BORROW_RECORD_GROUP";
	String AUTO_BORROW_RECORD_TOPIC = "AUTO_BORROW_RECORD_TOPIC";
	/** TAG */
	String AUTO_BORROW_RECORD_ADMIN_TAG = "AUTO_BORROW_RECORD_ADMIN_TAG";
	String AUTO_BORROW_RECORD_ISSUE_TAG = "AUTO_BORROW_RECORD_ISSUE_TAG";
	String AUTO_BORROW_RECORD_REPAIR_TAG = "AUTO_BORROW_RECORD_REPAIR_TAG";

	/**
	 * 自动审核保证金
	 *
	 * 来源：
	 * 	1.admin标的备案成功;
	 * 	2.汇计划发标修复定时任务;
	 *
	 */
	String AUTO_VERIFY_BAIL_GROUP = "AUTO_VERIFY_BAIL_GROUP";
	String AUTO_VERIFY_BAIL_TOPIC = "AUTO_VERIFY_BAIL_TOPIC";
	/** TAG */
	String AUTO_VERIFY_BAIL_ADMIN_TAG =  "AUTO_VERIFY_BAIL_ADMIN_TAG";
	String AUTO_VERIFY_BAIL_REPAIR_TAG =  "AUTO_VERIFY_BAIL_REPAIR_TAG";

	/**
	 * 自动初审
	 *
	 * 来源：
	 * 	1.admin确认已交保证金判断是自动初审;
	 * 	2.admin标的备案成功判断是自动初审;
	 * 	3.admin异常中心-标的备案掉单
	 * 	4.汇计划发标修复定时任务;
	 * 	5.自动审核保证金成功;
	 * 	6.自动备案成功;
	 * 	7.借款人受托支付申请异步回调更新;
	 */
	String AUTO_BORROW_PREAUDIT_GROUP = "AUTO_BORROW_PREAUDIT_GROUP";
	String AUTO_BORROW_PREAUDIT_TOPIC = "AUTO_BORROW_PREAUDIT_TOPIC";
	/** TAG */
	String AUTO_BORROW_PREAUDIT_ADMIN_BAIL_TAG = "AUTO_BORROW_PREAUDIT_ADMIN_BAIL_TAG";
	String AUTO_BORROW_PREAUDIT_ADMIN_RECORD_TAG = "AUTO_BORROW_PREAUDIT_ADMIN_RECORD_TAG";
	String AUTO_BORROW_PREAUDIT_ADMIN_EXCEPTION_TAG = "AUTO_BORROW_PREAUDIT_ADMIN_EXCEPTION_TAG";
	/** 汇计划发标修复定时任务发两种消息，标的和资产的，分开区分 **/
	String AUTO_BORROW_PREAUDIT_ASSET_REPAIR_TAG = "AUTO_BORROW_PREAUDIT_ASSET_REPAIR_TAG";
	String AUTO_BORROW_PREAUDIT_BORROW_REPAIR_TAG = "AUTO_BORROW_PREAUDIT_BORROW_REPAIR_TAG";
	String AUTO_BORROW_PREAUDIT_AUTO_BAIL_TAG = "AUTO_BORROW_PREAUDIT_AUTO_BAIL_TAG";
	/** 自动备案成功发两种消息，标的和资产的，分开区分 **/
	String AUTO_BORROW_PREAUDIT_ASSET_RECORD_TAG = "AUTO_BORROW_PREAUDIT_ASSET_RECORD_TAG";
	String AUTO_BORROW_PREAUDIT_BORROW_RECORD_TAG = "AUTO_BORROW_PREAUDIT_BORROW_RECORD_TAG";
	String AUTO_BORROW_PREAUDIT_ST_TAG = "AUTO_BORROW_PREAUDIT_ST_TAG";

	/**
	 * 自动关联计划
	 *
	 * 来源：
	 * 	1.admin发标 产品中心-汇直投-借款初审查询初审状态暂不发布的;
	 * 	2.admin标的录入
	 * 	3.汇计划发标修复定时任务;
	 * 	4.自动初审成功
	 * 	5.汇计划自动清算
	 * 	6.汇计划定时发标
	 * 	7.散标定时发标 - 拆分标的
	 */
	String AUTO_ASSOCIATE_PLAN_GROUP = "AUTO_ASSOCIATE_PLAN_GROUP";
	String AUTO_ASSOCIATE_PLAN_TOPIC = "AUTO_ASSOCIATE_PLAN_TOPIC";
	/** TAG */
	String AUTO_ASSOCIATE_PLAN_ADMIN_ISSUE_TAG = "AUTO_ASSOCIATE_PLAN_ADMIN_ISSUE_TAG";
	String AUTO_ASSOCIATE_PLAN_ADMIN_INSERT_TAG = "AUTO_ASSOCIATE_PLAN_ADMIN_INSERT_TAG";
	/** 汇计划发标修复定时任务发两种消息，原始标的和债转标的，分开区分 **/
	String AUTO_ASSOCIATE_PLAN_BORROW_REPAIR_TAG = "AUTO_ASSOCIATE_PLAN_BORROW_REPAIR_TAG";
	String AUTO_ASSOCIATE_PLAN_CREDIT_REPAIR_TAG = "AUTO_ASSOCIATE_PLAN_CREDIT_REPAIR_TAG";
	String AUTO_ASSOCIATE_PLAN_AUTO_PRE_ISSUE_TAG = "AUTO_ASSOCIATE_PLAN_AUTO_PRE_ISSUE_TAG";
	String AUTO_ASSOCIATE_PLAN_CLEAR_TAG = "AUTO_ASSOCIATE_PLAN_CLEAR_TAG";
	String AUTO_ASSOCIATE_PLAN_JOB_TAG = "AUTO_ASSOCIATE_PLAN_JOB_TAG";

	/** ------------------------------ 标的自动化 end ------------------------------ */




	/** ------------------------------ 开户流程 start ------------------------------ */
	/**
	 * 注册保存资产表account
	 */
	String ACCOUNT_GROUP = "ACCOUNT_GROUP";
	String ACCOUNT_TOPIC = "ACCOUNT_TOPIC";
	/** 注册送188红包 */
	String REGISTER_COUPON_TOPIC = "REGISTER_COUPON_TOPIC";
	String REGISTER_COUPON_TAG = "REGISTER_COUPON_TAG";

	/** ------------------------------ 开户流程 end ------------------------------ */




	/** ------------------------------ 放款还款 start ------------------------------ */

	/**
	 * 放款，还款请求，放款，还款
	 */
	String BORROW_GROUP = "BORROW_GROUP";

	String BORROW_REPAY_REQUEST_TOPIC = "BORROW_REPAY_REQUEST_TOPIC";
	String BORROW_REPAY_ZT_RESULT_TOPIC = "BORROW_REPAY_ZT_RESULT_TOPIC";
	String BORROW_REPAY_PLAN_RESULT_TOPIC = "BORROW_REPAY_PLAN_RESULT_TOPIC";

	String BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC = "BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC";
	String BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC = "BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC";
	
	String BORROW_REPAY_REQUEST_GROUP = "BORROW_REPAY_REQUEST_GROUP";
	String BORROW_REPAY_ZT_RESULT_GROUP = "BORROW_REPAY_ZT_RESULT_GROUP";
	String BORROW_REPAY_PLAN_RESULT_GROUP = "BORROW_REPAY_PLAN_RESULT_GROUP";

	String BORROW_REALTIMELOAN_ZT_REQUEST_GROUP = "BORROW_REALTIMELOAN_ZT_REQUEST_GROUP";
	String BORROW_REALTIMELOAN_PLAN_REQUEST_GROUP = "BORROW_REALTIMELOAN_PLAN_REQUEST_GROUP";

	/** ------------------------------ 放款还款 end ------------------------------ */




	/** ------------------------------ 法大大 start ------------------------------ */
	/**
	 * 法大大
	 */
	String FDD_GROUP = "FDD_GROUP";
	String FDD_TOPIC = "FDD_TOPIC";

	/**
	 * 法大大修改客户信息
	 */
	String FDD_USERINFO_CHANGE_GROUP = "FDD_USERINFO_CHANGE_GROUP";
	String FDD_USERINFO_CHANGE_TOPIC = "FDD_USERINFO_CHANGE_TOPIC";

	/**
	 * 法大大CA认证
	 */
	String FDD_CERTIFICATE_AUTHORITY_GROUP = "FDD_CERTIFICATE_AUTHORITY_GROUP";
	String FDD_CERTIFICATE_AUTHORITY_TOPIC = "FDD_CERTIFICATE_AUTHORITY_TOPIC";

	/** 法大大生成合同处理 */
	String FDD_GENERATE_CONTRACT_TAG = "FDD_GENERATE_CONTRACT_TAG";
	/** 法大大自动签署处理 */
	String FDD_AUTO_SIGN_TAG = "FDD_AUTO_SIGN_TAG";
	/** 法大大下载脱敏处理 */
	String FDD_DOWNPDF_AND_DESSENSITIZATION_TAG = "FDD_DOWNPDF_AND_DESSENSITIZATION_TAG";

	/** ------------------------------ 法大大 end ------------------------------ */


	/**
	 * crm开户信息推送
	 */
	String CRM_ROUTINGKEY_BANCKOPEN_GROUP = "CRM_ROUTINGKEY_BANCKOPEN_GROUP";
	String CRM_ROUTINGKEY_BANCKOPEN_TOPIC = "CRM_ROUTINGKEY_BANCKOPEN_TOPIC";

    /**
     * crm出借信息推送
     */
    String CRM_TENDER_INFO_TOPIC = "CRM_TENDER_INFO_TOPIC";

	/**
	 * 存款业务红包流水全明细数据文件下载定时任务相关
	 */
	String DOWNLOAD_FILE_GROUP = "DOWNLOAD_FILE_GROUP";

	String ALEVE_FILE_GROUP = "ALEVE_FILE_GROUP";
	String ALEVE_FILE_TOPIC = "ALEVE_FILE_TOPIC";

	String EVE_FILE_GROUP = "EVE_FILE_GROUP";
	String EVE_FILE_TOPIC = "EVE_FILE_TOPIC";

	String AUTO_CORRECTION_GROUP = "AUTO_CORRECTION_GROUP";
	String AUTO_CORRECTION_TOPIC = "AUTO_CORRECTION_TOPIC";

	/**
	 * 更新huiyingdai_utm_reg的首投信息
	 */
	String STATISTICS_UTM_REG_GROUP = "STATISTICS_UTM_REG_GROUP";
	String STATISTICS_UTM_REG_TOPIC = "STATISTICS_UTM_REG_TOPIC";

	/**
	 * VIP用户
	 */
	String VIP_USER_TENDER_GROUP = "VIP_USER_TENDER_GROUP";
	String VIP_USER_TENDER_TOPIC = "VIP_USER_TENDER_TOPIC";

	/** 风车理财 */
	String WRB_QUEUE_CALLBACK_NOTIFY_GROUP = "WRB_QUEUE_CALLBACK_NOTIFY_GROUP";
	String WRB_QUEUE_CALLBACK_NOTIFY_TOPIC = "WRB_QUEUE_CALLBACK_NOTIFY_TOPIC";

    /** 原子服务生产通用组  同步r_user用户信息用topic */
    String AM_USER_GENERAL_GROUP = "AM_USER_GENERAL_GROUP";
    String AM_CONFIG_GENERAL_GROUP = "AM_CONFIG_GENERAL_GROUP";
    String AM_TRADE_GENERAL_GROUP = "AM_TRADE_GENERAL_GROUP";
    String SYNC_RUSER_TOPIC = "SYNC_RUSER_TOPIC";

	/**
	 * 账户额度信息同步到crm
	 */
	String SYNC_ACCOUNT_GROUP = "SYNC_ACCOUNT_GROUP";
	String SYNC_ACCOUNT_TOPIC = "SYNC_ACCOUNT_TOPIC";

	/**
	 * user操作CRM  OA USER
	 */
	String USER_CRM_OA_USER_GROUP = "USER_CRM_OA_USER_GROUP";
	/**
	 * trade操作CRM  OA USER
	 */
	String TRADE_CRM_OA_USER_GROUP = "TRADE_CRM_OA_USER_GROUP";
	String CRM_OA_USER_TOPIC = "CRM_OA_USER_TOPIC";

	/**
	 * user操作CRM  OA_DEPARTMENT
	 */
	String USER_CRM_OA_DEPARTMENT_GROUP = "USER_CRM_OA_DEPARTMENT_GROUP";
	/**
	 * trade操作CRM  OA_DEPARTMENT
	 */
	String TRADE_CRM_OA_DEPARTMENT_GROUP = "TRADE_CRM_OA_DEPARTMENT_GROUP";
	String CRM_OA_DEPARTMENT_TOPIC = "CRM_OA_DEPARTMENT_TOPIC";


	/**
	 * 入职 离职 员工信息更新（原CRM调用接口，改为MQ实现）
	 */
	String CRM_USER_ENTRY_GROUP = "CRM_USER_ENTRY_GROUP";
	String CRM_USER_ENTRY_TOPIC = "CRM_USER_ENTRY_TOPIC";

	String CRM_USER_LEAVE_GROUP = "CRM_USER_LEAVE_GROUP";
	String CRM_USER_LEAVE_TOPIC = "CRM_USER_LEAVE_TOPIC";

	/**
	 * 客户修改推荐人（原CRM调用接口，改为MQ实现）
	 */
	String CRM_REFEREE_GROUP = "CRM_REFEREE_GROUP";
	String CRM_REFEREE_TOPIC = "CRM_REFEREE_TOPIC";

	/**
	 * 定时短信
	 */
	String SMS_ONTIME_GROUP = "SMS_ONTIME_GROUP";
	String SMS_ONTIME_TOPIC = "SMS_ONTIME_TOPIC";

	/**
	 * 互金放款生成合同信息要素
	 */
	String CONTRACT_ESSENCE_GROUP = "CONTRACT_ESSENCE_GROUP";
	String CONTRACT_ESSENCE_TOPIC = "CONTRACT_ESSENCE_TOPIC";
	/**
	 * 互金还款更新合同信息和还款信息
	 */
	String NIFA_REPAY_INFO_GROUP = "NIFA_REPAY_INFO_GROUP";
	String NIFA_REPAY_INFO_TOPIC = "NIFA_REPAY_INFO_TOPIC";

	/**
	 * 互金放款生成合同信息要素
	 */
	String NIFA_LOAN_GROUP = "NIFA_LOAN_GROUP";
	String NIFA_LOAN_TOPIC = "NIFA_LOAN_TOPIC";

	/**
	 * 互金放款生成合同信息要素
	 */
	String NIFA_REPAY_GROUP = "NIFA_REPAY_GROUP";
	String NIFA_REPAY_TOPIC = "NIFA_REPAY_TOPIC";

	/**
	 * 互金放款生成合同信息要素
	 */
	String NIFA_CREDIT_GROUP = "NIFA_CREDIT_GROUP";
	String NIFA_CREDIT_TOPIC = "NIFA_CREDIT_TOPIC";

	/** ------------------------------ 神策数据统计 start ------------------------------ */
	/**
	 * 用户注册事件
	 */
	String SENSORSDATA_REGISTER_GROUP = "SENSORSDATA_REGISTER_GROUP";
	String SENSORSDATA_REGISTER_TOPIC = "SENSORSDATA_REGISTER_TOPIC";

	String WBS_REGISTER_GROUP = "WBS_REGISTER_GROUP";
	String WBS_REGISTER_TOPIC="WBS_REGISTER_TOPIC";
	String WBS_REGISTER_TAG="WBS_REGISTER_TAG";

	/**
	 * 用户登陆事件
	 */
	String SENSORSDATA_LOGIN_GROUP = "SENSORSDATA_LOGIN_GROUP";
	String SENSORSDATA_LOGIN_TOPIC = "SENSORSDATA_LOGIN_TOPIC";


	/**
	 * 用户充值事件
	 */
	String SENSORSDATA_RECHARGE_GROUP = "SENSORSDATA_RECHARGE_GROUP";
	String SENSORSDATA_RECHARGE_TOPIC = "SENSORSDATA_RECHARGE_TOPIC";


	/**
	 * 用户提现事件
	 */
	String SENSORSDATA_WITHDRAW_GROUP = "SENSORSDATA_WITHDRAW_GROUP";
	String SENSORSDATA_WITHDRAW_TOPIC = "SENSORSDATA_WITHDRAW_TOPIC";

	/**
	 * 债转相关事件
	 */
	String SENSORSDATA_CREDIT_GROUP = "SENSORSDATA_CREDIT_GROUP";
	String SENSORSDATA_CREDIT_TOPIC = "SENSORSDATA_CREDIT_TOPIC";

	/**
	 * 计划加入相关
	 */
	String SENSORSDATA_HJH_INVEST_GROUP = "SENSORSDATA_HJH_INVEST_GROUP";
	String SENSORSDATA_HJH_INVEST_TOPIC = "SENSORSDATA_HJH_INVEST_TOPIC";


	/**
	 * 散标出借结果相关
	 */
	String SENSORSDATA_HZT_INVEST_GROUP = "SENSORSDATA_HZT_INVEST_GROUP";
	String SENSORSDATA_HZT_INVEST_TOPIC = "SENSORSDATA_HZT_INVEST_TOPIC";
	/**
	 * 纳觅返现活动
	 */
	String RETURN_CASH_ACTIVITY_SAVE_GROUP = "RETURN_CASH_ACTIVITY_SAVE_GROUP";
	String RETURN_CASH_ACTIVITY_SAVE_TOPIC = "RETURN_CASH_ACTIVITY_SAVE_TOPIC";
	String RETURN_CASH1_ACTIVITY_SAVE_GROUP = "RETURN_CASH1_ACTIVITY_SAVE_GROUP";
	String RETURN_CASH1_ACTIVITY_SAVE_TOPIC = "RETURN_CASH1_ACTIVITY_SAVE_TOPIC";

	/**
	 * 授权相关
	 */
	String SENSORSDATA_AUTH_GROUP = "SENSORSDATA_AUTH_GROUP";
	String SENSORSDATA_AUTH_TOPIC = "SENSORSDATA_AUTH_TOPIC";


	/**
	 * 开户相关
	 */
	String SENSORSDATA_OPEN_ACCOUNT_GROUP = "SENSORSDATA_OPEN_ACCOUNT_GROUP";
	String SENSORSDATA_OPEN_ACCOUNT_TOPIC = "SENSORSDATA_OPEN_ACCOUNT_TOPIC";


	/** ------------------------------ 神策数据统计 end ------------------------------ */

	/**
	 * 销售日报
	 */
	String SELL_DAILY_GROUP = "SELL_DAILY_GROUP";
	String SELL_DAILY_TOPIC = "SELL_DAILY_TOPIC";
    String SELL_DAILY_SELECT_TAG = "SELL_DAILY_SELECT_TAG";

    /** ------------------------------ 运营大屏数据统计 start ------------------------------ */
	String SCREEN_DATA_GROUP = "SCREEN_DATA_GROUP";
	String SCREEN_DATA_TOPIC = "SCREEN_DATA_TOPIC";
	String SCREEN_DATA_TWO_GROUP = "SCREEN_DATA_TWO_GROUP";
	String SCREEN_DATA_TWO_TOPIC = "SCREEN_DATA_TWO_TOPIC";
	String SCREEN_DATA_TWO_SELECT_TAG = "SCREEN_DATA_TWO_SELECT_TAG";
	/** ------------------------------ 运营大屏数据统计 end ------------------------------ */

    /**--------------------------------- 应急中心 -------------------------------------*/

    /**用户信息*/
	String CERT_USER_INFO_GROUP = "CERT_USER_INFO_GROUP";
	String CERT_USER_INFO_TOPIC = "SELL_DAILY_TOPIC";
	String CERT_REPAIR_USER_INFO = "CERT_REPAIR_USER_INFO";
	String CERT_USER_INFO_TAG = "OPEN_ACCOUNT_SUCCESS_TAG||USERINFO_CHANGE_TAG||"+CERT_REPAIR_USER_INFO;
	/** 债权信息*/
	String CERT_TENDER_INFO_GROUP = "CERT_TENDER_INFO_GROUP";
	String CERT_TENDER_INFO_TOPIC = "CERT_TENDER_INFO_TOPIC";
	String CERT_REPAIR_TENDER_INFO = "CERT_REPAIR_TENDER_INFO";
	// 异常重发
	String CERT_TENDER_INFO_TAG = CERT_REPAIR_TENDER_INFO + "||" + MQConstant.LOAN_SUCCESS_TAG;


	/**散标数据*/
	String CERT_SCATTER_INVEST_GROUP = "CERT_SCATTER_INVEST_GROUP";
	String CERT_SCATTER_INVEST_TOPIC = "CERT_SCATTER_INVEST_TOPIC";
	String CERT_REPAIR_SCATTER_INVEST = "CERT_REPAIR_SCATTER_INVEST";
	String CERT_SCATTER_INVEST_TAG = "LOAN_SUCCESS_TAG||"+CERT_REPAIR_SCATTER_INVEST;


	/** 还款计划信息*/
	String CERT_BORROW_REPAYMENTPLAN_GROUP = "CERT_BORROW_REPAYMENTPLAN_GROUP";
	String CERT_REPAIR_BORROW_REPAYMENTPLAN = "CERT_REPAIR_BORROW_REPAYMENTPLAN";
	String CERT_BORROW_REPAYMENTPLAN_TOPIC = "CERT_BORROW_REPAYMENTPLAN_TOPIC";
	// 异常重发
	String CERT_BORROW_REPAYMENTPLAN_TAG = CERT_REPAIR_BORROW_REPAYMENTPLAN + "||" + MQConstant.LOAN_SUCCESS_TAG;

    /** 散标状态信息*/
	String CERT_BORROW_STATUS_GROUP_INVEST = "CERT_BORROW_STATUS_GROUP_INVEST";
	String CERT_BORROW_STATUS_GROUP_INVESTED = "CERT_BORROW_STATUS_GROUP_INVESTED";
	String CERT_BORROW_STATUS_GROUP_INVEST_LOAN = "CERT_BORROW_STATUS_GROUP_INVEST_LOAN";
	String CERT_BORROW_STATUS_GROUP_INVEST_REPAY = "CERT_BORROW_STATUS_GROUP_INVEST_REPAY";
    String CERT_BORROW_STATUS_TOPIC = "CERT_BORROW_STATUS_TOPIC";
    // 异常重发
	String CERT_REPAIR_BORROW_STATUS = "CERT_REPAIR_BORROW_STATUS";
	String CERT_BORROW_STATUS_TAG = CERT_REPAIR_BORROW_STATUS + "||" + MQConstant.ISSUE_INVESTED_TAG + "||" + MQConstant.LOAN_SUCCESS_TAG + "||" + MQConstant.REPAY_ALL_SUCCESS_TAG;

	/**转让项目*/
	String CERT_TRANSFER_PROJECT_GROUP = "CERT_TRANSFER_PROJECT_GROUP";
	String CERT_REPAIR_TRANSFER_PROJECT = "CERT_REPAIR_TRANSFER_PROJECT";
	String CERT_TRANSFER_PROJECT_TAG = MQConstant.TRANSFER_SUCCESS_TAG+"||"+CERT_REPAIR_TRANSFER_PROJECT;
	/**转让状态*/
	String CERT_TRANSFER_STATUS_GROUP = "CERT_TRANSFER_STATUS_GROUP";
	String CERT_TRANSFER_STATUS_TOPIC = "CERT_TRANSFER_STATUS_TOPIC";

	String CERT_REPAIR_TRANSFER_STATUS = "CERT_REPAIR_TRANSFER_STATUS";

	String CERT_TRANSFER_STATUS_TAG = MQConstant.TRANSFER_SUCCESS_TAG+"||"+MQConstant.UNDERTAKE_ALL_FAIL_TAG+"||"
			+MQConstant.UNDERTAKE_ALL_SUCCESS_TAG+"||"+MQConstant.REPAY_ALL_SUCCESS_TAG+"||"+CERT_REPAIR_TRANSFER_STATUS;
	/**交易流水*/
	String CERT_TRANSACT_GROUP = "CERT_TRANSACT_GROUP";
	String CERT_TRANSACT_TOPIC = "CERT_TRANSACT_TOPIC";
	String CERT_TRANSACT_TAG = "CERT_TRANSACT_TAG";

	/**投资明细*/
	String CERT_INVEST_DETAIL_GROUP = "CERT_INVEST_DETAIL_GROUP";
	String CERT_INVEST_DETAIL_TOPIC = "CERT_INVEST_DETAIL_TOPIC";
	String CERT_INVEST_DETAIL_TAG = "CERT_INVEST_DETAIL_TAG";

	/**投资明细投资收到回款历史数据*/
	String CERT_OLD_INVEST_DETAIL_TENDER_RECOVER_YES_GROUP = "CERT_OLD_INVEST_DETAIL_TENDER_RECOVER_YES_GROUP";
	String CERT_OLD_INVEST_DETAIL_TENDER_RECOVER_YES_TOPIC = "CERT_OLD_INVEST_DETAIL_TENDER_RECOVER_YES_TOPIC";
	String CERT_OLD_INVEST_DETAIL_TENDER_RECOVER_YES_TAG = "CERT_OLD_INVEST_DETAIL_TENDER_RECOVER_YES_TAG";
	/**投资明细承接收到回款历史数据*/
	String CERT_OLD_INVEST_DETAIL_CREDIT_TENDER_RECOVER_YES_GROUP = "CERT_OLD_INVEST_DETAIL_CREDIT_TENDER_RECOVER_YES_GROUP";
	String CERT_OLD_INVEST_DETAIL_CREDIT_TENDER_RECOVER_YES_TOPIC = "CERT_OLD_INVEST_DETAIL_CREDIT_TENDER_RECOVER_YES_TOPIC";
	String CERT_OLD_INVEST_DETAIL_CREDIT_TENDER_RECOVER_YES_TAG = "CERT_OLD_INVEST_DETAIL_CREDIT_TENDER_RECOVER_YES_TAG";
	/**投资明细自动投资成功历史数据*/
	String CERT_OLD_INVEST_DETAIL_HJH_TENDER_SUCCESS_GROUP = "CERT_OLD_INVEST_DETAIL_HJH_TENDER_SUCCESS_GROUP";
	String CERT_OLD_INVEST_DETAIL_HJH_TENDER_SUCCESS_TOPIC = "CERT_OLD_INVEST_DETAIL_HJH_TENDER_SUCCESS_TOPIC";
	String CERT_OLD_INVEST_DETAIL_HJH_TENDER_SUCCESS_TAG = "CERT_OLD_INVEST_DETAIL_HJH_TENDER_SUCCESS_TAG";
	/**投资明细投资成功历史数据*/
	String CERT_OLD_INVEST_DETAIL_TENDER_SUCCESS_GROUP = "CERT_OLD_INVEST_DETAIL_TENDER_SUCCESS_GROUP";
	String CERT_OLD_INVEST_DETAIL_TENDER_SUCCESS_TOPIC = "CERT_OLD_INVEST_DETAIL_TENDER_SUCCESS_TOPIC";
	String CERT_OLD_INVEST_DETAIL_TENDER_SUCCESS_TAG = "CERT_OLD_INVEST_DETAIL_TENDER_SUCCESS_TAG";
	/**投资明细自动债转投资成功历史数据*/
	String CERT_OLD_INVEST_DETAIL_ACCEDE_ASSIGN_GROUP = "CERT_OLD_INVEST_DETAIL_ACCEDE_ASSIGN_GROUP";
	String CERT_OLD_INVEST_DETAIL_ACCEDE_ASSIGN_TOPIC = "CERT_OLD_INVEST_DETAIL_ACCEDE_ASSIGN_TOPIC";
	String CERT_OLD_INVEST_DETAIL_ACCEDE_ASSIGN_TAG = "CERT_OLD_INVEST_DETAIL_ACCEDE_ASSIGN_TAG";

	/**投资明细债转投资成功历史数据*/
	String CERT_OLD_INVEST_DETAIL_CREDIT_ASSIGN_GROUP = "CERT_OLD_INVEST_DETAIL_CREDIT_ASSIGN_GROUP";
	String CERT_OLD_INVEST_DETAIL_CREDIT_ASSIGN_TOPIC = "CERT_OLD_INVEST_DETAIL_CREDIT_ASSIGN_TOPIC";
	String CERT_OLD_INVEST_DETAIL_CREDIT_ASSIGN_TAG = "CERT_OLD_INVEST_DETAIL_CREDIT_ASSIGN_TAG";

	/**投资明细上报历史数据*/
	String CERT_OLD_INVEST_DETAIL_MESSAGE_SEND_GROUP = "CERT_OLD_INVEST_DETAIL_MESSAGE_SEND_GROUP";
	String CERT_OLD_INVEST_DETAIL_MESSAGE_SEND_TOPIC = "CERT_OLD_INVEST_DETAIL_MESSAGE_SEND_TOPIC";
	String CERT_OLD_INVEST_DETAIL_MESSAGE_SEND_TAG = "CERT_OLD_INVEST_DETAIL_MESSAGE_SEND_TAG";
	/**投资明细转让收到回款历史数据*/
	String CERT_OLD_INVEST_DETAIL_CREDIT_SELL_GROUP = "CERT_OLD_INVEST_DETAIL_CREDIT_SELL_GROUP";
	String CERT_OLD_INVEST_DETAIL_CREDIT_SELL_TOPIC = "CERT_OLD_INVEST_DETAIL_CREDIT_SELL_TOPIC";
	String CERT_OLD_INVEST_DETAIL_CREDIT_SELL_TAG = "CERT_OLD_INVEST_DETAIL_CREDIT_SELL_TAG";

	/**投资明细清算收到回款历史数据*/
	String CERT_OLD_INVEST_DETAIL_LIQUIDATES_SELL_GROUP = "CERT_OLD_INVEST_DETAIL_LIQUIDATES_SELL_GROUP";
	String CERT_OLD_INVEST_DETAIL_LIQUIDATES_SELL_TOPIC = "CERT_OLD_INVEST_DETAIL_LIQUIDATES_SELL_TOPIC";
	String CERT_OLD_INVEST_DETAIL_LIQUIDATES_SELL_TAG = "CERT_OLD_INVEST_DETAIL_LIQUIDATES_SELL_TAG";
	/**投资明细清算收到回款历史数据*/
	String CERT_OLD_INVEST_DETAIL_OTHER_GROUP = "CERT_OLD_INVEST_DETAIL_OTHER_GROUP";
	String CERT_OLD_INVEST_DETAIL_OTHER_TOPIC = "CERT_OLD_INVEST_DETAIL_OTHER_TOPIC";
	String CERT_OLD_INVEST_DETAIL_OTHER_TAG = "CERT_OLD_INVEST_DETAIL_OTHER_TAG";
	/** 承接信息*/
	String CERT_CREDITTENDERINFO_GROUP = "CERT_CREDITTENDERINFO_GROUP";
	String CERT_CREDITTENDERINFO_TOPIC = "CERT_CREDITTENDERINFO_TOPIC";
	// 承接异常重发
	String CERT_REPAIR_CREDITTENDERINFO = "CERT_REPAIR_CREDITTENDERINFO";
	String CERT_CREDITTENDERINFO_TAG = CERT_REPAIR_CREDITTENDERINFO + "||" + MQConstant.UNDERTAKE_SINGLE_SUCCESS_TAG;

	/** 产品信息*/
	String CERT_LENDPRODUCT_GROUP = "CERT_LENDPRODUCT_GROUP";
	// 产品信息异常重发
	String CERT_REPAIR_LENDPRODUCT = "CERT_REPAIR_LENDPRODUCT";
	// 产品信息异常、添加智投、修改智投时报送
	String CERT_LENDPRODUCT_TAG = CERT_REPAIR_LENDPRODUCT+"||"+MQConstant.HJHPLAN_ADD_TAG+"||"+MQConstant.HJHPLAN_MODIFY_TAG +"||"+MQConstant.ISSUE_INVESTING_TAG;

	/** 产品配置信息*/
	String CERT_LENDPRODUCTCONFIG_GROUP = "CERT_PRODUCTCONFIG_GROUP";
	// 产品配置异常重发
	String CERT_REPAIR_LENDPRODUCTCONFIG = "CERT_REPAIR_LENDPRODUCTCONFIG";
	// 计入计划，或单笔承接成功
	String CERT_LENDPRODUCTCONFIG_TAG = CERT_REPAIR_LENDPRODUCTCONFIG+"||"+MQConstant.UNDERTAKE_SINGLE_SUCCESS_TAG+"||"+MQConstant.LOAN_SUCCESS_TAG;

	//产品信息历史数据推送
	String CERT_OLD_LENDPRODUCT_GROUP = "CERT_OLD_LENDPRODUCT_GROUP";
	String CERT_OLD_LENDPRODUCT_TAG = "CERT_OLD_LENDPRODUCT_TAG" ;

	/** 产品配置信息历史数据推送*/
	String CERT_OLD_LENDPRODUCTCONFIG_GROUP = "CERT_OLD_LENDPRODUCTCONFIG_GROUP";
	String CERT_OLD_LENDPRODUCTCONFIG_TAG = "CERT_OLD_LENDPRODUCTCONFIG_TAG";

	/**异常处理*/
	String CERT_EXCEPTION_GROUP = "CERT_EXCEPTION_GROUP";
	String CERT_EXCEPTION_TOPIC = "CERT_EXCEPTION_TOPIC";
	String CERT_EXCEPTION_TAG = "CERT_EXCEPTION_TAG";

	/**查询批次数据入库消息*/
	String CERT_GETYIBU_MESSAGE_GROUP = "CERT_GETYIBU_MESSAGE_GROUP";
	String CERT_GETYIBU_MESSAGE_TOPIC = "CERT_GETYIBU_MESSAGE_TOPIC";
	String CERT_GETYIBU_MESSAGE_TAG = "CERT_GETYIBU_MESSAGE_TAG";
	/**--------------------------------- 应急中心 -------------------------------------*/

	/**--------------------------------- 北互金start -------------------------------------*/
	String BIFA_BORROW_STATUS_GROUP = "BIFA_BORROW_STATUS_GROUP";
	String BIFA_CREDITTENDERINFO_GROUP = "BIFA_CREDITTENDERINFO_GROUP";
	String BIFA_HJH_PLANINFO_GROUP = "BIFA_HJH_PLANINFO_GROUP";
	/**--------------------------------- 北互金end -------------------------------------*/

	/**--------------------------------- 平台公用RocketMQ常量 -------------------------------------*/
	// TOPIC：开户
	String HYJF_TOPIC = "HYJF_TOPIC";
	// TAG：开户--成功
	String OPEN_ACCOUNT_SUCCESS_GROUP = "OPEN_ACCOUNT_SUCCESS_GROUP";
	String OPEN_ACCOUNT_SUCCESS_TAG = "OPEN_ACCOUNT_SUCCESS_TAG";

	// TAG：用户信息--修改
	String USERINFO_CHANGE_GROUP = "USERINFO_CHANGE_GROUP";
	String USERINFO_CHANGE_TAG = "USERINFO_CHANGE_TAG";

	// TAG：发标--投资中
	String ISSUE_INVESTING_GROUP = "ISSUE_INVESTING_GROUP";
	String ISSUE_INVESTING_TAG = "ISSUE_INVESTING_TAG";
	/** TAG：发标--投资完成(满标) */
	String ISSUE_INVESTED_GROUP = "ISSUE_INVESTED_GROUP";
	String ISSUE_INVESTED_TAG = "ISSUE_INVESTED_TAG";

	// TAG：放款--放款成功
	String LOAN_SUCCESS_GROUP = "LOAN_SUCCESS_GROUP";
	String LOAN_SUCCESS_TAG = "LOAN_SUCCESS_TAG";

	// TAG：转让--转让成功
	String TRANSFER_SUCCESS_GROUP = "TRANSFER_SUCCESS_GROUP";
	String TRANSFER_SUCCESS_TAG = "TRANSFER_SUCCESS_TAG";

	// TAG：承接--单笔承接成功
	String UNDERTAKE_SINGLE_SUCCESS_GROUP = "UNDERTAKE_SINGLE_SUCCESS_GROUP";
	String UNDERTAKE_SINGLE_SUCCESS_TAG = "UNDERTAKE_SINGLE_SUCCESS_TAG";
	// TAG：承接--全部承接失败
	String UNDERTAKE_ALL_FAIL_GROUP = "UNDERTAKE_ALL_FAIL_GROUP";
	String UNDERTAKE_ALL_FAIL_TAG = "UNDERTAKE_ALL_FAIL_TAG";
	// TAG：承接--全部承接成功
	String UNDERTAKE_ALL_SUCCESS_GROUP = "UNDERTAKE_ALL_SUCCESS_GROUP";
	String UNDERTAKE_ALL_SUCCESS_TAG = "UNDERTAKE_ALL_SUCCESS_TAG";

	// TAG：还款--单笔还款成功
	String REPAY_SINGLE_SUCCESS_GROUP = "REPAY_SINGLE_SUCCESS_GROUP";
	String REPAY_SINGLE_SUCCESS_TAG = "REPAY_SINGLE_SUCCESS_TAG";
	// TAG：还款--全部还款成功
	String REPAY_ALL_SUCCESS_GROUP = "REPAY_ALL_SUCCESS_GROUP";
	String REPAY_ALL_SUCCESS_TAG = "REPAY_ALL_SUCCESS_TAG";
	// TAG：新增智投
	String HJHPLAN_ADD_TAG = "HJHPLAN_ADD_TAG";
	// TAG：修改智投 add by nxl
	String HJHPLAN_MODIFY_TAG = "HJHPLAN_ADD_MODIFY";
	// TAG：同步余额
	String SYNBALANCE_GROUP = "SYNBALANCE_GROUP";
	String SYNBALANCE_TOPIC = "SYNBALANCE_TOPIC";
	String SYNBALANCE_TAG = "SYNBALANCE_TAG";

	/**--------------------------------- 平台公用RocketMQ常量 -------------------------------------*/

	// 合规互金上报用MQ延迟发送等级（30s）
	int HG_REPORT_DELAY_LEVEL = 4;
	// 合规互金上报用MQ延迟发送等级（10s） add by nxl
	int HG_REPORT_DELAY_LEVEL_TEN = 3;


	// add by liuyang 20190415 wbs系统 标的信息推送MQ start
	String WBS_BORROW_INFO_TOPIC = "WBS_BORROW_INFO_TOPIC";
	String WBS_BORROW_INFO_TAG = "WBS_BORROW_INFO_TAG";
	// add by liuyang 20190415 wbs系统 标的信息推送MQ end

	/**--------------------------------- 业务流程配置start -------------------------------------*/
	String WORKFLOW_GROUP = "WORKFLOW_GROUP";
	String WORKFLOW_MESSAGE_TOPIC = "WORKFLOW_MESSAGE_TOPIC";
	/**--------------------------------- 业务流程配置end -------------------------------------*/

	/** 温金投修改借款人机构编号MQ */
	String WJT_BORROW_USER_MODIFY_GROUP = "WJT_BORROW_USER_MODIFY_GROUP";
	String WJT_BORROW_USER_MODIFY_TOPIC = "WJT_BORROW_USER_MODIFY_TOPIC";

	/**--------------------------------- 法大大合同状态 0:初始,1:生成成功,2,签署成功,3,下载成功start -------------------------------------*/
	int FDD_STATUS_INITIALIZE_ = 0;
	int FDD_STATUS_CREATE = 1;
	int FDD_STATUS_SIGN = 2;
	int FDD_STATUS_DOWNLOAD = 3;
	/**--------------------------------- 法大大合同状态 0:初始,1:生成成功,2,签署成功,3,下载成功end  -------------------------------------*/
	/** 兑吧扣积分后生成订单 */
	String DUIBA_ORDER_GROUP = "DUIBA_ORDER_GROUP";
	String DUIBA_ORDER_TOPIC = "DUIBA_ORDER_TOPIC";

}