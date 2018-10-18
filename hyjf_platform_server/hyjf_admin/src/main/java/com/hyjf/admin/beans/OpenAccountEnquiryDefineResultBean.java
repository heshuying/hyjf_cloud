package com.hyjf.admin.beans;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 开户点单查询返回实体
 * @version OpenAccountEnquiryDefineResultBean, v0.1 2018/8/20 15:42
 * @Author: Zha Daojian
 */
public class OpenAccountEnquiryDefineResultBean implements Serializable {

    @ApiModelProperty(value = "手机号")
    private String mobile;
    @ApiModelProperty(value = "身份证")
    private String idcard;
    @ApiModelProperty(value = "查询返回结果,y 是可以提交保存")
    private String status;
    @ApiModelProperty(value = "查询返回结果说明")
    private String result;
    @ApiModelProperty(value = "开户时间")
    private String regTimeEnd;
    @ApiModelProperty(value = "开户状态")
    private String accountStatus;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "用户id")
    private String userid;
    @ApiModelProperty(value = "查询返回结果说明")
    private String ordeidString;
    @ApiModelProperty(value = "交易渠道")
    private String channel;
    @ApiModelProperty(value = "电子账号")
    private String accountId;
    @ApiModelProperty(value = "开户平台")
    private String platform;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "地址")
    private String addr;
    @ApiModelProperty(value = "是否开户  0否1 是")
    private String isOpen;
    @ApiModelProperty(value = "角色")
    private String roleId;


    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRegTimeEnd() {
        return regTimeEnd;
    }

    public void setRegTimeEnd(String regTimeEnd) {
        this.regTimeEnd = regTimeEnd;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getOrdeidString() {
        return ordeidString;
    }

    public void setOrdeidString(String ordeidString) {
        this.ordeidString = ordeidString;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(String isOpen) {
        this.isOpen = isOpen;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }
}
