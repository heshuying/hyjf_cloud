package com.hyjf.am.vo.admin;

import java.io.Serializable;

/**
 * @author xiasq
 * @version ActivityUserRewardVO, v0.1 2019-04-18 16:10
 */
public class AdminActivityUserRewardVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer activityId;
	private Integer userId;
	private String userName;
	private String trueName;
	private String rewardName;
	private String rewardType;
	private String sendType;
	private Integer sendStatus;
	private String sendStatusName;
	private String getTime;
	private String createTime;
	private Integer grade;

    @Override
    public String toString() {
        return "ActivityUserRewardVO{" +
                "activityId=" + activityId +
                ", userId=" + userId +
                ", rewardName='" + rewardName + '\'' +
                ", rewardType='" + rewardType + '\'' +
                ", sendType='" + sendType + '\'' +
                ", sendStatus=" + sendStatus +
                '}';
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRewardName() {
        return rewardName;
    }

    public void setRewardName(String rewardName) {
        this.rewardName = rewardName;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTrueName() {
        return trueName;
    }

    public void setTrueName(String trueName) {
        this.trueName = trueName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGetTime() {
        return getTime = createTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getSendStatusName() {
        return sendStatusName;
    }

    public void setSendStatusName(String sendStatusName) {
        this.sendStatusName = sendStatusName;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
