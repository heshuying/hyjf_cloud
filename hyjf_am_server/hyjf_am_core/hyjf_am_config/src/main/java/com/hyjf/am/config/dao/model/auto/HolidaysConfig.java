package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class HolidaysConfig implements Serializable {
    private Integer id;

    private Date dayTime;

    private Integer holidayFlag;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDayTime() {
        return dayTime;
    }

    public void setDayTime(Date dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getHolidayFlag() {
        return holidayFlag;
    }

    public void setHolidayFlag(Integer holidayFlag) {
        this.holidayFlag = holidayFlag;
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