package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class HjhAccede implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 汇计划加入订单号
     *
     * @mbggenerated
     */
    private String accedeOrderId;

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
     * 推荐人用户属性（投资时）
     *
     * @mbggenerated
     */
    private Integer inviteUserAttribute;

    /**
     * 分公司(投资时)
     *
     * @mbggenerated
     */
    private String inviteUserRegionname;

    /**
     * 部门(投资时)
     *
     * @mbggenerated
     */
    private String inviteUserBranchname;

    /**
     * 团队(投资时)
     *
     * @mbggenerated
     */
    private String inviteUserDepartmentname;

    /**
     * 加入金额
     *
     * @mbggenerated
     */
    private BigDecimal accedeAccount;

    /**
     * 已投资金额
     *
     * @mbggenerated
     */
    private BigDecimal alreadyInvest;

    /**
     * 客户端 0PC，1微信，2安卓APP，3IOS，4其他
     *
     * @mbggenerated
     */
    private Integer client;

    /**
     *  0自动投标中 2自动投标成功 3锁定中 5退出中 7已退出 9自动投资异常
     *
     * @mbggenerated
     */
    private Integer orderStatus;

    /**
     * 计息时间
     *
     * @mbggenerated
     */
    private Integer countInterestTime;

    /**
     * 协议发送状态0未发送 1已发送
     *
     * @mbggenerated
     */
    private Integer sendStatus;

    /**
     * 锁定期
     *
     * @mbggenerated
     */
    private Integer lockPeriod;

    /**
     * 提成计算状态:0:未计算,1:已计算
     *
     * @mbggenerated
     */
    private Integer commissionStatus;

    /**
     * 提成计算时间
     *
     * @mbggenerated
     */
    private Integer commissionCountTime;

    /**
     * 可投金额
     *
     * @mbggenerated
     */
    private BigDecimal availableInvestAccount;

    /**
     * 计划订单结束日期(yyyyMMdd)
     *
     * @mbggenerated
     */
    private Date endDate;

    /**
     * 是否完成转让标识(0:未完成转让,1:已完成转让)
     *
     * @mbggenerated
     */
    private Integer creditCompleteFlag;

    /**
     * 冻结金额（待用）
     *
     * @mbggenerated
     */
    private BigDecimal frostAccount;

    /**
     * 待收总额
     *
     * @mbggenerated
     */
    private BigDecimal waitTotal;

    /**
     * 待收本金
     *
     * @mbggenerated
     */
    private BigDecimal waitCaptical;

    /**
     * 待收利息
     *
     * @mbggenerated
     */
    private BigDecimal waitInterest;

    /**
     * 已收总额
     *
     * @mbggenerated
     */
    private BigDecimal receivedTotal;

    /**
     * 已收利息
     *
     * @mbggenerated
     */
    private BigDecimal receivedInterest;

    /**
     * 已收本金
     *
     * @mbggenerated
     */
    private BigDecimal receivedCapital;

    /**
     * 退出时间
     *
     * @mbggenerated
     */
    private Integer quitTime;

    /**
     * 最后回款时间
     *
     * @mbggenerated
     */
    private Integer lastPaymentTime;

    /**
     * 实际回款时间
     *
     * @mbggenerated
     */
    private Integer acctualPaymentTime;

    /**
     * 应还总额
     *
     * @mbggenerated
     */
    private BigDecimal shouldPayTotal;

    /**
     * 应还本金
     *
     * @mbggenerated
     */
    private BigDecimal shouldPayCapital;

    /**
     * 应还利息
     *
     * @mbggenerated
     */
    private BigDecimal shouldPayInterest;

    /**
     * 加入时预期年化收益率(可能跟计划表不一致)
     *
     * @mbggenerated
     */
    private BigDecimal expectApr;

    /**
     * 计划订单当前持有债权(每一小时计算一次)
     *
     * @mbggenerated
     */
    private BigDecimal fairValue;

    /**
     * 计划订单清算时债权价值
     *
     * @mbggenerated
     */
    private BigDecimal liquidationFairValue;

    /**
     * 实际年化收益率
     *
     * @mbggenerated
     */
    private BigDecimal actualApr;

    /**
     * 投资笔数
     *
     * @mbggenerated
     */
    private Integer investCounts;

    /**
     * 匹配期
     *
     * @mbggenerated
     */
    private Integer matchDates;

    /**
     * 清算服务费(元)
     *
     * @mbggenerated
     */
    private BigDecimal lqdServiceFee;

    /**
     * 清算服务费率
     *
     * @mbggenerated
     */
    private BigDecimal lqdServiceApr;

    /**
     * 投资服务费率
     *
     * @mbggenerated
     */
    private BigDecimal investServiceApr;

    /**
     * 清算进度
     *
     * @mbggenerated
     */
    private BigDecimal lqdProgress;

    /**
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

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

    public String getAccedeOrderId() {
        return accedeOrderId;
    }

    public void setAccedeOrderId(String accedeOrderId) {
        this.accedeOrderId = accedeOrderId == null ? null : accedeOrderId.trim();
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

    public String getInviteUserRegionname() {
        return inviteUserRegionname;
    }

    public void setInviteUserRegionname(String inviteUserRegionname) {
        this.inviteUserRegionname = inviteUserRegionname == null ? null : inviteUserRegionname.trim();
    }

    public String getInviteUserBranchname() {
        return inviteUserBranchname;
    }

    public void setInviteUserBranchname(String inviteUserBranchname) {
        this.inviteUserBranchname = inviteUserBranchname == null ? null : inviteUserBranchname.trim();
    }

    public String getInviteUserDepartmentname() {
        return inviteUserDepartmentname;
    }

    public void setInviteUserDepartmentname(String inviteUserDepartmentname) {
        this.inviteUserDepartmentname = inviteUserDepartmentname == null ? null : inviteUserDepartmentname.trim();
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public BigDecimal getAlreadyInvest() {
        return alreadyInvest;
    }

    public void setAlreadyInvest(BigDecimal alreadyInvest) {
        this.alreadyInvest = alreadyInvest;
    }

    public Integer getClient() {
        return client;
    }

    public void setClient(Integer client) {
        this.client = client;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getCountInterestTime() {
        return countInterestTime;
    }

    public void setCountInterestTime(Integer countInterestTime) {
        this.countInterestTime = countInterestTime;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
    }

    public Integer getCommissionStatus() {
        return commissionStatus;
    }

    public void setCommissionStatus(Integer commissionStatus) {
        this.commissionStatus = commissionStatus;
    }

    public Integer getCommissionCountTime() {
        return commissionCountTime;
    }

    public void setCommissionCountTime(Integer commissionCountTime) {
        this.commissionCountTime = commissionCountTime;
    }

    public BigDecimal getAvailableInvestAccount() {
        return availableInvestAccount;
    }

    public void setAvailableInvestAccount(BigDecimal availableInvestAccount) {
        this.availableInvestAccount = availableInvestAccount;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getCreditCompleteFlag() {
        return creditCompleteFlag;
    }

    public void setCreditCompleteFlag(Integer creditCompleteFlag) {
        this.creditCompleteFlag = creditCompleteFlag;
    }

    public BigDecimal getFrostAccount() {
        return frostAccount;
    }

    public void setFrostAccount(BigDecimal frostAccount) {
        this.frostAccount = frostAccount;
    }

    public BigDecimal getWaitTotal() {
        return waitTotal;
    }

    public void setWaitTotal(BigDecimal waitTotal) {
        this.waitTotal = waitTotal;
    }

    public BigDecimal getWaitCaptical() {
        return waitCaptical;
    }

    public void setWaitCaptical(BigDecimal waitCaptical) {
        this.waitCaptical = waitCaptical;
    }

    public BigDecimal getWaitInterest() {
        return waitInterest;
    }

    public void setWaitInterest(BigDecimal waitInterest) {
        this.waitInterest = waitInterest;
    }

    public BigDecimal getReceivedTotal() {
        return receivedTotal;
    }

    public void setReceivedTotal(BigDecimal receivedTotal) {
        this.receivedTotal = receivedTotal;
    }

    public BigDecimal getReceivedInterest() {
        return receivedInterest;
    }

    public void setReceivedInterest(BigDecimal receivedInterest) {
        this.receivedInterest = receivedInterest;
    }

    public BigDecimal getReceivedCapital() {
        return receivedCapital;
    }

    public void setReceivedCapital(BigDecimal receivedCapital) {
        this.receivedCapital = receivedCapital;
    }

    public Integer getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(Integer quitTime) {
        this.quitTime = quitTime;
    }

    public Integer getLastPaymentTime() {
        return lastPaymentTime;
    }

    public void setLastPaymentTime(Integer lastPaymentTime) {
        this.lastPaymentTime = lastPaymentTime;
    }

    public Integer getAcctualPaymentTime() {
        return acctualPaymentTime;
    }

    public void setAcctualPaymentTime(Integer acctualPaymentTime) {
        this.acctualPaymentTime = acctualPaymentTime;
    }

    public BigDecimal getShouldPayTotal() {
        return shouldPayTotal;
    }

    public void setShouldPayTotal(BigDecimal shouldPayTotal) {
        this.shouldPayTotal = shouldPayTotal;
    }

    public BigDecimal getShouldPayCapital() {
        return shouldPayCapital;
    }

    public void setShouldPayCapital(BigDecimal shouldPayCapital) {
        this.shouldPayCapital = shouldPayCapital;
    }

    public BigDecimal getShouldPayInterest() {
        return shouldPayInterest;
    }

    public void setShouldPayInterest(BigDecimal shouldPayInterest) {
        this.shouldPayInterest = shouldPayInterest;
    }

    public BigDecimal getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(BigDecimal expectApr) {
        this.expectApr = expectApr;
    }

    public BigDecimal getFairValue() {
        return fairValue;
    }

    public void setFairValue(BigDecimal fairValue) {
        this.fairValue = fairValue;
    }

    public BigDecimal getLiquidationFairValue() {
        return liquidationFairValue;
    }

    public void setLiquidationFairValue(BigDecimal liquidationFairValue) {
        this.liquidationFairValue = liquidationFairValue;
    }

    public BigDecimal getActualApr() {
        return actualApr;
    }

    public void setActualApr(BigDecimal actualApr) {
        this.actualApr = actualApr;
    }

    public Integer getInvestCounts() {
        return investCounts;
    }

    public void setInvestCounts(Integer investCounts) {
        this.investCounts = investCounts;
    }

    public Integer getMatchDates() {
        return matchDates;
    }

    public void setMatchDates(Integer matchDates) {
        this.matchDates = matchDates;
    }

    public BigDecimal getLqdServiceFee() {
        return lqdServiceFee;
    }

    public void setLqdServiceFee(BigDecimal lqdServiceFee) {
        this.lqdServiceFee = lqdServiceFee;
    }

    public BigDecimal getLqdServiceApr() {
        return lqdServiceApr;
    }

    public void setLqdServiceApr(BigDecimal lqdServiceApr) {
        this.lqdServiceApr = lqdServiceApr;
    }

    public BigDecimal getInvestServiceApr() {
        return investServiceApr;
    }

    public void setInvestServiceApr(BigDecimal investServiceApr) {
        this.investServiceApr = investServiceApr;
    }

    public BigDecimal getLqdProgress() {
        return lqdProgress;
    }

    public void setLqdProgress(BigDecimal lqdProgress) {
        this.lqdProgress = lqdProgress;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public Integer getTenderUserUtmId() {
        return tenderUserUtmId;
    }

    public void setTenderUserUtmId(Integer tenderUserUtmId) {
        this.tenderUserUtmId = tenderUserUtmId;
    }
}