package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowInfo implements Serializable {
    private Integer id;

    /**
     * 借款的识别名
     *
     * @mbggenerated
     */
    private String borrowNid;

    private String borrowPreNid;

    /**
     * 新的标的编号排序
     *
     * @mbggenerated
     */
    private String borrowPreNidNew;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 借款用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 借款用户名称
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 项目申请人
     *
     * @mbggenerated
     */
    private String applicant;

    /**
     * 担保机构用户名
     *
     * @mbggenerated
     */
    private String repayOrgName;

    /**
     * 是否可用担保机构还款(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer isRepayOrgFlag;

    /**
     * 担保机构用户ID
     *
     * @mbggenerated
     */
    private Integer repayOrgUserId;

    /**
     * 1房贷2车贷
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 用途
     *
     * @mbggenerated
     */
    private String borrowUse;

    /**
     * 借款有效时间
     *
     * @mbggenerated
     */
    private Integer borrowValidTime;

    /**
     * 机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 资产类型
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 资产类型名称
     *
     * @mbggenerated
     */
    private String assetTypeName;

    /**
     * 受托支付标志 0 否；1是
     *
     * @mbggenerated
     */
    private Integer entrustedFlg;

    /**
     * 受托支付用户名
     *
     * @mbggenerated
     */
    private String entrustedUserName;

    /**
     * 受托支付用户id
     *
     * @mbggenerated
     */
    private Integer entrustedUserId;

    /**
     * 受托支付申请时间
     *
     * @mbggenerated
     */
    private Integer trusteePayTime;

    /**
     * 最小的出借额
     *
     * @mbggenerated
     */
    private Integer tenderAccountMin;

    /**
     * 最大的出借额
     *
     * @mbggenerated
     */
    private Integer tenderAccountMax;

    /**
     *  发标上传图片
     *
     * @mbggenerated
     */
    private String upfilesId;

    /**
     * 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 可出借平台_PC
     *
     * @mbggenerated
     */
    private String canTransactionPc;

    /**
     * 可出借平台_微网站
     *
     * @mbggenerated
     */
    private String canTransactionWei;

    /**
     * 可出借平台_IOS
     *
     * @mbggenerated
     */
    private String canTransactionIos;

    /**
     * 可出借平台_Android
     *
     * @mbggenerated
     */
    private String canTransactionAndroid;

    /**
     * 运营标签
     *
     * @mbggenerated
     */
    private String operationLabel;

    /**
     * 借款人信息 借款类型 1：公司 2：个人
     *
     * @mbggenerated
     */
    private Integer companyOrPersonal;

    /**
     * 账户管理费率下线
     *
     * @mbggenerated
     */
    private String borrowManagerScaleEnd;

    /**
     * 资产包编号
     *
     * @mbggenerated
     */
    private String consumeId;

    /**
     * 售价预估    
     *
     * @mbggenerated
     */
    private String disposalPriceEstimate;

    /**
     * 处置周期    
     *
     * @mbggenerated
     */
    private String disposalPeriod;

    /**
     * 处置渠道    
     *
     * @mbggenerated
     */
    private String disposalChannel;

    /**
     * 处置结果预案
     *
     * @mbggenerated
     */
    private String disposalResult;

    /**
     * 备注说明
     *
     * @mbggenerated
     */
    private String disposalNote;

    /**
     * 项目名称    
     *
     * @mbggenerated
     */
    private String disposalProjectName;

    /**
     * 项目类型    
     *
     * @mbggenerated
     */
    private String disposalProjectType;

    /**
     * 所在地区    
     *
     * @mbggenerated
     */
    private String disposalArea;

    /**
     * 预估价值    
     *
     * @mbggenerated
     */
    private String disposalPredictiveValue;

    /**
     * 权属类别    
     *
     * @mbggenerated
     */
    private String disposalOwnershipCategory;

    /**
     * 资产成因    
     *
     * @mbggenerated
     */
    private String disposalAssetOrigin;

    /**
     * 附件信息
     *
     * @mbggenerated
     */
    private String disposalAttachmentInfo;

    /**
     * 递增金额
     *
     * @mbggenerated
     */
    private Long borrowIncreaseMoney;

    /**
     * 优惠券
     *
     * @mbggenerated
     */
    private Integer borrowInterestCoupon;

    /**
     * 体验金
     *
     * @mbggenerated
     */
    private Integer borrowTasteMoney;

    /**
     * 资产编号（风险缓释金）
     *
     * @mbggenerated
     */
    private String borrowAssetNumber;

    /**
     * 项目来源（项目详情中）
     *
     * @mbggenerated
     */
    private String borrowProjectSource;

    /**
     * 起息时间（项目详情中）
     *
     * @mbggenerated
     */
    private String borrowInterestTime;

    /**
     * 到期时间（项目详情中）
     *
     * @mbggenerated
     */
    private String borrowDueTime;

    /**
     * 保障方式（项目详情中）
     *
     * @mbggenerated
     */
    private String borrowSafeguardWay;

    /**
     * 收益说明（项目详情中）
     *
     * @mbggenerated
     */
    private String borrowIncomeDescription;

    /**
     * 发行人（风险缓释金）
     *
     * @mbggenerated
     */
    private String borrowPublisher;

    /**
     * 产品加息收益率（风险缓释金）
     *
     * @mbggenerated
     */
    private BigDecimal borrowExtraYield;

    /**
     * 是否加息标志位(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer increaseInterestFlag;

    /**
     * 融通宝协议期数
     *
     * @mbggenerated
     */
    private Integer contractPeriod;

    /**
     * 借款人评级
     *
     * @mbggenerated
     */
    private String borrowLevel;

    /**
     * 资产属性 1:抵押标 2:质押标 3:信用标 4:债权转让标 5:净值标
     *
     * @mbggenerated
     */
    private Integer assetAttributes;

    /**
     * 银行募集开始时间
     *
     * @mbggenerated
     */
    private String bankRaiseStartDate;

    /**
     * 银行募集结束时间
     *
     * @mbggenerated
     */
    private String bankRaiseEndDate;

    /**
     * 银行备案天数
     *
     * @mbggenerated
     */
    private Integer bankRegistDays;

    /**
     * 银行项目天数
     *
     * @mbggenerated
     */
    private Integer bankBorrowDays;

    /**
     * 项目标题
     *
     * @mbggenerated
     */
    private String projectName;

    /**
     * 融资用途
     *
     * @mbggenerated
     */
    private String financePurpose;

    /**
     * 月薪收入
     *
     * @mbggenerated
     */
    private String monthlyIncome;

    /**
     * 还款来源
     *
     * @mbggenerated
     */
    private String payment;

    /**
     * 第一还款来源
     *
     * @mbggenerated
     */
    private String firstPayment;

    /**
     * 第二还款来源
     *
     * @mbggenerated
     */
    private String secondPayment;

    /**
     * 费用说明
     *
     * @mbggenerated
     */
    private String costIntrodution;

    /**
     * 财务状况
     *
     * @mbggenerated
     */
    private String fianceCondition;

    /**
     * 是否为新标 0 为老标 1为新标 （网站改版后都为新标）
     *
     * @mbggenerated
     */
    private Integer isNew;

    /**
     * 定向标: 0-全部  其他值-资金机构的编号
     *
     * @mbggenerated
     */
    private String publishInstCode;

    /**
     * 100位字符限制-备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建用户-添加标的人员（账户操作人）
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
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

    public Integer getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(Integer increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}