package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class AppPushManage implements Serializable {
    private Integer id;

    /**
     * 标题名称
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 跳转类型:0:原生;1:H5;
     *
     * @mbggenerated
     */
    private Integer jumpType;

    /**
     * 跳转内容:原生,0;H5 URL,1;H5自定义:2;
     *
     * @mbggenerated
     */
    private Integer jumpContent;

    /**
     * 跳转URL
     *
     * @mbggenerated
     */
    private String jumpUrl;

    /**
     * 排序
     *
     * @mbggenerated
     */
    private Integer orderId;

    /**
     * 0:禁用;1:启用.
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 开始时间
     *
     * @mbggenerated
     */
    private Date timeStart;

    /**
     * 结束时间
     *
     * @mbggenerated
     */
    private Date timeEnd;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 最后修改时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 缩略图
     *
     * @mbggenerated
     */
    private String thumb;

    /**
     * 内容
     *
     * @mbggenerated
     */
    private String content;

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
        this.jumpUrl = jumpUrl == null ? null : jumpUrl.trim();
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

    public Date getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Date timeStart) {
        this.timeStart = timeStart;
    }

    public Date getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Date timeEnd) {
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
        this.thumb = thumb == null ? null : thumb.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}