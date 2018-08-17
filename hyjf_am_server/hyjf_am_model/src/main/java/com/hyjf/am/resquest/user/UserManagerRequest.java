/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import com.hyjf.am.vo.BasePage;

/**
 * @author NXL
 * @version UserMemberParamVO, v0.1 2018/6/19 17:41
 *          会员中心->会员管理(请求参数）
 */
public class UserManagerRequest extends BasePage {
    //注册开始时间
    public String regTimeStart;
    // 注册结束时间
    public String regTimeEnd;
    // 用户名
    public String userName;
    // 真是姓名
    public String realName;
    // 手机号
    public String mobile;
    // 推荐人
    public String recommendName;
    // 用户角色
    public String userRole;
    // 用户类型
    public String userType;
    // 用户属性
    public String userProperty;
    // 银行开户状态
    public String accountStatus;
    // 用户状态
    public String userStatus;
    // 部门
    public String combotreeListSrch;
    // 客户编号
    public String customerId;
    // 用户来源
    public String instCodeSrch;
    //默认为true ,获取全部数据，为false时，获取部分数据
    public boolean limitFlg = false;
    //
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

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserProperty() {
        return userProperty;
    }

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public String getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public boolean isLimitFlg() {
        return limitFlg;
    }

    public void setLimitFlg(boolean limitFlg) {
        this.limitFlg = limitFlg;
    }
}
