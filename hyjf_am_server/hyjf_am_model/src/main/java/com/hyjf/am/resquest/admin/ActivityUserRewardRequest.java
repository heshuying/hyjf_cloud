/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author yaoyong
 * @version ActivityUserRewardRequest, v0.1 2019/4/19 9:26
 */
@ApiModel("奖励列表请求参数")
public class ActivityUserRewardRequest extends BasePage {

    /**
     * 用户名
     */
    @ApiModelProperty(value = "用户名（过滤条件）")
    private String userName;

    /**
     * 姓名
     */
    @ApiModelProperty(value = "姓名（过滤条件）")
    private String trueName;

    /**
     * 发放状态
     */
    @ApiModelProperty(value = "发放状态（过滤条件）")
    private Integer sendStatus;

    /**
     * 排序
     * @return
     */
    @ApiModelProperty(value = "排序（1-按照创建时间升序，2-按照创建时间倒序）")
    private Integer sort;

    /**
     * 奖励代号
     */
    @ApiModelProperty(value = "奖励代号（过滤条件），代号参考接口文档编码")
    private Integer rewardCode;

    /**
     * 活动id
     */
    @ApiModelProperty(value = "活动id,从活动列表获取的id,必要值")
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
