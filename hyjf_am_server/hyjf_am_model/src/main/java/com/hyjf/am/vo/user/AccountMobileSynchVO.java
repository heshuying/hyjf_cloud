/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import com.hyjf.am.vo.BaseVO;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author wangjun
 * @version AccountMobileSynchVO, v0.1 2018/6/22 14:29
 */
public class AccountMobileSynchVO extends BaseVO implements Serializable {
    @ApiModelProperty(value = "银行卡号手机号同步表主键id")
    private Integer id;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "电子账号")
    private String accountid;
    @ApiModelProperty(value = "原银行卡号")
    private String account;
    @ApiModelProperty(value = "新银行卡号")
    private String newAccount;
    @ApiModelProperty(value = "原手机号")
    private String mobile;
    @ApiModelProperty(value = "新手机号")
    private String newMobile;
    @ApiModelProperty(value = "查询次数")
    private Integer searchtime;
    @ApiModelProperty(value = "同步状态：0：未同步1：已同步")
    private Integer status;
    @ApiModelProperty(value = "同步标识：1：手机号同步，2：银行卡同步")
    private Integer flag;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid == null ? null : accountid.trim();
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getNewAccount() {
        return newAccount;
    }

    public void setNewAccount(String newAccount) {
        this.newAccount = newAccount == null ? null : newAccount.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getNewMobile() {
        return newMobile;
    }

    public void setNewMobile(String newMobile) {
        this.newMobile = newMobile == null ? null : newMobile.trim();
    }

    public Integer getSearchtime() {
        return searchtime;
    }

    public void setSearchtime(Integer searchtime) {
        this.searchtime = searchtime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
