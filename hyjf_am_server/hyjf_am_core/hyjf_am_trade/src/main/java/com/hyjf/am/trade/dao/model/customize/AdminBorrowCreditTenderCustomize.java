package com.hyjf.am.trade.dao.model.customize;



/**
 * admin: borrowTender业务bean
 * @author zhangyk
 * @date 2018/7/11 14:28
 */
public class AdminBorrowCreditTenderCustomize  {
    /* 用户名称 */
    private String userName;
    /* 债转标号 */
    private String creditNid;
    /* 出让人名称 */
    private String creditUserName;
    /* 原标标号 */
    private String bidNid;
    /* 债转投标单号 */
    private String creditTenderNid;
    /* 认购单号 */
    private String assignNid;
    /* 投资本金 */
    private String assignCapital;
    /* 债转利息 */
    private String assignInterest;
    /* 回收总额 */
    private String assignAccount;
    /* 购买价格 */
    private String assignPrice;
    /* 支付金额 */
    private String assignPay;
    /* 已还总额 */
    private String assignRepayAccount;
    /* 服务费 */
    private String creditFee;
    /* 状态 */
    private String status;
    /* 最后还款日 */
    private String assignRepayEndTime;
    /* 下次还款时间 */
    private String assignRepayNextTime;

    /* 添加时间 */
    private String addTime;
    
    /*----add by LSY START---------*/
    /* 应收本金、应收利息、应收本息、已收本息、管理费 */
    private String sumAssignCapital;
    private String sumAssignInterest;
    private String sumAssignAccount;
    private String sumAssignRepayAccount;
    private String sumCreditFee;
    /*----add by LSY END---------*/

    /*----add by LSY START---------*/
    /* 页面检索条件 */
    // 承接人ID
    private String userNameSrch;
    // 出让人ID
    private String creditUserNameSrch;
    // 债转编号
    private String creditNidSrch;
    // 还款状态
    private String statusSrch;
    // 项目编号
    private String bidNidSrch;
    // 订单号
    private String assignNidSrch;
    //下次还款时间
    private String assignRepayNextTimeStartSrch;
    //下次还款时间
    private String assignRepayNextTimeEndSrch;
    // 债权承接时间
    private String addTimeStartSrch;
    // 债权承接时间
    private String addTimeEndSrch;
	// 检索条件 limitStart
	private int limitStart = -1;
	// 检索条件 limitEnd
	private int limitEnd = -1;

    /*----add by LSY END---------*/

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreditNid() {
		return creditNid;
	}

	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}

	public String getCreditUserName() {
		return creditUserName;
	}

	public void setCreditUserName(String creditUserName) {
		this.creditUserName = creditUserName;
	}

	public String getBidNid() {
		return bidNid;
	}

	public void setBidNid(String bidNid) {
		this.bidNid = bidNid;
	}

	public String getCreditTenderNid() {
		return creditTenderNid;
	}

	public void setCreditTenderNid(String creditTenderNid) {
		this.creditTenderNid = creditTenderNid;
	}

	public String getAssignNid() {
		return assignNid;
	}

	public void setAssignNid(String assignNid) {
		this.assignNid = assignNid;
	}

	public String getAssignCapital() {
		return assignCapital;
	}

	public void setAssignCapital(String assignCapital) {
		this.assignCapital = assignCapital;
	}

	public String getAssignInterest() {
		return assignInterest;
	}

	public void setAssignInterest(String assignInterest) {
		this.assignInterest = assignInterest;
	}

	public String getAssignAccount() {
		return assignAccount;
	}

	public void setAssignAccount(String assignAccount) {
		this.assignAccount = assignAccount;
	}

	public String getAssignPrice() {
		return assignPrice;
	}

	public void setAssignPrice(String assignPrice) {
		this.assignPrice = assignPrice;
	}

	public String getAssignPay() {
		return assignPay;
	}

	public void setAssignPay(String assignPay) {
		this.assignPay = assignPay;
	}

	public String getAssignRepayAccount() {
		return assignRepayAccount;
	}

	public void setAssignRepayAccount(String assignRepayAccount) {
		this.assignRepayAccount = assignRepayAccount;
	}

	public String getCreditFee() {
		return creditFee;
	}

	public void setCreditFee(String creditFee) {
		this.creditFee = creditFee;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssignRepayEndTime() {
		return assignRepayEndTime;
	}

	public void setAssignRepayEndTime(String assignRepayEndTime) {
		this.assignRepayEndTime = assignRepayEndTime;
	}

	public String getAssignRepayNextTime() {
		return assignRepayNextTime;
	}

	public void setAssignRepayNextTime(String assignRepayNextTime) {
		this.assignRepayNextTime = assignRepayNextTime;
	}

	public String getAddTime() {
		return addTime;
	}

	public void setAddTime(String addTime) {
		this.addTime = addTime;
	}

	public String getSumAssignCapital() {
		return sumAssignCapital;
	}

	public void setSumAssignCapital(String sumAssignCapital) {
		this.sumAssignCapital = sumAssignCapital;
	}

	public String getSumAssignInterest() {
		return sumAssignInterest;
	}

	public void setSumAssignInterest(String sumAssignInterest) {
		this.sumAssignInterest = sumAssignInterest;
	}

	public String getSumAssignAccount() {
		return sumAssignAccount;
	}

	public void setSumAssignAccount(String sumAssignAccount) {
		this.sumAssignAccount = sumAssignAccount;
	}

	public String getSumAssignRepayAccount() {
		return sumAssignRepayAccount;
	}

	public void setSumAssignRepayAccount(String sumAssignRepayAccount) {
		this.sumAssignRepayAccount = sumAssignRepayAccount;
	}

	public String getSumCreditFee() {
		return sumCreditFee;
	}

	public void setSumCreditFee(String sumCreditFee) {
		this.sumCreditFee = sumCreditFee;
	}

	public String getUserNameSrch() {
		return userNameSrch;
	}

	public void setUserNameSrch(String userNameSrch) {
		this.userNameSrch = userNameSrch;
	}

	public String getCreditUserNameSrch() {
		return creditUserNameSrch;
	}

	public void setCreditUserNameSrch(String creditUserNameSrch) {
		this.creditUserNameSrch = creditUserNameSrch;
	}

	public String getCreditNidSrch() {
		return creditNidSrch;
	}

	public void setCreditNidSrch(String creditNidSrch) {
		this.creditNidSrch = creditNidSrch;
	}

	public String getStatusSrch() {
		return statusSrch;
	}

	public void setStatusSrch(String statusSrch) {
		this.statusSrch = statusSrch;
	}

	public String getBidNidSrch() {
		return bidNidSrch;
	}

	public void setBidNidSrch(String bidNidSrch) {
		this.bidNidSrch = bidNidSrch;
	}

	public String getAssignNidSrch() {
		return assignNidSrch;
	}

	public void setAssignNidSrch(String assignNidSrch) {
		this.assignNidSrch = assignNidSrch;
	}

	public String getAssignRepayNextTimeStartSrch() {
		return assignRepayNextTimeStartSrch;
	}

	public void setAssignRepayNextTimeStartSrch(String assignRepayNextTimeStartSrch) {
		this.assignRepayNextTimeStartSrch = assignRepayNextTimeStartSrch;
	}

	public String getAssignRepayNextTimeEndSrch() {
		return assignRepayNextTimeEndSrch;
	}

	public void setAssignRepayNextTimeEndSrch(String assignRepayNextTimeEndSrch) {
		this.assignRepayNextTimeEndSrch = assignRepayNextTimeEndSrch;
	}

	public String getAddTimeStartSrch() {
		return addTimeStartSrch;
	}

	public void setAddTimeStartSrch(String addTimeStartSrch) {
		this.addTimeStartSrch = addTimeStartSrch;
	}

	public String getAddTimeEndSrch() {
		return addTimeEndSrch;
	}

	public void setAddTimeEndSrch(String addTimeEndSrch) {
		this.addTimeEndSrch = addTimeEndSrch;
	}

	public int getLimitStart() {
		return limitStart;
	}

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	public int getLimitEnd() {
		return limitEnd;
	}

	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}
}




	