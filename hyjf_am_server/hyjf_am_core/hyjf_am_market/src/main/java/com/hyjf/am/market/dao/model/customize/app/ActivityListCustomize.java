/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.market.dao.model.customize.app;

import com.hyjf.am.market.dao.model.auto.ActivityList;

/**
 * @author yaoyong
 * @version ActivityListCustomize, v0.1 2018/7/24 15:42
 */
public class ActivityListCustomize extends ActivityList {

    private static final long serialVersionUID = 1L;

    // 传参创建时间范围
    protected int startCreate;

    protected int endCreate;

    protected int startTime;

    protected int endTime;

    protected int limitStart = -1;

    protected int limitEnd = -1;

    protected Integer nowTime;
    protected String platform; //平台

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

    @Override
    public String getPlatform() {
        return platform;
    }

    @Override
    public void setPlatform(String platform) {
        this.platform = platform;
    }

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
}
