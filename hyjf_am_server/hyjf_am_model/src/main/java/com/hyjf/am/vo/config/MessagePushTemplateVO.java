package com.hyjf.am.vo.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;
import io.swagger.models.auth.In;

import java.io.Serializable;
import java.util.Date;

/**
 * @author xiasq
 * @version MessagePushTemplateVO, v0.1 2018/5/4 10:42
 */
public class MessagePushTemplateVO extends BaseVO implements Serializable {
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

    private Date createTime;

    private Integer createUserId;

    private String createUserName;

    private Date updateTime;

    private Integer lastupdateUserId;

    private String lastupdateUserName;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getLastupdateUserId() {
        return lastupdateUserId;
    }

    public void setLastupdateUserId(Integer lastupdateUserId) {
        this.lastupdateUserId = lastupdateUserId;
    }

    public String getLastupdateUserName() {
        return lastupdateUserName;
    }

    public void setLastupdateUserName(String lastupdateUserName) {
        this.lastupdateUserName = lastupdateUserName == null ? null : lastupdateUserName.trim();
    }

    @Override
    public String toString() {
        return "MessagePushTemplateVO{" +
                "id=" + id +
                ", tagId=" + tagId +
                ", tagCode='" + tagCode + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", templateTitle='" + templateTitle + '\'' +
                ", templateImageUrl='" + templateImageUrl + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", templateTerminal='" + templateTerminal + '\'' +
                ", templateAction=" + templateAction +
                ", templateActionUrl='" + templateActionUrl + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", createUserId=" + createUserId +
                ", createUserName='" + createUserName + '\'' +
                ", lastupdateTime=" + updateTime +
                ", lastupdateUserId=" + lastupdateUserId +
                ", lastupdateUserName='" + lastupdateUserName + '\'' +
                '}';
    }
}