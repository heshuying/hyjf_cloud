package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class BankOpenAccountLogKey implements Serializable {
    private Integer id;

    private String retMsg;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg == null ? null : retMsg.trim();
    }
}