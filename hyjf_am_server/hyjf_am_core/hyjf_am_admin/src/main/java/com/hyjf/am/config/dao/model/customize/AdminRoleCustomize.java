/**
 * Description:（类功能描述-必填） 需要在每个方法前添加业务描述，方便业务业务行为的BI工作
 * Copyright: Copyright (HYJF Corporation)2015
 * Company: HYJF Corporation
 * @author: Administrator
 * @version: 1.0
 * Created at: 2015年11月20日 下午5:24:10
 * Modification History:
 * Modified by : 
 */

package com.hyjf.am.config.dao.model.customize;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
public class AdminRoleCustomize implements Serializable {

    /**
     * serialVersionUID:
     */
    private static final long serialVersionUID = 1L;

    private String roleId;

    private String menuPuuid;

    private String menuUuid;

    private String menuPname;

    private String menuName;

    private String menuSort;

    private String menuIcon;

    private String permissionUuid;

    private String permissionName;

    private String selected;

    private List<AdminRoleCustomize> childMenu;

    private Map<String, List<AdminRoleCustomize>> childPermission;

    public String getMenuPuuid() {
        return menuPuuid;
    }

    public void setMenuPuuid(String menuPuuid) {
        this.menuPuuid = menuPuuid;
    }

    public String getMenuUuid() {
        return menuUuid;
    }

    public void setMenuUuid(String menuUuid) {
        this.menuUuid = menuUuid;
    }

    public String getMenuPname() {
        return menuPname;
    }

    public void setMenuPname(String menuPname) {
        this.menuPname = menuPname;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getMenuSort() {
        return menuSort;
    }

    public void setMenuSort(String menuSort) {
        this.menuSort = menuSort;
    }

    public String getPermissionUuid() {
        return permissionUuid;
    }

    public void setPermissionUuid(String permissionUuid) {
        this.permissionUuid = permissionUuid;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public List<AdminRoleCustomize> getChildMenu() {
        return childMenu;
    }

    public void setChildMenu(List<AdminRoleCustomize> childMenu) {
        this.childMenu = childMenu;
    }

    public Map<String, List<AdminRoleCustomize>> getChildPermission() {
        return childPermission;
    }

    public void setChildPermission(Map<String, List<AdminRoleCustomize>> childPermission) {
        this.childPermission = childPermission;
    }

}
