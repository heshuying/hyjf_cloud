package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BindUser implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer bindUniqueId;

    private Integer bindPlatformId;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

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

    public Integer getBindUniqueId() {
        return bindUniqueId;
    }

    public void setBindUniqueId(Integer bindUniqueId) {
        this.bindUniqueId = bindUniqueId;
    }

    public Integer getBindPlatformId() {
        return bindPlatformId;
    }

    public void setBindPlatformId(Integer bindPlatformId) {
        this.bindPlatformId = bindPlatformId;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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