package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

/**
 * 标的详情vo
 * @author zhangyk
 * @date 2018/6/22 19:22
 */
public class ProjectCustomeDetailVO  extends BaseVO {

    /** 序列化id */
    private static final long serialVersionUID = 4853144718353886143L;
    /* 项目大分类 projectType HZT 汇直投 HXF 汇消费 */
    private String projectType;
    /* 项目子分类代码 type 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标 5汇租赁 6供应贷 7汇房贷 8汇消费 9汇资产 */
    private String type;
    /* 项目子分类名称 typeName 0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标 5汇租赁 6供应贷 7汇房贷 8汇消费 9汇资产 */
    private String typeName;
    /* 项目编号 borrowNid */
    private String borrowNid;
    /* 项目名称 borrowName */
    private String borrowName;
    /* 项目还款方式 */
    private String borrowStyle;
    /* 授信额度 userCredit */
    private String userCredit;
    /* 合作机构MeasuresInstit */
    private String measuresInstit;
    /* 借款金额 */
    private String borrowAccount;
    /* 借款金额 万元 */
    private String borrowAccountWan;
    /* 项目年化收益 borrowApr */
    private String borrowApr;
    /* 项目期限 borrowPeriod */
    private String borrowPeriod;
    /* 项目期限类型 borrowPeriodType */
    private String borrowPeriodType;
    /* 可投金额 investAccount */
    private String investAccount;
    /* 项目区分 comOrPer 项目是个人项目还是企业项目 1企业 2个人 */
    private String comOrPer;
    /* 预期收益 borrowInterest */
    private String borrowInterest;
    /* 还款方式 repayStyle */
    private String repayStyle;
    /* 项目进度 borrowSchedule */
    private String borrowSchedule;
    // 最小投资金额
    private String tenderAccountMin;
    // 最小投资金额万
    private String tenderAccountMinWan;
    // 最大投资金额
    private String tenderAccountMax;
    /* 发标时间 sendTime ----------- */
    private String sendTime;
    // 项目满标时间
    private String fullTime;
    // 项目结束时间
    private String endTime;
    /* 定时发标时间 sendTime ----------- */
    private String onTime;
    /* 定时发标时间戳 time ----------- */
    private String time;
    /* 项目状态 status 10 定时发标 11投资中 12复审中 13 还款中 14 已还款 15 已流标 */
    private String status;
    /* 倍增金额 increaseMoney ----------- */
    private String increaseMoney;
    /* 加息券 interestCoupon ----------- */
    private String interestCoupon;
    /* 体验金 tasteMoney ----------- */
    private String tasteMoney;
    /* 预约状态 0初始 1预约中 2预约结束 bookingStatus ----------- */
    private String bookingStatus;
    /* 预约开始时间 */
    private Integer bookingBeginTime;
    /* 预约结束时间 ----------- */
    private Integer bookingEndTime;
    /* 预约标等待预约金额 borrowAccountWaitAppoint ----------- */
    private String borrowAccountWaitAppoint;
    /* 预约进度 borrowAccountScaleAppoint ----------- */
    private String borrowAccountScaleAppoint;
    /* 开始预约时间格式化显示 onAppointTime ----------- */
    private String onAppointTime;

    /* -----融通宝添加------ */
    // 资产编号
    private String borrowAssetNumber;
    // 项目来源
    private String borrowProjectSource;
    // 起息日期
    private String borrowInterestTime;
    // 到期日期
    private String borrowDueTime;
    // 保障方式
    private String borrowSafeguardWay;
    // 收益说明
    private String borrowIncomeDescription;
    // 发行人
    private String borrowPublisher;
    // 产品加息收益率
    private String borrowExtraYield;
    // 协议期数
    private String contractPeriod;
    // 放款日期
    private String recoverLastTime;
    // 借款评级
    private String borrowLevel;

    /* -----融通宝添加end------ */

    /* -----网站改版添加------ */

    // 新老标的 0为新标 1为老标
    private Integer isNew;
    // 项目标题
    private String projectName;
    // 融资用途
    private String financePurpose;
    // 月薪收入
    private String monthlyIncome;
    // 还款来源
    private String payment;
    // 第一还款来源
    private String firstPayment;
    // 第二还款来源
    private String secondPayment;
    // 费用说明
    private String costIntrodution;
    // 财务状况
    private String fianceCondition;
    // 项目信息
    private String borrowContents;
    // 风控措施
    private String  borrowMeasuresMea;

    /* -----网站改版添加end------ */


    // 是否展示(隐藏测试标用:0:显示,1:不显示)
    private Integer isShow;

    // 机构编号
    private String instCode;

    // 资产类型
    private Integer assetType;
    // 标的原始状态
    private Integer statusOrginal;

    private Integer projectStatus;

    // add by nxl 20180730 产品加息标志位(0:不加息,1:加息)
    private String increaseInterestFlag;
    // add by nxl 20180730 是否为产品加息
    private String isIncrease;

    private String borrowStatus;

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public ProjectCustomeDetailVO() {
        super();
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBorrowName() {
        return borrowName;
    }

    public void setBorrowName(String borrowName) {
        this.borrowName = borrowName;
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid;
    }

    public String getUserCredit() {
        return userCredit;
    }

    public void setUserCredit(String userCredit) {
        this.userCredit = userCredit;
    }

    public String getRepayStyle() {
        return repayStyle;
    }

    public void setRepayStyle(String repayStyle) {
        this.repayStyle = repayStyle;
    }

    public String getBorrowApr() {
        return borrowApr;
    }

    public void setBorrowApr(String borrowApr) {
        this.borrowApr = borrowApr;
    }

    public String getBorrowPeriod() {
        return borrowPeriod;
    }

    public void setBorrowPeriod(String borrowPeriod) {
        this.borrowPeriod = borrowPeriod;
    }

    public String getInvestAccount() {
        return investAccount;
    }

    public void setInvestAccount(String investAccount) {
        this.investAccount = investAccount;
    }

    public String getBorrowSchedule() {
        return borrowSchedule;
    }

    public void setBorrowSchedule(String borrowSchedule) {
        this.borrowSchedule = borrowSchedule;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComOrPer() {
        return comOrPer;
    }

    public void setComOrPer(String comOrPer) {
        this.comOrPer = comOrPer;
    }

    public String getBorrowAccount() {
        return borrowAccount;
    }

    public void setBorrowAccount(String borrowAccount) {
        this.borrowAccount = borrowAccount;
    }

    public String getBorrowInterest() {
        return borrowInterest;
    }

    public void setBorrowInterest(String borrowInterest) {
        this.borrowInterest = borrowInterest;
    }

    public String getMeasuresInstit() {
        return measuresInstit;
    }

    public void setMeasuresInstit(String measuresInstit) {
        this.measuresInstit = measuresInstit;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getBorrowPeriodType() {
        return borrowPeriodType;
    }

    public void setBorrowPeriodType(String borrowPeriodType) {
        this.borrowPeriodType = borrowPeriodType;
    }

    public String getTenderAccountMin() {
        return tenderAccountMin;
    }

    public void setTenderAccountMin(String tenderAccountMin) {
        this.tenderAccountMin = tenderAccountMin;
    }

    public String getTenderAccountMax() {
        return tenderAccountMax;
    }

    public void setTenderAccountMax(String tenderAccountMax) {
        this.tenderAccountMax = tenderAccountMax;
    }

    public String getOnTime() {
        return onTime;
    }

    public void setOnTime(String onTime) {
        this.onTime = onTime;
    }

    public String getFullTime() {
        return fullTime;
    }

    public void setFullTime(String fullTime) {
        this.fullTime = fullTime;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIncreaseMoney() {
        return increaseMoney;
    }

    public void setIncreaseMoney(String increaseMoney) {
        this.increaseMoney = increaseMoney;
    }

    public String getInterestCoupon() {
        return interestCoupon;
    }

    public void setInterestCoupon(String interestCoupon) {
        this.interestCoupon = interestCoupon;
    }

    public String getTasteMoney() {
        return tasteMoney;
    }

    public void setTasteMoney(String tasteMoney) {
        this.tasteMoney = tasteMoney;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getBorrowAccountWaitAppoint() {
        return borrowAccountWaitAppoint;
    }

    public void setBorrowAccountWaitAppoint(String borrowAccountWaitAppoint) {
        this.borrowAccountWaitAppoint = borrowAccountWaitAppoint;
    }

    public String getBorrowAccountScaleAppoint() {
        return borrowAccountScaleAppoint;
    }

    public void setBorrowAccountScaleAppoint(String borrowAccountScaleAppoint) {
        this.borrowAccountScaleAppoint = borrowAccountScaleAppoint;
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

    public String getOnAppointTime() {
        return onAppointTime;
    }

    public void setOnAppointTime(String onAppointTime) {
        this.onAppointTime = onAppointTime;
    }

    public String getBorrowAssetNumber() {
        return borrowAssetNumber;
    }

    public void setBorrowAssetNumber(String borrowAssetNumber) {
        this.borrowAssetNumber = borrowAssetNumber;
    }

    public String getBorrowProjectSource() {
        return borrowProjectSource;
    }

    public void setBorrowProjectSource(String borrowProjectSource) {
        this.borrowProjectSource = borrowProjectSource;
    }

    public String getBorrowInterestTime() {
        return borrowInterestTime;
    }

    public void setBorrowInterestTime(String borrowInterestTime) {
        this.borrowInterestTime = borrowInterestTime;
    }

    public String getBorrowDueTime() {
        return borrowDueTime;
    }

    public void setBorrowDueTime(String borrowDueTime) {
        this.borrowDueTime = borrowDueTime;
    }

    public String getBorrowSafeguardWay() {
        return borrowSafeguardWay;
    }

    public void setBorrowSafeguardWay(String borrowSafeguardWay) {
        this.borrowSafeguardWay = borrowSafeguardWay;
    }

    public String getBorrowIncomeDescription() {
        return borrowIncomeDescription;
    }

    public void setBorrowIncomeDescription(String borrowIncomeDescription) {
        this.borrowIncomeDescription = borrowIncomeDescription;
    }

    public String getBorrowPublisher() {
        return borrowPublisher;
    }

    public void setBorrowPublisher(String borrowPublisher) {
        this.borrowPublisher = borrowPublisher;
    }

    public String getBorrowExtraYield() {
        return borrowExtraYield;
    }

    public void setBorrowExtraYield(String borrowExtraYield) {
        this.borrowExtraYield = borrowExtraYield;
    }

    public String getContractPeriod() {
        return contractPeriod;
    }

    public void setContractPeriod(String contractPeriod) {
        this.contractPeriod = contractPeriod;
    }

    public String getBorrowAccountWan() {
        return borrowAccountWan;
    }

    public void setBorrowAccountWan(String borrowAccountWan) {
        this.borrowAccountWan = borrowAccountWan;
    }

    public String getTenderAccountMinWan() {
        return tenderAccountMinWan;
    }

    public void setTenderAccountMinWan(String tenderAccountMinWan) {
        this.tenderAccountMinWan = tenderAccountMinWan;
    }

    public String getRecoverLastTime() {
        return recoverLastTime;
    }

    public void setRecoverLastTime(String recoverLastTime) {
        this.recoverLastTime = recoverLastTime;
    }

    public String getBorrowLevel() {
        return borrowLevel;
    }

    public void setBorrowLevel(String borrowLevel) {
        this.borrowLevel = borrowLevel;
    }

    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFinancePurpose() {
        return financePurpose;
    }

    public void setFinancePurpose(String financePurpose) {
        this.financePurpose = financePurpose;
    }

    public String getMonthlyIncome() {
        return monthlyIncome;
    }

    public void setMonthlyIncome(String monthlyIncome) {
        this.monthlyIncome = monthlyIncome;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getFirstPayment() {
        return firstPayment;
    }

    public void setFirstPayment(String firstPayment) {
        this.firstPayment = firstPayment;
    }

    public String getSecondPayment() {
        return secondPayment;
    }

    public void setSecondPayment(String secondPayment) {
        this.secondPayment = secondPayment;
    }

    public String getCostIntrodution() {
        return costIntrodution;
    }

    public void setCostIntrodution(String costIntrodution) {
        this.costIntrodution = costIntrodution;
    }

    public String getFianceCondition() {
        return fianceCondition;
    }

    public void setFianceCondition(String fianceCondition) {
        this.fianceCondition = fianceCondition;
    }

    public String getBorrowContents() {
        return borrowContents;
    }

    public void setBorrowContents(String borrowContents) {
        this.borrowContents = borrowContents;
    }

    public String getBorrowMeasuresMea() {
        return borrowMeasuresMea;
    }

    public void setBorrowMeasuresMea(String borrowMeasuresMea) {
        this.borrowMeasuresMea = borrowMeasuresMea;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public Integer getAssetType() {
        return assetType;
    }

    public void setAssetType(Integer assetType) {
        this.assetType = assetType;
    }

    public Integer getStatusOrginal() {
        return statusOrginal;
    }

    public void setStatusOrginal(Integer statusOrginal) {
        this.statusOrginal = statusOrginal;
    }

    public Integer getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(Integer projectStatus) {
        this.projectStatus = projectStatus;
    }

    public String getIncreaseInterestFlag() {
        return increaseInterestFlag;
    }

    public void setIncreaseInterestFlag(String increaseInterestFlag) {
        this.increaseInterestFlag = increaseInterestFlag;
    }

    public String getIsIncrease() {
        return isIncrease;
    }

    public void setIsIncrease(String isIncrease) {
        this.isIncrease = isIncrease;
    }
}
