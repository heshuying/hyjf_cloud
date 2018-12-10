package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DebtPlanAccede implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 计划nid
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 用户属性
     *
     * @mbggenerated
     */
    private Integer userAttribute;

    /**
     * 计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

    /**
     * 加入时的冻结订单号
     *
     * @mbggenerated
     */
    private String freezeOrderId;

    /**
     * 计划加入总金额
     *
     * @mbggenerated
     */
    private BigDecimal accedeAccount;

    /**
     * 可用余额
     *
     * @mbggenerated
     */
    private BigDecimal accedeBalance;

    /**
     * 计划订单冻结金额（专指出借汇添金专属标的冻结）
     *
     * @mbggenerated
     */
    private BigDecimal accedeFrost;

    /**
     * 清算债权承接冻结的余额
     *
     * @mbggenerated
     */
    private BigDecimal liquidatesCreditFrost;

    /**
     * 清算进度
     *
     * @mbggenerated
     */
    private BigDecimal liquidatesApr;

    /**
     * 清算还款冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal liquidatesRepayFrost;

    /**
     * 服务费收取比例
     *
     * @mbggenerated
     */
    private BigDecimal serviceFeeRate;

    /**
     * 服务费
     *
     * @mbggenerated
     */
    private BigDecimal serviceFee;

    /**
     * 到期公允价值
     *
     * @mbggenerated
     */
    private BigDecimal expireFairValue;

    /**
     * 承接次数
     *
     * @mbggenerated
     */
    private Integer underTakeTimes;

    /**
     * 最大出借金额（参考值）
     *
     * @mbggenerated
     */
    private BigDecimal investMax;

    /**
     * 最小出借金额（参考值）
     *
     * @mbggenerated
     */
    private BigDecimal investMin;

    /**
     * 遍历次数（已经尝试自动出借次数）
     *
     * @mbggenerated
     */
    private Integer cycleTimes;

    /**
     * 应还本息
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
     * 待还本息
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
     * 已还本息
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
     * 协议发送状态0未发送 1已发送
     *
     * @mbggenerated
     */
    private Integer sendStatus;

    /**
     * 是否需要计算到期公允价值状态(0:是,1否)
     *
     * @mbggenerated
     */
    private Integer calculationStatus;

    /**
     * 此笔加入是否已经完成 0出借中 1出借完成 2还款中 3还款完成
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 是否复投 0非复投 1复投
     *
     * @mbggenerated
     */
    private Integer reinvestStatus;

    /**
     * 是否有其他项目在还款中 0否 1是
     *
     * @mbggenerated
     */
    private Integer repayRunningStatus;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 客户端 0PC，1微信，2安卓APP，3IOS，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建者id
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建者用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 更新用户id
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * 还款推荐人id
     *
     * @mbggenerated
     */
    private Integer inviteRepayUserId;

    /**
     * 还款推荐人属性
     *
     * @mbggenerated
     */
    private Integer inviteRepayUserAttribute;

    /**
     * 推荐人用户名
     *
     * @mbggenerated
     */
    private String inviteRepayUserName;

    /**
     * 还款推荐人部门id
     *
     * @mbggenerated
     */
    private Integer inviteRepayRegionId;

    /**
     * 还款推荐人部门名
     *
     * @mbggenerated
     */
    private String inviteRepayRegionName;

    /**
     * 还款推荐人分部id
     *
     * @mbggenerated
     */
    private Integer inviteRepayBranchId;

    /**
     * 还款推荐人分部名
     *
     * @mbggenerated
     */
    private String inviteRepayBranchName;

    /**
     * 还款推荐人所属部门id
     *
     * @mbggenerated
     */
    private Integer inviteRepayDepartmentId;

    /**
     * 还款推荐人所属部门名
     *
     * @mbggenerated
     */
    private String inviteRepayDepartmentName;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
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

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
    }

    public String getFreezeOrderId() {
        return freezeOrderId;
    }

    public void setFreezeOrderId(String freezeOrderId) {
        this.freezeOrderId = freezeOrderId == null ? null : freezeOrderId.trim();
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public BigDecimal getAccedeBalance() {
        return accedeBalance;
    }

    public void setAccedeBalance(BigDecimal accedeBalance) {
        this.accedeBalance = accedeBalance;
    }

    public BigDecimal getAccedeFrost() {
        return accedeFrost;
    }

    public void setAccedeFrost(BigDecimal accedeFrost) {
        this.accedeFrost = accedeFrost;
    }

    public BigDecimal getLiquidatesCreditFrost() {
        return liquidatesCreditFrost;
    }

    public void setLiquidatesCreditFrost(BigDecimal liquidatesCreditFrost) {
        this.liquidatesCreditFrost = liquidatesCreditFrost;
    }

    public BigDecimal getLiquidatesApr() {
        return liquidatesApr;
    }

    public void setLiquidatesApr(BigDecimal liquidatesApr) {
        this.liquidatesApr = liquidatesApr;
    }

    public BigDecimal getLiquidatesRepayFrost() {
        return liquidatesRepayFrost;
    }

    public void setLiquidatesRepayFrost(BigDecimal liquidatesRepayFrost) {
        this.liquidatesRepayFrost = liquidatesRepayFrost;
    }

    public BigDecimal getServiceFeeRate() {
        return serviceFeeRate;
    }

    public void setServiceFeeRate(BigDecimal serviceFeeRate) {
        this.serviceFeeRate = serviceFeeRate;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getExpireFairValue() {
        return expireFairValue;
    }

    public void setExpireFairValue(BigDecimal expireFairValue) {
        this.expireFairValue = expireFairValue;
    }

    public Integer getUnderTakeTimes() {
        return underTakeTimes;
    }

    public void setUnderTakeTimes(Integer underTakeTimes) {
        this.underTakeTimes = underTakeTimes;
    }

    public BigDecimal getInvestMax() {
        return investMax;
    }

    public void setInvestMax(BigDecimal investMax) {
        this.investMax = investMax;
    }

    public BigDecimal getInvestMin() {
        return investMin;
    }

    public void setInvestMin(BigDecimal investMin) {
        this.investMin = investMin;
    }

    public Integer getCycleTimes() {
        return cycleTimes;
    }

    public void setCycleTimes(Integer cycleTimes) {
        this.cycleTimes = cycleTimes;
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

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getCalculationStatus() {
        return calculationStatus;
    }

    public void setCalculationStatus(Integer calculationStatus) {
        this.calculationStatus = calculationStatus;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getReinvestStatus() {
        return reinvestStatus;
    }

    public void setReinvestStatus(Integer reinvestStatus) {
        this.reinvestStatus = reinvestStatus;
    }

    public Integer getRepayRunningStatus() {
        return repayRunningStatus;
    }

    public void setRepayRunningStatus(Integer repayRunningStatus) {
        this.repayRunningStatus = repayRunningStatus;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getInviteRepayUserId() {
        return inviteRepayUserId;
    }

    public void setInviteRepayUserId(Integer inviteRepayUserId) {
        this.inviteRepayUserId = inviteRepayUserId;
    }

    public Integer getInviteRepayUserAttribute() {
        return inviteRepayUserAttribute;
    }

    public void setInviteRepayUserAttribute(Integer inviteRepayUserAttribute) {
        this.inviteRepayUserAttribute = inviteRepayUserAttribute;
    }

    public String getInviteRepayUserName() {
        return inviteRepayUserName;
    }

    public void setInviteRepayUserName(String inviteRepayUserName) {
        this.inviteRepayUserName = inviteRepayUserName == null ? null : inviteRepayUserName.trim();
    }

    public Integer getInviteRepayRegionId() {
        return inviteRepayRegionId;
    }

    public void setInviteRepayRegionId(Integer inviteRepayRegionId) {
        this.inviteRepayRegionId = inviteRepayRegionId;
    }

    public String getInviteRepayRegionName() {
        return inviteRepayRegionName;
    }

    public void setInviteRepayRegionName(String inviteRepayRegionName) {
        this.inviteRepayRegionName = inviteRepayRegionName == null ? null : inviteRepayRegionName.trim();
    }

    public Integer getInviteRepayBranchId() {
        return inviteRepayBranchId;
    }

    public void setInviteRepayBranchId(Integer inviteRepayBranchId) {
        this.inviteRepayBranchId = inviteRepayBranchId;
    }

    public String getInviteRepayBranchName() {
        return inviteRepayBranchName;
    }

    public void setInviteRepayBranchName(String inviteRepayBranchName) {
        this.inviteRepayBranchName = inviteRepayBranchName == null ? null : inviteRepayBranchName.trim();
    }

    public Integer getInviteRepayDepartmentId() {
        return inviteRepayDepartmentId;
    }

    public void setInviteRepayDepartmentId(Integer inviteRepayDepartmentId) {
        this.inviteRepayDepartmentId = inviteRepayDepartmentId;
    }

    public String getInviteRepayDepartmentName() {
        return inviteRepayDepartmentName;
    }

    public void setInviteRepayDepartmentName(String inviteRepayDepartmentName) {
        this.inviteRepayDepartmentName = inviteRepayDepartmentName == null ? null : inviteRepayDepartmentName.trim();
    }
}