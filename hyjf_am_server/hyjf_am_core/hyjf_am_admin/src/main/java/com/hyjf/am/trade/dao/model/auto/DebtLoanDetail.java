package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtLoanDetail implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer borrowUserId;

    private String borrowUserName;

    private String borrowNid;

    private String planNid;

    private String planOrderId;

    private Integer investId;

    private String investOrderId;

    private BigDecimal loanAccount;

    private BigDecimal loanInterest;

    private BigDecimal loanCapital;

    private BigDecimal receiveAccountYes;

    private BigDecimal receiveCapitalYes;

    private BigDecimal receiveInterestYes;

    private Integer repayPeriod;

    private String repayTime;

    private String repayActionTime;

    private BigDecimal repayAccountYes;

    private BigDecimal repayInterestYes;

    private BigDecimal repayCapitalYes;

    private BigDecimal repayAccountWait;

    private BigDecimal repayCapitalWait;

    private BigDecimal repayInterestWait;

    private Integer repayStatus;

    private BigDecimal manageFee;

    private String loanType;

    private Integer advanceStatus;

    private Integer advanceDays;

    private BigDecimal repayAdvanceInterest;

    private BigDecimal repayAdvanceInterestYes;

    private Integer lateDays;

    private BigDecimal repayLateInterest;

    private BigDecimal repayLateInterestYes;

    private Integer delayDays;

    private BigDecimal repayDelayInterest;

    private BigDecimal repayDelayInterestYes;

    private BigDecimal receiveAdvanceInterest;

    private BigDecimal receiveLateInterest;

    private BigDecimal receiveDelayInterest;

    private BigDecimal receiveAdvanceInterestYes;

    private BigDecimal receiveLateInterestYes;

    private BigDecimal receiveDelayInterestYes;

    private String addIp;

    private Integer sendmail;

    private Integer web;

    private BigDecimal creditAmount;

    private BigDecimal creditInterestAmount;

    private Integer creditTime;

    private String repayOrderId;

    private String repayOrderDate;

    private Integer createUserId;

    private String createUserName;

    private Integer updateUserId;

    private String updateUserName;

    private Date createTime;

    private Date updateTime;

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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId == null ? null : planOrderId.trim();
    }

    public Integer getInvestId() {
        return investId;
    }

    public void setInvestId(Integer investId) {
        this.investId = investId;
    }

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId == null ? null : investOrderId.trim();
    }

    public BigDecimal getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(BigDecimal loanAccount) {
        this.loanAccount = loanAccount;
    }

    public BigDecimal getLoanInterest() {
        return loanInterest;
    }

    public void setLoanInterest(BigDecimal loanInterest) {
        this.loanInterest = loanInterest;
    }

    public BigDecimal getLoanCapital() {
        return loanCapital;
    }

    public void setLoanCapital(BigDecimal loanCapital) {
        this.loanCapital = loanCapital;
    }

    public BigDecimal getReceiveAccountYes() {
        return receiveAccountYes;
    }

    public void setReceiveAccountYes(BigDecimal receiveAccountYes) {
        this.receiveAccountYes = receiveAccountYes;
    }

    public BigDecimal getReceiveCapitalYes() {
        return receiveCapitalYes;
    }

    public void setReceiveCapitalYes(BigDecimal receiveCapitalYes) {
        this.receiveCapitalYes = receiveCapitalYes;
    }

    public BigDecimal getReceiveInterestYes() {
        return receiveInterestYes;
    }

    public void setReceiveInterestYes(BigDecimal receiveInterestYes) {
        this.receiveInterestYes = receiveInterestYes;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(String repayTime) {
        this.repayTime = repayTime == null ? null : repayTime.trim();
    }

    public String getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(String repayActionTime) {
        this.repayActionTime = repayActionTime == null ? null : repayActionTime.trim();
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public BigDecimal getRepayCapitalYes() {
        return repayCapitalYes;
    }

    public void setRepayCapitalYes(BigDecimal repayCapitalYes) {
        this.repayCapitalYes = repayCapitalYes;
    }

    public BigDecimal getRepayAccountWait() {
        return repayAccountWait;
    }

    public void setRepayAccountWait(BigDecimal repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
    }

    public BigDecimal getRepayCapitalWait() {
        return repayCapitalWait;
    }

    public void setRepayCapitalWait(BigDecimal repayCapitalWait) {
        this.repayCapitalWait = repayCapitalWait;
    }

    public BigDecimal getRepayInterestWait() {
        return repayInterestWait;
    }

    public void setRepayInterestWait(BigDecimal repayInterestWait) {
        this.repayInterestWait = repayInterestWait;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
    }

    public Integer getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Integer advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public Integer getAdvanceDays() {
        return advanceDays;
    }

    public void setAdvanceDays(Integer advanceDays) {
        this.advanceDays = advanceDays;
    }

    public BigDecimal getRepayAdvanceInterest() {
        return repayAdvanceInterest;
    }

    public void setRepayAdvanceInterest(BigDecimal repayAdvanceInterest) {
        this.repayAdvanceInterest = repayAdvanceInterest;
    }

    public BigDecimal getRepayAdvanceInterestYes() {
        return repayAdvanceInterestYes;
    }

    public void setRepayAdvanceInterestYes(BigDecimal repayAdvanceInterestYes) {
        this.repayAdvanceInterestYes = repayAdvanceInterestYes;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getRepayLateInterest() {
        return repayLateInterest;
    }

    public void setRepayLateInterest(BigDecimal repayLateInterest) {
        this.repayLateInterest = repayLateInterest;
    }

    public BigDecimal getRepayLateInterestYes() {
        return repayLateInterestYes;
    }

    public void setRepayLateInterestYes(BigDecimal repayLateInterestYes) {
        this.repayLateInterestYes = repayLateInterestYes;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public BigDecimal getRepayDelayInterest() {
        return repayDelayInterest;
    }

    public void setRepayDelayInterest(BigDecimal repayDelayInterest) {
        this.repayDelayInterest = repayDelayInterest;
    }

    public BigDecimal getRepayDelayInterestYes() {
        return repayDelayInterestYes;
    }

    public void setRepayDelayInterestYes(BigDecimal repayDelayInterestYes) {
        this.repayDelayInterestYes = repayDelayInterestYes;
    }

    public BigDecimal getReceiveAdvanceInterest() {
        return receiveAdvanceInterest;
    }

    public void setReceiveAdvanceInterest(BigDecimal receiveAdvanceInterest) {
        this.receiveAdvanceInterest = receiveAdvanceInterest;
    }

    public BigDecimal getReceiveLateInterest() {
        return receiveLateInterest;
    }

    public void setReceiveLateInterest(BigDecimal receiveLateInterest) {
        this.receiveLateInterest = receiveLateInterest;
    }

    public BigDecimal getReceiveDelayInterest() {
        return receiveDelayInterest;
    }

    public void setReceiveDelayInterest(BigDecimal receiveDelayInterest) {
        this.receiveDelayInterest = receiveDelayInterest;
    }

    public BigDecimal getReceiveAdvanceInterestYes() {
        return receiveAdvanceInterestYes;
    }

    public void setReceiveAdvanceInterestYes(BigDecimal receiveAdvanceInterestYes) {
        this.receiveAdvanceInterestYes = receiveAdvanceInterestYes;
    }

    public BigDecimal getReceiveLateInterestYes() {
        return receiveLateInterestYes;
    }

    public void setReceiveLateInterestYes(BigDecimal receiveLateInterestYes) {
        this.receiveLateInterestYes = receiveLateInterestYes;
    }

    public BigDecimal getReceiveDelayInterestYes() {
        return receiveDelayInterestYes;
    }

    public void setReceiveDelayInterestYes(BigDecimal receiveDelayInterestYes) {
        this.receiveDelayInterestYes = receiveDelayInterestYes;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getSendmail() {
        return sendmail;
    }

    public void setSendmail(Integer sendmail) {
        this.sendmail = sendmail;
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getCreditInterestAmount() {
        return creditInterestAmount;
    }

    public void setCreditInterestAmount(BigDecimal creditInterestAmount) {
        this.creditInterestAmount = creditInterestAmount;
    }

    public Integer getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(Integer creditTime) {
        this.creditTime = creditTime;
    }

    public String getRepayOrderId() {
        return repayOrderId;
    }

    public void setRepayOrderId(String repayOrderId) {
        this.repayOrderId = repayOrderId == null ? null : repayOrderId.trim();
    }

    public String getRepayOrderDate() {
        return repayOrderDate;
    }

    public void setRepayOrderDate(String repayOrderDate) {
        this.repayOrderDate = repayOrderDate == null ? null : repayOrderDate.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
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