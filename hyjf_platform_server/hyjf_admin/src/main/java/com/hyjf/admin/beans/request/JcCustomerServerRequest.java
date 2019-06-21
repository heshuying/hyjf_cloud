/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcCustomerServerRequest, v0.1 2019/6/21 11:06
 */
public class JcCustomerServerRequest extends BaseRequest implements Serializable {

    private String id;

    private Integer solveProblem;

    private Integer responseTime;

    private Integer complaintNum;

    private Integer phoneReception;

    private Integer qqReception;

    private Integer wxReception;

    private String time;

    private Integer updateTime;

    private String startTime;

    private String endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
