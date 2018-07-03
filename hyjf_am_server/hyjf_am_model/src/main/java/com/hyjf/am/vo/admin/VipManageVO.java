/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version VipManageVO, v0.1 2018/7/2 16:25
 */
public class VipManageVO extends BaseVO implements Serializable {
    //用戶id
    public String userId;
    //用戶名
    public String userName;
    //真实姓名
    public String realName;
    //用户手机号
    public String mobile;
    //VIP等级名称
    public String vipName;
    //VIP累计V值
    public String vipValue;
    //VIP购买平台
    public String vipPlatform;

    //用戶角色
    public String userRole;
    //用戶属性
    public String userProperty;
    //推荐人名称
    public String recommendName;
    //是否51老用户
    public String is51;
    //开户状态
    public String accountStatus;
    //用户状态
    public String userStatus;
    //注册平台
    public String registPlat;
    //注册时间
    public String regTime;
    //vip购买时间
    public String vipAddTime;

    /** 大区 */
    private String regionName;
    /** 分公司 */
    private String branchName;
    /** 部门 */
    private String departmentName;
    /** 部门 */
    private String combotreeSrch;
    /** 部门 */
    private String[] combotreeListSrch;

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

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getVipValue() {
        return vipValue;
    }

    public void setVipValue(String vipValue) {
        this.vipValue = vipValue;
    }

    public String getVipPlatform() {
        return vipPlatform;
    }

    public void setVipPlatform(String vipPlatform) {
        this.vipPlatform = vipPlatform;
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

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getIs51() {
        return is51;
    }

    public void setIs51(String is51) {
        this.is51 = is51;
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

    public String getRegistPlat() {
        return registPlat;
    }

    public void setRegistPlat(String registPlat) {
        this.registPlat = registPlat;
    }

    public String getRegTime() {
        return regTime;
    }

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    public String getVipAddTime() {
        return vipAddTime;
    }

    public void setVipAddTime(String vipAddTime) {
        this.vipAddTime = vipAddTime;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCombotreeSrch() {
        return combotreeSrch;
    }

    public void setCombotreeSrch(String combotreeSrch) {
        this.combotreeSrch = combotreeSrch;
    }

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
    }
}
