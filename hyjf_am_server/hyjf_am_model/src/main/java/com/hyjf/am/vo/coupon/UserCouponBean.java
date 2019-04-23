package com.hyjf.am.vo.coupon;

import java.io.Serializable;

/**
 * @Auther: walter.limeng
 * @Date: 2018/7/16 15:18
 * @Description: UserCouponBean
 */
public class UserCouponBean implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2569482809922162226L;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 发放优惠券的类别标识
     */
    private Integer sendFlg;
    /**
     * 发放优惠券子分类区分标识
     */
    private Integer subFlg;

    /**
     * 发放时间
     */
    private int sendTime;
    /**
     * 发放优惠券的类别标识
     */
    private String sign;
    /**
     * vip编号
     */
    private Integer vipId;

    /**
     * 奖品分组编号
     */
    private String prizeGroupCode;

    /**
     * 发放张数
     */
    private int sendCount;
    /**
     * 优惠券编号
     */
    private String couponCode;
    /**
     * 来源 1：手动 2：活动 3：vip礼包
     */
    private Integer couponSource;
    /**
     * 活动id
     */
    private Integer activityId;
    /**
     * 备注
     */
    private String remark;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSendFlg() {
        return sendFlg;
    }

    public void setSendFlg(Integer sendFlg) {
        this.sendFlg = sendFlg;
    }

    public Integer getSubFlg() {
        return subFlg;
    }

    public void setSubFlg(Integer subFlg) {
        this.subFlg = subFlg;
    }

    public int getSendTime() {
        return sendTime;
    }

    public void setSendTime(int sendTime) {
        this.sendTime = sendTime;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public String getPrizeGroupCode() {
        return prizeGroupCode;
    }

    public void setPrizeGroupCode(String prizeGroupCode) {
        this.prizeGroupCode = prizeGroupCode;
    }

    public int getSendCount() {
        return sendCount;
    }

    public void setSendCount(int sendCount) {
        this.sendCount = sendCount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public Integer getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(Integer couponSource) {
        this.couponSource = couponSource;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
