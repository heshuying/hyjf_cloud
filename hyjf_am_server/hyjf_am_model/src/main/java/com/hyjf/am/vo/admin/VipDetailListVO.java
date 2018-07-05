/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.hyjf.am.vo.admin;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

/**
 * @author yaoyong
 * @version VipDetailListVO, v0.1 2018/7/3 11:41
 */
public class VipDetailListVO extends BaseVO implements Serializable {
    private String userId;

    private String userName;

    private String tenderNid;

    private String account;

    private String tradeTime;

    private String vipName;

    private String tenderVipValue;

    private String sumVipValue;

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

    public String getTenderNid() {
        return tenderNid;
    }

    public void setTenderNid(String tenderNid) {
        this.tenderNid = tenderNid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getTenderVipValue() {
        return tenderVipValue;
    }

    public void setTenderVipValue(String tenderVipValue) {
        this.tenderVipValue = tenderVipValue;
    }

    public String getSumVipValue() {
        return sumVipValue;
    }

    public void setSumVipValue(String sumVipValue) {
        this.sumVipValue = sumVipValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
