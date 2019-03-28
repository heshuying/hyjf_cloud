package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowRecover implements Serializable {
    private Integer id;

    /**
     * 状态(初始:0,放款成功后更新成:1)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 投资人用户名
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
     * 投资订单号(对应borrow_tender表的nid)
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 投资人投标成功的授权号
     *
     * @mbggenerated
     */
    private String authCode;

    /**
     * 借款人用户ID
     *
     * @mbggenerated
     */
    private Integer borrowUserid;

    /**
     * 借款人用户名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 投资ID(对应borrow_tender表里的ID字段)
     *
     * @mbggenerated
     */
    private Integer tenderId;

    /**
     * 汇计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 还款状态(只用于处理还款,展示请关联ht_borrow.status)(0:未还款,1:已还款,2:还款失败)
     *
     * @mbggenerated
     */
    private Integer recoverStatus;

    /**
     * 还款期数
     *
     * @mbggenerated
     */
    private Integer recoverPeriod;

    /**
     * 估计还款时间(分期标的是下一期应还时间)
     *
     * @mbggenerated
     */
    private Integer recoverTime;

    /**
     * 已经还款时间
     *
     * @mbggenerated
     */
    private Integer recoverYestime;

    /**
     * 应还总额
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccount;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal recoverInterest;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverCapital;

    /**
     * 已还总额
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountYes;

    /**
     * 已还利息
     *
     * @mbggenerated
     */
    private BigDecimal recoverInterestYes;

    /**
     * 已还本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverCapitalYes;

    /**
     * 未收总额
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountWait;

    /**
     * 未收本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverCapitalWait;

    /**
     * 未收本息
     *
     * @mbggenerated
     */
    private BigDecimal recoverInterestWait;

    /**
     * 还款流程(yes,late,wait)
     *
     * @mbggenerated
     */
    private String recoverType;

    /**
     * 放款服务费
     *
     * @mbggenerated
     */
    private BigDecimal recoverServiceFee;

    /**
     * 已收管理费
     *
     * @mbggenerated
     */
    private BigDecimal recoverFeeYes;

    /**
     * 已还款提前还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayChargeInterest;

    /**
     * 已还款延期还款利息
     *
     * @mbggenerated
     */
    private BigDecimal repayDelayInterest;

    /**
     * 已收债转管理费
     *
     * @mbggenerated
     */
    private BigDecimal creditManageFee;

    /**
     * 已还款逾期利息
     *
     * @mbggenerated
     */
    private BigDecimal repayLateInterest;

    /**
     * 债权是否结束状态(0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer debtStatus;

    /**
     * 债转状态 0初始 1承接停止 2完全承接
     *
     * @mbggenerated
     */
    private Integer creditStatus;

    /**
     * 账户管理费
     *
     * @mbggenerated
     */
    private BigDecimal recoverFee;

    /**
     * 逾期费用收入
     *
     * @mbggenerated
     */
    private BigDecimal recoverLateFee;

    /**
     * 0:正常,1:提前,2:延期,3:逾期
     *
     * @mbggenerated
     */
    private Integer advanceStatus;

    /**
     * 提前天数
     *
     * @mbggenerated
     */
    private Integer chargeDays;

    /**
     * 提前减息(已加罚息)
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
     * 延期费率
     *
     * @mbggenerated
     */
    private BigDecimal delayRate;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 0发送邮件，1已发送
     *
     * @mbggenerated
     */
    private Integer sendmail;

    /**
     * 网站收支计算标识
     *
     * @mbggenerated
     */
    private Integer web;

    /**
     * 已转让本金
     *
     * @mbggenerated
     */
    private BigDecimal creditAmount;

    /**
     * 已债转总利息（含垫付）
     *
     * @mbggenerated
     */
    private BigDecimal creditInterestAmount;

    /**
     * 债转时间
     *
     * @mbggenerated
     */
    private Integer creditTime;

    /**
     * 还款订单号
     *
     * @mbggenerated
     */
    private String repayOrdid;

    /**
     * 还款时间
     *
     * @mbggenerated
     */
    private String repayOrddate;

    /**
     * 推荐人用户名(还款时更新)
     *
     * @mbggenerated
     */
    private String inviteUserName;

    /**
     * 推荐人用户id(还款时更新)
     *
     * @mbggenerated
     */
    private Integer inviteUserId;

    /**
     * 一级部门id(还款时更新)
     *
     * @mbggenerated
     */
    private Integer inviteRegionId;

    /**
     * 一级部门名称(还款时更新)
     *
     * @mbggenerated
     */
    private String inviteRegionName;

    /**
     * 二级部门id(还款时更新)
     *
     * @mbggenerated
     */
    private Integer inviteBranchId;

    /**
     * 二级部门名称(还款时更新)
     *
     * @mbggenerated
     */
    private String inviteBranchName;

    /**
     * 三级部门id(还款时更新)
     *
     * @mbggenerated
     */
    private Integer inviteDepartmentId;

    /**
     * 三级部门名称(还款时更新)
     *
     * @mbggenerated
     */
    private String inviteDepartmentName;

    /**
     * 投资人用户属性
     *
     * @mbggenerated
     */
    private Integer tenderUserAttribute;

    /**
     * 推荐人用户属性
     *
     * @mbggenerated
     */
    private Integer inviteUserAttribute;

    /**
     * 放款批次号
     *
     * @mbggenerated
     */
    private String loanBatchNo;

    /**
     * 还款批次号
     *
     * @mbggenerated
     */
    private String repayBatchNo;

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

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public Integer getBorrowUserid() {
        return borrowUserid;
    }

    public void setBorrowUserid(Integer borrowUserid) {
        this.borrowUserid = borrowUserid;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
    }

    public Integer getTenderId() {
        return tenderId;
    }

    public void setTenderId(Integer tenderId) {
        this.tenderId = tenderId;
    }

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public Integer getRecoverStatus() {
        return recoverStatus;
    }

    public void setRecoverStatus(Integer recoverStatus) {
        this.recoverStatus = recoverStatus;
    }

    public Integer getRecoverPeriod() {
        return recoverPeriod;
    }

    public void setRecoverPeriod(Integer recoverPeriod) {
        this.recoverPeriod = recoverPeriod;
    }

    public Integer getRecoverTime() {
        return recoverTime;
    }

    public void setRecoverTime(Integer recoverTime) {
        this.recoverTime = recoverTime;
    }

    public Integer getRecoverYestime() {
        return recoverYestime;
    }

    public void setRecoverYestime(Integer recoverYestime) {
        this.recoverYestime = recoverYestime;
    }

    public BigDecimal getRecoverAccount() {
        return recoverAccount;
    }

    public void setRecoverAccount(BigDecimal recoverAccount) {
        this.recoverAccount = recoverAccount;
    }

    public BigDecimal getRecoverInterest() {
        return recoverInterest;
    }

    public void setRecoverInterest(BigDecimal recoverInterest) {
        this.recoverInterest = recoverInterest;
    }

    public BigDecimal getRecoverCapital() {
        return recoverCapital;
    }

    public void setRecoverCapital(BigDecimal recoverCapital) {
        this.recoverCapital = recoverCapital;
    }

    public BigDecimal getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public BigDecimal getRecoverInterestYes() {
        return recoverInterestYes;
    }

    public void setRecoverInterestYes(BigDecimal recoverInterestYes) {
        this.recoverInterestYes = recoverInterestYes;
    }

    public BigDecimal getRecoverCapitalYes() {
        return recoverCapitalYes;
    }

    public void setRecoverCapitalYes(BigDecimal recoverCapitalYes) {
        this.recoverCapitalYes = recoverCapitalYes;
    }

    public BigDecimal getRecoverAccountWait() {
        return recoverAccountWait;
    }

    public void setRecoverAccountWait(BigDecimal recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public BigDecimal getRecoverCapitalWait() {
        return recoverCapitalWait;
    }

    public void setRecoverCapitalWait(BigDecimal recoverCapitalWait) {
        this.recoverCapitalWait = recoverCapitalWait;
    }

    public BigDecimal getRecoverInterestWait() {
        return recoverInterestWait;
    }

    public void setRecoverInterestWait(BigDecimal recoverInterestWait) {
        this.recoverInterestWait = recoverInterestWait;
    }

    public String getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(String recoverType) {
        this.recoverType = recoverType == null ? null : recoverType.trim();
    }

    public BigDecimal getRecoverServiceFee() {
        return recoverServiceFee;
    }

    public void setRecoverServiceFee(BigDecimal recoverServiceFee) {
        this.recoverServiceFee = recoverServiceFee;
    }

    public BigDecimal getRecoverFeeYes() {
        return recoverFeeYes;
    }

    public void setRecoverFeeYes(BigDecimal recoverFeeYes) {
        this.recoverFeeYes = recoverFeeYes;
    }

    public BigDecimal getRepayChargeInterest() {
        return repayChargeInterest;
    }

    public void setRepayChargeInterest(BigDecimal repayChargeInterest) {
        this.repayChargeInterest = repayChargeInterest;
    }

    public BigDecimal getRepayDelayInterest() {
        return repayDelayInterest;
    }

    public void setRepayDelayInterest(BigDecimal repayDelayInterest) {
        this.repayDelayInterest = repayDelayInterest;
    }

    public BigDecimal getCreditManageFee() {
        return creditManageFee;
    }

    public void setCreditManageFee(BigDecimal creditManageFee) {
        this.creditManageFee = creditManageFee;
    }

    public BigDecimal getRepayLateInterest() {
        return repayLateInterest;
    }

    public void setRepayLateInterest(BigDecimal repayLateInterest) {
        this.repayLateInterest = repayLateInterest;
    }

    public Integer getDebtStatus() {
        return debtStatus;
    }

    public void setDebtStatus(Integer debtStatus) {
        this.debtStatus = debtStatus;
    }

    public Integer getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(Integer creditStatus) {
        this.creditStatus = creditStatus;
    }

    public BigDecimal getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(BigDecimal recoverFee) {
        this.recoverFee = recoverFee;
    }

    public BigDecimal getRecoverLateFee() {
        return recoverLateFee;
    }

    public void setRecoverLateFee(BigDecimal recoverLateFee) {
        this.recoverLateFee = recoverLateFee;
    }

    public Integer getAdvanceStatus() {
        return advanceStatus;
    }

    public void setAdvanceStatus(Integer advanceStatus) {
        this.advanceStatus = advanceStatus;
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

    public BigDecimal getDelayRate() {
        return delayRate;
    }

    public void setDelayRate(BigDecimal delayRate) {
        this.delayRate = delayRate;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getSendmail() {
        return sendmail;
    }

    public void setSendmail(Integer sendmail) {
        this.sendmail = sendmail;
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }

    public BigDecimal getCreditInterestAmount() {
        return creditInterestAmount;
    }

    public void setCreditInterestAmount(BigDecimal creditInterestAmount) {
        this.creditInterestAmount = creditInterestAmount;
    }

    public Integer getCreditTime() {
        return creditTime;
    }

    public void setCreditTime(Integer creditTime) {
        this.creditTime = creditTime;
    }

    public String getRepayOrdid() {
        return repayOrdid;
    }

    public void setRepayOrdid(String repayOrdid) {
        this.repayOrdid = repayOrdid == null ? null : repayOrdid.trim();
    }

    public String getRepayOrddate() {
        return repayOrddate;
    }

    public void setRepayOrddate(String repayOrddate) {
        this.repayOrddate = repayOrddate == null ? null : repayOrddate.trim();
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName == null ? null : inviteUserName.trim();
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public Integer getInviteRegionId() {
        return inviteRegionId;
    }

    public void setInviteRegionId(Integer inviteRegionId) {
        this.inviteRegionId = inviteRegionId;
    }

    public String getInviteRegionName() {
        return inviteRegionName;
    }

    public void setInviteRegionName(String inviteRegionName) {
        this.inviteRegionName = inviteRegionName == null ? null : inviteRegionName.trim();
    }

    public Integer getInviteBranchId() {
        return inviteBranchId;
    }

    public void setInviteBranchId(Integer inviteBranchId) {
        this.inviteBranchId = inviteBranchId;
    }

    public String getInviteBranchName() {
        return inviteBranchName;
    }

    public void setInviteBranchName(String inviteBranchName) {
        this.inviteBranchName = inviteBranchName == null ? null : inviteBranchName.trim();
    }

    public Integer getInviteDepartmentId() {
        return inviteDepartmentId;
    }

    public void setInviteDepartmentId(Integer inviteDepartmentId) {
        this.inviteDepartmentId = inviteDepartmentId;
    }

    public String getInviteDepartmentName() {
        return inviteDepartmentName;
    }

    public void setInviteDepartmentName(String inviteDepartmentName) {
        this.inviteDepartmentName = inviteDepartmentName == null ? null : inviteDepartmentName.trim();
    }

    public Integer getTenderUserAttribute() {
        return tenderUserAttribute;
    }

    public void setTenderUserAttribute(Integer tenderUserAttribute) {
        this.tenderUserAttribute = tenderUserAttribute;
    }

    public Integer getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(Integer inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
    }

    public String getLoanBatchNo() {
        return loanBatchNo;
    }

    public void setLoanBatchNo(String loanBatchNo) {
        this.loanBatchNo = loanBatchNo == null ? null : loanBatchNo.trim();
    }

    public String getRepayBatchNo() {
        return repayBatchNo;
    }

    public void setRepayBatchNo(String repayBatchNo) {
        this.repayBatchNo = repayBatchNo == null ? null : repayBatchNo.trim();
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
}