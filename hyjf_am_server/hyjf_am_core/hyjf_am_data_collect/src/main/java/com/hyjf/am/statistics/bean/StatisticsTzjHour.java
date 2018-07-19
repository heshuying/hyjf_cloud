package com.hyjf.am.statistics.bean;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;


/**
 * @author xiasq
 * @version StatisticsTzjHour, v0.1 2018/7/6 16:56
 */
@Document(collection = "t_statistics_tzj_hour")
@Deprecated
public class StatisticsTzjHour implements Serializable {
    private static final long serialVersionUID = 6385345438848569447L;

    private String id;

    private String day;

    private String hour;

    private Integer registCount;

    private Integer tenderFirstCount;

    private Date createTime;

    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public Integer getRegistCount() {
        return registCount;
    }

    public void setRegistCount(Integer registCount) {
        this.registCount = registCount;
    }

    public Integer getTenderFirstCount() {
        return tenderFirstCount;
    }

    public void setTenderFirstCount(Integer tenderFirstCount) {
        this.tenderFirstCount = tenderFirstCount;
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
