package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowTenderCpn implements Serializable {
    private Integer id;

    /**
     * 用户名称
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 状态(0:初始,1:已放款,2:放款失败)
     *
     * @mbggenerated
     */
    private Integer status;

    private String borrowNid;

    /**
     * 出借订单号
     *
     * @mbggenerated
     */
    private String nid;

    private BigDecimal accountTender;

    private BigDecimal account;

    /**
     * 债权转让
     *
     * @mbggenerated
     */
    private Integer changeStatus;

    /**
     * 转让人
     *
     * @mbggenerated
     */
    private Integer changeUserid;

    /**
     * 债权转让期数
     *
     * @mbggenerated
     */
    private Integer changePeriod;

    /**
     * 出借状态
     *
     * @mbggenerated
     */
    private Integer tenderStatus;

    private String tenderNid;

    /**
     * 出借奖励金额
     *
     * @mbggenerated
     */
    private BigDecimal tenderAwardAccount;

    /**
     * 满标状态(0:初始,1:满标)记录哪一笔是最后一笔满标
     *
     * @mbggenerated
     */
    private Integer recoverFullStatus;

    /**
     * 回收的总费用
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
     * 收款总额
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountAll;

    /**
     * 收款总利息
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountInterest;

    /**
     * 已收总额
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountYes;

    /**
     * 已收利息
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountInterestYes;

    /**
     * 已收本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountCapitalYes;

    /**
     * 待收总额
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountWait;

    /**
     * 待收利息
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountInterestWait;

    /**
     * 待收本金
     *
     * @mbggenerated
     */
    private BigDecimal recoverAccountCapitalWait;

    /**
     * 已收期数
     *
     * @mbggenerated
     */
    private Integer recoverTimes;

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
     * 出借奖励增加
     *
     * @mbggenerated
     */
    private BigDecimal tenderAwardFee;

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

    private String contents;

    /**
     * 自动投标
     *
     * @mbggenerated
     */
    private Integer autoStatus;

    /**
     * 网站投标
     *
     * @mbggenerated
     */
    private Integer webStatus;

    /**
     * 放款状态
     *
     * @mbggenerated
     */
    private Integer apiStatus;

    /**
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    private Integer periodStatus;

    private Integer isok;

    private Integer isReport;

    /**
     * 专用标记
     *
     * @mbggenerated
     */
    private Integer flag;

    /**
     * 活动专用标志
     *
     * @mbggenerated
     */
    private Integer activityFlag;

    /**
     * 客户端0PC，1微信2安卓APP，3IOS APP，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 网站收支计算标识
     *
     * @mbggenerated
     */
    private Integer web;

    private String inviteUserName;

    private Integer inviteBranchId;

    private String inviteBranchName;

    private Integer inviteDepartmentId;

    private String inviteDepartmentName;

    private Integer tenderUserAttribute;

    private Integer inviteUserAttribute;

    private String orderDate;

    private String loanOrdid;

    private Integer inviteUserId;

    private Integer inviteRegionId;

    private String inviteRegionName;

    /**
     * 出借人username
     *
     * @mbggenerated
     */
    private String tenderUserName;

    /**
     * 出借类别 1：直投类，2：汇添金
     *
     * @mbggenerated
     */
    private Integer tenderType;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public BigDecimal getAccountTender() {
        return accountTender;
    }

    public void setAccountTender(BigDecimal accountTender) {
        this.accountTender = accountTender;
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
    }

    public Integer getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(Integer changeStatus) {
        this.changeStatus = changeStatus;
    }

    public Integer getChangeUserid() {
        return changeUserid;
    }

    public void setChangeUserid(Integer changeUserid) {
        this.changeUserid = changeUserid;
    }

    public Integer getChangePeriod() {
        return changePeriod;
    }

    public void setChangePeriod(Integer changePeriod) {
        this.changePeriod = changePeriod;
    }

    public Integer getTenderStatus() {
        return tenderStatus;
    }

    public void setTenderStatus(Integer tenderStatus) {
        this.tenderStatus = tenderStatus;
    }

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid == null ? null : tenderNid.trim();
    }

    public BigDecimal getTenderAwardAccount() {
        return tenderAwardAccount;
    }

    public void setTenderAwardAccount(BigDecimal tenderAwardAccount) {
        this.tenderAwardAccount = tenderAwardAccount;
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

    public BigDecimal getRecoverAccountAll() {
        return recoverAccountAll;
    }

    public void setRecoverAccountAll(BigDecimal recoverAccountAll) {
        this.recoverAccountAll = recoverAccountAll;
    }

    public BigDecimal getRecoverAccountInterest() {
        return recoverAccountInterest;
    }

    public void setRecoverAccountInterest(BigDecimal recoverAccountInterest) {
        this.recoverAccountInterest = recoverAccountInterest;
    }

    public BigDecimal getRecoverAccountYes() {
        return recoverAccountYes;
    }

    public void setRecoverAccountYes(BigDecimal recoverAccountYes) {
        this.recoverAccountYes = recoverAccountYes;
    }

    public BigDecimal getRecoverAccountInterestYes() {
        return recoverAccountInterestYes;
    }

    public void setRecoverAccountInterestYes(BigDecimal recoverAccountInterestYes) {
        this.recoverAccountInterestYes = recoverAccountInterestYes;
    }

    public BigDecimal getRecoverAccountCapitalYes() {
        return recoverAccountCapitalYes;
    }

    public void setRecoverAccountCapitalYes(BigDecimal recoverAccountCapitalYes) {
        this.recoverAccountCapitalYes = recoverAccountCapitalYes;
    }

    public BigDecimal getRecoverAccountWait() {
        return recoverAccountWait;
    }

    public void setRecoverAccountWait(BigDecimal recoverAccountWait) {
        this.recoverAccountWait = recoverAccountWait;
    }

    public BigDecimal getRecoverAccountInterestWait() {
        return recoverAccountInterestWait;
    }

    public void setRecoverAccountInterestWait(BigDecimal recoverAccountInterestWait) {
        this.recoverAccountInterestWait = recoverAccountInterestWait;
    }

    public BigDecimal getRecoverAccountCapitalWait() {
        return recoverAccountCapitalWait;
    }

    public void setRecoverAccountCapitalWait(BigDecimal recoverAccountCapitalWait) {
        this.recoverAccountCapitalWait = recoverAccountCapitalWait;
    }

    public Integer getRecoverTimes() {
        return recoverTimes;
    }

    public void setRecoverTimes(Integer recoverTimes) {
        this.recoverTimes = recoverTimes;
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

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents == null ? null : contents.trim();
    }

    public Integer getAutoStatus() {
        return autoStatus;
    }

    public void setAutoStatus(Integer autoStatus) {
        this.autoStatus = autoStatus;
    }

    public Integer getWebStatus() {
        return webStatus;
    }

    public void setWebStatus(Integer webStatus) {
        this.webStatus = webStatus;
    }

    public Integer getApiStatus() {
        return apiStatus;
    }

    public void setApiStatus(Integer apiStatus) {
        this.apiStatus = apiStatus;
    }

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getPeriodStatus() {
        return periodStatus;
    }

    public void setPeriodStatus(Integer periodStatus) {
        this.periodStatus = periodStatus;
    }

    public Integer getIsok() {
        return isok;
    }

    public void setIsok(Integer isok) {
        this.isok = isok;
    }

    public Integer getIsReport() {
        return isReport;
    }

    public void setIsReport(Integer isReport) {
        this.isReport = isReport;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getActivityFlag() {
        return activityFlag;
    }

    public void setActivityFlag(Integer activityFlag) {
        this.activityFlag = activityFlag;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getWeb() {
        return web;
    }

    public void setWeb(Integer web) {
        this.web = web;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName == null ? null : inviteUserName.trim();
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid == null ? null : loanOrdid.trim();
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

    public String getTenderUserName() {
        return tenderUserName;
    }

    public void setTenderUserName(String tenderUserName) {
        this.tenderUserName = tenderUserName == null ? null : tenderUserName.trim();
    }

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}