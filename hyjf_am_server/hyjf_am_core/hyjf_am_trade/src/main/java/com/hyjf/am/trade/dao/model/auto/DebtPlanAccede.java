package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlanAccede implements Serializable {
    private Integer id;

    private String planNid;

    private Integer userId;

    private String userName;

    private Integer userAttribute;

    private String accedeOrderId;

    private String freezeOrderId;

    private BigDecimal accedeAccount;

    private BigDecimal accedeBalance;

    private BigDecimal accedeFrost;

    private BigDecimal liquidatesCreditFrost;

    private BigDecimal liquidatesApr;

    private BigDecimal liquidatesRepayFrost;

    private BigDecimal serviceFeeRate;

    private BigDecimal serviceFee;

    private BigDecimal expireFairValue;

    private Integer underTakeTimes;

    private BigDecimal investMax;

    private BigDecimal investMin;

    private Integer cycleTimes;

    private BigDecimal repayAccount;

    private BigDecimal repayCapital;

    private BigDecimal repayInterest;

    private BigDecimal repayAccountWait;

    private BigDecimal repayCapitalWait;

    private BigDecimal repayInterestWait;

    private BigDecimal repayAccountYes;

    private BigDecimal repayCapitalYes;

    private BigDecimal repayInterestYes;

    private Integer inviteUserId;

    private String inviteUserName;

    private Integer inviteUserAttribute;

    private Integer inviteRegionId;

    private String inviteRegionName;

    private Integer inviteBranchId;

    private String inviteBranchName;

    private Integer inviteDepartmentId;

    private String inviteDepartmentName;

    private Integer sendStatus;

    private Integer calculationStatus;

    private Integer status;

    private Integer reinvestStatus;

    private Integer repayRunningStatus;

    private Integer delFlag;

    private Integer client;

    private Date createTime;

    private Integer createUserId;

    private String createUserName;

    private Date updateTime;

    private Integer updateUserId;

    private String updateUserName;

    private Integer inviteRepayUserId;

    private Integer inviteRepayUserAttribute;

    private String inviteRepayUserName;

    private Integer inviteRepayRegionId;

    private String inviteRepayRegionName;

    private Integer inviteRepayBranchId;

    private String inviteRepayBranchName;

    private Integer inviteRepayDepartmentId;

    private String inviteRepayDepartmentName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
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

    public Integer getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(Integer userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public String getFreezeOrderId() {
        return freezeOrderId;
    }

    public void setFreezeOrderId(String freezeOrderId) {
        this.freezeOrderId = freezeOrderId == null ? null : freezeOrderId.trim();
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public BigDecimal getAccedeBalance() {
        return accedeBalance;
    }

    public void setAccedeBalance(BigDecimal accedeBalance) {
        this.accedeBalance = accedeBalance;
    }

    public BigDecimal getAccedeFrost() {
        return accedeFrost;
    }

    public void setAccedeFrost(BigDecimal accedeFrost) {
        this.accedeFrost = accedeFrost;
    }

    public BigDecimal getLiquidatesCreditFrost() {
        return liquidatesCreditFrost;
    }

    public void setLiquidatesCreditFrost(BigDecimal liquidatesCreditFrost) {
        this.liquidatesCreditFrost = liquidatesCreditFrost;
    }

    public BigDecimal getLiquidatesApr() {
        return liquidatesApr;
    }

    public void setLiquidatesApr(BigDecimal liquidatesApr) {
        this.liquidatesApr = liquidatesApr;
    }

    public BigDecimal getLiquidatesRepayFrost() {
        return liquidatesRepayFrost;
    }

    public void setLiquidatesRepayFrost(BigDecimal liquidatesRepayFrost) {
        this.liquidatesRepayFrost = liquidatesRepayFrost;
    }

    public BigDecimal getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(BigDecimal serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getExpireFairValue() {
        return expireFairValue;
    }

    public void setExpireFairValue(BigDecimal expireFairValue) {
        this.expireFairValue = expireFairValue;
    }

    public Integer getUnderTakeTimes() {
        return underTakeTimes;
    }

    public void setUnderTakeTimes(Integer underTakeTimes) {
        this.underTakeTimes = underTakeTimes;
    }

    public BigDecimal getInvestMax() {
        return investMax;
    }

    public void setInvestMax(BigDecimal investMax) {
        this.investMax = investMax;
    }

    public BigDecimal getInvestMin() {
        return investMin;
    }

    public void setInvestMin(BigDecimal investMin) {
        this.investMin = investMin;
    }

    public Integer getCycleTimes() {
        return cycleTimes;
    }

    public void setCycleTimes(Integer cycleTimes) {
        this.cycleTimes = cycleTimes;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(BigDecimal repayCapital) {
        this.repayCapital = repayCapital;
    }

    public BigDecimal getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(BigDecimal repayInterest) {
        this.repayInterest = repayInterest;
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

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayCapitalYes() {
        return repayCapitalYes;
    }

    public void setRepayCapitalYes(BigDecimal repayCapitalYes) {
        this.repayCapitalYes = repayCapitalYes;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
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

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getCalculationStatus() {
        return calculationStatus;
    }

    public void setCalculationStatus(Integer calculationStatus) {
        this.calculationStatus = calculationStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReinvestStatus() {
        return reinvestStatus;
    }

    public void setReinvestStatus(Integer reinvestStatus) {
        this.reinvestStatus = reinvestStatus;
    }

    public Integer getRepayRunningStatus() {
        return repayRunningStatus;
    }

    public void setRepayRunningStatus(Integer repayRunningStatus) {
        this.repayRunningStatus = repayRunningStatus;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getInviteRepayUserId() {
        return inviteRepayUserId;
    }

    public void setInviteRepayUserId(Integer inviteRepayUserId) {
        this.inviteRepayUserId = inviteRepayUserId;
    }

    public Integer getInviteRepayUserAttribute() {
        return inviteRepayUserAttribute;
    }

    public void setInviteRepayUserAttribute(Integer inviteRepayUserAttribute) {
        this.inviteRepayUserAttribute = inviteRepayUserAttribute;
    }

    public String getInviteRepayUserName() {
        return inviteRepayUserName;
    }

    public void setInviteRepayUserName(String inviteRepayUserName) {
        this.inviteRepayUserName = inviteRepayUserName == null ? null : inviteRepayUserName.trim();
    }

    public Integer getInviteRepayRegionId() {
        return inviteRepayRegionId;
    }

    public void setInviteRepayRegionId(Integer inviteRepayRegionId) {
        this.inviteRepayRegionId = inviteRepayRegionId;
    }

    public String getInviteRepayRegionName() {
        return inviteRepayRegionName;
    }

    public void setInviteRepayRegionName(String inviteRepayRegionName) {
        this.inviteRepayRegionName = inviteRepayRegionName == null ? null : inviteRepayRegionName.trim();
    }

    public Integer getInviteRepayBranchId() {
        return inviteRepayBranchId;
    }

    public void setInviteRepayBranchId(Integer inviteRepayBranchId) {
        this.inviteRepayBranchId = inviteRepayBranchId;
    }

    public String getInviteRepayBranchName() {
        return inviteRepayBranchName;
    }

    public void setInviteRepayBranchName(String inviteRepayBranchName) {
        this.inviteRepayBranchName = inviteRepayBranchName == null ? null : inviteRepayBranchName.trim();
    }

    public Integer getInviteRepayDepartmentId() {
        return inviteRepayDepartmentId;
    }

    public void setInviteRepayDepartmentId(Integer inviteRepayDepartmentId) {
        this.inviteRepayDepartmentId = inviteRepayDepartmentId;
    }

    public String getInviteRepayDepartmentName() {
        return inviteRepayDepartmentName;
    }

    public void setInviteRepayDepartmentName(String inviteRepayDepartmentName) {
        this.inviteRepayDepartmentName = inviteRepayDepartmentName == null ? null : inviteRepayDepartmentName.trim();
    }
}