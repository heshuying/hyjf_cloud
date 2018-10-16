package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class MessagePushTemplate implements Serializable {
    private Integer id;

    private Integer tagId;

    private String tagCode;

    private String templateCode;

    private String templateTitle;

    private String templateImageUrl;

    private String templateContent;

    private String templateTerminal;

    private Integer templateAction;

    private String templateActionUrl;

    private Integer status;

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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagCode() {
        return tagCode;
    }

    public void setTagCode(String tagCode) {
        this.tagCode = tagCode == null ? null : tagCode.trim();
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode == null ? null : templateCode.trim();
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle == null ? null : templateTitle.trim();
    }

    public String getTemplateImageUrl() {
        return templateImageUrl;
    }

    public void setTemplateImageUrl(String templateImageUrl) {
        this.templateImageUrl = templateImageUrl == null ? null : templateImageUrl.trim();
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent == null ? null : templateContent.trim();
    }

    public String getTemplateTerminal() {
        return templateTerminal;
    }

    public void setTemplateTerminal(String templateTerminal) {
        this.templateTerminal = templateTerminal == null ? null : templateTerminal.trim();
    }

    public Integer getTemplateAction() {
        return templateAction;
    }

    public void setTemplateAction(Integer templateAction) {
        this.templateAction = templateAction;
    }

    public String getTemplateActionUrl() {
        return templateActionUrl;
    }

    public void setTemplateActionUrl(String templateActionUrl) {
        this.templateActionUrl = templateActionUrl == null ? null : templateActionUrl.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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