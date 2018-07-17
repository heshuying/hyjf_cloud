package com.hyjf.am.resquest.market;

import com.hyjf.am.vo.BasePage;

/**
 * @author lisheng
 * @version AppBannerRequest, v0.1 2018/7/11 14:30
 */

public class AppBannerRequest extends BasePage {
    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032581L;

    /**
     * 前台时间接收
     */
    private String startCreate;

    private String endCreate;

    private String ids;

    private String content;

    private String shareContent;

    private Short id;

    private String name; //广告名称

    private Integer typeid; // 广告类型

    private String url;

    private String code;

    private String image;

    private Short order;

    private Integer hits;

    private Integer status;//广告状态

    private String shareUrl;

    private String shareImage;

    private Short isIndex;

    private Integer createTime;

    private Integer updateTime;

    private String startTime;

    private String endTime;

    private Integer isEnd;

    private String shareTitle;

    private String activitiImage;

    private String activitiDesc;

    private Integer clientType;

    private Integer newuserShow;


    public Short getId() {
        return id;
    }

    public void setId(Short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getTypeid() {
        return typeid;
    }

    public void setTypeid(Integer typeid) {
        this.typeid = typeid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }

    public Short getOrder() {
        return order;
    }

    public void setOrder(Short order) {
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

    public Short getIsIndex() {
        return isIndex;
    }

    public void setIsIndex(Short isIndex) {
        this.isIndex = isIndex;
    }

    public Integer getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Integer createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
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

    public Integer getClientType() {
        return clientType;
    }

    public void setClientType(Integer clientType) {
        this.clientType = clientType;
    }

    public Integer getNewuserShow() {
        return newuserShow;
    }

    public void setNewuserShow(Integer newuserShow) {
        this.newuserShow = newuserShow;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getShareContent() {
        return shareContent;
    }

    public void setShareContent(String shareContent) {
        this.shareContent = shareContent == null ? null : shareContent.trim();
    }
    public String getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(String startCreate) {
        this.startCreate = startCreate;
    }

    public String getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(String endCreate) {
        this.endCreate = endCreate;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }
}
