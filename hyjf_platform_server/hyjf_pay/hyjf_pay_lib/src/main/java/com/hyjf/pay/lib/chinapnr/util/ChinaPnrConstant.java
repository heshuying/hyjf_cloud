/**
 * Description:汇付天下相关常量
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: GOGTZ-T
 * @version: 1.0
 * Created at: 2015年11月23日 上午11:48:46
 * Modification History:
 * Modified by :
 */
package com.hyjf.pay.lib.chinapnr.util;

/**
 * @author Administrator
 */

public class ChinaPnrConstant {

	// 认证密钥hyjf.chinapnr.url
	/** ceshiURL */
	public static final String PROP_URL = "hyjf.chinapnr.url";
	/** 商户号 */
	public static final String PROP_MERID = "hyjf.chinapnr.merid";
	/** 商户客户号 */
	public static final String PROP_MERCUSTID = "hyjf.chinapnr.mercustid";
	/** 商户子账户号 */
	public static final String PROP_MERACCTID = "hyjf.chinapnr.meracctId";

	/** 融资服务费 放款商户子账户号 */
	public static final String PROP_MERACCT02 = "hyjf.chinapnr.meracct02";
	/** 账户管理费 还款商户子账户号 */
	public static final String PROP_MERACCT03 = "hyjf.chinapnr.meracct03";
	/** 汇转让服务费 债权转让商户子账户号 */
	public static final String PROP_MERACCT04 = "hyjf.chinapnr.meracct04";
	/** 运营活动商户子账户号 */
	public static final String PROP_MERACCT05 = "hyjf.chinapnr.meracct05";
	/** 推广提成商户子账户号 */
	public static final String PROP_MERACCT06 = "hyjf.chinapnr.meracct06";
	/** 充值手续费垫付商户子账户号 */
	public static final String PROP_MERACCT07 = "hyjf.chinapnr.meracct07";
	/** 充值手续费返现商户子账户号 */
	public static final String PROP_MERACCT08 = "hyjf.chinapnr.meracct08";
	/** 充值手续费收回商户子账户号 */
	public static final String PROP_MERACCT09 = "hyjf.chinapnr.meracct09";
	/** 购买会员商户子账户号 */
	public static final String PROP_MERACCT10 = "hyjf.chinapnr.meracct10";
	/** 加息券商户子账户号 */
	public static final String PROP_MERACCT11 = "hyjf.chinapnr.meracct11";
	/** 体验金商户子账户号 */
	public static final String PROP_MERACCT12 = "hyjf.chinapnr.meracct12";
	/** 抵用券商户子账户号 */
	public static final String PROP_MERACCT13 = "hyjf.chinapnr.meracct13";
	/** 现金红包商户子账户号 */
	public static final String PROP_MERACCT14 = "hyjf.chinapnr.meracct14";
	/** 转账商户子账户号 */
	public static final String PROP_MERACCT15 = "hyjf.chinapnr.meracct15";
	/** 调账商户子账户号 */
	public static final String PROP_MERACCT16 = "hyjf.chinapnr.meracct16";
	/** 汇添金服务费账户号 */
	public static final String PROP_MERACCT17 = "hyjf.chinapnr.meracct17";
	/** 融通宝加息还款出账账户号 */
	public static final String PROP_MERACCT18 = "hyjf.chinapnr.meracct18";

	/** 商户公钥文件地址 */
	public static final String PROP_MER_PUB_KEY_PATH = "hyjf.chinapnr.mer.pubkey.path";
	/** 商户私钥文件地址 */
	public static final String PROP_MER_PRI_KEY_PATH = "hyjf.chinapnr.mer.prikey.path";
	/** 汇付天下回调URL */
	public static final String PROP_CALLBACK_URL = "hyjf.chinapnr.callback.url";
	/** 页面返回 URL */
	public static final String PROP_RETURN_URL = "hyjf.chinapnr.return.url";
	/** 企业用户绑定 页面返回 URL */
	public static final String PROP_BIND_RETURN_URL = "hyjf.chinapnr.bindreturn.url";
	/** hyjf-web URL */
	public static final String PROP_WEB_HOST = "hyjf.web.host";
	// 版本号
	/** 版本号Key */
	public static final String PROP_VERSION_KEY = "hyjf.chinapnr.version";
	/** 版本号:10 */
	public static final String VERSION_10 = "10";
	/** 版本号:20 */
	public static final String VERSION_20 = "20";
	/** 版本号:30 */
	public static final String VERSION_30 = "30";

	// 消息类型
	/** 用户开户,页面浏览器方式 */
	public static final String CMDID_USER_REGISTER = "UserRegister";
	/** 后台用户开户,后台数据流方式 */
	public static final String CMDID_BG_REGISTER = "BgRegister";
	/** 用户绑卡,页面浏览器方式 */
	public static final String CMDID_USER_BIND_CARD = "UserBindCard";
	/** 后台接口绑卡,后台数据流方式 */
	public static final String CMDID_BG_BIND_CARD = "BgBindCard";
	/** 后台接口绑卡,后台数据流方式 */
	public static final String CMDID_BG_UNBIND_CARD = "PnrUsrUnBindExpressCard";
	/** 用户登录,页面浏览器方式 */
	public static final String CMDID_USER_LOGIN = "UserLogin";
	/** 账户信息修改,页面浏览器方式 */
	public static final String CMDID_ACCT_MODIFY = "AcctModify";
	/** 担保类型企业开户接口,页面浏览器方式 */
	public static final String CMDID_CORP_REGISTER = "CorpRegister";
	/** 删除银行卡接口,后台数据流方式 */
	public static final String CMDID_DEL_CARD = "DelCard";
	/** 网银充值,页面浏览器方式 */
	public static final String CMDID_NET_SAVE = "NetSave";
	/** 商户无卡代扣充值,后台数据流方式 */
	public static final String CMDID_POS_WH_SAVE = "PosWhSave";
	/** 资金（货款）冻结,后台数据流方式 */
	public static final String CMDID_USR_FREEZE_BG = "UsrFreezeBg";
	/** 资金（货款）解冻,后台数据流方式 */
	public static final String CMDID_USR_UN_FREEZE = "UsrUnFreeze";
	/** 主动投标,页面浏览器方式 */
	public static final String CMDID_INITIATIVE_TENDER = "InitiativeTender";
	/** 自动投标,后台数据流方式 */
	public static final String CMDID_AUTO_TENDER = "AutoTender";
	/** 投标撤销,页面浏览器方式 */
	public static final String CMDID_TENDER_CANCLE = "TenderCancle";
	/** 自动投标计划,页面浏览器方式 */
	public static final String CMDID_AUTO_TENDER_PLAN = "AutoTenderPlan";
	/** 自动投标关闭,页面浏览器方式 */
	public static final String CMDID_AUTO_TENDER_PLAN_CLOSE = "AutoTenderPlanClose";
	/** 自动扣款（放款）,后台数据流方式 */
	public static final String CMDID_LOANS = "Loans";
	/** 自动扣款（还款）,后台数据流方式 */
	public static final String CMDID_REPAYMENT = "Repayment";
	/** 转账（商户用）,后台数据流方式 */
	public static final String CMDID_TRANSFER = "Transfer";
	/** 取现复核,后台数据流方式 */
	public static final String CMDID_CASH_AUDIT = "CashAudit";
	/** 取现,页面浏览器方式 */
	public static final String CMDID_CASH = "Cash";
	/** 用户账户支付,页面浏览器方式 */
	public static final String CMDID_USR_ACCT_PAY = "UsrAcctPay";
	/** 商户代取现接口,后台数据流方式 */
	public static final String CMDID_MER_CASH = "MerCash";
	/** 前台用户间转账接口,页面浏览器方式 */
	public static final String CMDID_USR_TRANSFER = "UsrTransfer";
	/** 债权转让接口,页面浏览器方式 */
	public static final String CMDID_CREDIT_ASSIGN = "CreditAssign";
	/** 自动债权转让接口,后台数据流方式 */
	public static final String CMDID_AUTO_CREDIT_ASSIGN = "AutoCreditAssign";
	/** 生利宝交易接口,页面浏览器方式 */
	public static final String CMDID_FSS_TRANS = "FssTrans";
	/** 余额查询(页面),页面浏览器方式 */
	public static final String CMDID_QUERY_BALANCE = "QueryBalance";
	/** 余额查询(后台),后台数据流方式 */
	public static final String CMDID_QUERY_BALANCE_BG = "QueryBalanceBg";
	/** 商户子账户信息查询,后台数据流方式 */
	public static final String CMDID_QUERY_ACCTS = "QueryAccts";
	/** 交易状态查询,后台数据流方式 */
	public static final String CMDID_QUERY_TRANS_STAT = "QueryTransStat";
	/** 交易明细查询,后台数据流方式 */
	public static final String CMDID_QUERY_TRANS_DETAIL = "QueryTransDetail";
	/** 自动投标计划状态查询,后台数据流方式 */
	public static final String CMDID_QUERY_TENDER_PLAN = "QueryTenderPlan";
	/** 交易状态查询 */
	public static final String CMDID_QUERYTRANSSTAT = "QueryTransStat";
	/** 投标对账(放款和还款对账),后台数据流方式 */
	public static final String CMDID_RECONCILIATION = "Reconciliation";
	/** 商户扣款对账,后台数据流方式 */
	public static final String CMDID_TRF_RECONCILIATION = "TrfReconciliation";
	/** 取现对账,后台数据流方式 */
	public static final String CMDID_CASH_RECONCILIATION = "CashReconciliation";
	/** 账户明细查询,页面浏览器方式 */
	public static final String CMDID_QUERY_ACCT_DETAILS = "QueryAcctDetails";
	/** 充值对账,后台数据流方式 */
	public static final String CMDID_SAVE_RECONCILIATION = "SaveReconciliation";
	/** 垫资手续费返还查询,后台数据流方式 */
	public static final String CMDID_QUERY_RETURN_DZ_FEE = "QueryReturnDzFee";
	/** 担保类型企业开户状态查询接口,后台数据流方式 */
	public static final String CMDID_CORP_REGISTER_QUERY = "CorpRegisterQuery";
	/** 债权查询接口,后台数据流方式 */
	public static final String CMDID_CREDIT_ASSIGN_RECONCILIATION = "CreditAssignReconciliation";
	/** 生利宝转入对账接口,后台数据流方式 */
	public static final String CMDID_FSS_PURCHASE_RECONCILIATION = "FssPurchaseReconciliation";
	/** 生利宝转出对账接口,后台数据流方式 */
	public static final String CMDID_FSS_REDEEM_RECONCILIATION = "FssRedeemReconciliation";
	/** 生利宝产品信息查询,后台数据流方式 */
	public static final String CMDID_QUERY_FSS = "QueryFss";
	/** 生利宝账户信息查询,后台数据流方式 */
	public static final String CMDID_QUERY_FSS_ACCTS = "QueryFssAccts";
	/** 银行卡查询接口,后台数据流方式 */
	public static final String CMDID_QUERY_CARD_INFO = "QueryCardInfo";
	/** 用户信息查询 */
	public static final String CMDID_QUERY_USR_INFO = "QueryUsrInfo";
	/** 标的信息录入接口 */
	public static final String CMDID_ADD_BID_INFO = "AddBidInfo";
	/** 标的信息补录输入接口 */
	public static final String CMDID_ADD_BID_ATTACH_INFO = "AddBidAttachInfo";
	/** 标的审核状态查询接口 */
	public static final String CMDID_QUERY_BID_INFO = "QueryBidInfo";
	/** 定向支付授权接口 */
	public static final String CMDID_DIRECT_TRF_AUTH = "DirecTrfAuth";
	/** 定向支付接口 */
	public static final String CMDID_DIRECT_TRF = "DirecTrf";
	/** 快捷充值限额信息查询接口 */
	public static final String CMDID_QUERY_PAY_QUOTA = "QueryPayQuota";

	// 参数
	/** 版本前缀 */
	public static final String PARAM_PRE = "PARAM_";
	/** 版本号 */
	public static final String PARAM_VERSION = "Version";
	/** 消息类型 */
	public static final String PARAM_CMDID = "CmdId";
	/** 商户客户号 */
	public static final String PARAM_MERCUSTID = "MerCustId";
	/** 商户后台应答地址 */
	public static final String PARAM_BGRETURL = "BgRetUrl";
	/** 商户私有域对象 */
	public static final String PARAM_MERPRIVPO = "MerPrivPo";
	/** 页面返回 URL */
	public static final String PARAM_RETURL = "RetUrl";
	/** 用户号 */
	public static final String PARAM_USRID = "UsrId";
	/** 真实名称 */
	public static final String PARAM_USRNAME = "UsrName";
	/** 证件类型 */
	public static final String PARAM_IDTYPE = "IdType";
	/** 证件号码 */
	public static final String PARAM_IDNO = "IdNo";
	/** 手机号 */
	public static final String PARAM_USRMP = "UsrMp";
	/** 用户 Email */
	public static final String PARAM_USREMAIL = "UsrEmail";
	/** 商户私有域 */
	public static final String PARAM_MERPRIV = "MerPriv";
	/** 页面类型 */
	public static final String PARAM_PAGETYPE = "PageType";
	/** 编码集 */
	public static final String PARAM_CHARSET = "CharSet";
	/** 签名 */
	public static final String PARAM_CHKVALUE = "ChkValue";
	/** 应答返回码 */
	public static final String PARAM_RESPCODE = "RespCode";
	/** 应答描述 */
	public static final String PARAM_RESPDESC = "RespDesc";
	/** 本平台交易唯一标识 */
	public static final String PARAM_TRXID = "TrxId";
	/** 用户客户号 */
	public static final String PARAM_USRCUSTID = "UsrCustId";
	/** 自动投标计划授权类型 */
	public static final String PARAM_TENDERPLANTYPE = "TenderPlanType";
	/** 用户客户号 (定向转账 入账用户) */
	public static final String PARAM_INUSRCUSTID = "InUsrCustId";
	/** 授权金额 */
	public static final String PARAM_AUTHAMT = "AuthAmt";
	/** 用户客户号,解绑快捷卡中用到 */
	public static final String PARAM_CUSTID = "CustId";
	/** 用户银行卡,解绑快捷卡中用到 */
	public static final String PARAM_BANKID = "BankId";
	/** 用户银行卡,解绑快捷卡中用到 */
	public static final String PARAM_EXPRESSFLAG = "ExpressFlag";
	/** 开户银行账号 */
	public static final String PARAM_CARDID = "CardId";
	/** 订单号 */
	public static final String PARAM_ORDID = "OrdId";
	/** 订单日期 */
	public static final String PARAM_ORDDATE = "OrdDate";
	/** 支付网关业务代号 */
	public static final String PARAM_GATEBUSIID = "GateBusiId";
	/** 开户银行代号 */
	public static final String PARAM_OPENBANKID = "OpenBankId";
	/** 借贷记标记 */
	public static final String PARAM_DCFLAG = "DcFlag";
	/** 交易金额 */
	public static final String PARAM_TRANSAMT = "TransAmt";
	/** 还款本金 */
	public static final String PARAM_PRINCIPALAMT = "PrincipalAmt";
	/** 还款利息 */
	public static final String PARAM_INTERESTAMT = "InterestAmt";
	/** 是否垫资还款 */
	public static final String PARAM_DZOBJECT = "DzObject";
	/** 生利宝交易类型 */
	public static final String PARAM_TRANSTYPE = "TransType";
	/** 汇付交易状态 */
	public static final String PARAM_TRANSSTAT = "TransStat";
	/** 银行卡号 */
	public static final String PARAM_OPENACCTID = "OpenAcctId";
	/** 身份证号 */
	public static final String PARAM_CERTID = "CertId";
	/** 子账户类型 */
	public static final String PARAM_SUBACCTTYPE = "SubAcctType";
	/** 子账户号 */
	public static final String PARAM_SUBACCTID = "SubAcctId";
	/** 最大出借手续费率 */
	public static final String PARAM_MAXTENDERRATE = "MaxTenderRate";
	/** 借款人信息 */
	public static final String PARAM_BORROWERDETAILS = "BorrowerDetails";
	/** 借款人客户号 */
	public static final String PARAM_BORROWERCUSTID = "BorrowerCustId";
	/** 借款金额 */
	public static final String PARAM_BORROWERAMT = "BorrowerAmt";
	/** 借款手续费率 */
	public static final String PARAM_BORROWERRATE = "BorrowerRate";
	/** 是否冻结 */
	public static final String PARAM_ISFREEZE = "IsFreeze";
	/** 冻结订单号 */
	public static final String PARAM_FREEZEORDID = "FreezeOrdId";
	/** 入参扩展域 */
	public static final String PARAM_REQEXT = "ReqExt";
	/** 是否解冻 */
	public static final String PARAM_ISUNFREEZE = "IsUnFreeze";
	/** 解冻订单号 */
	public static final String PARAM_UNFREEZEORDID = "UnFreezeOrdId";
	/** 冻结标识 */
	public static final String PARAM_FREEZETRXID = "FreezeTrxId";
	/** 出账客户号 */
	public static final String PARAM_OUTCUSTID = "OutCustId";
	/** 扣款手续费 */
	public static final String PARAM_FEE = "Fee";
	/** 从属订单号 */
	public static final String PARAM_SUBORDID = "SubOrdId";
	/** 从属订单日期 */
	public static final String PARAM_SUBORDDATE = "SubOrdDate";
	/** 入账客户号 */
	public static final String PARAM_INCUSTID = "InCustId";
	/** 分账账户串 */
	public static final String PARAM_DIVDETAILS = "DivDetails";
	/** 是否默认 */
	public static final String PARAM_ISDEFAULT = "IsDefault";
	/** 续费收取对象标志 I/O */
	public static final String PARAM_FEEOBJFLAG = "FeeObjFlag";
	/** 出账子账户 */
	public static final String PARAM_OUTACCTID = "OutAcctId";
	/** 入账子账户 */
	public static final String PARAM_INACCTID = "InAcctId";
	/** 入账账户类型 */
	public static final String PARAM_INACCTTYPE = "InAcctType";
	/** 备注 */
	public static final String PARAM_REMARK = "Remark";
	/** 商户收取服务费金额 */
	public static final String PARAM_SERVFEE = "ServFee";
	/** 商户子账户号 */
	public static final String PARAM_SERVFEEACCTID = "ServFeeAcctId";
	/** 转让人客户号 */
	public static final String PARAM_SELLCUSTID = "SellCustId";
	/** 转让金额 */
	public static final String PARAM_CREDITAMT = "CreditAmt";
	/** 承接金额 */
	public static final String PARAM_CREDITDEALAMT = "CreditDealAmt";
	/** 债权转让明细 */
	public static final String PARAM_BIDDETAILS = "BidDetails";
	/** 承接人客户号 */
	public static final String PARAM_BUYCUSTID = "BuyCustId";
	/** 挂牌债权 ID */
	public static final String PARAM_LCID = "LcId";
	/** 承接人客户号 */
	public static final String PARAM_TOTALLCAMT = "TotalLcAmt";
	/** 开始时间 */
	public static final String PARAM_BEGINDATE = "BeginDate";
	/** 结束时间 */
	public static final String PARAM_ENDDATE = "EndDate";
	/** 页数 */
	public static final String PARAM_PAGENUM = "PageNum";
	/** 每页记录数 */
	public static final String PARAM_PAGESIZE = "PageSize";
	/** 交易查询类型 */
	public static final String PARAM_QUERYTRANSTYPE = "QueryTransType";
	/** 记录总条数 */
	public static final String PARAM_TOTALITEMS = "TotalItems";
	/** 返参扩展域 */
	public static final String PARAM_RESPEXT = "RespExt";
	/** 手续费金额 */
	public static final String PARAM_FEEAMT = "FeeAmt";
	/** 手续费扣款客户号 */
	public static final String PARAM_FEECUSTID = "FeeCustId";
	/** 手续费扣款子账户号 */
	public static final String PARAM_FEEACCTID = "FeeAcctId";
	/** 异步返回的消息类型 */
	public static final String PARAM_RESPTYPE = "RespType";
	/** 实际到账金额 */
	public static final String PARAM_REALTRANSAMT = "RealTransAmt";
	/** 分账商户号 */
	public static final String PARAM_DIVCUSTID = "DivCustId";
	/** 分账账户号 */
	public static final String PARAM_DIVACCTID = "DivAcctId";
	/** 分账金额 */
	public static final String PARAM_DIVAMT = "DivAmt";
	/** 标的信息录入接口-标的类型 */
	public static final String PARAM_BIDTYPE = "BidType";
	/** 标的信息录入接口-发表金额 */
	public static final String PARAM_BORRTOTAMT = "BorrTotAmt";
	/** 标的信息录入接口-发表年化利率 */
	public static final String PARAM_YEARRATE = "YearRate";
	/** 标的信息录入接口-应还款总利息 */
	public static final String PARAM_RETINTEREST = "RetInterest";
	/** 标的信息录入接口-最后还款日期 */
	public static final String PARAM_LASTRETDATE = "LastRetDate";
	/** 标的信息录入接口-计划投标开始日期 */
	public static final String PARAM_BIDSTARTDATE = "BidStartDate";
	/** 标的信息录入接口-计划投标截止日期 */
	public static final String PARAM_BIDENDDATE = "BidEndDate";
	/** 标的信息录入接口-还款方式 */
	public static final String PARAM_RETTYPE = "RetType";
	/** 标的信息录入接口-应还款日期 */
	public static final String PARAM_RETDATE = "RetDate";
	/** 标的信息录入接口-本息保障 */
	public static final String PARAM_GUARANTTYPE = "GuarantType";
	/** 标的信息录入接口-标的产品类型 */
	public static final String PARAM_BIDPRODTYPE = "BidProdType";
	/** 标的信息录入接口-风险控制方式 */
	public static final String PARAM_RISKCTLTYPE = "RiskCtlType";
	/** 标的信息录入接口-限定最低投标份数 */
	public static final String PARAM_LIMITMINBIDAMT = "LimitMinBidAmt";
	/** 标的信息录入接口-限定每份投标金额 */
	public static final String PARAM_LIMITBIDSUM = "LimitBidSum";
	/** 标的信息录入接口-限定最多投标金额 */
	public static final String PARAM_LIMITMAXBIDSUM = "LimitMaxBidSum";
	/** 标的信息录入接口-限定最少投标金额 */
	public static final String PARAM_LIMITMINBIDSUM = "LimitMinBidSum";
	/** 标的信息录入接口-逾期是否垫资 */
	public static final String PARAM_BIDPAYFORSTATE = "BidPayforState";
	/** 标的信息录入接口-借款人类型 */
	public static final String PARAM_BORRTYPE = "BorrType";
	/** 标的信息录入接口-借款人ID */
	public static final String PARAM_BORRCUSTID = "BorrCustId";
	/** 标的信息录入接口-借款企业营业执照编号 */
	public static final String PARAM_BORRBUSICODE = "BorrBusiCode";
	/** 标的信息录入接口-借款人证件类型 */
	public static final String PARAM_BORRCERTTYPE = "BorrCertType";
	/** 标的信息录入接口-借款人证件号码 */
	public static final String PARAM_BORRCERTID = "BorrCertId";
	/** 标的信息录入接口-借款人手机号码 */
	public static final String PARAM_BORRMOBIPHONE = "BorrMobiPhone";
	/** 标的信息录入接口-借款人固定电话 */
	public static final String PARAM_BORRPHONE = "BorrPhone";
	/** 标的信息录入接口-借款人工作年限 */
	public static final String PARAM_BORRWORKYEAR = "BorrWorkYear";
	/** 标的信息录入接口-借款人税后月收入 */
	public static final String PARAM_BORRINCOME = "BorrIncome";
	/** 标的信息录入接口-借款人婚姻状态 */
	public static final String PARAM_BORRMARRIAGE = "BorrMarriage";
	/** 标的信息录入接口-借款人点子邮箱 */
	public static final String PARAM_BORREMAIL = "BorrEmail";
	/** 标的审核状态查询接口-审核状态 */
	public static final String PARAM_STATUS = "Status";
	/** 快捷支付限额查询 接口- 快捷支付 */
	public static final String PARAM_QP = "QP";

	/** Client */
	public static final String PARAM_CLIENT = "Client";
	/** MsgType */
	public static final String PARAM_MSGTYPE = "MsgType";
	/** MsgData */
	public static final String PARAM_MSGDATA = "MsgData";
	/** BorrowNid */
	public static final String PARAM_BORROWNID = "BorrowNid";
	/** Ip */
	public static final String PARAM_IP = "Ip";
	/** 用户号 */
	public static final String PARAM_USERID = "UserId";
	/** 项目id */
	public static final String PARAM_PROID = "ProId";
	/** 银行卡列表 */
	public static final String PARAM_USRCARDINFOLIST = "UsrCardInfolist";
	/** 组织机构代码 */
	public static final String PARAM_INSTUCODE = "InstuCode ";
	/** 营业执照编号 */
	public static final String PARAM_BUSICODE = "BusiCode";
	/** 税务登记号 */
	public static final String PARAM_TAXCODE = "TaxCode";
	/** 担保类型 */
	public static final String PARAM_GUARTYPE = "GuarType";
	/** 审核状态 */
	public static final String PARAM_AUDITSTAT = "AuditStat";
	// 状态
	/** 状态 0:处理成功 */
	public static final String STATUS_SUCCESS = "0";
	/** 状态 1:请求中 */
	public static final String STATUS_SENDING = "1";
	/** 状态 2:处理中 */
	public static final String STATUS_RUNNING = "2";
	/** 状态 3:验签成功 */
	public static final String STATUS_VERTIFY_OK = "3";
	/** 状态 4:验签失败 */
	public static final String STATUS_VERTIFY_NG = "4";
	/** 状态 9:处理失败 */
	public static final String STATUS_FAIL = "9";

	// 返回状态
	/** 状态 000:成功 */
	public static final String RESPCODE_SUCCESS = "000";
	/** 状态 070: 请求数据不存在 */
	public static final String RESPCODE_NOTEXIST = "070";
	/** 状态 107:重复交易 */
	public static final String RESPCODE_REPEAT_DEAL = "107";
	/** 状态 334:已放款金额加本次放款金额超过出借人原单中的出借金额 */
	public static final String RESPCODE_ACCOUNT_OUT = "334";
	/** 状态 349:本次还款金额加上已还款金额超过还款总额 */
	public static final String RESPCODE_REPAY_OUT = "349";
	/** 状态 345:重复的放款请求 */
	public static final String RESPCODE_REPEAT_LOANS = "345";
	/** 状态 358:冻结失败 */
	public static final String RESPCODE_FREEZE_FAIL = "358";
	/** 状态 351:重复的还款请求 */
	public static final String RESPCODE_REPEAT_REPAY = "351";
	/** 状态 359:解冻失败 */
	public static final String RESPCODE_UNFREEZE_FAIL = "359";
	/** 状态 359:解冻失败 */
	public static final String RESPCODE_TENDER_FAIL = "363";
	/** 状态 400:取现失败 */
	public static final String RESPCODE_WITHDRAW_FAIL = "400";
	/** 状态 421:借款交易记录不存在 */
	public static final String RESPCODE_NO_LOANS_RECORD = "421";
	/** 状态 422:还款交易记录不存在 */
	public static final String RESPCODE_NO_REPAY_RECORD = "422";
	/** 状态 999:审核中 */
	public static final String RESPCODE_CHECK = "999";
	/** 状态 1000:查询数据不存在 */
	public static final String RESPCODE_NO_RESULT = "1000";

	/** 状态 215:充值手续费不足 */
	public static final String RESPCODE_YUE0_FAIL = "215";
	/** 状态 343:出账用户余额不足 */
	public static final String RESPCODE_YUE1_FAIL = "343";
	/** 状态 364:余额不足 */
	public static final String RESPCODE_YUE2_FAIL = "364";

	/** 状态 216:企业用户注册信息不存在 */
	public static final String RESPCODE_216 = "216";
	/** 状态 217:企业用户注册审核拒绝 */
	public static final String RESPCODE_217 = "217";
	/** 状态 218:企业用户注册审核中 */
	public static final String RESPCODE_218 = "218";
	/** 状态 219:企业用户注册已提交待审核 */
	public static final String RESPCODE_219 = "219";

	// 其他
	/** GET */
	public static final String GET = "get";
	/** SET */
	public static final String SET = "set";
}
