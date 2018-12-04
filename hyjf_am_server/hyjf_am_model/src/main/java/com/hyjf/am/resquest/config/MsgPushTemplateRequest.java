/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fuqiang
 * @version MsgPushTemplateRequest, v0.1 2018/6/26 9:34
 */
public class MsgPushTemplateRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = 9183336360106479183L;

    private Integer id;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;

    @ApiModelProperty(value = "标签编号")
    private String tagCode;

    @ApiModelProperty(value = "标签名称")
    private String tagName;

    @ApiModelProperty(value = "消息模板编号")
    private String templateCode;

    @ApiModelProperty(value = "消息模板标题")
    private String templateTitle;

    @ApiModelProperty(value = "模板图片url")
    private String templateImageUrl;

    @ApiModelProperty(value = "消息模板内容")
    private String templateContent;

    @ApiModelProperty(value = "推送终端")
    private String templateTerminal;

    @ApiModelProperty(value = "后续动作")
    private Integer templateAction;

    @ApiModelProperty(value = "后续动作url")
    private String templateActionUrl;

    @ApiModelProperty(value = "后续动作url1")
    private String templateActionUrl1;

    @ApiModelProperty(value = "后续动作url2")
    private String templateActionUrl2;

    @ApiModelProperty(value = "后续动作url3")
    private String templateActionUrl3;

    @ApiModelProperty(value = "状态")
    private Integer status;

    private String createUserName;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

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
        this.tagCode = tagCode;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getTemplateTitle() {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle) {
        this.templateTitle = templateTitle;
    }

    public String getTemplateImageUrl() {
        return templateImageUrl;
    }

    public void setTemplateImageUrl(String templateImageUrl) {
        this.templateImageUrl = templateImageUrl;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getTemplateTerminal() {
        return templateTerminal;
    }

    public void setTemplateTerminal(String templateTerminal) {
        this.templateTerminal = templateTerminal;
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
        this.templateActionUrl = templateActionUrl;
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

    public String getTemplateActionUrl1() {
        return templateActionUrl1;
    }

    public void setTemplateActionUrl1(String templateActionUrl1) {
        this.templateActionUrl1 = templateActionUrl1;
    }

    public String getTemplateActionUrl2() {
        return templateActionUrl2;
    }

    public void setTemplateActionUrl2(String templateActionUrl2) {
        this.templateActionUrl2 = templateActionUrl2;
    }

    public String getTemplateActionUrl3() {
        return templateActionUrl3;
    }

    public void setTemplateActionUrl3(String templateActionUrl3) {
        this.templateActionUrl3 = templateActionUrl3;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }
}
