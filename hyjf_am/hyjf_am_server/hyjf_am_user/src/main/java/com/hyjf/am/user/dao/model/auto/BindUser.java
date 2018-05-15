package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BindUser implements Serializable {
    private Integer id;

    private Integer userId;

    private Long bindUniqueId;

    private Integer bindPlatformId;

    private Integer delFlg;

    private Integer createUserId;

    private Date createTime;

    private Integer updateUserId;

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

    public Long getBindUniqueId() {
        return bindUniqueId;
    }

    public void setBindUniqueId(Long bindUniqueId) {
        this.bindUniqueId = bindUniqueId;
    }

    public Integer getBindPlatformId() {
        return bindPlatformId;
    }

    public void setBindPlatformId(Integer bindPlatformId) {
        this.bindPlatformId = bindPlatformId;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}