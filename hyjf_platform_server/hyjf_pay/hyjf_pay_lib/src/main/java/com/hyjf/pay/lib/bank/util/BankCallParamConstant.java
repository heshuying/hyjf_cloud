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

public class BankCallParamConstant extends BankCallMethodConstant implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 7289087039220811053L;
	// 参数
	// log参数
	/** 银行存管调用url */
	public static final String BANK_LOGBANKDETAIL_URL = "logBankDetailUrl";
	/** 订单号 */
	public static final String PARAM_LOGORDERID = "logOrderId";
	/** 登陆用户id */
	public static final String PARAM_LOGUSERID = "logUserId";
	/** 订单号 */
	public static final String PARAM_LOGNOTIFYURLTYPE = "logNotifyType";
	/** 异步回调地址1 */
	public static final String PARAM_LOGNOTIFYURL = "logNotifyURL";
	/** 异步回调地址2 */
	public static final String PARAM_LOGRETNOTIFYURL = "logRetNotifyURL";
	/** 订单日期 */
	public static final String PARAM_LOGORDERDATE = "logOrderDate";
	/** 商户私有域对象 */
	public static final String PARAM_LOGACQRESBEAN = "logAcqResBean";
	/** 响应描述 */
	public static final String PARAM_LOGRESPTYPE = "logRespType";
	/** MsgType */
	public static final String PARAM_LOGMSGTYPE = "logMsgType";
	/** MsgData */
	public static final String PARAM_LOGMSGDATA = "logMsgData";
	/** 平台 */
	public static final String PARAM_LOGCLIENT = "logClient";
	/** 备注 */
	public static final String PARAM_LOGREMARK = "logRemark";
	// 共通参数
	/** 版本号 */
	public static final String PARAM_VERSION = "version";
	/** 交易代码 */
	public static final String PARAM_TXCODE = "txCode";
	/** 业务类别 */
	public static final String PARAM_TXTYPE = "txType";
	/** 银行代码 */
	public static final String PARAM_BANKCODE = "bankCode";
	/** 机构代码 */
	public static final String PARAM_INSTCODE = "instCode";
	/** 交易渠道 */
	public static final String PARAM_CHANNEL = "channel";
	/** 电子账号 */
	public static final String PARAM_ACCOUNTID = "accountId";
	/** 订单号 */
	public static final String PARAM_ORDERID = "orderId";
	/*是否维护标识*/
	public static final String PARAM_BITMAP = "bitMap";
	/*开通自动投标功能标志*/
	public static final String PARAM_AUTOBID = "autoBid";
	/*开通自动债转功能标志*/
	public static final String PARAM_AUTOTRANSFER = "autoTransfer";
	/*开通预约取现功能标志*/
	public static final String PARAM_AGREEWITHDRAW = "agreeWithdraw";
	/*代扣签约功能标志*/
    public static final String PARAM_AGREEDEDUCT = "agreeDeduct";
    /*缴费授权功能标志*/
    public static final String PARAM_PAYMENTAUTH = "paymentAuth";
    /*还款授权功能标志*/
    public static final String PARAM_REPAYAUTH = "repayAuth";
    /*自动投标到期日*/
    public static final String PARAM_AUTOBIDDEADLINE = "autoBidDeadline";
    /*自动投标签约最高金额*/
    public static final String PARAM_AUTOBIDMAXAMT = "autoBidMaxAmt";
    /*缴费授权到期日*/
    public static final String PARAM_PAYMENTDEADLINE = "paymentDeadline";
    /*缴费签约最高金额*/
    public static final String PARAM_PAYMENTMAXAMT = "paymentMaxAmt";
    /*还款授权到期日*/
    public static final String PARAM_REPAYDEADLINE = "repayDeadline";
    /*还款签约最高金额*/
    public static final String PARAM_REPAYMAXAMT = "repayMaxAmt";
	/*开通无密消费功能标识*/
	public static final String PARAM_DIRECTCONSUME = "directConsume";
	/*备注*/
	public static final String PARAM_REMARK = "remark";
	/*restMsg*/
	public static final String PARAM_RESTMSG = "restMsg";
	/*返回交易页面链接*/
	public static final String PARAM_TRANSACTIONURL = "transactionUrl";
	/*订单有效性连接*/
	public static final String PARAM_VERIFYORDERURL = "verifyOrderUrl";
	/** 交易流水号 */
	public static final String PARAM_SEQNO = "seqNo";
	/** 流水是否经过借款人标志 1：经过; 空：不经过 */
	public static final String PARAM_SEQFLAG = "seqFlag";
	/** 前台跳转链接 */
	public static final String PARAM_RETURL = "retUrl";
	/** 后台通知连接 */
	public static final String PARAM_NOTIFYURL = "notifyUrl";
	/** 请求方保留 */
	public static final String PARAM_ACQRES = "acqRes";
	/** 验证值 */
	public static final String PARAM_SIGN = "sign";
	/** 响应代码 */
	public static final String PARAM_RETCODE = "retCode";
	/** 响应描述 */
	public static final String PARAM_RETMSG = "retMsg";
	/**开户日期*/
	public static final String PARAM_OPENDATE = "openDate";
	/**账户状态*/
	public static final String PARAM_ACCTSTATE = "acctState";
	/**地址*/
    public static final String PARAM_ADDR = "addr";
    /**身份角色*/
    public static final String PARAM_IDENTITY = "identity";
	/**冻结状态*/
	public static final String PARAM_FRZSTATE = "frzState";
	/**冻结业务类型*/
	public static final String PARAM_FRZSTYPE = "frzType";

	/**密码挂失状态*/
	public static final String PARAM_PINLOSCD = "pinLosCd";
	// 方法内部参数
	/** 客户IP */
	public static final String PARAM_USERIP = "userIP";
	/** 交易日期 */
	public static final String PARAM_TXDATE = "txDate";
	/** 交易时间 */
	public static final String PARAM_TXTIME = "txTime";
	/** 证件类型 */
	public static final String PARAM_IDTYPE = "idType";
	/** 证件号码 */
	public static final String PARAM_IDNO = "idNo";
	/** 姓名 */
	public static final String PARAM_NAME = "name";
	/** 手机号 */
	public static final String PARAM_MOBILE = "mobile";
	/** 银行卡号 */
	public static final String PARAM_CARDNO = "cardNo";
	/** 邮箱 */
	public static final String PARAM_EMAIL = "email";
	/** 账户用途 */
	public static final String PARAM_ACCTUSE = "acctUse";
	/** 交易币种 */
	public static final String PARAM_CURRENCY = "currency";
	/** 交易金额 */
	public static final String PARAM_TXAMOUNT = "txAmount";
	/** 交易本金 */
	public static final String PARAM_TXCAPAMOUT = "txCapAmout";
	/** 交易利息 */
	public static final String PARAM_TXINTAMOUNT = "txIntAmount";
	/** 选项 */
	public static final String PARAM_OPTION = "option";
	/** 自动投标总金额上限 */
	public static final String PARAM_TOTAMOUNT = "totAmount";
	/** 自动投标总金额上限 */
    public static final String PARAM_BIDDEADLINE = "bidDeadline";

	/** 原订单id */
	public static final String PARAM_ORGORDERID = "orgOrderId";
	/** 起始日期 */
	public static final String PARAM_STARTDATE = "startDate";
	/** 结束日期 */
	public static final String PARAM_ENDDATE = "endDate";
	/** 交易种类 */
	public static final String PARAM_TYPE = "type";
	/** 交易类型 */
	public static final String PARAM_TRANTYPE = "tranType";
	/** 页数 */
	public static final String PARAM_PAGENUM = "pageNum";
	/** 页长 */
	public static final String PARAM_PAGESIZE = "pageSize";
	/**对公账号*/
	public static final String PARAM_CACCOUNT = "caccount";
	/**组织机构代码*/
	public static final String PARAM_BUSID = "busId";
	/**税务登记号*/
	public static final String PARAM_TAXID = "taxId";
	
	/** 账户类型 */
	public static final String PARAM_ACCTYPE = "accType";
	/** 可用余额 */
	public static final String PARAM_AVAILBAL = "availBal";
	/** 账面余额 账面余额-可用余额=冻结金额 */
	public static final String PARAM_CURRBAL = "currBal";
	/** 提现开关 0-关闭 1-打开 */
	public static final String PARAM_WITHDRAWFLAG = "withdrawFlag";
	/** 总记录数 */
	public static final String PARAM_TOTALITEMS = "totalItems";
	/** 结果数组 */
	public static final String PARAM_SUBPACKS = "subPacks";
	/** 提现手续费 */
	public static final String PARAM_TXFEE = "txFee";
	/** 发送短信验证码业务交易代码 */
	public static final String PARAM_SRVTXCODE = "srvTxCode";
	/** 发送短信验证码业务授权码 */
	public static final String PARAM_SRVAUTHCODE = "srvAuthCode";
	/** 验证码类型 */
    public static final String PARAM_SMSTYPE = "smsType";

	/** 路由代码0-本行通道 1-银联通道 2-人行通道空-自动选择 */
	public static final String PARAM_ROUTECODE = "routeCode";

	/** 对公账户提现标识 */
    public static final String PARAM_BUSINESSACCOUNTIDFLAG = "businessAccountIdFlag";

	/** 绑定银行联行号人民银行分配的12位联行号 routeCode=2，必输 或者routeCode为空，但交易金额>20万，必输 */
	public static final String PARAM_CARDBANKCNAPS = "cardBankCnaps";
	/** 绑定银行中文名称 ,绑定的银行卡对应的银行中文名称 */
	public static final String PARAM_CARDBANKNAMECN = "cardBankNameCn";
	/** 绑定银行英文名称 ,绑定的银行卡对应的银行英文名称缩写 */
	public static final String PARAM_CARDBANKNAMEEN = "cardBankNameEn";
	/** 绑定银行卡开户省份 ,绑定的银行卡的开户省份 */
	public static final String PARAM_CARDBANKPROVINCE = "cardBankProvince";
	/** 绑定银行卡开户城市,绑定的银行卡的开户城市 */
	public static final String PARAM_CARDBANKCITY = "cardBankCity";
	/** 忘记密码跳转 ,忘记密码的跳转URL */
	public static final String PARAM_FORGOTPWDURL = "forgotPwdUrl";
	/** 绑定银行代码 ,绑定的银行卡对应的银行代码 */
	public static final String PARAM_CARDBANKCODE = "cardBankCode";
	/** 批次号 */
	public static final String PARAM_BATCHNO = "batchNo";
	/** 交易笔数 */
	public static final String PARAM_TXCOUNTS = "txCounts";
	/** 业务结果通知 */
	public static final String PARAM_NOTIFY_URL = "notifyURL";
	/** 业务结果通知 */
	public static final String PARAM_RETNOTIFY_URL = "retNotifyURL";
	/** 接收结果 */
	public static final String PARAM_RECEIVED = "received";

	/** 转让金额 卖出的债权金额 */
	public static final String PARAM_TSFAMOUNT = "tsfAmount";
	/** 原交易金额 卖出方投标的原交易金额（或卖出方购买债权的原交易金额） */
	public static final String PARAM_ORGTXAMOUNT = "orgTxAmount";
	/** 对手电子账号:出借人账号 */
	public static final String PARAM_FORACCOUNTID = "forAccountId";
	/** 标的号 */
	public static final String PARAM_PRODUCTID = "productId";
	/** 买入方获得的债权授权码 */
	public static final String PARAM_AUTHCODE = "authCode";
	/** 批次交易日期 */
	public static final String PARAM_BATCHTXDATE = "batchTxDate";
	/**
	 * 查询类别1-按流水号查询（批次类交易不可用） 2-按订单号查询
	 */
	public static final String PARAM_REQTYPE = "reqType";

	/** 查询交易代码 */
	public static final String PARAM_REQTXCODE = "reqTxCode";
	/** 查询交易日期 */
	public static final String PARAM_REQTXDATE = "reqTxDate";
	/** 查询交易时间 */
	public static final String PARAM_REQTXTIME = "reqTxTime";
	/** 查询交易流水号 */
	public static final String PARAM_REQSEQNO = "reqSeqNo";
	/** 查询订单号 */
	public static final String PARAM_REQORDERID = "reqOrderId";
	/** 失败描述txState为F时有效 */
	public static final String PARAM_FAILMSG = "failMsg";
	/**
	 * 交易状态S-成功 F-失败 N-交易不存在 Z-未知 D-待处理
	 */
	public static final String PARAM_TXSTATE = "txState";
	/**
	 * 查询状态0-所有（默认） 1-当前有效的绑定卡
	 */
	public static final String PARAM_STATE = "state";
	/** 交易利息 */
	public static final String PARAM_INTAMOUNT = "intAmount";
	/** 罚息金额 */
	public static final String PARAM_FINEAMOUNT = "fineAmount";
	/** 还款手续费 */
	public static final String PARAM_TXFEEOUT = "txFeeOut";
	/** 收款手续费 */
	public static final String PARAM_TXFEEIN = "txFeeIn";

	/** 前导业务授权码 */
	public static final String PARAM_LASTSRVAUTHCODE = "lastSrvAuthCode";
	/** 短信验证码 */
	public static final String PARAM_SMSCODE = "smsCode";
	/** 成功交易金额 */
	public static final String PARAM_SUCAMOUNT = "sucAmount";
	/** 成功交易笔数 */
	public static final String PARAM_SUCCOUNTS = "sucCounts";
	/** 失败交易金额 */
	public static final String PARAM_FAILAMOUNT = "failAmount";
	/** 失败交易笔数 */
	public static final String PARAM_FAILCOUNTS = "failCounts";

	/** 标的描述 */
	public static final String PARAM_PRODUCTDESC = "productDesc";
	/** 募集日 */
	public static final String PARAM_RAISEDATE = "raiseDate";
	/** 募集结束日期 */
	public static final String PARAM_RAISEENDDATE = "raiseEndDate";
	/** 付息方式 0-到期与本金一起归还 1-每月固定日期支付 2-每月不确定日期支付 */
	public static final String PARAM_INTTYPE = "intType";
	/** 利息每月支付日 */
	public static final String PARAM_INTPAYDAY = "intPayDay";
	/** 借款期限 */
	public static final String PARAM_DURATION = "duration";
	/** 年化利率 */
	public static final String PARAM_RATE = "rate";
	/** 担保账户 */
	public static final String PARAM_BAILACCOUNTID = "bailAccountId";
	/** 名义借款人电子帐号 */
	public static final String PARAM_NOMINALACCOUNTID = "nominalAccountId";

	/** 是否冻结金额 */
	public static final String PARAM_FRZFLAG = "frzFlag";
	/** 是否使用红包 */
	public static final String PARAM_BONUSFLAG = "bonusFlag";
	/** 抵扣红包金额 */
	public static final String PARAM_BONUSAMOUNT = "bonusAmount";
	/** 批次交易代码 */
	public static final String PARAM_BATCHTXCODE = "batchTxCode";

	public static final String PARAM_DESLINEFLAG = "desLineFlag";

	public static final String PARAM_DESLINE = "desLine";

	/** 批次放款出借手续费 */
	public static final String PARAM_BIDFEE = "bidFee";
	/** 批次放款融资手续费 */
	public static final String PARAM_DEBTFEE = "debtFee";

	/** 批次处理金额 */
	public static final String PARAM_RELAMOUNT = "relAmount";
	/** 批次处理笔数 */
	public static final String PARAM_RELCOUNTS = "relCounts";
	/** 批次处理状态 */
	public static final String PARAM_BATCHSTATE = "batchState";
	/** 是否设置过密码 */
	public static final String PARAM_PINFLAG = "pinFlag";
	/** 对手姓名 */
	public static final String PARAM_FORNAME = "forName";
	/** 剩余可转让金额 */
	public static final String PARAM_AVAILAMOUNT = "availAmount";
	/** 转让所得 */
	public static final String PARAM_TXINCOME = "txIncome";
	/**预期收益*/
	public static final String PARAM_FORINCOME = "forIncome";
	/**投标日期*/
	public static final String PARAM_BUYDATE = "buyDate";
	/** 联行号*/
	public static final String PARAM_PAYALLIANCECODE = "payAllianceCode";
	/** 原交易日期*/
	public static final String PARAM_ORGTXDATE = "orgTxDate";
	/** 原交易时间*/
	public static final String PARAM_ORGTXTIME = "orgTxTime";
	/** 原交易流水号*/
	public static final String PARAM_ORGSEQNO = "orgSeqNo";
	/** 冲正撤销标志*/
	public static final String PARAM_ORFLAG = "orFlag";
	/** 交易处理结果*/
	public static final String PARAM_RESULT = "result";
	/** 付款账号 */
	public static final String PARAM_PAYACCOUNTID = "payAccountId";
	/** 推送地址 */
	public static final String PARAM_NOTICEADDRESS = "noticeAddress";
	/** 退汇标志*/
	public static final String PARAM_TXSTSFLAG = "txstsFlag";
	/** 摘要*/
	public static final String PARAM_NOTE = "note";

	/** 预期年化收益率*/
	public static final String PARAM_YIELD = "yield";
	/** 预期本息收益*/
	public static final String PARAM_INTTOTAL = "intTotal";
	/** 实际收益*/
	public static final String PARAM_INCOME = "income";
	/** 实际收益符号*/
	public static final String PARAM_INCFLAG = "incFlag";

	/** 回调地址*/
	public static final String PARAM_CALLBACKADRRESS = "callBackAdrress";
	/** 短信序列号*/
	public static final String PARAM_SMSSEQ = "smsSeq";
	/** 短信发送时间*/
	public static final String PARAM_SENDTIME = "sendTime";
	/** 验证码有效时长*/
	public static final String PARAM_VALIDTIME = "validTime";
	/** 签约订单号*/
	public static final String PARAM_CONTORDERID = "contOrderId";
	/**
	 * 手续费金额
	 */
	public static final String PARAM_FEEAMOUNT = "feeAmount";
	
	/**
	 * 风险准备金
	 */
	public static final String PARAM_RISKAMOUNT = "riskAmount";
	
	/**
	 * 申请订单号
	 */
	public static final String PARAM_LENPAY_ORDERID = "lendPayOrderId";
	
	/**
     * 签约日期
     */
    public static final String PARAM_TXNDATE = "txnDate";
    /**
     * 签约时间
     */
    public static final String PARAM_TXNTIME = "txnTime";
	
	
	/** 交易描述 */
	public static final String PARAM_DESCRIPTION = "description";
	
	/** 受托支付标志 */
	public static final String PARAM_ENTRUSTFLAG = "entrustFlag";
	
	/** 收款人电子帐户 */
	public static final String PARAM_RECEIPTACCOUNTID = "receiptAccountId";
	
	/**交易确认日期*/
	public static final String PARAM_AFFIRMDATE = "affirmDate";
	/**交易确认时间*/
	public static final String PARAM_AFFIRMTIME = "affirmTime";
	
	/**翻页标识 rtnInd */
	public static final String PARAM_RTNIND = "rtnInd";
	/**交易日期 inpDate*/
    public static final String PARAM_INPDATE = "inpDate";
    /**交易时间 inpTime*/
    public static final String PARAM_INPTIME = "inpTime";
    /**自然日期 inpDate*/
    public static final String PARAM_RELDATE = "relDate";
    /**流水号 inpDate*/
    public static final String PARAM_TRACENO = "traceNo";
    
    /** 调用是否成功*/
    public static final String PARAM_ISSUCCESS = "isSuccess";
    
    /** 调用成功的地址*/
    public static final String PARAM_SUCCESSFULURL = "successfulUrl";

    /**签约到期日期*/
    public static final String PARAM_DEADLINE = "deadline";
    /**签约最高金额*/
    public static final String PARAM_MAXAMT = "maxAmt";


    /**签约最大金额*/
    public static final String PARAM_CANCELDATE = "cancelDate";
    /**签约最大金额*/
    public static final String PARAM_CANCELTIME = "cancelTime";


   
}
