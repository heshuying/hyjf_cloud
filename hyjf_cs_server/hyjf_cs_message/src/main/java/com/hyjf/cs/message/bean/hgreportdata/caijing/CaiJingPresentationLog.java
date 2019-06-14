/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.hgreportdata.caijing;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CaiJingPresentationLog, v0.1 2019/6/6 11:15
 */
@Document(collection = "ht_caiJing_presentation_log")
public class CaiJingPresentationLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String logType;

    private Integer count;

    private Integer status;

    private String description;

    private Integer presentationTime;

    //报送开始时间
    private String startDate;

    //报送结束时间
    private String endDate;

    private Integer createTime;

    private Integer updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogType() {
        return logType;
    }

    public void setLogType(String logType) {
        this.logType = logType;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPresentationTime() {
        return presentationTime;
    }

    public void setPresentationTime(Integer presentationTime) {
        this.presentationTime = presentationTime;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
