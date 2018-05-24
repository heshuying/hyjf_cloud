package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SmsConfig implements Serializable {
    private Integer id;

    private Integer maxIpCount;

    private Integer maxMachineCount;

    private Integer maxBrowserCount;

    private Integer maxPhoneCount;

    private Integer maxIntervalTime;

    private Integer maxValidTime;

    private String noticeToPhone;

    private String noticeToEmail;

    private Integer noticeToTime;

    private String repayMobiles;

    private String fullMobiles;

    private Integer createUserid;

    private Integer updateUserid;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMaxIpCount() {
        return maxIpCount;
    }

    public void setMaxIpCount(Integer maxIpCount) {
        this.maxIpCount = maxIpCount;
    }

    public Integer getMaxMachineCount() {
        return maxMachineCount;
    }

    public void setMaxMachineCount(Integer maxMachineCount) {
        this.maxMachineCount = maxMachineCount;
    }

    public Integer getMaxBrowserCount() {
        return maxBrowserCount;
    }

    public void setMaxBrowserCount(Integer maxBrowserCount) {
        this.maxBrowserCount = maxBrowserCount;
    }

    public Integer getMaxPhoneCount() {
        return maxPhoneCount;
    }

    public void setMaxPhoneCount(Integer maxPhoneCount) {
        this.maxPhoneCount = maxPhoneCount;
    }

    public Integer getMaxIntervalTime() {
        return maxIntervalTime;
    }

    public void setMaxIntervalTime(Integer maxIntervalTime) {
        this.maxIntervalTime = maxIntervalTime;
    }

    public Integer getMaxValidTime() {
        return maxValidTime;
    }

    public void setMaxValidTime(Integer maxValidTime) {
        this.maxValidTime = maxValidTime;
    }

    public String getNoticeToPhone() {
        return noticeToPhone;
    }

    public void setNoticeToPhone(String noticeToPhone) {
        this.noticeToPhone = noticeToPhone == null ? null : noticeToPhone.trim();
    }

    public String getNoticeToEmail() {
        return noticeToEmail;
    }

    public void setNoticeToEmail(String noticeToEmail) {
        this.noticeToEmail = noticeToEmail == null ? null : noticeToEmail.trim();
    }

    public Integer getNoticeToTime() {
        return noticeToTime;
    }

    public void setNoticeToTime(Integer noticeToTime) {
        this.noticeToTime = noticeToTime;
    }

    public String getRepayMobiles() {
        return repayMobiles;
    }

    public void setRepayMobiles(String repayMobiles) {
        this.repayMobiles = repayMobiles == null ? null : repayMobiles.trim();
    }

    public String getFullMobiles() {
        return fullMobiles;
    }

    public void setFullMobiles(String fullMobiles) {
        this.fullMobiles = fullMobiles == null ? null : fullMobiles.trim();
    }

    public Integer getCreateUserid() {
        return createUserid;
    }

    public void setCreateUserid(Integer createUserid) {
        this.createUserid = createUserid;
    }

    public Integer getUpdateUserid() {
        return updateUserid;
    }

    public void setUpdateUserid(Integer updateUserid) {
        this.updateUserid = updateUserid;
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