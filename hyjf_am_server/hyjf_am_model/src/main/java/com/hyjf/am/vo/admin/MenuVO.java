package com.hyjf.am.vo.admin;



import java.io.Serializable;
import java.util.List;

import com.hyjf.am.vo.BaseVO;

/**
 * @author lisheng
 * @version MenuVO, v0.1 2018/9/13 14:33
 */
public class MenuVO extends BaseVO implements Serializable{
    private String id;
    /**
     * 菜单名
     */
    private String name;
    /**
     * 图标
     */
    private String icon;
    /**
     * 路径
     */
    private String path;

    private List<MenuVO> children;

    private String menuUuid;

    private Integer sort;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}

	public String getMenuUuid() {
		return menuUuid;
	}

	public void setMenuUuid(String menuUuid) {
		this.menuUuid = menuUuid;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}
    
}
