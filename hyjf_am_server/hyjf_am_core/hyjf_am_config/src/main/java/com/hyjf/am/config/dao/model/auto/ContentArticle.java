package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ContentArticle implements Serializable {
    /**
     * 文章管理主键
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 文章分类
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 文章标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 状态0关闭，1启用
     *
     * @mbggenerated
     */
    private Integer status;

    /**
     * 文章作者
     *
     * @mbggenerated
     */
    private String author;

    /**
     * 文章图片
     *
     * @mbggenerated
     */
    private String imgurl;

    /**
     * 简介
     *
     * @mbggenerated
     */
    private String summary;

    /**
     * 点击率
     *
     * @mbggenerated
     */
    private Integer click;

    /**
     * 文章内容
     *
     * @mbggenerated
     */
    private String content;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author == null ? null : author.trim();
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl == null ? null : imgurl.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }

    public Integer getClick() {
        return click;
    }

    public void setClick(Integer click) {
        this.click = click;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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