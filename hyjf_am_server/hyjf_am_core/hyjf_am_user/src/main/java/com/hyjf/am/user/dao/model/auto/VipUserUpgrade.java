package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;
import java.util.Date;

public class VipUserUpgrade implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer vipId;

    private Integer upgradeVipValue;

    private Integer upgradeVipType;

    private Integer giftFlg;

    private String remark;

    private Integer delFlag;

    private Integer createUserId;

    private Integer updateUserId;

    private Date createTime;

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