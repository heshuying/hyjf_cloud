/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.admin.beans.vo.DropDownVO;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

/**
 * @author nxl
 * @version UserManagerInitResponseBean, v0.1 2018/7/19 18:15
 */
public class UserParamExceptInitResponseBean {
    // 用户角色
    @ApiModelProperty(value = "用户角色")
    List<DropDownVO> userRoles;
    // 用户属性
    @ApiModelProperty(value = "用户属性")
    List<DropDownVO> userPropertys;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    List<DropDownVO> accountStatus;
    // 用户状态
    @ApiModelProperty(value = "用户状态")
    List<DropDownVO> userStatus;

    public List<DropDownVO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<DropDownVO> userRoles) {
        this.userRoles = userRoles;
    }

    public List<DropDownVO> getUserPropertys() {
        return userPropertys;
    }

    public void setUserPropertys(List<DropDownVO> userPropertys) {
        this.userPropertys = userPropertys;
    }

    public List<DropDownVO> getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(List<DropDownVO> accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<DropDownVO> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(List<DropDownVO> userStatus) {
        this.userStatus = userStatus;
    }
}
