package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HjhRegion implements Serializable {
    /**
     * 计划专区表id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 计划编号
     *
     * @mbggenerated
     */
    private String planNid;

    /**
     * 计划名称
     *
     * @mbggenerated
     */
    private String planName;

    /**
     * 计划专区添加时间
     *
     * @mbggenerated
     */
    private Integer configAddTime;

    /**
     * 计划专区状态 0：停用 1：启用
     *
     * @mbggenerated
     */
    private Integer configStatus;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人id
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新人id
     *
     * @mbggenerated
     */
    private Integer updateUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPlanNid() {
        return planNid;
    }

    public void setPlanNid(String planNid) {
        this.planNid = planNid == null ? null : planNid.trim();
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName == null ? null : planName.trim();
    }

    public Integer getConfigAddTime() {
        return configAddTime;
    }

    public void setConfigAddTime(Integer configAddTime) {
        this.configAddTime = configAddTime;
    }

    public Integer getConfigStatus() {
        return configStatus;
    }

    public void setConfigStatus(Integer configStatus) {
        this.configStatus = configStatus;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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