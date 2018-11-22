package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Author : huanghui
 */
public class AppPushManageRequest extends BasePage implements Serializable {

    private Integer id;

    private String title;

    private Integer jumpType;

    private Integer jumpContent;

    private String jumpUrl;

    private Integer orderId;

    private Integer status;

    private String timeStart;

    private String timeEnd;

    private String timeStartDiy;

    private String timeEndDiy;

    private Integer createUserId;

    private Date createTime;

    private Date updateTime;

    private String thumb;

    private String content;

    /**
     * 分页变量
     */
    private int limitStart = -1;

    private int limitEnd = -1;

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

    public Integer getJumpType() {
        return jumpType;
    }

    public void setJumpType(Integer jumpType) {
        this.jumpType = jumpType;
    }

    public Integer getJumpContent() {
        return jumpContent;
    }

    public void setJumpContent(Integer jumpContent) {
        this.jumpContent = jumpContent;
    }

    public String getJumpUrl() {
        return jumpUrl;
    }

    public void setJumpUrl(String jumpUrl) {
        this.jumpUrl = jumpUrl;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getTimeStartDiy() {
        return timeStartDiy;
    }

    public void setTimeStartDiy(String timeStartDiy) {
        this.timeStartDiy = timeStartDiy;
    }

    public String getTimeEndDiy() {
        return timeEndDiy;
    }

    public void setTimeEndDiy(String timeEndDiy) {
        this.timeEndDiy = timeEndDiy;
    }
}
