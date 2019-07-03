package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowRepay implements Serializable {
    /**
     * ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 借款人用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 借款人用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 标识名(标的编号_借款人用户ID_1)放款时更新
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 实际还款人（借款人、担保机构、保证金）的用户ID
     *
     * @mbggenerated
     */
    private Integer repayUserId;

    /**
     * 还款状态(wait,wait_yes)
     *
     * @mbggenerated
     */
    private String repayType;

    /**
     * 还款费用
     *
     * @mbggenerated
     */
    private BigDecimal repayFee;

    /**
     * 执行还款的时间
     *
     * @mbggenerated
     */
    private Integer repayActionTime;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer repayPeriod;

    /**
     * 下期还款时间
     *
     * @mbggenerated
     */
    private Integer repayTime;

    /**
     * 已经还款时间
     *
     * @mbggenerated
     */
    private Integer repayYestime;

    /**
     * 还款总额，加上费用
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountAll;

    /**
     * 应还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccount;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterest;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapital;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal repayAccountYes;

    /**
     * 逾期的天数
     *
     * @mbggenerated
     */
    private Integer lateRepayDays;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestYes;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalYes;

    /**
     * 未还本金
     *
     * @mbggenerated
     */
    private BigDecimal repayCapitalWait;

    /**
     * 未还利息
     *
     * @mbggenerated
     */
    private BigDecimal repayInterestWait;

    /**
     * 提前还款天数
     *
     * @mbggenerated
     */
    private Integer chargeDays;

    /**
     * 提前还款减息(已加罚息)
     *
     * @mbggenerated
     */
    private BigDecimal chargeInterest;

    /**
     * 逾期天数
     *
     * @mbggenerated
     */
    private Integer lateDays;

    /**
     * 逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal lateInterest;

    /**
     * 延期天数
     *
     * @mbggenerated
     */
    private Integer delayDays;

    /**
     * 延期利息
     *
     * @mbggenerated
     */
    private BigDecimal delayInterest;

    /**
     * 延期备注说明
     *
     * @mbggenerated
     */
    private String delayRemark;

    /**
     * 还款来源（1、借款人还款，2、机构垫付，3、保证金垫付）
     *
     * @mbggenerated
     */
    private Integer repayMoneySource;

    /**
     * 还款短信提醒(0:未发送提醒,1:还款前三天提醒,2:还款当天提醒)
     *
     * @mbggenerated
     */
    private Integer repaySmsReminder;

    /**
     * 状态(初始:0,放款成功后更新成:1)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 是否自动还款 0:非自动还款 1:是自动还款
     *
     * @mbggenerated
     */
    private Integer autoRepay;

    /**
     * 0:正常,1:提前,2:延期,3:逾期
     *
     * @mbggenerated
     */
    private Integer advanceStatus;

    /**
     * 还款状态(0:未还款,1:已还款)
     *
     * @mbggenerated
     */
    private Integer repayStatus;

    /**
     * 实际还款人（借款人、担保机构、保证金）的用户名
     *
     * @mbggenerated
     */
    private String repayUsername;

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
     * 提前还款罚息
     *
     * @mbggenerated
     */
    private BigDecimal chargePenaltyInterest;

    /**
     * 逾期待还利息
     *
     * @mbggenerated
     */
    private BigDecimal lateInterestWait;

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

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public String getNid() {
        return nid;
    }

    public void setNid(String nid) {
        this.nid = nid == null ? null : nid.trim();
    }

    public Integer getRepayUserId() {
        return repayUserId;
    }

    public void setRepayUserId(Integer repayUserId) {
        this.repayUserId = repayUserId;
    }

    public String getRepayType() {
        return repayType;
    }

    public void setRepayType(String repayType) {
        this.repayType = repayType == null ? null : repayType.trim();
    }

    public BigDecimal getRepayFee() {
        return repayFee;
    }

    public void setRepayFee(BigDecimal repayFee) {
        this.repayFee = repayFee;
    }

    public Integer getRepayActionTime() {
        return repayActionTime;
    }

    public void setRepayActionTime(Integer repayActionTime) {
        this.repayActionTime = repayActionTime;
    }

    public Integer getRepayPeriod() {
        return repayPeriod;
    }

    public void setRepayPeriod(Integer repayPeriod) {
        this.repayPeriod = repayPeriod;
    }

    public Integer getRepayTime() {
        return repayTime;
    }

    public void setRepayTime(Integer repayTime) {
        this.repayTime = repayTime;
    }

    public Integer getRepayYestime() {
        return repayYestime;
    }

    public void setRepayYestime(Integer repayYestime) {
        this.repayYestime = repayYestime;
    }

    public BigDecimal getRepayAccountAll() {
        return repayAccountAll;
    }

    public void setRepayAccountAll(BigDecimal repayAccountAll) {
        this.repayAccountAll = repayAccountAll;
    }

    public BigDecimal getRepayAccount() {
        return repayAccount;
    }

    public void setRepayAccount(BigDecimal repayAccount) {
        this.repayAccount = repayAccount;
    }

    public BigDecimal getRepayInterest() {
        return repayInterest;
    }

    public void setRepayInterest(BigDecimal repayInterest) {
        this.repayInterest = repayInterest;
    }

    public BigDecimal getRepayCapital() {
        return repayCapital;
    }

    public void setRepayCapital(BigDecimal repayCapital) {
        this.repayCapital = repayCapital;
    }

    public BigDecimal getRepayAccountYes() {
        return repayAccountYes;
    }

    public void setRepayAccountYes(BigDecimal repayAccountYes) {
        this.repayAccountYes = repayAccountYes;
    }

    public Integer getLateRepayDays() {
        return lateRepayDays;
    }

    public void setLateRepayDays(Integer lateRepayDays) {
        this.lateRepayDays = lateRepayDays;
    }

    public BigDecimal getRepayInterestYes() {
        return repayInterestYes;
    }

    public void setRepayInterestYes(BigDecimal repayInterestYes) {
        this.repayInterestYes = repayInterestYes;
    }

    public BigDecimal getRepayCapitalYes() {
        return repayCapitalYes;
    }

    public void setRepayCapitalYes(BigDecimal repayCapitalYes) {
        this.repayCapitalYes = repayCapitalYes;
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

    public Integer getChargeDays() {
        return chargeDays;
    }

    public void setChargeDays(Integer chargeDays) {
        this.chargeDays = chargeDays;
    }

    public BigDecimal getChargeInterest() {
        return chargeInterest;
    }

    public void setChargeInterest(BigDecimal chargeInterest) {
        this.chargeInterest = chargeInterest;
    }

    public Integer getLateDays() {
        return lateDays;
    }

    public void setLateDays(Integer lateDays) {
        this.lateDays = lateDays;
    }

    public BigDecimal getLateInterest() {
        return lateInterest;
    }

    public void setLateInterest(BigDecimal lateInterest) {
        this.lateInterest = lateInterest;
    }

    public Integer getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Integer delayDays) {
        this.delayDays = delayDays;
    }

    public BigDecimal getDelayInterest() {
        return delayInterest;
    }

    public void setDelayInterest(BigDecimal delayInterest) {
        this.delayInterest = delayInterest;
    }

    public String getDelayRemark() {
        return delayRemark;
    }

    public void setDelayRemark(String delayRemark) {
        this.delayRemark = delayRemark == null ? null : delayRemark.trim();
    }

    public Integer getRepayMoneySource() {
        return repayMoneySource;
    }

    public void setRepayMoneySource(Integer repayMoneySource) {
        this.repayMoneySource = repayMoneySource;
    }

    public Integer getRepaySmsReminder() {
        return repaySmsReminder;
    }

    public void setRepaySmsReminder(Integer repaySmsReminder) {
        this.repaySmsReminder = repaySmsReminder;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getAutoRepay() {
        return autoRepay;
    }

    public void setAutoRepay(Integer autoRepay) {
        this.autoRepay = autoRepay;
    }

    public Integer getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Integer advanceStatus) {
        this.advanceStatus = advanceStatus;
    }

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public String getRepayUsername() {
        return repayUsername;
    }

    public void setRepayUsername(String repayUsername) {
        this.repayUsername = repayUsername == null ? null : repayUsername.trim();
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

    public BigDecimal getChargePenaltyInterest() {
        return chargePenaltyInterest;
    }

    public void setChargePenaltyInterest(BigDecimal chargePenaltyInterest) {
        this.chargePenaltyInterest = chargePenaltyInterest;
    }

    public BigDecimal getLateInterestWait() {
        return lateInterestWait;
    }

    public void setLateInterestWait(BigDecimal lateInterestWait) {
        this.lateInterestWait = lateInterestWait;
    }
}