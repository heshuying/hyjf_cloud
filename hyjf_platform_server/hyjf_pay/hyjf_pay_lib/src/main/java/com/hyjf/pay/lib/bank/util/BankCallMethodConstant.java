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

public class BankCallMethodConstant implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3572381392739370415L;
	// 消息类型
	/**
	 * 个人开户,页面浏览器方式 
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile
	 */
	public static final String TXCODE_ACCOUNT_OPEN = "accountOpen";
	/**
	 * 绑定银行卡,页面浏览器方式 地址：
	 * https://xxxx.credit2go.cn/escrow/p2p/page/mobile
	 */
	public static final String TXCODE_CARD_BIND = "cardBind";
	/**
	 * 解绑银行卡 接口调用
	 */
	public static final String TXCODE_CARD_UNBIND = "cardUnbind";
	/**
	 * 密码设置,页面浏览器方式
	 *  地址：https://access.credit2go.cn/escrow/p2p/page/passwordset
	 */
	public static final String TXCODE_PASSWORD_SET = "passwordSet";
	/**
	 * 密码重置,页面浏览器方式 
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile
	 */
	public static final String TXCODE_PASSWORD_RESET = "passwordReset";
	/**
	 * 电子账户手机号修改,页面浏览器方式
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobileModify
	 */
	public static final String TXCODE_MOBILE_MODIFY = "mobileModify";
	/**
	 * 请求发送短信验证码
	 */
	public static final String TXCODE_SMSCODE_APPLY = "smsCodeApply";
	/**
	 * 个人开户增强
	 */
	public static final String TXCODE_ACCOUNT_OPEN_PLUS = "accountOpenPlus";
	
	/**
     * 静默开户(免短信开户接口)
     */
    public static final String TXCODE_FREE_SMS_ACCOUNT_OPEN = "freeSmsAccountOpen";
	/**
	 * 绑定银行卡增强
	 */
	public static final String TXCODE_CARD_BIND_PLUS = "cardBindPlus";
	/**
	 * 电子账户手机号修改增强
	 */
	public static final String TXCODE_MOBILE_MODIFY_PLUS = "mobileModifyPlus";
	/**
	 * 密码重置增强,页面浏览器方式 
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile/plus
	 */
	public static final String TXCODE_PASSWORD_RESET_PLUS = "passwordResetPlus";
	/**
	 * 绑定卡到电子账户充值,页面浏览器方式 
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile
	 */
	public static final String TXCODE_DIRECT_RECHARGE = "directRecharge";
	/**
	 * 联机绑定卡到电子账户充值 
	 */
	public static final String TXCODE_DIRECT_RECHARGE_ONLINE = "directRechargeOnline";
	/**
	 * 绑定卡到电子账户充值增强,页面浏览器方式
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile/plus
	 */
	public static final String TXCODE_DIRECT_RECHARGE_PLUS = "directRechargePlus";
	/**
	 * 借款人标的登记
	 */
	public static final String TXCODE_DEBT_REGISTER = "debtRegister";
	/**
	 * 借款人标的撤销
	 */
	public static final String TXCODE_DEBT_REGISTER_CANCEL = "debtRegisterCancel";
	/**
	 * 出借人投标申请,页面浏览器方式
	 * https://xxxx.credit2go.cn/escrow/p2p/page/bidapply
	 */
	public static final String TXCODE_BID_APPLY = "bidApply";
	/**
	 * 出借人自动投标签约,页面浏览器方式
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile
	 */
	public static final String TXCODE_AUTOBID_AUTH = "autoBidAuth";
	/**
	 * 撤销自动投标签约
	 */
	public static final String TXCODE_AUTOBID_AUTH_CANCEL = "autoBidAuthCancel";
	/**
	 * 自动投标申请
	 */
	public static final String TXCODE_BIDAUTO_APPLY = "bidAutoApply";
	/**
	 * 满标自动放款
	 */
	public static final String TXCODE_AUTOLEND_PAY = "autoLendPay";
	/**
	 * 满标自动放款查询
	 */
	public static final String TXCODE_AUTOLEND_PAY_QUERY = "autoLendPayQuery";
	/**
	 * 投标申请撤销
	 */
	public static final String TXCODE_BID_CANCEL = "bidCancel";
	/**
	 * 出借人自动债权转让签约,页面浏览器方式
	 *  地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile
	 */
	public static final String TXCODE_AUTO_CREDIT_INVEST_AUTH = "autoCreditInvestAuth";
	/**
	 * 撤销自动债权转让签约
	 */
	public static final String TXCODE_AUTO_CREDIT_INVEST_AUTH_CANCEL = "autoCreditInvestAuthCancel";
	/**
	 * 出借人自动投标签约增强,页面浏览器方式
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile/plus
	 */
	public static final String TXCODE_AUTOBID_AUTH_PLUS = "autoBidAuthPlus";
	/**
	 * 出借人自动债权转让签约增强,页面浏览器方式
	 * 地址：https://xxxx.credit2go.cn/escrow/p2p/page/mobile/plus
	 */
	public static final String TXCODE_AUTO_CREDIT_INVEST_AUTH_PLUS = "autoCreditInvestAuthPlus";
	/**
	 * 放款
	 */
	public static final String TXCODE_LEND_PAY = "lendPay";
	/**
	 * 还款
	 */
	public static final String TXCODE_REPAY = "repay";
	/**
	 * 提现,页面浏览器方式 地址：https://xxxx.credit2go.cn/escrow/p2p/page/withdraw
	 */
	public static final String TXCODE_WITHDRAW = "withdraw";
	
	/**
     * 免密提现
     */
    public static final String TXCODE_AGREE_WITHDRAW = "agreeWithdraw";
    /**
     * 四合一授权(页面)
     */
    public static final String TXCODE_USER_AUTH_MODIFY = "authModify";
    /**
     * 四合一授权(联机)
     */
    public static final String TXCODE_USER_AUTH_BACK = "termsAuth";

	/**
	 * 出借人购买债权,页面浏览器方式 地址：https://xxxx.credit2go.cn/escrow/p2p/page/creditInvest
	 */
	public static final String TXCODE_CREDITINVEST = "creditInvest";
	/**
	 * 红包发放
	 */
	public static final String TXCODE_VOUCHER_PAY = "voucherPay";
	/**
	 * 红包发放撤销
	 */
	public static final String TXCODE_VOUCHER_PAY_CANCEL = "voucherPayCancel";
	/**
	 * 融资人还担保账户垫款
	 */
	public static final String TXCODE_REPAY_BAIL = "repayBail";
	/**
	 * 结束债权
	 */
	public static final String TXCODE_CREDIT_END = "creditEnd";
	/**
	 * 放款或还款撤销
	 */
	public static final String TXCODE_PAY_CANCEL = "payCancel";
	/**
	 * 批次放款
	 */
	public static final String TXCODE_BATCH_LEND_PAY = "batchLendPay";
	/**
	 * 批次还款
	 */
	public static final String TXCODE_BATCH_REPAY = "batchRepay";
	/**
	 * 批次代偿
	 */
	public static final String TXCODE_BATCH_SUBST_REPAY = "batchSubstRepay";
	/**
	 * 批次融资人还担保账户垫款
	 */
	public static final String TXCODE_BATCH_REPAY_BAIL = "batchRepayBail";
	/**
	 * 批次结束债权
	 */
	public static final String TXCODE_BATCH_CREDIT_END = "batchCreditEnd";
	/**
	 * 批次撤销
	 */
	public static final String TXCODE_BATCH_CANCEL = "batchCancel";
	/**
	 * 批次出借人购买债权
	 */
	public static final String TXCODE_BATCH_CREDIT_INVEST = "batchCreditInvest";
	/**
	 * 批次担保账户代偿
	 */
	public static final String TXCODE_BATCH_BAIL_REPAY = "batchBailRepay";
	/**
	 * 还款申请冻结资金
	 */
	public static final String TXCODE_BALANCE_FREEZE = "balanceFreeze";
	/**
	 * 还款申请撤销资金解冻
	 */
	public static final String TXCODE_BALANCE_UNFREEZE = "balanceUnfreeze";
	/**
	 * 代偿冻结
	 */
	public static final String TXCODE_REFINANCE_FREEZE_PAGE = "refinanceFreezePage";
	/**
	 * 电子账户余额查询
	 */
	public static final String TXCODE_BALANCE_QUERY = "balanceQuery";
	/**
	 * 新版查询接口修改2017-12-12
	 * 电子账户资金交易明细查询
	 */
	public static final String TXCODE_ACCOUNT_DETAILS_QUERY = "accountDetailsQuery2";
	/**
	 * 出借人债权明细查询
	 */
	public static final String TXCODE_OFFLINE_RECHARGE_DETAILS_QUERY= "offlineRechargeDetailsQuery";
	/**
	 * 出借人债权明细查询
	 */
	public static final String TXCODE_CREDIT_DETAILS_QUERY = "creditDetailsQuery";
	/**
	 * 绑卡关系查询
	 */
	public static final String TXCODE_CARD_BIND_DETAILS_QUERY = "cardBindDetailsQuery";
	/**
	 * 按证件号查询电子账号
	 */
	public static final String TXCODE_ACCOUNTID_QUERY = "accountIdQuery";
	/**
	 * 借款人标的信息查询
	 */
	public static final String TXCODE_DEBT_DETAILS_QUERY = "debtDetailsQuery";
	/**
	 * 电子账户手机号查询
	 */
	public static final String TXCODE_MOBILE_MAINTAINACE = "mobileMaintainace";
	/**
	 * 按手机号查询电子账号信息
	 */
	public static final String TXCODE_ACCOUNT_QUERY_BY_MOBILE = "accountQueryByMobile";

	/**
	 * 按手机号查询电子账号信息增强
	 */
	public static final String TXCODE_ACCOUNT_QUERY_BY_MOBILE_PLUS = "accountQueryByMobilePlus";
	/**
	 * 查询交易状态
	 */
	public static final String TXCODE_TRANSACTION_STATUS_QUERY = "transactionStatusQuery";
	/**
	 * 查询批次状态
	 */
	public static final String TXCODE_BATCH_QUERY = "batchQuery";
	/**
	 * 查询批次交易明细状态
	 */
	public static final String TXCODE_BATCH_DETAILS_QUERY = "batchDetailsQuery";
	/**
	 * 出借人购买债权查询
	 */
	public static final String TXCODE_CREDIT_INVEST_QUERY = "creditInvestQuery";
	/**
	 * 出借人投标申请查询
	 */
	public static final String TXCODE_BID_APPLY_QUERY = "bidApplyQuery";
	/**
	 * 出借人签约状态查询
	 */
	public static final String TXCODE_CREDIT_AUTH_QUERY = "creditAuthQuery";
	/**
	 * 企业账户查询
	 */
	public static final String TXCODE_CORPRATION_QUERY = "corprationQuery";
	/**
	 * 账户资金冻结明细查询
	 */
	public static final String TXCODE_FREEZE_DETAILS_QUERY = "freezeDetailsQuery";
	/**
	 * 电子账户密码是否设置查询
	 */
	public static final String TXCODE_PASSWORD_SET_QUERY = "passwordSetQuery";
	/**
	 * 单笔还款申请冻结查询
	 */
	public static final String TXCODE_BALANCE_FREEZE_QUERY = "balanceFreezeQuery";
	/**
	 * 联行号查询
	 */
	public static final String TXCODE_PAY_ALLIANCE_CODE_QUERY = "payAllianceCodeQuery";
	/**
	 * 单笔资金类业务交易查询
	 */
	public static final String TXCODE_FUND_TRANS_QUERY = "fundTransQuery";
	/**
	 * 线下充值回调
	 */
	public static final String TXCODE_OFFLINE_RECHARGE_CALL = "offlineRechargeCall";
	/**
	 * 手续费分账
	 */
	public static final String TXCODE_FEE_SHARE = "feeShare";
	// 其他
	/** GET */
	public static final String GET = "get";
	/** SET */
	public static final String SET = "set";
	
	/**借款人受托支付申请*/
	public static final String TXCODE_TRUSTEE_PAY = "trusteePay";
	
	/**借款人受托支付申请 查询*/
    public static final String TXCODE_TRUSTEE_PAY_QUERY = "trusteePayQuery";
	/**
	 * 自动购买债权
	 */
	public static final String TXCODE_CREDIT_AUTO_INVEST = "creditAutoInvest";


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

    public static final String TXCODE_AUTO_PAY_AUTH_CANCEL = "paymentAuthCancel";
    public static final String TXCODE_AUTO_REPAY_AUTH_CANCEL = "repayAuthCancel";

	/**2.2.5绑定银行卡（页面）*/
	public static final String TXCODE_BIND_CARD_PAGE = "bindCardPage";
	/**解绑银行卡*/
	public  static final String TXCODE_ACCOUNT_UNBINDCARD_PAGE = "unbindCardPage";
}
