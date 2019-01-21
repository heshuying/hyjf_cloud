package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreditTender implements Serializable {
    private Integer assignId;

    /**
     * 承接人用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 承接人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 出让人用户ID
     *
     * @mbggenerated
     */
    private Integer creditUserId;

    /**
     * 出让人用户名
     *
     * @mbggenerated
     */
    private String creditUserName;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 原标标号
     *
     * @mbggenerated
     */
    private String bidNid;

    /**
     * 标的借款人用户ID
     *
     * @mbggenerated
     */
    private Integer borrowUserId;

    /**
     * 标的借款人用户名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 债转标号
     *
     * @mbggenerated
     */
    private String creditNid;

    /**
     * 债转投标单号
     *
     * @mbggenerated
     */
    private String creditTenderNid;

    /**
     * 认购单号
     *
     * @mbggenerated
     */
    private String assignNid;

    /**
     * 投资人投标成功的授权号
     *
     * @mbggenerated
     */
    private String authCode;

    /**
     * 投资本金
     *
     * @mbggenerated
     */
    private BigDecimal assignCapital;

    /**
     * 回收总额
     *
     * @mbggenerated
     */
    private BigDecimal assignAccount;

    /**
     * 债转利息
     *
     * @mbggenerated
     */
    private BigDecimal assignInterest;

    /**
     * 垫付利息
     *
     * @mbggenerated
     */
    private BigDecimal assignInterestAdvance;

    /**
     * 购买价格
     *
     * @mbggenerated
     */
    private BigDecimal assignPrice;

    /**
     * 支付金额
     *
     * @mbggenerated
     */
    private BigDecimal assignPay;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayAccount;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayCapital;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal assignRepayInterest;

    /**
     * 最后还款日
     *
     * @mbggenerated
     */
    private Integer assignRepayEndTime;

    /**
     * 上次还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayLastTime;

    /**
     * 下次还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayNextTime;

    /**
     * 最终实际还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayYesTime;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer assignRepayPeriod;

    /**
     * 认购日期
     *
     * @mbggenerated
     */
    private Integer assignCreateDate;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal creditFee;

    /**
     * 承接人的推荐人用户名
     *
     * @mbggenerated
     */
    private String inviteUserName;

    /**
     * 承接人的推荐人用户属性
     *
     * @mbggenerated
     */
    private Integer inviteUserAttribute;

    /**
     * 承接人的推荐人部门信息
     *
     * @mbggenerated
     */
    private String inviteUserRegionname;

    /**
     * 承接人的推荐人部门信息
     *
     * @mbggenerated
     */
    private String inviteUserBranchname;

    /**
     * 团队(投资时)
     *
     * @mbggenerated
     */
    private String inviteUserDepartmentname;

    /**
     * 出让人的推荐人用户名
     *
     * @mbggenerated
     */
    private String inviteUserCreditName;

    /**
     * 出让人的推荐人属性
     *
     * @mbggenerated
     */
    private Integer inviteUserCreditAttribute;

    /**
     * 出让人的推荐人用户部门信息
     *
     * @mbggenerated
     */
    private String inviteUserCreditRegionname;

    /**
     * 出让人的推荐人用户部门信息
     *
     * @mbggenerated
     */
    private String inviteUserCreditBranchname;

    /**
     * 出让人的推荐人用户部门信息
     *
     * @mbggenerated
     */
    private String inviteUserCreditDepartmentname;

    /**
     * 客户端
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 已还款期数
     *
     * @mbggenerated
     */
    private Integer recoverPeriod;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getAssignId() {
        return assignId;
    }

    public void setAssignId(Integer assignId) {
        this.assignId = assignId;
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

    public Integer getCreditUserId() {
        return creditUserId;
    }

    public void setCreditUserId(Integer creditUserId) {
        this.creditUserId = creditUserId;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName == null ? null : creditUserName.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid == null ? null : bidNid.trim();
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

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid == null ? null : creditNid.trim();
    }

    public String getCreditTenderNid() {
        return creditTenderNid;
    }

    public void setCreditTenderNid(String creditTenderNid) {
        this.creditTenderNid = creditTenderNid == null ? null : creditTenderNid.trim();
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid == null ? null : assignNid.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public BigDecimal getAssignCapital() {
        return assignCapital;
    }

    public void setAssignCapital(BigDecimal assignCapital) {
        this.assignCapital = assignCapital;
    }

    public BigDecimal getAssignAccount() {
        return assignAccount;
    }

    public void setAssignAccount(BigDecimal assignAccount) {
        this.assignAccount = assignAccount;
    }

    public BigDecimal getAssignInterest() {
        return assignInterest;
    }

    public void setAssignInterest(BigDecimal assignInterest) {
        this.assignInterest = assignInterest;
    }

    public BigDecimal getAssignInterestAdvance() {
        return assignInterestAdvance;
    }

    public void setAssignInterestAdvance(BigDecimal assignInterestAdvance) {
        this.assignInterestAdvance = assignInterestAdvance;
    }

    public BigDecimal getAssignPrice() {
        return assignPrice;
    }

    public void setAssignPrice(BigDecimal assignPrice) {
        this.assignPrice = assignPrice;
    }

    public BigDecimal getAssignPay() {
        return assignPay;
    }

    public void setAssignPay(BigDecimal assignPay) {
        this.assignPay = assignPay;
    }

    public BigDecimal getAssignRepayAccount() {
        return assignRepayAccount;
    }

    public void setAssignRepayAccount(BigDecimal assignRepayAccount) {
        this.assignRepayAccount = assignRepayAccount;
    }

    public BigDecimal getAssignRepayCapital() {
        return assignRepayCapital;
    }

    public void setAssignRepayCapital(BigDecimal assignRepayCapital) {
        this.assignRepayCapital = assignRepayCapital;
    }

    public BigDecimal getAssignRepayInterest() {
        return assignRepayInterest;
    }

    public void setAssignRepayInterest(BigDecimal assignRepayInterest) {
        this.assignRepayInterest = assignRepayInterest;
    }

    public Integer getAssignRepayEndTime() {
        return assignRepayEndTime;
    }

    public void setAssignRepayEndTime(Integer assignRepayEndTime) {
        this.assignRepayEndTime = assignRepayEndTime;
    }

    public Integer getAssignRepayLastTime() {
        return assignRepayLastTime;
    }

    public void setAssignRepayLastTime(Integer assignRepayLastTime) {
        this.assignRepayLastTime = assignRepayLastTime;
    }

    public Integer getAssignRepayNextTime() {
        return assignRepayNextTime;
    }

    public void setAssignRepayNextTime(Integer assignRepayNextTime) {
        this.assignRepayNextTime = assignRepayNextTime;
    }

    public Integer getAssignRepayYesTime() {
        return assignRepayYesTime;
    }

    public void setAssignRepayYesTime(Integer assignRepayYesTime) {
        this.assignRepayYesTime = assignRepayYesTime;
    }

    public Integer getAssignRepayPeriod() {
        return assignRepayPeriod;
    }

    public void setAssignRepayPeriod(Integer assignRepayPeriod) {
        this.assignRepayPeriod = assignRepayPeriod;
    }

    public Integer getAssignCreateDate() {
        return assignCreateDate;
    }

    public void setAssignCreateDate(Integer assignCreateDate) {
        this.assignCreateDate = assignCreateDate;
    }

    public BigDecimal getCreditFee() {
        return creditFee;
    }

    public void setCreditFee(BigDecimal creditFee) {
        this.creditFee = creditFee;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName == null ? null : inviteUserName.trim();
    }

    public Integer getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(Integer inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public String getInviteUserRegionname() {
        return inviteUserRegionname;
    }

    public void setInviteUserRegionname(String inviteUserRegionname) {
        this.inviteUserRegionname = inviteUserRegionname == null ? null : inviteUserRegionname.trim();
    }

    public String getInviteUserBranchname() {
        return inviteUserBranchname;
    }

    public void setInviteUserBranchname(String inviteUserBranchname) {
        this.inviteUserBranchname = inviteUserBranchname == null ? null : inviteUserBranchname.trim();
    }

    public String getInviteUserDepartmentname() {
        return inviteUserDepartmentname;
    }

    public void setInviteUserDepartmentname(String inviteUserDepartmentname) {
        this.inviteUserDepartmentname = inviteUserDepartmentname == null ? null : inviteUserDepartmentname.trim();
    }

    public String getInviteUserCreditName() {
        return inviteUserCreditName;
    }

    public void setInviteUserCreditName(String inviteUserCreditName) {
        this.inviteUserCreditName = inviteUserCreditName == null ? null : inviteUserCreditName.trim();
    }

    public Integer getInviteUserCreditAttribute() {
        return inviteUserCreditAttribute;
    }

    public void setInviteUserCreditAttribute(Integer inviteUserCreditAttribute) {
        this.inviteUserCreditAttribute = inviteUserCreditAttribute;
    }

    public String getInviteUserCreditRegionname() {
        return inviteUserCreditRegionname;
    }

    public void setInviteUserCreditRegionname(String inviteUserCreditRegionname) {
        this.inviteUserCreditRegionname = inviteUserCreditRegionname == null ? null : inviteUserCreditRegionname.trim();
    }

    public String getInviteUserCreditBranchname() {
        return inviteUserCreditBranchname;
    }

    public void setInviteUserCreditBranchname(String inviteUserCreditBranchname) {
        this.inviteUserCreditBranchname = inviteUserCreditBranchname == null ? null : inviteUserCreditBranchname.trim();
    }

    public String getInviteUserCreditDepartmentname() {
        return inviteUserCreditDepartmentname;
    }

    public void setInviteUserCreditDepartmentname(String inviteUserCreditDepartmentname) {
        this.inviteUserCreditDepartmentname = inviteUserCreditDepartmentname == null ? null : inviteUserCreditDepartmentname.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}