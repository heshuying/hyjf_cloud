/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

/**
 * @author yaoyong
 * @version VipManageListCustomize, v0.1 2018/7/2 18:03
 */
public class VipManageListCustomize {
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

    /**
     * 构造方法不带参数
     */

//    public VIPManageListCustomize() {
//        super();
//    }

    /**
     * 获取用户id
     * userId
     * @return the userId
     */

    public String getUserId() {
        return userId;
    }

    /**
     * 设置用户id
     * @param userId the userId to set
     */

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * 获取用户名
     * userName
     * @return the userName
     */

    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     * @param userName the userName to set
     */

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户角色
     * userRole
     * @return the userRole
     */

    public String getUserRole() {
        return userRole;
    }

    /**
     * 设置用户角色
     * @param userRole the userRole to set
     */

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    /**
     * 获取用户属性
     * userProperty
     * @return the userProperty
     */

    public String getUserProperty() {
        return userProperty;
    }

    /**
     * 设置用户属性
     * @param userProperty the userProperty to set
     */

    public void setUserProperty(String userProperty) {
        this.userProperty = userProperty;
    }

    /**
     * 获取推荐人
     * recommendName
     * @return the recommendName
     */

    public String getRecommendName() {
        return recommendName;
    }

    /**
     * 设置推荐人
     * @param recommendName the recommendName to set
     */

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    /**
     * accountStatus
     * @return the accountStatus
     */

    public String getAccountStatus() {
        return accountStatus;
    }

    /**
     * @param accountStatus the accountStatus to set
     */

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }


    /**
     * registPlat
     * @return the registPlat
     */

    public String getRegistPlat() {
        return registPlat;
    }

    /**
     * @param registPlat the registPlat to set
     */

    public void setRegistPlat(String registPlat) {
        this.registPlat = registPlat;
    }

    /**
     * 获取用户注册时间
     * regTime
     * @return the regTime
     */

    public String getRegTime() {
        return regTime;
    }

    /**
     * 设置注册时间
     * @param regTime the regTime to set
     */

    public void setRegTime(String regTime) {
        this.regTime = regTime;
    }

    /**
     * is51
     * @return the is51
     */

    public String getIs51() {
        return is51;
    }

    /**
     * @param is51 the is51 to set
     */

    public void setIs51(String is51) {
        this.is51 = is51;
    }

    /**
     * userStatus
     * @return the userStatus
     */

    public String getUserStatus() {
        return userStatus;
    }

    /**
     * @param userStatus the userStatus to set
     */

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    /**
     * realName
     * @return the realName
     */

    public String getRealName() {
        return realName;
    }

    /**
     * @param realName the realName to set
     */

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * regionName
     * @return the regionName
     */

    public String getRegionName() {
        return regionName;
    }

    /**
     * @param regionName the regionName to set
     */

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    /**
     * branchName
     * @return the branchName
     */

    public String getBranchName() {
        return branchName;
    }

    /**
     * @param branchName the branchName to set
     */

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    /**
     * departmentName
     * @return the departmentName
     */

    public String getDepartmentName() {
        return departmentName;
    }

    /**
     * @param departmentName the departmentName to set
     */

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    /**
     * combotreeSrch
     * @return the combotreeSrch
     */

    public String getCombotreeSrch() {
        return combotreeSrch;
    }

    /**
     * @param combotreeSrch the combotreeSrch to set
     */

    public void setCombotreeSrch(String combotreeSrch) {
        this.combotreeSrch = combotreeSrch;
    }

    /**
     * combotreeListSrch
     * @return the combotreeListSrch
     */

    public String[] getCombotreeListSrch() {
        return combotreeListSrch;
    }

    /**
     * @param combotreeListSrch the combotreeListSrch to set
     */

    public void setCombotreeListSrch(String[] combotreeListSrch) {
        this.combotreeListSrch = combotreeListSrch;
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

    public String getVipAddTime() {
        return vipAddTime;
    }

    public void setVipAddTime(String vipAddTime) {
        this.vipAddTime = vipAddTime;
    }
}
