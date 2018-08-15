/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;

/**
 * @author fuqiang
 * @version MailTemplateRequest, v0.1 2018/6/25 14:52
 */
public class MailTemplateRequest extends BasePage implements Serializable {
    private static final long serialVersionUID = -2804856326536538472L;

    /**
     * 序号拼接字段
     */
    private String ids;

    /**
     * 模块标识
     */
    private String templateCode;

    private Integer id;

    private String mailValue;

    private String mailName;

    private Integer mailStatus;

    private Integer createTime;

    private Integer updateTime;

    private String mailContent;

    public String getMailValue() {
        return mailValue;
    }

    public void setMailValue(String mailValue) {
        this.mailValue = mailValue;
    }

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
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

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
