package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UserBindEmailLog implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 绑定的邮箱
     *
     * @mbggenerated
     */
    private String userEmail;

    /**
     * 账号邮箱1未验证 2已验证 3过期(已发送新的邮件)
     *
     * @mbggenerated
     */
    private Integer userEmailStatus;

    /**
     * email激活码
     *
     * @mbggenerated
     */
    private String emailActiveCode;

    /**
     * email激活链接过期时间
     *
     * @mbggenerated
     */
    private Date emailActiveUrlDeadtime;

    /**
     * 发送邮件时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail == null ? null : userEmail.trim();
    }

    public Integer getUserEmailStatus() {
        return userEmailStatus;
    }

    public void setUserEmailStatus(Integer userEmailStatus) {
        this.userEmailStatus = userEmailStatus;
    }

    public String getEmailActiveCode() {
        return emailActiveCode;
    }

    public void setEmailActiveCode(String emailActiveCode) {
        this.emailActiveCode = emailActiveCode == null ? null : emailActiveCode.trim();
    }

    public Date getEmailActiveUrlDeadtime() {
        return emailActiveUrlDeadtime;
    }

    public void setEmailActiveUrlDeadtime(Date emailActiveUrlDeadtime) {
        this.emailActiveUrlDeadtime = emailActiveUrlDeadtime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}