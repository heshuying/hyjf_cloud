package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BankMobileModify implements Serializable {
    private Integer id;

    private Integer userId;

    /**
     * 用户客户号
     *
     * @mbggenerated
     */
    private String account;

    /**
     * 修改前银行预留手机号
     *
     * @mbggenerated
     */
    private String bankMobileOld;

    /**
     * 修改后银行预留手机号
     *
     * @mbggenerated
     */
    private String bankMobileNew;

    /**
     * 0初始 1成功 
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private String createBy;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private String updateBy;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getBankMobileOld() {
        return bankMobileOld;
    }

    public void setBankMobileOld(String bankMobileOld) {
        this.bankMobileOld = bankMobileOld == null ? null : bankMobileOld.trim();
    }

    public String getBankMobileNew() {
        return bankMobileNew;
    }

    public void setBankMobileNew(String bankMobileNew) {
        this.bankMobileNew = bankMobileNew == null ? null : bankMobileNew.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}