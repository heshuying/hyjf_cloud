package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class Config implements Serializable {
    /**
     * 配置ID
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 配置名称
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 配置类型
     *
     * @mbggenerated
     */
    private Integer type;

    /**
     * 配置说明
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 配置分组
     *
     * @mbggenerated
     */
    private Integer group;

    /**
     * 配置值
     *
     * @mbggenerated
     */
    private String extra;

    /**
     * 配置说明
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 状态
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 配置值
     *
     * @mbggenerated
     */
    private String value;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Short sort;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra == null ? null : extra.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public Short getSort() {
        return sort;
    }

    public void setSort(Short sort) {
        this.sort = sort;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}