package com.hyjf.pay.lib.chinapnr;

import java.io.Serializable;

import com.hyjf.pay.lib.PnrApiBean;


public class ChinapnrBean extends PnrApiBean implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1319285566828004025L;

    /** 版本号 */
    public String Version;

    /** 消息类型 */
    public String CmdId;

    /** 2.0异步对账返回消息类型 */
    public String RespType;

    /** 项目id */
    public String ProId;

    /** 商户客户号 */
    public String MerCustId;

    /** 商户后台应答地址 */
    public String BgRetUrl;

    /** 页面返回URL */
    public String RetUrl;

    /** 用户号 */
    public String UsrId;

    /** 真实名称 */
    public String UsrName;

    /** 证件类型 */
    public String IdType;

    /** 证件号码 */
    public String IdNo;

    /** 手机号 */
    public String UsrMp;

    /** 用户Email */
    public String UsrEmail;

    /** 商户私有域 */
    public String MerPriv;

    /** 页面类型 */
    public String PageType;

    /** 编码集 */
    public String CharSet;

    /** 签名 */
    public String ChkValue;

    /** 应答返回码 */
    public String RespCode;

    /** 应答描述 */
    public String RespDesc;

    /** 本平台交易唯一标识 */
    public String TrxId;

    /** 用户客户号 */
    public String UsrCustId;
    
    /** 用户客户号，定向转账授权 绑定客户号 */
    public String InUsrCustId;

    /** 用户客户号,解绑快捷卡中用到 */
    public String CustId;

    /** 用户银行卡,解绑快捷卡中用到 */
    public String BankId;

    /** 用户银行卡,解绑快捷卡中用到 */
    public String ExpressFlag;

    /** 开户银行账号 */
    public String CardId;

    /** 订单号 */
    public String OrdId;

    /** 订单日期 */
    public String OrdDate;

    /** 支付网关业务代号 */
    public String GateBusiId;

    /** 开户银行代号 */
    public String OpenBankId;

    /** 借贷记标记 */
    public String DcFlag;

    /** 交易金额 */
    public String TransAmt;

    /** 还款本金*/
    public String PrincipalAmt;
    
    /** 还款利息*/
    public String InterestAmt;

    /** 银行卡号 */
    public String OpenAcctId;

    /** 身份证号 */
    public String CertId;

    /** 子账户类型 */
    public String SubAcctType;

    /** 子账户号 */
    public String SubAcctId;

    /** 最大投资手续费率 */
    public String MaxTenderRate;

    /** 借款人信息 */
    public String BorrowerDetails;

    /** 借款人客户号 */
    public String BorrowerCustId;

    /** 借款金额 */
    public String BorrowerAmt;

    /** 借款手续费率 */
    public String BorrowerRate;

    /** 是否冻结 */
    public String IsFreeze;

    /** 冻结订单号 */
    public String FreezeOrdId;

    /** 入参扩展域 */
    public String ReqExt;

    /** 是否解冻 */
    public String IsUnFreeze;

    /** 解冻订单号 */
    public String UnFreezeOrdId;

    /** 冻结标识 */
    public String FreezeTrxId;

    /** 出账客户号 */
    public String OutCustId;

    /** 扣款手续费 */
    public String Fee;

    /** 手续费金额 */
    public String FeeAmt;

    /** 从属订单号 */
    public String SubOrdId;

    /** 从属订单日期 */
    public String SubOrdDate;

    /** 入账客户号 */
    public String InCustId;

    /** 分账账户串 */
    public String DivDetails;

    /** 是否默认 */
    public String IsDefault;

    /** 续费收取对象标志I/O */
    public String FeeObjFlag;

    /** 出账子账户 */
    public String OutAcctId;

    /** 入账子账户 */
    public String InAcctId;

    /** 入账账户类型 */
    public String InAcctType;

    /** 备注 */
    public String Remark;

    /** 商户收取服务费金额 */
    public String ServFee;

    /** 商户子账户号 */
    public String ServFeeAcctId;

    /** 转让人客户号 */
    public String SellCustId;

    /** 转让金额 */
    public String CreditAmt;

    /** 承接金额 */
    public String CreditDealAmt;

    /** 债权转让明细 */
    public String BidDetails;

    /** 挂牌债权 ID */
    public String LcId;

    /** 挂牌债权总金额 */
    public String TotalLcAmt;

    /** 承接人客户号 */
    public String BuyCustId;

    /** 开始时间 */
    public String BeginDate;

    /** 结束时间 */
    public String EndDate;

    /** 页数 */
    public String PageNum;

    /** 每页记录数 */
    public String PageSize;

    /** 交易查询类型 */
    public String QueryTransType;

    /** 记录总条数 */
    public String TotalItems;

    /** 实际到账金额 */
    public String RealTransAmt;

    /** 分账商户号 */
    public String DivCustID;

    /** 分账账户号 */
    public String DivAcctId;

    /** 分账金额 */
    public String DivAmt;

    /** 账务结果串 */
    public String AcctDetails;

    /** 可用余额 */
    public String AvlBal;

    /** 账户余额 */
    public String AcctBal;

    /** 冻结余额 */
    public String FrzBal;

    /** 开户银行代号 */
    public String GateBankId;

    /** 汇付交易状态 */
    public String TransStat;

    /** 查询相应的用户的用户的状态 */
    public String UsrStat;

    /** 用户的银行卡列表 */
    public String UsrCardInfolist;

    /** 取现对账结果串 */
    public String CashReconciliationDtoList;
    
    /** 快捷充值限额信息列表 */
    public String PayQuotaDetails;
    
    //=================标的信息录入接口
    /** 标的信息录入接口-标的名称 */
    public String BidName;
    /** 标的信息录入接口-标的类型 */
    public String BidType;
    /** 标的信息录入接口-发表金额 */
    public String BorrTotAmt;
    /** 标的信息录入接口-发表年化利率 */
    public String YearRate;
    /** 标的信息录入接口-应还款总利息 */
    public String RetInterest;
    /** 标的信息录入接口-最后还款日期 */
    public String LastRetDate;
    /** 标的信息录入接口-计划投标开始日期 */
    public String BidStartDate;
    /** 标的信息录入接口-计划投标截止日期 */
    public String BidEndDate;
    /** 标的信息录入接口-借款期限 */
    public String LoanPeriod;
    /** 标的信息录入接口-还款方式 */
    public String RetType;
    /** 标的信息录入接口-应还款日期 */
    public String RetDate;
    /** 标的信息录入接口-借款用途 */
    public String BorrPurpose;
    /** 标的信息录入接口-本息保障 */
    public String GuarantType;
    /** 标的信息录入接口-标的产品类型 */
    public String BidProdType;
    /** 标的信息录入接口-风险控制方式 */
    public String RiskCtlType;
    /** 标的信息录入接口-限定最低投标份数 */
    public String LimitMinBidAmt;
    /** 标的信息录入接口-限定每份投标金额 */
    public String LimitBidSum;
    /** 标的信息录入接口-限定最多投标金额 */
    public String LimitMaxBidSum;
    /** 标的信息录入接口-限定最少投标金额 */
    public String LimitMinBidSum;
    /** 标的信息录入接口-逾期是否垫资 */
    public String BidPayforState;
    /** 标的信息录入接口-借款人类型 */
    public String BorrType;
    /** 标的信息录入接口-借款人ID */
    public String BorrCustId;
    /** 标的信息录入接口-借款人名称(借款人真实姓名或者借款企业名称) */
    public String BorrName;
    /** 标的信息录入接口-借款企业营业执照编号 */
    public String BorrBusiCode;
    /** 标的信息录入接口-借款人证件类型 */
    public String BorrCertType;
    /** 标的信息录入接口-借款人证件号码 */
    public String BorrCertId;
    /** 标的信息录入接口-借款人手机号码 */
    public String BorrMobiPhone;
    /** 标的信息录入接口-借款人固定电话 */
    public String BorrPhone;
    /** 标的信息录入接口-借款人工作年限 */
    public String BorrWorkYear;
    /** 标的信息录入接口-借款人税后月收入 */
    public String BorrIncome;
    /** 标的信息录入接口-借款人婚姻状态 */
    public String BorrMarriage;
    /** 标的信息录入接口-借款人点子邮箱 */
    public String BorrEmail;
    public String Status;

    // 提现2.0接口 补充
    /** 手续费扣款客户号 */
    public String FeeCustId;

    /** 手续费扣款子账户号 */
    public String FeeAcctId;

    /** 返参扩展域 */
    public String RespExt;

    /** 二级应答返回码 */
    public String SecRespCode;

    /** 二级应答描述 */
    public String SecRespDesc;

    /** 营业执照编号 */
    public String BusiCode;

    /** 审核状态 */
    public String AuditStat;

    /** 审核状态描述 */
    public String AuditDesc;

    /**定向支付授权接口用*/
    public String AuthAmt;

    // 写日志用变量
    /** 类型(日志用) */
    private String type;

    /** 类型(日志用) */
    private String logType;

    /** 描述(日志用) */
    private String logRemark;

    /** 借款编号(日志用) */
    private String logBorrowNid;

    /** 用户ID(日志用) */
    private Integer logUserId;

    /** IP地址(日志用) */
    private String logIp;

    /** Client(日志用) */
    private String logClient;

    // 其他作用
    /** 验签结果 */
    private boolean verifyFlag;

    /** 验签返回内容 */
    private String verifyResult;

    /** UUID */
    private String uuid;

    /**  */
    private String chkValueStatus;

    /** 各功能的页面返回URL */
    private String realRetUrl;
    
    /** 投标计划类型P--部分授权
				W--完全授权 */
    private String TenderPlanType;
    
    /** 企业开户担保类型 是否担保类型，Y：是 N：否 */
    public String GuarType;
    
    /** 担保企业还款:垫资/代偿对象*/
    public String DzObject;
    
    public String getChkValueStatus() {
        return chkValueStatus;
    }

    public void setChkValueStatus(String chkValueStatus) {
        this.chkValueStatus = chkValueStatus;
    }

    public String getDivCustID() {
        return DivCustID;
    }

    public void setDivCustID(String divCustID) {
        DivCustID = divCustID;
    }

    public String getDivAcctId() {
        return DivAcctId;
    }

    public void setDivAcctId(String divAcctId) {
        DivAcctId = divAcctId;
    }

    public String getDivAmt() {
        return DivAmt;
    }

    public void setDivAmt(String divAmt) {
        DivAmt = divAmt;
    }

    public String getVersion() {
        return Version;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public String getRespType() {
        return RespType;
    }

    public void setRespType(String respType) {
        RespType = respType;
    }

    public String getCmdId() {
        return CmdId;
    }

    public void setCmdId(String cmdId) {
        CmdId = cmdId;
    }

    public String getMerCustId() {
        return MerCustId;
    }

    public void setMerCustId(String merCustId) {
        MerCustId = merCustId;
    }

    public String getBgRetUrl() {
        return BgRetUrl;
    }

    public void setBgRetUrl(String bgRetUrl) {
        BgRetUrl = bgRetUrl;
    }

    public String getRetUrl() {
        return RetUrl;
    }

    public void setRetUrl(String retUrl) {
        RetUrl = retUrl;
    }

    public String getUsrId() {
        return UsrId;
    }

    public void setUsrId(String usrId) {
        UsrId = usrId;
    }

    public String getUsrName() {
        return UsrName;
    }

    public void setUsrName(String usrName) {
        UsrName = usrName;
    }

    public String getIdType() {
        return IdType;
    }

    public void setIdType(String idType) {
        IdType = idType;
    }

    public String getIdNo() {
        return IdNo;
    }

    public void setIdNo(String idNo) {
        IdNo = idNo;
    }

    public String getUsrMp() {
        return UsrMp;
    }

    public void setUsrMp(String usrMp) {
        UsrMp = usrMp;
    }

    public String getUsrEmail() {
        return UsrEmail;
    }

    public void setUsrEmail(String usrEmail) {
        UsrEmail = usrEmail;
    }

    public String getMerPriv() {
        return MerPriv;
    }

    public void setMerPriv(String merPriv) {
        MerPriv = merPriv;
    }

    public String getPageType() {
        return PageType;
    }

    public void setPageType(String pageType) {
        PageType = pageType;
    }

    public String getCharSet() {
        return CharSet;
    }

    public void setCharSet(String charSet) {
        CharSet = charSet;
    }

    public String getChkValue() {
        return ChkValue;
    }

    public void setChkValue(String chkValue) {
        ChkValue = chkValue;
    }

    public String getRespCode() {
        return RespCode;
    }

    public void setRespCode(String respCode) {
        RespCode = respCode;
    }

    public String getRespDesc() {
        return RespDesc;
    }

    public void setRespDesc(String respDesc) {
        RespDesc = respDesc;
    }

    public String getTrxId() {
        return TrxId;
    }

    public void setTrxId(String trxId) {
        TrxId = trxId;
    }

    public String getUsrCustId() {
        return UsrCustId;
    }

    public void setUsrCustId(String usrCustId) {
        UsrCustId = usrCustId;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }

    public String getBankId() {
        return BankId;
    }

    public void setBankId(String bankId) {
        BankId = bankId;
    }

    public String getExpressFlag() {
        return ExpressFlag;
    }

    public void setExpressFlag(String expressFlag) {
        ExpressFlag = expressFlag;
    }

    public String getCardId() {
        return CardId;
    }

    public void setCardId(String cardId) {
        CardId = cardId;
    }

    public String getOrdId() {
        return OrdId;
    }

    public void setOrdId(String ordId) {
        OrdId = ordId;
    }

    public String getOrdDate() {
        return OrdDate;
    }

    public void setOrdDate(String ordDate) {
        OrdDate = ordDate;
    }

    public String getGateBusiId() {
        return GateBusiId;
    }

    public void setGateBusiId(String gateBusiId) {
        GateBusiId = gateBusiId;
    }

    public String getOpenBankId() {
        return OpenBankId;
    }

    public void setOpenBankId(String openBankId) {
        OpenBankId = openBankId;
    }

    public String getDcFlag() {
        return DcFlag;
    }

    public void setDcFlag(String dcFlag) {
        DcFlag = dcFlag;
    }

    public String getTransAmt() {
        return TransAmt;
    }

    public void setTransAmt(String transAmt) {
        TransAmt = transAmt;
    }

    public String getOpenAcctId() {
        return OpenAcctId;
    }

    public void setOpenAcctId(String openAcctId) {
        OpenAcctId = openAcctId;
    }

    public String getCertId() {
        return CertId;
    }

    public void setCertId(String certId) {
        CertId = certId;
    }

    public String getSubAcctType() {
        return SubAcctType;
    }

    public void setSubAcctType(String subAcctType) {
        SubAcctType = subAcctType;
    }

    public String getSubAcctId() {
        return SubAcctId;
    }

    public void setSubAcctId(String subAcctId) {
        SubAcctId = subAcctId;
    }

    public String getMaxTenderRate() {
        return MaxTenderRate;
    }

    public void setMaxTenderRate(String maxTenderRate) {
        MaxTenderRate = maxTenderRate;
    }

    public String getBorrowerDetails() {
        return BorrowerDetails;
    }

    public void setBorrowerDetails(String borrowerDetails) {
        BorrowerDetails = borrowerDetails;
    }

    public String getBorrowerCustId() {
        return BorrowerCustId;
    }

    public void setBorrowerCustId(String borrowerCustId) {
        BorrowerCustId = borrowerCustId;
    }

    public String getBorrowerAmt() {
        return BorrowerAmt;
    }

    public void setBorrowerAmt(String borrowerAmt) {
        BorrowerAmt = borrowerAmt;
    }

    public String getBorrowerRate() {
        return BorrowerRate;
    }

    public void setBorrowerRate(String borrowerRate) {
        BorrowerRate = borrowerRate;
    }

    public String getIsFreeze() {
        return IsFreeze;
    }

    public void setIsFreeze(String isFreeze) {
        IsFreeze = isFreeze;
    }

    public String getFreezeOrdId() {
        return FreezeOrdId;
    }

    public void setFreezeOrdId(String freezeOrdId) {
        FreezeOrdId = freezeOrdId;
    }

    public String getReqExt() {
        return ReqExt;
    }

    public void setReqExt(String reqExt) {
        ReqExt = reqExt;
    }

    public String getIsUnFreeze() {
        return IsUnFreeze;
    }

    public void setIsUnFreeze(String isUnFreeze) {
        IsUnFreeze = isUnFreeze;
    }

    public String getUnFreezeOrdId() {
        return UnFreezeOrdId;
    }

    public void setUnFreezeOrdId(String unFreezeOrdId) {
        UnFreezeOrdId = unFreezeOrdId;
    }

    public String getFreezeTrxId() {
        return FreezeTrxId;
    }

    public void setFreezeTrxId(String freezeTrxId) {
        FreezeTrxId = freezeTrxId;
    }

    public String getOutCustId() {
        return OutCustId;
    }

    public void setOutCustId(String outCustId) {
        OutCustId = outCustId;
    }

    public String getFee() {
        return Fee;
    }

    public void setFee(String fee) {
        Fee = fee;
    }

    public String getSubOrdId() {
        return SubOrdId;
    }

    public void setSubOrdId(String subOrdId) {
        SubOrdId = subOrdId;
    }

    public String getSubOrdDate() {
        return SubOrdDate;
    }

    public void setSubOrdDate(String subOrdDate) {
        SubOrdDate = subOrdDate;
    }

    public String getInCustId() {
        return InCustId;
    }

    public void setInCustId(String inCustId) {
        InCustId = inCustId;
    }

    public String getDivDetails() {
        return DivDetails;
    }

    public void setDivDetails(String divDetails) {
        DivDetails = divDetails;
    }

    public String getIsDefault() {
        return IsDefault;
    }

    public void setIsDefault(String isDefault) {
        IsDefault = isDefault;
    }

    public String getFeeObjFlag() {
        return FeeObjFlag;
    }

    public void setFeeObjFlag(String feeObjFlag) {
        FeeObjFlag = feeObjFlag;
    }

    public String getOutAcctId() {
        return OutAcctId;
    }

    public void setOutAcctId(String outAcctId) {
        OutAcctId = outAcctId;
    }

    public String getInAcctId() {
        return InAcctId;
    }

    public void setInAcctId(String inAcctId) {
        InAcctId = inAcctId;
    }

    public String getInAcctType() {
        return InAcctType;
    }

    public void setInAcctType(String inAcctType) {
        InAcctType = inAcctType;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        this.Remark = remark;
    }

    public String getServFee() {
        return ServFee;
    }

    public void setServFee(String servFee) {
        ServFee = servFee;
    }

    public String getServFeeAcctId() {
        return ServFeeAcctId;
    }

    public void setServFeeAcctId(String servFeeAcctId) {
        ServFeeAcctId = servFeeAcctId;
    }

    public String getSellCustId() {
        return SellCustId;
    }

    public void setSellCustId(String sellCustId) {
        SellCustId = sellCustId;
    }

    public String getCreditAmt() {
        return CreditAmt;
    }

    public void setCreditAmt(String creditAmt) {
        CreditAmt = creditAmt;
    }

    public String getCreditDealAmt() {
        return CreditDealAmt;
    }

    public void setCreditDealAmt(String creditDealAmt) {
        CreditDealAmt = creditDealAmt;
    }

    public String getBidDetails() {
        return BidDetails;
    }

    public void setBidDetails(String bidDetails) {
        BidDetails = bidDetails;
    }

    public String getLcId() {
        return LcId;
    }

    public void setLcId(String lcId) {
        LcId = lcId;
    }

    public String getTotalLcAmt() {
        return TotalLcAmt;
    }

    public void setTotalLcAmt(String totalLcAmt) {
        TotalLcAmt = totalLcAmt;
    }

    public String getBuyCustId() {
        return BuyCustId;
    }

    public void setBuyCustId(String buyCustId) {
        BuyCustId = buyCustId;
    }

    public String getBeginDate() {
        return BeginDate;
    }

    public void setBeginDate(String beginDate) {
        BeginDate = beginDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getPageNum() {
        return PageNum;
    }

    public void setPageNum(String pageNum) {
        PageNum = pageNum;
    }

    public String getPageSize() {
        return PageSize;
    }

    public void setPageSize(String pageSize) {
        PageSize = pageSize;
    }

    public String getQueryTransType() {
        return QueryTransType;
    }

    public void setQueryTransType(String queryTransType) {
        QueryTransType = queryTransType;
    }

    public String getTotalItems() {
        return TotalItems;
    }

    public void setTotalItems(String totalItems) {
        TotalItems = totalItems;
    }

    public String getFeeAmt() {
        return FeeAmt;
    }

    public void setFeeAmt(String feeAmt) {
        FeeAmt = feeAmt;
    }

    public String getRealTransAmt() {
        return RealTransAmt;
    }

    public void setRealTransAmt(String realTransAmt) {
        RealTransAmt = realTransAmt;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public String getLogRemark() {
        return logRemark;
    }

    public void setLogRemark(String logRemark) {
        this.logRemark = logRemark;
    }

    public String getLogBorrowNid() {
        return logBorrowNid;
    }

    public void setLogBorrowNid(String logBorrowNid) {
        this.logBorrowNid = logBorrowNid;
    }

    public Integer getLogUserId() {
        return logUserId;
    }

    public void setLogUserId(Integer logUserId) {
        this.logUserId = logUserId;
    }

    public String getLogIp() {
        return logIp;
    }

    public void setLogIp(String logIp) {
        this.logIp = logIp;
    }

    public String getRealRetUrl() {
        return realRetUrl;
    }

    public void setRealRetUrl(String realRetUrl) {
        this.realRetUrl = realRetUrl;
    }

    public String getTenderPlanType() {
		return TenderPlanType;
	}

	public void setTenderPlanType(String tenderPlanType) {
		TenderPlanType = tenderPlanType;
	}

	public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProId() {
        return ProId;
    }

    public void setProId(String proId) {
        ProId = proId;
    }

    public boolean isVerifyFlag() {
        return verifyFlag;
    }

    public void setVerifyFlag(boolean verifyFlag) {
        this.verifyFlag = verifyFlag;
    }

    public String getVerifyResult() {
        return verifyResult;
    }

    public void setVerifyResult(String verifyResult) {
        this.verifyResult = verifyResult;
    }

    public String getLogClient() {
        return logClient;
    }

    public void setLogClient(String logClient) {
        this.logClient = logClient;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getAcctDetails() {
		return AcctDetails;
	}

	public void setAcctDetails(String acctDetails) {
		AcctDetails = acctDetails;
	}

	public String getAvlBal() {
        return AvlBal;
    }

    public void setAvlBal(String avlBal) {
        AvlBal = avlBal;
    }

    public String getAcctBal() {
        return AcctBal;
    }

    public void setAcctBal(String acctBal) {
        AcctBal = acctBal;
    }

    public String getFrzBal() {
        return FrzBal;
    }

    public void setFrzBal(String frzBal) {
        FrzBal = frzBal;
    }

    public String getGateBankId() {
        return GateBankId;
    }

    public void setGateBankId(String gateBankId) {
        GateBankId = gateBankId;
    }

    public String getTransStat() {
        return TransStat;
    }

    public void setTransStat(String transStat) {
        TransStat = transStat;
    }

    public String getUsrStat() {
        return UsrStat;
    }

    public void setUsrStat(String usrStat) {
        UsrStat = usrStat;
    }

    public String getUsrCardInfolist() {
        return UsrCardInfolist;
    }

    public void setUsrCardInfolist(String usrCardInfolist) {
        UsrCardInfolist = usrCardInfolist;
    }

    public String getCashReconciliationDtoList() {
        return CashReconciliationDtoList;
    }

    public void setCashReconciliationDtoList(String cashReconciliationDtoList) {
        CashReconciliationDtoList = cashReconciliationDtoList;
    }

    public String getBidName() {
		return BidName;
	}

	public void setBidName(String bidName) {
		BidName = bidName;
	}

	public String getBidType() {
		return BidType;
	}

	public void setBidType(String bidType) {
		BidType = bidType;
	}

	public String getBorrTotAmt() {
		return BorrTotAmt;
	}

	public void setBorrTotAmt(String borrTotAmt) {
		BorrTotAmt = borrTotAmt;
	}

	public String getYearRate() {
		return YearRate;
	}

	public void setYearRate(String yearRate) {
		YearRate = yearRate;
	}

	public String getRetInterest() {
		return RetInterest;
	}

	public void setRetInterest(String retInterest) {
		RetInterest = retInterest;
	}

	public String getLastRetDate() {
		return LastRetDate;
	}

	public void setLastRetDate(String lastRetDate) {
		LastRetDate = lastRetDate;
	}

	public String getBorrPurpose() {
		return BorrPurpose;
	}

	public void setBorrPurpose(String borrPurpose) {
		BorrPurpose = borrPurpose;
	}

	public String getBidStartDate() {
		return BidStartDate;
	}

	public void setBidStartDate(String bidStartDate) {
		BidStartDate = bidStartDate;
	}

	public String getBidEndDate() {
		return BidEndDate;
	}

	public void setBidEndDate(String bidEndDate) {
		BidEndDate = bidEndDate;
	}

	public String getLoanPeriod() {
		return LoanPeriod;
	}

	public void setLoanPeriod(String loanPeriod) {
		LoanPeriod = loanPeriod;
	}

	public String getRetType() {
		return RetType;
	}

	public void setRetType(String retType) {
		RetType = retType;
	}

	public String getRetDate() {
		return RetDate;
	}

	public void setRetDate(String retDate) {
		RetDate = retDate;
	}

	public String getGuarantType() {
		return GuarantType;
	}

	public void setGuarantType(String guarantType) {
		GuarantType = guarantType;
	}

	public String getBidProdType() {
		return BidProdType;
	}

	public void setBidProdType(String bidProdType) {
		BidProdType = bidProdType;
	}

	public String getRiskCtlType() {
		return RiskCtlType;
	}

	public void setRiskCtlType(String riskCtlType) {
		RiskCtlType = riskCtlType;
	}

	public String getLimitMinBidAmt() {
		return LimitMinBidAmt;
	}

	public void setLimitMinBidAmt(String limitMinBidAmt) {
		LimitMinBidAmt = limitMinBidAmt;
	}

	public String getLimitBidSum() {
		return LimitBidSum;
	}

	public void setLimitBidSum(String limitBidSum) {
		LimitBidSum = limitBidSum;
	}

	public String getLimitMaxBidSum() {
		return LimitMaxBidSum;
	}

	public void setLimitMaxBidSum(String limitMaxBidSum) {
		LimitMaxBidSum = limitMaxBidSum;
	}

	public String getLimitMinBidSum() {
		return LimitMinBidSum;
	}

	public void setLimitMinBidSum(String limitMinBidSum) {
		LimitMinBidSum = limitMinBidSum;
	}

	public String getBidPayforState() {
		return BidPayforState;
	}

	public void setBidPayforState(String bidPayforState) {
		BidPayforState = bidPayforState;
	}

	public String getBorrType() {
		return BorrType;
	}

	public void setBorrType(String borrType) {
		BorrType = borrType;
	}

	public String getBorrCustId() {
		return BorrCustId;
	}

	public void setBorrCustId(String borrCustId) {
		BorrCustId = borrCustId;
	}

	public String getBorrName() {
		return BorrName;
	}

	public void setBorrName(String borrName) {
		BorrName = borrName;
	}

	public String getBorrBusiCode() {
		return BorrBusiCode;
	}

	public void setBorrBusiCode(String borrBusiCode) {
		BorrBusiCode = borrBusiCode;
	}

	public String getBorrCertType() {
		return BorrCertType;
	}

	public void setBorrCertType(String borrCertType) {
		BorrCertType = borrCertType;
	}

	public String getBorrCertId() {
		return BorrCertId;
	}

	public void setBorrCertId(String borrCertId) {
		BorrCertId = borrCertId;
	}

	public String getBorrMobiPhone() {
		return BorrMobiPhone;
	}

	public void setBorrMobiPhone(String borrMobiPhone) {
		BorrMobiPhone = borrMobiPhone;
	}

	public String getBorrPhone() {
		return BorrPhone;
	}

	public void setBorrPhone(String borrPhone) {
		BorrPhone = borrPhone;
	}

	public String getBorrWorkYear() {
		return BorrWorkYear;
	}

	public void setBorrWorkYear(String borrWorkYear) {
		BorrWorkYear = borrWorkYear;
	}

	public String getBorrIncome() {
		return BorrIncome;
	}

	public void setBorrIncome(String borrIncome) {
		BorrIncome = borrIncome;
	}

	public String getBorrMarriage() {
		return BorrMarriage;
	}

	public void setBorrMarriage(String borrMarriage) {
		BorrMarriage = borrMarriage;
	}

	public String getBorrEmail() {
		return BorrEmail;
	}

	public void setBorrEmail(String borrEmail) {
		BorrEmail = borrEmail;
	}

	public String getAuditStat() {
		return AuditStat;
	}

	public void setAuditStat(String auditStat) {
		AuditStat = auditStat;
	}

	public String getAuditDesc() {
		return AuditDesc;
	}

	public void setAuditDesc(String auditDesc) {
		AuditDesc = auditDesc;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getFeeCustId() {
        return FeeCustId;
    }

    public void setFeeCustId(String feeCustId) {
        FeeCustId = feeCustId;
    }

    public String getFeeAcctId() {
        return FeeAcctId;
    }

    public void setFeeAcctId(String feeAcctId) {
        FeeAcctId = feeAcctId;
    }

    public String getRespExt() {
        return RespExt;
    }

    public void setRespExt(String respExt) {
        RespExt = respExt;
    }

    public String getSecRespCode() {
        return SecRespCode;
    }

    public void setSecRespCode(String secRespCode) {
        SecRespCode = secRespCode;
    }

    public String getSecRespDesc() {
        return SecRespDesc;
    }

    public void setSecRespDesc(String secRespDesc) {
        SecRespDesc = secRespDesc;
    }

	public String getPrincipalAmt() {
		return PrincipalAmt;
	}


	public void setPrincipalAmt(String principalAmt) {
		PrincipalAmt = principalAmt;
	}

	public String getInterestAmt() {
		return InterestAmt;
	}

	public void setInterestAmt(String interestAmt) {
		InterestAmt = interestAmt;
	}
	public String getBusiCode() {
        return BusiCode;
    }


    public void setBusiCode(String busiCode) {
        BusiCode = busiCode;
    }


	public String getInUsrCustId() {
		return InUsrCustId;
	}

	public void setInUsrCustId(String inUsrCustId) {
		InUsrCustId = inUsrCustId;
	}

	public String getAuthAmt() {
		return AuthAmt;
	}

	public void setAuthAmt(String authAmt) {
		AuthAmt = authAmt;
	}

	public String getPayQuotaDetails() {
		return PayQuotaDetails;
	}

	public void setPayQuotaDetails(String payQuotaDetails) {
		PayQuotaDetails = payQuotaDetails;
	}

	public String getGuarType() {
		return GuarType;
	}

	public void setGuarType(String guarType) {
		GuarType = guarType;
	}

	public String getDzObject() {
		return DzObject;
	}

	public void setDzObject(String dzObject) {
		DzObject = dzObject;
	}
}
    