/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author PC-LIUSHOUYI
 * @version HjhRepayVO, v0.1 2018/6/26 10:02
 */
public class HjhRepayVO extends BaseVO implements Serializable {

    private static final long serialVersionUID = -4055933025341268189L;

    private Integer id;

    private String accedeOrderId;

    private String planNid;

    private Integer lockPeriod;

    private Integer userId;

    private String userName;

    private Integer userAttribute;

    private BigDecimal accedeAccount;

    private BigDecimal repayInterest;

    private BigDecimal repayCapital;

    private Integer repayStatus;

    private BigDecimal repayAlready;

    private BigDecimal repayWait;

    private BigDecimal repayShould;

    private BigDecimal repayActual;

    private Integer orderStatus;

    private String repayActualTime;

    private String repayShouldTime;

    private BigDecimal planRepayCapital;

    private BigDecimal planRepayInterest;

    private BigDecimal repayTotal;

    private BigDecimal planWaitCaptical;

    private BigDecimal planWaitInterest;

    private BigDecimal waitTotal;

    private BigDecimal serviceFee;

    private BigDecimal actualRevenue;

    private BigDecimal actualPayTotal;

    private Integer delFlag;

    private Integer createUser;

    private Integer updateUser;

    private String createTime;

    private String updateTime;

    // 推荐人用户名（当前） 汇计划三期继续使用此字段 列表展示用
    private String recommendName;

    // 推荐人属性（当前）
    private String recommendAttr;

    /**
     * 推荐人部门信息(出借时)
     */
    private String inviteUserName;
    private String inviteUserAttributeName;
    private String inviteUserRegionName;
    private String inviteUserBranchName;
    private String inviteUserDepartmentName;

    // 出借人人属性（当前）
    private String userCurrentAttribute;

    /**
     * 推荐人信息(当前)
     */
    private String inviteCurrentUserName;
    private String inviteUserCurrentRegionName;
    private String inviteUserCurrentBranchName;
    private String inviteUserCurrentDepartmentName;

    /*
     * 锁定期天月
     */
    private Integer isMonth;
    /*
     * 还款方式
     */
    private String borrowStyle;

    // 汇计划三期新增
    /*
     * 还款方式
     */
    private String expectApr;
    /*
     * 清算服务费（元）
     */
    private BigDecimal lqdServiceFee;
    /*
     * 清算服务费率
     */
    private String lqdServiceApr;
    /*
     * 出借服务费率
     */
    private String investServiceApr;
    /*
     * 清算进度
     */
    private String lqdProgress;
    /*
     * 推荐人用户名
     */
    private String inviteUser;

    /*
     * 最晚退出时间 = 清算时间(计划应还日期) + 3天
     */
    private String lastQuitTime;
    /*
     * 汇计划加入时间
     */
    private String joinTime;
    /*
     * 订单锁定时间 = 加入计划的计息时间
     */
    private Integer orderLockTime;
    /*
     * 计划名称
     */
    private String planName;

    private String utmSource1;
    private String utmSource2;

    
    public String getUtmSource1() {
		return utmSource1;
	}

	public void setUtmSource1(String utmSource1) {
		this.utmSource1 = utmSource1;
	}

	public String getUtmSource2() {
		return utmSource2;
	}

	public void setUtmSource2(String utmSource2) {
		this.utmSource2 = utmSource2;
	}
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
        this.accedeOrderId = accedeOrderId;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid;
    }

    public Integer getLockPeriod() {
        return lockPeriod;
    }

    public void setLockPeriod(Integer lockPeriod) {
        this.lockPeriod = lockPeriod;
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
        this.userName = userName;
    }

    public Integer getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(Integer userAttribute) {
        this.userAttribute = userAttribute;
    }

    public BigDecimal getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(BigDecimal accedeAccount) {
        this.accedeAccount = accedeAccount;
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

    public Integer getRepayStatus() {
        return repayStatus;
    }

    public void setRepayStatus(Integer repayStatus) {
        this.repayStatus = repayStatus;
    }

    public BigDecimal getRepayAlready() {
        return repayAlready;
    }

    public void setRepayAlready(BigDecimal repayAlready) {
        this.repayAlready = repayAlready;
    }

    public BigDecimal getRepayWait() {
        return repayWait;
    }

    public void setRepayWait(BigDecimal repayWait) {
        this.repayWait = repayWait;
    }

    public BigDecimal getRepayShould() {
        return repayShould;
    }

    public void setRepayShould(BigDecimal repayShould) {
        this.repayShould = repayShould;
    }

    public BigDecimal getRepayActual() {
        return repayActual;
    }

    public void setRepayActual(BigDecimal repayActual) {
        this.repayActual = repayActual;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getRepayActualTime() {
        return repayActualTime;
    }

    public void setRepayActualTime(String repayActualTime) {
        this.repayActualTime = repayActualTime;
    }

    public String getRepayShouldTime() {
        return repayShouldTime;
    }

    public void setRepayShouldTime(String repayShouldTime) {
        this.repayShouldTime = repayShouldTime;
    }

    public BigDecimal getPlanRepayCapital() {
        return planRepayCapital;
    }

    public void setPlanRepayCapital(BigDecimal planRepayCapital) {
        this.planRepayCapital = planRepayCapital;
    }

    public BigDecimal getPlanRepayInterest() {
        return planRepayInterest;
    }

    public void setPlanRepayInterest(BigDecimal planRepayInterest) {
        this.planRepayInterest = planRepayInterest;
    }

    public BigDecimal getRepayTotal() {
        return repayTotal;
    }

    public void setRepayTotal(BigDecimal repayTotal) {
        this.repayTotal = repayTotal;
    }

    public BigDecimal getPlanWaitCaptical() {
        return planWaitCaptical;
    }

    public void setPlanWaitCaptical(BigDecimal planWaitCaptical) {
        this.planWaitCaptical = planWaitCaptical;
    }

    public BigDecimal getPlanWaitInterest() {
        return planWaitInterest;
    }

    public void setPlanWaitInterest(BigDecimal planWaitInterest) {
        this.planWaitInterest = planWaitInterest;
    }

    public BigDecimal getWaitTotal() {
        return waitTotal;
    }

    public void setWaitTotal(BigDecimal waitTotal) {
        this.waitTotal = waitTotal;
    }

    public BigDecimal getServiceFee() {
        return serviceFee;
    }

    public void setServiceFee(BigDecimal serviceFee) {
        this.serviceFee = serviceFee;
    }

    public BigDecimal getActualRevenue() {
        return actualRevenue;
    }

    public void setActualRevenue(BigDecimal actualRevenue) {
        this.actualRevenue = actualRevenue;
    }

    public BigDecimal getActualPayTotal() {
        return actualPayTotal;
    }

    public void setActualPayTotal(BigDecimal actualPayTotal) {
        this.actualPayTotal = actualPayTotal;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getRecommendAttr() {
        return recommendAttr;
    }

    public void setRecommendAttr(String recommendAttr) {
        this.recommendAttr = recommendAttr;
    }

    public String getInviteUserName() {
        return inviteUserName;
    }

    public void setInviteUserName(String inviteUserName) {
        this.inviteUserName = inviteUserName;
    }

    public String getInviteUserAttributeName() {
        return inviteUserAttributeName;
    }

    public void setInviteUserAttributeName(String inviteUserAttributeName) {
        this.inviteUserAttributeName = inviteUserAttributeName;
    }

    public String getInviteUserRegionName() {
        return inviteUserRegionName;
    }

    public void setInviteUserRegionName(String inviteUserRegionName) {
        this.inviteUserRegionName = inviteUserRegionName;
    }

    public String getInviteUserBranchName() {
        return inviteUserBranchName;
    }

    public void setInviteUserBranchName(String inviteUserBranchName) {
        this.inviteUserBranchName = inviteUserBranchName;
    }

    public String getInviteUserDepartmentName() {
        return inviteUserDepartmentName;
    }

    public void setInviteUserDepartmentName(String inviteUserDepartmentName) {
        this.inviteUserDepartmentName = inviteUserDepartmentName;
    }

    public String getUserCurrentAttribute() {
        return userCurrentAttribute;
    }

    public void setUserCurrentAttribute(String userCurrentAttribute) {
        this.userCurrentAttribute = userCurrentAttribute;
    }

    public String getInviteCurrentUserName() {
        return inviteCurrentUserName;
    }

    public void setInviteCurrentUserName(String inviteCurrentUserName) {
        this.inviteCurrentUserName = inviteCurrentUserName;
    }

    public String getInviteUserCurrentRegionName() {
        return inviteUserCurrentRegionName;
    }

    public void setInviteUserCurrentRegionName(String inviteUserCurrentRegionName) {
        this.inviteUserCurrentRegionName = inviteUserCurrentRegionName;
    }

    public String getInviteUserCurrentBranchName() {
        return inviteUserCurrentBranchName;
    }

    public void setInviteUserCurrentBranchName(String inviteUserCurrentBranchName) {
        this.inviteUserCurrentBranchName = inviteUserCurrentBranchName;
    }

    public String getInviteUserCurrentDepartmentName() {
        return inviteUserCurrentDepartmentName;
    }

    public void setInviteUserCurrentDepartmentName(String inviteUserCurrentDepartmentName) {
        this.inviteUserCurrentDepartmentName = inviteUserCurrentDepartmentName;
    }

    public Integer getIsMonth() {
        return isMonth;
    }

    public void setIsMonth(Integer isMonth) {
        this.isMonth = isMonth;
    }

    public String getBorrowStyle() {
        return borrowStyle;
    }

    public void setBorrowStyle(String borrowStyle) {
        this.borrowStyle = borrowStyle;
    }

    public String getExpectApr() {
        return expectApr;
    }

    public void setExpectApr(String expectApr) {
        this.expectApr = expectApr;
    }

    public BigDecimal getLqdServiceFee() {
        return lqdServiceFee;
    }

    public void setLqdServiceFee(BigDecimal lqdServiceFee) {
        this.lqdServiceFee = lqdServiceFee;
    }

    public String getLqdServiceApr() {
        return lqdServiceApr;
    }

    public void setLqdServiceApr(String lqdServiceApr) {
        this.lqdServiceApr = lqdServiceApr;
    }

    public String getInvestServiceApr() {
        return investServiceApr;
    }

    public void setInvestServiceApr(String investServiceApr) {
        this.investServiceApr = investServiceApr;
    }

    public String getLqdProgress() {
        return lqdProgress;
    }

    public void setLqdProgress(String lqdProgress) {
        this.lqdProgress = lqdProgress;
    }

    public String getInviteUser() {
        return inviteUser;
    }

    public void setInviteUser(String inviteUser) {
        this.inviteUser = inviteUser;
    }

    public String getLastQuitTime() {
        return lastQuitTime;
    }

    public void setLastQuitTime(String lastQuitTime) {
        this.lastQuitTime = lastQuitTime;
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime;
    }

    public Integer getOrderLockTime() {
        return orderLockTime;
    }

    public void setOrderLockTime(Integer orderLockTime) {
        this.orderLockTime = orderLockTime;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }
}
