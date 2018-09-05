package com.hyjf.cs.trade.bean;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;

/**
 * @version ApiBean, v0.1 2018/8/28 10:59
 * @Author: Zha Daojian
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiBean<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    public static final String SUCCESS = "0";
    public static final String ERROR = "-1";
    public static final String FAIL = "1";
    public static final String NO_PERMISSION = "2";
    private String statusDesc = "成功";
    private String status = SUCCESS;
    private T data;

    public ApiBean() {
        super();
    }

    public ApiBean(T data) {
        super();
        this.data = data;
    }

    public ApiBean(Throwable e) {
        super();
        this.status = ERROR;
        this.statusDesc = e.toString();
    }

    public ApiBean(String status, String statusDesc) {
        super();
        this.status = status;
        this.statusDesc = statusDesc;
    }

    /**
     * statusDesc
     * @return the statusDesc
     */

    public String getStatusDesc() {
        return statusDesc;
    }

    /**
     * @param statusDesc the statusDesc to set
     */
    public void setStatusInfo(String status, String statusDesc) {
        this.status = status;
        this.statusDesc = statusDesc;
    }

    /**
     * @param statusDesc the statusDesc to set
     */

    public void setStatusDesc(String statusDesc) {
        this.statusDesc = statusDesc;
    }

    /**
     * status
     * @return the status
     */

    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * data
     * @return the data
     */

    public T getData() {
        return data;
    }

    /**
     * @param data the data to set
     */

    public void setData(T data) {
        this.data = data;
    }


}

