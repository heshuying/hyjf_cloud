/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin.coupon;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CouponUserCustomizeVO, v0.1 2018/7/23 15:57
 */
public class CouponUserCustomizeVO extends BaseVO implements Serializable {
    private Integer id;

    private Integer couponId;

    //优惠券编码
    private String couponCode;

    //优惠券编码
    private String couponUserCode;

    //优惠券名字
    private String couponName;

    //优惠券类型
    private String couponType;

    //优惠券额度
    private String couponQuota;

    //优惠券适用平台
    private String couponSystem;

    //优惠券适用产品类型
    private String projectType;

    //投资额度条件
    private String tenderQuota;

    //投资项目期限条件
    private String projectExpirationType;

    //活动id
    private Integer activityId;

    //优惠券来源
    private String couponFrom;

    private Integer userId;

    //用户名
    private String username;

    //备注
    private String content;

    //使用状态
    private String usedFlag;

    //使用状态（页面展现）
    private String usedFlagView;

    private String endTime;

    private String addTime;

    private String addUser;

    private Integer updateTime;

    private String updateUser;

    private Integer delFlag;

    private String detailUrl;

    private String host;

    private String readFlag;

    private String couponSource;
    private String couponContent;

    private String appUtmSource;

    private String pcUtmSource;

    private String attribute;

    private String channel;

    //检索条件 limitStart
    private int limitStart = -1;

    //检索条件 limitEnd
    private int limitEnd = -1;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
    }

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser == null ? null : addUser.trim();
    }

    public Integer getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Integer updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType;
    }

    public String getCouponQuota() {
        return couponQuota;
    }

    public void setCouponQuota(String couponQuota) {
        this.couponQuota = couponQuota;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    public int getLimitEnd() {
        return limitEnd;
    }

    public void setLimitEnd(int limitEnd) {
        this.limitEnd = limitEnd;
    }

    public String getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(String couponFrom) {
        this.couponFrom = couponFrom;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCouponSystem() {
        return couponSystem;
    }

    public void setCouponSystem(String couponSystem) {
        this.couponSystem = couponSystem;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getTenderQuota() {
        return tenderQuota;
    }

    public void setTenderQuota(String tenderQuota) {
        this.tenderQuota = tenderQuota;
    }

    public String getProjectExpirationType() {
        return projectExpirationType;
    }

    public void setProjectExpirationType(String projectExpirationType) {
        this.projectExpirationType = projectExpirationType;
    }

    public String getUsedFlagView() {
        return usedFlagView;
    }

    public void setUsedFlagView(String usedFlagView) {
        this.usedFlagView = usedFlagView;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(String readFlag) {
        this.readFlag = readFlag;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(String couponSource) {
        this.couponSource = couponSource;
    }

    public String getCouponContent() {
        return couponContent;
    }

    public void setCouponContent(String couponContent) {
        this.couponContent = couponContent;
    }

    public String getAppUtmSource() {
        return appUtmSource;
    }

    public void setAppUtmSource(String appUtmSource) {
        this.appUtmSource = appUtmSource;
    }

    public String getPcUtmSource() {
        return pcUtmSource;
    }

    public void setPcUtmSource(String pcUtmSource) {
        this.pcUtmSource = pcUtmSource;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }
}
