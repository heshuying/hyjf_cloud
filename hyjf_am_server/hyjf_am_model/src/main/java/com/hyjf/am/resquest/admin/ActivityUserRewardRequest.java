/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;

/**
 * @author yaoyong
 * @version ActivityUserRewardRequest, v0.1 2019/4/19 9:26
 */
public class ActivityUserRewardRequest extends BasePage {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 姓名
     */
    private String trueName;

    /**
     * 发放状态
     */
    private Integer sendStatus;

    /**
     * 排序
     * @return
     */
    private Integer sort;

    /**
     * 奖励代号
     */
    private Integer rewardCode;

    /**
     * 活动id
     */
    private Integer activityId;

    @Override
    public String toString() {
        return "ActivityUserRewardRequest{" +
                "userName='" + userName + '\'' +
                ", trueName='" + trueName + '\'' +
                ", sendStatus=" + sendStatus +
                ", sort=" + sort +
                ", rewardCode=" + rewardCode +
                ", activityId=" + activityId +
                '}';
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

    public Integer getSendStatus() {
        return sendStatus;
    }

    public void setSendStatus(Integer sendStatus) {
        this.sendStatus = sendStatus;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getRewardCode() {
        return rewardCode;
    }

    public void setRewardCode(Integer rewardCode) {
        this.rewardCode = rewardCode;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }
}
