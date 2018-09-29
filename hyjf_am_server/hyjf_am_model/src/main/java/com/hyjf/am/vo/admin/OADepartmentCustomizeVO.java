/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author libin
 * @version OADepartmentCustomizeVO.java, v0.1 2018年8月9日 上午9:41:33
 */
public class OADepartmentCustomizeVO extends BaseVO implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;

    private Integer parentid;

    private String name;

    private String description;

    private Integer ishead;

    private Integer ishr;

    private Integer isfinance;

    private Integer cuttype;

    private String provinceid;

    private String cityid;

    private String header;

    private String manager;

    private Integer positionCategory;

    private Integer listorder;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getParentid() {
        return parentid;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Integer getIshead() {
        return ishead;
    }

    public void setIshead(Integer ishead) {
        this.ishead = ishead;
    }

    public Integer getIshr() {
        return ishr;
    }

    public void setIshr(Integer ishr) {
        this.ishr = ishr;
    }

    public Integer getIsfinance() {
        return isfinance;
    }

    public void setIsfinance(Integer isfinance) {
        this.isfinance = isfinance;
    }

    public Integer getCuttype() {
        return cuttype;
    }

    public void setCuttype(Integer cuttype) {
        this.cuttype = cuttype;
    }

    public String getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(String provinceid) {
        this.provinceid = provinceid == null ? null : provinceid.trim();
    }

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid == null ? null : cityid.trim();
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header == null ? null : header.trim();
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager == null ? null : manager.trim();
    }

    public Integer getPositionCategory() {
        return positionCategory;
    }

    public void setPositionCategory(Integer positionCategory) {
        this.positionCategory = positionCategory;
    }

    public Integer getListorder() {
        return listorder;
    }

    public void setListorder(Integer listorder) {
        this.listorder = listorder;
    }

}
