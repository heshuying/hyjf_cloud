package com.hyjf.admin.beans.request;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class BankCardLogRequestBean extends BasePage implements Serializable {

    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "所属银行code")
    private String bankCode;
    @ApiModelProperty(value = "所属银行名")
    private String bankName;
    /**
     * 操作时间
     */
    @ApiModelProperty(value = "操作时间开始时间")
    private String startTime;
    @ApiModelProperty(value = "操作时间结束时间")
    private String endTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }
}