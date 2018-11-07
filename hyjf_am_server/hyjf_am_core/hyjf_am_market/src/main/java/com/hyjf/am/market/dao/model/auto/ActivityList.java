package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ActivityList implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 活动名称
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 活动开始时间
     *
     * @mbggenerated
     */
    private Integer timeStart;

    /**
     * 活动结束时间
     *
     * @mbggenerated
     */
    private Integer timeEnd;

    private String imgPc;

    private String imgApp;

    private String imgWei;

    private String activityPcUrl;

    private String activityAppUrl;

    private String activityWeiUrl;

    /**
     * 主图
     *
     * @mbggenerated
     */
    private String img;

    /**
     * 二维码
     *
     * @mbggenerated
     */
    private String qr;

    /**
     * 平台
     *
     * @mbggenerated
     */
    private String platform;

    /**
     * 前台地址
     *
     * @mbggenerated
     */
    private String urlForeground;

    /**
     * 后台管理地址
     *
     * @mbggenerated
     */
    private String urlBackground;

    /**
     * 描述
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 创建时间
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Integer timeStart) {
        this.timeStart = timeStart;
    }

    public Integer getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Integer timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getImgPc() {
        return imgPc;
    }

    public void setImgPc(String imgPc) {
        this.imgPc = imgPc == null ? null : imgPc.trim();
    }

    public String getImgApp() {
        return imgApp;
    }

    public void setImgApp(String imgApp) {
        this.imgApp = imgApp == null ? null : imgApp.trim();
    }

    public String getImgWei() {
        return imgWei;
    }

    public void setImgWei(String imgWei) {
        this.imgWei = imgWei == null ? null : imgWei.trim();
    }

    public String getActivityPcUrl() {
        return activityPcUrl;
    }

    public void setActivityPcUrl(String activityPcUrl) {
        this.activityPcUrl = activityPcUrl == null ? null : activityPcUrl.trim();
    }

    public String getActivityAppUrl() {
        return activityAppUrl;
    }

    public void setActivityAppUrl(String activityAppUrl) {
        this.activityAppUrl = activityAppUrl == null ? null : activityAppUrl.trim();
    }

    public String getActivityWeiUrl() {
        return activityWeiUrl;
    }

    public void setActivityWeiUrl(String activityWeiUrl) {
        this.activityWeiUrl = activityWeiUrl == null ? null : activityWeiUrl.trim();
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img == null ? null : img.trim();
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr == null ? null : qr.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getUrlForeground() {
        return urlForeground;
    }

    public void setUrlForeground(String urlForeground) {
        this.urlForeground = urlForeground == null ? null : urlForeground.trim();
    }

    public String getUrlBackground() {
        return urlBackground;
    }

    public void setUrlBackground(String urlBackground) {
        this.urlBackground = urlBackground == null ? null : urlBackground.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
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