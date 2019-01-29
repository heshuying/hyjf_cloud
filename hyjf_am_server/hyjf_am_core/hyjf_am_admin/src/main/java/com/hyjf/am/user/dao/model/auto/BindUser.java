package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class BindUser implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 用户ID
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 绑定唯一id（第三方提供）
     *
     * @mbggenerated
     */
    private String bindUniqueId;

    /**
     * 绑定用户第三方平台编号 汇晶社：2000000011
     *
     * @mbggenerated
     */
    private Integer bindPlatformId;

    /**
     * 删除标识
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建用户
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 更新用户
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    /**
     * 添加时间
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

    public String getBindUniqueId() {
        return bindUniqueId;
    }

    public void setBindUniqueId(String bindUniqueId) {
        this.bindUniqueId = bindUniqueId == null ? null : bindUniqueId.trim();
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