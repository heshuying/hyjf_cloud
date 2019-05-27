/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.trade;

import com.hyjf.am.vo.BasePage;

/**
 * 项目列表请求参数
 *
 * @author liuyang
 * @version ProjectListRequest, v0.1 2018/6/13 10:58
 */
public class ProjectListRequest extends BasePage {

    private String projectType;

    private String borrowClass;

    private Integer limitStart;

    private Integer limitEnd;

    private String platform;

    private String type;

    private String host;
    /**
     * app端计划列表查询使用
     */
    private String isHome;

    /**
     * app债转列表用
     */
    private String creditStatus;

    private String status;

    private String publishInstCode;

    private String wjtInstCode;

    /**
     * 翻页机能用的隐藏变量（app原来的参数）
     */
    private int page = 1;

    public int getPage() {
        if (page == 0) {
            page = 1;
        }
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }


    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getBorrowClass() {
        return borrowClass;
    }

    public void setBorrowClass(String borrowClass) {
        this.borrowClass = borrowClass;
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

    public String getIsHome() {
        return isHome;
    }

    public void setIsHome(String isHome) {
        this.isHome = isHome;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }


    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPublishInstCode() {
        return publishInstCode;
    }

    public void setPublishInstCode(String publishInstCode) {
        this.publishInstCode = publishInstCode;
    }

    public String getWjtInstCode() {
        return wjtInstCode;
    }

    public void setWjtInstCode(String wjtInstCode) {
        this.wjtInstCode = wjtInstCode;
    }
}
