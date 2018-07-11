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
	String GRANT_COUPON_TOPIC = "COUPON_TOPIC";

	/** 注册送188红包 */
	String REGISTER_COUPON_TOPIC = "REGISTER_COUPON_TOPIC";
	String REGISTER_COUPON_TAG = "REGISTER_COUPON_TAG";
	/** 投之家送2张加息券 */
	String TZJ_REGISTER_INTEREST_TAG = "TZJ_REGISTER_INTEREST_TAG";


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
	 * 资产推送发送mq
	 */
	String ASSET_PUSH_GROUP = "ASSET_PUSH_GROUP";
	String ASSET_PUST_TOPIC = "ASSET_PUSH_TOPIC";

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
	 * 网站收支数据统计
	 */
	String ACCOUNT_WEB_LIST_GROUP = "ACCOUNT_WEB_LIST_GROUP";
	String ACCOUNT_WEB_LIST_TOPIC = "ACCOUNT_WEB_LIST_TOPIC";


	/**
	 * 放款，还款请求，放款，还款
	 */
	String BORROW_GROUP = "BORROW_GROUP";

	String BORROW_REPAY_REQUEST_TOPIC = "BORROW_REPAY_REQUEST_TOPIC";
	String BORROW_REPAY_ZT_RESULT_TOPIC = "BORROW_REPAY_ZT_RESULT_TOPIC";
	String BORROW_REPAY_PLAN_RESULT_TOPIC = "BORROW_REPAY_PLAN_RESULT_TOPIC";

	String BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC = "BORROW_REALTIMELOAN_ZT_REQUEST_TOPIC";
	String BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC = "BORROW_REALTIMELOAN_PLAN_REQUEST_TOPIC";

	/**
	 * 法大大
	 */
	String FDD_GROUP = "FDD_GROUP";
	String FDD_TOPIC = "FDD_TOPIC";

	/**
	 * 法大大CA认证
	 */
	String FDD_CERTIFICATE_AUTHORITY_GROUP = "FDD_CERTIFICATE_AUTHORITY_GROUP";
	String FDD_CERTIFICATE_AUTHORITY_TOPIC = "FDD_CERTIFICATE_AUTHORITY_TOPIC";

	/**
	 * crm开户信息推送
	 */
	 String CRM_ROUTINGKEY_BANCKOPEN_GROUP = "CRM_ROUTINGKEY_BANCKOPEN_GROUP";
	String CRM_ROUTINGKEY_BANCKOPEN_TOPIC="CRM_ROUTINGKEY_BANCKOPEN_TOPIC";

	/**法大大生成合同处理*/
	String FDD_GENERATE_CONTRACT_TAG = "FDD_GENERATE_CONTRACT_TAG";
	/**法大大自动签署处理*/
	String FDD_AUTO_SIGN_TAG = "FDD_AUTO_SIGN_TAG";
	/**法大大下载脱敏处理*/
	String FDD_DOWNPDF_AND_DESSENSITIZATION_TAG = "FDD_DOWNPDF_AND_DESSENSITIZATION_TAG";
	/**
	 * 汇计划退出
	 */
	String HJH_QUIT = "HJH_QUIT";
	String HJH_QUIT_TOPIC = "HJH_QUIT_TOPIC";

	/**
	 * 汇计划自动计算公允价值
	 */
	String HJH_CALCULATE_FAIR_VALUE_GROUP = "HJH_CALCULATE_FAIR_VALUE_GROUP";
	String HJH_CALCULATE_FAIR_VALUE_TOPIC = "HJH_CALCULATE_FAIR_VALUE_TOPIC";

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
	 * 投之家日报表
	 */
	String STATISTICS_TZJ_GROUP = "STATISTICS_TZJ_GROUP";
	String STATISTICS_TZJ_TOPIC = "STATISTICS_TZJ_TOPIC";

	/**
	 * 投之家渠道报表
	 */
	String STATISTICS_TZJ_UTM_GROUP = "STATISTICS_TZJ_UTM_GROUP";
	String STATISTICS_TZJ_UTM_TOPIC = "STATISTICS_TZJ_UTM_TOPIC";

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


	/**
	 * 网站累计投资追加    修改mongodb运营数据
	 */
	String STATISTICS_CALCULATE_INVEST_INTEREST_GROUP = "STATISTICS_CALCULATE_INVEST_INTEREST_GROUP";
	String STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC = "STATISTICS_CALCULATE_INVEST_INTEREST_TOPIC";

	/**
	 * 标的关联计划
	 */
	String AUTO_ISSUE_BORROW_JOIN_PLAN_GROUP = "AUTO_ISSUE_BORROW_JOIN_PLAN_GROUP";
	String AUTO_ISSUE_BORROW_JOIN_PLAN_TOPIC = "AUTO_ISSUE_BORROW_JOIN_PLAN_TOPIC";

}