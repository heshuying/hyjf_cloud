package com.hyjf.am.user.dao.model.auto;

import java.io.Serializable;

public class VipUserUpgrade implements Serializable {
    private Integer id;

    private Integer userId;

    private Integer vipId;

    private Integer upgradeVipValue;

    private Integer upgradeVipType;

    private Integer giftFlg;

    private String remark;

    private Integer addTime;

    private String addUser;

    private Integer updateTime;

    private String updateUser;

    private Integer delFlg;

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

    public Integer getAddTime() {
        return addTime;
    }

    public void setAddTime(Integer addTime) {
        this.addTime = addTime;
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

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}