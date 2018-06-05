package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class UserBindEmailLog implements Serializable {
    private Integer id;

    private Integer userId;

    private String userEmail;

    private Integer userEmailStatus;

    private Date createtime;

    private String emailActiveCode;

    private Date emailActiveUrlDeadtime;

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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
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
}