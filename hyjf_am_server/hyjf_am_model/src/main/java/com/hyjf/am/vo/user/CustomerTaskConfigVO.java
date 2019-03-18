/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author A2
 * @version CustomerTaskConfigVO, v0.1 2019/3/18 15:07
 */
public class CustomerTaskConfigVO implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 任务时间,精确到月 yyyy-mm
     *
     * @mbggenerated
     */
    private String taskTime;

    /**
     * 坐席姓名
     *
     * @mbggenerated
     */
    private String customerName;

    /**
     * 坐席分组 1:新客组,2:老客组
     *
     * @mbggenerated
     */
    private Integer customerGroup;

    /**
     * 月目标（元）
     *
     * @mbggenerated
     */
    private BigDecimal monthGoal;

    /**
     * 是否有效 1:有效,2:无效
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 删除标识 0：未删除，1：已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建用户ID
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户ID
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

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime == null ? null : taskTime.trim();
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public Integer getCustomerGroup() {
        return customerGroup;
    }

    public void setCustomerGroup(Integer customerGroup) {
        this.customerGroup = customerGroup;
    }

    public BigDecimal getMonthGoal() {
        return monthGoal;
    }

    public void setMonthGoal(BigDecimal monthGoal) {
        this.monthGoal = monthGoal;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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
