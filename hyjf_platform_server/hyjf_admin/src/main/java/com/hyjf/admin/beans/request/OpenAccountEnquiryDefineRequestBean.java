package com.hyjf.admin.beans.request;

import com.hyjf.admin.beans.BaseRequest;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户按照手机号和身份证号查询开户掉单
 * @version OpenAccountEnquiryDefineRequestBean, v0.1 2018/8/20 16:22
 * @Author: Zha Daojian
 */
public class OpenAccountEnquiryDefineRequestBean extends BaseRequest implements Serializable {

    @ApiModelProperty(value = "选择1为手机号查询，2为身份证号查询")
    public String num;

    @ApiModelProperty(value = "手机号/身份证")
    public String lastname;

    @ApiModelProperty(value = "用户开户订单号，同步系统掉单信息用")
    public String ordeidString;
    @ApiModelProperty(value = "用户id，同步系统掉单信息用")
    public String userid;
    @ApiModelProperty(value = "交易渠道，同步系统掉单信息用")
    public String channel;
    @ApiModelProperty(value = "电子账号，同步系统掉单信息用")
    public String accountId;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getOrdeidString() {
        return ordeidString;
    }

    public void setOrdeidString(String ordeidString) {
        this.ordeidString = ordeidString;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }
}
