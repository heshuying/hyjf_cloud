/**
 * Description:银行存管接口相关常量
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author:
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.common.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ClientConstants implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7131277696965280498L;

	public static final String FAIL = "false";

	public static final String ISSUCCESS = "1";
	/**
	 * web端
	 */
	public static final int WEB_CLIENT = 0;

	/**
	 * 微信端
	 */
	public static final int WECHAT_CLIENT = 1;

	/**
	 * APP 安卓端
	 */
	public static final int APP_CLIENT = 2;

	/**
	 * APP IOS端
	 */
	public static final int APP_CLIENT_IOS = 3;

	public static final int API_CLIENT = 3;

	/**
	 * 用户授权自动债转
	 */
	public static final String CREDIT_AUTO_TYPE = "1";

	/**
	 * 用户授权自动出借
	 */
	public static final String INVES_AUTO_TYPE = "0";

	/** 自动投标签约 签约状态查询 */
	public static final String QUERY_TYPE_1 = "1";

	/** 自动债转签约签约状态查询 */
	public static final String QUERY_TYPE_2 = "2";

	/**
	 * 自动债转URL_TYPE
	 */
	public static final String CREDIT_URL_TYPE = "1";

	/**
	 * 自动出借URL_TYPE
	 */
	public static final String INVES_URL_TYPE = "2";

	/** 存管渠道 app */
	public static final String CHANNEL_APP = "000001";

	/** 存管渠道 pc*/
	public static final String CHANNEL_PC = "000002";

	/** 存管渠道 wechat*/
	public static final String CHANNEL_WEI = "000003";

	/** 存管渠道柜面 */
	public static final String CHANNEL_OTHER = "000004";
	/**
	 * url header获取
	 */
	public static final Map<Integer,String> CLIENT_HEADER_MAP = new HashMap<Integer, String>(){{
		put(WEB_CLIENT,"/web");
		put(WECHAT_CLIENT,"/wechat");
		put(APP_CLIENT,"/app");
		put(API_CLIENT,"/api");
	}};

	// 版本号
	/** 版本号:10 */
	public static final String VERSION_10 = "10";
	/** 版本号:20 */
	public static final String VERSION_20 = "20";
	/** 版本号:30 */
	public static final String VERSION_30 = "30";

	/** 账户用途 */
	/** 普通账户 */
	public static final String ACCOUNT_USE_COMMON = "00000";
	/** 红包账户 */
	public static final String ACCOUNT_USE_RED_PACKET = "10000";
	/** 手续费账户 */
	public static final String ACCOUNT_USE_SERVICE_FEE = "01000";
	/** 担保账户 */
	public static final String ACCOUNT_USE_GUARANTEE = "00100";

	/** 证件类型 */
	public static final String ID_TYPE_IDCARD = "01";
	/** 证件类型 */
	public static final String ID_TYPE_COMCODE = "25";
	
	/** 选项 */
	public static final String OPTION_1 = "1";
	/** 交易币种 */
	public static final String CURRENCY_TYPE_RMB = "156";

	/** 银行详细url */
	public static final String BANK_URL_MOBILE = "/p2p/page/mobile";
	public static final String BANK_URL_PASSWORDSET = "/p2p/page/passwordset";
	public static final String BANK_URL_MOBILEMODIFY = "/p2p/page/mobileModify";
	public static final String BANK_URL_MOBILE_PLUS = "/p2p/page/mobile/plus";
	public static final String BANK_URL_MOBILE_BIDAPPLY = "/p2p/page/bidapply";
	public static final String BANK_URL_MOBILE_WITHDRAW = "/p2p/page/withdraw";
	public static final String BANK_URL_MOBILE_CREDITINVEST = "/p2p/page/creditInvest";
	public static final String BANK_URL_AUTH_MODIFY = "/p2p/page/authModify";
	public static final String BANK_URL_TRUSTEE_PAY = "/p2p/page/trusteePay";
	public static final String BANK_URL_ACCOUNT_OPEN_PAGE = "/p2p/page/accountOpenPage";
	public static final String BANK_URL_DIRECT_RECHARGE_PAGE = "/p2p/page/directRechargePage";
	public static final String BANK_URL_PAYMENT_AUTH_PAGE = "/p2p/page/paymentAuthPage";
	public static final String BANK_URL_REPAY_AUTH_PAGE = "/p2p/page/repayAuthPage";

	/** 提现手续费 */
	public static final String BANK_FEE = "1.00";

	/** 后台交易类型 线下充值 7820 */
    public static final String TRANS_TYPE_7820 = "7820";
    /** 后台交易类型 线下充值 7617 */
    public static final String TRANS_TYPE_7617 = "7617";
	/** 后台交易类型 线下充值 7821 */
	public static final String TRANS_TYPE_7821 = "7821";
	/** 后台交易类型 线下充值 7823 */
	public static final String TRANS_TYPE_7823 = "7823";
	/** 后台交易类型 线下充值 7826 */
	public static final String TRANS_TYPE_7826 = "7826";
	/** 后台交易类型 线下充值 7938 */
	public static final String TRANS_TYPE_7938 = "7938";
	/** 后台交易类型 线下充值 7939 */
	public static final String TRANS_TYPE_7939 = "7939";

	
	/** 付息方式 */
	/**0 到期与本金一期归还*/
	public static final String DEBT_INTTYPE_EXPIREDATE = "0";
	/**1 每月固定日期支付*/
	public static final String DEBT_INTTYPE_FIXEDDATE= "1";
	/**2每月不确定日期支付*/
	public static final String DEBT_INTTYPE_UNCERTAINDATE = "2";
	
	/**出借是否冻结 0不冻结*/
	public static final String DEBT_FRZFLAG_UNFREEZE = "0";
	/**出借是否冻结 1冻结 */
	public static final String DEBT_FRZFLAG_FREEZE = "1";
	
	/**批次交易种类 0所有交易*/
	public static final String DEBT_BATCH_TYPE_ALL = "0";
	/**批次交易种类1成功交易 */
	public static final String DEBT_BATCH_TYPE_SUCCESS = "1";
	/**批次交易种类2失败交易 */
	public static final String DEBT_BATCH_TYPE_FAIL = "2";
	/**批次交易种类9合法性校验失败交易 */
	public static final String DEBT_BATCH_TYPE_VERIFYFAIL = "9";
	
	/**出借人债权明细 0-所有债权*/
	public static final String ALL_INVESTOR_DEBT = "0";
	/**出借人债权明细 1-有效债权（投标成功，且本息尚未返还完成） */
	public static final String EFFECTIVE_INVESTOR_DEBT = "1";
	/** 受托人电子帐号 */
	public static final String PARAM_RECEIPTACCOUNTID = "receiptAccountId";

    // 页面调用不返回成功的结果 返回值都在这里面
    public static final String PARAM_FRONTPARAMS = "frontParams";

    /**操作成功后跳转地址*/
    public static final String PARAM_SUCCESSFUL_URL = "successfulUrl";
	
    /**性别  M  男性 F  女性*/
    public static final String PARAM_GENDER = "gender";

    /**是否需要开通动账短信通知 0：不需要  1或空：需要*/
    public static final String PARAM_SMSFLAG = "smsFlag";

    /** 身份属性  1：出借角色2：借款角色3：代偿角色*/
    public static final String PARAM_IDENTITY = "identity";

    /**商户名称*/
    public static final String PARAM_COINSTNAME = "coinstName";
    /**签约取消日期*/
    public static final String PARAM_CANCELTIME = "cancelDate";
    /**签约取消时间*/
    public static final String PARAM_CANCELDATE = "cancelTime";
    /**3.12.客户授权功能查询 */
    public static final String TXCODE_TERMS_AUTH_QUERY = "termsAuthQuery";
	/**
	 * 2.4.8.出借人自动债权转让签约增强
	 */
	public static final String TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUSS = "autoCreditInvestAuthPlus";

	/**
	 * .页面充值
	 */
	public static final String TXCODE_DIRECT_RECHARGE_PAGE = "directRechargePage";
	/**
	 * 3.8.缴费授权
	 */
	public static final String TXCODE_PAYMENT_AUTH_PAGE = "paymentAuthPage";


	/**
	 * 2.4.4.出借人自动投标签约增强
	 */
	public static final String TXCODE_AUTO_BID_AUTH_PLUS = "autoBidAuthPlus";


	/**3.1.开户页面*/
	public static final String TXCODE_ACCOUNT_OPEN_PAGE = "accountOpenPage";

	/** 还款授权 */
	public static final String TXCODE_REPAY_AUTH_PAGE = "repayAuthPage";

	/**
	 * 出借人签约状态查询
	 */
	public static final String TXCODE_CREDIT_AUTH_QUERY = "creditAuthQuery";

	/**回调端地址前缀*/
	public static final String CLIENT_HEADER_PC = "web";
	public static final String CLIENT_HEADER_APP = "app";
	public static final String CLIENT_HEADER_API = "api";
	public static final String CLIENT_HEADER_WX = "wechat";


	/**
	 * 出借人投标申请查询
	 */
	public static final String TXCODE_BID_APPLY_QUERY = "bidApplyQuery";

	// 江西银行绑卡状态
	/** 已绑卡 */
	public static final Integer BANK_BINDCARD_STATUS_SUCCESS = 1;
	/** 未绑卡 */
	public static final Integer BANK_BINDCARD_STATUS_FAIL = 0;

	/**
	 * 交易密码
	 */
	public static final String SETPASSWORD_ACTION = "/bank/user/transpassword/setPassword";
	public static final String RESETPASSWORD_ACTION = "/bank/user/transpassword/resetPassword";

	/**
	 * vip 初始化跳转路径
	 */
	public static final String USER_VIP_DETAIL_ACTIVE_INIT = "/vip/userVipDetailInit";

	/**
	 * vip等级显示图片
	 */
	public static final String APPLY_INIT = "/vip/apply/init";

	/**
	 * 绑卡Url
	 */
	public static final String USER_BIND_CARD = "/bindCard/bindCard";

	public static final String REQUEST_BINDCARDPAGE = "/bank/user/bindCardPage/bindCardPage";

	public static final String BINDCARD_ACTION = "/user/bankCard/bind";

	public static final String BANKOPEN_OPEN_ACTION = "/user/open";

	/** 风险测评 */
	public static final String USER_RISKTEST = "/user/riskTest?evaluation=";

	public static final String USER_AUTH_INVES_ACTION = "/bank/user/auto/userAuthInves";

	public static final String PAYMENT_AUTH_ACTION = "/bank/user/paymentAuth/authPage";

	/** 企业用户开户 */
	public static final String ENTERPRISEGUIDE = "/enterpriseguide";

	/** 我的奖励Url */
	public static final String REWARD_URL = "/user/reward";

	//-------------------------------活动---------------------------------
	//活动编号不能为空
	public final static String ACTIVITYID_IS_NULL="活动编号不能为空";
	//该活动不存在
	public final static String ACTIVITY_ISNULL="该活动不存在";
	//该活动不存在
	public final static String ACTIVITY_TIME_NOT_START="该活动还未开始";
	//该活动已结束
	public final static String ACTIVITY_TIME_END="您来晚了，活动已过期~~";
	//该活动已结束
	public final static String PLATFORM_LIMIT="本活动限***端参与";
	//该活动用户已参与
	public final static String ACTIVITY_LIMIT="该活动用户已参与";

	/** 提现规则  路径 */
	public static final String WITHDRAW_RULE_PATH = "/withdraw/withdraw";

	/** 获取提现信息  */
	public static final String GET_WITHDRAW_INFO_MAPPING = "/getInfoAction";

	/** @RequestMapping值 */
	public static final String GET_WITHDRAW_RULE_MAPPING = "/getRule";

	/** 发现页 运营报告*/
	public static final String FIND_REPORTS = "/find/report";
}
