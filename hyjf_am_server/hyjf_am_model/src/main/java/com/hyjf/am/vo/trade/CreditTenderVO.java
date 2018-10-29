package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class CreditTenderVO extends BaseVO implements Serializable {
    private Integer assignId;

    private Integer userId;

    private String userName;

    private Integer creditUserId;

    private String creditUserName;

    private Integer status;

    private String bidNid;

    private Integer borrowUserId;

    private String borrowUserName;

    private String creditNid;

    private String creditTenderNid;

    private String assignNid;

    private String authCode;

    private BigDecimal assignCapital;

    private BigDecimal assignAccount;

    private BigDecimal assignInterest;

    private BigDecimal assignInterestAdvance;

    private BigDecimal assignPrice;

    private BigDecimal assignPay;

    private BigDecimal assignRepayAccount;

    private BigDecimal assignRepayCapital;

    private BigDecimal assignRepayInterest;

    private Integer assignRepayEndTime;

    private Integer assignRepayLastTime;

    private Integer assignRepayNextTime;

    private Integer assignRepayYesTime;

    private Integer assignRepayPeriod;

    private Integer assignCreateDate;

    private BigDecimal creditFee;

    private String inviteUserName;

    private Integer inviteUserAttribute;

    private String inviteUserRegionname;

    private String inviteUserBranchname;

    private String inviteUserDepartmentname;

    private String inviteUserCreditName;

    private Integer inviteUserCreditAttribute;

    private String inviteUserCreditRegionname;

    private String inviteUserCreditBranchname;

    private String inviteUserCreditDepartmentname;

    private Integer client;

    private Integer recoverPeriod;

    private String addIp;

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
        this.userName = userName;
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
        this.creditUserName = creditUserName;
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
        this.bidNid = bidNid;
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
        this.borrowUserName = borrowUserName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
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
        this.inviteUserName = inviteUserName;
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
        this.inviteUserRegionname = inviteUserRegionname;
    }

    public String getInviteUserBranchname() {
        return inviteUserBranchname;
    }

    public void setInviteUserBranchname(String inviteUserBranchname) {
        this.inviteUserBranchname = inviteUserBranchname;
    }

    public String getInviteUserDepartmentname() {
        return inviteUserDepartmentname;
    }

    public void setInviteUserDepartmentname(String inviteUserDepartmentname) {
        this.inviteUserDepartmentname = inviteUserDepartmentname;
    }

    public String getInviteUserCreditName() {
        return inviteUserCreditName;
    }

    public void setInviteUserCreditName(String inviteUserCreditName) {
        this.inviteUserCreditName = inviteUserCreditName;
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
        this.inviteUserCreditRegionname = inviteUserCreditRegionname;
    }

    public String getInviteUserCreditBranchname() {
        return inviteUserCreditBranchname;
    }

    public void setInviteUserCreditBranchname(String inviteUserCreditBranchname) {
        this.inviteUserCreditBranchname = inviteUserCreditBranchname;
    }

    public String getInviteUserCreditDepartmentname() {
        return inviteUserCreditDepartmentname;
    }

    public void setInviteUserCreditDepartmentname(String inviteUserCreditDepartmentname) {
        this.inviteUserCreditDepartmentname = inviteUserCreditDepartmentname;
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
        this.addIp = addIp;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}