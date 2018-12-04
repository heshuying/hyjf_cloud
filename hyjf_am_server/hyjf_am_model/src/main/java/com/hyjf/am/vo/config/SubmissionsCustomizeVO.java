package com.hyjf.am.vo.config;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyjf.common.util.GetDate;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author lisheng
 * @version SubmissionsCustomizeVO, v0.1 2018/7/13 17:03
 */

public class SubmissionsCustomizeVO  {
    @ApiModelProperty(value = "图片地址")
    private String img;
    @ApiModelProperty(value = "客服回复")
    private String reply;

    @ApiModelProperty(value = "反馈id")
    private String submissionsId;

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 系统类别
     */
    @ApiModelProperty(value = "系统类别")
    private String sysType;

    /**
     * 系统版本号
     */
    @ApiModelProperty(value = "系统版本号")
    private String sysVersion;

    /**
     * 平台版本号
     */
    @ApiModelProperty(value = "平台版本号")
    private String platformVersion;

    /**
     * 手机型号
     */
    @ApiModelProperty(value = "手机型号")
    private String phoneType;

    /**
     * 回复内容
     */
    @ApiModelProperty(value = "回复内容")
    private String content;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone="GMT+8")
    private Date addTime;

    /**
     * 处理状态
     */
    @ApiModelProperty(value = "处理状态")
    private String subState;

    /**
     * 用戶id
     */
    @ApiModelProperty(value = "用戶id")
    private Integer userId;

    @ApiModelProperty(value = "状态 0未审 1已审核")
    private String status;

    private String createTime;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getSubmissionsId() {
        return submissionsId;
    }

    public void setSubmissionsId(String submissionsId) {
        this.submissionsId = submissionsId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public String getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(String phoneType) {
        this.phoneType = phoneType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreateTime() {
        return GetDate.date2Str(addTime, GetDate.datetimeFormat);
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }



}
