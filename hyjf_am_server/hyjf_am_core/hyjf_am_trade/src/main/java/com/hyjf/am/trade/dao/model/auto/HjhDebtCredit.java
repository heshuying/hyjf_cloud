package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhDebtCredit implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 债转用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 转让用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 新计划编号
     *
     * @mbggenerated
     */
    private String planNidNew;

    /**
     * 计划加入订单号(旧计划)
     *
     * @mbggenerated
     */
    private String planOrderId;

    /**
     * 借款编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 标的名称
     *
     * @mbggenerated
     */
    private String borrowName;

    /**
     * 原标年化利率
     *
     * @mbggenerated
     */
    private BigDecimal borrowApr;

    /**
     * 原标还款方式
     *
     * @mbggenerated
     */
    private String borrowStyle;

    /**
     * 原标项目期限
     *
     * @mbggenerated
     */
    private Integer borrowPeriod;

    /**
     * 原标机构编号
     *
     * @mbggenerated
     */
    private String instCode;

    /**
     * 原标资产类型
     *
     * @mbggenerated
     */
    private Integer assetType;

    /**
     * 原标项目类型(0汇保贷 1汇典贷 2汇小贷 3汇车贷 4新手标)
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 清算后债权实际年华收益率
     *
     * @mbggenerated
     */
    private BigDecimal actualApr;

    /**
     * 原始标的投资订单号
     *
     * @mbggenerated
     */
    private String investOrderId;

    /**
     * 原投资订单号
     *
     * @mbggenerated
     */
    private String sellOrderId;

    /**
     * 债转编号
     *
     * @mbggenerated
     */
    private String creditNid;

    /**
     * 转让状态：0.承接中，1.部分承接，2完全承接，3承接终止
     *
     * @mbggenerated
     */
    private Integer creditStatus;

    /**
     * 债转还款状态 0 未还款 1还款中 2还款完成
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 是否已清算(0:未清算,1:已清算)
     *
     * @mbggenerated
     */
    private Integer isLiquidates;

    /**
     * 持有天数
     *
     * @mbggenerated
     */
    private Integer holdDays;

    /**
     * 剩余天数
     *
     * @mbggenerated
     */
    private Integer remainDays;

    /**
     * 当前期计息天数
     *
     * @mbggenerated
     */
    private Integer duringDays;

    /**
     * 承接原项目所在期数
     *
     * @mbggenerated
     */
    private Integer assignPeriod;

    /**
     * 清算时所在期数
     *
     * @mbggenerated
     */
    private Integer liquidatesPeriod;

    /**
     * 债转期数
     *
     * @mbggenerated
     */
    private Integer creditPeriod;

    /**
     * 已还款期数
     *
     * @mbggenerated
     */
    private Integer repayPeriod;

    /**
     * 债转期限
     *
     * @mbggenerated
     */
    private Integer creditTerm;

    /**
     * 清算时公允价值
     *
     * @mbggenerated
     */
    private BigDecimal liquidationFairValue;

    /**
     * 清算总本金
     *
     * @mbggenerated
     */
    private BigDecimal liquidatesCapital;

    /**
     * 债转总额
     *
     * @mbggenerated
     */
    private BigDecimal creditAccount;

    /**
     * 债转总本金
     *
     * @mbggenerated
     */
    private BigDecimal creditCapital;

    /**
     * 债转总利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterest;

    /**
     * 垫付总利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAdvance;

    /**
     * 清算时延期利息
     *
     * @mbggenerated
     */
    private BigDecimal creditDelayInterest;

    /**
     * 清算时逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal creditLateInterest;

    /**
     * 已承接总金额（此金额为本金加利息与还款相关，同垫付利息无关）
     *
     * @mbggenerated
     */
    private BigDecimal creditAccountAssigned;

    /**
     * 已承接本金
     *
     * @mbggenerated
     */
    private BigDecimal creditCapitalAssigned;

    /**
     * 已承接待还总利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAssigned;

    /**
     * 已承接垫付总利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAdvanceAssigned;

    /**
     * 承接已垫付的延期利息
     *
     * @mbggenerated
     */
    private BigDecimal creditDelayInterestAssigned;

    /**
     * 承接已垫付的逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal creditLateInterestAssigned;

    /**
     * 待承接总金额
     *
     * @mbggenerated
     */
    private BigDecimal creditAccountWait;

    /**
     * 待承接本金
     *
     * @mbggenerated
     */
    private BigDecimal creditCapitalWait;

    /**
     * 待承接利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestWait;

    /**
     * 已承接垫付总利息
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAdvanceWait;

    /**
     * 总收入，本金+垫付利息
     *
     * @mbggenerated
     */
    private BigDecimal creditIncome;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal creditServiceFee;

    /**
     * 出让价格
     *
     * @mbggenerated
     */
    private BigDecimal creditPrice;

    /**
     * 已还款总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccount;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapital;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterest;

    /**
     * 待还款本息
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountWait;

    /**
     * 待还款本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalWait;

    /**
     * 待还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestWait;

    /**
     * 债转计划最后还款时间
     *
     * @mbggenerated
     */
    private Integer creditRepayEndTime;

    /**
     * 上次还款时间
     *
     * @mbggenerated
     */
    private Integer creditRepayLastTime;

    /**
     * 下次还款时间
     *
     * @mbggenerated
     */
    private Integer creditRepayNextTime;

    /**
     * 最终实际还款时间
     *
     * @mbggenerated
     */
    private Integer creditRepayYesTime;

    /**
     * 债转结束时间（全部承接或者提前还款导致）
     *
     * @mbggenerated
     */
    private Integer endTime;

    /**
     * 承接次数
     *
     * @mbggenerated
     */
    private Integer assignNum;

    /**
     * 债转发起客户端,0PC,1微官网,2Android,3iOS,4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 是否原始债权 0非原始 1原始
     *
     * @mbggenerated
     */
    private Integer sourceType;

    /**
     * 债权出让次数
     *
     * @mbggenerated
     */
    private Integer creditTimes;

    /**
     * 是否逾期之后的债权
     *
     * @mbggenerated
     */
    private Integer isLateCredit;

    /**
     * 标签ID
     *
     * @mbggenerated
     */
    private Integer labelId;

    /**
     * 标签名称
     *
     * @mbggenerated
     */
    private String labelName;

    /**
     * 删除标识 0 未删除 1已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建用户id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新用户id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

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