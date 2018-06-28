package com.hyjf.am.vo.market;

import com.hyjf.am.vo.BaseVO;

/**
 * @author xiasq
 * @version ActivityListVO, v0.1 2018/5/14 16:13
 */
public class ActivityListVO extends BaseVO {
    private int id;
    //活动名称
    private String title;
    /**
     * 前台时间接收
     */
    private int timeStart;

    private int timeEnd;

    private int startCreate;

    private int endCreate;

    private String platform;

    private String status;

    private String urlForeground;

    private int createTime;

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUrlForeground() {
        return urlForeground;
    }

    public void setUrlForeground(String urlForeground) {
        this.urlForeground = urlForeground;
    }

    public int getCreateTime() {
        return createTime;
    }

    public void setCreateTime(int createTime) {
        this.createTime = createTime;
    }
}
