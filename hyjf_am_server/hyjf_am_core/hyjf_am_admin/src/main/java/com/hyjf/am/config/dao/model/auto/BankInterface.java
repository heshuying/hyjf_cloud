package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BankInterface implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 银行接口类型
     *
     * @mbggenerated
     */
    private String interfaceType;

    /**
     * 银行接口类型名称
     *
     * @mbggenerated
     */
    private String interfaceName;

    /**
     * 银行接口使用状态(0:老接口，1:新接口)
     *
     * @mbggenerated
     */
    private Integer interfaceStatus;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建用户id
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
     * 更新用户id
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

    /**
     * 是否可用 0否 1是
     *
     * @mbggenerated
     */
    private Integer isUsable;

    /**
     * 是否删除 0否 1是
     *
     * @mbggenerated
     */
    private Integer isDelete;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(String interfaceType) {
        this.interfaceType = interfaceType == null ? null : interfaceType.trim();
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName == null ? null : interfaceName.trim();
    }

    public Integer getInterfaceStatus() {
        return interfaceStatus;
    }

    public void setInterfaceStatus(Integer interfaceStatus) {
        this.interfaceStatus = interfaceStatus;
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

    public Integer getIsUsable() {
        return isUsable;
    }

    public void setIsUsable(Integer isUsable) {
        this.isUsable = isUsable;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}