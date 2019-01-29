package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class VipUserUpgrade implements Serializable {
    private Integer id;

    /**
     * 用户编号
     *
     * @mbggenerated
     */
    private Integer userId;

    /**
     * vip编号
     *
     * @mbggenerated
     */
    private Integer vipId;

    /**
     * vip升级时的V值
     *
     * @mbggenerated
     */
    private Integer upgradeVipValue;

    /**
     * vip升级的类别，1：购买，2：V值升级
     *
     * @mbggenerated
     */
    private Integer upgradeVipType;

    /**
     * 礼包发放状态，0：未发放，1：已发放
     *
     * @mbggenerated
     */
    private Integer giftFlg;

    /**
     * 备注
     *
     * @mbggenerated
     */
    private String remark;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getUpgradeVipValue() {
        return upgradeVipValue;
    }

    public void setUpgradeVipValue(Integer upgradeVipValue) {
        this.upgradeVipValue = upgradeVipValue;
    }

    public Integer getUpgradeVipType() {
        return upgradeVipType;
    }

    public void setUpgradeVipType(Integer upgradeVipType) {
        this.upgradeVipType = upgradeVipType;
    }

    public Integer getGiftFlg() {
        return giftFlg;
    }

    public void setGiftFlg(Integer giftFlg) {
        this.giftFlg = giftFlg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
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