package com.hyjf.am.resquest.admin;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BorrowCreditRepayAmRequest  implements Serializable {

    /* 承接人用户名称 */
    private String userName;

    /* 出让人名称 */
    private String creditUserName;

    /* 债转标号 */
    private String creditNid;

    /* 原标标号 */
    private String bidNid;

    /* 认购单号 */
    private String assignNid;

    /* 还款状态 */
    private String status;

    /* 下次还款时间*/
    private String assignRepayNextTimeStart;

    /* 下次还款时间*/
    private String assignRepayNextTimeEnd;

    /* 债权承接时间*/
    private String addTimeStart;

    /* 债权承接时间*/
    private String addTimeEnd;

    private Integer limitStart;

    private Integer limitEnd;

    /* 平台 */
    private String client;

    /** 债权结束状态 S-成功;F-失败;A-初始:W-未开始*/
    private String stateSrch;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCreditUserName() {
        return creditUserName;
    }

    public void setCreditUserName(String creditUserName) {
        this.creditUserName = creditUserName;
    }

    public String getCreditNid() {
        return creditNid;
    }

    public void setCreditNid(String creditNid) {
        this.creditNid = creditNid;
    }

    public String getBidNid() {
        return bidNid;
    }

    public void setBidNid(String bidNid) {
        this.bidNid = bidNid;
    }

    public String getAssignNid() {
        return assignNid;
    }

    public void setAssignNid(String assignNid) {
        this.assignNid = assignNid;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAssignRepayNextTimeStart() {
        return assignRepayNextTimeStart;
    }

    public void setAssignRepayNextTimeStart(String assignRepayNextTimeStart) {
        this.assignRepayNextTimeStart = assignRepayNextTimeStart;
    }

    public String getAssignRepayNextTimeEnd() {
        return assignRepayNextTimeEnd;
    }

    public void setAssignRepayNextTimeEnd(String assignRepayNextTimeEnd) {
        this.assignRepayNextTimeEnd = assignRepayNextTimeEnd;
    }

    public String getAddTimeStart() {
        return addTimeStart;
    }

    public void setAddTimeStart(String addTimeStart) {
        this.addTimeStart = addTimeStart;
    }

    public String getAddTimeEnd() {
        return addTimeEnd;
    }

    public void setAddTimeEnd(String addTimeEnd) {
        this.addTimeEnd = addTimeEnd;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getStateSrch() {
        return stateSrch;
    }

    public void setStateSrch(String stateSrch) {
        this.stateSrch = stateSrch;
    }
}
