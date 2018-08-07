package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhDebtCredit implements Serializable {
    private Integer id;

    private Integer userId;

    private String userName;

    private String planNid;

    private String planNidNew;

    private String planOrderId;

    private String borrowNid;

    private String borrowName;

    private BigDecimal borrowApr;

    private String borrowStyle;

    private Integer borrowPeriod;

    private String instCode;

    private Integer assetType;

    private Integer projectType;

    private BigDecimal actualApr;

    private String investOrderId;

    private String sellOrderId;

    private String creditNid;

    private Integer creditStatus;

    private Integer repayStatus;

    private Integer isLiquidates;

    private Integer holdDays;

    private Integer remainDays;

    private Integer duringDays;

    private Integer assignPeriod;

    private Integer liquidatesPeriod;

    private Integer creditPeriod;

    private Integer repayPeriod;

    private Integer creditTerm;

    private BigDecimal liquidationFairValue;

    private BigDecimal liquidatesCapital;

    private BigDecimal creditAccount;

    private BigDecimal creditCapital;

    private BigDecimal creditInterest;

    private BigDecimal creditInterestAdvance;

    private BigDecimal creditDelayInterest;

    private BigDecimal creditLateInterest;

    private BigDecimal creditAccountAssigned;

    private BigDecimal creditCapitalAssigned;

    private BigDecimal creditInterestAssigned;

    private BigDecimal creditInterestAdvanceAssigned;

    private BigDecimal creditDelayInterestAssigned;

    private BigDecimal creditLateInterestAssigned;

    private BigDecimal creditAccountWait;

    private BigDecimal creditCapitalWait;

    private BigDecimal creditInterestWait;

    private BigDecimal creditInterestAdvanceWait;

    private BigDecimal creditIncome;

    private BigDecimal creditServiceFee;

    private BigDecimal creditPrice;

    private BigDecimal repayAccount;

    private BigDecimal repayCapital;

    private BigDecimal repayInterest;

    private BigDecimal repayAccountWait;

    private BigDecimal repayCapitalWait;

    private BigDecimal repayInterestWait;

    private Integer creditRepayEndTime;

    private Integer creditRepayLastTime;

    private Integer creditRepayNextTime;

    private Integer creditRepayYesTime;

    private Integer endTime;

    private Integer assignNum;

    private Integer client;

    private Integer sourceType;

    private Integer creditTimes;

    private Integer isLateCredit;

    private Integer labelId;

    private String labelName;

    private Integer delFlag;

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

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getPlanNidNew() {
        return planNidNew;
    }

    public void setPlanNidNew(String planNidNew) {
        this.planNidNew = planNidNew == null ? null : planNidNew.trim();
    }

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId == null ? null : planOrderId.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName == null ? null : borrowName.trim();
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle == null ? null : borrowStyle.trim();
    }

    public Integer getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(Integer borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode == null ? null : instCode.trim();
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public BigDecimal getActualApr() {
        return actualApr;
    }

    public void setActualApr(BigDecimal actualApr) {
        this.actualApr = actualApr;
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

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid == null ? null : creditNid.trim();
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getIsLiquidates() {
        return isLiquidates;
    }

    public void setIsLiquidates(Integer isLiquidates) {
        this.isLiquidates = isLiquidates;
    }

    public Integer getHoldDays() {
        return holdDays;
    }

    public void setHoldDays(Integer holdDays) {
        this.holdDays = holdDays;
    }

    public Integer getRemainDays() {
        return remainDays;
    }

    public void setRemainDays(Integer remainDays) {
        this.remainDays = remainDays;
    }

    public Integer getDuringDays() {
        return duringDays;
    }

    public void setDuringDays(Integer duringDays) {
        this.duringDays = duringDays;
    }

    public Integer getAssignPeriod() {
        return assignPeriod;
    }

    public void setAssignPeriod(Integer assignPeriod) {
        this.assignPeriod = assignPeriod;
    }

    public Integer getLiquidatesPeriod() {
        return liquidatesPeriod;
    }

    public void setLiquidatesPeriod(Integer liquidatesPeriod) {
        this.liquidatesPeriod = liquidatesPeriod;
    }

    public Integer getCreditPeriod() {
        return creditPeriod;
    }

    public void setCreditPeriod(Integer creditPeriod) {
        this.creditPeriod = creditPeriod;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public Integer getCreditTerm() {
        return creditTerm;
    }

    public void setCreditTerm(Integer creditTerm) {
        this.creditTerm = creditTerm;
    }

    public BigDecimal getLiquidationFairValue() {
        return liquidationFairValue;
    }

    public void setLiquidationFairValue(BigDecimal liquidationFairValue) {
        this.liquidationFairValue = liquidationFairValue;
    }

    public BigDecimal getLiquidatesCapital() {
        return liquidatesCapital;
    }

    public void setLiquidatesCapital(BigDecimal liquidatesCapital) {
        this.liquidatesCapital = liquidatesCapital;
    }

    public BigDecimal getCreditAccount() {
        return creditAccount;
    }

    public void setCreditAccount(BigDecimal creditAccount) {
        this.creditAccount = creditAccount;
    }

    public BigDecimal getCreditCapital() {
        return creditCapital;
    }

    public void setCreditCapital(BigDecimal creditCapital) {
        this.creditCapital = creditCapital;
    }

    public BigDecimal getCreditInterest() {
        return creditInterest;
    }

    public void setCreditInterest(BigDecimal creditInterest) {
        this.creditInterest = creditInterest;
    }

    public BigDecimal getCreditInterestAdvance() {
        return creditInterestAdvance;
    }

    public void setCreditInterestAdvance(BigDecimal creditInterestAdvance) {
        this.creditInterestAdvance = creditInterestAdvance;
    }

    public BigDecimal getCreditDelayInterest() {
        return creditDelayInterest;
    }

    public void setCreditDelayInterest(BigDecimal creditDelayInterest) {
        this.creditDelayInterest = creditDelayInterest;
    }

    public BigDecimal getCreditLateInterest() {
        return creditLateInterest;
    }

    public void setCreditLateInterest(BigDecimal creditLateInterest) {
        this.creditLateInterest = creditLateInterest;
    }

    public BigDecimal getCreditAccountAssigned() {
        return creditAccountAssigned;
    }

    public void setCreditAccountAssigned(BigDecimal creditAccountAssigned) {
        this.creditAccountAssigned = creditAccountAssigned;
    }

    public BigDecimal getCreditCapitalAssigned() {
        return creditCapitalAssigned;
    }

    public void setCreditCapitalAssigned(BigDecimal creditCapitalAssigned) {
        this.creditCapitalAssigned = creditCapitalAssigned;
    }

    public BigDecimal getCreditInterestAssigned() {
        return creditInterestAssigned;
    }

    public void setCreditInterestAssigned(BigDecimal creditInterestAssigned) {
        this.creditInterestAssigned = creditInterestAssigned;
    }

    public BigDecimal getCreditInterestAdvanceAssigned() {
        return creditInterestAdvanceAssigned;
    }

    public void setCreditInterestAdvanceAssigned(BigDecimal creditInterestAdvanceAssigned) {
        this.creditInterestAdvanceAssigned = creditInterestAdvanceAssigned;
    }

    public BigDecimal getCreditDelayInterestAssigned() {
        return creditDelayInterestAssigned;
    }

    public void setCreditDelayInterestAssigned(BigDecimal creditDelayInterestAssigned) {
        this.creditDelayInterestAssigned = creditDelayInterestAssigned;
    }

    public BigDecimal getCreditLateInterestAssigned() {
        return creditLateInterestAssigned;
    }

    public void setCreditLateInterestAssigned(BigDecimal creditLateInterestAssigned) {
        this.creditLateInterestAssigned = creditLateInterestAssigned;
    }

    public BigDecimal getCreditAccountWait() {
        return creditAccountWait;
    }

    public void setCreditAccountWait(BigDecimal creditAccountWait) {
        this.creditAccountWait = creditAccountWait;
    }

    public BigDecimal getCreditCapitalWait() {
        return creditCapitalWait;
    }

    public void setCreditCapitalWait(BigDecimal creditCapitalWait) {
        this.creditCapitalWait = creditCapitalWait;
    }

    public BigDecimal getCreditInterestWait() {
        return creditInterestWait;
    }

    public void setCreditInterestWait(BigDecimal creditInterestWait) {
        this.creditInterestWait = creditInterestWait;
    }

    public BigDecimal getCreditInterestAdvanceWait() {
        return creditInterestAdvanceWait;
    }

    public void setCreditInterestAdvanceWait(BigDecimal creditInterestAdvanceWait) {
        this.creditInterestAdvanceWait = creditInterestAdvanceWait;
    }

    public BigDecimal getCreditIncome() {
        return creditIncome;
    }

    public void setCreditIncome(BigDecimal creditIncome) {
        this.creditIncome = creditIncome;
    }

    public BigDecimal getCreditServiceFee() {
        return creditServiceFee;
    }

    public void setCreditServiceFee(BigDecimal creditServiceFee) {
        this.creditServiceFee = creditServiceFee;
    }

    public BigDecimal getCreditPrice() {
        return creditPrice;
    }

    public void setCreditPrice(BigDecimal creditPrice) {
        this.creditPrice = creditPrice;
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

    public Integer getCreditRepayEndTime() {
        return creditRepayEndTime;
    }

    public void setCreditRepayEndTime(Integer creditRepayEndTime) {
        this.creditRepayEndTime = creditRepayEndTime;
    }

    public Integer getCreditRepayLastTime() {
        return creditRepayLastTime;
    }

    public void setCreditRepayLastTime(Integer creditRepayLastTime) {
        this.creditRepayLastTime = creditRepayLastTime;
    }

    public Integer getCreditRepayNextTime() {
        return creditRepayNextTime;
    }

    public void setCreditRepayNextTime(Integer creditRepayNextTime) {
        this.creditRepayNextTime = creditRepayNextTime;
    }

    public Integer getCreditRepayYesTime() {
        return creditRepayYesTime;
    }

    public void setCreditRepayYesTime(Integer creditRepayYesTime) {
        this.creditRepayYesTime = creditRepayYesTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(Integer assignNum) {
        this.assignNum = assignNum;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    public Integer getCreditTimes() {
        return creditTimes;
    }

    public void setCreditTimes(Integer creditTimes) {
        this.creditTimes = creditTimes;
    }

    public Integer getIsLateCredit() {
        return isLateCredit;
    }

    public void setIsLateCredit(Integer isLateCredit) {
        this.isLateCredit = isLateCredit;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName == null ? null : labelName.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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