/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yaoyong
 * @version ActivityListCustomizeVO, v0.1 2018/7/24 15:39
 */
public class ActivityListCustomizeVO extends BaseVO implements Serializable{
    private static final long serialVersionUID = 1L;

    // 传参创建时间范围
    protected int startCreate;

    protected int endCreate;

    protected int startTime;

    protected int endTime;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    protected Integer nowTime;

    private Integer id;

    private String title;

    private Integer timeStart;

    private Integer timeEnd;

    private String imgPc;

    private String imgApp;

    private String imgWei;

    private String activityPcUrl;

    private String activityAppUrl;

    private String activityWeiUrl;

    private String img;

    private String qr;

    private String platform;

    private String urlForeground;

    private String urlBackground;

    private String description;

    private Date createTime;

    private Date updateTime;

    public int getStartCreate() {
        return startCreate;
    }

    public void setStartCreate(int startCreate) {
        this.startCreate = startCreate;
    }

    public int getEndCreate() {
        return endCreate;
    }

    public void setEndCreate(int endCreate) {
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

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getNowTime() {
        return nowTime;
    }

    public void setNowTime(Integer nowTime) {
        this.nowTime = nowTime;
    }

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
        this.title = title;
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

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getUrlForeground() {
        return urlForeground;
    }

    public void setUrlForeground(String urlForeground) {
        this.urlForeground = urlForeground;
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
