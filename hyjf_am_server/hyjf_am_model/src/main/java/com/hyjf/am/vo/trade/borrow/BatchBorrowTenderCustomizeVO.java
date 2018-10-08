/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author jijun
 * @date 20180623
 */
public class BatchBorrowTenderCustomizeVO extends BaseVO implements Serializable {


    private String accountNo;

    private Integer id;

    private Integer userId;

    private Integer status;

    private String borrowNid;

    private String nid;

    private String authCode;

    private BigDecimal accountTender;

    private BigDecimal account;

    private Integer changeStatus;

    private Integer changeUserid;

    private Integer changePeriod;

    private Integer tenderStatus;

    private String tenderNid;

    private BigDecimal tenderAwardAccount;

    private Integer recoverFullStatus;

    private BigDecimal recoverFee;

    private String recoverType;

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

    private BigDecimal tenderAwardFee;

    private BigDecimal loanAmount;

    private BigDecimal loanFee;

    private String contents;

    private Integer autoStatus;

    private Integer webStatus;

    private Integer apiStatus;

    private Integer addtime;

    private String addip;

    private Integer periodStatus;

    private Integer isok;

    private Integer isReport;

    private Integer flag;

    private Integer activityFlag;

    private Integer client;

    private Integer web;

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

    private String loanOrderDate;

    private String loanOrdid;

    private String tenderUserName;

    private String remark;

    private Integer investType;

    private static final long serialVersionUID = 1L;


    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public BigDecimal getAccountTender() {
        return accountTender;
    }

    public void setAccountTender(BigDecimal accountTender) {
        this.accountTender = accountTender;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
    }

    public Integer getChangeUserid() {
        return changeUserid;
    }

    public void setChangeUserid(Integer changeUserid) {
        this.changeUserid = changeUserid;
    }

    public Integer getChangePeriod() {
        return changePeriod;
    }

    public void setChangePeriod(Integer changePeriod) {
        this.changePeriod = changePeriod;
    }

    public Integer getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(Integer tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid == null ? null : tenderNid.trim();
    }

    public BigDecimal getTenderAwardAccount() {
        return tenderAwardAccount;
    }

    public void setTenderAwardAccount(BigDecimal tenderAwardAccount) {
        this.tenderAwardAccount = tenderAwardAccount;
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

    public String getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(String recoverType) {
        this.recoverType = recoverType == null ? null : recoverType.trim();
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

    public BigDecimal getTenderAwardFee() {
        return tenderAwardFee;
    }

    public void setTenderAwardFee(BigDecimal tenderAwardFee) {
        this.tenderAwardFee = tenderAwardFee;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Integer getAutoStatus() {
        return autoStatus;
    }

    public void setAutoStatus(Integer autoStatus) {
        this.autoStatus = autoStatus;
    }

    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public Integer getAddtime() {
        return addtime;
    }

    public void setAddtime(Integer addtime) {
        this.addtime = addtime;
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
    }

    public Integer getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(Integer periodStatus) {
        this.periodStatus = periodStatus;
    }

    public Integer getIsok() {
        return isok;
    }

    public void setIsok(Integer isok) {
        this.isok = isok;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(Integer activityFlag) {
        this.activityFlag = activityFlag;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
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

    public String getLoanOrderDate() {
        return loanOrderDate;
    }

    public void setLoanOrderDate(String loanOrderDate) {
        this.loanOrderDate = loanOrderDate == null ? null : loanOrderDate.trim();
    }

    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid == null ? null : loanOrdid.trim();
    }

    public String getTenderUserName() {
        return tenderUserName;
    }

    public void setTenderUserName(String tenderUserName) {
        this.tenderUserName = tenderUserName == null ? null : tenderUserName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }



}
