/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.config;

import com.hyjf.am.resquest.admin.Paginator;
import com.hyjf.am.vo.BasePage;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yinhui
 * @version ContentArticleRequest, v0.1 2018/7/18 11:20
 */
public class ContentArticleRequest extends BasePage implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 3803722754627032561L;

    /**
     * 前台时间接收
     */
    private String ids;

    private String startCreate;

    private String endCreate;

    private Integer id;

    private String type;

    private String title;

    private Integer status;

    private String author;

    private Integer createBy;

    private Date createTime;

    private Integer updateBy;

    private Date updateTime;

    private String imgurl;

    private String summary;

    private Integer click;

    private String content;

    private String noticeType;

    private Integer limitStart;

    private Integer limitEnd;

    private Date startCreateTime;

    private Date endCreateTime;
    /**
     * 翻页机能用的隐藏变量
     */
    private int paginatorPage = 1;

    /**
     * 列表画面自定义标签上的用翻页对象：paginator
     */
    private Paginator paginator;

    public int getPaginatorPage() {
        if (paginatorPage == 0) {
            paginatorPage = 1;
        }
        return paginatorPage;
    }

    public void setPaginatorPage(int paginatorPage) {
        this.paginatorPage = paginatorPage;
    }

    public Paginator getPaginator() {
        return paginator;
    }

    public void setPaginator(Paginator paginator) {
        this.paginator = paginator;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
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
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
        this.author = author;
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
        this.imgurl = imgurl;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
        this.content = content;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndCreateTime() {
        return endCreateTime;
    }

    public void setEndCreateTime(Date endCreateTime) {
        this.endCreateTime = endCreateTime;
    }
}
