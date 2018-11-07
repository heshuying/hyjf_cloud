package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class Ads implements Serializable {
    private Integer id;

    private String name;

    /**
     * 广告类型
     *
     * @mbggenerated
     */
    private Integer typeId;

    private String url;

    /**
     * 调用代码
     *
     * @mbggenerated
     */
    private String code;

    /**
     * 内容
     *
     * @mbggenerated
     */
    private String content;

    private String image;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer order;

    private Integer hits;

    private Integer status;

    /**
     * 分享url
     *
     * @mbggenerated
     */
    private String shareUrl;

    /**
     * 分享图片url
     *
     * @mbggenerated
     */
    private String shareImage;

    /**
     * 是否在首页特色banner位置显示，0为不显示，1显示
     *
     * @mbggenerated
     */
    private Integer isIndex;

    private String startTime;

    private String endTime;

    /**
     * 是否已结束(只针对活动banner有效0:否,1:是)
     *
     * @mbggenerated
     */
    private Integer isEnd;

    private String shareTitle;

    /**
     * 活动列表图
     *
     * @mbggenerated
     */
    private String activitiImage;

    /**
     * 活动描述
     *
     * @mbggenerated
     */
    private String activitiDesc;

    /**
     * 共享内容
     *
     * @mbggenerated
     */
    private String shareContent;

    /**
     * 客户端类型 0为pc广告  1为手机广告
     *
     * @mbggenerated
     */
    private Integer clientType;

    /**
     * 是否限制新手 1：限制 2：不限制
     *
     * @mbggenerated
     */
    private Integer newUserShow;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    /**
     * 拆分状态 1：android广告管理 2：ios广告管理 3: 微信广告管理
     *
     * @mbggenerated
     */
    private Integer platformType;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl == null ? null : shareUrl.trim();
    }

    public String getShareImage() {
        return shareImage;
    }

    public void setShareImage(String shareImage) {
        this.shareImage = shareImage == null ? null : shareImage.trim();
    }

    public Integer getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Integer isIndex) {
        this.isIndex = isIndex;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime == null ? null : endTime.trim();
    }

    public Integer getIsEnd() {
        return isEnd;
    }

    public void setIsEnd(Integer isEnd) {
        this.isEnd = isEnd;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle == null ? null : shareTitle.trim();
    }

    public String getActivitiImage() {
        return activitiImage;
    }

    public void setActivitiImage(String activitiImage) {
        this.activitiImage = activitiImage == null ? null : activitiImage.trim();
    }

    public String getActivitiDesc() {
        return activitiDesc;
    }

    public void setActivitiDesc(String activitiDesc) {
        this.activitiDesc = activitiDesc == null ? null : activitiDesc.trim();
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? null : shareContent.trim();
    }

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getNewUserShow() {
        return newUserShow;
    }

    public void setNewUserShow(Integer newUserShow) {
        this.newUserShow = newUserShow;
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

    public Integer getPlatformType() {
        return platformType;
    }

    public void setPlatformType(Integer platformType) {
        this.platformType = platformType;
    }
}