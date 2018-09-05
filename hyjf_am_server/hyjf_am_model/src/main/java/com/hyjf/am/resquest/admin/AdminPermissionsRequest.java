/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author: sunpeikai
 * @version: AdminPermissionsRequest, v0.1 2018/9/5 14:03
 */
@ApiModel(value = "功能权限维护查询请求参数")
public class AdminPermissionsRequest extends BasePage implements Serializable {

    private static final long serialVersionUID = 387630498860089653L;
    @ApiModelProperty(value = "查询条件-权限")
    private String permissionSrch;
    @ApiModelProperty(value = "查询条件-权限名称")
    private String permissionNameSrch;
    /**
     * 分页变量
     */
    private int limitStart = -1;
    private int limitEnd = -1;

    public String getPermissionSrch() {
        return permissionSrch;
    }

    public void setPermissionSrch(String permissionSrch) {
        this.permissionSrch = permissionSrch;
    }

    public String getPermissionNameSrch() {
        return permissionNameSrch;
    }

    public void setPermissionNameSrch(String permissionNameSrch) {
        this.permissionNameSrch = permissionNameSrch;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }
}
