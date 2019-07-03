package com.hyjf.am.vo.trade.repay;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hesy
 * @version DebtCreditRepayVO, v0.1 2018/6/27 15:46
 */
public class DebtCreditRepayVO implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private Integer creditUserId;

    private String creditUserName;

    private String investOrderId;

    private String sellOrderId;

    private Integer repayStatus;

    private String borrowNid;

    private Integer repayPeriod;

    private String creditNid;

    private String assignPlanNid;

    private String assignPlanOrderId;

    private String assignOrderId;

    private BigDecimal repayAccount;

    private BigDecimal repayCapital;

    private BigDecimal repayInterest;

    private BigDecimal repayAccountWait;

    private BigDecimal repayCapitalWait;

    private BigDecimal repayInterestWait;

    private BigDecimal repayAccountYes;

    private BigDecimal repayCapitalYes;

    private BigDecimal repayInterestYes;

    private Integer assignRepayEndTime;

    private Integer assignRepayLastTime;

    private Integer assignRepayNextTime;

    private Integer assignRepayYesTime;

    private Integer assignRepayTime;

    private Integer delFlag;

    private Integer assignRepayPeriod;

    private Integer assignCreateDate;

    private String addIp;

    private Integer client;

    private BigDecimal manageFee;

    private BigDecimal liquidatesServiceFee;

    private String uniqueNid;

    private String creditRepayOrderId;

    private String creditRepayOrderDate;

    private Integer advanceStatus;

    private Integer advanceDays;

    private BigDecimal repayAdvanceInterest;

    private Integer lateDays;

    private BigDecimal repayLateInterest;

    private Integer delayDays;

    private BigDecimal repayDelayInterest;

    private BigDecimal receiveAccountYes;

    private BigDecimal receiveCapitalYes;

    private BigDecimal receiveInterestYes;

    private BigDecimal receiveAdvanceInterest;

    private BigDecimal receiveLateInterest;

    private BigDecimal receiveDelayInterest;

    private BigDecimal receiveAdvanceInterestYes;

    private BigDecimal receiveLateInterestYes;

    private BigDecimal receiveDelayInterestYes;

    private Integer createUserId;

    private String createUserName;

    private Integer updateUserId;

    private String updateUserName;

    private Date createTime;

    private Date updateTime;

    // 提前还款罚息
    public BigDecimal repayAdvancePenaltyInterest;

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

    public String getInvestOrderId() {
        return investOrderId;
    }

    public void setInvestOrderId(String investOrderId) {
        this.investOrderId = investOrderId == null ? null : investOrderId.trim();
    }

    public String getSellOrderId() {
        return sellOrderId;
    }

    public void setSellOrderId(String sellOrderId) {
        this.sellOrderId = sellOrderId == null ? null : sellOrderId.trim();
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid == null ? null : creditNid.trim();
    }

    public String getAssignPlanNid() {
        return assignPlanNid;
    }

    public void setAssignPlanNid(String assignPlanNid) {
        this.assignPlanNid = assignPlanNid == null ? null : assignPlanNid.trim();
    }

    public String getAssignPlanOrderId() {
        return assignPlanOrderId;
    }

    public void setAssignPlanOrderId(String assignPlanOrderId) {
        this.assignPlanOrderId = assignPlanOrderId == null ? null : assignPlanOrderId.trim();
    }

    public String getAssignOrderId() {
        return assignOrderId;
    }

    public void setAssignOrderId(String assignOrderId) {
        this.assignOrderId = assignOrderId == null ? null : assignOrderId.trim();
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

    public Integer getAssignRepayTime() {
        return assignRepayTime;
    }

    public void setAssignRepayTime(Integer assignRepayTime) {
        this.assignRepayTime = assignRepayTime;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public BigDecimal getManageFee() {
        return manageFee;
    }

    public void setManageFee(BigDecimal manageFee) {
        this.manageFee = manageFee;
    }

    public BigDecimal getLiquidatesServiceFee() {
        return liquidatesServiceFee;
    }

    public void setLiquidatesServiceFee(BigDecimal liquidatesServiceFee) {
        this.liquidatesServiceFee = liquidatesServiceFee;
    }

    public String getUniqueNid() {
        return uniqueNid;
    }

    public void setUniqueNid(String uniqueNid) {
        this.uniqueNid = uniqueNid == null ? null : uniqueNid.trim();
    }

    public String getCreditRepayOrderId() {
        return creditRepayOrderId;
    }

    public void setCreditRepayOrderId(String creditRepayOrderId) {
        this.creditRepayOrderId = creditRepayOrderId == null ? null : creditRepayOrderId.trim();
    }

    public String getCreditRepayOrderDate() {
        return creditRepayOrderDate;
    }

    public void setCreditRepayOrderDate(String creditRepayOrderDate) {
        this.creditRepayOrderDate = creditRepayOrderDate == null ? null : creditRepayOrderDate.trim();
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

    public BigDecimal getRepayAdvancePenaltyInterest() {
        return repayAdvancePenaltyInterest;
    }

    public void setRepayAdvancePenaltyInterest(BigDecimal repayAdvancePenaltyInterest) {
        this.repayAdvancePenaltyInterest = repayAdvancePenaltyInterest;
    }
}