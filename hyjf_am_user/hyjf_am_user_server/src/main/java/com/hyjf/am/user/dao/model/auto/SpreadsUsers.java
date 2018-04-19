package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class SpreadsUsers implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer spreadsUserid;

    private String type;

    private String opernote;

    private String operation;

    private String addtime;

    private String addip;

    private String set;

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

    public Integer getSpreadsUserid() {
        return spreadsUserid;
    }

    public void setSpreadsUserid(Integer spreadsUserid) {
        this.spreadsUserid = spreadsUserid;
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

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime == null ? null : addtime.trim();
    }

    public String getAddip() {
        return addip;
    }

    public void setAddip(String addip) {
        this.addip = addip == null ? null : addip.trim();
    }

    public String getSet() {
        return set;
    }

    public void setSet(String set) {
        this.set = set == null ? null : set.trim();
    }
}