/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.user;

import java.util.Date;

/**
 * 用户销户Request
 *
 * @author liuyang
 * @version BankCancellationAccountRequest, v0.1 2019/3/31 9:49
 */
public class BankCancellationAccountRequest {

    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String username;

    /**
     * 销户前手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 是否银行开户,0未开户,1已开户
     *
     * @mbggenerated
     */
    private Integer bankOpenAccount;

    /**
     * 真实姓名
     *
     * @mbggenerated
     */
    private String truename;

    /**
     * 身份证号
     *
     * @mbggenerated
     */
    private String idcard;

    /**
     * 销户前银行账号
     *
     * @mbggenerated
     */
    private String cardNo;

    /**
     * 用户客户号
     *
     * @mbggenerated
     */
    private String bankAccount;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private Date regTime;

    /**
     * 创建者用户ID
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建者
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新者用户ID
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新者
     *
     * @mbggenerated
     */
    private String updateUserName;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

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
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getBankOpenAccount() {
        return bankOpenAccount;
    }

    public void setBankOpenAccount(Integer bankOpenAccount) {
        this.bankOpenAccount = bankOpenAccount;
    }

    public String getTruename() {
        return truename;
    }

    public void setTruename(String truename) {
        this.truename = truename;
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public String getUpdateUserName() {
        return updateUserName;
    }

    public void setUpdateUserName(String updateUserName) {
        this.updateUserName = updateUserName;
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
