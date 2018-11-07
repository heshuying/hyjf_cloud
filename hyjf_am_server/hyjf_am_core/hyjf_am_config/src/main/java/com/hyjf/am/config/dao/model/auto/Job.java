package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class Job implements Serializable {
    private Integer id;

    /**
     * 职位名称
     *
     * @mbggenerated
     */
    private String officeName;

    /**
     * 工作地点
     *
     * @mbggenerated
     */
    private String place;

    /**
     * 是否显示（1：显示，0不显示）
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 管理员
     *
     * @mbggenerated
     */
    private String addAdmin;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer order;

    /**
     * 招聘人数
     *
     * @mbggenerated
     */
    private Integer persons;

    /**
     * 邮箱
     *
     * @mbggenerated
     */
    private String email;

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

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 招聘内容
     *
     * @mbggenerated
     */
    private String content;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName == null ? null : officeName.trim();
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place == null ? null : place.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAddAdmin() {
        return addAdmin;
    }

    public void setAddAdmin(String addAdmin) {
        this.addAdmin = addAdmin == null ? null : addAdmin.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}