/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.resquest.admin;

import com.hyjf.am.resquest.Request;
import com.hyjf.am.vo.BasePage;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version CouponUserBeanRequest, v0.1 2018/7/23 16:04
 */
public class CouponUserBeanRequest extends BasePage implements Serializable {

    private Integer id;

    @ApiModelProperty(value = "优惠券id")
    private Integer couponId;

    @ApiModelProperty(value = "优惠券编码")
    private String couponCode;

    //优惠券编码
    private String couponUserCode;

    //优惠券名字
    private String couponName;

    @ApiModelProperty(value = "优惠券类型")
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

    @ApiModelProperty(value = "优惠券来源")
    private String couponFrom;

    private Integer userId;

    @ApiModelProperty(value = "用户名")
    private String userName;

    //备注
    private String content;

    @ApiModelProperty(value = "使用状态")
    private String usedFlag;

    //使用状态（页面展现）
    private String usedFlagView;

    private String endTime;

    private String addTime;

    private String addUser;

    private String updateTime;

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

    /**
     * 审核口令
     */
    private String couponAuditPwd;

    /**
     * 检索条件 审批状态
     */
    private String auditStatus;

    @ApiModelProperty(value = "有效时间开始")
    private String timeStartSrch;

    @ApiModelProperty(value = "有效时间结束")
    private String timeEndSrch;


    @ApiModelProperty(value = "优惠券获得时间开始")
    private String timeStartAddSrch;

    @ApiModelProperty(value = "优惠券获取时间结束")
    private String timeEndAddSrch;
    /**
     * 检索条件 审批备注
     */
    private String description;

    private Integer amount;

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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
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

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getCouponFrom() {
        return couponFrom;
    }

    public void setCouponFrom(String couponFrom) {
        this.couponFrom = couponFrom;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(String usedFlag) {
        this.usedFlag = usedFlag;
    }

    public String getUsedFlagView() {
        return usedFlagView;
    }

    public void setUsedFlagView(String usedFlagView) {
        this.usedFlagView = usedFlagView;
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

    public String getAddUser() {
        return addUser;
    }

    public void setAddUser(String addUser) {
        this.addUser = addUser;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
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

    public String getTimeStartSrch() {
        return timeStartSrch;
    }

    public void setTimeStartSrch(String timeStartSrch) {
        this.timeStartSrch = timeStartSrch;
    }

    public String getTimeEndSrch() {
        return timeEndSrch;
    }

    public void setTimeEndSrch(String timeEndSrch) {
        this.timeEndSrch = timeEndSrch;
    }

    public String getTimeStartAddSrch() {
        return timeStartAddSrch;
    }

    public void setTimeStartAddSrch(String timeStartAddSrch) {
        this.timeStartAddSrch = timeStartAddSrch;
    }

    public String getTimeEndAddSrch() {
        return timeEndAddSrch;
    }

    public void setTimeEndAddSrch(String timeEndAddSrch) {
        this.timeEndAddSrch = timeEndAddSrch;
    }

//    public int getLimit() {
//        return limit;
//    }
//
//    public void setLimit(int limit) {
//        this.limit = limit;
//    }
//
//    public void setPaginatorPage(int paginatorPage) {
//        this.paginatorPage = paginatorPage;
//    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getCouponAuditPwd() {
        return couponAuditPwd;
    }

    public void setCouponAuditPwd(String couponAuditPwd) {
        this.couponAuditPwd = couponAuditPwd;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus;
    }
}
