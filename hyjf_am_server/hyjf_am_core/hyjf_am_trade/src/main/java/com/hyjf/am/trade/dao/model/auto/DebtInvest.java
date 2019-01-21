package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtInvest implements Serializable {
    private Integer id;

    /**
     * 用户userId
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 投资用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 投资用户属性
     *
     * @mbggenerated
     */
    private Integer userAttribute;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 计划订单号
     *
     * @mbggenerated
     */
    private String planOrderId;

    /**
     * 项目编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 投资订单日期
     *
     * @mbggenerated
     */
    private String orderDate;

    /**
     * 冻结标识
     *
     * @mbggenerated
     */
    private String trxId;

    /**
     * 投资金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    /**
     * 放款金额
     *
     * @mbggenerated
     */
    private BigDecimal loanAmount;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal loanFee;

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
     * 投资类型 0手动投标 1预约投标 2自动投标
     *
     * @mbggenerated
     */
    private Integer investType;

    /**
     * 已还款次数
     *
     * @mbggenerated
     */
    private Integer repayTimes;

    /**
     * 推荐人用户userId
     *
     * @mbggenerated
     */
    private Integer inviteUserId;

    /**
     * 推荐人用户名
     *
     * @mbggenerated
     */
    private String inviteUserName;

    /**
     * 推荐人用户属性
     *
     * @mbggenerated
     */
    private Integer inviteUserAttribute;

    private Integer inviteRegionId;

    private String inviteRegionName;

    private Integer inviteBranchId;

    private String inviteBranchName;

    private Integer inviteDepartmentId;

    private String inviteDepartmentName;

    /**
     * 放款订单号
     *
     * @mbggenerated
     */
    private String loanOrderId;

    /**
     * 放款日期
     *
     * @mbggenerated
     */
    private String loanOrderDate;

    /**
     * 投资状态 0冻结成功 1放款成功 2还款中 3还款成功
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 网站收支计算标识
     *
     * @mbggenerated
     */
    private Integer web;

    /**
     * 客户端0PC，1微信2安卓APP，3IOS APP，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

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
     * 提前还款费用
     *
     * @mbggenerated
     */
    private BigDecimal recoverAdvanceFee;

    /**
     * 逾期还款费用
     *
     * @mbggenerated
     */
    private BigDecimal recoverLateFee;

    /**
     * 投资奖励增加
     *
     * @mbggenerated
     */
    private BigDecimal tenderAwardFee;

    private String contents;

    /**
     * 是否已回收完成
     *
     * @mbggenerated
     */
    private Integer recoverFullStatus;

    /**
     * 放款服务费
     *
     * @mbggenerated
     */
    private BigDecimal recoverFee;

    /**
     * 回收类型
     *
     * @mbggenerated
     */
    private String recoverType;

    /**
     * 债权转让
     *
     * @mbggenerated
     */
    private Integer changeStatus;

    /**
     * 债权转让期数
     *
     * @mbggenerated
     */
    private Integer changePeriod;

    /**
     * 转让人
     *
     * @mbggenerated
     */
    private Integer changeUserid;

    /**
     * 投资奖励金额
     *
     * @mbggenerated
     */
    private BigDecimal tenderAwardAccount;

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

    public Integer getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(Integer userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
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

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId == null ? null : trxId.trim();
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public BigDecimal getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(BigDecimal loanAmount) {
        this.loanAmount = loanAmount;
    }

    public BigDecimal getLoanFee() {
        return loanFee;
    }

    public void setLoanFee(BigDecimal loanFee) {
        this.loanFee = loanFee;
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

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }

    public Integer getRepayTimes() {
        return repayTimes;
    }

    public void setRepayTimes(Integer repayTimes) {
        this.repayTimes = repayTimes;
    }

    public Integer getInviteUserId() {
        return inviteUserId;
    }

    public void setInviteUserId(Integer inviteUserId) {
        this.inviteUserId = inviteUserId;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName == null ? null : inviteUserName.trim();
    }

    public Integer getInviteUserAttribute() {
        return inviteUserAttribute;
    }

    public void setInviteUserAttribute(Integer inviteUserAttribute) {
        this.inviteUserAttribute = inviteUserAttribute;
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

    public String getLoanOrderId() {
        return loanOrderId;
    }

    public void setLoanOrderId(String loanOrderId) {
        this.loanOrderId = loanOrderId == null ? null : loanOrderId.trim();
    }

    public String getLoanOrderDate() {
        return loanOrderDate;
    }

    public void setLoanOrderDate(String loanOrderDate) {
        this.loanOrderDate = loanOrderDate == null ? null : loanOrderDate.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
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

    public BigDecimal getRecoverAdvanceFee() {
        return recoverAdvanceFee;
    }

    public void setRecoverAdvanceFee(BigDecimal recoverAdvanceFee) {
        this.recoverAdvanceFee = recoverAdvanceFee;
    }

    public BigDecimal getRecoverLateFee() {
        return recoverLateFee;
    }

    public void setRecoverLateFee(BigDecimal recoverLateFee) {
        this.recoverLateFee = recoverLateFee;
    }

    public BigDecimal getTenderAwardFee() {
        return tenderAwardFee;
    }

    public void setTenderAwardFee(BigDecimal tenderAwardFee) {
        this.tenderAwardFee = tenderAwardFee;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Integer getRecoverFullStatus() {
        return recoverFullStatus;
    }

    public void setRecoverFullStatus(Integer recoverFullStatus) {
        this.recoverFullStatus = recoverFullStatus;
    }

    public BigDecimal getRecoverFee() {
        return recoverFee;
    }

    public void setRecoverFee(BigDecimal recoverFee) {
        this.recoverFee = recoverFee;
    }

    public String getRecoverType() {
        return recoverType;
    }

    public void setRecoverType(String recoverType) {
        this.recoverType = recoverType == null ? null : recoverType.trim();
    }

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
    }

    public Integer getChangePeriod() {
        return changePeriod;
    }

    public void setChangePeriod(Integer changePeriod) {
        this.changePeriod = changePeriod;
    }

    public Integer getChangeUserid() {
        return changeUserid;
    }

    public void setChangeUserid(Integer changeUserid) {
        this.changeUserid = changeUserid;
    }

    public BigDecimal getTenderAwardAccount() {
        return tenderAwardAccount;
    }

    public void setTenderAwardAccount(BigDecimal tenderAwardAccount) {
        this.tenderAwardAccount = tenderAwardAccount;
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