package com.hyjf.cs.trade.bean;

public class AuthBean extends BaseBean {
	//自动投标授权
	public static final String AUTH_TYPE_AUTO_BID = "autoBid";
	//自动债转授权
	public static final String AUTH_TYPE_AUTO_CREDIT = "autoCredit";
	//服务费授权
	public static final String AUTH_TYPE_PAYMENT_AUTH = "paymentAuth";
	//还款授权
	public static final String AUTH_TYPE_REPAY_AUTH = "repayAuth";
	//合并授权（自动投标、自动债转、服务费授权）三合一
	public static final String AUTH_TYPE_MERGE_AUTH = "mergeAuth";
	//（服务费授权、还款授权）二合一
	public static final String AUTH_TYPE_PAY_REPAY_AUTH = "payRepayAuth";

}
