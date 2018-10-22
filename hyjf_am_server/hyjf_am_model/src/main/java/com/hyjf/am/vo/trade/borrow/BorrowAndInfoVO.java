/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.borrow;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author fuqiang
 * @version BorrowVO, v0.1 2018/6/12 18:58
 */
public class BorrowAndInfoVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer userId;

    private String applicant;

    private String repayOrgName;

    private Integer isRepayOrgFlag;

    private Integer repayOrgUserId;

    private String name;

    private Integer status;

    private String borrowPic;

    private Integer hits;

    private Integer commentCount;

    private String litpic;

    private String flag;

    private String type;

    private String viewType;

    private String addtime;

    private String addip;

    private BigDecimal amountAccount;

    private String amountType;

    private Integer cashStatus;

    private BigDecimal account;

    private Integer otherWebStatus;

    private String borrowType;

    private String borrowPassword;

    private String borrowFlag;

    private Integer borrowStatus;

    private Integer borrowFullStatus;

    private String borrowNid;

    private String borrowPreNid;

    private String borrowPreNidNew;

    private BigDecimal borrowAccountYes;

    private BigDecimal borrowAccountWait;

    private BigDecimal borrowAccountScale;

    private String borrowUse;

    private String borrowStyle;

    private Integer borrowPeriod;

    private String borrowPeriodStr;

    private Integer borrowPeriodRoam;

    private Integer borrowDay;

    private BigDecimal borrowApr;

    private BigDecimal borrowFrostAccount;

    private String borrowFrostScale;

    private BigDecimal borrowFrostSecond;

    private Integer borrowValidTime;

    private Integer borrowSuccessTime;

    private String borrowEndTime;

    private Integer borrowPartStatus;

    private String planNid;

    private Integer labelId;

    private String instCode;

    private Integer assetType;

    private String assetTypeName;

    private Integer entrustedFlg;

    private String entrustedUserName;

    private Integer entrustedUserId;

    private Integer trusteePayTime;

    private Integer cancelUserid;

    private Integer cancelStatus;

    private String cancelTime;

    private String cancelRemark;

    private String cancelContents;

    private Integer tenderAccountMin;

    private Integer tenderAccountMax;

    private Integer tenderTimes;

    private String tenderLastTime;

    private Integer repayAdvanceStatus;

    private String repayAdvanceTime;

    private Integer repayAdvanceStep;

    private BigDecimal repayAccountAll;

    private BigDecimal repayAccountInterest;

    private BigDecimal repayAccountCapital;

    private BigDecimal repayAccountYes;

    private BigDecimal repayAccountInterestYes;

    private BigDecimal repayAccountCapitalYes;

    private BigDecimal repayAccountWait;

    private BigDecimal repayAccountInterestWait;

    private BigDecimal repayAccountCapitalWait;

    private Integer repayAccountTimes;

    private Integer repayMonthAccount;

    private String repayLastTime;

    private String repayEachTime;

    private Integer repayNextTime;

    private BigDecimal repayNextAccount;

    private Integer repayTimes;

    private Integer repayFullStatus;

    private BigDecimal repayFeeNormal;

    private BigDecimal repayFeeAdvance;

    private BigDecimal repayFeeLate;

    private BigDecimal lateInterest;

    private BigDecimal lateForfeit;

    private Integer vouchStatus;

    private Integer vouchAdvanceStatus;

    private Integer vouchUserStatus;

    private String vouchUsers;

    private BigDecimal vouchAccount;

    private BigDecimal vouchAccountYes;

    private BigDecimal vouchAccountWait;

    private Long vouchAccountScale;

    private Integer vouchTimes;

    private Integer vouchAwardStatus;

    private BigDecimal vouchAwardScale;

    private BigDecimal vouchAwardAccount;

    private String voucherName;

    private String voucherLianxi;

    private String voucherAtt;

    private String vouchjgName;

    private String vouchjgLianxi;

    private String vouchjgJs;

    private String vouchjgXy;

    private Integer fastStatus;

    private Integer vouchstatus;

    private Integer groupStatus;

    private Integer groupId;

    private Integer awardStatus;

    private Integer awardFalse;

    private BigDecimal awardAccount;

    private BigDecimal awardScale;

    private BigDecimal awardAccountAll;

    private Integer openAccount;

    private Integer openBorrow;

    private Integer openTender;

    private Integer openCredit;

    private Integer commentStaus;

    private Integer commentTimes;

    private String commentUsertype;

    private String borrowPawnApp;

    private String borrowPawnAppUrl;

    private String borrowPawnAuth;

    private String borrowPawnAuthUrl;

    private String borrowPawnFormalities;

    private String borrowPawnFormalitiesUrl;

    private String borrowPawnType;

    private String borrowPawnTime;

    private String borrowPawnValue;

    private String borrowPawnXin;

    private String orderTop;

    private String verifyUserid;

    private String verifyUserName;

    private String verifyTime;

    private String verifyRemark;

    private String verifyContents;

    private Integer verifyStatus;

    private String reverifyUserid;

    private String reverifyUserName;

    private String reverifyTime;

    private String reverifyRemark;

    private Integer reverifyStatus;

    private String reverifyContents;

    private String upfilesId;

    private String xmupfilesId;

    private String dyupfilesId;

    private Integer guaranteeType;

    private Integer projectType;

    private Integer ontime;

    private Date updatetime;

    private String serviceFeeRate;

    private String manageFeeRate;

    private String differentialRate;

    private String lateInterestRate;

    private Integer lateFreeDays;

    private String canTransactionPc;

    private String canTransactionWei;

    private String canTransactionIos;

    private String canTransactionAndroid;

    private String operationLabel;

    private Integer companyOrPersonal;

    private String borrowManager;

    private String borrowService;

    private String borrowManagerScaleEnd;

    private Integer borrowFullTime;

    private Integer recoverLastTime;

    private String consumeId;

    private Integer verifyOverTime;

    private String borrowUserName;

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

    private Integer bankInputFlag;

    private Integer bookingBeginTime;

    private Integer bookingEndTime;

    private BigDecimal borrowAccountWaitAppoint;

    private BigDecimal borrowAccountScaleAppoint;

    private Integer bookingStatus;

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

    private Integer isShow;

    private Integer registStatus;

    private Date registTime;

    private Integer registUserId;

    private String registUserName;

    private Integer repayStatus;

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

    private String remark;

    private String createUserName;

    private String publishInstCode;

    private Integer isEngineUsed;

    private Date createTime;

    private Integer increaseInterestFlag;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBorrowPic() {
        return borrowPic;
    }

    public void setBorrowPic(String borrowPic) {
        this.borrowPic = borrowPic == null ? null : borrowPic.trim();
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getLitpic() {
        return litpic;
    }

    public void setLitpic(String litpic) {
        this.litpic = litpic == null ? null : litpic.trim();
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag == null ? null : flag.trim();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType == null ? null : viewType.trim();
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
    }

    public BigDecimal getAmountAccount() {
        return amountAccount;
    }

    public void setAmountAccount(BigDecimal amountAccount) {
        this.amountAccount = amountAccount;
    }

    public String getAmountType() {
        return amountType;
    }

    public void setAmountType(String amountType) {
        this.amountType = amountType == null ? null : amountType.trim();
    }

    public Integer getCashStatus() {
        return cashStatus;
    }

    public void setCashStatus(Integer cashStatus) {
        this.cashStatus = cashStatus;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getOtherWebStatus() {
        return otherWebStatus;
    }

    public void setOtherWebStatus(Integer otherWebStatus) {
        this.otherWebStatus = otherWebStatus;
    }

    public String getBorrowType() {
        return borrowType;
    }

    public void setBorrowType(String borrowType) {
        this.borrowType = borrowType == null ? null : borrowType.trim();
    }

    public String getBorrowPassword() {
        return borrowPassword;
    }

    public void setBorrowPassword(String borrowPassword) {
        this.borrowPassword = borrowPassword == null ? null : borrowPassword.trim();
    }

    public String getBorrowFlag() {
        return borrowFlag;
    }

    public void setBorrowFlag(String borrowFlag) {
        this.borrowFlag = borrowFlag == null ? null : borrowFlag.trim();
    }

    public Integer getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(Integer borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public Integer getBorrowFullStatus() {
        return borrowFullStatus;
    }

    public void setBorrowFullStatus(Integer borrowFullStatus) {
        this.borrowFullStatus = borrowFullStatus;
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

    public String getBorrowUse() {
        return borrowUse;
    }

    public void setBorrowUse(String borrowUse) {
        this.borrowUse = borrowUse == null ? null : borrowUse.trim();
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

    public Integer getBorrowPeriodRoam() {
        return borrowPeriodRoam;
    }

    public void setBorrowPeriodRoam(Integer borrowPeriodRoam) {
        this.borrowPeriodRoam = borrowPeriodRoam;
    }

    public Integer getBorrowDay() {
        return borrowDay;
    }

    public void setBorrowDay(Integer borrowDay) {
        this.borrowDay = borrowDay;
    }

    public BigDecimal getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(BigDecimal borrowApr) {
        this.borrowApr = borrowApr;
    }

    public BigDecimal getBorrowFrostAccount() {
        return borrowFrostAccount;
    }

    public void setBorrowFrostAccount(BigDecimal borrowFrostAccount) {
        this.borrowFrostAccount = borrowFrostAccount;
    }

    public String getBorrowFrostScale() {
        return borrowFrostScale;
    }

    public void setBorrowFrostScale(String borrowFrostScale) {
        this.borrowFrostScale = borrowFrostScale == null ? null : borrowFrostScale.trim();
    }

    public BigDecimal getBorrowFrostSecond() {
        return borrowFrostSecond;
    }

    public void setBorrowFrostSecond(BigDecimal borrowFrostSecond) {
        this.borrowFrostSecond = borrowFrostSecond;
    }

    public Integer getBorrowValidTime() {
        return borrowValidTime;
    }

    public void setBorrowValidTime(Integer borrowValidTime) {
        this.borrowValidTime = borrowValidTime;
    }

    public Integer getBorrowSuccessTime() {
        return borrowSuccessTime;
    }

    public void setBorrowSuccessTime(Integer borrowSuccessTime) {
        this.borrowSuccessTime = borrowSuccessTime;
    }

    public String getBorrowEndTime() {
        return borrowEndTime;
    }

    public void setBorrowEndTime(String borrowEndTime) {
        this.borrowEndTime = borrowEndTime == null ? null : borrowEndTime.trim();
    }

    public Integer getBorrowPartStatus() {
        return borrowPartStatus;
    }

    public void setBorrowPartStatus(Integer borrowPartStatus) {
        this.borrowPartStatus = borrowPartStatus;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public Integer getLabelId() {
        return labelId;
    }

    public void setLabelId(Integer labelId) {
        this.labelId = labelId;
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

    public Integer getCancelUserid() {
        return cancelUserid;
    }

    public void setCancelUserid(Integer cancelUserid) {
        this.cancelUserid = cancelUserid;
    }

    public Integer getCancelStatus() {
        return cancelStatus;
    }

    public void setCancelStatus(Integer cancelStatus) {
        this.cancelStatus = cancelStatus;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime == null ? null : cancelTime.trim();
    }

    public String getCancelRemark() {
        return cancelRemark;
    }

    public void setCancelRemark(String cancelRemark) {
        this.cancelRemark = cancelRemark == null ? null : cancelRemark.trim();
    }

    public String getCancelContents() {
        return cancelContents;
    }

    public void setCancelContents(String cancelContents) {
        this.cancelContents = cancelContents == null ? null : cancelContents.trim();
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

    public Integer getTenderTimes() {
        return tenderTimes;
    }

    public void setTenderTimes(Integer tenderTimes) {
        this.tenderTimes = tenderTimes;
    }

    public String getTenderLastTime() {
        return tenderLastTime;
    }

    public void setTenderLastTime(String tenderLastTime) {
        this.tenderLastTime = tenderLastTime == null ? null : tenderLastTime.trim();
    }

    public Integer getRepayAdvanceStatus() {
        return repayAdvanceStatus;
    }

    public void setRepayAdvanceStatus(Integer repayAdvanceStatus) {
        this.repayAdvanceStatus = repayAdvanceStatus;
    }

    public String getRepayAdvanceTime() {
        return repayAdvanceTime;
    }

    public void setRepayAdvanceTime(String repayAdvanceTime) {
        this.repayAdvanceTime = repayAdvanceTime == null ? null : repayAdvanceTime.trim();
    }

    public Integer getRepayAdvanceStep() {
        return repayAdvanceStep;
    }

    public void setRepayAdvanceStep(Integer repayAdvanceStep) {
        this.repayAdvanceStep = repayAdvanceStep;
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

    public Integer getRepayAccountTimes() {
        return repayAccountTimes;
    }

    public void setRepayAccountTimes(Integer repayAccountTimes) {
        this.repayAccountTimes = repayAccountTimes;
    }

    public Integer getRepayMonthAccount() {
        return repayMonthAccount;
    }

    public void setRepayMonthAccount(Integer repayMonthAccount) {
        this.repayMonthAccount = repayMonthAccount;
    }

    public String getRepayLastTime() {
        return repayLastTime;
    }

    public void setRepayLastTime(String repayLastTime) {
        this.repayLastTime = repayLastTime == null ? null : repayLastTime.trim();
    }

    public String getRepayEachTime() {
        return repayEachTime;
    }

    public void setRepayEachTime(String repayEachTime) {
        this.repayEachTime = repayEachTime == null ? null : repayEachTime.trim();
    }

    public Integer getRepayNextTime() {
        return repayNextTime;
    }

    public void setRepayNextTime(Integer repayNextTime) {
        this.repayNextTime = repayNextTime;
    }

    public BigDecimal getRepayNextAccount() {
        return repayNextAccount;
    }

    public void setRepayNextAccount(BigDecimal repayNextAccount) {
        this.repayNextAccount = repayNextAccount;
    }

    public Integer getRepayTimes() {
        return repayTimes;
    }

    public void setRepayTimes(Integer repayTimes) {
        this.repayTimes = repayTimes;
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

    public BigDecimal getRepayFeeAdvance() {
        return repayFeeAdvance;
    }

    public void setRepayFeeAdvance(BigDecimal repayFeeAdvance) {
        this.repayFeeAdvance = repayFeeAdvance;
    }

    public BigDecimal getRepayFeeLate() {
        return repayFeeLate;
    }

    public void setRepayFeeLate(BigDecimal repayFeeLate) {
        this.repayFeeLate = repayFeeLate;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public BigDecimal getLateForfeit() {
        return lateForfeit;
    }

    public void setLateForfeit(BigDecimal lateForfeit) {
        this.lateForfeit = lateForfeit;
    }

    public Integer getVouchStatus() {
        return vouchStatus;
    }

    public void setVouchStatus(Integer vouchStatus) {
        this.vouchStatus = vouchStatus;
    }

    public Integer getVouchAdvanceStatus() {
        return vouchAdvanceStatus;
    }

    public void setVouchAdvanceStatus(Integer vouchAdvanceStatus) {
        this.vouchAdvanceStatus = vouchAdvanceStatus;
    }

    public Integer getVouchUserStatus() {
        return vouchUserStatus;
    }

    public void setVouchUserStatus(Integer vouchUserStatus) {
        this.vouchUserStatus = vouchUserStatus;
    }

    public String getVouchUsers() {
        return vouchUsers;
    }

    public void setVouchUsers(String vouchUsers) {
        this.vouchUsers = vouchUsers == null ? null : vouchUsers.trim();
    }

    public BigDecimal getVouchAccount() {
        return vouchAccount;
    }

    public void setVouchAccount(BigDecimal vouchAccount) {
        this.vouchAccount = vouchAccount;
    }

    public BigDecimal getVouchAccountYes() {
        return vouchAccountYes;
    }

    public void setVouchAccountYes(BigDecimal vouchAccountYes) {
        this.vouchAccountYes = vouchAccountYes;
    }

    public BigDecimal getVouchAccountWait() {
        return vouchAccountWait;
    }

    public void setVouchAccountWait(BigDecimal vouchAccountWait) {
        this.vouchAccountWait = vouchAccountWait;
    }

    public Long getVouchAccountScale() {
        return vouchAccountScale;
    }

    public void setVouchAccountScale(Long vouchAccountScale) {
        this.vouchAccountScale = vouchAccountScale;
    }

    public Integer getVouchTimes() {
        return vouchTimes;
    }

    public void setVouchTimes(Integer vouchTimes) {
        this.vouchTimes = vouchTimes;
    }

    public Integer getVouchAwardStatus() {
        return vouchAwardStatus;
    }

    public void setVouchAwardStatus(Integer vouchAwardStatus) {
        this.vouchAwardStatus = vouchAwardStatus;
    }

    public BigDecimal getVouchAwardScale() {
        return vouchAwardScale;
    }

    public void setVouchAwardScale(BigDecimal vouchAwardScale) {
        this.vouchAwardScale = vouchAwardScale;
    }

    public BigDecimal getVouchAwardAccount() {
        return vouchAwardAccount;
    }

    public void setVouchAwardAccount(BigDecimal vouchAwardAccount) {
        this.vouchAwardAccount = vouchAwardAccount;
    }

    public String getVoucherName() {
        return voucherName;
    }

    public void setVoucherName(String voucherName) {
        this.voucherName = voucherName == null ? null : voucherName.trim();
    }

    public String getVoucherLianxi() {
        return voucherLianxi;
    }

    public void setVoucherLianxi(String voucherLianxi) {
        this.voucherLianxi = voucherLianxi == null ? null : voucherLianxi.trim();
    }

    public String getVoucherAtt() {
        return voucherAtt;
    }

    public void setVoucherAtt(String voucherAtt) {
        this.voucherAtt = voucherAtt == null ? null : voucherAtt.trim();
    }

    public String getVouchjgName() {
        return vouchjgName;
    }

    public void setVouchjgName(String vouchjgName) {
        this.vouchjgName = vouchjgName == null ? null : vouchjgName.trim();
    }

    public String getVouchjgLianxi() {
        return vouchjgLianxi;
    }

    public void setVouchjgLianxi(String vouchjgLianxi) {
        this.vouchjgLianxi = vouchjgLianxi == null ? null : vouchjgLianxi.trim();
    }

    public String getVouchjgJs() {
        return vouchjgJs;
    }

    public void setVouchjgJs(String vouchjgJs) {
        this.vouchjgJs = vouchjgJs == null ? null : vouchjgJs.trim();
    }

    public String getVouchjgXy() {
        return vouchjgXy;
    }

    public void setVouchjgXy(String vouchjgXy) {
        this.vouchjgXy = vouchjgXy == null ? null : vouchjgXy.trim();
    }

    public Integer getFastStatus() {
        return fastStatus;
    }

    public void setFastStatus(Integer fastStatus) {
        this.fastStatus = fastStatus;
    }

    public Integer getVouchstatus() {
        return vouchstatus;
    }

    public void setVouchstatus(Integer vouchstatus) {
        this.vouchstatus = vouchstatus;
    }

    public Integer getGroupStatus() {
        return groupStatus;
    }

    public void setGroupStatus(Integer groupStatus) {
        this.groupStatus = groupStatus;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getAwardStatus() {
        return awardStatus;
    }

    public void setAwardStatus(Integer awardStatus) {
        this.awardStatus = awardStatus;
    }

    public Integer getAwardFalse() {
        return awardFalse;
    }

    public void setAwardFalse(Integer awardFalse) {
        this.awardFalse = awardFalse;
    }

    public BigDecimal getAwardAccount() {
        return awardAccount;
    }

    public void setAwardAccount(BigDecimal awardAccount) {
        this.awardAccount = awardAccount;
    }

    public BigDecimal getAwardScale() {
        return awardScale;
    }

    public void setAwardScale(BigDecimal awardScale) {
        this.awardScale = awardScale;
    }

    public BigDecimal getAwardAccountAll() {
        return awardAccountAll;
    }

    public void setAwardAccountAll(BigDecimal awardAccountAll) {
        this.awardAccountAll = awardAccountAll;
    }

    public Integer getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(Integer openAccount) {
        this.openAccount = openAccount;
    }

    public Integer getOpenBorrow() {
        return openBorrow;
    }

    public void setOpenBorrow(Integer openBorrow) {
        this.openBorrow = openBorrow;
    }

    public Integer getOpenTender() {
        return openTender;
    }

    public void setOpenTender(Integer openTender) {
        this.openTender = openTender;
    }

    public Integer getOpenCredit() {
        return openCredit;
    }

    public void setOpenCredit(Integer openCredit) {
        this.openCredit = openCredit;
    }

    public Integer getCommentStaus() {
        return commentStaus;
    }

    public void setCommentStaus(Integer commentStaus) {
        this.commentStaus = commentStaus;
    }

    public Integer getCommentTimes() {
        return commentTimes;
    }

    public void setCommentTimes(Integer commentTimes) {
        this.commentTimes = commentTimes;
    }

    public String getCommentUsertype() {
        return commentUsertype;
    }

    public void setCommentUsertype(String commentUsertype) {
        this.commentUsertype = commentUsertype == null ? null : commentUsertype.trim();
    }

    public String getBorrowPawnApp() {
        return borrowPawnApp;
    }

    public void setBorrowPawnApp(String borrowPawnApp) {
        this.borrowPawnApp = borrowPawnApp == null ? null : borrowPawnApp.trim();
    }

    public String getBorrowPawnAppUrl() {
        return borrowPawnAppUrl;
    }

    public void setBorrowPawnAppUrl(String borrowPawnAppUrl) {
        this.borrowPawnAppUrl = borrowPawnAppUrl == null ? null : borrowPawnAppUrl.trim();
    }

    public String getBorrowPawnAuth() {
        return borrowPawnAuth;
    }

    public void setBorrowPawnAuth(String borrowPawnAuth) {
        this.borrowPawnAuth = borrowPawnAuth == null ? null : borrowPawnAuth.trim();
    }

    public String getBorrowPawnAuthUrl() {
        return borrowPawnAuthUrl;
    }

    public void setBorrowPawnAuthUrl(String borrowPawnAuthUrl) {
        this.borrowPawnAuthUrl = borrowPawnAuthUrl == null ? null : borrowPawnAuthUrl.trim();
    }

    public String getBorrowPawnFormalities() {
        return borrowPawnFormalities;
    }

    public void setBorrowPawnFormalities(String borrowPawnFormalities) {
        this.borrowPawnFormalities = borrowPawnFormalities == null ? null : borrowPawnFormalities.trim();
    }

    public String getBorrowPawnFormalitiesUrl() {
        return borrowPawnFormalitiesUrl;
    }

    public void setBorrowPawnFormalitiesUrl(String borrowPawnFormalitiesUrl) {
        this.borrowPawnFormalitiesUrl = borrowPawnFormalitiesUrl == null ? null : borrowPawnFormalitiesUrl.trim();
    }

    public String getBorrowPawnType() {
        return borrowPawnType;
    }

    public void setBorrowPawnType(String borrowPawnType) {
        this.borrowPawnType = borrowPawnType == null ? null : borrowPawnType.trim();
    }

    public String getBorrowPawnTime() {
        return borrowPawnTime;
    }

    public void setBorrowPawnTime(String borrowPawnTime) {
        this.borrowPawnTime = borrowPawnTime == null ? null : borrowPawnTime.trim();
    }

    public String getBorrowPawnValue() {
        return borrowPawnValue;
    }

    public void setBorrowPawnValue(String borrowPawnValue) {
        this.borrowPawnValue = borrowPawnValue == null ? null : borrowPawnValue.trim();
    }

    public String getBorrowPawnXin() {
        return borrowPawnXin;
    }

    public void setBorrowPawnXin(String borrowPawnXin) {
        this.borrowPawnXin = borrowPawnXin == null ? null : borrowPawnXin.trim();
    }

    public String getOrderTop() {
        return orderTop;
    }

    public void setOrderTop(String orderTop) {
        this.orderTop = orderTop == null ? null : orderTop.trim();
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

    public String getVerifyTime() {
        return verifyTime;
    }

    public void setVerifyTime(String verifyTime) {
        this.verifyTime = verifyTime == null ? null : verifyTime.trim();
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

    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
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

    public String getReverifyTime() {
        return reverifyTime;
    }

    public void setReverifyTime(String reverifyTime) {
        this.reverifyTime = reverifyTime == null ? null : reverifyTime.trim();
    }

    public String getReverifyRemark() {
        return reverifyRemark;
    }

    public void setReverifyRemark(String reverifyRemark) {
        this.reverifyRemark = reverifyRemark == null ? null : reverifyRemark.trim();
    }

    public Integer getReverifyStatus() {
        return reverifyStatus;
    }

    public void setReverifyStatus(Integer reverifyStatus) {
        this.reverifyStatus = reverifyStatus;
    }

    public String getReverifyContents() {
        return reverifyContents;
    }

    public void setReverifyContents(String reverifyContents) {
        this.reverifyContents = reverifyContents == null ? null : reverifyContents.trim();
    }

    public String getUpfilesId() {
        return upfilesId;
    }

    public void setUpfilesId(String upfilesId) {
        this.upfilesId = upfilesId == null ? null : upfilesId.trim();
    }

    public String getXmupfilesId() {
        return xmupfilesId;
    }

    public void setXmupfilesId(String xmupfilesId) {
        this.xmupfilesId = xmupfilesId == null ? null : xmupfilesId.trim();
    }

    public String getDyupfilesId() {
        return dyupfilesId;
    }

    public void setDyupfilesId(String dyupfilesId) {
        this.dyupfilesId = dyupfilesId == null ? null : dyupfilesId.trim();
    }

    public Integer getGuaranteeType() {
        return guaranteeType;
    }

    public void setGuaranteeType(Integer guaranteeType) {
        this.guaranteeType = guaranteeType;
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getOntime() {
        return ontime;
    }

    public void setOntime(Integer ontime) {
        this.ontime = ontime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
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

    public String getBorrowManager() {
        return borrowManager;
    }

    public void setBorrowManager(String borrowManager) {
        this.borrowManager = borrowManager == null ? null : borrowManager.trim();
    }

    public String getBorrowService() {
        return borrowService;
    }

    public void setBorrowService(String borrowService) {
        this.borrowService = borrowService == null ? null : borrowService.trim();
    }

    public String getBorrowManagerScaleEnd() {
        return borrowManagerScaleEnd;
    }

    public void setBorrowManagerScaleEnd(String borrowManagerScaleEnd) {
        this.borrowManagerScaleEnd = borrowManagerScaleEnd == null ? null : borrowManagerScaleEnd.trim();
    }

    public Integer getBorrowFullTime() {
        return borrowFullTime;
    }

    public void setBorrowFullTime(Integer borrowFullTime) {
        this.borrowFullTime = borrowFullTime;
    }

    public Integer getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(Integer recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getConsumeId() {
        return consumeId;
    }

    public void setConsumeId(String consumeId) {
        this.consumeId = consumeId == null ? null : consumeId.trim();
    }

    public Integer getVerifyOverTime() {
        return verifyOverTime;
    }

    public void setVerifyOverTime(Integer verifyOverTime) {
        this.verifyOverTime = verifyOverTime;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
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

    public Integer getBankInputFlag() {
        return bankInputFlag;
    }

    public void setBankInputFlag(Integer bankInputFlag) {
        this.bankInputFlag = bankInputFlag;
    }

    public Integer getBookingBeginTime() {
        return bookingBeginTime;
    }

    public void setBookingBeginTime(Integer bookingBeginTime) {
        this.bookingBeginTime = bookingBeginTime;
    }

    public Integer getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(Integer bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public BigDecimal getBorrowAccountWaitAppoint() {
        return borrowAccountWaitAppoint;
    }

    public void setBorrowAccountWaitAppoint(BigDecimal borrowAccountWaitAppoint) {
        this.borrowAccountWaitAppoint = borrowAccountWaitAppoint;
    }

    public BigDecimal getBorrowAccountScaleAppoint() {
        return borrowAccountScaleAppoint;
    }

    public void setBorrowAccountScaleAppoint(BigDecimal borrowAccountScaleAppoint) {
        this.borrowAccountScaleAppoint = borrowAccountScaleAppoint;
    }

    public Integer getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(Integer bookingStatus) {
        this.bookingStatus = bookingStatus;
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

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
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

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
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

    public String getPublishInstCode() {
        return publishInstCode;
    }

    public void setPublishInstCode(String publishInstCode) {
        this.publishInstCode = publishInstCode == null ? null : publishInstCode.trim();
    }

    public Integer getIsEngineUsed() {
        return isEngineUsed;
    }

    public void setIsEngineUsed(Integer isEngineUsed) {
        this.isEngineUsed = isEngineUsed;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getBorrowPeriodStr() {
        return borrowPeriodStr;
    }

    public void setBorrowPeriodStr(String borrowPeriodStr) {
        this.borrowPeriodStr = borrowPeriodStr;
    }

    public Integer getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(Integer increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }
}
