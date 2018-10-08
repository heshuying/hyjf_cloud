package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

public class MspApplyVO extends BaseVO implements Serializable {
    private Integer id;

    private String applyId;

    private String name;

    private String identityCard;

    private String mobileNo;

    private String applyDate;

    private String loanType;

    private BigDecimal loanMoney;

    private Integer loanTimeLimit;

    private String creditAddress;

    private Integer shareIdentification;

    private String serviceType;

    private BigDecimal unredeemedMoney;

    private String repaymentStatus;

    private BigDecimal overdueAmount;

    private String overdueDate;

    private String overdueLength;

    private String overdueReason;

    private String approvalResult;

    private String approvalDate;

    private String contractBegin;

    private String contractEnd;

    private String guaranteeType;

    private String createUser;

    private Integer createTime;

    private String updateUser;

    private Integer updateTime;

    private Integer delFlg;

    private Integer configureId;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplyId() {
        return applyId;
    }

    public void setApplyId(String applyId) {
        this.applyId = applyId == null ? null : applyId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard == null ? null : identityCard.trim();
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo == null ? null : mobileNo.trim();
    }

    public String getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(String applyDate) {
        this.applyDate = applyDate == null ? null : applyDate.trim();
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
    }

    public BigDecimal getLoanMoney() {
        return loanMoney;
    }

    public void setLoanMoney(BigDecimal loanMoney) {
        this.loanMoney = loanMoney;
    }

    public Integer getLoanTimeLimit() {
        return loanTimeLimit;
    }

    public void setLoanTimeLimit(Integer loanTimeLimit) {
        this.loanTimeLimit = loanTimeLimit;
    }

    public String getCreditAddress() {
        return creditAddress;
    }

    public void setCreditAddress(String creditAddress) {
        this.creditAddress = creditAddress == null ? null : creditAddress.trim();
    }

    public Integer getShareIdentification() {
        return shareIdentification;
    }

    public void setShareIdentification(Integer shareIdentification) {
        this.shareIdentification = shareIdentification;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public BigDecimal getUnredeemedMoney() {
        return unredeemedMoney;
    }

    public void setUnredeemedMoney(BigDecimal unredeemedMoney) {
        this.unredeemedMoney = unredeemedMoney;
    }

    public String getRepaymentStatus() {
        return repaymentStatus;
    }

    public void setRepaymentStatus(String repaymentStatus) {
        this.repaymentStatus = repaymentStatus == null ? null : repaymentStatus.trim();
    }

    public BigDecimal getOverdueAmount() {
        return overdueAmount;
    }

    public void setOverdueAmount(BigDecimal overdueAmount) {
        this.overdueAmount = overdueAmount;
    }

    public String getOverdueDate() {
        return overdueDate;
    }

    public void setOverdueDate(String overdueDate) {
        this.overdueDate = overdueDate == null ? null : overdueDate.trim();
    }

    public String getOverdueLength() {
        return overdueLength;
    }

    public void setOverdueLength(String overdueLength) {
        this.overdueLength = overdueLength == null ? null : overdueLength.trim();
    }

    public String getOverdueReason() {
        return overdueReason;
    }

    public void setOverdueReason(String overdueReason) {
        this.overdueReason = overdueReason == null ? null : overdueReason.trim();
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult == null ? null : approvalResult.trim();
    }

    public String getApprovalDate() {
        return approvalDate;
    }

    public void setApprovalDate(String approvalDate) {
        this.approvalDate = approvalDate == null ? null : approvalDate.trim();
    }

    public String getContractBegin() {
        return contractBegin;
    }

    public void setContractBegin(String contractBegin) {
        this.contractBegin = contractBegin == null ? null : contractBegin.trim();
    }

    public String getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(String contractEnd) {
        this.contractEnd = contractEnd == null ? null : contractEnd.trim();
    }

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType == null ? null : guaranteeType.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getConfigureId() {
        return configureId;
    }

    public void setConfigureId(Integer configureId) {
        this.configureId = configureId;
    }
}