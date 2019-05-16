/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author libin
 * @version AdminHjhCreditTenderCustomizeVO.java, v0.1 2018年7月21日 下午1:56:14
 */
public class AdminHjhCreditTenderCustomizeVO extends BaseVO implements Serializable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "承接人id")
	private String userId;
	
	@ApiModelProperty(value = "承接人")
	private String assignUserName;
	
	@ApiModelProperty(value = "承接计划编号")
	private String assignPlanNid;
	
	@ApiModelProperty(value = "承接计划订单号-计划订单号")
	private String assignPlanOrderId;
	
	@ApiModelProperty(value = "承接订单号")
	private String assignOrderId;
	
	@ApiModelProperty(value = "出让人用户名-出让人")
	private String creditUserName;
	
	@ApiModelProperty(value = "债转编号")
	private String creditNid;

	@ApiModelProperty(value = "项目编号-原项目编号")
	private String borrowNid;
	
	@ApiModelProperty(value = "还款方式")
	private String repayStyleName;

	@ApiModelProperty(value = "承接本金")
	private String assignCapital;
	
	@ApiModelProperty(value = "垫付利息")
	private String assignInterestAdvance;
	
	@ApiModelProperty(value = "实际支付金额")
	private String assignPay;
	
	@ApiModelProperty(value = "承接类型")
	private String assignTypeName;

	@ApiModelProperty(value = "承接时间")
	private String assignTime;

	@ApiModelProperty(value = "项目总期数")
	private String borrowPeriod;

	@ApiModelProperty(value = "承接时所在期数")
	private String assignPeriod;
	
	@ApiModelProperty(value = "合同状态")
	private String contractStatus;

	@ApiModelProperty(value = "合同编号")
	private String contractNumber;

	@ApiModelProperty(value = "合同下载地址")
	private String downloadUrl;

	@ApiModelProperty(value = "合同查看地址")
	private String viewpdfUrl;

	@ApiModelProperty(value = "脱敏合同地址")
	private String imgUrl;
	
	@ApiModelProperty(value = "是否复投承接")
	private String tenderType;

	@ApiModelProperty(value = "债转服务费")
	private String assignServiceFee;

	@ApiModelProperty(value = "债转服务费率")
	private String assignServiceApr;

	@ApiModelProperty(value = "出让人智投订单号")
	private String sellOrderId;

	/**
	 * 构造方法
	 */
	public AdminHjhCreditTenderCustomizeVO() {
		super();
	}

	public String getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(String sellOrderId) {
		this.sellOrderId = sellOrderId;
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
}
