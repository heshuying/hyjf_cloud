package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class Borrow implements Serializable {
    private Integer id;

    private String borrowNid;

    private Integer projectType;

    private Integer userId;

    private String borrowUserName;

    private Integer status;

    private BigDecimal account;

    private Integer borrowValidTime;

    private String borrowStyle;

    private Integer borrowPeriod;

    private BigDecimal borrowApr;

    private Integer labelId;

    private Integer isShow;

    private Integer isEngineUsed;

    private Integer isInstallment;

    private Integer isMonth;

    private Integer registStatus;

    private Date registTime;

    private Integer registUserId;

    private String registUserName;

    private Integer verifyStatus;

    private Integer verifyOverTime;

    private Integer verifyTime;

    private String verifyUserid;

    private String verifyUserName;

    private String verifyRemark;

    private String verifyContents;

    private Integer borrowStatus;

    private Integer ontime;

    private String borrowEndTime;

    private String planNid;

    private Integer borrowFullStatus;

    private Integer borrowFullTime;

    private Integer tenderTimes;

    private BigDecimal borrowAccountYes;

    private BigDecimal borrowAccountWait;

    private BigDecimal borrowAccountScale;

    private String borrowService;

    private Integer reverifyStatus;

    private Integer reverifyTime;

    private String reverifyUserid;

    private String reverifyUserName;

    private String reverifyRemark;

    private String reverifyContents;

    private Integer recoverLastTime;

    private Integer repayLastTime;

    private Integer repayNextTime;

    private Integer repayStatus;

    private Integer repayFullStatus;

    private BigDecimal repayFeeNormal;

    private BigDecimal repayAccountAll;

    private BigDecimal repayAccountInterest;

    private BigDecimal repayAccountCapital;

    private BigDecimal repayAccountYes;

    private BigDecimal repayAccountInterestYes;

    private BigDecimal repayAccountCapitalYes;

    private BigDecimal repayAccountWait;

    private BigDecimal repayAccountInterestWait;

    private BigDecimal repayAccountCapitalWait;

    private String borrowManager;

    private String serviceFeeRate;

    private String manageFeeRate;

    private String differentialRate;

    private String lateInterestRate;

    private Integer lateFreeDays;

    private String createUserName;

    private String addIp;

    private Date createTime;

    private Date updatetime;

    private Boolean increaseInterestFlag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(Integer borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
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

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public Integer getIsEngineUsed() {
        return isEngineUsed;
    }

    public void setIsEngineUsed(Integer isEngineUsed) {
        this.isEngineUsed = isEngineUsed;
    }

    public Integer getIsInstallment() {
        return isInstallment;
    }

    public void setIsInstallment(Integer isInstallment) {
        this.isInstallment = isInstallment;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public Integer getRegistStatus() {
        return registStatus;
    }

    public void setRegistStatus(Integer registStatus) {
        this.registStatus = registStatus;
    }

    public Date getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Date registTime) {
        this.registTime = registTime;
    }

    public Integer getRegistUserId() {
        return registUserId;
    }

    public void setRegistUserId(Integer registUserId) {
        this.registUserId = registUserId;
    }

    public String getRegistUserName() {
        return registUserName;
    }

    public void setRegistUserName(String registUserName) {
        this.registUserName = registUserName == null ? null : registUserName.trim();
    }

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    public Integer getVerifyOverTime() {
        return verifyOverTime;
    }

    public void setVerifyOverTime(Integer verifyOverTime) {
        this.verifyOverTime = verifyOverTime;
    }

    public Integer getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(Integer verifyTime) {
        this.verifyTime = verifyTime;
    }

    public String getVerifyUserid() {
        return verifyUserid;
    }

    public void setVerifyUserid(String verifyUserid) {
        this.verifyUserid = verifyUserid == null ? null : verifyUserid.trim();
    }

    public String getVerifyUserName() {
        return verifyUserName;
    }

    public void setVerifyUserName(String verifyUserName) {
        this.verifyUserName = verifyUserName == null ? null : verifyUserName.trim();
    }

    public String getVerifyRemark() {
        return verifyRemark;
    }

    public void setVerifyRemark(String verifyRemark) {
        this.verifyRemark = verifyRemark == null ? null : verifyRemark.trim();
    }

    public String getVerifyContents() {
        return verifyContents;
    }

    public void setVerifyContents(String verifyContents) {
        this.verifyContents = verifyContents == null ? null : verifyContents.trim();
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Integer getOntime() {
        return ontime;
    }

    public void setOntime(Integer ontime) {
        this.ontime = ontime;
    }

    public String getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(String borrowEndTime) {
        this.borrowEndTime = borrowEndTime == null ? null : borrowEndTime.trim();
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public Integer getBorrowFullStatus() {
        return borrowFullStatus;
    }

    public void setBorrowFullStatus(Integer borrowFullStatus) {
        this.borrowFullStatus = borrowFullStatus;
    }

    public Integer getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(Integer borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public BigDecimal getBorrowAccountYes() {
        return borrowAccountYes;
    }

    public void setBorrowAccountYes(BigDecimal borrowAccountYes) {
        this.borrowAccountYes = borrowAccountYes;
    }

    public BigDecimal getBorrowAccountWait() {
        return borrowAccountWait;
    }

    public void setBorrowAccountWait(BigDecimal borrowAccountWait) {
        this.borrowAccountWait = borrowAccountWait;
    }

    public BigDecimal getBorrowAccountScale() {
        return borrowAccountScale;
    }

    public void setBorrowAccountScale(BigDecimal borrowAccountScale) {
        this.borrowAccountScale = borrowAccountScale;
    }

    public String getBorrowService() {
        return borrowService;
    }

    public void setBorrowService(String borrowService) {
        this.borrowService = borrowService == null ? null : borrowService.trim();
    }

    public Integer getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(Integer reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public Integer getReverifyTime() {
        return reverifyTime;
    }

    public void setReverifyTime(Integer reverifyTime) {
        this.reverifyTime = reverifyTime;
    }

    public String getReverifyUserid() {
        return reverifyUserid;
    }

    public void setReverifyUserid(String reverifyUserid) {
        this.reverifyUserid = reverifyUserid == null ? null : reverifyUserid.trim();
    }

    public String getReverifyUserName() {
        return reverifyUserName;
    }

    public void setReverifyUserName(String reverifyUserName) {
        this.reverifyUserName = reverifyUserName == null ? null : reverifyUserName.trim();
    }

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark == null ? null : reverifyRemark.trim();
    }

    public String getReverifyContents() {
        return reverifyContents;
    }

    public void setReverifyContents(String reverifyContents) {
        this.reverifyContents = reverifyContents == null ? null : reverifyContents.trim();
    }

    public Integer getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(Integer recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public Integer getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(Integer repayLastTime) {
        this.repayLastTime = repayLastTime;
    }

    public Integer getRepayNextTime() {
        return repayNextTime;
    }

    public void setRepayNextTime(Integer repayNextTime) {
        this.repayNextTime = repayNextTime;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public Integer getRepayFullStatus() {
        return repayFullStatus;
    }

    public void setRepayFullStatus(Integer repayFullStatus) {
        this.repayFullStatus = repayFullStatus;
    }

    public BigDecimal getRepayFeeNormal() {
        return repayFeeNormal;
    }

    public void setRepayFeeNormal(BigDecimal repayFeeNormal) {
        this.repayFeeNormal = repayFeeNormal;
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public BigDecimal getRepayAccountInterest() {
        return repayAccountInterest;
    }

    public void setRepayAccountInterest(BigDecimal repayAccountInterest) {
        this.repayAccountInterest = repayAccountInterest;
    }

    public BigDecimal getRepayAccountCapital() {
        return repayAccountCapital;
    }

    public void setRepayAccountCapital(BigDecimal repayAccountCapital) {
        this.repayAccountCapital = repayAccountCapital;
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public BigDecimal getRepayAccountInterestYes() {
        return repayAccountInterestYes;
    }

    public void setRepayAccountInterestYes(BigDecimal repayAccountInterestYes) {
        this.repayAccountInterestYes = repayAccountInterestYes;
    }

    public BigDecimal getRepayAccountCapitalYes() {
        return repayAccountCapitalYes;
    }

    public void setRepayAccountCapitalYes(BigDecimal repayAccountCapitalYes) {
        this.repayAccountCapitalYes = repayAccountCapitalYes;
    }

    public BigDecimal getRepayAccountWait() {
        return repayAccountWait;
    }

    public void setRepayAccountWait(BigDecimal repayAccountWait) {
        this.repayAccountWait = repayAccountWait;
    }

    public BigDecimal getRepayAccountInterestWait() {
        return repayAccountInterestWait;
    }

    public void setRepayAccountInterestWait(BigDecimal repayAccountInterestWait) {
        this.repayAccountInterestWait = repayAccountInterestWait;
    }

    public BigDecimal getRepayAccountCapitalWait() {
        return repayAccountCapitalWait;
    }

    public void setRepayAccountCapitalWait(BigDecimal repayAccountCapitalWait) {
        this.repayAccountCapitalWait = repayAccountCapitalWait;
    }

    public String getBorrowManager() {
        return borrowManager;
    }

    public void setBorrowManager(String borrowManager) {
        this.borrowManager = borrowManager == null ? null : borrowManager.trim();
    }

    public String getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(String serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate == null ? null : serviceFeeRate.trim();
    }

    public String getManageFeeRate() {
        return manageFeeRate;
    }

    public void setManageFeeRate(String manageFeeRate) {
        this.manageFeeRate = manageFeeRate == null ? null : manageFeeRate.trim();
    }

    public String getDifferentialRate() {
        return differentialRate;
    }

    public void setDifferentialRate(String differentialRate) {
        this.differentialRate = differentialRate == null ? null : differentialRate.trim();
    }

    public String getLateInterestRate() {
        return lateInterestRate;
    }

    public void setLateInterestRate(String lateInterestRate) {
        this.lateInterestRate = lateInterestRate == null ? null : lateInterestRate.trim();
    }

    public Integer getLateFreeDays() {
        return lateFreeDays;
    }

    public void setLateFreeDays(Integer lateFreeDays) {
        this.lateFreeDays = lateFreeDays;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Boolean getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(Boolean increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }
}