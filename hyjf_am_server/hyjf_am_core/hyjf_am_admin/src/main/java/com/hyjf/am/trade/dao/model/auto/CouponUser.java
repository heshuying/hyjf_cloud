package com.hyjf.am.trade.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class CouponUser implements Serializable {
    /**
     * 主键id
     *
     * @mbggenerated
     */
    private Integer id;

    /**
     * 优惠券编号
     *
     * @mbggenerated
     */
    private String couponCode;

    /**
     * 优惠券用户编号
     *
     * @mbggenerated
     */
    private String couponUserCode;

    /**
     * 优惠券组编号
     *
     * @mbggenerated
     */
    private String couponGroupCode;

    /**
     * 用户编号
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * 活动编号
     *
     * @mbggenerated
     */
    private Integer activityId;

    /**
     * 1：手动发放，2：活动发放，3：vip礼包
     *
     * @mbggenerated
     */
    private Integer couponSource;

    /**
     * 优惠券使用标识 0：未使用，0：未使用，1：已使用，2：审核不通过，3：待审核，4：已失效
     *
     * @mbggenerated
     */
    private Integer usedFlag;

    /**
     * 0：未读，1：已读
     *
     * @mbggenerated
     */
    private Integer readFlag;

    /**
     * 使用截止时间
     *
     * @mbggenerated
     */
    private Integer endTime;

    private String content;

    /**
     * 审核备注
     *
     * @mbggenerated
     */
    private String auditContent;

    /**
     * 删除时备注
     *
     * @mbggenerated
     */
    private String deleteContent;

    /**
     * 审核用户
     *
     * @mbggenerated
     */
    private String auditUser;

    /**
     * 审核时间
     *
     * @mbggenerated
     */
    private Integer auditTime;

    /**
     * 用券时用户属性 0=>无主单 1=>有主单 2=>线下员工 3=>线上员工
     *
     * @mbggenerated
     */
    private Integer attribute;

    /**
     * 注册渠道
     *
     * @mbggenerated
     */
    private String channel;

    /**
     * 删除标识 0：未删除，1：已删除
     *
     * @mbggenerated
     */
    private Integer delFlag;

    /**
     * 创建人
     *
     * @mbggenerated
     */
    private Integer createUserId;

    /**
     * 修改人
     *
     * @mbggenerated
     */
    private Integer updateUserId;

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

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

    public String getCouponUserCode() {
        return couponUserCode;
    }

    public void setCouponUserCode(String couponUserCode) {
        this.couponUserCode = couponUserCode == null ? null : couponUserCode.trim();
    }

    public String getCouponGroupCode() {
        return couponGroupCode;
    }

    public void setCouponGroupCode(String couponGroupCode) {
        this.couponGroupCode = couponGroupCode == null ? null : couponGroupCode.trim();
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getCouponSource() {
        return couponSource;
    }

    public void setCouponSource(Integer couponSource) {
        this.couponSource = couponSource;
    }

    public Integer getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Integer usedFlag) {
        this.usedFlag = usedFlag;
    }

    public Integer getReadFlag() {
        return readFlag;
    }

    public void setReadFlag(Integer readFlag) {
        this.readFlag = readFlag;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent == null ? null : auditContent.trim();
    }

    public String getDeleteContent() {
        return deleteContent;
    }

    public void setDeleteContent(String deleteContent) {
        this.deleteContent = deleteContent == null ? null : deleteContent.trim();
    }

    public String getAuditUser() {
        return auditUser;
    }

    public void setAuditUser(String auditUser) {
        this.auditUser = auditUser == null ? null : auditUser.trim();
    }

    public Integer getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Integer auditTime) {
        this.auditTime = auditTime;
    }

    public Integer getAttribute() {
        return attribute;
    }

    public void setAttribute(Integer attribute) {
        this.attribute = attribute;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
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