/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade.hjh;

/**
 * @author cui
 * @version HjhPlanAccedeCustomizeVO, v0.1 2019/6/18 16:55
 */
public class HjhPlanAccedeCustomizeVO {

    /**
     * 加入订单号
     */
    private String planOrderId;
    /**
     * 计划编号
     */
    private String debtPlanNid;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 用户属性
     */
    private String userAttribute;

    /**
     * 推荐人
     */
    private String refereeUserName;

    /**
     * 推荐人用户属性
     */
    private String recommendAttr;

    /**
     * 加入金额
     */
    private String accedeAccount;

    /**
     * 加入时间
     */
    private String createTime;

    /**
     * 推荐人部门信息
     */
    private String inviteUserRegionname;
    private String inviteUserBranchname;
    private String inviteUserDepartmentname;

    //出借时渠道
    private String utmName;

    //当前渠道
    private String utmNameNow;

    public String getPlanOrderId() {
        return planOrderId;
    }

    public void setPlanOrderId(String planOrderId) {
        this.planOrderId = planOrderId;
    }

    public String getDebtPlanNid() {
        return debtPlanNid;
    }

    public void setDebtPlanNid(String debtPlanNid) {
        this.debtPlanNid = debtPlanNid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAttribute() {
        return userAttribute;
    }

    public void setUserAttribute(String userAttribute) {
        this.userAttribute = userAttribute;
    }

    public String getRefereeUserName() {
        return refereeUserName;
    }

    public void setRefereeUserName(String refereeUserName) {
        this.refereeUserName = refereeUserName;
    }

    public String getRecommendAttr() {
        return recommendAttr;
    }

    public void setRecommendAttr(String recommendAttr) {
        this.recommendAttr = recommendAttr;
    }

    public String getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getInviteUserRegionname() {
        return inviteUserRegionname;
    }

    public void setInviteUserRegionname(String inviteUserRegionname) {
        this.inviteUserRegionname = inviteUserRegionname;
    }

    public String getInviteUserBranchname() {
        return inviteUserBranchname;
    }

    public void setInviteUserBranchname(String inviteUserBranchname) {
        this.inviteUserBranchname = inviteUserBranchname;
    }

    public String getInviteUserDepartmentname() {
        return inviteUserDepartmentname;
    }

    public void setInviteUserDepartmentname(String inviteUserDepartmentname) {
        this.inviteUserDepartmentname = inviteUserDepartmentname;
    }

    public String getUtmName() {
        return utmName;
    }

    public void setUtmName(String utmName) {
        this.utmName = utmName;
    }

    public String getUtmNameNow() {
        return utmNameNow;
    }

    public void setUtmNameNow(String utmNameNow) {
        this.utmNameNow = utmNameNow;
    }
}
