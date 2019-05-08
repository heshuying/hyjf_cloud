/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version HjhCreditTenderCustomizeVO.java, v0.1 2018年7月11日 下午2:44:43
 */
public class HjhCreditTenderCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 主键
	 */
	private String id;
	/**
     * 承接人id
     */
	private String userId;
	
	/**
	 * 承接人
	 */
	private String assignUserName;
	
	/**
	 * 承接计划编号
	 */
	private String assignPlanNid;
	
	/**
	 * 承接计划订单号-计划订单号
	 */
	private String assignPlanOrderId;
	
	/**
	 * 承接订单号
	 */
	private String assignOrderId;
	
	/**
	 * 出让人用户名-出让人
	 */
	private String creditUserName;
	
	/**
	 * 债转编号
	 */
	private String creditNid;

	/**
	 * 项目编号-原项目编号
	 */
	private String borrowNid;
	
	/**
	 * 还款方式
	 */
	private String repayStyleName;

	/**
	 * 承接本金
	 */
	private String assignCapital;
	
	/**
	 * 垫付利息
	 */
	private String assignInterestAdvance;
	
	/**
	 * 实际支付金额
	 */
	private String assignPay;
	
	/**
	 * 承接类型
	 */
	private String assignTypeName;

	/**
	 * 承接时间
	 */
	private String assignTime;

	/**
	 * 项目总期数
	 */
	private String borrowPeriod;

	/**
	 * 承接时所在期数
	 */
	private String assignPeriod;
	/**
	 * 合同状态
	 */
	private String contractStatus;

	/**
	 * 合同编号
	 */
	private String contractNumber;

	/**
	 * 合同下载地址
	 */
	private String downloadUrl;

	/**
	 * 合同查看地址
	 */
	private String viewpdfUrl;

	/**
	 * 脱敏合同地址
	 */
	private String imgUrl;
	
	/**
	 * 是否复投承接
	 */
	private String tenderType;
	/**
	 * 债转服务费
	 */
	private String assignServiceFee;
	/**
	 * 债转服务费率
	 */
	private String assignServiceApr;
	/**
	 * 项目期数
	 */
	private String projectPeriod;
	/**
	 * 原投资订单号
	 */
	private String sellOrderId;

	/**
	 * 构造方法
	 */
	public HjhCreditTenderCustomizeVO() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	
	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAssignUserName() {
		return assignUserName;
	}

	public void setAssignUserName(String assignUserName) {
		this.assignUserName = assignUserName;
	}

	public String getAssignPlanNid() {
		return assignPlanNid;
	}

	public void setAssignPlanNid(String assignPlanNid) {
		this.assignPlanNid = assignPlanNid;
	}

	public String getAssignPlanOrderId() {
		return assignPlanOrderId;
	}

	public void setAssignPlanOrderId(String assignPlanOrderId) {
		this.assignPlanOrderId = assignPlanOrderId;
	}

	public String getAssignOrderId() {
		return assignOrderId;
	}

	public void setAssignOrderId(String assignOrderId) {
		this.assignOrderId = assignOrderId;
	}
	
	public String getCreditUserName() {
		return creditUserName;
	}

	public void setCreditUserName(String creditUserName) {
		this.creditUserName = creditUserName;
	}

	public String getCreditNid() {
		return creditNid;
	}

	public void setCreditNid(String creditNid) {
		this.creditNid = creditNid;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid;
	}

	public String getRepayStyleName() {
		return repayStyleName;
	}

	public void setRepayStyleName(String repayStyleName) {
		this.repayStyleName = repayStyleName;
	}

	public String getAssignCapital() {
		return assignCapital;
	}

	public void setAssignCapital(String assignCapital) {
		this.assignCapital = assignCapital;
	}

	public String getAssignInterestAdvance() {
		return assignInterestAdvance;
	}

	public void setAssignInterestAdvance(String assignInterestAdvance) {
		this.assignInterestAdvance = assignInterestAdvance;
	}

	public String getAssignPay() {
		return assignPay;
	}

	public void setAssignPay(String assignPay) {
		this.assignPay = assignPay;
	}

	public String getAssignTime() {
		return assignTime;
	}

	public void setAssignTime(String assignTime) {
		this.assignTime = assignTime;
	}

	public String getBorrowPeriod() {
		return borrowPeriod;
	}

	public void setBorrowPeriod(String borrowPeriod) {
		this.borrowPeriod = borrowPeriod;
	}

	public String getAssignPeriod() {
		return assignPeriod;
	}

	public void setAssignPeriod(String assignPeriod) {
		this.assignPeriod = assignPeriod;
	}

	public String getAssignTypeName() {
		return assignTypeName;
	}

	public void setAssignTypeName(String assignTypeName) {
		this.assignTypeName = assignTypeName;
	}


	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public String getViewpdfUrl() {
		return viewpdfUrl;
	}

	public void setViewpdfUrl(String viewpdfUrl) {
		this.viewpdfUrl = viewpdfUrl;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getTenderType() {
		return tenderType;
	}

	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}

	public String getAssignServiceFee() {
		return assignServiceFee;
	}

	public void setAssignServiceFee(String assignServiceFee) {
		this.assignServiceFee = assignServiceFee;
	}

	public String getAssignServiceApr() {
		return assignServiceApr;
	}

	public void setAssignServiceApr(String assignServiceApr) {
		this.assignServiceApr = assignServiceApr;
	}

    public void setProjectPeriod(String projectPeriod) {
        this.projectPeriod = projectPeriod;
    }

    public String getProjectPeriod() {
        return projectPeriod;
    }

    public String getPeriodView(){
		return this.getAssignPeriod() + "/" + this.getBorrowPeriod();
	}

	public String getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(String sellOrderId) {
		this.sellOrderId = sellOrderId;
	}
}
