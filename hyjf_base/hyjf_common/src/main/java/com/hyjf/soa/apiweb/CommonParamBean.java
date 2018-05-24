package com.hyjf.soa.apiweb;

import java.io.Serializable;

public class CommonParamBean implements Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2569482809922162226L;
	
	/**
	 * 用户id
	 */
	private String userId;
	/**
	 * 发放优惠券的类别标识
	 */
	private Integer sendFlg;
	
	/**
	 * 发放优惠券子分类区分标识
	 */
	private Integer subFlg;
	
	/**
	 * vip编号
	 */
	private int vipId;
	
	/**
     * 奖品分组编号
     */
    private String prizeGroupCode;
    
    /**
     * 发放张数
     */
    private Integer sendCount;
    
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
	
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
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
    public Integer getSendCount() {
        return sendCount;
    }
    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }
	public Integer getSubFlg() {
		return subFlg;
	}
	public void setSubFlg(Integer subFlg) {
		this.subFlg = subFlg;
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
    public void setSendFlg(Integer sendFlg) {
        this.sendFlg = sendFlg;
    }
    public void setVipId(int vipId) {
        this.vipId = vipId;
    }
    public Integer getSendFlg() {
        return sendFlg;
    }
	
	
	
}
