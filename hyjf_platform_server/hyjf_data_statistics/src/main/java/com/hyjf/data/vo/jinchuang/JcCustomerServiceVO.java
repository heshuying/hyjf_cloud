/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.data.vo.jinchuang;

import com.hyjf.data.vo.BaseVO;

/**
 * @author yaoyong
 * @version JcCustomerServiceVO, v0.1 2019/6/20 15:27
 */
public class JcCustomerServiceVO extends BaseVO {

    private Integer solveProblem;

    private Integer responseTime;

    private String complaintNum;

    private Integer phoneReception;

    private Integer qqReception;

    private Integer wxReception;

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

    public String getComplaintNum() {
        return complaintNum;
    }

    public void setComplaintNum(String complaintNum) {
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
}
