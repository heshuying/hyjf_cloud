package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class PushMoney implements Serializable {
    /**
     * 提成设置表：主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户类型：属于什么用户
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 产品类型：1 汇直投 2 汇计划
     *
     * @mbggenerated
     */
    private Integer projectType;

    /**
     * 是否发放提成：0 不发放 1 发放
     *
     * @mbggenerated
     */
    private Integer rewardSend;

    /**
     * 天标
     *
     * @mbggenerated
     */
    private String dayTender;

    /**
     * 月标
     *
     * @mbggenerated
     */
    private String monthTender;

    private Integer createBy;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateBy;

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

    /**
     * 备注说明
     *
     * @mbggenerated
     */
    private String remark;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getProjectType() {
        return projectType;
    }

    public void setProjectType(Integer projectType) {
        this.projectType = projectType;
    }

    public Integer getRewardSend() {
        return rewardSend;
    }

    public void setRewardSend(Integer rewardSend) {
        this.rewardSend = rewardSend;
    }

    public String getDayTender() {
        return dayTender;
    }

    public void setDayTender(String dayTender) {
        this.dayTender = dayTender == null ? null : dayTender.trim();
    }

    public String getMonthTender() {
        return monthTender;
    }

    public void setMonthTender(String monthTender) {
        this.monthTender = monthTender == null ? null : monthTender.trim();
    }

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }
}