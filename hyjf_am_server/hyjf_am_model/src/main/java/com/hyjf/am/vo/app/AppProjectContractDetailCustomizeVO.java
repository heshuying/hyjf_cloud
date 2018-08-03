/**
 * Description:用户开户列表前端显示查询所用po
 * Copyright: Copyright (HYJF Corporation) 2015
 * Company: HYJF Corporation
 * @author: 王坤
 * @version: 1.0
 * Created at: 2015年11月11日 下午2:17:31
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.vo.app;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author 王坤
 */

public class AppProjectContractDetailCustomizeVO extends BaseVO implements Serializable {

	/** 序列化id */
	private static final long serialVersionUID = -2708328205111331639L;
	/* 项目编号 borrowNid */
	private String borrowNid;
	/* 项目大分类 projectType HZT 汇直投 HXF 汇消费 */
	private String projectType;
	/* 项目期限 borrowPeriod */
	private String borrowPeriod;
	/* 项目年化收益 borrowApr */
	private String borrowApr;
	/* 还款方式 repayStyle */
	private String repayStyle;
	//还款类型
	private String repayType;
	/* 真实姓名 tenderRealName */
	private String tenderRealName;
	/* 身份证号 tenderIdCard */
	private String tenderIdCard;
	/* 投资金额 invest */
	private String invest;
	/* 投资开始日 investStartDate */
	private String investStartDate;
	/* 投资到期日 investEndDate */
	private String investEndDate;
	/* 签订日期 signDate */
	private String signDate;
	/* 应收本息 account */
	private String account;
	/* 乙方信息 borrowUserName */
	private String borrowUserName;

	public AppProjectContractDetailCustomizeVO() {
		super();
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getBorrowApr() {
		return borrowApr;
	}

	public void setBorrowApr(String borrowApr) {
		this.borrowApr = borrowApr;
	}

	public String getRepayStyle() {
		return repayStyle;
	}

	public void setRepayStyle(String repayStyle) {
		this.repayStyle = repayStyle;
	}

	public String getTenderRealName() {
		return tenderRealName;
	}

	public void setTenderRealName(String tenderRealName) {
		this.tenderRealName = tenderRealName;
	}

	public String getTenderIdCard() {
		return tenderIdCard;
	}

	public void setTenderIdCard(String tenderIdCard) {
		this.tenderIdCard = tenderIdCard;
	}

	public String getInvest() {
		return invest;
	}

	public void setInvest(String invest) {
		this.invest = invest;
	}

	public String getInvestStartDate() {
		return investStartDate;
	}

	public void setInvestStartDate(String investStartDate) {
		this.investStartDate = investStartDate;
	}

	public String getInvestEndDate() {
		return investEndDate;
	}

	public void setInvestEndDate(String investEndDate) {
		this.investEndDate = investEndDate;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getRepayType() {
		return repayType;
	}

	public void setRepayType(String repayType) {
		this.repayType = repayType;
	}

}
