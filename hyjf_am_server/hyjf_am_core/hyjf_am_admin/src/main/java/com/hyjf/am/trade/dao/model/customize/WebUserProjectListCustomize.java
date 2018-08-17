package com.hyjf.am.trade.dao.model.customize;

/**
 * @author jijun
 * @date 20180624
 */

public class WebUserProjectListCustomize {

	// 项目类型
	public String borrowType;
	// 项目类型标识
	public String projectType;
	// 项目编号
	public String borrowNid;
	// 项目名称
	public String borrowName;
	// 项目年华收益率
	public String borrowInterest;
	// 项目期限
	public String borrowPeriod;
	// 项目进度
	public String borrowSchedule;
	// 项目是个人还是企业
	public String comOrPer;
	// 投资时间
	public String investTime;
	// 投资时间
	public String createTime;
	// 投资金额
	public String accountInvest;
	// 待收金额
	public String accountWait;
	// 还款时间
	public String repayTime;
	// nid
	public String nid;
	// 还款方式
	public String borrowStyle;
	// 已收金额
	public String accountYes;
	// 项目状态
	public String status;
	// 项目状态
	public String transStatus;
	//投资类型 1 普通投资 2 优惠券投资
	public String investType;
	//备注
	public String remark;
	//融通宝资产编号
	public String borrowAssetNumber;
	//融通宝加息收益率
	public String borrowExtraYield;
	
	/**
	 * 构造方法
	 */

	public WebUserProjectListCustomize() {
		super();
	}

	public String getBorrowType() {
		return borrowType;
	}

	public void setBorrowType(String borrowType) {
		this.borrowType = borrowType;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowName() {
		return borrowName;
	}

	public void setBorrowName(String borrowName) {
		this.borrowName = borrowName;
	}

	public String getBorrowInterest() {
		return borrowInterest;
	}

	public void setBorrowInterest(String borrowInterest) {
		this.borrowInterest = borrowInterest;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getBorrowSchedule() {
		return borrowSchedule;
	}

	public void setBorrowSchedule(String borrowSchedule) {
		this.borrowSchedule = borrowSchedule;
	}

	public String getInvestTime() {
		return investTime;
	}

	public void setInvestTime(String investTime) {
		this.investTime = investTime;
	}

	public String getAccountInvest() {
		return accountInvest;
	}

	public void setAccountInvest(String accountInvest) {
		this.accountInvest = accountInvest;
	}

	public String getAccountWait() {
		return accountWait;
	}

	public void setAccountWait(String accountWait) {
		this.accountWait = accountWait;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getAccountYes() {
		return accountYes;
	}

	public void setAccountYes(String accountYes) {
		this.accountYes = accountYes;
	}

	public String getComOrPer() {
		return comOrPer;
	}

	public void setComOrPer(String comOrPer) {
		this.comOrPer = comOrPer;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getBorrowStyle() {
		return borrowStyle;
	}

	public void setBorrowStyle(String borrowStyle) {
		this.borrowStyle = borrowStyle;
	}

	public String getTransStatus() {
		return transStatus;
	}

	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}

    public String getInvestType() {
        return investType;
    }

    public void setInvestType(String investType) {
        this.investType = investType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

	public String getBorrowAssetNumber() {
		return borrowAssetNumber;
	}

	public void setBorrowAssetNumber(String borrowAssetNumber) {
		this.borrowAssetNumber = borrowAssetNumber;
	}

	public String getBorrowExtraYield() {
		return borrowExtraYield;
	}

	public void setBorrowExtraYield(String borrowExtraYield) {
		this.borrowExtraYield = borrowExtraYield;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

}
