/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.market;

import java.util.List;

/**
 * @author yaoy
 * @version ActivityListRequest, v0.1 2018/6/26 17:03
 */
public class ActivityListRequest {

    private int id;
    //活动名称
    private String title;
    /**
     * 前台时间接收
     */
    private int startTime;

    private int endTime;

    private String startCreate;

    private String endCreate;

    private String platform;

    private String imgPc;

    private String imgApp;

    private String imgWei;

    private String activityPcUrl;

    private String activityAppUrl;

    private String activityWeiUrl;

    private String img;

    private String qr;

    private String status;

    private String urlForeground;

    private String urlBackground;

    private String description;

    private int limit;

    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;
    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
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

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrlForeground() {
        return urlForeground;
    }

    public void setUrlForeground(String urlForeground) {
        this.urlForeground = urlForeground;
    }

    public String getImgPc() {
        return imgPc;
    }

    public void setImgPc(String imgPc) {
        this.imgPc = imgPc;
    }

    public String getImgApp() {
        return imgApp;
    }

    public void setImgApp(String imgApp) {
        this.imgApp = imgApp;
    }

    public String getImgWei() {
        return imgWei;
    }

    public void setImgWei(String imgWei) {
        this.imgWei = imgWei;
    }

    public String getActivityPcUrl() {
        return activityPcUrl;
    }

    public void setActivityPcUrl(String activityPcUrl) {
        this.activityPcUrl = activityPcUrl;
    }

    public String getActivityAppUrl() {
        return activityAppUrl;
    }

    public void setActivityAppUrl(String activityAppUrl) {
        this.activityAppUrl = activityAppUrl;
    }

    public String getActivityWeiUrl() {
        return activityWeiUrl;
    }

    public void setActivityWeiUrl(String activityWeiUrl) {
        this.activityWeiUrl = activityWeiUrl;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getUrlBackground() {
        return urlBackground;
    }

    public void setUrlBackground(String urlBackground) {
        this.urlBackground = urlBackground;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}