/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author nxl
 * @version UserBankOpenAccountCustomize, v0.1 2018/6/21 21:38
 */
public class UserManagerUpdateVO extends BaseVO implements Serializable {

    /** 用户id */
    public String userId;
    /** 用户名 */
    public String userName;
    /** 用户邮箱 */
    public String email;
    /** 手机号 */
    public String mobile;
    /** 用户角色 */
    public String userRole;
    /** 修改说明*/
    public String remark;
    /** 用户状态 */
    public String status;
    /** 借款人类型*/
    public Integer borrowerType;

    public Integer getBorrowerType() {
        return borrowerType;
    }

    public void setBorrowerType(Integer borrowerType) {
        this.borrowerType = borrowerType;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
