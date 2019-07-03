package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class WorkName implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 业务名称
     *
     * @mbggenerated
     */
    private String workName;

    /**
     * 业务主管姓名
     *
     * @mbggenerated
     */
    private String chargeName;

    /**
     * 业务主管邮件
     *
     * @mbggenerated
     */
    private String chargeMail;

    /**
     * 1可用，2禁用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 创建操作人
     *
     * @mbggenerated
     */
    private String createUser;

    /**
     * 更新操作人
     *
     * @mbggenerated
     */
    private String updateUser;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 创建时间
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

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getChargeName() {
        return chargeName;
    }

    public void setChargeName(String chargeName) {
        this.chargeName = chargeName == null ? null : chargeName.trim();
    }

    public String getChargeMail() {
        return chargeMail;
    }

    public void setChargeMail(String chargeMail) {
        this.chargeMail = chargeMail == null ? null : chargeMail.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
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