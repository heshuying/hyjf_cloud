package com.hyjf.am.vo.config;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeVO extends AdminMenuVO implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	private String menuPuuid;

	private String menuUuid;

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}


	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String name;

	private String path;

	private String icon;


	private List<TreeVO> children = new ArrayList<TreeVO>();

    public List<TreeVO> getChildren() {
		return children;
	}

	public void setChildren(List<TreeVO> children) {
		this.children = children;
	}

	private boolean selected;


}