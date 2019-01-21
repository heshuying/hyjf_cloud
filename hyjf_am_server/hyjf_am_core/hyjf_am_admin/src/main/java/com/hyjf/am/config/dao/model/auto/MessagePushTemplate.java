package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class MessagePushTemplate implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 消息标签,外键,消息标签表的id
     *
     * @mbggenerated
     */
    private Integer tagId;

    /**
     * 消息标签编码,消息标签表的编码
     *
     * @mbggenerated
     */
    private String tagCode;

    /**
     * 消息编码
     *
     * @mbggenerated
     */
    private String templateCode;

    /**
     * 消息标题
     *
     * @mbggenerated
     */
    private String templateTitle;

    /**
     * 图片url
     *
     * @mbggenerated
     */
    private String templateImageUrl;

    /**
     * 消息内容
     *
     * @mbggenerated
     */
    private String templateContent;

    /**
     * 推送终端
     *
     * @mbggenerated
     */
    private String templateTerminal;

    /**
     * 后续动作,0打开APP,1打开H5页面,2指定原生页面
     *
     * @mbggenerated
     */
    private Integer templateAction;

    /**
     * 后续动作url,后续动作为0时此字段无效,1时为填写的url,2时为原生页面的url
     *
     * @mbggenerated
     */
    private String templateActionUrl;

    /**
     * 状态 0 新建 1启用 2 禁用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
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