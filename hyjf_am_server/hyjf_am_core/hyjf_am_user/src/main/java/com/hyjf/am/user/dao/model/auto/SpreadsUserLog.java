package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SpreadsUserLog implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer oldSpreadsUserId;

    private Integer spreadsUserId;

    private String type;

    private String opernote;

    private String operation;

    private String createIp;

    private Date createTime;

    private Date updateTime;

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

    public Integer getOldSpreadsUserId() {
        return oldSpreadsUserId;
    }

    public void setOldSpreadsUserId(Integer oldSpreadsUserId) {
        this.oldSpreadsUserId = oldSpreadsUserId;
    }

    public Integer getSpreadsUserId() {
        return spreadsUserId;
    }

    public void setSpreadsUserId(Integer spreadsUserId) {
        this.spreadsUserId = spreadsUserId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getOpernote() {
        return opernote;
    }

    public void setOpernote(String opernote) {
        this.opernote = opernote == null ? null : opernote.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getCreateIp() {
        return createIp;
    }

    public void setCreateIp(String createIp) {
        this.createIp = createIp == null ? null : createIp.trim();
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