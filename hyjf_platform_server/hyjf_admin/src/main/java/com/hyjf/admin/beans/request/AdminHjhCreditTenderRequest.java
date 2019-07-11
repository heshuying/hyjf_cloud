/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author libin
 * @version AdminHjhCreditTenderRequest.java, v0.1 2018年7月21日 上午11:45:47
 */
public class AdminHjhCreditTenderRequest extends BaseRequest implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "检索条件项目还款方式")
	private String repayStyle;

	@ApiModelProperty(value = "检索条件 债权承接方式")
	private String assignType;

	@ApiModelProperty(value = "检索条件 承接时间开始")
	private String assignTimeStart;
	
	@ApiModelProperty(value = "检索条件 承接时间开始")
	private String assignTimeEnd;
	
	@ApiModelProperty(value = "检索条件 是否复投承接")
	private String tenderType;
	
	/**
	 * 翻页机能用的隐藏变量
	 */
	private int paginatorPage = 1;

	/**
	 * 检索条件 limitStart
	 */
	private int limitStart = -1;

	/**
	 * 检索条件 limitEnd
	 */
	private int limitEnd = -1;
	
	@ApiModelProperty(value = "是否从加入明细列表跳转 1:是 0:否")
    private int isAccedelist = 0;

	@ApiModelProperty(value = "主键")
	private String id;

	@ApiModelProperty(value = "承接人id")
	private String userId;
	
	@ApiModelProperty(value = "承接人")
	private String assignUserName;
	
	@ApiModelProperty(value = "承接计划编号")
	private String assignPlanNid;
	
	@ApiModelProperty(value = "承接计划的计划订单号")
	private String assignPlanOrderId;
	/**
	 * 承接订单号(计划订单号是加入计划时生成的订单号，
	 * 加入后出借会有出借订单号 也就是tender表的nig，
	 * 债转相当于再出借 然后债转再出借时生成 assignOrderId 类似于 nid,跟加入计划的订单号无关)
	 */
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
	
	private int limit;

	@ApiModelProperty(value = "隐藏域用户id")
	private String userIdHidden;

	@ApiModelProperty(value = "隐藏域标的号")
	private String borrowNidHidden;

	@ApiModelProperty(value = "隐藏域承接订单号")
	private String assignNidHidden;

	@ApiModelProperty(value = "隐藏域债转编号")
	private String creditNidHidden;
	
	@ApiModelProperty(value = "是否从债转标的页面调转(1:是)")
	private String isOptFlag;
	
	@ApiModelProperty(value = "债转服务费")
	private String assignServiceFee;

	@ApiModelProperty(value = "债转服务费率")
	private String assignServiceApr;

	@ApiModelProperty(value = "出让人智投订单号")
	private String sellOrderId;

	@ApiModelProperty(value = "债权结束状态 S-成功;F-失败;A-初始:W-未开始")
	private String stateSrch;

	/**
	 * 构造方法
	 */
	public AdminHjhCreditTenderRequest() {
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
	
	public int getIsAccedelist() {
        return isAccedelist;
    }

    public void setIsAccedelist(int isAccedelist) {
        this.isAccedelist = isAccedelist;
    }

    /**
	 * limitStart
	 * 
	 * @return the limitStart
	 */
	public int getLimitStart() {
		return limitStart;
	}

	/**
	 * @param limitStart
	 *            the limitStart to set
	 */

	public void setLimitStart(int limitStart) {
		this.limitStart = limitStart;
	}

	/**
	 * limitEnd
	 * 
	 * @return the limitEnd
	 */

	public int getLimitEnd() {
		return limitEnd;
	}

	/**
	 * @param limitEnd
	 *            the limitEnd to set
	 */
	public void setLimitEnd(int limitEnd) {
		this.limitEnd = limitEnd;
	}

	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	public void setPaginatorPage(int paginatorPage) {
		this.paginatorPage = paginatorPage;
	}

	public String getAssignTimeStart() {
		return assignTimeStart;
	}

	public void setAssignTimeStart(String assignTimeStart) {
		this.assignTimeStart = assignTimeStart;
	}

	public String getAssignTimeEnd() {
		return assignTimeEnd;
	}

	public void setAssignTimeEnd(String assignTimeEnd) {
		this.assignTimeEnd = assignTimeEnd;
	}

	public String getRepayStyle() {
		return repayStyle;
	}

	public void setRepayStyle(String repayStyle) {
		this.repayStyle = repayStyle;
	}

	public String getAssignType() {
		return assignType;
	}

	public void setAssignType(String assignType) {
		this.assignType = assignType;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getUserIdHidden() {
		return userIdHidden;
	}

	public void setUserIdHidden(String userIdHidden) {
		this.userIdHidden = userIdHidden;
	}

	public String getBorrowNidHidden() {
		return borrowNidHidden;
	}

	public void setBorrowNidHidden(String borrowNidHidden) {
		this.borrowNidHidden = borrowNidHidden;
	}

	public String getAssignNidHidden() {
		return assignNidHidden;
	}

	public void setAssignNidHidden(String assignNidHidden) {
		this.assignNidHidden = assignNidHidden;
	}

	public String getCreditNidHidden() {
		return creditNidHidden;
	}

	public void setCreditNidHidden(String creditNidHidden) {
		this.creditNidHidden = creditNidHidden;
	}

	public String getIsOptFlag() {
		return isOptFlag;
	}

	public void setIsOptFlag(String isOptFlag) {
		this.isOptFlag = isOptFlag;
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

	public String getSellOrderId() {
		return sellOrderId;
	}

	public void setSellOrderId(String sellOrderId) {
		this.sellOrderId = sellOrderId;
	}

	public String getStateSrch() {
		return stateSrch;
	}

	public void setStateSrch(String stateSrch) {
		this.stateSrch = stateSrch;
	}
}
