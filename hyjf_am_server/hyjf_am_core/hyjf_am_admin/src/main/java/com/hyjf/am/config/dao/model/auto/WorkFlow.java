package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class WorkFlow implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 业务名称表id
     *
     * @mbggenerated
     */
    private Integer worknameId;

    /**
     * 1正常，2异常
     *
     * @mbggenerated
     */
    private Integer flowStatus;

    /**
     * 1是，2否
     *
     * @mbggenerated
     */
    private Byte checkStatus;

    /**
     * 邮件预警通知人
     *
     * @mbggenerated
     */
    private String mailNotifier;

    /**
     * 创建操作人
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * 更新操作人
     *
     * @mbggenerated
     */
    private String updateUser;

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

    public Integer getWorknameId() {
        return worknameId;
    }

    public void setWorknameId(Integer worknameId) {
        this.worknameId = worknameId;
    }

    public Integer getFlowStatus() {
        return flowStatus;
    }

    public void setFlowStatus(Integer flowStatus) {
        this.flowStatus = flowStatus;
    }

    public Byte getCheckStatus() {
        return checkStatus;
    }

    public void setCheckStatus(Byte checkStatus) {
        this.checkStatus = checkStatus;
    }

    public String getMailNotifier() {
        return mailNotifier;
    }

    public void setMailNotifier(String mailNotifier) {
        this.mailNotifier = mailNotifier == null ? null : mailNotifier.trim();
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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