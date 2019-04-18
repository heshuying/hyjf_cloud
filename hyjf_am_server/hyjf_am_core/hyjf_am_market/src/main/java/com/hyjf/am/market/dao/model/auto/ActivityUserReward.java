package com.hyjf.am.market.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class ActivityUserReward implements Serializable {
    /**
     * id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 活动id
     *
     * @mbggenerated
     */
    private Integer activityId;

    /**
     * 用户id
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 奖励名称
     *
     * @mbggenerated
     */
    private String rewardName;

    /**
     * 奖励类型
     *
     * @mbggenerated
     */
    private String rewardType;

    /**
     * 发放方式
     *
     * @mbggenerated
     */
    private String sendType;

    /**
     * 发放状态 1-已发放 0-未发放
     *
     * @mbggenerated
     */
    private Integer sendStatus;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
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
        this.rewardName = rewardName == null ? null : rewardName.trim();
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType == null ? null : rewardType.trim();
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType == null ? null : sendType.trim();
    }

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
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