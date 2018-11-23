package com.hyjf.am.resquest.user;

import java.util.Date;

/**
 * @author fuqiang
 * @version SellDailyRequest, v0.1 2018/11/21 11:38
 */
public class SellDailyRequest {
    private Date startTime;

    private Date endTime;

    private Integer queryAllDivisionType;

    public SellDailyRequest(Date startTime, Date endTime, Integer queryAllDivisionType) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.queryAllDivisionType = queryAllDivisionType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getQueryAllDivisionType() {
        return queryAllDivisionType;
    }

    public void setQueryAllDivisionType(Integer queryAllDivisionType) {
        this.queryAllDivisionType = queryAllDivisionType;
    }
}
