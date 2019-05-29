package com.hyjf.am.vo.app.reward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "邀请记录", description = "邀请记录")
public class AppInviteRecordVO implements Serializable {
    private static final long serialVersionUID = -7616107535997777728L;
    @ApiModelProperty(value = "好友名称")
    private String friendName;
    @ApiModelProperty(value = "注册时间", example = "2017-07-19 10:52:46")
    private String date;
    @ApiModelProperty(value = "用户状态", example = "未开户/已开户")
    private String openStatus;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getOpenStatus() {
        return openStatus;
    }

    public void setOpenStatus(String openStatus) {
        this.openStatus = openStatus;
    }
}
