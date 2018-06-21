package com.hyjf.admin.beans;

import java.io.Serializable;

public class PermissionsBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//权限名称
	String permissionName;
	//权限功能
	String permissionKey;
	public String getPermissionName() {
		return permissionName;
	}
	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}
	public String getPermissionKey() {
		return permissionKey;
	}
	public void setPermissionKey(String permissionKey) {
		this.permissionKey = permissionKey;
	}

}
