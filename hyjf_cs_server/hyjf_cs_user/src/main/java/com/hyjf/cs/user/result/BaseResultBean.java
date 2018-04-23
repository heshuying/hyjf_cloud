/*
 * Copyright(c) 2012-2016 JD Pharma.Ltd. All Rights Reserved.
 */
package com.hyjf.cs.user.result;

import java.io.Serializable;

/**
 * app接口返回数据的几类
 * @author renxingchen
 * @version hyjf 1.0
 * @since hyjf 1.0 2016年2月19日
 * @see 14:23:01
 */
public class BaseResultBean implements Serializable {

    /**
     * 此处为属性说明
     */
    private static final long serialVersionUID = -3589570872364671096L;

    public BaseResultBean() {
        this("");
    }

    public BaseResultBean(String request) {
        super();
        this.status = "1";
        this.request = request;
    }

    public BaseResultBean(String status, String statusDesc) {
        super();
        this.status = status;
        this.statusDesc = statusDesc;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDesc() {
        return statusDesc;
    }

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    private String status;

    private String statusDesc;

    private String request;

}
