package com.hyjf.am.vo.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author: yinhui
 * @Date: 2019/4/15 11:14
 * @Version 1.0
 */
public class WorkNameVO  extends BaseVO implements Serializable {
    private static final long serialVersionUID = 1L;

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

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date updateTime;

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
