package com.hyjf.am.resquest.admin;

import com.hyjf.am.resquest.Request;

import java.io.Serializable;

/**
 * @author xiehuili on 2018/8/14.
 */
public class SmsConfigRequest extends Request implements Serializable {
    private String repayMobiles;

    private String fullMobiles;

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

    private static final long serialVersionUID = 1L;

    public String getRepayMobiles() {
        return repayMobiles;
    }

    public void setRepayMobiles(String repayMobiles) {
        this.repayMobiles = repayMobiles;
    }

    public String getFullMobiles() {
        return fullMobiles;
    }

    public void setFullMobiles(String fullMobiles) {
        this.fullMobiles = fullMobiles;
    }

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
        this.noticeToPhone = noticeToPhone;
    }

    public String getNoticeToEmail() {
        return noticeToEmail;
    }

    public void setNoticeToEmail(String noticeToEmail) {
        this.noticeToEmail = noticeToEmail;
    }

    public Integer getNoticeToTime() {
        return noticeToTime;
    }

    public void setNoticeToTime(Integer noticeToTime) {
        this.noticeToTime = noticeToTime;
    }
}
