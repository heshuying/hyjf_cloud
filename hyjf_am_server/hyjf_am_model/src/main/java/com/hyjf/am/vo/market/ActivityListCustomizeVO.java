package com.hyjf.am.vo.market;

import io.swagger.models.auth.In;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 13:55
 * @Description: ActivityListCustomizeVO
 */
public class ActivityListCustomizeVO extends ActivityListVO {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    protected Integer startTime;

    protected Integer endTime;

    protected Integer limitStart = -1;

    protected Integer limitEnd = -1;

    protected Integer nowTime;
    protected String platform; //平台


    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(Integer limitStart) {
        this.limitStart = limitStart;
    }

    public Integer getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(Integer limitEnd) {
        this.limitEnd = limitEnd;
    }

    public Integer getNowTime() {
        return nowTime;
    }

    public void setNowTime(Integer nowTime) {
        this.nowTime = nowTime;
    }

    @Override
    public String getPlatform() {
        return platform;
    }

    @Override
    public void setPlatform(String platform) {
        this.platform = platform;
    }
}
