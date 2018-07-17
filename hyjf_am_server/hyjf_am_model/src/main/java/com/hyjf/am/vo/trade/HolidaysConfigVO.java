/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.trade;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yaoy
 * @version HolidaysConfigVO, v0.1 2018/6/20 16:52
 */
public class HolidaysConfigVO extends BaseVO implements Serializable {
    private Integer id;

    private Date day;

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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
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
