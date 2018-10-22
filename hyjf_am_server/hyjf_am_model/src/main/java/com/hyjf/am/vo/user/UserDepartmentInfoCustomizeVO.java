/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author liuyang
 * @version UserDepartmentInfoCustomizeVo, v0.1 2018/10/19 10:59
 */
public class UserDepartmentInfoCustomizeVO extends BaseVO implements Serializable {
    private static final long serialVersionUID = -40209930118071625L;

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
