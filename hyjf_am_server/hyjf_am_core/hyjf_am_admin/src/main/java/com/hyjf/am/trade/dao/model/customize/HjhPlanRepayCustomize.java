package com.hyjf.am.trade.dao.model.customize;

import com.hyjf.am.trade.dao.model.auto.HjhRepay;

import java.io.Serializable;

public class HjhPlanRepayCustomize extends HjhRepay implements Serializable {

    // 推荐人用户名（当前） 汇计划三期继续使用此字段 列表展示用
    private String recommendName;

    // 推荐人属性（当前）
    private String recommendAttr;

    /**
     * 推荐人部门信息(投资时)
     */
    private String inviteUserName;
    private String inviteUserAttributeName;
    private String inviteUserRegionName;
    private String inviteUserBranchName;
    private String inviteUserDepartmentName;
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
    private String lqdServiceFee;
    /*
     * 清算服务费率
     */
    private String lqdServiceApr;
    /*
     * 投资服务费率
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
    private Integer joinTime;
    /*
     * 订单锁定时间 = 加入计划的计息时间
     */
    private Integer orderLockTime;
    /*
     * 计划名称
     */
    private String planName;

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

    public String getLqdServiceFee() {
        return lqdServiceFee;
    }

    public void setLqdServiceFee(String lqdServiceFee) {
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

    public Integer getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Integer joinTime) {
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
