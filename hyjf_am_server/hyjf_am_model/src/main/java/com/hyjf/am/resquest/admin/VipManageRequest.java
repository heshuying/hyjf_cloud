/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

/**
 * @author yaoyong
 * @version VipManageRequest, v0.1 2018/7/2 15:13
 */
public class VipManageRequest {
    /** 注册开始时间 */
    public String regTimeStart;
    /** 注册结束时间 */
    public String regTimeEnd;
    /** 用户名 */
    public String userName;
    /** 真实姓名 */
    public String realName;
    /** 用户手机号 */
    public String mobile;
    /** 推荐人名称 */
    public String recommendName;
    /** 部门 */
    public String combotreeListSrch;
    /** 用戶角色 */
    public String userRole;
    /** 用戶属性 */
    public String userProperty;
    /** 用户状态 */
    public String userStatus;
    /** 是否51老用户 */
    public String is51;

    public int limit;

    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public String getRegTimeStart() {
        return regTimeStart;
    }

    public void setRegTimeStart(String regTimeStart) {
        this.regTimeStart = regTimeStart;
    }

    public String getRegTimeEnd() {
        return regTimeEnd;
    }

    public void setRegTimeEnd(String regTimeEnd) {
        this.regTimeEnd = regTimeEnd;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getIs51() {
        return is51;
    }

    public void setIs51(String is51) {
        this.is51 = is51;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
