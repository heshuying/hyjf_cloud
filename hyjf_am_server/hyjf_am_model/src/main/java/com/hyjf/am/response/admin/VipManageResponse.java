/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.response.admin;

import com.hyjf.am.response.Response;
import com.hyjf.am.vo.admin.OADepartmentCustomizeVO;
import com.hyjf.am.vo.admin.VipManageVO;
import com.hyjf.am.vo.config.ParamNameVO;

import java.util.List;

/**
 * @author yaoyong
 * @version VipManageResponse, v0.1 2018/7/2 16:47
 */
public class VipManageResponse extends Response<VipManageVO> {
    // 数据查询条数 主要用于分页情况，原子层向组合层返回
    private  Integer  count;

    private List<ParamNameVO> userRoles;

    private List<ParamNameVO> userPropertys;

    private List<ParamNameVO> accountStatus;

    private List<ParamNameVO> userStatus;

    private List<ParamNameVO> registPlat;

    private List<OADepartmentCustomizeVO> departmentList;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<ParamNameVO> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<ParamNameVO> userRoles) {
        this.userRoles = userRoles;
    }

    public List<ParamNameVO> getUserPropertys() {
        return userPropertys;
    }

    public void setUserPropertys(List<ParamNameVO> userPropertys) {
        this.userPropertys = userPropertys;
    }

    public List<ParamNameVO> getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(List<ParamNameVO> accountStatus) {
        this.accountStatus = accountStatus;
    }

    public List<ParamNameVO> getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(List<ParamNameVO> userStatus) {
        this.userStatus = userStatus;
    }

    public List<ParamNameVO> getRegistPlat() {
        return registPlat;
    }

    public void setRegistPlat(List<ParamNameVO> registPlat) {
        this.registPlat = registPlat;
    }

    public List<OADepartmentCustomizeVO> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<OADepartmentCustomizeVO> departmentList) {
        this.departmentList = departmentList;
    }
}
