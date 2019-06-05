package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BorrowTender implements Serializable {
    private Integer id;

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
     * 借款人ID
     *
     * @mbggenerated
     */
    private Integer borrowUserId;

    /**
     * 借款人用户名
     *
     * @mbggenerated
     */
    private String borrowUserName;

    /**
     * 状态(0:初始,1:已放款,2:放款失败)
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 标的编号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 投资订单号
     *
     * @mbggenerated
     */
    private String nid;

    /**
     * 汇计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 投资人投标成功的授权号
     *
     * @mbggenerated
     */
    private String authCode;

    /**
     * 投资金额
     *
     * @mbggenerated
     */
    private BigDecimal account;

    /**
     * 满标状态(0:初始,1:满标)记录哪一笔是最后一笔满标
     *
     * @mbggenerated
     */
    private Integer recoverFullStatus;

    /**
     * 管理费
     *
     * @mbggenerated
     */
    private BigDecimal recoverFee;

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
     * create ip
     *
     * @mbggenerated
     */
    private String addIp;

    /**
     * 客户端0PC，1微信2安卓APP，3IOS APP，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 推荐人用户名(投资时)
     *
     * @mbggenerated
     */
    private String inviteUserName;

    /**
     * 推荐人用户id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteUserId;

    /**
     * 一级部门id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteRegionId;

    /**
     * 一级部门名称(投资时)
     *
     * @mbggenerated
     */
    private String inviteRegionName;

    /**
     * 二级部门id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteBranchId;

    /**
     * 二级部门名称(投资时)
     *
     * @mbggenerated
     */
    private String inviteBranchName;

    /**
     * 三级部门id(投资时)
     *
     * @mbggenerated
     */
    private Integer inviteDepartmentId;

    /**
     * 三级部门名称(投资时)
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
     * 投资订单日期
     *
     * @mbggenerated
     */
    private String orderDate;

    /**
     * 放款订单日期
     *
     * @mbggenerated
     */
    private String loanOrderDate;

    /**
     * 放款订单号
     *
     * @mbggenerated
     */
    private String loanOrdid;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 投资类型 0手动投标 1预约投标 2自动投标
     *
     * @mbggenerated
     */
    private Integer investType;

    /**
     * 0投资1复投
     *
     * @mbggenerated
     */
    private Integer tenderType;

    /**
     * 投资来源:默认-hyjf,wrb-风车理财
     *
     * @mbggenerated
     */
    private String tenderFrom;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 出借人渠道来源（出借时）
     *
     * @mbggenerated
     */
    private Integer tenderUserUtmId;

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

    public Integer getBorrowUserId() {
        return borrowUserId;
    }

    public void setBorrowUserId(Integer borrowUserId) {
        this.borrowUserId = borrowUserId;
    }

    public String getBorrowUserName() {
        return borrowUserName;
    }

    public void setBorrowUserName(String borrowUserName) {
        this.borrowUserName = borrowUserName == null ? null : borrowUserName.trim();
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

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public BigDecimal getAccount() {
        return account;
    }

    public void setAccount(BigDecimal account) {
        this.account = account;
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

    public String getAddIp() {
        return addIp;
    }

    public void setAddIp(String addIp) {
        this.addIp = addIp == null ? null : addIp.trim();
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
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

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate == null ? null : orderDate.trim();
    }

    public String getLoanOrderDate() {
        return loanOrderDate;
    }

    public void setLoanOrderDate(String loanOrderDate) {
        this.loanOrderDate = loanOrderDate == null ? null : loanOrderDate.trim();
    }

    public String getLoanOrdid() {
        return loanOrdid;
    }

    public void setLoanOrdid(String loanOrdid) {
        this.loanOrdid = loanOrdid == null ? null : loanOrdid.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getInvestType() {
        return investType;
    }

    public void setInvestType(Integer investType) {
        this.investType = investType;
    }

    public Integer getTenderType() {
        return tenderType;
    }

    public void setTenderType(Integer tenderType) {
        this.tenderType = tenderType;
    }

    public String getTenderFrom() {
        return tenderFrom;
    }

    public void setTenderFrom(String tenderFrom) {
        this.tenderFrom = tenderFrom == null ? null : tenderFrom.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getTenderUserUtmId() {
        return tenderUserUtmId;
    }

    public void setTenderUserUtmId(Integer tenderUserUtmId) {
        this.tenderUserUtmId = tenderUserUtmId;
    }
}