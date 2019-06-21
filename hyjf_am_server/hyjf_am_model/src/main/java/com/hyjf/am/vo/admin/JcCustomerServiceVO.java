/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version JcCustomerServiceVO, v0.1 2019/6/21 11:39
 */
public class JcCustomerServiceVO extends BaseVO implements Serializable {
    private String id;

    private Integer solveProblem;

    private Integer responseTime;

    private Integer complaintNum;

    private Integer phoneReception;

    private Integer qqReception;

    private Integer wxReception;

    private String time;

    private String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getSolveProblem() {
        return solveProblem;
    }

    public void setSolveProblem(Integer solveProblem) {
        this.solveProblem = solveProblem;
    }

    public Integer getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Integer responseTime) {
        this.responseTime = responseTime;
    }

    public Integer getComplaintNum() {
        return complaintNum;
    }

    public void setComplaintNum(Integer complaintNum) {
        this.complaintNum = complaintNum;
    }

    public Integer getPhoneReception() {
        return phoneReception;
    }

    public void setPhoneReception(Integer phoneReception) {
        this.phoneReception = phoneReception;
    }

    public Integer getQqReception() {
        return qqReception;
    }

    public void setQqReception(Integer qqReception) {
        this.qqReception = qqReception;
    }

    public Integer getWxReception() {
        return wxReception;
    }

    public void setWxReception(Integer wxReception) {
        this.wxReception = wxReception;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
