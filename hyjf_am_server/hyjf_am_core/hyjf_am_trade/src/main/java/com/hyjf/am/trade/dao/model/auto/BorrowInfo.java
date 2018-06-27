package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowInfo implements Serializable {
    private Integer id;

    private String borrowNid;

    private String borrowPreNid;

    private String borrowPreNidNew;

    private String name;

    private Integer userId;

    private String borrowUserName;

    private String applicant;

    private String repayOrgName;

    private Integer isRepayOrgFlag;

    private Integer repayOrgUserId;

    private String type;

    private String borrowUse;

    private Integer borrowValidTime;

    private String instCode;

    private Integer assetType;

    private String assetTypeName;

    private Integer entrustedFlg;

    private String entrustedUserName;

    private Integer entrustedUserId;

    private Integer trusteePayTime;

    private Integer tenderAccountMin;

    private Integer tenderAccountMax;

    private String upfilesId;

    private Integer projectType;

    private String canTransactionPc;

    private String canTransactionWei;

    private String canTransactionIos;

    private String canTransactionAndroid;

    private String operationLabel;

    private Integer companyOrPersonal;

    private String borrowManagerScaleEnd;

    private String consumeId;

    private String disposalPriceEstimate;

    private String disposalPeriod;

    private String disposalChannel;

    private String disposalResult;

    private String disposalNote;

    private String disposalProjectName;

    private String disposalProjectType;

    private String disposalArea;

    private String disposalPredictiveValue;

    private String disposalOwnershipCategory;

    private String disposalAssetOrigin;

    private String disposalAttachmentInfo;

    private Long borrowIncreaseMoney;

    private Integer borrowInterestCoupon;

    private Integer borrowTasteMoney;

    private String borrowAssetNumber;

    private String borrowProjectSource;

    private String borrowInterestTime;

    private String borrowDueTime;

    private String borrowSafeguardWay;

    private String borrowIncomeDescription;

    private String borrowPublisher;

    private BigDecimal borrowExtraYield;

    private Integer contractPeriod;

    private String borrowLevel;

    private Integer assetAttributes;

    private String bankRaiseStartDate;

    private String bankRaiseEndDate;

    private Integer bankRegistDays;

    private Integer bankBorrowDays;

    private String projectName;

    private String financePurpose;

    private String monthlyIncome;

    private String payment;

    private String firstPayment;

    private String secondPayment;

    private String costIntrodution;

    private String fianceCondition;

    private Integer isNew;

    private String publishInstCode;

    private String remark;

    private String createUserName;

    private String addip;

    private Date createTime;

    private Date updateTime;

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

    public String getBorrowPreNid() {
        return borrowPreNid;
    }

    public void setBorrowPreNid(String borrowPreNid) {
        this.borrowPreNid = borrowPreNid == null ? null : borrowPreNid.trim();
    }

    public String getBorrowPreNidNew() {
        return borrowPreNidNew;
    }

    public void setBorrowPreNidNew(String borrowPreNidNew) {
        this.borrowPreNidNew = borrowPreNidNew == null ? null : borrowPreNidNew.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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

    public String getApplicant() {
        return applicant;
    }

    public void setApplicant(String applicant) {
        this.applicant = applicant == null ? null : applicant.trim();
    }

    public String getRepayOrgName() {
        return repayOrgName;
    }

    public void setRepayOrgName(String repayOrgName) {
        this.repayOrgName = repayOrgName == null ? null : repayOrgName.trim();
    }

    public Integer getIsRepayOrgFlag() {
        return isRepayOrgFlag;
    }

    public void setIsRepayOrgFlag(Integer isRepayOrgFlag) {
        this.isRepayOrgFlag = isRepayOrgFlag;
    }

    public Integer getRepayOrgUserId() {
        return repayOrgUserId;
    }

    public void setRepayOrgUserId(Integer repayOrgUserId) {
        this.repayOrgUserId = repayOrgUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse == null ? null : borrowUse.trim();
    }

    public Integer getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(Integer borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
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

    public String getAssetTypeName() {
        return assetTypeName;
    }

    public void setAssetTypeName(String assetTypeName) {
        this.assetTypeName = assetTypeName == null ? null : assetTypeName.trim();
    }

    public Integer getEntrustedFlg() {
        return entrustedFlg;
    }

    public void setEntrustedFlg(Integer entrustedFlg) {
        this.entrustedFlg = entrustedFlg;
    }

    public String getEntrustedUserName() {
        return entrustedUserName;
    }

    public void setEntrustedUserName(String entrustedUserName) {
        this.entrustedUserName = entrustedUserName == null ? null : entrustedUserName.trim();
    }

    public Integer getEntrustedUserId() {
        return entrustedUserId;
    }

    public void setEntrustedUserId(Integer entrustedUserId) {
        this.entrustedUserId = entrustedUserId;
    }

    public Integer getTrusteePayTime() {
        return trusteePayTime;
    }

    public void setTrusteePayTime(Integer trusteePayTime) {
        this.trusteePayTime = trusteePayTime;
    }

    public Integer getTenderAccountMin() {
        return tenderAccountMin;
    }

    public void setTenderAccountMin(Integer tenderAccountMin) {
        this.tenderAccountMin = tenderAccountMin;
    }

    public Integer getTenderAccountMax() {
        return tenderAccountMax;
    }

    public void setTenderAccountMax(Integer tenderAccountMax) {
        this.tenderAccountMax = tenderAccountMax;
    }

    public String getUpfilesId() {
        return upfilesId;
    }

    public void setUpfilesId(String upfilesId) {
        this.upfilesId = upfilesId == null ? null : upfilesId.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public String getCanTransactionPc() {
        return canTransactionPc;
    }

    public void setCanTransactionPc(String canTransactionPc) {
        this.canTransactionPc = canTransactionPc == null ? null : canTransactionPc.trim();
    }

    public String getCanTransactionWei() {
        return canTransactionWei;
    }

    public void setCanTransactionWei(String canTransactionWei) {
        this.canTransactionWei = canTransactionWei == null ? null : canTransactionWei.trim();
    }

    public String getCanTransactionIos() {
        return canTransactionIos;
    }

    public void setCanTransactionIos(String canTransactionIos) {
        this.canTransactionIos = canTransactionIos == null ? null : canTransactionIos.trim();
    }

    public String getCanTransactionAndroid() {
        return canTransactionAndroid;
    }

    public void setCanTransactionAndroid(String canTransactionAndroid) {
        this.canTransactionAndroid = canTransactionAndroid == null ? null : canTransactionAndroid.trim();
    }

    public String getOperationLabel() {
        return operationLabel;
    }

    public void setOperationLabel(String operationLabel) {
        this.operationLabel = operationLabel == null ? null : operationLabel.trim();
    }

    public Integer getCompanyOrPersonal() {
        return companyOrPersonal;
    }

    public void setCompanyOrPersonal(Integer companyOrPersonal) {
        this.companyOrPersonal = companyOrPersonal;
    }

    public String getBorrowManagerScaleEnd() {
        return borrowManagerScaleEnd;
    }

    public void setBorrowManagerScaleEnd(String borrowManagerScaleEnd) {
        this.borrowManagerScaleEnd = borrowManagerScaleEnd == null ? null : borrowManagerScaleEnd.trim();
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }

    public String getDisposalPriceEstimate() {
        return disposalPriceEstimate;
    }

    public void setDisposalPriceEstimate(String disposalPriceEstimate) {
        this.disposalPriceEstimate = disposalPriceEstimate == null ? null : disposalPriceEstimate.trim();
    }

    public String getDisposalPeriod() {
        return disposalPeriod;
    }

    public void setDisposalPeriod(String disposalPeriod) {
        this.disposalPeriod = disposalPeriod == null ? null : disposalPeriod.trim();
    }

    public String getDisposalChannel() {
        return disposalChannel;
    }

    public void setDisposalChannel(String disposalChannel) {
        this.disposalChannel = disposalChannel == null ? null : disposalChannel.trim();
    }

    public String getDisposalResult() {
        return disposalResult;
    }

    public void setDisposalResult(String disposalResult) {
        this.disposalResult = disposalResult == null ? null : disposalResult.trim();
    }

    public String getDisposalNote() {
        return disposalNote;
    }

    public void setDisposalNote(String disposalNote) {
        this.disposalNote = disposalNote == null ? null : disposalNote.trim();
    }

    public String getDisposalProjectName() {
        return disposalProjectName;
    }

    public void setDisposalProjectName(String disposalProjectName) {
        this.disposalProjectName = disposalProjectName == null ? null : disposalProjectName.trim();
    }

    public String getDisposalProjectType() {
        return disposalProjectType;
    }

    public void setDisposalProjectType(String disposalProjectType) {
        this.disposalProjectType = disposalProjectType == null ? null : disposalProjectType.trim();
    }

    public String getDisposalArea() {
        return disposalArea;
    }

    public void setDisposalArea(String disposalArea) {
        this.disposalArea = disposalArea == null ? null : disposalArea.trim();
    }

    public String getDisposalPredictiveValue() {
        return disposalPredictiveValue;
    }

    public void setDisposalPredictiveValue(String disposalPredictiveValue) {
        this.disposalPredictiveValue = disposalPredictiveValue == null ? null : disposalPredictiveValue.trim();
    }

    public String getDisposalOwnershipCategory() {
        return disposalOwnershipCategory;
    }

    public void setDisposalOwnershipCategory(String disposalOwnershipCategory) {
        this.disposalOwnershipCategory = disposalOwnershipCategory == null ? null : disposalOwnershipCategory.trim();
    }

    public String getDisposalAssetOrigin() {
        return disposalAssetOrigin;
    }

    public void setDisposalAssetOrigin(String disposalAssetOrigin) {
        this.disposalAssetOrigin = disposalAssetOrigin == null ? null : disposalAssetOrigin.trim();
    }

    public String getDisposalAttachmentInfo() {
        return disposalAttachmentInfo;
    }

    public void setDisposalAttachmentInfo(String disposalAttachmentInfo) {
        this.disposalAttachmentInfo = disposalAttachmentInfo == null ? null : disposalAttachmentInfo.trim();
    }

    public Long getBorrowIncreaseMoney() {
        return borrowIncreaseMoney;
    }

    public void setBorrowIncreaseMoney(Long borrowIncreaseMoney) {
        this.borrowIncreaseMoney = borrowIncreaseMoney;
    }

    public Integer getBorrowInterestCoupon() {
        return borrowInterestCoupon;
    }

    public void setBorrowInterestCoupon(Integer borrowInterestCoupon) {
        this.borrowInterestCoupon = borrowInterestCoupon;
    }

    public Integer getBorrowTasteMoney() {
        return borrowTasteMoney;
    }

    public void setBorrowTasteMoney(Integer borrowTasteMoney) {
        this.borrowTasteMoney = borrowTasteMoney;
    }

    public String getBorrowAssetNumber() {
        return borrowAssetNumber;
    }

    public void setBorrowAssetNumber(String borrowAssetNumber) {
        this.borrowAssetNumber = borrowAssetNumber == null ? null : borrowAssetNumber.trim();
    }

    public String getBorrowProjectSource() {
        return borrowProjectSource;
    }

    public void setBorrowProjectSource(String borrowProjectSource) {
        this.borrowProjectSource = borrowProjectSource == null ? null : borrowProjectSource.trim();
    }

    public String getBorrowInterestTime() {
        return borrowInterestTime;
    }

    public void setBorrowInterestTime(String borrowInterestTime) {
        this.borrowInterestTime = borrowInterestTime == null ? null : borrowInterestTime.trim();
    }

    public String getBorrowDueTime() {
        return borrowDueTime;
    }

    public void setBorrowDueTime(String borrowDueTime) {
        this.borrowDueTime = borrowDueTime == null ? null : borrowDueTime.trim();
    }

    public String getBorrowSafeguardWay() {
        return borrowSafeguardWay;
    }

    public void setBorrowSafeguardWay(String borrowSafeguardWay) {
        this.borrowSafeguardWay = borrowSafeguardWay == null ? null : borrowSafeguardWay.trim();
    }

    public String getBorrowIncomeDescription() {
        return borrowIncomeDescription;
    }

    public void setBorrowIncomeDescription(String borrowIncomeDescription) {
        this.borrowIncomeDescription = borrowIncomeDescription == null ? null : borrowIncomeDescription.trim();
    }

    public String getBorrowPublisher() {
        return borrowPublisher;
    }

    public void setBorrowPublisher(String borrowPublisher) {
        this.borrowPublisher = borrowPublisher == null ? null : borrowPublisher.trim();
    }

    public BigDecimal getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(BigDecimal borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }

    public Integer getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(Integer contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public String getBorrowLevel() {
        return borrowLevel;
    }

    public void setBorrowLevel(String borrowLevel) {
        this.borrowLevel = borrowLevel == null ? null : borrowLevel.trim();
    }

    public Integer getAssetAttributes() {
        return assetAttributes;
    }

    public void setAssetAttributes(Integer assetAttributes) {
        this.assetAttributes = assetAttributes;
    }

    public String getBankRaiseStartDate() {
        return bankRaiseStartDate;
    }

    public void setBankRaiseStartDate(String bankRaiseStartDate) {
        this.bankRaiseStartDate = bankRaiseStartDate == null ? null : bankRaiseStartDate.trim();
    }

    public String getBankRaiseEndDate() {
        return bankRaiseEndDate;
    }

    public void setBankRaiseEndDate(String bankRaiseEndDate) {
        this.bankRaiseEndDate = bankRaiseEndDate == null ? null : bankRaiseEndDate.trim();
    }

    public Integer getBankRegistDays() {
        return bankRegistDays;
    }

    public void setBankRegistDays(Integer bankRegistDays) {
        this.bankRegistDays = bankRegistDays;
    }

    public Integer getBankBorrowDays() {
        return bankBorrowDays;
    }

    public void setBankBorrowDays(Integer bankBorrowDays) {
        this.bankBorrowDays = bankBorrowDays;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getFinancePurpose() {
        return financePurpose;
    }

    public void setFinancePurpose(String financePurpose) {
        this.financePurpose = financePurpose == null ? null : financePurpose.trim();
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome == null ? null : monthlyIncome.trim();
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment == null ? null : payment.trim();
    }

    public String getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(String firstPayment) {
        this.firstPayment = firstPayment == null ? null : firstPayment.trim();
    }

    public String getSecondPayment() {
        return secondPayment;
    }

    public void setSecondPayment(String secondPayment) {
        this.secondPayment = secondPayment == null ? null : secondPayment.trim();
    }

    public String getCostIntrodution() {
        return costIntrodution;
    }

    public void setCostIntrodution(String costIntrodution) {
        this.costIntrodution = costIntrodution == null ? null : costIntrodution.trim();
    }

    public String getFianceCondition() {
        return fianceCondition;
    }

    public void setFianceCondition(String fianceCondition) {
        this.fianceCondition = fianceCondition == null ? null : fianceCondition.trim();
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getPublishInstCode() {
        return publishInstCode;
    }

    public void setPublishInstCode(String publishInstCode) {
        this.publishInstCode = publishInstCode == null ? null : publishInstCode.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
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