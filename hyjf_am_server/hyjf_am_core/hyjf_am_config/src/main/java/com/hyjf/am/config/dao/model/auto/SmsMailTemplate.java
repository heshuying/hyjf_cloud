package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SmsMailTemplate implements Serializable {
    private Integer id;

    /**
     * 模板名称
     *
     * @mbggenerated
     */
    private String mailName;

    private String mailValue;

    /**
     * 是否开启 0关闭 1开启
     *
     * @mbggenerated
     */
    private Integer mailStatus;

    /**
     * 模板内容
     *
     * @mbggenerated
     */
    private String mailContent;

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
     * 修改时间
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

    public String getMailName() {
        return mailName;
    }

    public void setMailName(String mailName) {
        this.mailName = mailName == null ? null : mailName.trim();
    }

    public String getMailValue() {
        return mailValue;
    }

    public void setMailValue(String mailValue) {
        this.mailValue = mailValue == null ? null : mailValue.trim();
    }

    public Integer getMailStatus() {
        return mailStatus;
    }

    public void setMailStatus(Integer mailStatus) {
        this.mailStatus = mailStatus;
    }

    public String getMailContent() {
        return mailContent;
    }

    public void setMailContent(String mailContent) {
        this.mailContent = mailContent == null ? null : mailContent.trim();
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