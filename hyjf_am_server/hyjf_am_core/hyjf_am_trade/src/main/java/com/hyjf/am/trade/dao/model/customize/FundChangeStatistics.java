package com.hyjf.am.trade.dao.model.customize;

import java.io.Serializable;

/**
 * @Author : huanghui
 */
public class FundChangeStatistics implements Serializable {

    private Integer times;

    private Integer total;

    private String startTime;

    private String endTime;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(Integer times) {
        this.times = times;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
