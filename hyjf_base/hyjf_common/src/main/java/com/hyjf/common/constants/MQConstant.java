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
	 * 测试 group topic todo 暂时使用
	 */
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
	 * 发送app push group topic
	 */
	String APP_MESSAGE_GROUP = "APP_MESSAGE_GROUP";
	String APP_MESSAGE_TOPIC = "APP_MESSAGE_TOPIC";
	/** ------------------------------ 消息中心 end ------------------------------ */




	/** ------------------------------ 优惠券相关 start ------------------------------ */

	/**
	 * 优惠券发放 - 后台批量发放
	 */
	String BATCH_GRANT_COUPON_GROUP = "BATCH_GRANT_COUPON_GROUP";
	String BATCH_GRANT_COUPON_TOPIC = "BATCH_GRANT_COUPON_TOPIC";

	/**
	 * 优惠券发放
	 */
	String GRANT_COUPON_GROUP = "COUPON_GROUP";
	String GRANT_COUPON_TOPIC = "COUPON_TOPIC";

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

	/** ------------------------------- 优惠券相关 end ------------------------- end */




	/** ------------------------------ 数据统计 start ------------------------------ */

	/** 每月统计运营数据 */
	String STATISTICS_OPERATION_REPORT_GROUP = "STATISTICS_OPERATION_REPORT_GROUP";
	String STATISTICS_OPERATION_REPORT_TOPIC = "STATISTICS_OPERATION_REPORT_TOPIC";

	/** 借款用户运营数据 */
	String BORROW_USER_STATISTICS_GROUP = "BORROW_USER_STATISTICS_GROUP";
	String BORROW_USER_STATISTICS_TOPIC = "BORROW_USER_STATISTICS_TOPIC";

	/**
	 * 网站累计投资追加 修改mongodb运营数据
	 */
	String STATISTICS_CALCULATE_INVEST_INTEREST_GROUP = "STATISTICS_CALCULATE_INVEST_INTEREST_GROUP";
	String STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC = "STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC";

	/**
	 * 新PC渠道统计
	 */
	String PC_CHANNEL_STATISTICS_GROUP = "PC_CHANNEL_STATISTICS_GROUP";
	String PC_CHANNEL_STATISTICS_TOPIC = "PC_CHANNEL_STATISTICS_TOPIC";

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
	 * 资产推送发送mq
	 */
	String ASSET_PUSH_GROUP = "ASSET_PUSH_GROUP";
	String ASSET_PUST_TOPIC = "ASSET_PUSH_TOPIC";

	/**
	 * 汇计划退出
	 */
	String HJH_QUIT_GROUP = "HJH_QUIT_GROUP";
	String HJH_QUIT_TOPIC = "HJH_QUIT_TOPIC";

	/**
	 * 汇计划自动计算公允价值
	 */
	String HJH_CALCULATE_FAIR_VALUE_GROUP = "HJH_CALCULATE_FAIR_VALUE_GROUP";
	String HJH_CALCULATE_FAIR_VALUE_TOPIC = "HJH_CALCULATE_FAIR_VALUE_TOPIC";

	/** ------------------------------ 汇计划 end ------------------------------ */




	/** ------------------------------ 标的自动化 start ------------------------------ */

	/**
	 * 自动备案mq
	 */
	String BORROW_RECORD_GROUP = "BORROW_RECORD_GROUP";
	String BORROW_RECORD_TOPIC = "BORROW_RECORD_TOPIC";

	/**
	 * 自动初审mq
	 */
	String BORROW_PREAUDIT_GROUP = "BORROW_PREAUDIT_GROUP";
	String BORROW_PREAUDIT_TOPIC = "BORROW_PREAUDIT_TOPIC";

	/**
	 * 自动关联计划
	 */
	String HYJF_BORROW_ISSUE_GROUP = "HYJF_BORROW_ISSUE_GROUP";
	String HYJF_BORROW_ISSUE_TOPIC = "HYJF_BORROW_ISSUE_TOPIC";

	/**
	 * 自动录标
	 */
	String HJH_AUTO_ISSUERECOVER_GROUP = "HJH_AUTO_ISSUERECOVER_GROUP";
	String HJH_AUTO_ISSUERECOVER_TOPIC = "HJH_AUTO_ISSUERECOVER_TOPIC";

	/** 自动备案 */
	String ROCKETMQ_BORROW_RECORD_GROUP = "HYJF-ROCKETMQ-BORROW-RECORD-GROUP";
	String ROCKETMQ_BORROW_RECORD_TOPIC = "HYJF-ROCKETMQ-BORROW-RECORD-TOPIC";

	/** 自动初审 */
	String ROCKETMQ_BORROW_PREAUDIT_GROUP = "HYJF-ROCKETMQ-BORROW-PREAUDIT-GROUP";
	String ROCKETMQ_BORROW_PREAUDIT_TOPIC = "HYJF-ROCKETMQ-BORROW-PREAUDIT-TOPIC";

	/** 自动关联计划 */
	String ROCKETMQ_BORROW_ISSUE_GROUP = "HYJF-ROCKETMQ-BORROW-ISSUE-GROUP";
	String ROCKETMQ_BORROW_ISSUE_TOPIC = "HYJF-ROCKETMQ-BORROW-ISSUE-TOPIC";

	/** 自动审核保证金 */
	String ROCKETMQ_BORROW_BAIL_GROUP = "HYJF-ROCKETMQ-BORROW-BAIL-GROUP";
	String ROCKETMQ_BORROW_BAIL_TOPIC = "HYJF-ROCKETMQ-BORROW-BAIL-TOPIC";

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
	/** 投之家送2张加息券 */
	String TZJ_REGISTER_INTEREST_TAG = "TZJ_REGISTER_INTEREST_TAG";

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
     * crm投资信息推送
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
}