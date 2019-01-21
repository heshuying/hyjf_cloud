package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BankRepayFreezeLog implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户userId
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 客户号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 客户号
     *
     * @mbggenerated
     */
    private String orderId;

    /**
     * 冻结金额
     *
     * @mbggenerated
     */
    private BigDecimal amount;

    /**
     * 客户号
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 客户号
     *
     * @mbggenerated
     */
    private String borrowNid;

    /**
     * 是否有效 0有效 1无效记录
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建用户userId
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建用户名
     *
     * @mbggenerated
     */
    private String createUserName;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 更新用户userid
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 更新用户名
     *
     * @mbggenerated
     */
    private String updateUserName;

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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId == null ? null : orderId.trim();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getBorrowNid() {
        return borrowNid;
    }

    public void setBorrowNid(String borrowNid) {
        this.borrowNid = borrowNid == null ? null : borrowNid.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        this.updateUserName = updateUserName == null ? null : updateUserName.trim();
    }
}