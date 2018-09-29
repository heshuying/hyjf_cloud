/*
 * @Copyright: 2005-2018 so_what. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @Description
 * @Author sss
 * @Date 2018/7/26 11:26
 */
public class CrmDepartmentVO extends BaseVO implements Serializable {

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

    private Integer sort;

    private Integer flag;

    private String operation;

    private static final long serialVersionUID = 1L;

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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
