package com.hyjf.am.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class MspConfigureVO implements Serializable {
    private Integer id;

    private String configureName;

    private String loanType;

    private String serviceType;

    private String approvalResult;

    private BigDecimal loanMoney;

    private Integer loanTimeLimit;

    private String creditAddress;

    private String guaranteeType;

    private BigDecimal unredeemedMoney;

    private String repaymentStatus;

    private BigDecimal overdueAmount;

    private String overdueDate;

    private Integer overdueLength;

    private String overdueReason;

    private Integer delFlag;

    private Integer createUser;

    private Integer updateUser;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConfigureName() {
        return configureName;
    }

    public void setConfigureName(String configureName) {
        this.configureName = configureName == null ? null : configureName.trim();
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType == null ? null : loanType.trim();
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType == null ? null : serviceType.trim();
    }

    public String getApprovalResult() {
        return approvalResult;
    }

    public void setApprovalResult(String approvalResult) {
        this.approvalResult = approvalResult == null ? null : approvalResult.trim();
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

    public String getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(String guaranteeType) {
        this.guaranteeType = guaranteeType == null ? null : guaranteeType.trim();
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

    public Integer getOverdueLength() {
        return overdueLength;
    }

    public void setOverdueLength(Integer overdueLength) {
        this.overdueLength = overdueLength;
    }

    public String getOverdueReason() {
        return overdueReason;
    }

    public void setOverdueReason(String overdueReason) {
        this.overdueReason = overdueReason == null ? null : overdueReason.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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