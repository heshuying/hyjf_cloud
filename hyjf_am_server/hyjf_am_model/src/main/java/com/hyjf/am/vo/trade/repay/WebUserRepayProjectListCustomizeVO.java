/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 *           Created at: 2015年11月11日 下午2:17:31
 *           Modification History:
 *           Modified by :
 */

package com.hyjf.am.vo.trade.repay;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class WebUserRepayProjectListCustomizeVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5429348260157960848L;

	// 项目类型标识
	private String projectType;

	// 项目类型
	private String projectTypeName;

	// 项目还款方式
	private String borrowStyle;

	// 项目编号
	private String borrowNid;

	// 项目名称
	private String borrowName;

	// 项目年华收益率
	private String borrowInterest;

	// 项目期限
	private String borrowPeriod;

	// 项目是个人还是企业
	private String comOrPer;

	// 借款时间
	private String loanTime;

	// 借款金额
	private String borrowAccount;

	// 还款金额
	private String borrowTotal;

	// 还款时间
	private String repayTime;

	// 项目状态
	private String status;

	// 融通宝资产编号
	private String borrowAssetNumber;
	/** 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付） */
	private String repayMoneySource;

	/**法大大协议生成状态：0:初始,1:成功,2:失败，3下载成功*/
    private int fddStatus;
    
    
    public int getFddStatus() {
        return fddStatus;
    }
    public void setFddStatus(int fddStatus) {
        this.fddStatus = fddStatus;
    }
	
	// 实还时间
	private String repayActionTime;
	// 已还总额
	private String repayTotal;
	// 已还本息
	private String accountTotal;
	// 还款期数
	private String repayPeriod;
	//项目总期数
	private String borrowAllPeriod;
	
	// 实际还款时间
	private String repayYesTime;

	//本期管理费 add by cwyang
	private String repayFee;
	//借款人本期应还总额 add by cwyang
	private String realAccountYes;
	//总管理费  add by cwyang
	private String allRepayFee;
	//垫付机构期数
	private String orgBorrowPeriod;
	/**
	 * 到账金额
	 */
	private String yesAccount;
	/**
	 * 到账时间
	 */
	private String yesAccountTime;
	/**
	 * 服务费
	 */
	private String serviceFee;
	/**
	 * 构造方法
	 */
	public WebUserRepayProjectListCustomizeVO() {
		super();
	}
	
	
	
	public String getServiceFee() {
		return serviceFee;
	}



	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}



	public String getAllRepayFee() {
		return allRepayFee;
	}

	public void setAllRepayFee(String allRepayFee) {
		this.allRepayFee = allRepayFee;
	}

	public String getRealAccountYes() {
		return realAccountYes;
	}


	public void setRealAccountYes(String realAccountYes) {
		this.realAccountYes = realAccountYes;
	}

	public String getYesAccount() {
		return yesAccount;
	}

	public void setYesAccount(String yesAccount) {
		this.yesAccount = yesAccount;
	}

	public String getYesAccountTime() {
		return yesAccountTime;
	}

	public void setYesAccountTime(String yesAccountTime) {
		this.yesAccountTime = yesAccountTime;
	}

	public String getOrgBorrowPeriod() {
		return orgBorrowPeriod;
	}


	public void setOrgBorrowPeriod(String orgBorrowPeriod) {
		this.orgBorrowPeriod = orgBorrowPeriod;
	}


	public String getBorrowAllPeriod() {
		return borrowAllPeriod;
	}


	public void setBorrowAllPeriod(String borrowAllPeriod) {
		this.borrowAllPeriod = borrowAllPeriod;
	}


	public String getRepayFee() {
		return repayFee;
	}


	public void setRepayFee(String repayFee) {
		this.repayFee = repayFee;
	}


	public String getAccountTotal() {
		return accountTotal;
	}

	public void setAccountTotal(String accountTotal) {
		this.accountTotal = accountTotal;
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

	public String getComOrPer() {
		return comOrPer;
	}

	public void setComOrPer(String comOrPer) {
		this.comOrPer = comOrPer;
	}

	public String getLoanTime() {
		return loanTime;
	}

	public void setLoanTime(String loanTime) {
		this.loanTime = loanTime;
	}

	public String getBorrowAccount() {
		return borrowAccount;
	}

	public void setBorrowAccount(String borrowAccount) {
		this.borrowAccount = borrowAccount;
	}

	public String getBorrowTotal() {
		return borrowTotal;
	}

	public void setBorrowTotal(String borrowTotal) {
		this.borrowTotal = borrowTotal;
	}

	public String getRepayTime() {
		return repayTime;
	}

	public void setRepayTime(String repayTime) {
		this.repayTime = repayTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBorrowAssetNumber() {
		return borrowAssetNumber;
	}

	public void setBorrowAssetNumber(String borrowAssetNumber) {
		this.borrowAssetNumber = borrowAssetNumber;
	}

	public String getRepayMoneySource() {
		return repayMoneySource;
	}

	public void setRepayMoneySource(String repayMoneySource) {
		this.repayMoneySource = repayMoneySource;
	}

	public String getProjectTypeName() {
		return projectTypeName;
	}

	public void setProjectTypeName(String projectTypeName) {
		this.projectTypeName = projectTypeName;
	}

	public String getRepayActionTime() {
		return repayActionTime;
	}

	public void setRepayActionTime(String repayActionTime) {
		this.repayActionTime = repayActionTime;
	}

	public String getRepayTotal() {
		return repayTotal;
	}

	public void setRepayTotal(String repayTotal) {
		this.repayTotal = repayTotal;
	}

	public String getRepayPeriod() {
		return repayPeriod;
	}

	public void setRepayPeriod(String repayPeriod) {
		this.repayPeriod = repayPeriod;
	}

	public String getRepayYesTime() {
		return repayYesTime;
	}

	public void setRepayYesTime(String repayYesTime) {
		this.repayYesTime = repayYesTime;
	}

}
