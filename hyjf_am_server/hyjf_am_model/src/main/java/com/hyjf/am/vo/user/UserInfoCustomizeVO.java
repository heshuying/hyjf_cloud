package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class UserInfoCustomizeVO extends BaseVO implements Serializable{

    private static final long serialVersionUID = 4090228100052120725L;

    private Integer userId;

    private String userName;

    private String trueName;

    private String mobile;

    private String sex;
    
    private Integer regionId;

    private Integer branchId;

    private Integer departmentId;

    private String regionName;

    private String branchName;

    private String departmentName;

    private Integer referrer;

    private Integer attribute;

    /**
     * 用户角色1投资人  2借款人 3垫付机构
     */
    private Integer roleId;
    /**
     * 用户角色1投资人  2借款人 3垫付机构
     */
    private String roleName;
    /**
     * 用户类型 0个人 1企业
     */
    private Integer userType;

    /**
     * 用户类型 0个人 1企业
     */
    private String userTypeName;
    /**
     * 合作机构编号
     */
    private String cooperateNum;
    /**
     * 江西银行电子 账户
     */
    private String account;
    /**
     * 是否开户 0 未开 1开
     */
    private String open;
    /**
     * 状态
     */
    private Integer status;

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

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
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

    public Integer getReferrer() {
        return referrer;
    }

    public void setReferrer(Integer referrer) {
        this.referrer = referrer;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public Integer getRegionId() {
        return regionId;
    }

    public void setRegionId(Integer regionId) {
        this.regionId = regionId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getCooperateNum() {
        return cooperateNum;
    }

    public void setCooperateNum(String cooperateNum) {
        this.cooperateNum = cooperateNum;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserTypeName() {
        return userTypeName;
    }

    public void setUserTypeName(String userTypeName) {
        this.userTypeName = userTypeName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
