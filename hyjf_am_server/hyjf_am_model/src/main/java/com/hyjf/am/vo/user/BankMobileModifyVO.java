/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.user;

import java.util.Date;

/**
 * @author PC-LIUSHOUYI
 * @version BankMobileModifyVO, v0.1 2019/5/9 15:33
 */
public class BankMobileModifyVO {

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
        this.account = account;
    }

    public String getBankMobileOld() {
        return bankMobileOld;
    }

    public void setBankMobileOld(String bankMobileOld) {
        this.bankMobileOld = bankMobileOld;
    }

    public String getBankMobileNew() {
        return bankMobileNew;
    }

    public void setBankMobileNew(String bankMobileNew) {
        this.bankMobileNew = bankMobileNew;
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
        this.createBy = createBy;
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
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
