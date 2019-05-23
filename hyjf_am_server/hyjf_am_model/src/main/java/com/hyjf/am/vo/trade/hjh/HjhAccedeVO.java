package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.resquest.trade.TenderRequest;
import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Description 汇计划加入明细表
 * @Author sunss
 * @Version v0.1
 * @Date 2018/6/20 15:48
 */
public class HjhAccedeVO extends BaseVO implements Serializable {
    private Integer id;

    private String accedeOrderId;

    private String planNid;

    private Integer userId;

    private String userName;

    private Integer userAttribute;

    private Integer inviteUserId;

    private String inviteUserName;

    private Integer inviteUserAttribute;

    private String inviteUserRegionname;

    private String inviteUserBranchname;

    private String inviteUserDepartmentname;

    private BigDecimal accedeAccount;

    private BigDecimal alreadyInvest;

    private Integer client;

    private Integer orderStatus;

    private Integer countInterestTime;

    private Integer sendStatus;

    private Integer lockPeriod;

    private Integer commissionStatus;

    private Integer commissionCountTime;

    private BigDecimal availableInvestAccount;

    private Date endDate;

    private Integer creditCompleteFlag;

    private BigDecimal frostAccount;

    private BigDecimal waitTotal;

    private BigDecimal waitCaptical;

    private BigDecimal waitInterest;

    private BigDecimal receivedTotal;

    private BigDecimal receivedInterest;

    private BigDecimal receivedCapital;

    private Integer quitTime;

    private Integer lastPaymentTime;

    private Integer acctualPaymentTime;

    private BigDecimal shouldPayTotal;

    private BigDecimal shouldPayCapital;

    private BigDecimal shouldPayInterest;

    private BigDecimal expectApr;

    private BigDecimal fairValue;

    private BigDecimal liquidationFairValue;

    private BigDecimal actualApr;

    private Integer investCounts;

    private Integer matchDates;

    private BigDecimal lqdServiceFee;

    private BigDecimal lqdServiceApr;

    private BigDecimal investServiceApr;

    private BigDecimal lqdProgress;

    private Integer createUser;

    private Integer updateUser;

    private Integer delFlag;

    private Date createTime;

    private Date updateTime;
    private TenderRequest request;
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

    public TenderRequest getRequest() {
        return request;
    }

    public void setRequest(TenderRequest request) {
        this.request = request;
    }

    public Integer getTenderUserUtmId() {
        return tenderUserUtmId;
    }

    public void setTenderUserUtmId(Integer tenderUserUtmId) {
        this.tenderUserUtmId = tenderUserUtmId;
    }
}