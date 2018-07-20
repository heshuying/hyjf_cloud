package com.hyjf.admin.beans.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

public class AdminWithdrawAPIVO extends BaseVO implements Serializable {

	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "主键")
  	private Integer id;

	@ApiModelProperty(value = "用户id")
	private Integer userId;

	@ApiModelProperty(value = "交易凭证号")
	private String nid;

	@ApiModelProperty(value = "提现状态:0:提现中,1:失败,2:成功")
	private Integer status;

	@ApiModelProperty(value = "账号")
	private String account;

	@ApiModelProperty(value = "所属银行")
	private String bank;

	@ApiModelProperty(value = "所属编号")
	private Integer bankId;

	@ApiModelProperty(value = "总额")
	private BigDecimal total;

	@ApiModelProperty(value = "到账总额")
	private BigDecimal credited;

	@ApiModelProperty(value = "手续费")
	private String fee;

	@ApiModelProperty(value = "创建的IP")
	private String addIp;

	@ApiModelProperty(value = "备注")
	private String remark;

	@ApiModelProperty(value = "平台 0pc 1wechat 2android 3ios 4other")
	private Integer client;

	@ApiModelProperty(value = "提现原因")
	private String reason;

	@ApiModelProperty(value = "银行存管提现标志位 1为银行存管 0非银行存管")
	private Integer bankFlag;

	@ApiModelProperty(value = "银行电子账号")
	private String accountId;

	@ApiModelProperty(value = "交易日期")
	private Integer txDate;

	@ApiModelProperty(value = "交易时间")
	private Integer txTime;

	@ApiModelProperty(value = "交易流水号")
	private Integer seqNo;

	@ApiModelProperty(value = "银行交易流水号")
	private String bankSeqNo;

	@ApiModelProperty(value = "提现类型 0主动提现  1代提现")
	private Integer withdrawType;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "订单号，提现银行，提交时间，提现平台，处理状态")
	private String ordid;

	@ApiModelProperty(value = "用户属性")
	private String userProperty;

	@ApiModelProperty(value = "IP地址")
	private String ip;

	@ApiModelProperty(value = "用户属性(当前)")
	private String userAttribute;

	@ApiModelProperty(value = "用户所属一级分部（当前）")
	private String userRegionName;

	@ApiModelProperty(value = "用户所属二级分部（当前）")
	private String userBranchName;

	@ApiModelProperty(value = "用户所属团队（当前）")
	private String userDepartmentName;

	@ApiModelProperty(value = "推荐人用户名（当前）")
	private String referrerName;

	@ApiModelProperty(value = "推荐人userId（当前）")
	private String referrerUserId;

	@ApiModelProperty(value = "推荐人真实姓名（当前）")
	private String referrerTrueName;

	@ApiModelProperty(value = "推荐人所属一级分部（当前）")
	private String referrerRegionName;

	@ApiModelProperty(value = "推荐人所属二级分部（当前）")
	private String referrerBranchName;

	@ApiModelProperty(value = "推荐人所属团队（当前）")
	private String referrerDepartmentName;


	public String getAddtimeStr() {
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getClientStr() {
		return clientStr;
	}

	public void setClientStr(String clientStr) {
		this.clientStr = clientStr;
	}

	public String getStatusStr() {
		return statusStr;
	}

	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}

	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "角色ID")
	private String roleid;

	@ApiModelProperty(value = "添加时间")
	private String addtimeStr;

	@ApiModelProperty(value = "平台名称")
	private String clientStr;

	@ApiModelProperty(value = "状态名称")
	private String statusStr;

	/**
	 * 分页功能
	 */
	public int limit;

	private int paginatorPage = 1;
	public int getPaginatorPage() {
		if (paginatorPage == 0) {
			paginatorPage = 1;
		}
		return paginatorPage;
	}

	@ApiModelProperty(value = "交易日期")
	private String txDateS;

	@ApiModelProperty(value = "交易时间")
	private String txTimeS;


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getOrdid() {
		return ordid;
	}

	public void setOrdid(String ordid) {
		this.ordid = ordid;
	}

	public String getUserProperty() {
		return userProperty;
	}

	public void setUserProperty(String userProperty) {
		this.userProperty = userProperty;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getUserAttribute() {
		return userAttribute;
	}

	public void setUserAttribute(String userAttribute) {
		this.userAttribute = userAttribute;
	}

	public String getUserRegionName() {
		return userRegionName;
	}

	public void setUserRegionName(String userRegionName) {
		this.userRegionName = userRegionName;
	}

	public String getUserBranchName() {
		return userBranchName;
	}

	public void setUserBranchName(String userBranchName) {
		this.userBranchName = userBranchName;
	}

	public String getUserDepartmentName() {
		return userDepartmentName;
	}

	public void setUserDepartmentName(String userDepartmentName) {
		this.userDepartmentName = userDepartmentName;
	}

	public String getReferrerName() {
		return referrerName;
	}

	public void setReferrerName(String referrerName) {
		this.referrerName = referrerName;
	}

	public String getReferrerUserId() {
		return referrerUserId;
	}

	public void setReferrerUserId(String referrerUserId) {
		this.referrerUserId = referrerUserId;
	}

	public String getReferrerTrueName() {
		return referrerTrueName;
	}

	public void setReferrerTrueName(String referrerTrueName) {
		this.referrerTrueName = referrerTrueName;
	}

	public String getReferrerRegionName() {
		return referrerRegionName;
	}

	public void setReferrerRegionName(String referrerRegionName) {
		this.referrerRegionName = referrerRegionName;
	}

	public String getReferrerBranchName() {
		return referrerBranchName;
	}

	public void setReferrerBranchName(String referrerBranchName) {
		this.referrerBranchName = referrerBranchName;
	}

	public String getReferrerDepartmentName() {
		return referrerDepartmentName;
	}

	public void setReferrerDepartmentName(String referrerDepartmentName) {
		this.referrerDepartmentName = referrerDepartmentName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getRoleid() {
		return roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	public String getTxDateS() {
		return txDateS;
	}

	public void setTxDateS(String txDateS) {
		this.txDateS = txDateS;
	}

	public String getTxTimeS() {
		return txTimeS;
	}

	public void setTxTimeS(String txTimeS) {
		this.txTimeS = txTimeS;
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

	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid == null ? null : nid.trim();
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account == null ? null : account.trim();
	}

	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank == null ? null : bank.trim();
	}

	public Integer getBankId() {
		return bankId;
	}

	public void setBankId(Integer bankId) {
		this.bankId = bankId;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCredited() {
		return credited;
	}

	public void setCredited(BigDecimal credited) {
		this.credited = credited;
	}

	public String getFee() {
		return fee;
	}

	public void setFee(String fee) {
		this.fee = fee == null ? null : fee.trim();
	}

	public String getAddIp() {
		return addIp;
	}

	public void setAddIp(String addIp) {
		this.addIp = addIp == null ? null : addIp.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getClient() {
		return client;
	}

	public void setClient(Integer client) {
		this.client = client;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason == null ? null : reason.trim();
	}

	public Integer getBankFlag() {
		return bankFlag;
	}

	public void setBankFlag(Integer bankFlag) {
		this.bankFlag = bankFlag;
	}

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId == null ? null : accountId.trim();
	}

	public Integer getTxDate() {
		return txDate;
	}

	public void setTxDate(Integer txDate) {
		this.txDate = txDate;
	}

	public Integer getTxTime() {
		return txTime;
	}

	public void setTxTime(Integer txTime) {
		this.txTime = txTime;
	}

	public Integer getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(Integer seqNo) {
		this.seqNo = seqNo;
	}

	public String getBankSeqNo() {
		return bankSeqNo;
	}

	public void setBankSeqNo(String bankSeqNo) {
		this.bankSeqNo = bankSeqNo == null ? null : bankSeqNo.trim();
	}

	public Integer getWithdrawType() {
		return withdrawType;
	}

	public void setWithdrawType(Integer withdrawType) {
		this.withdrawType = withdrawType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
