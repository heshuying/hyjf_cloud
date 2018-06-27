package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowTenderTmp implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer borrowUserId;

    private String borrowUserName;

    private Integer status;

    private String borrowNid;

    private String nid;

    private BigDecimal account;

    private Integer recoverFullStatus;

    private BigDecimal recoverFee;

    private BigDecimal recoverAccountAll;

    private BigDecimal recoverAccountInterest;

    private BigDecimal recoverAccountYes;

    private BigDecimal recoverAccountInterestYes;

    private BigDecimal recoverAccountCapitalYes;

    private BigDecimal recoverAccountWait;

    private BigDecimal recoverAccountInterestWait;

    private BigDecimal recoverAccountCapitalWait;

    private Integer recoverTimes;

    private BigDecimal recoverAdvanceFee;

    private BigDecimal recoverLateFee;

    private BigDecimal loanAmount;

    private BigDecimal loanFee;

    private String addIp;

    private Integer client;

    private String inviteUserName;

    private Integer inviteUserId;

    private Integer inviteRegionId;

    private String inviteRegionName;

    private Integer inviteBranchId;

    private String inviteBranchName;

    private Integer inviteDepartmentId;

    private String inviteDepartmentName;

    private Integer tenderUserAttribute;

    private Integer inviteUserAttribute;

    private String orderDate;

    private Integer couponGrantId;

    private Integer isBankTender;

    private Date createTime;

    private static final long serialVersionUID = 1L;

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

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}