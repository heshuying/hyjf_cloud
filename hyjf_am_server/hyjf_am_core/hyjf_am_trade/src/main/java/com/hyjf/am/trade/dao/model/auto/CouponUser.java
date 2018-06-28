package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CouponUser implements Serializable {
    private Integer id;

    private String couponCode;

    private String couponUserCode;

    private String couponGroupCode;

    private Integer userId;

    private Integer activityId;

    private Integer couponSource;

    private Integer usedFlag;

    private Integer readFlag;

    private Integer endTime;

    private String content;

    private String auditContent;

    private String deleteContent;

    private String auditUser;

    private Integer auditTime;

    private Integer attribute;

    private String channel;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode == null ? null : couponUserCode.trim();
    }

    public String getCouponGroupCode() {
        return couponGroupCode;
    }

    public void setCouponGroupCode(String couponGroupCode) {
        this.couponGroupCode = couponGroupCode == null ? null : couponGroupCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(Integer couponSource) {
        this.couponSource = couponSource;
    }

    public Integer getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Integer usedFlag) {
        this.usedFlag = usedFlag;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent == null ? null : auditContent.trim();
    }

    public String getDeleteContent() {
        return deleteContent;
    }

    public void setDeleteContent(String deleteContent) {
        this.deleteContent = deleteContent == null ? null : deleteContent.trim();
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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