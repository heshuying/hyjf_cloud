package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UserBindEmailLog implements Serializable {
    private Integer id;

    private Integer userId;

    private String userEmail;

    private Integer userEmailStatus;

    private String emailActiveCode;

    private Date emailActiveUrlDeadtime;

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