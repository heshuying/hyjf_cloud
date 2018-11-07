package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class PreRegist implements Serializable {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 手机号
     *
     * @mbggenerated
     */
    private String mobile;

    /**
     * 推荐人
     *
     * @mbggenerated
     */
    private String referrer;

    /**
     * 关键词ID
     *
     * @mbggenerated
     */
    private Integer utmId;

    /**
     * 渠道ID
     *
     * @mbggenerated
     */
    private Integer sourceId;

    /**
     * 预注册时间
     *
     * @mbggenerated
     */
    private Integer preRegistTime;

    /**
     * 是否已注册 0:否,1:是
     *
     * @mbggenerated
     */
    private Integer registFlag;

    /**
     * 注册时间
     *
     * @mbggenerated
     */
    private Integer registTime;

    /**
     * 平台ID
     *
     * @mbggenerated
     */
    private String platformId;

    /**
     * 平台名称
     *
     * @mbggenerated
     */
    private String platformName;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

    /**
     * 创建者
     *
     * @mbggenerated
     */
    private Integer createUser;

    /**
     * 更新者
     *
     * @mbggenerated
     */
    private Integer updateUser;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getReferrer() {
        return referrer;
    }

    public void setReferrer(String referrer) {
        this.referrer = referrer == null ? null : referrer.trim();
    }

    public Integer getUtmId() {
        return utmId;
    }

    public void setUtmId(Integer utmId) {
        this.utmId = utmId;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public Integer getPreRegistTime() {
        return preRegistTime;
    }

    public void setPreRegistTime(Integer preRegistTime) {
        this.preRegistTime = preRegistTime;
    }

    public Integer getRegistFlag() {
        return registFlag;
    }

    public void setRegistFlag(Integer registFlag) {
        this.registFlag = registFlag;
    }

    public Integer getRegistTime() {
        return registTime;
    }

    public void setRegistTime(Integer registTime) {
        this.registTime = registTime;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId == null ? null : platformId.trim();
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName == null ? null : platformName.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Integer getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(Integer updateUser) {
        this.updateUser = updateUser;
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