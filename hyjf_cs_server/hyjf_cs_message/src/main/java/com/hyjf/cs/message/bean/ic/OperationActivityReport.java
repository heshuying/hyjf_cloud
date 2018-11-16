/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.message.bean.ic;

import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;

/**
 * 运营报告活动
 * @author tanyy
 * @version OperationActivityReport, v0.1 2018/6/27 9:45
 */
@Document(collection = "ht_operation_activity_report")
public class OperationActivityReport implements Serializable {

    private String id;

    private String operationReportId;

    private Integer operationReportType;

    private Integer activtyType;

    private String activtyName;

    private Integer activtyTime;

    private Integer activtyStartTime;

    private Integer activtyEndTime;

    private String activtyPictureUrl;

    private Integer updateTime;

    private Integer updateUserId;

    private Integer createTime;

    private Integer createUserId;

    private static final long serialVersionUID = 1L;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOperationReportId() {
        return operationReportId;
    }

    public void setOperationReportId(String operationReportId) {
        this.operationReportId = operationReportId;
    }

    public Integer getOperationReportType() {
        return operationReportType;
    }

    public void setOperationReportType(Integer operationReportType) {
        this.operationReportType = operationReportType;
    }

    public Integer getActivtyType() {
        return activtyType;
    }

    public void setActivtyType(Integer activtyType) {
        this.activtyType = activtyType;
    }

    public String getActivtyName() {
        return activtyName;
    }

    public void setActivtyName(String activtyName) {
        this.activtyName = activtyName == null ? null : activtyName.trim();
    }

    public Integer getActivtyTime() {
        return activtyTime;
    }

    public void setActivtyTime(Integer activtyTime) {
        this.activtyTime = activtyTime;
    }

    public Integer getActivtyStartTime() {
        return activtyStartTime;
    }

    public void setActivtyStartTime(Integer activtyStartTime) {
        this.activtyStartTime = activtyStartTime;
    }

    public Integer getActivtyEndTime() {
        return activtyEndTime;
    }

    public void setActivtyEndTime(Integer activtyEndTime) {
        this.activtyEndTime = activtyEndTime;
    }

    public String getActivtyPictureUrl() {
        return activtyPictureUrl;
    }

    public void setActivtyPictureUrl(String activtyPictureUrl) {
        this.activtyPictureUrl = activtyPictureUrl == null ? null : activtyPictureUrl.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }
}
