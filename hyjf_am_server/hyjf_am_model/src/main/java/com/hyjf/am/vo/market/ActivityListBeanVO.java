package com.hyjf.am.vo.market;

import java.io.Serializable;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/26 11:00
 * @Description: ActivityListBeanVO
 */
public class ActivityListBeanVO implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private Integer timeStart;//活动开始时间
    private Integer timeEnd;
    private String title;//活动标题
    private String img;//活动图片url
    private String status;//活动状态
    private String urlForeground;//:活动的url

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
}
