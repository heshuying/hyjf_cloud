package com.hyjf.am.vo.config;

/**
 * @author lisheng
 * @version SubmissionsCustomizeVO, v0.1 2018/7/13 17:03
 */

public class SubmissionsCustomizeVO {
    private String submissionsId;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 系统类别
     */
    private String sysType;

    /**
     * 系统版本号
     */
    private String sysVersion;

    /**
     * 平台版本号
     */
    private String platformVersion;

    /**
     * 手机型号
     */
    private String phoneType;

    /**
     * 回复内容
     */
    private String content;

    /**
     * 添加时间
     */
    private String addTime;

    /**
     * 处理状态
     */
    private String subState;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getSysType() {
        return sysType;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getSubState() {
        return subState;
    }

    public void setSubState(String subState) {
        this.subState = subState;
    }

    public String getSubmissionsId() {
        return submissionsId;
    }

    public void setSubmissionsId(String submissionsId) {
        this.submissionsId = submissionsId;
    }

    public String getSysVersion() {
        return sysVersion;
    }

    public void setSysVersion(String sysVersion) {
        this.sysVersion = sysVersion;
    }
}
