/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.user.dao.model.customize;

/**
 * @author yaoyong
 * @version VipUpdateGradeListCustomize, v0.1 2018/7/4 9:41
 */
public class VipUpdateGradeListCustomize {
    private String userId;

    private String userName;

    private String vipName;

    private String upgradeVipType;

    private String upgradeTime;

    private String upgradeVipValue;

    private String giftFlg;

    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getUpgradeVipType() {
        return upgradeVipType;
    }

    public void setUpgradeVipType(String upgradeVipType) {
        this.upgradeVipType = upgradeVipType;
    }

    public String getUpgradeTime() {
        return upgradeTime;
    }

    public void setUpgradeTime(String upgradeTime) {
        this.upgradeTime = upgradeTime;
    }

    public String getUpgradeVipValue() {
        return upgradeVipValue;
    }

    public void setUpgradeVipValue(String upgradeVipValue) {
        this.upgradeVipValue = upgradeVipValue;
    }

    public String getGiftFlg() {
        return giftFlg;
    }

    public void setGiftFlg(String giftFlg) {
        this.giftFlg = giftFlg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
