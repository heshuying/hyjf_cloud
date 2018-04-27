package com.hyjf.pay.lib.anrong.bean;

import java.io.Serializable;

import com.hyjf.common.util.CreateUUID;
import com.hyjf.common.util.GetOrderIdUtils;
import com.hyjf.common.util.PropUtils;

/**
 * 
 * 汇盈请求安融接口参数
 * @author sss
 * @version hyjf 1.0
 * @since hyjf 1.0 2017年10月10日
 * @see 上午9:46:26
 */
public class AnRongBean extends AnRongApiBean implements Serializable {

	public AnRongBean() {
		super();
	}

	public AnRongBean(Integer userId , String customerName, String paperNumber,String bizType,String loanType) {
		super();
		//设置共通参数
		setBankCallCommon(customerName, paperNumber, bizType, loanType, userId+"");
		//设置log参数
		setBankCallLog(userId);
	}

	private void setBankCallCommon(String customerName, String paperNumber,String bizType,String loanType,String userid) {
		// 获取共通信息
	    String member = PropUtils.getSystem(AnRongConstant.PARM_MEMBER_CODE);
	    String sign = PropUtils.getSystem(AnRongConstant.PARM_SIGN_CODE);
	    this.member = member;
	    this.sign = sign;
	    this.customerName = customerName;
	    this.paperNumber = paperNumber;
	    /** 格式为：年月日+去除【-】的uuid */
	    this.loanId = GetOrderIdUtils.getTxDate()+CreateUUID.createUUID().replaceAll("-", "");
	    this.bizType = bizType;
	    this.loanType = loanType;
	}

	private void setBankCallLog(Integer userId) {
		// 获取log信息
		String orderDate = GetOrderIdUtils.getOrderDate();
		
		//设置log字段
		// 订单号
		this.logOrderId = this.loanId;
		// 订单日期
		this.logOrderDate = orderDate;
		// 操作用户Id
		this.logUserId = String.valueOf(userId);
	}

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
	/** 订单号 */
	public String logOrderId;
	/** 订单日期 */
	public String logOrderDate;
	/** 银行请求详细url */
	public String logBankDetailUrl;
	/** 中间表uuid */
	public String logUuid;
	/** 用户操作平台 */
	public int logClient;

	/** 操作方法 */
	private String txCode;
    
	// 系统字段
	/** 机构编码 */
	private String member;
	/** 接口秘钥 */
	private String sign;
	/** 姓名 */
	private String customerName;
	/** 身份证号 */
	private String paperNumber;
	/** 业务标识符 */
	private String loanId;
	/** 业务类型 */
	private String bizType;
	/** 借款用途 */
	private String loanType;
	
	// 审批/合同信息字段
	/** 审批日期 */
	private String checkResultTime;
	/** 审批结果 */
	private String checkResult;
	/** 合同金额 */
	private String loanMoney;
	/** 合同借款开始日期 */
	private String loanStartDate;
	/** 合同借款到期日期 */
	private String loanEndDate;
	/** 借款地点 */
	private String loanCreditCity;
	/** 还款期数 */
	private String loanPeriods;
	/** 担保类型 */
	private String loanAssureType;
	/** 借款期限*/
	private String applyLoanTimeLimit;
	
	// 债权信息字段
	/** 未偿还本金 */
	private String nbsMoney;
	/** 当前还款状态 */
	private String state;
	/** 逾期总金额    在当前还款状态为02和03时必输*/
	private String nbMoney;
	/** 逾期开始日期   在当前还款状态为02和03时必输 */
	private String overdueStartDate;
	/** 逾期时长   在当前还款状态为02和03时必输 */
	private String overdueDays;
	/** 逾期原因    在当前还款状态为02和03时必输*/
	private String overdueReason;
	/** 借款城市（字典）*/
	private String creditAddress;
	/** 借款期数*/
	private String loanTimeLimit;
	/** 申请日期*/
	private String applyDate;
	/** 借款金额*/
    private String applyLoanMoney;
	
    public String getApplyLoanTimeLimit() {
        return applyLoanTimeLimit;
    }

    public void setApplyLoanTimeLimit(String applyLoanTimeLimit) {
        this.applyLoanTimeLimit = applyLoanTimeLimit;
    }

    public String getCheckResultTime() {
        return checkResultTime;
    }

    public void setCheckResultTime(String checkResultTime) {
        this.checkResultTime = checkResultTime;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(String loanMoney) {
        this.loanMoney = loanMoney;
    }

    public String getLoanStartDate() {
        return loanStartDate;
    }

    public void setLoanStartDate(String loanStartDate) {
        this.loanStartDate = loanStartDate;
    }

    public String getLoanEndDate() {
        return loanEndDate;
    }

    public void setLoanEndDate(String loanEndDate) {
        this.loanEndDate = loanEndDate;
    }

    public String getLoanCreditCity() {
        return loanCreditCity;
    }

    public void setLoanCreditCity(String loanCreditCity) {
        this.loanCreditCity = loanCreditCity;
    }

    public String getLoanPeriods() {
        return loanPeriods;
    }

    public void setLoanPeriods(String loanPeriods) {
        this.loanPeriods = loanPeriods;
    }

    public String getLoanAssureType() {
        return loanAssureType;
    }

    public void setLoanAssureType(String loanAssureType) {
        this.loanAssureType = loanAssureType;
    }

    public String getNbsMoney() {
        return nbsMoney;
    }

    public void setNbsMoney(String nbsMoney) {
        this.nbsMoney = nbsMoney;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getNbMoney() {
        return nbMoney;
    }

    public void setNbMoney(String nbMoney) {
        this.nbMoney = nbMoney;
    }

    public String getOverdueStartDate() {
        return overdueStartDate;
    }

    public void setOverdueStartDate(String overdueStartDate) {
        this.overdueStartDate = overdueStartDate;
    }

    public String getOverdueDays() {
        return overdueDays;
    }

    public void setOverdueDays(String overdueDays) {
        this.overdueDays = overdueDays;
    }

    public String getOverdueReason() {
        return overdueReason;
    }

    public void setOverdueReason(String overdueReason) {
        this.overdueReason = overdueReason;
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

    public int getLogClient() {
        return logClient;
    }

    public void setLogClient(int logClient) {
        this.logClient = logClient;
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPaperNumber() {
        return paperNumber;
    }

    public void setPaperNumber(String paperNumber) {
        this.paperNumber = paperNumber;
    }

    public String getLoanId() {
        return loanId;
    }

    public void setLoanId(String loanId) {
        this.loanId = loanId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public String getTxCode() {
        return txCode;
    }

    public void setTxCode(String txCode) {
        this.txCode = txCode;
    }

    public String getCreditAddress() {
        return creditAddress;
    }

    public void setCreditAddress(String creditAddress) {
        this.creditAddress = creditAddress;
    }

    public String getLoanTimeLimit() {
        return loanTimeLimit;
    }

    public void setLoanTimeLimit(String loanTimeLimit) {
        this.loanTimeLimit = loanTimeLimit;
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyLoanMoney() {
        return applyLoanMoney;
    }

    public void setApplyLoanMoney(String applyLoanMoney) {
        this.applyLoanMoney = applyLoanMoney;
    }

    public void setSystemParm(String loanId, String userid) {
        this.loanId = loanId;
        setBankCallLog(Integer.parseInt(userid));
        // 获取共通信息
        String member = PropUtils.getSystem(AnRongConstant.PARM_MEMBER_CODE);
        String sign = PropUtils.getSystem(AnRongConstant.PARM_SIGN_CODE);
        this.member = member;
        this.sign = sign;
    }
    
    public void setSystemParm(String userid) {
        /** 格式为：年月日+去除【-】的uuid */
        this.loanId = GetOrderIdUtils.getTxDate()+CreateUUID.createUUID().replaceAll("-", "");
        setBankCallLog(Integer.parseInt(userid));
        // 获取共通信息
        String member = PropUtils.getSystem(AnRongConstant.PARM_MEMBER_CODE);
        String sign = PropUtils.getSystem(AnRongConstant.PARM_SIGN_CODE);
        this.member = member;
        this.sign = sign;
    }
}
