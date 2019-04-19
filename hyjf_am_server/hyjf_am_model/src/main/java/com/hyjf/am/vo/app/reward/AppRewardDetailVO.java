package com.hyjf.am.vo.app.reward;

/**
 * @author wgx
 * @date 2019/4/18
 */
public class AppRewardDetailVO {

    // 累计奖金金额
    private String total;
    // 邀请好友人数
    private String friendCount;
    // 优惠券数量
    private int coupon;
    // 优惠券提示信息
    private String couponTag;
    private String userName;
    private Integer userId;
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

    public void setCoupon(int coupon) {
        this.coupon = coupon;
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
