/**
 * Description:银行存管接口相关常量
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.bank.util;

import java.io.Serializable;

public class BankCallConstant extends BankCallStatusConstant implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -7131277696965280498L;

	// 银行接口相关
	/** 银行接口页面 URL */
	public static final String BANK_PAGE_URL = "hyjf.bank.page.url";
	/** 银行接口联机 URL */
	public static final String BANK_ONLINE_URL = "hyjf.bank.online.url";
	/** 银行代码 */
	public static final String BANK_BANKCODE = "hyjf.bank.bankcode";
	/** 交易渠道 */
	public static final String BANK_COINST_CHANNEL = "hyjf.bank.coinst.channel";
	/** 接口默认版本号 */
	public static final String BANK_VERSION = "hyjf.bank.version";
	/** 平台机构代码 */
	public static final String BANK_INSTCODE = "hyjf.bank.instcode";
	/** 商户公钥文件地址 */
	public static final String BANK_PUB_KEY_PATH = "hyjf.bank.pubkey.path";
	/** 商户私钥文件地址 */
	public static final String BANK_PRI_KEY_PATH = "hyjf.bank.prikey.path";
	/** 私钥密码 */
	public static final String BANK_PRI_KEY_PASS = "hyjf.bank.prikey.pass";
	/** 页面返回 URL */
	public static final String BANK_PAGE_RETURN_URL = "hyjf.bank.page.return.url";
	/** 页面回调URL */
	public static final String BANK_PAGE_CALLBACK_URL = "hyjf.bank.page.callback.url";
	/** 忘记密码页面回调URL */
	public static final String BANK_PAGE_FORGOTPWD_URL = "hyjf.bank.page.forgotpwd.url";
	/** 联机回调URL */
	public static final String BANK_API_CALLBACK_URL = "hyjf.bank.api.callback.url";
	/** 服务费子账户号 */
	public static final String BANK_MERS_ACCOUNT = "hyjf.bank.mers.account";
	/** 红包子账户号 */
	public static final String BANK_MERRP_ACCOUNT = "hyjf.bank.merrp.account";
	/** hyjf-web URL */
	public static final String PROP_WEB_HOST = "hyjf.web.host";
	// 版本号
	/** 版本号:10 */
	public static final String VERSION_10 = "10";
	/** 版本号:20 */
	public static final String VERSION_20 = "20";
	/** 版本号:30 */
	public static final String VERSION_30 = "30";

	/** 存管渠道 app */
	public static final String CHANNEL_APP = "000001";
	/** 存管渠道 pc*/
	public static final String CHANNEL_PC = "000002";
	/** 存管渠道 wechat*/
	public static final String CHANNEL_WEI = "000003";
	/** 存管渠道柜面 */
	public static final String CHANNEL_OTHER = "000004";

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
	public static final String ID_TYPE_IDCARD = "01";// 身份证
	/** 证件类型 */
	public static final String ID_TYPE_COMCODE = "25";// 组织机构代码
	
	/** 选项 */
	public static final String OPTION_1 = "1";// 1为修改
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
	public static final String BANK_URL_BIND_CARD_PAGE = "/p2p/page/bindCardPage";
	public static final String BANK_URL_TERMS_AUTH_PAGE = "/p2p/page/termsAuthPage";
	public static final String BANK_URL_REFINANCE_FREEZE_PAGE = "/p2p/page/refinanceFreezePage";
	public static final String BANK_URL_ACCOUNT_OPEN_ENCRYPT_PAGE = "/p2p/page/accountOpenEncryptPage";
	public static final String BANK_URL_MOBILE_MODIFY_PAGE = "/p2p/page/mobileModifyPage";
	// 解卡页面(合规)
	public static final String BANK_URL_UNBIND_CARD_PAGE = "/p2p/page/unbindCardPage";
	/** 签约状态查询 1 自动投标签约*/
	public static final String QUERY_TYPE_1 = "1";
	/** 签约状态查询 2 自动债转签约*/
	public static final String QUERY_TYPE_2 = "2";

	/** 提现手续费 */
	public static final String BANK_FEE = "hyjf.bank.fee";

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
	/** 后台交易类型 线下充值 7610 */
	public static final String TRANS_TYPE_7610 = "7610";
	/** 后台交易类型 线下充值 7611 */
	public static final String TRANS_TYPE_7611 = "7611";
	/** 后台交易类型 线下充值 7612 */
	public static final String TRANS_TYPE_7612 = "7612";
	/** 后台交易类型 线下充值 7613 */
	public static final String TRANS_TYPE_7613 = "7613";

	
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
	 * 2.5.19	多合一授权
	 */
	public static final String TXCODE_TERMS_AUTH_PAGE = "termsAuthPage";
	/** 开户角色属性   1：出借角色2：借款角色3：代偿角色*/
	public static final String ACCOUNT_USER_IDENTITY_3 = "3";
	public static final String ACCOUNT_USER_IDENTITY_2 = "2";
	public static final String ACCOUNT_USER_IDENTITY_1 = "1";

	/** 圈存 */
	public static final String BANK_URL_TRANSFERENCE = "/p2p/page/creditForLoadPage";
	/**
	 * 圈存
	 */
	public static final String TXCODE_CREDIT_FOR_LOADPAGE = "creditForLoadPage";

	/**
	 * 圈提
	 */
	public static final String BANK_URL_CREDIT_FOR_UNLOAD_PAGE = "/p2p/page/creditForUnloadPage";

	/** 圈提 */
	public static final String TXCODE_CREDIT_FOR_UNLOAD_PAGE = "creditForUnloadPage";


	/**
	 * 密码重置，页面浏览器方式（合规改造）
	 * add by yangchangwei 20180816
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/passwordResetPage
	 */
	public static final String TXCODE_PASSWORD_RESET_PAGE = "passwordResetPage";


	public static final String TXCODE_PASSWORD_SET_PAGE = "passwordSetPage";

	public static final String BANK_URL_PASSWORDRESETPAGE = "/p2p/page/passwordResetPage";

	/**（加密）开户设密页面*/
	public static final String TXCODE_ACCOUNT_OPEN_ENCRYPT_PAGE = "accountOpenEncryptPage";
	/**解绑银行卡*/
	public  static final String TXCODE_ACCOUNT_UNBINDCARD_PAGE = "unbindCardPage";

	/**
	 * 修改银行预留手机号
	 */
	public  static final String TXCODE_MOBILE_MODIFY_PAGE = "mobileModifyPage";

	/**
	 * 查询银行预留手机号
	 */
	public  static final String TXCODE_MOBILE_MODIFY_QUERY = "mobileQueryByAccount";
}
