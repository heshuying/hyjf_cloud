/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.cs.market.bean;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author fq
 * @version AppContentArticleBean, v0.1 2018/7/20 9:38
 */
public class AppContentArticleBean extends AppBaseBean implements Serializable {

    @ApiModelProperty(value = "消息id", required = true)
    private Integer messageId;
    private Integer page=1;
    private Integer size=10;

    @ApiModelProperty(value = "代表当前网贷知识之前为0，之后为1", required = true)
    private String offset;

    @ApiModelProperty(value = "文章管理主键")
    private Integer id;

    @ApiModelProperty(value = "文章分类")
    private String type;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "状态0关闭，1启用")
    private Integer status;

    @ApiModelProperty(value = "文章作者")
    private String author;

    @ApiModelProperty(value = "创建人")
    private Integer createBy;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(value = "修改人")
    private Integer updateBy;

    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @ApiModelProperty(value = "文章图片")
    private String imgurl;

    @ApiModelProperty(value = "简介")
    private String summary;

    @ApiModelProperty(value = "点击率")
    private Integer click;

    @ApiModelProperty(value = "文章内容")
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

    public Integer getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Integer createBy) {
        this.createBy = createBy;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(Integer updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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

    public Integer getPage() {
        return page;
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public Integer getSize() {
        return size;
    }
    public void setSize(Integer size) {
        this.size = size;
    }
    public Integer getMessageId() {
        return messageId;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public String getOffset() {
        return offset;
    }
    public void setOffset(String offset) {
        this.offset = offset;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
