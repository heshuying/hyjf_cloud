package com.hyjf.am.vo.trade.hjh;

import com.hyjf.am.vo.BaseVO;

import java.io.Serializable;

public class HjhAccedeCustomizeVO extends BaseVO implements Serializable {

    //用户id
    public String userId;
    //vip等级
    private String vipId;
    //vip等级
    private String vipLevel;
    //用户名
    public String userName;
    //加入金额
    public String accedeAccount;
    //加入时间
    public String accedeTime;
    //加入平台
    public String client;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public String getVipLevel() {
        return vipLevel;
    }

    public void setVipLevel(String vipLevel) {
        this.vipLevel = vipLevel;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccedeAccount() {
        return accedeAccount;
    }

    public void setAccedeAccount(String accedeAccount) {
        this.accedeAccount = accedeAccount;
    }

    public String getAccedeTime() {
        return accedeTime;
    }

    public void setAccedeTime(String accedeTime) {
        this.accedeTime = accedeTime;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}
