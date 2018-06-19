package com.hyjf.am.vo.trade;

import java.io.Serializable;

import com.hyjf.am.vo.BaseVO;

/**
 * �������з�������
 * @author jun 20180619
 *
 */
public class BankCallBeanVO extends BaseVO implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 8472170072969081116L;
	// 参数
	// log参数
	/** 操作用户userId */
	public String logUserId;
	/** 操作用户的userName */
	public String logUserName;
	/** 操作类型 */
	public String logType;
	/** 操作用户ip */
	public String logIp;
	/** 操作时间 */
	public String logTime;
	/** 备注 */
	public String logRemark;
	/** 订单�? */
	public String logOrderId;
	/** 订单日期 */
	public String logOrderDate;
	/** 银行请求详细url */
	public String logBankDetailUrl;
	/** 中间表uuid */
	public String logUuid;
	/** 用户操作平台 */
	public int logClient;
	/** 验证状�?? */
	public boolean logVerifyFlag;
	/** 验证结果返回 */
	public String logVerifyResult;
	/** 订单状�?? */
	public String logOrderStatus;
	/** 回调url类型 */
	public String logNotifyType;

	// 共�?�参�?
	/** 版本�? */
	public String version;
	/** 交易代码 */
	public String txCode;
	/** 机构代码 */
	public String instCode;
	/** 银行代码 */
	public String bankCode;
	/** 交易日期 */
	public String txDate;
	/** 交易时间 */
	public String txTime;
	/** 交易流水�? */
	public String seqNo;
	/** 交易金额 */
	public String txAmount;
	/** 手续�? */
	public String txFee;
	/** 交易渠道 */
	public String channel;
	/** 电子账号 */
	public String accountId;
	/** 订单�? */
	public String orderId;
	/** 前台跳转链接 */
	public String retUrl;
	/** 后台通知连接 */
	public String notifyUrl;
	/** 客户IP */
	public String userIP;
	/** 请求方保�? */
	public String acqRes;
	/** 验证�? */
	public String sign;
	/** 响应代码 */
	public String retCode;
	/** 响应描述 */
	public String retMsg;
	public String restMsg;
    // 方法内部参数
	/** 证件类型 */
	public String idType;
	/** 证件号码 */
	public String idNo;
	/** 姓名 */
	public String name;
	/** 手机�? */
	public String mobile;
	/** 银行卡号 */
	public String cardNo;
	/** 邮箱 */
	public String email;
	/** 账户用�?? */
	public String acctUse;
	/** 交易币种 */
	public String currency;
	/** srvTxCode */
	public String srvTxCode;
	/** 前导业务授权�? */
	public String lastSrvAuthCode;
	/** 验证�? */
	public String smsCode;
	/** 选项 */
	public String option;
	/** 起始日期 */
	public String startDate;
	/** 结束日期 */
	public String endDate;
	/** 交易种类 0-�?有交�? 1-转入交易 2-转出交易 9-指定交易类型 */
	public String type;
	/** 交易类型 */
	public String tranType;
	/** 页数 */
	public String pageNum;
	
	/** 自动投标总金额上限（不算已还金额�? */
	public String totAmount;
	/** 原订单号 */
	public String orgOrderId;
	/** 验证码授权码 */
	public String srvAuthCode;
	/** 交易利息 */
	public String intAmount;
	/** 还款手续�?:向融资人收取的手续费 */
	public String txFeeOut;
	/** 收款手续�?:向投资人收取的手续费 */
	public String txFeeIn;
	/** 对手电子账号:投资人账�? */
	public String forAccountId;
	/** 标的�? */
	public String productId;
	/** 授权�?:投资人投标成功的授权�? */
	public String authCode;
	/** 冻结资金�?�? */
	public String frzFlag;
	/** 是否使用红包 0-不使用红�?,1-使用红包 */
	public String bonusFlag;
	/** 抵扣红包金额 */
	public String bonusAmount;
	/** 路由代码0-本行通道 1-银联通道 2-人行通道�?-自动选择 */
	public String routeCode;
	/** 绑定银行联行号人民银行分配的12位联行号 routeCode=2，必�? 或�?�routeCode为空，但交易金额>20万，必输 */
	public String cardBankCnaps;
	/** 绑定银行中文名称 ,绑定的银行卡对应的银行中文名�? */
	public String cardBankNameCn;
	/** 绑定银行英文名称 ,绑定的银行卡对应的银行英文名称缩�? */
	public String cardBankNameEn;
	/** 绑定银行卡开户省�? ,绑定的银行卡的开户省�? */
	public String cardBankProvince;
	/** 绑定银行卡开户城�?,绑定的银行卡的开户城�? */
	public String cardBankCity;
	/** 忘记密码跳转 ,忘记密码的跳转URL */
	public String forgotPwdUrl;
	/** 绑定银行代码 ,绑定的银行卡对应的银行代�? */
	public String cardBankCode;
	/** 批次�? */
	public String batchNo;
	/** 交易笔数 */
	public String txCounts;
	/** 后台通知连接 */
	public String notifyURL;
	/** 业务结果通知 */
	public String retNotifyURL;
	/** 转让金额 卖出的�?�权金额 */
	public String tsfAmount;
	/** 原交易金�? 卖出方投标的原交易金额（或卖出方购买债权的原交易金额�? */
	public String orgTxAmount;
	/** 请求数组 */
	public String subPacks;
	/** 接收结果 */
	public String received;
	/** 签约状�?? 0：未签约 1：已签约 */
	public String state;
	/** 批次交易日期 */
	public String batchTxDate;

	/** 总记录数 �?�?10条记�? */
	public String totalItems;

	/** 投资手续�? */
	public String bidFee;
	/** 融资手续�? */
	public String debtFee;

	/**
	 * 查询类别1-按流水号查询（批次类交易不可用） 2-按订单号查询
	 */
	public String reqType;

	/** 查询交易代码 */
	public String reqTxCode;
	/** 查询交易日期 */
	public String reqTxDate;
	/** 查询交易时间 */
	public String reqTxTime;
	/** 查询交易流水�? */
	public String reqSeqNo;
	/** 查询订单�? */
	public String reqOrderId;
	/** 交易状�?�S-成功 F-失败 N-交易不存�? Z-未知 D-待处�? */
	public String txState;
	/** 可用余额 */
	public String availBal;
	/** 账面余额 账面余额-可用余额=冻结金额 */
	public String currBal;
	/** 标的描述 */
	public String productDesc;
	/** 募集�? */
	public String raiseDate;
	/** 募集结束日期 */
	public String raiseEndDate;
	/** 付息方式 0-到期与本金一起归�? 1-每月固定日期支付 2-每月不确定日期支�? */
	public String intType;
	/** 利息每月支付�? */
	public String intPayDay;
	/** 借款期限 */
	public String duration;
	/** 年化利率 */
	public String rate;
	/** 担保账户 */
	public String bailAccountId;
	/** 名义借款人电子帐�? */
	public String nominalAccountId;
	/** 是否使用交易描述 */
	public String desLineFlag;
	/** 交易描述 */
	public String desLine;
	/** 成功交易金额 */
	public String sucAmount;
	/** 成功交易笔数 */
	public String sucCounts;
	/** 失败交易金额 */
	public String failAmount;
	/** 失败交易笔数 */
	public String failCounts;
	/** 批次处理金额 */
	public String relAmount;
	/** 批次处理笔数 */
	public String relCounts;
	/** 批次处理状�?? */
	public String batchState;
	/** 批次交易代码 */
	public String batchTxCode;
	/** 失败描述 */
	public String failMsg;
	/** 账户类型 */
	public String accType;
	/** 提现�?�? */
	public String withdrawFlag;
	/** 是否设置过密�? */
	public String pinFlag;
	/** 对手姓名 */
	public String forName;
	/** 剩余可转让金�? */
	public String availAmount;
	/** 转让�?�? */
	public String txIncome;
	/**预期收益*/
	public String forIncome;
	/**投标日期*/
	public String buyDate;
	/**�?户日�?*/
	public String openDate;
	/**账户状�??*/
	public String acctState;
	/**冻结业务类别*/
	public String frzState;

	/**密码挂失状�??*/
	public String pinLosCd;
	/** 联行�?*/
	public String payAllianceCode;
	/** 原交易日�? */
	public String orgTxDate;
	/** 原交易时�? */
	public String orgTxTime;
	/** 原交易流水号 */
	public String orgSeqNo;
	/** 交易处理结果 */
	public String result;
	/** 冲正撤销标志 0:正常 1：已冲正/撤销*/
	public String orFlag;
	/**对公账号*/
	public String caccount;
	/**证件号码*/
	public String busId;
	/**税务登记�?*/ 
	public String taxId;
	/**冻结状�??*/
    public String frzType;
	/** 预期年化收益�?*/
	public String yield;
	/** 预期本息收益*/
	public String intTotal;
	/** 实际收益*/
	public String income;
	/** 实际收益符号*/
	public String incFlag;
	/** 交易描述 */
	public String description;
	
	/** 短信发�?�时�?*/
	public String sendTime;
	/** 短信序号*/
	public String smsSeq;
	/** 验证码有效时�?*/
	public String validTime;
	/** 签约订单�?*/
	public String contOrderId;
	/** 手续费金�?*/
	public String feeAmount;
	/** 风险准备�?*/
	public String riskAmount;
	/**申请订单�?*/
	public String lendPayOrderId;
	
	/**签约日期*/
    public String txnDate;
    /**签约时间*/
    public String txnTime;
    /**性别 M  男�??  F  女�??*/
    public String gender;
    /** 身份属�?? 1：出借角�?2：�?�款角色3：代偿角�?*/
    public String identity;

    
    //是否维护标志�? �?0�?1组成的标志位�?0表示不维护，1表示维护，共20位，�?1位表�?12域是否生效，�?2位表�?13域是否生效，其余以此类推�?
    private String bitMap;
    //�?通自动投标功能标�? 0：取�? 1：开�?
    private String autoBid;
    //�?通自动�?�转功能标志 0：取�? 1：开�?
    private String autoTransfer;
    //�?通预约取现功能标�? 0：取�? 1：开�?
    private String agreeWithdraw;
    //�?通无密消费功能标�? 0：取�? 1：开�?
    private String directConsume;
    /**返回交易页面链接*/
    private String transactionUrl ;
    /**订单有效性连�?*/
    private String verifyOrderUrl ;

    /** 备注 */
    public String remark;
    
    /**交易确认日期*/
    public String affirmDate;
    /**交易确认时间*/
    public String affirmTime;
    
    /**翻页标识 空：首次查询�?1：翻页查询；其它：非�?*/
    public String rtnInd;

    /**交易日期 翻页控制使用；首次查询上送空；翻页查询时上�?�上页返回的�?后一条记录交易日期；YYYYMMDD */
    public String inpDate;

    /**交易时间 翻页控制使用；首次查询上送空；翻页查询时上�?�上页返回的�?后一条记录交易时间；HH24MISSTT */
    public String inpTime;
    /**自然日期 翻页控制使用；首次查询上送空；翻页查询时上�?�上页返回的�?后一条记录自然日期；YYYYMMDD */
    public String relDate;
    /**流水�? 翻页控制使用；首次查询上送空；翻页查询时上�?�上页返回的�?后一条记录流水号�? */
    public String traceNo;

    /** 地址*/
    public String addr;

    /** 签约到期日期*/
    public String deadline;
    /** 签约到期日期*/
    public String bidDeadline;
    /*签约�?大金�?*/
    public String maxAmt;

    /** 缴费授权*/
    public String paymentAuth;

    /** 还款授权*/
    public String repayAuth;

    /** 自动投标到期�?*/
    public String autoBidDeadline;

    /** 自动投标签约�?高金�?*/
    public String autoBidMaxAmt;
    /** 缴费授权到期�?*/
    public String paymentDeadline;
    /** 缴费签约�?高金�?*/
    public String paymentMaxAmt;
    /** 还款授权到期�?*/
    public String repayDeadline;
    /** 还款签约�?高金�?*/
    public String repayMaxAmt;


    /** 代扣签约*/
    public String agreeDeduct;


    /** 签约取消日期*/
    public String cancelDate;
    /** cancelTime*/
    public String cancelTime;

    /** 验证码类�?*/
    public String smsType;

    //缴费授权
    public String txType;
    public String getTransactionUrl() {
        return transactionUrl;
    }

    public void setTransactionUrl(String transactionUrl) {
        this.transactionUrl = transactionUrl;
    }

    public String getVerifyOrderUrl() {
        return verifyOrderUrl;
    }

    public void setVerifyOrderUrl(String verifyOrderUrl) {
        this.verifyOrderUrl = verifyOrderUrl;
    }

    //保留�?
    private String reserved;
    
    //受托支付flg 0:否，1：是
    private String entrustFlag;
    //(受托)收款人电子账�?
    private String receiptAccountId;
    // 页面调用成功后跳转连�?
    private String successfulUrl;
    // 商户名称
    private String coinstName;
    
    public String getRestMsg() {
        return restMsg;
    }

    public void setRestMsg(String restMsg) {
        this.restMsg = restMsg;
    }
    
    
    public String getReserved() {
        return reserved;
    }


	public void setReserved(String reserved) {
        this.reserved = reserved;
    }
	public String getTxCode() {
        return txCode;
    }
    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }
    public String getInstCode() {
        return instCode;
    }
    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }
    public String getBankCode() {
        return bankCode;
    }
    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }
    public String getTxDate() {
        return txDate;
    }
    public void setTxDate(String txDate) {
        this.txDate = txDate;
    }
    public String getTxTime() {
        return txTime;
    }
    public void setTxTime(String txTime) {
        this.txTime = txTime;
    }
    public String getSeqNo() {
        return seqNo;
    }
    public void setSeqNo(String seqNo) {
        this.seqNo = seqNo;
    }
    public String getChannel() {
        return channel;
    }
    public void setChannel(String channel) {
        this.channel = channel;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getBitMap() {
        return bitMap;
    }
    public void setBitMap(String bitMap) {
        this.bitMap = bitMap;
    }
    public String getAutoBid() {
        return autoBid;
    }
    public void setAutoBid(String autoBid) {
        this.autoBid = autoBid;
    }
    public String getAutoTransfer() {
        return autoTransfer;
    }
    public void setAutoTransfer(String autoTransfer) {
        this.autoTransfer = autoTransfer;
    }
    public String getAgreeWithdraw() {
        return agreeWithdraw;
    }
    public void setAgreeWithdraw(String agreeWithdraw) {
        this.agreeWithdraw = agreeWithdraw;
    }
    public String getDirectConsume() {
        return directConsume;
    }
    public void setDirectConsume(String directConsume) {
        this.directConsume = directConsume;
    }
    
	public String getLendPayOrderId() {
		return lendPayOrderId;
	}

	public void setLendPayOrderId(String lendPayOrderId) {
		this.lendPayOrderId = lendPayOrderId;
	}

	public String getFeeAmount() {
		return feeAmount;
	}

	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}

	public String getRiskAmount() {
		return riskAmount;
	}

	public void setRiskAmount(String riskAmount) {
		this.riskAmount = riskAmount;
	}

	public String getCaccount() {
		return caccount;
	}

	public void setCaccount(String caccount) {
		this.caccount = caccount;
	}

	public String getBusId() {
		return busId;
	}

	public void setBusId(String busId) {
		this.busId = busId;
	}

	public String getTaxId() {
		return taxId;
	}

	public void setTaxId(String taxId) {
		this.taxId = taxId;
	}

	public String getOpenDate() {
		return openDate;
	}

	public void setOpenDate(String openDate) {
		this.openDate = openDate;
	}

	public String getAcctState() {
		return acctState;
	}

	public void setAcctState(String acctState) {
		this.acctState = acctState;
	}

	public String getFrzState() {
		return frzState;
	}

	public void setFrzState(String frzState) {
		this.frzState = frzState;
	}

	public String getPinLosCd() {
		return pinLosCd;
	}

	public void setPinLosCd(String pinLosCd) {
		this.pinLosCd = pinLosCd;
	}

	public String getForIncome() {
		return forIncome;
	}

	public void setForIncome(String forIncome) {
		this.forIncome = forIncome;
	}

	public String getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(String buyDate) {
		this.buyDate = buyDate;
	}

	public String getLogUserId() {
		return logUserId;
	}

	public void setLogUserId(String logUserId) {
		this.logUserId = logUserId;
	}

	public String getLogUserName() {
		return logUserName;
	}

	public void setLogUserName(String logUserName) {
		this.logUserName = logUserName;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogIp() {
		return logIp;
	}

	public void setLogIp(String logIp) {
		this.logIp = logIp;
	}

	public String getLogTime() {
		return logTime;
	}

	public void setLogTime(String logTime) {
		this.logTime = logTime;
	}

	public String getLogRemark() {
		return logRemark;
	}

	public void setLogRemark(String logRemark) {
		this.logRemark = logRemark;
	}

	public String getLogOrderId() {
		return logOrderId;
	}

	public void setLogOrderId(String logOrderId) {
		this.logOrderId = logOrderId;
	}

	public String getLogOrderDate() {
		return logOrderDate;
	}

	public void setLogOrderDate(String logOrderDate) {
		this.logOrderDate = logOrderDate;
	}

	public String getLogBankDetailUrl() {
		return logBankDetailUrl;
	}

	public void setLogBankDetailUrl(String logBankDetailUrl) {
		this.logBankDetailUrl = logBankDetailUrl;
	}

	public String getLogUuid() {
		return logUuid;
	}

	public void setLogUuid(String logUuid) {
		this.logUuid = logUuid;
	}

	public boolean isLogVerifyFlag() {
		return logVerifyFlag;
	}

	public void setLogVerifyFlag(boolean logVerifyFlag) {
		this.logVerifyFlag = logVerifyFlag;
	}

	public String getLogVerifyResult() {
		return logVerifyResult;
	}

	public void setLogVerifyResult(String logVerifyResult) {
		this.logVerifyResult = logVerifyResult;
	}

	public String getLogOrderStatus() {
		return logOrderStatus;
	}

	public void setLogOrderStatus(String logOrderStatus) {
		this.logOrderStatus = logOrderStatus;
	}

	public String getLogNotifyType() {
		return logNotifyType;
	}

	public void setLogNotifyType(String logNotifyType) {
		this.logNotifyType = logNotifyType;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(String txAmount) {
		this.txAmount = txAmount;
	}

	public String getTxFee() {
		return txFee;
	}

	public void setTxFee(String txFee) {
		this.txFee = txFee;
	}

	public String getRetUrl() {
		return retUrl;
	}

	public void setRetUrl(String retUrl) {
		this.retUrl = retUrl;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getUserIP() {
		return userIP;
	}

	public void setUserIP(String userIP) {
		this.userIP = userIP;
	}

	public String getAcqRes() {
		return acqRes;
	}

	public void setAcqRes(String acqRes) {
		this.acqRes = acqRes;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAcctUse() {
		return acctUse;
	}

	public void setAcctUse(String acctUse) {
		this.acctUse = acctUse;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSrvTxCode() {
		return srvTxCode;
	}

	public void setSrvTxCode(String srvTxCode) {
		this.srvTxCode = srvTxCode;
	}

	public String getLastSrvAuthCode() {
		return lastSrvAuthCode;
	}

	public void setLastSrvAuthCode(String lastSrvAuthCode) {
		this.lastSrvAuthCode = lastSrvAuthCode;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	public String getOption() {
		return option;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getTotAmount() {
		return totAmount;
	}

	public void setTotAmount(String totAmount) {
		this.totAmount = totAmount;
	}

	public String getOrgOrderId() {
		return orgOrderId;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}

	public String getSrvAuthCode() {
		return srvAuthCode;
	}

	public void setSrvAuthCode(String srvAuthCode) {
		this.srvAuthCode = srvAuthCode;
	}

	public String getIntAmount() {
		return intAmount;
	}

	public void setIntAmount(String intAmount) {
		this.intAmount = intAmount;
	}

	public String getTxFeeOut() {
		return txFeeOut;
	}

	public void setTxFeeOut(String txFeeOut) {
		this.txFeeOut = txFeeOut;
	}

	public String getTxFeeIn() {
		return txFeeIn;
	}

	public void setTxFeeIn(String txFeeIn) {
		this.txFeeIn = txFeeIn;
	}

	public String getForAccountId() {
		return forAccountId;
	}

	public void setForAccountId(String forAccountId) {
		this.forAccountId = forAccountId;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getFrzFlag() {
		return frzFlag;
	}

	public void setFrzFlag(String frzFlag) {
		this.frzFlag = frzFlag;
	}

	public String getRouteCode() {
		return routeCode;
	}

	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}

	public String getCardBankCnaps() {
		return cardBankCnaps;
	}

	public void setCardBankCnaps(String cardBankCnaps) {
		this.cardBankCnaps = cardBankCnaps;
	}

	public String getCardBankNameCn() {
		return cardBankNameCn;
	}

	public void setCardBankNameCn(String cardBankNameCn) {
		this.cardBankNameCn = cardBankNameCn;
	}

	public String getCardBankNameEn() {
		return cardBankNameEn;
	}

	public void setCardBankNameEn(String cardBankNameEn) {
		this.cardBankNameEn = cardBankNameEn;
	}

	public String getCardBankProvince() {
		return cardBankProvince;
	}

	public void setCardBankProvince(String cardBankProvince) {
		this.cardBankProvince = cardBankProvince;
	}

	public String getCardBankCity() {
		return cardBankCity;
	}

	public void setCardBankCity(String cardBankCity) {
		this.cardBankCity = cardBankCity;
	}

	public String getForgotPwdUrl() {
		return forgotPwdUrl;
	}

	public void setForgotPwdUrl(String forgotPwdUrl) {
		this.forgotPwdUrl = forgotPwdUrl;
	}

	public String getCardBankCode() {
		return cardBankCode;
	}

	public void setCardBankCode(String cardBankCode) {
		this.cardBankCode = cardBankCode;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getTxCounts() {
		return txCounts;
	}

	public void setTxCounts(String txCounts) {
		this.txCounts = txCounts;
	}

	public String getRetNotifyURL() {
		return retNotifyURL;
	}

	public void setRetNotifyURL(String retNotifyURL) {
		this.retNotifyURL = retNotifyURL;
	}

	public String getTsfAmount() {
		return tsfAmount;
	}

	public void setTsfAmount(String tsfAmount) {
		this.tsfAmount = tsfAmount;
	}

	public String getOrgTxAmount() {
		return orgTxAmount;
	}

	public String getMaxAmt() {
        return maxAmt;
    }


    public void setMaxAmt(String maxAmt) {
        this.maxAmt = maxAmt;
    }


    public void setOrgTxAmount(String orgTxAmount) {
		this.orgTxAmount = orgTxAmount;
	}

	public String getSubPacks() {
		return subPacks;
	}

	public void setSubPacks(String subPacks) {
		this.subPacks = subPacks;
	}

	public String getReceived() {
		return received;
	}

	public void setReceived(String received) {
		this.received = received;
	}

	public String getNotifyURL() {
		return notifyURL;
	}

	public void setNotifyURL(String notifyURL) {
		this.notifyURL = notifyURL;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getBatchTxDate() {
		return batchTxDate;
	}

	public void setBatchTxDate(String batchTxDate) {
		this.batchTxDate = batchTxDate;
	}

	public String getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(String totalItems) {
		this.totalItems = totalItems;
	}

	public int getLogClient() {
		return logClient;
	}

	public void setLogClient(int logClient) {
		this.logClient = logClient;
	}

	public String getReqType() {
		return reqType;
	}

	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	public String getReqTxCode() {
		return reqTxCode;
	}

	public void setReqTxCode(String reqTxCode) {
		this.reqTxCode = reqTxCode;
	}

	public String getReqTxDate() {
		return reqTxDate;
	}

	public void setReqTxDate(String reqTxDate) {
		this.reqTxDate = reqTxDate;
	}

	public String getReqTxTime() {
		return reqTxTime;
	}

	public void setReqTxTime(String reqTxTime) {
		this.reqTxTime = reqTxTime;
	}

	public String getReqSeqNo() {
		return reqSeqNo;
	}

	public void setReqSeqNo(String reqSeqNo) {
		this.reqSeqNo = reqSeqNo;
	}

	public String getReqOrderId() {
		return reqOrderId;
	}

	public void setReqOrderId(String reqOrderId) {
		this.reqOrderId = reqOrderId;
	}

	public String getTxState() {
		return txState;
	}

	public void setTxState(String txState) {
		this.txState = txState;
	}

	public String getAvailBal() {
		return availBal;
	}

	public void setAvailBal(String availBal) {
		this.availBal = availBal;
	}

	public String getProductDesc() {
		return productDesc;
	}

	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}

	public String getRaiseDate() {
		return raiseDate;
	}

	public void setRaiseDate(String raiseDate) {
		this.raiseDate = raiseDate;
	}

	public String getRaiseEndDate() {
		return raiseEndDate;
	}

	public void setRaiseEndDate(String raiseEndDate) {
		this.raiseEndDate = raiseEndDate;
	}

	public String getIntType() {
		return intType;
	}

	public void setIntType(String intType) {
		this.intType = intType;
	}

	public String getIntPayDay() {
		return intPayDay;
	}

	public void setIntPayDay(String intPayDay) {
		this.intPayDay = intPayDay;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getBailAccountId() {
		return bailAccountId;
	}

	public void setBailAccountId(String bailAccountId) {
		this.bailAccountId = bailAccountId;
	}

	public String getNominalAccountId() {
		return nominalAccountId;
	}

	public void setNominalAccountId(String nominalAccountId) {
		this.nominalAccountId = nominalAccountId;
	}

	public String getCurrBal() {
		return currBal;
	}

	public void setCurrBal(String currBal) {
		this.currBal = currBal;
	}

	public String getBonusFlag() {
		return bonusFlag;
	}

	public void setBonusFlag(String bonusFlag) {
		this.bonusFlag = bonusFlag;
	}

	public String getBonusAmount() {
		return bonusAmount;
	}

	public void setBonusAmount(String bonusAmount) {
		this.bonusAmount = bonusAmount;
	}

	public String getDesLineFlag() {
		return desLineFlag;
	}

	public void setDesLineFlag(String desLineFlag) {
		this.desLineFlag = desLineFlag;
	}

	public String getDesLine() {
		return desLine;
	}

	public void setDesLine(String desLine) {
		this.desLine = desLine;
	}

	public String getSucAmount() {
		return sucAmount;
	}

	public void setSucAmount(String sucAmount) {
		this.sucAmount = sucAmount;
	}

	public String getSucCounts() {
		return sucCounts;
	}

	public void setSucCounts(String sucCounts) {
		this.sucCounts = sucCounts;
	}

	public String getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(String failAmount) {
		this.failAmount = failAmount;
	}

	public String getFailCounts() {
		return failCounts;
	}

	public void setFailCounts(String failCounts) {
		this.failCounts = failCounts;
	}

	public String getRelAmount() {
		return relAmount;
	}

	public void setRelAmount(String relAmount) {
		this.relAmount = relAmount;
	}

	public String getRelCounts() {
		return relCounts;
	}

	public void setRelCounts(String relCounts) {
		this.relCounts = relCounts;
	}

	public String getBatchState() {
		return batchState;
	}

	public void setBatchState(String batchState) {
		this.batchState = batchState;
	}

	public String getFailMsg() {
		return failMsg;
	}

	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}

	public String getBatchTxCode() {
		return batchTxCode;
	}

	public void setBatchTxCode(String batchTxCode) {
		this.batchTxCode = batchTxCode;
	}

	public String getBidFee() {
		return bidFee;
	}

	public void setBidFee(String bidFee) {
		this.bidFee = bidFee;
	}

	public String getDebtFee() {
		return debtFee;
	}

	public void setDebtFee(String debtFee) {
		this.debtFee = debtFee;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getWithdrawFlag() {
		return withdrawFlag;
	}

	public void setWithdrawFlag(String withdrawFlag) {
		this.withdrawFlag = withdrawFlag;
	}

	public String getPinFlag() {
		return pinFlag;
	}

	public void setPinFlag(String pinFlag) {
		this.pinFlag = pinFlag;
	}

	public String getForName() {
		return forName;
	}

	public void setForName(String forName) {
		this.forName = forName;
	}

	public String getAvailAmount() {
		return availAmount;
	}

	public void setAvailAmount(String availAmount) {
		this.availAmount = availAmount;
	}

	public String getTxIncome() {
		return txIncome;
	}

	public void setTxIncome(String txIncome) {
		this.txIncome = txIncome;
	}

	public String getPayAllianceCode() {
		return payAllianceCode;
	}

	public void setPayAllianceCode(String payAllianceCode) {
		this.payAllianceCode = payAllianceCode;
	}

	public String getOrgTxDate() {
		return orgTxDate;
	}

	public void setOrgTxDate(String orgTxDate) {
		this.orgTxDate = orgTxDate;
	}

	public String getOrgTxTime() {
		return orgTxTime;
	}

	public void setOrgTxTime(String orgTxTime) {
		this.orgTxTime = orgTxTime;
	}

	public String getOrgSeqNo() {
		return orgSeqNo;
	}

	public void setOrgSeqNo(String orgSeqNo) {
		this.orgSeqNo = orgSeqNo;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOrFlag() {
		return orFlag;
	}

	public void setOrFlag(String orFlag) {
		this.orFlag = orFlag;
	}

	public String getYield() {
		return yield;
	}

	public void setYield(String yield) {
		this.yield = yield;
	}

	public String getIntTotal() {
		return intTotal;
	}

	public void setIntTotal(String intTotal) {
		this.intTotal = intTotal;
	}

	public String getIncome() {
		return income;
	}

	public void setIncome(String income) {
		this.income = income;
	}

	public String getIncFlag() {
		return incFlag;
	}

	public void setIncFlag(String incFlag) {
		this.incFlag = incFlag;
	}

	public String getSendTime() {
		return sendTime;
	}

	public void setSendTime(String sendTime) {
		this.sendTime = sendTime;
	}

	public String getSmsSeq() {
		return smsSeq;
	}

	public void setSmsSeq(String smsSeq) {
		this.smsSeq = smsSeq;
	}

	public String getValidTime() {
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getContOrderId() {
		return contOrderId;
	}

	public void setContOrderId(String contOrderId) {
		this.contOrderId = contOrderId;
	}

    public String getTxnDate() {
        return txnDate;
    }

    public void setTxnDate(String txnDate) {
        this.txnDate = txnDate;
    }

    public String getTxnTime() {
        return txnTime;
    }

    public void setTxnTime(String txnTime) {
        this.txnTime = txnTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
	

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEntrustFlag() {
		return entrustFlag;
	}

	public void setEntrustFlag(String entrustFlag) {
		this.entrustFlag = entrustFlag;
	}

	public String getReceiptAccountId() {
		return receiptAccountId;
	}

	public void setReceiptAccountId(String receiptAccountId) {
		this.receiptAccountId = receiptAccountId;
	}

    public String getAffirmDate() {
        return affirmDate;
    }

    public void setAffirmDate(String affirmDate) {
        this.affirmDate = affirmDate;
    }

    public String getAffirmTime() {
        return affirmTime;
    }

    public void setAffirmTime(String affirmTime) {
        this.affirmTime = affirmTime;
    }

    public String getRtnInd() {
        return rtnInd;
    }

    public void setRtnInd(String rtnInd) {
        this.rtnInd = rtnInd;
    }

    public String getInpDate() {
        return inpDate;
    }

    public void setInpDate(String inpDate) {
        this.inpDate = inpDate;
    }

    public String getInpTime() {
        return inpTime;
    }

    public void setInpTime(String inpTime) {
        this.inpTime = inpTime;
    }

    public String getRelDate() {
        return relDate;
    }

    public void setRelDate(String relDate) {
        this.relDate = relDate;
    }

    public String getTraceNo() {
        return traceNo;
    }

    public void setTraceNo(String traceNo) {
        this.traceNo = traceNo;
    }


    public String getSuccessfulUrl() {
        return successfulUrl;
    }


    public void setSuccessfulUrl(String successfulUrl) {
        this.successfulUrl = successfulUrl;
    }
    public String getGender() {
        return gender;
    }


    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getIdentity() {
        return identity;
    }


    public void setIdentity(String identity) {
        this.identity = identity;
    }


    public String getAddr() {
        return addr;
    }


    public void setAddr(String addr) {
        this.addr = addr;
    }


    public String getDeadline() {
        return deadline;
    }


    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }


    public String getBidDeadline() {
        return bidDeadline;
    }


    public void setBidDeadline(String bidDeadline) {
        this.bidDeadline = bidDeadline;
    }


    public String getCoinstName() {
        return coinstName;
    }


    public void setCoinstName(String coinstName) {
        this.coinstName = coinstName;
    }


    public String getPaymentAuth() {
        return paymentAuth;
    }


    public void setPaymentAuth(String paymentAuth) {
        this.paymentAuth = paymentAuth;
    }


    public String getRepayAuth() {
        return repayAuth;
    }


    public void setRepayAuth(String repayAuth) {
        this.repayAuth = repayAuth;
    }


    public String getAutoBidDeadline() {
        return autoBidDeadline;
    }


    public void setAutoBidDeadline(String autoBidDeadline) {
        this.autoBidDeadline = autoBidDeadline;
    }


    public String getAutoBidMaxAmt() {
        return autoBidMaxAmt;
    }


    public void setAutoBidMaxAmt(String autoBidMaxAmt) {
        this.autoBidMaxAmt = autoBidMaxAmt;
    }


    public String getPaymentDeadline() {
        return paymentDeadline;
    }


    public void setPaymentDeadline(String paymentDeadline) {
        this.paymentDeadline = paymentDeadline;
    }


    public String getPaymentMaxAmt() {
        return paymentMaxAmt;
    }


    public void setPaymentMaxAmt(String paymentMaxAmt) {
        this.paymentMaxAmt = paymentMaxAmt;
    }


    public String getRepayDeadline() {
        return repayDeadline;
    }


    public void setRepayDeadline(String repayDeadline) {
        this.repayDeadline = repayDeadline;
    }


    public String getRepayMaxAmt() {
        return repayMaxAmt;
    }


    public void setRepayMaxAmt(String repayMaxAmt) {
        this.repayMaxAmt = repayMaxAmt;
    }


    public String getAgreeDeduct() {
        return agreeDeduct;
    }


    public void setAgreeDeduct(String agreeDeduct) {
        this.agreeDeduct = agreeDeduct;
    }
    public String getCancelDate() {
        return cancelDate;
    }


    public void setCancelDate(String cancelDate) {
        this.cancelDate = cancelDate;
    }


    public String getCancelTime() {
        return cancelTime;
    }


    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }


    public String getSmsType() {
        return smsType;
    }


    public void setSmsType(String smsType) {
        this.smsType = smsType;
    }




	public String getTxType() {
		return txType;
	}


	public void setTxType(String txType) {
		this.txType = txType;
	}


	public String getFrzType() {
		return frzType;
	}


	public void setFrzType(String frzType) {
		this.frzType = frzType;
	}

}
