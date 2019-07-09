package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowRecoverPlanVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer status;

    private Integer userId;

    private String userName;

    private String borrowNid;

    private String nid;

    private Integer borrowUserid;

    private String borrowUserName;

    private Integer tenderId;

    private String accedeOrderId;

    private Integer recoverStatus;

    private Integer recoverPeriod;

    private Integer recoverTime;

    private String recoverYestime;

    private BigDecimal recoverAccount;

    private BigDecimal recoverInterest;

    private BigDecimal recoverCapital;

    private BigDecimal recoverAccountYes;

    private BigDecimal recoverInterestYes;

    private BigDecimal recoverCapitalYes;

    private BigDecimal recoverAccountWait;

    private BigDecimal recoverCapitalWait;

    private BigDecimal recoverInterestWait;

    private String recoverType;

    private BigDecimal recoverFee;

    private BigDecimal recoverLateFee;

    private Integer advanceStatus;

    private Integer chargeDays;

    private BigDecimal chargeInterest;

    private Integer lateDays;

    private BigDecimal lateInterest;

    private Integer delayDays;

    private BigDecimal delayInterest;

    private BigDecimal delayRate;

    private Date createTime;

    private String addIp;

    private Integer sendmail;

    private String repayOrderId;

    private String repayOrderDate;

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

    private String authCode;

    private String loanBatchNo;

    private String repayBatchNo;

    private BigDecimal recoverFeeYes;

    private Integer creditStatus;

    private BigDecimal repayChargeInterest;

    private BigDecimal repayDelayInterest;

    private BigDecimal repayLateInterest;

    private BigDecimal creditAmount;

    private BigDecimal creditInterestAmount;

    private BigDecimal creditManageFee;

    private Integer debtStatus;

    private BigDecimal creditInterest;

    // 提前还款罚息
    public BigDecimal chargePenaltyInterest;

    // 已还提前还款罚息
    public BigDecimal repayChargePenaltyInterest;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getBorrowUserid() {
        return borrowUserid;
    }

    public void setBorrowUserid(Integer borrowUserid) {
        this.borrowUserid = borrowUserid;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public Integer getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Integer recoverTime) {
        this.recoverTime = recoverTime;
    }

    public String getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(String recoverYestime) {
        this.recoverYestime = recoverYestime == null ? null : recoverYestime.trim();
    }

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public BigDecimal getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public BigDecimal getRecoverInterestYes() {
        return recoverInterestYes;
    }

    public void setRecoverInterestYes(BigDecimal recoverInterestYes) {
        this.recoverInterestYes = recoverInterestYes;
    }

    public BigDecimal getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(BigDecimal recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public BigDecimal getRecoverAccountWait() {
        return recoverAccountWait;
    }

    public void setRecoverAccountWait(BigDecimal recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public BigDecimal getRecoverCapitalWait() {
        return recoverCapitalWait;
    }

    public void setRecoverCapitalWait(BigDecimal recoverCapitalWait) {
        this.recoverCapitalWait = recoverCapitalWait;
    }

    public BigDecimal getRecoverInterestWait() {
        return recoverInterestWait;
    }

    public void setRecoverInterestWait(BigDecimal recoverInterestWait) {
        this.recoverInterestWait = recoverInterestWait;
    }

    public String getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(String recoverType) {
        this.recoverType = recoverType == null ? null : recoverType.trim();
    }

    public BigDecimal getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(BigDecimal recoverFee) {
        this.recoverFee = recoverFee;
    }

    public BigDecimal getRecoverLateFee() {
        return recoverLateFee;
    }

    public void setRecoverLateFee(BigDecimal recoverLateFee) {
        this.recoverLateFee = recoverLateFee;
    }

    public Integer getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Integer advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public Integer getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(BigDecimal chargeInterest) {
        this.chargeInterest = chargeInterest;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public BigDecimal getDelayInterest() {
        return delayInterest;
    }

    public void setDelayInterest(BigDecimal delayInterest) {
        this.delayInterest = delayInterest;
    }

    public BigDecimal getDelayRate() {
        return delayRate;
    }

    public void setDelayRate(BigDecimal delayRate) {
        this.delayRate = delayRate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getLoanBatchNo() {
        return loanBatchNo;
    }

    public void setLoanBatchNo(String loanBatchNo) {
        this.loanBatchNo = loanBatchNo == null ? null : loanBatchNo.trim();
    }

    public String getRepayBatchNo() {
        return repayBatchNo;
    }

    public void setRepayBatchNo(String repayBatchNo) {
        this.repayBatchNo = repayBatchNo == null ? null : repayBatchNo.trim();
    }

    public BigDecimal getRecoverFeeYes() {
        return recoverFeeYes;
    }

    public void setRecoverFeeYes(BigDecimal recoverFeeYes) {
        this.recoverFeeYes = recoverFeeYes;
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public BigDecimal getRepayChargeInterest() {
        return repayChargeInterest;
    }

    public void setRepayChargeInterest(BigDecimal repayChargeInterest) {
        this.repayChargeInterest = repayChargeInterest;
    }

    public BigDecimal getRepayDelayInterest() {
        return repayDelayInterest;
    }

    public void setRepayDelayInterest(BigDecimal repayDelayInterest) {
        this.repayDelayInterest = repayDelayInterest;
    }

    public BigDecimal getRepayLateInterest() {
        return repayLateInterest;
    }

    public void setRepayLateInterest(BigDecimal repayLateInterest) {
        this.repayLateInterest = repayLateInterest;
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

    public BigDecimal getCreditManageFee() {
        return creditManageFee;
    }

    public void setCreditManageFee(BigDecimal creditManageFee) {
        this.creditManageFee = creditManageFee;
    }

    public Integer getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(Integer debtStatus) {
        this.debtStatus = debtStatus;
    }

    public BigDecimal getCreditInterest() {
        return creditInterest;
    }

    public void setCreditInterest(BigDecimal creditInterest) {
        this.creditInterest = creditInterest;
    }

    public BigDecimal getChargePenaltyInterest() {
        return chargePenaltyInterest;
    }

    public void setChargePenaltyInterest(BigDecimal chargePenaltyInterest) {
        this.chargePenaltyInterest = chargePenaltyInterest;
    }

    public BigDecimal getRepayChargePenaltyInterest() {
        return repayChargePenaltyInterest;
    }

    public void setRepayChargePenaltyInterest(BigDecimal repayChargePenaltyInterest) {
        this.repayChargePenaltyInterest = repayChargePenaltyInterest;
    }
}