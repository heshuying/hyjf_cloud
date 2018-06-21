package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;

public class AdminRoleMenuPermissionsKey implements Serializable {
    private Integer roleId;

    private String menuUuid;

    private String permissionUuid;

    private static final long serialVersionUID = 1L;

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getMenuUuid() {
        return menuUuid;
    }

    public void setMenuUuid(String menuUuid) {
        this.menuUuid = menuUuid == null ? null : menuUuid.trim();
    }

    public String getPermissionUuid() {
        return permissionUuid;
    }

    public void setPermissionUuid(String permissionUuid) {
        this.permissionUuid = permissionUuid == null ? null : permissionUuid.trim();
    }
}