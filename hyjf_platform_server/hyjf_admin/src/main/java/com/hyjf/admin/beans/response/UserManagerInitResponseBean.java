/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.hyjf.am.vo.user.HjhInstConfigVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

/**
 * @author nxl
 * @version UserManagerInitResponseBean, v0.1 2018/7/19 18:15
 */
public class UserManagerInitResponseBean {
    // 用户角色
    @ApiModelProperty(value = "用户角色")
    private Map<String, String> userRoles;
    // 用户属性
    @ApiModelProperty(value = "用户属性")
    private Map<String, String> userPropertys;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    private Map<String, String> accountStatus;
    // 用户状态
    @ApiModelProperty(value = "用户状态")
    private Map<String, String> userStatus;
    // 注册平台
    @ApiModelProperty(value = "注册平台")
    private Map<String, String> registPlat;
    // 用户类型
    @ApiModelProperty(value = "用户类型")
    private Map<String, String> userTypes;
    // 借款人类型
    @ApiModelProperty(value = "借款人类型")
    private Map<String, String> borrowTypes;
    //
    @ApiModelProperty(value = "机构配置")
    private List<HjhInstConfigVO> listHjhInstConfig;

    public Map<String, String> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Map<String, String> userRoles) {
        this.userRoles = userRoles;
    }

    public Map<String, String> getUserPropertys() {
        return userPropertys;
    }

    public void setUserPropertys(Map<String, String> userPropertys) {
        this.userPropertys = userPropertys;
    }

    public Map<String, String> getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(Map<String, String> accountStatus) {
        this.accountStatus = accountStatus;
    }

    public Map<String, String> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Map<String, String> userStatus) {
        this.userStatus = userStatus;
    }

    public Map<String, String> getRegistPlat() {
        return registPlat;
    }

    public void setRegistPlat(Map<String, String> registPlat) {
        this.registPlat = registPlat;
    }

    public Map<String, String> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(Map<String, String> userTypes) {
        this.userTypes = userTypes;
    }

    public Map<String, String> getBorrowTypes() {
        return borrowTypes;
    }

    public void setBorrowTypes(Map<String, String> borrowTypes) {
        this.borrowTypes = borrowTypes;
    }

    public List<HjhInstConfigVO> getListHjhInstConfig() {
        return listHjhInstConfig;
    }

    public void setListHjhInstConfig(List<HjhInstConfigVO> listHjhInstConfig) {
        this.listHjhInstConfig = listHjhInstConfig;
    }
}
