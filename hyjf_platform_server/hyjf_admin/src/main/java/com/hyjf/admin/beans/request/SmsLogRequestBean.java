/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.admin.beans.request;

/**
 * @author fq
 * @version SmsLogRequestBean, v0.1 2018/8/15 14:03
 */
public class SmsLogRequestBean {

    /**
     * serialVersionUID:
     */

    private static final long serialVersionUID = 1L;

    /**
     * 序号
     */
    private Integer id;

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 全部手机号码
     */
    private String allMobile;

    /**
     * 用户名
     */
    private String username;

    /**
     * 提交状态  0 成功 1 失败  3 全部
     */
    private Integer load_status;

    /**
     * 短信内容
     */
    private String content;


    /**
     * 全部短信内容
     */
    private String allContent;

    /**
     * 全部短信内容大小
     */
    private String contentSize;
    private String referrerName;
    private String attribute;
    private String deptName;
    private String deptId;
    private String initStar;
    private String initEnd;
    /**
     * 发送状态
     */
    private Integer status;

    /**
     * 发送人
     */
    private String sender;

    /**
     * 提交时间
     */
    private Integer posttime;

    /**
     * 提交时间区间查询开始时间
     */
    private String postTimeBegin;

    /**
     * 提交时间区间查询结束时间
     */
    private String postTimeEnd;

    /**
     * 提交时间区间查询开始时间
     */
    private Integer post_time_begin;

    /**
     * 提交时间区间查询结束时间
     */
    private Integer post_time_end;

    /**
     * 短息类型
     */
    private String type;


    /**
     * String类型时间
     */
    private String postString;


    private int limitStart = -1;

    private int limitEnd = -1;

    private String rowrank;//排序类型 asc 还是 desc

    /**
     * @return the allMobile
     */
    public String getAllMobile() {
        return allMobile;
    }

    /**
     * @param allMobile the allMobile to set
     */
    public void setAllMobile(String allMobile) {
        this.allMobile = allMobile;
    }

    /**
     * @return the postString
     */
    public String getPostString() {
        return postString;
    }

    /**
     * @param postString the postString to set
     */
    public void setPostString(String postString) {
        this.postString = postString;
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the limitStart
     */
    public int getLimitStart() {
        return limitStart;
    }

    /**
     * @param limitStart the limitStart to set
     */
    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    /**
     * @return the limitEnd
     */
    public int getLimitEnd() {
        return limitEnd;
    }

    /**
     * @param limitEnd the limitEnd to set
     */
    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return the load_status
     */
    public Integer getLoad_status() {
        return load_status;
    }

    /**
     * @param load_status the load_status to set
     */
    public void setLoad_status(Integer load_status) {
        this.load_status = load_status;
    }

    /**
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * @return the status
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * @return the sender
     */
    public String getSender() {
        return sender;
    }

    /**
     * @param sender the sender to set
     */
    public void setSender(String sender) {
        this.sender = sender;
    }

    /**
     * @return the posttime
     */
    public Integer getPosttime() {
        return posttime;
    }

    /**
     * @param posttime the posttime to set
     */
    public void setPosttime(Integer posttime) {
        this.posttime = posttime;
    }

    public String getPostTimeBegin() {
        return postTimeBegin;
    }

    public void setPostTimeBegin(String postTimeBegin) {
        this.postTimeBegin = postTimeBegin;
    }

    public String getPostTimeEnd() {
        return postTimeEnd;
    }

    public void setPostTimeEnd(String postTimeEnd) {
        this.postTimeEnd = postTimeEnd;
    }

    /**
     * @return the allContent
     */
    public String getAllContent() {
        return allContent;
    }

    /**
     * @param allContent the allContent to set
     */
    public void setAllContent(String allContent) {
        this.allContent = allContent;
    }

    public String getContentSize() {
        return contentSize;
    }

    public void setContentSize(String contentSize) {
        this.contentSize = contentSize;
    }

    public String getReferrerName() {
        return referrerName;
    }

    public void setReferrerName(String referrerName) {
        this.referrerName = referrerName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getInitStar() {
        return initStar;
    }

    public void setInitStar(String initStar) {
        this.initStar = initStar;
    }

    public String getInitEnd() {
        return initEnd;
    }

    public void setInitEnd(String initEnd) {
        this.initEnd = initEnd;
    }

    public String getRowrank() {
        return rowrank;
    }

    public void setRowrank(String rowrank) {
        this.rowrank = rowrank;
    }

    public Integer getPost_time_begin() {
        return post_time_begin;
    }

    public void setPost_time_begin(Integer post_time_begin) {
        this.post_time_begin = post_time_begin;
    }

    public Integer getPost_time_end() {
        return post_time_end;
    }

    public void setPost_time_end(Integer post_time_end) {
        this.post_time_end = post_time_end;
    }
}
