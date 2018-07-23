/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.vo;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author nxl
 * @version UserMemberListVO, v0.1 2018/6/19 17:36
 * 会员中心 ->会员管理(列表）
 */
public class UserManagerCustomizeVO extends BaseVO implements Serializable {
    //用戶id
    @ApiModelProperty(value = "用戶id")
    public String userId;
    //用戶名
    @ApiModelProperty(value = "用戶名")
    public String userName;
    //真实姓名
    @ApiModelProperty(value = "真实姓名")
    public String realName;
    //用户手机号
    @ApiModelProperty(value = "用户手机号")
    public String mobile;
    //用戶角色
    @ApiModelProperty(value = "用戶角色")
    public String userRole;
    //用户类型
    @ApiModelProperty(value = "用户类型")
    public String userType;
    //用戶属性
    @ApiModelProperty(value = "用戶属性")
    public String userProperty;
    //推荐人名称
    @ApiModelProperty(value = "推荐人名称")
    public String recommendName;
    //开户状态
    @ApiModelProperty(value = "开户状态")
    public String accountStatus;
    //用户状态
    @ApiModelProperty(value = "用户状态")
    public String userStatus;
    //注册平台
    @ApiModelProperty(value = "注册平台")
    public String registPlat;
    //注册时间
    @ApiModelProperty(value = "注册时间")
    public String regTime;
    //借款人类型
    @ApiModelProperty(value = "借款人类型")
    public String borrowerType;
    // 性别
    @ApiModelProperty(value = "性别")
    public String sex;
    // 生日
    @ApiModelProperty(value = "生日")
    public String birthday;
    // 注册所在地
    @ApiModelProperty(value = "注册所在地")
    public String registArea;
    // 身份证号
    @ApiModelProperty(value = "身份证号")
    public String idcard;
    @ApiModelProperty(value = "开户行")
    private String openAccount;

    private String bankOpenAccount;

    private String bankOpenTime;

    //客户编号
    @ApiModelProperty(value = "客户编号")
    private String customerId;
    //电子账户
    @ApiModelProperty(value = "电子账户")
    private String account;

    /** 大区 */
    @ApiModelProperty(value = "大区")
    private String regionName;
    /** 分公司 */
    @ApiModelProperty(value = "分公司")
    private String branchName;
    /** 部门 */
    @ApiModelProperty(value = "部门")
    private String departmentName;
    /** 部门 */
    private String combotreeSrch;
    /** 部门 */
    private String[] combotreeListSrch;
    /** 用户来源code--查询用 */
    @ApiModelProperty(value = " 用户来源code--查询用")
    private String instCodeSrch;
    /** 用户来源code--展示用 */
    @ApiModelProperty(value = "用户来源code--展示用 ")
    private String instCode;
    /** 用户来源名称--展示用 */
    @ApiModelProperty(value = "用户来源名称--展示用")
    private String instName;

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

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
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

    public String getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(String borrowerType) {
        this.borrowerType = borrowerType;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRegistArea() {
        return registArea;
    }

    public void setRegistArea(String registArea) {
        this.registArea = registArea;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getOpenAccount() {
        return openAccount;
    }

    public void setOpenAccount(String openAccount) {
        this.openAccount = openAccount;
    }

    public String getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(String bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public String getBankOpenTime() {
        return bankOpenTime;
    }

    public void setBankOpenTime(String bankOpenTime) {
        this.bankOpenTime = bankOpenTime;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public String getInstCodeSrch() {
        return instCodeSrch;
    }

    public void setInstCodeSrch(String instCodeSrch) {
        this.instCodeSrch = instCodeSrch;
    }

    public String getInstCode() {
        return instCode;
    }

    public void setInstCode(String instCode) {
        this.instCode = instCode;
    }

    public String getInstName() {
        return instName;
    }

    public void setInstName(String instName) {
        this.instName = instName;
    }
}
