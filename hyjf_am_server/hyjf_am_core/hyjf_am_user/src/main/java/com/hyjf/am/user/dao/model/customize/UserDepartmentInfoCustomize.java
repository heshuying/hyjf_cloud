/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

import java.io.Serializable;

/**
 * 用户部门信息
 *
 * @author liuyang
 * @version UserDepartmentInfoCustomize, v0.1 2018/7/18 14:45
 */
public class UserDepartmentInfoCustomize implements Serializable {

    private static final long serialVersionUID = -2784688394380896046L;

    // 用户ID
    private Integer userId;
    // 用户名
    private String userName;
    // 大区
    private String regionName;
    // 分公司
    private String branchName;
    // 部门
    private String departmentName;

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
}
