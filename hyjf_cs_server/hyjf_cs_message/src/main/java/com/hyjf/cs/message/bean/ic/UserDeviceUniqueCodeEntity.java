package com.hyjf.cs.message.bean.ic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

@Document(collection = "t__userdevice_uniquecode")
public class UserDeviceUniqueCodeEntity implements Serializable {
    private Integer id;

    private String uniqueIdentifier;

    private Integer userId;

    private Integer requestTimesDay;

    private String currentDay;

    private Integer createTime;

    private Integer updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier == null ? null : uniqueIdentifier.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRequestTimesDay() {
        return requestTimesDay;
    }

    public void setRequestTimesDay(Integer requestTimesDay) {
        this.requestTimesDay = requestTimesDay;
    }

    public String getCurrentDay() {
        return currentDay;
    }

    public void setCurrentDay(String currentDay) {
        this.currentDay = currentDay == null ? null : currentDay.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserDeviceUniqueCodeEntity{" +
                "id='" + id + '\'' +
                ", uniqueIdentifier=" + uniqueIdentifier +
                ", userId=" + userId +
                ", requestTimesDay=" + requestTimesDay +
                ", currentDay=" + currentDay +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}