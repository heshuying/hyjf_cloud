/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author jijun
 * @date 20180623
 */
@ApiModel(value = "投资")
public class BorrowTenderTmpVO extends BaseVO implements Serializable {
	@ApiModelProperty(value = "主键id")
	private Integer id;
	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	@ApiModelProperty(value = "投资用户名")
	private String userName;
	@ApiModelProperty(value = "借款人用户ID")
	private Integer borrowUserId;
	@ApiModelProperty(value = "借款人用户名")
	private String borrowUserName;
	@ApiModelProperty(value = "状态(0:初始,1:已放款,2:放款失败)")
	private Integer status;
	@ApiModelProperty(value = "标的编号")
	private String borrowNid;
	@ApiModelProperty(value = "投资订单号")
	private String nid;
	@ApiModelProperty(value = "投资金额")
	private BigDecimal account;
	@ApiModelProperty(value = "满标状态(0:初始,1:满标)记录哪一笔是最后一笔满标")
	private Integer recoverFullStatus;
	@ApiModelProperty(value = "管理费")
	private BigDecimal recoverFee;
	@ApiModelProperty(value = "收款总额")
	private BigDecimal recoverAccountAll;
	@ApiModelProperty(value = "收款总利息")
	private BigDecimal recoverAccountInterest;
	@ApiModelProperty(value = "已收总额")
	private BigDecimal recoverAccountYes;
	@ApiModelProperty(value = "已收利息")
	private BigDecimal recoverAccountInterestYes;
	@ApiModelProperty(value = "已收本金")
	private BigDecimal recoverAccountCapitalYes;
	@ApiModelProperty(value = "待收总额")
	private BigDecimal recoverAccountWait;
	@ApiModelProperty(value = "待收利息")
	private BigDecimal recoverAccountInterestWait;
	@ApiModelProperty(value = "待收本金")
	private BigDecimal recoverAccountCapitalWait;
	@ApiModelProperty(value = "已收期数")
	private Integer recoverTimes;
	@ApiModelProperty(value = "提前还款费用")
	private BigDecimal recoverAdvanceFee;
	@ApiModelProperty(value = "逾期还款费用")
	private BigDecimal recoverLateFee;
	@ApiModelProperty(value = "放款金额")
	private BigDecimal loanAmount;
	@ApiModelProperty(value = "服务费")
	private BigDecimal loanFee;
	@ApiModelProperty(value = "创建时间")
	private Date createTime;
	@ApiModelProperty(value = "ip")
	private String addip;
	@ApiModelProperty(value = "平台0PC，1微信2安卓APP，3IOS APP，4其他")
	private Integer client;
	@ApiModelProperty(value = "推荐人用户名(投资时)")
	private String inviteUserName;
	@ApiModelProperty(value = "推荐人用户id(投资时)")
	private Integer inviteUserId;
	@ApiModelProperty(value = "一级部门id(投资时)")
	private Integer inviteRegionId;
	@ApiModelProperty(value = "一级部门名称(投资时)")
	private String inviteRegionName;
	@ApiModelProperty(value = "二级部门id(投资时)")
	private Integer inviteBranchId;
	@ApiModelProperty(value = "二级部门名称(投资时)")
	private String inviteBranchName;
	@ApiModelProperty(value = "三级部门id(投资时)")
	private Integer inviteDepartmentId;
	@ApiModelProperty(value = "三级部门名称(投资时)")
	private String inviteDepartmentName;
	@ApiModelProperty(value = "投资人用户属性")
	private Integer tenderUserAttribute;
	@ApiModelProperty(value = "推荐人用户属性")
	private Integer inviteUserAttribute;
	@ApiModelProperty(value = "投资订单日期")
	private String orderDate;
	@ApiModelProperty(value = "优惠券发放编号（coupon_user.id）")
	private Integer couponGrantId;
	@ApiModelProperty(value = "是否江西银行投资: 1 是")
	private Integer isBankTender;
	@ApiModelProperty(value = "投资来源:默认-hyjf,wrb-风车理财")
	private String tenderFrom;
	@ApiModelProperty(value = "银行返回码")
	private String retCode;;
	@ApiModelProperty(value = "银行返回码描述")
	private String retMsg;

	private static final long serialVersionUID = 1L;

	public String getTenderFrom() {
		return tenderFrom;
	}

	public void setTenderFrom(String tenderFrom) {
		this.tenderFrom = tenderFrom;
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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName == null ? null : userName.trim();
	}

	public Integer getBorrowUserId() {
		return borrowUserId;
	}

	public void setBorrowUserId(Integer borrowUserId) {
		this.borrowUserId = borrowUserId;
	}

	public String getBorrowUserName() {
		return borrowUserName;
	}

	public void setBorrowUserName(String borrowUserName) {
		this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getBorrowNid() {
		return borrowNid;
	}

	public void setBorrowNid(String borrowNid) {
		this.borrowNid = borrowNid == null ? null : borrowNid.trim();
	}

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid == null ? null : nid.trim();
	}

	public BigDecimal getAccount() {
		return account;
	}

	public void setAccount(BigDecimal account) {
		this.account = account;
	}

	public Integer getRecoverFullStatus() {
		return recoverFullStatus;
	}

	public void setRecoverFullStatus(Integer recoverFullStatus) {
		this.recoverFullStatus = recoverFullStatus;
	}

	public BigDecimal getRecoverFee() {
		return recoverFee;
	}

	public void setRecoverFee(BigDecimal recoverFee) {
		this.recoverFee = recoverFee;
	}

	public BigDecimal getRecoverAccountAll() {
		return recoverAccountAll;
	}

	public void setRecoverAccountAll(BigDecimal recoverAccountAll) {
		this.recoverAccountAll = recoverAccountAll;
	}

	public BigDecimal getRecoverAccountInterest() {
		return recoverAccountInterest;
	}

	public void setRecoverAccountInterest(BigDecimal recoverAccountInterest) {
		this.recoverAccountInterest = recoverAccountInterest;
	}

	public BigDecimal getRecoverAccountYes() {
		return recoverAccountYes;
	}

	public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
		this.recoverAccountYes = recoverAccountYes;
	}

	public BigDecimal getRecoverAccountInterestYes() {
		return recoverAccountInterestYes;
	}

	public void setRecoverAccountInterestYes(BigDecimal recoverAccountInterestYes) {
		this.recoverAccountInterestYes = recoverAccountInterestYes;
	}

	public BigDecimal getRecoverAccountCapitalYes() {
		return recoverAccountCapitalYes;
	}

	public void setRecoverAccountCapitalYes(BigDecimal recoverAccountCapitalYes) {
		this.recoverAccountCapitalYes = recoverAccountCapitalYes;
	}

	public BigDecimal getRecoverAccountWait() {
		return recoverAccountWait;
	}

	public void setRecoverAccountWait(BigDecimal recoverAccountWait) {
		this.recoverAccountWait = recoverAccountWait;
	}

	public BigDecimal getRecoverAccountInterestWait() {
		return recoverAccountInterestWait;
	}

	public void setRecoverAccountInterestWait(BigDecimal recoverAccountInterestWait) {
		this.recoverAccountInterestWait = recoverAccountInterestWait;
	}

	public BigDecimal getRecoverAccountCapitalWait() {
		return recoverAccountCapitalWait;
	}

	public void setRecoverAccountCapitalWait(BigDecimal recoverAccountCapitalWait) {
		this.recoverAccountCapitalWait = recoverAccountCapitalWait;
	}

	public Integer getRecoverTimes() {
		return recoverTimes;
	}

	public void setRecoverTimes(Integer recoverTimes) {
		this.recoverTimes = recoverTimes;
	}

	public BigDecimal getRecoverAdvanceFee() {
		return recoverAdvanceFee;
	}

	public void setRecoverAdvanceFee(BigDecimal recoverAdvanceFee) {
		this.recoverAdvanceFee = recoverAdvanceFee;
	}

	public BigDecimal getRecoverLateFee() {
		return recoverLateFee;
	}

	public void setRecoverLateFee(BigDecimal recoverLateFee) {
		this.recoverLateFee = recoverLateFee;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public BigDecimal getLoanFee() {
		return loanFee;
	}

	public void setLoanFee(BigDecimal loanFee) {
		this.loanFee = loanFee;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getAddip() {
		return addip;
	}

	public void setAddip(String addip) {
		this.addip = addip == null ? null : addip.trim();
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getInviteUserName() {
		return inviteUserName;
	}

	public void setInviteUserName(String inviteUserName) {
		this.inviteUserName = inviteUserName == null ? null : inviteUserName.trim();
	}

	public Integer getInviteUserId() {
		return inviteUserId;
	}

	public void setInviteUserId(Integer inviteUserId) {
		this.inviteUserId = inviteUserId;
	}

	public Integer getInviteRegionId() {
		return inviteRegionId;
	}

	public void setInviteRegionId(Integer inviteRegionId) {
		this.inviteRegionId = inviteRegionId;
	}

	public String getInviteRegionName() {
		return inviteRegionName;
	}

	public void setInviteRegionName(String inviteRegionName) {
		this.inviteRegionName = inviteRegionName == null ? null : inviteRegionName.trim();
	}

	public Integer getInviteBranchId() {
		return inviteBranchId;
	}

	public void setInviteBranchId(Integer inviteBranchId) {
		this.inviteBranchId = inviteBranchId;
	}

	public String getInviteBranchName() {
		return inviteBranchName;
	}

	public void setInviteBranchName(String inviteBranchName) {
		this.inviteBranchName = inviteBranchName == null ? null : inviteBranchName.trim();
	}

	public Integer getInviteDepartmentId() {
		return inviteDepartmentId;
	}

	public void setInviteDepartmentId(Integer inviteDepartmentId) {
		this.inviteDepartmentId = inviteDepartmentId;
	}

	public String getInviteDepartmentName() {
		return inviteDepartmentName;
	}

	public void setInviteDepartmentName(String inviteDepartmentName) {
		this.inviteDepartmentName = inviteDepartmentName == null ? null : inviteDepartmentName.trim();
	}

	public Integer getTenderUserAttribute() {
		return tenderUserAttribute;
	}

	public void setTenderUserAttribute(Integer tenderUserAttribute) {
		this.tenderUserAttribute = tenderUserAttribute;
	}

	public Integer getInviteUserAttribute() {
		return inviteUserAttribute;
	}

	public void setInviteUserAttribute(Integer inviteUserAttribute) {
		this.inviteUserAttribute = inviteUserAttribute;
	}

	public String getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate == null ? null : orderDate.trim();
	}

	public Integer getCouponGrantId() {
		return couponGrantId;
	}

	public void setCouponGrantId(Integer couponGrantId) {
		this.couponGrantId = couponGrantId;
	}

	public Integer getIsBankTender() {
		return isBankTender;
	}

	public void setIsBankTender(Integer isBankTender) {
		this.isBankTender = isBankTender;
	}
}
