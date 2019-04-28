package com.hyjf.am.vo.app.reward;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author wgx
 * @date 2019/4/18
 */
@ApiModel(value = "我的奖励信息", description = "我的奖励信息")
public class AppRewardDetailVO implements Serializable {
    private static final long serialVersionUID = 3543816915996261068L;
    @ApiModelProperty(value = "累计奖金金额")
    private String total;
    @ApiModelProperty(value = "邀请好友人数")
    private String friendCount;
    @ApiModelProperty(value = "优惠券数量", example = "0")
    private String couponCount;
    @ApiModelProperty(value = "优惠券提示信息", example = "邀请好友获得")
    private String couponTag;
    @ApiModelProperty(value = "用户名")
    private String userName;
    @ApiModelProperty(value = "用户id")
    private Integer userId;
    @ApiModelProperty(value = "app头像的url", example = "https://img.hyjf.com/...")
    private String iconUrl;

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getFriendCount() {
        return friendCount;
    }

    public void setFriendCount(String friendCount) {
        this.friendCount = friendCount;
    }

    public String getCouponCount() {
        return couponCount;
    }

    public void setCouponCount(String couponCount) {
        this.couponCount = couponCount;
    }

    public String getCouponTag() {
        return couponTag;
    }

    public void setCouponTag(String couponTag) {
        this.couponTag = couponTag;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
}
