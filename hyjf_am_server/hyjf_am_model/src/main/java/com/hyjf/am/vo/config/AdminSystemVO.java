package com.hyjf.am.vo.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.hyjf.am.vo.BaseVO;

public class AdminSystemVO extends BaseVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String menuPuuid;

	private String menuUuid;

	private String menuName;

	private String menuUrl;

	private String menuIcon;

	private String menuSort;

	private List<AdminSystemVO> menuTreeClild = new ArrayList<AdminSystemVO>();

	private String id;

	private String username;

	private String password;

	private String truename;

	private String menuCtrl;

    private String permissionUuid;

	private String permission;

    private String permissionName;

    private boolean selected;

    private Integer roleId;

    private String state;

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getMenuCtrl() {
		return menuCtrl;
	}

	public void setMenuCtrl(String menuCtrl) {
		this.menuCtrl = menuCtrl;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getMenuIcon() {
		return menuIcon;
	}

	public void setMenuIcon(String menuIcon) {
		this.menuIcon = menuIcon;
	}

	public String getMenuSort() {
		return menuSort;
	}

	public void setMenuSort(String menuSort) {
		this.menuSort = menuSort;
	}

	public List<AdminSystemVO> getMenuTreeClild() {
		return menuTreeClild;
	}

	public void setMenuTreeClild(List<AdminSystemVO> menuTreeClild) {
		this.menuTreeClild = menuTreeClild;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getPermissionUuid() {
        return permissionUuid;
    }

    public void setPermissionUuid(String permissionUuid) {
        this.permissionUuid = permissionUuid;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}