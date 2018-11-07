package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SellDailyDistribution implements Serializable {
    private Integer id;

    /**
     * 业务名称
     *
     * @mbggenerated
     */
    private String businessName;

    /**
     * 联系人邮箱
     *
     * @mbggenerated
     */
    private String email;

    /**
     * 时间点 1:每个工作日 2:每天  3:每月第一个工作日
     *
     * @mbggenerated
     */
    private Integer timePoint;

    /**
     * 邮件发送具体时间
     *
     * @mbggenerated
     */
    private Date time;

    /**
     * 状态 1:有效,2:无效
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createName;

    /**
     * 更新人
     *
     * @mbggenerated
     */
    private String updateName;

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

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getTimePoint() {
        return timePoint;
    }

    public void setTimePoint(Integer timePoint) {
        this.timePoint = timePoint;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName == null ? null : createName.trim();
    }

    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName == null ? null : updateName.trim();
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