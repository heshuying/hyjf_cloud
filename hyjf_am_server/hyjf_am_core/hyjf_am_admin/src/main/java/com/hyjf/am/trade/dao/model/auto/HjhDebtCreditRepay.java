package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhDebtCreditRepay implements Serializable {
    private Integer id;

    /**
     * 承接用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 承接用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 出让人用户ID
     *
     * @mbggenerated
     */
    private Integer creditUserId;

    /**
     * 出让人用户名
     *
     * @mbggenerated
     */
    private String creditUserName;

    /**
     * 原始标的投资订单号
     *
     * @mbggenerated
     */
    private String investOrderId;

    /**
     * 债转原始订单号
     *
     * @mbggenerated
     */
    private String sellOrderId;

    /**
     * 还款状态 0未还款 1已还款
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 原标标号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 原标还款期数
     *
     * @mbggenerated
     */
    private Integer repayPeriod;

    /**
     * 债转标号
     *
     * @mbggenerated
     */
    private String creditNid;

    /**
     * 承接计划nid
     *
     * @mbggenerated
     */
    private String assignPlanNid;

    /**
     * 承接计划订单号
     *
     * @mbggenerated
     */
    private String assignPlanOrderId;

    /**
     * 债转承接订单号
     *
     * @mbggenerated
     */
    private String assignOrderId;

    /**
     * 投资人投标成功的授权号
     *
     * @mbggenerated
     */
    private String authCode;

    /**
     * 应还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccount;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapital;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterest;

    /**
     * 待还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountWait;

    /**
     * 待还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalWait;

    /**
     * 待还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestWait;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountYes;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalYes;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestYes;

    /**
     * 最后还款日
     *
     * @mbggenerated
     */
    private Integer assignRepayEndTime;

    /**
     * 上次还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayLastTime;

    /**
     * 下次还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayNextTime;

    /**
     * 最终实际还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayYesTime;

    /**
     * 还款时间
     *
     * @mbggenerated
     */
    private Integer assignRepayTime;

    /**
     * 是否删除 0未删除 1已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 债权还款期数
     *
     * @mbggenerated
     */
    private Integer assignRepayPeriod;

    /**
     * 认购日期
     *
     * @mbggenerated
     */
    private Integer assignCreateDate;

    /**
     * 承接客户端 0PC,1微官网,2Android,3iOS,4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 管理费
     *
     * @mbggenerated
     */
    private BigDecimal manageFee;

    /**
     * 清算时收取的服务费
     *
     * @mbggenerated
     */
    private BigDecimal liquidatesServiceFee;

    /**
     * 唯一nid
     *
     * @mbggenerated
     */
    private String uniqueNid;

    /**
     * 债转还款订单号
     *
     * @mbggenerated
     */
    private String creditRepayOrderId;

    /**
     * 债转还款订单日期
     *
     * @mbggenerated
     */
    private String creditRepayOrderDate;

    /**
     * 是否提前还款 0正常还款 1提前还款 2延期还款 3逾期还款
     *
     * @mbggenerated
     */
    private Integer advanceStatus;

    /**
     * 提前还款天数
     *
     * @mbggenerated
     */
    private Integer advanceDays;

    /**
     * 借款人提前还款利息(已加罚息)
     *
     * @mbggenerated
     */
    private BigDecimal repayAdvanceInterest;

    /**
     * 逾期天数
     *
     * @mbggenerated
     */
    private Integer lateDays;

    /**
     * 借款人还款逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal repayLateInterest;

    /**
     * 延期天数
     *
     * @mbggenerated
     */
    private Integer delayDays;

    /**
     * 借款人还款延期利息
     *
     * @mbggenerated
     */
    private BigDecimal repayDelayInterest;

    /**
     * 投资人收到的还款本息
     *
     * @mbggenerated
     */
    private BigDecimal receiveAccountYes;

    /**
     * 投资人收到的还款本金
     *
     * @mbggenerated
     */
    private BigDecimal receiveCapitalYes;

    /**
     * 投资人收到的还款利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveInterestYes;

    /**
     * 投资人收取提前还款利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveAdvanceInterest;

    /**
     * 投资人收取逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveLateInterest;

    /**
     * 投资人收取延期利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveDelayInterest;

    /**
     * 投资人已收取提前还款利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveAdvanceInterestYes;

    /**
     * 投资人已收取逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveLateInterestYes;

    /**
     * 投资人已收取延期利息
     *
     * @mbggenerated
     */
    private BigDecimal receiveDelayInterestYes;

    /**
     * 出让次数
     *
     * @mbggenerated
     */
    private Integer creditTimes;

    /**
     * 债权是否结束状态(0:否,1:是)还款时用
     *
     * @mbggenerated
     */
    private Integer debtStatus;

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
     * 更新用户名
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

    /**
     * 提前还款罚息
     *
     * @mbggenerated
     */
    private BigDecimal repayAdvancePenaltyInterest;

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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
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

    public Integer getCreditTimes() {
        return creditTimes;
    }

    public void setCreditTimes(Integer creditTimes) {
        this.creditTimes = creditTimes;
    }

    public Integer getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(Integer debtStatus) {
        this.debtStatus = debtStatus;
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