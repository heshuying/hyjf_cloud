/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.response;

import com.alibaba.fastjson.JSONObject;
import com.hyjf.admin.beans.vo.DropDownVO;
import com.hyjf.am.vo.user.UtmPlatVO;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author nxl
 * @version UserManagerInitResponseBean, v0.1 2018/7/19 18:15
 */
public class UserManagerInitResponseBean {
    // 用户角色
    @ApiModelProperty(value = "用户角色")
    private List<DropDownVO> userRoles;
    // 用户属性
    @ApiModelProperty(value = "用户属性")
    private List<DropDownVO> userPropertys;
    // 开户状态
    @ApiModelProperty(value = "开户状态")
    private List<DropDownVO> accountStatus;
    // 用户状态
    @ApiModelProperty(value = "用户状态")
    private List<DropDownVO> userStatus;
    // 注册平台
    @ApiModelProperty(value = "注册平台")
    private List<DropDownVO> registPlat;
    // 用户类型
    @ApiModelProperty(value = "用户类型")
    private List<DropDownVO> userTypes;
    // 借款人类型
    @ApiModelProperty(value = "借款人类型")
    private List<DropDownVO> borrowTypes;
    //
    @ApiModelProperty(value = "机构配置")
    private List<DropDownVO> listHjhInstConfig;
    @ApiModelProperty(value = "部门树形列表")
    private JSONObject deptList;

    @ApiModelProperty(value = "注册渠道")
    private List<UtmPlatVO> utmPlatList;

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

    public List<DropDownVO> getRegistPlat() {
        return registPlat;
    }

    public void setRegistPlat(List<DropDownVO> registPlat) {
        this.registPlat = registPlat;
    }

    public List<DropDownVO> getUserTypes() {
        return userTypes;
    }

    public void setUserTypes(List<DropDownVO> userTypes) {
        this.userTypes = userTypes;
    }

    public List<DropDownVO> getBorrowTypes() {
        return borrowTypes;
    }

    public void setBorrowTypes(List<DropDownVO> borrowTypes) {
        this.borrowTypes = borrowTypes;
    }

    public List<DropDownVO> getListHjhInstConfig() {
        return listHjhInstConfig;
    }

    public void setListHjhInstConfig(List<DropDownVO> listHjhInstConfig) {
        this.listHjhInstConfig = listHjhInstConfig;
    }

    public JSONObject getDeptList() {
        return deptList;
    }

    public void setDeptList(JSONObject deptList) {
        this.deptList = deptList;
    }

    public List<UtmPlatVO> getUtmPlatList() {
        return utmPlatList;
    }

    public void setUtmPlatList(List<UtmPlatVO> utmPlatList) {
        this.utmPlatList = utmPlatList;
    }
}
