package com.hyjf.am.config.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class SmsConfig implements Serializable {
    private Integer id;

    /**
     * 每天最大发送量（同一IP）
     *
     * @mbggenerated
     */
    private Integer maxIpCount;

    /**
     * 每天最大发送量（同一设备）
     *
     * @mbggenerated
     */
    private Integer maxMachineCount;

    /**
     * 每天最大发送量（同一浏览器）
     *
     * @mbggenerated
     */
    private Integer maxBrowserCount;

    /**
     * 每天最大发送量（同一手机号）
     *
     * @mbggenerated
     */
    private Integer maxPhoneCount;

    /**
     * 发送短信的间隔时间(单位：秒)
     *
     * @mbggenerated
     */
    private Integer maxIntervalTime;

    /**
     * 验证码有效时间(单位：分钟）
     *
     * @mbggenerated
     */
    private Integer maxValidTime;

    /**
     * 接受超限通知的手机号
     *
     * @mbggenerated
     */
    private String noticeToPhone;

    /**
     * 接受超限通知的邮箱
     *
     * @mbggenerated
     */
    private String noticeToEmail;

    /**
     * 发送超限通知的间隔时间
     *
     * @mbggenerated
     */
    private Integer noticeToTime;

    /**
     * 还款提醒手机号
     *
     * @mbggenerated
     */
    private String repayMobiles;

    /**
     * 满标提醒手机号
     *
     * @mbggenerated
     */
    private String fullMobiles;

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

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 修改时间
     *
     * @mbggenerated
     */
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