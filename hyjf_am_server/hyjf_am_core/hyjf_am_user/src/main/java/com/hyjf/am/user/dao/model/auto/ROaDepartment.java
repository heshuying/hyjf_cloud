package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class ROaDepartment implements Serializable {
    /**
     * 部门id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 父类部门id
     *
     * @mbggenerated
     */
    private Integer parentid;

    /**
     * 部门名
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 部门描述
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 是否为总部(1,0)
     *
     * @mbggenerated
     */
    private Integer ishead;

    /**
     * 是否人力资源部(1,0)
     *
     * @mbggenerated
     */
    private Integer ishr;

    /**
     * 是否财务部(1,0)
     *
     * @mbggenerated
     */
    private Integer isfinance;

    /**
     * 提成发放方式（1线上2线下）
     *
     * @mbggenerated
     */
    private Integer cuttype;

    /**
     * 所在省份
     *
     * @mbggenerated
     */
    private String provinceid;

    /**
     * 所在城市
     *
     * @mbggenerated
     */
    private String cityid;

    /**
     * 负责人：员工ID
     *
     * @mbggenerated
     */
    private String header;

    /**
     * 督导：用户ID
     *
     * @mbggenerated
     */
    private String manager;

    /**
     * 岗位类别
     *
     * @mbggenerated
     */
    private Integer positionCategory;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer listorder;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer sort;

    /**
     * 删除标记 0：已删除  1：未删除
     *
     * @mbggenerated
     */
    private Integer flag;

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
}