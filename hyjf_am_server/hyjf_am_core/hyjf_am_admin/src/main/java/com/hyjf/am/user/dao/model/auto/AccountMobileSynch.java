package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AccountMobileSynch implements Serializable {
    private Integer id;

    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 姓名
     *
     * @mbggenerated
     */
    private String name;

    /**
     * 电子账号
     *
     * @mbggenerated
     */
    private String accountid;

    /**
     * 银行卡号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 新银行卡号
     *
     * @mbggenerated
     */
    private String newAccount;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 新手机号
     *
     * @mbggenerated
     */
    private String newMobile;

    /**
     * 查询次数
     *
     * @mbggenerated
     */
    private Integer searchtime;

    /**
     * 同步状态：0：未同步1：已同步
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 同步标识：1：手机号同步，2：银行卡同步
     *
     * @mbggenerated
     */
    private Integer flag;

    /**
     * 添加时间
     *
     * @mbggenerated
     */
    private Date createTime;

    private Date updateTime;

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}